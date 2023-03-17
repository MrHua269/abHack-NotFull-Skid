/*     */ package me.abHack.features.modules.player;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.RotationUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoWither
/*     */   extends Module
/*     */ {
/*     */   private static boolean isSneaking;
/*     */   private final Setting<UseMode> useMode;
/*     */   private final Setting<Float> placeRange;
/*     */   private final Setting<Integer> delay;
/*     */   private final Setting<Boolean> rotate;
/*     */   private final Setting<Boolean> debug;
/*     */   private BlockPos placeTarget;
/*     */   private boolean rotationPlaceableX;
/*     */   private boolean rotationPlaceableZ;
/*     */   private int bodySlot;
/*     */   private int headSlot;
/*     */   private int buildStage;
/*     */   private int delayStep;
/*     */   
/*     */   public AutoWither() {
/*  54 */     super("AutoWither", "Automatically places a wither", Module.Category.PLAYER, true, false, false);
/*  55 */     this.useMode = register(new Setting("UseMode", UseMode.SPAM));
/*  56 */     this.placeRange = register(new Setting("PlaceRange", Float.valueOf(3.5F), Float.valueOf(2.0F), Float.valueOf(6.0F)));
/*  57 */     this.delay = register(new Setting("Delay", Integer.valueOf(20), Integer.valueOf(12), Integer.valueOf(100), v -> ((UseMode)this.useMode.getValue()).equals(UseMode.SPAM)));
/*  58 */     this.rotate = register(new Setting("Rotate", Boolean.TRUE));
/*  59 */     this.debug = register(new Setting("Debug", Boolean.FALSE));
/*     */   }
/*     */   
/*     */   private static EnumFacing getPlaceableSide(BlockPos pos) {
/*  63 */     for (EnumFacing side : EnumFacing.values()) {
/*  64 */       BlockPos neighbour = pos.offset(side);
/*  65 */       if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
/*  66 */         IBlockState blockState = mc.world.getBlockState(neighbour);
/*  67 */         if (!blockState.getMaterial().isReplaceable() && !(blockState.getBlock() instanceof net.minecraft.block.BlockTallGrass) && !(blockState.getBlock() instanceof net.minecraft.block.BlockDeadBush))
/*  68 */           return side; 
/*     */       } 
/*     */     } 
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   private static void placeBlock(BlockPos pos, boolean rotate) {
/*  75 */     EnumFacing side = getPlaceableSide(pos);
/*  76 */     if (side == null)
/*     */       return; 
/*  78 */     BlockPos neighbour = pos.offset(side);
/*  79 */     EnumFacing opposite = side.getOpposite();
/*  80 */     Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
/*  81 */     Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();
/*  82 */     if (!isSneaking && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
/*  83 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
/*  84 */       isSneaking = true;
/*     */     } 
/*  86 */     if (rotate)
/*  87 */       faceVectorPacketInstant(hitVec); 
/*  88 */     mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
/*  89 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/*  90 */     mc.rightClickDelayTimer = 4;
/*     */   }
/*     */   
/*     */   public static void faceVectorPacketInstant(Vec3d vec) {
/*  94 */     float[] rotations = RotationUtil.getLegitRotations(vec);
/*  95 */     ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc.getConnection())).sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  99 */     if (mc.player == null) {
/* 100 */       disable();
/*     */       return;
/*     */     } 
/* 103 */     this.buildStage = 1;
/* 104 */     this.delayStep = 1;
/*     */   }
/*     */   
/*     */   private boolean checkBlocksInHotbar() {
/* 108 */     this.headSlot = -1;
/* 109 */     this.bodySlot = -1;
/* 110 */     for (int i = 0; i < 9; i++) {
/* 111 */       ItemStack stack = mc.player.inventory.getStackInSlot(i);
/* 112 */       if (stack != ItemStack.EMPTY)
/* 113 */         if (stack.getItem() == Items.SKULL && stack.getItemDamage() == 1) {
/* 114 */           if ((mc.player.inventory.getStackInSlot(i)).stackSize >= 3)
/* 115 */             this.headSlot = i; 
/* 116 */         } else if (stack.getItem() instanceof ItemBlock) {
/* 117 */           Block block = ((ItemBlock)stack.getItem()).getBlock();
/* 118 */           if (block instanceof net.minecraft.block.BlockSoulSand && 
/* 119 */             (mc.player.inventory.getStackInSlot(i)).stackSize >= 4)
/* 120 */             this.bodySlot = i; 
/*     */         }  
/*     */     } 
/* 123 */     return (this.bodySlot != -1 && this.headSlot != -1);
/*     */   }
/*     */   
/*     */   private boolean testStructure() {
/* 127 */     return testWitherStructure();
/*     */   }
/*     */   
/*     */   private boolean testWitherStructure() {
/* 131 */     boolean noRotationPlaceable = true;
/* 132 */     this.rotationPlaceableX = true;
/* 133 */     this.rotationPlaceableZ = true;
/* 134 */     boolean isShitGrass = false;
/* 135 */     mc.world.getBlockState(this.placeTarget);
/* 136 */     Block block = mc.world.getBlockState(this.placeTarget).getBlock();
/* 137 */     if (block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockDeadBush)
/* 138 */       isShitGrass = true; 
/* 139 */     if (getPlaceableSide(this.placeTarget.up()) == null)
/* 140 */       return false; 
/* 141 */     for (BlockPos pos : BodyParts.bodyBase) {
/* 142 */       if (placingIsBlocked(this.placeTarget.add((Vec3i)pos)))
/* 143 */         noRotationPlaceable = false; 
/*     */     } 
/* 145 */     for (BlockPos pos : BodyParts.ArmsX) {
/* 146 */       if (placingIsBlocked(this.placeTarget.add((Vec3i)pos)) || placingIsBlocked(this.placeTarget.add((Vec3i)pos.down())))
/* 147 */         this.rotationPlaceableX = false; 
/*     */     } 
/* 149 */     for (BlockPos pos : BodyParts.ArmsZ) {
/* 150 */       if (placingIsBlocked(this.placeTarget.add((Vec3i)pos)) || placingIsBlocked(this.placeTarget.add((Vec3i)pos.down())))
/* 151 */         this.rotationPlaceableZ = false; 
/*     */     } 
/* 153 */     for (BlockPos pos : BodyParts.headsX) {
/* 154 */       if (placingIsBlocked(this.placeTarget.add((Vec3i)pos)))
/* 155 */         this.rotationPlaceableX = false; 
/*     */     } 
/* 157 */     for (BlockPos pos : BodyParts.headsZ) {
/* 158 */       if (placingIsBlocked(this.placeTarget.add((Vec3i)pos)))
/* 159 */         this.rotationPlaceableZ = false; 
/*     */     } 
/* 161 */     return (!isShitGrass && noRotationPlaceable && (this.rotationPlaceableX || this.rotationPlaceableZ));
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 165 */     if (mc.player == null)
/*     */       return; 
/* 167 */     if (this.buildStage == 1) {
/* 168 */       isSneaking = false;
/* 169 */       this.rotationPlaceableX = false;
/* 170 */       this.rotationPlaceableZ = false;
/* 171 */       if (!checkBlocksInHotbar()) {
/* 172 */         if (((Boolean)this.debug.getValue()).booleanValue())
/* 173 */           Command.sendMessage("[AutoSpawner] " + ChatFormatting.RED + "Blocks missing for: " + ChatFormatting.RESET + "Wither" + ChatFormatting.RED + ", disabling."); 
/* 174 */         disable();
/*     */         return;
/*     */       } 
/* 177 */       List<BlockPos> blockPosList = BlockUtil.getSphere(mc.player.getPosition().down(), ((Float)this.placeRange.getValue()).floatValue(), ((Float)this.placeRange.getValue()).intValue(), false, true, 0);
/* 178 */       boolean noPositionInArea = true;
/* 179 */       for (BlockPos pos : blockPosList) {
/* 180 */         this.placeTarget = pos.down();
/* 181 */         if (testStructure()) {
/* 182 */           noPositionInArea = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 186 */       if (noPositionInArea) {
/* 187 */         if (((UseMode)this.useMode.getValue()).equals(UseMode.SINGLE)) {
/* 188 */           if (((Boolean)this.debug.getValue()).booleanValue())
/* 189 */             Command.sendMessage("[AutoSpawner] " + ChatFormatting.RED + "Position not valid, disabling."); 
/* 190 */           disable();
/*     */         } 
/*     */         return;
/*     */       } 
/* 194 */       mc.player.inventory.currentItem = this.bodySlot;
/* 195 */       for (BlockPos pos : BodyParts.bodyBase)
/* 196 */         placeBlock(this.placeTarget.add((Vec3i)pos), ((Boolean)this.rotate.getValue()).booleanValue()); 
/* 197 */       if (this.rotationPlaceableX) {
/* 198 */         for (BlockPos pos : BodyParts.ArmsX)
/* 199 */           placeBlock(this.placeTarget.add((Vec3i)pos), ((Boolean)this.rotate.getValue()).booleanValue()); 
/* 200 */       } else if (this.rotationPlaceableZ) {
/* 201 */         for (BlockPos pos : BodyParts.ArmsZ)
/* 202 */           placeBlock(this.placeTarget.add((Vec3i)pos), ((Boolean)this.rotate.getValue()).booleanValue()); 
/*     */       } 
/* 204 */       this.buildStage = 2;
/* 205 */     } else if (this.buildStage == 2) {
/* 206 */       mc.player.inventory.currentItem = this.headSlot;
/* 207 */       if (this.rotationPlaceableX) {
/* 208 */         for (BlockPos pos : BodyParts.headsX)
/* 209 */           placeBlock(this.placeTarget.add((Vec3i)pos), ((Boolean)this.rotate.getValue()).booleanValue()); 
/* 210 */       } else if (this.rotationPlaceableZ) {
/* 211 */         for (BlockPos pos : BodyParts.headsZ)
/* 212 */           placeBlock(this.placeTarget.add((Vec3i)pos), ((Boolean)this.rotate.getValue()).booleanValue()); 
/*     */       } 
/* 214 */       if (isSneaking) {
/* 215 */         mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
/* 216 */         isSneaking = false;
/*     */       } 
/* 218 */       if (((UseMode)this.useMode.getValue()).equals(UseMode.SINGLE))
/* 219 */         disable(); 
/* 220 */       this.buildStage = 3;
/* 221 */     } else if (this.buildStage == 3) {
/* 222 */       if (this.delayStep < ((Integer)this.delay.getValue()).intValue()) {
/* 223 */         this.delayStep++;
/*     */       } else {
/* 225 */         this.delayStep = 1;
/* 226 */         this.buildStage = 1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean placingIsBlocked(BlockPos pos) {
/* 233 */     Block block = mc.world.getBlockState(pos).getBlock();
/* 234 */     if (!(block instanceof net.minecraft.block.BlockAir))
/* 235 */       return true; 
/* 236 */     for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos))) {
/* 237 */       if (!(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb))
/* 238 */         return true; 
/*     */     } 
/* 240 */     return false;
/*     */   }
/*     */   
/*     */   private enum UseMode {
/* 244 */     SINGLE, SPAM;
/*     */   }
/*     */   
/*     */   private static class BodyParts {
/* 248 */     private static final BlockPos[] bodyBase = new BlockPos[] { new BlockPos(0, 1, 0), new BlockPos(0, 2, 0) };
/*     */     
/* 250 */     private static final BlockPos[] ArmsX = new BlockPos[] { new BlockPos(-1, 2, 0), new BlockPos(1, 2, 0) };
/*     */     
/* 252 */     private static final BlockPos[] ArmsZ = new BlockPos[] { new BlockPos(0, 2, -1), new BlockPos(0, 2, 1) };
/*     */     
/* 254 */     private static final BlockPos[] headsX = new BlockPos[] { new BlockPos(0, 3, 0), new BlockPos(-1, 3, 0), new BlockPos(1, 3, 0) };
/*     */     
/* 256 */     private static final BlockPos[] headsZ = new BlockPos[] { new BlockPos(0, 3, 0), new BlockPos(0, 3, -1), new BlockPos(0, 3, 1) };
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\AutoWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
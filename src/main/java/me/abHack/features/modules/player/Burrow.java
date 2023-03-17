/*     */ package me.abHack.features.modules.player;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.combat.Surround;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEnderChest;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class Burrow extends Module {
/*  32 */   private final Setting<Boolean> breakcrystal = register(new Setting("BreakCrystal", Boolean.valueOf(false))); public static Burrow INSTANCE;
/*  33 */   private final Setting<Boolean> antiSuicide = register(new Setting("AntiSuicide", Boolean.valueOf(true), v -> ((Boolean)this.breakcrystal.getValue()).booleanValue()));
/*  34 */   private final Setting<Boolean> center = register(new Setting("TPCenter", Boolean.valueOf(false)));
/*  35 */   public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(false)));
/*     */   private BlockPos player;
/*     */   
/*     */   public Burrow() {
/*  39 */     super("Burrow", "Rubberbands you into a block", Module.Category.PLAYER, true, false, false);
/*  40 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  46 */     this.player = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/*     */     
/*  48 */     if (!BlockUtil.isAir(this.player) || !BlockUtil.isAir(this.player.add(0, 2, 0))) {
/*  49 */       disable();
/*     */       return;
/*     */     } 
/*  52 */     if (((Boolean)this.breakcrystal.getValue()).booleanValue() && BlockUtil.CrystalCheck(this.player))
/*  53 */       Surround.breakcrystal(((Boolean)this.antiSuicide.getValue()).booleanValue()); 
/*  54 */     BlockPos startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
/*  55 */     if (((Boolean)this.center.getValue()).booleanValue()) {
/*  56 */       OyVey.positionManager.setPositionPacket(startPos.getX() + 0.5D, startPos.getY(), startPos.getZ() + 0.5D, true, true, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  61 */     if (mc.player.onGround) {
/*  62 */       int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/*  63 */       int chestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
/*  64 */       int old = mc.player.inventory.currentItem;
/*  65 */       if (obbySlot == -1 && chestSlot == -1) {
/*  66 */         Command.sendMessage(ChatFormatting.RED + "No Obsidian in hotbar disabling...");
/*  67 */         disable();
/*     */         return;
/*     */       } 
/*  70 */       if (old != InventoryUtil.findHotbarBlock(BlockEnderChest.class))
/*  71 */         if (obbySlot != -1) {
/*  72 */           InventoryUtil.switchToSlot(obbySlot);
/*     */         } else {
/*  74 */           InventoryUtil.switchToSlot(chestSlot);
/*     */         }  
/*  76 */       mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
/*  77 */       mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
/*  78 */       mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.9999957640154541D, mc.player.posZ, true));
/*  79 */       mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.06610926093821D, mc.player.posZ, true));
/*  80 */       placeBlock(this.player, EnumHand.MAIN_HAND, false);
/*  81 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
/*  82 */       mc.player.setSneaking(false);
/*  83 */       mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + ez(), mc.player.posZ, false));
/*  84 */       InventoryUtil.switchToSlot(old);
/*     */     } 
/*     */     
/*  87 */     disable();
/*     */   }
/*     */   
/*     */   public double ez() {
/*  91 */     if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 3.0D, mc.player.posZ)).getBlock() != Blocks.AIR)
/*  92 */       return 1.2D; 
/*  93 */     double lol = 2.2D;
/*  94 */     for (int i = 4; i < 13; i++) {
/*  95 */       if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + i, mc.player.posZ)).getBlock() != Blocks.AIR)
/*  96 */         return lol + i - 4.0D; 
/*     */     } 
/*  98 */     return 10.0D;
/*     */   }
/*     */   
/*     */   public List<EnumFacing> getPossibleSides(BlockPos pos) {
/* 102 */     ArrayList<EnumFacing> facings = new ArrayList<>();
/* 103 */     if (mc.world == null || pos == null)
/* 104 */       return facings; 
/* 105 */     for (EnumFacing side : EnumFacing.values()) {
/* 106 */       BlockPos neighbour = pos.offset(side);
/* 107 */       IBlockState blockState = mc.world.getBlockState(neighbour);
/* 108 */       if (blockState.getBlock().canCollideCheck(blockState, false) && !blockState.getMaterial().isReplaceable())
/* 109 */         facings.add(side); 
/*     */     } 
/* 111 */     return facings;
/*     */   }
/*     */   
/*     */   public void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
/* 115 */     if (packet) {
/* 116 */       float f = (float)(vec.x - pos.getX());
/* 117 */       float f1 = (float)(vec.y - pos.getY());
/* 118 */       float f2 = (float)(vec.z - pos.getZ());
/* 119 */       mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
/*     */     } else {
/* 121 */       mc.playerController.processRightClickBlock(mc.player, mc.world, pos, direction, vec, hand);
/*     */     } 
/* 123 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/* 124 */     mc.rightClickDelayTimer = 4;
/*     */   }
/*     */   
/*     */   public boolean placeBlock(BlockPos pos, EnumHand hand, boolean isSneaking) {
/* 128 */     boolean sneaking = false;
/* 129 */     EnumFacing side = null;
/* 130 */     Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
/* 131 */     if (iterator.hasNext())
/* 132 */       side = iterator.next(); 
/* 133 */     if (side == null)
/* 134 */       return isSneaking; 
/* 135 */     BlockPos neighbour = pos.offset(side);
/* 136 */     EnumFacing opposite = side.getOpposite();
/* 137 */     Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
/* 138 */     Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();
/* 139 */     if (!mc.player.isSneaking() && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
/* 140 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
/* 141 */       mc.player.setSneaking(true);
/* 142 */       sneaking = true;
/*     */     } 
/* 144 */     rightClickBlock(neighbour, hitVec, hand, opposite, true);
/* 145 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/* 146 */     mc.rightClickDelayTimer = 4;
/* 147 */     return (sneaking || isSneaking);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Burrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
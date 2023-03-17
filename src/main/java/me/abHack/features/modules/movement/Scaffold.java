/*     */ package me.abHack.features.modules.movement;
/*     */ import java.util.Arrays;
import java.util.List;
/*     */ import me.abHack.OyVey;
import me.abHack.event.events.UpdateWalkingPlayerEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Scaffold extends Module {
/*  36 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Legit));
/*  37 */   private final Setting<Boolean> swing = register(new Setting("Swing Arm", Boolean.FALSE, v -> (this.mode.getValue() == Mode.Legit)));
/*  38 */   private final Setting<Boolean> bSwitch = register(new Setting("Switch", Boolean.TRUE, v -> (this.mode.getValue() == Mode.Legit)));
/*  39 */   private final Setting<Boolean> center = register(new Setting("Center", Boolean.FALSE, v -> (this.mode.getValue() == Mode.Legit)));
/*  40 */   private final Setting<Boolean> keepY = register(new Setting("KeepYLevel", Boolean.FALSE, v -> (this.mode.getValue() == Mode.Legit)));
/*  41 */   private final Setting<Boolean> sprint = register(new Setting("UseSprint", Boolean.TRUE, v -> (this.mode.getValue() == Mode.Legit)));
/*  42 */   private final Setting<Boolean> replenishBlocks = register(new Setting("ReplenishBlocks", Boolean.TRUE, v -> (this.mode.getValue() == Mode.Legit)));
/*  43 */   private final Setting<Boolean> down = register(new Setting("Down", Boolean.FALSE, v -> (this.mode.getValue() == Mode.Legit)));
/*  44 */   private final Setting<Float> expand = register(new Setting("Expand", Float.valueOf(2.0F), Float.valueOf(1.0F), Float.valueOf(6.0F), v -> (this.mode.getValue() == Mode.Legit)));
/*  45 */   private final List<Block> invalid = Arrays.asList(new Block[] { Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, (Block)Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, (Block)Blocks.WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.FLOWING_LAVA, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, (Block)Blocks.RED_MUSHROOM, (Block)Blocks.BROWN_MUSHROOM, (Block)Blocks.YELLOW_FLOWER, (Block)Blocks.RED_FLOWER, Blocks.ANVIL, (Block)Blocks.CACTUS, Blocks.LADDER, Blocks.ENDER_CHEST, Blocks.WEB, (Block)Blocks.PISTON, Blocks.REDSTONE_BLOCK });
/*  46 */   private final Timer timerMotion = new Timer();
/*  47 */   private final Timer itemTimer = new Timer();
/*  48 */   private final Timer timer = new Timer();
/*  49 */   public Setting<Boolean> rotation = register(new Setting("Rotate", Boolean.FALSE, v -> (this.mode.getValue() == Mode.Fast)));
/*     */   private int lastY;
/*     */   private BlockPos pos;
/*     */   private boolean teleported;
/*     */   
/*     */   public Scaffold() {
/*  55 */     super("Scaffold", "Places Blocks underneath you.", Module.Category.MOVEMENT, true, false, false);
/*     */   }
/*     */   
/*     */   public static void swap(int slot, int hotbarNum) {
/*  59 */     mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
/*  60 */     mc.playerController.windowClick(mc.player.inventoryContainer.windowId, hotbarNum, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
/*  61 */     mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
/*  62 */     mc.playerController.updateController();
/*     */   }
/*     */   
/*     */   public static int getItemSlot(Container container, Item item) {
/*  66 */     int slot = 0;
/*  67 */     for (int i = 9; i < 45; i++) {
/*  68 */       if (container.getSlot(i).getHasStack() && container.getSlot(i).getStack().getItem() == item)
/*     */       {
/*  70 */         slot = i; } 
/*     */     } 
/*  72 */     return slot;
/*     */   }
/*     */   
/*     */   public static boolean isMoving(EntityLivingBase entity) {
/*  76 */     return (entity.moveForward != 0.0F || entity.moveStrafing != 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  81 */     this.timer.reset();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPost(UpdateWalkingPlayerEvent event) {
/*  86 */     if (this.mode.getValue() == Mode.Fast) {
/*     */       
/*  88 */       if (isOff() || fullNullCheck() || event.getStage() == 0) {
/*     */         return;
/*     */       }
/*  91 */       if (!mc.gameSettings.keyBindJump.isKeyDown())
/*  92 */         this.timer.reset(); 
/*     */       BlockPos playerBlock;
/*  94 */       if (BlockUtil.isScaffoldPos((playerBlock = EntityUtil.getPlayerPosWithEntity()).add(0, -1, 0))) {
/*  95 */         if (BlockUtil.isValidBlock(playerBlock.add(0, -2, 0))) {
/*  96 */           place(playerBlock.add(0, -1, 0), EnumFacing.UP);
/*  97 */         } else if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 0))) {
/*  98 */           place(playerBlock.add(0, -1, 0), EnumFacing.EAST);
/*  99 */         } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 0))) {
/* 100 */           place(playerBlock.add(0, -1, 0), EnumFacing.WEST);
/* 101 */         } else if (BlockUtil.isValidBlock(playerBlock.add(0, -1, -1))) {
/* 102 */           place(playerBlock.add(0, -1, 0), EnumFacing.SOUTH);
/* 103 */         } else if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
/* 104 */           place(playerBlock.add(0, -1, 0), EnumFacing.NORTH);
/* 105 */         } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
/* 106 */           if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
/* 107 */             place(playerBlock.add(0, -1, 1), EnumFacing.NORTH);
/*     */           }
/* 109 */           place(playerBlock.add(1, -1, 1), EnumFacing.EAST);
/* 110 */         } else if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 1))) {
/* 111 */           if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 0))) {
/* 112 */             place(playerBlock.add(0, -1, 1), EnumFacing.WEST);
/*     */           }
/* 114 */           place(playerBlock.add(-1, -1, 1), EnumFacing.SOUTH);
/* 115 */         } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
/* 116 */           if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
/* 117 */             place(playerBlock.add(0, -1, 1), EnumFacing.SOUTH);
/*     */           }
/* 119 */           place(playerBlock.add(1, -1, 1), EnumFacing.WEST);
/* 120 */         } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
/* 121 */           if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
/* 122 */             place(playerBlock.add(0, -1, 1), EnumFacing.EAST);
/*     */           }
/* 124 */           place(playerBlock.add(1, -1, 1), EnumFacing.NORTH);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 132 */     if (this.mode.getValue() == Mode.Legit) {
/* 133 */       if (OyVey.moduleManager.isModuleEnabled("Sprint") && ((((Boolean)this.down.getValue()).booleanValue() && mc.gameSettings.keyBindSneak.isKeyDown()) || !((Boolean)this.sprint.getValue()).booleanValue())) {
/* 134 */         mc.player.setSprinting(false);
/* 135 */         Sprint.getInstance().disable();
/*     */       } 
/* 137 */       if (((Boolean)this.replenishBlocks.getValue()).booleanValue() && !(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) && getBlockCountHotbar() <= 0 && this.itemTimer.passedMs(100L))
/* 138 */         for (int i = 9; i < 45; i++) {
/*     */           ItemStack is;
/* 140 */           if (mc.player.inventoryContainer.getSlot(i).getHasStack() && (is = mc.player.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemBlock && !this.invalid.contains(Block.getBlockFromItem(is.getItem())) && i < 36)
/*     */           {
/* 142 */             swap(getItemSlot(mc.player.inventoryContainer, is.getItem()), 44);
/*     */           }
/*     */         }  
/* 145 */       if (((Boolean)this.keepY.getValue()).booleanValue()) {
/* 146 */         if ((!isMoving((EntityLivingBase)mc.player) && mc.gameSettings.keyBindJump.isKeyDown()) || mc.player.collidedVertically || mc.player.onGround) {
/* 147 */           this.lastY = MathHelper.floor(mc.player.posY);
/*     */         }
/*     */       } else {
/* 150 */         this.lastY = MathHelper.floor(mc.player.posY);
/*     */       } 
/* 152 */       BlockData blockData = null;
/* 153 */       double x = mc.player.posX;
/* 154 */       double z = mc.player.posZ;
/* 155 */       double y = ((Boolean)this.keepY.getValue()).booleanValue() ? this.lastY : mc.player.posY;
/* 156 */       double forward = mc.player.movementInput.moveForward;
/* 157 */       double strafe = mc.player.movementInput.moveStrafe;
/* 158 */       float yaw = mc.player.rotationYaw;
/* 159 */       if (!mc.player.collidedHorizontally) {
/* 160 */         double[] coords = getExpandCoords(x, z, forward, strafe, yaw);
/* 161 */         x = coords[0];
/* 162 */         z = coords[1];
/*     */       } 
/* 164 */       if (canPlace(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - ((mc.gameSettings.keyBindSneak.isKeyDown() && (Boolean) this.down.getValue()) ? 2 : 1), mc.player.posZ)).getBlock())) {
/* 165 */         x = mc.player.posX;
/* 166 */         z = mc.player.posZ;
/*     */       } 
/* 168 */       BlockPos blockBelow = new BlockPos(x, y - 1.0D, z);
/* 169 */       if (mc.gameSettings.keyBindSneak.isKeyDown() && ((Boolean)this.down.getValue()).booleanValue()) {
/* 170 */         blockBelow = new BlockPos(x, y - 2.0D, z);
/*     */       }
/* 172 */       this.pos = blockBelow;
/* 173 */       if (mc.world.getBlockState(blockBelow).getBlock() == Blocks.AIR) {
/* 174 */         blockData = getBlockData2(blockBelow);
/*     */       }
/* 176 */       if (blockData != null) {
/* 177 */         if (getBlockCountHotbar() <= 0 || (!((Boolean)this.bSwitch.getValue()).booleanValue() && !(mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock))) {
/*     */           return;
/*     */         }
/* 180 */         int heldItem = mc.player.inventory.currentItem;
/* 181 */         if (((Boolean)this.bSwitch.getValue()).booleanValue()) {
/* 182 */           for (int j = 0; j < 9; ) {
/* 183 */             mc.player.inventory.getStackInSlot(j);
/* 184 */             if (mc.player.inventory.getStackInSlot(j).getCount() == 0 || !(mc.player.inventory.getStackInSlot(j).getItem() instanceof ItemBlock) || this.invalid.contains(((ItemBlock)mc.player.inventory.getStackInSlot(j).getItem()).getBlock())) {
/*     */               j++; continue;
/* 186 */             }  mc.player.inventory.currentItem = j;
/*     */           } 
/*     */         }
/*     */         
/* 190 */         if (this.mode.getValue() == Mode.Legit) {
/* 191 */           if (mc.gameSettings.keyBindJump.isKeyDown() && mc.player.moveForward == 0.0F && mc.player.moveStrafing == 0.0F && !mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
/* 192 */             if (!this.teleported && ((Boolean)this.center.getValue()).booleanValue()) {
/* 193 */               this.teleported = true;
/* 194 */               BlockPos pos = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/* 195 */               mc.player.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
/*     */             } 
/* 197 */             if (((Boolean)this.center.getValue()).booleanValue() && !this.teleported) {
/*     */               return;
/*     */             }
/* 200 */             mc.player.motionY = 0.41999998688697815D;
/* 201 */             mc.player.motionZ = 0.0D;
/* 202 */             mc.player.motionX = 0.0D;
/* 203 */             if (this.timerMotion.sleep(1500L)) {
/* 204 */               mc.player.motionY = -0.28D;
/*     */             }
/*     */           } else {
/* 207 */             this.timerMotion.reset();
/* 208 */             if (this.teleported && ((Boolean)this.center.getValue()).booleanValue()) {
/* 209 */               this.teleported = false;
/*     */             }
/*     */           } 
/*     */         }
/* 213 */         if (mc.playerController.processRightClickBlock(mc.player, mc.world, blockData.position, blockData.face, new Vec3d(blockData.position.getX() + Math.random(), blockData.position.getY() + Math.random(), blockData.position.getZ() + Math.random()), EnumHand.MAIN_HAND) != EnumActionResult.FAIL) {
/* 214 */           if (((Boolean)this.swing.getValue()).booleanValue()) {
/* 215 */             mc.player.swingArm(EnumHand.MAIN_HAND);
/*     */           } else {
/* 217 */             mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*     */           } 
/*     */         }
/* 220 */         mc.player.inventory.currentItem = heldItem;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public double[] getExpandCoords(double x, double z, double forward, double strafe, float YAW) {
/* 226 */     BlockPos underPos = new BlockPos(x, mc.player.posY - ((mc.gameSettings.keyBindSneak.isKeyDown() && ((Boolean)this.down.getValue()).booleanValue()) ? 2 : 1), z);
/* 227 */     Block underBlock = mc.world.getBlockState(underPos).getBlock();
/* 228 */     double xCalc = -999.0D;
/* 229 */     double zCalc = -999.0D;
/* 230 */     double dist = 0.0D;
/* 231 */     double expandDist = (((Float)this.expand.getValue()).floatValue() * 2.0F);
/* 232 */     while (!canPlace(underBlock)) {
/* 233 */       xCalc = x;
/* 234 */       zCalc = z;
/* 235 */       if (++dist > expandDist) {
/* 236 */         dist = expandDist;
/*     */       }
/* 238 */       xCalc += (forward * 0.45D * Math.cos(Math.toRadians((YAW + 90.0F))) + strafe * 0.45D * Math.sin(Math.toRadians((YAW + 90.0F)))) * dist;
/* 239 */       zCalc += (forward * 0.45D * Math.sin(Math.toRadians((YAW + 90.0F))) - strafe * 0.45D * Math.cos(Math.toRadians((YAW + 90.0F)))) * dist;
/* 240 */       if (dist == expandDist)
/* 241 */         break;  underPos = new BlockPos(xCalc, mc.player.posY - ((mc.gameSettings.keyBindSneak.isKeyDown() && ((Boolean)this.down.getValue()).booleanValue()) ? 2 : 1), zCalc);
/* 242 */       underBlock = mc.world.getBlockState(underPos).getBlock();
/*     */     } 
/* 244 */     return new double[] { xCalc, zCalc };
/*     */   }
/*     */   
/*     */   public boolean canPlace(Block block) {
/* 248 */     return ((block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid) && mc.world != null && mc.player != null && this.pos != null && mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(this.pos)).isEmpty());
/*     */   }
/*     */   
/*     */   private int getBlockCountHotbar() {
/* 252 */     int blockCount = 0;
/* 253 */     for (int i = 36; i < 45; i++) {
/* 254 */       if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
/* 255 */         ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
/* 256 */         Item item = is.getItem();
/* 257 */         if (is.getItem() instanceof ItemBlock && !this.invalid.contains(((ItemBlock)item).getBlock()))
/* 258 */           blockCount += is.getCount(); 
/*     */       } 
/* 260 */     }  return blockCount;
/*     */   }
/*     */   
/*     */   private BlockData getBlockData2(BlockPos pos) {
/* 264 */     if (!this.invalid.contains(mc.world.getBlockState(pos.add(0, -1, 0)).getBlock())) {
/* 265 */       return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 267 */     if (!this.invalid.contains(mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
/* 268 */       return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 270 */     if (!this.invalid.contains(mc.world.getBlockState(pos.add(1, 0, 0)).getBlock())) {
/* 271 */       return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 273 */     if (!this.invalid.contains(mc.world.getBlockState(pos.add(0, 0, 1)).getBlock())) {
/* 274 */       return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 276 */     if (!this.invalid.contains(mc.world.getBlockState(pos.add(0, 0, -1)).getBlock())) {
/* 277 */       return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 279 */     if (!this.invalid.contains(mc.world.getBlockState(pos.add(0, 1, 0)).getBlock())) {
/* 280 */       return new BlockData(pos.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 282 */     BlockPos pos2 = pos.add(-1, 0, 0);
/* 283 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, -1, 0)).getBlock())) {
/* 284 */       return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 286 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, 1, 0)).getBlock())) {
/* 287 */       return new BlockData(pos2.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 289 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(-1, 0, 0)).getBlock())) {
/* 290 */       return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 292 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(1, 0, 0)).getBlock())) {
/* 293 */       return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 295 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, 0, 1)).getBlock())) {
/* 296 */       return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 298 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, 0, -1)).getBlock())) {
/* 299 */       return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 301 */     BlockPos pos3 = pos.add(1, 0, 0);
/* 302 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, -1, 0)).getBlock())) {
/* 303 */       return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 305 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, 1, 0)).getBlock())) {
/* 306 */       return new BlockData(pos3.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 308 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(-1, 0, 0)).getBlock())) {
/* 309 */       return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 311 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(1, 0, 0)).getBlock())) {
/* 312 */       return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 314 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, 0, 1)).getBlock())) {
/* 315 */       return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 317 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, 0, -1)).getBlock())) {
/* 318 */       return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 320 */     BlockPos pos4 = pos.add(0, 0, 1);
/* 321 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, -1, 0)).getBlock())) {
/* 322 */       return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 324 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, 1, 0)).getBlock())) {
/* 325 */       return new BlockData(pos4.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 327 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(-1, 0, 0)).getBlock())) {
/* 328 */       return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 330 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(1, 0, 0)).getBlock())) {
/* 331 */       return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 333 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, 0, 1)).getBlock())) {
/* 334 */       return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 336 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, 0, -1)).getBlock())) {
/* 337 */       return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 339 */     BlockPos pos5 = pos.add(0, 0, -1);
/* 340 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, -1, 0)).getBlock())) {
/* 341 */       return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 343 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, 1, 0)).getBlock())) {
/* 344 */       return new BlockData(pos5.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 346 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(-1, 0, 0)).getBlock())) {
/* 347 */       return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 349 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(1, 0, 0)).getBlock())) {
/* 350 */       return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 352 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, 0, 1)).getBlock())) {
/* 353 */       return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 355 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, 0, -1)).getBlock())) {
/* 356 */       return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 358 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, -1, 0)).getBlock())) {
/* 359 */       return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 361 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, 1, 0)).getBlock())) {
/* 362 */       return new BlockData(pos2.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 364 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(-1, 0, 0)).getBlock())) {
/* 365 */       return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 367 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(1, 0, 0)).getBlock())) {
/* 368 */       return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 370 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, 0, 1)).getBlock())) {
/* 371 */       return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 373 */     if (!this.invalid.contains(mc.world.getBlockState(pos2.add(0, 0, -1)).getBlock())) {
/* 374 */       return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 376 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, -1, 0)).getBlock())) {
/* 377 */       return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 379 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, 1, 0)).getBlock())) {
/* 380 */       return new BlockData(pos3.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 382 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(-1, 0, 0)).getBlock())) {
/* 383 */       return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 385 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(1, 0, 0)).getBlock())) {
/* 386 */       return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 388 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, 0, 1)).getBlock())) {
/* 389 */       return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 391 */     if (!this.invalid.contains(mc.world.getBlockState(pos3.add(0, 0, -1)).getBlock())) {
/* 392 */       return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 394 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, -1, 0)).getBlock())) {
/* 395 */       return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 397 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, 1, 0)).getBlock())) {
/* 398 */       return new BlockData(pos4.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 400 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(-1, 0, 0)).getBlock())) {
/* 401 */       return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 403 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(1, 0, 0)).getBlock())) {
/* 404 */       return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 406 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, 0, 1)).getBlock())) {
/* 407 */       return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 409 */     if (!this.invalid.contains(mc.world.getBlockState(pos4.add(0, 0, -1)).getBlock())) {
/* 410 */       return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 412 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, -1, 0)).getBlock())) {
/* 413 */       return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 415 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, 1, 0)).getBlock())) {
/* 416 */       return new BlockData(pos5.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 418 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(-1, 0, 0)).getBlock())) {
/* 419 */       return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 421 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(1, 0, 0)).getBlock())) {
/* 422 */       return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 424 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, 0, 1)).getBlock())) {
/* 425 */       return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 427 */     if (!this.invalid.contains(mc.world.getBlockState(pos5.add(0, 0, -1)).getBlock())) {
/* 428 */       return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 430 */     BlockPos pos10 = pos.add(0, -1, 0);
/* 431 */     if (!this.invalid.contains(mc.world.getBlockState(pos10.add(0, -1, 0)).getBlock())) {
/* 432 */       return new BlockData(pos10.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 434 */     if (!this.invalid.contains(mc.world.getBlockState(pos10.add(0, 1, 0)).getBlock())) {
/* 435 */       return new BlockData(pos10.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 437 */     if (!this.invalid.contains(mc.world.getBlockState(pos10.add(-1, 0, 0)).getBlock())) {
/* 438 */       return new BlockData(pos10.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 440 */     if (!this.invalid.contains(mc.world.getBlockState(pos10.add(1, 0, 0)).getBlock())) {
/* 441 */       return new BlockData(pos10.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 443 */     if (!this.invalid.contains(mc.world.getBlockState(pos10.add(0, 0, 1)).getBlock())) {
/* 444 */       return new BlockData(pos10.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 446 */     if (!this.invalid.contains(mc.world.getBlockState(pos10.add(0, 0, -1)).getBlock())) {
/* 447 */       return new BlockData(pos10.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 449 */     BlockPos pos11 = pos10.add(1, 0, 0);
/* 450 */     if (!this.invalid.contains(mc.world.getBlockState(pos11.add(0, -1, 0)).getBlock())) {
/* 451 */       return new BlockData(pos11.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 453 */     if (!this.invalid.contains(mc.world.getBlockState(pos11.add(0, 1, 0)).getBlock())) {
/* 454 */       return new BlockData(pos11.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 456 */     if (!this.invalid.contains(mc.world.getBlockState(pos11.add(-1, 0, 0)).getBlock())) {
/* 457 */       return new BlockData(pos11.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 459 */     if (!this.invalid.contains(mc.world.getBlockState(pos11.add(1, 0, 0)).getBlock())) {
/* 460 */       return new BlockData(pos11.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 462 */     if (!this.invalid.contains(mc.world.getBlockState(pos11.add(0, 0, 1)).getBlock())) {
/* 463 */       return new BlockData(pos11.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 465 */     if (!this.invalid.contains(mc.world.getBlockState(pos11.add(0, 0, -1)).getBlock())) {
/* 466 */       return new BlockData(pos11.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 468 */     BlockPos pos12 = pos10.add(-1, 0, 0);
/* 469 */     if (!this.invalid.contains(mc.world.getBlockState(pos12.add(0, -1, 0)).getBlock())) {
/* 470 */       return new BlockData(pos12.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 472 */     if (!this.invalid.contains(mc.world.getBlockState(pos12.add(0, 1, 0)).getBlock())) {
/* 473 */       return new BlockData(pos12.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 475 */     if (!this.invalid.contains(mc.world.getBlockState(pos12.add(-1, 0, 0)).getBlock())) {
/* 476 */       return new BlockData(pos12.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 478 */     if (!this.invalid.contains(mc.world.getBlockState(pos12.add(1, 0, 0)).getBlock())) {
/* 479 */       return new BlockData(pos12.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 481 */     if (!this.invalid.contains(mc.world.getBlockState(pos12.add(0, 0, 1)).getBlock())) {
/* 482 */       return new BlockData(pos12.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 484 */     if (!this.invalid.contains(mc.world.getBlockState(pos12.add(0, 0, -1)).getBlock())) {
/* 485 */       return new BlockData(pos12.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 487 */     BlockPos pos13 = pos10.add(0, 0, 1);
/* 488 */     if (!this.invalid.contains(mc.world.getBlockState(pos13.add(0, -1, 0)).getBlock())) {
/* 489 */       return new BlockData(pos13.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 491 */     if (!this.invalid.contains(mc.world.getBlockState(pos13.add(-1, 0, 0)).getBlock())) {
/* 492 */       return new BlockData(pos13.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 494 */     if (!this.invalid.contains(mc.world.getBlockState(pos13.add(0, 1, 0)).getBlock())) {
/* 495 */       return new BlockData(pos13.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 497 */     if (!this.invalid.contains(mc.world.getBlockState(pos13.add(1, 0, 0)).getBlock())) {
/* 498 */       return new BlockData(pos13.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 500 */     if (!this.invalid.contains(mc.world.getBlockState(pos13.add(0, 0, 1)).getBlock())) {
/* 501 */       return new BlockData(pos13.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 503 */     if (!this.invalid.contains(mc.world.getBlockState(pos13.add(0, 0, -1)).getBlock())) {
/* 504 */       return new BlockData(pos13.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 506 */     BlockPos pos14 = pos10.add(0, 0, -1);
/* 507 */     if (!this.invalid.contains(mc.world.getBlockState(pos14.add(0, -1, 0)).getBlock())) {
/* 508 */       return new BlockData(pos14.add(0, -1, 0), EnumFacing.UP);
/*     */     }
/* 510 */     if (!this.invalid.contains(mc.world.getBlockState(pos14.add(0, 1, 0)).getBlock())) {
/* 511 */       return new BlockData(pos14.add(0, 1, 0), EnumFacing.DOWN);
/*     */     }
/* 513 */     if (!this.invalid.contains(mc.world.getBlockState(pos14.add(-1, 0, 0)).getBlock())) {
/* 514 */       return new BlockData(pos14.add(-1, 0, 0), EnumFacing.EAST);
/*     */     }
/* 516 */     if (!this.invalid.contains(mc.world.getBlockState(pos14.add(1, 0, 0)).getBlock())) {
/* 517 */       return new BlockData(pos14.add(1, 0, 0), EnumFacing.WEST);
/*     */     }
/* 519 */     if (!this.invalid.contains(mc.world.getBlockState(pos14.add(0, 0, 1)).getBlock())) {
/* 520 */       return new BlockData(pos14.add(0, 0, 1), EnumFacing.NORTH);
/*     */     }
/* 522 */     if (!this.invalid.contains(mc.world.getBlockState(pos14.add(0, 0, -1)).getBlock())) {
/* 523 */       return new BlockData(pos14.add(0, 0, -1), EnumFacing.SOUTH);
/*     */     }
/* 525 */     return null;
/*     */   }
/*     */   
/*     */   public void place(BlockPos posI, EnumFacing face) {
/* 529 */     BlockPos pos = posI;
/* 530 */     if (face == EnumFacing.UP) {
/* 531 */       pos = pos.add(0, -1, 0);
/* 532 */     } else if (face == EnumFacing.NORTH) {
/* 533 */       pos = pos.add(0, 0, 1);
/* 534 */     } else if (face == EnumFacing.SOUTH) {
/* 535 */       pos = pos.add(0, 0, -1);
/* 536 */     } else if (face == EnumFacing.EAST) {
/* 537 */       pos = pos.add(-1, 0, 0);
/* 538 */     } else if (face == EnumFacing.WEST) {
/* 539 */       pos = pos.add(1, 0, 0);
/*     */     } 
/* 541 */     int oldSlot = mc.player.inventory.currentItem;
/* 542 */     int newSlot = -1;
/* 543 */     for (int i = 0; i < 9; ) {
/* 544 */       ItemStack stack = mc.player.inventory.getStackInSlot(i);
/* 545 */       if (InventoryUtil.isNull(stack) || !(stack.getItem() instanceof ItemBlock) || !Block.getBlockFromItem(stack.getItem()).getDefaultState().isFullBlock()) {
/*     */         i++; continue;
/* 547 */       }  newSlot = i;
/*     */     } 
/*     */     
/* 550 */     if (newSlot == -1) {
/*     */       return;
/*     */     }
/* 553 */     boolean crouched = false;
/* 554 */     if (!mc.player.isSneaking() && BlockUtil.blackList.contains(mc.world.getBlockState(pos).getBlock())) {
/* 555 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
/* 556 */       crouched = true;
/*     */     } 
/* 558 */     if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
/* 559 */       mc.player.inventory.currentItem = newSlot;
/* 560 */       mc.playerController.updateController();
/*     */     } 
/* 562 */     if (mc.gameSettings.keyBindJump.isKeyDown()) {
/* 563 */       mc.player.motionX *= 0.3D;
/* 564 */       mc.player.motionZ *= 0.3D;
/* 565 */       mc.player.jump();
/* 566 */       if (this.timer.passedMs(1500L)) {
/* 567 */         mc.player.motionY = -0.28D;
/* 568 */         this.timer.reset();
/*     */       } 
/*     */     } 
/* 571 */     if (((Boolean)this.rotation.getValue()).booleanValue()) {
/* 572 */       float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((pos.getX() + 0.5F), (pos.getY() - 0.5F), (pos.getZ() + 0.5F)));
/* 573 */       mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], MathHelper.normalizeAngle((int)angle[1], 360), mc.player.onGround));
/*     */     } 
/* 575 */     mc.playerController.processRightClickBlock(mc.player, mc.world, pos, face, new Vec3d(0.5D, 0.5D, 0.5D), EnumHand.MAIN_HAND);
/* 576 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/* 577 */     mc.player.inventory.currentItem = oldSlot;
/* 578 */     mc.playerController.updateController();
/* 579 */     if (crouched) {
/* 580 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 586 */     Legit,
/* 587 */     Fast;
/*     */   }
/*     */   
/*     */   private static class BlockData
/*     */   {
/*     */     public BlockPos position;
/*     */     public EnumFacing face;
/*     */     
/*     */     public BlockData(BlockPos position, EnumFacing face) {
/* 596 */       this.position = position;
/* 597 */       this.face = face;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\Scaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
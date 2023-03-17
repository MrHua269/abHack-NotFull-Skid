/*     */ package me.abHack.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import me.abHack.OyVey;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityUtil
/*     */   implements Util
/*     */ {
/*  40 */   public static final Vec3d[] antiDropOffsetList = new Vec3d[] { new Vec3d(0.0D, -2.0D, 0.0D) };
/*  41 */   public static final Vec3d[] platformOffsetList = new Vec3d[] { new Vec3d(0.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(1.0D, -1.0D, 0.0D) };
/*  42 */   public static final Vec3d[] legOffsetList = new Vec3d[] { new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 1.0D) };
/*  43 */   public static final Vec3d[] OffsetList = new Vec3d[] { new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, 0.0D) };
/*  44 */   public static final Vec3d[] antiStepOffsetList = new Vec3d[] { new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(0.0D, 2.0D, -1.0D) };
/*  45 */   public static final Vec3d[] antiScaffoldOffsetList = new Vec3d[] { new Vec3d(0.0D, 3.0D, 0.0D) };
/*     */   
/*     */   public static void attackEntity(Entity entity, boolean packet, boolean swingArm) {
/*  48 */     if (packet) {
/*  49 */       mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
/*     */     } else {
/*  51 */       mc.playerController.attackEntity((EntityPlayer)mc.player, entity);
/*     */     } 
/*  53 */     if (swingArm) {
/*  54 */       mc.player.swingArm(EnumHand.MAIN_HAND);
/*     */     }
/*     */   }
/*     */   
/*     */   public static double getMaxSpeed() {
/*  59 */     double maxModifier = 0.2873D;
/*  60 */     if (mc.player != null && mc.player.isPotionActive(Objects.<Potion>requireNonNull(Potion.getPotionById(1))))
/*  61 */       maxModifier *= 1.0D + 0.2D * (((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(Objects.<Potion>requireNonNull(Potion.getPotionById(1))))).getAmplifier() + 1); 
/*  62 */     return maxModifier;
/*     */   }
/*     */   
/*     */   public static boolean isInLiquid() {
/*  66 */     return (mc.player.isInWater() || mc.player.isInLava());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void attackEntity(Entity entity, boolean packet, EnumHand hand) {
/*  71 */     if (packet) {
/*  72 */       mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
/*     */     } else {
/*  74 */       mc.playerController.attackEntity((EntityPlayer)mc.player, entity);
/*     */     } 
/*  76 */     mc.player.swingArm(hand);
/*     */     
/*  78 */     if (!packet) {
/*  79 */       mc.player.resetCooldown();
/*     */     }
/*     */   }
/*     */   
/*     */   public static Vec3d interpolateEntity(Entity entity, float time) {
/*  84 */     return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFakePlayer() {
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isPassive(Entity entity) {
/*  93 */     if (entity instanceof EntityWolf && ((EntityWolf)entity).isAngry()) {
/*  94 */       return false;
/*     */     }
/*  96 */     if (entity instanceof net.minecraft.entity.EntityAgeable || entity instanceof net.minecraft.entity.passive.EntityAmbientCreature || entity instanceof net.minecraft.entity.passive.EntitySquid) {
/*  97 */       return true;
/*     */     }
/*  99 */     return (entity instanceof EntityIronGolem && ((EntityIronGolem)entity).getRevengeTarget() == null);
/*     */   }
/*     */   
/*     */   public static boolean isSafe(Entity entity, int height, boolean floor) {
/* 103 */     return (getUnsafeBlocks(entity, height, floor).size() == 0);
/*     */   }
/*     */   
/*     */   public static boolean stopSneaking(boolean isSneaking) {
/* 107 */     if (isSneaking && mc.player != null) {
/* 108 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */     }
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSafe(Entity entity) {
/* 114 */     return isSafe(entity, 0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void attackEntity(Entity entity, boolean packet) {
/* 119 */     if (packet) {
/* 120 */       mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
/*     */     } else {
/* 122 */       mc.playerController.attackEntity((EntityPlayer)mc.player, entity);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setTimer(float speed) {
/* 128 */     (Minecraft.getMinecraft()).timer.tickLength = 50.0F / speed;
/*     */   }
/*     */   
/*     */   public static BlockPos getPlayerPos(EntityPlayer player) {
/* 132 */     return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getUnsafeBlocks(Entity entity, int height, boolean floor) {
/* 136 */     return getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
/*     */   }
/*     */   
/*     */   public static boolean isMobAggressive(Entity entity) {
/* 140 */     if (entity instanceof EntityPigZombie) {
/* 141 */       if (((EntityPigZombie)entity).isArmsRaised() || ((EntityPigZombie)entity).isAngry()) {
/* 142 */         return true;
/*     */       }
/*     */     } else {
/* 145 */       if (entity instanceof EntityWolf) {
/* 146 */         return (((EntityWolf)entity).isAngry() && !mc.player.equals(((EntityWolf)entity).getOwner()));
/*     */       }
/* 148 */       if (entity instanceof EntityEnderman) {
/* 149 */         return ((EntityEnderman)entity).isScreaming();
/*     */       }
/*     */     } 
/* 152 */     return isHostileMob(entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNeutralMob(Entity entity) {
/* 157 */     return ((entity instanceof EntityPigZombie && !((EntityPigZombie)entity).isAngry()) || (entity instanceof EntityWolf && !((EntityWolf)entity).isAngry()) || (entity instanceof EntityEnderman && ((EntityEnderman)entity).isScreaming()));
/*     */   }
/*     */   
/*     */   public static boolean isProjectile(Entity entity) {
/* 161 */     return (entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball);
/*     */   }
/*     */   
/*     */   public static boolean isVehicle(Entity entity) {
/* 165 */     return (entity instanceof net.minecraft.entity.item.EntityBoat || entity instanceof net.minecraft.entity.item.EntityMinecart);
/*     */   }
/*     */   
/*     */   public static boolean isHostileMob(Entity entity) {
/* 169 */     return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity));
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getUnsafeBlocksFromVec3d(Vec3d pos, int height, boolean floor) {
/* 173 */     ArrayList<Vec3d> vec3ds = new ArrayList<>();
/* 174 */     for (Vec3d vector : getOffsets(height, floor)) {
/* 175 */       BlockPos targetPos = (new BlockPos(pos)).add(vector.x, vector.y, vector.z);
/* 176 */       Block block = mc.world.getBlockState(targetPos).getBlock();
/* 177 */       if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockFire || block instanceof net.minecraft.block.BlockDeadBush || block instanceof net.minecraft.block.BlockSnow)
/*     */       {
/* 179 */         vec3ds.add(vector); } 
/*     */     } 
/* 181 */     return vec3ds;
/*     */   }
/*     */   
/*     */   public static boolean isInHole(Entity entity) {
/* 185 */     return isBlockValid(new BlockPos(entity.posX, entity.posY, entity.posZ));
/*     */   }
/*     */   
/*     */   public static boolean isBlockValid(BlockPos blockPos) {
/* 189 */     return (isBedrockHole(blockPos) || isObbyHole(blockPos) || isBothHole(blockPos));
/*     */   } public static boolean isObbyHole(BlockPos blockPos) { BlockPos[] arrayOfBlockPos;
/*     */     int i;
/*     */     byte b;
/* 193 */     for (arrayOfBlockPos = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() }, i = arrayOfBlockPos.length, b = 0; b < i; ) { BlockPos pos = arrayOfBlockPos[b];
/* 194 */       IBlockState touchingState = mc.world.getBlockState(pos);
/* 195 */       if (touchingState.getBlock() != Blocks.AIR && touchingState.getBlock() == Blocks.OBSIDIAN) { b++; continue; }
/* 196 */        return false; }
/*     */     
/* 198 */     return true; }
/*     */   public static boolean isBedrockHole(BlockPos blockPos) { BlockPos[] arrayOfBlockPos;
/*     */     int i;
/*     */     byte b;
/* 202 */     for (arrayOfBlockPos = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() }, i = arrayOfBlockPos.length, b = 0; b < i; ) { BlockPos pos = arrayOfBlockPos[b];
/* 203 */       IBlockState touchingState = mc.world.getBlockState(pos);
/* 204 */       if (touchingState.getBlock() != Blocks.AIR && touchingState.getBlock() == Blocks.BEDROCK) { b++; continue; }
/* 205 */        return false; }
/*     */     
/* 207 */     return true; } public static boolean isBothHole(BlockPos blockPos) {
/*     */     BlockPos[] arrayOfBlockPos;
/*     */     int i;
/*     */     byte b;
/* 211 */     for (arrayOfBlockPos = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() }, i = arrayOfBlockPos.length, b = 0; b < i; ) { BlockPos pos = arrayOfBlockPos[b];
/* 212 */       IBlockState touchingState = mc.world.getBlockState(pos);
/* 213 */       if (touchingState.getBlock() != Blocks.AIR && (touchingState.getBlock() == Blocks.BEDROCK || touchingState.getBlock() == Blocks.OBSIDIAN)) {
/*     */         b++; continue;
/* 215 */       }  return false; }
/*     */     
/* 217 */     return true;
/*     */   }
/*     */   
/*     */   public static Vec3d[] getUnsafeBlockArray(Entity entity, int height, boolean floor) {
/* 221 */     List<Vec3d> list = getUnsafeBlocks(entity, height, floor);
/* 222 */     Vec3d[] array = new Vec3d[list.size()];
/* 223 */     return list.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static Vec3d[] getUnsafeBlockArrayFromVec3d(Vec3d pos, int height, boolean floor) {
/* 227 */     List<Vec3d> list = getUnsafeBlocksFromVec3d(pos, height, floor);
/* 228 */     Vec3d[] array = new Vec3d[list.size()];
/* 229 */     return list.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static boolean isTrapped(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 233 */     return (getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() == 0);
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getUntrappedBlocks(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 237 */     ArrayList<Vec3d> vec3ds = new ArrayList<>();
/* 238 */     if (!antiStep && getUnsafeBlocks((Entity)player, 2, false).size() == 4) {
/* 239 */       vec3ds.addAll(getUnsafeBlocks((Entity)player, 2, false));
/*     */     }
/* 241 */     for (int i = 0; i < (getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)).length; i++) {
/* 242 */       Vec3d vector = getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)[i];
/* 243 */       BlockPos targetPos = (new BlockPos(player.getPositionVector())).add(vector.x, vector.y, vector.z);
/* 244 */       Block block = mc.world.getBlockState(targetPos).getBlock();
/* 245 */       if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockFire || block instanceof net.minecraft.block.BlockDeadBush || block instanceof net.minecraft.block.BlockSnow)
/*     */       {
/* 247 */         vec3ds.add(vector); } 
/*     */     } 
/* 249 */     return vec3ds;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Vec3d> targets(Vec3d vec3d, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
/* 254 */     ArrayList<Vec3d> placeTargets = new ArrayList<>();
/* 255 */     if (antiDrop) {
/* 256 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiDropOffsetList));
/*     */     }
/* 258 */     if (platform) {
/* 259 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, platformOffsetList));
/*     */     }
/* 261 */     if (legs) {
/* 262 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, legOffsetList));
/*     */     }
/* 264 */     Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, OffsetList));
/* 265 */     if (antiStep) {
/* 266 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiStepOffsetList));
/*     */     } else {
/* 268 */       List<Vec3d> vec3ds = getUnsafeBlocksFromVec3d(vec3d, 2, false);
/* 269 */       if (vec3ds.size() == 4)
/*     */       {
/* 271 */         for (Vec3d vector : vec3ds) {
/* 272 */           BlockPos position = (new BlockPos(vec3d)).add(vector.x, vector.y, vector.z);
/* 273 */           switch (BlockUtil.isPositionPlaceable(position, raytrace)) {
/*     */             case 0:
/*     */               break;
/*     */             
/*     */             case -1:
/*     */             case 1:
/*     */             case 2:
/*     */               continue;
/*     */             
/*     */             case 3:
/* 283 */               placeTargets.add(vec3d.add(vector)); break;
/*     */             default:
/*     */               // Byte code: goto -> 234
/*     */           } 
/* 287 */           if (antiScaffold) {
/* 288 */             Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList));
/*     */           }
/* 290 */           return placeTargets;
/*     */         } 
/*     */       }
/*     */     } 
/* 294 */     if (antiScaffold) {
/* 295 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList));
/*     */     }
/* 297 */     return placeTargets;
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getOffsetList(int y, boolean floor) {
/* 301 */     ArrayList<Vec3d> offsets = new ArrayList<>();
/* 302 */     offsets.add(new Vec3d(-1.0D, y, 0.0D));
/* 303 */     offsets.add(new Vec3d(1.0D, y, 0.0D));
/* 304 */     offsets.add(new Vec3d(0.0D, y, -1.0D));
/* 305 */     offsets.add(new Vec3d(0.0D, y, 1.0D));
/* 306 */     if (floor) {
/* 307 */       offsets.add(new Vec3d(0.0D, (y - 1), 0.0D));
/*     */     }
/* 309 */     return offsets;
/*     */   }
/*     */   
/*     */   public static Vec3d[] getOffsets(int y, boolean floor) {
/* 313 */     List<Vec3d> offsets = getOffsetList(y, floor);
/* 314 */     Vec3d[] array = new Vec3d[offsets.size()];
/* 315 */     return offsets.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static Vec3d[] getTrapOffsets(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 319 */     List<Vec3d> offsets = getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
/* 320 */     Vec3d[] array = new Vec3d[offsets.size()];
/* 321 */     return offsets.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getTrapOffsetsList(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 325 */     ArrayList<Vec3d> offsets = new ArrayList<>(getOffsetList(1, false));
/* 326 */     offsets.add(new Vec3d(0.0D, 2.0D, 0.0D));
/* 327 */     if (antiScaffold) {
/* 328 */       offsets.add(new Vec3d(0.0D, 3.0D, 0.0D));
/*     */     }
/* 330 */     if (antiStep) {
/* 331 */       offsets.addAll(getOffsetList(2, false));
/*     */     }
/* 333 */     if (legs) {
/* 334 */       offsets.addAll(getOffsetList(0, false));
/*     */     }
/* 336 */     if (platform) {
/* 337 */       offsets.addAll(getOffsetList(-1, false));
/* 338 */       offsets.add(new Vec3d(0.0D, -1.0D, 0.0D));
/*     */     } 
/* 340 */     if (antiDrop) {
/* 341 */       offsets.add(new Vec3d(0.0D, -2.0D, 0.0D));
/*     */     }
/* 343 */     return offsets;
/*     */   }
/*     */   
/*     */   public static BlockPos getRoundedBlockPos(Entity entity) {
/* 347 */     return new BlockPos(MathUtil.roundVec(entity.getPositionVector(), 0));
/*     */   }
/*     */   
/*     */   public static boolean isLiving(Entity entity) {
/* 351 */     return entity instanceof EntityLivingBase;
/*     */   }
/*     */   
/*     */   public static boolean isAlive(Entity entity) {
/* 355 */     return (isLiving(entity) && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0F);
/*     */   }
/*     */   
/*     */   public static boolean isDead(Entity entity) {
/* 359 */     return !isAlive(entity);
/*     */   }
/*     */   
/*     */   public static float getHealth(Entity entity) {
/* 363 */     if (isLiving(entity)) {
/* 364 */       EntityLivingBase livingBase = (EntityLivingBase)entity;
/* 365 */       return livingBase.getHealth() + livingBase.getAbsorptionAmount();
/*     */     } 
/* 367 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static float getHealth(Entity entity, boolean absorption) {
/* 371 */     if (isLiving(entity)) {
/* 372 */       EntityLivingBase livingBase = (EntityLivingBase)entity;
/* 373 */       return livingBase.getHealth() + (absorption ? livingBase.getAbsorptionAmount() : 0.0F);
/*     */     } 
/* 375 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isntValid(Entity entity, double range) {
/* 380 */     return (entity == null || isDead(entity) || entity.equals(mc.player) || (entity instanceof EntityPlayer && OyVey.friendManager.isFriend(entity.getName())) || mc.player.getDistanceSq(entity) > MathUtil.square(range));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean holdingWeapon(EntityPlayer player) {
/* 385 */     return (player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemSword || player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemAxe);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color getColor(Entity entity, int red, int green, int blue, int alpha, boolean colorFriends) {
/* 390 */     Color color = new Color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/* 391 */     if (entity instanceof EntityPlayer && colorFriends && OyVey.friendManager.isFriend((EntityPlayer)entity)) {
/* 392 */       color = new Color(0.33333334F, 1.0F, 1.0F, alpha / 255.0F);
/*     */     }
/* 394 */     return color;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayer getClosestEnemy(double distance) {
/* 399 */     EntityPlayer closest = null;
/* 400 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 401 */       if (isntValid((Entity)player, distance))
/* 402 */         continue;  if (closest == null) {
/* 403 */         closest = player;
/*     */         continue;
/*     */       } 
/* 406 */       if (mc.player.getDistanceSq((Entity)player) >= mc.player.getDistanceSq((Entity)closest))
/*     */         continue; 
/* 408 */       closest = player;
/*     */     } 
/* 410 */     return closest;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPos getPlayerPosWithEntity() {
/* 415 */     return new BlockPos((mc.player.getRidingEntity() != null) ? (mc.player.getRidingEntity()).posX : mc.player.posX, (mc.player.getRidingEntity() != null) ? (mc.player.getRidingEntity()).posY : mc.player.posY, (mc.player.getRidingEntity() != null) ? (mc.player.getRidingEntity()).posZ : mc.player.posZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\EntityUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
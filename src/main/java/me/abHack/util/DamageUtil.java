/*     */ package me.abHack.util;
/*     */ import java.util.Objects;
import java.util.function.BiPredicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.CombatRules;
import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
/*     */ 
/*     */ public class DamageUtil implements Util {
/*     */   public static double getDistance(Vec3d vec, double x, double y, double z) {
/*  23 */     double d0 = vec.x - x;
/*  24 */     double d1 = vec.y - y;
/*  25 */     double d2 = vec.z - z;
/*  26 */     return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */   }
/*     */   
/*     */   public static float calculateDamagePredict(double posX, double posY, double posZ, Entity entity, BlockPos.MutableBlockPos mutablePos, int predictTick) {
/*  30 */     if (entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative()) {
/*  31 */       return 0.0F;
/*     */     }
/*     */     
/*  34 */     double x = entity.posX, y = entity.posY, z = entity.posZ, width = entity.width / 2.0D;
/*     */     
/*  36 */     double motionX = x - entity.prevPosX, motionY = y - entity.prevPosY, motionZ = z - entity.prevPosZ;
/*     */     
/*  38 */     for (int i = 0; i < predictTick; i++) {
/*     */ 
/*     */       
/*  41 */       motionY = (motionY > 0.4D) ? 0.314D : ((motionY > 0.3D) ? 0.23D : ((motionY > 0.2D) ? 0.147D : ((motionY > 0.1D) ? 0.065D : motionY)));
/*  42 */       boolean jump = (motionY > 0.0D);
/*  43 */       if (jump && entity instanceof EntityPlayer) motionY += ((EntityPlayer)entity).isPotionActive(MobEffects.JUMP_BOOST) ? ((((PotionEffect) Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST))).getAmplifier() + 1) * 0.1F) : 0.0D;
/*  44 */       x += motionX;
/*  45 */       y += jump ? motionY : 0.0D;
/*  46 */       z += motionZ;
/*     */ 
/*     */       
/*  49 */       if (!mc.world.getCollisionBoxes(entity, new AxisAlignedBB(x - width, y, z - width, x + width, y + entity.height, z + width)).isEmpty()) {
/*  50 */         x -= motionX;
/*  51 */         y -= jump ? motionY : 0.0D;
/*  52 */         z -= motionZ;
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     float doubleExplosionSize = 12.0F;
/*  57 */     float scaledDist = (float)getDistance(new Vec3d(x, y, z), posX, posY, posZ) / doubleExplosionSize;
/*  58 */     if (scaledDist > 1.0F) {
/*  59 */       return 0.0F;
/*     */     }
/*     */     
/*  62 */     Vec3d vec = new Vec3d(posX, posY, posZ);
/*  63 */     float factor = (1.0F - scaledDist) * getBlockDensity(vec, new AxisAlignedBB(x - width, y, z - width, x + width, y + entity.height, z + width), mutablePos);
/*  64 */     float damage = (factor * factor + factor) * 3.5F * doubleExplosionSize + 1.0F;
/*     */     
/*  66 */     if (entity instanceof EntityLivingBase) {
/*  67 */       damage = getBlastReduction((EntityLivingBase)entity, damage);
/*     */     }
/*     */     
/*  70 */     return damage;
/*     */   }
/*     */   
/*     */   public static float calculateDamage(double posX, double posY, double posZ, Entity entity, BlockPos.MutableBlockPos mutablePos) {
/*  74 */     if (entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative()) {
/*  75 */       return 0.0F;
/*     */     }
/*     */     
/*  78 */     float doubleExplosionSize = 12.0F;
/*  79 */     float scaledDist = (float)entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
/*  80 */     if (scaledDist > 1.0F) {
/*  81 */       return 0.0F;
/*     */     }
/*     */     
/*  84 */     Vec3d vec = new Vec3d(posX, posY, posZ);
/*  85 */     float factor = (1.0F - scaledDist) * getBlockDensity(vec, entity.getEntityBoundingBox(), mutablePos);
/*  86 */     float damage = (factor * factor + factor) * 3.5F * doubleExplosionSize + 1.0F;
/*     */     
/*  88 */     if (entity instanceof EntityLivingBase) {
/*  89 */       damage = getBlastReduction((EntityLivingBase)entity, damage);
/*     */     }
/*     */     
/*  92 */     return damage;
/*     */   }
/*     */   
/*     */   public static float getBlastReduction(EntityLivingBase entity, float damage) {
/*  96 */     if (entity instanceof EntityPlayer) {
/*  97 */       EntityPlayer player = (EntityPlayer)entity;
/*  98 */       ICachedEntityPlayer cachedData = (ICachedEntityPlayer)player;
/*     */       
/* 100 */       float totalArmourValue = cachedData.getTotalArmourValue();
/* 101 */       float armourToughness = cachedData.getArmourToughness();
/* 102 */       float resistanceMultiplier = cachedData.getResistanceMultiplier();
/* 103 */       float blastMultiplier = cachedData.getBlastMultiplier();
/*     */       
/* 105 */       damage = getDifficultyMultiplier(damage);
/* 106 */       damage = CombatRules.getDamageAfterAbsorb(damage, totalArmourValue, armourToughness) * resistanceMultiplier * blastMultiplier;
/*     */       
/* 108 */       return Math.max(0.0F, damage);
/*     */     } 
/*     */     
/* 111 */     damage = CombatRules.getDamageAfterAbsorb(damage, entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
/* 112 */     return damage;
/*     */   }
/*     */   
/*     */   public static float getDifficultyMultiplier(float damage) {
/* 116 */     switch (mc.world.getDifficulty()) {
/*     */       case PEACEFUL:
/* 118 */         return 0.0F;
/*     */       case EASY:
/* 120 */         return Math.min(damage / 2.0F + 1.0F, damage);
/*     */       case HARD:
/* 122 */         return damage * 1.5F;
/*     */     } 
/* 124 */     return damage;
/*     */   }
/*     */   
/*     */   private static float getBlockDensity(Vec3d vec, AxisAlignedBB bb, BlockPos.MutableBlockPos mutablePos) {
/* 128 */     double x = 1.0D / ((bb.maxX - bb.minX) * 2.0D + 1.0D);
/* 129 */     double y = 1.0D / ((bb.maxY - bb.minY) * 2.0D + 1.0D);
/* 130 */     double z = 1.0D / ((bb.maxZ - bb.minZ) * 2.0D + 1.0D);
/* 131 */     double xFloor = (1.0D - Math.floor(1.0D / x) * x) / 2.0D;
/* 132 */     double zFloor = (1.0D - Math.floor(1.0D / z) * z) / 2.0D;
/*     */     
/* 134 */     if (x >= 0.0D && y >= 0.0D && z >= 0.0D) {
/* 135 */       int air = 0;
/* 136 */       int traced = 0;
/*     */       float a;
/* 138 */       for (a = 0.0F; a <= 1.0F; a = (float)(a + x)) {
/* 139 */         float b; for (b = 0.0F; b <= 1.0F; b = (float)(b + y)) {
/* 140 */           float c; for (c = 0.0F; c <= 1.0F; c = (float)(c + z)) {
/* 141 */             double xOff = bb.minX + (bb.maxX - bb.minX) * a;
/* 142 */             double yOff = bb.minY + (bb.maxY - bb.minY) * b;
/* 143 */             double zOff = bb.minZ + (bb.maxZ - bb.minZ) * c;
/*     */             
/* 145 */             RayTraceResult result = rayTraceBlocks((World)mc.world, new Vec3d(xOff + xFloor, yOff, zOff + zFloor), vec, 20, mutablePos, DamageUtil::isResistant);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 154 */             if (result == null) {
/* 155 */               air++;
/*     */             }
/*     */             
/* 158 */             traced++;
/*     */           } 
/*     */         } 
/*     */       } 
/* 162 */       return air / traced;
/*     */     } 
/* 164 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTraceBlocks(World world, Vec3d start, Vec3d end, int attempts, BlockPos.MutableBlockPos mutablePos) {
/* 175 */     return rayTraceBlocks(world, start, end, attempts, mutablePos, (pos, state) -> (state.getCollisionBoundingBox(world, pos) != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RayTraceResult rayTraceBlocks(World world, Vec3d start, Vec3d end, int attempts, BlockPos.MutableBlockPos mutablePos, BiPredicate<BlockPos, IBlockState> predicate) {
/* 186 */     if (!Double.isNaN(start.x) && !Double.isNaN(start.y) && !Double.isNaN(start.z) && 
/* 187 */       !Double.isNaN(end.x) && !Double.isNaN(end.y) && !Double.isNaN(end.z)) {
/* 188 */       int currentX = MathHelper.floor(start.x);
/* 189 */       int currentY = MathHelper.floor(start.y);
/* 190 */       int currentZ = MathHelper.floor(start.z);
/*     */       
/* 192 */       int endXFloor = MathHelper.floor(end.x);
/* 193 */       int endYFloor = MathHelper.floor(end.y);
/* 194 */       int endZFloor = MathHelper.floor(end.z);
/*     */       
/* 196 */       IBlockState startBlockState = world.getBlockState((BlockPos)mutablePos.setPos(currentX, currentY, currentZ));
/* 197 */       Block startBlock = startBlockState.getBlock();
/*     */       
/* 199 */       if (startBlock.canCollideCheck(startBlockState, false) && predicate.test(mutablePos, startBlockState)) {
/* 200 */         RayTraceResult result = startBlockState.collisionRayTrace(world, (BlockPos)mutablePos, start, end);
/*     */         
/* 202 */         if (result != null) return result;
/*     */       
/*     */       } 
/* 205 */       int counter = attempts;
/* 206 */       while (counter-- >= 0) {
/* 207 */         EnumFacing side; if (Double.isNaN(start.x) || Double.isNaN(start.y) || Double.isNaN(start.z)) {
/* 208 */           return null;
/*     */         }
/*     */         
/* 211 */         if (currentX == endXFloor && currentY == endYFloor && currentZ == endZFloor) {
/* 212 */           return null;
/*     */         }
/*     */         
/* 215 */         double totalDiffX = end.x - start.x;
/* 216 */         double totalDiffY = end.y - start.y;
/* 217 */         double totalDiffZ = end.z - start.z;
/*     */         
/* 219 */         double nextX = 999.0D;
/* 220 */         double nextY = 999.0D;
/* 221 */         double nextZ = 999.0D;
/*     */         
/* 223 */         double diffX = 999.0D;
/* 224 */         double diffY = 999.0D;
/* 225 */         double diffZ = 999.0D;
/*     */         
/* 227 */         if (endXFloor > currentX) {
/* 228 */           nextX = currentX + 1.0D;
/* 229 */           diffX = (nextX - start.x) / totalDiffX;
/* 230 */         } else if (endXFloor < currentX) {
/* 231 */           nextX = currentX;
/* 232 */           diffX = (nextX - start.x) / totalDiffX;
/*     */         } 
/*     */         
/* 235 */         if (endYFloor > currentY) {
/* 236 */           nextY = currentY + 1.0D;
/* 237 */           diffY = (nextY - start.y) / totalDiffY;
/* 238 */         } else if (endYFloor < currentY) {
/* 239 */           nextY = currentY;
/* 240 */           diffY = (nextY - start.y) / totalDiffY;
/*     */         } 
/*     */         
/* 243 */         if (endZFloor > currentZ) {
/* 244 */           nextZ = currentZ + 1.0D;
/* 245 */           diffZ = (nextZ - start.z) / totalDiffZ;
/* 246 */         } else if (endZFloor < currentZ) {
/* 247 */           nextZ = currentZ;
/* 248 */           diffZ = (nextZ - start.z) / totalDiffZ;
/*     */         } 
/*     */         
/* 251 */         if (diffX == -0.0D) diffX = -1.0E-4D; 
/* 252 */         if (diffY == -0.0D) diffY = -1.0E-4D; 
/* 253 */         if (diffZ == -0.0D) diffZ = -1.0E-4D;
/*     */ 
/*     */         
/* 256 */         if (diffX < diffY && diffX < diffZ) {
/* 257 */           side = (endXFloor > currentX) ? EnumFacing.WEST : EnumFacing.EAST;
/* 258 */           start = new Vec3d(nextX, start.y + totalDiffY * diffX, start.z + totalDiffZ * diffX);
/* 259 */         } else if (diffY < diffZ) {
/* 260 */           side = (endYFloor > currentY) ? EnumFacing.DOWN : EnumFacing.UP;
/* 261 */           start = new Vec3d(start.x + totalDiffX * diffY, nextY, start.z + totalDiffZ * diffY);
/*     */         } else {
/* 263 */           side = (endZFloor > currentZ) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/* 264 */           start = new Vec3d(start.x + totalDiffX * diffZ, start.y + totalDiffY * diffZ, nextZ);
/*     */         } 
/*     */         
/* 267 */         currentX = MathHelper.floor(start.x) - ((side == EnumFacing.EAST) ? 1 : 0);
/* 268 */         currentY = MathHelper.floor(start.y) - ((side == EnumFacing.UP) ? 1 : 0);
/* 269 */         currentZ = MathHelper.floor(start.z) - ((side == EnumFacing.SOUTH) ? 1 : 0);
/* 270 */         mutablePos.setPos(currentX, currentY, currentZ);
/*     */         
/* 272 */         IBlockState state = world.getBlockState((BlockPos)mutablePos);
/* 273 */         Block block = state.getBlock();
/*     */         
/* 275 */         if (block.canCollideCheck(state, false) && predicate.test(mutablePos, state)) {
/* 276 */           RayTraceResult result = state.collisionRayTrace(world, (BlockPos)mutablePos, start, end);
/*     */           
/* 278 */           if (result != null) return result;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 285 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isResistant(BlockPos pos, IBlockState state) {
/* 290 */     return (!state.getMaterial().isLiquid() && state
/* 291 */       .getBlock().getExplosionResistance((World)mc.world, pos, null, null) >= 19.7D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasDurability(ItemStack stack) {
/* 296 */     Item item = stack.getItem();
/* 297 */     return (item instanceof net.minecraft.item.ItemArmor || item instanceof net.minecraft.item.ItemSword || item instanceof net.minecraft.item.ItemTool || item instanceof net.minecraft.item.ItemShield);
/*     */   }
/*     */   
/*     */   public static int getCooldownByWeapon(EntityPlayer player) {
/* 301 */     Item item = player.getHeldItemMainhand().getItem();
/* 302 */     if (item instanceof net.minecraft.item.ItemSword) {
/* 303 */       return 600;
/*     */     }
/* 305 */     if (item instanceof net.minecraft.item.ItemPickaxe) {
/* 306 */       return 850;
/*     */     }
/* 308 */     if (item == Items.IRON_AXE) {
/* 309 */       return 1100;
/*     */     }
/* 311 */     if (item == Items.STONE_HOE) {
/* 312 */       return 500;
/*     */     }
/* 314 */     if (item == Items.IRON_HOE) {
/* 315 */       return 350;
/*     */     }
/* 317 */     if (item == Items.WOODEN_AXE || item == Items.STONE_AXE) {
/* 318 */       return 1250;
/*     */     }
/* 320 */     if (item instanceof net.minecraft.item.ItemSpade || item == Items.GOLDEN_AXE || item == Items.DIAMOND_AXE || item == Items.WOODEN_HOE || item == Items.GOLDEN_HOE) {
/* 321 */       return 1000;
/*     */     }
/* 323 */     return 250;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\DamageUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
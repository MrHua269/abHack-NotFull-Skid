/*     */ package me.abHack.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import me.abHack.OyVey;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockUtil
/*     */   implements Util
/*     */ {
/*  30 */   public static final List<Block> blackList = Arrays.asList(new Block[] { Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE });
/*  31 */   public static final List<Block> shulkerList = Arrays.asList(new Block[] { Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX });
/*  32 */   public static final List<Block> noPlaceBlock = Arrays.asList(new Block[] { Blocks.AIR, (Block)Blocks.WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.FIRE });
/*  33 */   public static List<Block> godBlocks = Arrays.asList(new Block[] { Blocks.AIR, (Block)Blocks.WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.FLOWING_LAVA, Blocks.BEDROCK, (Block)Blocks.FIRE });
/*     */   
/*     */   public static List<EnumFacing> getPossibleSides(BlockPos pos) {
/*  36 */     ArrayList<EnumFacing> facings = new ArrayList<>();
/*  37 */     for (EnumFacing side : EnumFacing.values()) {
/*  38 */       BlockPos neighbour = pos.offset(side);
/*  39 */       if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false) && !mc.world.getBlockState(neighbour).getMaterial().isReplaceable())
/*     */       {
/*  41 */         facings.add(side); } 
/*     */     } 
/*  43 */     return facings;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFirstFacing(BlockPos pos) {
/*  47 */     Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
/*  48 */     if (iterator.hasNext()) {
/*  49 */       return iterator.next();
/*     */     }
/*  51 */     return null;
/*     */   }
/*     */   
/*     */   public static double getMineDistance(BlockPos to) {
/*  55 */     return getMineDistance((Entity)mc.player, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getMineDistance(Entity from, BlockPos to) {
/*  60 */     double x = from.posX - to.getX() + 0.5D;
/*  61 */     double y = from.posY - to.getY() + 0.5D + 1.5D;
/*  62 */     double z = from.posZ - to.getZ() + 0.5D;
/*  63 */     return x * x + y * y + z * z;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getPushDistance(EntityPlayer player, double x, double z) {
/*  68 */     double d0 = player.posX - x;
/*  69 */     double d2 = player.posZ - z;
/*  70 */     return Math.sqrt(d0 * d0 + d2 * d2);
/*     */   }
/*     */   
/*     */   public static boolean isAir(BlockPos pos) {
/*  74 */     return (getBlock(pos) == Blocks.AIR);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isReplaceable(BlockPos pos) {
/*  79 */     return getState(pos).getMaterial().isReplaceable();
/*     */   }
/*     */   
/*     */   public static EnumFacing getRayTraceFacing(BlockPos pos) {
/*  83 */     RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX() + 0.5D, pos.getX() - 0.5D, pos.getX() + 0.5D));
/*  84 */     if (result == null || result.sideHit == null) {
/*  85 */       return EnumFacing.UP;
/*     */     }
/*  87 */     return result.sideHit;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean CanPlace(BlockPos block) {
/*  92 */     for (EnumFacing face : EnumFacing.VALUES) {
/*  93 */       if (isReplaceable(block) && !noPlaceBlock.contains(getBlock(block.offset(face))) && mc.player.getDistanceSq(block) <= MathUtil.square(5.0D))
/*  94 */         return true; 
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int isPositionPlaceable(BlockPos pos, boolean rayTrace) {
/* 101 */     return isPositionPlaceable(pos, rayTrace, true);
/*     */   }
/*     */   
/*     */   public static int isPositionPlaceable(BlockPos pos, boolean rayTrace, boolean entityCheck) {
/* 105 */     Block block = mc.world.getBlockState(pos).getBlock();
/* 106 */     if (!(block instanceof net.minecraft.block.BlockAir) && !(block instanceof net.minecraft.block.BlockLiquid) && !(block instanceof net.minecraft.block.BlockTallGrass) && !(block instanceof net.minecraft.block.BlockFire) && !(block instanceof net.minecraft.block.BlockDeadBush) && !(block instanceof net.minecraft.block.BlockSnow)) {
/* 107 */       return 0;
/*     */     }
/* 109 */     if (!rayTracePlaceCheck(pos, rayTrace, 0.0F)) {
/* 110 */       return -1;
/*     */     }
/* 112 */     if (entityCheck) {
/* 113 */       for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos))) {
/* 114 */         if (entity instanceof net.minecraft.entity.item.EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb)
/* 115 */           continue;  return 1;
/*     */       } 
/*     */     }
/* 118 */     for (EnumFacing side : getPossibleSides(pos)) {
/* 119 */       if (!canBeClicked(pos.offset(side)))
/* 120 */         continue;  return 3;
/*     */     } 
/* 122 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean EntityCheck(BlockPos pos) {
/* 127 */     for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos))) {
/* 128 */       if (entity instanceof net.minecraft.entity.item.EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb)
/*     */         continue; 
/* 130 */       if (entity != null)
/* 131 */         return true; 
/*     */     } 
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean PlayerCheck(BlockPos pos) {
/* 138 */     for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos))) {
/* 139 */       if (entity instanceof EntityPlayer)
/* 140 */         return true; 
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean CrystalCheck(BlockPos pos) {
/* 147 */     for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos))) {
/* 148 */       if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal)
/* 149 */         return true; 
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<BlockPos> getDisc(BlockPos pos, float r) {
/* 157 */     ArrayList<BlockPos> circleblocks = new ArrayList<>();
/* 158 */     int cx = pos.getX();
/* 159 */     int cy = pos.getY();
/* 160 */     int cz = pos.getZ();
/* 161 */     int x = cx - (int)r;
/* 162 */     while (x <= cx + r) {
/* 163 */       int z = cz - (int)r;
/* 164 */       while (z <= cz + r) {
/* 165 */         double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z));
/* 166 */         if (dist < (r * r)) {
/* 167 */           BlockPos position = new BlockPos(x, cy, z);
/* 168 */           circleblocks.add(position);
/*     */         } 
/* 170 */         z++;
/*     */       } 
/* 172 */       x++;
/*     */     } 
/* 174 */     return circleblocks;
/*     */   }
/*     */   
/*     */   public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
/* 178 */     if (packet) {
/* 179 */       float f = (float)(vec.x - pos.getX());
/* 180 */       float f1 = (float)(vec.y - pos.getY());
/* 181 */       float f2 = (float)(vec.z - pos.getZ());
/* 182 */       mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
/*     */     } else {
/* 184 */       mc.playerController.processRightClickBlock(mc.player, mc.world, pos, direction, vec, hand);
/*     */     } 
/* 186 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/* 187 */     mc.rightClickDelayTimer = 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void placeBlockNotRetarded(BlockPos pos, EnumHand hand, boolean rotate, boolean packet, boolean altRotate) {
/* 192 */     EnumFacing side = getFirstFacing(pos);
/* 193 */     if (side == null)
/* 194 */       return;  BlockPos neighbour = pos.offset(side);
/* 195 */     EnumFacing opposite = side.getOpposite();
/* 196 */     Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
/* 197 */     Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();
/* 198 */     if (!mc.player.isSneaking() && (blackList.contains(neighbourBlock) || shulkerList.contains(neighbourBlock))) {
/* 199 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
/* 200 */       mc.player.setSneaking(true);
/*     */     } 
/* 202 */     if (rotate) RotationUtil.faceVector(altRotate ? new Vec3d((Vec3i)pos) : hitVec, true); 
/* 203 */     rightClickBlock(neighbour, hitVec, hand, opposite, packet);
/* 204 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canBlockBeSeen(double x, double y, double z) {
/* 209 */     return (mc.world.rayTraceBlocks(mc.player
/* 210 */         .getPositionEyes(1.0F), new Vec3d(x, y + 1.7D, z), false, true, false) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canBlockBeSeen(World world, double x, double y, double z, BlockPos.MutableBlockPos mutablePos) {
/* 219 */     return (DamageUtil.rayTraceBlocks(world, mc.player
/*     */         
/* 221 */         .getPositionEyes(1.0F), new Vec3d(x, y + 1.7D, z), 20, mutablePos) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean placeBlock(BlockPos pos, EnumHand hand, boolean rotate, boolean packet, boolean isSneaking) {
/* 229 */     boolean sneaking = false;
/* 230 */     EnumFacing side = getFirstFacing(pos);
/* 231 */     if (side == null) {
/* 232 */       return isSneaking;
/*     */     }
/* 234 */     BlockPos neighbour = pos.offset(side);
/* 235 */     EnumFacing opposite = side.getOpposite();
/* 236 */     Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
/* 237 */     Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();
/* 238 */     if (!mc.player.isSneaking() && (blackList.contains(neighbourBlock) || shulkerList.contains(neighbourBlock))) {
/* 239 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
/* 240 */       mc.player.setSneaking(true);
/* 241 */       sneaking = true;
/*     */     } 
/* 243 */     if (rotate) {
/* 244 */       RotationUtil.faceVector(hitVec, true);
/*     */     }
/* 246 */     rightClickBlock(neighbour, hitVec, hand, opposite, packet);
/* 247 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/* 248 */     mc.rightClickDelayTimer = 4;
/* 249 */     return (sneaking || isSneaking);
/*     */   }
/*     */   
/*     */   public static boolean placeBlockSmartRotate(BlockPos pos, EnumHand hand, boolean rotate, boolean packet, boolean isSneaking) {
/* 253 */     boolean sneaking = false;
/* 254 */     EnumFacing side = getFirstFacing(pos);
/*     */     
/* 256 */     if (side == null) {
/* 257 */       return isSneaking;
/*     */     }
/* 259 */     BlockPos neighbour = pos.offset(side);
/* 260 */     EnumFacing opposite = side.getOpposite();
/* 261 */     Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
/* 262 */     Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();
/* 263 */     if (!mc.player.isSneaking() && (blackList.contains(neighbourBlock) || shulkerList.contains(neighbourBlock))) {
/* 264 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
/* 265 */       sneaking = true;
/*     */     } 
/* 267 */     if (rotate) {
/* 268 */       OyVey.rotationManager.lookAtVec3d(hitVec);
/*     */     }
/* 270 */     rightClickBlock(neighbour, hitVec, hand, opposite, packet);
/* 271 */     mc.player.swingArm(EnumHand.MAIN_HAND);
/* 272 */     mc.rightClickDelayTimer = 4;
/* 273 */     return (sneaking || isSneaking);
/*     */   }
/*     */   
/*     */   public static Vec3d[] getHelpingBlocks(Vec3d vec3d) {
/* 277 */     return new Vec3d[] { new Vec3d(vec3d.x, vec3d.y - 1.0D, vec3d.z), new Vec3d((vec3d.x != 0.0D) ? (vec3d.x * 2.0D) : vec3d.x, vec3d.y, (vec3d.x != 0.0D) ? vec3d.z : (vec3d.z * 2.0D)), new Vec3d((vec3d.x == 0.0D) ? (vec3d.x + 1.0D) : vec3d.x, vec3d.y, (vec3d.x == 0.0D) ? vec3d.z : (vec3d.z + 1.0D)), new Vec3d((vec3d.x == 0.0D) ? (vec3d.x - 1.0D) : vec3d.x, vec3d.y, (vec3d.x == 0.0D) ? vec3d.z : (vec3d.z - 1.0D)), new Vec3d(vec3d.x, vec3d.y + 1.0D, vec3d.z) };
/*     */   }
/*     */   
/*     */   public static List<BlockPos> getSphere(BlockPos pos, float r, int h, boolean hollow, boolean sphere, int plus_y) {
/* 281 */     ArrayList<BlockPos> circleblocks = new ArrayList<>();
/* 282 */     int cx = pos.getX();
/* 283 */     int cy = pos.getY();
/* 284 */     int cz = pos.getZ();
/* 285 */     int x = cx - (int)r;
/* 286 */     while (x <= cx + r) {
/* 287 */       int z = cz - (int)r;
/* 288 */       while (z <= cz + r) {
/* 289 */         int y = sphere ? (cy - (int)r) : cy;
/*     */         while (true) {
/* 291 */           float f = y;
/* 292 */           float f2 = sphere ? (cy + r) : (cy + h);
/* 293 */           if (f >= f2)
/* 294 */             break;  double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
/* 295 */           if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
/* 296 */             BlockPos l = new BlockPos(x, y + plus_y, z);
/* 297 */             circleblocks.add(l);
/*     */           } 
/* 299 */           y++;
/*     */         } 
/* 301 */         z++;
/*     */       } 
/* 303 */       x++;
/*     */     } 
/* 305 */     return circleblocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canBeClicked(BlockPos pos) {
/* 310 */     return getBlock(pos).canCollideCheck(getState(pos), false);
/*     */   }
/*     */   
/*     */   public static Block getBlock(BlockPos pos) {
/* 314 */     return getState(pos).getBlock();
/*     */   }
/*     */   
/*     */   private static IBlockState getState(BlockPos pos) {
/* 318 */     return mc.world.getBlockState(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand) {
/* 324 */     mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, hand, pos.getX(), pos.getY(), pos.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d[] convertVec3ds(Vec3d vec3d, Vec3d[] input) {
/* 329 */     Vec3d[] output = new Vec3d[input.length];
/* 330 */     for (int i = 0; i < input.length; i++) {
/* 331 */       output[i] = vec3d.add(input[i]);
/*     */     }
/* 333 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canBreak(BlockPos pos) {
/* 338 */     return canBreak(mc.world.getBlockState(pos), pos);
/*     */   }
/*     */   
/*     */   public static boolean canBreak(IBlockState state, BlockPos pos) {
/* 342 */     return (state.getBlockHardness((World)mc.world, pos) != -1.0F || state
/* 343 */       .getMaterial().isLiquid());
/*     */   }
/*     */   
/*     */   public static boolean isValidBlock(BlockPos pos) {
/* 347 */     Block block = mc.world.getBlockState(pos).getBlock();
/* 348 */     return (!(block instanceof net.minecraft.block.BlockLiquid) && block.getMaterial(null) != Material.AIR);
/*     */   }
/*     */   
/*     */   public static boolean isScaffoldPos(BlockPos pos) {
/* 352 */     return (mc.world.isAirBlock(pos) || mc.world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER || mc.world.getBlockState(pos).getBlock() == Blocks.TALLGRASS || mc.world.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockLiquid);
/*     */   }
/*     */   
/*     */   public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck, float height) {
/* 356 */     return (!shouldCheck || mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX(), (pos.getY() + height), pos.getZ()), false, true, false) == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\BlockUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
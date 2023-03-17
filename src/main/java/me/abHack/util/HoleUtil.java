/*     */ package me.abHack.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class HoleUtil implements Util {
/*  11 */   public static BlockPos[] holeOffsets = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0) };
/*     */   
/*     */   public static boolean isObbyHole(BlockPos pos) {
/*  14 */     boolean isHole = true;
/*  15 */     int bedrock = 0;
/*  16 */     for (BlockPos off : holeOffsets) {
/*  17 */       Block b = mc.world.getBlockState(pos.add((Vec3i)off)).getBlock();
/*  18 */       if (!isSafeBlock(pos.add((Vec3i)off))) {
/*  19 */         isHole = false;
/*  20 */       } else if (b == Blocks.OBSIDIAN || b == Blocks.ENDER_CHEST || b == Blocks.ANVIL) {
/*  21 */         bedrock++;
/*     */       } 
/*     */     } 
/*  24 */     if (HoleDetection(pos))
/*  25 */       isHole = false; 
/*  26 */     if (bedrock < 1)
/*  27 */       isHole = false; 
/*  28 */     return isHole;
/*     */   }
/*     */   
/*     */   public static boolean isBedrockHoles(BlockPos pos) {
/*  32 */     boolean isHole = true;
/*  33 */     for (BlockPos off : holeOffsets) {
/*  34 */       Block b = mc.world.getBlockState(pos.add((Vec3i)off)).getBlock();
/*  35 */       if (b != Blocks.BEDROCK)
/*  36 */         isHole = false; 
/*     */     } 
/*  38 */     if (HoleDetection(pos))
/*  39 */       isHole = false; 
/*  40 */     return isHole;
/*     */   }
/*     */   
/*     */   public static Hole isDoubleHole(BlockPos pos) {
/*  44 */     if (checkOffset(pos, 1, 0))
/*  45 */       return new Hole(false, true, pos, pos.add(1, 0, 0)); 
/*  46 */     if (checkOffset(pos, 0, 1))
/*  47 */       return new Hole(false, true, pos, pos.add(0, 0, 1)); 
/*  48 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean checkOffset(BlockPos pos, int offX, int offZ) {
/*  52 */     return (mc.world.getBlockState(pos).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(offX, 0, offZ)).getBlock() == Blocks.AIR && isSafeBlock(pos.add(0, -1, 0)) && isSafeBlock(pos.add(offX, -1, offZ)) && isSafeBlock(pos.add(offX * 2, 0, offZ * 2)) && isSafeBlock(pos.add(-offX, 0, -offZ)) && isSafeBlock(pos.add(offZ, 0, offX)) && isSafeBlock(pos.add(-offZ, 0, -offX)) && isSafeBlock(pos.add(offX, 0, offZ).add(offZ, 0, offX)) && isSafeBlock(pos.add(offX, 0, offZ).add(-offZ, 0, -offX)));
/*     */   }
/*     */   
/*     */   static boolean isSafeBlock(BlockPos pos) {
/*  56 */     return (mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(pos).getBlock() == Blocks.ENDER_CHEST);
/*     */   }
/*     */   
/*     */   public static List<Hole> getHoles(double range, BlockPos playerPos, boolean doubles) {
/*  60 */     ArrayList<Hole> holes = new ArrayList<>();
/*  61 */     List<BlockPos> circle = getSphere(range, playerPos, true, false);
/*  62 */     for (BlockPos pos : circle) {
/*  63 */       if (mc.world.getBlockState(pos).getBlock() != Blocks.AIR)
/*     */         continue; 
/*  65 */       if (isObbyHole(pos)) {
/*  66 */         holes.add(new Hole(false, false, pos));
/*     */         continue;
/*     */       } 
/*  69 */       if (isBedrockHoles(pos)) {
/*  70 */         holes.add(new Hole(true, false, pos));
/*     */         continue;
/*     */       } 
/*     */       Hole dh;
/*  74 */       if (!doubles || (dh = isDoubleHole(pos)) == null || (mc.world.getBlockState(dh.pos1.add(0, 1, 0)).getBlock() != Blocks.AIR && mc.world.getBlockState(dh.pos2.add(0, 1, 0)).getBlock() != Blocks.AIR))
/*     */         continue; 
/*  76 */       holes.add(dh);
/*     */     } 
/*  78 */     return holes;
/*     */   }
/*     */   
/*     */   public static List<BlockPos> getSphere(double range, BlockPos pos, boolean sphere, boolean hollow) {
/*  82 */     ArrayList<BlockPos> circleblocks = new ArrayList<>();
/*  83 */     int cx = pos.getX();
/*  84 */     int cy = pos.getY();
/*  85 */     int cz = pos.getZ();
/*  86 */     int x = cx - (int)range;
/*  87 */     while (x <= cx + range) {
/*  88 */       int z = cz - (int)range;
/*  89 */       while (z <= cz + range) {
/*  90 */         int y = sphere ? (cy - (int)range) : cy;
/*     */         while (true) {
/*  92 */           double d = y;
/*  93 */           double d2 = cy + range;
/*  94 */           if (d >= d2)
/*     */             break; 
/*  96 */           double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
/*  97 */           if (dist < range * range && (!hollow || dist >= (range - 1.0D) * (range - 1.0D))) {
/*  98 */             BlockPos l = new BlockPos(x, y, z);
/*  99 */             circleblocks.add(l);
/*     */           } 
/* 101 */           y++;
/*     */         } 
/* 103 */         z++;
/*     */       } 
/* 105 */       x++;
/*     */     } 
/* 107 */     return circleblocks;
/*     */   }
/*     */   
/*     */   private static boolean HoleDetection(BlockPos pos) {
/* 111 */     return (!BlockUtil.isAir(pos.add(0, 1, 0)) || !BlockUtil.isAir(pos.add(0, 2, 0)) || ((!BlockUtil.isAir(pos.add(1, 1, 0)) || !BlockUtil.isAir(pos.add(1, 2, 0))) && (!BlockUtil.isAir(pos.add(-1, 1, 0)) || !BlockUtil.isAir(pos.add(-1, 2, 0))) && (
/* 112 */       !BlockUtil.isAir(pos.add(0, 1, 1)) || !BlockUtil.isAir(pos.add(0, 2, 1))) && (!BlockUtil.isAir(pos.add(0, 1, -1)) || !BlockUtil.isAir(pos.add(0, 2, -1))) && (!BlockUtil.isAir(pos.add(0, 3, 0)) || (!BlockUtil.isAir(pos.add(0, 4, 0)) && !BlockUtil.isAir(pos.add(1, 2, 0)) && !BlockUtil.isAir(pos.add(-1, 2, 0)) && !BlockUtil.isAir(pos.add(0, 2, 1)) && !BlockUtil.isAir(pos.add(0, 2, -1))))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Hole
/*     */   {
/*     */     public boolean bedrock;
/*     */     
/*     */     public boolean doubleHole;
/*     */     
/*     */     public BlockPos pos1;
/*     */     public BlockPos pos2;
/*     */     
/*     */     public Hole(boolean bedrock, boolean doubleHole, BlockPos pos1, BlockPos pos2) {
/* 126 */       this.bedrock = bedrock;
/* 127 */       this.doubleHole = doubleHole;
/* 128 */       this.pos1 = pos1;
/* 129 */       this.pos2 = pos2;
/*     */     }
/*     */     
/*     */     public Hole(boolean bedrock, boolean doubleHole, BlockPos pos1) {
/* 133 */       this.bedrock = bedrock;
/* 134 */       this.doubleHole = doubleHole;
/* 135 */       this.pos1 = pos1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\HoleUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
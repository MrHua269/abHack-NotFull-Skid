/*     */ package me.abHack.util.render.HoleEsp;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.Util;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class HoleUtil
/*     */   implements Util
/*     */ {
/*  14 */   public static final HashMap<EnumFacing, Integer> FACEMAP = new HashMap<>();
/*     */   
/*     */   static {
/*  17 */     FACEMAP.put(EnumFacing.DOWN, Integer.valueOf(1));
/*  18 */     FACEMAP.put(EnumFacing.WEST, Integer.valueOf(16));
/*  19 */     FACEMAP.put(EnumFacing.NORTH, Integer.valueOf(4));
/*  20 */     FACEMAP.put(EnumFacing.SOUTH, Integer.valueOf(8));
/*  21 */     FACEMAP.put(EnumFacing.EAST, Integer.valueOf(32));
/*  22 */     FACEMAP.put(EnumFacing.UP, Integer.valueOf(2));
/*     */   }
/*     */   
/*     */   public static boolean validObi(BlockPos pos) {
/*  26 */     return (!validBedrock(pos) && (mc.world
/*  27 */       .getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) && (mc.world
/*  28 */       .getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) && (mc.world
/*  29 */       .getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) && (mc.world
/*  30 */       .getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) && (mc.world
/*  31 */       .getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) && mc.world
/*  32 */       .getBlockState(pos).getMaterial() == Material.AIR &&
/*  33 */       HoleDetection(pos) && mc.world
/*  34 */       .getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && mc.world
/*  35 */       .getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR);
/*     */   }
/*     */   
/*     */   public static boolean validBedrock(BlockPos pos) {
/*  39 */     return (mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK && mc.world
/*  40 */       .getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK && mc.world
/*  41 */       .getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK && mc.world
/*  42 */       .getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK && mc.world
/*  43 */       .getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK && mc.world
/*  44 */       .getBlockState(pos).getMaterial() == Material.AIR &&
/*  45 */       HoleDetection(pos) && mc.world
/*  46 */       .getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && mc.world
/*  47 */       .getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR);
/*     */   }
/*     */   
/*     */   public static BlockPos validTwoBlockObiXZ(BlockPos pos) {
/*  51 */     if ((mc.world
/*  52 */       .getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  53 */       .getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  54 */       .getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  55 */       .getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK) && mc.world
/*  56 */       .getBlockState(pos).getMaterial() == Material.AIR && mc.world
/*  57 */       .getBlockState(pos.up()).getMaterial() == Material.AIR && mc.world
/*  58 */       .getBlockState(pos.up(2)).getMaterial() == Material.AIR && (mc.world
/*  59 */       .getBlockState(pos.east().down()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.east().down()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  60 */       .getBlockState(pos.east(2)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.east(2)).getBlock() == Blocks.BEDROCK) && (mc.world
/*  61 */       .getBlockState(pos.east().south()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.east().south()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  62 */       .getBlockState(pos.east().north()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.east().north()).getBlock() == Blocks.BEDROCK) && mc.world
/*  63 */       .getBlockState(pos.east()).getMaterial() == Material.AIR && mc.world
/*  64 */       .getBlockState(pos.east().up()).getMaterial() == Material.AIR && mc.world
/*  65 */       .getBlockState(pos.east().up(2)).getMaterial() == Material.AIR)
/*     */     {
/*  67 */       return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(1, 0, 0) : null; } 
/*  68 */     if ((mc.world
/*  69 */       .getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  70 */       .getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  71 */       .getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  72 */       .getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK) && mc.world
/*  73 */       .getBlockState(pos).getMaterial() == Material.AIR && mc.world
/*  74 */       .getBlockState(pos.up()).getMaterial() == Material.AIR && mc.world
/*  75 */       .getBlockState(pos.up(2)).getMaterial() == Material.AIR && (mc.world
/*  76 */       .getBlockState(pos.south().down()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.south().down()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  77 */       .getBlockState(pos.south(2)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.south(2)).getBlock() == Blocks.BEDROCK) && (mc.world
/*  78 */       .getBlockState(pos.south().east()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.south().east()).getBlock() == Blocks.BEDROCK) && (mc.world
/*  79 */       .getBlockState(pos.south().west()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos.south().west()).getBlock() == Blocks.BEDROCK) && mc.world
/*  80 */       .getBlockState(pos.south()).getMaterial() == Material.AIR && mc.world
/*  81 */       .getBlockState(pos.south().up()).getMaterial() == Material.AIR && mc.world
/*  82 */       .getBlockState(pos.south().up(2)).getMaterial() == Material.AIR)
/*     */     {
/*  84 */       return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(0, 0, 1) : null;
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public static BlockPos validTwoBlockBedrockXZ(BlockPos pos) {
/*  90 */     if (mc.world
/*  91 */       .getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && mc.world
/*  92 */       .getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && mc.world
/*  93 */       .getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && mc.world
/*  94 */       .getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && mc.world
/*  95 */       .getBlockState(pos).getMaterial() == Material.AIR && mc.world
/*  96 */       .getBlockState(pos.up()).getMaterial() == Material.AIR && mc.world
/*  97 */       .getBlockState(pos.up(2)).getMaterial() == Material.AIR && mc.world
/*  98 */       .getBlockState(pos.east().down()).getBlock() == Blocks.BEDROCK && mc.world
/*  99 */       .getBlockState(pos.east(2)).getBlock() == Blocks.BEDROCK && mc.world
/* 100 */       .getBlockState(pos.east().south()).getBlock() == Blocks.BEDROCK && mc.world
/* 101 */       .getBlockState(pos.east().north()).getBlock() == Blocks.BEDROCK && mc.world
/* 102 */       .getBlockState(pos.east()).getMaterial() == Material.AIR && mc.world
/* 103 */       .getBlockState(pos.east().up()).getMaterial() == Material.AIR && mc.world
/* 104 */       .getBlockState(pos.east().up(2)).getMaterial() == Material.AIR)
/*     */     {
/* 106 */       return new BlockPos(1, 0, 0); } 
/* 107 */     if (mc.world
/* 108 */       .getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && mc.world
/* 109 */       .getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && mc.world
/* 110 */       .getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && mc.world
/* 111 */       .getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && mc.world
/* 112 */       .getBlockState(pos).getMaterial() == Material.AIR && mc.world
/* 113 */       .getBlockState(pos.up()).getMaterial() == Material.AIR && mc.world
/* 114 */       .getBlockState(pos.up(2)).getMaterial() == Material.AIR && mc.world
/* 115 */       .getBlockState(pos.south().down()).getBlock() == Blocks.BEDROCK && mc.world
/* 116 */       .getBlockState(pos.south(2)).getBlock() == Blocks.BEDROCK && mc.world
/* 117 */       .getBlockState(pos.south().east()).getBlock() == Blocks.BEDROCK && mc.world
/* 118 */       .getBlockState(pos.south().west()).getBlock() == Blocks.BEDROCK && mc.world
/* 119 */       .getBlockState(pos.south()).getMaterial() == Material.AIR && mc.world
/* 120 */       .getBlockState(pos.south().up()).getMaterial() == Material.AIR && mc.world
/* 121 */       .getBlockState(pos.south().up(2)).getMaterial() == Material.AIR)
/*     */     {
/* 123 */       return new BlockPos(0, 0, 1);
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean HoleDetection(BlockPos pos) {
/* 129 */     return ((BlockUtil.isAir(pos.add(1, 1, 0)) && BlockUtil.isAir(pos.add(1, 2, 0))) || (BlockUtil.isAir(pos.add(-1, 1, 0)) && BlockUtil.isAir(pos.add(-1, 2, 0))) || (
/* 130 */       BlockUtil.isAir(pos.add(0, 1, 1)) && BlockUtil.isAir(pos.add(0, 2, 1))) || (BlockUtil.isAir(pos.add(0, 1, -1)) && BlockUtil.isAir(pos.add(0, 2, -1))) || (BlockUtil.isAir(pos.add(0, 3, 0)) && (BlockUtil.isAir(pos.add(0, 4, 0)) || BlockUtil.isAir(pos.add(1, 2, 0)) || BlockUtil.isAir(pos.add(-1, 2, 0)) || BlockUtil.isAir(pos.add(0, 2, 1)) || BlockUtil.isAir(pos.add(0, 2, -1)))));
/*     */   }
/*     */   
/*     */   public static final class Quad {
/*     */     public static final int DOWN = 1;
/*     */     public static final int UP = 2;
/*     */     public static final int NORTH = 4;
/*     */     public static final int SOUTH = 8;
/*     */     public static final int WEST = 16;
/*     */     public static final int EAST = 32;
/*     */     public static final int ALL = 63;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\HoleEsp\HoleUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
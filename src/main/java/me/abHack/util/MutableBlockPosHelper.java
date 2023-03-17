/*    */ package me.abHack.util;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class MutableBlockPosHelper {
/*  6 */   public BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/*    */   
/*    */   public static BlockPos.MutableBlockPos set(BlockPos.MutableBlockPos mutablePos, double x, double y, double z) {
/*  9 */     return mutablePos.setPos(x, y, z);
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos set(BlockPos.MutableBlockPos mutablePos, BlockPos pos) {
/* 13 */     return mutablePos.setPos(pos.getX(), pos.getY(), pos.getZ());
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos set(BlockPos.MutableBlockPos mutablePos, BlockPos pos, double x, double y, double z) {
/* 17 */     return mutablePos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos set(BlockPos.MutableBlockPos mutablePos, BlockPos pos, int x, int y, int z) {
/* 21 */     return mutablePos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos set(BlockPos.MutableBlockPos mutablePos, int x, int y, int z) {
/* 25 */     return mutablePos.setPos(x, y, z);
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos setAndAdd(BlockPos.MutableBlockPos mutablePos, int x, int y, int z) {
/* 29 */     return mutablePos.setPos(mutablePos.getX() + x, mutablePos.getY() + y, mutablePos.getZ() + z);
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos setAndAdd(BlockPos.MutableBlockPos mutablePos, double x, double y, double z) {
/* 33 */     return mutablePos.setPos(mutablePos.getX() + x, mutablePos.getY() + y, mutablePos.getZ() + z);
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos setAndAdd(BlockPos.MutableBlockPos mutablePos, BlockPos pos) {
/* 37 */     return mutablePos.setPos(mutablePos.getX() + pos.getX(), mutablePos.getY() + pos.getY(), mutablePos.getZ() + pos.getZ());
/*    */   }
/*    */   
/*    */   public static BlockPos.MutableBlockPos setAndAdd(BlockPos.MutableBlockPos mutablePos, BlockPos pos, double x, double y, double z) {
/* 41 */     return mutablePos.setPos((mutablePos.getX() + pos.getX()) + x, (mutablePos.getY() + pos.getY()) + y, (mutablePos.getZ() + pos.getZ()) + z);
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos set(double x, double y, double z) {
/* 45 */     return this.mutablePos.setPos(x, y, z);
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos set(BlockPos pos) {
/* 49 */     return this.mutablePos.setPos(pos.getX(), pos.getY(), pos.getZ());
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos set(BlockPos pos, double x, double y, double z) {
/* 53 */     return this.mutablePos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos set(BlockPos pos, int x, int y, int z) {
/* 57 */     return this.mutablePos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos set(int x, int y, int z) {
/* 61 */     return this.mutablePos.setPos(x, y, z);
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos setAndAdd(int x, int y, int z) {
/* 65 */     return this.mutablePos.setPos(this.mutablePos.getX() + x, this.mutablePos.getY() + y, this.mutablePos.getZ() + z);
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos setAndAdd(double x, double y, double z) {
/* 69 */     return this.mutablePos.setPos(this.mutablePos.getX() + x, this.mutablePos.getY() + y, this.mutablePos.getZ() + z);
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos setAndAdd(BlockPos pos) {
/* 73 */     return this.mutablePos.setPos(this.mutablePos.getX() + pos.getX(), this.mutablePos.getY() + pos.getY(), this.mutablePos.getZ() + pos.getZ());
/*    */   }
/*    */   
/*    */   public BlockPos.MutableBlockPos setAndAdd(BlockPos pos, double x, double y, double z) {
/* 77 */     return this.mutablePos.setPos((this.mutablePos.getX() + pos.getX()) + x, (this.mutablePos.getY() + pos.getY()) + y, (this.mutablePos.getZ() + pos.getZ()) + z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\MutableBlockPosHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
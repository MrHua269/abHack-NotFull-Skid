/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Lawnmower
/*    */   extends Module
/*    */ {
/* 21 */   public Setting<Integer> Radius = register(new Setting("Radius", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(6)));
/* 22 */   public Setting<Boolean> Flowers = register(new Setting("Flowers", Boolean.valueOf(true)));
/*    */ 
/*    */   
/*    */   public Lawnmower() {
/* 26 */     super("Lawnmower", "Breaks grass and flowers in range", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */   
/*    */   public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
/* 30 */     List<BlockPos> circleblocks = new ArrayList<>();
/* 31 */     int cx = loc.getX();
/* 32 */     int cy = loc.getY();
/* 33 */     int cz = loc.getZ();
/* 34 */     for (int x = cx - (int)r; x <= cx + r; x++) {
/* 35 */       for (int z = cz - (int)r; z <= cz + r; ) {
/* 36 */         int y = sphere ? (cy - (int)r) : cy; for (;; z++) { if (y < (sphere ? (cy + r) : (cy + h))) {
/* 37 */             double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
/* 38 */             if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F))))
/* 39 */               circleblocks.add(new BlockPos(x, y + plus_y, z));  y++; continue;
/*    */           }  }
/*    */       
/*    */       } 
/*    */     } 
/* 44 */     return circleblocks;
/*    */   }
/*    */   
/*    */   public static BlockPos GetLocalPlayerPosFloored() {
/* 48 */     return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
/*    */   }
/*    */   
/*    */   public static double GetDistanceOfEntityToBlock(Entity p_Entity, BlockPos p_Pos) {
/* 52 */     return GetDistance(p_Entity.posX, p_Entity.posY, p_Entity.posZ, p_Pos.getX(), p_Pos.getY(), p_Pos.getZ());
/*    */   }
/*    */   
/*    */   public static double GetDistance(double p_X, double p_Y, double p_Z, double x, double y, double z) {
/* 56 */     double d0 = p_X - x;
/* 57 */     double d1 = p_Y - y;
/* 58 */     double d2 = p_Z - z;
/* 59 */     return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 66 */     BlockPos l_ClosestPos = getSphere(GetLocalPlayerPosFloored(), ((Integer)this.Radius.getValue()).intValue(), ((Integer)this.Radius.getValue()).intValue(), false, true, 0).stream().filter(this::IsValidBlockPos).min(Comparator.comparing(p_Pos -> Double.valueOf(GetDistanceOfEntityToBlock((Entity)mc.player, p_Pos)))).orElse(null);
/*    */     
/* 68 */     if (l_ClosestPos != null) {
/* 69 */       mc.player.swingArm(EnumHand.MAIN_HAND);
/* 70 */       mc.playerController.clickBlock(l_ClosestPos, EnumFacing.UP);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean IsValidBlockPos(BlockPos p_Pos) {
/* 75 */     IBlockState l_State = mc.world.getBlockState(p_Pos);
/*    */     
/* 77 */     if (l_State.getBlock() instanceof net.minecraft.block.BlockTallGrass || l_State.getBlock() instanceof net.minecraft.block.BlockDoublePlant) {
/* 78 */       return true;
/*    */     }
/* 80 */     return (((Boolean)this.Flowers.getValue()).booleanValue() && l_State.getBlock() instanceof net.minecraft.block.BlockFlower);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Lawnmower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
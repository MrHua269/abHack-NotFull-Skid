/*    */ package me.abHack.util;
/*    */ 
/*    */ import me.abHack.features.modules.combat.Surround;
/*    */ import me.abHack.features.modules.player.Burrow;
/*    */ import me.abHack.features.modules.player.BurrowX;
/*    */ import me.abHack.features.modules.player.Freecam;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class abUtil
/*    */   implements Util
/*    */ {
/*    */   public static boolean CanPlaceCrystal(BlockPos pos) {
/* 16 */     return (BlockUtil.canBlockBeSeen(pos.getX() + 0.5D, (pos.getY() + 1), pos.getZ() + 0.5D) || Math.sqrt(mc.player.getDistanceSq(pos.getX() + 0.5D, (pos.getY() + 1), pos.getZ() + 0.5D)) <= 3.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean CanAttackCrystal(Entity crystal) {
/* 21 */     if (crystal == null)
/* 22 */       return false; 
/* 23 */     if (!(crystal instanceof net.minecraft.entity.item.EntityEnderCrystal))
/* 24 */       return false; 
/* 25 */     if (crystal.isDead)
/* 26 */       return false; 
/* 27 */     if (Math.sqrt(mc.player.getDistanceSq(crystal.posX, crystal.posY, crystal.posZ)) > 6.0D)
/* 28 */       return false; 
/* 29 */     return (mc.player.canEntityBeSeen(crystal) || Math.sqrt(mc.player.getDistanceSq(crystal.posX, crystal.posY, crystal.posZ)) <= 3.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean Freecamcheck(Entity entiy) {
/* 34 */     return (Freecam.INSTANCE.isOff() || !entiy.getName().equals(mc.player.getName()));
/*    */   }
/*    */   
/*    */   public static boolean HealthSecurity() {
/* 38 */     return (Surround.INSTANCE.isOn() || Burrow.INSTANCE.isOn() || BurrowX.INSTANCE.isOn() || EntityUtil.getHealth((Entity)mc.player) <= 8.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isAir(BlockPos pos) {
/* 43 */     return (mc.world.getBlockState(pos).getBlock() == Blocks.AIR);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\abUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class AntiVoid
/*    */   extends Module {
/* 10 */   public Setting<Double> yLevel = register(new Setting("YLevel", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D)));
/* 11 */   public Setting<Double> yForce = register(new Setting("YMotion", Double.valueOf(0.1D), Double.valueOf(0.0D), Double.valueOf(1.0D)));
/*    */   
/*    */   public AntiVoid() {
/* 14 */     super("AntiVoid", "Glitches you up from void.", Module.Category.MOVEMENT, false, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 19 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 22 */     if (!mc.player.noClip && mc.player.posY <= ((Double)this.yLevel.getValue()).doubleValue()) {
/* 23 */       RayTraceResult trace = mc.world.rayTraceBlocks(mc.player.getPositionVector(), new Vec3d(mc.player.posX, 0.0D, mc.player.posZ), false, false, false);
/* 24 */       if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
/*    */         return;
/*    */       }
/* 27 */       mc.player.motionY = ((Double)this.yForce.getValue()).doubleValue();
/* 28 */       if (mc.player.getRidingEntity() != null) {
/* 29 */         (mc.player.getRidingEntity()).motionY = ((Double)this.yForce.getValue()).doubleValue();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayInfo() {
/* 36 */     return ((Double)this.yLevel.getValue()).toString() + ", " + ((Double)this.yForce.getValue()).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\AntiVoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
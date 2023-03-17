/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class BoatFly
/*    */   extends Module
/*    */ {
/*  9 */   private final Setting<Double> speed = register(new Setting("Speed", Double.valueOf(5.0D), Double.valueOf(0.1D), Double.valueOf(20.0D)));
/* 10 */   private final Setting<Double> yspeed = register(new Setting("YSpeed", Double.valueOf(3.0D), Double.valueOf(0.1D), Double.valueOf(20.0D)));
/* 11 */   private final Setting<Boolean> glide = register(new Setting("Glide", Boolean.valueOf(true)));
/*    */   
/*    */   public BoatFly() {
/* 14 */     super("BoatFly", "BoatFly.", Module.Category.MOVEMENT, false, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 19 */     if (mc.player.getRidingEntity() != null) {
/* 20 */       mc.player.getRidingEntity().setNoGravity(false);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     if (mc.player == null || mc.player.getRidingEntity() == null || mc.world == null) {
/*    */       return;
/*    */     }
/* 29 */     if (mc.player.getRidingEntity() != null) {
/*    */       
/* 31 */       mc.player.getRidingEntity().setNoGravity(true);
/* 32 */       (mc.player.getRidingEntity()).motionY = 0.0D;
/*    */       
/* 34 */       if (mc.gameSettings.keyBindJump.isKeyDown()) {
/* 35 */         (mc.player.getRidingEntity()).onGround = false;
/* 36 */         (mc.player.getRidingEntity()).motionY = ((Double)this.yspeed.getValue()).doubleValue();
/*    */       } 
/* 38 */       if (mc.gameSettings.keyBindSprint.isKeyDown()) {
/* 39 */         (mc.player.getRidingEntity()).onGround = false;
/* 40 */         (mc.player.getRidingEntity()).motionY = -(((Double)this.speed.getValue()).doubleValue() / 10.0D);
/*    */       } 
/*    */       
/* 43 */       double[] normalDir = directionSpeed(((Double)this.speed.getValue()).doubleValue() / 2.0D);
/* 44 */       if (mc.player.movementInput.moveStrafe != 0.0F || mc.player.movementInput.moveForward != 0.0F) {
/* 45 */         (mc.player.getRidingEntity()).motionX = normalDir[0];
/* 46 */         (mc.player.getRidingEntity()).motionZ = normalDir[1];
/*    */       } else {
/* 48 */         (mc.player.getRidingEntity()).motionX = 0.0D;
/* 49 */         (mc.player.getRidingEntity()).motionZ = 0.0D;
/*    */       } 
/* 51 */       if (((Boolean)this.glide.getValue()).booleanValue()) {
/* 52 */         if (mc.gameSettings.keyBindJump.isKeyDown()) {
/* 53 */           if (mc.player.ticksExisted % 8 < 2) {
/* 54 */             (mc.player.getRidingEntity()).motionY = -0.03999999910593033D;
/*    */           }
/* 56 */         } else if (mc.player.ticksExisted % 8 < 4) {
/* 57 */           (mc.player.getRidingEntity()).motionY = -0.03999999910593033D;
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private double[] directionSpeed(double speed) {
/* 64 */     float forward = mc.player.movementInput.moveForward;
/* 65 */     float side = mc.player.movementInput.moveStrafe;
/* 66 */     float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
/* 67 */     if (forward != 0.0F) {
/* 68 */       if (side > 0.0F) {
/* 69 */         yaw += ((forward > 0.0F) ? -45 : 45);
/* 70 */       } else if (side < 0.0F) {
/* 71 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*    */       } 
/* 73 */       side = 0.0F;
/* 74 */       if (forward > 0.0F) {
/* 75 */         forward = 1.0F;
/* 76 */       } else if (forward < 0.0F) {
/* 77 */         forward = -1.0F;
/*    */       } 
/*    */     } 
/* 80 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 81 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 82 */     double posX = forward * speed * cos + side * speed * sin;
/* 83 */     double posZ = forward * speed * sin - side * speed * cos;
/* 84 */     return new double[] { posX, posZ };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\BoatFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
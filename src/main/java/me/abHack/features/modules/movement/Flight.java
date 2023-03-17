/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public final class Flight extends Module {
/*  7 */   private final Setting<Float> speed = register(new Setting("Speed", Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(10.0F)));
/*  8 */   private final Setting<Boolean> glide = register(new Setting("Glide", Boolean.valueOf(true)));
/*    */   
/*    */   public Flight() {
/* 11 */     super("Flight", "Allows you to fly.", Module.Category.MOVEMENT, false, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 16 */     if (mc.player == null || mc.world == null) {
/*    */       return;
/*    */     }
/* 19 */     mc.player.capabilities.isFlying = false;
/* 20 */     mc.player.motionX = 0.0D;
/* 21 */     mc.player.motionY = 0.0D;
/* 22 */     mc.player.motionZ = 0.0D;
/* 23 */     mc.player.jumpMovementFactor = ((Float)this.speed.getValue()).floatValue();
/* 24 */     if (((Boolean)this.glide.getValue()).booleanValue() && !mc.player.onGround) {
/* 25 */       mc.player.motionY = -0.03150000050663948D;
/* 26 */       mc.player.jumpMovementFactor *= 1.21337F;
/*    */     } 
/*    */     
/* 29 */     if (mc.gameSettings.keyBindJump.isKeyDown())
/* 30 */       mc.player.motionY += ((Float)this.speed.getValue()).floatValue(); 
/* 31 */     if (mc.gameSettings.keyBindSneak.isKeyDown())
/* 32 */       mc.player.motionY -= ((Float)this.speed.getValue()).floatValue(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\Flight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
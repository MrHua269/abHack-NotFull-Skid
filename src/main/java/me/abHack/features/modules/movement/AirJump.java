/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class AirJump
/*    */   extends Module {
/*  9 */   private final Setting<Float> speed = register(new Setting("Speed", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(20.0F)));
/* 10 */   private final Setting<Float> movementspeed = register(new Setting("MoveSpeed", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(20.0F)));
/*    */   
/*    */   private boolean owo = false;
/*    */   
/*    */   public AirJump() {
/* 15 */     super("AirJump", "AirJump.", Module.Category.MOVEMENT, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 20 */     if (mc.player == null)
/*    */       return; 
/* 22 */     if (shouldReturn())
/*    */       return; 
/* 24 */     if (mc.player.capabilities.isCreativeMode)
/*    */       return; 
/* 26 */     mc.player.capabilities.isFlying = false;
/* 27 */     mc.player.jumpMovementFactor = ((Float)this.movementspeed.getValue()).floatValue() / 100.0F;
/* 28 */     if (mc.gameSettings.keyBindJump.isKeyDown()) {
/* 29 */       if (!this.owo) {
/* 30 */         mc.player.motionY = (((Float)this.speed.getValue()).floatValue() / 10.0F);
/* 31 */         this.owo = true;
/*    */       } 
/* 33 */     } else if (!mc.gameSettings.keyBindJump.isKeyDown()) {
/* 34 */       this.owo = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean shouldReturn() {
/* 39 */     return (OyVey.moduleManager.isModuleEnabled("ElytraFlight") || OyVey.moduleManager.isModuleEnabled("Speed") || OyVey.moduleManager.isModuleEnabled("Phase") || OyVey.moduleManager.isModuleEnabled("Flight") || OyVey.moduleManager.isModuleEnabled("Strafe"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\AirJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ 
/*    */ 
/*    */ public class Phase
/*    */   extends Module
/*    */ {
/* 12 */   private final Setting<Double> speed = register(new Setting("Speed", Double.valueOf(5.0D), Double.valueOf(0.0D), Double.valueOf(50.0D)));
/*    */   
/*    */   public Phase() {
/* 15 */     super("Phase", "Phase.", Module.Category.MOVEMENT, true, false, false);
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 19 */     mc.player.capabilities.isFlying = true;
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 23 */     mc.player.capabilities.isFlying = false;
/* 24 */     mc.player.noClip = false;
/*    */   }
/*    */   
/*    */   private boolean shouldReturn() {
/* 28 */     return (OyVey.moduleManager.isModuleEnabled("ElytraFlight") || OyVey.moduleManager.isModuleEnabled("Flight") || OyVey.moduleManager.isModuleEnabled("Strafe") || OyVey.moduleManager.isModuleEnabled("Speed"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 33 */     if (shouldReturn()) {
/* 34 */       mc.player.capabilities.isFlying = false;
/* 35 */       mc.player.noClip = false;
/*    */     } else {
/* 37 */       mc.player.motionY = 0.0D;
/* 38 */       mc.player.noClip = true;
/* 39 */       mc.player.capabilities.isFlying = true;
/* 40 */       mc.player.onGround = false;
/* 41 */       mc.player.fallDistance = 0.0F;
/* 42 */       mc.player.capabilities.setFlySpeed((float)(((Double)this.speed.getValue()).doubleValue() / 100.0D));
/* 43 */       if (mc.gameSettings.keyBindJump.isPressed()) {
/* 44 */         EntityPlayerSP thePlayer = mc.player;
/* 45 */         thePlayer.motionY += 0.010000000149011612D;
/*    */       } 
/* 47 */       if (mc.gameSettings.keyBindSneak.isPressed()) {
/* 48 */         EntityPlayerSP thePlayer2 = mc.player;
/* 49 */         thePlayer2.motionY -= 0.010000000149011612D;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\Phase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
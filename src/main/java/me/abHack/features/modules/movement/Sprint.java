/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.event.events.MoveEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class Sprint
/*    */   extends Module {
/* 10 */   private static Sprint INSTANCE = new Sprint();
/* 11 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.LEGIT));
/*    */   
/*    */   public Sprint() {
/* 14 */     super("Sprint", "Modifies sprinting", Module.Category.MOVEMENT, false, false, false);
/* 15 */     setInstance();
/*    */   }
/*    */   
/*    */   public static Sprint getInstance() {
/* 19 */     if (INSTANCE == null) {
/* 20 */       INSTANCE = new Sprint();
/*    */     }
/* 22 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 26 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onSprint(MoveEvent event) {
/* 31 */     if (event.getStage() == 1 && this.mode.getValue() == Mode.RAGE && (mc.player.movementInput.moveForward != 0.0F || mc.player.movementInput.moveStrafe != 0.0F)) {
/* 32 */       event.setCanceled(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 38 */     switch ((Mode)this.mode.getValue()) {
/*    */       case RAGE:
/* 40 */         if ((!mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown()) || mc.player.isSneaking() || mc.player.collidedHorizontally || mc.player.getFoodStats().getFoodLevel() <= 6.0F)
/*    */           break; 
/* 42 */         mc.player.setSprinting(true);
/*    */         break;
/*    */       
/*    */       case LEGIT:
/* 46 */         if (!mc.gameSettings.keyBindForward.isKeyDown() || mc.player.isSneaking() || mc.player.isHandActive() || mc.player.collidedHorizontally || mc.player.getFoodStats().getFoodLevel() <= 6.0F || mc.currentScreen != null)
/*    */           break; 
/* 48 */         mc.player.setSprinting(true);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 55 */     if (!nullCheck()) {
/* 56 */       mc.player.setSprinting(false);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayInfo() {
/* 62 */     return this.mode.currentEnumName();
/*    */   }
/*    */   
/*    */   public enum Mode
/*    */   {
/* 67 */     LEGIT,
/* 68 */     RAGE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\Sprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
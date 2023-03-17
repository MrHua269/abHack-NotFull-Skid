/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ReverseStep extends Module {
/*  9 */   private static ReverseStep INSTANCE = new ReverseStep();
/* 10 */   private final Setting<Integer> speed = register(new Setting("Speed", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(20)));
/* 11 */   private final Setting<Boolean> inliquid = register(new Setting("Liquid", Boolean.valueOf(false)));
/* 12 */   private final Setting<Cancel> canceller = register(new Setting("CancelType", Cancel.None));
/*    */   
/*    */   public ReverseStep() {
/* 15 */     super("ReverseStep", "Rapid decline", Module.Category.MOVEMENT, true, false, false);
/* 16 */     setInstance();
/*    */   }
/*    */   
/*    */   public static ReverseStep getInstance() {
/* 20 */     if (INSTANCE == null) {
/* 21 */       INSTANCE = new ReverseStep();
/*    */     }
/* 23 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 27 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 32 */     if (nullCheck()) {
/*    */       return;
/*    */     }
/* 35 */     if (mc.player.isSneaking() || mc.player.isDead || mc.player.collidedHorizontally || !mc.player.onGround || (mc.player.isInWater() && !((Boolean)this.inliquid.getValue()).booleanValue()) || (mc.player.isInLava() && !((Boolean)this.inliquid.getValue()).booleanValue()) || mc.player.isOnLadder() || mc.gameSettings.keyBindJump.isKeyDown() || OyVey.moduleManager.isModuleEnabled("Burrow") || mc.player.noClip || OyVey.moduleManager.isModuleEnabled("Packetfly") || OyVey.moduleManager.isModuleEnabled("Phase") || (mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue() == Cancel.Shift) || (mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue() == Cancel.Both) || (mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue() == Cancel.Space) || (mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue() == Cancel.Both) || OyVey.moduleManager.isModuleEnabled("Strafe")) {
/*    */       return;
/*    */     }
/* 38 */     for (double y = 0.0D; y < 90.5D; ) {
/* 39 */       if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(0.0D, -y, 0.0D)).isEmpty()) {
/*    */         y += 0.01D; continue;
/* 41 */       }  mc.player.motionY = (-((Integer)this.speed.getValue()).intValue() / 10.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public enum Cancel
/*    */   {
/* 48 */     None,
/* 49 */     Space,
/* 50 */     Shift,
/* 51 */     Both;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\ReverseStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
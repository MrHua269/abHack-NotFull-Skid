/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.init.MobEffects;
/*    */ 
/*    */ public class AntiEffects extends Module {
/*  8 */   private final Setting<Boolean> levitation = register(new Setting("Levitation", Boolean.valueOf(true)));
/*  9 */   private final Setting<Boolean> jumpBoost = register(new Setting("JumpBoost", Boolean.valueOf(false)));
/*    */   
/*    */   public AntiEffects() {
/* 12 */     super("AntiEffects", "Removes unwanted effects from the player", Module.Category.MOVEMENT, true, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 18 */     if (nullCheck())
/*    */       return; 
/* 20 */     if (((Boolean)this.levitation.getValue()).booleanValue() && mc.player.isPotionActive(MobEffects.LEVITATION)) {
/* 21 */       mc.player.removeActivePotionEffect(MobEffects.LEVITATION);
/*    */     }
/* 23 */     if (((Boolean)this.jumpBoost.getValue()).booleanValue() && mc.player.isPotionActive(MobEffects.JUMP_BOOST))
/* 24 */       mc.player.removeActivePotionEffect(MobEffects.JUMP_BOOST); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\AntiEffects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
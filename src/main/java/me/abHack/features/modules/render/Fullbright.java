/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class Fullbright extends Module {
/* 12 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.GAMMA));
/*    */   
/* 14 */   public Setting<Boolean> effects = register(new Setting("Effects", Boolean.valueOf(false)));
/*    */   
/* 16 */   private float previousSetting = 1.0F;
/*    */   
/*    */   public Fullbright() {
/* 19 */     super("Fullbright", "Makes your game brighter.", Module.Category.RENDER, true, false, false);
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 23 */     this.previousSetting = mc.gameSettings.gammaSetting;
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 27 */     if (this.mode.getValue() == Mode.GAMMA)
/* 28 */       mc.gameSettings.gammaSetting = 1000.0F; 
/* 29 */     if (this.mode.getValue() == Mode.POTION)
/* 30 */       mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210)); 
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 34 */     if (this.mode.getValue() == Mode.POTION)
/* 35 */       mc.player.removePotionEffect(MobEffects.NIGHT_VISION); 
/* 36 */     mc.gameSettings.gammaSetting = this.previousSetting;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 41 */     if (event.getStage() == 0 && event.getPacket() instanceof SPacketEntityEffect && ((Boolean)this.effects.getValue()).booleanValue()) {
/* 42 */       SPacketEntityEffect packet = (SPacketEntityEffect)event.getPacket();
/* 43 */       if (mc.player != null && packet.getEntityId() == mc.player.getEntityId() && (packet.getEffectId() == 9 || packet.getEffectId() == 15))
/* 44 */         event.setCanceled(true); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum Mode {
/* 49 */     GAMMA, POTION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Fullbright.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
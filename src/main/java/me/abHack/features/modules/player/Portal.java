/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraftforge.client.GuiIngameForge;
/*    */ import net.minecraftforge.client.event.sound.PlaySoundEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class Portal
/*    */   extends Module
/*    */ {
/* 14 */   public Setting<Boolean> godMode = register(new Setting("GodMode", Boolean.valueOf(false), "Cancels teleport packets"));
/*    */   
/* 16 */   public Setting<Boolean> screens = register(new Setting("Screens", Boolean.valueOf(true), "Allow the use of screens in portals"));
/*    */   
/* 18 */   public Setting<Boolean> effect = register(new Setting("Effect", Boolean.valueOf(true), "Cancels the portal overlay effect"));
/*    */   
/* 20 */   public Setting<Boolean> sounds = register(new Setting("Sounds", Boolean.valueOf(false), "Cancels portal sounds"));
/*    */   
/*    */   public Portal() {
/* 23 */     super("Portal", "Modifies portal behavior.", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 30 */     if (mc.player.inPortal && ((Boolean)this.screens.getValue()).booleanValue()) {
/* 31 */       mc.player.inPortal = false;
/*    */     }
/* 33 */     GuiIngameForge.renderPortal = !((Boolean)this.effect.getValue()).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 38 */     super.onDisable();
/*    */ 
/*    */     
/* 41 */     GuiIngameForge.renderPortal = true;
/*    */   }
/*    */   
/*    */   public void onLogin() {
/* 45 */     this.godMode.setValue(Boolean.valueOf(false));
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onSound(PlaySoundEvent event) {
/* 50 */     if (((Boolean)this.sounds.getValue()).booleanValue())
/*    */     {
/*    */       
/* 53 */       if (event.getName().equals("block.portal.ambient") || event.getName().equals("block.portal.travel") || event.getName().equals("block.portal.trigger")) {
/* 54 */         event.setResultSound(null);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 63 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketConfirmTeleport)
/*    */     {
/*    */       
/* 66 */       if (((Boolean)this.godMode.getValue()).booleanValue())
/* 67 */         event.setCanceled(true); 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Portal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
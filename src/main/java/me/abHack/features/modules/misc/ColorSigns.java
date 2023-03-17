/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.mixin.mixins.ICPacketUpdateSign;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class ColorSigns
/*    */   extends Module
/*    */ {
/* 12 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.SPIGOT));
/*    */   
/*    */   public ColorSigns() {
/* 15 */     super("ColorSigns", "Lets you use color codes on signs", Module.Category.MISC, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 20 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketUpdateSign) {
/*    */       
/* 22 */       int i = 0;
/* 23 */       while (i < 4) {
/* 24 */         if (((ICPacketUpdateSign)event.getPacket()).getLines()[i] != null) {
/* 25 */           switch ((Mode)this.mode.getValue()) {
/*    */             case VANILLA:
/* 27 */               ((ICPacketUpdateSign)event.getPacket()).getLines()[i] = ((ICPacketUpdateSign)event.getPacket()).getLines()[i].replace("&", "§§0");
/*    */               break;
/*    */             
/*    */             case SPIGOT:
/* 31 */               ((ICPacketUpdateSign)event.getPacket()).getLines()[i] = ((ICPacketUpdateSign)event.getPacket()).getLines()[i].replace("&", "§§§00");
/*    */               break;
/*    */           } 
/*    */         
/*    */         }
/* 36 */         i++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private enum Mode
/*    */   {
/* 43 */     VANILLA, SPIGOT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\ColorSigns.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
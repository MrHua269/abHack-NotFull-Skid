/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class ChatSuffix
/*    */   extends Module
/*    */ {
/*    */   public ChatSuffix() {
/* 12 */     super("ChatSuffix", "suffix", Module.Category.MISC, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 17 */     if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage) {
/* 18 */       CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
/* 19 */       String message = packet.getMessage();
/* 20 */       if (message.startsWith("/") || message.startsWith(".") || message.startsWith(",") || message.startsWith("-") || message.startsWith("$") || message.startsWith("*"))
/*    */         return; 
/* 22 */       String abHackChat = message + " | ☭ ᴀʙʜᴀᴄᴋ ☭";
/* 23 */       if (abHackChat.length() >= 256)
/* 24 */         abHackChat = abHackChat.substring(0, 256); 
/* 25 */       packet.message = abHackChat;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\ChatSuffix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.manager;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.Feature;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class ReloadManager
/*    */   extends Feature {
/*    */   public String prefix;
/*    */   
/*    */   public void init(String prefix) {
/* 17 */     this.prefix = prefix;
/* 18 */     MinecraftForge.EVENT_BUS.register(this);
/* 19 */     if (!fullNullCheck()) {
/* 20 */       Command.sendMessage(ChatFormatting.RED + "OyVey has been unloaded. Type " + prefix + "reload to reload.");
/*    */     }
/*    */   }
/*    */   
/*    */   public void unload() {
/* 25 */     MinecraftForge.EVENT_BUS.unregister(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/*    */     CPacketChatMessage packet;
/* 31 */     if (event.getPacket() instanceof CPacketChatMessage && (packet = (CPacketChatMessage)event.getPacket()).getMessage().startsWith(this.prefix) && packet.getMessage().contains("reload")) {
/* 32 */       OyVey.load();
/* 33 */       event.setCanceled(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\ReloadManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class XCarry extends Module {
/*  9 */   private static XCarry INSTANCE = new XCarry();
/*    */   
/*    */   public XCarry() {
/* 12 */     super("XCarry", "Uses the crafting inventory for storage", Module.Category.PLAYER, true, false, false);
/* 13 */     setInstance();
/*    */   }
/*    */   
/*    */   public static XCarry getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new XCarry();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 24 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSent(PacketEvent.Send event) {
/* 29 */     if (event.getPacket() instanceof CPacketCloseWindow) {
/* 30 */       CPacketCloseWindow packet = (CPacketCloseWindow)event.getPacket();
/* 31 */       if (packet.windowId == 0)
/* 32 */         event.setCanceled(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\XCarry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
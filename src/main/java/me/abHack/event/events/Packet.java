/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class Packet
/*    */   extends Event {
/*    */   private Object packet;
/*    */   private Type type;
/*    */   
/*    */   public Packet(Object packet, Type type) {
/* 13 */     this.packet = packet;
/* 14 */     this.type = type;
/*    */   }
/*    */   
/*    */   public Object getPacket() {
/* 18 */     return this.packet;
/*    */   }
/*    */   
/*    */   public void setPacket(Object packet) {
/* 22 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public Type getType() {
/* 26 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(Type type) {
/* 30 */     this.type = type;
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 35 */     INCOMING,
/* 36 */     OUTGOING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\Packet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
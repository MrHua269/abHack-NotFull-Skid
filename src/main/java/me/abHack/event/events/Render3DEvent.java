/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import me.abHack.event.EventStage;
/*    */ 
/*    */ public class Render3DEvent
/*    */   extends EventStage {
/*    */   private final float partialTicks;
/*    */   
/*    */   public Render3DEvent(float partialTicks) {
/* 10 */     this.partialTicks = partialTicks;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 14 */     return this.partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\Render3DEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
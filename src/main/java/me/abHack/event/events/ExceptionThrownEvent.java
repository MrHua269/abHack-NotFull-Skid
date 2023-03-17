/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class ExceptionThrownEvent
/*    */   extends Event
/*    */ {
/*    */   private final Throwable exception;
/*    */   
/*    */   public ExceptionThrownEvent(Throwable exception) {
/* 14 */     this.exception = exception;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getException() {
/* 23 */     return this.exception;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\ExceptionThrownEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
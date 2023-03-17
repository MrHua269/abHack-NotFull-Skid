/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import me.abHack.event.EventStage;
/*    */ 
/*    */ public class KeyEvent
/*    */   extends EventStage {
/*    */   private final int key;
/*    */   
/*    */   public KeyEvent(int key) {
/* 10 */     this.key = key;
/*    */   }
/*    */   
/*    */   public int getKey() {
/* 14 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\KeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
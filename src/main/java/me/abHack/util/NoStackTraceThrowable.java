/*    */ package me.abHack.util;
/*    */ 
/*    */ public class NoStackTraceThrowable
/*    */   extends RuntimeException {
/*    */   public NoStackTraceThrowable(String msg) {
/*  6 */     super(msg);
/*  7 */     setStackTrace(new StackTraceElement[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 12 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\NoStackTraceThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
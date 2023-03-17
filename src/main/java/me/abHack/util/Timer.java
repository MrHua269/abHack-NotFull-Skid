/*    */ package me.abHack.util;
/*    */ 
/*    */ public class Timer {
/*  4 */   private long time = -1L;
/*    */   
/*    */   public boolean passedS(double s) {
/*  7 */     return (getMs(System.nanoTime() - this.time) >= (long)(s * 1000.0D));
/*    */   }
/*    */   
/*    */   public boolean passedM(double m) {
/* 11 */     return (getMs(System.nanoTime() - this.time) >= (long)(m * 1000.0D * 60.0D));
/*    */   }
/*    */   
/*    */   public boolean passedDms(double dms) {
/* 15 */     return (getMs(System.nanoTime() - this.time) >= (long)(dms * 10.0D));
/*    */   }
/*    */   
/*    */   public boolean passedDs(double ds) {
/* 19 */     return (getMs(System.nanoTime() - this.time) >= (long)(ds * 100.0D));
/*    */   }
/*    */   
/*    */   public boolean passedMs(long ms) {
/* 23 */     return (getMs(System.nanoTime() - this.time) >= ms);
/*    */   }
/*    */   
/*    */   public boolean passedNS(long ns) {
/* 27 */     return (System.nanoTime() - this.time >= ns);
/*    */   }
/*    */   
/*    */   public void setMs(long ms) {
/* 31 */     this.time = System.nanoTime() - ms * 1000000L;
/*    */   }
/*    */   
/*    */   public long getPassedTimeMs() {
/* 35 */     return getMs(System.nanoTime() - this.time);
/*    */   }
/*    */   
/*    */   public Timer reset() {
/* 39 */     this.time = System.nanoTime();
/* 40 */     return this;
/*    */   }
/*    */   
/*    */   public long getMs(long time) {
/* 44 */     return time / 1000000L;
/*    */   }
/*    */   
/*    */   public boolean sleep(long time) {
/* 48 */     if (System.nanoTime() / 1000000L - time >= time) {
/* 49 */       reset();
/* 50 */       return true;
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
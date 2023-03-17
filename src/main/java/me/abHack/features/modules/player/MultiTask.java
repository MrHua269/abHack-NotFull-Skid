/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ 
/*    */ public class MultiTask
/*    */   extends Module {
/*  7 */   private static MultiTask INSTANCE = new MultiTask();
/*    */   
/*    */   public MultiTask() {
/* 10 */     super("MultiTask", "Allows you to eat while mining.", Module.Category.PLAYER, false, false, false);
/* 11 */     setInstance();
/*    */   }
/*    */   
/*    */   public static MultiTask getInstance() {
/* 15 */     if (INSTANCE == null) {
/* 16 */       INSTANCE = new MultiTask();
/*    */     }
/* 18 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 22 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\MultiTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
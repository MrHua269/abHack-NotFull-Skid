/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ 
/*    */ public final class NoWeb
/*    */   extends Module
/*    */ {
/*    */   public NoWeb() {
/*  9 */     super("NoWeb", "Prevents you from getting slowed down in webs.", Module.Category.MOVEMENT, true, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 15 */     if (mc.player.isInWeb)
/* 16 */       mc.player.isInWeb = false; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\NoWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
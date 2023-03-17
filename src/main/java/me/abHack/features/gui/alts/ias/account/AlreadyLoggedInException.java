/*    */ package me.abHack.features.gui.alts.ias.account;
/*    */ 
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class AlreadyLoggedInException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -7572892045698003265L;
/*    */   
/*    */   public String getLocalizedMessage() {
/* 11 */     return I18n.format("ias.alreadyloggedin", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\account\AlreadyLoggedInException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
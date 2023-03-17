/*    */ package me.abHack.features.gui.alts.ias.gui;
/*    */ 
/*    */ import me.abHack.features.gui.alts.ias.account.ExtendedAccountData;
/*    */ import me.abHack.features.gui.alts.tools.alt.AltDatabase;
/*    */ 
/*    */ public class GuiAddAccount
/*    */   extends AbstractAccountGui {
/*    */   public GuiAddAccount() {
/*  9 */     super("ias.addaccount");
/*    */   }
/*    */ 
/*    */   
/*    */   public void complete() {
/* 14 */     AltDatabase.getInstance().getAlts().add(new ExtendedAccountData(getUsername(), getPassword(), getUsername()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\gui\GuiAddAccount.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
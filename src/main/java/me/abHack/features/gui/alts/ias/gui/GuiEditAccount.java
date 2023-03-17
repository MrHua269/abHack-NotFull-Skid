/*    */ package me.abHack.features.gui.alts.ias.gui;
/*    */ 
/*    */ import me.abHack.features.gui.alts.ias.account.ExtendedAccountData;
/*    */ import me.abHack.features.gui.alts.ias.enums.EnumBool;
/*    */ import me.abHack.features.gui.alts.ias.tools.JavaTools;
/*    */ import me.abHack.features.gui.alts.iasencrypt.EncryptionTools;
/*    */ import me.abHack.features.gui.alts.tools.alt.AccountData;
/*    */ import me.abHack.features.gui.alts.tools.alt.AltDatabase;
/*    */ 
/*    */ class GuiEditAccount
/*    */   extends AbstractAccountGui {
/*    */   private final ExtendedAccountData data;
/*    */   private final int selectedIndex;
/*    */   
/*    */   public GuiEditAccount(int index) {
/* 16 */     super("ias.editaccount");
/* 17 */     this.selectedIndex = index;
/* 18 */     AccountData data = AltDatabase.getInstance().getAlts().get(index);
/* 19 */     this.data = (data instanceof ExtendedAccountData) ? (ExtendedAccountData)data : new ExtendedAccountData(data.user, data.pass, data.alias, 0, JavaTools.getJavaCompat().getDate(), EnumBool.UNKNOWN);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 24 */     super.initGui();
/* 25 */     setUsername(EncryptionTools.decode(this.data.user));
/* 26 */     setPassword(EncryptionTools.decode(this.data.pass));
/*    */   }
/*    */ 
/*    */   
/*    */   public void complete() {
/* 31 */     AltDatabase.getInstance().getAlts().set(this.selectedIndex, new ExtendedAccountData(getUsername(), getPassword(), this.hasUserChanged ? getUsername() : this.data.alias, this.data.useCount, this.data.lastused, this.data.premium));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\gui\GuiEditAccount.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
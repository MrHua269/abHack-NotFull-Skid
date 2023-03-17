/*    */ package me.abHack.features.gui.alts.tools.alt;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import me.abHack.features.gui.alts.iasencrypt.EncryptionTools;
/*    */ 
/*    */ public class AccountData
/*    */   implements Serializable
/*    */ {
/*    */   public static final long serialVersionUID = -147985492L;
/*    */   public final String user;
/*    */   public final String pass;
/*    */   public String alias;
/*    */   
/*    */   protected AccountData(String user, String pass, String alias) {
/* 15 */     this.user = EncryptionTools.encode(user);
/* 16 */     this.pass = EncryptionTools.encode(pass);
/* 17 */     this.alias = alias;
/*    */   }
/*    */   
/*    */   public boolean equalsBasic(AccountData obj) {
/* 21 */     if (this == obj) {
/* 22 */       return true;
/*    */     }
/* 24 */     if (obj == null) {
/* 25 */       return false;
/*    */     }
/* 27 */     if (getClass() != obj.getClass()) {
/* 28 */       return false;
/*    */     }
/* 30 */     return this.user.equals(obj.user);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\tools\alt\AccountData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
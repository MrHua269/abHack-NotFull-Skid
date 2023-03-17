/*    */ package me.abHack.features.gui.alts.ias.account;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import me.abHack.features.gui.alts.ias.enums.EnumBool;
/*    */ import me.abHack.features.gui.alts.ias.tools.JavaTools;
/*    */ import me.abHack.features.gui.alts.tools.alt.AccountData;
/*    */ 
/*    */ public class ExtendedAccountData
/*    */   extends AccountData
/*    */ {
/*    */   private static final long serialVersionUID = -909128662161235160L;
/*    */   public EnumBool premium;
/*    */   public int[] lastused;
/*    */   public int useCount;
/*    */   
/*    */   public ExtendedAccountData(String user, String pass, String alias) {
/* 17 */     super(user, pass, alias);
/* 18 */     this.useCount = 0;
/* 19 */     this.lastused = JavaTools.getJavaCompat().getDate();
/* 20 */     this.premium = EnumBool.UNKNOWN;
/*    */   }
/*    */   
/*    */   public ExtendedAccountData(String user, String pass, String alias, int useCount, int[] lastused, EnumBool premium) {
/* 24 */     super(user, pass, alias);
/* 25 */     this.useCount = useCount;
/* 26 */     this.lastused = lastused;
/* 27 */     this.premium = premium;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 31 */     if (this == obj) {
/* 32 */       return true;
/*    */     }
/* 34 */     if (obj == null) {
/* 35 */       return false;
/*    */     }
/* 37 */     if (getClass() != obj.getClass()) {
/* 38 */       return false;
/*    */     }
/* 40 */     ExtendedAccountData other = (ExtendedAccountData)obj;
/* 41 */     if (!Arrays.equals(this.lastused, other.lastused)) {
/* 42 */       return false;
/*    */     }
/* 44 */     if (this.premium != other.premium) {
/* 45 */       return false;
/*    */     }
/* 47 */     if (this.useCount != other.useCount) {
/* 48 */       return false;
/*    */     }
/* 50 */     return (this.user.equals(other.user) && this.pass.equals(other.pass));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\account\ExtendedAccountData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.gui.alts.ias.tools;
/*    */ 
/*    */ import me.abHack.features.gui.alts.ias.legacysupport.ILegacyCompat;
/*    */ import me.abHack.features.gui.alts.ias.legacysupport.NewJava;
/*    */ import me.abHack.features.gui.alts.ias.legacysupport.OldJava;
/*    */ 
/*    */ public class JavaTools {
/*    */   private static double getJavaVersion() {
/*  9 */     String version = System.getProperty("java.version");
/* 10 */     int pos = version.indexOf('.');
/* 11 */     pos = version.indexOf('.', pos + 1);
/* 12 */     return Double.parseDouble(version.substring(0, pos));
/*    */   }
/*    */   
/*    */   public static ILegacyCompat getJavaCompat() {
/* 16 */     if (getJavaVersion() >= 1.8D) {
/* 17 */       return (ILegacyCompat)new NewJava();
/*    */     }
/* 19 */     return (ILegacyCompat)new OldJava();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\tools\JavaTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
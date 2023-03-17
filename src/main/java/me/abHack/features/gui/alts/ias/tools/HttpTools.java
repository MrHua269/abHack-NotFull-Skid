/*    */ package me.abHack.features.gui.alts.ias.tools;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ 
/*    */ public class HttpTools {
/*    */   public static boolean ping(String url) {
/*    */     try {
/* 10 */       URLConnection con = (new URL(url)).openConnection();
/* 11 */       con.connect();
/* 12 */       return true;
/* 13 */     } catch (IOException e) {
/* 14 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\tools\HttpTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
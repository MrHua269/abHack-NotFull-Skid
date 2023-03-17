/*    */ package me.abHack.manager;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.abHack.util.DisplayUtil;
/*    */ import me.abHack.util.NoStackTraceThrowable;
/*    */ import me.abHack.util.SystemUtil;
/*    */ import me.abHack.util.VerificationUtil;
/*    */ 
/*    */ 
/*    */ public class VerificationManager
/*    */ {
/*    */   public static final String checkURL = "https://pastebin.com/raw/XmqVc8S8";
/* 14 */   public static List<String> hwids = new ArrayList<>();
/*    */   
/*    */   public static void hwidCheck() {
/* 17 */     hwids = VerificationUtil.readURL();
/* 18 */     boolean isHwidPresent = hwids.contains(SystemUtil.getSystemInfo());
/* 19 */     boolean All = hwids.contains(SystemUtil.getAll());
/* 20 */     if (!isHwidPresent && !All) {
/* 21 */       DisplayUtil.Display();
/* 22 */       throw new NoStackTraceThrowable("");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\VerificationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
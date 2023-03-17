/*    */ package me.abHack.util.render;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.util.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class HudUtil implements Util {
/*    */   public static String getPingSatus() {
/*  9 */     String line = "";
/* 10 */     int ping = OyVey.serverManager.getPing();
/* 11 */     return line + " " + ping;
/*    */   }
/*    */   
/*    */   public static String getTpsStatus() {
/* 15 */     String line = "";
/* 16 */     double tps = Math.ceil(OyVey.serverManager.getTPS());
/* 17 */     return line + " " + tps;
/*    */   }
/*    */   
/*    */   public static String getFpsStatus() {
/* 21 */     String line = "";
/* 22 */     int fps = Minecraft.getDebugFPS();
/* 23 */     return line + " " + fps;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\HudUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
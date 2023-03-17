/*    */ package me.abHack.manager;
/*    */ 
/*    */ import me.abHack.features.Feature;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class TextManager
/*    */   extends Feature {
/*  9 */   private final Timer idleTimer = new Timer();
/*    */   public int scaledWidth;
/*    */   public int scaledHeight;
/*    */   public int scaleFactor;
/*    */   private boolean idling;
/*    */   
/*    */   public TextManager() {
/* 16 */     updateResolution();
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawStringWithShadow(String text, float x, float y, int color) {
/* 21 */     drawString(text, x, y, color, true);
/*    */   }
/*    */   
/*    */   public void drawString(String text, float x, float y, int color, boolean shadow) {
/* 25 */     mc.fontRenderer.drawString(text, x, y, color, shadow);
/*    */   }
/*    */   
/*    */   public int getStringWidth(String text) {
/* 29 */     return mc.fontRenderer.getStringWidth(text);
/*    */   }
/*    */   
/*    */   public int getFontHeight() {
/* 33 */     return mc.fontRenderer.FONT_HEIGHT;
/*    */   }
/*    */   
/*    */   public void updateResolution() {
/* 37 */     this.scaledWidth = mc.displayWidth;
/* 38 */     this.scaledHeight = mc.displayHeight;
/* 39 */     this.scaleFactor = 1;
/* 40 */     boolean flag = mc.isUnicode();
/* 41 */     int i = mc.gameSettings.guiScale;
/* 42 */     if (i == 0) {
/* 43 */       i = 1000;
/*    */     }
/* 45 */     while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
/* 46 */       this.scaleFactor++;
/*    */     }
/* 48 */     if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
/* 49 */       this.scaleFactor--;
/*    */     }
/* 51 */     double scaledWidthD = (this.scaledWidth / this.scaleFactor);
/* 52 */     double scaledHeightD = (this.scaledHeight / this.scaleFactor);
/* 53 */     this.scaledWidth = MathHelper.ceil(scaledWidthD);
/* 54 */     this.scaledHeight = MathHelper.ceil(scaledHeightD);
/*    */   }
/*    */   
/*    */   public String getIdleSign() {
/* 58 */     if (this.idleTimer.passedMs(500L)) {
/* 59 */       this.idling = !this.idling;
/* 60 */       this.idleTimer.reset();
/*    */     } 
/* 62 */     if (this.idling) {
/* 63 */       return "_";
/*    */     }
/* 65 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\TextManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
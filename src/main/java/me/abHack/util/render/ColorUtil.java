/*    */ package me.abHack.util.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.features.modules.client.Colors;
/*    */ 
/*    */ 
/*    */ public class ColorUtil
/*    */ {
/*    */   public static int toRGBA(int r, int g, int b) {
/* 11 */     return toRGBA(r, g, b, 255);
/*    */   }
/*    */   
/*    */   public static int toRGBA(int r, int g, int b, int a) {
/* 15 */     return (r << 16) + (g << 8) + b + (a << 24);
/*    */   }
/*    */   
/*    */   public static Color rainbow(int delay) {
/* 19 */     double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0D);
/* 20 */     return Color.getHSBColor((float)(rainbowState % 360.0D / 360.0D), ((Float)ClickGui.INSTANCE.rainbowSaturation.getValue()).floatValue() / 255.0F, ((Float)ClickGui.INSTANCE.rainbowBrightness.getValue()).floatValue() / 255.0F);
/*    */   }
/*    */   
/*    */   public static int toRGBA(Color color) {
/* 24 */     return toRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*    */   }
/*    */   
/*    */   public static Color getPrimaryColor(int offset) {
/* 28 */     switch ((Colors.Rainbow)Colors.INSTANCE.rainbow.getValue()) {
/*    */       case GRADIENT:
/* 30 */         return rainbow(offset, ((Integer)Colors.INSTANCE.alpha.getValue()).intValue());
/*    */       case STATIC:
/* 32 */         return rainbow(1L, ((Integer)Colors.INSTANCE.alpha.getValue()).intValue());
/*    */       case ALPHA:
/* 34 */         return alphaCycle(Colors.INSTANCE.getColor(), offset * 2 + 10);
/*    */     } 
/*    */     
/* 37 */     return Colors.INSTANCE.getColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Color getPrimaryColor() {
/* 42 */     switch ((Colors.Rainbow)Colors.INSTANCE.rainbow.getValue()) {
/*    */       case GRADIENT:
/*    */       case STATIC:
/* 45 */         return rainbow(1L, ((Integer)Colors.INSTANCE.alpha.getValue()).intValue());
/*    */       case ALPHA:
/* 47 */         return alphaCycle(Colors.INSTANCE.getColor(), 10);
/*    */     } 
/*    */     
/* 50 */     return Colors.INSTANCE.getColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Color getPrimaryAlphaColor(int alpha) {
/* 55 */     return new Color(getPrimaryColor().getRed(), getPrimaryColor().getGreen(), getPrimaryColor().getBlue(), alpha);
/*    */   }
/*    */   
/*    */   public static Color alphaCycle(Color clientColor, int count) {
/* 59 */     float[] hsb = new float[3];
/* 60 */     Color.RGBtoHSB(clientColor.getRed(), clientColor.getGreen(), clientColor.getBlue(), hsb);
/* 61 */     float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + 50.0F / count * 2.0F) % 2.0F - 1.0F);
/* 62 */     brightness = 0.5F + 0.5F * brightness;
/* 63 */     hsb[2] = brightness % 2.0F;
/* 64 */     return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
/*    */   }
/*    */   
/*    */   public static Color rainbow(long offset, int alpha) {
/* 68 */     float hue = (float)((System.currentTimeMillis() * ((Double)Colors.INSTANCE.speed.getValue()).doubleValue() / 10.0D + (offset * 500L)) % 30000.0D / ((Double)Colors.INSTANCE.difference.getValue()).doubleValue() / 100.0D / 30000.0D / ((Double)Colors.INSTANCE.difference.getValue()).doubleValue() / 20.0D);
/* 69 */     int rgb = Color.HSBtoRGB(hue, ((Double)Colors.INSTANCE.saturation.getValue()).floatValue(), ((Double)Colors.INSTANCE.brightness.getValue()).floatValue());
/* 70 */     int red = rgb >> 16 & 0xFF;
/* 71 */     int green = rgb >> 8 & 0xFF;
/* 72 */     int blue = rgb & 0xFF;
/* 73 */     return new Color(red, green, blue, alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
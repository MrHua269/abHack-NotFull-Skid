/*    */ package me.abHack.manager;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.abHack.features.gui.components.Component;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.util.render.ColorUtil;
/*    */ 
/*    */ public class ColorManager
/*    */ {
/* 10 */   private float red = 1.0F;
/* 11 */   private float green = 1.0F;
/* 12 */   private float blue = 1.0F;
/* 13 */   private float alpha = 1.0F;
/* 14 */   private Color color = new Color(this.red, this.green, this.blue, this.alpha);
/*    */   
/*    */   public Color getColor() {
/* 17 */     return this.color;
/*    */   }
/*    */   
/*    */   public void setColor(Color color) {
/* 21 */     this.color = color;
/*    */   }
/*    */   
/*    */   public int getColorAsInt() {
/* 25 */     return ColorUtil.toRGBA(this.color);
/*    */   }
/*    */   
/*    */   public int getColorAsIntFullAlpha() {
/* 29 */     return ColorUtil.toRGBA(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColorWithAlpha(int color) {
/* 34 */     if (((Boolean)ClickGui.INSTANCE.rainbow.getValue()).booleanValue()) {
/* 35 */       if (ClickGui.INSTANCE.arraymode.getValue() == ClickGui.RainbowArray.Up)
/* 36 */         return ColorUtil.rainbow(Component.counter1[0] * ((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRGB(); 
/* 37 */       if (ClickGui.INSTANCE.arraymode.getValue() == ClickGui.RainbowArray.Unique) {
/* 38 */         return ColorUtil.toRGBA(Component.counter1[0] * 10000 % 255, Component.counter1[0] * 30000 % 255, Component.counter1[0] * 80000 % 255);
/*    */       }
/*    */     } 
/* 41 */     return ColorUtil.toRGBA(new Color(this.red, this.green, this.blue, this.alpha));
/*    */   }
/*    */   
/*    */   public void setColor(float red, float green, float blue, float alpha) {
/* 45 */     this.red = red;
/* 46 */     this.green = green;
/* 47 */     this.blue = blue;
/* 48 */     this.alpha = alpha;
/* 49 */     updateColor();
/*    */   }
/*    */   
/*    */   public void updateColor() {
/* 53 */     setColor(new Color(this.red, this.green, this.blue, this.alpha));
/*    */   }
/*    */   
/*    */   public void setColor(int red, int green, int blue, int alpha) {
/* 57 */     this.red = red / 255.0F;
/* 58 */     this.green = green / 255.0F;
/* 59 */     this.blue = blue / 255.0F;
/* 60 */     this.alpha = alpha / 255.0F;
/* 61 */     updateColor();
/*    */   }
/*    */   
/*    */   public void setRed(float red) {
/* 65 */     this.red = red;
/* 66 */     updateColor();
/*    */   }
/*    */   
/*    */   public void setGreen(float green) {
/* 70 */     this.green = green;
/* 71 */     updateColor();
/*    */   }
/*    */   
/*    */   public void setBlue(float blue) {
/* 75 */     this.blue = blue;
/* 76 */     updateColor();
/*    */   }
/*    */   
/*    */   public void setAlpha(float alpha) {
/* 80 */     this.alpha = alpha;
/* 81 */     updateColor();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\ColorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
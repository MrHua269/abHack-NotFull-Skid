/*    */ package me.abHack.features.modules.client;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class Colors
/*    */   extends Module
/*    */ {
/*    */   public static Colors INSTANCE;
/* 11 */   public Setting<Integer> red = register(new Setting("Red", Integer.valueOf(118), Integer.valueOf(0), Integer.valueOf(255)));
/*    */ 
/*    */   
/* 14 */   public Setting<Integer> green = register(new Setting("Green", Integer.valueOf(98), Integer.valueOf(0), Integer.valueOf(255)));
/* 15 */   public Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(224), Integer.valueOf(0), Integer.valueOf(255)));
/* 16 */   public Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 17 */   public Setting<Rainbow> rainbow = register(new Setting("Rainbow", Rainbow.NONE));
/*    */ 
/*    */ 
/*    */   
/* 21 */   public Setting<Double> speed = register(new Setting("RainbowSpeed", Double.valueOf(50.0D), Double.valueOf(0.1D), Double.valueOf(100.0D), v -> !((Rainbow)this.rainbow.getValue()).equals(Rainbow.NONE)));
/* 22 */   public Setting<Double> saturation = register(new Setting("RainbowSaturation", Double.valueOf(0.35D), Double.valueOf(0.01D), Double.valueOf(1.0D), v -> !((Rainbow)this.rainbow.getValue()).equals(Rainbow.NONE)));
/* 23 */   public Setting<Double> brightness = register(new Setting("RainbowBrightness", Double.valueOf(1.0D), Double.valueOf(0.01D), Double.valueOf(1.0D), v -> !((Rainbow)this.rainbow.getValue()).equals(Rainbow.NONE)));
/* 24 */   public Setting<Double> difference = register(new Setting("RainbowDifference", Double.valueOf(40.0D), Double.valueOf(0.1D), Double.valueOf(100.0D), v -> !((Rainbow)this.rainbow.getValue()).equals(Rainbow.NONE)));
/*    */ 
/*    */   
/*    */   public Colors() {
/* 28 */     super("Colors", "The universal color for the client", Module.Category.CLIENT, true, false, false);
/* 29 */     INSTANCE = this;
/* 30 */     setDrawn(false);
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 34 */     return new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Rainbow
/*    */   {
/* 42 */     GRADIENT,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     STATIC,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     ALPHA,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     NONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\client\Colors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
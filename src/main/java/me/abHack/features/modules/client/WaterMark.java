/*    */ package me.abHack.features.modules.client;
/*    */ 
/*    */ import me.abHack.event.events.Render2DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.render.ColorUtil;
/*    */ import me.abHack.util.render.HudUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ 
/*    */ public class WaterMark
/*    */   extends Module
/*    */ {
/* 13 */   public Setting<Integer> compactX = register(new Setting("WaterMarkX", Integer.valueOf(170), Integer.valueOf(0), Integer.valueOf(1000)));
/* 14 */   public Setting<Integer> compactY = register(new Setting("WaterMarkY", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500)));
/* 15 */   public Setting<Integer> red = register(new Setting("Red", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255)));
/* 16 */   public Setting<Integer> green = register(new Setting("Green", Integer.valueOf(24), Integer.valueOf(0), Integer.valueOf(255)));
/* 17 */   public Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(255)));
/* 18 */   private final Setting<Boolean> rainbow = register(new Setting("Rainbow", Boolean.valueOf(false)));
/*    */ 
/*    */   
/*    */   public WaterMark() {
/* 22 */     super("WaterMark", "watermark", Module.Category.CLIENT, true, false, false);
/*    */   }
/*    */   
/*    */   public void onRender2D(Render2DEvent event) {
/*    */     int color1;
/* 27 */     if (((Boolean)this.rainbow.getValue()).booleanValue()) {
/* 28 */       color1 = ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRGB();
/* 29 */       int color2 = ColorUtil.rainbow(100).getRGB();
/* 30 */       RenderUtil.drawRectangleCorrectly(((Integer)this.compactX.getValue()).intValue(), ((Integer)this.compactY.getValue()).intValue(), 200, 15, ColorUtil.toRGBA(20, 20, 20, 200));
/* 31 */       RenderUtil.drawGradientSideways(((Integer)this.compactX.getValue()).intValue(), 0.0D + ((Integer)this.compactY.getValue()).intValue(), (200 + ((Integer)this.compactX.getValue()).intValue()), 1.5D + ((Integer)this.compactY.getValue()).intValue(), color1, color2);
/*    */     } else {
/* 33 */       color1 = ColorUtil.toRGBA(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), 255);
/* 34 */       RenderUtil.drawRectangleCorrectly(((Integer)this.compactX.getValue()).intValue(), ((Integer)this.compactY.getValue()).intValue(), 200, 15, ColorUtil.toRGBA(20, 20, 20, 200));
/* 35 */       RenderUtil.drawGradientSideways(((Integer)this.compactX.getValue()).intValue(), 0.0D + ((Integer)this.compactY.getValue()).intValue(), (200 + ((Integer)this.compactX.getValue()).intValue()), 1.5D + ((Integer)this.compactY.getValue()).intValue(), color1, color1);
/*    */     } 
/* 37 */     this.renderer.drawString("abHack v0.0.1 | " + HudUtil.getPingSatus() + "ms | " + HudUtil.getFpsStatus() + "fps | " + HudUtil.getTpsStatus() + "tps", ((Integer)this.compactX.getValue()).intValue(), (((Integer)this.compactY.getValue()).intValue() + 3), color1, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\client\WaterMark.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
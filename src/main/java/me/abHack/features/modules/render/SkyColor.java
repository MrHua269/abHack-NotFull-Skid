/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class SkyColor
/*    */   extends Module
/*    */ {
/* 13 */   private static SkyColor INSTANCE = new SkyColor();
/* 14 */   private final Setting<Integer> red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 15 */   private final Setting<Integer> green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 16 */   private final Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 17 */   private final Setting<Boolean> rainbow = register(new Setting("Rainbow", Boolean.valueOf(true)));
/*    */   
/*    */   public SkyColor() {
/* 20 */     super("SkyColor", "Sky Render.", Module.Category.RENDER, false, false, false);
/*    */   }
/*    */   
/*    */   public static SkyColor getInstance() {
/* 24 */     if (INSTANCE == null) {
/* 25 */       INSTANCE = new SkyColor();
/*    */     }
/* 27 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void fogColors(EntityViewRenderEvent.FogColors event) {
/* 32 */     event.setRed(((Integer)this.red.getValue()).intValue() / 255.0F);
/* 33 */     event.setGreen(((Integer)this.green.getValue()).intValue() / 255.0F);
/* 34 */     event.setBlue(((Integer)this.blue.getValue()).intValue() / 255.0F);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void fog_density(EntityViewRenderEvent.FogDensity event) {
/* 39 */     event.setDensity(0.0F);
/* 40 */     event.setCanceled(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 45 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 50 */     MinecraftForge.EVENT_BUS.unregister(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 55 */     if (((Boolean)this.rainbow.getValue()).booleanValue()) {
/* 56 */       doRainbow();
/*    */     }
/*    */   }
/*    */   
/*    */   public void doRainbow() {
/* 61 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/* 62 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/* 63 */     this.red.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 64 */     this.green.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 65 */     this.blue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\SkyColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
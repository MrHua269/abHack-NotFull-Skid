/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class CameraClip
/*    */   extends Module {
/*  8 */   public static CameraClip INSTANCE = new CameraClip();
/*  9 */   public Setting<Double> distance = register(new Setting("Distance", Double.valueOf(5.5D), Double.valueOf(0.0D), Double.valueOf(50.0D)));
/*    */   
/*    */   public CameraClip() {
/* 12 */     super("CameraClip", "Change F5 Perspective.", Module.Category.RENDER, true, false, false);
/* 13 */     setInstance();
/*    */   }
/*    */   
/*    */   public static CameraClip getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new CameraClip();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 24 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\CameraClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
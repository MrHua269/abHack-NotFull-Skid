/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import me.abHack.event.events.RenderItemEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class ViewModel
/*     */   extends Module
/*     */ {
/*  12 */   private static ViewModel INSTANCE = new ViewModel();
/*     */   
/*     */   public Setting<Settings> settings;
/*     */   
/*     */   public Setting<Boolean> noEatAnimation;
/*     */   public Setting<Double> eatX;
/*     */   public Setting<Double> eatY;
/*     */   public Setting<Boolean> doBob;
/*     */   public Setting<Double> mainX;
/*     */   public Setting<Double> mainY;
/*     */   public Setting<Double> mainZ;
/*     */   public Setting<Double> offX;
/*     */   public Setting<Double> offY;
/*     */   public Setting<Double> offZ;
/*     */   public Setting<Integer> mainRotX;
/*     */   public Setting<Integer> mainRotY;
/*     */   public Setting<Integer> mainRotZ;
/*     */   public Setting<Integer> offRotX;
/*     */   public Setting<Integer> offRotY;
/*     */   public Setting<Integer> offRotZ;
/*     */   public Setting<Double> mainScaleX;
/*     */   public Setting<Double> mainScaleY;
/*     */   public Setting<Double> mainScaleZ;
/*     */   public Setting<Double> offScaleX;
/*     */   public Setting<Double> offScaleY;
/*     */   public Setting<Double> offScaleZ;
/*     */   
/*     */   public ViewModel() {
/*  40 */     super("ViewModel", "Change the position of the arm", Module.Category.RENDER, true, false, false);
/*  41 */     this.settings = register(new Setting("Settings", Settings.TRANSLATE));
/*  42 */     this.noEatAnimation = register(new Setting("NoEatAnimation", Boolean.valueOf(false), v -> (this.settings.getValue() == Settings.TWEAKS)));
/*  43 */     this.eatX = register(new Setting("EatX", Double.valueOf(1.0D), Double.valueOf(-2.0D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.TWEAKS && !((Boolean)this.noEatAnimation.getValue()).booleanValue())));
/*  44 */     this.eatY = register(new Setting("EatY", Double.valueOf(1.0D), Double.valueOf(-2.0D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.TWEAKS && !((Boolean)this.noEatAnimation.getValue()).booleanValue())));
/*  45 */     this.doBob = register(new Setting("ItemBob", Boolean.valueOf(true), v -> (this.settings.getValue() == Settings.TWEAKS)));
/*  46 */     this.mainX = register(new Setting("MainX", Double.valueOf(1.2D), Double.valueOf(-2.0D), Double.valueOf(4.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  47 */     this.mainY = register(new Setting("MainY", Double.valueOf(-0.95D), Double.valueOf(-3.0D), Double.valueOf(3.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  48 */     this.mainZ = register(new Setting("MainZ", Double.valueOf(-1.45D), Double.valueOf(-5.0D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  49 */     this.offX = register(new Setting("OffX", Double.valueOf(1.2D), Double.valueOf(-2.0D), Double.valueOf(4.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  50 */     this.offY = register(new Setting("OffY", Double.valueOf(-0.95D), Double.valueOf(-3.0D), Double.valueOf(3.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  51 */     this.offZ = register(new Setting("OffZ", Double.valueOf(-1.45D), Double.valueOf(-5.0D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  52 */     this.mainRotX = register(new Setting("MainRotationX", Integer.valueOf(0), Integer.valueOf(-36), Integer.valueOf(36), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  53 */     this.mainRotY = register(new Setting("MainRotationY", Integer.valueOf(0), Integer.valueOf(-36), Integer.valueOf(36), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  54 */     this.mainRotZ = register(new Setting("MainRotationZ", Integer.valueOf(0), Integer.valueOf(-36), Integer.valueOf(36), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  55 */     this.offRotX = register(new Setting("OffRotationX", Integer.valueOf(0), Integer.valueOf(-36), Integer.valueOf(36), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  56 */     this.offRotY = register(new Setting("OffRotationY", Integer.valueOf(0), Integer.valueOf(-36), Integer.valueOf(36), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  57 */     this.offRotZ = register(new Setting("OffRotationZ", Integer.valueOf(0), Integer.valueOf(-36), Integer.valueOf(36), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  58 */     this.mainScaleX = register(new Setting("MainScaleX", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.SCALE)));
/*  59 */     this.mainScaleY = register(new Setting("MainScaleY", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.SCALE)));
/*  60 */     this.mainScaleZ = register(new Setting("MainScaleZ", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.SCALE)));
/*  61 */     this.offScaleX = register(new Setting("OffScaleX", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.SCALE)));
/*  62 */     this.offScaleY = register(new Setting("OffScaleY", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.SCALE)));
/*  63 */     this.offScaleZ = register(new Setting("OffScaleZ", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.SCALE)));
/*  64 */     setInstance();
/*     */   }
/*     */   
/*     */   public static ViewModel getInstance() {
/*  68 */     if (INSTANCE == null) {
/*  69 */       INSTANCE = new ViewModel();
/*     */     }
/*  71 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  75 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onItemRender(RenderItemEvent event) {
/*  80 */     event.setMainX(((Double)this.mainX.getValue()).doubleValue());
/*  81 */     event.setMainY(((Double)this.mainY.getValue()).doubleValue());
/*  82 */     event.setMainZ(((Double)this.mainZ.getValue()).doubleValue());
/*  83 */     event.setOffX(-((Double)this.offX.getValue()).doubleValue());
/*  84 */     event.setOffY(((Double)this.offY.getValue()).doubleValue());
/*  85 */     event.setOffZ(((Double)this.offZ.getValue()).doubleValue());
/*  86 */     event.setMainRotX((((Integer)this.mainRotX.getValue()).intValue() * 5));
/*  87 */     event.setMainRotY((((Integer)this.mainRotY.getValue()).intValue() * 5));
/*  88 */     event.setMainRotZ((((Integer)this.mainRotZ.getValue()).intValue() * 5));
/*  89 */     event.setOffRotX((((Integer)this.offRotX.getValue()).intValue() * 5));
/*  90 */     event.setOffRotY((((Integer)this.offRotY.getValue()).intValue() * 5));
/*  91 */     event.setOffRotZ((((Integer)this.offRotZ.getValue()).intValue() * 5));
/*  92 */     event.setOffHandScaleX(((Double)this.offScaleX.getValue()).doubleValue());
/*  93 */     event.setOffHandScaleY(((Double)this.offScaleY.getValue()).doubleValue());
/*  94 */     event.setOffHandScaleZ(((Double)this.offScaleZ.getValue()).doubleValue());
/*  95 */     event.setMainHandScaleX(((Double)this.mainScaleX.getValue()).doubleValue());
/*  96 */     event.setMainHandScaleY(((Double)this.mainScaleY.getValue()).doubleValue());
/*  97 */     event.setMainHandScaleZ(((Double)this.mainScaleZ.getValue()).doubleValue());
/*     */   }
/*     */   
/*     */   private enum Settings {
/* 101 */     TRANSLATE,
/* 102 */     ROTATE,
/* 103 */     SCALE,
/* 104 */     TWEAKS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\ViewModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
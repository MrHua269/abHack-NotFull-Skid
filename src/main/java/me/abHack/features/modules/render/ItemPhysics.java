/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class ItemPhysics
/*    */   extends Module
/*    */ {
/*  9 */   public static ItemPhysics INSTANCE = new ItemPhysics();
/* 10 */   public final Setting<Float> Scaling = register(new Setting("Scaling", Float.valueOf(0.5F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*    */   
/*    */   public ItemPhysics() {
/* 13 */     super("ItemPhysics", "Apply physics to items.", Module.Category.RENDER, false, false, false);
/* 14 */     setInstance();
/*    */   }
/*    */   
/*    */   public static ItemPhysics getInstance() {
/* 18 */     if (INSTANCE == null) {
/* 19 */       INSTANCE = new ItemPhysics();
/*    */     }
/* 21 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 25 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\ItemPhysics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
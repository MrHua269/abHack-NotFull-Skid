/*    */ package me.abHack.features.gui.components.items;
/*    */ 
/*    */ import me.abHack.features.Feature;
/*    */ 
/*    */ public class Item
/*    */   extends Feature {
/*    */   protected float x;
/*    */   protected float y;
/*    */   protected int width;
/*    */   protected int height;
/*    */   private boolean hidden;
/*    */   
/*    */   public Item(String name) {
/* 14 */     super(name);
/*    */   }
/*    */   
/*    */   public void setLocation(float x, float y) {
/* 18 */     this.x = x;
/* 19 */     this.y = y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
/*    */ 
/*    */   
/*    */   public void mouseReleased() {}
/*    */ 
/*    */   
/*    */   public void update() {}
/*    */ 
/*    */   
/*    */   public void onKeyTyped(char typedChar, int keyCode) {}
/*    */   
/*    */   public float getX() {
/* 38 */     return this.x;
/*    */   }
/*    */   
/*    */   public float getY() {
/* 42 */     return this.y;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 46 */     return this.width;
/*    */   }
/*    */   
/*    */   public void setWidth(int width) {
/* 50 */     this.width = width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 54 */     return this.height;
/*    */   }
/*    */   
/*    */   public void setHeight(int height) {
/* 58 */     this.height = height;
/*    */   }
/*    */   
/*    */   public boolean isHidden() {
/* 62 */     return this.hidden;
/*    */   }
/*    */   
/*    */   public void setHidden(boolean hidden) {
/* 66 */     this.hidden = hidden;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
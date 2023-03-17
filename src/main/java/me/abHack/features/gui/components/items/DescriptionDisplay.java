/*    */ package me.abHack.features.gui.components.items;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ 
/*    */ public class DescriptionDisplay
/*    */   extends Item {
/*    */   private String description;
/*    */   private boolean draw;
/*    */   
/*    */   public DescriptionDisplay(String description, float x, float y) {
/* 12 */     super("DescriptionDisplay");
/* 13 */     this.description = description;
/* 14 */     setLocation(x, y);
/* 15 */     this.width = OyVey.textManager.getStringWidth(this.description) + 4;
/* 16 */     this.height = OyVey.textManager.getFontHeight() + 4;
/* 17 */     this.draw = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 22 */     this.width = OyVey.textManager.getStringWidth(this.description) + 4;
/* 23 */     this.height = OyVey.textManager.getFontHeight() + 4;
/* 24 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -704643072);
/* 25 */     OyVey.textManager.drawString(this.description, this.x + 2.0F, this.y + 2.0F, 16777215, true);
/*    */   }
/*    */   
/*    */   public boolean shouldDraw() {
/* 29 */     return this.draw;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 33 */     this.description = description;
/*    */   }
/*    */   
/*    */   public void setDraw(boolean draw) {
/* 37 */     this.draw = draw;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\DescriptionDisplay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
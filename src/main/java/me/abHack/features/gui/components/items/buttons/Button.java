/*    */ package me.abHack.features.gui.components.items.buttons;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.gui.OyVeyGui;
/*    */ import me.abHack.features.gui.components.Component;
/*    */ import me.abHack.features.gui.components.items.Item;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ 
/*    */ public class Button extends Item {
/*    */   private boolean state;
/*    */   
/*    */   public Button(String name) {
/* 17 */     super(name);
/* 18 */     this.height = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 23 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height - 0.5F, getState() ? OyVey.colorManager.getColorWithAlpha(((Integer)((ClickGui)OyVey.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue()) : (!isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
/* 24 */     OyVey.textManager.drawStringWithShadow(getName(), this.x + 2.3F, this.y - 2.0F - OyVeyGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 29 */     if (mouseButton == 0 && isHovering(mouseX, mouseY)) {
/* 30 */       onMouseClick();
/*    */     }
/*    */   }
/*    */   
/*    */   public void onMouseClick() {
/* 35 */     this.state = !this.state;
/* 36 */     toggle();
/* 37 */     mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*    */   }
/*    */ 
/*    */   
/*    */   public void toggle() {}
/*    */   
/*    */   public boolean getState() {
/* 44 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 49 */     return 14;
/*    */   }
/*    */   
/*    */   public boolean isHovering(int mouseX, int mouseY) {
/* 53 */     for (Component component : OyVeyGui.getClickGui().getComponents()) {
/* 54 */       if (!component.drag)
/* 55 */         continue;  return false;
/*    */     } 
/* 57 */     return (mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + this.height);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
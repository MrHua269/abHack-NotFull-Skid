/*    */ package me.abHack.features.gui.components.items.buttons;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.gui.OyVeyGui;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ 
/*    */ public class BooleanButton extends Button {
/*    */   private final Setting setting;
/*    */   
/*    */   public BooleanButton(Setting setting) {
/* 16 */     super(setting.getName());
/* 17 */     this.setting = setting;
/* 18 */     this.width = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 23 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4F, this.y + this.height - 0.5F, getState() ? OyVey.colorManager.getColorWithAlpha(((Integer)((ClickGui)OyVey.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue()) : (!isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
/* 24 */     OyVey.textManager.drawStringWithShadow(getName(), this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 29 */     setHidden(!this.setting.isVisible());
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 34 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 35 */     if (isHovering(mouseX, mouseY)) {
/* 36 */       mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 42 */     return 14;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toggle() {
/* 47 */     this.setting.setValue(Boolean.valueOf(!((Boolean)this.setting.getValue()).booleanValue()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getState() {
/* 52 */     return ((Boolean)this.setting.getValue()).booleanValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\BooleanButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
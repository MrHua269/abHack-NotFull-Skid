/*    */ package me.abHack.features.gui.components.items.buttons;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.gui.OyVeyGui;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ 
/*    */ public class UnlimitedSlider extends Button {
/*    */   public Setting setting;
/*    */   
/*    */   public UnlimitedSlider(Setting setting) {
/* 17 */     super(setting.getName());
/* 18 */     this.setting = setting;
/* 19 */     this.width = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 24 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4F, this.y + this.height - 0.5F, OyVey.colorManager.getColorWithAlpha(((Integer)((ClickGui)OyVey.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue()));
/* 25 */     OyVey.textManager.drawStringWithShadow(" - " + this.setting.getName() + " " + ChatFormatting.GRAY + this.setting.getValue() + ChatFormatting.WHITE + " +", this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 30 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 31 */     if (isHovering(mouseX, mouseY)) {
/* 32 */       mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 33 */       if (isRight(mouseX)) {
/* 34 */         if (this.setting.getValue() instanceof Double) {
/* 35 */           this.setting.setValue(Double.valueOf(((Double)this.setting.getValue()).doubleValue() + 1.0D));
/* 36 */         } else if (this.setting.getValue() instanceof Float) {
/* 37 */           this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() + 1.0F));
/* 38 */         } else if (this.setting.getValue() instanceof Integer) {
/* 39 */           this.setting.setValue(Integer.valueOf(((Integer)this.setting.getValue()).intValue() + 1));
/*    */         } 
/* 41 */       } else if (this.setting.getValue() instanceof Double) {
/* 42 */         this.setting.setValue(Double.valueOf(((Double)this.setting.getValue()).doubleValue() - 1.0D));
/* 43 */       } else if (this.setting.getValue() instanceof Float) {
/* 44 */         this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() - 1.0F));
/* 45 */       } else if (this.setting.getValue() instanceof Integer) {
/* 46 */         this.setting.setValue(Integer.valueOf(((Integer)this.setting.getValue()).intValue() - 1));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 53 */     setHidden(!this.setting.isVisible());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 58 */     return 14;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void toggle() {}
/*    */ 
/*    */   
/*    */   public boolean getState() {
/* 67 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isRight(int x) {
/* 71 */     return (x > this.x + (this.width + 7.4F) / 2.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\UnlimitedSlider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
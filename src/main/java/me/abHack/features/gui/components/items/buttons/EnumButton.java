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
/*    */ public class EnumButton extends Button {
/*    */   public Setting setting;
/*    */   
/*    */   public EnumButton(Setting setting) {
/* 17 */     super(setting.getName());
/* 18 */     this.setting = setting;
/* 19 */     this.width = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 24 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4F, this.y + this.height - 0.5F, getState() ? OyVey.colorManager.getColorWithAlpha(((Integer)((ClickGui)OyVey.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue()) : (!isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
/* 25 */     OyVey.textManager.drawStringWithShadow(this.setting.getName() + " " + ChatFormatting.GRAY + (this.setting.currentEnumName().equalsIgnoreCase("ABC") ? "ABC" : this.setting.currentEnumName()), this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 30 */     setHidden(!this.setting.isVisible());
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 35 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 36 */     if (isHovering(mouseX, mouseY)) {
/* 37 */       mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 43 */     return 14;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toggle() {
/* 48 */     this.setting.increaseEnum();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getState() {
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\EnumButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
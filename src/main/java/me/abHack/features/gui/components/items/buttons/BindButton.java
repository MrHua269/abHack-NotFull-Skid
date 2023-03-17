/*    */ package me.abHack.features.gui.components.items.buttons;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.gui.OyVeyGui;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.features.setting.Bind;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ 
/*    */ public class BindButton extends Button {
/*    */   private final Setting setting;
/*    */   public boolean isListening;
/*    */   
/*    */   public BindButton(Setting setting) {
/* 19 */     super(setting.getName());
/* 20 */     this.setting = setting;
/* 21 */     this.width = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 26 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4F, this.y + this.height - 0.5F, getState() ? (!isHovering(mouseX, mouseY) ? 290805077 : -2007673515) : OyVey.colorManager.getColorWithAlpha(((Integer)((ClickGui)OyVey.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue()));
/* 27 */     if (this.isListening) {
/* 28 */       OyVey.textManager.drawStringWithShadow("Press a Key...", this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), -1);
/*    */     } else {
/* 30 */       OyVey.textManager.drawStringWithShadow(this.setting.getName() + " " + ChatFormatting.GRAY + this.setting.getValue().toString().toUpperCase(), this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 36 */     setHidden(!this.setting.isVisible());
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 41 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 42 */     if (isHovering(mouseX, mouseY)) {
/* 43 */       mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onKeyTyped(char typedChar, int keyCode) {
/* 49 */     if (this.isListening) {
/* 50 */       Bind bind = new Bind(keyCode);
/* 51 */       if (bind.toString().equalsIgnoreCase("Escape")) {
/*    */         return;
/*    */       }
/* 54 */       if (bind.toString().equalsIgnoreCase("Delete")) {
/* 55 */         bind = new Bind(-1);
/*    */       }
/* 57 */       this.setting.setValue(bind);
/* 58 */       onMouseClick();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 64 */     return 14;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toggle() {
/* 69 */     this.isListening = !this.isListening;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getState() {
/* 74 */     return !this.isListening;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\BindButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
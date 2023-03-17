/*     */ package me.abHack.features.gui.components.items.buttons;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.gui.OyVeyGui;
/*     */ import me.abHack.features.modules.client.ClickGui;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class StringButton
/*     */   extends Button {
/*     */   private final Setting setting;
/*     */   public boolean isListening;
/*  21 */   private CurrentString currentString = new CurrentString("");
/*     */   
/*     */   public StringButton(Setting setting) {
/*  24 */     super(setting.getName());
/*  25 */     this.setting = setting;
/*  26 */     this.width = 15;
/*     */   }
/*     */   
/*     */   public static String removeLastChar(String str) {
/*  30 */     String output = "";
/*  31 */     if (str != null && str.length() > 0) {
/*  32 */       output = str.substring(0, str.length() - 1);
/*     */     }
/*  34 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  39 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4F, this.y + this.height - 0.5F, getState() ? OyVey.colorManager.getColorWithAlpha(((Integer)((ClickGui)OyVey.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue()) : (!isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
/*  40 */     if (this.isListening) {
/*  41 */       OyVey.textManager.drawStringWithShadow(this.currentString.getString() + OyVey.textManager.getIdleSign(), this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
/*     */     } else {
/*  43 */       OyVey.textManager.drawStringWithShadow((this.setting.getName().equals("Buttons") ? "Buttons " : (this.setting.getName().equals("Prefix") ? ("Prefix  " + ChatFormatting.GRAY) : "")) + this.setting.getValue(), this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/*  49 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  50 */     if (isHovering(mouseX, mouseY)) {
/*  51 */       mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onKeyTyped(char typedChar, int keyCode) {
/*  57 */     if (this.isListening) {
/*  58 */       if (keyCode == 1) {
/*     */         return;
/*     */       }
/*  61 */       if (keyCode == 28) {
/*  62 */         enterString();
/*  63 */       } else if (keyCode == 14) {
/*  64 */         setString(removeLastChar(this.currentString.getString()));
/*  65 */       } else if (keyCode == 47 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
/*     */         try {
/*  67 */           setString(this.currentString.getString() + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
/*  68 */         } catch (Exception e) {
/*  69 */           e.printStackTrace();
/*     */         } 
/*  71 */       } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
/*  72 */         setString(this.currentString.getString() + typedChar);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  79 */     setHidden(!this.setting.isVisible());
/*     */   }
/*     */   
/*     */   private void enterString() {
/*  83 */     if (this.currentString.getString().isEmpty()) {
/*  84 */       this.setting.setValue(this.setting.getDefaultValue());
/*     */     } else {
/*  86 */       this.setting.setValue(this.currentString.getString());
/*     */     } 
/*  88 */     setString("");
/*  89 */     onMouseClick();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  94 */     return 14;
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggle() {
/*  99 */     this.isListening = !this.isListening;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getState() {
/* 104 */     return !this.isListening;
/*     */   }
/*     */   
/*     */   public void setString(String newString) {
/* 108 */     this.currentString = new CurrentString(newString);
/*     */   }
/*     */   
/*     */   public static class CurrentString {
/*     */     private final String string;
/*     */     
/*     */     public CurrentString(String string) {
/* 115 */       this.string = string;
/*     */     }
/*     */     
/*     */     public String getString() {
/* 119 */       return this.string;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\StringButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
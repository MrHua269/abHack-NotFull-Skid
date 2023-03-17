/*    */ package me.abHack.features.gui.alts.ias.gui;
/*    */ 
/*    */ import joptsimple.internal.Strings;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiTextField;
/*    */ 
/*    */ public class GuiPasswordField
/*    */   extends GuiTextField {
/*    */   public GuiPasswordField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
/* 11 */     super(componentId, fontrendererObj, x, y, par5Width, par6Height);
/*    */   }
/*    */   
/*    */   public void drawTextBox() {
/* 15 */     String password = getText();
/* 16 */     replaceText(Strings.repeat('*', getText().length()));
/* 17 */     super.drawTextBox();
/* 18 */     replaceText(password);
/*    */   }
/*    */   
/*    */   public boolean textboxKeyTyped(char typedChar, int keyCode) {
/* 22 */     return (!GuiScreen.isKeyComboCtrlC(keyCode) && !GuiScreen.isKeyComboCtrlX(keyCode) && super.textboxKeyTyped(typedChar, keyCode));
/*    */   }
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 26 */     String password = getText();
/* 27 */     replaceText(Strings.repeat('*', getText().length()));
/* 28 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 29 */     replaceText(password);
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   private void replaceText(String newText) {
/* 34 */     int cursorPosition = getCursorPosition();
/* 35 */     int selectionEnd = getSelectionEnd();
/* 36 */     setText(newText);
/* 37 */     setCursorPosition(cursorPosition);
/* 38 */     setSelectionPos(selectionEnd);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\gui\GuiPasswordField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
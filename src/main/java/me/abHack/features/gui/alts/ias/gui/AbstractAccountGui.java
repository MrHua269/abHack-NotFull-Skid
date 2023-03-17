/*     */ package me.abHack.features.gui.alts.ias.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import me.abHack.features.gui.alts.iasencrypt.EncryptionTools;
/*     */ import me.abHack.features.gui.alts.tools.alt.AccountData;
/*     */ import me.abHack.features.gui.alts.tools.alt.AltDatabase;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public abstract class AbstractAccountGui
/*     */   extends GuiScreen
/*     */ {
/*     */   private final String actionString;
/*     */   protected boolean hasUserChanged;
/*     */   private GuiTextField username;
/*     */   private GuiTextField password;
/*     */   private GuiButton complete;
/*     */   
/*     */   public AbstractAccountGui(String actionString) {
/*  23 */     this.actionString = actionString;
/*     */   }
/*     */   
/*     */   public void initGui() {
/*  27 */     Keyboard.enableRepeatEvents(true);
/*  28 */     this.buttonList.clear();
/*  29 */     this.complete = new GuiButton(2, this.width / 2 - 152, this.height - 28, 150, 20, I18n.format(this.actionString, new Object[0]));
/*  30 */     this.buttonList.add(this.complete);
/*  31 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 2, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  32 */     this.username = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
/*  33 */     this.username.setFocused(true);
/*  34 */     this.username.setMaxStringLength(64);
/*  35 */     this.password = new GuiPasswordField(1, this.fontRenderer, this.width / 2 - 100, 90, 200, 20);
/*  36 */     this.password.setMaxStringLength(64);
/*  37 */     this.complete.enabled = false;
/*     */   }
/*     */   
/*     */   public void drawScreen(int par1, int par2, float par3) {
/*  41 */     drawDefaultBackground();
/*  42 */     drawCenteredString(this.fontRenderer, I18n.format(this.actionString, new Object[0]), this.width / 2, 7, -1);
/*  43 */     drawCenteredString(this.fontRenderer, I18n.format("ias.username", new Object[0]), this.width / 2 - 130, 66, -1);
/*  44 */     drawCenteredString(this.fontRenderer, I18n.format("ias.password", new Object[0]), this.width / 2 - 130, 96, -1);
/*  45 */     this.username.drawTextBox();
/*  46 */     this.password.drawTextBox();
/*  47 */     super.drawScreen(par1, par2, par3);
/*     */   }
/*     */   
/*     */   protected void keyTyped(char character, int keyIndex) {
/*  51 */     if (keyIndex == 1) {
/*  52 */       escape();
/*  53 */     } else if (keyIndex == 28) {
/*  54 */       if (this.username.isFocused()) {
/*  55 */         this.username.setFocused(false);
/*  56 */         this.password.setFocused(true);
/*  57 */       } else if (this.password.isFocused() && this.complete.enabled) {
/*  58 */         complete();
/*  59 */         escape();
/*     */       } 
/*  61 */     } else if (keyIndex == 15) {
/*  62 */       this.username.setFocused(!this.username.isFocused());
/*  63 */       this.password.setFocused(!this.password.isFocused());
/*     */     } else {
/*  65 */       this.username.textboxKeyTyped(character, keyIndex);
/*  66 */       this.password.textboxKeyTyped(character, keyIndex);
/*  67 */       if (this.username.isFocused()) {
/*  68 */         this.hasUserChanged = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/*  74 */     this.username.updateCursorCounter();
/*  75 */     this.password.updateCursorCounter();
/*  76 */     this.complete.enabled = canComplete();
/*     */   }
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  80 */     if (button.enabled) {
/*  81 */       if (button.id == 2) {
/*  82 */         complete();
/*  83 */         escape();
/*  84 */       } else if (button.id == 3) {
/*  85 */         escape();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  91 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  92 */     this.username.mouseClicked(mouseX, mouseY, mouseButton);
/*  93 */     this.password.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/*  97 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */   
/*     */   private void escape() {
/* 101 */     this.mc.displayGuiScreen(new GuiAccountSelector());
/*     */   }
/*     */   
/*     */   public String getUsername() {
/* 105 */     return this.username.getText();
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/* 109 */     this.username.setText(username);
/*     */   }
/*     */   
/*     */   public String getPassword() {
/* 113 */     return this.password.getText();
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/* 117 */     this.password.setText(password);
/*     */   }
/*     */   
/*     */   protected boolean accountNotInList() {
/* 121 */     for (AccountData data : AltDatabase.getInstance().getAlts()) {
/* 122 */       if (!EncryptionTools.decode(data.user).equals(getUsername()))
/* 123 */         continue;  return false;
/*     */     } 
/* 125 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canComplete() {
/* 129 */     return (getUsername().length() > 0 && accountNotInList());
/*     */   }
/*     */   
/*     */   public abstract void complete();
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\gui\AbstractAccountGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
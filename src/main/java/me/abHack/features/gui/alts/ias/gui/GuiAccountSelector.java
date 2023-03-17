/*     */ package me.abHack.features.gui.alts.ias.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ import me.abHack.features.gui.alts.ias.account.ExtendedAccountData;
/*     */ import me.abHack.features.gui.alts.ias.config.ConfigValues;
/*     */ import me.abHack.features.gui.alts.ias.enums.EnumBool;
/*     */ import me.abHack.features.gui.alts.ias.tools.HttpTools;
/*     */ import me.abHack.features.gui.alts.ias.tools.JavaTools;
/*     */ import me.abHack.features.gui.alts.ias.tools.SkinTools;
/*     */ import me.abHack.features.gui.alts.iasencrypt.EncryptionTools;
/*     */ import me.abHack.features.gui.alts.tools.Config;
/*     */ import me.abHack.features.gui.alts.tools.Tools;
/*     */ import me.abHack.features.gui.alts.tools.alt.AccountData;
/*     */ import me.abHack.features.gui.alts.tools.alt.AltDatabase;
/*     */ import me.abHack.features.gui.alts.tools.alt.AltManager;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiAccountSelector<T>
/*     */   extends GuiScreen
/*     */ {
/*     */   private int selectedAccountIndex;
/*     */   private int prevIndex;
/*     */   private Throwable loginfailed;
/*  34 */   private ArrayList<ExtendedAccountData> queriedaccounts = convertData();
/*     */   private List accountsgui;
/*     */   private GuiButton login;
/*     */   private GuiButton loginoffline;
/*     */   private GuiButton delete;
/*     */   private GuiButton edit;
/*     */   private GuiButton reloadskins;
/*     */   private String query;
/*     */   private GuiTextField search;
/*     */   
/*     */   public void initGui() {
/*  45 */     Keyboard.enableRepeatEvents(true);
/*  46 */     this.accountsgui = new List(this.mc);
/*  47 */     this.accountsgui.registerScrollButtons(5, 6);
/*  48 */     this.query = I18n.format("ias.search", new Object[0]);
/*  49 */     this.buttonList.clear();
/*  50 */     this.reloadskins = new GuiButton(8, this.width / 2 - 154 - 10, this.height - 76 - 8, 120, 20, I18n.format("ias.reloadskins", new Object[0]));
/*  51 */     this.buttonList.add(this.reloadskins);
/*  52 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 40, this.height - 52, 120, 20, I18n.format("ias.addaccount", new Object[0])));
/*  53 */     this.login = new GuiButton(1, this.width / 2 - 154 - 10, this.height - 52, 120, 20, I18n.format("ias.login", new Object[0]));
/*  54 */     this.buttonList.add(this.login);
/*  55 */     this.edit = new GuiButton(7, this.width / 2 - 40, this.height - 52, 80, 20, I18n.format("ias.edit", new Object[0]));
/*  56 */     this.buttonList.add(this.edit);
/*  57 */     this.loginoffline = new GuiButton(2, this.width / 2 - 154 - 10, this.height - 28, 110, 20, I18n.format("ias.login", new Object[0]) + " " + I18n.format("ias.offline", new Object[0]));
/*  58 */     this.buttonList.add(this.loginoffline);
/*  59 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 28, 110, 20, I18n.format("gui.cancel", new Object[0])));
/*  60 */     this.delete = new GuiButton(4, this.width / 2 - 50, this.height - 28, 100, 20, I18n.format("ias.delete", new Object[0]));
/*  61 */     this.buttonList.add(this.delete);
/*  62 */     this.search = new GuiTextField(8, this.fontRenderer, this.width / 2 - 80, 14, 160, 16);
/*  63 */     this.search.setText(this.query);
/*  64 */     updateButtons();
/*  65 */     if (!this.queriedaccounts.isEmpty()) {
/*  66 */       SkinTools.buildSkin(((ExtendedAccountData)this.queriedaccounts.get(this.selectedAccountIndex)).alias);
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  71 */     super.handleMouseInput();
/*  72 */     this.accountsgui.handleMouseInput();
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/*  76 */     this.search.updateCursorCounter();
/*  77 */     updateText();
/*  78 */     updateButtons();
/*  79 */     if (this.prevIndex != this.selectedAccountIndex) {
/*  80 */       updateShownSkin();
/*  81 */       this.prevIndex = this.selectedAccountIndex;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateShownSkin() {
/*  86 */     if (!this.queriedaccounts.isEmpty()) {
/*  87 */       SkinTools.buildSkin(((ExtendedAccountData)this.queriedaccounts.get(this.selectedAccountIndex)).alias);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  92 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  93 */     boolean flag = this.search.isFocused();
/*  94 */     this.search.mouseClicked(mouseX, mouseY, mouseButton);
/*  95 */     if (!flag && this.search.isFocused()) {
/*  96 */       this.query = "";
/*  97 */       updateText();
/*  98 */       updateQueried();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateText() {
/* 103 */     this.search.setText(this.query);
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/* 107 */     Keyboard.enableRepeatEvents(false);
/* 108 */     Config.save();
/*     */   }
/*     */   
/*     */   public void drawScreen(int par1, int par2, float par3) {
/* 112 */     this.accountsgui.drawScreen(par1, par2, par3);
/* 113 */     drawCenteredString(this.fontRenderer, I18n.format("ias.selectaccount", new Object[0]), this.width / 2, 4, -1);
/* 114 */     if (this.loginfailed != null) {
/* 115 */       drawCenteredString(this.fontRenderer, this.loginfailed.getLocalizedMessage(), this.width / 2, this.height - 62, 16737380);
/*     */     }
/* 117 */     this.search.drawTextBox();
/* 118 */     super.drawScreen(par1, par2, par3);
/* 119 */     if (!this.queriedaccounts.isEmpty()) {
/* 120 */       SkinTools.javDrawSkin(8, this.height / 2 - 64 - 16, 64, 128);
/* 121 */       Tools.drawBorderedRect(this.width - 8 - 64, this.height / 2 - 64 - 16, this.width - 8, this.height / 2 + 64 - 16, 2, -5855578, -13421773);
/* 122 */       if (((ExtendedAccountData)this.queriedaccounts.get(this.selectedAccountIndex)).premium == EnumBool.TRUE) {
/* 123 */         drawString(this.fontRenderer, I18n.format("ias.premium", new Object[0]), this.width - 8 - 61, this.height / 2 - 64 - 13, 6618980);
/* 124 */       } else if (((ExtendedAccountData)this.queriedaccounts.get(this.selectedAccountIndex)).premium == EnumBool.FALSE) {
/* 125 */         drawString(this.fontRenderer, I18n.format("ias.notpremium", new Object[0]), this.width - 8 - 61, this.height / 2 - 64 - 13, 16737380);
/*     */       } 
/* 127 */       drawString(this.fontRenderer, I18n.format("ias.timesused", new Object[0]), this.width - 8 - 61, this.height / 2 - 64 - 15 + 12, -1);
/* 128 */       drawString(this.fontRenderer, String.valueOf(((ExtendedAccountData)this.queriedaccounts.get(this.selectedAccountIndex)).useCount), this.width - 8 - 61, this.height / 2 - 64 - 15 + 21, -1);
/* 129 */       if (((ExtendedAccountData)this.queriedaccounts.get(this.selectedAccountIndex)).useCount > 0) {
/* 130 */         drawString(this.fontRenderer, I18n.format("ias.lastused", new Object[0]), this.width - 8 - 61, this.height / 2 - 64 - 15 + 30, -1);
/* 131 */         drawString(this.fontRenderer, JavaTools.getJavaCompat().getFormattedDate(), this.width - 8 - 61, this.height / 2 - 64 - 15 + 39, -1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 137 */     if (button.enabled) {
/* 138 */       if (button.id == 3) {
/* 139 */         escape();
/* 140 */       } else if (button.id == 0) {
/* 141 */         add();
/* 142 */       } else if (button.id == 4) {
/* 143 */         delete();
/* 144 */       } else if (button.id == 1) {
/* 145 */         login(this.selectedAccountIndex);
/* 146 */       } else if (button.id == 2) {
/* 147 */         logino(this.selectedAccountIndex);
/* 148 */       } else if (button.id == 7) {
/* 149 */         edit();
/* 150 */       } else if (button.id == 8) {
/* 151 */         reloadSkins();
/*     */       } else {
/* 153 */         this.accountsgui.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void reloadSkins() {
/* 159 */     Config.save();
/* 160 */     SkinTools.cacheSkins();
/* 161 */     updateShownSkin();
/*     */   }
/*     */   
/*     */   private void escape() {
/* 165 */     this.mc.displayGuiScreen(null);
/*     */   }
/*     */   
/*     */   private void delete() {
/* 169 */     AltDatabase.getInstance().getAlts().remove(getCurrentAsEditable());
/* 170 */     if (this.selectedAccountIndex > 0) {
/* 171 */       this.selectedAccountIndex--;
/*     */     }
/* 173 */     updateQueried();
/* 174 */     updateButtons();
/*     */   }
/*     */   
/*     */   private void add() {
/* 178 */     this.mc.displayGuiScreen(new GuiAddAccount());
/*     */   }
/*     */   
/*     */   private void logino(int selected) {
/* 182 */     ExtendedAccountData data = this.queriedaccounts.get(selected);
/* 183 */     AltManager.getInstance().setUserOffline(data.alias);
/* 184 */     this.loginfailed = null;
/* 185 */     Minecraft.getMinecraft().displayGuiScreen(null);
/* 186 */     ExtendedAccountData current = getCurrentAsEditable();
/* 187 */     ((ExtendedAccountData)Objects.requireNonNull((T)current)).useCount++;
/* 188 */     current.lastused = JavaTools.getJavaCompat().getDate();
/*     */   }
/*     */   
/*     */   private void login(int selected) {
/* 192 */     ExtendedAccountData data = this.queriedaccounts.get(selected);
/* 193 */     this.loginfailed = AltManager.getInstance().setUser(data.user, data.pass);
/* 194 */     if (this.loginfailed == null) {
/* 195 */       Minecraft.getMinecraft().displayGuiScreen(null);
/* 196 */       ExtendedAccountData current = getCurrentAsEditable();
/* 197 */       ((ExtendedAccountData)Objects.requireNonNull((T)current)).premium = EnumBool.TRUE;
/* 198 */       current.useCount++;
/* 199 */       current.lastused = JavaTools.getJavaCompat().getDate();
/* 200 */     } else if (this.loginfailed instanceof me.abHack.features.gui.alts.ias.account.AlreadyLoggedInException) {
/* 201 */       ((ExtendedAccountData)Objects.requireNonNull((T)getCurrentAsEditable())).lastused = JavaTools.getJavaCompat().getDate();
/* 202 */     } else if (HttpTools.ping("https://minecraft.net")) {
/* 203 */       ((ExtendedAccountData)Objects.requireNonNull((T)getCurrentAsEditable())).premium = EnumBool.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void edit() {
/* 208 */     this.mc.displayGuiScreen(new GuiEditAccount(this.selectedAccountIndex));
/*     */   }
/*     */   
/*     */   private void updateQueried() {
/* 212 */     this.queriedaccounts = convertData();
/* 213 */     if (!this.query.equals(I18n.format("ias.search", new Object[0])) && !this.query.equals(""))
/* 214 */       for (int i = 0; i < this.queriedaccounts.size(); i++) {
/* 215 */         if (!((ExtendedAccountData)this.queriedaccounts.get(i)).alias.contains(this.query) && ConfigValues.CASESENSITIVE) {
/* 216 */           this.queriedaccounts.remove(i);
/* 217 */           i--;
/*     */         
/*     */         }
/* 220 */         else if (!((ExtendedAccountData)this.queriedaccounts.get(i)).alias.toLowerCase().contains(this.query.toLowerCase()) && !ConfigValues.CASESENSITIVE) {
/*     */           
/* 222 */           this.queriedaccounts.remove(i);
/* 223 */           i--;
/*     */         } 
/*     */       }  
/* 226 */     if (!this.queriedaccounts.isEmpty()) {
/* 227 */       while (this.selectedAccountIndex >= this.queriedaccounts.size()) {
/* 228 */         this.selectedAccountIndex--;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void keyTyped(char character, int keyIndex) {
/* 234 */     if (keyIndex == 200 && !this.queriedaccounts.isEmpty()) {
/* 235 */       if (this.selectedAccountIndex > 0) {
/* 236 */         this.selectedAccountIndex--;
/*     */       }
/* 238 */     } else if (keyIndex == 208 && !this.queriedaccounts.isEmpty()) {
/* 239 */       if (this.selectedAccountIndex < this.queriedaccounts.size() - 1) {
/* 240 */         this.selectedAccountIndex++;
/*     */       }
/* 242 */     } else if (keyIndex == 1) {
/* 243 */       escape();
/* 244 */     } else if (keyIndex == 211 && this.delete.enabled) {
/* 245 */       delete();
/* 246 */     } else if (character == '+') {
/* 247 */       add();
/* 248 */     } else if (character == '/' && this.edit.enabled) {
/* 249 */       edit();
/* 250 */     } else if (!this.search.isFocused() && keyIndex == 19) {
/* 251 */       reloadSkins();
/* 252 */     } else if (keyIndex == 28 && !this.search.isFocused() && (this.login.enabled || this.loginoffline.enabled)) {
/* 253 */       if ((Keyboard.isKeyDown(54) || Keyboard.isKeyDown(42)) && this.loginoffline.enabled) {
/* 254 */         logino(this.selectedAccountIndex);
/* 255 */       } else if (this.login.enabled) {
/* 256 */         login(this.selectedAccountIndex);
/*     */       } 
/* 258 */     } else if (keyIndex == 14) {
/* 259 */       if (this.search.isFocused() && this.query.length() > 0) {
/* 260 */         this.query = this.query.substring(0, this.query.length() - 1);
/* 261 */         updateText();
/* 262 */         updateQueried();
/*     */       } 
/* 264 */     } else if (keyIndex == 63) {
/* 265 */       reloadSkins();
/* 266 */     } else if (character != '\000' && this.search.isFocused()) {
/* 267 */       if (keyIndex == 28) {
/* 268 */         this.search.setFocused(false);
/* 269 */         updateText();
/* 270 */         updateQueried();
/*     */         return;
/*     */       } 
/* 273 */       this.query += character;
/* 274 */       updateText();
/* 275 */       updateQueried();
/*     */     } 
/*     */   }
/*     */   
/*     */   private ArrayList<ExtendedAccountData> convertData() {
/* 280 */     ArrayList<AccountData> tmp = (ArrayList<AccountData>)AltDatabase.getInstance().getAlts().clone();
/* 281 */     ArrayList<ExtendedAccountData> converted = new ArrayList<>();
/* 282 */     int index = 0;
/* 283 */     for (AccountData data : tmp) {
/* 284 */       if (data instanceof ExtendedAccountData) {
/* 285 */         converted.add((ExtendedAccountData)data);
/*     */       } else {
/* 287 */         converted.add(new ExtendedAccountData(EncryptionTools.decode(data.user), EncryptionTools.decode(data.pass), data.alias));
/* 288 */         AltDatabase.getInstance().getAlts().set(index, new ExtendedAccountData(EncryptionTools.decode(data.user), EncryptionTools.decode(data.pass), data.alias));
/*     */       } 
/* 290 */       index++;
/*     */     } 
/* 292 */     return converted;
/*     */   }
/*     */   
/*     */   private ArrayList<AccountData> getAccountList() {
/* 296 */     return AltDatabase.getInstance().getAlts();
/*     */   }
/*     */   
/*     */   private ExtendedAccountData getCurrentAsEditable() {
/* 300 */     for (AccountData dat : getAccountList()) {
/* 301 */       if (!(dat instanceof ExtendedAccountData) || !dat.equals(this.queriedaccounts.get(this.selectedAccountIndex)))
/*     */         continue; 
/* 303 */       return (ExtendedAccountData)dat;
/*     */     } 
/* 305 */     return null;
/*     */   }
/*     */   
/*     */   private void updateButtons() {
/* 309 */     this.login.enabled = (!this.queriedaccounts.isEmpty() && !EncryptionTools.decode(((ExtendedAccountData)this.queriedaccounts.get(this.selectedAccountIndex)).pass).equals(""));
/* 310 */     this.loginoffline.enabled = !this.queriedaccounts.isEmpty();
/* 311 */     this.delete.enabled = !this.queriedaccounts.isEmpty();
/* 312 */     this.edit.enabled = !this.queriedaccounts.isEmpty();
/* 313 */     this.reloadskins.enabled = !AltDatabase.getInstance().getAlts().isEmpty();
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot {
/*     */     public List(Minecraft mcIn) {
/* 319 */       super(mcIn, GuiAccountSelector.this.width, GuiAccountSelector.this.height, 32, GuiAccountSelector.this.height - 64, 14);
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 323 */       return GuiAccountSelector.this.queriedaccounts.size();
/*     */     }
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 327 */       GuiAccountSelector.this.selectedAccountIndex = slotIndex;
/* 328 */       GuiAccountSelector.this.updateButtons();
/* 329 */       if (isDoubleClick && GuiAccountSelector.this.login.enabled) {
/* 330 */         GuiAccountSelector.this.login(slotIndex);
/*     */       }
/*     */     }
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 335 */       return (slotIndex == GuiAccountSelector.this.selectedAccountIndex);
/*     */     }
/*     */     
/*     */     protected int getContentHeight() {
/* 339 */       return GuiAccountSelector.this.queriedaccounts.size() * 14;
/*     */     }
/*     */     
/*     */     protected void drawBackground() {
/* 343 */       GuiAccountSelector.this.drawDefaultBackground();
/*     */     }
/*     */     
/*     */     protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
/* 347 */       ExtendedAccountData data = GuiAccountSelector.this.queriedaccounts.get(slotIndex);
/* 348 */       String s = data.alias;
/* 349 */       if (StringUtils.isEmpty(s)) {
/* 350 */         s = I18n.format("ias.alt", new Object[0]) + " " + (slotIndex + 1);
/*     */       }
/* 352 */       int color = 16777215;
/* 353 */       if (Minecraft.getMinecraft().getSession().getUsername().equals(data.alias)) {
/* 354 */         color = 65280;
/*     */       }
/* 356 */       GuiAccountSelector.this.drawString(GuiAccountSelector.this.fontRenderer, s, xPos + 2, yPos + 1, color);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\gui\GuiAccountSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
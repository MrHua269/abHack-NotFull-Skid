/*     */ package me.abHack.features.gui.easiervillagertrading;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiMerchant;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BetterGuiMerchant
/*     */   extends GuiMerchant
/*     */ {
/*  32 */   private static final ResourceLocation icons = new ResourceLocation("easiervillagertrading", "textures/icons.png");
/*  33 */   private final int lineHeight = 18;
/*  34 */   private final int titleDistance = 20;
/*  35 */   private final int secondBuyItemXpos = 18;
/*  36 */   private final int okNokXpos = 40;
/*  37 */   private final int textXpos = 85;
/*     */   private int xBase;
/*  39 */   private int scrollCount = 0;
/*     */   
/*     */   BetterGuiMerchant(InventoryPlayer inv, GuiMerchant template, World world) {
/*  42 */     super(inv, template.getMerchant(), world);
/*  43 */     if (ConfigurationHandler.showLeft()) {
/*  44 */       this.xBase = -ConfigurationHandler.leftPixelOffset();
/*  45 */       if (this.xBase == 0)
/*  46 */         this.xBase = -getXSize(); 
/*     */     } else {
/*  48 */       this.xBase = getXSize() + 5;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/*  53 */     super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  58 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  64 */     super.drawGuiContainerForegroundLayer(mouseX, mouseY);
/*  65 */     MerchantRecipeList trades = getMerchant().getRecipes((EntityPlayer)this.mc.player);
/*  66 */     if (trades == null)
/*     */       return; 
/*  68 */     int topAdjust = getTopAdjust(trades.size());
/*  69 */     String s = trades.size() + " trades";
/*  70 */     this.fontRenderer.drawString(s, this.xBase + 40, -topAdjust, 16711935);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     RenderHelper.enableGUIStandardItemLighting();
/*  78 */     int sellItemXpos = 60;
/*  79 */     int firstBuyItemXpos = 0;
/*  80 */     for (int i = 0; i < trades.size() - this.scrollCount; i++) {
/*  81 */       NBTTagList enchantments; MerchantRecipe trade = (MerchantRecipe)trades.get(i + this.scrollCount);
/*  82 */       ItemStack i1 = trade.getItemToBuy();
/*  83 */       ItemStack i2 = trade.hasSecondItemToBuy() ? trade.getSecondItemToBuy() : null;
/*  84 */       ItemStack o1 = trade.getItemToSell();
/*     */ 
/*     */ 
/*     */       
/*  88 */       drawItem(i1, this.xBase + firstBuyItemXpos, i * 18 - topAdjust + 20);
/*  89 */       drawItem(i2, this.xBase + 18, i * 18 - topAdjust + 20);
/*  90 */       drawItem(o1, this.xBase + sellItemXpos, i * 18 - topAdjust + 20);
/*     */ 
/*     */ 
/*     */       
/*  94 */       if (o1.getItem() instanceof ItemEnchantedBook) {
/*  95 */         enchantments = ItemEnchantedBook.getEnchantments(o1);
/*     */       } else {
/*  97 */         enchantments = o1.getEnchantmentTagList();
/*     */       } 
/*  99 */       if (enchantments != null) {
/* 100 */         StringBuilder enchants = new StringBuilder();
/* 101 */         for (int t = 0; t < enchantments.tagCount(); t++) {
/* 102 */           int m = enchantments.getCompoundTagAt(t).getShort("id");
/* 103 */           int k = enchantments.getCompoundTagAt(t).getShort("lvl");
/*     */           
/* 105 */           Enchantment enchant = Enchantment.getEnchantmentByID(m);
/* 106 */           if (enchant != null) {
/* 107 */             if (t > 0)
/* 108 */               enchants.append(", "); 
/* 109 */             enchants.append(enchant.getTranslatedName(k));
/*     */           } 
/*     */         } 
/* 112 */         String shownEnchants = enchants.toString();
/* 113 */         if (this.xBase < 0) {
/* 114 */           shownEnchants = this.fontRenderer.trimStringToWidth(shownEnchants, -this.xBase - 85 - 5);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 119 */         this.fontRenderer.drawString(shownEnchants, this.xBase + 85, i * 18 - topAdjust + 24, 16776960);
/*     */       } 
/*     */     } 
/* 122 */     RenderHelper.disableStandardItemLighting();
/*     */     
/* 124 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 125 */     GlStateManager.enableBlend();
/* 126 */     this.mc.getTextureManager().bindTexture(icons);
/* 127 */     int arrowX = this.xBase + 40;
/* 128 */     int[] tradeState = new int[trades.size()]; int j;
/* 129 */     for (j = 0; j < trades.size() - this.scrollCount; j++) {
/* 130 */       int y = j * 18 - topAdjust + 20;
/* 131 */       MerchantRecipe trade = (MerchantRecipe)trades.get(j + this.scrollCount);
/* 132 */       if (!trade.isRecipeDisabled() && 
/* 133 */         inputSlotsAreEmpty() && 
/* 134 */         hasEnoughItemsInInventory(trade) && 
/* 135 */         canReceiveOutput(trade.getItemToSell())) {
/* 136 */         drawTexturedModalRect(arrowX, y, 108, 36, 18, 18);
/* 137 */         tradeState[j] = 0;
/* 138 */       } else if (!trade.isRecipeDisabled()) {
/* 139 */         drawTexturedModalRect(arrowX, y, 90, 54, 18, 18);
/* 140 */         tradeState[j] = 1;
/*     */       } else {
/* 142 */         drawTexturedModalRect(arrowX, y, 216, 54, 18, 18);
/* 143 */         tradeState[j] = 2;
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     if (this.scrollCount > 0) {
/* 148 */       drawTexturedModalRect(this.xBase + firstBuyItemXpos, -topAdjust - 3, 162, 36, 18, 18);
/*     */     }
/* 150 */     if ((trades.size() - 1 - this.scrollCount) * 18 + 40 >= this.height)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       drawTexturedModalRect(this.xBase + 18, -topAdjust - 3, 18, 36, 18, 18);
/*     */     }
/*     */ 
/*     */     
/* 163 */     for (j = 0; j < trades.size(); j++) {
/* 164 */       int y = j * 18 - topAdjust + 20;
/* 165 */       MerchantRecipe trade = (MerchantRecipe)trades.get(j);
/* 166 */       ItemStack i1 = trade.getItemToBuy();
/* 167 */       ItemStack i2 = trade.hasSecondItemToBuy() ? trade.getSecondItemToBuy() : null;
/* 168 */       ItemStack o1 = trade.getItemToSell();
/* 169 */       drawTooltip(i1, this.xBase + firstBuyItemXpos, y, mouseX, mouseY);
/* 170 */       drawTooltip(i2, this.xBase + 18, y, mouseX, mouseY);
/* 171 */       drawTooltip(o1, this.xBase + sellItemXpos, y, mouseX, mouseY);
/* 172 */       switch (tradeState[j]) {
/*     */         case 0:
/* 174 */           drawTooltip(I18n.format("msg.cantrade", (Object[])null), arrowX, y, mouseX, mouseY);
/*     */           break;
/*     */         case 1:
/* 177 */           drawTooltip(I18n.format("msg.notradeinv", (Object[])null), arrowX, y, mouseX, mouseY);
/*     */           break;
/*     */         case 2:
/* 180 */           drawTooltip(I18n.format("msg.tradelocked", (Object[])null), arrowX, y, mouseX, mouseY);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private int getTopAdjust(int numTrades) {
/* 186 */     int topAdjust = (numTrades * 18 + 20 - this.ySize) / 2;
/* 187 */     if (topAdjust < 0)
/* 188 */       topAdjust = 0; 
/* 189 */     getClass(); if (topAdjust > this.guiTop - 20 / 2) {
/* 190 */       getClass(); topAdjust = this.guiTop - 20 / 2;
/* 191 */     }  return topAdjust;
/*     */   }
/*     */   
/*     */   private void drawItem(ItemStack stack, int x, int y) {
/* 195 */     if (stack == null)
/*     */       return; 
/* 197 */     this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
/* 198 */     this.itemRender.renderItemOverlays(this.fontRenderer, stack, x, y);
/*     */   }
/*     */   
/*     */   private void drawTooltip(ItemStack stack, int x, int y, int mousex, int mousey) {
/* 202 */     if (stack == null)
/*     */       return; 
/* 204 */     mousex -= this.guiLeft;
/* 205 */     mousey -= this.guiTop;
/* 206 */     if (mousex >= x && mousex <= x + 16 && mousey >= y && mousey <= y + 16)
/* 207 */       renderToolTip(stack, mousex, mousey); 
/*     */   }
/*     */   
/*     */   private void drawTooltip(String s, int x, int y, int mousex, int mousey) {
/* 211 */     mousex -= this.guiLeft;
/* 212 */     mousey -= this.guiTop;
/* 213 */     if (mousex >= x && mousex <= x + 16 && mousey >= y && mousey <= y + 16) {
/* 214 */       drawHoveringText(s, mousex, mousey);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 220 */     if (mouseButton == 0 && mouseX - this.guiLeft >= this.xBase && mouseX - this.guiLeft <= this.xBase + 85) {
/*     */ 
/*     */ 
/*     */       
/* 224 */       MerchantRecipeList trades = getMerchant().getRecipes((EntityPlayer)this.mc.player);
/* 225 */       if (trades == null)
/*     */         return; 
/* 227 */       int numTrades = trades.size();
/* 228 */       int topAdjust = getTopAdjust(numTrades);
/* 229 */       int yPixel = mouseY + topAdjust - this.guiTop - 20;
/* 230 */       if (yPixel >= 0) {
/* 231 */         int tradeIndex = yPixel / 18 + this.scrollCount;
/*     */         
/* 233 */         if (tradeIndex >= 0 && tradeIndex < numTrades) {
/* 234 */           GuiButton myNextButton = this.buttonList.get(0);
/* 235 */           GuiButton myPrevButton = this.buttonList.get(1); int i;
/* 236 */           for (i = 0; i < numTrades; i++)
/* 237 */             actionPerformed(myPrevButton); 
/* 238 */           for (i = 0; i < tradeIndex; i++)
/* 239 */             actionPerformed(myNextButton); 
/* 240 */           MerchantRecipe recipe = (MerchantRecipe)trades.get(tradeIndex);
/* 241 */           while (!recipe.isRecipeDisabled() && 
/* 242 */             inputSlotsAreEmpty() && 
/* 243 */             hasEnoughItemsInInventory(recipe) && 
/* 244 */             canReceiveOutput(recipe.getItemToSell())) {
/* 245 */             transact(recipe);
/* 246 */             if (!isShiftKeyDown()) {
/*     */               break;
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 253 */       } else if (mouseX - this.guiLeft < this.xBase + 18) {
/* 254 */         mouseScrolled(1);
/* 255 */       } else if (mouseX - this.guiLeft < this.xBase + 40) {
/* 256 */         mouseScrolled(-1);
/*     */       } 
/*     */     } else {
/*     */       
/* 260 */       super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 266 */     super.handleMouseInput();
/* 267 */     int i = Mouse.getEventDWheel();
/* 268 */     if (i != 0) {
/* 269 */       mouseScrolled((i < 0) ? -1 : 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseScrolled(int delta) {
/*     */     MerchantRecipeList trades;
/* 276 */     if ((trades = getMerchant().getRecipes((EntityPlayer)this.mc.player)) != null) {
/* 277 */       this.scrollCount -= delta;
/* 278 */       while ((trades.size() - this.scrollCount) * 18 + 40 < this.height) {
/* 279 */         this.scrollCount--;
/*     */       }
/* 281 */       if (this.scrollCount < 0)
/* 282 */         this.scrollCount = 0; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean inputSlotsAreEmpty() {
/* 287 */     return (
/* 288 */       !this.inventorySlots.getSlot(0).getHasStack() &&
/* 289 */       !this.inventorySlots.getSlot(1).getHasStack() &&
/* 290 */       !this.inventorySlots.getSlot(2).getHasStack());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasEnoughItemsInInventory(MerchantRecipe recipe) {
/* 295 */     if (!hasEnoughItemsInInventory(recipe.getItemToBuy()))
/* 296 */       return false; 
/* 297 */     return (!recipe.hasSecondItemToBuy() || hasEnoughItemsInInventory(recipe.getSecondItemToBuy()));
/*     */   }
/*     */   
/*     */   private boolean hasEnoughItemsInInventory(ItemStack stack) {
/* 301 */     int remaining = stack.getCount();
/* 302 */     for (int i = this.inventorySlots.inventorySlots.size() - 36; i < this.inventorySlots.inventorySlots.size(); i++) {
/* 303 */       ItemStack invstack = this.inventorySlots.getSlot(i).getStack();
/* 304 */       if (invstack != null) {
/*     */         
/* 306 */         if (areItemStacksMergable(stack, invstack))
/*     */         {
/* 308 */           remaining -= invstack.getCount();
/*     */         }
/* 310 */         if (remaining <= 0)
/* 311 */           return true; 
/*     */       } 
/* 313 */     }  return false;
/*     */   }
/*     */   
/*     */   private boolean canReceiveOutput(ItemStack stack) {
/* 317 */     int remaining = stack.getCount();
/* 318 */     for (int i = this.inventorySlots.inventorySlots.size() - 36; i < this.inventorySlots.inventorySlots.size(); i++) {
/* 319 */       ItemStack invstack = this.inventorySlots.getSlot(i).getStack();
/* 320 */       if (invstack == null || invstack.isEmpty())
/*     */       {
/* 322 */         return true;
/*     */       }
/* 324 */       if (areItemStacksMergable(stack, invstack) && stack
/* 325 */         .getMaxStackSize() >= stack.getCount() + invstack.getCount())
/*     */       {
/* 327 */         remaining -= invstack.getMaxStackSize() - invstack.getCount();
/*     */       }
/* 329 */       if (remaining <= 0)
/* 330 */         return true; 
/*     */     } 
/* 332 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void transact(MerchantRecipe recipe) {
/* 337 */     int putback1 = -1;
/* 338 */     int putback0 = fillSlot(0, recipe.getItemToBuy());
/* 339 */     if (recipe.hasSecondItemToBuy()) {
/* 340 */       putback1 = fillSlot(1, recipe.getSecondItemToBuy());
/*     */     }
/* 342 */     getslot(recipe.getItemToSell(), new int[] { putback0, putback1 });
/*     */     
/* 344 */     if (putback0 != -1) {
/* 345 */       slotClick(0);
/* 346 */       slotClick(putback0);
/*     */     } 
/* 348 */     if (putback1 != -1) {
/* 349 */       slotClick(1);
/* 350 */       slotClick(putback1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fillSlot(int slot, ItemStack stack) {
/* 361 */     int remaining = stack.getCount();
/* 362 */     for (int i = this.inventorySlots.inventorySlots.size() - 36; i < this.inventorySlots.inventorySlots.size(); i++) {
/* 363 */       ItemStack invstack = this.inventorySlots.getSlot(i).getStack();
/* 364 */       if (invstack != null) {
/*     */         
/* 366 */         boolean needPutBack = false;
/* 367 */         if (areItemStacksMergable(stack, invstack)) {
/* 368 */           if (stack.getCount() + invstack.getCount() > stack.getMaxStackSize())
/* 369 */             needPutBack = true; 
/* 370 */           remaining -= invstack.getCount();
/*     */           
/* 372 */           slotClick(i);
/* 373 */           slotClick(slot);
/*     */         } 
/* 375 */         if (needPutBack) {
/* 376 */           slotClick(i);
/*     */         }
/* 378 */         if (remaining <= 0) {
/* 379 */           return (remaining < 0) ? i : -1;
/*     */         }
/*     */       } 
/*     */     } 
/* 383 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean areItemStacksMergable(ItemStack a, ItemStack b) {
/* 387 */     if (a == null || b == null)
/* 388 */       return false; 
/* 389 */     return (a.getItem() == b.getItem() && (
/* 390 */       !a.getHasSubtypes() || a.getItemDamage() == b.getItemDamage()) && 
/* 391 */       ItemStack.areItemStackTagsEqual(a, b));
/*     */   }
/*     */   
/*     */   private void getslot(ItemStack stack, int... forbidden) {
/* 395 */     int remaining = stack.getCount();
/* 396 */     slotClick(2); int i;
/* 397 */     for (i = this.inventorySlots.inventorySlots.size() - 36; i < this.inventorySlots.inventorySlots.size(); i++) {
/* 398 */       ItemStack invstack = this.inventorySlots.getSlot(i).getStack();
/* 399 */       if (invstack != null && !invstack.isEmpty()) {
/*     */ 
/*     */         
/* 402 */         if (areItemStacksMergable(stack, invstack) && invstack
/* 403 */           .getCount() < invstack.getMaxStackSize()) {
/*     */ 
/*     */           
/* 406 */           remaining -= invstack.getMaxStackSize() - invstack.getCount();
/* 407 */           slotClick(i);
/*     */         } 
/* 409 */         if (remaining <= 0) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 414 */     for (i = this.inventorySlots.inventorySlots.size() - 36; i < this.inventorySlots.inventorySlots.size(); i++) {
/* 415 */       boolean isForbidden = false;
/* 416 */       for (int f : forbidden) {
/* 417 */         if (i == f) {
/* 418 */           isForbidden = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 422 */       if (!isForbidden) {
/*     */         
/* 424 */         ItemStack invstack = this.inventorySlots.getSlot(i).getStack();
/* 425 */         if (invstack == null || invstack.isEmpty()) {
/* 426 */           slotClick(i);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void slotClick(int slot) {
/* 435 */     this.mc.playerController.windowClick(this.mc.player.openContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\easiervillagertrading\BetterGuiMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
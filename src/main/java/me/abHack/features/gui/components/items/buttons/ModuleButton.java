/*     */ package me.abHack.features.gui.components.items.buttons;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.abHack.features.gui.OyVeyGui;
/*     */ import me.abHack.features.gui.components.Component;
/*     */ import me.abHack.features.gui.components.items.DescriptionDisplay;
/*     */ import me.abHack.features.gui.components.items.Item;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.client.HUD;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class ModuleButton
/*     */   extends Button
/*     */ {
/*     */   private final Module module;
/*     */   private int logs;
/*  25 */   private List<Item> items = new ArrayList<>();
/*     */   private boolean subOpen;
/*     */   
/*     */   public ModuleButton(Module module) {
/*  29 */     super(module.getName());
/*  30 */     this.module = module;
/*  31 */     this.logs = 0;
/*  32 */     initSettings();
/*     */   }
/*     */   
/*     */   public static float fa(float var0) {
/*  36 */     if ((var0 %= 360.0F) >= 180.0F) {
/*  37 */       var0 -= 360.0F;
/*     */     }
/*     */     
/*  40 */     if (var0 < -180.0F) {
/*  41 */       var0 += 360.0F;
/*     */     }
/*     */     
/*  44 */     return var0;
/*     */   }
/*     */   
/*     */   public static void drawModalRect(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
/*  48 */     Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
/*     */   }
/*     */   
/*     */   public void initSettings() {
/*  52 */     ArrayList<Item> newItems = new ArrayList<>();
/*  53 */     if (!this.module.getSettings().isEmpty()) {
/*  54 */       for (Setting setting : this.module.getSettings()) {
/*  55 */         if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
/*  56 */           newItems.add(new BooleanButton(setting));
/*     */         }
/*  58 */         if (setting.getValue() instanceof me.abHack.features.setting.Bind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
/*  59 */           newItems.add(new BindButton(setting));
/*     */         }
/*  61 */         if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
/*  62 */           newItems.add(new StringButton(setting));
/*     */         }
/*  64 */         if (setting.isNumberSetting()) {
/*  65 */           if (setting.hasRestriction()) {
/*  66 */             newItems.add(new Slider(setting));
/*     */             continue;
/*     */           } 
/*  69 */           newItems.add(new UnlimitedSlider(setting));
/*     */         } 
/*  71 */         if (!setting.isEnumSetting())
/*  72 */           continue;  newItems.add(new EnumButton(setting));
/*     */       } 
/*     */     }
/*  75 */     newItems.add(new BindButton(this.module.getSettingByName("Keybind")));
/*  76 */     this.items = newItems;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  81 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*  82 */     if (!this.items.isEmpty()) {
/*  83 */       if (((Boolean)HUD.INSTANCE.magenDavid.getValue()).booleanValue()) {
/*  84 */         GlStateManager.pushMatrix();
/*  85 */         GlStateManager.enableBlend();
/*  86 */         Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/logs.png"));
/*  87 */         GlStateManager.translate(getX() + getWidth() - 6.7F, getY() + 7.7F - 0.3F, 0.0F);
/*  88 */         GlStateManager.rotate(fa(this.logs), 0.0F, 0.0F, 1.0F);
/*  89 */         drawModalRect(-5, -5, 0.0F, 0.0F, 10, 10, 10, 10, 10.0F, 10.0F);
/*  90 */         GlStateManager.disableBlend();
/*  91 */         GlStateManager.popMatrix();
/*     */       } 
/*  93 */       if (this.subOpen) {
/*  94 */         float height = 1.0F;
/*  95 */         this.logs += 5;
/*  96 */         for (Item item : this.items) {
/*  97 */           Component.counter1[0] = Component.counter1[0] + 1;
/*  98 */           if (!item.isHidden()) {
/*  99 */             item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
/* 100 */             item.setHeight(15);
/* 101 */             item.setWidth(this.width - 9);
/* 102 */             item.drawScreen(mouseX, mouseY, partialTicks);
/*     */           } 
/* 104 */           item.update();
/*     */         } 
/*     */       } else {
/* 107 */         this.logs = 0;
/*     */       } 
/* 109 */     }  if (isHovering(mouseX, mouseY)) {
/* 110 */       DescriptionDisplay descriptionDisplay = OyVeyGui.getInstance().getDescriptionDisplay();
/* 111 */       descriptionDisplay.setDescription(this.module.getDescription());
/* 112 */       descriptionDisplay.setLocation((mouseX + 2), (mouseY + 1));
/* 113 */       descriptionDisplay.setDraw(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 119 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 120 */     if (!this.items.isEmpty()) {
/* 121 */       if (mouseButton == 1 && isHovering(mouseX, mouseY)) {
/* 122 */         this.subOpen = !this.subOpen;
/* 123 */         mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */       } 
/* 125 */       if (this.subOpen) {
/* 126 */         for (Item item : this.items) {
/* 127 */           if (item.isHidden())
/* 128 */             continue;  item.mouseClicked(mouseX, mouseY, mouseButton);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onKeyTyped(char typedChar, int keyCode) {
/* 136 */     super.onKeyTyped(typedChar, keyCode);
/* 137 */     if (!this.items.isEmpty() && this.subOpen) {
/* 138 */       for (Item item : this.items) {
/* 139 */         if (item.isHidden())
/* 140 */           continue;  item.onKeyTyped(typedChar, keyCode);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 147 */     if (this.subOpen) {
/* 148 */       int height = 14;
/* 149 */       for (Item item : this.items) {
/* 150 */         if (item.isHidden())
/* 151 */           continue;  height += item.getHeight() + 1;
/*     */       } 
/* 153 */       return height + 2;
/*     */     } 
/* 155 */     return 14;
/*     */   }
/*     */   
/*     */   public Module getModule() {
/* 159 */     return this.module;
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggle() {
/* 164 */     this.module.toggle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getState() {
/* 171 */     return this.module.isEnabled();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\ModuleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
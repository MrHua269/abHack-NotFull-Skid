/*     */ package me.abHack.features.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Random;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.Feature;
/*     */ import me.abHack.features.gui.components.Component;
/*     */ import me.abHack.features.gui.components.items.DescriptionDisplay;
/*     */ import me.abHack.features.gui.components.items.Item;
/*     */ import me.abHack.features.gui.components.items.buttons.Button;
/*     */ import me.abHack.features.gui.components.items.buttons.ModuleButton;
/*     */ import me.abHack.features.gui.particle.ParticleSystem;
/*     */ import me.abHack.features.gui.particle.Snow;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.client.ClickGui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OyVeyGui
/*     */   extends GuiScreen
/*     */ {
/*     */   private static final DescriptionDisplay descriptionDisplay;
/*  28 */   private static OyVeyGui INSTANCE = new OyVeyGui(); static {
/*  29 */     descriptionDisplay = new DescriptionDisplay("", 0.0F, 0.0F);
/*     */   }
/*     */   
/*  32 */   private final ArrayList<Snow> _snowList = new ArrayList<>();
/*  33 */   private final ArrayList<Component> components = new ArrayList<>();
/*     */   public ParticleSystem particleSystem;
/*     */   
/*     */   public OyVeyGui() {
/*  37 */     setInstance();
/*  38 */     load();
/*     */   }
/*     */   
/*     */   public static OyVeyGui getInstance() {
/*  42 */     if (INSTANCE == null) {
/*  43 */       INSTANCE = new OyVeyGui();
/*     */     }
/*  45 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static OyVeyGui getClickGui() {
/*  49 */     return getInstance();
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  53 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   private void load() {
/*  57 */     int x = -84;
/*  58 */     Random random = new Random();
/*     */     
/*  60 */     for (int i = 0; i < 100; i++) {
/*  61 */       for (int y = 0; y < 3; y++) {
/*  62 */         Snow snow = new Snow(25 * i, y * -50, random.nextInt(3) + 1, random.nextInt(2) + 1);
/*  63 */         this._snowList.add(snow);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  68 */     for (Module.Category category : OyVey.moduleManager.getCategories()) {
/*  69 */       x += 90; this.components.add(new Component(category.getName(), x, 4, true)
/*     */           {
/*     */             public void setupItems()
/*     */             {
/*  73 */               counter1 = new int[] { 1 };
/*  74 */               OyVey.moduleManager.getModulesByCategory(category).forEach(module -> {
/*     */                     if (!module.hidden) {
/*     */                       addButton((Button)new ModuleButton(module));
/*     */                     }
/*     */                   });
/*     */             }
/*     */           });
/*     */     } 
/*  82 */     this.components.forEach(components -> components.getItems().sort(Comparator.comparing(Feature::getName)));
/*     */   }
/*     */   
/*     */   public void updateModule(Module module) {
/*  86 */     for (Component component : this.components) {
/*  87 */       for (Item item : component.getItems()) {
/*  88 */         if (!(item instanceof ModuleButton))
/*  89 */           continue;  ModuleButton button = (ModuleButton)item;
/*  90 */         Module mod = button.getModule();
/*  91 */         if (module == null || !module.equals(mod))
/*  92 */           continue;  button.initSettings();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  98 */     ClickGui clickGui = ClickGui.INSTANCE;
/*  99 */     descriptionDisplay.setDraw(false);
/* 100 */     checkMouseWheel();
/* 101 */     drawDefaultBackground();
/* 102 */     this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
/* 103 */     if (descriptionDisplay.shouldDraw() && ((Boolean)clickGui.moduleDescription.getValue()).booleanValue()) {
/* 104 */       descriptionDisplay.drawScreen(mouseX, mouseY, partialTicks);
/*     */     }
/* 106 */     ScaledResolution res = new ScaledResolution(this.mc);
/* 107 */     if (!this._snowList.isEmpty() && ((Boolean)ClickGui.INSTANCE.snowing.getValue()).booleanValue()) {
/* 108 */       this._snowList.forEach(snow -> snow.Update(res));
/*     */     }
/* 110 */     if (this.particleSystem != null && ((Boolean)ClickGui.INSTANCE.particles.getValue()).booleanValue()) {
/* 111 */       this.particleSystem.render(mouseX, mouseY);
/*     */     } else {
/* 113 */       this.particleSystem = new ParticleSystem(new ScaledResolution(this.mc));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
/* 118 */     this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
/* 122 */     this.components.forEach(components -> components.mouseReleased(releaseButton));
/*     */   }
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   public final ArrayList<Component> getComponents() {
/* 130 */     return this.components;
/*     */   }
/*     */   
/*     */   public DescriptionDisplay getDescriptionDisplay() {
/* 134 */     return descriptionDisplay;
/*     */   }
/*     */   
/*     */   public void checkMouseWheel() {
/* 138 */     int dWheel = Mouse.getDWheel();
/* 139 */     if (dWheel < 0) {
/* 140 */       this.components.forEach(component -> component.setY(component.getY() - 10));
/* 141 */     } else if (dWheel > 0) {
/* 142 */       this.components.forEach(component -> component.setY(component.getY() + 10));
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getTextOffset() {
/* 147 */     return -6;
/*     */   }
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) throws IOException {
/* 151 */     super.keyTyped(typedChar, keyCode);
/* 152 */     this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/* 156 */     if (this.particleSystem != null)
/* 157 */       this.particleSystem.update(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\OyVeyGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
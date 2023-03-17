/*     */ package me.abHack.features.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.Feature;
/*     */ import me.abHack.features.gui.OyVeyGui;
/*     */ import me.abHack.features.gui.components.items.Item;
/*     */ import me.abHack.features.gui.components.items.buttons.Button;
/*     */ import me.abHack.features.modules.client.ClickGui;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Component extends Feature {
/*  22 */   public static int[] counter1 = new int[] { 1 };
/*     */   
/*  24 */   private final ArrayList<Item> items = new ArrayList<>();
/*  25 */   private final Minecraft minecraft = Minecraft.getMinecraft();
/*     */   public boolean drag;
/*     */   private int x;
/*     */   private int y;
/*     */   private int x2;
/*     */   private int y2;
/*     */   private int width;
/*     */   private int height;
/*     */   private boolean open;
/*     */   private int angle;
/*     */   
/*     */   public Component(String name, int x, int y, boolean open) {
/*  37 */     super(name);
/*  38 */     this.x = x;
/*  39 */     this.y = y;
/*  40 */     this.width = 88;
/*  41 */     this.height = 18;
/*  42 */     this.angle = 180;
/*  43 */     this.open = open;
/*  44 */     setupItems();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawModalRect(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
/*  49 */     Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
/*     */   }
/*     */   
/*     */   public static void glColor(Color color) {
/*  53 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   public static float calculateRotation(float var0) {
/*  57 */     if ((var0 %= 360.0F) >= 180.0F) {
/*  58 */       var0 -= 360.0F;
/*     */     }
/*     */     
/*  61 */     if (var0 < -180.0F) {
/*  62 */       var0 += 360.0F;
/*     */     }
/*     */     
/*  65 */     return var0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupItems() {}
/*     */   
/*     */   private void drag(int mouseX, int mouseY) {
/*  72 */     if (!this.drag)
/*     */       return; 
/*  74 */     this.x = this.x2 + mouseX;
/*  75 */     this.y = this.y2 + mouseY;
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  79 */     drag(mouseX, mouseY);
/*  80 */     counter1 = new int[] { 1 };
/*  81 */     float totalItemHeight = this.open ? (getTotalItemHeight() - 2.0F) : 0.0F;
/*  82 */     Gui.drawRect(this.x, this.y - 1, this.x + this.width, this.y + this.height - 6, ClickGui.INSTANCE.getGuiColor().getRGB());
/*  83 */     if (this.open)
/*  84 */       RenderUtil.drawRect(this.x, this.y + 12.5F, (this.x + this.width), (this.y + this.height) + totalItemHeight, 1996488704); 
/*  85 */     OyVey.textManager.drawStringWithShadow(getName(), this.x + 3.0F, this.y - 4.0F - OyVeyGui.getClickGui().getTextOffset(), -1);
/*     */     
/*  87 */     if (!this.open) {
/*  88 */       if (this.angle > 0) {
/*  89 */         this.angle -= 6;
/*     */       }
/*  91 */     } else if (this.angle < 180) {
/*  92 */       this.angle += 6;
/*     */     } 
/*  94 */     GlStateManager.pushMatrix();
/*  95 */     GlStateManager.enableBlend();
/*  96 */     glColor(new Color(255, 255, 255, 255));
/*  97 */     this.minecraft.getTextureManager().bindTexture(new ResourceLocation("textures/arrow.png"));
/*  98 */     GlStateManager.translate((getX() + getWidth() - 7), (getY() + 6) - 0.3F, 0.0F);
/*  99 */     GlStateManager.rotate(calculateRotation(this.angle), 0.0F, 0.0F, 1.0F);
/* 100 */     drawModalRect(-5, -5, 0.0F, 0.0F, 10, 10, 10, 10, 10.0F, 10.0F);
/* 101 */     GlStateManager.disableBlend();
/* 102 */     GlStateManager.popMatrix();
/* 103 */     if (this.open) {
/* 104 */       float y = (getY() + getHeight()) - 3.0F;
/* 105 */       for (Item item : getItems()) {
/* 106 */         counter1[0] = counter1[0] + 1;
/* 107 */         if (item.isHidden())
/*     */           continue; 
/* 109 */         item.setLocation(this.x + 2.0F, y);
/* 110 */         item.setWidth(getWidth() - 4);
/* 111 */         item.drawScreen(mouseX, mouseY, partialTicks);
/* 112 */         y += item.getHeight() + 1.5F;
/*     */       } 
/*     */     } 
/* 115 */     if (this.open && (
/* 116 */       (Boolean)ClickGui.INSTANCE.outline.getValue()).booleanValue()) {
/* 117 */       GlStateManager.disableTexture2D();
/* 118 */       GlStateManager.enableBlend();
/* 119 */       GlStateManager.disableAlpha();
/* 120 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 121 */       GlStateManager.shadeModel(7425);
/* 122 */       GL11.glBegin(2);
/* 123 */       GL11.glColor4f(((Integer)ClickGui.INSTANCE.red.getValue()).intValue() / 255.0F, ((Integer)ClickGui.INSTANCE.green.getValue()).intValue() / 255.0F, ((Integer)ClickGui.INSTANCE.blue.getValue()).intValue() / 255.0F, 255.0F);
/* 124 */       GL11.glVertex3f(this.x, this.y - 0.5F, 0.0F);
/* 125 */       GL11.glVertex3f((this.x + this.width), this.y - 0.5F, 0.0F);
/* 126 */       GL11.glVertex3f((this.x + this.width), (this.y + this.height) + totalItemHeight, 0.0F);
/* 127 */       GL11.glVertex3f(this.x, (this.y + this.height) + totalItemHeight, 0.0F);
/* 128 */       GL11.glEnd();
/* 129 */       GlStateManager.shadeModel(7424);
/* 130 */       GlStateManager.disableBlend();
/* 131 */       GlStateManager.enableAlpha();
/* 132 */       GlStateManager.enableTexture2D();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 139 */     if (mouseButton == 0 && isHovering(mouseX, mouseY)) {
/* 140 */       this.x2 = this.x - mouseX;
/* 141 */       this.y2 = this.y - mouseY;
/* 142 */       OyVeyGui.getClickGui().getComponents().forEach(component -> {
/*     */             if (component.drag)
/*     */               component.drag = false; 
/*     */           });
/* 146 */       this.drag = true;
/*     */       return;
/*     */     } 
/* 149 */     if (mouseButton == 1 && isHovering(mouseX, mouseY)) {
/* 150 */       this.open = !this.open;
/* 151 */       mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */       return;
/*     */     } 
/* 154 */     if (!this.open)
/*     */       return; 
/* 156 */     getItems().forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
/*     */   }
/*     */   
/*     */   public void mouseReleased(int releaseButton) {
/* 160 */     if (releaseButton == 0)
/* 161 */       this.drag = false; 
/* 162 */     if (!this.open)
/*     */       return; 
/* 164 */     getItems().forEach(Item::mouseReleased);
/*     */   }
/*     */   
/*     */   public void onKeyTyped(char typedChar, int keyCode) {
/* 168 */     if (!this.open)
/*     */       return; 
/* 170 */     getItems().forEach(item -> item.onKeyTyped(typedChar, keyCode));
/*     */   }
/*     */   
/*     */   public void addButton(Button button) {
/* 174 */     this.items.add(button);
/*     */   }
/*     */   
/*     */   public int getX() {
/* 178 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(int x) {
/* 182 */     this.x = x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 186 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/* 190 */     this.y = y;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 194 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/* 198 */     this.width = width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 202 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(int height) {
/* 206 */     this.height = height;
/*     */   }
/*     */   
/*     */   public final ArrayList<Item> getItems() {
/* 210 */     return this.items;
/*     */   }
/*     */   
/*     */   private boolean isHovering(int mouseX, int mouseY) {
/* 214 */     return (mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight() - (this.open ? 2 : 0));
/*     */   }
/*     */   
/*     */   private float getTotalItemHeight() {
/* 218 */     float height = 0.0F;
/* 219 */     for (Item item : getItems())
/* 220 */       height += item.getHeight() + 1.5F; 
/* 221 */     return height;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\Component.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
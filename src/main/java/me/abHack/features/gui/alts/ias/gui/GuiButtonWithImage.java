/*    */ package me.abHack.features.gui.alts.ias.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiButtonWithImage
/*    */   extends GuiButton {
/* 11 */   private static final ResourceLocation customButtonTextures = new ResourceLocation("accswitcher:textures/gui/custombutton.png");
/*    */   
/*    */   public GuiButtonWithImage(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/* 14 */     super(buttonId, x, y, widthIn, heightIn, buttonText);
/*    */   }
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
/* 18 */     if (this.visible) {
/* 19 */       FontRenderer fontrenderer = mc.fontRenderer;
/* 20 */       mc.getTextureManager().bindTexture(customButtonTextures);
/* 21 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 22 */       this.hovered = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
/* 23 */       int k = getHoverState(this.hovered);
/* 24 */       GlStateManager.enableBlend();
/* 25 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 26 */       GlStateManager.blendFunc(770, 771);
/* 27 */       drawTexturedModalRect(this.x, this.y, 0, 46 + k * 20, this.width / 2, this.height);
/* 28 */       drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
/* 29 */       mouseDragged(mc, mouseX, mouseY);
/* 30 */       int l = 14737632;
/* 31 */       if (this.packedFGColour != 0) {
/* 32 */         l = this.packedFGColour;
/* 33 */       } else if (!this.enabled) {
/* 34 */         l = 10526880;
/* 35 */       } else if (this.hovered) {
/* 36 */         l = 16777120;
/*    */       } 
/* 38 */       drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, l);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\gui\GuiButtonWithImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
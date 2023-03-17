/*    */ package me.abHack.features.gui.alts.ias.tools;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import javax.imageio.ImageIO;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class SkinRender
/*    */ {
/*    */   private final File file;
/*    */   private final TextureManager textureManager;
/*    */   private DynamicTexture previewTexture;
/*    */   private ResourceLocation resourceLocation;
/*    */   
/*    */   public SkinRender(TextureManager textureManager, File file) {
/* 20 */     this.textureManager = textureManager;
/* 21 */     this.file = file;
/*    */   }
/*    */   
/*    */   private boolean loadPreview() {
/*    */     try {
/* 26 */       BufferedImage image = ImageIO.read(this.file);
/* 27 */       this.previewTexture = new DynamicTexture(image);
/* 28 */       this.resourceLocation = this.textureManager.getDynamicTextureLocation("ias", this.previewTexture);
/* 29 */       return true;
/* 30 */     } catch (IOException e) {
/* 31 */       e.printStackTrace();
/* 32 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawImage(int xPos, int yPos, int width, int height) {
/* 37 */     if (this.previewTexture == null && !loadPreview()) {
/* 38 */       System.out.println("Failure to load preview.");
/*    */       return;
/*    */     } 
/* 41 */     this.previewTexture.updateDynamicTexture();
/* 42 */     this.textureManager.bindTexture(this.resourceLocation);
/* 43 */     Gui.drawModalRectWithCustomSizedTexture(xPos, yPos, 0.0F, 0.0F, width, height, 64.0F, 128.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\tools\SkinRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
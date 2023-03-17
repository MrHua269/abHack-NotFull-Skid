/*    */ package me.abHack.util.render.ItemChams;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ 
/*    */ public class EfficientTexture
/*    */   extends AbstractTexture
/*    */ {
/*    */   private final int width;
/*    */   private final int height;
/*    */   private int[] textureData;
/*    */   
/*    */   public EfficientTexture(BufferedImage bufferedImage) {
/* 17 */     this(bufferedImage.getWidth(), bufferedImage.getHeight());
/* 18 */     bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.textureData, 0, bufferedImage.getWidth());
/* 19 */     updateEfficientTexture();
/*    */   }
/*    */   
/*    */   public EfficientTexture(int textureWidth, int textureHeight) {
/* 23 */     this.width = textureWidth;
/* 24 */     this.height = textureHeight;
/* 25 */     this.textureData = new int[textureWidth * textureHeight];
/* 26 */     TextureUtil.allocateTexture(getGlTextureId(), textureWidth, textureHeight);
/*    */   }
/*    */ 
/*    */
/*    */   
/*    */   private void updateEfficientTexture() {
/* 33 */     TextureUtil.uploadTexture(getGlTextureId(), this.textureData, this.width, this.height);
/* 34 */     this.textureData = new int[0];
/*    */   }

    @Override
    public void loadTexture(IResourceManager iResourceManager) throws IOException {

    }
    /*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\EfficientTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
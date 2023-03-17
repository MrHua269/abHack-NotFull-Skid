/*    */ package me.abHack.util;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Arrays;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ public class IconUtil
/*    */ {
/* 12 */   public static final IconUtil INSTANCE = new IconUtil();
/*    */ 
/*    */   
/*    */   public ByteBuffer readImageToBuffer(InputStream inputStream) throws IOException {
/* 16 */     BufferedImage bufferedimage = ImageIO.read(inputStream);
/* 17 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/* 18 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
/* 19 */     Arrays.stream(aint).map(i -> i << 8 | i >> 24 & 0xFF).forEach(bytebuffer::putInt);
/* 20 */     bytebuffer.flip();
/* 21 */     return bytebuffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\IconUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
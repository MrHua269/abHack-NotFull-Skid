/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class GlowShader
/*    */   extends FramebufferShader {
/*    */   private static GlowShader INSTANCE;
/*    */   
/*    */   private GlowShader() {
/* 11 */     super("glow.frag");
/*    */   }
/*    */   
/*    */   public static GlowShader getInstance() {
/* 15 */     if (INSTANCE == null) {
/* 16 */       INSTANCE = new GlowShader();
/*    */     }
/* 18 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 23 */     setupUniform("texture");
/* 24 */     setupUniform("texelSize");
/* 25 */     setupUniform("color");
/* 26 */     setupUniform("divider");
/* 27 */     setupUniform("radius");
/* 28 */     setupUniform("maxSample");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 33 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 34 */     GL20.glUniform2f(getUniform("texelSize"), Float.intBitsToFloat(Float.floatToIntBits(1531.2186F) ^ 0x7B3F66FF) / this.mc.displayWidth * this.radius * this.quality, Float.intBitsToFloat(Float.floatToIntBits(103.132805F) ^ 0x7D4E43FF) / this.mc.displayHeight * this.radius * this.quality);
/* 35 */     GL20.glUniform3f(getUniform("color"), this.red, this.green, this.blue);
/* 36 */     GL20.glUniform1f(getUniform("divider"), Float.intBitsToFloat(Float.floatToIntBits(0.060076397F) ^ 0x7E7A12AB));
/* 37 */     GL20.glUniform1f(getUniform("radius"), this.radius);
/* 38 */     GL20.glUniform1f(getUniform("maxSample"), Float.intBitsToFloat(Float.floatToIntBits(0.08735179F) ^ 0x7C92E57F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\GlowShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
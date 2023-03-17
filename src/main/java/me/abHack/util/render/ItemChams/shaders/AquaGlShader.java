/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class AquaGlShader
/*    */   extends FramebufferShader {
/*    */   private static AquaGlShader INSTANCE;
/* 10 */   public float time = 0.0F;
/*    */   
/*    */   public AquaGlShader() {
/* 13 */     super("aquaglow.frag");
/*    */   }
/*    */   
/*    */   public static FramebufferShader getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new AquaGlShader();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 25 */     setupUniform("resolution");
/* 26 */     setupUniform("time");
/* 27 */     setupUniform("texture");
/* 28 */     setupUniform("texelSize");
/* 29 */     setupUniform("color");
/* 30 */     setupUniform("divider");
/* 31 */     setupUniform("radius");
/* 32 */     setupUniform("maxSample");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 37 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 38 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth(), (new ScaledResolution(this.mc)).getScaledHeight());
/* 39 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 40 */     GL20.glUniform2f(getUniform("texelSize"), Float.intBitsToFloat(Float.floatToIntBits(1531.2186F) ^ 0x7B3F66FF) / this.mc.displayWidth * this.radius * this.quality, Float.intBitsToFloat(Float.floatToIntBits(103.132805F) ^ 0x7D4E43FF) / this.mc.displayHeight * this.radius * this.quality);
/* 41 */     GL20.glUniform3f(getUniform("color"), this.red, this.green, this.blue);
/* 42 */     GL20.glUniform1f(getUniform("divider"), this.divider);
/* 43 */     GL20.glUniform1f(getUniform("radius"), this.radius);
/* 44 */     GL20.glUniform1f(getUniform("maxSample"), this.maxSample);
/* 45 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 48 */     this.time = (this.time > 100.0F) ? 0.0F : (float)(this.time + 0.01D * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\AquaGlShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
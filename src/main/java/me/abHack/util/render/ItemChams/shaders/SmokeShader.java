/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class SmokeShader
/*    */   extends FramebufferShader {
/*    */   private static SmokeShader INSTANCE;
/* 10 */   protected float time = 0.0F;
/*    */   
/*    */   private SmokeShader() {
/* 13 */     super("smoke.frag");
/*    */   }
/*    */   
/*    */   public static SmokeShader getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new SmokeShader();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 25 */     setupUniform("time");
/* 26 */     setupUniform("resolution");
/* 27 */     setupUniform("radius");
/* 28 */     setupUniform("divider");
/* 29 */     setupUniform("maxSample");
/* 30 */     setupUniform("texelSize");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 35 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 36 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth() / 2.0F, (new ScaledResolution(this.mc)).getScaledHeight() / 2.0F);
/* 37 */     GL20.glUniform1f(getUniform("radius"), this.radius);
/* 38 */     GL20.glUniform1f(getUniform("divider"), this.divider);
/* 39 */     GL20.glUniform1f(getUniform("maxSample"), this.maxSample);
/* 40 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.displayWidth * this.radius * this.quality, 1.0F / this.mc.displayHeight * this.radius * this.quality);
/* 41 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 44 */     this.time = (this.time > 100.0F) ? 0.0F : (float)(this.time + 0.05D * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\SmokeShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
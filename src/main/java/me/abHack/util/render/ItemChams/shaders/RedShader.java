/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class RedShader
/*    */   extends FramebufferShader {
/*    */   private static RedShader INSTANCE;
/* 10 */   protected float time = 0.0F;
/*    */   
/*    */   private RedShader() {
/* 13 */     super("red.frag");
/*    */   }
/*    */   
/*    */   public static RedShader getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new RedShader();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 25 */     setupUniform("time");
/* 26 */     setupUniform("resolution");
/* 27 */     setupUniform("texelSize");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 32 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 33 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth(), (new ScaledResolution(this.mc)).getScaledHeight());
/* 34 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.displayWidth * this.radius * this.quality, 1.0F / this.mc.displayHeight * this.radius * this.quality);
/* 35 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 38 */     this.time = (this.time > 100.0F) ? 0.0F : (float)(this.time + 0.05D * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\RedShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
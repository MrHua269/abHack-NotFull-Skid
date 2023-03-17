/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class HolyFuckShader
/*    */   extends FramebufferShader {
/*    */   private static HolyFuckShader INSTANCE;
/* 10 */   protected float time = 0.0F;
/*    */   
/*    */   private HolyFuckShader() {
/* 13 */     super("holyfuck.frag");
/*    */   }
/*    */   
/*    */   public static HolyFuckShader getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new HolyFuckShader();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 25 */     setupUniform("time");
/* 26 */     setupUniform("resolution");
/* 27 */     setupUniform("speed");
/* 28 */     setupUniform("shift");
/* 29 */     setupUniform("color");
/* 30 */     setupUniform("radius");
/* 31 */     setupUniform("quality");
/* 32 */     setupUniform("divider");
/* 33 */     setupUniform("texelSize");
/* 34 */     setupUniform("maxSample");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 39 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 40 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth(), (new ScaledResolution(this.mc)).getScaledHeight());
/* 41 */     GL20.glUniform3f(getUniform("color"), this.red, this.green, this.blue);
/* 42 */     GL20.glUniform1f(getUniform("radius"), this.radius);
/* 43 */     GL20.glUniform1f(getUniform("quality"), this.quality);
/* 44 */     GL20.glUniform1f(getUniform("divider"), this.divider);
/* 45 */     GL20.glUniform2f(getUniform("speed"), this.animationSpeed, this.animationSpeed);
/* 46 */     GL20.glUniform1f(getUniform("shift"), 1.0F);
/* 47 */     GL20.glUniform1f(getUniform("maxSample"), this.maxSample);
/* 48 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 51 */     this.time = (this.time > 100.0F) ? 0.0F : (this.time += 0.01F * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\HolyFuckShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
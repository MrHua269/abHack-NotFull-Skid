/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class GangGlShader
/*    */   extends FramebufferShader {
/*    */   private static GangGlShader INSTANCE;
/* 10 */   public float time = 0.0F;
/*    */   
/*    */   public GangGlShader() {
/* 13 */     super("gang.frag");
/*    */   }
/*    */   
/*    */   public static FramebufferShader getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new GangGlShader();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 25 */     setupUniform("resolution");
/* 26 */     setupUniform("time");
/* 27 */     setupUniform("speed");
/* 28 */     setupUniform("shift");
/* 29 */     setupUniform("texture");
/* 30 */     setupUniform("color");
/* 31 */     setupUniform("radius");
/* 32 */     setupUniform("quality");
/* 33 */     setupUniform("divider");
/* 34 */     setupUniform("maxSample");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 39 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 40 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth(), (new ScaledResolution(this.mc)).getScaledHeight());
/* 41 */     GL20.glUniform2f(getUniform("speed"), this.animationSpeed, this.animationSpeed);
/* 42 */     GL20.glUniform1f(getUniform("shift"), 1.0F);
/* 43 */     GL20.glUniform3f(getUniform("color"), this.red, this.green, this.blue);
/* 44 */     GL20.glUniform1f(getUniform("radius"), Math.min(this.radius, 2.5F));
/* 45 */     GL20.glUniform1f(getUniform("quality"), this.quality);
/* 46 */     GL20.glUniform1f(getUniform("divider"), this.divider);
/* 47 */     GL20.glUniform1f(getUniform("maxSample"), this.maxSample);
/* 48 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 51 */     this.time = (this.time > 100.0F) ? 0.0F : (float)(this.time + 0.01D * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\GangGlShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
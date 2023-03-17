/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class AquaShader
/*    */   extends FramebufferShader {
/*    */   private static AquaShader INSTANCE;
/* 10 */   public float time = 0.0F;
/*    */   
/*    */   private AquaShader() {
/* 13 */     super("aqua.frag");
/*    */   }
/*    */   
/*    */   public static FramebufferShader getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new AquaShader();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 25 */     setupUniform("resolution");
/* 26 */     setupUniform("time");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 31 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 32 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth(), (new ScaledResolution(this.mc)).getScaledHeight());
/* 33 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 36 */     this.time = (this.time > 100.0F) ? 0.0F : (float)(this.time + 0.01D * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\AquaShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
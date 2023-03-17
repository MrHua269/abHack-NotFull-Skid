/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class FlowShader
/*    */   extends FramebufferShader {
/*    */   public static FlowShader INSTANCE;
/* 10 */   protected float time = 0.0F;
/*    */   
/*    */   private FlowShader() {
/* 13 */     super("flow.frag");
/*    */   }
/*    */   
/*    */   public static FlowShader getInstance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new FlowShader();
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
/* 31 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth(), (new ScaledResolution(this.mc)).getScaledHeight());
/* 32 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 33 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 36 */     this.time = (this.time > 100.0F) ? 0.0F : (float)(this.time + 0.001D * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\FlowShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class BasicShader
/*    */   extends FramebufferShader {
/*    */   private static BasicShader INSTANCE;
/* 10 */   private float time = 0.0F;
/* 11 */   private float timeMult = 0.1F;
/*    */   
/*    */   private BasicShader(String fragmentShader) {
/* 14 */     super(fragmentShader);
/*    */   }
/*    */   
/*    */   private BasicShader(String fragmentShader, float timeMult) {
/* 18 */     super(fragmentShader);
/* 19 */     this.timeMult = timeMult;
/*    */   }
/*    */   
/*    */   public static FramebufferShader getInstance(String fragmentShader) {
/* 23 */     if (INSTANCE == null || !INSTANCE.fragmentShader.equals(fragmentShader)) {
/* 24 */       INSTANCE = new BasicShader(fragmentShader);
/*    */     }
/* 26 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public static FramebufferShader getInstance(String fragmentShader, float timeMult) {
/* 30 */     if (INSTANCE == null || !INSTANCE.fragmentShader.equals(fragmentShader)) {
/* 31 */       INSTANCE = new BasicShader(fragmentShader, timeMult);
/*    */     }
/* 33 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 38 */     setupUniform("time");
/* 39 */     setupUniform("resolution");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 44 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 45 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).getScaledWidth(), (new ScaledResolution(this.mc)).getScaledHeight());
/* 46 */     if (!this.animation) {
/*    */       return;
/*    */     }
/* 49 */     int timeLimit = 10000;
/* 50 */     this.time = (this.time > timeLimit) ? 0.0F : (this.time + this.timeMult * this.animationSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\BasicShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
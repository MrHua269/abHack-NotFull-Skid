/*    */ package me.abHack.util.render.ItemChams.shaders;
/*    */ 
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class PurpleShader extends FramebufferShader {
/*    */   public static PurpleShader INSTANCE;
/*    */   public float time;
/* 11 */   public float timeMult = 0.05F;
/*    */   
/*    */   public PurpleShader() {
/* 14 */     super("purple.frag");
/*    */   }
/*    */   
/*    */   public static PurpleShader getInstance() {
/* 18 */     if (INSTANCE == null) {
/* 19 */       INSTANCE = new PurpleShader();
/*    */     }
/* 21 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 26 */     setupUniform("resolution");
/* 27 */     setupUniform("time");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 32 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(Minecraft.getMinecraft())).getScaledWidth(), (new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight());
/* 33 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 34 */     this.time += this.timeMult * this.animationSpeed;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\shaders\PurpleShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
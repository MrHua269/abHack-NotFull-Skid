/*    */ package me.abHack.util.render.Esp.shaders;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.abHack.util.render.Esp.Shader;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FillShader
/*    */   extends Shader
/*    */ {
/*    */   public FillShader() {
/* 15 */     super("/assets/shaders/glsl/fill.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupConfiguration() {
/* 20 */     setupConfigurations("texture");
/* 21 */     setupConfigurations("texelSize");
/* 22 */     setupConfigurations("color");
/* 23 */     setupConfigurations("divider");
/* 24 */     setupConfigurations("radius");
/* 25 */     setupConfigurations("maxSample");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateConfiguration(int radius, Color color) {
/* 30 */     GL20.glUniform1i(getConfigurations("texture"), 0);
/* 31 */     GL20.glUniform2f(getConfigurations("texelSize"), 1.0F / mc.displayWidth, 1.0F / mc.displayHeight);
/* 32 */     GL20.glUniform4f(getConfigurations("color"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 33 */     GL20.glUniform1f(getConfigurations("radius"), radius);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\Esp\shaders\FillShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
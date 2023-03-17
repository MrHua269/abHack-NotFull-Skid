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
/*    */ public class DotShader
/*    */   extends Shader
/*    */ {
/*    */   public DotShader() {
/* 15 */     super("/assets/shaders/glsl/dot.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupConfiguration() {
/* 20 */     setupConfigurations("texture");
/* 21 */     setupConfigurations("texelSize");
/* 22 */     setupConfigurations("colorDot");
/* 23 */     setupConfigurations("colorFilled");
/* 24 */     setupConfigurations("divider");
/* 25 */     setupConfigurations("radius");
/* 26 */     setupConfigurations("maxSample");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateConfiguration(int radius, Color color) {
/* 31 */     GL20.glUniform1i(getConfigurations("texture"), 0);
/* 32 */     GL20.glUniform2f(getConfigurations("texelSize"), 1.0F / mc.displayWidth, 1.0F / mc.displayHeight);
/* 33 */     GL20.glUniform4f(getConfigurations("colorFilled"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 0.39215687F);
/* 34 */     GL20.glUniform4f(getConfigurations("colorDot"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 35 */     GL20.glUniform1f(getConfigurations("radius"), radius);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\Esp\shaders\DotShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
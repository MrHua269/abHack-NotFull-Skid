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
/*    */ public class RainbowOutlineShader
/*    */   extends Shader
/*    */ {
/*    */   public RainbowOutlineShader() {
/* 15 */     super("/assets/shaders/glsl/rainbow_outline.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupConfiguration() {
/* 20 */     setupConfigurations("texture");
/* 21 */     setupConfigurations("texelSize");
/* 22 */     setupConfigurations("color");
/* 23 */     setupConfigurations("radius");
/* 24 */     setupConfigurations("rainbowStrength");
/* 25 */     setupConfigurations("rainbowSpeed");
/* 26 */     setupConfigurations("saturation");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateConfiguration(int radius, Color color) {
/* 31 */     GL20.glUniform1i(getConfigurations("texture"), 0);
/* 32 */     GL20.glUniform2f(getConfigurations("texelSize"), 1.0F / mc.displayWidth, 1.0F / mc.displayHeight);
/* 33 */     GL20.glUniform4f(getConfigurations("color"), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/* 34 */     GL20.glUniform1f(getConfigurations("radius"), radius);
/* 35 */     GL20.glUniform2f(getConfigurations("rainbowStrength"), -0.0033333334F, -0.0033333334F);
/* 36 */     GL20.glUniform1f(getConfigurations("rainbowSpeed"), 0.4F);
/* 37 */     GL20.glUniform1f(getConfigurations("saturation"), 0.5F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\Esp\shaders\RainbowOutlineShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
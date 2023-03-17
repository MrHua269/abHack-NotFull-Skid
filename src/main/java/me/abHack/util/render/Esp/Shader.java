/*     */ package me.abHack.util.render.Esp;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.abHack.util.Util;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Shader
/*     */   implements Util
/*     */ {
/*     */   private int program;
/*     */   private Map<String, Integer> configurationMap;
/*     */   
/*     */   public Shader(String fragmentShader) {
/*  38 */     int vertexShaderID = 0;
/*  39 */     int fragmentShaderID = 0;
/*     */ 
/*     */     
/*     */     try {
/*  43 */       InputStream vertexStream = getClass().getResourceAsStream("/assets/shaders/glsl/vertex.vert");
/*     */ 
/*     */       
/*  46 */       if (vertexStream != null) {
/*     */ 
/*     */         
/*  49 */         vertexShaderID = createShader(IOUtils.toString(vertexStream, Charset.defaultCharset()), 35633);
/*  50 */         IOUtils.closeQuietly(vertexStream);
/*     */       } 
/*     */ 
/*     */       
/*  54 */       InputStream fragmentStream = getClass().getResourceAsStream(fragmentShader);
/*     */ 
/*     */       
/*  57 */       if (fragmentStream != null)
/*     */       {
/*     */         
/*  60 */         fragmentShaderID = createShader(IOUtils.toString(fragmentStream, Charset.defaultCharset()), 35632);
/*  61 */         IOUtils.closeQuietly(fragmentStream);
/*     */       }
/*     */     
/*  64 */     } catch (Exception exception) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (vertexShaderID != 0 && fragmentShaderID != 0) {
/*  72 */       this.program = ARBShaderObjects.glCreateProgramObjectARB();
/*     */ 
/*     */       
/*  75 */       if (this.program != 0) {
/*  76 */         ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
/*  77 */         ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
/*  78 */         ARBShaderObjects.glLinkProgramARB(this.program);
/*  79 */         ARBShaderObjects.glValidateProgramARB(this.program);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startShader(int radius, Color color) {
/*  90 */     GL20.glUseProgram(this.program);
/*     */ 
/*     */     
/*  93 */     if (this.configurationMap == null) {
/*  94 */       this.configurationMap = new HashMap<>();
/*  95 */       setupConfiguration();
/*     */     } 
/*     */ 
/*     */     
/*  99 */     updateConfiguration(radius, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setupConfiguration();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void updateConfiguration(int paramInt, Color paramColor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int createShader(String shaderSource, int shaderType) {
/* 120 */     int shader = 0;
/*     */ 
/*     */     
/*     */     try {
/* 124 */       shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
/*     */ 
/*     */       
/* 127 */       if (shader != 0) {
/*     */ 
/*     */         
/* 130 */         ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
/* 131 */         ARBShaderObjects.glCompileShaderARB(shader);
/*     */ 
/*     */         
/* 134 */         if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
/* 135 */           throw new RuntimeException("Error creating shader: " + ARBShaderObjects.glGetInfoLogARB(shader, ARBShaderObjects.glGetObjectParameteriARB(shader, 35716)));
/*     */         }
/*     */ 
/*     */         
/* 139 */         return shader;
/*     */       } 
/* 141 */       return 0;
/*     */     
/*     */     }
/* 144 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 147 */       ARBShaderObjects.glDeleteObjectARB(shader);
/* 148 */       throw exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupConfigurations(String configurationName) {
/* 158 */     this.configurationMap.put(configurationName, Integer.valueOf(GL20.glGetUniformLocation(this.program, configurationName)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConfigurations(String configurationName) {
/* 168 */     return ((Integer)this.configurationMap.get(configurationName)).intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\Esp\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
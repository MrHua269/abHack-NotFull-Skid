/*     */ package me.abHack.util.render.ItemChams;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ public abstract class Shader
/*     */ {
/*     */   public int program;
/*     */   public Map<String, Integer> uniformsMap;
/*     */   protected String fragmentShader;
/*     */   
/*     */   public Shader(String fragmentShader) {
/*     */     int fragmentShaderID, vertexShaderID;
/*  21 */     this.fragmentShader = fragmentShader;
/*     */     try {
/*  23 */       InputStream vertexStream = getClass().getResourceAsStream("/shader/vertex.vert");
/*  24 */       vertexShaderID = createShader(IOUtils.toString(vertexStream, Charset.defaultCharset()), 35633);
/*  25 */       IOUtils.closeQuietly(vertexStream);
/*  26 */       InputStream fragmentStream = getClass().getResourceAsStream("/shader/" + fragmentShader);
/*  27 */       fragmentShaderID = createShader(IOUtils.toString(fragmentStream, Charset.defaultCharset()), 35632);
/*  28 */       IOUtils.closeQuietly(fragmentStream);
/*  29 */     } catch (Exception e) {
/*  30 */       e.printStackTrace();
/*     */       return;
/*     */     } 
/*  33 */     if (vertexShaderID == 0 || fragmentShaderID == 0) {
/*     */       return;
/*     */     }
/*  36 */     this.program = ARBShaderObjects.glCreateProgramObjectARB();
/*  37 */     if (this.program == 0) {
/*     */       return;
/*     */     }
/*  40 */     ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
/*  41 */     ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
/*  42 */     ARBShaderObjects.glLinkProgramARB(this.program);
/*  43 */     ARBShaderObjects.glValidateProgramARB(this.program);
/*     */   }
/*     */   
/*     */   public void startShader() {
/*  47 */     GlStateManager.pushMatrix();
/*  48 */     GL20.glUseProgram(this.program);
/*  49 */     if (this.uniformsMap == null) {
/*  50 */       this.uniformsMap = new HashMap<>();
/*  51 */       setupUniforms();
/*     */     } 
/*  53 */     updateUniforms();
/*     */   }
/*     */   
/*     */   public void stopShader() {
/*  57 */     GL20.glUseProgram(0);
/*  58 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public abstract void setupUniforms();
/*     */   
/*     */   public abstract void updateUniforms();
/*     */   
/*     */   public int createShader(String shaderSource, int shaderType) {
/*  66 */     int shader = 0;
/*     */     try {
/*  68 */       shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
/*  69 */       if (shader == 0) {
/*  70 */         return 0;
/*     */       }
/*  72 */       ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
/*  73 */       ARBShaderObjects.glCompileShaderARB(shader);
/*  74 */       if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
/*  75 */         throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
/*     */       }
/*  77 */       return shader;
/*  78 */     } catch (Exception e) {
/*  79 */       ARBShaderObjects.glDeleteObjectARB(shader);
/*  80 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getLogInfo(int i) {
/*  85 */     return ARBShaderObjects.glGetInfoLogARB(i, ARBShaderObjects.glGetObjectParameteriARB(i, 35716));
/*     */   }
/*     */   
/*     */   public void setUniform(String uniformName, int location) {
/*  89 */     this.uniformsMap.put(uniformName, Integer.valueOf(location));
/*     */   }
/*     */   
/*     */   public void setupUniform(String uniformName) {
/*  93 */     setUniform(uniformName, GL20.glGetUniformLocation(this.program, uniformName));
/*     */   }
/*     */   
/*     */   public int getUniform(String uniformName) {
/*  97 */     return ((Integer)this.uniformsMap.get(uniformName)).intValue();
/*     */   }
/*     */   
/*     */   public int getProgramId() {
/* 101 */     return this.program;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.util.render.ItemChams;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ 
/*     */ 
/*     */ public class GlShader
/*     */ {
/*     */   private final Map<String, Integer> uniforms;
/*     */   private int programId;
/*     */   private int vertexShaderId;
/*     */   private int fragmentShaderId;
/*     */   private String name;
/*     */   
/*     */   public GlShader(InputStream sourceStream, String name) {
/*     */     String source;
/*  28 */     this.name = name;
/*  29 */     this.uniforms = new HashMap<>();
/*  30 */     if (sourceStream == null) {
/*     */       return;
/*     */     }
/*     */     try {
/*  34 */       source = readStreamToString(sourceStream);
/*  35 */     } catch (IOException e) {
/*  36 */       e.printStackTrace();
/*     */       return;
/*     */     } 
/*  39 */     StringBuilder vertexSource = new StringBuilder(source.length() / 2);
/*  40 */     StringBuilder fragmentSource = new StringBuilder(source.length() / 2);
/*  41 */     int mode2 = -1;
/*  42 */     for (String line : source.split("\n")) {
/*  43 */       if (line.contains("#shader vert")) {
/*  44 */         mode2 = 0;
/*     */       
/*     */       }
/*  47 */       else if (line.contains("#shader frag")) {
/*  48 */         mode2 = 1;
/*     */       
/*     */       }
/*  51 */       else if (mode2 == 0) {
/*  52 */         vertexSource.append(line).append("\n");
/*     */       
/*     */       }
/*  55 */       else if (mode2 == 1) {
/*  56 */         fragmentSource.append(line).append("\n");
/*     */       } 
/*  58 */     }  int vertId = GL20.glCreateShader(35633);
/*  59 */     int fragId = GL20.glCreateShader(35632);
/*  60 */     GL20.glShaderSource(vertId, vertexSource);
/*  61 */     GL20.glShaderSource(fragId, fragmentSource);
/*  62 */     GL20.glCompileShader(vertId);
/*  63 */     GL20.glCompileShader(fragId);
/*  64 */     if (GL20.glGetShaderi(vertId, 35713) == 0) {
/*  65 */       String error = GL20.glGetShaderInfoLog(vertId, 1024);
/*  66 */       System.err.println("Vertex shader " + name + " could not compile: " + error);
/*     */     } 
/*  68 */     if (GL20.glGetShaderi(fragId, 35713) == 0) {
/*  69 */       String error = GL20.glGetShaderInfoLog(fragId, 1024);
/*  70 */       System.err.println("Fragment shader " + name + " could not compile: " + error);
/*     */     } 
/*  72 */     int programId = GL20.glCreateProgram();
/*  73 */     this.vertexShaderId = vertId;
/*  74 */     this.fragmentShaderId = fragId;
/*  75 */     GL20.glAttachShader(programId, vertId);
/*  76 */     GL20.glAttachShader(programId, fragId);
/*  77 */     GL20.glLinkProgram(programId);
/*  78 */     if (GL20.glGetProgrami(programId, 35714) == 0) {
/*  79 */       String error = GL20.glGetShaderInfoLog(programId, 1024);
/*  80 */       System.err.println("Shader " + name + " could not be linked: " + error);
/*     */     } 
/*  82 */     GL20.glDetachShader(programId, vertId);
/*  83 */     GL20.glDetachShader(programId, fragId);
/*  84 */     GL20.glValidateProgram(programId);
/*     */   }
/*     */   
/*     */   public GlShader(String name) {
/*  88 */     this(GlShader.class.getResourceAsStream("/shader/" + name), name);
/*     */   }
/*     */   
/*     */   public GlShader(int programId, int vertexShaderId, int fragmentShaderId) {
/*  92 */     this.programId = programId;
/*  93 */     this.vertexShaderId = vertexShaderId;
/*  94 */     this.fragmentShaderId = fragmentShaderId;
/*  95 */     this.uniforms = new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String readStreamToString(InputStream inputStream) throws IOException {
/* 100 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 101 */     byte[] buffer = new byte[512]; int read;
/* 102 */     while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
/* 103 */       out.write(buffer, 0, read);
/*     */     }
/* 105 */     return new String(out.toByteArray(), StandardCharsets.UTF_8);
/*     */   }
/*     */   
/*     */   public static GlShader createShader(String name) {
/*     */     String source;
/* 110 */     InputStream sourceStream = GlShader.class.getResourceAsStream("/shader/" + name);
/* 111 */     if (sourceStream == null) {
/* 112 */       return null;
/*     */     }
/*     */     try {
/* 115 */       source = readStreamToString(sourceStream);
/* 116 */     } catch (IOException e) {
/* 117 */       e.printStackTrace();
/* 118 */       return null;
/*     */     } 
/* 120 */     StringBuilder vertexSource = new StringBuilder(source.length() / 2);
/* 121 */     StringBuilder fragmentSource = new StringBuilder(source.length() / 2);
/* 122 */     int mode2 = -1;
/* 123 */     for (String line : source.split("\n")) {
/* 124 */       if (line.contains("#shader vert")) {
/* 125 */         mode2 = 0;
/*     */       
/*     */       }
/* 128 */       else if (line.contains("#shader frag")) {
/* 129 */         mode2 = 1;
/*     */       
/*     */       }
/* 132 */       else if (mode2 == 0) {
/* 133 */         vertexSource.append(line).append("\n");
/*     */       
/*     */       }
/* 136 */       else if (mode2 == 1) {
/* 137 */         fragmentSource.append(line).append("\n");
/*     */       } 
/* 139 */     }  int vertId = GL20.glCreateShader(35633);
/* 140 */     int fragId = GL20.glCreateShader(35632);
/* 141 */     GL20.glShaderSource(vertId, vertexSource);
/* 142 */     GL20.glShaderSource(fragId, fragmentSource);
/* 143 */     GL20.glCompileShader(vertId);
/* 144 */     GL20.glCompileShader(fragId);
/* 145 */     if (GL20.glGetShaderi(vertId, 35713) == 0) {
/* 146 */       String error = GL20.glGetShaderInfoLog(vertId, 1024);
/* 147 */       System.err.println("Vertex shader " + name + " could not compile: " + error);
/*     */     } 
/* 149 */     if (GL20.glGetShaderi(fragId, 35713) == 0) {
/* 150 */       String error = GL20.glGetShaderInfoLog(fragId, 1024);
/* 151 */       System.err.println("Fragment shader " + name + " could not compile: " + error);
/*     */     } 
/* 153 */     int programId = GL20.glCreateProgram();
/* 154 */     GlShader shader = new GlShader(programId, vertId, fragId);
/* 155 */     GL20.glAttachShader(programId, vertId);
/* 156 */     GL20.glAttachShader(programId, fragId);
/* 157 */     GL20.glLinkProgram(programId);
/* 158 */     if (GL20.glGetProgrami(programId, 35714) == 0) {
/* 159 */       String error = GL20.glGetShaderInfoLog(programId, 1024);
/* 160 */       System.err.println("Shader " + name + " could not be linked: " + error);
/*     */     } 
/* 162 */     GL20.glDetachShader(programId, vertId);
/* 163 */     GL20.glDetachShader(programId, fragId);
/* 164 */     GL20.glValidateProgram(programId);
/* 165 */     return shader;
/*     */   }
/*     */   
/*     */   public void bind() {
/* 169 */     GL20.glUseProgram(this.programId);
/*     */   }
/*     */   
/*     */   public void unbind() {
/* 173 */     GL20.glUseProgram(0);
/*     */   }
/*     */   
/*     */   protected void finalize() {
/* 177 */     unbind();
/* 178 */     GL20.glDeleteProgram(this.programId);
/*     */   }
/*     */   
/*     */   public int createUniform(String uniformName) {
/* 182 */     if (this.uniforms.containsKey(uniformName)) {
/* 183 */       return ((Integer)this.uniforms.get(uniformName)).intValue();
/*     */     }
/* 185 */     int location = GL20.glGetUniformLocation(this.programId, uniformName);
/* 186 */     this.uniforms.put(uniformName, Integer.valueOf(location));
/* 187 */     return location;
/*     */   }
/*     */   
/*     */   public void set(String uniformName, int value) {
/* 191 */     GL20.glUniform1i(createUniform(uniformName), value);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, float value) {
/* 195 */     GL20.glUniform1f(createUniform(uniformName), value);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, boolean value) {
/* 199 */     GL20.glUniform1i(createUniform(uniformName), value ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, Vec2f value) {
/* 203 */     GL20.glUniform2f(createUniform(uniformName), value.x, value.y);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, Vec3d value) {
/* 207 */     GL20.glUniform3f(createUniform(uniformName), (float)value.x, (float)value.y, (float)value.z);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, Vector3f value) {
/* 211 */     GL20.glUniform3f(createUniform(uniformName), value.x, value.y, value.z);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, Vector4f value) {
/* 215 */     GL20.glUniform4f(createUniform(uniformName), value.x, value.y, value.z, value.w);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, Color value) {
/* 219 */     GL20.glUniform4f(createUniform(uniformName), value.getRed() / 255.0F, value.getBlue() / 255.0F, value.getGreen() / 255.0F, value.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, FloatBuffer matrix) {
/* 223 */     GL20.glUniformMatrix4(createUniform(uniformName), false, matrix);
/*     */   }
/*     */   
/*     */   public void set(String uniformName, int[] integers) {
/* 227 */     GL20.glUniform4i(createUniform(uniformName), integers[0], integers[1], integers[2], integers[3]);
/*     */   }
/*     */   
/*     */   public int getVertexShaderId() {
/* 231 */     return this.vertexShaderId;
/*     */   }
/*     */   
/*     */   public int getFragmentShaderId() {
/* 235 */     return this.fragmentShaderId;
/*     */   }
/*     */   
/*     */   public int getProgramId() {
/* 239 */     return this.programId;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 243 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\GlShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
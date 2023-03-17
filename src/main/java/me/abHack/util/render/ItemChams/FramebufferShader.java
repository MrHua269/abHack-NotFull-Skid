/*     */ package me.abHack.util.render.ItemChams;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ public abstract class FramebufferShader
/*     */   extends Shader
/*     */ {
/*     */   protected static int lastScale;
/*     */   protected static int lastScaleWidth;
/*     */   protected static int lastScaleHeight;
/*     */   private static Framebuffer framebuffer;
/*  20 */   public Minecraft mc = Minecraft.getMinecraft();
/*     */   protected float red;
/*     */   protected float green;
/*     */   protected float blue;
/*  24 */   protected float alpha = 1.0F;
/*  25 */   protected float radius = 2.0F;
/*  26 */   protected float quality = 1.0F;
/*     */   protected boolean animation = true;
/*  28 */   protected int animationSpeed = 1;
/*  29 */   protected float divider = 1.0F;
/*  30 */   protected float maxSample = 1.0F;
/*     */   private boolean entityShadows;
/*     */   
/*     */   public FramebufferShader(String fragmentShader) {
/*  34 */     super(fragmentShader);
/*     */   }
/*     */   
/*     */   public void setShaderParams(Boolean animation, int animationSpeed, Color color) {
/*  38 */     this.animation = animation.booleanValue();
/*  39 */     this.animationSpeed = animationSpeed;
/*  40 */     this.red = color.getRed() / 255.0F;
/*  41 */     this.green = color.getGreen() / 255.0F;
/*  42 */     this.blue = color.getBlue() / 255.0F;
/*  43 */     this.alpha = color.getAlpha() / 255.0F;
/*     */   }
/*     */   
/*     */   public void setShaderParams(Boolean animation, int animationSpeed, Color color, float radius) {
/*  47 */     setShaderParams(animation, animationSpeed, color);
/*  48 */     this.radius = radius;
/*     */   }
/*     */   
/*     */   public void setShaderParams(Boolean animation, int animationSpeed, Color color, float radius, float divider, float maxSample) {
/*  52 */     setShaderParams(animation, animationSpeed, color, radius);
/*  53 */     this.divider = divider;
/*  54 */     this.maxSample = maxSample;
/*     */   }
/*     */   
/*     */   public void startDraw(float partialTicks) {
/*  58 */     GlStateManager.enableAlpha();
/*  59 */     GlStateManager.pushMatrix();
/*  60 */     framebuffer = setupFrameBuffer(framebuffer);
/*  61 */     framebuffer.bindFramebuffer(true);
/*  62 */     this.entityShadows = this.mc.gameSettings.entityShadows;
/*  63 */     this.mc.gameSettings.entityShadows = false;
/*     */   }
/*     */   
/*     */   public void stopDraw() {
/*  67 */     this.mc.gameSettings.entityShadows = this.entityShadows;
/*  68 */     GlStateManager.enableBlend();
/*  69 */     GL11.glBlendFunc(770, 771);
/*  70 */     this.mc.getFramebuffer().bindFramebuffer(true);
/*  71 */     this.mc.entityRenderer.disableLightmap();
/*  72 */     RenderHelper.disableStandardItemLighting();
/*  73 */     startShader();
/*  74 */     this.mc.entityRenderer.setupOverlayRendering();
/*  75 */     drawFramebuffer(framebuffer);
/*  76 */     stopShader();
/*  77 */     this.mc.entityRenderer.disableLightmap();
/*  78 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public void stopDraw(Color color, float radius, float quality, Runnable... shaderOps) {
/*  82 */     this.mc.gameSettings.entityShadows = this.entityShadows;
/*  83 */     GlStateManager.enableBlend();
/*  84 */     GL11.glBlendFunc(770, 771);
/*  85 */     this.mc.getFramebuffer().bindFramebuffer(true);
/*  86 */     this.red = color.getRed() / 255.0F;
/*  87 */     this.green = color.getGreen() / 255.0F;
/*  88 */     this.blue = color.getBlue() / 255.0F;
/*  89 */     this.alpha = color.getAlpha() / 255.0F;
/*  90 */     this.radius = radius;
/*  91 */     this.quality = quality;
/*  92 */     this.mc.entityRenderer.disableLightmap();
/*  93 */     RenderHelper.disableStandardItemLighting();
/*  94 */     startShader();
/*  95 */     this.mc.entityRenderer.setupOverlayRendering();
/*  96 */     drawFramebuffer(framebuffer);
/*  97 */     stopShader();
/*  98 */     this.mc.entityRenderer.disableLightmap();
/*  99 */     GlStateManager.popMatrix();
/* 100 */     GlStateManager.popAttrib();
/*     */   }
/*     */   
/*     */   public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
/* 104 */     if (Display.isActive() || Display.isVisible()) {
/* 105 */       if (frameBuffer != null) {
/* 106 */         frameBuffer.framebufferClear();
/* 107 */         ScaledResolution scale = new ScaledResolution(this.mc);
/* 108 */         int factor = scale.getScaleFactor();
/* 109 */         int factor2 = scale.getScaledWidth();
/* 110 */         int factor3 = scale.getScaledHeight();
/* 111 */         if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3) {
/* 112 */           frameBuffer.deleteFramebuffer();
/* 113 */           frameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
/* 114 */           frameBuffer.framebufferClear();
/*     */         } 
/* 116 */         lastScale = factor;
/* 117 */         lastScaleWidth = factor2;
/* 118 */         lastScaleHeight = factor3;
/*     */       } else {
/* 120 */         frameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
/*     */       } 
/* 122 */     } else if (frameBuffer == null) {
/* 123 */       frameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
/*     */     } 
/* 125 */     return frameBuffer;
/*     */   }
/*     */   
/*     */   public void drawFramebuffer(Framebuffer framebuffer) {
/* 129 */     ScaledResolution scaledResolution = new ScaledResolution(this.mc);
/* 130 */     GL11.glBindTexture(3553, framebuffer.framebufferTexture);
/* 131 */     GL11.glBegin(7);
/* 132 */     GL11.glTexCoord2d(0.0D, 1.0D);
/* 133 */     GL11.glVertex2d(0.0D, 0.0D);
/* 134 */     GL11.glTexCoord2d(0.0D, 0.0D);
/* 135 */     GL11.glVertex2d(0.0D, scaledResolution.getScaledHeight());
/* 136 */     GL11.glTexCoord2d(1.0D, 0.0D);
/* 137 */     GL11.glVertex2d(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
/* 138 */     GL11.glTexCoord2d(1.0D, 1.0D);
/* 139 */     GL11.glVertex2d(scaledResolution.getScaledWidth(), 0.0D);
/* 140 */     GL11.glEnd();
/* 141 */     GL20.glUseProgram(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\FramebufferShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
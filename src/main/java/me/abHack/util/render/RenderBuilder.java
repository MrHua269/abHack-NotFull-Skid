/*     */ package me.abHack.util.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderBuilder
/*     */ {
/*     */   private boolean setup;
/*     */   private boolean depth;
/*     */   private boolean blend;
/*     */   private boolean texture;
/*     */   private boolean cull;
/*     */   private boolean alpha;
/*     */   private boolean shade;
/*  22 */   private AxisAlignedBB axisAlignedBB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  23 */   private Box box = Box.FILL;
/*     */   
/*     */   private double height;
/*     */   
/*     */   private double length;
/*     */   private double width;
/*  29 */   private Color color = Color.WHITE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder setup() {
/*  37 */     GlStateManager.pushMatrix();
/*  38 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  39 */     GL11.glEnable(2848);
/*  40 */     GL11.glHint(3154, 4354);
/*  41 */     this.setup = true;
/*  42 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder depth(boolean in) {
/*  52 */     if (in) {
/*  53 */       GlStateManager.disableDepth();
/*  54 */       GlStateManager.depthMask(false);
/*     */     } 
/*     */     
/*  57 */     this.depth = in;
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder blend() {
/*  67 */     GlStateManager.enableBlend();
/*  68 */     this.blend = true;
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder texture() {
/*  78 */     GlStateManager.disableTexture2D();
/*  79 */     this.texture = true;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder line(float width) {
/*  90 */     GlStateManager.glLineWidth(width);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder cull(boolean in) {
/* 101 */     if (this.cull) {
/* 102 */       GlStateManager.disableCull();
/*     */     }
/*     */     
/* 105 */     this.cull = in;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder alpha(boolean in) {
/* 116 */     if (this.alpha) {
/* 117 */       GlStateManager.disableAlpha();
/*     */     }
/*     */     
/* 120 */     this.alpha = in;
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder shade(boolean in) {
/* 131 */     if (in) {
/* 132 */       GlStateManager.shadeModel(7425);
/*     */     }
/*     */     
/* 135 */     this.shade = in;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder build() {
/* 145 */     if (this.depth) {
/* 146 */       GlStateManager.depthMask(true);
/* 147 */       GlStateManager.enableDepth();
/*     */     } 
/*     */     
/* 150 */     if (this.texture) {
/* 151 */       GlStateManager.enableTexture2D();
/*     */     }
/*     */     
/* 154 */     if (this.blend) {
/* 155 */       GlStateManager.disableBlend();
/*     */     }
/*     */     
/* 158 */     if (this.cull) {
/* 159 */       GlStateManager.enableCull();
/*     */     }
/*     */     
/* 162 */     if (this.alpha) {
/* 163 */       GlStateManager.enableAlpha();
/*     */     }
/*     */     
/* 166 */     if (this.shade) {
/* 167 */       GlStateManager.shadeModel(7424);
/*     */     }
/*     */     
/* 170 */     if (this.setup) {
/* 171 */       GL11.glDisable(2848);
/* 172 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder position(BlockPos in) {
/* 185 */     position(new AxisAlignedBB(in.getX(), in.getY(), in.getZ(), (in.getX() + 1), (in.getY() + 1), (in.getZ() + 1)));
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder position(Vec3d in) {
/* 196 */     position(new AxisAlignedBB(in.x, in.y, in.z, in.x + 1.0D, in.y + 1.0D, in.z + 1.0D));
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder position(AxisAlignedBB in) {
/* 207 */     this.axisAlignedBB = in;
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder height(double in) {
/* 218 */     this.height = in;
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder width(double in) {
/* 229 */     this.width = in;
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder length(double in) {
/* 240 */     this.length = in;
/* 241 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder color(Color in) {
/* 251 */     this.color = in;
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderBuilder box(Box in) {
/* 262 */     this.box = in;
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getAxisAlignedBB() {
/* 272 */     return this.axisAlignedBB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHeight() {
/* 281 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWidth() {
/* 290 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLength() {
/* 299 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 308 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Box getBox() {
/* 317 */     return this.box;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Box
/*     */   {
/* 325 */     FILL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     OUTLINE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     BOTH,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 340 */     GLOW,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     REVERSE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     CLAW,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     NONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\RenderBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package me.abHack.util.render;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.util.Objects;
/*      */ import me.abHack.OyVey;
/*      */ import me.abHack.features.modules.player.InstantMine;
/*      */ import me.abHack.util.EntityUtil;
/*      */ import me.abHack.util.Util;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.RenderItem;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.glu.Sphere;
/*      */ 
/*      */ public class RenderUtil implements Util {
/*      */   public static RenderItem itemRender;
/*      */   public static ICamera camera;
/*   34 */   public static Tessellator tessellator = Tessellator.getInstance();
/*   35 */   public static BufferBuilder bufferBuilder = tessellator.getBuffer();
/*      */   
/*      */   static {
/*   38 */     itemRender = mc.getRenderItem();
/*   39 */     camera = (ICamera)new Frustum();
/*      */   }
/*      */   
/*      */   public static void drawRectangleCorrectly(int x, int y, int w, int h, int color) {
/*   43 */     GL11.glLineWidth(1.0F);
/*   44 */     Gui.drawRect(x, y, x + w, y + h, color);
/*      */   }
/*      */   
/*      */   public static AxisAlignedBB interpolateAxis(AxisAlignedBB bb) {
/*   48 */     return new AxisAlignedBB(bb.minX - (mc.getRenderManager()).viewerPosX, bb.minY - (mc.getRenderManager()).viewerPosY, bb.minZ - (mc.getRenderManager()).viewerPosZ, bb.maxX - (mc.getRenderManager()).viewerPosX, bb.maxY - (mc.getRenderManager()).viewerPosY, bb.maxZ - (mc.getRenderManager()).viewerPosZ);
/*      */   }
/*      */   
/*      */   public static void drawGradientSideways(double leftpos, double top, double right, double bottom, int col1, int col2) {
/*   52 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*   53 */     float f2 = (col1 >> 16 & 0xFF) / 255.0F;
/*   54 */     float f3 = (col1 >> 8 & 0xFF) / 255.0F;
/*   55 */     float f4 = (col1 & 0xFF) / 255.0F;
/*   56 */     float f5 = (col2 >> 24 & 0xFF) / 255.0F;
/*   57 */     float f6 = (col2 >> 16 & 0xFF) / 255.0F;
/*   58 */     float f7 = (col2 >> 8 & 0xFF) / 255.0F;
/*   59 */     float f8 = (col2 & 0xFF) / 255.0F;
/*   60 */     GL11.glEnable(3042);
/*   61 */     GL11.glDisable(3553);
/*   62 */     GL11.glBlendFunc(770, 771);
/*   63 */     GL11.glEnable(2848);
/*   64 */     GL11.glShadeModel(7425);
/*   65 */     GL11.glPushMatrix();
/*   66 */     GL11.glBegin(7);
/*   67 */     GL11.glColor4f(f2, f3, f4, f);
/*   68 */     GL11.glVertex2d(leftpos, top);
/*   69 */     GL11.glVertex2d(leftpos, bottom);
/*   70 */     GL11.glColor4f(f6, f7, f8, f5);
/*   71 */     GL11.glVertex2d(right, bottom);
/*   72 */     GL11.glVertex2d(right, top);
/*   73 */     GL11.glEnd();
/*   74 */     GL11.glPopMatrix();
/*   75 */     GL11.glEnable(3553);
/*   76 */     GL11.glDisable(3042);
/*   77 */     GL11.glDisable(2848);
/*   78 */     GL11.glShadeModel(7424);
/*      */   }
/*      */   
/*      */   public static void rotationHelper(float xAngle, float yAngle, float zAngle) {
/*   82 */     GlStateManager.rotate(yAngle, 0.0F, 1.0F, 0.0F);
/*   83 */     GlStateManager.rotate(zAngle, 0.0F, 0.0F, 1.0F);
/*   84 */     GlStateManager.rotate(xAngle, 1.0F, 0.0F, 0.0F);
/*      */   }
/*      */   
/*      */   public static void prepareGL3D() {
/*   88 */     GlStateManager.pushMatrix();
/*   89 */     GlStateManager.enableBlend();
/*   90 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*   91 */     GlStateManager.disableDepth();
/*   92 */     GlStateManager.disableAlpha();
/*   93 */     GlStateManager.disableCull();
/*      */     
/*   95 */     GL11.glEnable(2848);
/*   96 */     GL11.glHint(3154, 4354);
/*      */     
/*   98 */     GlStateManager.shadeModel(7425);
/*      */     
/*  100 */     GlStateManager.disableTexture2D();
/*  101 */     GlStateManager.disableLighting();
/*  102 */     GlStateManager.depthMask(false);
/*      */   }
/*      */   
/*      */   public static void releaseGL3D() {
/*  106 */     GlStateManager.depthMask(true);
/*  107 */     GlStateManager.enableLighting();
/*  108 */     GlStateManager.enableTexture2D();
/*  109 */     GL11.glDisable(2848);
/*      */     
/*  111 */     GlStateManager.shadeModel(7424);
/*      */     
/*  113 */     GlStateManager.enableAlpha();
/*  114 */     GlStateManager.enableCull();
/*  115 */     GlStateManager.enableDepth();
/*  116 */     GlStateManager.disableBlend();
/*      */     
/*  118 */     GlStateManager.glLineWidth(1.0F);
/*  119 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  120 */     GlStateManager.popMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void startRender() {
/*  125 */     GL11.glPushAttrib(1048575);
/*  126 */     GL11.glPushMatrix();
/*  127 */     GL11.glDisable(3008);
/*  128 */     GL11.glEnable(3042);
/*  129 */     GL11.glBlendFunc(770, 771);
/*  130 */     GL11.glDisable(3553);
/*  131 */     GL11.glDisable(2929);
/*  132 */     GL11.glDepthMask(false);
/*  133 */     GL11.glEnable(2884);
/*  134 */     GL11.glEnable(2848);
/*  135 */     GL11.glHint(3154, 4353);
/*  136 */     GL11.glDisable(2896);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endRender() {
/*  141 */     GL11.glEnable(2896);
/*  142 */     GL11.glDisable(2848);
/*  143 */     GL11.glEnable(3553);
/*  144 */     GL11.glEnable(2929);
/*  145 */     GL11.glDisable(3042);
/*  146 */     GL11.glEnable(3008);
/*  147 */     GL11.glDepthMask(true);
/*  148 */     GL11.glCullFace(1029);
/*  149 */     GL11.glPopMatrix();
/*  150 */     GL11.glPopAttrib();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawBBFill(AxisAlignedBB BB, Color color, int alpha) {
/*  155 */     AxisAlignedBB bb = new AxisAlignedBB(BB.minX - (mc.getRenderManager()).viewerPosX, BB.minY - (mc.getRenderManager()).viewerPosY, BB.minZ - (mc.getRenderManager()).viewerPosZ, BB.maxX - (mc.getRenderManager()).viewerPosX, BB.maxY - (mc.getRenderManager()).viewerPosY, BB.maxZ - (mc.getRenderManager()).viewerPosZ);
/*  156 */     camera.setPosition(((Entity)Objects.requireNonNull(mc.getRenderViewEntity())).posX, (mc.getRenderViewEntity()).posY, (mc.getRenderViewEntity()).posZ);
/*      */     
/*  158 */     if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + (mc.getRenderManager()).viewerPosX, bb.minY + (mc.getRenderManager()).viewerPosY, bb.minZ + (mc.getRenderManager()).viewerPosZ, bb.maxX + (mc.getRenderManager()).viewerPosX, bb.maxY + (mc.getRenderManager()).viewerPosY, bb.maxZ + (mc.getRenderManager()).viewerPosZ))) {
/*  159 */       prepareGL3D();
/*  160 */       RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F);
/*  161 */       releaseGL3D();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawBBBox(AxisAlignedBB BB, Color color, int alpha) {
/*  166 */     AxisAlignedBB bb = new AxisAlignedBB(BB.minX - (mc.getRenderManager()).viewerPosX, BB.minY - (mc.getRenderManager()).viewerPosY, BB.minZ - (mc.getRenderManager()).viewerPosZ, BB.maxX - (mc.getRenderManager()).viewerPosX, BB.maxY - (mc.getRenderManager()).viewerPosY, BB.maxZ - (mc.getRenderManager()).viewerPosZ);
/*  167 */     camera.setPosition(((Entity)Objects.requireNonNull(mc.getRenderViewEntity())).posX, (mc.getRenderViewEntity()).posY, (mc.getRenderViewEntity()).posZ);
/*  168 */     if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + (mc.getRenderManager()).viewerPosX, bb.minY + (mc.getRenderManager()).viewerPosY, bb.minZ + (mc.getRenderManager()).viewerPosZ, bb.maxX + (mc.getRenderManager()).viewerPosX, bb.maxY + (mc.getRenderManager()).viewerPosY, bb.maxZ + (mc.getRenderManager()).viewerPosZ))) {
/*  169 */       prepareGL3D();
/*  170 */       RenderGlobal.drawSelectionBoundingBox(bb, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F);
/*  171 */       releaseGL3D();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderLabel(Entity entityIn, String str, int maxDistance, float partialTicks) {
/*  177 */     double x = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks - (mc.getRenderManager()).renderPosX;
/*  178 */     double y = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks - (mc.getRenderManager()).renderPosY;
/*  179 */     double z = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks - (mc.getRenderManager()).renderPosZ;
/*      */     
/*  181 */     double d0 = entityIn.getDistance((Entity)mc.player);
/*      */     
/*  183 */     if (d0 <= maxDistance) {
/*  184 */       FontRenderer fontrenderer = mc.fontRenderer;
/*  185 */       float f = 1.6F;
/*  186 */       float f1 = 0.016666668F * f;
/*  187 */       GlStateManager.pushMatrix();
/*  188 */       GlStateManager.translate((float)x + 0.0F, (float)y + entityIn.height + 0.5F, (float)z);
/*  189 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  190 */       GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/*  191 */       GlStateManager.rotate((mc.getRenderManager()).playerViewX, 1.0F, 0.0F, 0.0F);
/*  192 */       GlStateManager.scale(-f1, -f1, f1);
/*  193 */       GlStateManager.disableLighting();
/*  194 */       GlStateManager.depthMask(false);
/*  195 */       GlStateManager.disableDepth();
/*  196 */       GlStateManager.enableBlend();
/*  197 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  198 */       Tessellator tessellator = Tessellator.getInstance();
/*  199 */       BufferBuilder worldrenderer = tessellator.getBuffer();
/*  200 */       int i = 0;
/*      */       
/*  202 */       if (str.equals("deadmau5")) {
/*  203 */         i = -10;
/*      */       }
/*      */       
/*  206 */       int j = fontrenderer.getStringWidth(str) / 2;
/*  207 */       GlStateManager.disableTexture2D();
/*  208 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  209 */       worldrenderer.pos((-j - 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/*  210 */       worldrenderer.pos((-j - 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/*  211 */       worldrenderer.pos((j + 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/*  212 */       worldrenderer.pos((j + 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/*  213 */       tessellator.draw();
/*  214 */       GlStateManager.enableTexture2D();
/*  215 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
/*  216 */       GlStateManager.enableDepth();
/*  217 */       GlStateManager.depthMask(true);
/*  218 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
/*  219 */       GlStateManager.disableBlend();
/*  220 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void boxESP(BlockPos blockPos, Color color, float f, boolean bl, boolean bl2, int n, boolean bl3) {
/*  226 */     AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - (mc.getRenderManager()).viewerPosX, blockPos.getY() - (mc.getRenderManager()).viewerPosY, blockPos.getZ() - (mc.getRenderManager()).viewerPosZ, (blockPos.getX() + 1) - (mc.getRenderManager()).viewerPosX, (blockPos.getY() + 1) - (mc.getRenderManager()).viewerPosY, (blockPos.getZ() + 1) - (mc.getRenderManager()).viewerPosZ);
/*  227 */     camera.setPosition(((Entity)Objects.requireNonNull(mc.getRenderViewEntity())).posX, (mc.getRenderViewEntity()).posY, (mc.getRenderViewEntity()).posZ);
/*  228 */     if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(axisAlignedBB.minX + (mc.getRenderManager()).viewerPosX, axisAlignedBB.minY + (mc.getRenderManager()).viewerPosY, axisAlignedBB.minZ + (mc.getRenderManager()).viewerPosZ, axisAlignedBB.maxX + (mc.getRenderManager()).viewerPosX, axisAlignedBB.maxY + (mc.getRenderManager()).viewerPosY, axisAlignedBB.maxZ + (mc.getRenderManager()).viewerPosZ))) {
/*      */       double d, d2, d3, d4, d5, d6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  236 */       prepareGL3D();
/*      */       
/*  238 */       GL11.glLineWidth(f);
/*  239 */       double d8 = mc.playerController.curBlockDamageMP;
/*  240 */       float f2 = (float)InstantMine.INSTANCE.timer.getPassedTimeMs() / 1000.0F / InstantMine.INSTANCE.breakTime * OyVey.serverManager.getTpsFactor();
/*  241 */       f2 = Math.min(f2, 1.0F);
/*  242 */       if (bl3) {
/*  243 */         d6 = axisAlignedBB.minX + 0.5D - (f2 / 2.0F);
/*  244 */         d5 = axisAlignedBB.minY + 0.5D - (f2 / 2.0F);
/*  245 */         d4 = axisAlignedBB.minZ + 0.5D - (f2 / 2.0F);
/*  246 */         d3 = axisAlignedBB.maxX - 0.5D + (f2 / 2.0F);
/*  247 */         d2 = axisAlignedBB.maxY - 0.5D + (f2 / 2.0F);
/*  248 */         d = axisAlignedBB.maxZ - 0.5D + (f2 / 2.0F);
/*      */       } else {
/*  250 */         d6 = axisAlignedBB.minX + 0.5D - d8 / 2.0D;
/*  251 */         d5 = axisAlignedBB.minY + 0.5D - d8 / 2.0D;
/*  252 */         d4 = axisAlignedBB.minZ + 0.5D - d8 / 2.0D;
/*  253 */         d3 = axisAlignedBB.maxX - 0.5D + d8 / 2.0D;
/*  254 */         d2 = axisAlignedBB.maxY - 0.5D + d8 / 2.0D;
/*  255 */         d = axisAlignedBB.maxZ - 0.5D + d8 / 2.0D;
/*      */       } 
/*      */       
/*  258 */       AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(d6, d5, d4, d3, d2, d);
/*      */       
/*  260 */       if (bl2) {
/*  261 */         drawFilledBox(axisAlignedBB2, (new Color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, n / 255.0F)).getRGB());
/*      */       }
/*  263 */       if (bl) {
/*  264 */         drawBlockOutline(axisAlignedBB2, new Color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1.0F), 1.0F);
/*      */       }
/*      */       
/*  267 */       releaseGL3D();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawTexturedRect(int x, int y, int textureX, int textureY, int width, int height, int zLevel) {
/*  272 */     Tessellator tessellator = Tessellator.getInstance();
/*  273 */     BufferBuilder BufferBuilder2 = tessellator.getBuffer();
/*  274 */     BufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  275 */     BufferBuilder2.pos(x, (y + height), zLevel).tex((textureX * 0.00390625F), ((textureY + height) * 0.00390625F)).endVertex();
/*  276 */     BufferBuilder2.pos((x + width), (y + height), zLevel).tex(((textureX + width) * 0.00390625F), ((textureY + height) * 0.00390625F)).endVertex();
/*  277 */     BufferBuilder2.pos((x + width), y, zLevel).tex(((textureX + width) * 0.00390625F), (textureY * 0.00390625F)).endVertex();
/*  278 */     BufferBuilder2.pos(x, y, zLevel).tex((textureX * 0.00390625F), (textureY * 0.00390625F)).endVertex();
/*  279 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawBox(RenderBuilder renderBuilder) {
/*  286 */     if (mc.getRenderViewEntity() != null) {
/*      */ 
/*      */ 
/*      */       
/*  290 */       AxisAlignedBB axisAlignedBB = renderBuilder.getAxisAlignedBB().offset(-(mc.getRenderManager()).viewerPosX, -(mc.getRenderManager()).viewerPosY, -(mc.getRenderManager()).viewerPosZ);
/*      */ 
/*      */       
/*  293 */       switch (renderBuilder.getBox()) {
/*      */         case FILL:
/*  295 */           drawSelectionBox(axisAlignedBB, renderBuilder.getHeight(), renderBuilder.getLength(), renderBuilder.getWidth(), renderBuilder.getColor());
/*      */           break;
/*      */         case OUTLINE:
/*  298 */           drawSelectionBoundingBox(axisAlignedBB, renderBuilder.getHeight(), renderBuilder.getLength(), renderBuilder.getWidth(), new Color(renderBuilder.getColor().getRed(), renderBuilder.getColor().getGreen(), renderBuilder.getColor().getBlue(), 144));
/*      */           break;
/*      */         case BOTH:
/*  301 */           drawSelectionBox(axisAlignedBB, renderBuilder.getHeight(), renderBuilder.getLength(), renderBuilder.getWidth(), renderBuilder.getColor());
/*  302 */           drawSelectionBoundingBox(axisAlignedBB, renderBuilder.getHeight(), renderBuilder.getLength(), renderBuilder.getWidth(), new Color(renderBuilder.getColor().getRed(), renderBuilder.getColor().getGreen(), renderBuilder.getColor().getBlue(), 144));
/*      */           break;
/*      */         case GLOW:
/*  305 */           drawSelectionGlowFilledBox(axisAlignedBB, renderBuilder.getHeight(), renderBuilder.getLength(), renderBuilder.getWidth(), renderBuilder.getColor(), new Color(renderBuilder.getColor().getRed(), renderBuilder.getColor().getGreen(), renderBuilder.getColor().getBlue(), 0));
/*      */           break;
/*      */         case REVERSE:
/*  308 */           drawSelectionGlowFilledBox(axisAlignedBB, renderBuilder.getHeight(), renderBuilder.getLength(), renderBuilder.getWidth(), new Color(renderBuilder.getColor().getRed(), renderBuilder.getColor().getGreen(), renderBuilder.getColor().getBlue(), 0), renderBuilder.getColor());
/*      */           break;
/*      */         case CLAW:
/*  311 */           drawClawBox(axisAlignedBB, renderBuilder.getHeight(), renderBuilder.getLength(), renderBuilder.getWidth(), new Color(renderBuilder.getColor().getRed(), renderBuilder.getColor().getGreen(), renderBuilder.getColor().getBlue(), 255));
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/*  316 */       renderBuilder.build();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawSelectionBoundingBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color color) {
/*  323 */     bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*      */ 
/*      */     
/*  326 */     addChainedBoundingBoxVertices(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, color);
/*      */ 
/*      */     
/*  329 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addChainedBoundingBoxVertices(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
/*  335 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  336 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  337 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  338 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  339 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  340 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  341 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  342 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  343 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  344 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  345 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  346 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  347 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  348 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  349 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  350 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  351 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  352 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawSelectionBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color color) {
/*  358 */     bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*      */ 
/*      */     
/*  361 */     addChainedFilledBoxVertices(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, color);
/*      */ 
/*      */     
/*  364 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawSelectionGlowFilledBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color startColor, Color endColor) {
/*  370 */     bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*      */ 
/*      */     
/*  373 */     addChainedGlowBoxVertices(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, startColor, endColor);
/*      */ 
/*      */     
/*  376 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addChainedFilledBoxVertices(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
/*  382 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  383 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  384 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  385 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  386 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  387 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  388 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  389 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  390 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  391 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  392 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  393 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  394 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  395 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  396 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  397 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  398 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  399 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  400 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  401 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  402 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  403 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  404 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  405 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  406 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  407 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  408 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  409 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  410 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  411 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addChainedGlowBoxVertices(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color startColor, Color endColor) {
/*  417 */     bufferBuilder.pos(minX, minY, minZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  418 */     bufferBuilder.pos(maxX, minY, minZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  419 */     bufferBuilder.pos(maxX, minY, maxZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  420 */     bufferBuilder.pos(minX, minY, maxZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  421 */     bufferBuilder.pos(minX, maxY, minZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  422 */     bufferBuilder.pos(minX, maxY, maxZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  423 */     bufferBuilder.pos(maxX, maxY, maxZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  424 */     bufferBuilder.pos(maxX, maxY, minZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  425 */     bufferBuilder.pos(minX, minY, minZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  426 */     bufferBuilder.pos(minX, maxY, minZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  427 */     bufferBuilder.pos(maxX, maxY, minZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  428 */     bufferBuilder.pos(maxX, minY, minZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  429 */     bufferBuilder.pos(maxX, minY, minZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  430 */     bufferBuilder.pos(maxX, maxY, minZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  431 */     bufferBuilder.pos(maxX, maxY, maxZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  432 */     bufferBuilder.pos(maxX, minY, maxZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  433 */     bufferBuilder.pos(minX, minY, maxZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  434 */     bufferBuilder.pos(maxX, minY, maxZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  435 */     bufferBuilder.pos(maxX, maxY, maxZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  436 */     bufferBuilder.pos(minX, maxY, maxZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  437 */     bufferBuilder.pos(minX, minY, minZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  438 */     bufferBuilder.pos(minX, minY, maxZ).color(startColor.getRed() / 255.0F, startColor.getGreen() / 255.0F, startColor.getBlue() / 255.0F, startColor.getAlpha() / 255.0F).endVertex();
/*  439 */     bufferBuilder.pos(minX, maxY, maxZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*  440 */     bufferBuilder.pos(minX, maxY, minZ).color(endColor.getRed() / 255.0F, endColor.getGreen() / 255.0F, endColor.getBlue() / 255.0F, endColor.getAlpha() / 255.0F).endVertex();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawClawBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color color) {
/*  446 */     bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*      */ 
/*      */     
/*  449 */     addChainedClawBoxVertices(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, color);
/*      */ 
/*      */     
/*  452 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addChainedClawBoxVertices(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
/*  458 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  459 */     bufferBuilder.pos(minX, minY, maxZ - 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  460 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  461 */     bufferBuilder.pos(minX, minY, minZ + 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  462 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  463 */     bufferBuilder.pos(maxX, minY, maxZ - 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  464 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  465 */     bufferBuilder.pos(maxX, minY, minZ + 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  466 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  467 */     bufferBuilder.pos(maxX - 0.8D, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  468 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  469 */     bufferBuilder.pos(maxX - 0.8D, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  470 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  471 */     bufferBuilder.pos(minX + 0.8D, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  472 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  473 */     bufferBuilder.pos(minX + 0.8D, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  474 */     bufferBuilder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  475 */     bufferBuilder.pos(minX, minY + 0.2D, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  476 */     bufferBuilder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  477 */     bufferBuilder.pos(minX, minY + 0.2D, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  478 */     bufferBuilder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  479 */     bufferBuilder.pos(maxX, minY + 0.2D, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  480 */     bufferBuilder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  481 */     bufferBuilder.pos(maxX, minY + 0.2D, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  482 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  483 */     bufferBuilder.pos(minX, maxY, maxZ - 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  484 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  485 */     bufferBuilder.pos(minX, maxY, minZ + 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  486 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  487 */     bufferBuilder.pos(maxX, maxY, maxZ - 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  488 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  489 */     bufferBuilder.pos(maxX, maxY, minZ + 0.8D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  490 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  491 */     bufferBuilder.pos(maxX - 0.8D, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  492 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  493 */     bufferBuilder.pos(maxX - 0.8D, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  494 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  495 */     bufferBuilder.pos(minX + 0.8D, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  496 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  497 */     bufferBuilder.pos(minX + 0.8D, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  498 */     bufferBuilder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  499 */     bufferBuilder.pos(minX, maxY - 0.2D, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  500 */     bufferBuilder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  501 */     bufferBuilder.pos(minX, maxY - 0.2D, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  502 */     bufferBuilder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  503 */     bufferBuilder.pos(maxX, maxY - 0.2D, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*  504 */     bufferBuilder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
/*  505 */     bufferBuilder.pos(maxX, maxY - 0.2D, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*      */   }
/*      */   
/*      */   public static void drawBoxESP(BlockPos pos, Color color, boolean secondC, Color secondColor, float lineWidth, boolean outline, boolean box, int boxAlpha, boolean air) {
/*  509 */     if (box) {
/*  510 */       drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha));
/*      */     }
/*  512 */     if (outline) {
/*  513 */       drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawCircle(RenderBuilder renderBuilder, Vec3d vec3d, double radius, double height, Color color) {
/*  519 */     renderCircle(bufferBuilder, vec3d, radius, height, color);
/*  520 */     renderBuilder.build();
/*      */   }
/*      */   
/*      */   public static void renderCircle(BufferBuilder bufferBuilder, Vec3d vec3d, double radius, double height, Color color) {
/*  524 */     GlStateManager.disableCull();
/*  525 */     GlStateManager.disableAlpha();
/*  526 */     GlStateManager.shadeModel(7425);
/*  527 */     bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*      */     
/*  529 */     for (int i = 0; i < 361; i++) {
/*  530 */       bufferBuilder.pos(vec3d.x + Math.sin(Math.toRadians(i)) * radius - (mc.getRenderManager()).viewerPosX, vec3d.y + height - (mc.getRenderManager()).viewerPosY, vec3d.z + Math.cos(Math.toRadians(i)) * radius - (mc.getRenderManager()).viewerPosZ).color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1.0F).endVertex();
/*      */     }
/*      */     
/*  533 */     tessellator.draw();
/*      */     
/*  535 */     GlStateManager.enableCull();
/*  536 */     GlStateManager.enableAlpha();
/*  537 */     GlStateManager.shadeModel(7424);
/*      */   }
/*      */   
/*      */   public static void drawLine(float x, float y, float x1, float y1, float thickness, int hex) {
/*  541 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/*  542 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/*  543 */     float blue = (hex & 0xFF) / 255.0F;
/*  544 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/*  545 */     GlStateManager.pushMatrix();
/*  546 */     GlStateManager.disableTexture2D();
/*  547 */     GlStateManager.enableBlend();
/*  548 */     GlStateManager.disableAlpha();
/*  549 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  550 */     GlStateManager.shadeModel(7425);
/*  551 */     GL11.glLineWidth(thickness);
/*  552 */     GL11.glEnable(2848);
/*  553 */     GL11.glHint(3154, 4354);
/*  554 */     Tessellator tessellator = Tessellator.getInstance();
/*  555 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  556 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*  557 */     bufferbuilder.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
/*  558 */     bufferbuilder.pos(x1, y1, 0.0D).color(red, green, blue, alpha).endVertex();
/*  559 */     tessellator.draw();
/*  560 */     GlStateManager.shadeModel(7424);
/*  561 */     GL11.glDisable(2848);
/*  562 */     GlStateManager.disableBlend();
/*  563 */     GlStateManager.enableAlpha();
/*  564 */     GlStateManager.enableTexture2D();
/*  565 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void drawBox(BlockPos pos, Color color) {
/*  569 */     AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - (mc.getRenderManager()).viewerPosX, pos.getY() - (mc.getRenderManager()).viewerPosY, pos.getZ() - (mc.getRenderManager()).viewerPosZ, (pos.getX() + 1) - (mc.getRenderManager()).viewerPosX, (pos.getY() + 1) - (mc.getRenderManager()).viewerPosY, (pos.getZ() + 1) - (mc.getRenderManager()).viewerPosZ);
/*  570 */     camera.setPosition(((Entity)Objects.requireNonNull(mc.getRenderViewEntity())).posX, (mc.getRenderViewEntity()).posY, (mc.getRenderViewEntity()).posZ);
/*  571 */     if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + (mc.getRenderManager()).viewerPosX, bb.minY + (mc.getRenderManager()).viewerPosY, bb.minZ + (mc.getRenderManager()).viewerPosZ, bb.maxX + (mc.getRenderManager()).viewerPosX, bb.maxY + (mc.getRenderManager()).viewerPosY, bb.maxZ + (mc.getRenderManager()).viewerPosZ))) {
/*  572 */       GlStateManager.pushMatrix();
/*  573 */       GlStateManager.enableBlend();
/*  574 */       GlStateManager.disableDepth();
/*  575 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  576 */       GlStateManager.disableTexture2D();
/*  577 */       GlStateManager.depthMask(false);
/*  578 */       GL11.glEnable(2848);
/*  579 */       GL11.glHint(3154, 4354);
/*  580 */       RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*  581 */       GL11.glDisable(2848);
/*  582 */       GlStateManager.depthMask(true);
/*  583 */       GlStateManager.enableDepth();
/*  584 */       GlStateManager.enableTexture2D();
/*  585 */       GlStateManager.disableBlend();
/*  586 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawBlockOutline(BlockPos pos, Color color, float linewidth, boolean air) {
/*  591 */     IBlockState iblockstate = mc.world.getBlockState(pos);
/*  592 */     if ((air || iblockstate.getMaterial() != Material.AIR) && mc.world.getWorldBorder().contains(pos)) {
/*  593 */       assert mc.renderViewEntity != null;
/*  594 */       Vec3d interp = EntityUtil.interpolateEntity(mc.renderViewEntity, mc.getRenderPartialTicks());
/*  595 */       drawBlockOutline(iblockstate.getSelectedBoundingBox((World)mc.world, pos).grow(0.0020000000949949026D).offset(-interp.x, -interp.y, -interp.z), color, linewidth);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawBlockOutline(AxisAlignedBB bb, Color color, float linewidth) {
/*  600 */     float red = color.getRed() / 255.0F;
/*  601 */     float green = color.getGreen() / 255.0F;
/*  602 */     float blue = color.getBlue() / 255.0F;
/*  603 */     float alpha = color.getAlpha() / 255.0F;
/*  604 */     GlStateManager.pushMatrix();
/*  605 */     GlStateManager.enableBlend();
/*  606 */     GlStateManager.disableDepth();
/*  607 */     GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  608 */     GlStateManager.disableTexture2D();
/*  609 */     GlStateManager.depthMask(false);
/*  610 */     GL11.glEnable(2848);
/*  611 */     GL11.glHint(3154, 4354);
/*  612 */     GL11.glLineWidth(linewidth);
/*  613 */     Tessellator tessellator = Tessellator.getInstance();
/*  614 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  615 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*  616 */     bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  617 */     bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  618 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  619 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  620 */     bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  621 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  622 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  623 */     bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  624 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  625 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  626 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  627 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  628 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  629 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  630 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  631 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  632 */     tessellator.draw();
/*  633 */     GL11.glDisable(2848);
/*  634 */     GlStateManager.depthMask(true);
/*  635 */     GlStateManager.enableDepth();
/*  636 */     GlStateManager.enableTexture2D();
/*  637 */     GlStateManager.disableBlend();
/*  638 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void drawBoxESP(BlockPos pos, Color color, boolean secondC, Color secondColor, float lineWidth, boolean outline, boolean box, int boxAlpha, boolean air, double height, boolean gradientBox, boolean gradientOutline, boolean invertGradientBox, boolean invertGradientOutline, int gradientAlpha) {
/*  642 */     if (box) {
/*  643 */       drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha), height, gradientBox, invertGradientBox, gradientAlpha);
/*      */     }
/*  645 */     if (outline) {
/*  646 */       drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air, height, gradientOutline, invertGradientOutline, gradientAlpha);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void drawBlockOutline(BlockPos pos, Color color, float linewidth, boolean air, double height, boolean gradient, boolean invert, int alpha) {
/*  651 */     if (gradient) {
/*  652 */       Color endColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
/*  653 */       drawGradientBlockOutline(pos, invert ? endColor : color, invert ? color : endColor, linewidth, height);
/*      */       return;
/*      */     } 
/*  656 */     IBlockState iblockstate = mc.world.getBlockState(pos);
/*  657 */     if ((air || iblockstate.getMaterial() != Material.AIR) && mc.world.getWorldBorder().contains(pos)) {
/*  658 */       AxisAlignedBB blockAxis = new AxisAlignedBB(pos.getX() - (mc.getRenderManager()).viewerPosX, pos.getY() - (mc.getRenderManager()).viewerPosY, pos.getZ() - (mc.getRenderManager()).viewerPosZ, (pos.getX() + 1) - (mc.getRenderManager()).viewerPosX, (pos.getY() + 1) - (mc.getRenderManager()).viewerPosY + height, (pos.getZ() + 1) - (mc.getRenderManager()).viewerPosZ);
/*  659 */       drawBlockOutline(blockAxis.grow(0.0020000000949949026D), color, linewidth);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawGradientBlockOutline(BlockPos pos, Color startColor, Color endColor, float linewidth, double height) {
/*  664 */     IBlockState iblockstate = mc.world.getBlockState(pos);
/*  665 */     Vec3d interp = EntityUtil.interpolateEntity((Entity)mc.player, mc.getRenderPartialTicks());
/*  666 */     drawGradientBlockOutline(iblockstate.getSelectedBoundingBox((World)mc.world, pos).grow(0.0020000000949949026D).offset(-interp.x, -interp.y, -interp.z).expand(0.0D, height, 0.0D), startColor, endColor, linewidth);
/*      */   }
/*      */   
/*      */   public static void drawGradientBlockOutline(AxisAlignedBB bb, Color startColor, Color endColor, float linewidth) {
/*  670 */     float red = startColor.getRed() / 255.0F;
/*  671 */     float green = startColor.getGreen() / 255.0F;
/*  672 */     float blue = startColor.getBlue() / 255.0F;
/*  673 */     float alpha = startColor.getAlpha() / 255.0F;
/*  674 */     float red1 = endColor.getRed() / 255.0F;
/*  675 */     float green1 = endColor.getGreen() / 255.0F;
/*  676 */     float blue1 = endColor.getBlue() / 255.0F;
/*  677 */     float alpha1 = endColor.getAlpha() / 255.0F;
/*  678 */     GlStateManager.pushMatrix();
/*  679 */     GlStateManager.enableBlend();
/*  680 */     GlStateManager.disableDepth();
/*  681 */     GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  682 */     GlStateManager.disableTexture2D();
/*  683 */     GlStateManager.depthMask(false);
/*  684 */     GL11.glEnable(2848);
/*  685 */     GL11.glHint(3154, 4354);
/*  686 */     GL11.glLineWidth(linewidth);
/*  687 */     Tessellator tessellator = Tessellator.getInstance();
/*  688 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  689 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*  690 */     bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red1, green1, blue1, alpha1).endVertex();
/*  691 */     bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red1, green1, blue1, alpha1).endVertex();
/*  692 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red1, green1, blue1, alpha1).endVertex();
/*  693 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red1, green1, blue1, alpha1).endVertex();
/*  694 */     bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red1, green1, blue1, alpha1).endVertex();
/*  695 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  696 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  697 */     bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red1, green1, blue1, alpha1).endVertex();
/*  698 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red1, green1, blue1, alpha1).endVertex();
/*  699 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  700 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  701 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  702 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  703 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red1, green1, blue1, alpha1).endVertex();
/*  704 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  705 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  706 */     tessellator.draw();
/*  707 */     GL11.glDisable(2848);
/*  708 */     GlStateManager.depthMask(true);
/*  709 */     GlStateManager.enableDepth();
/*  710 */     GlStateManager.enableTexture2D();
/*  711 */     GlStateManager.disableBlend();
/*  712 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static <T> void drawBox(BlockPos pos, Color color, double height, boolean gradient, boolean invert, int alpha) {
/*  716 */     if (gradient) {
/*  717 */       Color endColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
/*  718 */       drawOpenGradientBox(pos, invert ? endColor : color, invert ? color : endColor, height);
/*      */       return;
/*      */     } 
/*  721 */     AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - (mc.getRenderManager()).viewerPosX, pos.getY() - (mc.getRenderManager()).viewerPosY, pos.getZ() - (mc.getRenderManager()).viewerPosZ, (pos.getX() + 1) - (mc.getRenderManager()).viewerPosX, (pos.getY() + 1) - (mc.getRenderManager()).viewerPosY + height, (pos.getZ() + 1) - (mc.getRenderManager()).viewerPosZ);
/*  722 */     camera.setPosition(((Entity)Objects.requireNonNull((T)mc.getRenderViewEntity())).posX, (mc.getRenderViewEntity()).posY, (mc.getRenderViewEntity()).posZ);
/*  723 */     if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + (mc.getRenderManager()).viewerPosX, bb.minY + (mc.getRenderManager()).viewerPosY, bb.minZ + (mc.getRenderManager()).viewerPosZ, bb.maxX + (mc.getRenderManager()).viewerPosX, bb.maxY + (mc.getRenderManager()).viewerPosY, bb.maxZ + (mc.getRenderManager()).viewerPosZ))) {
/*  724 */       GlStateManager.pushMatrix();
/*  725 */       GlStateManager.enableBlend();
/*  726 */       GlStateManager.disableDepth();
/*  727 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  728 */       GlStateManager.disableTexture2D();
/*  729 */       GlStateManager.depthMask(false);
/*  730 */       GL11.glEnable(2848);
/*  731 */       GL11.glHint(3154, 4354);
/*  732 */       RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*  733 */       GL11.glDisable(2848);
/*  734 */       GlStateManager.depthMask(true);
/*  735 */       GlStateManager.enableDepth();
/*  736 */       GlStateManager.enableTexture2D();
/*  737 */       GlStateManager.disableBlend();
/*  738 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawOpenGradientBox(BlockPos pos, Color startColor, Color endColor, double height) {
/*  743 */     for (EnumFacing face : EnumFacing.values()) {
/*  744 */       if (face != EnumFacing.UP)
/*  745 */         drawGradientPlane(pos, face, startColor, endColor, height); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawGradientPlane(BlockPos pos, EnumFacing face, Color startColor, Color endColor, double height) {
/*  750 */     Tessellator tessellator = Tessellator.getInstance();
/*  751 */     BufferBuilder builder = tessellator.getBuffer();
/*  752 */     IBlockState iblockstate = mc.world.getBlockState(pos);
/*  753 */     Vec3d interp = EntityUtil.interpolateEntity((Entity)mc.player, mc.getRenderPartialTicks());
/*  754 */     AxisAlignedBB bb = iblockstate.getSelectedBoundingBox((World)mc.world, pos).grow(0.0020000000949949026D).offset(-interp.x, -interp.y, -interp.z).expand(0.0D, height, 0.0D);
/*  755 */     float red = startColor.getRed() / 255.0F;
/*  756 */     float green = startColor.getGreen() / 255.0F;
/*  757 */     float blue = startColor.getBlue() / 255.0F;
/*  758 */     float alpha = startColor.getAlpha() / 255.0F;
/*  759 */     float red1 = endColor.getRed() / 255.0F;
/*  760 */     float green1 = endColor.getGreen() / 255.0F;
/*  761 */     float blue1 = endColor.getBlue() / 255.0F;
/*  762 */     float alpha1 = endColor.getAlpha() / 255.0F;
/*  763 */     double x1 = 0.0D;
/*  764 */     double y1 = 0.0D;
/*  765 */     double z1 = 0.0D;
/*  766 */     double x2 = 0.0D;
/*  767 */     double y2 = 0.0D;
/*  768 */     double z2 = 0.0D;
/*  769 */     if (face == EnumFacing.DOWN) {
/*  770 */       x1 = bb.minX;
/*  771 */       x2 = bb.maxX;
/*  772 */       y1 = bb.minY;
/*  773 */       y2 = bb.minY;
/*  774 */       z1 = bb.minZ;
/*  775 */       z2 = bb.maxZ;
/*  776 */     } else if (face == EnumFacing.UP) {
/*  777 */       x1 = bb.minX;
/*  778 */       x2 = bb.maxX;
/*  779 */       y1 = bb.maxY;
/*  780 */       y2 = bb.maxY;
/*  781 */       z1 = bb.minZ;
/*  782 */       z2 = bb.maxZ;
/*  783 */     } else if (face == EnumFacing.EAST) {
/*  784 */       x1 = bb.maxX;
/*  785 */       x2 = bb.maxX;
/*  786 */       y1 = bb.minY;
/*  787 */       y2 = bb.maxY;
/*  788 */       z1 = bb.minZ;
/*  789 */       z2 = bb.maxZ;
/*  790 */     } else if (face == EnumFacing.WEST) {
/*  791 */       x1 = bb.minX;
/*  792 */       x2 = bb.minX;
/*  793 */       y1 = bb.minY;
/*  794 */       y2 = bb.maxY;
/*  795 */       z1 = bb.minZ;
/*  796 */       z2 = bb.maxZ;
/*  797 */     } else if (face == EnumFacing.SOUTH) {
/*  798 */       x1 = bb.minX;
/*  799 */       x2 = bb.maxX;
/*  800 */       y1 = bb.minY;
/*  801 */       y2 = bb.maxY;
/*  802 */       z1 = bb.maxZ;
/*  803 */       z2 = bb.maxZ;
/*  804 */     } else if (face == EnumFacing.NORTH) {
/*  805 */       x1 = bb.minX;
/*  806 */       x2 = bb.maxX;
/*  807 */       y1 = bb.minY;
/*  808 */       y2 = bb.maxY;
/*  809 */       z1 = bb.minZ;
/*  810 */       z2 = bb.minZ;
/*      */     } 
/*  812 */     GlStateManager.pushMatrix();
/*  813 */     GlStateManager.disableDepth();
/*  814 */     GlStateManager.disableTexture2D();
/*  815 */     GlStateManager.enableBlend();
/*  816 */     GlStateManager.disableAlpha();
/*  817 */     GlStateManager.depthMask(false);
/*  818 */     builder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*  819 */     if (face == EnumFacing.EAST || face == EnumFacing.WEST || face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
/*  820 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  821 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  822 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  823 */       builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
/*  824 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  825 */       builder.pos(x1, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  826 */       builder.pos(x1, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  827 */       builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
/*  828 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  829 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  830 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  831 */       builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
/*  832 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  833 */       builder.pos(x2, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  834 */       builder.pos(x2, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  835 */       builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
/*  836 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  837 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  838 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  839 */       builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
/*  840 */       builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
/*  841 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  842 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  843 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  844 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  845 */       builder.pos(x1, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  846 */       builder.pos(x2, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  847 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  848 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  849 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  850 */     } else if (face == EnumFacing.UP) {
/*  851 */       builder.pos(x1, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  852 */       builder.pos(x1, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  853 */       builder.pos(x1, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  854 */       builder.pos(x1, y1, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  855 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  856 */       builder.pos(x1, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  857 */       builder.pos(x1, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  858 */       builder.pos(x1, y1, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  859 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  860 */       builder.pos(x2, y1, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  861 */       builder.pos(x2, y1, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  862 */       builder.pos(x2, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  863 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  864 */       builder.pos(x2, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  865 */       builder.pos(x2, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  866 */       builder.pos(x2, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  867 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  868 */       builder.pos(x1, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  869 */       builder.pos(x1, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  870 */       builder.pos(x2, y1, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  871 */       builder.pos(x1, y1, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  872 */       builder.pos(x2, y1, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  873 */       builder.pos(x2, y1, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  874 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  875 */       builder.pos(x1, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  876 */       builder.pos(x1, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  877 */       builder.pos(x2, y2, z1).color(red1, green1, blue1, alpha1).endVertex();
/*  878 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  879 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  880 */       builder.pos(x2, y2, z2).color(red1, green1, blue1, alpha1).endVertex();
/*  881 */     } else if (face == EnumFacing.DOWN) {
/*  882 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  883 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  884 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  885 */       builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
/*  886 */       builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
/*  887 */       builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
/*  888 */       builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
/*  889 */       builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
/*  890 */       builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
/*  891 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  892 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  893 */       builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
/*  894 */       builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
/*  895 */       builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
/*  896 */       builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
/*  897 */       builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
/*  898 */       builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
/*  899 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  900 */       builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
/*  901 */       builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
/*  902 */       builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
/*  903 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  904 */       builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
/*  905 */       builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
/*  906 */       builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
/*  907 */       builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
/*  908 */       builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
/*  909 */       builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
/*  910 */       builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
/*  911 */       builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
/*      */     } 
/*  913 */     tessellator.draw();
/*  914 */     GlStateManager.depthMask(true);
/*  915 */     GlStateManager.disableBlend();
/*  916 */     GlStateManager.enableAlpha();
/*  917 */     GlStateManager.enableTexture2D();
/*  918 */     GlStateManager.enableDepth();
/*  919 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void drawText(BlockPos pos, String text) {
/*  923 */     GlStateManager.pushMatrix();
/*  924 */     glBillboardDistanceScaled(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, (EntityPlayer)mc.player, 1.0F);
/*  925 */     GlStateManager.disableDepth();
/*  926 */     GlStateManager.translate(-(OyVey.textManager.getStringWidth(text) / 2.0D), 0.0D, 0.0D);
/*  927 */     OyVey.textManager.drawStringWithShadow(text, 0.0F, 0.0F, -5592406);
/*  928 */     GlStateManager.popMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawText(BlockPos pos, String text, int color) {
/*  933 */     GlStateManager.pushMatrix();
/*  934 */     glBillboardDistanceScaled(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, (EntityPlayer)mc.player, 1.0F);
/*  935 */     GlStateManager.disableDepth();
/*  936 */     GlStateManager.translate(-(OyVey.textManager.getStringWidth(text) / 2.0D), 0.0D, 0.0D);
/*  937 */     OyVey.textManager.drawStringWithShadow(text, 0.0F, 0.0F, color);
/*  938 */     GlStateManager.popMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawRect(float x, float y, float w, float h, int color) {
/*  943 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/*  944 */     float red = (color >> 16 & 0xFF) / 255.0F;
/*  945 */     float green = (color >> 8 & 0xFF) / 255.0F;
/*  946 */     float blue = (color & 0xFF) / 255.0F;
/*  947 */     Tessellator tessellator = Tessellator.getInstance();
/*  948 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  949 */     GlStateManager.enableBlend();
/*  950 */     GlStateManager.disableTexture2D();
/*  951 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  952 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  953 */     bufferbuilder.pos(x, h, 0.0D).color(red, green, blue, alpha).endVertex();
/*  954 */     bufferbuilder.pos(w, h, 0.0D).color(red, green, blue, alpha).endVertex();
/*  955 */     bufferbuilder.pos(w, y, 0.0D).color(red, green, blue, alpha).endVertex();
/*  956 */     bufferbuilder.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
/*  957 */     tessellator.draw();
/*  958 */     GlStateManager.enableTexture2D();
/*  959 */     GlStateManager.disableBlend();
/*      */   }
/*      */   
/*      */   public static void drawFilledBox(AxisAlignedBB bb, int color) {
/*  963 */     GlStateManager.pushMatrix();
/*  964 */     GlStateManager.enableBlend();
/*  965 */     GlStateManager.disableDepth();
/*  966 */     GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  967 */     GlStateManager.disableTexture2D();
/*  968 */     GlStateManager.depthMask(false);
/*  969 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/*  970 */     float red = (color >> 16 & 0xFF) / 255.0F;
/*  971 */     float green = (color >> 8 & 0xFF) / 255.0F;
/*  972 */     float blue = (color & 0xFF) / 255.0F;
/*  973 */     Tessellator tessellator = Tessellator.getInstance();
/*  974 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  975 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  976 */     bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  977 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  978 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  979 */     bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  980 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  981 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  982 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  983 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  984 */     bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  985 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  986 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  987 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  988 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  989 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  990 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  991 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  992 */     bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  993 */     bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  994 */     bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  995 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  996 */     bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
/*  997 */     bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  998 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
/*  999 */     bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
/* 1000 */     tessellator.draw();
/* 1001 */     GlStateManager.depthMask(true);
/* 1002 */     GlStateManager.enableDepth();
/* 1003 */     GlStateManager.enableTexture2D();
/* 1004 */     GlStateManager.disableBlend();
/* 1005 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void glBillboard(float x, float y, float z) {
/* 1009 */     float scale = 0.02666667F;
/* 1010 */     GlStateManager.translate(x - (mc.getRenderManager()).renderPosX, y - (mc.getRenderManager()).renderPosY, z - (mc.getRenderManager()).renderPosZ);
/* 1011 */     GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1012 */     GlStateManager.rotate(-mc.player.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 1013 */     GlStateManager.rotate(mc.player.rotationPitch, (mc.gameSettings.thirdPersonView == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/* 1014 */     GlStateManager.scale(-scale, -scale, scale);
/*      */   }
/*      */   
/*      */   public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer player, float scale) {
/* 1018 */     glBillboard(x, y, z);
/* 1019 */     int distance = (int)player.getDistance(x, y, z);
/* 1020 */     float scaleDistance = distance / 2.0F / (2.0F + 2.0F - scale);
/* 1021 */     if (scaleDistance < 1.0F) {
/* 1022 */       scaleDistance = 1.0F;
/*      */     }
/* 1024 */     GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
/*      */   }
/*      */   
/*      */   public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
/* 1028 */     Sphere s = new Sphere();
/* 1029 */     GL11.glPushMatrix();
/* 1030 */     GL11.glBlendFunc(770, 771);
/* 1031 */     GL11.glEnable(3042);
/* 1032 */     GL11.glLineWidth(1.2F);
/* 1033 */     GL11.glDisable(3553);
/* 1034 */     GL11.glDisable(2929);
/* 1035 */     GL11.glDepthMask(false);
/* 1036 */     s.setDrawStyle(100013);
/* 1037 */     GL11.glTranslated(x - mc.renderManager.renderPosX, y - mc.renderManager.renderPosY, z - mc.renderManager.renderPosZ);
/* 1038 */     s.draw(size, slices, stacks);
/* 1039 */     GL11.glLineWidth(2.0F);
/* 1040 */     GL11.glEnable(3553);
/* 1041 */     GL11.glEnable(2929);
/* 1042 */     GL11.glDepthMask(true);
/* 1043 */     GL11.glDisable(3042);
/* 1044 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawCircle(float x, float y, float z, float radius, Color color) {
/* 1049 */     BlockPos pos = new BlockPos(x, y, z);
/* 1050 */     AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - (mc.getRenderManager()).viewerPosX, pos.getY() - (mc.getRenderManager()).viewerPosY, pos.getZ() - (mc.getRenderManager()).viewerPosZ, (pos.getX() + 1) - (mc.getRenderManager()).viewerPosX, (pos.getY() + 1) - (mc.getRenderManager()).viewerPosY, (pos.getZ() + 1) - (mc.getRenderManager()).viewerPosZ);
/* 1051 */     camera.setPosition(((Entity)Objects.requireNonNull(mc.getRenderViewEntity())).posX, (mc.getRenderViewEntity()).posY, (mc.getRenderViewEntity()).posZ);
/* 1052 */     if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + (mc.getRenderManager()).viewerPosX, bb.minY + (mc.getRenderManager()).viewerPosY, bb.minZ + (mc.getRenderManager()).viewerPosZ, bb.maxX + (mc.getRenderManager()).viewerPosX, bb.maxY + (mc.getRenderManager()).viewerPosY, bb.maxZ + (mc.getRenderManager()).viewerPosZ)))
/* 1053 */       drawCircleVertices(bb, radius, color); 
/*      */   }
/*      */   
/*      */   public static void drawCircleVertices(AxisAlignedBB bb, float radius, Color color) {
/* 1057 */     float r = color.getRed() / 255.0F;
/* 1058 */     float g = color.getGreen() / 255.0F;
/* 1059 */     float b = color.getBlue() / 255.0F;
/* 1060 */     float a = color.getAlpha() / 255.0F;
/* 1061 */     Tessellator tessellator = Tessellator.getInstance();
/* 1062 */     BufferBuilder buffer = tessellator.getBuffer();
/* 1063 */     GlStateManager.pushMatrix();
/* 1064 */     GlStateManager.enableBlend();
/* 1065 */     GlStateManager.disableDepth();
/* 1066 */     GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/* 1067 */     GlStateManager.disableTexture2D();
/* 1068 */     GlStateManager.depthMask(false);
/* 1069 */     GL11.glEnable(2848);
/* 1070 */     GL11.glHint(3154, 4354);
/* 1071 */     GL11.glLineWidth(1.0F);
/* 1072 */     for (int i = 0; i < 360; i++) {
/* 1073 */       buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 1074 */       buffer.pos((bb.getCenter()).x + Math.sin(i * 3.1415926D / 180.0D) * radius, bb.minY, (bb.getCenter()).z + Math.cos(i * 3.1415926D / 180.0D) * radius).color(r, g, b, a).endVertex();
/* 1075 */       buffer.pos((bb.getCenter()).x + Math.sin((i + 1) * 3.1415926D / 180.0D) * radius, bb.minY, (bb.getCenter()).z + Math.cos((i + 1) * 3.1415926D / 180.0D) * radius).color(r, g, b, a).endVertex();
/* 1076 */       tessellator.draw();
/*      */     } 
/* 1078 */     GL11.glDisable(2848);
/* 1079 */     GlStateManager.depthMask(true);
/* 1080 */     GlStateManager.enableDepth();
/* 1081 */     GlStateManager.enableTexture2D();
/* 1082 */     GlStateManager.disableBlend();
/* 1083 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void setColor(Color color) {
/* 1087 */     GL11.glColor4d(color.getRed() / 255.0D, color.getGreen() / 255.0D, color.getBlue() / 255.0D, color.getAlpha() / 255.0D);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\RenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
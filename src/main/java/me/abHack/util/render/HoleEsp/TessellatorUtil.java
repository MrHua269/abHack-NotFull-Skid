/*    */ package me.abHack.util.render.HoleEsp;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ 
/*    */ 
/*    */ public class TessellatorUtil
/*    */ {
/* 14 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static void drawBoundingBox(AxisAlignedBB bb, double width, Color color, int alpha) {
/* 17 */     Tessellator tessellator = Tessellator.getInstance();
/* 18 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 19 */     GlStateManager.glLineWidth((float)width);
/* 20 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 21 */     colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
/* 22 */     colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
/* 23 */     colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
/* 24 */     colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
/* 25 */     colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
/* 26 */     colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
/* 27 */     colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
/* 28 */     colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
/* 29 */     colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
/* 30 */     colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
/* 31 */     colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
/* 32 */     colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
/* 33 */     colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
/* 34 */     colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
/* 35 */     colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
/* 36 */     colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
/* 37 */     tessellator.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void drawBox(AxisAlignedBB bb, boolean check, double height, Color color, int alpha, int sides) {
/* 42 */     if (check) {
/* 43 */       drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, bb.maxY - bb.minY, bb.maxZ - bb.minZ, color, alpha, sides);
/*    */     } else {
/* 45 */       drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, height, bb.maxZ - bb.minZ, color, alpha, sides);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void drawBox(double x, double y, double z, double w, double h, double d, Color color, int alpha, int sides) {
/* 50 */     GlStateManager.disableAlpha();
/* 51 */     Tessellator tessellator = Tessellator.getInstance();
/* 52 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 53 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 54 */     doVerticies(new AxisAlignedBB(x, y, z, x + w, y + h, z + d), color, alpha, bufferbuilder, sides);
/* 55 */     tessellator.draw();
/* 56 */     GlStateManager.enableAlpha();
/*    */   }
/*    */   
/*    */   private static void colorVertex(double x, double y, double z, Color color, int alpha, BufferBuilder bufferbuilder) {
/* 60 */     bufferbuilder.pos(x - (mc.getRenderManager()).viewerPosX, y - (mc.getRenderManager()).viewerPosY, z - (mc.getRenderManager()).viewerPosZ).color(color.getRed(), color.getGreen(), color.getBlue(), alpha).endVertex();
/*    */   }
/*    */   
/*    */   private static void doVerticies(AxisAlignedBB axisAlignedBB, Color color, int alpha, BufferBuilder bufferbuilder, int sides) {
/* 64 */     if ((sides & 0x20) != 0) {
/* 65 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
/* 66 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
/* 67 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
/* 68 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
/*    */     } 
/* 70 */     if ((sides & 0x10) != 0) {
/* 71 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
/* 72 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
/* 73 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
/* 74 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
/*    */     } 
/* 76 */     if ((sides & 0x4) != 0) {
/* 77 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
/* 78 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
/* 79 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
/* 80 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
/*    */     } 
/* 82 */     if ((sides & 0x8) != 0) {
/* 83 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
/* 84 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
/* 85 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
/* 86 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
/*    */     } 
/* 88 */     if ((sides & 0x2) != 0) {
/* 89 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
/* 90 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
/* 91 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
/* 92 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
/*    */     } 
/* 94 */     if ((sides & 0x1) != 0) {
/* 95 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
/* 96 */       colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
/* 97 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
/* 98 */       colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\HoleEsp\TessellatorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
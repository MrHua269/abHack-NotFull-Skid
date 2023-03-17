/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class JumpCircles
/*     */   extends Module
/*     */ {
/*  19 */   static List<Circle> circles = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public JumpCircles() {
/*  23 */     super("JumpCircles", "jump render", Module.Category.RENDER, true, false, false);
/*     */   }
/*     */   
/*     */   public static void onLocalPlayerUpdate() {
/*  27 */     circles.removeIf(Circle::update);
/*     */   }
/*     */   
/*     */   public static void handleEntityJump(Entity entity) {
/*  31 */     Color rbg = ColorUtil.getPrimaryColor();
/*  32 */     Vec3d color = new Vec3d(rbg.getRed(), rbg.getGreen(), rbg.getBlue());
/*  33 */     circles.add(new Circle(entity.getPositionVector(), color));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  38 */     if (mc.player.motionY == 0.33319999363422365D) {
/*  39 */       handleEntityJump((Entity)mc.player);
/*     */     }
/*  41 */     onLocalPlayerUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  46 */     EntityPlayerSP client = mc.player;
/*  47 */     double ix = -(client.lastTickPosX + (client.posX - client.lastTickPosX) * mc.getRenderPartialTicks());
/*  48 */     double iy = -(client.lastTickPosY + (client.posY - client.lastTickPosY) * mc.getRenderPartialTicks());
/*  49 */     double iz = -(client.lastTickPosZ + (client.posZ - client.lastTickPosZ) * mc.getRenderPartialTicks());
/*  50 */     RenderUtil.startRender();
/*  51 */     GL11.glPushMatrix();
/*  52 */     GL11.glTranslated(ix, iy, iz);
/*  53 */     GL11.glDisable(2884);
/*  54 */     GL11.glEnable(3042);
/*  55 */     GL11.glDisable(3553);
/*  56 */     GL11.glDisable(3008);
/*  57 */     GL11.glDisable(2929);
/*  58 */     GL11.glBlendFunc(770, 771);
/*  59 */     GL11.glDisable(2896);
/*  60 */     GL11.glShadeModel(7425);
/*  61 */     Collections.reverse(circles);
/*     */     try {
/*  63 */       for (Circle c : circles) {
/*  64 */         float k = c.existed / 20.0F;
/*  65 */         double x = (c.position()).x;
/*  66 */         double y = (c.position()).y - k * 0.5D;
/*  67 */         double z = (c.position()).z;
/*  68 */         float end = k + 1.0F - k;
/*  69 */         GL11.glBegin(8);
/*  70 */         for (int i = 0; i <= 360; i += 5) {
/*  71 */           GL11.glColor4f((float)(c.color()).x / 255.0F, (float)(c.color()).y / 255.0F, (float)(c.color()).z / 255.0F, 0.2F * (1.0F - c.existed / 20.0F));
/*  72 */           GL11.glVertex3d(x + Math.cos(Math.toRadians((i * 4))) * k, y, z + Math.sin(Math.toRadians((i * 4))) * k);
/*  73 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.01F * (1.0F - c.existed / 20.0F));
/*  74 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * end, y + Math.sin((k * 8.0F)) * 0.5D, z + Math.sin(Math.toRadians(i) * end));
/*     */         } 
/*  76 */         GL11.glEnd();
/*     */       } 
/*  78 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  81 */     Collections.reverse(circles);
/*  82 */     GL11.glEnable(2896);
/*  83 */     GL11.glEnable(3553);
/*  84 */     GL11.glDisable(3042);
/*  85 */     GL11.glShadeModel(7424);
/*  86 */     GL11.glEnable(2884);
/*  87 */     GL11.glEnable(2929);
/*  88 */     GL11.glEnable(3008);
/*  89 */     GL11.glPopMatrix();
/*  90 */     RenderUtil.endRender();
/*     */   }
/*     */   
/*     */   static class Circle {
/*     */     private final Vec3d vec;
/*     */     private final Vec3d color;
/*     */     byte existed;
/*     */     
/*     */     Circle(Vec3d vec, Vec3d color) {
/*  99 */       this.vec = vec;
/* 100 */       this.color = color;
/*     */     }
/*     */     
/*     */     Vec3d position() {
/* 104 */       return this.vec;
/*     */     }
/*     */     
/*     */     Vec3d color() {
/* 108 */       return this.color;
/*     */     }
/*     */     
/*     */     boolean update() {
/* 112 */       this.existed = (byte)(this.existed + 1);
/* 113 */       return (this.existed > 20);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\JumpCircles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
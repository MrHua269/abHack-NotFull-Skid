/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class Ranges
/*    */   extends Module
/*    */ {
/* 22 */   private final Setting<Boolean> hitSpheres = register(new Setting("HitSpheres", Boolean.valueOf(false)));
/* 23 */   private final Setting<Boolean> circle = register(new Setting("Circle", Boolean.valueOf(true)));
/* 24 */   private final Setting<Boolean> ownSphere = register(new Setting("OwnSphere", Boolean.FALSE, v -> ((Boolean)this.hitSpheres.getValue()).booleanValue()));
/* 25 */   private final Setting<Boolean> raytrace = register(new Setting("RayTrace", Boolean.FALSE, v -> ((Boolean)this.circle.getValue()).booleanValue()));
/* 26 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/* 27 */   private final Setting<Double> radius = register(new Setting("Radius", Double.valueOf(4.5D), Double.valueOf(0.1D), Double.valueOf(8.0D)));
/*    */   
/*    */   public Ranges() {
/* 30 */     super("Ranges", "Draws a circle around the player.", Module.Category.RENDER, false, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {}
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/* 39 */     if (((Boolean)this.circle.getValue()).booleanValue()) {
/* 40 */       RenderUtil.prepareGL3D();
/* 41 */       GlStateManager.pushMatrix();
/* 42 */       GlStateManager.enableBlend();
/* 43 */       GlStateManager.disableTexture2D();
/* 44 */       GlStateManager.enableDepth();
/* 45 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 46 */       RenderManager renderManager = mc.getRenderManager();
/* 47 */       float hue = (float)(System.currentTimeMillis() % 7200L) / 7200.0F;
/* 48 */       Color color = new Color(Color.HSBtoRGB(hue, 1.0F, 1.0F));
/* 49 */       ArrayList<Vec3d> hVectors = new ArrayList<>();
/* 50 */       double x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks() - renderManager.renderPosX;
/* 51 */       double y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks() - renderManager.renderPosY;
/* 52 */       double z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks() - renderManager.renderPosZ;
/* 53 */       GL11.glLineWidth(((Float)this.lineWidth.getValue()).floatValue());
/* 54 */       GL11.glBegin(1);
/* 55 */       for (int i = 0; i <= 360; i++) {
/* 56 */         Vec3d vec = new Vec3d(x + Math.sin(i * Math.PI / 180.0D) * ((Double)this.radius.getValue()).doubleValue(), y + 0.1D, z + Math.cos(i * Math.PI / 180.0D) * ((Double)this.radius.getValue()).doubleValue());
/* 57 */         RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), vec, false, false, true);
/* 58 */         if (result != null && ((Boolean)this.raytrace.getValue()).booleanValue()) {
/* 59 */           OyVey.LOGGER.info("raytrace was not null");
/* 60 */           hVectors.add(result.hitVec);
/*    */         } else {
/*    */           
/* 63 */           hVectors.add(vec);
/*    */         } 
/* 65 */       }  for (int j = 0; j < hVectors.size() - 1; j++) {
/* 66 */         GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 67 */         GL11.glVertex3d(((Vec3d)hVectors.get(j)).x, ((Vec3d)hVectors.get(j)).y, ((Vec3d)hVectors.get(j)).z);
/* 68 */         GL11.glVertex3d(((Vec3d)hVectors.get(j + 1)).x, ((Vec3d)hVectors.get(j + 1)).y, ((Vec3d)hVectors.get(j + 1)).z);
/* 69 */         color = new Color(Color.HSBtoRGB(hue += 0.0027777778F, 1.0F, 1.0F));
/*    */       } 
/* 71 */       GL11.glEnd();
/* 72 */       GlStateManager.resetColor();
/* 73 */       GlStateManager.disableDepth();
/* 74 */       GlStateManager.enableTexture2D();
/* 75 */       GlStateManager.disableBlend();
/* 76 */       GlStateManager.popMatrix();
/* 77 */       RenderUtil.releaseGL3D();
/*    */     } 
/* 79 */     if (((Boolean)this.hitSpheres.getValue()).booleanValue())
/* 80 */       for (EntityPlayer player : mc.world.playerEntities) {
/* 81 */         if (player == null || (player.equals(mc.player) && !((Boolean)this.ownSphere.getValue()).booleanValue()))
/*    */           continue; 
/* 83 */         Vec3d interpolated = EntityUtil.interpolateEntity((Entity)player, event.getPartialTicks());
/* 84 */         if (OyVey.friendManager.isFriend(player.getName())) {
/* 85 */           GL11.glColor4f(0.15F, 0.15F, 1.0F, 1.0F);
/* 86 */         } else if (mc.player.getDistance((Entity)player) >= 64.0F) {
/* 87 */           GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
/*    */         } else {
/* 89 */           GL11.glColor4f(1.0F, mc.player.getDistance((Entity)player) / 150.0F, 0.0F, 1.0F);
/*    */         } 
/* 91 */         RenderUtil.prepareGL3D();
/* 92 */         RenderUtil.drawSphere(interpolated.x, interpolated.y, interpolated.z, ((Double)this.radius.getValue()).floatValue(), 20, 15);
/* 93 */         RenderUtil.releaseGL3D();
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Ranges.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
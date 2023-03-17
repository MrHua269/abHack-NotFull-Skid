/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.modules.combat.HFAura;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.MathUtil;
/*    */ import me.abHack.util.abUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class Tracer
/*    */   extends Module
/*    */ {
/* 22 */   public Setting<Boolean> players = register(new Setting("Players", Boolean.valueOf(true)));
/* 23 */   public Setting<Boolean> mobs = register(new Setting("Mobs", Boolean.valueOf(false)));
/* 24 */   public Setting<Boolean> animals = register(new Setting("Animals", Boolean.valueOf(false)));
/* 25 */   public Setting<Boolean> invisibles = register(new Setting("Invisibles", Boolean.valueOf(false)));
/* 26 */   public Setting<Boolean> drawFromSky = register(new Setting("DrawFromSky", Boolean.valueOf(false)));
/* 27 */   public Setting<Float> width = register(new Setting("Width", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/* 28 */   public Setting<Integer> distance = register(new Setting("Ranger", Integer.valueOf(60), Integer.valueOf(1), Integer.valueOf(140)));
/* 29 */   public Setting<Boolean> crystalCheck = register(new Setting("CrystalCheck", Boolean.valueOf(false)));
/*    */   
/*    */   public Tracer() {
/* 32 */     super("Tracers", "Draws lines to other players.", Module.Category.RENDER, false, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/* 37 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/*    */     
/* 41 */     RenderUtil.prepareGL3D();
/* 42 */     GlStateManager.glLineWidth(((Float)this.width.getValue()).floatValue());
/*    */     
/* 44 */     mc.world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter(entity -> (entity instanceof net.minecraft.entity.player.EntityPlayer) ? ((((Boolean)this.players.getValue()).booleanValue() && mc.player != entity && abUtil.Freecamcheck(entity))) : (EntityUtil.isPassive(entity) ? ((Boolean)this.animals.getValue()).booleanValue() : ((Boolean)this.mobs.getValue()).booleanValue())).filter(entity -> (mc.player.getDistanceSq(entity) < MathUtil.square(((Integer)this.distance.getValue()).intValue()))).filter(entity -> (((Boolean)this.invisibles.getValue()).booleanValue() || !entity.isInvisible())).forEach(entity -> {
/*    */           float[] colour = getColorByDistance(entity);
/*    */           
/*    */           GlStateManager.color(colour[0], colour[1], colour[2], colour[3]);
/*    */           drawLineToEntity(entity);
/*    */         });
/* 50 */     RenderUtil.releaseGL3D();
/*    */   }
/*    */   
/*    */   public double interpolate(double now, double then) {
/* 54 */     return then + (now - then) * mc.getRenderPartialTicks();
/*    */   }
/*    */   
/*    */   public double[] interpolate(Entity entity) {
/* 58 */     double posX = interpolate(entity.posX, entity.lastTickPosX) - (mc.getRenderManager()).renderPosX;
/* 59 */     double posY = interpolate(entity.posY, entity.lastTickPosY) - (mc.getRenderManager()).renderPosY;
/* 60 */     double posZ = interpolate(entity.posZ, entity.lastTickPosZ) - (mc.getRenderManager()).renderPosZ;
/* 61 */     return new double[] { posX, posY, posZ };
/*    */   }
/*    */   
/*    */   public void drawLineToEntity(Entity e) {
/* 65 */     double[] xyz = interpolate(e);
/* 66 */     drawLine(xyz[0], xyz[1], xyz[2]);
/*    */   }
/*    */   
/*    */   public void drawLine(double posx, double posy, double posz) {
/* 70 */     Vec3d eyes = (new Vec3d(0.0D, 0.0D, 1.0D)).rotatePitch(-((float)Math.toRadians(mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(mc.player.rotationYaw)));
/* 71 */     if (!((Boolean)this.drawFromSky.getValue()).booleanValue()) {
/* 72 */       drawLineFromPosToPos(eyes.x, eyes.y + mc.player.getEyeHeight(), eyes.z, posx, posy, posz);
/*    */     } else {
/* 74 */       drawLineFromPosToPos(posx, 256.0D, posz, posx, posy, posz);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2) {
/* 79 */     GL11.glLoadIdentity();
/* 80 */     mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
/* 81 */     GL11.glBegin(1);
/* 82 */     GL11.glVertex3d(posx, posy, posz);
/* 83 */     GL11.glVertex3d(posx2, posy2, posz2);
/* 84 */     GL11.glVertex3d(posx2, posy2, posz2);
/* 85 */     GL11.glEnd();
/*    */   }
/*    */   
/*    */   public float[] getColorByDistance(Entity entity) {
/* 89 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer && OyVey.friendManager.isFriend(entity.getName())) {
/* 90 */       return new float[] { 0.0F, 0.5F, 1.0F, 1.0F };
/*    */     }
/* 92 */     HFAura hfAura = (HFAura)OyVey.moduleManager.getModuleByClass(HFAura.class);
/* 93 */     Color col = new Color(Color.HSBtoRGB((float)(Math.max(0.0D, Math.min(mc.player.getDistanceSq(entity), ((Boolean)this.crystalCheck.getValue()).booleanValue() ? (((Double)hfAura.placeRange.getValue()).doubleValue() * ((Double)hfAura.placeRange.getValue()).floatValue()) : 2500.0D) / (((Boolean)this.crystalCheck.getValue()).booleanValue() ? (((Double)hfAura.placeRange.getValue()).doubleValue() * ((Double)hfAura.placeRange.getValue()).doubleValue()) : 2500.0D)) / 3.0D), 1.0F, 0.8F) | 0xFF000000);
/* 94 */     return new float[] { col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, 1.0F };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Tracer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
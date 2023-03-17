/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Breadcrumbs
/*     */   extends Module
/*     */ {
/*     */   public static Breadcrumbs INSTANCE;
/*  18 */   private final LinkedList<Position> positions = new LinkedList<>();
/*     */ 
/*     */   
/*  21 */   public Setting<Boolean> infinite = register(new Setting("Infinite", Boolean.valueOf(false)));
/*     */   
/*  23 */   public Setting<Float> lifespan = register(new Setting("Lifespan", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(15.0F), v -> !((Boolean)this.infinite.getValue()).booleanValue()));
/*     */   
/*  25 */   public Setting<Float> width = register(new Setting("Width", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*     */ 
/*     */   
/*     */   public Breadcrumbs() {
/*  29 */     super("Breadcrumbs", "Draws a trail behind you", Module.Category.RENDER, true, false, false);
/*  30 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  35 */     super.onDisable();
/*     */ 
/*     */     
/*  38 */     this.positions.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  43 */     if (mc.player.ticksExisted <= 20) {
/*     */ 
/*     */       
/*  46 */       this.positions.clear();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  52 */     this.positions.add(new Position(new Vec3d(mc.player.lastTickPosX, mc.player.lastTickPosY, mc.player.lastTickPosZ), System.currentTimeMillis()));
/*     */ 
/*     */     
/*  55 */     this.positions.removeIf(position -> ((float)(System.currentTimeMillis() - position.getTime()) >= ((Float)this.lifespan.getValue()).floatValue() * 1000.0F && !((Boolean)this.infinite.getValue()).booleanValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  60 */     RenderUtil.prepareGL3D();
/*  61 */     GL11.glPushMatrix();
/*  62 */     GL11.glDisable(3553);
/*  63 */     GL11.glBlendFunc(770, 771);
/*  64 */     GL11.glEnable(2848);
/*  65 */     GL11.glEnable(3042);
/*  66 */     GL11.glDisable(2929);
/*  67 */     GL11.glLineWidth(((Float)this.width.getValue()).floatValue());
/*     */ 
/*     */     
/*  70 */     mc.entityRenderer.disableLightmap();
/*     */     
/*  72 */     GL11.glBegin(3);
/*     */ 
/*     */     
/*  75 */     this.positions.forEach(position -> {
/*     */           GL11.glColor4f(ColorUtil.getPrimaryColor().getRed() / 255.0F, ColorUtil.getPrimaryColor().getGreen() / 255.0F, ColorUtil.getPrimaryColor().getBlue() / 255.0F, 1.0F);
/*     */ 
/*     */ 
/*     */           
/*     */           GL11.glVertex3d((position.getVec()).x - (mc.getRenderManager()).viewerPosX, (position.getVec()).y - (mc.getRenderManager()).viewerPosY, (position.getVec()).z - (mc.getRenderManager()).viewerPosZ);
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*  85 */     GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/*     */     
/*  87 */     GL11.glEnd();
/*  88 */     GL11.glEnable(2929);
/*  89 */     GL11.glDisable(2848);
/*  90 */     GL11.glDisable(3042);
/*  91 */     GL11.glEnable(3553);
/*  92 */     GL11.glPopMatrix();
/*  93 */     RenderUtil.releaseGL3D();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Position
/*     */   {
/*     */     private final Vec3d vec;
/*     */     
/*     */     private final long time;
/*     */ 
/*     */     
/*     */     public Position(Vec3d vec, long time) {
/* 105 */       this.vec = vec;
/* 106 */       this.time = time;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Vec3d getVec() {
/* 115 */       return this.vec;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getTime() {
/* 124 */       return this.time;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Breadcrumbs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
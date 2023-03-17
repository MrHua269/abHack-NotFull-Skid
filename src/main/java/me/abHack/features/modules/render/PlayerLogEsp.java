/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import me.abHack.event.events.ConnectionEvent;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class PlayerLogEsp
/*     */   extends Module
/*     */ {
/*  25 */   private final Setting<Integer> red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/*  26 */   private final Setting<Integer> green = register(new Setting("Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/*  27 */   private final Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/*  28 */   private final Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/*  29 */   private final Setting<Boolean> rainbow = register(new Setting("Rainbow", Boolean.valueOf(false)));
/*  30 */   private final Setting<Integer> rainbowhue = register(new Setting("Brightness", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.rainbow.getValue()).booleanValue()));
/*  31 */   private final Setting<Boolean> scaleing = register(new Setting("Scale", Boolean.valueOf(false)));
/*  32 */   private final Setting<Float> scaling = register(new Setting("Size", Float.valueOf(4.0F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
/*  33 */   private final Setting<Float> factor = register(new Setting("Factor", Float.valueOf(0.3F), Float.valueOf(0.1F), Float.valueOf(1.0F), v -> ((Boolean)this.scaleing.getValue()).booleanValue()));
/*  34 */   private final Setting<Boolean> smartScale = register(new Setting("SmartScale", Boolean.valueOf(false), v -> ((Boolean)this.scaleing.getValue()).booleanValue()));
/*  35 */   private final Setting<Boolean> rect = register(new Setting("Rectangle", Boolean.valueOf(true)));
/*  36 */   private final Setting<Boolean> coords = register(new Setting("Coords", Boolean.valueOf(true)));
/*  37 */   private final List<LogoutPos> spots = new CopyOnWriteArrayList<>();
/*  38 */   public Setting<Float> range = register(new Setting("Range", Float.valueOf(300.0F), Float.valueOf(50.0F), Float.valueOf(500.0F)));
/*  39 */   public Setting<Boolean> message = register(new Setting("Message", Boolean.valueOf(false)));
/*     */   
/*     */   public PlayerLogEsp() {
/*  42 */     super("PlayerLogEsp", "Render player log coords", Module.Category.RENDER, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLogout() {
/*  47 */     this.spots.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  52 */     this.spots.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  57 */     if (!this.spots.isEmpty()) {
/*  58 */       synchronized (this.spots) {
/*  59 */         this.spots.forEach(spot -> {
/*     */               if (spot.getEntity() != null) {
/*     */                 AxisAlignedBB bb = RenderUtil.interpolateAxis(spot.getEntity().getEntityBoundingBox());
/*     */                 RenderUtil.drawBlockOutline(bb, ((Boolean)this.rainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)this.rainbowhue.getValue()).intValue()) : new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue()), 1.0F);
/*     */                 double x = interpolate((spot.getEntity()).lastTickPosX, (spot.getEntity()).posX, event.getPartialTicks()) - (mc.getRenderManager()).renderPosX;
/*     */                 double y = interpolate((spot.getEntity()).lastTickPosY, (spot.getEntity()).posY, event.getPartialTicks()) - (mc.getRenderManager()).renderPosY;
/*     */                 double z = interpolate((spot.getEntity()).lastTickPosZ, (spot.getEntity()).posZ, event.getPartialTicks()) - (mc.getRenderManager()).renderPosZ;
/*     */                 renderNameTag(spot.getName(), x, y, z, event.getPartialTicks(), spot.getX(), spot.getY(), spot.getZ());
/*     */               } 
/*     */             });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  75 */     if (!fullNullCheck()) {
/*  76 */       this.spots.removeIf(spot -> (mc.player.getDistanceSq((Entity)spot.getEntity()) >= MathUtil.square(((Float)this.range.getValue()).floatValue())));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConnection(ConnectionEvent event) {
/*  82 */     if (event.getStage() == 0) {
/*  83 */       UUID uuid = event.getUuid();
/*  84 */       EntityPlayer entity = mc.world.getPlayerEntityByUUID(uuid);
/*  85 */       if (entity != null && ((Boolean)this.message.getValue()).booleanValue()) {
/*  86 */         Command.sendMessagePh("§a" + entity.getName() + " just logged in" + (((Boolean)this.coords.getValue()).booleanValue() ? (" at (" + entity.posX + ", " + entity.posY + ", " + entity.posZ + ")!") : "!"));
/*     */       }
/*  88 */       this.spots.removeIf(pos -> pos.getName().equalsIgnoreCase(event.getName()));
/*  89 */     } else if (event.getStage() == 1) {
/*  90 */       EntityPlayer entity = event.getEntity();
/*  91 */       UUID uuid = event.getUuid();
/*  92 */       String name = event.getName();
/*  93 */       if (((Boolean)this.message.getValue()).booleanValue()) {
/*  94 */         Command.sendMessagePh("§c" + event.getName() + " just logged out" + (((Boolean)this.coords.getValue()).booleanValue() ? (" at (" + entity.posX + ", " + entity.posY + ", " + entity.posZ + ")!") : "!"));
/*     */       }
/*  96 */       if (name != null && entity != null && uuid != null) {
/*  97 */         this.spots.add(new LogoutPos(name, entity));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderNameTag(String name, double x, double yi, double z, float delta, double xPos, double yPos, double zPos) {
/* 103 */     double y = yi + 0.7D;
/* 104 */     Entity camera = mc.getRenderViewEntity();
/* 105 */     assert camera != null;
/* 106 */     double originalPositionX = camera.posX;
/* 107 */     double originalPositionY = camera.posY;
/* 108 */     double originalPositionZ = camera.posZ;
/* 109 */     camera.posX = interpolate(camera.prevPosX, camera.posX, delta);
/* 110 */     camera.posY = interpolate(camera.prevPosY, camera.posY, delta);
/* 111 */     camera.posZ = interpolate(camera.prevPosZ, camera.posZ, delta);
/* 112 */     String displayTag = name + " XYZ: " + (int)xPos + ", " + (int)yPos + ", " + (int)zPos;
/* 113 */     double distance = camera.getDistance(x + (mc.getRenderManager()).viewerPosX, y + (mc.getRenderManager()).viewerPosY, z + (mc.getRenderManager()).viewerPosZ);
/* 114 */     int width = this.renderer.getStringWidth(displayTag) / 2;
/* 115 */     double scale = (0.0018D + ((Float)this.scaling.getValue()).floatValue() * distance * ((Float)this.factor.getValue()).floatValue()) / 1000.0D;
/* 116 */     if (distance <= 8.0D && ((Boolean)this.smartScale.getValue()).booleanValue()) {
/* 117 */       scale = 0.0245D;
/*     */     }
/* 119 */     if (!((Boolean)this.scaleing.getValue()).booleanValue()) {
/* 120 */       scale = ((Float)this.scaling.getValue()).floatValue() / 100.0D;
/*     */     }
/* 122 */     GlStateManager.pushMatrix();
/* 123 */     RenderHelper.enableStandardItemLighting();
/* 124 */     GlStateManager.enablePolygonOffset();
/* 125 */     GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
/* 126 */     GlStateManager.disableLighting();
/* 127 */     GlStateManager.translate((float)x, (float)y + 1.4F, (float)z);
/* 128 */     GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 129 */     GlStateManager.rotate((mc.getRenderManager()).playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/* 130 */     GlStateManager.scale(-scale, -scale, scale);
/* 131 */     GlStateManager.disableDepth();
/* 132 */     GlStateManager.enableBlend();
/* 133 */     GlStateManager.enableBlend();
/* 134 */     if (((Boolean)this.rect.getValue()).booleanValue()) {
/* 135 */       RenderUtil.drawRect((-width - 2), -(this.renderer.getFontHeight() + 1), width + 2.0F, 1.5F, 1426063360);
/*     */     }
/* 137 */     GlStateManager.disableBlend();
/* 138 */     this.renderer.drawStringWithShadow(displayTag, -width, -(this.renderer.getFontHeight() - 1), ColorUtil.toRGBA(new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue())));
/* 139 */     camera.posX = originalPositionX;
/* 140 */     camera.posY = originalPositionY;
/* 141 */     camera.posZ = originalPositionZ;
/* 142 */     GlStateManager.enableDepth();
/* 143 */     GlStateManager.disableBlend();
/* 144 */     GlStateManager.disablePolygonOffset();
/* 145 */     GlStateManager.doPolygonOffset(1.0F, 1500000.0F);
/* 146 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private double interpolate(double previous, double current, float delta) {
/* 150 */     return previous + (current - previous) * delta;
/*     */   }
/*     */   
/*     */   private static class LogoutPos {
/*     */     private final String name;
/*     */     private final EntityPlayer entity;
/*     */     private final double x;
/*     */     private final double y;
/*     */     private final double z;
/*     */     
/*     */     public LogoutPos(String name, EntityPlayer entity) {
/* 161 */       this.name = name;
/* 162 */       this.entity = entity;
/* 163 */       this.x = entity.posX;
/* 164 */       this.y = entity.posY;
/* 165 */       this.z = entity.posZ;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 169 */       return this.name;
/*     */     }
/*     */     
/*     */     public EntityPlayer getEntity() {
/* 173 */       return this.entity;
/*     */     }
/*     */     
/*     */     public double getX() {
/* 177 */       return this.x;
/*     */     }
/*     */     
/*     */     public double getY() {
/* 181 */       return this.y;
/*     */     }
/*     */     
/*     */     public double getZ() {
/* 185 */       return this.z;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\PlayerLogEsp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
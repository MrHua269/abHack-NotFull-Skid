/*     */ package me.abHack.util.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.abHack.features.modules.render.PopChams;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TotemPopChams {
/*  19 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*     */   EntityOtherPlayerMP player;
/*     */   
/*     */   ModelPlayer playerModel;
/*     */   
/*     */   Long startTime;
/*     */   
/*     */   double alphaFill;
/*     */   
/*     */   double alphaLine;
/*     */   
/*     */   public TotemPopChams(EntityOtherPlayerMP player, ModelPlayer playerModel, Long startTime, double alphaFill, double alphaLine) {
/*  32 */     this.alphaLine = alphaLine;
/*  33 */     MinecraftForge.EVENT_BUS.register(this);
/*  34 */     this.player = player;
/*  35 */     this.playerModel = playerModel;
/*  36 */     this.startTime = startTime;
/*  37 */     this.alphaFill = alphaFill;
/*  38 */     this.alphaLine = alphaFill;
/*     */   }
/*     */   
/*     */   public static void renderEntity(EntityLivingBase entity, ModelBase modelBase, float limbSwing, float limbSwingAmount, float scale) {
/*  42 */     float partialTicks = mc.getRenderPartialTicks();
/*  43 */     double x = entity.posX - (mc.getRenderManager()).viewerPosX;
/*  44 */     double y = entity.posY - (mc.getRenderManager()).viewerPosY;
/*  45 */     double z = entity.posZ - (mc.getRenderManager()).viewerPosZ;
/*  46 */     GlStateManager.pushMatrix();
/*  47 */     if (entity.isSneaking())
/*  48 */       y -= 0.125D; 
/*  49 */     renderLivingAt(x, y, z);
/*  50 */     float f8 = handleRotationFloat();
/*  51 */     prepareRotations(entity);
/*  52 */     float f9 = prepareScale(entity, scale);
/*  53 */     GlStateManager.enableAlpha();
/*  54 */     modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
/*  55 */     modelBase.setRotationAngles(limbSwing, limbSwingAmount, f8, entity.rotationYawHead, entity.rotationPitch, f9, (Entity)entity);
/*  56 */     modelBase.render((Entity)entity, limbSwing, limbSwingAmount, f8, entity.rotationYawHead, entity.rotationPitch, f9);
/*  57 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public static void renderLivingAt(double x, double y, double z) {
/*  61 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*     */   }
/*     */   
/*     */   public static float prepareScale(EntityLivingBase entity, float scale) {
/*  65 */     GlStateManager.enableRescaleNormal();
/*  66 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/*  67 */     double widthX = (entity.getRenderBoundingBox()).maxX - (entity.getRenderBoundingBox()).minX;
/*  68 */     double widthZ = (entity.getRenderBoundingBox()).maxZ - (entity.getRenderBoundingBox()).minZ;
/*  69 */     GlStateManager.scale(scale + widthX, (scale * entity.height), scale + widthZ);
/*  70 */     GlStateManager.translate(0.0F, -1.501F, 0.0F);
/*  71 */     return 0.0625F;
/*     */   }
/*     */   
/*     */   public static void prepareRotations(EntityLivingBase entityLivingBase) {
/*  75 */     GlStateManager.rotate(180.0F - entityLivingBase.rotationYaw, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static Color newAlpha(Color color, int alpha) {
/*  79 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
/*     */   }
/*     */   
/*     */   public static void glColor(Color color) {
/*  83 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   public static float handleRotationFloat() {
/*  87 */     return 0.0F;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderWorld(RenderWorldLastEvent event) {
/*  92 */     if (this.player == null || mc.world == null || mc.player == null)
/*     */       return; 
/*  94 */     GL11.glLineWidth(1.0F);
/*  95 */     Color lineColorS = new Color(((Integer)PopChams.rL.getValue()).intValue(), ((Integer)PopChams.gL.getValue()).intValue(), ((Integer)PopChams.bL.getValue()).intValue(), ((Integer)PopChams.aL.getValue()).intValue());
/*  96 */     Color fillColorS = new Color(((Integer)PopChams.rF.getValue()).intValue(), ((Integer)PopChams.gF.getValue()).intValue(), ((Integer)PopChams.bF.getValue()).intValue(), ((Integer)PopChams.aF.getValue()).intValue());
/*  97 */     int lineA = lineColorS.getAlpha();
/*  98 */     int fillA = fillColorS.getAlpha();
/*  99 */     long time = System.currentTimeMillis() - this.startTime.longValue() - ((Number)PopChams.fadestart.getValue()).longValue();
/* 100 */     if (System.currentTimeMillis() - this.startTime.longValue() > ((Number)PopChams.fadestart.getValue()).longValue()) {
/* 101 */       double normal = normalize(time, ((Number)PopChams.fadetime.getValue()).doubleValue());
/* 102 */       normal = MathHelper.clamp(normal, 0.0D, 1.0D);
/* 103 */       normal = -normal + 1.0D;
/* 104 */       lineA *= (int)normal;
/* 105 */       fillA *= (int)normal;
/*     */     } 
/* 107 */     Color lineColor = newAlpha(lineColorS, lineA);
/* 108 */     Color fillColor = newAlpha(fillColorS, fillA);
/* 109 */     if (this.player != null && this.playerModel != null) {
/* 110 */       PopChams.prepareGL();
/* 111 */       GL11.glPushAttrib(1048575);
/* 112 */       GL11.glEnable(2881);
/* 113 */       GL11.glEnable(2848);
/* 114 */       if (this.alphaFill > 1.0D)
/* 115 */         this.alphaFill -= ((Float)PopChams.fadetime.getValue()).floatValue(); 
/* 116 */       Color fillFinal = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int)this.alphaFill);
/* 117 */       if (this.alphaLine > 1.0D)
/* 118 */         this.alphaLine -= ((Float)PopChams.fadetime.getValue()).floatValue(); 
/* 119 */       Color outlineFinal = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int)this.alphaLine);
/* 120 */       glColor(fillFinal);
/* 121 */       GL11.glPolygonMode(1032, 6914);
/* 122 */       renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, 1.0F);
/* 123 */       glColor(outlineFinal);
/* 124 */       GL11.glPolygonMode(1032, 6913);
/* 125 */       renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, 1.0F);
/* 126 */       GL11.glPolygonMode(1032, 6914);
/* 127 */       GL11.glPopAttrib();
/* 128 */       PopChams.releaseGL();
/*     */     } 
/*     */   }
/*     */   
/*     */   double normalize(double value, double max) {
/* 133 */     return (value - 0.0D) / (max - 0.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\TotemPopChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
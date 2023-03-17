/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.Cylinder;
/*     */ 
/*     */ 
/*     */ public class Trajectories
/*     */   extends Module
/*     */ {
/*     */   public Trajectories() {
/*  26 */     super("Trajectories", "Shows the way of projectiles.", Module.Category.RENDER, false, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  31 */     if (mc.world == null || mc.player == null) {
/*     */       return;
/*     */     }
/*  34 */     drawTrajectories((EntityPlayer)mc.player, event.getPartialTicks());
/*     */   }
/*     */   
/*     */   public void enableGL3D(float lineWidth) {
/*  38 */     GL11.glDisable(3008);
/*  39 */     GL11.glEnable(3042);
/*  40 */     GL11.glBlendFunc(770, 771);
/*  41 */     GL11.glDisable(3553);
/*  42 */     GL11.glDisable(2929);
/*  43 */     GL11.glDepthMask(false);
/*  44 */     GL11.glEnable(2884);
/*  45 */     mc.entityRenderer.disableLightmap();
/*  46 */     GL11.glEnable(2848);
/*  47 */     GL11.glHint(3154, 4354);
/*  48 */     GL11.glHint(3155, 4354);
/*  49 */     GL11.glLineWidth(lineWidth);
/*     */   }
/*     */   
/*     */   public void disableGL3D() {
/*  53 */     GL11.glEnable(3553);
/*  54 */     GL11.glEnable(2929);
/*  55 */     GL11.glDisable(3042);
/*  56 */     GL11.glEnable(3008);
/*  57 */     GL11.glDepthMask(true);
/*  58 */     GL11.glCullFace(1029);
/*  59 */     GL11.glDisable(2848);
/*  60 */     GL11.glHint(3154, 4352);
/*  61 */     GL11.glHint(3155, 4352);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawTrajectories(EntityPlayer player, float partialTicks) {
/*  66 */     double renderPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
/*  67 */     double renderPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
/*  68 */     double renderPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
/*  69 */     player.getHeldItem(EnumHand.MAIN_HAND);
/*  70 */     if (mc.gameSettings.thirdPersonView != 0 || (!(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof net.minecraft.item.ItemBow) && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof net.minecraft.item.ItemFishingRod) && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof net.minecraft.item.ItemEnderPearl) && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof net.minecraft.item.ItemEgg) && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof net.minecraft.item.ItemSnowball) && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof net.minecraft.item.ItemExpBottle))) {
/*     */       return;
/*     */     }
/*  73 */     RenderUtil.prepareGL3D();
/*  74 */     GL11.glPushMatrix();
/*  75 */     Item item = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
/*  76 */     double posX = renderPosX - (MathHelper.cos(player.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  77 */     double posY = renderPosY + player.getEyeHeight() - 0.1000000014901161D;
/*  78 */     double posZ = renderPosZ - (MathHelper.sin(player.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  79 */     double motionX = (-MathHelper.sin(player.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(player.rotationPitch / 180.0F * 3.1415927F)) * ((item instanceof net.minecraft.item.ItemBow) ? 1.0D : 0.4D);
/*  80 */     double motionY = -MathHelper.sin(player.rotationPitch / 180.0F * 3.1415927F) * ((item instanceof net.minecraft.item.ItemBow) ? 1.0D : 0.4D);
/*  81 */     double motionZ = (MathHelper.cos(player.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(player.rotationPitch / 180.0F * 3.1415927F)) * ((item instanceof net.minecraft.item.ItemBow) ? 1.0D : 0.4D);
/*  82 */     int var6 = 72000 - player.getItemInUseCount();
/*  83 */     float power = var6 / 20.0F;
/*  84 */     power = (power * power + power * 2.0F) / 3.0F;
/*  85 */     if (power > 1.0F) {
/*  86 */       power = 1.0F;
/*     */     }
/*  88 */     float distance = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
/*  89 */     motionX /= distance;
/*  90 */     motionY /= distance;
/*  91 */     motionZ /= distance;
/*  92 */     float pow = (item instanceof net.minecraft.item.ItemBow) ? (power * 2.0F) : ((item instanceof net.minecraft.item.ItemFishingRod) ? 1.25F : ((player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.9F : 1.0F));
/*     */     
/*  94 */     motionX *= (pow * ((item instanceof net.minecraft.item.ItemFishingRod) ? 0.75F : ((player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75F : 1.5F)));
/*  95 */     motionY *= (pow * ((item instanceof net.minecraft.item.ItemFishingRod) ? 0.75F : ((player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75F : 1.5F)));
/*  96 */     motionZ *= (pow * ((item instanceof net.minecraft.item.ItemFishingRod) ? 0.75F : ((player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE) ? 0.75F : 1.5F)));
/*  97 */     enableGL3D(2.0F);
/*  98 */     if (power > 0.6F) {
/*  99 */       GlStateManager.color(0.0F, 1.0F, 0.0F, 1.0F);
/*     */     } else {
/* 101 */       GlStateManager.color(0.8F, 0.5F, 0.0F, 1.0F);
/*     */     } 
/* 103 */     GL11.glEnable(2848);
/* 104 */     float size = (float)((item instanceof net.minecraft.item.ItemBow) ? 0.3D : 0.25D);
/* 105 */     boolean hasLanded = false;
/* 106 */     Entity landingOnEntity = null;
/* 107 */     RayTraceResult landingPosition = null;
/* 108 */     while (!hasLanded && posY > 0.0D) {
/* 109 */       Vec3d present = new Vec3d(posX, posY, posZ);
/* 110 */       Vec3d future = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
/* 111 */       RayTraceResult possibleLandingStrip = mc.world.rayTraceBlocks(present, future, false, true, false);
/* 112 */       if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != RayTraceResult.Type.MISS) {
/* 113 */         landingPosition = possibleLandingStrip;
/* 114 */         hasLanded = true;
/*     */       } 
/* 116 */       AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
/* 117 */       List entities = getEntitiesWithinAABB(arrowBox.offset(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
/* 118 */       for (Object entity : entities) {
/* 119 */         Entity boundingBox = (Entity)entity;
/* 120 */         if (!boundingBox.canBeCollidedWith() || boundingBox == player)
/* 121 */           continue;  float var7 = 0.3F;
/* 122 */         AxisAlignedBB var8 = boundingBox.getEntityBoundingBox().expand(var7, var7, var7);
/* 123 */         RayTraceResult possibleEntityLanding = var8.calculateIntercept(present, future);
/* 124 */         if (possibleEntityLanding == null)
/* 125 */           continue;  hasLanded = true;
/* 126 */         landingOnEntity = boundingBox;
/* 127 */         landingPosition = possibleEntityLanding;
/*     */       } 
/* 129 */       if (landingOnEntity != null) {
/* 130 */         GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
/*     */       }
/* 132 */       posX += motionX;
/* 133 */       posY += motionY;
/* 134 */       posZ += motionZ;
/* 135 */       float motionAdjustment = 0.99F;
/* 136 */       motionX *= motionAdjustment;
/* 137 */       motionY *= motionAdjustment;
/* 138 */       motionZ *= motionAdjustment;
/* 139 */       motionY -= (item instanceof net.minecraft.item.ItemBow) ? 0.05D : 0.03D;
/*     */     } 
/* 141 */     if (landingPosition != null && landingPosition.typeOfHit == RayTraceResult.Type.BLOCK) {
/* 142 */       GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
/* 143 */       int side = landingPosition.sideHit.getIndex();
/* 144 */       if (side == 2) {
/* 145 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 146 */       } else if (side == 3) {
/* 147 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 148 */       } else if (side == 4) {
/* 149 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 150 */       } else if (side == 5) {
/* 151 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/* 153 */       Cylinder c = new Cylinder();
/* 154 */       GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/* 155 */       c.setDrawStyle(100011);
/* 156 */       if (landingOnEntity != null) {
/* 157 */         GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
/* 158 */         GL11.glLineWidth(2.5F);
/* 159 */         c.draw(0.6F, 0.3F, 0.0F, 4, 1);
/* 160 */         GL11.glLineWidth(0.1F);
/* 161 */         GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/* 163 */       c.draw(0.6F, 0.3F, 0.0F, 4, 1);
/*     */     } 
/* 165 */     disableGL3D();
/* 166 */     GL11.glPopMatrix();
/* 167 */     RenderUtil.releaseGL3D();
/*     */   }
/*     */   
/*     */   private List getEntitiesWithinAABB(AxisAlignedBB bb) {
/* 171 */     ArrayList list = new ArrayList();
/* 172 */     int chunkMinX = MathHelper.floor((bb.minX - 2.0D) / 16.0D);
/* 173 */     int chunkMaxX = MathHelper.floor((bb.maxX + 2.0D) / 16.0D);
/* 174 */     int chunkMinZ = MathHelper.floor((bb.minZ - 2.0D) / 16.0D);
/* 175 */     int chunkMaxZ = MathHelper.floor((bb.maxZ + 2.0D) / 16.0D);
/* 176 */     for (int x = chunkMinX; x <= chunkMaxX; x++) {
/* 177 */       for (int z = chunkMinZ; z <= chunkMaxZ; z++) {
/* 178 */         if (mc.world.getChunkProvider().getLoadedChunk(x, z) != null)
/* 179 */           mc.world.getChunk(x, z).getEntitiesWithinAABBForEntity((Entity)mc.player, bb, list, null);
/*     */       } 
/*     */     } 
/* 182 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Trajectories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.awt.Color;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.client.ClickGui;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PopChams
/*     */   extends Module
/*     */ {
/*     */   public static Setting<Boolean> self;
/*     */   public static Setting<Boolean> elevator;
/*     */   public static Setting<Integer> rL;
/*     */   public static Setting<Integer> gL;
/*     */   public static Setting<Integer> bL;
/*     */   public static Setting<Integer> aL;
/*     */   public static Setting<Integer> rF;
/*     */   public static Setting<Integer> gF;
/*     */   public static Setting<Integer> bF;
/*     */   public static Setting<Integer> aF;
/*     */   public static Setting<Integer> fadestart;
/*     */   public static Setting<Float> fadetime;
/*     */   public static Setting<Boolean> fillrainbow;
/*     */   public static Setting<Boolean> outlinerainbow;
/*     */   public static Setting<Boolean> onlyOneEsp;
/*     */   public static Setting<ElevatorMode> elevatorMode;
/*     */   EntityOtherPlayerMP player;
/*     */   ModelPlayer playerModel;
/*     */   Long startTime;
/*     */   double alphaFill;
/*     */   double alphaLine;
/*     */   
/*     */   public PopChams() {
/*  66 */     super("PopChams", "Pop rendering", Module.Category.RENDER, true, false, false);
/*  67 */     self = register(new Setting("Render Own Pops", Boolean.valueOf(true)));
/*  68 */     elevator = register(new Setting("Travel", Boolean.valueOf(true)));
/*  69 */     elevatorMode = register(new Setting("Elevator", ElevatorMode.UP, v -> ((Boolean)elevator.getValue()).booleanValue()));
/*  70 */     rL = register(new Setting("Outline Red", Integer.valueOf(30), Integer.valueOf(0), Integer.valueOf(255)));
/*  71 */     bL = register(new Setting("Outline Green", Integer.valueOf(167), Integer.valueOf(0), Integer.valueOf(255)));
/*  72 */     gL = register(new Setting("Outline Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/*  73 */     aL = register(new Setting("Outline Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/*  74 */     outlinerainbow = register(new Setting("Outline Rainbow", Boolean.valueOf(true)));
/*  75 */     rF = register(new Setting("Fill Red", Integer.valueOf(30), Integer.valueOf(0), Integer.valueOf(255)));
/*  76 */     bF = register(new Setting("Fill Green", Integer.valueOf(167), Integer.valueOf(0), Integer.valueOf(255)));
/*  77 */     gF = register(new Setting("Fill Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/*  78 */     aF = register(new Setting("Fill Alpha", Integer.valueOf(140), Integer.valueOf(0), Integer.valueOf(255)));
/*  79 */     fillrainbow = register(new Setting("Fill Rainbow", Boolean.valueOf(true)));
/*  80 */     fadestart = register(new Setting("Fade Start", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/*  81 */     fadetime = register(new Setting("Fade Time", Float.valueOf(0.5F), Float.valueOf(0.0F), Float.valueOf(2.0F)));
/*  82 */     onlyOneEsp = register(new Setting("Only Render One", Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   public static void renderEntity(EntityLivingBase entity, ModelBase modelBase, float limbSwing, float limbSwingAmount, int scale) {
/*  86 */     float partialTicks = mc.getRenderPartialTicks();
/*  87 */     double x = entity.posX - (mc.getRenderManager()).viewerPosX;
/*  88 */     double y = entity.posY - (mc.getRenderManager()).viewerPosY;
/*  89 */     double z = entity.posZ - (mc.getRenderManager()).viewerPosZ;
/*  90 */     GlStateManager.pushMatrix();
/*  91 */     if (entity.isSneaking())
/*  92 */       y -= 0.125D; 
/*  93 */     renderLivingAt(x, y, z);
/*  94 */     float f8 = handleRotationFloat();
/*  95 */     prepareRotations(entity);
/*  96 */     float f9 = prepareScale(entity, scale);
/*  97 */     GlStateManager.enableAlpha();
/*  98 */     modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
/*  99 */     modelBase.setRotationAngles(limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9, (Entity)entity);
/* 100 */     modelBase.render((Entity)entity, limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9);
/* 101 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public static void renderLivingAt(double x, double y, double z) {
/* 105 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*     */   }
/*     */   
/*     */   public static float prepareScale(EntityLivingBase entity, float scale) {
/* 109 */     GlStateManager.enableRescaleNormal();
/* 110 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 111 */     double widthX = (entity.getRenderBoundingBox()).maxX - (entity.getRenderBoundingBox()).minX;
/* 112 */     double widthZ = (entity.getRenderBoundingBox()).maxZ - (entity.getRenderBoundingBox()).minZ;
/* 113 */     GlStateManager.scale(scale + widthX, (scale * entity.height), scale + widthZ);
/* 114 */     GlStateManager.translate(0.0F, -1.501F, 0.0F);
/* 115 */     return 0.0625F;
/*     */   }
/*     */   
/*     */   public static void prepareRotations(EntityLivingBase entityLivingBase) {
/* 119 */     GlStateManager.rotate(180.0F - entityLivingBase.rotationYaw, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public static Color newAlpha(Color color, int alpha) {
/* 123 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
/*     */   }
/*     */   
/*     */   public static void glColor(Color color) {
/* 127 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*     */   }
/*     */   
/*     */   public static float handleRotationFloat() {
/* 131 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static void prepareGL() {
/* 135 */     GL11.glBlendFunc(770, 771);
/* 136 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 137 */     GlStateManager.glLineWidth(1.5F);
/* 138 */     GlStateManager.disableTexture2D();
/* 139 */     GlStateManager.depthMask(false);
/* 140 */     GlStateManager.enableBlend();
/* 141 */     GlStateManager.disableDepth();
/* 142 */     GlStateManager.disableLighting();
/* 143 */     GlStateManager.disableCull();
/* 144 */     GlStateManager.enableAlpha();
/* 145 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void releaseGL() {
/* 149 */     GlStateManager.enableCull();
/* 150 */     GlStateManager.depthMask(true);
/* 151 */     GlStateManager.enableTexture2D();
/* 152 */     GlStateManager.enableBlend();
/* 153 */     GlStateManager.enableDepth();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdate(PacketEvent.Receive event) {
/*     */     SPacketEntityStatus packet;
/* 159 */     if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getOpCode() == 35 && packet.getEntity((World)mc.world) != null && (((Boolean)self.getValue()).booleanValue() || packet.getEntity((World)mc.world).getEntityId() != mc.player.getEntityId())) {
/* 160 */       GameProfile profile = new GameProfile(mc.player.getUniqueID(), "");
/* 161 */       this.player = new EntityOtherPlayerMP((World)mc.world, profile);
/* 162 */       this.player.copyLocationAndAnglesFrom(packet.getEntity((World)mc.world));
/* 163 */       this.playerModel = new ModelPlayer(0.0F, false);
/* 164 */       this.startTime = Long.valueOf(System.currentTimeMillis());
/* 165 */       this.playerModel.bipedHead.showModel = false;
/* 166 */       this.playerModel.bipedBody.showModel = false;
/* 167 */       this.playerModel.bipedLeftArmwear.showModel = false;
/* 168 */       this.playerModel.bipedLeftLegwear.showModel = false;
/* 169 */       this.playerModel.bipedRightArmwear.showModel = false;
/* 170 */       this.playerModel.bipedRightLegwear.showModel = false;
/* 171 */       this.alphaFill = ((Integer)aF.getValue()).intValue();
/* 172 */       this.alphaLine = ((Integer)aL.getValue()).intValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderWorld(RenderWorldLastEvent event) {
/* 178 */     if (((Boolean)onlyOneEsp.getValue()).booleanValue()) {
/* 179 */       if (this.player == null || mc.world == null || mc.player == null)
/*     */         return; 
/* 181 */       if (((Boolean)elevator.getValue()).booleanValue())
/* 182 */         if (elevatorMode.getValue() == ElevatorMode.UP) {
/* 183 */           this.player.posY += (0.05F * event.getPartialTicks());
/* 184 */         } else if (elevatorMode.getValue() == ElevatorMode.DOWN) {
/* 185 */           this.player.posY -= (0.05F * event.getPartialTicks());
/*     */         }  
/* 187 */       GL11.glLineWidth(1.0F);
/* 188 */       Color lineColorS = ((Boolean)outlinerainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()) : new Color(((Integer)rL.getValue()).intValue(), ((Integer)bL.getValue()).intValue(), ((Integer)gL.getValue()).intValue(), ((Integer)aL.getValue()).intValue());
/* 189 */       Color fillColorS = ((Boolean)fillrainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()) : new Color(((Integer)rF.getValue()).intValue(), ((Integer)bF.getValue()).intValue(), ((Integer)gF.getValue()).intValue(), ((Integer)aF.getValue()).intValue());
/* 190 */       int lineA = lineColorS.getAlpha();
/* 191 */       int fillA = fillColorS.getAlpha();
/*     */       
/* 193 */       long time = System.currentTimeMillis() - this.startTime.longValue() - ((Integer)fadestart.getValue()).longValue();
/* 194 */       if (System.currentTimeMillis() - this.startTime.longValue() > ((Integer)fadestart.getValue()).longValue()) {
/* 195 */         double normal = normalize(time, ((Float)fadetime.getValue()).doubleValue());
/* 196 */         normal = MathHelper.clamp(normal, 0.0D, 1.0D);
/* 197 */         normal = -normal + 1.0D;
/* 198 */         lineA *= (int)normal;
/* 199 */         fillA *= (int)normal;
/*     */       } 
/* 201 */       Color lineColor = newAlpha(lineColorS, lineA);
/* 202 */       Color fillColor = newAlpha(fillColorS, fillA);
/* 203 */       if (this.player != null && this.playerModel != null) {
/* 204 */         prepareGL();
/* 205 */         GL11.glPushAttrib(1048575);
/* 206 */         GL11.glEnable(2881);
/* 207 */         GL11.glEnable(2848);
/* 208 */         if (this.alphaFill > 1.0D)
/* 209 */           this.alphaFill -= ((Float)fadetime.getValue()).floatValue(); 
/* 210 */         Color fillFinal = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int)this.alphaFill);
/* 211 */         if (this.alphaLine > 1.0D)
/* 212 */           this.alphaLine -= ((Float)fadetime.getValue()).floatValue(); 
/* 213 */         Color outlineFinal = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int)this.alphaLine);
/* 214 */         glColor(fillFinal);
/* 215 */         GL11.glPolygonMode(1032, 6914);
/* 216 */         renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, 1);
/* 217 */         glColor(outlineFinal);
/* 218 */         GL11.glPolygonMode(1032, 6913);
/* 219 */         renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, 1);
/* 220 */         GL11.glPolygonMode(1032, 6914);
/* 221 */         GL11.glPopAttrib();
/* 222 */         releaseGL();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   double normalize(double value, double max) {
/* 228 */     return (value - 0.0D) / (max - 0.0D);
/*     */   }
/*     */   
/*     */   public enum ElevatorMode {
/* 232 */     UP, DOWN;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\PopChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
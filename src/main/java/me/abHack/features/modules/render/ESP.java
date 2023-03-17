/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.event.events.Render2DEvent;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.event.events.RenderCrystalEvent;
/*     */ import me.abHack.event.events.RenderLivingEntityEvent;
/*     */ import me.abHack.event.events.RenderTileEntityEvent;
/*     */ import me.abHack.event.events.ShaderColorEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.client.Colors;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.mixin.mixins.IEntityRenderer;
/*     */ import me.abHack.mixin.mixins.IRenderGlobal;
/*     */ import me.abHack.mixin.mixins.IShaderGroup;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.Esp.shaders.DotShader;
/*     */ import me.abHack.util.render.Esp.shaders.FillShader;
/*     */ import me.abHack.util.render.Esp.shaders.OutlineShader;
/*     */ import me.abHack.util.render.Esp.shaders.RainbowOutlineShader;
/*     */ import me.abHack.util.render.RenderBuilder;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.client.shader.Shader;
/*     */ import net.minecraft.client.shader.ShaderGroup;
/*     */ import net.minecraft.client.shader.ShaderUniform;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ESP
/*     */   extends Module
/*     */ {
/*     */   public static ESP INSTANCE;
/*  61 */   private final OutlineShader outlineShader = new OutlineShader();
/*  62 */   private final RainbowOutlineShader rainbowOutlineShader = new RainbowOutlineShader();
/*  63 */   private final DotShader dotShader = new DotShader();
/*  64 */   private final FillShader fillShader = new FillShader();
/*     */   
/*  66 */   private final List<Vec3d> chorusTeleports = new ArrayList<>();
/*  67 */   private final ArrayList<EntityItem> itemsname = new ArrayList<>();
/*  68 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.SHADER));
/*     */   
/*  70 */   public Setting<FragmentShader> shader = register(new Setting("Shader", FragmentShader.OUTLINE, v -> ((Mode)this.mode.getValue()).equals(Mode.SHADER)));
/*  71 */   public Setting<Double> width = register(new Setting("Width", Double.valueOf(1.25D), Double.valueOf(1.0D), Double.valueOf(5.0D)));
/*  72 */   public Setting<Boolean> players = register(new Setting("Players", Boolean.valueOf(true)));
/*  73 */   public Setting<Boolean> passives = register(new Setting("Passives", Boolean.valueOf(true)));
/*  74 */   public Setting<Boolean> neutrals = register(new Setting("Neutrals", Boolean.valueOf(true)));
/*     */   
/*  76 */   public Setting<Boolean> hostiles = register(new Setting("Hostiles", Boolean.valueOf(true)));
/*  77 */   public Setting<Boolean> items = register(new Setting("Items", Boolean.valueOf(true)));
/*  78 */   public Setting<Boolean> name = register(new Setting("ItemsName", Boolean.valueOf(true), v -> ((Boolean)this.items.getValue()).booleanValue()));
/*  79 */   public Setting<Boolean> crystals = register(new Setting("Crystals", Boolean.valueOf(true)));
/*  80 */   public Setting<Boolean> vehicles = register(new Setting("Vehicles", Boolean.valueOf(true)));
/*  81 */   public Setting<Boolean> chests = register(new Setting("Chests", Boolean.valueOf(true)));
/*  82 */   public Setting<Boolean> enderChests = register(new Setting("EnderChests", Boolean.valueOf(true)));
/*  83 */   public Setting<Boolean> shulkers = register(new Setting("Shulkers", Boolean.valueOf(true)));
/*  84 */   public Setting<Boolean> hoppers = register(new Setting("Hoppers", Boolean.valueOf(true)));
/*  85 */   public Setting<Boolean> furnaces = register(new Setting("Furnaces", Boolean.valueOf(true)));
/*  86 */   public Setting<Boolean> chorus = register(new Setting("Chorus", Boolean.valueOf(false)));
/*     */   
/*     */   private Framebuffer framebuffer;
/*     */   private int lastScaleFactor;
/*     */   private int lastScaleWidth;
/*     */   private int lastScaleHeight;
/*     */   
/*     */   public ESP() {
/*  94 */     super("ESP", "Allows you to see entities through walls", Module.Category.RENDER, true, false, false);
/*  95 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 100 */     if (fullNullCheck())
/*     */       return; 
/* 102 */     if (((Mode)this.mode.getValue()).equals(Mode.GLOW)) {
/*     */ 
/*     */       
/* 105 */       mc.world.loadedEntityList.forEach(entity -> {
/*     */             if (entity != null && !entity.equals(mc.player) && hasHighlight(entity)) {
/*     */               entity.setGlowing(true);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 112 */       ShaderGroup outlineShaderGroup = ((IRenderGlobal)mc.renderGlobal).getEntityOutlineShader();
/* 113 */       List<Shader> shaders = ((IShaderGroup)outlineShaderGroup).getListShaders();
/*     */ 
/*     */       
/* 116 */       shaders.forEach(shader -> {
/*     */             ShaderUniform outlineRadius = shader.getShaderManager().getShaderUniform("Radius");
/*     */             
/*     */             if (outlineRadius != null) {
/*     */               outlineRadius.set(((Double)this.width.getValue()).floatValue());
/*     */             }
/*     */           });
/*     */     } else {
/* 124 */       mc.world.loadedEntityList.forEach(entity -> {
/*     */             if (entity != null && entity.isGlowing()) {
/*     */               entity.setGlowing(false);
/*     */             }
/*     */           });
/*     */     } 
/* 130 */     if (((Boolean)this.items.getValue()).booleanValue() && ((Boolean)this.name.getValue()).booleanValue()) {
/* 131 */       this.itemsname.clear();
/* 132 */       for (Entity entity : mc.world.loadedEntityList) {
/* 133 */         if (entity instanceof EntityItem) {
/* 134 */           this.itemsname.add((EntityItem)entity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 142 */     if (((Mode)this.mode.getValue()).equals(Mode.GLOW)) {
/* 143 */       mc.world.loadedEntityList.forEach(entity -> {
/*     */             if (entity != null && entity.isGlowing()) {
/*     */               entity.setGlowing(false);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 151 */     this.chorusTeleports.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacket(PacketEvent.Receive event) {
/* 159 */     if (event.getPacket() instanceof SPacketSoundEffect) {
/* 160 */       SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
/*     */ 
/*     */ 
/*     */       
/* 164 */       if (packet.getSound().equals(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) || packet.getSound().equals(SoundEvents.ENTITY_ENDERMEN_TELEPORT))
/*     */       {
/*     */         
/* 167 */         this.chorusTeleports.add(new Vec3d(packet.getX(), packet.getY(), packet.getZ()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 175 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     if (((Boolean)this.chorus.getValue()).booleanValue() && !this.chorusTeleports.isEmpty())
/*     */     {
/*     */       
/* 182 */       this.chorusTeleports.forEach(pos -> RenderUtil.drawBox((new RenderBuilder()).position(new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y + 2.0D, pos.z)).box(RenderBuilder.Box.BOTH).width(((Double)this.width.getValue()).doubleValue()).color(ColorUtil.getPrimaryAlphaColor(80)).blend().depth(true).texture()));
/*     */     }
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
/* 196 */     if (((Boolean)this.items.getValue()).booleanValue() && ((Boolean)this.name.getValue()).booleanValue()) {
/*     */       
/* 198 */       GL11.glBlendFunc(770, 771);
/* 199 */       GL11.glEnable(2848);
/* 200 */       GL11.glLineWidth(2.0F);
/* 201 */       RenderUtil.prepareGL3D();
/* 202 */       GL11.glPushMatrix();
/* 203 */       GL11.glTranslated(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);
/*     */ 
/*     */ 
/*     */       
/* 207 */       double partialTicks = event.getPartialTicks();
/* 208 */       renderBoxes(partialTicks);
/* 209 */       GL11.glPopMatrix();
/*     */       
/* 211 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 212 */       GL11.glEnable(2929);
/* 213 */       GL11.glEnable(3553);
/* 214 */       GL11.glDisable(3042);
/* 215 */       GL11.glDisable(2848);
/* 216 */       RenderUtil.releaseGL3D();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderBoxes(double partialTicks) {
/* 222 */     for (EntityItem e : this.itemsname) {
/* 223 */       GL11.glPushMatrix();
/* 224 */       GL11.glTranslated(e.prevPosX + (e.posX - e.prevPosX) * partialTicks, e.prevPosY + (e.posY - e.prevPosY) * partialTicks, e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks);
/*     */ 
/*     */       
/* 227 */       ItemStack stack = e.getItem();
/* 228 */       if ((mc.getRenderManager()).options == null)
/*     */         return; 
/* 230 */       EntityRenderer.drawNameplate(mc.fontRenderer, stack
/* 231 */           .getCount() + "x " + stack.getDisplayName(), 0.0F, 1.0F, 0.0F, 0, 
/* 232 */           (mc.getRenderManager()).playerViewY, 
/* 233 */           (mc.getRenderManager()).playerViewX, 
/* 234 */           ((mc.getRenderManager()).options.thirdPersonView == 2), false);
/* 235 */       GL11.glDisable(2896);
/* 236 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {
/* 243 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*     */     
/* 247 */     if (((Mode)this.mode.getValue()).equals(Mode.SHADER)) {
/* 248 */       GlStateManager.enableAlpha();
/* 249 */       GlStateManager.pushMatrix();
/*     */ 
/*     */ 
/*     */       
/* 253 */       if (this.framebuffer != null) {
/* 254 */         this.framebuffer.framebufferClear();
/*     */ 
/*     */         
/* 257 */         ScaledResolution scaledResolution1 = new ScaledResolution(mc);
/*     */         
/* 259 */         if (this.lastScaleFactor != scaledResolution1.getScaleFactor() || this.lastScaleWidth != scaledResolution1.getScaledWidth() || this.lastScaleHeight != scaledResolution1.getScaledHeight()) {
/* 260 */           this.framebuffer.deleteFramebuffer();
/*     */ 
/*     */           
/* 263 */           this.framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
/* 264 */           this.framebuffer.framebufferClear();
/*     */         } 
/*     */ 
/*     */         
/* 268 */         this.lastScaleFactor = scaledResolution1.getScaleFactor();
/* 269 */         this.lastScaleWidth = scaledResolution1.getScaledWidth();
/* 270 */         this.lastScaleHeight = scaledResolution1.getScaledHeight();
/*     */       } else {
/*     */         
/* 273 */         this.framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
/*     */       } 
/*     */ 
/*     */       
/* 277 */       this.framebuffer.bindFramebuffer(false);
/*     */ 
/*     */       
/* 280 */       boolean previousShadows = mc.gameSettings.entityShadows;
/* 281 */       mc.gameSettings.entityShadows = false;
/*     */ 
/*     */       
/* 284 */       ((IEntityRenderer)mc.entityRenderer).setupCamera(mc.getRenderPartialTicks(), 0);
/*     */ 
/*     */       
/* 287 */       mc.world.loadedEntityList.forEach(entity -> {
/*     */             if (entity != null && entity != mc.player && !entity.isDead && !entity.getName().equals(mc.player.getName()) && hasHighlight(entity)) {
/*     */               mc.getRenderManager().renderEntityStatic(entity, mc.getRenderPartialTicks(), true);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 294 */       mc.world.loadedTileEntityList.forEach(tileEntity -> {
/*     */             if (tileEntity != null && hasStorageHighlight(tileEntity)) {
/*     */               double renderX = (mc.getRenderManager()).renderPosX;
/*     */ 
/*     */               
/*     */               double renderY = (mc.getRenderManager()).renderPosY;
/*     */ 
/*     */               
/*     */               double renderZ = (mc.getRenderManager()).renderPosZ;
/*     */               
/*     */               TileEntityRendererDispatcher.instance.render(tileEntity, tileEntity.getPos().getX() - renderX, tileEntity.getPos().getY() - renderY, tileEntity.getPos().getZ() - renderZ, mc.getRenderPartialTicks());
/*     */             } 
/*     */           });
/*     */       
/* 308 */       mc.gameSettings.entityShadows = previousShadows;
/*     */       
/* 310 */       GlStateManager.enableBlend();
/* 311 */       GL11.glBlendFunc(770, 771);
/*     */ 
/*     */       
/* 314 */       this.framebuffer.unbindFramebuffer();
/* 315 */       mc.getFramebuffer().bindFramebuffer(true);
/*     */ 
/*     */       
/* 318 */       mc.entityRenderer.disableLightmap();
/* 319 */       RenderHelper.disableStandardItemLighting();
/*     */       
/* 321 */       GlStateManager.pushMatrix();
/*     */ 
/*     */       
/* 324 */       if (!((Colors.Rainbow)Colors.INSTANCE.rainbow.getValue()).equals(Colors.Rainbow.NONE)) {
/* 325 */         switch ((FragmentShader)this.shader.getValue()) {
/*     */           case DOTTED:
/* 327 */             this.dotShader.startShader(((Double)this.width.getValue()).intValue(), ColorUtil.getPrimaryColor());
/*     */             break;
/*     */           case OUTLINE:
/* 330 */             this.rainbowOutlineShader.startShader(((Double)this.width.getValue()).intValue(), ColorUtil.getPrimaryColor());
/*     */             break;
/*     */           case OUTLINE_FILL:
/* 333 */             this.fillShader.startShader(((Double)this.width.getValue()).intValue(), ColorUtil.getPrimaryColor());
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       } else {
/* 340 */         switch ((FragmentShader)this.shader.getValue()) {
/*     */           case DOTTED:
/* 342 */             this.dotShader.startShader(((Double)this.width.getValue()).intValue(), ColorUtil.getPrimaryColor());
/*     */             break;
/*     */           case OUTLINE:
/* 345 */             this.outlineShader.startShader(((Double)this.width.getValue()).intValue(), ColorUtil.getPrimaryColor());
/*     */             break;
/*     */           case OUTLINE_FILL:
/* 348 */             this.fillShader.startShader(((Double)this.width.getValue()).intValue(), ColorUtil.getPrimaryColor());
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 354 */       mc.entityRenderer.setupOverlayRendering();
/*     */       
/* 356 */       ScaledResolution scaledResolution = new ScaledResolution(mc);
/*     */ 
/*     */       
/* 359 */       GL11.glBindTexture(3553, this.framebuffer.framebufferTexture);
/* 360 */       GL11.glBegin(7);
/* 361 */       GL11.glTexCoord2d(0.0D, 1.0D);
/* 362 */       GL11.glVertex2d(0.0D, 0.0D);
/* 363 */       GL11.glTexCoord2d(0.0D, 0.0D);
/* 364 */       GL11.glVertex2d(0.0D, scaledResolution.getScaledHeight());
/* 365 */       GL11.glTexCoord2d(1.0D, 0.0D);
/* 366 */       GL11.glVertex2d(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
/* 367 */       GL11.glTexCoord2d(1.0D, 1.0D);
/* 368 */       GL11.glVertex2d(scaledResolution.getScaledWidth(), 0.0D);
/* 369 */       GL11.glEnd();
/*     */ 
/*     */       
/* 372 */       GL20.glUseProgram(0);
/* 373 */       GL11.glPopMatrix();
/*     */ 
/*     */       
/* 376 */       mc.entityRenderer.enableLightmap();
/*     */       
/* 378 */       GlStateManager.popMatrix();
/*     */ 
/*     */ 
/*     */       
/* 382 */       mc.entityRenderer.setupOverlayRendering();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderEntity(RenderLivingEntityEvent event) {
/* 389 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 392 */     if (((Mode)this.mode.getValue()).equals(Mode.OUTLINE) && 
/* 393 */       hasHighlight((Entity)event.getEntityLivingBase())) {
/*     */ 
/*     */       
/* 396 */       if ((mc.getFramebuffer()).depthBuffer > -1) {
/*     */ 
/*     */         
/* 399 */         EXTFramebufferObject.glDeleteRenderbuffersEXT((mc.getFramebuffer()).depthBuffer);
/*     */ 
/*     */         
/* 402 */         int stencilFrameBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
/*     */ 
/*     */         
/* 405 */         EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilFrameBufferID);
/*     */ 
/*     */         
/* 408 */         EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/*     */ 
/*     */         
/* 411 */         EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilFrameBufferID);
/* 412 */         EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilFrameBufferID);
/*     */ 
/*     */         
/* 415 */         (mc.getFramebuffer()).depthBuffer = -1;
/*     */       } 
/*     */ 
/*     */       
/* 419 */       GL11.glPushAttrib(1048575);
/* 420 */       GL11.glDisable(3008);
/* 421 */       GL11.glDisable(3553);
/* 422 */       GL11.glDisable(2896);
/* 423 */       GL11.glEnable(3042);
/* 424 */       GL11.glBlendFunc(770, 771);
/* 425 */       GL11.glLineWidth(((Double)this.width.getValue()).floatValue());
/* 426 */       GL11.glEnable(2848);
/* 427 */       GL11.glEnable(2960);
/* 428 */       GL11.glClear(1024);
/* 429 */       GL11.glClearStencil(15);
/* 430 */       GL11.glStencilFunc(512, 1, 15);
/* 431 */       GL11.glStencilOp(7681, 7681, 7681);
/* 432 */       GL11.glPolygonMode(1032, 6913);
/*     */ 
/*     */       
/* 435 */       event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
/*     */ 
/*     */       
/* 438 */       GL11.glStencilFunc(512, 0, 15);
/* 439 */       GL11.glStencilOp(7681, 7681, 7681);
/* 440 */       GL11.glPolygonMode(1032, 6914);
/*     */ 
/*     */       
/* 443 */       event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
/*     */ 
/*     */       
/* 446 */       GL11.glStencilFunc(514, 1, 15);
/* 447 */       GL11.glStencilOp(7680, 7680, 7680);
/* 448 */       GL11.glPolygonMode(1032, 6913);
/*     */ 
/*     */       
/* 451 */       GL11.glColor4d((getColor((Entity)event.getEntityLivingBase()).getRed() / 255.0F), (getColor((Entity)event.getEntityLivingBase()).getGreen() / 255.0F), (getColor((Entity)event.getEntityLivingBase()).getBlue() / 255.0F), (getColor((Entity)event.getEntityLivingBase()).getAlpha() / 255.0F));
/* 452 */       GL11.glDepthMask(false);
/* 453 */       GL11.glDisable(2929);
/* 454 */       GL11.glEnable(10754);
/* 455 */       GL11.glPolygonOffset(3.0F, -2000000.0F);
/* 456 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/*     */ 
/*     */       
/* 459 */       event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
/*     */ 
/*     */       
/* 462 */       GL11.glPolygonOffset(-3.0F, 2000000.0F);
/* 463 */       GL11.glDisable(10754);
/* 464 */       GL11.glEnable(2929);
/* 465 */       GL11.glDepthMask(true);
/* 466 */       GL11.glDisable(2960);
/* 467 */       GL11.glDisable(2848);
/* 468 */       GL11.glHint(3154, 4352);
/* 469 */       GL11.glEnable(3042);
/* 470 */       GL11.glEnable(2896);
/* 471 */       GL11.glEnable(3553);
/* 472 */       GL11.glEnable(3008);
/* 473 */       GL11.glPopAttrib();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderCrystal(RenderCrystalEvent.RenderCrystalPostEvent event) {
/* 481 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 484 */     if (((Mode)this.mode.getValue()).equals(Mode.OUTLINE) && (
/* 485 */       (Boolean)this.crystals.getValue()).booleanValue()) {
/*     */ 
/*     */       
/* 488 */       float rotation = (event.getEntityEnderCrystal()).innerRotation + event.getPartialTicks();
/* 489 */       float rotationMoved = MathHelper.sin(rotation * 0.2F) / 2.0F + 0.5F;
/* 490 */       rotationMoved = (float)(rotationMoved + StrictMath.pow(rotationMoved, 2.0D));
/*     */       
/* 492 */       GL11.glPushMatrix();
/*     */ 
/*     */       
/* 495 */       GL11.glTranslated(event.getX(), event.getY(), event.getZ());
/* 496 */       GL11.glLineWidth(1.0F + ((Double)this.width.getValue()).floatValue());
/*     */ 
/*     */       
/* 499 */       if (event.getEntityEnderCrystal().shouldShowBottom()) {
/* 500 */         event.getModelBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } else {
/* 502 */         event.getModelNoBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } 
/*     */ 
/*     */       
/* 506 */       if ((mc.getFramebuffer()).depthBuffer > -1) {
/*     */ 
/*     */         
/* 509 */         EXTFramebufferObject.glDeleteRenderbuffersEXT((mc.getFramebuffer()).depthBuffer);
/*     */ 
/*     */         
/* 512 */         int stencilFrameBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
/*     */ 
/*     */         
/* 515 */         EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilFrameBufferID);
/*     */ 
/*     */         
/* 518 */         EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/*     */ 
/*     */         
/* 521 */         EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilFrameBufferID);
/* 522 */         EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilFrameBufferID);
/*     */ 
/*     */         
/* 525 */         (mc.getFramebuffer()).depthBuffer = -1;
/*     */       } 
/*     */ 
/*     */       
/* 529 */       GL11.glPushAttrib(1048575);
/* 530 */       GL11.glDisable(3008);
/* 531 */       GL11.glDisable(3553);
/* 532 */       GL11.glDisable(2896);
/* 533 */       GL11.glEnable(3042);
/* 534 */       GL11.glBlendFunc(770, 771);
/* 535 */       GL11.glLineWidth(1.0F + ((Double)this.width.getValue()).floatValue());
/* 536 */       GL11.glEnable(2848);
/* 537 */       GL11.glEnable(2960);
/* 538 */       GL11.glClear(1024);
/* 539 */       GL11.glClearStencil(15);
/* 540 */       GL11.glStencilFunc(512, 1, 15);
/* 541 */       GL11.glStencilOp(7681, 7681, 7681);
/* 542 */       GL11.glPolygonMode(1032, 6913);
/*     */ 
/*     */       
/* 545 */       if (event.getEntityEnderCrystal().shouldShowBottom()) {
/* 546 */         event.getModelBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } else {
/* 548 */         event.getModelNoBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } 
/*     */ 
/*     */       
/* 552 */       GL11.glStencilFunc(512, 0, 15);
/* 553 */       GL11.glStencilOp(7681, 7681, 7681);
/* 554 */       GL11.glPolygonMode(1032, 6914);
/*     */ 
/*     */       
/* 557 */       if (event.getEntityEnderCrystal().shouldShowBottom()) {
/* 558 */         event.getModelBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } else {
/* 560 */         event.getModelNoBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } 
/*     */ 
/*     */       
/* 564 */       GL11.glStencilFunc(514, 1, 15);
/* 565 */       GL11.glStencilOp(7680, 7680, 7680);
/* 566 */       GL11.glPolygonMode(1032, 6913);
/*     */ 
/*     */       
/* 569 */       GL11.glDepthMask(false);
/* 570 */       GL11.glDisable(2929);
/* 571 */       GL11.glEnable(10754);
/* 572 */       GL11.glPolygonOffset(3.0F, -2000000.0F);
/* 573 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/* 574 */       GL11.glColor4d((ColorUtil.getPrimaryColor().getRed() / 255.0F), (ColorUtil.getPrimaryColor().getGreen() / 255.0F), (ColorUtil.getPrimaryColor().getBlue() / 255.0F), (ColorUtil.getPrimaryColor().getAlpha() / 255.0F));
/*     */ 
/*     */       
/* 577 */       if (event.getEntityEnderCrystal().shouldShowBottom()) {
/* 578 */         event.getModelBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } else {
/* 580 */         event.getModelNoBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } 
/*     */ 
/*     */       
/* 584 */       GL11.glPolygonOffset(-3.0F, 2000000.0F);
/* 585 */       GL11.glDisable(10754);
/* 586 */       GL11.glEnable(2929);
/* 587 */       GL11.glDepthMask(true);
/* 588 */       GL11.glDisable(2960);
/* 589 */       GL11.glDisable(2848);
/* 590 */       GL11.glHint(3154, 4352);
/* 591 */       GL11.glEnable(3042);
/* 592 */       GL11.glEnable(2896);
/* 593 */       GL11.glEnable(3553);
/* 594 */       GL11.glEnable(3008);
/* 595 */       GL11.glPopAttrib();
/* 596 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderTileEntity(RenderTileEntityEvent event) {
/* 605 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 608 */     if (((Mode)this.mode.getValue()).equals(Mode.OUTLINE) && 
/* 609 */       hasStorageHighlight(event.getTileEntity())) {
/*     */ 
/*     */       
/* 612 */       boolean hotbarRender = (event.getX() == 0.0D && event.getY() == 0.0D && event.getZ() == 0.0D);
/*     */ 
/*     */       
/* 615 */       if (!hotbarRender && 
/* 616 */         TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()) != null) {
/*     */ 
/*     */         
/* 619 */         event.setCanceled(true);
/*     */         
/* 621 */         GL11.glPushMatrix();
/*     */ 
/*     */         
/* 624 */         if (event.getTileEntity().hasFastRenderer()) {
/* 625 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).renderTileEntityFast(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial(), event.getBuffer().getBuffer());
/*     */         } else {
/* 627 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).render(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial());
/*     */         } 
/*     */ 
/*     */         
/* 631 */         if ((mc.getFramebuffer()).depthBuffer > -1) {
/*     */ 
/*     */           
/* 634 */           EXTFramebufferObject.glDeleteRenderbuffersEXT((mc.getFramebuffer()).depthBuffer);
/*     */ 
/*     */           
/* 637 */           int stencilFrameBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
/*     */ 
/*     */           
/* 640 */           EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilFrameBufferID);
/*     */ 
/*     */           
/* 643 */           EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
/*     */ 
/*     */           
/* 646 */           EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilFrameBufferID);
/* 647 */           EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilFrameBufferID);
/*     */ 
/*     */           
/* 650 */           (mc.getFramebuffer()).depthBuffer = -1;
/*     */         } 
/*     */ 
/*     */         
/* 654 */         GL11.glPushAttrib(1048575);
/* 655 */         GL11.glDisable(3008);
/* 656 */         GL11.glDisable(3553);
/* 657 */         GL11.glDisable(2896);
/* 658 */         GL11.glEnable(3042);
/* 659 */         GL11.glBlendFunc(770, 771);
/* 660 */         GL11.glLineWidth(1.0F + ((Double)this.width.getValue()).floatValue());
/* 661 */         GL11.glEnable(2848);
/* 662 */         GL11.glEnable(2960);
/* 663 */         GL11.glClear(1024);
/* 664 */         GL11.glClearStencil(15);
/* 665 */         GL11.glStencilFunc(512, 1, 15);
/* 666 */         GL11.glStencilOp(7681, 7681, 7681);
/* 667 */         GL11.glPolygonMode(1032, 6913);
/*     */ 
/*     */         
/* 670 */         if (event.getTileEntity().hasFastRenderer()) {
/* 671 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).renderTileEntityFast(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial(), event.getBuffer().getBuffer());
/*     */         } else {
/* 673 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).render(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial());
/*     */         } 
/*     */ 
/*     */         
/* 677 */         GL11.glStencilFunc(512, 0, 15);
/* 678 */         GL11.glStencilOp(7681, 7681, 7681);
/* 679 */         GL11.glPolygonMode(1032, 6914);
/*     */ 
/*     */         
/* 682 */         if (event.getTileEntity().hasFastRenderer()) {
/* 683 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).renderTileEntityFast(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial(), event.getBuffer().getBuffer());
/*     */         } else {
/* 685 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).render(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial());
/*     */         } 
/*     */ 
/*     */         
/* 689 */         GL11.glStencilFunc(514, 1, 15);
/* 690 */         GL11.glStencilOp(7680, 7680, 7680);
/* 691 */         GL11.glPolygonMode(1032, 6913);
/*     */         
/* 693 */         GL11.glDepthMask(false);
/* 694 */         GL11.glDisable(2929);
/* 695 */         GL11.glEnable(10754);
/* 696 */         GL11.glPolygonOffset(3.0F, -2000000.0F);
/* 697 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/*     */ 
/*     */         
/* 700 */         GL11.glColor4d((ColorUtil.getPrimaryColor().getRed() / 255.0F), (ColorUtil.getPrimaryColor().getGreen() / 255.0F), (ColorUtil.getPrimaryColor().getBlue() / 255.0F), (ColorUtil.getPrimaryColor().getAlpha() / 255.0F));
/*     */ 
/*     */         
/* 703 */         if (event.getTileEntity().hasFastRenderer()) {
/* 704 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).renderTileEntityFast(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial(), event.getBuffer().getBuffer());
/*     */         } else {
/* 706 */           TileEntityRendererDispatcher.instance.getRenderer(event.getTileEntity()).render(event.getTileEntity(), event.getX(), event.getY(), event.getZ(), event.getPartialTicks(), event.getDestroyStage(), event.getPartial());
/*     */         } 
/*     */ 
/*     */         
/* 710 */         GL11.glPolygonOffset(-3.0F, 2000000.0F);
/* 711 */         GL11.glDisable(10754);
/* 712 */         GL11.glEnable(2929);
/* 713 */         GL11.glDepthMask(true);
/* 714 */         GL11.glDisable(2960);
/* 715 */         GL11.glDisable(2848);
/* 716 */         GL11.glHint(3154, 4352);
/* 717 */         GL11.glEnable(3042);
/* 718 */         GL11.glEnable(2896);
/* 719 */         GL11.glEnable(3553);
/* 720 */         GL11.glEnable(3008);
/* 721 */         GL11.glPopAttrib();
/* 722 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onShaderColor(ShaderColorEvent event) {
/* 732 */     if (((Mode)this.mode.getValue()).equals(Mode.GLOW)) {
/*     */ 
/*     */       
/* 735 */       event.setColor(getColor(event.getEntity()));
/*     */ 
/*     */       
/* 738 */       event.setCanceled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor(Entity in) {
/* 749 */     return OyVey.friendManager.isFriend(in.getName()) ? Color.CYAN : ColorUtil.getPrimaryColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasHighlight(Entity entity) {
/* 759 */     return ((((Boolean)this.players.getValue()).booleanValue() && entity instanceof net.minecraft.entity.player.EntityPlayer) || (((Boolean)this.passives.getValue()).booleanValue() && EntityUtil.isPassive(entity)) || (((Boolean)this.neutrals.getValue()).booleanValue() && EntityUtil.isNeutralMob(entity)) || (((Boolean)this.hostiles.getValue()).booleanValue() && EntityUtil.isHostileMob(entity)) || (((Boolean)this.vehicles.getValue()).booleanValue() && EntityUtil.isVehicle(entity)) || (((Boolean)this.items.getValue()).booleanValue() && (entity instanceof EntityItem || entity instanceof net.minecraft.entity.item.EntityExpBottle || entity instanceof net.minecraft.entity.item.EntityXPOrb)) || (((Boolean)this.crystals.getValue()).booleanValue() && entity instanceof net.minecraft.entity.item.EntityEnderCrystal));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasStorageHighlight(TileEntity tileEntity) {
/* 769 */     return ((((Boolean)this.chests.getValue()).booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityChest) || (((Boolean)this.enderChests.getValue()).booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest) || (((Boolean)this.shulkers.getValue()).booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox) || (((Boolean)this.hoppers.getValue()).booleanValue() && (tileEntity instanceof net.minecraft.tileentity.TileEntityHopper || tileEntity instanceof net.minecraft.tileentity.TileEntityDropper)) || (((Boolean)this.furnaces.getValue()).booleanValue() && tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Mode
/*     */   {
/* 777 */     GLOW,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 782 */     SHADER,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 787 */     OUTLINE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum FragmentShader
/*     */   {
/* 795 */     OUTLINE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 800 */     DOTTED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 805 */     OUTLINE_FILL;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\ESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
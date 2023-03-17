/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.RenderCrystalEvent;
/*     */ import me.abHack.event.events.RenderLivingEntityEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Chams
/*     */   extends Module
/*     */ {
/*     */   public static Chams INSTANCE;
/*  27 */   private final ResourceLocation GLINT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*     */ 
/*     */   
/*  30 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.Normal));
/*     */ 
/*     */ 
/*     */   
/*  34 */   public Setting<Boolean> players = register(new Setting("Players", Boolean.valueOf(true), v -> (this.mode.getValue() != Mode.Normal)));
/*     */   
/*  36 */   public Setting<Boolean> local = register(new Setting("Local", Boolean.valueOf(false), v -> (((Boolean)this.players.getValue()).booleanValue() && this.mode.getValue() != Mode.Normal)));
/*     */   
/*  38 */   public Setting<Boolean> mobs = register(new Setting("Mobs", Boolean.valueOf(true), v -> (this.mode.getValue() != Mode.Normal)));
/*     */   
/*  40 */   public Setting<Boolean> monsters = register(new Setting("Monsters", Boolean.valueOf(true), v -> (this.mode.getValue() != Mode.Normal)));
/*     */   
/*  42 */   public Setting<Boolean> crystals = register(new Setting("Crystals", Boolean.valueOf(true), v -> (this.mode.getValue() != Mode.Normal)));
/*     */   
/*  44 */   public Setting<Double> scale = register(new Setting("CrystalScale", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(2.0D), v -> (((Boolean)this.crystals.getValue()).booleanValue() && this.mode.getValue() != Mode.Normal)));
/*     */ 
/*     */ 
/*     */   
/*  48 */   public Setting<Double> width = register(new Setting("Width", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(5.0D), v -> (((Mode)this.mode.getValue()).equals(Mode.WIRE) || ((Mode)this.mode.getValue()).equals(Mode.WIRE_MODEL))));
/*     */   
/*  50 */   public Setting<Boolean> texture = register(new Setting("Texture", Boolean.valueOf(false), v -> (this.mode.getValue() != Mode.Normal)));
/*     */   
/*  52 */   public Setting<Boolean> transparent = register(new Setting("Transparent", Boolean.valueOf(true), v -> ((Boolean)this.texture.getValue()).booleanValue()));
/*     */   
/*  54 */   public Setting<Boolean> shine = register(new Setting("Shine", Boolean.valueOf(false), v -> (!((Mode)this.mode.getValue()).equals(Mode.WIRE) && this.mode.getValue() != Mode.Normal)));
/*     */   
/*  56 */   public Setting<Boolean> lighting = register(new Setting("Lighting", Boolean.valueOf(true), v -> (this.mode.getValue() != Mode.Normal)));
/*     */   
/*  58 */   public Setting<Boolean> walls = register(new Setting("Walls", Boolean.valueOf(true), v -> (this.mode.getValue() != Mode.Normal)));
/*     */ 
/*     */   
/*     */   public Chams() {
/*  62 */     super("Chams", "Renders entity models through walls", Module.Category.RENDER, true, false, false);
/*  63 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderLivingEntityPre(RenderLivingEntityEvent.RenderLivingEntityPreEvent event) {
/*  69 */     if (this.mode.getValue() == Mode.Normal)
/*     */       return; 
/*  71 */     if (hasChams(event.getEntityLivingBase())) {
/*     */ 
/*     */       
/*  74 */       if (((Boolean)this.transparent.getValue()).booleanValue()) {
/*  75 */         GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
/*     */       }
/*     */ 
/*     */       
/*  79 */       if (!((Boolean)this.texture.getValue()).booleanValue()) {
/*  80 */         event.setCanceled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderLivingEntityPost(RenderLivingEntityEvent.RenderLivingEntityPostEvent event) {
/*  88 */     if (this.mode.getValue() == Mode.Normal)
/*     */       return; 
/*  90 */     if (hasChams(event.getEntityLivingBase())) {
/*     */       
/*  92 */       GL11.glPushMatrix();
/*  93 */       GL11.glPushAttrib(1048575);
/*     */ 
/*     */       
/*  96 */       GL11.glDisable(3553);
/*  97 */       GL11.glEnable(3042);
/*     */ 
/*     */       
/* 100 */       if (((Boolean)this.lighting.getValue()).booleanValue()) {
/* 101 */         GL11.glDisable(2896);
/*     */       }
/*     */ 
/*     */       
/* 105 */       if (((Boolean)this.walls.getValue()).booleanValue()) {
/* 106 */         GL11.glDisable(2929);
/*     */       }
/*     */ 
/*     */       
/* 110 */       switch ((Mode)this.mode.getValue()) {
/*     */         case WIRE:
/* 112 */           GL11.glPolygonMode(1032, 6913);
/*     */           break;
/*     */         case WIRE_MODEL:
/*     */         case MODEL:
/* 116 */           GL11.glPolygonMode(1032, 6914);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 121 */       GL11.glEnable(2848);
/* 122 */       GL11.glHint(3154, 4354);
/* 123 */       GL11.glLineWidth(((Double)this.width.getValue()).floatValue());
/*     */ 
/*     */       
/* 126 */       GL11.glColor4d((getColor((Entity)event.getEntityLivingBase()).getRed() / 255.0F), (getColor((Entity)event.getEntityLivingBase()).getGreen() / 255.0F), (getColor((Entity)event.getEntityLivingBase()).getBlue() / 255.0F), ((Mode)this.mode.getValue()).equals(Mode.WIRE) ? 1.0D : 0.2D);
/*     */ 
/*     */       
/* 129 */       if (((Boolean)this.shine.getValue()).booleanValue() && !((Mode)this.mode.getValue()).equals(Mode.WIRE)) {
/* 130 */         GL11.glEnable(3553);
/* 131 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
/*     */ 
/*     */         
/* 134 */         for (float i = 0.0F; i < 2.0F; i++) {
/*     */ 
/*     */           
/* 137 */           (mc.getRenderManager()).renderEngine.bindTexture(this.GLINT_TEXTURE);
/*     */ 
/*     */           
/* 140 */           GlStateManager.matrixMode(5890);
/* 141 */           GlStateManager.loadIdentity();
/* 142 */           float textureScale = 0.33333334F;
/*     */ 
/*     */           
/* 145 */           GlStateManager.scale(textureScale, textureScale, textureScale);
/* 146 */           GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
/* 147 */           GlStateManager.translate(0.0F, ((event.getEntityLivingBase()).ticksExisted + mc.getRenderPartialTicks()) * (0.001F + i * 0.003F) * 4.0F, 0.0F);
/* 148 */           GlStateManager.matrixMode(5888);
/* 149 */           GL11.glTranslatef(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */           
/* 152 */           event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
/*     */ 
/*     */           
/* 155 */           GlStateManager.matrixMode(5890);
/* 156 */           GlStateManager.loadIdentity();
/* 157 */           GlStateManager.matrixMode(5888);
/*     */         } 
/*     */         
/* 160 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 161 */         GL11.glDisable(3553);
/*     */       }
/*     */       else {
/*     */         
/* 165 */         event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
/*     */       } 
/*     */ 
/*     */       
/* 169 */       if (((Boolean)this.walls.getValue()).booleanValue() && !((Mode)this.mode.getValue()).equals(Mode.WIRE_MODEL)) {
/* 170 */         GL11.glEnable(2929);
/*     */       }
/*     */ 
/*     */       
/* 174 */       if (((Mode)this.mode.getValue()).equals(Mode.WIRE_MODEL)) {
/* 175 */         GL11.glPolygonMode(1032, 6913);
/*     */ 
/*     */         
/* 178 */         GL11.glColor4d((getColor((Entity)event.getEntityLivingBase()).getRed() / 255.0F), (getColor((Entity)event.getEntityLivingBase()).getGreen() / 255.0F), (getColor((Entity)event.getEntityLivingBase()).getBlue() / 255.0F), (((Mode)this.mode.getValue()).equals(Mode.WIRE) || ((Mode)this.mode.getValue()).equals(Mode.WIRE_MODEL)) ? 1.0D : 0.2D);
/*     */ 
/*     */         
/* 181 */         event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
/*     */ 
/*     */         
/* 184 */         if (((Boolean)this.walls.getValue()).booleanValue()) {
/* 185 */           GL11.glEnable(2929);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 190 */       if (((Boolean)this.lighting.getValue()).booleanValue()) {
/* 191 */         GL11.glEnable(2896);
/*     */       }
/*     */ 
/*     */       
/* 195 */       GL11.glDisable(3042);
/* 196 */       GL11.glEnable(3553);
/*     */       
/* 198 */       GL11.glPopAttrib();
/* 199 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderCrystalPre(RenderCrystalEvent.RenderCrystalPreEvent event) {
/* 206 */     if (this.mode.getValue() == Mode.Normal) {
/*     */       return;
/*     */     }
/*     */     
/* 210 */     if (((Boolean)this.transparent.getValue()).booleanValue()) {
/* 211 */       GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
/*     */     }
/*     */ 
/*     */     
/* 215 */     if (!((Boolean)this.texture.getValue()).booleanValue()) {
/* 216 */       event.setCanceled(((Boolean)this.crystals.getValue()).booleanValue());
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderCrystalPost(RenderCrystalEvent.RenderCrystalPostEvent event) {
/* 222 */     if (this.mode.getValue() == Mode.Normal)
/*     */       return; 
/* 224 */     if (((Boolean)this.crystals.getValue()).booleanValue()) {
/*     */       
/* 226 */       GL11.glPushMatrix();
/* 227 */       GL11.glPushAttrib(1048575);
/*     */ 
/*     */       
/* 230 */       float rotation = (event.getEntityEnderCrystal()).innerRotation + event.getPartialTicks();
/* 231 */       float rotationMoved = MathHelper.sin(rotation * 0.2F) / 2.0F + 0.5F;
/* 232 */       rotationMoved = (float)(rotationMoved + StrictMath.pow(rotationMoved, 2.0D));
/*     */ 
/*     */       
/* 235 */       GL11.glTranslated(event.getX(), event.getY(), event.getZ());
/* 236 */       GL11.glScaled(((Double)this.scale.getValue()).doubleValue(), ((Double)this.scale.getValue()).doubleValue(), ((Double)this.scale.getValue()).doubleValue());
/*     */ 
/*     */       
/* 239 */       GL11.glDisable(3553);
/* 240 */       GL11.glEnable(3042);
/*     */ 
/*     */       
/* 243 */       if (((Boolean)this.lighting.getValue()).booleanValue()) {
/* 244 */         GL11.glDisable(2896);
/*     */       }
/*     */ 
/*     */       
/* 248 */       if (((Boolean)this.walls.getValue()).booleanValue()) {
/* 249 */         GL11.glDisable(2929);
/*     */       }
/*     */ 
/*     */       
/* 253 */       switch ((Mode)this.mode.getValue()) {
/*     */         case WIRE:
/* 255 */           GL11.glPolygonMode(1032, 6913);
/*     */           break;
/*     */         case WIRE_MODEL:
/*     */         case MODEL:
/* 259 */           GL11.glPolygonMode(1032, 6914);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 264 */       GL11.glEnable(2848);
/* 265 */       GL11.glHint(3154, 4354);
/* 266 */       GL11.glLineWidth(((Double)this.width.getValue()).floatValue());
/*     */ 
/*     */       
/* 269 */       GL11.glColor4d((getColor((Entity)event.getEntityEnderCrystal()).getRed() / 255.0F), (getColor((Entity)event.getEntityEnderCrystal()).getGreen() / 255.0F), (getColor((Entity)event.getEntityEnderCrystal()).getBlue() / 255.0F), ((Mode)this.mode.getValue()).equals(Mode.WIRE) ? 1.0D : 0.2D);
/*     */ 
/*     */       
/* 272 */       if (((Boolean)this.shine.getValue()).booleanValue() && !((Mode)this.mode.getValue()).equals(Mode.WIRE)) {
/* 273 */         GL11.glEnable(3553);
/* 274 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
/*     */ 
/*     */         
/* 277 */         for (int i = 0; i < 2; i++) {
/*     */ 
/*     */           
/* 280 */           (mc.getRenderManager()).renderEngine.bindTexture(this.GLINT_TEXTURE);
/*     */ 
/*     */           
/* 283 */           GlStateManager.matrixMode(5890);
/* 284 */           GlStateManager.loadIdentity();
/* 285 */           float textureScale = 0.33333334F;
/* 286 */           GlStateManager.scale(textureScale, textureScale, textureScale);
/* 287 */           GlStateManager.rotate((30 - i * 60), 0.0F, 0.0F, 1.0F);
/* 288 */           GlStateManager.translate(0.0F, ((event.getEntityEnderCrystal()).ticksExisted + mc.getRenderPartialTicks()) * (0.001F + i * 0.003F) * 4.0F, 0.0F);
/* 289 */           GlStateManager.matrixMode(5888);
/* 290 */           GL11.glTranslatef(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */           
/* 293 */           if (event.getEntityEnderCrystal().shouldShowBottom()) {
/* 294 */             event.getModelBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */           } else {
/* 296 */             event.getModelNoBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */           } 
/*     */ 
/*     */           
/* 300 */           GlStateManager.matrixMode(5890);
/* 301 */           GlStateManager.loadIdentity();
/* 302 */           GlStateManager.matrixMode(5888);
/*     */         } 
/*     */         
/* 305 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 306 */         GL11.glDisable(3553);
/*     */ 
/*     */       
/*     */       }
/* 310 */       else if (event.getEntityEnderCrystal().shouldShowBottom()) {
/* 311 */         event.getModelBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } else {
/* 313 */         event.getModelNoBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 318 */       if (((Boolean)this.walls.getValue()).booleanValue() && !((Mode)this.mode.getValue()).equals(Mode.WIRE_MODEL)) {
/* 319 */         GL11.glEnable(2929);
/*     */       }
/*     */ 
/*     */       
/* 323 */       if (((Mode)this.mode.getValue()).equals(Mode.WIRE_MODEL)) {
/* 324 */         GL11.glPolygonMode(1032, 6913);
/*     */ 
/*     */         
/* 327 */         GL11.glColor4d((getColor((Entity)event.getEntityEnderCrystal()).getRed() / 255.0F), (getColor((Entity)event.getEntityEnderCrystal()).getGreen() / 255.0F), (getColor((Entity)event.getEntityEnderCrystal()).getBlue() / 255.0F), (((Mode)this.mode.getValue()).equals(Mode.WIRE) || ((Mode)this.mode.getValue()).equals(Mode.WIRE_MODEL)) ? 1.0D : 0.2D);
/*     */ 
/*     */         
/* 330 */         if (event.getEntityEnderCrystal().shouldShowBottom()) {
/* 331 */           event.getModelBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */         } else {
/* 333 */           event.getModelNoBase().render((Entity)event.getEntityEnderCrystal(), 0.0F, rotation * 3.0F, rotationMoved * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */         } 
/*     */ 
/*     */         
/* 337 */         if (((Boolean)this.walls.getValue()).booleanValue()) {
/* 338 */           GL11.glEnable(2929);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 343 */       if (((Boolean)this.lighting.getValue()).booleanValue()) {
/* 344 */         GL11.glEnable(2896);
/*     */       }
/*     */ 
/*     */       
/* 348 */       GL11.glDisable(3042);
/* 349 */       GL11.glEnable(3553);
/*     */ 
/*     */       
/* 352 */       GL11.glScaled(1.0D / ((Double)this.scale.getValue()).doubleValue(), 1.0D / ((Double)this.scale.getValue()).doubleValue(), 1.0D / ((Double)this.scale.getValue()).doubleValue());
/*     */       
/* 354 */       GL11.glPopAttrib();
/* 355 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor(Entity in) {
/* 365 */     return OyVey.friendManager.isFriend(in.getName()) ? Color.CYAN : ColorUtil.getPrimaryColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChams(EntityLivingBase entity) {
/* 375 */     return ((entity instanceof net.minecraft.client.entity.EntityOtherPlayerMP && ((Boolean)this.players.getValue()).booleanValue()) || (entity instanceof net.minecraft.client.entity.EntityPlayerSP && ((Boolean)this.local.getValue()).booleanValue()) || ((EntityUtil.isPassive((Entity)entity) || EntityUtil.isNeutralMob((Entity)entity)) && ((Boolean)this.mobs.getValue()).booleanValue()) || (EntityUtil.isHostileMob((Entity)entity) && ((Boolean)this.monsters.getValue()).booleanValue()));
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 379 */     Normal,
/*     */ 
/*     */ 
/*     */     
/* 383 */     MODEL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     WIRE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 393 */     WIRE_MODEL;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Chams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
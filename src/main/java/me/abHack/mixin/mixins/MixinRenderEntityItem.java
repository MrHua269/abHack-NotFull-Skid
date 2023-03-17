/*     */ package me.abHack.mixin.mixins;
/*     */ 
/*     */ import java.util.Random;
/*     */ import me.abHack.features.modules.render.ItemPhysics;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.RenderEntityItem;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.client.ForgeHooksClient;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ @Mixin({RenderEntityItem.class})
/*     */ public abstract class MixinRenderEntityItem extends MixinRenderer<EntityItem> {
/*  32 */   private final Minecraft mc = Minecraft.getMinecraft();
/*     */   @Shadow
/*     */   @Final
/*     */   private RenderItem itemRenderer;
/*     */   @Shadow
/*     */   @Final
/*     */   private Random random;
/*     */   private long tick;
/*     */   
/*     */   @Shadow
/*     */   protected abstract int getModelCount(ItemStack paramItemStack);
/*     */   
/*     */   @Shadow
/*     */   public abstract boolean shouldSpreadItems();
/*     */   
/*     */   @Shadow
/*     */   protected abstract ResourceLocation getEntityTexture(EntityItem paramEntityItem);
/*     */   
/*     */   @Shadow
/*     */   protected abstract int transformModelCount(EntityItem paramEntityItem, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, IBakedModel paramIBakedModel);
/*     */   
/*     */   private double formPositive(float rotationPitch) {
/*  54 */     return (rotationPitch > 0.0F) ? rotationPitch : -rotationPitch;
/*     */   }
/*     */   
/*     */   @Inject(method = {"transformModelCount"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onInjectTransformModelCount(EntityItem itemIn, double x, double y, double z, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable<Integer> cir) {
/*  59 */     if (ItemPhysics.INSTANCE.isEnabled()) {
/*  60 */       ItemStack itemstack = itemIn.getItem();
/*     */       
/*  62 */       boolean flag = p_177077_9_.isAmbientOcclusion();
/*  63 */       int i = getModelCount(itemstack);
/*  64 */       float f2 = 0.0F;
/*  65 */       GlStateManager.translate((float)x, (float)y + 0.0F + 0.1F, (float)z);
/*  66 */       float f3 = 0.0F;
/*  67 */       if (flag || ((this.mc.getRenderManager()).options != null && (this.mc.getRenderManager()).options.fancyGraphics)) {
/*  68 */         GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
/*     */       }
/*  70 */       if (!flag) {
/*  71 */         f3 = -0.0F * (i - 1) * 0.5F;
/*  72 */         float f4 = -0.0F * (i - 1) * 0.5F;
/*  73 */         float f5 = -0.046875F * (i - 1) * 0.5F;
/*  74 */         GlStateManager.translate(f3, f4, f5);
/*     */       } 
/*  76 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  77 */       cir.setReturnValue(Integer.valueOf(i));
/*  78 */       cir.cancel();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overwrite
/*     */   public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  89 */     if (ItemPhysics.INSTANCE.isEnabled()) {
/*  90 */       double rotation = (System.nanoTime() - this.tick) / 3000000.0D;
/*  91 */       if (!this.mc.inGameHasFocus) {
/*  92 */         rotation = 0.0D;
/*     */       }
/*  94 */       ItemStack itemStack = entity.getItem();
/*  95 */       itemStack.getItem();
/*  96 */       this.random.setSeed(187L);
/*  97 */       (this.mc.getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  98 */       (this.mc.getRenderManager()).renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
/*  99 */       GlStateManager.enableRescaleNormal();
/* 100 */       GlStateManager.alphaFunc(516, 0.1F);
/* 101 */       GlStateManager.enableBlend();
/* 102 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 103 */       GlStateManager.pushMatrix();
/* 104 */       IBakedModel iBakedModel = this.itemRenderer.getItemModelMesher().getItemModel(itemStack);
/* 105 */       int m = transformModelCount(entity, x, y, z, partialTicks, iBakedModel);
/* 106 */       BlockPos blockpos = new BlockPos((Entity)entity);
/* 107 */       if (entity.rotationPitch > 360.0F) {
/* 108 */         entity.rotationPitch = 0.0F;
/*     */       }
/* 110 */       if (!Double.isNaN(entity.posX) && !Double.isNaN(entity.posY) && !Double.isNaN(entity.posZ) && entity.world != null) {
/* 111 */         if (entity.onGround) {
/* 112 */           if (entity.rotationPitch != 0.0F && entity.rotationPitch != 90.0F && entity.rotationPitch != 180.0F && entity.rotationPitch != 270.0F) {
/* 113 */             double d0 = formPositive(entity.rotationPitch);
/* 114 */             double d2 = formPositive(entity.rotationPitch - 90.0F);
/* 115 */             double d3 = formPositive(entity.rotationPitch - 180.0F);
/* 116 */             double d4 = formPositive(entity.rotationPitch - 270.0F);
/* 117 */             if (d0 <= d2 && d0 <= d3 && d0 <= d4) {
/* 118 */               if (entity.rotationPitch < 0.0F) {
/* 119 */                 entity.rotationPitch += (float)rotation;
/*     */               } else {
/* 121 */                 entity.rotationPitch -= (float)rotation;
/*     */               } 
/*     */             }
/* 124 */             if (d2 < d0 && d2 <= d3 && d2 <= d4) {
/* 125 */               if (entity.rotationPitch - 90.0F < 0.0F) {
/* 126 */                 entity.rotationPitch += (float)rotation;
/*     */               } else {
/* 128 */                 entity.rotationPitch -= (float)rotation;
/*     */               } 
/*     */             }
/* 131 */             if (d3 < d2 && d3 < d0 && d3 <= d4) {
/* 132 */               if (entity.rotationPitch - 180.0F < 0.0F) {
/* 133 */                 entity.rotationPitch += (float)rotation;
/*     */               } else {
/* 135 */                 entity.rotationPitch -= (float)rotation;
/*     */               } 
/*     */             }
/* 138 */             if (d4 < d2 && d4 < d3 && d4 < d0) {
/* 139 */               if (entity.rotationPitch - 270.0F < 0.0F) {
/* 140 */                 entity.rotationPitch += (float)rotation;
/*     */               } else {
/* 142 */                 entity.rotationPitch -= (float)rotation;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } else {
/* 147 */           BlockPos blockpos2 = new BlockPos((Entity)entity);
/* 148 */           blockpos2.add(0, 1, 0);
/* 149 */           Material material = entity.world.getBlockState(blockpos2).getMaterial();
/* 150 */           Material material2 = entity.world.getBlockState(blockpos).getMaterial();
/* 151 */           boolean flag2 = entity.isInsideOfMaterial(Material.WATER);
/* 152 */           boolean flag3 = entity.isInWater();
/* 153 */           if (!(flag2 | (material == Material.WATER) | (material2 == Material.WATER) | flag3)) {
/* 154 */             entity.rotationPitch += (float)(rotation / 4.0D);
/*     */           } else {
/* 156 */             entity.rotationPitch += (float)(rotation * 2.0D);
/*     */           } 
/*     */         } 
/*     */       }
/* 160 */       GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 161 */       GL11.glRotatef(entity.rotationPitch + 90.0F, 1.0F, 0.0F, 0.0F);
/* 162 */       for (int n = 0; n < m; n++) {
/* 163 */         if (iBakedModel.isAmbientOcclusion()) {
/* 164 */           GlStateManager.pushMatrix();
/* 165 */           GlStateManager.scale(((Float)ItemPhysics.INSTANCE.Scaling.getValue()).floatValue(), ((Float)ItemPhysics.INSTANCE.Scaling.getValue()).floatValue(), ((Float)ItemPhysics.INSTANCE.Scaling.getValue()).floatValue());
/* 166 */           this.itemRenderer.renderItem(itemStack, iBakedModel);
/* 167 */           GlStateManager.popMatrix();
/*     */         } else {
/* 169 */           GlStateManager.pushMatrix();
/* 170 */           if (n > 0 && shouldSpreadItems()) {
/* 171 */             GlStateManager.translate(0.0F, 0.0F, 0.046875F * n);
/*     */           }
/* 173 */           this.itemRenderer.renderItem(itemStack, iBakedModel);
/* 174 */           if (!shouldSpreadItems()) {
/* 175 */             GlStateManager.translate(0.0F, 0.0F, 0.046875F);
/*     */           }
/* 177 */           GlStateManager.popMatrix();
/*     */         } 
/*     */       } 
/* 180 */       GlStateManager.popMatrix();
/* 181 */       GlStateManager.disableRescaleNormal();
/* 182 */       GlStateManager.disableBlend();
/* 183 */       (this.mc.getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 184 */       (this.mc.getRenderManager()).renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 189 */     ItemStack itemstack = entity.getItem();
/* 190 */     int i = itemstack.isEmpty() ? 187 : (Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata());
/* 191 */     this.random.setSeed(i);
/* 192 */     boolean flag = false;
/*     */     
/* 194 */     if (bindEntityTexture(entity)) {
/* 195 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).setBlurMipmap(false, false);
/* 196 */       flag = true;
/*     */     } 
/*     */     
/* 199 */     GlStateManager.enableRescaleNormal();
/* 200 */     GlStateManager.alphaFunc(516, 0.1F);
/* 201 */     GlStateManager.enableBlend();
/* 202 */     RenderHelper.enableStandardItemLighting();
/* 203 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 204 */     GlStateManager.pushMatrix();
/* 205 */     IBakedModel ibakedmodel = this.itemRenderer.getItemModelWithOverrides(itemstack, entity.world, null);
/* 206 */     int j = transformModelCount(entity, x, y, z, partialTicks, ibakedmodel);
/* 207 */     boolean flag1 = ibakedmodel.isGui3d();
/*     */     
/* 209 */     if (!flag1) {
/* 210 */       float f3 = -0.0F * (j - 1) * 0.5F;
/* 211 */       float f4 = -0.0F * (j - 1) * 0.5F;
/* 212 */       float f5 = -0.09375F * (j - 1) * 0.5F;
/* 213 */       GlStateManager.translate(f3, f4, f5);
/*     */     } 
/*     */     
/* 216 */     if (this.renderOutlines) {
/* 217 */       GlStateManager.enableColorMaterial();
/* 218 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*     */     } 
/*     */     
/* 221 */     for (int k = 0; k < j; k++) {
/* 222 */       GlStateManager.pushMatrix();
/* 223 */       if (flag1) {
/*     */         
/* 225 */         if (k > 0) {
/* 226 */           float f7 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 227 */           float f9 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 228 */           float f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 229 */           GlStateManager.translate(shouldSpreadItems() ? f7 : 0.0F, shouldSpreadItems() ? f9 : 0.0F, f6);
/*     */         } 
/*     */         
/* 232 */         IBakedModel transformedModel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
/* 233 */         this.itemRenderer.renderItem(itemstack, transformedModel);
/* 234 */         GlStateManager.popMatrix();
/*     */       } else {
/*     */         
/* 237 */         if (k > 0) {
/* 238 */           float f8 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
/* 239 */           float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
/* 240 */           GlStateManager.translate(f8, f10, 0.0F);
/*     */         } 
/*     */         
/* 243 */         IBakedModel transformedModel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
/* 244 */         this.itemRenderer.renderItem(itemstack, transformedModel);
/* 245 */         GlStateManager.popMatrix();
/* 246 */         GlStateManager.translate(0.0F, 0.0F, 0.09375F);
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     if (this.renderOutlines) {
/* 251 */       GlStateManager.disableOutlineMode();
/* 252 */       GlStateManager.disableColorMaterial();
/*     */     } 
/*     */     
/* 255 */     GlStateManager.popMatrix();
/* 256 */     GlStateManager.disableRescaleNormal();
/* 257 */     GlStateManager.disableBlend();
/* 258 */     bindEntityTexture(entity);
/*     */     
/* 260 */     if (flag)
/* 261 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).restoreLastBlurMipmap();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinRenderEntityItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
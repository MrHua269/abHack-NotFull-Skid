/*     */ package me.abHack.mixin.mixins;
/*     */ 
/*     */ import me.abHack.event.events.FreecamEntityEvent;
/*     */ import me.abHack.event.events.FreecamEvent;
/*     */ import me.abHack.event.events.RenderItemEvent;
/*     */ import me.abHack.event.events.RenderItemOverlayEvent;
/*     */ import me.abHack.features.modules.render.ViewModel;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ @Mixin({ItemRenderer.class})
/*     */ public abstract class MixinItemRenderer
/*     */ {
/*     */   @Inject(method = {"transformSideFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo cancel) {
/*  31 */     RenderItemEvent event = new RenderItemEvent(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D);
/*  32 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  33 */     if (ViewModel.getInstance().isEnabled()) {
/*  34 */       boolean bob = (ViewModel.getInstance().isDisabled() || ((Boolean)(ViewModel.getInstance()).doBob.getValue()).booleanValue());
/*  35 */       int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
/*  36 */       GlStateManager.translate(i * 0.56F, -0.52F + (bob ? p_187459_2_ : 0.0F) * -0.6F, -0.72F);
/*  37 */       if (hand == EnumHandSide.RIGHT) {
/*  38 */         GlStateManager.translate(event.getMainX(), event.getMainY(), event.getMainZ());
/*  39 */         RenderUtil.rotationHelper((float)event.getMainRotX(), (float)event.getMainRotY(), (float)event.getMainRotZ());
/*     */       } else {
/*  41 */         GlStateManager.translate(event.getOffX(), event.getOffY(), event.getOffZ());
/*  42 */         RenderUtil.rotationHelper((float)event.getOffRotX(), (float)event.getOffRotY(), (float)event.getOffRotZ());
/*     */       } 
/*  44 */       cancel.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderItemInFirstPerson(F)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void renderItemInFirstPerson(float partialTicks, CallbackInfo ci) {
/*  50 */     RenderItemOverlayEvent event = new RenderItemOverlayEvent();
/*  51 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  52 */     if (event.isCanceled()) ci.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderOverlays"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void renderOverlaysInject(float partialTicks, CallbackInfo ci) {
/*  57 */     FreecamEvent event = new FreecamEvent();
/*  58 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  59 */     if (event.isCancelable()) ci.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"transformEatFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void transformEatFirstPerson(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo cancel) {
/*  64 */     if (ViewModel.getInstance().isEnabled()) {
/*  65 */       if (!((Boolean)(ViewModel.getInstance()).noEatAnimation.getValue()).booleanValue()) {
/*  66 */         float f = (Minecraft.getMinecraft()).player.getItemInUseCount() - p_187454_1_ + 1.0F;
/*  67 */         float f2 = f / stack.getMaxItemUseDuration();
/*  68 */         if (f2 < 0.8F) {
/*  69 */           float f1 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
/*  70 */           GlStateManager.translate(0.0F, f1, 0.0F);
/*     */         } 
/*  72 */         float f3 = 1.0F - (float)Math.pow(f2, 27.0D);
/*  73 */         int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
/*  74 */         GlStateManager.translate((f3 * 0.6F * i) * ((Double)(ViewModel.getInstance()).eatX.getValue()).doubleValue(), (f3 * 0.5F) * -((Double)(ViewModel.getInstance()).eatY.getValue()).doubleValue(), 0.0D);
/*  75 */         GlStateManager.rotate(i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
/*  76 */         GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
/*  77 */         GlStateManager.rotate(i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/*  79 */       cancel.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Redirect(method = {"setLightmap"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
/*     */   private EntityPlayerSP redirectLightmapPlayer(Minecraft mc) {
/*  85 */     FreecamEntityEvent event = new FreecamEntityEvent((Entity)mc.player);
/*  86 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  87 */     return (EntityPlayerSP)event.getEntity();
/*     */   }
/*     */   
/*     */   @Redirect(method = {"rotateArm"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
/*     */   private EntityPlayerSP rotateArmPlayer(Minecraft mc) {
/*  92 */     FreecamEntityEvent event = new FreecamEntityEvent((Entity)mc.player);
/*  93 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  94 */     return (EntityPlayerSP)event.getEntity();
/*     */   }
/*     */   
/*     */   @Redirect(method = {"renderItemInFirstPerson(F)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
/*     */   private EntityPlayerSP redirectPlayer(Minecraft mc) {
/*  99 */     FreecamEntityEvent event = new FreecamEntityEvent((Entity)mc.player);
/* 100 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 101 */     return (EntityPlayerSP)event.getEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   @Redirect(method = {"renderOverlays"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
/*     */   private EntityPlayerSP renderOverlaysPlayer(Minecraft mc) {
/* 107 */     FreecamEntityEvent event = new FreecamEntityEvent((Entity)mc.player);
/* 108 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 109 */     return (EntityPlayerSP)event.getEntity();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.RenderLivingEntityEvent;
/*    */ import me.abHack.features.modules.render.Chams;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({RenderLivingBase.class})
/*    */ public abstract class MixinRenderLivingBase<T extends EntityLivingBase>
/*    */   extends Render<T> {
/*    */   @Shadow
/*    */   protected ModelBase mainModel;
/*    */   
/*    */   public MixinRenderLivingBase(RenderManager renderManagerIn) {
/* 28 */     super(renderManagerIn);
/*    */   }
/*    */   
/*    */   @Redirect(method = {"renderModel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
/*    */   private void onRenderModelPreEntityLivingBase(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 33 */     RenderLivingEntityEvent.RenderLivingEntityPreEvent renderLivingEntityPreEvent = new RenderLivingEntityEvent.RenderLivingEntityPreEvent(this.mainModel, (EntityLivingBase)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 34 */     MinecraftForge.EVENT_BUS.post((Event)renderLivingEntityPreEvent);
/*    */     
/* 36 */     if (!renderLivingEntityPreEvent.isCanceled()) {
/* 37 */       modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderModel"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void onRenderModelPost(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo info) {
/* 43 */     RenderLivingEntityEvent.RenderLivingEntityPostEvent renderLivingEntityPostEvent = new RenderLivingEntityEvent.RenderLivingEntityPostEvent(this.mainModel, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/* 44 */     MinecraftForge.EVENT_BUS.post((Event)renderLivingEntityPostEvent);
/*    */     
/* 46 */     if (renderLivingEntityPostEvent.isCanceled()) {
/* 47 */       info.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"doRender*"}, at = {@At("HEAD")})
/*    */   public void doRenderPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
/* 53 */     if (Chams.INSTANCE.isEnabled() && Chams.INSTANCE.mode.getValue() == Chams.Mode.Normal && entity != null) {
/* 54 */       GL11.glEnable(32823);
/* 55 */       GL11.glPolygonOffset(1.0F, -1100000.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"doRender*"}, at = {@At("RETURN")})
/*    */   public void doRenderPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
/* 61 */     if (Chams.INSTANCE.isEnabled() && Chams.INSTANCE.mode.getValue() == Chams.Mode.Normal && entity != null) {
/* 62 */       GL11.glPolygonOffset(1.0F, 1000000.0F);
/* 63 */       GL11.glDisable(32823);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinRenderLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
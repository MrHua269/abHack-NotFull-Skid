/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.CrystalTextureEvent;
/*    */ import me.abHack.event.events.RenderCrystalEvent;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({RenderEnderCrystal.class})
/*    */ public abstract class MixinRenderEnderCrystal {
/*    */   @Final
/*    */   @Shadow
/*    */   private ModelBase modelEnderCrystal;
/*    */   @Final
/*    */   @Shadow
/*    */   private ModelBase modelEnderCrystalNoBase;
/*    */   
/*    */   @Redirect(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
/*    */   private void onDoRenderPre(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 30 */     RenderCrystalEvent.RenderCrystalPreEvent renderCrystalEvent = new RenderCrystalEvent.RenderCrystalPreEvent(modelBase, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 31 */     MinecraftForge.EVENT_BUS.post((Event)renderCrystalEvent);
/*    */     
/* 33 */     if (!renderCrystalEvent.isCanceled()) {
/* 34 */       modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */     }
/*    */     
/* 37 */     CrystalTextureEvent crystalTextureEvent = new CrystalTextureEvent();
/* 38 */     MinecraftForge.EVENT_BUS.post((Event)crystalTextureEvent);
/*    */   }
/*    */   
/*    */   @Inject(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = {@At("RETURN")}, cancellable = true)
/*    */   public void onDoRenderPost(EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
/* 43 */     RenderCrystalEvent.RenderCrystalPostEvent renderCrystalEvent = new RenderCrystalEvent.RenderCrystalPostEvent(this.modelEnderCrystal, this.modelEnderCrystalNoBase, entityEnderCrystal, x, y, z, entityYaw, partialTicks);
/* 44 */     MinecraftForge.EVENT_BUS.post((Event)renderCrystalEvent);
/*    */     
/* 46 */     if (renderCrystalEvent.isCanceled())
/* 47 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinRenderEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
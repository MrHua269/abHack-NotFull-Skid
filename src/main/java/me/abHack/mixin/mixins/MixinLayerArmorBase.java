/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.features.modules.render.NoRender;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({LayerArmorBase.class})
/*    */ public class MixinLayerArmorBase {
/*    */   @Inject(method = {"doRenderLayer"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
/* 15 */     if (NoRender.getInstance().isEnabled() && ((Boolean)(NoRender.getInstance()).armor.getValue()).booleanValue())
/* 16 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinLayerArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
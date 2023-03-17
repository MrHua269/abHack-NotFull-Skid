/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.render.Xray;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.BlockModelRenderer;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({BlockModelRenderer.class})
/*    */ public class MixinBlockModelRenderer
/*    */ {
/*    */   @Inject(method = {"renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onRenderModelPre(IBlockAccess p_187493_1_, IBakedModel p_187493_2_, IBlockState p_187493_3_, BlockPos p_187493_4_, BufferBuilder p_187493_5_, boolean p_187493_6_, long p_187493_7_, CallbackInfoReturnable<Boolean> cir) {
/* 21 */     if (OyVey.moduleManager.isModuleEnabled("Xray") && 
/* 22 */       !Xray.xrayBlocks.contains(p_187493_3_.getBlock())) {
/* 23 */       cir.setReturnValue(Boolean.valueOf(false));
/* 24 */       cir.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinBlockModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
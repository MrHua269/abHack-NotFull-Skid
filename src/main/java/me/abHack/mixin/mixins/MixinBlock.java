/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.render.Xray;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({Block.class})
/*    */ public class MixinBlock
/*    */ {
/*    */   @Inject(method = {"shouldSideBeRendered"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderBlockPatch(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
/* 20 */     if (OyVey.moduleManager.isModuleEnabled("Xray")) {
/* 21 */       if (Xray.xrayBlocks.contains(blockState.getBlock())) {
/* 22 */         callbackInfoReturnable.setReturnValue(Boolean.valueOf(true));
/*    */       } else {
/* 24 */         callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
/* 25 */         callbackInfoReturnable.cancel();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"isFullCube"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isFullCubePatch(IBlockState state, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
/* 32 */     if (OyVey.moduleManager.isModuleEnabled("Xray")) {
/* 33 */       callbackInfoReturnable.setReturnValue(Boolean.valueOf(Xray.xrayBlocks.contains(state.getBlock())));
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"getLightValue(Lnet/minecraft/block/state/IBlockState;)I"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onGetLightValuePre(IBlockState state, CallbackInfoReturnable<Integer> cir) {
/* 39 */     if (OyVey.moduleManager.isModuleEnabled("Xray") && Xray.xrayBlocks.contains(state.getBlock()))
/* 40 */       cir.setReturnValue(Integer.valueOf(15)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
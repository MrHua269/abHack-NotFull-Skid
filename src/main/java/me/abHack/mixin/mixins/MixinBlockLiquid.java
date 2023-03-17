/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.LiquidInteractEvent;
/*    */ import me.abHack.util.Util;
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({BlockLiquid.class})
/*    */ public class MixinBlockLiquid
/*    */   implements Util {
/*    */   @Inject(method = {"canCollideCheck"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void canCollideCheck(IBlockState blockState, boolean liquidLevel, CallbackInfoReturnable<Boolean> info) {
/* 19 */     LiquidInteractEvent liquidInteractEvent = new LiquidInteractEvent(blockState, liquidLevel);
/* 20 */     MinecraftForge.EVENT_BUS.post((Event)liquidInteractEvent);
/*    */     
/* 22 */     if (liquidInteractEvent.isCanceled()) {
/* 23 */       info.cancel();
/* 24 */       info.setReturnValue(Boolean.valueOf(true));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinBlockLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
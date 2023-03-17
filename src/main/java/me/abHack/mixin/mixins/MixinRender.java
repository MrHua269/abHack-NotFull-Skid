/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.ShaderColorEvent;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({Render.class})
/*    */ public class MixinRender<T extends Entity>
/*    */ {
/*    */   @Inject(method = {"getTeamColor"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getTeamColor(T entity, CallbackInfoReturnable<Integer> info) {
/* 18 */     ShaderColorEvent shaderColorEvent = new ShaderColorEvent((Entity)entity);
/* 19 */     MinecraftForge.EVENT_BUS.post((Event)shaderColorEvent);
/*    */     
/* 21 */     if (shaderColorEvent.isCanceled()) {
/* 22 */       info.cancel();
/* 23 */       info.setReturnValue(Integer.valueOf(shaderColorEvent.getColor().getRGB()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
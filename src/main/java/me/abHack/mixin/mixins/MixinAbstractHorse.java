/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.features.modules.movement.EntityControl;
/*    */ import net.minecraft.entity.passive.AbstractHorse;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({AbstractHorse.class})
/*    */ public class MixinAbstractHorse {
/*    */   @Inject(method = {"isHorseSaddled"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isHorseSaddled(CallbackInfoReturnable<Boolean> cir) {
/* 14 */     if (EntityControl.INSTANCE.isEnabled())
/* 15 */       cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinAbstractHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.PushEvent;
/*    */ import me.abHack.event.events.TurnEvent;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin(value = {Entity.class}, priority = 998)
/*    */ public abstract class MixinEntity
/*    */ {
/*    */   @Shadow
/*    */   public void move(MoverType type, double x, double y, double z) {}
/*    */   
/*    */   @Redirect(method = {"applyEntityCollision"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
/*    */   public void addVelocityHook(Entity entity, double x, double y, double z) {
/* 24 */     PushEvent event = new PushEvent(entity, x, y, z, true);
/* 25 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 26 */     if (!event.isCanceled()) {
/* 27 */       entity.motionX += event.x;
/* 28 */       entity.motionY += event.y;
/* 29 */       entity.motionZ += event.z;
/* 30 */       entity.isAirBorne = event.airbone;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"turn"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onTurn(float yaw, float pitch, CallbackInfo ci) {
/* 36 */     TurnEvent event = new TurnEvent(yaw, pitch);
/* 37 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 38 */     if (event.isCanceled())
/* 39 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.FreecamEvent;
/*    */ import me.abHack.event.events.MoveEvent;
/*    */ import me.abHack.event.events.PushEvent;
/*    */ import me.abHack.event.events.UpdateWalkingPlayerEvent;
/*    */ import me.abHack.features.modules.movement.Sprint;
/*    */ import me.abHack.util.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin(value = {EntityPlayerSP.class}, priority = 9998)
/*    */ public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer {
/*    */   @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("HEAD")})
/*    */   private void preMotion(CallbackInfo info) {
/* 26 */     UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(0);
/* 27 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*    */   }
/*    */   
/*    */   @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("RETURN")})
/*    */   private void postMotion(CallbackInfo info) {
/* 32 */     UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(1);
/* 33 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*    */   }
/*    */   
/*    */   @Inject(method = {"pushOutOfBlocks"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void pushOutOfBlocksHook(double x, double y, double z, CallbackInfoReturnable<Boolean> ci) {
/* 38 */     PushEvent event = new PushEvent(1);
/* 39 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 40 */     if (event.isCanceled())
/* 41 */       ci.setReturnValue(Boolean.FALSE); 
/*    */   }
/*    */   
/*    */   @Redirect(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setSprinting(Z)V", ordinal = 2))
/*    */   public void onLivingUpdate(EntityPlayerSP entityPlayerSP, boolean sprinting) {
/* 46 */     if (Sprint.getInstance().isOn() ? (((Sprint.getInstance()).mode.getValue() == Sprint.Mode.RAGE) ? (Util.mc.player.moveForward != 0.0F || Util.mc.player.moveStrafing != 0.0F) : (Util.mc.player.movementInput.moveStrafe != 0.0F)) : (Util.mc.player.movementInput.moveStrafe != 0.0F)) {
/* 47 */       entityPlayerSP.setSprinting(true);
/*    */     } else {
/* 49 */       entityPlayerSP.setSprinting(sprinting);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Redirect(method = {"move"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
/*    */   public void move(AbstractClientPlayer player, MoverType moverType, double x, double y, double z) {
/* 55 */     MoveEvent event = new MoveEvent(0, moverType, x, y, z);
/* 56 */     MinecraftForge.EVENT_BUS.post(event);
/* 57 */     if (!event.isCanceled()) {
/* 58 */       super.move(event.getType(), event.getX(), event.getY(), event.getZ());
/*    */     }
/*    */   }
/*    */   
/*    */   @Redirect(method = {"onUpdateWalkingPlayer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
/*    */   private boolean redirectIsCurrentViewEntity(EntityPlayerSP entityPlayerSP) {
/* 64 */     Minecraft mc = Minecraft.getMinecraft();
/* 65 */     FreecamEvent event = new FreecamEvent();
/* 66 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 67 */     if (event.isCancelable()) {
/* 68 */       return (entityPlayerSP == mc.player);
/*    */     }
/* 70 */     return (mc.getRenderViewEntity() == entityPlayerSP);
/*    */   }
/*    */   
/*    */   @Redirect(method = {"updateEntityActionState"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
/*    */   private boolean redirectIsCurrentViewEntity2(EntityPlayerSP entityPlayerSP) {
/* 75 */     Minecraft mc = Minecraft.getMinecraft();
/* 76 */     FreecamEvent event = new FreecamEvent();
/* 77 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 78 */     if (event.isCancelable()) {
/* 79 */       return (entityPlayerSP == mc.player);
/*    */     }
/* 81 */     return (mc.getRenderViewEntity() == entityPlayerSP);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinEntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
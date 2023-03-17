/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.FreecamEntityEvent;
/*    */ import me.abHack.event.events.PotionRenderHUDEvent;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.gui.GuiIngame;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiIngame.class})
/*    */ public class MixinGuiIngame {
/*    */   @Redirect(method = {"renderGameOverlay"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
/*    */   private EntityPlayerSP redirectOverlayPlayer(Minecraft mc) {
/* 22 */     FreecamEntityEvent event = new FreecamEntityEvent((Entity)mc.player);
/* 23 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 24 */     if (event.getEntity() instanceof EntityPlayerSP) {
/* 25 */       return (EntityPlayerSP)event.getEntity();
/*    */     }
/* 27 */     return mc.player;
/*    */   }
/*    */   
/*    */   @Redirect(method = {"renderPotionEffects"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
/*    */   private EntityPlayerSP redirectPotionPlayer(Minecraft mc) {
/* 32 */     FreecamEntityEvent event = new FreecamEntityEvent((Entity)mc.player);
/* 33 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 34 */     if (event.getEntity() instanceof EntityPlayerSP) {
/* 35 */       return (EntityPlayerSP)event.getEntity();
/*    */     }
/* 37 */     return mc.player;
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderPotionEffects"}, at = {@At("HEAD")}, cancellable = true)
/*    */   protected void renderPotionEffectsHook(ScaledResolution scaledRes, CallbackInfo info) {
/* 42 */     PotionRenderHUDEvent event = new PotionRenderHUDEvent();
/* 43 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 44 */     if (event.isCanceled()) info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinGuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
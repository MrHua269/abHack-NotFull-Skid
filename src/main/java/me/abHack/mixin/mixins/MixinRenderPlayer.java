/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.FreecamEvent;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({RenderPlayer.class})
/*    */ public class MixinRenderPlayer {
/*    */   @Inject(method = {"renderEntityName*"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderEntityNameHook(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
/* 20 */     if (OyVey.moduleManager.isModuleEnabled("NameTags"))
/* 21 */       info.cancel(); 
/*    */   }
/*    */   
/*    */   @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z"))
/*    */   private boolean isUserRedirect(AbstractClientPlayer abstractClientPlayer) {
/* 26 */     Minecraft mc = Minecraft.getMinecraft();
/* 27 */     FreecamEvent event = new FreecamEvent();
/* 28 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 29 */     if (event.isCancelable()) {
/* 30 */       return (abstractClientPlayer.isUser() && abstractClientPlayer == mc.getRenderViewEntity());
/*    */     }
/* 32 */     return abstractClientPlayer.isUser();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinRenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
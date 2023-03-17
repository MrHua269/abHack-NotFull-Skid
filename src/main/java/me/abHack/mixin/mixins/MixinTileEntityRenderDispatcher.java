/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.RenderTileEntityEvent;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({TileEntityRendererDispatcher.class})
/*    */ public class MixinTileEntityRenderDispatcher {
/*    */   @Shadow
/*    */   private Tessellator batchBuffer;
/*    */   
/*    */   @Inject(method = {"render(Lnet/minecraft/tileentity/TileEntity;DDDFIF)V"}, at = {@At("RETURN")}, cancellable = true)
/*    */   public void render(TileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage, float p_192854_10_, CallbackInfo info) {
/* 22 */     RenderTileEntityEvent renderTileEntityEvent = new RenderTileEntityEvent(tileEntityIn, x, y, z, partialTicks, destroyStage, p_192854_10_, this.batchBuffer);
/* 23 */     MinecraftForge.EVENT_BUS.post((Event)renderTileEntityEvent);
/*    */     
/* 25 */     if (renderTileEntityEvent.isCanceled())
/* 26 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinTileEntityRenderDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.mixin.mixins;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import me.abHack.event.events.FreecamEvent;
/*     */ import me.abHack.features.modules.misc.NoEntityTrace;
/*     */ import me.abHack.features.modules.render.CameraClip;
/*     */ import me.abHack.features.modules.render.NoRender;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ @Mixin({EntityRenderer.class})
/*     */ public class MixinEntityRenderer
/*     */ {
/*     */   @Shadow
/*     */   private ItemStack itemActivationItem;
/*     */   
/*     */   @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
/*     */   public List<Entity> getEntitiesInAABBexcludingHook(WorldClient worldClient, @Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate) {
/*  39 */     if (NoEntityTrace.getINSTANCE().isOn() && (NoEntityTrace.getINSTANCE()).noTrace) {
/*  40 */       return new ArrayList<>();
/*     */     }
/*  42 */     return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
/*     */   }
/*     */   
/*     */   @ModifyVariable(method = {"orientCamera"}, ordinal = 3, at = @At(value = "STORE", ordinal = 0), require = 1)
/*     */   public double changeCameraDistanceHook(double range) {
/*  47 */     if (CameraClip.INSTANCE.isEnabled()) {
/*  48 */       return ((Double)CameraClip.INSTANCE.distance.getValue()).doubleValue();
/*     */     }
/*  50 */     return range;
/*     */   }
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"orientCamera"}, ordinal = 7, at = @At(value = "STORE", ordinal = 0), require = 1)
/*     */   public double orientCameraHook(double range) {
/*  56 */     if (CameraClip.INSTANCE.isEnabled()) {
/*  57 */       return ((Double)CameraClip.INSTANCE.distance.getValue()).doubleValue();
/*     */     }
/*  59 */     return range;
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void hurtCameraEffectHook(float ticks, CallbackInfo info) {
/*  65 */     if (NoRender.getInstance().isEnabled() && ((Boolean)(NoRender.getInstance()).hurtCam.getValue()).booleanValue())
/*  66 */       info.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderItemActivation"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderItemActivationHook(CallbackInfo info) {
/*  71 */     if (this.itemActivationItem != null && NoRender.getInstance().isEnabled() && ((Boolean)(NoRender.getInstance()).totem.getValue()).booleanValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING)
/*  72 */       info.cancel(); 
/*     */   }
/*     */   
/*     */   @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
/*     */   private Entity redirectMouseOver(Minecraft mc) {
/*  77 */     FreecamEvent event = new FreecamEvent();
/*  78 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  79 */     if (event.isCancelable() && 
/*  80 */       Keyboard.isKeyDown(56)) {
/*  81 */       return (Entity)mc.player;
/*     */     }
/*     */     
/*  84 */     return mc.getRenderViewEntity();
/*     */   }
/*     */   
/*     */   @Redirect(method = {"updateCameraAndRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
/*     */   private void redirectTurn(EntityPlayerSP entityPlayerSP, float yaw, float pitch) {
/*     */     try {
/*  90 */       Minecraft mc = Minecraft.getMinecraft();
/*  91 */       FreecamEvent event = new FreecamEvent();
/*  92 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*  93 */       if (event.isCancelable()) {
/*  94 */         if (Keyboard.isKeyDown(56)) {
/*  95 */           mc.player.turn(yaw, pitch);
/*     */         } else {
/*  97 */           ((Entity)Objects.<Entity>requireNonNull(mc.getRenderViewEntity(), "Render Entity")).turn(yaw, pitch);
/*     */         } 
/*     */         return;
/*     */       } 
/* 101 */     } catch (Exception e) {
/* 102 */       e.printStackTrace();
/*     */       return;
/*     */     } 
/* 105 */     entityPlayerSP.turn(yaw, pitch);
/*     */   }
/*     */   
/*     */   @Redirect(method = {"renderWorldPass"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isSpectator()Z"))
/*     */   public boolean redirectIsSpectator(EntityPlayerSP entityPlayerSP) {
/* 110 */     FreecamEvent event = new FreecamEvent();
/* 111 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 112 */     if (event.isCancelable()) return true; 
/* 113 */     if (entityPlayerSP != null) {
/* 114 */       return entityPlayerSP.isSpectator();
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
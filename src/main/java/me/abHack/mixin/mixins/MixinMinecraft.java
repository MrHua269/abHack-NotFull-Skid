/*     */ package me.abHack.mixin.mixins;
/*     */ 
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.KeyEvent;
/*     */ import me.abHack.event.events.RootEvent;
/*     */ import me.abHack.features.modules.player.MultiTask;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({Minecraft.class})
/*     */ public abstract class MixinMinecraft
/*     */ {
/*     */   @Shadow
/*     */   public EntityPlayerSP player;
/*     */   @Shadow
/*     */   public PlayerControllerMP playerController;
/*     */   private boolean handActive = false;
/*     */   private boolean isHittingBlock = false;
/*     */   
/*     */   @Inject(method = {"shutdownMinecraftApplet"}, at = {@At("HEAD")})
/*     */   private void stopClient(CallbackInfo callbackInfo) {
/*  36 */     unload();
/*     */   }
/*     */   
/*     */   @Redirect(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
/*     */   public void displayCrashReport(Minecraft minecraft, CrashReport crashReport) {
/*  41 */     unload();
/*     */   }
/*     */   
/*     */   @Inject(method = {"runTickKeyboard"}, at = {@At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE)})
/*     */   private void onKeyboard(CallbackInfo callbackInfo) {
/*  46 */     int i = 0;
/*  47 */     if (Keyboard.getEventKeyState()) {
/*  48 */       KeyEvent event = new KeyEvent(i);
/*  49 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"runGameLoop"}, at = {@At("HEAD")})
/*     */   private void onRunGameLoop(CallbackInfo callbackInfo) {
/*  55 */     RootEvent event = new RootEvent();
/*  56 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */   }
/*     */   
/*     */   private void unload() {
/*  60 */     OyVey.LOGGER.info("Initiated client shutdown.");
/*  61 */     OyVey.onUnload();
/*  62 */     OyVey.LOGGER.info("Finished client shutdown.");
/*     */   }
/*     */   
/*     */   @Inject(method = {"rightClickMouse"}, at = {@At("HEAD")})
/*     */   public void rightClickMousePre(CallbackInfo ci) {
/*  67 */     if (MultiTask.getInstance().isOn()) {
/*  68 */       this.isHittingBlock = this.playerController.getIsHittingBlock();
/*  69 */       this.playerController.isHittingBlock = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overwrite
/*     */   public void shutdown() {
/*  80 */     (Minecraft.getMinecraft()).running = OyVey.eventManager.esc;
/*     */   }
/*     */   
/*     */   @Inject(method = {"rightClickMouse"}, at = {@At("RETURN")})
/*     */   public void rightClickMousePost(CallbackInfo ci) {
/*  85 */     if (MultiTask.getInstance().isOn() && !this.playerController.getIsHittingBlock()) {
/*  86 */       this.playerController.isHittingBlock = this.isHittingBlock;
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"sendClickBlockToController"}, at = {@At("HEAD")})
/*     */   public void sendClickBlockToControllerPre(boolean leftClick, CallbackInfo ci) {
/*  92 */     if (MultiTask.getInstance().isOn()) {
/*  93 */       this.handActive = this.player.isHandActive();
/*  94 */       ((AccessorEntityPlayerSP)this.player).gsSetHandActive(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"sendClickBlockToController"}, at = {@At("RETURN")})
/*     */   public void sendClickBlockToControllerPost(boolean leftClick, CallbackInfo ci) {
/* 100 */     if (MultiTask.getInstance().isOn() && !this.player.isHandActive())
/* 101 */       ((AccessorEntityPlayerSP)this.player).gsSetHandActive(this.handActive);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
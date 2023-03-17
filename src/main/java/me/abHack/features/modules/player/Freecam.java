/*     */ package me.abHack.features.modules.player;
/*     */ 
/*     */ import me.abHack.event.events.FreecamEntityEvent;
/*     */ import me.abHack.event.events.FreecamEvent;
/*     */ import me.abHack.event.events.RenderItemOverlayEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Bind;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.FreecamCamera;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.MovementInputFromOptions;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Freecam
/*     */   extends Module {
/*  19 */   public static Freecam INSTANCE = new Freecam();
/*     */   
/*  21 */   private final Setting<Boolean> follow = register(new Setting("Follow", Boolean.valueOf(false)));
/*  22 */   private final Setting<Boolean> copyInventory = register(new Setting("CopyInv", Boolean.valueOf(true)));
/*  23 */   private final Setting<Float> hSpeed = register(new Setting("HSpeed", Float.valueOf(1.0F), Float.valueOf(0.2F), Float.valueOf(2.0F)));
/*  24 */   private final Setting<Float> vSpeed = register(new Setting("VSpeed", Float.valueOf(1.0F), Float.valueOf(0.2F), Float.valueOf(2.0F)));
/*  25 */   public Setting<Bind> movePlayer = register(new Setting("Control", new Bind(0)));
/*  26 */   private final MovementInput cameraMovement = (MovementInput)new MovementInputFromOptions(mc.gameSettings)
/*     */     {
/*     */       public void updatePlayerMoveState() {
/*  29 */         if (!((Bind)Freecam.this.movePlayer.getValue()).isDown()) {
/*  30 */           super.updatePlayerMoveState();
/*     */         } else {
/*  32 */           this.moveStrafe = 0.0F;
/*  33 */           this.moveForward = 0.0F;
/*  34 */           this.forwardKeyDown = false;
/*  35 */           this.backKeyDown = false;
/*  36 */           this.leftKeyDown = false;
/*  37 */           this.rightKeyDown = false;
/*  38 */           this.jump = false;
/*  39 */           this.sneak = false;
/*     */         } 
/*     */       }
/*     */     };
/*  43 */   private final MovementInput playerMovement = (MovementInput)new MovementInputFromOptions(mc.gameSettings)
/*     */     {
/*     */       public void updatePlayerMoveState() {
/*  46 */         if (((Bind)Freecam.this.movePlayer.getValue()).isDown()) {
/*  47 */           super.updatePlayerMoveState();
/*     */         } else {
/*  49 */           this.moveStrafe = 0.0F;
/*  50 */           this.moveForward = 0.0F;
/*  51 */           this.forwardKeyDown = false;
/*  52 */           this.backKeyDown = false;
/*  53 */           this.leftKeyDown = false;
/*  54 */           this.rightKeyDown = false;
/*  55 */           this.jump = false;
/*  56 */           this.sneak = false;
/*     */         } 
/*     */       }
/*     */     };
/*  60 */   private Entity cachedActiveEntity = null;
/*  61 */   private int lastActiveTick = -1;
/*  62 */   private Entity oldRenderEntity = null;
/*  63 */   private FreecamCamera camera = null;
/*     */ 
/*     */   
/*     */   public Freecam() {
/*  67 */     super("Freecam", "Control your camera separately to your body", Module.Category.PLAYER, true, false, false);
/*  68 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public Entity getActiveEntity() {
/*  72 */     if (this.cachedActiveEntity == null) {
/*  73 */       this.cachedActiveEntity = (Entity)mc.player;
/*     */     }
/*     */     
/*  76 */     int currentTick = mc.player.ticksExisted;
/*  77 */     if (this.lastActiveTick != currentTick) {
/*  78 */       this.lastActiveTick = currentTick;
/*     */       
/*  80 */       if (isEnabled()) {
/*  81 */         if (((Bind)this.movePlayer.getValue()).isDown()) {
/*  82 */           this.cachedActiveEntity = (Entity)mc.player;
/*     */         } else {
/*  84 */           this.cachedActiveEntity = (mc.getRenderViewEntity() == null) ? (Entity)mc.player : mc.getRenderViewEntity();
/*     */         } 
/*     */       } else {
/*  87 */         this.cachedActiveEntity = (Entity)mc.player;
/*     */       } 
/*     */     } 
/*  90 */     return this.cachedActiveEntity;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldLoad(WorldEvent.Unload event) {
/*  95 */     mc.setRenderViewEntity((Entity)mc.player);
/*  96 */     toggle();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onFreecam(FreecamEvent event) {
/* 101 */     event.setCanceled(true);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onFreecamEntity(FreecamEntityEvent event) {
/* 106 */     if (getActiveEntity() != null) {
/* 107 */       event.setEntity(getActiveEntity());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 113 */     if (mc.player == null || mc.world == null)
/* 114 */       return;  this.camera.setCopyInventory(((Boolean)this.copyInventory.getValue()).booleanValue());
/* 115 */     this.camera.setFollow(((Boolean)this.follow.getValue()).booleanValue());
/* 116 */     this.camera.sethSpeed(((Float)this.hSpeed.getValue()).floatValue());
/* 117 */     this.camera.setvSpeed(((Float)this.vSpeed.getValue()).floatValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 122 */     if (mc.player == null)
/*     */       return; 
/* 124 */     this.camera = new FreecamCamera(((Boolean)this.copyInventory.getValue()).booleanValue(), ((Boolean)this.follow.getValue()).booleanValue(), ((Float)this.hSpeed.getValue()).floatValue(), ((Float)this.vSpeed.getValue()).floatValue());
/* 125 */     this.camera.movementInput = this.cameraMovement;
/* 126 */     mc.player.movementInput = this.playerMovement;
/* 127 */     mc.world.addEntityToWorld(-921, (Entity)this.camera);
/* 128 */     this.oldRenderEntity = mc.getRenderViewEntity();
/* 129 */     mc.setRenderViewEntity((Entity)this.camera);
/* 130 */     mc.renderChunksMany = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 135 */     if (mc.player == null)
/*     */       return; 
/* 137 */     if (this.camera != null) mc.world.removeEntity((Entity)this.camera); 
/* 138 */     this.camera = null;
/* 139 */     mc.player.movementInput = (MovementInput)new MovementInputFromOptions(mc.gameSettings);
/* 140 */     mc.setRenderViewEntity(this.oldRenderEntity);
/* 141 */     mc.renderChunksMany = true;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderOverlay(RenderItemOverlayEvent event) {
/* 146 */     event.setCanceled(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Freecam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
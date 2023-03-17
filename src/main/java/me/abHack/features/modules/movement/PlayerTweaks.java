/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.event.events.PushEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*    */ import net.minecraftforge.client.event.InputUpdateEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class PlayerTweaks extends Module {
/* 12 */   public static PlayerTweaks INSTANCE = new PlayerTweaks();
/*    */   public Setting<Boolean> noSlow;
/*    */   public Setting<Boolean> antiKnockBack;
/*    */   public Setting<Boolean> noEntityPush;
/*    */   public Setting<Boolean> noBlockPush;
/*    */   public Setting<Boolean> noWaterPush;
/*    */   public Setting<Boolean> guiMove;
/*    */   
/*    */   public PlayerTweaks() {
/* 21 */     super("PlayerTweaks", "tweaks", Module.Category.MOVEMENT, true, false, false);
/*    */ 
/*    */     
/* 24 */     this.noSlow = register(new Setting("No Slow", Boolean.valueOf(true)));
/* 25 */     this.antiKnockBack = register(new Setting("Velocity", Boolean.valueOf(true)));
/* 26 */     this.noEntityPush = register(new Setting("No PlayerPush", Boolean.valueOf(true)));
/* 27 */     this.noBlockPush = register(new Setting("No BlockPush", Boolean.valueOf(true)));
/* 28 */     this.noWaterPush = register(new Setting("No LiquidPush", Boolean.valueOf(true)));
/* 29 */     this.guiMove = register(new Setting("Gui Move", Boolean.valueOf(true)));
/* 30 */     setInstance();
/*    */   }
/*    */   
/*    */   public static PlayerTweaks getInstance() {
/* 34 */     if (INSTANCE == null) {
/* 35 */       INSTANCE = new PlayerTweaks();
/*    */     }
/* 37 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 41 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void Slow(InputUpdateEvent event) {
/* 46 */     if (((Boolean)this.noSlow.getValue()).booleanValue() && mc.player
/* 47 */       .isHandActive() && !mc.player.isRiding()) {
/* 48 */       (event.getMovementInput()).moveStrafe *= 5.0F;
/* 49 */       (event.getMovementInput()).moveForward *= 5.0F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceived(PacketEvent.Receive event) {
/* 56 */     if (fullNullCheck())
/*    */       return; 
/* 58 */     if (((Boolean)this.antiKnockBack.getValue()).booleanValue()) {
/* 59 */       if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event
/* 60 */         .getPacket()).getEntityID() == mc.player.getEntityId()) {
/* 61 */         event.setCanceled(true);
/*    */       }
/*    */       
/* 64 */       if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketExplosion) {
/* 65 */         event.setCanceled(true);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPush(PushEvent event) {
/* 72 */     if (fullNullCheck())
/*    */       return; 
/* 74 */     if (event.getStage() == 0 && ((Boolean)this.noEntityPush.getValue()).booleanValue() && event.entity.equals(mc.player)) {
/* 75 */       event.x = -event.x * 0.0D;
/* 76 */       event.y = -event.y * 0.0D;
/* 77 */       event.z = -event.z * 0.0D;
/* 78 */     } else if (event.getStage() == 1 && ((Boolean)this.noBlockPush.getValue()).booleanValue()) {
/* 79 */       event.setCanceled(true);
/* 80 */     } else if (event.getStage() == 2 && ((Boolean)this.noWaterPush.getValue()).booleanValue() && mc.player != null && mc.player.equals(event.entity)) {
/* 81 */       event.setCanceled(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\PlayerTweaks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
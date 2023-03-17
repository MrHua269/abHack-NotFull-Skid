/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.modules.combat.AutoPush;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoRotate
/*    */   extends Module
/*    */ {
/* 13 */   private final Timer timer = new Timer();
/*    */   private boolean cancelPackets = true;
/*    */   private boolean timerReset = false;
/*    */   
/*    */   public NoRotate() {
/* 18 */     super("NoRotate", "Dangerous to use might desync you.", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLogout() {
/* 23 */     this.cancelPackets = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLogin() {
/* 28 */     this.timer.reset();
/* 29 */     this.timerReset = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 34 */     if (this.timerReset && !this.cancelPackets) {
/* 35 */       this.cancelPackets = true;
/* 36 */       this.timerReset = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 42 */     if (event.getStage() == 0 && this.cancelPackets && event.getPacket() instanceof SPacketPlayerPosLook && mc.player != null && AutoPush.INSTANCE.isOff()) {
/* 43 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
/* 44 */       packet.yaw = mc.player.rotationYaw;
/* 45 */       packet.pitch = mc.player.rotationPitch;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\NoRotate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
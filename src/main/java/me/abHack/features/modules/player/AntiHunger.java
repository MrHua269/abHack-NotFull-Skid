/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AntiHunger extends Module {
/* 11 */   public Setting<Boolean> cancelSprint = register(new Setting("CancelSprint", Boolean.valueOf(true)));
/* 12 */   public Setting<Boolean> ground = register(new Setting("Ground", Boolean.valueOf(true)));
/*    */   
/*    */   public AntiHunger() {
/* 15 */     super("AntiHunger", "Prevents you from getting Hungry.", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 20 */     if (((Boolean)this.ground.getValue()).booleanValue() && event.getPacket() instanceof CPacketPlayer) {
/* 21 */       CPacketPlayer packet = (CPacketPlayer)event.getPacket();
/* 22 */       packet.onGround = (mc.player.fallDistance >= 0.0F || mc.playerController.isHittingBlock);
/*    */     } 
/* 24 */     if (((Boolean)this.cancelSprint.getValue()).booleanValue() && event.getPacket() instanceof CPacketEntityAction) {
/* 25 */       CPacketEntityAction packet = (CPacketEntityAction)event.getPacket();
/* 26 */       if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING || packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)
/* 27 */         event.setCanceled(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\AntiHunger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.combat;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketUseEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class Criticals
/*    */   extends Module {
/* 17 */   private final Setting<Integer> packets = register(new Setting("Packets", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(4)));
/* 18 */   private final Timer timer = new Timer();
/*    */   
/*    */   public Criticals() {
/* 21 */     super("Criticals", "Scores criticals for you", Module.Category.COMBAT, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/*    */     CPacketUseEntity packet;
/* 27 */     if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
/* 28 */       if (!this.timer.passedMs(0L)) {
/*    */         return;
/*    */       }
/* 31 */       if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && packet.getEntityFromWorld((World)mc.world) instanceof net.minecraft.entity.EntityLivingBase && !mc.player.isInWater() && !mc.player.isInLava()) {
/* 32 */         switch (((Integer)this.packets.getValue()).intValue()) {
/*    */           case 1:
/* 34 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612D, mc.player.posZ, false));
/* 35 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/*    */             break;
/*    */           
/*    */           case 2:
/* 39 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625101D, mc.player.posZ, false));
/* 40 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/* 41 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.1E-5D, mc.player.posZ, false));
/* 42 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/*    */             break;
/*    */           
/*    */           case 3:
/* 46 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625101D, mc.player.posZ, false));
/* 47 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/* 48 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0125D, mc.player.posZ, false));
/* 49 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/*    */             break;
/*    */           
/*    */           case 4:
/* 53 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1625D, mc.player.posZ, false));
/* 54 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/* 55 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 4.0E-6D, mc.player.posZ, false));
/* 56 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/* 57 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-6D, mc.player.posZ, false));
/* 58 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
/* 59 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer());
/* 60 */             mc.player.onCriticalHit(Objects.<Entity>requireNonNull(packet.getEntityFromWorld((World)mc.world)));
/*    */             break;
/*    */         } 
/* 63 */         this.timer.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getDisplayInfo() {
/* 69 */     return "Packet";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\Criticals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
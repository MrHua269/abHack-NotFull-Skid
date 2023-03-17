/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoFall
/*    */   extends Module
/*    */ {
/* 15 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.PACKET));
/* 16 */   private final Setting<Integer> distance = register(new Setting("Distance", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(50), v -> (this.mode.getValue() == Mode.PACKET)));
/*    */   
/*    */   public NoFall() {
/* 19 */     super("NoFall", "Prevents fall damage.", Module.Category.MOVEMENT, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 25 */     if (fullNullCheck())
/*    */       return; 
/* 27 */     if (this.mode.getValue() == Mode.PACKET && event.getPacket() instanceof CPacketPlayer && 
/* 28 */       mc.player.fallDistance >= ((Integer)this.distance.getValue()).intValue()) {
/* 29 */       CPacketPlayer packet = (CPacketPlayer)event.getPacket();
/* 30 */       packet.onGround = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 36 */     if (fullNullCheck())
/*    */       return; 
/* 38 */     if (mc.player.fallDistance > 2.0F && this.mode.getValue() == Mode.Wurst)
/* 39 */       ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc.getConnection())).sendPacket((Packet)new CPacketPlayer(true)); 
/*    */   }
/*    */   
/*    */   public String getDisplayInfo() {
/* 43 */     return this.mode.currentEnumName();
/*    */   }
/*    */   
/*    */   public enum Mode
/*    */   {
/* 48 */     PACKET,
/* 49 */     Wurst;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\NoFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
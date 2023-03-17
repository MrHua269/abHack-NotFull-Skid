/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.DeathEvent;
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.modules.combat.AutoCity;
/*    */ import me.abHack.features.modules.combat.KeyCity;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ import net.minecraft.network.play.client.CPacketUseEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.entity.player.AttackEntityEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoGG extends Module {
/* 23 */   private final Setting<String> messages = register(new Setting("Messages", "我的很大 你忍一下"));
/* 24 */   private final Setting<Boolean> greentext = register(new Setting("Greentext", Boolean.valueOf(true)));
/* 25 */   private final Setting<Integer> targetResetTimer = register(new Setting("Reset", Integer.valueOf(30), Integer.valueOf(0), Integer.valueOf(90)));
/* 26 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(30)));
/* 27 */   private final Timer timer = new Timer();
/* 28 */   private final Timer cooldownTimer = new Timer();
/* 29 */   public Map<EntityPlayer, Integer> targets = new ConcurrentHashMap<>();
/*    */   public EntityPlayer cauraTarget;
/*    */   private boolean cooldown;
/*    */   
/*    */   public AutoGG() {
/* 34 */     super("AutoGG", "Automatically GGs", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 39 */     this.timer.reset();
/* 40 */     this.cooldownTimer.reset();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 47 */     if ((AutoCity.target != null || KeyCity.target != null) && (this.cauraTarget != AutoCity.target || this.cauraTarget != KeyCity.target)) {
/* 48 */       this.cauraTarget = (AutoCity.target != null) ? AutoCity.target : KeyCity.target;
/*    */     }
/* 50 */     if (!this.cooldown) {
/* 51 */       this.cooldownTimer.reset();
/*    */     }
/* 53 */     if (this.cooldownTimer.passedS(((Integer)this.delay.getValue()).intValue()) && this.cooldown) {
/* 54 */       this.cooldown = false;
/* 55 */       this.cooldownTimer.reset();
/*    */     } 
/* 57 */     if (AutoCity.target != null || KeyCity.target != null) {
/* 58 */       this.targets.put((AutoCity.target != null) ? AutoCity.target : KeyCity.target, Integer.valueOf((int)(this.timer.getPassedTimeMs() / 1000L)));
/*    */     }
/* 60 */     this.targets.replaceAll((p, v) -> Integer.valueOf((int)(this.timer.getPassedTimeMs() / 1000L)));
/* 61 */     for (EntityPlayer player : this.targets.keySet()) {
/* 62 */       if (((Integer)this.targets.get(player)).intValue() <= ((Integer)this.targetResetTimer.getValue()).intValue())
/* 63 */         continue;  this.targets.remove(player);
/* 64 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEntityDeath(DeathEvent event) {
/* 70 */     if (this.targets.containsKey(event.player) && !this.cooldown) {
/* 71 */       announceDeath(event.player);
/* 72 */       this.cooldown = true;
/* 73 */       this.targets.remove(event.player);
/*    */     } 
/* 75 */     if (event.player == this.cauraTarget && !this.cooldown) {
/* 76 */       announceDeath(event.player);
/* 77 */       this.cooldown = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onAttackEntity(AttackEntityEvent event) {
/* 83 */     if (event.getTarget() instanceof EntityPlayer && !OyVey.friendManager.isFriend(event.getEntityPlayer())) {
/* 84 */       this.targets.put((EntityPlayer)event.getTarget(), Integer.valueOf(0));
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onSendAttackPacket(PacketEvent.Send event) {
/*    */     CPacketUseEntity packet;
/* 91 */     if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)mc.world) instanceof EntityPlayer && !OyVey.friendManager.isFriend((EntityPlayer)Objects.requireNonNull(packet.getEntityFromWorld((World)mc.world)))) {
/* 92 */       this.targets.put((EntityPlayer)packet.getEntityFromWorld((World)mc.world), Integer.valueOf(0));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void announceDeath(EntityPlayer target) {
/* 98 */     mc.player.connection.sendPacket((Packet)new CPacketChatMessage((((Boolean)this.greentext.getValue()).booleanValue() ? ">" : "") + target.getDisplayNameString() + " " + (String)this.messages.getValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\AutoGG.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
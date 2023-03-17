/*     */ package me.abHack.features.modules.player;
/*     */ 
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Blink
/*     */   extends Module {
/*  19 */   private static Blink INSTANCE = new Blink();
/*  20 */   private final Setting<Boolean> cPacketPlayer = register(new Setting("CPacketPlayer", Boolean.valueOf(true)));
/*  21 */   private final Setting<Mode> autoOff = register(new Setting("AutoOff", Mode.MANUAL));
/*  22 */   private final Setting<Integer> timeLimit = register(new Setting("Time", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(500), v -> (this.autoOff.getValue() == Mode.TIME)));
/*  23 */   private final Setting<Integer> packetLimit = register(new Setting("Packets", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(500), v -> (this.autoOff.getValue() == Mode.PACKETS)));
/*  24 */   private final Setting<Float> distance = register(new Setting("Distance", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(100.0F), v -> (this.autoOff.getValue() == Mode.DISTANCE)));
/*  25 */   private final Timer timer = new Timer();
/*  26 */   private final Queue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
/*     */   private EntityOtherPlayerMP entity;
/*  28 */   private int packetsCanceled = 0;
/*  29 */   private BlockPos startPos = null;
/*     */   
/*     */   public Blink() {
/*  32 */     super("Blink", "Fake lag.", Module.Category.PLAYER, true, false, false);
/*  33 */     setInstance();
/*     */   }
/*     */   
/*     */   public static Blink getInstance() {
/*  37 */     if (INSTANCE == null) {
/*  38 */       INSTANCE = new Blink();
/*     */     }
/*  40 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  44 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  48 */     if (!fullNullCheck()) {
/*  49 */       this.entity = new EntityOtherPlayerMP((World)mc.world, mc.session.getProfile());
/*  50 */       this.entity.copyLocationAndAnglesFrom((Entity)mc.player);
/*  51 */       this.entity.rotationYaw = mc.player.rotationYaw;
/*  52 */       this.entity.rotationYawHead = mc.player.rotationYawHead;
/*  53 */       this.entity.inventory.copyInventory(mc.player.inventory);
/*  54 */       mc.world.addEntityToWorld(6942069, (Entity)this.entity);
/*  55 */       this.startPos = mc.player.getPosition();
/*     */     } else {
/*  57 */       disable();
/*     */     } 
/*  59 */     this.packetsCanceled = 0;
/*  60 */     this.timer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  65 */     if (nullCheck() || (this.autoOff.getValue() == Mode.TIME && this.timer.passedS(((Integer)this.timeLimit.getValue()).intValue())) || (this.autoOff.getValue() == Mode.DISTANCE && this.startPos != null && mc.player.getDistanceSq(this.startPos) >= MathUtil.square(((Float)this.distance.getValue()).floatValue())) || (this.autoOff.getValue() == Mode.PACKETS && this.packetsCanceled >= ((Integer)this.packetLimit.getValue()).intValue())) {
/*  66 */       disable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLogout() {
/*  72 */     if (isOn()) {
/*  73 */       disable();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSendPacket(PacketEvent.Send event) {
/*  79 */     if (mc.world != null && !mc.isSingleplayer()) {
/*  80 */       Object packet = event.getPacket();
/*  81 */       if (((Boolean)this.cPacketPlayer.getValue()).booleanValue() && packet instanceof net.minecraft.network.play.client.CPacketPlayer) {
/*  82 */         event.setCanceled(true);
/*  83 */         this.packets.add((Packet)packet);
/*  84 */         this.packetsCanceled++;
/*     */       } 
/*  86 */       if (!((Boolean)this.cPacketPlayer.getValue()).booleanValue()) {
/*  87 */         if (packet instanceof net.minecraft.network.play.client.CPacketChatMessage || packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport || packet instanceof net.minecraft.network.play.client.CPacketKeepAlive || packet instanceof net.minecraft.network.play.client.CPacketTabComplete || packet instanceof net.minecraft.network.play.client.CPacketClientStatus) {
/*     */           return;
/*     */         }
/*  90 */         this.packets.add((Packet)packet);
/*  91 */         event.setCanceled(true);
/*  92 */         this.packetsCanceled++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  99 */     if (!fullNullCheck()) {
/* 100 */       mc.world.removeEntity((Entity)this.entity);
/* 101 */       while (!this.packets.isEmpty()) {
/* 102 */         mc.player.connection.sendPacket(this.packets.poll());
/*     */       }
/*     */     } 
/* 105 */     this.startPos = null;
/*     */   }
/*     */   
/*     */   public String getDisplayInfo() {
/* 109 */     if (this.packets != null) {
/* 110 */       return this.packets.size() + "";
/*     */     }
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 117 */     MANUAL,
/* 118 */     TIME,
/* 119 */     DISTANCE,
/* 120 */     PACKETS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Blink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
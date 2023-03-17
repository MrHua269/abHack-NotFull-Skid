/*     */ package me.abHack.features.modules.movement;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import me.abHack.event.events.MoveEvent;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class ElytraFlight
/*     */   extends Module {
/*  16 */   public Setting<Float> upSpeed = register(new Setting("UpSpeed", Float.valueOf(2.0F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*     */   
/*  18 */   public Setting<Float> oneSpeed = register(new Setting("One Speed", Float.valueOf(2.0F), Float.valueOf(0.0F), Float.valueOf(8.0F)));
/*     */   
/*     */   public ElytraFlight() {
/*  21 */     super("ElytraFlight", "Makes Elytra Flight better.", Module.Category.MOVEMENT, true, false, false);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSendPacket(PacketEvent.Send event) {
/*  26 */     if (event.getPacket() instanceof CPacketPlayer && !mc.player.isElytraFlying() && event
/*  27 */       .getPacket() instanceof CPacketPlayer && mc.player.isElytraFlying())
/*  28 */       if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
/*  29 */         CPacketPlayer.PositionRotation rotation = (CPacketPlayer.PositionRotation)event.getPacket();
/*  30 */         Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketPlayer.Position(rotation.x, rotation.y, rotation.z, rotation.onGround));
/*  31 */         event.setCanceled(true);
/*  32 */       } else if (event.getPacket() instanceof CPacketPlayer.Position) {
/*  33 */         event.setCanceled(true);
/*     */       }  
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMove(MoveEvent event) {
/*  39 */     float yaw = (float)Math.toRadians(mc.player.rotationYaw);
/*  40 */     double motionA = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
/*  41 */     if (!mc.player.isElytraFlying())
/*     */       return; 
/*  43 */     if (mc.gameSettings.keyBindSneak.isKeyDown()) {
/*  44 */       mc.player.motionY = -1.0D;
/*  45 */     } else if (mc.player.movementInput.jump && motionA > 1.0D) {
/*  46 */       if (mc.player.motionX == 0.0D && mc.player.motionZ == 0.0D) {
/*  47 */         mc.player.motionY = ((Float)this.upSpeed.getValue()).floatValue();
/*     */       } else {
/*  49 */         double motionB = motionA * 0.008D;
/*  50 */         mc.player.motionY += motionB * 3.2D;
/*  51 */         mc.player.motionX -= -MathHelper.sin(yaw) * motionB;
/*  52 */         mc.player.motionZ -= MathHelper.cos(yaw) * motionB;
/*  53 */         mc.player.motionY *= 0.9900000095367432D;
/*  54 */         mc.player.motionX *= 0.9800000190734863D;
/*  55 */         mc.player.motionZ *= 0.9900000095367432D;
/*     */       } 
/*     */     } else {
/*  58 */       if (event.getY() != -1.01E-4D) {
/*  59 */         event.setY(-1.01E-4D);
/*  60 */         mc.player.motionY = -1.01E-4D;
/*     */       } 
/*  62 */       setMoveSpeed(event, ((Float)this.oneSpeed.getValue()).floatValue() * 1.8D);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setMoveSpeed(MoveEvent event, double speed) {
/*  67 */     double forward = mc.player.movementInput.moveForward;
/*  68 */     double strafe = mc.player.movementInput.moveStrafe;
/*  69 */     float yaw = mc.player.rotationYaw;
/*  70 */     if (forward == 0.0D && strafe == 0.0D) {
/*  71 */       event.setX(0.0D);
/*  72 */       event.setZ(0.0D);
/*  73 */       mc.player.motionX = 0.0D;
/*  74 */       mc.player.motionZ = 0.0D;
/*     */     } else {
/*  76 */       if (forward != 0.0D) {
/*  77 */         if (strafe > 0.0D) {
/*  78 */           yaw += ((forward > 0.0D) ? -45 : 45);
/*  79 */         } else if (strafe < 0.0D) {
/*  80 */           yaw += ((forward > 0.0D) ? 45 : -45);
/*     */         } 
/*  82 */         strafe = 0.0D;
/*  83 */         if (forward > 0.0D) {
/*  84 */           forward = 1.0D;
/*  85 */         } else if (forward < 0.0D) {
/*  86 */           forward = -1.0D;
/*     */         } 
/*     */       } 
/*  89 */       double x = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));
/*  90 */       double z = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
/*  91 */       event.setX(x);
/*  92 */       event.setZ(z);
/*  93 */       mc.player.motionX = x;
/*  94 */       mc.player.motionZ = z;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  99 */     if (fullNullCheck() || mc.player.capabilities.isCreativeMode)
/*     */       return; 
/* 101 */     mc.player.capabilities.isFlying = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\ElytraFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
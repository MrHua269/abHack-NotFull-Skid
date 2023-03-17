/*     */ package me.abHack.features.modules.movement;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ 
/*     */ public class Step extends Module {
/*     */   private static Step instance;
/*  12 */   Setting<Double> height = register(new Setting("Height", Double.valueOf(2.5D), Double.valueOf(0.5D), Double.valueOf(2.5D)));
/*  13 */   Setting<Mode> mode = register(new Setting("Mode", Mode.Vanilla));
/*     */   
/*     */   public Step() {
/*  16 */     super("Step", "step", Module.Category.MOVEMENT, true, false, false);
/*  17 */     instance = this;
/*     */   }
/*     */   
/*     */   public static Step getInstance() {
/*  21 */     if (instance == null) {
/*  22 */       instance = new Step();
/*     */     }
/*  24 */     return instance;
/*     */   }
/*     */   
/*     */   public static double[] forward(double speed) {
/*  28 */     float forward = mc.player.movementInput.moveForward;
/*  29 */     float side = mc.player.movementInput.moveStrafe;
/*  30 */     float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
/*  31 */     if (forward != 0.0F) {
/*  32 */       if (side > 0.0F) {
/*  33 */         yaw += ((forward > 0.0F) ? -45 : 45);
/*  34 */       } else if (side < 0.0F) {
/*  35 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/*  37 */       side = 0.0F;
/*  38 */       if (forward > 0.0F) {
/*  39 */         forward = 1.0F;
/*  40 */       } else if (forward < 0.0F) {
/*  41 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/*  44 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/*  45 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/*  46 */     double posX = forward * speed * cos + side * speed * sin;
/*  47 */     double posZ = forward * speed * sin - side * speed * cos;
/*  48 */     return new double[] { posX, posZ };
/*     */   }
/*     */ 
/*     */   
/*     */   public void onToggle() {
/*  53 */     mc.player.stepHeight = 0.6F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  58 */     if (mc.world == null || mc.player == null) {
/*     */       return;
/*     */     }
/*  61 */     if (mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder() || mc.gameSettings.keyBindJump.isKeyDown()) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     if (this.mode.getValue() == Mode.Normal) {
/*  66 */       double[] dir = forward(0.1D);
/*  67 */       boolean twofive = false;
/*  68 */       boolean two = false;
/*  69 */       boolean onefive = false;
/*  70 */       boolean one = false;
/*  71 */       if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 2.6D, dir[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 2.4D, dir[1])).isEmpty()) {
/*  72 */         twofive = true;
/*     */       }
/*  74 */       if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 2.1D, dir[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.9D, dir[1])).isEmpty()) {
/*  75 */         two = true;
/*     */       }
/*  77 */       if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.6D, dir[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.4D, dir[1])).isEmpty()) {
/*  78 */         onefive = true;
/*     */       }
/*  80 */       if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.0D, dir[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 0.6D, dir[1])).isEmpty()) {
/*  81 */         one = true;
/*     */       }
/*  83 */       if (mc.player.collidedHorizontally && (mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) && mc.player.onGround) {
/*     */         
/*  85 */         if (one && ((Double)this.height.getValue()).doubleValue() >= 1.0D) {
/*  86 */           double[] oneOffset = { 0.42D, 0.753D };
/*  87 */           for (int i = 0; i < oneOffset.length; i++) {
/*  88 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + oneOffset[i], mc.player.posZ, mc.player.onGround));
/*     */           }
/*  90 */           mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0D, mc.player.posZ);
/*     */         } 
/*  92 */         if (onefive && ((Double)this.height.getValue()).doubleValue() >= 1.5D) {
/*  93 */           double[] oneFiveOffset = { 0.42D, 0.75D, 1.0D, 1.16D, 1.23D, 1.2D };
/*  94 */           for (int i = 0; i < oneFiveOffset.length; i++) {
/*  95 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + oneFiveOffset[i], mc.player.posZ, mc.player.onGround));
/*     */           }
/*  97 */           mc.player.setPosition(mc.player.posX, mc.player.posY + 1.5D, mc.player.posZ);
/*     */         } 
/*  99 */         if (two && ((Double)this.height.getValue()).doubleValue() >= 2.0D) {
/* 100 */           double[] twoOffset = { 0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D };
/* 101 */           for (int i = 0; i < twoOffset.length; i++) {
/* 102 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + twoOffset[i], mc.player.posZ, mc.player.onGround));
/*     */           }
/* 104 */           mc.player.setPosition(mc.player.posX, mc.player.posY + 2.0D, mc.player.posZ);
/*     */         } 
/* 106 */         if (twofive && ((Double)this.height.getValue()).doubleValue() >= 2.5D) {
/* 107 */           double[] twoFiveOffset = { 0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D };
/* 108 */           for (int i = 0; i < twoFiveOffset.length; i++) {
/* 109 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + twoFiveOffset[i], mc.player.posZ, mc.player.onGround));
/*     */           }
/* 111 */           mc.player.setPosition(mc.player.posX, mc.player.posY + 2.5D, mc.player.posZ);
/*     */         } 
/*     */       } 
/*     */     } 
/* 115 */     if (this.mode.getValue() == Mode.Vanilla) {
/* 116 */       DecimalFormat df = new DecimalFormat("#");
/* 117 */       mc.player.stepHeight = Float.parseFloat(df.format(this.height.getValue()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 123 */     return this.mode.currentEnumName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 128 */     mc.player.stepHeight = 0.5F;
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 133 */     Vanilla,
/* 134 */     Normal;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\Step.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
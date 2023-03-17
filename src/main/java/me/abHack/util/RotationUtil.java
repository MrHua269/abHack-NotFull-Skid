/*     */ package me.abHack.util;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import me.abHack.features.modules.client.ClickGui;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class RotationUtil
/*     */   implements Util
/*     */ {
/*     */   public static float[] getLegitRotations(Vec3d vec) {
/*  18 */     Vec3d eyesPos = getEyesPos();
/*  19 */     double diffX = vec.x - eyesPos.x;
/*  20 */     double diffY = vec.y - eyesPos.y;
/*  21 */     double diffZ = vec.z - eyesPos.z;
/*  22 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*  23 */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/*  24 */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/*  25 */     return new float[] { mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
/*     */   }
/*     */   
/*     */   public static void faceYawAndPitch(float yaw, float pitch) {
/*  29 */     mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, mc.player.onGround));
/*     */   }
/*     */   
/*     */   public static BlockPos getPlayerPos() {
/*  33 */     double decimalPoint = mc.player.posY - Math.floor(mc.player.posY);
/*  34 */     return new BlockPos(mc.player.posX, (decimalPoint > 0.8D) ? (Math.floor(mc.player.posY) + 1.0D) : Math.floor(mc.player.posY), mc.player.posZ);
/*     */   }
/*     */   
/*     */   public static Vec2f getRotationTo(Vec3d posTo, Vec3d posFrom) {
/*  38 */     return getRotationFromVec(posTo.subtract(posFrom));
/*     */   }
/*     */   
/*     */   public static Vec2f getRotationFromVec(Vec3d vec) {
/*  42 */     double xz = Math.hypot(vec.x, vec.z);
/*  43 */     float yaw = (float)normalizeAngle(Double.valueOf(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0D));
/*  44 */     float pitch = (float)normalizeAngle(Double.valueOf(Math.toDegrees(-Math.atan2(vec.y, xz))));
/*  45 */     return new Vec2f(yaw, pitch);
/*     */   }
/*     */   
/*     */   public static HoleUtil.Hole getTargetHoleVec3D(double targetRange) {
/*  49 */     return HoleUtil.getHoles(targetRange, getPlayerPos(), false).stream().filter(hole -> (mc.player.getPositionVector().distanceTo(new Vec3d(hole.pos1.getX() + 0.5D, mc.player.posY, hole.pos1.getZ() + 0.5D)) <= targetRange)).min(Comparator.comparingDouble(hole -> mc.player.getPositionVector().distanceTo(new Vec3d(hole.pos1.getX() + 0.5D, mc.player.posY, hole.pos1.getZ() + 0.5D)))).orElse(null);
/*     */   }
/*     */   
/*     */   public static double normalizeAngle(Double angleIn) {
/*  53 */     double angle = angleIn.doubleValue();
/*  54 */     if ((angle %= 360.0D) >= 180.0D)
/*  55 */       angle -= 360.0D; 
/*  56 */     if (angle < -180.0D)
/*  57 */       angle += 360.0D; 
/*  58 */     return angle;
/*     */   }
/*     */   
/*     */   public static void faceVector(Vec3d vec, boolean normalizeAngle) {
/*  62 */     float[] rotations = getLegitRotations(vec);
/*  63 */     mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? MathHelper.normalizeAngle((int)rotations[1], 360) : rotations[1], mc.player.onGround));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isInFov(BlockPos pos) {
/*  68 */     return (pos != null && (mc.player.getDistanceSq(pos) < 4.0D || yawDist(pos) < (getHalvedfov() + 2.0F)));
/*     */   }
/*     */   
/*     */   public static double yawDist(BlockPos pos) {
/*  72 */     if (pos != null) {
/*  73 */       Vec3d difference = (new Vec3d((Vec3i)pos)).subtract(mc.player.getPositionEyes(mc.getRenderPartialTicks()));
/*  74 */       double d = Math.abs(mc.player.rotationYaw - Math.toDegrees(Math.atan2(difference.z, difference.x)) - 90.0D) % 360.0D;
/*  75 */       return (d > 180.0D) ? (360.0D - d) : d;
/*     */     } 
/*  77 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public static float getFov() {
/*  81 */     return ((Boolean)ClickGui.INSTANCE.customFov.getValue()).booleanValue() ? ((Float)ClickGui.INSTANCE.fov.getValue()).floatValue() : mc.gameSettings.fovSetting;
/*     */   }
/*     */   
/*     */   public static float getHalvedfov() {
/*  85 */     return getFov() / 2.0F;
/*     */   }
/*     */   
/*     */   public static float[] getAngle(Entity entity) {
/*  89 */     return MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionEyes(mc.getRenderPartialTicks()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d getEyesPos() {
/*  95 */     return new Vec3d(mc.player.posX, mc.player.posY + mc.player
/*  96 */         .getEyeHeight(), mc.player.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDirection4D() {
/* 102 */     return MathHelper.floor((mc.player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */   }
/*     */   
/*     */   public static String getDirection4D(boolean northRed) {
/* 106 */     int dirnumber = getDirection4D();
/* 107 */     if (dirnumber == 0) {
/* 108 */       return "South (+Z)";
/*     */     }
/* 110 */     if (dirnumber == 1) {
/* 111 */       return "West (-X)";
/*     */     }
/* 113 */     if (dirnumber == 2) {
/* 114 */       return (northRed ? "Â§c" : "") + "North (-Z)";
/*     */     }
/* 116 */     if (dirnumber == 3) {
/* 117 */       return "East (+X)";
/*     */     }
/* 119 */     return "Loading...";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
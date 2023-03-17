/*    */ package me.abHack.manager;
/*    */ 
/*    */ import me.abHack.features.Feature;
/*    */ import me.abHack.util.MathUtil;
/*    */ import me.abHack.util.RotationUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class RotationManager
/*    */   extends Feature {
/*    */   private float yaw;
/*    */   private float pitch;
/*    */   
/*    */   public void updateRotations() {
/* 16 */     this.yaw = mc.player.rotationYaw;
/* 17 */     this.pitch = mc.player.rotationPitch;
/*    */   }
/*    */   
/*    */   public void restoreRotations() {
/* 21 */     mc.player.rotationYaw = this.yaw;
/* 22 */     mc.player.rotationYawHead = this.yaw;
/* 23 */     mc.player.rotationPitch = this.pitch;
/*    */   }
/*    */   
/*    */   public void setPlayerRotations(float yaw, float pitch) {
/* 27 */     mc.player.rotationYaw = yaw;
/* 28 */     mc.player.rotationYawHead = yaw;
/* 29 */     mc.player.rotationPitch = pitch;
/*    */   }
/*    */   
/*    */   public void setPlayerYaw(float yaw) {
/* 33 */     mc.player.rotationYaw = yaw;
/* 34 */     mc.player.rotationYawHead = yaw;
/*    */   }
/*    */   
/*    */   public void lookAtPos(BlockPos pos) {
/* 38 */     float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F)));
/* 39 */     setPlayerRotations(angle[0], angle[1]);
/*    */   }
/*    */   
/*    */   public void lookAtVec3d(Vec3d vec3d) {
/* 43 */     float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
/* 44 */     setPlayerRotations(angle[0], angle[1]);
/*    */   }
/*    */   
/*    */   public void lookAtVec3d(double x, double y, double z) {
/* 48 */     Vec3d vec3d = new Vec3d(x, y, z);
/* 49 */     lookAtVec3d(vec3d);
/*    */   }
/*    */   
/*    */   public void lookAtEntity(Entity entity) {
/* 53 */     float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionEyes(mc.getRenderPartialTicks()));
/* 54 */     setPlayerRotations(angle[0], angle[1]);
/*    */   }
/*    */   
/*    */   public void setPlayerPitch(float pitch) {
/* 58 */     mc.player.rotationPitch = pitch;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 62 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public void setYaw(float yaw) {
/* 66 */     this.yaw = yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 70 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public void setPitch(float pitch) {
/* 74 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public int getDirection4D() {
/* 78 */     return RotationUtil.getDirection4D();
/*    */   }
/*    */   
/*    */   public String getDirection4D(boolean northRed) {
/* 82 */     return RotationUtil.getDirection4D(northRed);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\RotationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
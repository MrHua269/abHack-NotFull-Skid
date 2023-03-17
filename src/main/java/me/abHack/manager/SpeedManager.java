/*    */ package me.abHack.manager;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import me.abHack.features.Feature;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class SpeedManager
/*    */   extends Feature {
/*    */   public static boolean didJumpThisTick = false;
/*    */   public static boolean isJumping = false;
/* 14 */   private final int distancer = 20;
/* 15 */   public double firstJumpSpeed = 0.0D;
/* 16 */   public double lastJumpSpeed = 0.0D;
/* 17 */   public double percentJumpSpeedChanged = 0.0D;
/* 18 */   public double jumpSpeedChanged = 0.0D;
/*    */   public boolean didJumpLastTick = false;
/* 20 */   public long jumpInfoStartTime = 0L;
/*    */   public boolean wasFirstJump = true;
/* 22 */   public double speedometerCurrentSpeed = 0.0D;
/* 23 */   public HashMap<EntityPlayer, Double> playerSpeeds = new HashMap<>();
/*    */   
/*    */   public static void setDidJumpThisTick(boolean val) {
/* 26 */     didJumpThisTick = val;
/*    */   }
/*    */   
/*    */   public static void setIsJumping(boolean val) {
/* 30 */     isJumping = val;
/*    */   }
/*    */   
/*    */   public float lastJumpInfoTimeRemaining() {
/* 34 */     return (float)(Minecraft.getSystemTime() - this.jumpInfoStartTime) / 1000.0F;
/*    */   }
/*    */   
/*    */   public void updateValues() {
/* 38 */     double distTraveledLastTickX = mc.player.posX - mc.player.prevPosX;
/* 39 */     double distTraveledLastTickZ = mc.player.posZ - mc.player.prevPosZ;
/* 40 */     this.speedometerCurrentSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
/* 41 */     if (didJumpThisTick && (!mc.player.onGround || isJumping)) {
/* 42 */       if (didJumpThisTick && !this.didJumpLastTick) {
/* 43 */         this.wasFirstJump = (this.lastJumpSpeed == 0.0D);
/* 44 */         this.percentJumpSpeedChanged = (this.speedometerCurrentSpeed != 0.0D) ? (this.speedometerCurrentSpeed / this.lastJumpSpeed - 1.0D) : -1.0D;
/* 45 */         this.jumpSpeedChanged = this.speedometerCurrentSpeed - this.lastJumpSpeed;
/* 46 */         this.jumpInfoStartTime = Minecraft.getSystemTime();
/* 47 */         this.lastJumpSpeed = this.speedometerCurrentSpeed;
/* 48 */         this.firstJumpSpeed = this.wasFirstJump ? this.lastJumpSpeed : 0.0D;
/*    */       } 
/* 50 */       this.didJumpLastTick = didJumpThisTick;
/*    */     } else {
/* 52 */       this.didJumpLastTick = false;
/* 53 */       this.lastJumpSpeed = 0.0D;
/*    */     } 
/* 55 */     updatePlayers();
/*    */   }
/*    */   
/*    */   public void updatePlayers() {
/* 59 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 60 */       getClass(); getClass(); if (mc.player.getDistanceSq((Entity)player) >= (20 * 20))
/*    */         continue; 
/* 62 */       double distTraveledLastTickX = player.posX - player.prevPosX;
/* 63 */       double distTraveledLastTickZ = player.posZ - player.prevPosZ;
/* 64 */       double playerSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
/* 65 */       this.playerSpeeds.put(player, Double.valueOf(playerSpeed));
/*    */     } 
/*    */   }
/*    */   
/*    */   public double getPlayerSpeed(EntityPlayer player) {
/* 70 */     if (this.playerSpeeds.get(player) == null) {
/* 71 */       return 0.0D;
/*    */     }
/* 73 */     return turnIntoKpH(((Double)this.playerSpeeds.get(player)).doubleValue());
/*    */   }
/*    */   
/*    */   public double turnIntoKpH(double input) {
/* 77 */     return MathHelper.sqrt(input) * 71.2729367892D;
/*    */   }
/*    */   
/*    */   public double getSpeedKpH() {
/* 81 */     double speedometerkphdouble = turnIntoKpH(this.speedometerCurrentSpeed);
/* 82 */     speedometerkphdouble = Math.round(10.0D * speedometerkphdouble) / 10.0D;
/* 83 */     return speedometerkphdouble;
/*    */   }
/*    */   
/*    */   public double getSpeedMpS() {
/* 87 */     double speedometerMpsdouble = turnIntoKpH(this.speedometerCurrentSpeed) / 3.6D;
/* 88 */     speedometerMpsdouble = Math.round(10.0D * speedometerMpsdouble) / 10.0D;
/* 89 */     return speedometerMpsdouble;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\SpeedManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
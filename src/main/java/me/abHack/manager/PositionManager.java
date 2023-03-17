/*    */ package me.abHack.manager;
/*    */ 
/*    */ import me.abHack.features.Feature;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class PositionManager extends Feature {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   private boolean onground;
/*    */   
/*    */   public void updatePosition() {
/* 14 */     this.x = mc.player.posX;
/* 15 */     this.y = mc.player.posY;
/* 16 */     this.z = mc.player.posZ;
/* 17 */     this.onground = mc.player.onGround;
/*    */   }
/*    */   
/*    */   public void restorePosition() {
/* 21 */     mc.player.posX = this.x;
/* 22 */     mc.player.posY = this.y;
/* 23 */     mc.player.posZ = this.z;
/* 24 */     mc.player.onGround = this.onground;
/*    */   }
/*    */   
/*    */   public void setPlayerPosition(double x, double y, double z) {
/* 28 */     mc.player.posX = x;
/* 29 */     mc.player.posY = y;
/* 30 */     mc.player.posZ = z;
/*    */   }
/*    */   
/*    */   public void setPlayerPosition(double x, double y, double z, boolean onground) {
/* 34 */     mc.player.posX = x;
/* 35 */     mc.player.posY = y;
/* 36 */     mc.player.posZ = z;
/* 37 */     mc.player.onGround = onground;
/*    */   }
/*    */   
/*    */   public void setPositionPacket(double x, double y, double z, boolean onGround, boolean setPos, boolean noLagBack) {
/* 41 */     mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, onGround));
/* 42 */     if (setPos) {
/* 43 */       mc.player.setPosition(x, y, z);
/* 44 */       if (noLagBack) {
/* 45 */         updatePosition();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public double getX() {
/* 51 */     return this.x;
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 55 */     this.x = x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 59 */     return this.y;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 63 */     this.y = y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 67 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setZ(double z) {
/* 71 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\PositionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import me.abHack.event.EventStage;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ @Cancelable
/*    */ public class MoveEvent
/*    */   extends EventStage {
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   private MoverType type;
/*    */   
/*    */   public MoveEvent(int stage, MoverType type, double x, double y, double z) {
/* 17 */     super(stage);
/* 18 */     this.type = type;
/* 19 */     this.x = x;
/* 20 */     this.y = y;
/* 21 */     this.z = z;
/*    */   }
/*    */   
/*    */   public MoverType getType() {
/* 25 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(MoverType type) {
/* 29 */     this.type = type;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 33 */     return this.x;
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 37 */     this.x = x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 41 */     return this.y;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 45 */     this.y = y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 49 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setZ(double z) {
/* 53 */     this.z = z;
/*    */   }
/*    */   
/*    */   public void setSpeed(double speed) {
/* 57 */     float yaw = (Minecraft.getMinecraft()).player.rotationYaw;
/* 58 */     double forward = (Minecraft.getMinecraft()).player.movementInput.moveForward;
/* 59 */     double strafe = (Minecraft.getMinecraft()).player.movementInput.moveStrafe;
/* 60 */     if (forward == 0.0D && strafe == 0.0D) {
/* 61 */       setX(0.0D);
/* 62 */       setZ(0.0D);
/*    */     } else {
/* 64 */       if (forward != 0.0D) {
/* 65 */         if (strafe > 0.0D) {
/* 66 */           yaw += ((forward > 0.0D) ? -45 : 45);
/* 67 */         } else if (strafe < 0.0D) {
/* 68 */           yaw += ((forward > 0.0D) ? 45 : -45);
/*    */         } 
/* 70 */         strafe = 0.0D;
/* 71 */         if (forward > 0.0D) {
/* 72 */           forward = 1.0D;
/*    */         } else {
/* 74 */           forward = -1.0D;
/*    */         } 
/*    */       } 
/* 77 */       double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 78 */       double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 79 */       setX(forward * speed * cos + strafe * speed * sin);
/* 80 */       setZ(forward * speed * sin - strafe * speed * cos);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\MoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
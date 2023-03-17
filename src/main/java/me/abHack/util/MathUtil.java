/*    */ package me.abHack.util;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.RoundingMode;
/*    */ import java.util.Calendar;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class MathUtil
/*    */   implements Util
/*    */ {
/*    */   public static Vec3d roundVec(Vec3d vec3d, int places) {
/* 13 */     return new Vec3d(round(vec3d.x, places), round(vec3d.y, places), round(vec3d.z, places));
/*    */   }
/*    */   
/*    */   public static double square(double input) {
/* 17 */     return input * input;
/*    */   }
/*    */   
/*    */   public static double round(double value, int places) {
/* 21 */     if (places < 0) {
/* 22 */       throw new IllegalArgumentException();
/*    */     }
/* 24 */     BigDecimal bd = BigDecimal.valueOf(value);
/* 25 */     bd = bd.setScale(places, RoundingMode.FLOOR);
/* 26 */     return bd.doubleValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Vec3d direction(float yaw) {
/* 31 */     return new Vec3d(Math.cos(degToRad((yaw + 90.0F))), 0.0D, Math.sin(degToRad((yaw + 90.0F))));
/*    */   }
/*    */   
/*    */   public static float round(float value, int places) {
/* 35 */     if (places < 0) {
/* 36 */       throw new IllegalArgumentException();
/*    */     }
/* 38 */     BigDecimal bd = BigDecimal.valueOf(value);
/* 39 */     bd = bd.setScale(places, RoundingMode.FLOOR);
/* 40 */     return bd.floatValue();
/*    */   }
/*    */   
/*    */   public static String getTimeOfDay() {
/* 44 */     Calendar c = Calendar.getInstance();
/* 45 */     int timeOfDay = c.get(11);
/* 46 */     if (timeOfDay < 12) {
/* 47 */       return "Good Morning ";
/*    */     }
/* 49 */     if (timeOfDay < 16) {
/* 50 */       return "Good Afternoon ";
/*    */     }
/* 52 */     if (timeOfDay < 21) {
/* 53 */       return "Good Evening ";
/*    */     }
/* 55 */     return "Good Night ";
/*    */   }
/*    */ 
/*    */   
/*    */   public static double degToRad(double deg) {
/* 60 */     return deg * 0.01745329238474369D;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float[] calcAngle(Vec3d from, Vec3d to) {
/* 65 */     double difX = to.x - from.x;
/* 66 */     double difY = (to.y - from.y) * -1.0D;
/* 67 */     double difZ = to.z - from.z;
/* 68 */     double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
/* 69 */     return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
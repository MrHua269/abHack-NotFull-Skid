/*    */ package me.abHack.util.render.Trails;
/*    */ 
/*    */ import java.util.List;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.DimensionType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Trace
/*    */ {
/*    */   private String name;
/*    */   private int index;
/*    */   private Vec3d pos;
/*    */   private List<TracePos> trace;
/*    */   private DimensionType type;
/*    */   
/*    */   public Trace(int index, String name, DimensionType type, Vec3d pos, List<TracePos> trace) {
/* 21 */     this.index = index;
/* 22 */     this.name = name;
/* 23 */     this.type = type;
/* 24 */     this.pos = pos;
/* 25 */     this.trace = trace;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 29 */     return this.index;
/*    */   }
/*    */   
/*    */   public void setIndex(int index) {
/* 33 */     this.index = index;
/*    */   }
/*    */   
/*    */   public DimensionType getType() {
/* 37 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(DimensionType type) {
/* 41 */     this.type = type;
/*    */   }
/*    */   
/*    */   public List<TracePos> getTrace() {
/* 45 */     return this.trace;
/*    */   }
/*    */   
/*    */   public void setTrace(List<TracePos> trace) {
/* 49 */     this.trace = trace;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 53 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 57 */     this.name = name;
/*    */   }
/*    */   
/*    */   public Vec3d getPos() {
/* 61 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void setPos(Vec3d pos) {
/* 65 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public static class TracePos {
/*    */     private final Vec3d pos;
/* 70 */     private final Timer stopWatch = new Timer();
/*    */     private long time;
/*    */     
/*    */     public TracePos(Vec3d pos) {
/* 74 */       this.pos = pos;
/* 75 */       this.stopWatch.reset();
/*    */     }
/*    */     
/*    */     public TracePos(Vec3d pos, long time) {
/* 79 */       this.pos = pos;
/* 80 */       this.stopWatch.reset();
/* 81 */       this.time = time;
/*    */     }
/*    */     
/*    */     public Vec3d getPos() {
/* 85 */       return this.pos;
/*    */     }
/*    */     
/*    */     public boolean shouldRemoveTrace() {
/* 89 */       return this.stopWatch.passedMs(2000L);
/*    */     }
/*    */     
/*    */     public long getTime() {
/* 93 */       return this.time;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\Trails\Trace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
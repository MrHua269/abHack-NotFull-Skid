/*    */ package me.abHack.features.gui.alts.tools;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Pair<V1, V2>
/*    */   implements Serializable {
/*    */   private static final long serialVersionUID = 2586850598481149380L;
/*    */   private final V1 obj1;
/*    */   private final V2 obj2;
/*    */   
/*    */   public Pair(V1 obj1, V2 obj2) {
/* 12 */     this.obj1 = obj1;
/* 13 */     this.obj2 = obj2;
/*    */   }
/*    */   
/*    */   public V1 getValue1() {
/* 17 */     return this.obj1;
/*    */   }
/*    */   
/*    */   public V2 getValue2() {
/* 21 */     return this.obj2;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 25 */     return Pair.class.getName() + "@" + Integer.toHexString(hashCode()) + " [" + this.obj1.toString() + ", " + this.obj2.toString() + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\tools\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.util.render;
/*    */ 
/*    */ public class ColorHolder
/*    */ {
/*    */   int r;
/*    */   int g;
/*    */   int b;
/*    */   int a;
/*    */   
/*    */   public ColorHolder(int r, int g, int b, int a) {
/* 11 */     this.r = r;
/* 12 */     this.g = g;
/* 13 */     this.b = b;
/* 14 */     this.a = a;
/*    */   }
/*    */   
/*    */   public static int toHex(int r, int g, int b) {
/* 18 */     return 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*    */   }
/*    */   
/*    */   public int getB() {
/* 22 */     return this.b;
/*    */   }
/*    */   
/*    */   public ColorHolder setB(int b) {
/* 26 */     this.b = b;
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   public ColorHolder setA(int a) {
/* 31 */     this.a = a;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ColorHolder clone() {
/* 37 */     return new ColorHolder(this.r, this.g, this.b, this.a);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ColorHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
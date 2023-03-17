/*    */ package me.abHack.features.gui.particle;
/*    */ 
/*    */ import java.util.Random;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public class Snow
/*    */ {
/*    */   private int _x;
/*    */   private int _y;
/*    */   private int _fallingSpeed;
/*    */   private int _size;
/*    */   
/*    */   public Snow(int x, int y, int fallingSpeed, int size) {
/* 15 */     this._x = x;
/* 16 */     this._y = y;
/* 17 */     this._fallingSpeed = fallingSpeed;
/* 18 */     this._size = size;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 22 */     return this._x;
/*    */   }
/*    */   
/*    */   public void setX(int x) {
/* 26 */     this._x = x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 30 */     return this._y;
/*    */   }
/*    */   
/*    */   public void setY(int _y) {
/* 34 */     this._y = _y;
/*    */   }
/*    */   
/*    */   public void Update(ScaledResolution res) {
/* 38 */     RenderUtil.drawRect(getX(), getY(), (getX() + this._size), (getY() + this._size), -1714829883);
/*    */     
/* 40 */     setY(getY() + this._fallingSpeed);
/*    */     
/* 42 */     if (getY() > res.getScaledHeight() + 10 || getY() < -10) {
/* 43 */       setY(-10);
/*    */       
/* 45 */       Random rand = new Random();
/*    */       
/* 47 */       this._fallingSpeed = rand.nextInt(10) + 1;
/* 48 */       this._size = rand.nextInt(4) + 1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\particle\Snow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
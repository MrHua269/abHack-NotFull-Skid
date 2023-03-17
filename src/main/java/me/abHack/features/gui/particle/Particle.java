/*    */ package me.abHack.features.gui.particle;
/*    */ 
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import javax.vecmath.Tuple2f;
/*    */ import javax.vecmath.Vector2f;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.util.render.ColorUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class Particle {
/*    */   private final int maxAlpha;
/*    */   private final Vector2f velocity;
/*    */   private final Vector2f acceleration;
/*    */   private Vector2f pos;
/*    */   private int alpha;
/*    */   private float size;
/*    */   
/*    */   public Particle(Vector2f pos) {
/* 21 */     this.pos = pos;
/* 22 */     int lowVel = -1;
/* 23 */     int highVel = 1;
/* 24 */     float resultXVel = -1.0F + ThreadLocalRandom.current().nextFloat() * 2.0F;
/* 25 */     float resultYVel = -1.0F + ThreadLocalRandom.current().nextFloat() * 2.0F;
/* 26 */     this.velocity = new Vector2f(resultXVel, resultYVel);
/* 27 */     this.acceleration = new Vector2f(0.0F, 0.35F);
/* 28 */     this.alpha = 0;
/* 29 */     this.maxAlpha = ThreadLocalRandom.current().nextInt(32, 192);
/* 30 */     this.size = 0.5F + ThreadLocalRandom.current().nextFloat() * 1.5F;
/*    */   }
/*    */   
/*    */   public static int changeAlpha(int origColor, int userInputedAlpha) {
/* 34 */     origColor &= 0xFFFFFF;
/* 35 */     return userInputedAlpha << 24 | origColor;
/*    */   }
/*    */   
/*    */   public void respawn(ScaledResolution scaledResolution) {
/* 39 */     this.pos = new Vector2f((float)(Math.random() * scaledResolution.getScaledWidth()), (float)(Math.random() * scaledResolution.getScaledHeight()));
/*    */   }
/*    */   
/*    */   public void update() {
/* 43 */     if (this.alpha < this.maxAlpha) {
/* 44 */       this.alpha += 8;
/*    */     }
/* 46 */     if (this.acceleration.getX() > 0.35F) {
/* 47 */       this.acceleration.setX(this.acceleration.getX() * 0.975F);
/* 48 */     } else if (this.acceleration.getX() < -0.35F) {
/* 49 */       this.acceleration.setX(this.acceleration.getX() * 0.975F);
/*    */     } 
/* 51 */     if (this.acceleration.getY() > 0.35F) {
/* 52 */       this.acceleration.setY(this.acceleration.getY() * 0.975F);
/* 53 */     } else if (this.acceleration.getY() < -0.35F) {
/* 54 */       this.acceleration.setY(this.acceleration.getY() * 0.975F);
/*    */     } 
/* 56 */     this.pos.add((Tuple2f)this.acceleration);
/* 57 */     this.pos.add((Tuple2f)this.velocity);
/*    */   }
/*    */   
/*    */   public void render(int mouseX, int mouseY) {
/* 61 */     if (Mouse.isButtonDown(0)) {
/* 62 */       float deltaXToMouse = mouseX - this.pos.getX();
/* 63 */       float deltaYToMouse = mouseY - this.pos.getY();
/* 64 */       if (Math.abs(deltaXToMouse) < 50.0F && Math.abs(deltaYToMouse) < 50.0F) {
/* 65 */         this.acceleration.setX(this.acceleration.getX() - deltaXToMouse * 0.0015F);
/* 66 */         this.acceleration.setY(this.acceleration.getY() - deltaYToMouse * 0.0015F);
/*    */       } 
/*    */     } 
/* 69 */     RenderUtil.drawRect(this.pos.x, this.pos.y, this.pos.x + this.size, this.pos.y + this.size, changeAlpha(ColorUtil.toRGBA(((Integer)ClickGui.INSTANCE.particlered.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.particlegreen.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.particleblue.getValue()).intValue()), this.alpha));
/*    */   }
/*    */   
/*    */   public Vector2f getPos() {
/* 73 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void setPos(Vector2f pos) {
/* 77 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public int getAlpha() {
/* 81 */     return this.alpha;
/*    */   }
/*    */   
/*    */   public void setAlpha(int alpha) {
/* 85 */     this.alpha = alpha;
/*    */   }
/*    */   
/*    */   public float getSize() {
/* 89 */     return this.size;
/*    */   }
/*    */   
/*    */   public void setSize(float size) {
/* 93 */     this.size = size;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\particle\Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
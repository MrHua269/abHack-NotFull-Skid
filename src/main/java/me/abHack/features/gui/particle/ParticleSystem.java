/*    */ package me.abHack.features.gui.particle;
/*    */ 
/*    */ import javax.vecmath.Tuple2f;
/*    */ import javax.vecmath.Vector2f;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.util.render.ColorUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public final class ParticleSystem {
/*    */   private final Particle[] particles;
/*    */   private final ScaledResolution scaledResolution;
/*    */   
/*    */   public ParticleSystem(ScaledResolution scaledResolution) {
/* 15 */     this.particles = new Particle[200];
/* 16 */     this.scaledResolution = scaledResolution;
/* 17 */     for (int i = 0; i < 200; i++) {
/* 18 */       this.particles[i] = new Particle(new Vector2f((float)(Math.random() * scaledResolution.getScaledWidth()), (float)(Math.random() * scaledResolution.getScaledHeight())));
/*    */     }
/*    */   }
/*    */   
/*    */   public static double map(double value, double a, double b, double c, double d) {
/* 23 */     value = (value - a) / (b - a);
/* 24 */     return c + value * (d - c);
/*    */   }
/*    */   
/*    */   public void update() {
/* 28 */     for (int i = 0; i < 200; i++) {
/* 29 */       Particle particle = this.particles[i];
/* 30 */       if (this.scaledResolution != null) {
/* 31 */         boolean isOffScreenX = ((particle.getPos()).x > this.scaledResolution.getScaledWidth() || (particle.getPos()).x < 0.0F);
/* 32 */         boolean isOffScreenY = ((particle.getPos()).y > this.scaledResolution.getScaledHeight() || (particle.getPos()).y < 0.0F);
/* 33 */         if (isOffScreenX || isOffScreenY) {
/* 34 */           particle.respawn(this.scaledResolution);
/*    */         }
/*    */       } 
/* 37 */       particle.update();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void render(int mouseX, int mouseY) {
/* 42 */     if (!((Boolean)ClickGui.INSTANCE.particles.getValue()).booleanValue()) {
/*    */       return;
/*    */     }
/* 45 */     for (int i = 0; i < 200; i++) {
/* 46 */       Particle particle = this.particles[i];
/* 47 */       for (int j = 1; j < 200; j++) {
/* 48 */         if (i != j) {
/* 49 */           Particle otherParticle = this.particles[j];
/* 50 */           Vector2f diffPos = new Vector2f(particle.getPos());
/* 51 */           diffPos.sub((Tuple2f)otherParticle.getPos());
/* 52 */           float diff = diffPos.length();
/* 53 */           int distance = ((Integer)ClickGui.INSTANCE.particleLength.getValue()).intValue() / ((this.scaledResolution.getScaleFactor() <= 1) ? 3 : this.scaledResolution.getScaleFactor());
/* 54 */           if (diff < distance) {
/* 55 */             int lineAlpha = (int)map(diff, distance, 0.0D, 0.0D, 127.0D);
/* 56 */             if (lineAlpha > 8) {
/* 57 */               RenderUtil.drawLine((particle.getPos()).x + particle.getSize() / 2.0F, (particle.getPos()).y + particle.getSize() / 2.0F, (otherParticle.getPos()).x + otherParticle.getSize() / 2.0F, (otherParticle.getPos()).y + otherParticle.getSize() / 2.0F, 1.0F, Particle.changeAlpha(ColorUtil.toRGBA(((Boolean)ClickGui.INSTANCE.rbg.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRed() : ((Integer)ClickGui.INSTANCE.particlered.getValue()).intValue(), ((Boolean)ClickGui.INSTANCE.rbg.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getGreen() : ((Integer)ClickGui.INSTANCE.particlegreen.getValue()).intValue(), ((Boolean)ClickGui.INSTANCE.rbg.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getBlue() : ((Integer)ClickGui.INSTANCE.particleblue.getValue()).intValue()), lineAlpha));
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/* 62 */       particle.render(mouseX, mouseY);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\particle\ParticleSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
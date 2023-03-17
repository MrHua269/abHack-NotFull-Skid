/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ 
/*    */ public class Particles
/*    */   extends Module {
/*    */   public Particles() {
/*  8 */     super("Particles", "Display Particle.", Module.Category.RENDER, false, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 13 */     int x = (int)(Math.random() * 5.0D + 0.0D);
/* 14 */     int y = (int)(Math.random() * 3.0D + 1.0D);
/* 15 */     int z = (int)(Math.random() * 5.0D - 1.0D);
/*    */     
/* 17 */     int particleId = (int)(Math.random() * 47.0D + 1.0D);
/*    */     
/* 19 */     if (particleId != 1 && particleId != 2 && particleId != 41)
/* 20 */       mc.effectRenderer.spawnEffectParticle(particleId, mc.player.posX + 1.5D - x, mc.player.posY + y, mc.player.posZ + 1.5D - z, 0.0D, 0.5D, 0.0D, new int[] { 10 }); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Particles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
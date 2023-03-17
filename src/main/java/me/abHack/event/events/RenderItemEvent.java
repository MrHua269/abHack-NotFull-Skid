/*     */ package me.abHack.event.events;
/*     */ 
/*     */ import me.abHack.event.EventStage;
/*     */ 
/*     */ public class RenderItemEvent extends EventStage {
/*     */   double mainX;
/*     */   double mainY;
/*     */   double mainZ;
/*     */   double offX;
/*     */   double offY;
/*     */   double offZ;
/*     */   double mainRotX;
/*     */   double mainRotY;
/*     */   double mainRotZ;
/*     */   double offRotX;
/*     */   double offRotY;
/*     */   double offRotZ;
/*     */   double mainHandScaleX;
/*     */   double mainHandScaleY;
/*     */   double mainHandScaleZ;
/*     */   double offHandScaleX;
/*     */   double offHandScaleY;
/*     */   double offHandScaleZ;
/*     */   
/*     */   public RenderItemEvent(double mainX, double mainY, double mainZ, double offX, double offY, double offZ, double mainRotX, double mainRotY, double mainRotZ, double offRotX, double offRotY, double offRotZ, double mainHandScaleX, double mainHandScaleY, double mainHandScaleZ, double offHandScaleX, double offHandScaleY, double offHandScaleZ) {
/*  26 */     this.mainX = mainX;
/*  27 */     this.mainY = mainY;
/*  28 */     this.mainZ = mainZ;
/*  29 */     this.offX = offX;
/*  30 */     this.offY = offY;
/*  31 */     this.offZ = offZ;
/*  32 */     this.mainRotX = mainRotX;
/*  33 */     this.mainRotY = mainRotY;
/*  34 */     this.mainRotZ = mainRotZ;
/*  35 */     this.offRotX = offRotX;
/*  36 */     this.offRotY = offRotY;
/*  37 */     this.offRotZ = offRotZ;
/*  38 */     this.mainHandScaleX = mainHandScaleX;
/*  39 */     this.mainHandScaleY = mainHandScaleY;
/*  40 */     this.mainHandScaleZ = mainHandScaleZ;
/*  41 */     this.offHandScaleX = offHandScaleX;
/*  42 */     this.offHandScaleY = offHandScaleY;
/*  43 */     this.offHandScaleZ = offHandScaleZ;
/*     */   }
/*     */   
/*     */   public double getMainX() {
/*  47 */     return this.mainX;
/*     */   }
/*     */   
/*     */   public void setMainX(double v) {
/*  51 */     this.mainX = v;
/*     */   }
/*     */   
/*     */   public double getMainY() {
/*  55 */     return this.mainY;
/*     */   }
/*     */   
/*     */   public void setMainY(double v) {
/*  59 */     this.mainY = v;
/*     */   }
/*     */   
/*     */   public double getMainZ() {
/*  63 */     return this.mainZ;
/*     */   }
/*     */   
/*     */   public void setMainZ(double v) {
/*  67 */     this.mainZ = v;
/*     */   }
/*     */   
/*     */   public double getOffX() {
/*  71 */     return this.offX;
/*     */   }
/*     */   
/*     */   public void setOffX(double v) {
/*  75 */     this.offX = v;
/*     */   }
/*     */   
/*     */   public double getOffY() {
/*  79 */     return this.offY;
/*     */   }
/*     */   
/*     */   public void setOffY(double v) {
/*  83 */     this.offY = v;
/*     */   }
/*     */   
/*     */   public double getOffZ() {
/*  87 */     return this.offZ;
/*     */   }
/*     */   
/*     */   public void setOffZ(double v) {
/*  91 */     this.offZ = v;
/*     */   }
/*     */   
/*     */   public double getMainRotX() {
/*  95 */     return this.mainRotX;
/*     */   }
/*     */   
/*     */   public void setMainRotX(double v) {
/*  99 */     this.mainRotX = v;
/*     */   }
/*     */   
/*     */   public double getMainRotY() {
/* 103 */     return this.mainRotY;
/*     */   }
/*     */   
/*     */   public void setMainRotY(double v) {
/* 107 */     this.mainRotY = v;
/*     */   }
/*     */   
/*     */   public double getMainRotZ() {
/* 111 */     return this.mainRotZ;
/*     */   }
/*     */   
/*     */   public void setMainRotZ(double v) {
/* 115 */     this.mainRotZ = v;
/*     */   }
/*     */   
/*     */   public double getOffRotX() {
/* 119 */     return this.offRotX;
/*     */   }
/*     */   
/*     */   public void setOffRotX(double v) {
/* 123 */     this.offRotX = v;
/*     */   }
/*     */   
/*     */   public double getOffRotY() {
/* 127 */     return this.offRotY;
/*     */   }
/*     */   
/*     */   public void setOffRotY(double v) {
/* 131 */     this.offRotY = v;
/*     */   }
/*     */   
/*     */   public double getOffRotZ() {
/* 135 */     return this.offRotZ;
/*     */   }
/*     */   
/*     */   public void setOffRotZ(double v) {
/* 139 */     this.offRotZ = v;
/*     */   }
/*     */   
/*     */   public double getMainHandScaleX() {
/* 143 */     return this.mainHandScaleX;
/*     */   }
/*     */   
/*     */   public void setMainHandScaleX(double v) {
/* 147 */     this.mainHandScaleX = v;
/*     */   }
/*     */   
/*     */   public double getMainHandScaleY() {
/* 151 */     return this.mainHandScaleY;
/*     */   }
/*     */   
/*     */   public void setMainHandScaleY(double v) {
/* 155 */     this.mainHandScaleY = v;
/*     */   }
/*     */   
/*     */   public double getMainHandScaleZ() {
/* 159 */     return this.mainHandScaleZ;
/*     */   }
/*     */   
/*     */   public void setMainHandScaleZ(double v) {
/* 163 */     this.mainHandScaleZ = v;
/*     */   }
/*     */   
/*     */   public double getOffHandScaleX() {
/* 167 */     return this.offHandScaleX;
/*     */   }
/*     */   
/*     */   public void setOffHandScaleX(double v) {
/* 171 */     this.offHandScaleX = v;
/*     */   }
/*     */   
/*     */   public double getOffHandScaleY() {
/* 175 */     return this.offHandScaleY;
/*     */   }
/*     */   
/*     */   public void setOffHandScaleY(double v) {
/* 179 */     this.offHandScaleY = v;
/*     */   }
/*     */   
/*     */   public double getOffHandScaleZ() {
/* 183 */     return this.offHandScaleZ;
/*     */   }
/*     */   
/*     */   public void setOffHandScaleZ(double v) {
/* 187 */     this.offHandScaleZ = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\RenderItemEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
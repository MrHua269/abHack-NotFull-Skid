/*     */ package me.abHack.util.render.Trails;
/*     */ 
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimeAnimation
/*     */ {
/*     */   private final long length;
/*     */   private final double start;
/*     */   private final double end;
/*     */   private double current;
/*     */   private double progress;
/*     */   private boolean playing;
/*     */   private boolean backwards;
/*     */   private boolean reverseOnEnd;
/*     */   private final long startTime;
/*     */   private long lastTime;
/*     */   private double per;
/*     */   private final long dif;
/*     */   private boolean flag;
/*     */   private AnimationMode mode;
/*     */   
/*     */   public TimeAnimation(long length, double start, double end, boolean backwards, AnimationMode mode) {
/*     */     double dif;
/*     */     int i;
/*  31 */     this.length = length;
/*  32 */     this.start = start;
/*  33 */     this.current = start;
/*  34 */     this.end = end;
/*  35 */     this.mode = mode;
/*  36 */     this.backwards = backwards;
/*  37 */     this.startTime = System.currentTimeMillis();
/*  38 */     this.playing = true;
/*  39 */     this.dif = System.currentTimeMillis() - this.startTime;
/*  40 */     switch (mode) {
/*     */       case LINEAR:
/*  42 */         this.per = (end - start) / length;
/*     */         break;
/*     */       case EXPONENTIAL:
/*  45 */         dif = end - start;
/*  46 */         this.flag = (dif < 0.0D);
/*  47 */         if (this.flag) dif *= -1.0D; 
/*  48 */         for (i = 0; i < length; i++) {
/*  49 */           dif = Math.sqrt(dif);
/*     */         }
/*  51 */         this.per = dif;
/*     */         break;
/*     */     } 
/*  54 */     this.lastTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public TimeAnimation(long length, double start, double end, boolean backwards, boolean reverseOnEnd, AnimationMode mode) {
/*  58 */     this(length, start, end, backwards, mode);
/*  59 */     this.reverseOnEnd = reverseOnEnd;
/*     */   }
/*     */   
/*     */   public void add(float partialTicks) {
/*  63 */     if (this.playing) {
/*  64 */       if (this.mode == AnimationMode.LINEAR) {
/*  65 */         this.current = this.start + this.progress;
/*     */         
/*  67 */         this.progress += this.per * (System.currentTimeMillis() - this.lastTime);
/*     */       
/*     */       }
/*  70 */       else if (this.mode == AnimationMode.EXPONENTIAL) {
/*     */       
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       this.current = MathHelper.clamp(this.current, this.start, this.end);
/*  79 */       if (this.current >= this.end || (this.backwards && this.current <= this.start)) {
/*  80 */         if (this.reverseOnEnd) {
/*  81 */           reverse();
/*  82 */           this.reverseOnEnd = false;
/*     */         } else {
/*  84 */           this.playing = false;
/*     */         } 
/*     */       }
/*     */     } 
/*  88 */     this.lastTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public long getLength() {
/*  92 */     return this.length;
/*     */   }
/*     */   
/*     */   public double getStart() {
/*  96 */     return this.start;
/*     */   }
/*     */   
/*     */   public double getEnd() {
/* 100 */     return this.end;
/*     */   }
/*     */   
/*     */   public double getCurrent() {
/* 104 */     return this.current;
/*     */   }
/*     */   
/*     */   public void setCurrent(double current) {
/* 108 */     this.current = current;
/*     */   }
/*     */   
/*     */   public AnimationMode getMode() {
/* 112 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(AnimationMode mode) {
/* 116 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public boolean isPlaying() {
/* 120 */     return this.playing;
/*     */   }
/*     */   
/*     */   public void play() {
/* 124 */     this.playing = true;
/*     */   }
/*     */   
/*     */   public void stop() {
/* 128 */     this.playing = false;
/*     */   }
/*     */   
/*     */   public boolean isBackwards() {
/* 132 */     return this.backwards;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reverse() {
/* 140 */     this.backwards = !this.backwards;
/* 141 */     this.per *= -1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\Trails\TimeAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
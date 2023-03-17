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
/*     */ public class Animation
/*     */ {
/*     */   private double start;
/*     */   private double end;
/*     */   private double speed;
/*     */   private double current;
/*     */   private double last;
/*  18 */   private double progress = 0.0D;
/*     */   
/*     */   private boolean backwards;
/*     */   
/*     */   private boolean reverseOnEnd;
/*     */   private boolean playing;
/*     */   private AnimationMode mode;
/*     */   
/*     */   public Animation(double start, double end, double speed, boolean backwards, AnimationMode mode) {
/*  27 */     this.start = start;
/*  28 */     this.end = end;
/*  29 */     this.speed = speed;
/*  30 */     this.backwards = backwards;
/*  31 */     this.current = start;
/*  32 */     this.last = start;
/*  33 */     this.mode = mode;
/*  34 */     this.playing = true;
/*     */   }
/*     */   
/*     */   public Animation(double start, double end, double speed, boolean backwards, boolean reverseOnEnd, AnimationMode mode) {
/*  38 */     this.start = start;
/*  39 */     this.end = end;
/*  40 */     this.speed = speed;
/*  41 */     this.backwards = backwards;
/*  42 */     this.reverseOnEnd = reverseOnEnd;
/*  43 */     this.current = start;
/*  44 */     this.last = start;
/*  45 */     this.mode = mode;
/*  46 */     this.playing = true;
/*     */   }
/*     */   
/*     */   public void add(float partialTicks) {
/*  50 */     if (this.playing) {
/*  51 */       this.last = this.current;
/*  52 */       if (this.mode == AnimationMode.LINEAR) {
/*  53 */         this.current += (this.backwards ? -1 : 1) * this.speed;
/*  54 */       } else if (this.mode == AnimationMode.EXPONENTIAL) {
/*  55 */         for (int i = 0; i < 1.0F / partialTicks; i++) {
/*  56 */           this.current += this.speed;
/*  57 */           this.speed *= this.speed;
/*  58 */           if (this.speed > 0.0D && this.backwards) {
/*  59 */             this.speed *= -1.0D;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  68 */       this.current = MathHelper.clamp(this.current, this.start, this.end);
/*  69 */       if (this.current >= this.end) {
/*  70 */         if (this.reverseOnEnd) {
/*  71 */           this.backwards = !this.backwards;
/*     */         } else {
/*  73 */           this.playing = false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStart() {
/*  99 */     return this.start;
/*     */   }
/*     */   
/*     */   public void setStart(double start) {
/* 103 */     this.start = start;
/*     */   }
/*     */   
/*     */   public double getEnd() {
/* 107 */     return this.end;
/*     */   }
/*     */   
/*     */   public void setEnd(double end) {
/* 111 */     this.end = end;
/*     */   }
/*     */   
/*     */   public double getSpeed() {
/* 115 */     return this.speed;
/*     */   }
/*     */   
/*     */   public void setSpeed(double speed) {
/* 119 */     this.speed = speed;
/*     */   }
/*     */   
/*     */   public double getCurrent() {
/* 123 */     return this.current;
/*     */   }
/*     */   
/*     */   public void setCurrent(double current) {
/* 127 */     this.current = current;
/*     */   }
/*     */   
/*     */   public double getCurrent(float partialTicks) {
/* 131 */     return this.playing ? (this.last + (this.current - this.last) * partialTicks) : this.current;
/*     */   }
/*     */   
/*     */   public AnimationMode getMode() {
/* 135 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(AnimationMode mode) {
/* 139 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public boolean isPlaying() {
/* 143 */     return this.playing;
/*     */   }
/*     */   
/*     */   public void play() {
/* 147 */     this.playing = true;
/*     */   }
/*     */   
/*     */   public void stop() {
/* 151 */     this.playing = false;
/*     */   }
/*     */   
/*     */   public boolean isBackwards() {
/* 155 */     return this.backwards;
/*     */   }
/*     */   
/*     */   public void setBackwards(boolean backwards) {
/* 159 */     this.backwards = backwards;
/*     */   }
/*     */   
/*     */   public boolean isReverseOnEnd() {
/* 163 */     return this.reverseOnEnd;
/*     */   }
/*     */   
/*     */   public void setReverseOnEnd(boolean reverseOnEnd) {
/* 167 */     this.reverseOnEnd = reverseOnEnd;
/*     */   }
/*     */   
/*     */   public double getProgress() {
/* 171 */     return this.progress;
/*     */   }
/*     */   
/*     */   public void setProgress(double progress) {
/* 175 */     this.progress = progress;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\Trails\Animation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
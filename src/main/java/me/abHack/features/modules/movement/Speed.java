/*     */ package me.abHack.features.modules.movement;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Objects;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.MoveEvent;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.event.events.UpdateWalkingPlayerEvent;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.SPacketExplosion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Speed extends Module {
/*  25 */   public static Speed INSTANCE = new Speed();
/*     */ 
/*     */   
/*  28 */   public Setting<Boolean> damageBoost = register(new Setting("DamageBoost", Boolean.valueOf(true)));
/*     */   
/*  30 */   public Setting<Integer> boostDelay = register(new Setting("BoostDelay", Integer.valueOf(750), Integer.valueOf(1), Integer.valueOf(3000), v -> ((Boolean)this.damageBoost.getValue()).booleanValue()));
/*     */   
/*  32 */   public Setting<Boolean> strictBoost = register(new Setting("StrictBoost", Boolean.valueOf(false), v -> ((Boolean)this.damageBoost.getValue()).booleanValue()));
/*     */   
/*  34 */   public Setting<Boolean> longjump = register(new Setting("TryLongJump", Boolean.valueOf(false)));
/*     */   
/*  36 */   public Setting<Double> lagCoolDown = register(new Setting("LagCoolDown", Double.valueOf(2.2D), Double.valueOf(1.0D), Double.valueOf(8.0D), v -> ((Boolean)this.longjump.getValue()).booleanValue()));
/*     */   
/*  38 */   public Setting<Integer> jumpStage = register(new Setting("JumpStage", Integer.valueOf(6), Integer.valueOf(1), Integer.valueOf(20), v -> ((Boolean)this.longjump.getValue()).booleanValue()));
/*     */   
/*  40 */   public Setting<Integer> jumpSec = register(new Setting("JumpCoolDown", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(10), v -> ((Boolean)this.longjump.getValue()).booleanValue()));
/*     */   
/*  42 */   public Setting<Boolean> motionJump = register(new Setting("MotionJump", Boolean.valueOf(false), v -> ((Boolean)this.longjump.getValue()).booleanValue()));
/*     */   
/*  44 */   public Setting<Boolean> lavaBoost = register(new Setting("LavaBoost", Boolean.valueOf(true)));
/*     */   
/*  46 */   public Setting<Boolean> potion = register(new Setting("Potion", Boolean.valueOf(true)));
/*     */   
/*  48 */   public Setting<Boolean> step = register(new Setting("SetStep", Boolean.valueOf(true)));
/*     */   
/*  50 */   public Setting<Boolean> SpeedInWater = register(new Setting("SpeedInWater", Boolean.valueOf(true)));
/*     */   
/*  52 */   public Setting<Boolean> bbtt = register(new Setting("2b2t", Boolean.valueOf(false)));
/*     */   
/*  54 */   public Setting<Boolean> useTimer = register(new Setting("UseTimer", Boolean.valueOf(true)));
/*     */   
/*  56 */   public Timer lagBackCoolDown = new Timer();
/*     */   
/*  58 */   public Timer boostTimer = new Timer();
/*     */   
/*  60 */   public Timer jumpDelay = new Timer();
/*     */   
/*     */   public boolean inCoolDown = false;
/*     */   
/*     */   public boolean checkCoolDown = false;
/*     */   
/*  66 */   public int readyStage = 0;
/*     */   
/*     */   public boolean warn = false;
/*     */   
/*     */   public double boostSpeed;
/*     */   
/*     */   public double boostSpeed2;
/*     */   
/*     */   public Vec3d boostRangeVec;
/*     */   
/*     */   public double lastDist;
/*     */   
/*  78 */   public int stage = 1;
/*     */   
/*  80 */   public int level = 1;
/*     */   
/*     */   public double moveSpeed;
/*     */ 
/*     */   
/*     */   public Speed() {
/*  86 */     super("Speed", "lol", Module.Category.MOVEMENT, true, false, false);
/*  87 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public static double round(double n, int n2) {
/*  91 */     if (n2 < 0)
/*  92 */       throw new IllegalArgumentException(); 
/*  93 */     return (new BigDecimal(n)).setScale(n2, RoundingMode.HALF_UP).doubleValue();
/*     */   }
/*     */   
/*     */   public static boolean isMoving() {
/*  97 */     return (mc.player.moveForward != 0.0D || mc.player.moveStrafing != 0.0D);
/*     */   }
/*     */   
/*     */   public static void motionJump() {
/* 101 */     if (!mc.player.collidedVertically) {
/* 102 */       if (mc.player.motionY == -0.07190068807140403D) {
/* 103 */         mc.player.motionY *= 0.3499999940395355D;
/* 104 */       } else if (mc.player.motionY == -0.10306193759436909D) {
/* 105 */         mc.player.motionY *= 0.550000011920929D;
/* 106 */       } else if (mc.player.motionY == -0.13395038817442878D) {
/* 107 */         mc.player.motionY *= 0.6700000166893005D;
/* 108 */       } else if (mc.player.motionY == -0.16635183030382D) {
/* 109 */         mc.player.motionY *= 0.6899999976158142D;
/* 110 */       } else if (mc.player.motionY == -0.19088711097794803D) {
/* 111 */         mc.player.motionY *= 0.7099999785423279D;
/* 112 */       } else if (mc.player.motionY == -0.21121925191528862D) {
/* 113 */         mc.player.motionY *= 0.20000000298023224D;
/*     */       } 
/* 115 */       if (mc.player.motionY < -0.2D && mc.player.motionY > -0.24D)
/* 116 */         mc.player.motionY *= 0.7D; 
/* 117 */       if (mc.player.motionY < -0.25D && mc.player.motionY > -0.32D)
/* 118 */         mc.player.motionY *= 0.8D; 
/* 119 */       if (mc.player.motionY < -0.35D && mc.player.motionY > -0.8D)
/* 120 */         mc.player.motionY *= 0.98D; 
/* 121 */       if (mc.player.motionY < -0.8D && mc.player.motionY > -1.6D)
/* 122 */         mc.player.motionY *= 0.99D; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed() {
/* 127 */     double n = 0.2873D;
/* 128 */     if (mc.player.isPotionActive(MobEffects.SPEED) && ((Boolean)this.potion.getValue()).booleanValue())
/* 129 */       n *= 1.0D + 0.2D * (((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED))).getAmplifier() + 1); 
/* 130 */     return n;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 134 */     if (fullNullCheck())
/*     */       return; 
/* 136 */     if (HoleSnap.INSTANCE.isOn())
/*     */       return; 
/* 138 */     if (this.lagBackCoolDown.passedMs((long)(((Double)this.lagCoolDown.getValue()).doubleValue() * 1000.0D))) {
/* 139 */       this.checkCoolDown = false;
/* 140 */       this.inCoolDown = false;
/* 141 */       this.lagBackCoolDown.reset();
/*     */     } 
/* 143 */     if (((Boolean)this.useTimer.getValue()).booleanValue())
/* 144 */       mc.timer.tickLength = me.abHack.features.modules.misc.Timer.INSTANCE.isOn() ? (50.0F / ((Float) me.abHack.features.modules.misc.Timer.INSTANCE.timer.getValue()).floatValue()) : 45.955883F;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMotion(UpdateWalkingPlayerEvent event) {
/* 149 */     if (fullNullCheck())
/*     */       return; 
/* 151 */     if (HoleSnap.INSTANCE.isOn())
/*     */       return; 
/* 153 */     if (event.getStage() == 1)
/* 154 */       this.lastDist = Math.sqrt((mc.player.posX - mc.player.prevPosX) * (mc.player.posX - mc.player.prevPosX) + (mc.player.posZ - mc.player.prevPosZ) * (mc.player.posZ - mc.player.prevPosZ)); 
/*     */   }
/*     */   
/*     */   public void onEnable() {
/* 158 */     this.boostSpeed = 0.0D;
/* 159 */     this.boostSpeed2 = 0.0D;
/* 160 */     this.jumpDelay.reset();
/* 161 */     this.lagBackCoolDown.reset();
/* 162 */     this.boostTimer.reset();
/* 163 */     this.readyStage = 0;
/* 164 */     this.warn = false;
/* 165 */     this.moveSpeed = getBaseMoveSpeed();
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 170 */     if (fullNullCheck())
/*     */       return; 
/* 172 */     if (HoleSnap.INSTANCE.isOn())
/*     */       return; 
/* 174 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/* 175 */       this.lastDist = 0.0D;
/* 176 */       this.moveSpeed = getBaseMoveSpeed();
/* 177 */       this.stage = 2;
/* 178 */       if (((Boolean)this.longjump.getValue()).booleanValue()) {
/* 179 */         this.readyStage = 0;
/* 180 */         this.inCoolDown = true;
/* 181 */         if (!this.checkCoolDown) {
/* 182 */           this.lagBackCoolDown.reset();
/* 183 */           this.checkCoolDown = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 187 */     if (event.getPacket() instanceof SPacketExplosion)
/* 188 */       this.boostRangeVec = new Vec3d(((SPacketExplosion)event.getPacket()).posX, ((SPacketExplosion)event.getPacket()).posY, ((SPacketExplosion)event.getPacket()).posZ); 
/* 189 */     if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event
/* 190 */       .getPacket()).getEntityID() == mc.player.getEntityId()) {
/* 191 */       if (((Boolean)this.longjump.getValue()).booleanValue())
/* 192 */         this.readyStage++; 
/* 193 */       this.boostSpeed = Math.hypot((((SPacketEntityVelocity)event.getPacket()).motionX / 8000.0F), (((SPacketEntityVelocity)event.getPacket()).motionZ / 8000.0F));
/* 194 */       this.boostSpeed2 = this.boostSpeed;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void move(MoveEvent event) {
/* 200 */     if (fullNullCheck())
/*     */       return; 
/* 202 */     if (HoleSnap.INSTANCE.isOn())
/*     */       return; 
/* 204 */     if (!((Boolean)this.SpeedInWater.getValue()).booleanValue() && 
/* 205 */       shouldReturn())
/*     */       return; 
/* 207 */     mc.player.stepHeight = OyVey.moduleManager.isModuleEnabled("Step") ? ((Double)(Step.getInstance()).height.getValue()).floatValue() : 0.0F;
/* 208 */     if (!((Boolean)this.SpeedInWater.getValue()).booleanValue() && 
/* 209 */       shouldReturn())
/*     */       return; 
/* 211 */     if (mc.player.onGround)
/* 212 */       this.level = 2; 
/* 213 */     if (((Boolean)this.step.getValue()).booleanValue())
/* 214 */       mc.player.stepHeight = OyVey.moduleManager.isModuleEnabled("Step") ? ((Double)(Step.getInstance()).height.getValue()).floatValue() : 0.6F; 
/* 215 */     if (round(mc.player.posY - (int)mc.player.posY, 3) == round(0.138D, 3)) {
/* 216 */       mc.player.motionY -= 0.07D;
/* 217 */       event.y -= 0.08316090325960147D;
/* 218 */       mc.player.posY -= 0.08316090325960147D;
/*     */     } 
/* 220 */     if (this.level != 1 || (mc.player.moveForward == 0.0F && mc.player.moveStrafing == 0.0F)) {
/* 221 */       if (this.level == 2) {
/* 222 */         this.level = 3;
/* 223 */         if (isMoving()) {
/* 224 */           if (!mc.player.isInLava() && mc.player.onGround) {
/* 225 */             mc.player.motionY = 0.4D;
/* 226 */             event.setY(0.4D);
/*     */           } 
/* 228 */           if (((Boolean)this.bbtt.getValue()).booleanValue()) {
/* 229 */             this.moveSpeed *= 1.433D;
/* 230 */           } else if (!mc.player.isSneaking()) {
/* 231 */             this.moveSpeed *= 1.7103D;
/*     */           } else {
/* 233 */             this.moveSpeed *= 1.433D;
/*     */           } 
/*     */         } 
/* 236 */       } else if (this.level == 3) {
/* 237 */         this.level = 4;
/* 238 */         this.moveSpeed = this.lastDist - 0.6553D * (this.lastDist - getBaseMoveSpeed() + 0.04D);
/*     */       } else {
/* 240 */         if (mc.player.onGround && (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.boundingBox.offset(0.0D, mc.player.motionY, 0.0D)).size() > 0 || mc.player.collidedVertically))
/* 241 */           this.level = 1; 
/* 242 */         this.moveSpeed = this.lastDist - this.lastDist / 201.0D;
/*     */       } 
/*     */     } else {
/* 245 */       this.level = 2;
/* 246 */       this.moveSpeed = 1.418D * getBaseMoveSpeed();
/*     */     } 
/* 248 */     if (((Boolean)this.damageBoost.getValue()).booleanValue() && isMoving() && this.boostSpeed2 != 0.0D) {
/*     */       
/* 250 */       if (this.boostTimer.passedMs(((Integer)this.boostDelay.getValue()).intValue())) {
/* 251 */         if (((Boolean)this.bbtt.getValue()).booleanValue()) {
/* 252 */           if (((Boolean)this.strictBoost.getValue()).booleanValue()) {
/* 253 */             this.moveSpeed = Math.max((this.moveSpeed + 0.10000000149011612D) / 1.5D, getBaseMoveSpeed());
/*     */           } else {
/* 255 */             this.moveSpeed = this.boostSpeed2;
/*     */           } 
/*     */         } else {
/* 258 */           this.moveSpeed = this.boostSpeed2;
/*     */         } 
/* 260 */         this.boostTimer.reset();
/*     */       } 
/* 262 */       this.boostSpeed2 = 0.0D;
/*     */     } 
/* 264 */     if (((Boolean)this.longjump.getValue()).booleanValue() && this.readyStage > 0 && this.readyStage >= ((Integer)this.jumpStage.getValue()).intValue() && !this.inCoolDown && this.jumpDelay.passedMs((long)(((Integer)this.jumpSec.getValue()).intValue() / 1000.0F)) && isMoving()) {
/* 265 */       if (!((Boolean)this.motionJump.getValue()).booleanValue()) {
/* 266 */         this.moveSpeed *= (((Integer)this.jumpStage.getValue()).intValue() / 10.0F);
/*     */       } else {
/* 268 */         double v = Math.abs(this.moveSpeed - this.boostSpeed);
/* 269 */         motionJump();
/* 270 */         mc.player.motionY *= 1.02D;
/* 271 */         mc.player.motionY *= 1.13D;
/* 272 */         mc.player.motionY *= 1.27D;
/* 273 */         this.moveSpeed += v;
/*     */       } 
/* 275 */       Command.sendMessage("Boost");
/* 276 */       this.jumpDelay.reset();
/* 277 */       this.readyStage = 0;
/*     */     } 
/* 279 */     this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
/* 280 */     mc.player.stepHeight = OyVey.moduleManager.isModuleEnabled("Step") ? ((Double)(Step.getInstance()).height.getValue()).floatValue() : 0.6F;
/* 281 */     if (!mc.player.isInLava()) {
/* 282 */       event.setSpeed(this.moveSpeed);
/* 283 */     } else if (((Boolean)this.lavaBoost.getValue()).booleanValue() && mc.player.isInLava()) {
/* 284 */       event.setX(event.getX() * 3.1D);
/* 285 */       event.setZ(event.getZ() * 3.1D);
/* 286 */       if (mc.gameSettings.keyBindJump.isKeyDown())
/* 287 */         event.setY(event.getY() * 3.0D); 
/*     */     } 
/* 289 */     if (mc.player.movementInput.moveForward == 0.0F && mc.player.movementInput.moveStrafe == 0.0F) {
/* 290 */       event.x = 0.0D;
/* 291 */       event.z = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReturn() {
/* 297 */     return (mc.player.isInLava() || mc.player.isInWater() || isDisabled() || mc.player.isInWeb);
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 301 */     this.moveSpeed = 0.0D;
/* 302 */     this.stage = 2;
/* 303 */     if (mc.player != null) {
/* 304 */       mc.player.stepHeight = 0.6F;
/* 305 */       mc.timer.tickLength = 50.0F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\Speed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.movement;
/*     */ import java.util.Objects;
/*     */ import me.abHack.event.events.MoveEvent;
/*     */ import me.abHack.event.events.UpdateWalkingPlayerEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.player.Freecam;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Strafe extends Module {
/*  14 */   private static Strafe INSTANCE = new Strafe();
/*  15 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.NORMAL));
/*     */   int stage;
/*     */   private double lastDist;
/*     */   private double moveSpeed;
/*     */   
/*     */   public Strafe() {
/*  21 */     super("Strafe", "Modifies sprinting", Module.Category.MOVEMENT, true, false, false);
/*  22 */     setInstance();
/*     */   }
/*     */   
/*     */   public static Strafe getInstance() {
/*  26 */     if (INSTANCE == null)
/*  27 */       INSTANCE = new Strafe(); 
/*  28 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  32 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
/*  37 */     if (event.getStage() == 1 && fullNullCheck())
/*     */       return; 
/*  39 */     if (Freecam.INSTANCE.isOn())
/*     */       return; 
/*  41 */     this.lastDist = Math.sqrt((mc.player.posX - mc.player.prevPosX) * (mc.player.posX - mc.player.prevPosX) + (mc.player.posZ - mc.player.prevPosZ) * (mc.player.posZ - mc.player.prevPosZ));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onStrafe(MoveEvent event) {
/*  46 */     if (fullNullCheck())
/*     */       return; 
/*  48 */     if (Freecam.INSTANCE.isOn() || HoleSnap.INSTANCE.isOn())
/*     */       return; 
/*  50 */     if (!mc.player.isInWater() && !mc.player.isInLava()) {
/*     */       double motionY;
/*  52 */       if (mc.player.onGround)
/*  53 */         this.stage = 2; 
/*  54 */       switch (this.stage) {
/*     */         case 0:
/*  56 */           this.stage++;
/*  57 */           this.lastDist = 0.0D;
/*     */           break;
/*     */         case 2:
/*  60 */           motionY = 0.40123128D;
/*  61 */           if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
/*  62 */             if (mc.player.isPotionActive(MobEffects.JUMP_BOOST))
/*  63 */               motionY += ((((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST))).getAmplifier() + 1) * 0.1F); 
/*  64 */             event.setY(mc.player.motionY = motionY);
/*  65 */             this.moveSpeed *= (this.mode.getValue() == Mode.NORMAL) ? 1.67D : 2.149D;
/*     */           } 
/*     */           break;
/*     */         case 3:
/*  69 */           this.moveSpeed = this.lastDist - ((this.mode.getValue() == Mode.NORMAL) ? 0.6896D : 0.795D) * (this.lastDist - getBaseMoveSpeed());
/*     */           break;
/*     */         default:
/*  72 */           if ((mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(0.0D, mc.player.motionY, 0.0D)).size() > 0 || mc.player.collidedVertically) && this.stage > 0)
/*  73 */             this.stage = (mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) ? 1 : 0; 
/*  74 */           this.moveSpeed = this.lastDist - this.lastDist / ((this.mode.getValue() == Mode.NORMAL) ? 730.0D : 159.0D);
/*     */           break;
/*     */       } 
/*  77 */       if (!mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
/*  78 */         this.moveSpeed = getBaseMoveSpeed();
/*     */       } else {
/*  80 */         this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
/*     */       } 
/*  82 */       double n = mc.player.movementInput.moveForward;
/*  83 */       double n2 = mc.player.movementInput.moveStrafe;
/*  84 */       double n3 = mc.player.rotationYaw;
/*  85 */       if (n == 0.0D && n2 == 0.0D) {
/*  86 */         event.setX(0.0D);
/*  87 */         event.setZ(0.0D);
/*  88 */       } else if (n != 0.0D && n2 != 0.0D) {
/*  89 */         n *= Math.sin(0.7853981633974483D);
/*  90 */         n2 *= Math.cos(0.7853981633974483D);
/*     */       } 
/*  92 */       double n4 = (this.mode.getValue() == Mode.NORMAL) ? 0.993D : 0.99D;
/*  93 */       event.setX((n * this.moveSpeed * -Math.sin(Math.toRadians(n3)) + n2 * this.moveSpeed * Math.cos(Math.toRadians(n3))) * n4);
/*  94 */       event.setZ((n * this.moveSpeed * Math.cos(Math.toRadians(n3)) - n2 * this.moveSpeed * -Math.sin(Math.toRadians(n3))) * n4);
/*  95 */       this.stage++;
/*  96 */       event.setCanceled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed() {
/* 101 */     double n = 0.2873D;
/* 102 */     if (mc.player.isPotionActive(MobEffects.SPEED))
/* 103 */       n *= 1.0D + 0.2D * (((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED))).getAmplifier() + 1); 
/* 104 */     return n;
/*     */   }
/*     */   
/*     */   public String getDisplayInfo() {
/* 108 */     return this.mode.currentEnumName();
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 112 */     NORMAL, Strict;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\Strafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
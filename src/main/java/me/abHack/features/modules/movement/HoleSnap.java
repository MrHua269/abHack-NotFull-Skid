/*     */ package me.abHack.features.modules.movement;
/*     */ 
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.HoleUtil;
/*     */ import me.abHack.util.RotationUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class HoleSnap extends Module {
/*  17 */   public static HoleSnap INSTANCE = new HoleSnap();
/*     */   public Setting<Float> range;
/*     */   Setting<Float> factor;
/*     */   Setting<Boolean> step;
/*     */   Setting<Float> height;
/*     */   Timer timer;
/*     */   HoleUtil.Hole holes;
/*     */   
/*     */   public HoleSnap() {
/*  26 */     super("HoleSnap", "drags u to the nearest hole", Module.Category.MOVEMENT, true, false, false);
/*  27 */     this.range = register(new Setting("Range", Float.valueOf(5.0F), Float.valueOf(0.1F), Float.valueOf(6.0F)));
/*  28 */     this.factor = register(new Setting("Factor", Float.valueOf(5.0F), Float.valueOf(0.1F), Float.valueOf(15.0F)));
/*  29 */     this.step = register(new Setting("Step", Boolean.valueOf(true)));
/*  30 */     this.height = register(new Setting("Height", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(2.0F), v -> ((Boolean)this.step.getValue()).booleanValue()));
/*  31 */     this.timer = new Timer();
/*  32 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public static void step(float height) {
/*  36 */     if (!mc.player.collidedVertically || mc.player.fallDistance > 0.1D || mc.player.isOnLadder() || !mc.player.onGround)
/*     */       return; 
/*  38 */     mc.player.stepHeight = height;
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  42 */     if (fullNullCheck())
/*     */       return; 
/*  44 */     this.timer.reset();
/*  45 */     this.holes = null;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  49 */     if (fullNullCheck())
/*     */       return; 
/*  51 */     this.timer.reset();
/*  52 */     this.holes = null;
/*  53 */     if (((Boolean)this.step.getValue()).booleanValue())
/*  54 */       mc.player.stepHeight = 0.6F; 
/*  55 */     if (mc.timer.tickLength != 50.0F)
/*     */     {
/*  57 */       mc.timer.tickLength = 50.0F; } 
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  61 */     if (fullNullCheck())
/*     */       return; 
/*  63 */     if (EntityUtil.isInLiquid()) {
/*  64 */       disable();
/*     */       return;
/*     */     } 
/*  67 */     this.holes = RotationUtil.getTargetHoleVec3D(((Float)this.range.getValue()).floatValue());
/*  68 */     if (this.holes == null) {
/*  69 */       Command.sendMessage("Unable to find hole, disabling HoleSnap");
/*  70 */       disable();
/*     */       return;
/*     */     } 
/*  73 */     if (this.timer.passedMs(500L)) {
/*  74 */       disable();
/*     */       return;
/*     */     } 
/*  77 */     if (((Boolean)this.step.getValue()).booleanValue())
/*  78 */       step(((Float)this.height.getValue()).floatValue()); 
/*  79 */     mc.timer.tickLength = 50.0F / ((Float)this.factor.getValue()).floatValue();
/*  80 */     if (HoleUtil.isObbyHole(RotationUtil.getPlayerPos()) || HoleUtil.isBedrockHoles(RotationUtil.getPlayerPos())) {
/*  81 */       disable();
/*     */       return;
/*     */     } 
/*  84 */     if (mc.world.getBlockState(this.holes.pos1).getBlock() == Blocks.AIR) {
/*  85 */       BlockPos it = this.holes.pos1;
/*  86 */       Vec3d playerPos = mc.player.getPositionVector();
/*  87 */       Vec3d targetPos = new Vec3d(it.getX() + 0.5D, mc.player.posY, it.getZ() + 0.5D);
/*  88 */       double yawRad = Math.toRadians((RotationUtil.getRotationTo(playerPos, targetPos)).x);
/*  89 */       double dist = playerPos.distanceTo(targetPos);
/*  90 */       double speed = mc.player.onGround ? -Math.min(0.2805D, dist / 2.0D) : (-EntityUtil.getMaxSpeed() + 0.02D);
/*  91 */       mc.player.motionX = -Math.sin(yawRad) * speed;
/*  92 */       mc.player.motionZ = Math.cos(yawRad) * speed;
/*     */     } else {
/*  94 */       disable();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 100 */     if (isDisabled())
/*     */       return; 
/* 102 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook)
/* 103 */       disable(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\HoleSnap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
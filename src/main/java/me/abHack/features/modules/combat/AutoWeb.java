/*     */ package me.abHack.features.modules.combat;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class AutoWeb extends Module {
/*  16 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(6.0F)));
/*  17 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(72), Integer.valueOf(0), Integer.valueOf(500)));
/*  18 */   private final Setting<Integer> downSpeed = register(new Setting("DownSpeed", Integer.valueOf(13)));
/*  19 */   private final Setting<Boolean> predict = register(new Setting("Predict", Boolean.valueOf(true)));
/*  20 */   private final Setting<Integer> predictSpeed = register(new Setting("PredictSpeed", Integer.valueOf(13), v -> ((Boolean)this.predict.getValue()).booleanValue()));
/*     */   
/*  22 */   private final Timer timer = new Timer();
/*     */   public EntityPlayer target;
/*  24 */   int webSlot = -1;
/*     */   
/*     */   public AutoWeb() {
/*  27 */     super("AutoWeb", "Traps other players in webs", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  34 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  37 */     if (abUtil.HealthSecurity()) {
/*     */       return;
/*     */     }
/*  40 */     this.webSlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
/*  41 */     if (this.webSlot == -1) {
/*     */       return;
/*     */     }
/*  44 */     this.target = getTarget(((Float)this.range.getValue()).floatValue());
/*  45 */     if (this.target == null)
/*     */       return; 
/*  47 */     BlockPos player = EntityUtil.getPlayerPos(this.target);
/*     */     
/*  49 */     if (EntityUtil.isInHole((Entity)this.target))
/*     */       return; 
/*  51 */     if (mc.player.onGround && this.timer.passedMs(((Integer)this.delay.getValue()).intValue())) {
/*  52 */       boolean ab = true;
/*  53 */       if (BlockUtil.CanPlace(player.add(0, -1, 0)) && OyVey.speedManager.getPlayerSpeed(this.target) >= ((Integer)this.downSpeed.getValue()).intValue()) {
/*  54 */         webPlace(player.add(0, -1, 0));
/*  55 */         ab = false;
/*     */       } 
/*  57 */       if (BlockUtil.CanPlace(player) && OyVey.speedManager.getPlayerSpeed(this.target) >= 13.0D) {
/*  58 */         webPlace(player);
/*  59 */         ab = false;
/*     */       } 
/*     */       
/*  62 */       if (((Boolean)this.predict.getValue()).booleanValue() && OyVey.speedManager.getPlayerSpeed(this.target) >= ((Integer)this.predictSpeed.getValue()).intValue() && ab)
/*  63 */         if (check(player.add(1, -1, 0))) {
/*  64 */           webPlace(player.add(1, -1, 0));
/*  65 */         } else if (check(player.add(0, -1, 1))) {
/*  66 */           webPlace(player.add(0, -1, 1));
/*  67 */         } else if (check(player.add(-1, -1, 0))) {
/*  68 */           webPlace(player.add(-1, -1, 0));
/*  69 */         } else if (check(player.add(0, -1, -1))) {
/*  70 */           webPlace(player.add(0, -1, -1));
/*     */         }  
/*  72 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean check(BlockPos pos) {
/*  78 */     return (BlockUtil.CanPlace(pos) && BlockUtil.isAir(pos.add(0, 1, 0)) && BlockUtil.isAir(pos.add(0, 2, 0)) && 
/*  79 */       !BlockUtil.CrystalCheck(pos) && mc.player.getDistanceSq(pos) <= MathUtil.square(((Float)this.range.getValue()).floatValue()) && !BreakManager.isMine(pos, true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void webPlace(BlockPos pos) {
/*  85 */     if (BlockUtil.CrystalCheck(pos))
/*     */       return; 
/*  87 */     int old = mc.player.inventory.currentItem;
/*  88 */     InventoryUtil.switchToSlot(this.webSlot);
/*  89 */     BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, false, true, false);
/*  90 */     InventoryUtil.switchToSlot(old);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/*  96 */     if (this.target != null) {
/*  97 */       return this.target.getName();
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   private EntityPlayer getTarget(double range) {
/* 103 */     EntityPlayer target = null;
/* 104 */     double distance = range;
/* 105 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 106 */       if (EntityUtil.isntValid((Entity)player, range))
/*     */         continue; 
/* 108 */       if (player.isInWeb || OyVey.speedManager.getPlayerSpeed(player) > 30.0D)
/*     */         continue; 
/* 110 */       if (target == null) {
/* 111 */         target = player;
/* 112 */         distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/* 115 */       if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
/*     */         continue; 
/* 117 */       target = player;
/* 118 */       distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 120 */     return target;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AutoWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
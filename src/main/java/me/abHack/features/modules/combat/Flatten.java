/*     */ package me.abHack.features.modules.combat;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
import me.abHack.util.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class Flatten extends Module {
/*  14 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(6.0F)));
/*  15 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Single));
/*  16 */   private final Setting<Boolean> onlyburrow = register(new Setting("OnlyBurrow", Boolean.valueOf(false)));
/*  17 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(500)));
/*  18 */   private final Timer timer = new Timer();
/*     */   public EntityPlayer target;
/*  20 */   int obsidian = -1;
/*     */ 
/*     */   
/*     */   public Flatten() {
/*  24 */     super("Flatten", "Automatic feetobsidian", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  30 */     if (fullNullCheck())
/*     */       return; 
/*  32 */     if (abUtil.HealthSecurity())
/*     */       return; 
/*  34 */     this.target = getTarget(((Float)this.range.getValue()).floatValue());
/*  35 */     if (this.target == null)
/*     */       return; 
/*  37 */     BlockPos player = EntityUtil.getPlayerPos(this.target);
/*  38 */     if (BlockUtil.isAir(player) && ((Boolean)this.onlyburrow.getValue()).booleanValue())
/*     */       return; 
/*  40 */     this.obsidian = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/*  41 */     if (this.obsidian == -1) {
/*     */       return;
/*     */     }
/*  44 */     if (mc.player.onGround && this.timer.passedMs(((Integer)this.delay.getValue()).intValue())) {
/*     */       
/*  46 */       if (check(player.add(0, -1, 0))) {
/*  47 */         Feetplace(player.add(0, -1, 0));
/*     */       }
/*  49 */       if (check(player.add(1, -1, 0)) && (((Mode)this.mode.getValue()).equals(Mode.Double) || ((Mode)this.mode.getValue()).equals(Mode.Fill))) {
/*  50 */         Feetplace(player.add(1, -1, 0));
/*  51 */       } else if (check(player.add(0, -1, -1)) && (((Mode)this.mode.getValue()).equals(Mode.Double) || ((Mode)this.mode.getValue()).equals(Mode.Fill))) {
/*  52 */         Feetplace(player.add(0, -1, -1));
/*     */       } 
/*  54 */       if (check(player.add(-1, -1, 0)) && ((Mode)this.mode.getValue()).equals(Mode.Fill)) {
/*  55 */         Feetplace(player.add(-1, -1, 0));
/*  56 */       } else if (check(player.add(0, -1, 1)) && ((Mode)this.mode.getValue()).equals(Mode.Fill)) {
/*  57 */         Feetplace(player.add(0, -1, 1));
/*     */       } 
/*     */       
/*  60 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean check(BlockPos pos) {
/*  66 */     return (BlockUtil.CanPlace(pos) && BlockUtil.isAir(pos.add(0, 1, 0)) && BlockUtil.isAir(pos.add(0, 2, 0)) && 
/*  67 */       !BlockUtil.EntityCheck(pos) && mc.player.getDistanceSq(pos) <= MathUtil.square(((Float)this.range.getValue()).floatValue()) && !BreakManager.isMine(pos, true));
/*     */   }
/*     */   
/*     */   private void Feetplace(BlockPos pos) {
/*  71 */     int old = mc.player.inventory.currentItem;
/*  72 */     InventoryUtil.switchToSlot(this.obsidian);
/*  73 */     BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, false, true, false);
/*  74 */     InventoryUtil.switchToSlot(old);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/*  79 */     if (this.target != null) {
/*  80 */       return this.target.getName();
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityPlayer getTarget(double range) {
/*  87 */     EntityPlayer target = null;
/*  88 */     double distance = range;
/*  89 */     for (EntityPlayer player : mc.world.playerEntities) {
/*  90 */       if (EntityUtil.isntValid((Entity)player, range))
/*     */         continue; 
/*  92 */       if (target == null) {
/*  93 */         target = player;
/*  94 */         distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/*  97 */       if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
/*     */         continue; 
/*  99 */       target = player;
/* 100 */       distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 102 */     return target;
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 107 */     Single,
/* 108 */     Double,
/* 109 */     Fill;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\Flatten.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.combat;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class TrapHead extends Module {
/*  15 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(6.0F)));
/*  16 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Smart));
/*  17 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500)));
/*  18 */   private final Setting<Boolean> three = register(new Setting("Three", Boolean.valueOf(false)));
/*  19 */   private final Setting<Boolean> self = register(new Setting("IgnoreSelf", Boolean.valueOf(true)));
/*  20 */   private final Setting<Boolean> antiself = register(new Setting("AntiSelf", Boolean.valueOf(true)));
/*  21 */   private final Timer timer = new Timer();
/*     */   public EntityPlayer target;
/*  23 */   int obsidian = -1;
/*     */ 
/*     */   
/*     */   public TrapHead() {
/*  27 */     super("TrapHead", "Trap Head", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/*  33 */     if (this.target != null) {
/*  34 */       return this.target.getName();
/*     */     }
/*  36 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  41 */     Surround.breakcrystal(true);
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  45 */     if (fullNullCheck())
/*     */       return; 
/*  47 */     if (abUtil.HealthSecurity())
/*     */       return; 
/*  49 */     this.target = getTarget(((Float)this.range.getValue()).floatValue());
/*  50 */     if (this.target == null)
/*     */       return; 
/*  52 */     BlockPos people = EntityUtil.getPlayerPos(this.target);
/*  53 */     this.obsidian = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/*  54 */     if (this.obsidian == -1)
/*     */       return; 
/*  56 */     if (((Boolean)this.antiself.getValue()).booleanValue() && people.equals(EntityUtil.getPlayerPos((EntityPlayer)mc.player))) {
/*     */       return;
/*     */     }
/*  59 */     if (this.timer.passedMs(((Integer)this.delay.getValue()).intValue()) && mc.player.onGround) {
/*     */       
/*  61 */       if (isAir(people.add(0, 2, 0)))
/*     */       {
/*  63 */         if (BlockUtil.CanPlace(people.add(0, 2, 0))) {
/*  64 */           if (!BreakManager.isMine(people.add(0, 3, 0), true))
/*  65 */             Headplace(people.add(0, 2, 0)); 
/*  66 */         } else if (!isAir(people.add(1, 1, 0))) {
/*  67 */           Headplace(people.add(1, 2, 0));
/*  68 */         } else if (!isAir(people.add(-1, 1, 0))) {
/*  69 */           Headplace(people.add(-1, 2, 0));
/*  70 */         } else if (!isAir(people.add(0, 1, 1))) {
/*  71 */           Headplace(people.add(0, 2, 1));
/*  72 */         } else if (!isAir(people.add(0, 1, -1))) {
/*  73 */           Headplace(people.add(0, 2, -1));
/*  74 */         } else if (!isAir(people.add(1, 0, 0))) {
/*  75 */           Headplace(people.add(1, 1, 0));
/*  76 */         } else if (!isAir(people.add(-1, 0, 0))) {
/*  77 */           Headplace(people.add(-1, 1, 0));
/*  78 */         } else if (!isAir(people.add(0, 0, 1))) {
/*  79 */           Headplace(people.add(0, 1, 1));
/*  80 */         } else if (!isAir(people.add(0, 0, -1))) {
/*  81 */           Headplace(people.add(0, 1, -1));
/*     */         } 
/*     */       }
/*     */       
/*  85 */       if (((Boolean)this.three.getValue()).booleanValue() && BlockUtil.CanPlace(people.add(0, 3, 0)) && 
/*  86 */         !BreakManager.isMine(people.add(0, 2, 0), true)) {
/*  87 */         Headplace(people.add(0, 3, 0));
/*     */       }
/*  89 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityPlayer getTarget(double range) {
/*  96 */     EntityPlayer target = null;
/*  97 */     double distance = range;
/*  98 */     for (EntityPlayer player : mc.world.playerEntities) {
/*  99 */       if (EntityUtil.isntValid((Entity)player, range))
/*     */         continue; 
/* 101 */       if (target == null) {
/* 102 */         target = player;
/* 103 */         distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/* 106 */       if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
/*     */         continue; 
/* 108 */       target = player;
/* 109 */       distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 111 */     return target;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void Headplace(BlockPos pos) {
/* 117 */     if (this.target == null)
/*     */       return; 
/* 119 */     if (mc.player.getDistanceSq(pos) > MathUtil.square(((Float)this.range.getValue()).floatValue()) || BlockUtil.EntityCheck(pos) || (BreakManager.isMine(pos, ((Boolean)this.self.getValue()).booleanValue()) && this.mode.getValue() == Mode.Smart))
/*     */       return; 
/* 121 */     int old = mc.player.inventory.currentItem;
/* 122 */     InventoryUtil.switchToSlot(this.obsidian);
/* 123 */     BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, false, true, false);
/* 124 */     InventoryUtil.switchToSlot(old);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAir(BlockPos block) {
/* 130 */     return (mc.world.getBlockState(block).getBlock() == Blocks.AIR);
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 134 */     Normal,
/* 135 */     Smart;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\TrapHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
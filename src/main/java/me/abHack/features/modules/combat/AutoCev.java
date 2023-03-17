/*     */ package me.abHack.features.modules.combat;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.player.InstantMine;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import me.abHack.util.abUtil;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class AutoCev extends Module {
/*  18 */   private final Setting<Integer> ranger = register(new Setting("Ranger", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(6))); public static AutoCev INSTANCE;
/*  19 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(275), Integer.valueOf(0), Integer.valueOf(500)));
/*  20 */   private final Setting<Boolean> head = register(new Setting("Head", Boolean.valueOf(true)));
/*  21 */   private final Setting<Boolean> autotoggle = register(new Setting("AutoToggle", Boolean.valueOf(false)));
/*  22 */   private final Timer timer = new Timer();
/*  23 */   private EntityPlayer targer = null;
/*     */   
/*     */   public AutoCev() {
/*  26 */     super("AutoCev", "cev fast+++", Module.Category.COMBAT, true, false, false);
/*  27 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  31 */     this.targer = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  36 */     this.targer = getTarget(((Integer)this.ranger.getValue()).intValue());
/*  37 */     if (this.targer == null || InstantMine.breakPos == null)
/*     */       return; 
/*  39 */     if (InstantMine.INSTANCE.isOff())
/*     */       return; 
/*  41 */     if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1 || InventoryUtil.getItemHotbar(Items.END_CRYSTAL) == -1) {
/*  42 */       Command.sendMessage("No items...");
/*  43 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*  47 */     BlockPos player = EntityUtil.getPlayerPos(this.targer);
/*     */     
/*  49 */     if (BlockUtil.getBlock(player) != Blocks.AIR) {
/*     */       return;
/*     */     }
/*  52 */     if (BlockUtil.getBlock(player.add(1, 1, 0)) != Blocks.BEDROCK && !abUtil.isAir(player.add(1, 0, 0)) && abUtil.isAir(player.add(1, 2, 0)) && abUtil.isAir(player.add(1, 3, 0)) && abUtil.CanPlaceCrystal(player.add(1, 1, 0))) {
/*  53 */       if (!InstantMine.breakPos.equals(player.add(1, 1, 0)))
/*  54 */         mc.playerController.onPlayerDamageBlock(player.add(1, 1, 0), BlockUtil.getRayTraceFacing(player.add(1, 1, 0))); 
/*  55 */       Cev();
/*  56 */     } else if (BlockUtil.getBlock(player.add(0, 1, 1)) != Blocks.BEDROCK && !abUtil.isAir(player.add(0, 0, 1)) && abUtil.isAir(player.add(0, 2, 1)) && abUtil.isAir(player.add(0, 3, 1)) && abUtil.CanPlaceCrystal(player.add(0, 1, 1))) {
/*  57 */       if (!InstantMine.breakPos.equals(player.add(0, 1, 1)))
/*  58 */         mc.playerController.onPlayerDamageBlock(player.add(0, 1, 1), BlockUtil.getRayTraceFacing(player.add(0, 1, 1))); 
/*  59 */       Cev();
/*  60 */     } else if (BlockUtil.getBlock(player.add(-1, 1, 0)) != Blocks.BEDROCK && !abUtil.isAir(player.add(-1, 0, 0)) && abUtil.isAir(player.add(-1, 2, 0)) && abUtil.isAir(player.add(-1, 3, 0)) && abUtil.CanPlaceCrystal(player.add(-1, 1, 0))) {
/*  61 */       if (!InstantMine.breakPos.equals(player.add(-1, 1, 0)))
/*  62 */         mc.playerController.onPlayerDamageBlock(player.add(-1, 1, 0), BlockUtil.getRayTraceFacing(player.add(-1, 1, 0))); 
/*  63 */       Cev();
/*  64 */     } else if (BlockUtil.getBlock(player.add(0, 1, -1)) != Blocks.BEDROCK && !abUtil.isAir(player.add(0, 0, -1)) && abUtil.isAir(player.add(0, 2, -1)) && abUtil.isAir(player.add(0, 3, -1)) && abUtil.CanPlaceCrystal(player.add(0, 1, -1))) {
/*  65 */       if (!InstantMine.breakPos.equals(player.add(0, 1, -1)))
/*  66 */         mc.playerController.onPlayerDamageBlock(player.add(0, 1, -1), BlockUtil.getRayTraceFacing(player.add(0, 1, -1))); 
/*  67 */       Cev();
/*  68 */     } else if (BlockUtil.getBlock(player.add(0, 3, 0)) != Blocks.BEDROCK && check(player.add(0, 3, 0)) && abUtil.isAir(player.add(0, 2, 0)) && abUtil.isAir(player.add(0, 4, 0)) && abUtil.isAir(player.add(0, 5, 0)) && abUtil.CanPlaceCrystal(player.add(0, 3, 0)) && ((Boolean)this.head.getValue()).booleanValue()) {
/*  69 */       if (!InstantMine.breakPos.equals(player.add(0, 3, 0)))
/*  70 */         mc.playerController.onPlayerDamageBlock(player.add(0, 3, 0), BlockUtil.getRayTraceFacing(player.add(0, 3, 0))); 
/*  71 */       Cev();
/*  72 */     } else if (BlockUtil.getBlock(player.add(0, 2, 0)) != Blocks.BEDROCK && check(player.add(0, 2, 0)) && abUtil.isAir(player.add(0, 3, 0)) && abUtil.isAir(player.add(0, 4, 0)) && abUtil.CanPlaceCrystal(player.add(0, 2, 0)) && ((Boolean)this.head.getValue()).booleanValue()) {
/*  73 */       if (!InstantMine.breakPos.equals(player.add(0, 2, 0)))
/*  74 */         mc.playerController.onPlayerDamageBlock(player.add(0, 2, 0), BlockUtil.getRayTraceFacing(player.add(0, 2, 0))); 
/*  75 */       Cev();
/*     */     }
/*  77 */     else if (((Boolean)this.autotoggle.getValue()).booleanValue()) {
/*  78 */       disable();
/*  79 */       Command.sendMessage("No targer...");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
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
/*     */   private boolean check(BlockPos pos) {
/* 106 */     return (BlockUtil.getBlock(pos.add(1, 0, 0)) != Blocks.AIR || BlockUtil.getBlock(pos.add(-1, 0, 0)) != Blocks.AIR || BlockUtil.getBlock(pos.add(0, 0, 1)) != Blocks.AIR || BlockUtil.getBlock(pos.add(0, 0, -1)) != Blocks.AIR);
/*     */   }
/*     */ 
/*     */   
/*     */   private void Cev() {
/* 111 */     if (this.timer.passedMs(((Integer)this.delay.getValue()).intValue())) {
/* 112 */       if (abUtil.isAir(InstantMine.breakPos) && abUtil.CanPlaceCrystal(InstantMine.breakPos)) {
/* 113 */         int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 114 */         int old = mc.player.inventory.currentItem;
/* 115 */         if (BlockUtil.CrystalCheck(InstantMine.breakPos) || BlockUtil.CrystalCheck(InstantMine.breakPos.add(0, 1, 0)) || BlockUtil.CrystalCheck(InstantMine.breakPos.add(0, 2, 0)))
/* 116 */           Surround.breakcrystal(true); 
/* 117 */         InventoryUtil.switchToSlot(obbySlot);
/* 118 */         BlockUtil.placeBlock(InstantMine.breakPos, EnumHand.MAIN_HAND, false, false, false);
/* 119 */         InventoryUtil.switchToSlot(old);
/*     */       } 
/* 121 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AutoCev.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
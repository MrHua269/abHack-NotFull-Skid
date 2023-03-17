/*     */ package me.abHack.features.modules.combat;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.Comparator;
import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.block.BlockEnderChest;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class AutoTrap extends Module {
/*  25 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(250))); public static boolean isPlacing = false;
/*  26 */   private final Setting<Integer> blocksPerPlace = register(new Setting("BlocksPerTick", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(30)));
/*  27 */   private final Setting<Boolean> smartplace = register(new Setting("SmartPlace", Boolean.valueOf(true)));
/*  28 */   private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
/*  29 */   private final Setting<Boolean> smartRotate = register(new Setting("SmartRotate", Boolean.valueOf(false)));
/*  30 */   private final Setting<Boolean> raytrace = register(new Setting("Raytrace", Boolean.valueOf(false)));
/*  31 */   private final Setting<Boolean> antiScaffold = register(new Setting("AntiScaffold", Boolean.valueOf(false)));
/*  32 */   private final Setting<Boolean> antiStep = register(new Setting("AntiStep", Boolean.valueOf(false)));
/*  33 */   private final Setting<Boolean> toggle = register(new Setting("AutoToggle", Boolean.valueOf(false)));
/*  34 */   private final Timer timer = new Timer();
/*  35 */   private final Map<BlockPos, Integer> retries = new HashMap<>();
/*  36 */   private final Timer retryTimer = new Timer();
/*     */   public EntityPlayer target;
/*     */   private boolean didPlace = false;
/*     */   private boolean isSneaking;
/*     */   private int lastHotbarSlot;
/*  41 */   private int placements = 0;
/*  42 */   private BlockPos startPos = null;
/*     */   
/*     */   public AutoTrap() {
/*  45 */     super("AutoTrap", "Traps other players", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  50 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  53 */     Surround.breakcrystal(true);
/*  54 */     this.startPos = EntityUtil.getRoundedBlockPos((Entity)mc.player);
/*  55 */     this.lastHotbarSlot = mc.player.inventory.currentItem;
/*  56 */     this.retries.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  61 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  64 */     doTrap();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/*  69 */     if (this.target != null) {
/*  70 */       return this.target.getName();
/*     */     }
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  77 */     isPlacing = false;
/*  78 */     this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
/*     */   }
/*     */   
/*     */   private void doTrap() {
/*  82 */     if (check()) {
/*     */       return;
/*     */     }
/*  85 */     doStaticTrap();
/*  86 */     if (this.didPlace) {
/*  87 */       this.timer.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   private void doStaticTrap() {
/*  92 */     List<Vec3d> placeTargets = EntityUtil.targets(this.target.getPositionVector(), ((Boolean)this.antiScaffold.getValue()).booleanValue(), ((Boolean)this.antiStep.getValue()).booleanValue(), false, false, false, ((Boolean)this.raytrace.getValue()).booleanValue());
/*  93 */     placeList(placeTargets);
/*     */   }
/*     */   
/*     */   private void placeList(List<Vec3d> list) {
/*  97 */     list.sort((vec3d, vec3d2) -> Double.compare(mc.player.getDistanceSq(vec3d2.x, vec3d2.y, vec3d2.z), mc.player.getDistanceSq(vec3d.x, vec3d.y, vec3d.z)));
/*  98 */     list.sort(Comparator.comparingDouble(vec3d -> vec3d.y));
/*  99 */     for (Vec3d vec3d3 : list) {
/* 100 */       BlockPos position = new BlockPos(vec3d3);
/* 101 */       int placeability = BlockUtil.isPositionPlaceable(position, ((Boolean)this.raytrace.getValue()).booleanValue());
/* 102 */       if (placeability == 1 && (this.retries.get(position) == null || ((Integer)this.retries.get(position)).intValue() < 4)) {
/* 103 */         placeBlock(position);
/* 104 */         this.retries.put(position, Integer.valueOf((this.retries.get(position) == null) ? 1 : (((Integer)this.retries.get(position)).intValue() + 1)));
/* 105 */         this.retryTimer.reset();
/*     */         continue;
/*     */       } 
/* 108 */       if (placeability != 3)
/* 109 */         continue;  placeBlock(position);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean check() {
/* 114 */     isPlacing = false;
/* 115 */     this.didPlace = false;
/* 116 */     this.placements = 0;
/* 117 */     int obbySlot2 = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 118 */     if (obbySlot2 == -1) {
/* 119 */       toggle();
/*     */     }
/* 121 */     int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 122 */     if (isOff()) {
/* 123 */       return true;
/*     */     }
/* 125 */     if (!this.startPos.equals(EntityUtil.getRoundedBlockPos((Entity)mc.player)) && ((Boolean)this.toggle.getValue()).booleanValue()) {
/* 126 */       disable();
/* 127 */       return true;
/*     */     } 
/* 129 */     if (this.retryTimer.passedMs(2000L)) {
/* 130 */       this.retries.clear();
/* 131 */       this.retryTimer.reset();
/*     */     } 
/* 133 */     if (obbySlot == -1) {
/* 134 */       Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "No Obsidian in hotbar disabling...");
/* 135 */       disable();
/* 136 */       return true;
/*     */     } 
/* 138 */     if (mc.player.inventory.currentItem != this.lastHotbarSlot && mc.player.inventory.currentItem != obbySlot) {
/* 139 */       this.lastHotbarSlot = mc.player.inventory.currentItem;
/*     */     }
/* 141 */     this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
/* 142 */     this.target = getTarget();
/* 143 */     return (this.target == null || !this.timer.passedMs(((Integer)this.delay.getValue()).intValue()));
/*     */   }
/*     */   
/*     */   private EntityPlayer getTarget() {
/* 147 */     EntityPlayer target = null;
/* 148 */     double distance = Math.pow(10.0D, 2.0D) + 1.0D;
/* 149 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 150 */       if (EntityUtil.isntValid((Entity)player, 10.0D) || EntityUtil.isTrapped(player, ((Boolean)this.antiScaffold.getValue()).booleanValue(), ((Boolean)this.antiStep.getValue()).booleanValue(), false, false, false) || OyVey.speedManager.getPlayerSpeed(player) > 10.0D)
/*     */         continue; 
/* 152 */       if (target == null) {
/* 153 */         target = player;
/* 154 */         distance = mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/* 157 */       if (mc.player.getDistanceSq((Entity)player) >= distance)
/* 158 */         continue;  target = player;
/* 159 */       distance = mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 161 */     return target;
/*     */   }
/*     */   
/*     */   private void placeBlock(BlockPos pos) {
/* 165 */     if (this.placements < ((Integer)this.blocksPerPlace.getValue()).intValue() && mc.player.getDistanceSq(pos) <= MathUtil.square(5.0D)) {
/* 166 */       isPlacing = true;
/* 167 */       int originalSlot = mc.player.inventory.currentItem;
/* 168 */       int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 169 */       int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
/* 170 */       if (obbySlot == -1 && eChestSot == -1) {
/* 171 */         toggle();
/*     */       }
/* 173 */       if (BreakManager.isMine(pos, true) && ((Boolean)this.smartplace.getValue()).booleanValue())
/*     */         return; 
/* 175 */       if (((Boolean)this.smartRotate.getValue()).booleanValue()) {
/* 176 */         mc.player.inventory.currentItem = (obbySlot == -1) ? eChestSot : obbySlot;
/* 177 */         mc.playerController.updateController();
/* 178 */         this.isSneaking = BlockUtil.placeBlockSmartRotate(pos, EnumHand.MAIN_HAND, true, true, this.isSneaking);
/* 179 */         mc.player.inventory.currentItem = originalSlot;
/* 180 */         mc.playerController.updateController();
/*     */       } else {
/* 182 */         mc.player.inventory.currentItem = (obbySlot == -1) ? eChestSot : obbySlot;
/* 183 */         mc.playerController.updateController();
/* 184 */         this.isSneaking = BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, this.isSneaking);
/* 185 */         mc.player.inventory.currentItem = originalSlot;
/* 186 */         mc.playerController.updateController();
/*     */       } 
/* 188 */       this.didPlace = true;
/* 189 */       this.placements++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AutoTrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
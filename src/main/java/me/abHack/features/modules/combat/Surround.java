/*     */ package me.abHack.features.modules.combat;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.*;
/*     */
/*     */
/*     */ import java.util.stream.Collectors;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.command.Command;
import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.DamageUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import me.abHack.util.abUtil;
/*     */ import net.minecraft.block.BlockEnderChest;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class Surround extends Module {
/*     */   public static boolean isPlacing = false;
/*  27 */   private final Setting<Integer> blocksPerTick = register(new Setting("BlocksPerTick", Integer.valueOf(12), Integer.valueOf(1), Integer.valueOf(20))); public static Surround INSTANCE;
/*  28 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(250)));
/*  29 */   private final Setting<Boolean> noGhost = register(new Setting("PacketPlace", Boolean.valueOf(true)));
/*  30 */   private final Setting<Boolean> breakcrystal = register(new Setting("BreakCrystal", Boolean.valueOf(true)));
/*  31 */   private final Setting<Boolean> antiSuicide = register(new Setting("AntiSuicide", Boolean.valueOf(true), v -> ((Boolean)this.breakcrystal.getValue()).booleanValue()));
/*  32 */   private final Setting<Boolean> center = register(new Setting("TPCenter", Boolean.valueOf(true)));
/*  33 */   private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(false)));
/*  34 */   private final Setting<Boolean> toggle = register(new Setting("AutoToggle", Boolean.valueOf(false)));
/*  35 */   private final Timer timer = new Timer();
/*  36 */   private final Timer retryTimer = new Timer();
/*  37 */   private final Set<Vec3d> extendingBlocks = new HashSet<>();
/*  38 */   private final Map<BlockPos, Integer> retries = new HashMap<>();
/*     */   private int isSafe;
/*     */   private BlockPos startPos;
/*     */   private boolean didPlace = false;
/*     */   private int lastHotbarSlot;
/*     */   private boolean isSneaking;
/*  44 */   private int placements = 0;
/*  45 */   private int extenders = 1;
/*     */   private boolean offHand = false;
/*     */   
/*     */   public Surround() {
/*  49 */     super("Surround", "Surrounds you with Obsidian", Module.Category.COMBAT, true, false, false);
/*  50 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public static void breakcrystal(boolean antiSuicide) {
/*  54 */     BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/*  55 */     Entity minCrystal = null;
/*  56 */     double minDamage = 114514.0D;
/*     */     
/*  58 */     for (Entity crystal : mc.world.loadedEntityList.stream().filter(e -> (e instanceof net.minecraft.entity.item.EntityEnderCrystal && !e.isDead)).sorted(Comparator.comparing(e -> Float.valueOf(mc.player.getDistance(e)))).collect(Collectors.toList())) {
/*  59 */       if (crystal instanceof net.minecraft.entity.item.EntityEnderCrystal && abUtil.CanAttackCrystal(crystal)) {
/*  60 */         double selfDamage = DamageUtil.calculateDamage(crystal.posX, crystal.posY, crystal.posZ, (Entity)mc.player, mutablePos);
/*     */         
/*  62 */         if (selfDamage + (antiSuicide ? 2.0D : 0.5D) >= EntityUtil.getHealth((Entity)mc.player)) {
/*     */           continue;
/*     */         }
/*     */         
/*  66 */         if (minDamage > selfDamage) {
/*  67 */           minDamage = selfDamage;
/*  68 */           minCrystal = crystal;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     if (minCrystal != null) {
/*  74 */       mc.player.connection.sendPacket((Packet)new CPacketUseEntity(minCrystal));
/*  75 */       mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  83 */     if (fullNullCheck()) {
/*  84 */       disable();
/*     */     }
/*  86 */     this.lastHotbarSlot = mc.player.inventory.currentItem;
/*  87 */     this.startPos = EntityUtil.getRoundedBlockPos((Entity)mc.player);
/*  88 */     if (((Boolean)this.center.getValue()).booleanValue()) {
/*  89 */       OyVey.positionManager.setPositionPacket(this.startPos.getX() + 0.5D, this.startPos.getY(), this.startPos.getZ() + 0.5D, true, true, true);
/*     */     }
/*  91 */     this.retries.clear();
/*  92 */     this.retryTimer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  97 */     if (((Boolean)this.breakcrystal.getValue()).booleanValue() && this.isSafe == 0)
/*  98 */       breakcrystal(((Boolean)this.antiSuicide.getValue()).booleanValue()); 
/*  99 */     doFeetPlace();
/* 100 */     if (((Boolean)this.toggle.getValue()).booleanValue() && EntityUtil.isInHole((Entity)mc.player)) {
/* 101 */       disable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 107 */     if (nullCheck()) {
/*     */       return;
/*     */     }
/* 110 */     isPlacing = false;
/* 111 */     this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 116 */     switch (this.isSafe) {
/*     */       case 0:
/* 118 */         return ChatFormatting.RED + "Unsafe";
/*     */       
/*     */       case 1:
/* 121 */         return ChatFormatting.YELLOW + "Safe";
/*     */     } 
/*     */     
/* 124 */     return ChatFormatting.GREEN + "Safe";
/*     */   }
/*     */   
/*     */   private void doFeetPlace() {
/* 128 */     if (check()) {
/*     */       return;
/*     */     }
/* 131 */     if (!EntityUtil.isSafe((Entity)mc.player, 0, true)) {
/* 132 */       this.isSafe = 0;
/* 133 */       placeBlocks(mc.player.getPositionVector(), EntityUtil.getUnsafeBlockArray((Entity)mc.player, 0, true), true, false, false);
/* 134 */     } else if (!EntityUtil.isSafe((Entity)mc.player, -1, false)) {
/* 135 */       this.isSafe = 1;
/* 136 */       placeBlocks(mc.player.getPositionVector(), EntityUtil.getUnsafeBlockArray((Entity)mc.player, -1, false), false, false, true);
/*     */     } else {
/* 138 */       this.isSafe = 2;
/*     */     } 
/* 140 */     processExtendingBlocks();
/* 141 */     if (this.didPlace) {
/* 142 */       this.timer.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   private void processExtendingBlocks() {
/* 147 */     if (this.extendingBlocks.size() == 2 && this.extenders < 1) {
/* 148 */       Vec3d[] array = new Vec3d[2];
/* 149 */       int i = 0;
/* 150 */       for (Vec3d extendingBlock : this.extendingBlocks) {
/* 151 */         array[i] = extendingBlock;
/* 152 */         i++;
/*     */       } 
/* 154 */       int placementsBefore = this.placements;
/* 155 */       if (areClose(array) != null) {
/* 156 */         placeBlocks(areClose(array), EntityUtil.getUnsafeBlockArrayFromVec3d(areClose(array), 0, true), true, false, true);
/*     */       }
/* 158 */       if (placementsBefore < this.placements) {
/* 159 */         this.extendingBlocks.clear();
/*     */       }
/* 161 */     } else if (this.extendingBlocks.size() > 2 || this.extenders >= 1) {
/* 162 */       this.extendingBlocks.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Vec3d areClose(Vec3d[] vec3ds) {
/* 167 */     int matches = 0;
/* 168 */     for (Vec3d vec3d : vec3ds) {
/* 169 */       for (Vec3d pos : EntityUtil.getUnsafeBlockArray((Entity)mc.player, 0, true)) {
/* 170 */         if (vec3d.equals(pos))
/* 171 */           matches++; 
/*     */       } 
/*     */     } 
/* 174 */     if (matches == 2) {
/* 175 */       return mc.player.getPositionVector().add(vec3ds[0].add(vec3ds[1]));
/*     */     }
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean placeBlocks(Vec3d pos, Vec3d[] vec3ds, boolean hasHelpingBlocks, boolean isHelping, boolean isExtending) {
/* 183 */     for (Vec3d vec3d : vec3ds) {
/* 184 */       boolean gotHelp = true;
/* 185 */       BlockPos position = (new BlockPos(pos)).add(vec3d.x, vec3d.y, vec3d.z);
/* 186 */       switch (BlockUtil.isPositionPlaceable(position, false)) {
/*     */         case 1:
/* 188 */           if (this.retries.get(position) == null || ((Integer)this.retries.get(position)).intValue() < 4) {
/* 189 */             placeBlock(position);
/* 190 */             this.retries.put(position, Integer.valueOf((this.retries.get(position) == null) ? 1 : (((Integer)this.retries.get(position)).intValue() + 1)));
/* 191 */             this.retryTimer.reset();
/*     */             break;
/*     */           } 
/* 194 */           if (OyVey.speedManager.getSpeedKpH() != 0.0D || isExtending || this.extenders >= 1)
/* 195 */             break;  placeBlocks(mc.player.getPositionVector().add(vec3d), EntityUtil.getUnsafeBlockArrayFromVec3d(mc.player.getPositionVector().add(vec3d), 0, true), hasHelpingBlocks, false, true);
/* 196 */           this.extendingBlocks.add(vec3d);
/* 197 */           this.extenders++;
/*     */           break;
/*     */         
/*     */         case 2:
/* 201 */           if (!hasHelpingBlocks)
/* 202 */             break;  gotHelp = placeBlocks(pos, BlockUtil.getHelpingBlocks(vec3d), false, true, true);
/*     */         
/*     */         case 3:
/* 205 */           if (gotHelp) {
/* 206 */             placeBlock(position);
/*     */           }
/* 208 */           if (!isHelping)
/* 209 */             break;  return true;
/*     */       } 
/*     */     
/*     */     } 
/* 213 */     return false;
/*     */   }
/*     */   
/*     */   private boolean check() {
/* 217 */     if (nullCheck()) {
/* 218 */       return true;
/*     */     }
/* 220 */     int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 221 */     int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
/* 222 */     if (obbySlot == -1 && eChestSot == -1) {
/* 223 */       toggle();
/*     */     }
/* 225 */     this.offHand = InventoryUtil.isBlock(mc.player.getHeldItemOffhand().getItem(), BlockObsidian.class);
/* 226 */     isPlacing = false;
/* 227 */     this.didPlace = false;
/* 228 */     this.extenders = 1;
/* 229 */     this.placements = 0;
/* 230 */     int obbySlot1 = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 231 */     int echestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
/* 232 */     if (isOff()) {
/* 233 */       return true;
/*     */     }
/* 235 */     if (this.retryTimer.passedMs(2500L)) {
/* 236 */       this.retries.clear();
/* 237 */       this.retryTimer.reset();
/*     */     } 
/* 239 */     if (obbySlot1 == -1 && !this.offHand && echestSlot == -1) {
/* 240 */       Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "No Obsidian in hotbar disabling...");
/* 241 */       disable();
/* 242 */       return true;
/*     */     } 
/* 244 */     this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
/* 245 */     if (mc.player.inventory.currentItem != this.lastHotbarSlot && mc.player.inventory.currentItem != obbySlot1 && mc.player.inventory.currentItem != echestSlot) {
/* 246 */       this.lastHotbarSlot = mc.player.inventory.currentItem;
/*     */     }
/* 248 */     if (!this.startPos.equals(EntityUtil.getRoundedBlockPos((Entity)mc.player))) {
/* 249 */       disable();
/* 250 */       return true;
/*     */     } 
/* 252 */     return !this.timer.passedMs(((Integer)this.delay.getValue()).intValue());
/*     */   }
/*     */   
/*     */   private void placeBlock(BlockPos pos) {
/* 256 */     if (this.placements < ((Integer)this.blocksPerTick.getValue()).intValue()) {
/* 257 */       int originalSlot = mc.player.inventory.currentItem;
/* 258 */       int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 259 */       int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
/* 260 */       if (obbySlot == -1 && eChestSot == -1) {
/* 261 */         toggle();
/*     */       }
/* 263 */       isPlacing = true;
/* 264 */       mc.player.inventory.currentItem = (obbySlot == -1) ? eChestSot : obbySlot;
/* 265 */       mc.playerController.updateController();
/* 266 */       this.isSneaking = BlockUtil.placeBlock(pos, this.offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), ((Boolean)this.noGhost.getValue()).booleanValue(), this.isSneaking);
/* 267 */       mc.player.inventory.currentItem = originalSlot;
/* 268 */       mc.playerController.updateController();
/* 269 */       this.didPlace = true;
/* 270 */       this.placements++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\Surround.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
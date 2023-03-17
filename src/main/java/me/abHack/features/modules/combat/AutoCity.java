/*     */ package me.abHack.features.modules.combat;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.player.InstantMine;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.MutableBlockPosHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class AutoCity extends Module {
/*     */   public static EntityPlayer target;
/*  19 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(1.0F), Float.valueOf(6.0F))); public static BlockPos feet;
/*  20 */   private final Setting<Boolean> under = register(new Setting("MineUnder", Boolean.valueOf(false)));
/*  21 */   private final Setting<Boolean> cycle = register(new Setting("CycleMine", Boolean.valueOf(false)));
/*  22 */   private final Setting<Boolean> mineweb = register(new Setting("Mine Web", Boolean.valueOf(true)));
/*  23 */   private final Setting<Boolean> toggle = register(new Setting("AutoToggle", Boolean.valueOf(false)));
/*     */   
/*  25 */   private final MutableBlockPosHelper mutablePos = new MutableBlockPosHelper();
/*     */ 
/*     */   
/*     */   public AutoCity() {
/*  29 */     super("AutoCity", "AutoCity", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean safeCheck(BlockPos pos) {
/*  34 */     for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos))) {
/*  35 */       if (entity instanceof EntityPlayer && entity == mc.player)
/*  36 */         return false; 
/*     */     } 
/*  38 */     return true;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  42 */     if (fullNullCheck())
/*     */       return; 
/*  44 */     if (InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1)
/*     */       return; 
/*  46 */     target = getTarget(((Float)this.range.getValue()).floatValue());
/*  47 */     if (target != null && (OyVey.moduleManager.isModuleEnabled("AutoCev") || OyVey.moduleManager.isModuleEnabled("PistonCrystal")) && isAir(new BlockPos(target.posX, target.posY, target.posZ)))
/*     */       return; 
/*  49 */     surroundMine();
/*  50 */     if (((Boolean)this.toggle.getValue()).booleanValue())
/*  51 */       disable(); 
/*     */   }
/*     */   
/*     */   public String getDisplayInfo() {
/*  55 */     if (target != null) {
/*  56 */       return target.getName();
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void surroundMine() {
/*  63 */     if (target == null) {
/*     */       return;
/*     */     }
/*  66 */     feet = EntityUtil.getPlayerPos(target);
/*     */     
/*  68 */     if (detection(target) && detection2(target) && (isAir(feet) || target.isInWeb)) {
/*     */       return;
/*     */     }
/*  71 */     if (canMine(feet)) {
/*  72 */       surroundMine(this.mutablePos.set(feet, 0.0D, 0.25D, 0.0D));
/*  73 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, 1, 0, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, 2, -1, 0)) || (canPlace((BlockPos)this.mutablePos.set(feet, 3, -1, 0)) && isAir((BlockPos)this.mutablePos.set(feet, 2, 0, 0)))) && safeCheck((BlockPos)this.mutablePos.set(feet, 2, 0, 0))) {
/*  74 */       surroundMine(this.mutablePos.set(feet, 1, 0, 0));
/*  75 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, 0, 0, -1)) && (canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, -2)) || (canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, -3)) && isAir((BlockPos)this.mutablePos.set(feet, 0, 0, -2)))) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, -2))) {
/*  76 */       surroundMine(this.mutablePos.set(feet, 0, 0, -1));
/*  77 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, -1, 0, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, -2, -1, 0)) || (canPlace((BlockPos)this.mutablePos.set(feet, -3, -1, 0)) && isAir((BlockPos)this.mutablePos.set(feet, -2, 0, 0)))) && safeCheck((BlockPos)this.mutablePos.set(feet, -2, 0, 0))) {
/*  78 */       surroundMine(this.mutablePos.set(feet, -1, 0, 0));
/*  79 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, 0, 0, 1)) && (canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, 2)) || (canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, 3)) && isAir((BlockPos)this.mutablePos.set(feet, 0, 0, 2)))) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, 2))) {
/*  80 */       surroundMine(this.mutablePos.set(feet, 0, 0, 1));
/*     */     
/*     */     }
/*  83 */     else if (isAir((BlockPos)this.mutablePos.set(feet, 1, 0, 0)) && !isAir((BlockPos)this.mutablePos.set(feet, 1, 1, 0)) && canMine((BlockPos)this.mutablePos.set(feet, 2, 0, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, 3, -1, 0)) || isAir((BlockPos)this.mutablePos.set(feet, 2, 1, 0))) && safeCheck((BlockPos)this.mutablePos.set(feet, 3, 0, 0))) {
/*  84 */       surroundMine(this.mutablePos.set(feet, 2, 0, 0));
/*  85 */     } else if (isAir((BlockPos)this.mutablePos.set(feet, 0, 0, -1)) && !isAir((BlockPos)this.mutablePos.set(feet, 0, 1, -1)) && canMine((BlockPos)this.mutablePos.set(feet, 0, 0, -2)) && (canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, -3)) || isAir((BlockPos)this.mutablePos.set(feet, 0, 1, -2))) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, -3))) {
/*  86 */       surroundMine(this.mutablePos.set(feet, 0, 0, -2));
/*  87 */     } else if (isAir((BlockPos)this.mutablePos.set(feet, -1, 0, 0)) && !isAir((BlockPos)this.mutablePos.set(feet, -1, 1, 0)) && canMine((BlockPos)this.mutablePos.set(feet, -2, 0, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, -3, -1, 0)) || isAir((BlockPos)this.mutablePos.set(feet, -2, 1, 0))) && safeCheck((BlockPos)this.mutablePos.set(feet, -3, 0, 0))) {
/*  88 */       surroundMine(this.mutablePos.set(feet, -2, 0, 0));
/*  89 */     } else if (isAir((BlockPos)this.mutablePos.set(feet, 0, 0, 1)) && !isAir((BlockPos)this.mutablePos.set(feet, 0, 1, 1)) && canMine((BlockPos)this.mutablePos.set(feet, 0, 0, 2)) && (canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, 3)) || isAir((BlockPos)this.mutablePos.set(feet, 0, 1, 2))) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, 3))) {
/*  90 */       surroundMine(this.mutablePos.set(feet, 0, 0, 2));
/*     */     
/*     */     }
/*  93 */     else if ((canMine((BlockPos)this.mutablePos.set(feet, 1, -1, 0)) || isAir((BlockPos)this.mutablePos.set(feet, 1, -1, 0))) && canMine((BlockPos)this.mutablePos.set(feet, 0, -1, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, 2, -2, 0)) || (canPlace((BlockPos)this.mutablePos.set(feet, 3, -2, 0)) && isAir((BlockPos)this.mutablePos.set(feet, 2, -1, 0)))) && ((Boolean)this.under.getValue()).booleanValue()) {
/*  94 */       surroundMine(this.mutablePos.set(feet, 0, -1, 0));
/*  95 */     } else if ((canMine((BlockPos)this.mutablePos.set(feet, 0, -1, -1)) || isAir((BlockPos)this.mutablePos.set(feet, 0, -1, -1))) && canMine((BlockPos)this.mutablePos.set(feet, 0, -1, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, 0, -2, -2)) || (canPlace((BlockPos)this.mutablePos.set(feet, 0, -2, -3)) && isAir((BlockPos)this.mutablePos.set(feet, 0, -1, -2)))) && ((Boolean)this.under.getValue()).booleanValue()) {
/*  96 */       surroundMine(this.mutablePos.set(feet, 0, -1, 0));
/*  97 */     } else if ((canMine((BlockPos)this.mutablePos.set(feet, -1, -1, 0)) || isAir((BlockPos)this.mutablePos.set(feet, -1, -1, 0))) && canMine((BlockPos)this.mutablePos.set(feet, 0, -1, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, -2, -2, 0)) || (canPlace((BlockPos)this.mutablePos.set(feet, -3, -2, 0)) && isAir((BlockPos)this.mutablePos.set(feet, -2, -1, 0)))) && ((Boolean)this.under.getValue()).booleanValue()) {
/*  98 */       surroundMine(this.mutablePos.set(feet, 0, -1, 0));
/*  99 */     } else if ((canMine((BlockPos)this.mutablePos.set(feet, 0, -1, 1)) || isAir((BlockPos)this.mutablePos.set(feet, 0, -1, 1))) && canMine((BlockPos)this.mutablePos.set(feet, 0, -1, 0)) && (canPlace((BlockPos)this.mutablePos.set(feet, 0, -2, 2)) || (canPlace((BlockPos)this.mutablePos.set(feet, 0, -2, 3)) && isAir((BlockPos)this.mutablePos.set(feet, 0, -1, 2)))) && ((Boolean)this.under.getValue()).booleanValue()) {
/* 100 */       surroundMine(this.mutablePos.set(feet, 0, -1, 0));
/*     */     }
/* 102 */     else if (canMine((BlockPos)this.mutablePos.set(feet, 1, 0, 0)) && mayPlace((BlockPos)this.mutablePos.set(feet, 1, -1, 0)) && !canPlace((BlockPos)this.mutablePos.set(feet, 2, -1, 0)) && isAir((BlockPos)this.mutablePos.set(feet, 1, 1, 0)) && safeCheck((BlockPos)this.mutablePos.set(feet, 2, 0, 0))) {
/* 103 */       surroundMine(this.mutablePos.set(feet, 1, 0, 0));
/* 104 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, 0, 0, -1)) && mayPlace((BlockPos)this.mutablePos.set(feet, 0, -1, -1)) && !canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, -2)) && isAir((BlockPos)this.mutablePos.set(feet, 0, 1, -1)) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, -2))) {
/* 105 */       surroundMine(this.mutablePos.set(feet, 0, 0, -1));
/* 106 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, -1, 0, 0)) && mayPlace((BlockPos)this.mutablePos.set(feet, -1, -1, 0)) && !canPlace((BlockPos)this.mutablePos.set(feet, -2, -1, 0)) && isAir((BlockPos)this.mutablePos.set(feet, -1, 1, 0)) && safeCheck((BlockPos)this.mutablePos.set(feet, -2, 0, 0))) {
/* 107 */       surroundMine(this.mutablePos.set(feet, -1, 0, 0));
/* 108 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, 0, 0, 1)) && mayPlace((BlockPos)this.mutablePos.set(feet, 0, -1, 1)) && !canPlace((BlockPos)this.mutablePos.set(feet, 0, -1, 2)) && isAir((BlockPos)this.mutablePos.set(feet, 0, 1, 1)) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, 2))) {
/* 109 */       surroundMine(this.mutablePos.set(feet, 0, 0, 1));
/*     */     }
/* 111 */     else if (canMine((BlockPos)this.mutablePos.set(feet, 1, 1, 0)) && mayPlace((BlockPos)this.mutablePos.set(feet, 1, -1, 0)) && (canMine((BlockPos)this.mutablePos.set(feet, 1, 0, 0)) || isAir((BlockPos)this.mutablePos.set(feet, 1, 0, 0))) && safeCheck((BlockPos)this.mutablePos.set(feet, 2, 0, 0)) && ((Boolean)this.cycle.getValue()).booleanValue()) {
/* 112 */       surroundMine(this.mutablePos.set(feet, 1, 1, 0));
/* 113 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, 0, 1, -1)) && mayPlace((BlockPos)this.mutablePos.set(feet, 0, -1, -1)) && (canMine((BlockPos)this.mutablePos.set(feet, 0, 0, -1)) || isAir((BlockPos)this.mutablePos.set(feet, 0, 0, -1))) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, -2)) && ((Boolean)this.cycle.getValue()).booleanValue()) {
/* 114 */       surroundMine(this.mutablePos.set(feet, 0, 1, -1));
/* 115 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, -1, 1, 0)) && mayPlace((BlockPos)this.mutablePos.set(feet, -1, -1, 0)) && (canMine((BlockPos)this.mutablePos.set(feet, -1, 0, 0)) || isAir((BlockPos)this.mutablePos.set(feet, -1, 0, 0))) && safeCheck((BlockPos)this.mutablePos.set(feet, -2, 0, 0)) && ((Boolean)this.cycle.getValue()).booleanValue()) {
/* 116 */       surroundMine(this.mutablePos.set(feet, -1, 1, 0));
/* 117 */     } else if (canMine((BlockPos)this.mutablePos.set(feet, 0, 1, 1)) && mayPlace((BlockPos)this.mutablePos.set(feet, 0, -1, 1)) && (canMine((BlockPos)this.mutablePos.set(feet, 0, 0, 1)) || isAir((BlockPos)this.mutablePos.set(feet, 0, 0, 1))) && safeCheck((BlockPos)this.mutablePos.set(feet, 0, 0, 2)) && ((Boolean)this.cycle.getValue()).booleanValue()) {
/* 118 */       surroundMine(this.mutablePos.set(feet, 0, 1, 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void surroundMine(BlockPos.MutableBlockPos position) {
/* 125 */     if (BreakManager.SelfMine((BlockPos)position))
/*     */       return; 
/* 127 */     if (InstantMine.breakPos != null && BlockUtil.getBlock(InstantMine.breakPos) == Blocks.REDSTONE_BLOCK)
/*     */       return; 
/* 129 */     if (InstantMine.breakPos != null && BlockUtil.getBlock(InstantMine.breakPos) != Blocks.AIR && mc.player.getDistanceSq(InstantMine.breakPos) <= MathUtil.square(6.0D)) {
/*     */       return;
/*     */     }
/* 132 */     if (BreakManager.SelfMine(new BlockPos(mc.player.posX, mc.player.posY + 2.0D, mc.player.posZ)) || BreakManager.SelfMine(new BlockPos(mc.player.posX, mc.player.posY + 3.0D, mc.player.posZ)) || BreakManager.SelfMine(new BlockPos(mc.player.posX, mc.player.posY - 1.0D, mc.player.posZ)))
/*     */       return; 
/* 134 */     if (mc.player.rotationPitch <= 90.0F && mc.player.rotationPitch >= 80.0F) {
/*     */       return;
/*     */     }
/* 137 */     BlockPos immutablePos = position.toImmutable();
/* 138 */     mc.playerController.onPlayerDamageBlock(immutablePos, BlockUtil.getRayTraceFacing(immutablePos));
/*     */   }
/*     */   
/*     */   private boolean detection(EntityPlayer player) {
/* 142 */     BlockPos target = EntityUtil.getPlayerPos(player);
/* 143 */     return (canPlace((BlockPos)this.mutablePos.set(target, 1, -1, 0)) || (canPlace((BlockPos)this.mutablePos.set(target, 2, -1, 0)) && isAir((BlockPos)this.mutablePos.set(target, 1, 0, 0))) || (canPlace((BlockPos)this.mutablePos.set(target, 3, -1, 0)) && isAir((BlockPos)this.mutablePos.set(target, 1, 0, 0)) && isAir((BlockPos)this.mutablePos.set(target, 2, 0, 0))) || 
/* 144 */       canPlace((BlockPos)this.mutablePos.set(target, -1, -1, 0)) || (canPlace((BlockPos)this.mutablePos.set(target, -2, -1, 0)) && isAir((BlockPos)this.mutablePos.set(target, -1, 0, 0))) || (canPlace((BlockPos)this.mutablePos.set(target, -3, -1, 0)) && isAir((BlockPos)this.mutablePos.set(target, -1, 0, 0)) && isAir((BlockPos)this.mutablePos.set(target, -2, 0, 0))) || 
/* 145 */       canPlace((BlockPos)this.mutablePos.set(target, 0, -1, 1)) || (canPlace((BlockPos)this.mutablePos.set(target, 0, -1, 2)) && isAir((BlockPos)this.mutablePos.set(target, 0, 0, 1))) || (canPlace((BlockPos)this.mutablePos.set(target, 0, -1, 3)) && isAir((BlockPos)this.mutablePos.set(target, 0, 0, 1)) && isAir((BlockPos)this.mutablePos.set(target, 0, 0, 2))) || 
/* 146 */       canPlace((BlockPos)this.mutablePos.set(target, 0, -1, -1)) || (canPlace((BlockPos)this.mutablePos.set(target, 0, -1, -2)) && isAir((BlockPos)this.mutablePos.set(target, 0, 0, -1))) || (canPlace((BlockPos)this.mutablePos.set(target, 0, -1, -3)) && isAir((BlockPos)this.mutablePos.set(target, 0, 0, -1)) && isAir((BlockPos)this.mutablePos.set(target, 0, 0, -2))));
/*     */   }
/*     */   
/*     */   private boolean skip(EntityPlayer player) {
/* 150 */     BlockPos target = EntityUtil.getPlayerPos(player);
/* 151 */     return (godblock((BlockPos)this.mutablePos.set(target, 1, 0, 0)) && godblock((BlockPos)this.mutablePos.set(target, -1, 0, 0)) && godblock((BlockPos)this.mutablePos.set(target, 0, 0, 1)) && godblock((BlockPos)this.mutablePos.set(target, 0, 0, -1)) && isAir(target) && !canMine((BlockPos)this.mutablePos.set(target, 0, -1, 0)));
/*     */   }
/*     */   
/*     */   private boolean detection2(EntityPlayer player) {
/* 155 */     return (mc.world.getBlockState(new BlockPos(player.posX + 1.2D, player.posY, player.posZ)).getBlock() == Blocks.AIR || mc.world.getBlockState(new BlockPos(player.posX - 1.2D, player.posY, player.posZ)).getBlock() == Blocks.AIR || mc.world
/* 156 */       .getBlockState(new BlockPos(player.posX, player.posY, player.posZ + 1.2D)).getBlock() == Blocks.AIR || mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ - 1.2D)).getBlock() == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private boolean placeCheck(BlockPos block) {
/* 160 */     if (!((Boolean)this.mineweb.getValue()).booleanValue() && BlockUtil.getBlock(block).equals(Blocks.WEB))
/* 161 */       return false; 
/* 162 */     return (BlockUtil.getMineDistance(block) <= MathUtil.square(((Float)this.range.getValue()).floatValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityPlayer getTarget(double range) {
/* 167 */     EntityPlayer target = null;
/* 168 */     double distance = range;
/* 169 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 170 */       if (EntityUtil.isntValid((Entity)player, range))
/*     */         continue; 
/* 172 */       if (skip(player))
/*     */         continue; 
/* 174 */       if (target == null) {
/* 175 */         target = player;
/* 176 */         distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/* 179 */       if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
/*     */         continue; 
/* 181 */       target = player;
/* 182 */       distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 184 */     return target;
/*     */   }
/*     */   
/*     */   private boolean canMine(BlockPos block) {
/* 188 */     return (!BlockUtil.godBlocks.contains(BlockUtil.getBlock(block)) && placeCheck(block));
/*     */   }
/*     */   
/*     */   private boolean isAir(BlockPos block) {
/* 192 */     return (BlockUtil.getBlock(block) == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private boolean godblock(BlockPos block) {
/* 196 */     return (BlockUtil.getBlock(block) == Blocks.BEDROCK);
/*     */   }
/*     */   
/*     */   private boolean canPlace(BlockPos block) {
/* 200 */     return ((BlockUtil.getBlock(block) == Blocks.OBSIDIAN || BlockUtil.getBlock(block) == Blocks.BEDROCK) && BlockUtil.getBlock(block.add(0, 1, 0)) == Blocks.AIR && BlockUtil.getBlock(block.add(0, 2, 0)) == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private boolean mayPlace(BlockPos block) {
/* 204 */     return (BlockUtil.getBlock(block) == Blocks.OBSIDIAN || BlockUtil.getBlock(block) == Blocks.BEDROCK);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AutoCity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
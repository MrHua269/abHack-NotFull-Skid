/*     */ package me.abHack.features.modules.combat;
/*     */ 
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Bind;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class KeyCity extends Module {
/*     */   public static EntityPlayer target;
/*  19 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(1.0F), Float.valueOf(6.0F))); public static BlockPos feet;
/*  20 */   private final Setting<Boolean> mineweb = register(new Setting("Mine Web", Boolean.valueOf(false)));
/*  21 */   private final Setting<Bind> bind = register(new Setting("Enable", new Bind(-1)));
/*     */   
/*     */   public KeyCity() {
/*  24 */     super("KeyCity", "KeyCity", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  28 */     if (fullNullCheck())
/*     */       return; 
/*  30 */     if (InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1)
/*     */       return; 
/*  32 */     target = getTarget(((Float)this.range.getValue()).floatValue());
/*  33 */     surroundMine();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/*  38 */     if (target != null) {
/*  39 */       return target.getName();
/*     */     }
/*  41 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void surroundMine() {
/*  46 */     if (target == null)
/*     */       return; 
/*  48 */     feet = EntityUtil.getPlayerPos(target);
/*  49 */     if (((Bind)this.bind.getValue()).isDown()) {
/*  50 */       boolean ab = true;
/*  51 */       int ez = 0;
/*  52 */       if (canMine(feet)) {
/*  53 */         surroundMine(feet.add(0.0D, 0.5D, 0.0D));
/*  54 */         ab = false;
/*     */       } 
/*  56 */       if (ab)
/*     */       {
/*  58 */         if (canMine(feet.add(1, 0, 0)) && canMine(feet.add(2, 0, 0)) && (canPlace(feet.add(3, -1, 0)) || (isAir(feet.add(2, 1, 0)) && mayPlace(feet.add(2, -1, 0))))) {
/*  59 */           surroundMine(feet.add(2, 0, 0));
/*  60 */           ez = 1;
/*  61 */         } else if (canMine(feet.add(0, 0, -1)) && canMine(feet.add(0, 0, -2)) && (canPlace(feet.add(0, -1, -3)) || (isAir(feet.add(0, 1, -2)) && mayPlace(feet.add(0, -1, -2))))) {
/*  62 */           surroundMine(feet.add(0, 0, -2));
/*  63 */           ez = 2;
/*  64 */         } else if (canMine(feet.add(0, 0, 1)) && canMine(feet.add(0, 0, 2)) && (canPlace(feet.add(0, -1, 3)) || (isAir(feet.add(0, 1, 2)) && mayPlace(feet.add(0, -1, 2))))) {
/*  65 */           surroundMine(feet.add(0, 0, 2));
/*  66 */           ez = 3;
/*  67 */         } else if (canMine(feet.add(-1, 0, 0)) && canMine(feet.add(-2, 0, 0)) && (canPlace(feet.add(-3, -1, 0)) || (isAir(feet.add(-2, 1, 0)) && mayPlace(feet.add(-2, -1, 0))))) {
/*  68 */           surroundMine(feet.add(-2, 0, 0));
/*  69 */           ez = 4;
/*  70 */         } else if (canMine(feet.add(1, 0, 0)) && canMine(feet.add(1, 1, 0)) && mayPlace(feet.add(1, -1, 0))) {
/*  71 */           surroundMine(feet.add(1, 1, 0));
/*  72 */           ez = 1;
/*  73 */         } else if (canMine(feet.add(0, 0, -1)) && canMine(feet.add(0, 1, -1)) && mayPlace(feet.add(0, -1, -1))) {
/*  74 */           surroundMine(feet.add(0, 1, -1));
/*  75 */           ez = 2;
/*  76 */         } else if (canMine(feet.add(0, 0, 1)) && canMine(feet.add(0, 1, 1)) && mayPlace(feet.add(0, -1, 1))) {
/*  77 */           surroundMine(feet.add(0, 1, 1));
/*  78 */           ez = 3;
/*  79 */         } else if (canMine(feet.add(-1, 0, 0)) && canMine(feet.add(-1, 1, 0)) && mayPlace(feet.add(-1, -1, 0))) {
/*  80 */           surroundMine(feet.add(-1, 1, 0));
/*  81 */           ez = 4;
/*  82 */         } else if (canMine(feet.add(-1, 0, 0)) && (mayPlace(feet.add(-1, -1, 0)) || canPlace(feet.add(-2, -1, 0)) || (canMine(feet.add(-2, 0, 0)) && canPlace(feet.add(-3, -1, 0))))) {
/*  83 */           surroundMine(feet.add(-1, 0, 0));
/*  84 */         } else if (canMine(feet.add(0, 0, 1)) && (mayPlace(feet.add(0, -1, 1)) || canPlace(feet.add(0, -1, 2)) || (canMine(feet.add(0, 0, 2)) && canPlace(feet.add(0, -1, 3))))) {
/*  85 */           surroundMine(feet.add(0, 0, 1));
/*  86 */         } else if (canMine(feet.add(0, 0, -1)) && (mayPlace(feet.add(0, -1, -1)) || canPlace(feet.add(0, -1, -2)) || (canMine(feet.add(0, 0, -2)) && canPlace(feet.add(0, -1, -3))))) {
/*  87 */           surroundMine(feet.add(0, 0, -1));
/*  88 */         } else if (canMine(feet.add(1, 0, 0)) && (mayPlace(feet.add(1, -1, 0)) || canPlace(feet.add(2, -1, 0)) || (canMine(feet.add(2, 0, 0)) && canPlace(feet.add(3, -1, 0))))) {
/*  89 */           surroundMine(feet.add(1, 0, 0));
/*     */         } 
/*     */       }
/*  92 */       if (canMine(feet.add(1, 0, 0)) && ((mayPlace(feet.add(1, -1, 0)) && canMine(feet.add(1, 1, 0))) || canPlace(feet.add(2, -1, 0)) || isAir(feet.add(2, 1, 0)) || (canMine(feet.add(2, 0, 0)) && canPlace(feet.add(3, -1, 0)))) && (ez == 1 || ez == 0)) {
/*  93 */         surroundMine(feet.add(1, 0, 0));
/*  94 */       } else if (canMine(feet.add(0, 0, -1)) && ((mayPlace(feet.add(0, -1, -1)) && canMine(feet.add(0, 1, -1))) || canPlace(feet.add(0, -1, -2)) || isAir(feet.add(0, 1, -2)) || (canMine(feet.add(0, 0, -2)) && canPlace(feet.add(0, -1, -3)))) && (ez == 2 || ez == 0)) {
/*  95 */         surroundMine(feet.add(0, 0, -1));
/*  96 */       } else if (canMine(feet.add(0, 0, 1)) && ((mayPlace(feet.add(0, -1, 1)) && canMine(feet.add(0, 1, 1))) || canPlace(feet.add(0, -1, 2)) || isAir(feet.add(0, 1, 2)) || (canMine(feet.add(0, 0, 2)) && canPlace(feet.add(0, -1, 3)))) && (ez == 3 || ez == 0)) {
/*  97 */         surroundMine(feet.add(0, 0, 1));
/*  98 */       } else if (canMine(feet.add(-1, 0, 0)) && ((mayPlace(feet.add(-1, -1, 0)) && canMine(feet.add(-1, 1, 0))) || canPlace(feet.add(-2, -1, 0)) || isAir(feet.add(2, 1, 0)) || (canMine(feet.add(-2, 0, 0)) && canPlace(feet.add(-3, -1, 0)))) && (ez == 4 || ez == 0)) {
/*  99 */         surroundMine(feet.add(-1, 0, 0));
/*     */       }
/* 101 */       else if (canMine(feet.add(1, 1, 0)) && mayPlace(feet.add(1, -1, 0)) && isAir(feet.add(1, 0, 0))) {
/* 102 */         surroundMine(feet.add(1, 1, 0));
/* 103 */       } else if (canMine(feet.add(0, 1, -1)) && mayPlace(feet.add(0, -1, -1)) && isAir(feet.add(0, 0, -1))) {
/* 104 */         surroundMine(feet.add(0, 1, -1));
/* 105 */       } else if (canMine(feet.add(0, 1, 1)) && mayPlace(feet.add(0, -1, 1)) && isAir(feet.add(0, 0, 1))) {
/* 106 */         surroundMine(feet.add(0, 1, 1));
/* 107 */       } else if (canMine(feet.add(-1, 1, 0)) && mayPlace(feet.add(-1, -1, 0)) && isAir(feet.add(-1, 0, 0))) {
/* 108 */         surroundMine(feet.add(-1, 1, 0));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void surroundMine(BlockPos position) {
/* 116 */     if (BreakManager.SelfMine(position)) {
/*     */       return;
/*     */     }
/* 119 */     mc.playerController.onPlayerDamageBlock(position, BlockUtil.getRayTraceFacing(position));
/*     */   }
/*     */   
/*     */   private boolean placeCheck(BlockPos block) {
/* 123 */     if (!((Boolean)this.mineweb.getValue()).booleanValue() && BlockUtil.getBlock(block).equals(Blocks.WEB) && InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD) == -1)
/* 124 */       return false; 
/* 125 */     return (BlockUtil.getMineDistance(block) <= MathUtil.square(((Float)this.range.getValue()).floatValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityPlayer getTarget(double range) {
/* 130 */     EntityPlayer target = null;
/* 131 */     double distance = range;
/* 132 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 133 */       if (EntityUtil.isntValid((Entity)player, range))
/*     */         continue; 
/* 135 */       if (skip(player))
/*     */         continue; 
/* 137 */       if (target == null) {
/* 138 */         target = player;
/* 139 */         distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/* 142 */       if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
/*     */         continue; 
/* 144 */       target = player;
/* 145 */       distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 147 */     return target;
/*     */   }
/*     */   
/*     */   private boolean skip(EntityPlayer player) {
/* 151 */     BlockPos target = EntityUtil.getPlayerPos(player);
/* 152 */     return (godblock(target.add(1, 0, 0)) && godblock(target.add(-1, 0, 0)) && godblock(target.add(0, 0, 1)) && godblock(target.add(0, 0, -1)) && isAir(target));
/*     */   }
/*     */   
/*     */   private boolean godblock(BlockPos block) {
/* 156 */     return (BlockUtil.getBlock(block) == Blocks.BEDROCK);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canMine(BlockPos block) {
/* 161 */     return (!BlockUtil.godBlocks.contains(BlockUtil.getBlock(block)) && placeCheck(block));
/*     */   }
/*     */   
/*     */   private boolean isAir(BlockPos block) {
/* 165 */     return (BlockUtil.getBlock(block) == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private boolean canPlace(BlockPos block) {
/* 169 */     return ((BlockUtil.getBlock(block) == Blocks.OBSIDIAN || BlockUtil.getBlock(block) == Blocks.BEDROCK) && BlockUtil.getBlock(block.add(0, 1, 0)) == Blocks.AIR && BlockUtil.getBlock(block.add(0, 2, 0)) == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private boolean mayPlace(BlockPos block) {
/* 173 */     return (BlockUtil.getBlock(block) == Blocks.OBSIDIAN || BlockUtil.getBlock(block) == Blocks.BEDROCK);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\KeyCity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class CityRender extends Module {
/*  16 */   private final Setting<Integer> range = register(new Setting("Range", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(10)));
/*     */   
/*     */   public EntityPlayer target;
/*     */   
/*     */   public CityRender() {
/*  21 */     super("CityRender", "CityRender", Module.Category.RENDER, true, false, false);
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  25 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  28 */     this.target = getTarget(((Integer)this.range.getValue()).intValue());
/*  29 */     surroundRender();
/*     */   }
/*     */   
/*     */   private void surroundRender() {
/*  33 */     if (this.target == null) {
/*     */       return;
/*     */     }
/*  36 */     BlockPos feet = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
/*  37 */     int ez = 0;
/*     */     
/*  39 */     if (canMine(feet.add(1, 0, 0)) && (canPlace(feet.add(2, -1, 0)) || (canPlace(feet.add(3, -1, 0)) && isAir(feet.add(2, 0, 0))))) {
/*  40 */       surroundRender(feet.add(1, 0, 0), 1);
/*  41 */       ez = 1;
/*  42 */     } else if (canMine(feet.add(0, 0, -1)) && (canPlace(feet.add(0, -1, -2)) || (canPlace(feet.add(0, -1, -3)) && isAir(feet.add(0, 0, -2))))) {
/*  43 */       surroundRender(feet.add(0, 0, -1), 1);
/*  44 */       ez = 2;
/*  45 */     } else if (canMine(feet.add(-1, 0, 0)) && (canPlace(feet.add(-2, -1, 0)) || (canPlace(feet.add(-3, -1, 0)) && isAir(feet.add(-2, 0, 0))))) {
/*  46 */       surroundRender(feet.add(-1, 0, 0), 1);
/*  47 */       ez = 3;
/*  48 */     } else if (canMine(feet.add(0, 0, 1)) && (canPlace(feet.add(0, -1, 2)) || (canPlace(feet.add(0, -1, 3)) && isAir(feet.add(0, 0, 2))))) {
/*  49 */       surroundRender(feet.add(0, 0, 1), 1);
/*  50 */       ez = 4;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  55 */     else if (isAir(feet.add(1, 0, 0)) && !isAir(feet.add(1, 1, 0)) && canMine(feet.add(2, 0, 0)) && (canPlace(feet.add(3, -1, 0)) || isAir(feet.add(2, 1, 0)))) {
/*  56 */       surroundRender(feet.add(2, 0, 0), 1);
/*  57 */     } else if (isAir(feet.add(-1, 0, 0)) && !isAir(feet.add(-1, 1, 0)) && canMine(feet.add(-2, 0, 0)) && (canPlace(feet.add(-3, -1, 0)) || isAir(feet.add(-2, 1, 0)))) {
/*  58 */       surroundRender(feet.add(-2, 0, 0), 1);
/*  59 */     } else if (isAir(feet.add(0, 0, 1)) && !isAir(feet.add(0, 1, 1)) && canMine(feet.add(0, 0, 2)) && (canPlace(feet.add(0, -1, 3)) || isAir(feet.add(0, 1, 2)))) {
/*  60 */       surroundRender(feet.add(0, 0, 2), 1);
/*  61 */     } else if (isAir(feet.add(0, 0, -1)) && !isAir(feet.add(0, 1, -1)) && canMine(feet.add(0, 0, -2)) && (canPlace(feet.add(0, -1, -3)) || isAir(feet.add(0, 1, -2)))) {
/*  62 */       surroundRender(feet.add(0, 0, -2), 1);
/*     */     
/*     */     }
/*  65 */     else if (canMine(feet.add(0, -1, 0)) && (canMine(feet.add(1, -1, 0)) || isAir(feet.add(1, -1, 0))) && (canPlace(feet.add(2, -2, 0)) || (canPlace(feet.add(3, -2, 0)) && isAir(feet.add(2, -1, 0))))) {
/*  66 */       surroundRender(feet.add(0, -1, 0), 4);
/*  67 */     } else if (canMine(feet.add(0, -1, 0)) && (canMine(feet.add(0, -1, -1)) || isAir(feet.add(0, -1, -1))) && (canPlace(feet.add(0, -2, -2)) || (canPlace(feet.add(0, -2, -3)) && isAir(feet.add(0, -1, -2))))) {
/*  68 */       surroundRender(feet.add(0, -1, 0), 4);
/*  69 */     } else if (canMine(feet.add(0, -1, 0)) && (canMine(feet.add(-1, -1, 0)) || isAir(feet.add(-1, -1, 0))) && (canPlace(feet.add(-2, -2, 0)) || (canPlace(feet.add(-3, -2, 0)) && isAir(feet.add(-2, -1, 0))))) {
/*  70 */       surroundRender(feet.add(0, -1, 0), 4);
/*  71 */     } else if (canMine(feet.add(0, -1, 0)) && (canMine(feet.add(0, -1, 1)) || isAir(feet.add(0, -1, 1))) && (canPlace(feet.add(0, -2, 2)) || (canPlace(feet.add(0, -2, 3)) && isAir(feet.add(0, -1, 2))))) {
/*  72 */       surroundRender(feet.add(0, -1, 0), 4);
/*     */     
/*     */     }
/*  75 */     else if (canMine(feet.add(1, 0, 0)) && mayPlace(feet.add(1, -1, 0)) && !canPlace(feet.add(2, -1, 0)) && isAir(feet.add(1, 1, 0))) {
/*  76 */       surroundRender(feet.add(1, 0, 0), 2);
/*  77 */       ez = 5;
/*  78 */     } else if (canMine(feet.add(0, 0, -1)) && mayPlace(feet.add(0, -1, -1)) && !canPlace(feet.add(0, -1, -2)) && isAir(feet.add(0, 1, -1))) {
/*  79 */       surroundRender(feet.add(0, 0, -1), 2);
/*  80 */       ez = 6;
/*  81 */     } else if (canMine(feet.add(-1, 0, 0)) && mayPlace(feet.add(-1, -1, 0)) && !canPlace(feet.add(-2, -1, 0)) && isAir(feet.add(-1, 1, 0))) {
/*  82 */       surroundRender(feet.add(-1, 0, 0), 2);
/*  83 */       ez = 7;
/*  84 */     } else if (canMine(feet.add(0, 0, 1)) && mayPlace(feet.add(0, -1, 1)) && !canPlace(feet.add(0, -1, 2)) && isAir(feet.add(0, 1, 1))) {
/*  85 */       surroundRender(feet.add(0, 0, 1), 2);
/*  86 */       ez = 8;
/*  87 */     } else if (isAir(feet.add(1, 0, 0)) && canMine(feet.add(1, 1, 0)) && mayPlace(feet.add(1, -1, 0)) && !canPlace(feet.add(2, -1, 0))) {
/*  88 */       surroundRender(feet.add(1, 1, 0), 2);
/*  89 */     } else if (isAir(feet.add(0, 0, 1)) && canMine(feet.add(0, 1, 1)) && mayPlace(feet.add(0, -1, 1)) && !canPlace(feet.add(0, -1, 2))) {
/*  90 */       surroundRender(feet.add(0, 1, 1), 2);
/*  91 */     } else if (isAir(feet.add(0, 0, -1)) && canMine(feet.add(0, 1, -1)) && mayPlace(feet.add(0, -1, -1)) && !canPlace(feet.add(0, -1, -2))) {
/*  92 */       surroundRender(feet.add(0, 1, -1), 2);
/*  93 */     } else if (isAir(feet.add(-1, 0, 0)) && canMine(feet.add(-1, 1, 0)) && mayPlace(feet.add(-1, -1, 0)) && !canPlace(feet.add(-2, -1, 0))) {
/*  94 */       surroundRender(feet.add(-1, 1, 0), 2);
/*     */     } 
/*     */     
/*  97 */     if (canMine(feet.add(1, 0, 0)) && canMine(feet.add(2, 0, 0)) && (canPlace(feet.add(3, -1, 0)) || (isAir(feet.add(2, 1, 0)) && mayPlace(feet.add(2, -1, 0))))) {
/*  98 */       if (ez != 5)
/*  99 */         surroundRender(feet.add(1, 0, 0), 3); 
/* 100 */       surroundRender(feet.add(2, 0, 0), 3);
/*     */     } 
/* 102 */     if (canMine(feet.add(0, 0, -1)) && canMine(feet.add(0, 0, -2)) && (canPlace(feet.add(0, -1, -3)) || (isAir(feet.add(0, 1, -2)) && mayPlace(feet.add(0, -1, -2))))) {
/* 103 */       if (ez != 6)
/* 104 */         surroundRender(feet.add(0, 0, -1), 3); 
/* 105 */       surroundRender(feet.add(0, 0, -2), 3);
/*     */     } 
/* 107 */     if (canMine(feet.add(-1, 0, 0)) && canMine(feet.add(-2, 0, 0)) && (canPlace(feet.add(-3, -1, 0)) || (isAir(feet.add(-2, 1, 0)) && mayPlace(feet.add(-2, -1, 0))))) {
/* 108 */       if (ez != 7)
/* 109 */         surroundRender(feet.add(-1, 0, 0), 3); 
/* 110 */       surroundRender(feet.add(-2, 0, 0), 3);
/*     */     } 
/* 112 */     if (canMine(feet.add(0, 0, 1)) && canMine(feet.add(0, 0, 2)) && (canPlace(feet.add(0, -1, 3)) || (isAir(feet.add(0, 1, 2)) && mayPlace(feet.add(0, -1, 2))))) {
/* 113 */       if (ez != 8)
/* 114 */         surroundRender(feet.add(0, 0, 1), 3); 
/* 115 */       surroundRender(feet.add(0, 0, 2), 3);
/*     */     } 
/* 117 */     if (canMine(feet.add(1, 0, 0)) && canMine(feet.add(1, 1, 0)) && mayPlace(feet.add(1, -1, 0))) {
/* 118 */       if (ez != 1)
/* 119 */         surroundRender(feet.add(1, 0, 0), 3); 
/* 120 */       surroundRender(feet.add(1, 1, 0), 3);
/*     */     } 
/* 122 */     if (canMine(feet.add(0, 0, -1)) && canMine(feet.add(0, 1, -1)) && mayPlace(feet.add(0, -1, -1))) {
/* 123 */       if (ez != 2)
/* 124 */         surroundRender(feet.add(0, 0, -1), 3); 
/* 125 */       surroundRender(feet.add(0, 1, -1), 3);
/*     */     } 
/* 127 */     if (canMine(feet.add(-1, 0, 0)) && canMine(feet.add(-1, 1, 0)) && mayPlace(feet.add(-1, -1, 0))) {
/* 128 */       if (ez != 3)
/* 129 */         surroundRender(feet.add(-1, 0, 0), 3); 
/* 130 */       surroundRender(feet.add(-1, 1, 0), 3);
/*     */     } 
/* 132 */     if (canMine(feet.add(0, 0, 1)) && canMine(feet.add(0, 1, 1)) && mayPlace(feet.add(0, -1, 1))) {
/* 133 */       if (ez != 4)
/* 134 */         surroundRender(feet.add(0, 0, 1), 3); 
/* 135 */       surroundRender(feet.add(0, 1, 1), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void surroundRender(BlockPos position, int rbg) {
/* 142 */     RenderUtil.prepareGL3D();
/* 143 */     if (rbg == 1) {
/* 144 */       RenderUtil.drawBoxESP(position, new Color(255, 0, 0), false, new Color(255, 0, 0), 1.0F, false, true, 42, true);
/* 145 */     } else if (rbg == 2) {
/* 146 */       RenderUtil.drawBoxESP(position, new Color(255, 255, 0), false, new Color(255, 255, 0), 1.0F, false, true, 42, true);
/* 147 */     } else if (rbg == 3) {
/* 148 */       RenderUtil.drawBoxESP(position, new Color(0, 0, 255), false, new Color(0, 0, 255), 1.0F, false, true, 42, true);
/* 149 */     } else if (rbg == 4) {
/* 150 */       RenderUtil.drawBoxESP(position, new Color(128, 0, 128), false, new Color(128, 0, 128), 1.0F, false, true, 42, true);
/* 151 */     }  RenderUtil.releaseGL3D();
/*     */   }
/*     */   
/*     */   private EntityPlayer getTarget(double range) {
/* 155 */     EntityPlayer target = null;
/* 156 */     double distance = range;
/* 157 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 158 */       if (EntityUtil.isntValid((Entity)player, range))
/*     */         continue; 
/* 160 */       if (target == null) {
/* 161 */         target = player;
/* 162 */         distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/* 165 */       if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
/*     */         continue; 
/* 167 */       target = player;
/* 168 */       distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 170 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canMine(BlockPos block) {
/* 175 */     return !BlockUtil.godBlocks.contains(mc.world.getBlockState(block).getBlock());
/*     */   }
/*     */   
/*     */   private boolean isAir(BlockPos block) {
/* 179 */     return (mc.world.getBlockState(block).getBlock() == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private boolean canPlace(BlockPos block) {
/* 183 */     return ((mc.world.getBlockState(block).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(block).getBlock() == Blocks.BEDROCK) && mc.world.getBlockState(block.add(0, 1, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(block.add(0, 2, 0)).getBlock() == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private boolean mayPlace(BlockPos block) {
/* 187 */     return (mc.world.getBlockState(block).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(block).getBlock() == Blocks.BEDROCK);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\CityRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
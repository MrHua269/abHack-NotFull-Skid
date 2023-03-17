/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.render.HoleEsp.HoleUtil;
/*     */ import me.abHack.util.render.HoleEsp.TessellatorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class HoleESP extends Module {
/*  18 */   private final Setting<Integer> rangeXZ = register(new Setting("RangeXZ", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(25)));
/*  19 */   private final Setting<Integer> rangeY = register(new Setting("RangeY", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(25)));
/*     */   
/*  21 */   private final Setting<Float> width = register(new Setting("Width", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(10.0F)));
/*  22 */   private final Setting<Float> height = register(new Setting("Height", Float.valueOf(1.0F), Float.valueOf(-2.0F), Float.valueOf(8.0F)));
/*     */   
/*  24 */   private final Setting<Integer> fadeAlpha = register(new Setting("FadeAlpha", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/*  25 */   private final Setting<Boolean> depth = register(new Setting("Depth", Boolean.valueOf(true)));
/*  26 */   private final Setting<Boolean> noLineDepth = register(new Setting("NotLines", Boolean.valueOf(true), v -> ((Boolean)this.depth.getValue()).booleanValue()));
/*  27 */   private final Setting<Lines> lines = register(new Setting("Lines", Lines.BOTTOM));
/*  28 */   private final Setting<Boolean> sides = register(new Setting("Sides", Boolean.valueOf(false)));
/*  29 */   private final Setting<Boolean> notSelf = register(new Setting("NotSelf", Boolean.valueOf(true)));
/*     */   
/*  31 */   private final Setting<Boolean> twoBlock = register(new Setting("TwoBlock", Boolean.valueOf(true)));
/*     */   
/*  33 */   private final Setting<Boolean> bedrock = register(new Setting("Bedrock", Boolean.valueOf(true)));
/*  34 */   private final Setting<Integer> bRockHoleRed = register(new Setting("bRockHoleRed", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*  35 */   private final Setting<Integer> bRockHoleGrass = register(new Setting("bRockHoleGrass", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*  36 */   private final Setting<Integer> bRockHoleBlue = register(new Setting("bRockHoleBlue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*  37 */   private final Setting<Integer> bRockHoleAlpha = register(new Setting("bRockHoleBlueAlpha", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*     */   
/*  39 */   private final Setting<Integer> bRockLineRed = register(new Setting("DoulebRockLineRed", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*  40 */   private final Setting<Integer> bRockLineGrass = register(new Setting("DoulebRockLineGrass", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*  41 */   private final Setting<Integer> bRockLineBlue = register(new Setting("DoulebRockLineBlue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*  42 */   private final Setting<Integer> bRockLineAlpha = register(new Setting("DoulebRockLineAlpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*     */   
/*  44 */   private final Setting<Boolean> obsidian = register(new Setting("Obsidian", Boolean.valueOf(true)));
/*     */   
/*  46 */   private final Setting<Integer> obiHoleRed = register(new Setting("obiHoleRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.obsidian.getValue()).booleanValue()));
/*  47 */   private final Setting<Integer> obiHoleGrass = register(new Setting("obiHoleGrass", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.obsidian.getValue()).booleanValue()));
/*  48 */   private final Setting<Integer> obiHoleBlue = register(new Setting("obiHoleBlue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.obsidian.getValue()).booleanValue()));
/*  49 */   private final Setting<Integer> obiHoleAlpha = register(new Setting("obiHoleAlpha", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*     */   
/*  51 */   private final Setting<Integer> obiLineHoleRed = register(new Setting("DouleObiHoleRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.obsidian.getValue()).booleanValue()));
/*  52 */   private final Setting<Integer> obiLineHoleGrass = register(new Setting("DouleObiHoleGrass", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.obsidian.getValue()).booleanValue()));
/*  53 */   private final Setting<Integer> obiLineHoleBlue = register(new Setting("DouleObiHoleBlue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.obsidian.getValue()).booleanValue()));
/*  54 */   private final Setting<Integer> obiLineHoleAlpha = register(new Setting("DouleObiHoleAlpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.bedrock.getValue()).booleanValue()));
/*  55 */   private final List<BlockPos> obiHoles = new ArrayList<>();
/*  56 */   private final List<BlockPos> bedrockHoles = new ArrayList<>();
/*  57 */   private final List<TwoBlockHole> obiHolesTwoBlock = new ArrayList<>();
/*  58 */   private final List<TwoBlockHole> bedrockHolesTwoBlock = new ArrayList<>();
/*     */   
/*     */   public HoleESP() {
/*  61 */     super("HoleESP", "Shows you holes", Module.Category.RENDER, true, false, false);
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  65 */     if (fullNullCheck())
/*     */       return; 
/*  67 */     this.obiHoles.clear();
/*  68 */     this.bedrockHoles.clear();
/*  69 */     this.obiHolesTwoBlock.clear();
/*  70 */     this.bedrockHolesTwoBlock.clear();
/*  71 */     Iterable<BlockPos> blocks = BlockPos.getAllInBox(mc.player.getPosition().add(-((Integer)this.rangeXZ.getValue()).intValue(), -((Integer)this.rangeY.getValue()).intValue(), -((Integer)this.rangeXZ.getValue()).intValue()), mc.player.getPosition().add(((Integer)this.rangeXZ.getValue()).intValue(), ((Integer)this.rangeY.getValue()).intValue(), ((Integer)this.rangeXZ.getValue()).intValue()));
/*     */     
/*  73 */     for (BlockPos pos : blocks) {
/*     */       
/*  75 */       if (!mc.world.getBlockState(pos).getMaterial().blocksMovement() ||
/*  76 */         !mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement() ||
/*  77 */         !mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial().blocksMovement()) {
/*     */ 
/*     */ 
/*     */         
/*  81 */         if (HoleUtil.validObi(pos) && ((Boolean)this.obsidian.getValue()).booleanValue()) {
/*  82 */           this.obiHoles.add(pos);
/*     */         } else {
/*  84 */           BlockPos blockPos = HoleUtil.validTwoBlockObiXZ(pos);
/*  85 */           if (blockPos != null && ((Boolean)this.obsidian.getValue()).booleanValue() && ((Boolean)this.twoBlock.getValue()).booleanValue()) {
/*  86 */             this.obiHolesTwoBlock.add(new TwoBlockHole(pos, pos.add(blockPos.getX(), blockPos.getY(), blockPos.getZ())));
/*     */           }
/*     */         } 
/*     */         
/*  90 */         if (HoleUtil.validBedrock(pos) && ((Boolean)this.bedrock.getValue()).booleanValue()) {
/*  91 */           this.bedrockHoles.add(pos); continue;
/*     */         } 
/*  93 */         BlockPos validTwoBlock = HoleUtil.validTwoBlockBedrockXZ(pos);
/*  94 */         if (validTwoBlock != null && ((Boolean)this.bedrock.getValue()).booleanValue() && ((Boolean)this.twoBlock.getValue()).booleanValue()) {
/*  95 */           this.bedrockHolesTwoBlock.add(new TwoBlockHole(pos, pos.add(validTwoBlock.getX(), validTwoBlock.getY(), validTwoBlock.getZ())));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Color getColor(int red, int grass, int blue, int alpha) {
/* 106 */     return new Color(red, grass, blue, alpha);
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 110 */     if (mc.world == null || mc.player == null)
/*     */       return; 
/* 112 */     for (BlockPos pos : this.bedrockHoles) {
/* 113 */       drawHole(pos, getColor(((Integer)this.bRockHoleRed.getValue()).intValue(), ((Integer)this.bRockHoleGrass.getValue()).intValue(), ((Integer)this.bRockHoleBlue.getValue()).intValue(), ((Integer)this.bRockHoleAlpha.getValue()).intValue()), getColor(((Integer)this.bRockLineRed.getValue()).intValue(), ((Integer)this.bRockLineGrass.getValue()).intValue(), ((Integer)this.bRockLineBlue.getValue()).intValue(), ((Integer)this.bRockLineAlpha.getValue()).intValue()));
/*     */     }
/*     */     
/* 116 */     for (BlockPos pos : this.obiHoles) {
/* 117 */       drawHole(pos, getColor(((Integer)this.obiHoleRed.getValue()).intValue(), ((Integer)this.obiHoleGrass.getValue()).intValue(), ((Integer)this.obiHoleBlue.getValue()).intValue(), ((Integer)this.obiHoleAlpha.getValue()).intValue()), getColor(((Integer)this.obiLineHoleRed.getValue()).intValue(), ((Integer)this.obiLineHoleGrass.getValue()).intValue(), ((Integer)this.obiLineHoleBlue.getValue()).intValue(), ((Integer)this.obiLineHoleAlpha.getValue()).intValue()));
/*     */     }
/*     */     
/* 120 */     for (TwoBlockHole pos : this.bedrockHolesTwoBlock) {
/* 121 */       drawHoleTwoBlock(pos.getOne(), pos.getExtra(), getColor(((Integer)this.bRockHoleRed.getValue()).intValue(), ((Integer)this.bRockHoleGrass.getValue()).intValue(), ((Integer)this.bRockHoleBlue.getValue()).intValue(), ((Integer)this.bRockHoleAlpha.getValue()).intValue()), getColor(((Integer)this.bRockLineRed.getValue()).intValue(), ((Integer)this.bRockLineGrass.getValue()).intValue(), ((Integer)this.bRockLineBlue.getValue()).intValue(), ((Integer)this.bRockLineAlpha.getValue()).intValue()));
/*     */     }
/*     */     
/* 124 */     for (TwoBlockHole pos : this.obiHolesTwoBlock) {
/* 125 */       drawHoleTwoBlock(pos.getOne(), pos.getExtra(), getColor(((Integer)this.obiHoleRed.getValue()).intValue(), ((Integer)this.obiHoleGrass.getValue()).intValue(), ((Integer)this.obiHoleBlue.getValue()).intValue(), ((Integer)this.obiHoleAlpha.getValue()).intValue()), getColor(((Integer)this.obiLineHoleRed.getValue()).intValue(), ((Integer)this.obiLineHoleGrass.getValue()).intValue(), ((Integer)this.obiLineHoleBlue.getValue()).intValue(), ((Integer)this.obiLineHoleAlpha.getValue()).intValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawHole(BlockPos pos, Color color, Color lineColor) {
/* 133 */     AxisAlignedBB tBB = mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)mc.world, pos).offset(pos);
/* 134 */     tBB = tBB.setMaxY(tBB.minY + ((Float)this.height.getValue()).floatValue());
/*     */     
/* 136 */     if (tBB.intersects(mc.player.getEntityBoundingBox()) && ((Boolean)this.notSelf.getValue()).booleanValue()) {
/* 137 */       tBB = tBB.setMaxY(Math.min(tBB.maxY, mc.player.posY + 1.0D));
/*     */     }
/*     */     
/* 140 */     RenderUtil.prepareGL3D();
/*     */     
/* 142 */     if (((Boolean)this.depth.getValue()).booleanValue()) {
/* 143 */       GlStateManager.enableDepth();
/* 144 */       tBB = tBB.shrink(0.01D);
/*     */     } 
/*     */     
/* 147 */     TessellatorUtil.drawBox(tBB, true, ((Float)this.height.getValue()).floatValue(), color, ((Integer)this.fadeAlpha.getValue()).intValue(), ((Boolean)this.sides.getValue()).booleanValue() ? 60 : 63);
/*     */     
/* 149 */     if (((Float)this.width.getValue()).floatValue() >= 0.1F) {
/* 150 */       if (this.lines.getValue() == Lines.BOTTOM) {
/* 151 */         tBB = new AxisAlignedBB(tBB.minX, tBB.minY, tBB.minZ, tBB.maxX, tBB.minY, tBB.maxZ);
/* 152 */       } else if (this.lines.getValue() == Lines.TOP) {
/* 153 */         tBB = new AxisAlignedBB(tBB.minX, tBB.maxY, tBB.minZ, tBB.maxX, tBB.maxY, tBB.maxZ);
/*     */       } 
/* 155 */       if (((Boolean)this.noLineDepth.getValue()).booleanValue()) {
/* 156 */         GlStateManager.disableDepth();
/*     */       }
/* 158 */       TessellatorUtil.drawBoundingBox(tBB, ((Float)this.width.getValue()).floatValue(), lineColor, ((Integer)this.fadeAlpha.getValue()).intValue());
/*     */     } 
/*     */     
/* 161 */     RenderUtil.releaseGL3D();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawHoleTwoBlock(BlockPos pos, BlockPos two, Color color, Color lineColor) {
/* 166 */     AxisAlignedBB tBB = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (two.getX() + 1), (two.getY() + ((Float)this.height.getValue()).floatValue()), (two.getZ() + 1));
/*     */     
/* 168 */     if (tBB.intersects(mc.player.getEntityBoundingBox()) && ((Boolean)this.notSelf.getValue()).booleanValue()) {
/* 169 */       tBB = tBB.setMaxY(Math.min(tBB.maxY, mc.player.posY + 1.0D));
/*     */     }
/*     */     
/* 172 */     RenderUtil.prepareGL3D();
/*     */     
/* 174 */     if (((Boolean)this.depth.getValue()).booleanValue()) {
/* 175 */       GlStateManager.enableDepth();
/* 176 */       tBB = tBB.shrink(0.01D);
/*     */     } 
/* 178 */     TessellatorUtil.drawBox(tBB, true, ((Float)this.height.getValue()).floatValue(), color, ((Integer)this.fadeAlpha.getValue()).intValue(), ((Boolean)this.sides.getValue()).booleanValue() ? 60 : 63);
/* 179 */     if (((Float)this.width.getValue()).floatValue() >= 0.1F) {
/* 180 */       if (this.lines.getValue() == Lines.BOTTOM) {
/* 181 */         tBB = new AxisAlignedBB(tBB.minX, tBB.minY, tBB.minZ, tBB.maxX, tBB.minY, tBB.maxZ);
/* 182 */       } else if (this.lines.getValue() == Lines.TOP) {
/* 183 */         tBB = new AxisAlignedBB(tBB.minX, tBB.maxY, tBB.minZ, tBB.maxX, tBB.maxY, tBB.maxZ);
/*     */       } 
/* 185 */       if (((Boolean)this.noLineDepth.getValue()).booleanValue()) {
/* 186 */         GlStateManager.disableDepth();
/*     */       }
/* 188 */       TessellatorUtil.drawBoundingBox(tBB, ((Float)this.width.getValue()).floatValue(), lineColor, ((Integer)this.fadeAlpha.getValue()).intValue());
/*     */     } 
/*     */     
/* 191 */     RenderUtil.releaseGL3D();
/*     */   }
/*     */   
/*     */   private enum Lines
/*     */   {
/* 196 */     FULL, BOTTOM, TOP;
/*     */   }
/*     */   
/*     */   private static class TwoBlockHole
/*     */   {
/*     */     private final BlockPos one;
/*     */     private final BlockPos extra;
/*     */     
/*     */     public TwoBlockHole(BlockPos one, BlockPos extra) {
/* 205 */       this.one = one;
/* 206 */       this.extra = extra;
/*     */     }
/*     */     
/*     */     public BlockPos getOne() {
/* 210 */       return this.one;
/*     */     }
/*     */     
/*     */     public BlockPos getExtra() {
/* 214 */       return this.extra;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\HoleESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
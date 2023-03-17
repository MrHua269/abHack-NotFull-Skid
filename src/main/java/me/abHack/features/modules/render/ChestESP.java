/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class ChestESP
/*     */   extends Module {
/*  19 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(60.0F), Float.valueOf(1.0F), Float.valueOf(120.0F)));
/*     */   
/*  21 */   private final Setting<Boolean> chest = register(new Setting("Chest", Boolean.valueOf(true)));
/*     */   
/*  23 */   private final Setting<Boolean> dispenser = register(new Setting("Dispenser", Boolean.valueOf(false)));
/*     */   
/*  25 */   private final Setting<Boolean> shulker = register(new Setting("Shulker", Boolean.valueOf(true)));
/*     */   
/*  27 */   private final Setting<Boolean> echest = register(new Setting("Ender Chest", Boolean.valueOf(true)));
/*     */   
/*  29 */   private final Setting<Boolean> furnace = register(new Setting("Furnace", Boolean.valueOf(false)));
/*     */   
/*  31 */   private final Setting<Boolean> hopper = register(new Setting("Hopper", Boolean.valueOf(false)));
/*     */   
/*  33 */   private final Setting<Boolean> cart = register(new Setting("Minecart", Boolean.valueOf(false)));
/*     */   
/*  35 */   private final Setting<Boolean> frame = register(new Setting("Item Frame", Boolean.valueOf(false)));
/*     */   
/*  37 */   private final Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(false)));
/*     */   
/*  39 */   private final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.box.getValue()).booleanValue()));
/*     */   
/*  41 */   private final Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/*     */   
/*  43 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*     */   
/*     */   public ChestESP() {
/*  46 */     super("ChestESP", "Helps you to see where container blocks are", Module.Category.RENDER, false, false, false);
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  50 */     HashMap<BlockPos, Integer> positions = new HashMap<>();
/*  51 */     for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
/*     */       int color;
/*     */       BlockPos pos;
/*  54 */       if (((!(tileEntity instanceof net.minecraft.tileentity.TileEntityChest) || !((Boolean)this.chest.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityDispenser) || !((Boolean)this.dispenser.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox) || !((Boolean)this.shulker.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest) || !((Boolean)this.echest.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace) || !((Boolean)this.furnace.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityHopper) || !((Boolean)this.hopper.getValue()).booleanValue())) || mc.player.getDistanceSq(pos = tileEntity.getPos()) > MathUtil.square(((Float)this.range.getValue()).floatValue()) || (color = getTileEntityColor(tileEntity)) == -1)
/*     */         continue; 
/*  56 */       positions.put(pos, Integer.valueOf(color));
/*     */     } 
/*  58 */     for (Entity entity : mc.world.loadedEntityList) {
/*     */       int color;
/*     */       BlockPos pos;
/*  61 */       if (((!(entity instanceof EntityItemFrame) || !((Boolean)this.frame.getValue()).booleanValue()) && (!(entity instanceof net.minecraft.entity.item.EntityMinecartChest) || !((Boolean)this.cart.getValue()).booleanValue())) || mc.player.getDistanceSq(pos = entity.getPosition()) > MathUtil.square(((Float)this.range.getValue()).floatValue()) || (color = getEntityColor(entity)) == -1)
/*     */         continue; 
/*  63 */       positions.put(pos, Integer.valueOf(color));
/*     */     } 
/*  65 */     for (Map.Entry<BlockPos, Integer> entry : positions.entrySet()) {
/*  66 */       BlockPos blockPos = entry.getKey();
/*  67 */       int color = ((Integer)entry.getValue()).intValue();
/*  68 */       RenderUtil.prepareGL3D();
/*  69 */       RenderUtil.drawBoxESP(blockPos, new Color(color), false, new Color(color), ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), false);
/*  70 */       RenderUtil.releaseGL3D();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getTileEntityColor(TileEntity tileEntity) {
/*  75 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityChest)
/*  76 */       return Colors.ORANGE; 
/*  77 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox)
/*  78 */       return Colors.PINK; 
/*  79 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest)
/*  80 */       return Colors.PURPLE; 
/*  81 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace)
/*  82 */       return Colors.GRAY; 
/*  83 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityHopper)
/*  84 */       return Colors.GRAY; 
/*  85 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityDispenser)
/*  86 */       return Colors.GRAY; 
/*  87 */     return -1;
/*     */   }
/*     */   
/*     */   private int getEntityColor(Entity entity) {
/*  91 */     if (entity instanceof net.minecraft.entity.item.EntityMinecartChest)
/*  92 */       return Colors.GRAY; 
/*  93 */     if (entity instanceof EntityItemFrame && ((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof net.minecraft.item.ItemShulkerBox)
/*  94 */       return Colors.YELLOW; 
/*  95 */     if (entity instanceof EntityItemFrame && !(((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof net.minecraft.item.ItemShulkerBox))
/*  96 */       return Colors.ORANGE; 
/*  97 */     return -1;
/*     */   }
/*     */   
/*     */   public static abstract class Colors
/*     */   {
/* 102 */     public static final int RED = ColorUtil.toRGBA(255, 0, 0, 155);
/*     */     
/* 104 */     public static final int GREEN = ColorUtil.toRGBA(0, 255, 0, 155);
/*     */     
/* 106 */     public static final int ORANGE = ColorUtil.toRGBA(255, 128, 0, 100);
/*     */     
/* 108 */     public static final int PURPLE = ColorUtil.toRGBA(105, 13, 173, 100);
/*     */     
/* 110 */     public static final int GRAY = ColorUtil.toRGBA(169, 169, 169, 155);
/*     */     
/* 112 */     public static final int YELLOW = ColorUtil.toRGBA(255, 255, 0, 155);
/*     */     
/* 114 */     public static final int PINK = ColorUtil.toRGBA(255, 120, 203, 100);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\ChestESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
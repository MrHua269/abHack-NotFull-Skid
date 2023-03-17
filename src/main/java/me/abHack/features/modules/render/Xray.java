/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class Xray
/*    */   extends Module
/*    */ {
/*    */   public static ArrayList<Block> xrayBlocks;
/*    */   
/*    */   public Xray() {
/* 14 */     super("Xray", "See ores inside the ground.", Module.Category.RENDER, true, false, false);
/* 15 */     initXray();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 20 */     if (mc.world == null)
/* 21 */       return;  mc.renderGlobal.loadRenderers();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 26 */     if (mc.world == null)
/* 27 */       return;  mc.renderGlobal.loadRenderers();
/*    */   }
/*    */   
/*    */   public void initXray() {
/* 31 */     xrayBlocks = new ArrayList<>();
/*    */     
/* 33 */     xrayBlocks.add(Blocks.DIAMOND_ORE);
/* 34 */     xrayBlocks.add(Blocks.DIAMOND_BLOCK);
/* 35 */     xrayBlocks.add(Blocks.EMERALD_ORE);
/* 36 */     xrayBlocks.add(Blocks.EMERALD_BLOCK);
/* 37 */     xrayBlocks.add(Blocks.COAL_ORE);
/* 38 */     xrayBlocks.add(Blocks.COAL_BLOCK);
/* 39 */     xrayBlocks.add(Blocks.GOLD_ORE);
/* 40 */     xrayBlocks.add(Blocks.GOLD_BLOCK);
/* 41 */     xrayBlocks.add(Blocks.IRON_ORE);
/* 42 */     xrayBlocks.add(Blocks.IRON_BLOCK);
/* 43 */     xrayBlocks.add(Blocks.LAPIS_ORE);
/* 44 */     xrayBlocks.add(Blocks.LAPIS_BLOCK);
/* 45 */     xrayBlocks.add(Blocks.REDSTONE_ORE);
/* 46 */     xrayBlocks.add(Blocks.REDSTONE_BLOCK);
/* 47 */     xrayBlocks.add(Blocks.LIT_REDSTONE_ORE);
/* 48 */     xrayBlocks.add(Blocks.REDSTONE_BLOCK);
/* 49 */     xrayBlocks.add(Blocks.QUARTZ_ORE);
/* 50 */     xrayBlocks.add(Blocks.BEACON);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Xray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
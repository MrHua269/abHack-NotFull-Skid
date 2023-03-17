/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.BlockUtil;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import me.abHack.util.MathUtil;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class AntiShulkerBox
/*    */   extends Module
/*    */ {
/* 16 */   private static AntiShulkerBox INSTANCE = new AntiShulkerBox();
/* 17 */   private final Setting<Integer> range = register(new Setting("Range", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(6)));
/* 18 */   private final Setting<Integer> saferange = register(new Setting("SafeRange", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(6)));
/*    */   
/*    */   public AntiShulkerBox() {
/* 21 */     super("AntiShulkerBox", "Automatically dig someone else's box", Module.Category.PLAYER, true, false, false);
/* 22 */     setInstance();
/*    */   }
/*    */   
/*    */   public static AntiShulkerBox getInstance() {
/* 26 */     if (INSTANCE == null) {
/* 27 */       INSTANCE = new AntiShulkerBox();
/*    */     }
/* 29 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 33 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 38 */     if (fullNullCheck())
/*    */       return; 
/* 40 */     int mainSlot = mc.player.inventory.currentItem;
/* 41 */     for (BlockPos blockPos : breakPos(((Integer)this.range.getValue()).intValue())) {
/* 42 */       int slotPick = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
/* 43 */       if (slotPick == -1)
/*    */         return; 
/* 45 */       if (BlockUtil.getMineDistance(blockPos) > MathUtil.square(((Integer)this.range.getValue()).intValue()))
/*    */         continue; 
/* 47 */       if (mc.player.getDistanceSq(blockPos) < MathUtil.square(((Integer)this.saferange.getValue()).intValue()))
/*    */         continue; 
/* 49 */       if (mc.world.getBlockState(blockPos).getBlock() instanceof net.minecraft.block.BlockShulkerBox) {
/* 50 */         mc.player.inventory.currentItem = slotPick;
/* 51 */         mc.player.swingArm(EnumHand.MAIN_HAND);
/* 52 */         mc.playerController.onPlayerDamageBlock(blockPos, BlockUtil.getRayTraceFacing(blockPos));
/*    */         continue;
/*    */       } 
/* 55 */       mc.player.inventory.currentItem = mainSlot;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private NonNullList<BlockPos> breakPos(float placeRange) {
/* 61 */     NonNullList<BlockPos> positions = NonNullList.create();
/* 62 */     positions.addAll(BlockUtil.getSphere(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ)), placeRange, 0, false, true, 0));
/* 63 */     return positions;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\AntiShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
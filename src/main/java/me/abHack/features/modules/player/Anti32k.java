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
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ public class Anti32k
/*    */   extends Module {
/* 17 */   private static Anti32k INSTANCE = new Anti32k();
/* 18 */   private final Setting<Integer> range = register(new Setting("Range", Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(6)));
/*    */   
/*    */   public Anti32k() {
/* 21 */     super("Anti32k", "Anti32k", Module.Category.PLAYER, true, false, false);
/* 22 */     setInstance();
/*    */   }
/*    */   
/*    */   public static Anti32k getInstance() {
/* 26 */     if (INSTANCE == null) {
/* 27 */       INSTANCE = new Anti32k();
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
/* 47 */       if (blockPos != null) {
/* 48 */         if (mc.world.getBlockState(blockPos).getBlock() instanceof net.minecraft.block.BlockHopper && mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() instanceof net.minecraft.block.BlockShulkerBox) {
/* 49 */           mc.player.inventory.currentItem = slotPick;
/* 50 */           mc.player.swingArm(EnumHand.MAIN_HAND);
/* 51 */           mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos.add(0, 1, 0), BlockUtil.getRayTraceFacing(blockPos.add(0, 1, 0)), new Vec3d((Vec3i)blockPos.add(0, 1, 0)), EnumHand.MAIN_HAND);
/* 52 */           mc.playerController.onPlayerDamageBlock(blockPos, BlockUtil.getRayTraceFacing(blockPos));
/*    */           continue;
/*    */         } 
/* 55 */         mc.player.inventory.currentItem = mainSlot;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private NonNullList<BlockPos> breakPos(float placeRange) {
/* 62 */     NonNullList<BlockPos> positions = NonNullList.create();
/* 63 */     positions.addAll(BlockUtil.getSphere(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ)), placeRange, 0, false, true, 0));
/* 64 */     return positions;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Anti32k.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
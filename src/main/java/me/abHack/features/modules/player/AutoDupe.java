/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.BlockUtil;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class AutoDupe
/*    */   extends Module
/*    */ {
/*    */   private final Setting<Integer> delay;
/*    */   private final Timer timer;
/*    */   int box;
/*    */   
/*    */   public AutoDupe() {
/* 21 */     super("AutoDupe", "Automatically places Shulker", Module.Category.PLAYER, true, false, false);
/* 22 */     this.delay = register(new Setting("Delay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(2000)));
/* 23 */     this.timer = new Timer();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 28 */     if (InstantMine.breakPos == null)
/*    */       return; 
/* 30 */     BlockPos pos = InstantMine.breakPos;
/* 31 */     IBlockState blockState = mc.world.getBlockState(pos);
/* 32 */     this.box = getItemShulkerBox();
/* 33 */     if (blockState.getBlock() == Blocks.AIR && this.box != -1) {
/* 34 */       mc.player.inventory.currentItem = this.box;
/* 35 */       BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, false, false, false);
/* 36 */       this.timer.passedDms(((Integer)this.delay.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getItemShulkerBox() {
/* 41 */     int box = -1;
/* 42 */     for (int x = 0; x <= 8; x++) {
/* 43 */       Item item = mc.player.inventory.getStackInSlot(x).getItem();
/* 44 */       if (item instanceof net.minecraft.item.ItemShulkerBox)
/* 45 */         box = x; 
/*    */     } 
/* 47 */     return box;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\AutoDupe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
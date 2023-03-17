/*    */ package me.abHack.features.modules.misc;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ 
/*    */ public class ChestStealer extends Module {
/*    */   public ChestStealer() {
/*  9 */     super("ChestStealer", "steal monkes in chests", Module.Category.MISC, true, false, false);
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 13 */     if (mc.player.openContainer instanceof ContainerChest) {
/* 14 */       ContainerChest chest = (ContainerChest)mc.player.openContainer;
/* 15 */       for (int items = 0; items < chest.getLowerChestInventory().getSizeInventory(); items++) {
/* 16 */         mc.playerController.windowClick(chest.windowId, items, 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
/* 17 */         if (isChestEmpty(chest))
/* 18 */           mc.player.closeScreen(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean isChestEmpty(ContainerChest chest) {
/* 24 */     int items = 0;
/* 25 */     return (items >= chest.getLowerChestInventory().getSizeInventory());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\ChestStealer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
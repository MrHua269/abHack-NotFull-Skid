/*    */ package me.abHack.features.modules.combat;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.modules.player.XCarry;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class AutoTotem
/*    */   extends Module
/*    */ {
/* 18 */   private final Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(true)));
/* 19 */   private final Setting<Integer> health = register(new Setting("Health", Integer.valueOf(13), Integer.valueOf(0), Integer.valueOf(36), v -> ((Boolean)this.strict.getValue()).booleanValue()));
/*    */   
/*    */   public AutoTotem() {
/* 22 */     super("AutoTotem", "Main Hand AutoTotem", Module.Category.COMBAT, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getItemSlot(Item item) {
/* 27 */     int itemSlot = -1;
/*    */     
/* 29 */     boolean xCarry = XCarry.getInstance().isEnabled();
/*    */     
/* 31 */     for (int i = 9; i <= mc.player.inventory.mainInventory.size(); i++) {
/*    */       
/* 33 */       if (i == 5) {
/*    */         break;
/*    */       }
/*    */       
/* 37 */       if (i == 36) {
/* 38 */         if (xCarry) {
/* 39 */           i = 1;
/*    */         } else {
/*    */           break;
/*    */         } 
/*    */       }
/* 44 */       ItemStack stack = (ItemStack)mc.player.inventoryContainer.getInventory().get(i);
/* 45 */       if (stack.getItem().equals(item)) {
/* 46 */         itemSlot = i;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 51 */     return itemSlot;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 57 */     if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer && !(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
/*    */       return;
/*    */     }
/* 60 */     if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && mc.player.getHeldItemMainhand().getItem() != Items.TOTEM_OF_UNDYING) {
/*    */       
/* 62 */       int totemSlot = getItemSlot(Items.TOTEM_OF_UNDYING);
/* 63 */       if (totemSlot != -1 && mc.player.inventory.getStackInSlot(0).getItem() != Items.TOTEM_OF_UNDYING) {
/* 64 */         mc.playerController.windowClick(mc.player.inventoryContainer.windowId, totemSlot, 0, ClickType.SWAP, (EntityPlayer)mc.player);
/*    */       }
/* 66 */       int hotBarSlot = InventoryUtil.getItemHotbar(Items.TOTEM_OF_UNDYING);
/* 67 */       if ((hotBarSlot != -1 && EntityUtil.getHealth((Entity)mc.player) <= ((Integer)this.health.getValue()).intValue() && ((Boolean)this.strict.getValue()).booleanValue()) || (hotBarSlot != -1 && !((Boolean)this.strict.getValue()).booleanValue()))
/* 68 */         InventoryUtil.switchToSlot(hotBarSlot); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AutoTotem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
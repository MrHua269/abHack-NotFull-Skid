/*     */ package me.abHack.features.modules.player;
/*     */ 
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class Replenish
/*     */   extends Module
/*     */ {
/*  16 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10)));
/*  17 */   private final Setting<Integer> stackPercentage = register(new Setting("RefillPercent", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(99)));
/*  18 */   private final Setting<Boolean> offHand = register(new Setting("RefillOffhand", Boolean.valueOf(true)));
/*  19 */   private final Timer timer = new Timer();
/*     */ 
/*     */   
/*     */   public Replenish() {
/*  23 */     super("Replenish", "Automatic replenishment.", Module.Category.PLAYER, false, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  30 */     if (mc.currentScreen != null) {
/*     */       return;
/*     */     }
/*  33 */     if (!this.timer.passedMs((((Integer)this.delay.getValue()).intValue() * 1000))) {
/*     */       return;
/*     */     }
/*  36 */     int toRefill = getRefillable(mc.player);
/*  37 */     if (toRefill != -1) {
/*  38 */       refillHotbarSlot(mc, toRefill);
/*  39 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getRefillable(EntityPlayerSP player) {
/*  45 */     if (((Boolean)this.offHand.getValue()).booleanValue() && 
/*  46 */       player.getHeldItemOffhand().getItem() != Items.AIR && player
/*  47 */       .getHeldItemOffhand().getCount() < player.getHeldItemOffhand().getMaxStackSize() && player
/*  48 */       .getHeldItemOffhand().getCount() / player.getHeldItemOffhand().getMaxStackSize() <= ((Integer)this.stackPercentage.getValue()).intValue() / 100.0D) {
/*  49 */       return 45;
/*     */     }
/*     */ 
/*     */     
/*  53 */     for (int i = 0; i < 9; i++) {
/*  54 */       ItemStack stack = (ItemStack)player.inventory.mainInventory.get(i);
/*  55 */       if (stack.getItem() != Items.AIR && stack.getCount() < stack.getMaxStackSize() && stack
/*  56 */         .getCount() / stack.getMaxStackSize() <= ((Integer)this.stackPercentage.getValue()).intValue() / 100.0D) {
/*  57 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  61 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSmallestStack(EntityPlayerSP player, ItemStack itemStack) {
/*  66 */     if (itemStack == null) {
/*  67 */       return -1;
/*     */     }
/*  69 */     int minCount = itemStack.getMaxStackSize() + 1;
/*  70 */     int minIndex = -1;
/*     */     
/*  72 */     boolean xCarry = XCarry.getInstance().isEnabled();
/*     */     
/*  74 */     for (int i = 9; i <= player.inventory.mainInventory.size(); i++) {
/*     */       
/*  76 */       if (i == 5) {
/*     */         break;
/*     */       }
/*     */       
/*  80 */       if (i == 36) {
/*  81 */         if (xCarry) {
/*  82 */           i = 1;
/*     */         } else {
/*     */           break;
/*     */         } 
/*     */       }
/*     */       
/*  88 */       ItemStack stack = (ItemStack)player.inventoryContainer.getInventory().get(i);
/*  89 */       if (stack.getItem() != Items.AIR && stack
/*  90 */         .getItem() == itemStack.getItem() && stack
/*  91 */         .getCount() < minCount) {
/*     */         
/*  93 */         minCount = stack.getCount();
/*  94 */         minIndex = i;
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     return minIndex;
/*     */   }
/*     */   
/*     */   public void refillHotbarSlot(Minecraft mc, int slot) {
/*     */     ItemStack stack;
/* 103 */     if (slot == 45) {
/* 104 */       stack = mc.player.getHeldItemOffhand();
/*     */     } else {
/* 106 */       stack = (ItemStack)mc.player.inventory.mainInventory.get(slot);
/*     */     } 
/*     */ 
/*     */     
/* 110 */     if (stack.getItem() == Items.AIR) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 115 */     int biggestStack = getSmallestStack(mc.player, stack);
/* 116 */     if (biggestStack == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 121 */     if (slot == 45) {
/* 122 */       mc.playerController.windowClick(mc.player.inventoryContainer.windowId, biggestStack, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
/* 123 */       mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
/* 124 */       mc.playerController.windowClick(mc.player.inventoryContainer.windowId, biggestStack, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     int overflow = -1;
/* 129 */     for (int i = 0; i < 9 && overflow == -1; i++) {
/* 130 */       if (((ItemStack)mc.player.inventory.mainInventory.get(i)).getItem() == Items.AIR) {
/* 131 */         overflow = i;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     mc.playerController.windowClick(mc.player.inventoryContainer.windowId, biggestStack, 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
/*     */ 
/*     */     
/* 138 */     if (overflow != -1 && ((ItemStack)mc.player.inventory.mainInventory.get(overflow)).getItem() != Items.AIR)
/* 139 */       mc.playerController.windowClick(mc.player.inventoryContainer.windowId, biggestStack, overflow, ClickType.SWAP, (EntityPlayer)mc.player); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Replenish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
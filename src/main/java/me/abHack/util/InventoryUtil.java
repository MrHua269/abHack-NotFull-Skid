/*     */ package me.abHack.util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import me.abHack.features.modules.player.XCarry;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class InventoryUtil implements Util {
/*     */   public static void switchToSlot(int slot) {
/*  24 */     if (mc.player.inventory.currentItem == slot || slot < 0) {
/*     */       return;
/*     */     }
/*  27 */     mc.player.inventory.currentItem = slot;
/*  28 */     mc.playerController.updateController();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getItemCount(Item item) {
/*  40 */     if (item instanceof ItemArmor) {
/*  41 */       return mc.player.inventory.armorInventory.stream()
/*  42 */         .filter(itemStack -> itemStack.getItem().equals(item))
/*  43 */         .mapToInt(ItemStack::getCount)
/*  44 */         .sum();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  49 */     return mc.player.inventory.mainInventory.stream()
/*  50 */       .filter(itemStack -> itemStack.getItem().equals(item))
/*  51 */       .mapToInt(ItemStack::getCount)
/*  52 */       .sum() + mc.player.inventory.offHandInventory
/*     */       
/*  54 */       .stream()
/*  55 */       .filter(itemStack -> itemStack.getItem().equals(item))
/*  56 */       .mapToInt(ItemStack::getCount)
/*  57 */       .sum() + getGridCount(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getGridCount(Item item) {
/*  62 */     int sum = 0;
/*  63 */     boolean xCarry = XCarry.getInstance().isEnabled();
/*  64 */     if (!xCarry)
/*  65 */       return sum; 
/*  66 */     for (int i = 1; i <= 4; i++) {
/*  67 */       ItemStack stack = (ItemStack)mc.player.inventoryContainer.getInventory().get(i);
/*  68 */       if (stack.getItem().equals(item))
/*  69 */         sum += stack.getCount(); 
/*     */     } 
/*  71 */     return sum;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getInventoryItemSlot(Item item, boolean hotbar) {
/*  76 */     for (int i = hotbar ? 0 : 9; i < 45; i++) {
/*  77 */       if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
/*  78 */         return i;
/*     */       }
/*     */     } 
/*  81 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getWeaponHotbar() {
/*  85 */     for (int i = 0; i < 9; i++) {
/*  86 */       Item item = mc.player.inventory.getStackInSlot(i).getItem();
/*  87 */       if (item instanceof net.minecraft.item.ItemSword || item instanceof net.minecraft.item.ItemAxe)
/*  88 */         return i; 
/*     */     } 
/*  90 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNull(ItemStack stack) {
/*  95 */     return (stack == null || stack.getItem() instanceof net.minecraft.item.ItemAir);
/*     */   }
/*     */   
/*     */   public static int findHotbarBlock(Class clazz) {
/*  99 */     for (int i = 0; i < 9; i++) {
/* 100 */       ItemStack stack = mc.player.inventory.getStackInSlot(i);
/* 101 */       if (stack != ItemStack.EMPTY) {
/* 102 */         if (clazz.isInstance(stack.getItem())) {
/* 103 */           return i;
/*     */         }
/* 105 */         if (stack.getItem() instanceof ItemBlock && clazz.isInstance(((ItemBlock)stack.getItem()).getBlock()))
/*     */         {
/* 107 */           return i; } 
/*     */       } 
/* 109 */     }  return -1;
/*     */   }
/*     */   
/*     */   public static int findHotbarBlock(Block blockIn) {
/* 113 */     for (int i = 0; i < 9; ) {
/* 114 */       ItemStack stack = mc.player.inventory.getStackInSlot(i);
/* 115 */       if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || ((ItemBlock)stack.getItem()).getBlock() != blockIn) {
/*     */         i++; continue;
/* 117 */       }  return i;
/*     */     } 
/* 119 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findAnyBlock() {
/* 124 */     for (int i = 0; i < 9; ) {
/* 125 */       ItemStack stack = mc.player.inventory.getStackInSlot(i);
/* 126 */       if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
/*     */         i++; continue;
/* 128 */       }  return i;
/*     */     } 
/* 130 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getItemHotbar(Item input) {
/* 134 */     for (int i = 0; i < 9; ) {
/* 135 */       Item item = mc.player.inventory.getStackInSlot(i).getItem();
/* 136 */       if (Item.getIdFromItem(item) != Item.getIdFromItem(input)) { i++; continue; }
/* 137 */        return i;
/*     */     } 
/* 139 */     return -1;
/*     */   }
/*     */   
/*     */   public static int findItemInventorySlot(Item item, boolean offHand) {
/* 143 */     AtomicInteger slot = new AtomicInteger();
/* 144 */     slot.set(-1);
/* 145 */     for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
/* 146 */       if (((ItemStack)entry.getValue()).getItem() != item || (((Integer)entry.getKey()).intValue() == 45 && !offHand))
/* 147 */         continue;  slot.set(((Integer)entry.getKey()).intValue());
/* 148 */       return slot.get();
/*     */     } 
/* 150 */     return slot.get();
/*     */   }
/*     */   
/*     */   public static List<Integer> findEmptySlots(boolean withXCarry) {
/* 154 */     ArrayList<Integer> outPut = new ArrayList<>();
/* 155 */     for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
/* 156 */       if (!((ItemStack)entry.getValue()).isEmpty && ((ItemStack)entry.getValue()).getItem() != Items.AIR)
/* 157 */         continue;  outPut.add(entry.getKey());
/*     */     } 
/* 159 */     if (withXCarry)
/* 160 */       for (int i = 1; i < 5; i++) {
/* 161 */         Slot craftingSlot = mc.player.inventoryContainer.inventorySlots.get(i);
/* 162 */         ItemStack craftingStack = craftingSlot.getStack();
/* 163 */         if (craftingStack.isEmpty() || craftingStack.getItem() == Items.AIR) {
/* 164 */           outPut.add(Integer.valueOf(i));
/*     */         }
/*     */       }  
/* 167 */     return outPut;
/*     */   }
/*     */   
/*     */   public static boolean isBlock(Item item, Class clazz) {
/* 171 */     if (item instanceof ItemBlock) {
/* 172 */       Block block = ((ItemBlock)item).getBlock();
/* 173 */       return clazz.isInstance(block);
/*     */     } 
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
/* 180 */     return getInventorySlots();
/*     */   }
/*     */   
/*     */   private static Map<Integer, ItemStack> getInventorySlots() {
/* 184 */     HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<>();
/* 185 */     for (int current = 9; current <= 44; current++) {
/* 186 */       fullInventorySlots.put(Integer.valueOf(current), mc.player.inventoryContainer.getInventory().get(current));
/*     */     }
/* 188 */     return fullInventorySlots;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean holdingItem(Class clazz) {
/* 194 */     ItemStack stack = mc.player.getHeldItemMainhand();
/* 195 */     boolean result = isInstanceOf(stack, clazz);
/* 196 */     if (!result) {
/* 197 */       result = isInstanceOf(stack, clazz);
/*     */     }
/* 199 */     return result;
/*     */   }
/*     */   
/*     */   public static boolean isInstanceOf(ItemStack stack, Class clazz) {
/* 203 */     if (stack == null) {
/* 204 */       return false;
/*     */     }
/* 206 */     Item item = stack.getItem();
/* 207 */     if (clazz.isInstance(item)) {
/* 208 */       return true;
/*     */     }
/* 210 */     if (item instanceof ItemBlock) {
/* 211 */       Block block = Block.getBlockFromItem(item);
/* 212 */       return clazz.isInstance(block);
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findArmorSlot(EntityEquipmentSlot type) {
/* 219 */     int slot = -1;
/* 220 */     float damage = 0.0F;
/* 221 */     for (int i = 9; i < 45; i++) {
/*     */       
/* 223 */       ItemStack s = (Minecraft.getMinecraft()).player.inventoryContainer.getSlot(i).getStack(); ItemArmor armor;
/* 224 */       if (s.getItem() != Items.AIR && s.getItem() instanceof ItemArmor && (armor = (ItemArmor)s.getItem()).getEquipmentSlot() == type) {
/*     */         
/* 226 */         float currentDamage = (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));
/* 227 */         if (currentDamage > damage)
/* 228 */         { damage = currentDamage;
/* 229 */           slot = i; } 
/*     */       } 
/* 231 */     }  return slot;
/*     */   }
/*     */   
/*     */   public static int findArmorSlot(EntityEquipmentSlot type, boolean withXCarry) {
/* 235 */     int slot = findArmorSlot(type);
/* 236 */     if (slot == -1 && withXCarry) {
/* 237 */       float damage = 0.0F;
/* 238 */       for (int i = 1; i < 5; i++) {
/*     */         
/* 240 */         Slot craftingSlot = mc.player.inventoryContainer.inventorySlots.get(i);
/* 241 */         ItemStack craftingStack = craftingSlot.getStack(); ItemArmor armor;
/* 242 */         if (craftingStack.getItem() != Items.AIR && craftingStack.getItem() instanceof ItemArmor && (armor = (ItemArmor)craftingStack.getItem()).getEquipmentSlot() == type) {
/*     */           
/* 244 */           float currentDamage = (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, craftingStack));
/* 245 */           if (currentDamage > damage)
/* 246 */           { damage = currentDamage;
/* 247 */             slot = i; } 
/*     */         } 
/*     */       } 
/* 250 */     }  return slot;
/*     */   }
/*     */   
/*     */   public static class Task
/*     */   {
/*     */     private final int slot;
/*     */     private final boolean update;
/*     */     private final boolean quickClick;
/*     */     
/*     */     public Task() {
/* 260 */       this.update = true;
/* 261 */       this.slot = -1;
/* 262 */       this.quickClick = false;
/*     */     }
/*     */     
/*     */     public Task(int slot) {
/* 266 */       this.slot = slot;
/* 267 */       this.quickClick = false;
/* 268 */       this.update = false;
/*     */     }
/*     */     
/*     */     public void run() {
/* 272 */       if (this.update) {
/* 273 */         Util.mc.playerController.updateController();
/*     */       }
/* 275 */       if (this.slot != -1) {
/* 276 */         Util.mc.playerController.windowClick(0, this.slot, 0, this.quickClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, (EntityPlayer)Util.mc.player);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean isSwitching() {
/* 281 */       return !this.update;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\InventoryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.combat;
/*     */ import java.util.*;
/*     */
/*     */
/*     */
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.player.AutoXP;
/*     */ import me.abHack.features.setting.Bind;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class AutoArmor extends Module {
/*  22 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(500)));
/*  23 */   private final Setting<Boolean> mendingTakeOff = register(new Setting("AutoMend", Boolean.valueOf(true)));
/*  24 */   private final Setting<Integer> closestEnemy = register(new Setting("Enemy", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(20), v -> ((Boolean)this.mendingTakeOff.getValue()).booleanValue()));
/*  25 */   private final Setting<Integer> repair = register(new Setting("Repair%", Integer.valueOf(90), Integer.valueOf(1), Integer.valueOf(100), v -> ((Boolean)this.mendingTakeOff.getValue()).booleanValue()));
/*  26 */   private final Setting<Integer> actions = register(new Setting("Packets", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(12)));
/*  27 */   private final Setting<Boolean> antiNaked = register(new Setting("AntiNaked", Boolean.valueOf(true)));
/*  28 */   private final Timer timer = new Timer();
/*  29 */   private final Queue<InventoryUtil.Task> taskList = new ConcurrentLinkedQueue<>();
/*  30 */   private final List<Integer> doneSlots = new ArrayList<>();
/*     */   boolean flag;
/*     */   
/*     */   public AutoArmor() {
/*  34 */     super("AutoArmor", "Puts Armor on for you.", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLogin() {
/*  39 */     this.timer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  44 */     this.taskList.clear();
/*  45 */     this.doneSlots.clear();
/*  46 */     this.flag = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLogout() {
/*  51 */     this.taskList.clear();
/*  52 */     this.doneSlots.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  57 */     if (fullNullCheck() || (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer && !(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     if (((Boolean)this.antiNaked.getValue()).booleanValue() && mc.player.inventory.getItemStack().getItem() instanceof net.minecraft.item.ItemArmor) {
/*  62 */       int ab = InventoryUtil.getInventoryItemSlot(Items.AIR, false);
/*  63 */       if (ab == -1)
/*     */         return; 
/*  65 */       mc.playerController.windowClick(0, ab, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
/*     */       
/*     */       return;
/*     */     } 
/*  69 */     if (this.taskList.isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       if (((Boolean)this.mendingTakeOff.getValue()).booleanValue() && InventoryUtil.getItemHotbar(Items.EXPERIENCE_BOTTLE) != -1 && AutoXP.INSTANCE.isOn() && ((Bind)AutoXP.INSTANCE.bind.getValue()).isDown() && mc.world.playerEntities.stream().noneMatch(e -> (e != mc.player && !OyVey.friendManager.isFriend(e.getName()) && mc.player.getDistance((Entity)e) <= ((Integer)this.closestEnemy.getValue()).intValue())) && !this.flag) {
/*     */         
/*  76 */         int takeOff = 0;
/*  77 */         for (Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
/*  78 */           ItemStack stack = armorSlot.getValue();
/*  79 */           float percent = ((Integer)this.repair.getValue()).intValue() / 100.0F;
/*  80 */           int dam = Math.round(stack.getMaxDamage() * percent);
/*  81 */           if (dam >= stack.getMaxDamage() - stack.getItemDamage())
/*  82 */             continue;  takeOff++;
/*     */         } 
/*  84 */         if (takeOff == 4) {
/*  85 */           this.flag = true;
/*     */         }
/*  87 */         if (!this.flag) {
/*  88 */           ItemStack itemStack1 = mc.player.inventoryContainer.getSlot(5).getStack();
/*  89 */           if (!itemStack1.isEmpty) {
/*  90 */             float percent = ((Integer)this.repair.getValue()).intValue() / 100.0F;
/*  91 */             int dam2 = Math.round(itemStack1.getMaxDamage() * percent);
/*  92 */             if (dam2 < itemStack1.getMaxDamage() - itemStack1.getItemDamage()) {
/*  93 */               takeOffSlot(5);
/*     */             }
/*     */           } 
/*  96 */           ItemStack itemStack2 = mc.player.inventoryContainer.getSlot(6).getStack();
/*  97 */           if (!itemStack2.isEmpty) {
/*  98 */             float percent = ((Integer)this.repair.getValue()).intValue() / 100.0F;
/*  99 */             int dam3 = Math.round(itemStack2.getMaxDamage() * percent);
/* 100 */             if (dam3 < itemStack2.getMaxDamage() - itemStack2.getItemDamage()) {
/* 101 */               takeOffSlot(6);
/*     */             }
/*     */           } 
/* 104 */           ItemStack itemStack3 = mc.player.inventoryContainer.getSlot(7).getStack();
/* 105 */           if (!itemStack3.isEmpty) {
/* 106 */             float percent = ((Integer)this.repair.getValue()).intValue() / 100.0F;
/* 107 */             int dam = Math.round(itemStack3.getMaxDamage() * percent);
/* 108 */             if (dam < itemStack3.getMaxDamage() - itemStack3.getItemDamage()) {
/* 109 */               takeOffSlot(7);
/*     */             }
/*     */           } 
/* 112 */           ItemStack itemStack4 = mc.player.inventoryContainer.getSlot(8).getStack();
/* 113 */           if (!itemStack4.isEmpty) {
/* 114 */             float percent = ((Integer)this.repair.getValue()).intValue() / 100.0F;
/* 115 */             int dam4 = Math.round(itemStack4.getMaxDamage() * percent);
/* 116 */             if (dam4 < itemStack4.getMaxDamage() - itemStack4.getItemDamage()) {
/* 117 */               takeOffSlot(8);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         return;
/*     */       } 
/* 123 */       this.flag = false;
/* 124 */       ItemStack helm = mc.player.inventoryContainer.getSlot(5).getStack(); int slot4;
/* 125 */       if (helm.getItem() == Items.AIR && (slot4 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.HEAD, true)) != -1)
/* 126 */         getSlotOn(5, slot4); 
/*     */       int slot3;
/* 128 */       if (mc.player.inventoryContainer.getSlot(6).getStack().getItem() == Items.AIR && (slot3 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.CHEST, true)) != -1)
/* 129 */         getSlotOn(6, slot3); 
/*     */       int slot2;
/* 131 */       if (mc.player.inventoryContainer.getSlot(7).getStack().getItem() == Items.AIR && (slot2 = InventoryUtil.findArmorSlot(EntityEquipmentSlot.LEGS, true)) != -1)
/* 132 */         getSlotOn(7, slot2); 
/*     */       int slot;
/* 134 */       if (mc.player.inventoryContainer.getSlot(8).getStack().getItem() == Items.AIR && (slot = InventoryUtil.findArmorSlot(EntityEquipmentSlot.FEET, true)) != -1) {
/* 135 */         getSlotOn(8, slot);
/*     */       }
/*     */     } 
/* 138 */     if (this.timer.passedMs((int)(((Integer)this.delay.getValue()).intValue() * OyVey.serverManager.getTpsFactor()))) {
/* 139 */       if (!this.taskList.isEmpty())
/* 140 */         for (int i = 0; i < ((Integer)this.actions.getValue()).intValue(); i++) {
/* 141 */           InventoryUtil.Task task = this.taskList.poll();
/* 142 */           if (task != null) {
/* 143 */             task.run();
/*     */           }
/*     */         }  
/* 146 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void takeOffSlot(int slot) {
/* 151 */     if (this.taskList.isEmpty()) {
/* 152 */       int target = -1;
/* 153 */       for (Iterator<Integer> iterator = InventoryUtil.findEmptySlots(true).iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
/* 154 */         if (this.doneSlots.contains(Integer.valueOf(target)))
/* 155 */           continue;  target = i;
/* 156 */         this.doneSlots.add(Integer.valueOf(i)); }
/*     */       
/* 158 */       if (target != -1) {
/* 159 */         this.taskList.add(new InventoryUtil.Task(slot));
/* 160 */         this.taskList.add(new InventoryUtil.Task(target));
/* 161 */         this.taskList.add(new InventoryUtil.Task());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getSlotOn(int slot, int target) {
/* 167 */     if (this.taskList.isEmpty()) {
/* 168 */       this.doneSlots.remove(Integer.valueOf(target));
/* 169 */       this.taskList.add(new InventoryUtil.Task(target));
/* 170 */       this.taskList.add(new InventoryUtil.Task(slot));
/* 171 */       this.taskList.add(new InventoryUtil.Task());
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map<Integer, ItemStack> getArmor() {
/* 176 */     return getInventorySlots(5);
/*     */   }
/*     */   
/*     */   private Map<Integer, ItemStack> getInventorySlots(int current) {
/* 180 */     HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<>();
/* 181 */     while (current <= 8) {
/* 182 */       fullInventorySlots.put(Integer.valueOf(current), mc.player.inventoryContainer.getInventory().get(current));
/* 183 */       current++;
/*     */     } 
/* 185 */     return fullInventorySlots;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AutoArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
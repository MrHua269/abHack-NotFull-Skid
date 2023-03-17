/*     */ package me.abHack.features.modules.player;
/*     */ 
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class AutoEat extends Module {
/*  17 */   public final Setting<Float> health = register(new Setting("Health", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(36.0F)));
/*  18 */   public final Setting<Float> hunger = register(new Setting("Hunger", Float.valueOf(15.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  19 */   public final Setting<Boolean> autoSwitch = register(new Setting("AutoSwitch", Boolean.valueOf(true)));
/*  20 */   public final Setting<Boolean> preferGaps = register(new Setting("PreferGaps", Boolean.valueOf(false)));
/*  21 */   int originalSlot = -1;
/*     */   boolean firstSwap = true;
/*     */   boolean resetKeyBind = false;
/*     */   
/*     */   public AutoEat() {
/*  26 */     super("AutoEat", "Auto eat", Module.Category.PLAYER, true, false, false);
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  30 */     if (mc.player == null || mc.world == null)
/*     */       return; 
/*  32 */     if (mc.player.isCreative())
/*     */       return; 
/*  34 */     if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= ((Float)this.health.getValue()).floatValue() || mc.player
/*  35 */       .getFoodStats().getFoodLevel() <= ((Float)this.hunger.getValue()).floatValue()) {
/*     */       
/*  37 */       if (((Boolean)this.autoSwitch.getValue()).booleanValue()) {
/*     */         
/*  39 */         int foodSlot = findFoodSlot();
/*  40 */         if (this.firstSwap) {
/*  41 */           this.originalSlot = mc.player.inventory.currentItem;
/*  42 */           this.firstSwap = false;
/*     */         } 
/*     */         
/*  45 */         if (foodSlot != -1) {
/*  46 */           mc.player.inventory.currentItem = foodSlot;
/*  47 */           mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(foodSlot));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  52 */       if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFood) {
/*  53 */         if (mc.currentScreen == null || mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
/*  54 */           KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
/*  55 */           this.resetKeyBind = true;
/*     */         } else {
/*  57 */           mc.playerController.processRightClick((EntityPlayer)mc.player, (World)mc.world, EnumHand.MAIN_HAND);
/*     */         } 
/*  59 */       } else if (mc.currentScreen == null || mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
/*  60 */         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  65 */       if (this.resetKeyBind) {
/*  66 */         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem));
/*  67 */         this.resetKeyBind = false;
/*     */       } 
/*     */       
/*  70 */       if (this.originalSlot != -1) {
/*  71 */         mc.player.inventory.currentItem = this.originalSlot;
/*  72 */         mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.originalSlot));
/*  73 */         this.originalSlot = -1;
/*  74 */         this.firstSwap = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findFoodSlot() {
/*  82 */     int foodSlot = -1;
/*  83 */     float bestHealAmount = 0.0F;
/*     */     
/*  85 */     for (int l = 0; l < 9; l++) {
/*  86 */       ItemStack item = mc.player.inventory.getStackInSlot(l);
/*     */       
/*  88 */       if (item.getItem() instanceof ItemFood) {
/*     */         
/*  90 */         if (((Boolean)this.preferGaps.getValue()).booleanValue() && item.getItem() == Items.GOLDEN_APPLE) {
/*  91 */           foodSlot = l;
/*     */           
/*     */           break;
/*     */         } 
/*  95 */         float healAmount = ((ItemFood)item.getItem()).getHealAmount(item);
/*     */         
/*  97 */         if (healAmount > bestHealAmount) {
/*  98 */           bestHealAmount = healAmount;
/*  99 */           foodSlot = l;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 105 */     return foodSlot;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\AutoEat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
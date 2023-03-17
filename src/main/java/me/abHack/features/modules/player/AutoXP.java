/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Bind;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.NonNullList;
/*    */ 
/*    */ public class AutoXP
/*    */   extends Module
/*    */ {
/*    */   public static AutoXP INSTANCE;
/*    */   public final Setting<Bind> bind;
/*    */   private final Timer timer;
/*    */   private final Setting<Integer> delay;
/*    */   private final Setting<Integer> minDamage;
/*    */   private final Setting<Integer> maxHeal;
/*    */   private final Setting<Boolean> sneakOnly;
/*    */   private final Setting<Boolean> predict;
/* 29 */   char toMend = Character.MIN_VALUE;
/*    */   
/*    */   public AutoXP() {
/* 32 */     super("AutoXP", "AutoXP", Module.Category.PLAYER, true, false, false);
/*    */     
/* 34 */     this.timer = new Timer();
/* 35 */     this.delay = register(new Setting("XP Delay", Integer.valueOf(4), Integer.valueOf(0), Integer.valueOf(4)));
/* 36 */     this.minDamage = register(new Setting("Min Damage", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(100)));
/* 37 */     this.maxHeal = register(new Setting("Repair To", Integer.valueOf(90), Integer.valueOf(0), Integer.valueOf(100)));
/* 38 */     this.sneakOnly = register(new Setting("Sneak Only", Boolean.valueOf(false)));
/* 39 */     this.predict = register(new Setting("Predict", Boolean.valueOf(false)));
/* 40 */     this.bind = register(new Setting("PacketBind", new Bind(-1)));
/* 41 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 46 */     if (fullNullCheck())
/*    */       return; 
/* 48 */     if (mc.currentScreen instanceof net.minecraft.client.gui.Gui && !(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
/*    */       return;
/*    */     }
/* 51 */     if (((Bind)this.bind.getValue()).isDown())
/* 52 */       mendArmor(); 
/* 53 */     int sumOfDamage = 0;
/* 54 */     NonNullList<ItemStack> nonNullList = mc.player.inventory.armorInventory;
/* 55 */     for (int i = 0; i < nonNullList.size(); i++) {
/* 56 */       ItemStack itemStack = (ItemStack)nonNullList.get(i);
/* 57 */       if (!itemStack.isEmpty) {
/* 58 */         float damageOnArmor = (itemStack.getMaxDamage() - itemStack.getItemDamage());
/* 59 */         float damagePercent = 100.0F - 100.0F * (1.0F - damageOnArmor / itemStack.getMaxDamage());
/*    */         
/* 61 */         if (damagePercent <= ((Integer)this.maxHeal.getValue()).intValue()) {
/* 62 */           if (damagePercent <= ((Integer)this.minDamage.getValue()).intValue()) {
/* 63 */             this.toMend = (char)(this.toMend | 1 << i);
/*    */           }
/* 65 */           if (((Boolean)this.predict.getValue()).booleanValue()) {
/* 66 */             sumOfDamage = (int)(sumOfDamage + (itemStack.getMaxDamage() * ((Integer)this.maxHeal.getValue()).intValue()) / 100.0F - (itemStack.getMaxDamage() - itemStack.getItemDamage()));
/*    */           }
/*    */         } else {
/* 69 */           this.toMend = (char)(this.toMend & (1 << i ^ 0xFFFFFFFF));
/*    */         } 
/*    */       } 
/*    */     } 
/* 73 */     if (this.toMend > '\000' && this.timer.passedMs(((Integer)this.delay.getValue()).intValue() * 45L)) {
/* 74 */       this.timer.reset();
/* 75 */       if (((Boolean)this.predict.getValue()).booleanValue()) {
/* 76 */         int totalXp = mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityXPOrb).filter(entity -> (entity.getDistanceSq((Entity)mc.player) <= 1.0D)).mapToInt(entity -> ((EntityXPOrb)entity).xpValue).sum();
/* 77 */         if (totalXp * 2 < sumOfDamage) {
/* 78 */           mendArmor();
/*    */         }
/* 80 */       } else if (((Boolean)this.sneakOnly.getValue()).booleanValue() && mc.player.isSneaking()) {
/* 81 */         mendArmor();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void mendArmor() {
/* 87 */     int a = InventoryUtil.getItemHotbar(Items.EXPERIENCE_BOTTLE);
/* 88 */     int b = mc.player.inventory.currentItem;
/* 89 */     if (a == -1)
/*    */       return; 
/* 91 */     mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0F, true));
/* 92 */     mc.player.inventory.currentItem = a;
/* 93 */     mc.playerController.updateController();
/* 94 */     mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 95 */     mc.player.inventory.currentItem = b;
/* 96 */     mc.playerController.updateController();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\AutoXP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
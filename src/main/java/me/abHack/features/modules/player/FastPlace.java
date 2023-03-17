/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import net.minecraft.block.BlockObsidian;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemEndCrystal;
/*    */ import net.minecraft.item.ItemExpBottle;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ 
/*    */ public class FastPlace extends Module {
/* 19 */   private final Setting<Boolean> all = register(new Setting("All", Boolean.valueOf(false)));
/* 20 */   private final Setting<Boolean> obby = register(new Setting("Obsidian", Boolean.valueOf(false), v -> !((Boolean)this.all.getValue()).booleanValue()));
/* 21 */   private final Setting<Boolean> crystals = register(new Setting("Crystals", Boolean.valueOf(false), v -> !((Boolean)this.all.getValue()).booleanValue()));
/* 22 */   private final Setting<Boolean> exp = register(new Setting("Experience", Boolean.valueOf(false), v -> !((Boolean)this.all.getValue()).booleanValue()));
/* 23 */   private final Setting<Boolean> PacketCrystal = register(new Setting("PacketCrystal", Boolean.valueOf(false)));
/* 24 */   private final Setting<Integer> useDelay = register(new Setting("Use Delay", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(10)));
/* 25 */   private BlockPos mousePos = null;
/*    */   
/*    */   public FastPlace() {
/* 28 */     super("FastPlace", "Allows you to use items faster", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 33 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 36 */     if (InventoryUtil.holdingItem(ItemExpBottle.class) && ((Boolean)this.exp.getValue()).booleanValue()) {
/* 37 */       mc.rightClickDelayTimer = ((Integer)this.useDelay.getValue()).intValue();
/*    */     }
/* 39 */     if (InventoryUtil.holdingItem(BlockObsidian.class) && ((Boolean)this.obby.getValue()).booleanValue()) {
/* 40 */       mc.rightClickDelayTimer = ((Integer)this.useDelay.getValue()).intValue();
/*    */     }
/* 42 */     if (((Boolean)this.all.getValue()).booleanValue()) {
/* 43 */       mc.rightClickDelayTimer = ((Integer)this.useDelay.getValue()).intValue();
/*    */     }
/* 45 */     if (InventoryUtil.holdingItem(ItemEndCrystal.class) && (((Boolean)this.crystals.getValue()).booleanValue() || ((Boolean)this.all.getValue()).booleanValue())) {
/* 46 */       mc.rightClickDelayTimer = ((Integer)this.useDelay.getValue()).intValue();
/*    */     }
/* 48 */     if (((Boolean)this.PacketCrystal.getValue()).booleanValue() && mc.gameSettings.keyBindUseItem.isKeyDown() && 
/* 49 */       mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
/* 50 */       Entity entity; RayTraceResult result = mc.objectMouseOver;
/* 51 */       if (result == null) {
/*    */         return;
/*    */       }
/* 54 */       switch (result.typeOfHit) {
/*    */         case MISS:
/* 56 */           this.mousePos = null;
/*    */           break;
/*    */         
/*    */         case BLOCK:
/* 60 */           this.mousePos = mc.objectMouseOver.getBlockPos();
/*    */           break;
/*    */ 
/*    */         
/*    */         case ENTITY:
/* 65 */           if (this.mousePos == null || (entity = result.entityHit) == null || !this.mousePos.equals(new BlockPos(entity.posX, entity.posY - 1.0D, entity.posZ)))
/*    */             break; 
/* 67 */           mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.mousePos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\FastPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
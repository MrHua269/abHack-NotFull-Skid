/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiContainer
/*    */   extends Module
/*    */ {
/*    */   public Setting<Boolean> Chest;
/*    */   public Setting<Boolean> EnderChest;
/*    */   public Setting<Boolean> Trapped_Chest;
/*    */   public Setting<Boolean> Hopper;
/*    */   public Setting<Boolean> Dispenser;
/*    */   public Setting<Boolean> Furnace;
/*    */   public Setting<Boolean> Beacon;
/*    */   public Setting<Boolean> Crafting_Table;
/*    */   public Setting<Boolean> Anvil;
/*    */   public Setting<Boolean> Enchanting_table;
/*    */   public Setting<Boolean> Brewing_Stand;
/*    */   public Setting<Boolean> ShulkerBox;
/*    */   
/*    */   public AntiContainer() {
/* 38 */     super("AntiContainer", "Do not display containers", Module.Category.PLAYER, true, false, false);
/* 39 */     this.Chest = register(new Setting("Chest", Boolean.valueOf(true)));
/* 40 */     this.EnderChest = register(new Setting("EnderChest", Boolean.valueOf(true)));
/* 41 */     this.Trapped_Chest = register(new Setting("Trapped_Chest", Boolean.valueOf(true)));
/* 42 */     this.Hopper = register(new Setting("Hopper", Boolean.valueOf(true)));
/* 43 */     this.Dispenser = register(new Setting("Dispenser", Boolean.valueOf(true)));
/* 44 */     this.Furnace = register(new Setting("Furnace", Boolean.valueOf(true)));
/* 45 */     this.Beacon = register(new Setting("Beacon", Boolean.valueOf(true)));
/* 46 */     this.Crafting_Table = register(new Setting("Crafting_Table", Boolean.valueOf(true)));
/* 47 */     this.Anvil = register(new Setting("Anvil", Boolean.valueOf(true)));
/* 48 */     this.Enchanting_table = register(new Setting("Enchanting_table", Boolean.valueOf(true)));
/* 49 */     this.Brewing_Stand = register(new Setting("Brewing_Stand", Boolean.valueOf(true)));
/* 50 */     this.ShulkerBox = register(new Setting("ShulkerBox", Boolean.valueOf(true)));
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onCheck(PacketEvent.Send packet) {
/* 55 */     if (packet.packet instanceof CPacketPlayerTryUseItemOnBlock) {
/* 56 */       BlockPos pos = ((CPacketPlayerTryUseItemOnBlock)packet.packet).getPos();
/* 57 */       if (check(pos) && (mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE || mc.player.getHeldItemMainhand().getItem() == Items.TOTEM_OF_UNDYING))
/* 58 */         packet.setCanceled(true); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check(BlockPos pos) {
/* 63 */     return ((mc.world.getBlockState(pos).getBlock() == Blocks.CHEST && ((Boolean)this.Chest.getValue()).booleanValue()) || (mc.world
/* 64 */       .getBlockState(pos).getBlock() == Blocks.ENDER_CHEST && ((Boolean)this.EnderChest.getValue()).booleanValue()) || (mc.world
/* 65 */       .getBlockState(pos).getBlock() == Blocks.TRAPPED_CHEST && ((Boolean)this.Trapped_Chest.getValue()).booleanValue()) || (mc.world
/* 66 */       .getBlockState(pos).getBlock() == Blocks.HOPPER && ((Boolean)this.Hopper.getValue()).booleanValue()) || (mc.world
/* 67 */       .getBlockState(pos).getBlock() == Blocks.DISPENSER && ((Boolean)this.Dispenser.getValue()).booleanValue()) || (mc.world
/* 68 */       .getBlockState(pos).getBlock() == Blocks.FURNACE && ((Boolean)this.Furnace.getValue()).booleanValue()) || (mc.world
/* 69 */       .getBlockState(pos).getBlock() == Blocks.BEACON && ((Boolean)this.Beacon.getValue()).booleanValue()) || (mc.world
/* 70 */       .getBlockState(pos).getBlock() == Blocks.CRAFTING_TABLE && ((Boolean)this.Crafting_Table.getValue()).booleanValue()) || (mc.world
/* 71 */       .getBlockState(pos).getBlock() == Blocks.ANVIL && ((Boolean)this.Anvil.getValue()).booleanValue()) || (mc.world
/* 72 */       .getBlockState(pos).getBlock() == Blocks.ENCHANTING_TABLE && ((Boolean)this.Enchanting_table.getValue()).booleanValue()) || (mc.world
/* 73 */       .getBlockState(pos).getBlock() == Blocks.BREWING_STAND && ((Boolean)this.Brewing_Stand.getValue()).booleanValue()) || (mc.world
/* 74 */       .getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockShulkerBox && ((Boolean)this.ShulkerBox.getValue()).booleanValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\AntiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.modules.combat.Surround;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.BlockUtil;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import net.minecraft.block.BlockEnderChest;
/*    */ import net.minecraft.block.BlockObsidian;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class BurrowX extends Module {
/* 25 */   private final Setting<Boolean> breakcrystal = register(new Setting("BreakCrystal", Boolean.valueOf(false))); public static BurrowX INSTANCE;
/* 26 */   private final Setting<Boolean> antiSuicide = register(new Setting("AntiSuicide", Boolean.valueOf(true), v -> ((Boolean)this.breakcrystal.getValue()).booleanValue()));
/* 27 */   private final Setting<Boolean> center = register(new Setting("TPCenter", Boolean.valueOf(false)));
/*    */   
/*    */   public BurrowX() {
/* 30 */     super("BurrowX", "burrow", Module.Category.PLAYER, true, false, false);
/* 31 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 36 */     BlockPos player = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/*    */     
/* 38 */     if (!BlockUtil.isAir(player) || !BlockUtil.isAir(player.add(0, 2, 0))) {
/* 39 */       disable();
/*    */       
/*    */       return;
/*    */     } 
/* 43 */     if (((Boolean)this.breakcrystal.getValue()).booleanValue() && BlockUtil.CrystalCheck(player))
/* 44 */       Surround.breakcrystal(((Boolean)this.antiSuicide.getValue()).booleanValue()); 
/* 45 */     BlockPos startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
/* 46 */     if (((Boolean)this.center.getValue()).booleanValue()) {
/* 47 */       OyVey.positionManager.setPositionPacket(startPos.getX() + 0.5D, startPos.getY(), startPos.getZ() + 0.5D, true, true, true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onTick() {
/* 52 */     if (mc.player == null || mc.world == null)
/*    */       return; 
/* 54 */     if (mc.player.onGround) {
/*    */       
/* 56 */       int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 57 */       int chestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
/* 58 */       int old = mc.player.inventory.currentItem;
/*    */       
/* 60 */       if (obbySlot != -1 || chestSlot != -1) {
/* 61 */         mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.4199999868869781D, mc.player.posZ, false));
/* 62 */         mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805212017D, mc.player.posZ, false));
/* 63 */         mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.9999957640154541D, mc.player.posZ, false));
/* 64 */         mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.1661092609382138D, mc.player.posZ, false));
/* 65 */         mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
/* 66 */         mc.player.setSneaking(true);
/* 67 */         if (old != InventoryUtil.findHotbarBlock(BlockEnderChest.class)) {
/* 68 */           mc.player.inventory.currentItem = (obbySlot != -1) ? obbySlot : chestSlot;
/* 69 */           mc.playerController.updateController();
/*    */         } 
/* 71 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos(mc.player.posX, mc.player.posY - 1.0D, mc.player.posZ), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
/* 72 */         mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
/* 73 */         mc.player.setSneaking(false);
/* 74 */         mc.player.inventory.currentItem = old;
/* 75 */         mc.playerController.updateController();
/* 76 */         mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, -7.0D, mc.player.posZ, false));
/*    */       } else {
/* 78 */         Command.sendMessage(ChatFormatting.RED + "No Obsidian...");
/*    */       } 
/* 80 */       disable();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\BurrowX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
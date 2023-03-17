/*    */ package me.abHack.features.modules.combat;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class FastBow extends Module {
/*    */   public FastBow() {
/* 10 */     super("FastBow", "Accelerates bow shots.", Module.Category.COMBAT, true, false, false);
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 14 */     if (mc.player.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
/* 15 */       mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
/* 16 */       mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
/* 17 */       mc.player.stopActiveHand();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\FastBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
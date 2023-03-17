/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class MiddlePearl extends Module {
/* 18 */   private final Setting<Boolean> fast_pearl = register(new Setting("Fast Pearl", Boolean.valueOf(true)));
/* 19 */   private final Setting<Integer> speed = register(new Setting("Speed", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(100), v -> ((Boolean)this.fast_pearl.getValue()).booleanValue()));
/*    */   
/*    */   public MiddlePearl() {
/* 22 */     super("MiddlePearl", "Key Pearl", Module.Category.MOVEMENT, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 27 */     RayTraceResult.Type type = mc.objectMouseOver.typeOfHit;
/*    */     
/* 29 */     if (type.equals(RayTraceResult.Type.MISS) && Mouse.isButtonDown(2)) {
/* 30 */       int oldSlot = mc.player.inventory.currentItem;
/*    */       
/* 32 */       int pearlSlot = InventoryUtil.getItemHotbar(Items.ENDER_PEARL);
/*    */       
/* 34 */       if (pearlSlot != -1) {
/* 35 */         if (((Boolean)this.fast_pearl.getValue()).booleanValue()) {
/* 36 */           mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SPRINTING));
/* 37 */           for (int index = 0; index < ((Integer)this.speed.getValue()).intValue(); index++) {
/* 38 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 1.0E-10D, mc.player.posZ, true));
/* 39 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-10D, mc.player.posZ, false));
/*    */           } 
/*    */         } 
/* 42 */         mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(pearlSlot));
/* 43 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 44 */         mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldSlot));
/* 45 */         mc.playerController.updateController();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\MiddlePearl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.event.events.LiquidInteractEvent;
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.ShiftBlocks;
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class Interact
/*    */   extends Module
/*    */ {
/* 18 */   public Setting<Boolean> heightLimit = register(new Setting("HeightLimit", Boolean.valueOf(true)));
/* 19 */   public Setting<Boolean> liquid = register(new Setting("Liquid", Boolean.valueOf(true), "Allows you to place blocks on liquid"));
/* 20 */   public Setting<Boolean> ignoreContainer = register(new Setting("IgnoreContainers", Boolean.valueOf(false), "Ignores containers"));
/* 21 */   public Setting<Boolean> worldBorder = register(new Setting("WorldBorder", Boolean.valueOf(false), "Allows you to interact with blocks at the world border"));
/* 22 */   public Setting<Boolean> noSwing = register(new Setting("NoSwing", Boolean.valueOf(false), "Cancels the server side animation for swinging"));
/*    */ 
/*    */   
/*    */   public Interact() {
/* 26 */     super("Interact", "ForceInteract", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onLiquidInteract(LiquidInteractEvent event) {
/* 34 */     if (((Boolean)this.liquid.getValue()).booleanValue() || (event.getLiquidLevel() && ((Integer)event.getBlockState().getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0)) {
/* 35 */       event.setCanceled(true);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 45 */     if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
/* 46 */       BlockPos limitPosition = ((CPacketPlayerTryUseItemOnBlock)event.getPacket()).getPos();
/*    */ 
/*    */       
/* 49 */       if (((Boolean)this.heightLimit.getValue()).booleanValue() && limitPosition.getY() >= mc.world.getHeight() - 1 && ((CPacketPlayerTryUseItemOnBlock)event.getPacket()).getDirection().equals(EnumFacing.UP)) {
/* 50 */         ((CPacketPlayerTryUseItemOnBlock)event.getPacket()).placedBlockDirection = EnumFacing.DOWN;
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 55 */       if (((Boolean)this.worldBorder.getValue()).booleanValue() && mc.world.getWorldBorder().contains(limitPosition))
/*    */       {
/* 57 */         ((CPacketPlayerTryUseItemOnBlock)event.getPacket()).placedBlockDirection = ((CPacketPlayerTryUseItemOnBlock)event.getPacket()).getDirection().getOpposite();
/*    */       }
/*    */ 
/*    */       
/* 61 */       if (((Boolean)this.ignoreContainer.getValue()).booleanValue() && 
/* 62 */         ShiftBlocks.contains(mc.world.getBlockState(limitPosition).getBlock())) {
/* 63 */         event.setCanceled(true);
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketAnimation && ((Boolean)this.noSwing.getValue()).booleanValue())
/* 71 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\Interact.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
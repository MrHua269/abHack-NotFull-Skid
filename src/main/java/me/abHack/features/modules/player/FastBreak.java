/*    */ package me.abHack.features.modules.player;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*    */ import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*    */
/*    */ public class FastBreak extends Module {
/*    */   public FastBreak() {
/* 11 */     super("FastBreak", "fast++", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */   
/*    */   public static float getHardness(BlockPos pos) {
/* 15 */     return mc.world.getBlockState(pos).getPlayerRelativeBlockHardness((EntityPlayer)mc.player, (World)mc.world, pos);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 21 */     mc.playerController.blockHitDelay = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
/* 27 */     float progress = mc.playerController.curBlockDamageMP + getHardness(event.getPos());
/* 28 */     if (progress >= 1.0F)
/* 29 */       return;  mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), mc.objectMouseOver.sideHit));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\FastBreak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
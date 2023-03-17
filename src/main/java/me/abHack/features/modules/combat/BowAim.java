/*    */ package me.abHack.features.modules.combat;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class BowAim extends Module {
/* 16 */   public final Setting<Boolean> packet = register(new Setting("PacketRotate", Boolean.valueOf(true)));
/* 17 */   public final Setting<Boolean> fastbow = register(new Setting("FastBow", Boolean.valueOf(true)));
/*    */   
/*    */   public BowAim() {
/* 20 */     super("BowAim", "Automatically aims your bow at your opponent", Module.Category.COMBAT, true, false, false);
/*    */   }
/*    */   
/*    */   public static Vec3d interpolateEntity(Entity entity, float time) {
/* 24 */     return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
/*    */   }
/*    */   
/*    */   public static float[] calcAngle(Vec3d from, Vec3d to) {
/* 28 */     double difX = to.x - from.x;
/* 29 */     double difY = (to.y - from.y) * -1.0D;
/* 30 */     double difZ = to.z - from.z;
/* 31 */     double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
/* 32 */     return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 36 */     if (mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
/* 37 */       EntityPlayer player = null;
/* 38 */       float tickDis = 100.0F;
/* 39 */       for (EntityPlayer p : mc.world.playerEntities) {
/*    */         float dis;
/* 41 */         if (p instanceof net.minecraft.client.entity.EntityPlayerSP || OyVey.friendManager.isFriend(p.getName()) || (dis = p.getDistance((Entity)mc.player)) >= tickDis)
/*    */           continue; 
/* 43 */         tickDis = dis;
/* 44 */         player = p;
/*    */       } 
/* 46 */       if (player != null) {
/* 47 */         Vec3d pos = interpolateEntity((Entity)player, mc.getRenderPartialTicks());
/* 48 */         float[] angels = calcAngle(interpolateEntity((Entity)mc.player, mc.getRenderPartialTicks()), pos);
/* 49 */         if (((Boolean)this.packet.getValue()).booleanValue()) {
/* 50 */           mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angels[0], angels[1], mc.player.onGround));
/*    */         } else {
/* 52 */           mc.player.rotationYaw = angels[0];
/* 53 */           mc.player.rotationPitch = angels[1];
/*    */         } 
/*    */       } 
/*    */     } 
/* 57 */     if (mc.player.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3 && ((Boolean)this.fastbow.getValue()).booleanValue()) {
/* 58 */       mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
/* 59 */       mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
/* 60 */       mc.player.stopActiveHand();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\BowAim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
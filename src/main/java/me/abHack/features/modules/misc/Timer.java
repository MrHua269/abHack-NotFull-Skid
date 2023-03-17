/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ 
/*    */ public class Timer extends Module {
/* 10 */   public static Timer INSTANCE = new Timer();
/* 11 */   public final Setting<Float> timer = register(new Setting("Timer", Float.valueOf(1.8F), Float.valueOf(1.0F), Float.valueOf(6.0F)));
/* 12 */   private final Setting<Boolean> bypass = register(new Setting("Bypass", Boolean.valueOf(false)));
/*    */ 
/*    */   
/*    */   public Timer() {
/* 16 */     super("Timer", "Timer", Module.Category.MISC, true, false, false);
/* 17 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 22 */     mc.timer.tickLength = 50.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 28 */     if (((Boolean)this.bypass.getValue()).booleanValue())
/* 29 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SLEEPING)); 
/* 30 */     mc.timer.tickLength = 50.0F / ((Float)this.timer.getValue()).floatValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplayInfo() {
/* 36 */     return this.timer.getValue() + "";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.misc;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.login.server.SPacketDisconnect;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public class AutoLog extends Module {
/* 14 */   private final Setting<Double> health = register(new Setting("Health", Double.valueOf(13.0D), Double.valueOf(0.0D), Double.valueOf(36.0D)));
/* 15 */   private final Setting<Integer> totem = register(new Setting("Totem", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10)));
/*    */   
/*    */   public AutoLog() {
/* 18 */     super("AutoLog", "run", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 24 */     int totems = InventoryUtil.getItemCount(Items.TOTEM_OF_UNDYING);
/* 25 */     if (totems <= ((Integer)this.totem.getValue()).intValue() && EntityUtil.getHealth((Entity)mc.player) <= ((Double)this.health.getValue()).doubleValue()) {
/* 26 */       mc.player.connection.sendPacket((Packet)new SPacketDisconnect((ITextComponent)new TextComponentString("run")));
/* 27 */       disable();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\AutoLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
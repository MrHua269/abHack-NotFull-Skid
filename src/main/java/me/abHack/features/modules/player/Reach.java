/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class Reach
/*    */   extends Module
/*    */ {
/* 10 */   private final Setting<Integer> Reach = register(new Setting("Reach", Integer.valueOf(6), Integer.valueOf(5), Integer.valueOf(10)));
/*    */   
/*    */   public Reach() {
/* 13 */     super("Reach", "reach", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 18 */     mc.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(((Integer)this.Reach.getValue()).intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 23 */     mc.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(5.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\Reach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
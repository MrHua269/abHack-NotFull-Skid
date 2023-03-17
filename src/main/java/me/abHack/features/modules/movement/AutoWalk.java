/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraftforge.client.event.InputUpdateEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoWalk
/*    */   extends Module {
/*    */   public AutoWalk() {
/* 10 */     super("AutoWalk", "Automatically walks in a straight line", Module.Category.MOVEMENT, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onUpdateInput(InputUpdateEvent event) {
/* 15 */     (event.getMovementInput()).moveForward = 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\AutoWalk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
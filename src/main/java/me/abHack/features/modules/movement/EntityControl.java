/*    */ package me.abHack.features.modules.movement;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ 
/*    */ public class EntityControl
/*    */   extends Module {
/*    */   public static EntityControl INSTANCE;
/*    */   
/*    */   public EntityControl() {
/* 10 */     super("EntityControl", "Control entities with the force or some shit", Module.Category.MOVEMENT, false, false, false);
/* 11 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\movement\EntityControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
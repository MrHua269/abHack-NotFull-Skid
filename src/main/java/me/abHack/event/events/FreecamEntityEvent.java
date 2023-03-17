/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class FreecamEntityEvent
/*    */   extends Event {
/*    */   private Entity entity;
/*    */   
/*    */   public FreecamEntityEvent(Entity entity) {
/* 13 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 17 */     return this.entity;
/*    */   }
/*    */   
/*    */   public void setEntity(Entity entity) {
/* 21 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\FreecamEntityEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
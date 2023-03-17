/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class EntityWorldEvent
/*    */   extends Event {
/*    */   private final Entity entity;
/*    */   
/*    */   public EntityWorldEvent(Entity entity) {
/* 13 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 17 */     return this.entity;
/*    */   }
/*    */   
/*    */   public static class EntitySpawnEvent extends EntityWorldEvent {
/*    */     public EntitySpawnEvent(Entity entity) {
/* 22 */       super(entity);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class EntityUpdateEvent extends EntityWorldEvent {
/*    */     public EntityUpdateEvent(Entity entity) {
/* 28 */       super(entity);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\EntityWorldEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
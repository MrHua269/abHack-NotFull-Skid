/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class ShaderColorEvent
/*    */   extends Event
/*    */ {
/*    */   private final Entity entity;
/*    */   private Color color;
/*    */   
/*    */   public ShaderColorEvent(Entity entity) {
/* 16 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 20 */     return this.entity;
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 24 */     return this.color;
/*    */   }
/*    */   
/*    */   public void setColor(Color in) {
/* 28 */     this.color = in;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\ShaderColorEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
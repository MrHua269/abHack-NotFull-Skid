/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import me.abHack.event.EventStage;
/*    */ import me.abHack.features.Feature;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ @Cancelable
/*    */ public class ClientEvent extends EventStage {
/*    */   private Feature feature;
/*    */   private Setting setting;
/*    */   
/*    */   public ClientEvent(int stage, Feature feature) {
/* 14 */     super(stage);
/* 15 */     this.feature = feature;
/*    */   }
/*    */   
/*    */   public ClientEvent(Setting setting) {
/* 19 */     super(2);
/* 20 */     this.setting = setting;
/*    */   }
/*    */   
/*    */   public Feature getFeature() {
/* 24 */     return this.feature;
/*    */   }
/*    */   
/*    */   public Setting getSetting() {
/* 28 */     return this.setting;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\ClientEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
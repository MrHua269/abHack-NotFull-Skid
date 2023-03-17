/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class VillagerNotifier
/*    */   extends Module
/*    */ {
/* 12 */   private final Set<Entity> entities = new HashSet<>();
/*    */   
/*    */   public VillagerNotifier() {
/* 15 */     super("VillagerNotifier", "Notifies you when a Villager is discovered", Module.Category.MISC, true, false, false);
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 19 */     this.entities.clear();
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 23 */     for (Entity entity : mc.world.loadedEntityList) {
/* 24 */       if (!(entity instanceof net.minecraft.entity.passive.EntityVillager) || this.entities.contains(entity))
/*    */         continue; 
/* 26 */       Command.sendMessage("Villager Detected at: X:" + (int)entity.posX + " X: " + (int)entity.posY + " Z:" + (int)entity.posZ);
/* 27 */       this.entities.add(entity);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\VillagerNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
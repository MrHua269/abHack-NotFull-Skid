/*     */ package me.abHack.event.events;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Cancelable
/*     */ public class RenderCrystalEvent
/*     */   extends Event
/*     */ {
/*     */   public static class RenderCrystalPreEvent
/*     */     extends RenderCrystalEvent
/*     */   {
/*     */     private final ModelBase modelBase;
/*     */     private final Entity entity;
/*     */     private final float limbSwing;
/*     */     private final float limbSwingAmount;
/*     */     private final float ageInTicks;
/*     */     private final float netHeadYaw;
/*     */     private final float headPitch;
/*     */     private final float scaleFactor;
/*     */     
/*     */     public RenderCrystalPreEvent(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
/*  31 */       this.modelBase = modelBase;
/*  32 */       this.entity = entity;
/*  33 */       this.limbSwing = limbSwing;
/*  34 */       this.limbSwingAmount = limbSwingAmount;
/*  35 */       this.ageInTicks = ageInTicks;
/*  36 */       this.netHeadYaw = netHeadYaw;
/*  37 */       this.headPitch = headPitch;
/*  38 */       this.scaleFactor = scaleFactor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ModelBase getModelBase() {
/*  47 */       return this.modelBase;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Entity getEntity() {
/*  56 */       return this.entity;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getLimbSwing() {
/*  65 */       return this.limbSwing;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getLimbSwingAmount() {
/*  74 */       return this.limbSwingAmount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getAgeInTicks() {
/*  83 */       return this.ageInTicks;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getNetHeadYaw() {
/*  92 */       return this.netHeadYaw;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getHeadPitch() {
/* 101 */       return this.headPitch;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getScaleFactor() {
/* 110 */       return this.scaleFactor;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RenderCrystalPostEvent
/*     */     extends RenderCrystalEvent
/*     */   {
/*     */     private final ModelBase modelBase;
/*     */     private final ModelBase modelNoBase;
/*     */     private final EntityEnderCrystal entityEnderCrystal;
/*     */     private final double x;
/*     */     private final double y;
/*     */     private final double z;
/*     */     private final float entityYaw;
/*     */     private final float partialTicks;
/*     */     
/*     */     public RenderCrystalPostEvent(ModelBase modelBase, ModelBase modelNoBase, EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks) {
/* 127 */       this.modelBase = modelBase;
/* 128 */       this.modelNoBase = modelNoBase;
/* 129 */       this.entityEnderCrystal = entityEnderCrystal;
/* 130 */       this.x = x;
/* 131 */       this.y = y;
/* 132 */       this.z = z;
/* 133 */       this.entityYaw = entityYaw;
/* 134 */       this.partialTicks = partialTicks;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ModelBase getModelBase() {
/* 143 */       return this.modelBase;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ModelBase getModelNoBase() {
/* 152 */       return this.modelNoBase;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EntityEnderCrystal getEntityEnderCrystal() {
/* 161 */       return this.entityEnderCrystal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getX() {
/* 170 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getY() {
/* 179 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getZ() {
/* 188 */       return this.z;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getEntityYaw() {
/* 197 */       return this.entityYaw;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getPartialTicks() {
/* 206 */       return this.partialTicks;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\RenderCrystalEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
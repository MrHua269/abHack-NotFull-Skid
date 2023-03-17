/*     */ package me.abHack.event.events;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Cancelable
/*     */ public class RenderLivingEntityEvent
/*     */   extends Event
/*     */ {
/*     */   private final ModelBase modelBase;
/*     */   private final EntityLivingBase entityLivingBase;
/*     */   private final float limbSwing;
/*     */   private final float limbSwingAmount;
/*     */   private final float ageInTicks;
/*     */   private final float netHeadYaw;
/*     */   private final float headPitch;
/*     */   private final float scaleFactor;
/*     */   
/*     */   public RenderLivingEntityEvent(ModelBase modelBase, EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
/*  28 */     this.modelBase = modelBase;
/*  29 */     this.entityLivingBase = entityLivingBase;
/*  30 */     this.limbSwing = limbSwing;
/*  31 */     this.limbSwingAmount = limbSwingAmount;
/*  32 */     this.ageInTicks = ageInTicks;
/*  33 */     this.netHeadYaw = netHeadYaw;
/*  34 */     this.headPitch = headPitch;
/*  35 */     this.scaleFactor = scaleFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelBase getModelBase() {
/*  44 */     return this.modelBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getEntityLivingBase() {
/*  53 */     return this.entityLivingBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLimbSwing() {
/*  62 */     return this.limbSwing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLimbSwingAmount() {
/*  71 */     return this.limbSwingAmount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAgeInTicks() {
/*  80 */     return this.ageInTicks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getNetHeadYaw() {
/*  89 */     return this.netHeadYaw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeadPitch() {
/*  98 */     return this.headPitch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScaleFactor() {
/* 107 */     return this.scaleFactor;
/*     */   }
/*     */   
/*     */   public static class RenderLivingEntityPreEvent
/*     */     extends RenderLivingEntityEvent
/*     */   {
/*     */     public RenderLivingEntityPreEvent(ModelBase modelBase, EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
/* 114 */       super(modelBase, entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RenderLivingEntityPostEvent
/*     */     extends RenderLivingEntityEvent
/*     */   {
/*     */     public RenderLivingEntityPostEvent(ModelBase modelBase, EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
/* 122 */       super(modelBase, entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\RenderLivingEntityEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
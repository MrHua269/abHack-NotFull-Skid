/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.List;
/*    */ import me.abHack.event.events.EntityWorldEvent;
/*    */ import me.abHack.event.events.PushEvent;
/*    */ import me.abHack.features.modules.render.NoRender;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({World.class})
/*    */ public class MixinWorld {
/*    */   @Redirect(method = {"getEntitiesWithinAABB(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;getEntitiesOfTypeWithinAABB(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lcom/google/common/base/Predicate;)V"))
/*    */   public <T extends Entity> void getEntitiesOfTypeWithinAABBHook(Chunk chunk, Class<? extends T> entityClass, AxisAlignedBB aabb, List<T> listToFill, Predicate<? super T> filter) {
/*    */     try {
/* 28 */       chunk.getEntitiesOfTypeWithinAABB(entityClass, aabb, listToFill, filter);
/* 29 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Redirect(method = {"handleMaterialAcceleration"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
/*    */   public boolean isPushedbyWaterHook(Entity entity) {
/* 36 */     PushEvent event = new PushEvent(2, entity);
/* 37 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 38 */     return (entity.isPushedByWater() && !event.isCanceled());
/*    */   }
/*    */   
/*    */   @Inject(method = {"checkLightFor"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void checkLightForHook(EnumSkyBlock lightType, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
/* 43 */     if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).skyLightUpdate.getValue()).booleanValue() && lightType == EnumSkyBlock.SKY) {
/*    */       
/* 45 */       ci.setReturnValue(Boolean.TRUE);
/* 46 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"spawnEntity"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onSpawnEntity(Entity entity, CallbackInfoReturnable<Boolean> info) {
/* 53 */     EntityWorldEvent.EntitySpawnEvent entitySpawnEvent = new EntityWorldEvent.EntitySpawnEvent(entity);
/* 54 */     MinecraftForge.EVENT_BUS.post((Event)entitySpawnEvent);
/* 55 */     if (entitySpawnEvent.isCanceled()) {
/* 56 */       info.cancel();
/* 57 */       info.setReturnValue(Boolean.valueOf(false));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"updateEntity"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onUpdateEntity(Entity entity, CallbackInfo info) {
/* 64 */     EntityWorldEvent.EntityUpdateEvent entityUpdateEvent = new EntityWorldEvent.EntityUpdateEvent(entity);
/* 65 */     MinecraftForge.EVENT_BUS.post((Event)entityUpdateEvent);
/*    */     
/* 67 */     if (entityUpdateEvent.isCanceled())
/* 68 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
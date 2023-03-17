/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import me.abHack.util.ICachedEntityPlayer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ @Mixin({EntityLivingBase.class})
/*    */ public abstract class MixinEntityLivingBase
/*    */   extends MixinEntity
/*    */   implements ICachedEntityPlayer
/*    */ {
/*    */   @Unique
/*    */   protected float resistanceMultiplier;
/*    */   @Unique
/*    */   protected float blastMultiplier;
/*    */   @Unique
/*    */   protected float totalArmourValue;
/*    */   @Unique
/*    */   protected float armourToughness;
/*    */   
/*    */   @Shadow
/*    */   @Nullable
/*    */   public abstract PotionEffect getActivePotionEffect(Potion paramPotion);
/*    */   
/*    */   @Shadow
/*    */   public abstract int getTotalArmorValue();
/*    */   
/*    */   @Shadow
/*    */   public abstract IAttributeInstance getEntityAttribute(IAttribute paramIAttribute);
/*    */   
/*    */   @Shadow
/*    */   public abstract Iterable<ItemStack> getArmorInventoryList();
/*    */   
/*    */   public float getResistanceMultiplier() {
/* 43 */     return this.resistanceMultiplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getBlastMultiplier() {
/* 48 */     return this.blastMultiplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getTotalArmourValue() {
/* 53 */     return this.totalArmourValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getArmourToughness() {
/* 58 */     return this.armourToughness;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinEntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
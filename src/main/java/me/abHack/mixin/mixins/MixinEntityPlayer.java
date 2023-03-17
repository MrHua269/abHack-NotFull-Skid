/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.player.TpsSync;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Enchantments;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({EntityPlayer.class})
/*    */ public abstract class MixinEntityPlayer
/*    */   extends MixinEntityLivingBase {
/*    */   @Inject(method = {"onUpdate"}, at = {@At("RETURN")})
/*    */   private void onUpdatePost(CallbackInfo ci) {
/* 23 */     this.totalArmourValue = getTotalArmorValue();
/* 24 */     this.armourToughness = (float) getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
/*    */     
/* 26 */     PotionEffect resistance = getActivePotionEffect(MobEffects.RESISTANCE);
/* 27 */     if (resistance != null) {
/* 28 */       int amplifier = resistance.getAmplifier();
/* 29 */       this.resistanceMultiplier = Math.max(0.0F, 1.0F - (amplifier + 1) * 0.2F);
/*    */     } else {
/* 31 */       this.resistanceMultiplier = 0.8F;
/*    */     } 
/*    */     
/* 34 */     int genericEPF = 0;
/* 35 */     int blastEPF = 0;
/*    */     
/* 37 */     for (ItemStack stack : getArmorInventoryList()) {
/* 38 */       genericEPF += EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack);
/* 39 */       blastEPF += EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, stack) * 2;
/*    */     } 
/*    */     
/* 42 */     this.blastMultiplier = 1.0F - Math.min(genericEPF + blastEPF, 20) / 25.0F;
/*    */   }
/*    */   
/*    */   @Inject(method = {"getCooldownPeriod"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void getCooldownPeriodHook(CallbackInfoReturnable<Float> callbackInfoReturnable) {
/* 47 */     if (TpsSync.getInstance().isOn() && ((Boolean)(TpsSync.getInstance()).attack.getValue()).booleanValue())
/* 48 */       callbackInfoReturnable.setReturnValue(Float.valueOf((float)(1.0D / getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue() * 20.0D * OyVey.serverManager.getTpsFactor())));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
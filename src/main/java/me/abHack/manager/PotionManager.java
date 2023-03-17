/*    */ package me.abHack.manager;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import me.abHack.features.Feature;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ public class PotionManager
/*    */   extends Feature
/*    */ {
/* 17 */   private final Map<EntityPlayer, PotionList> potions = new ConcurrentHashMap<>();
/*    */   
/*    */   public List<PotionEffect> getOwnPotions() {
/* 20 */     return getPlayerPotions((EntityPlayer)mc.player);
/*    */   }
/*    */   
/*    */   public List<PotionEffect> getPlayerPotions(EntityPlayer player) {
/* 24 */     PotionList list = this.potions.get(player);
/* 25 */     List<PotionEffect> potions = new ArrayList<>();
/* 26 */     if (list != null) {
/* 27 */       potions = list.getEffects();
/*    */     }
/* 29 */     return potions;
/*    */   }
/*    */   
/*    */   public PotionEffect[] getImportantPotions(EntityPlayer player) {
/* 33 */     PotionEffect[] array = new PotionEffect[3];
/* 34 */     for (PotionEffect effect : getPlayerPotions(player)) {
/* 35 */       Potion potion = effect.getPotion();
/* 36 */       switch (I18n.format(potion.getName(), new Object[0]).toLowerCase()) {
/*    */         case "strength":
/* 38 */           array[0] = effect;
/*    */         
/*    */         case "weakness":
/* 41 */           array[1] = effect;
/*    */         
/*    */         case "speed":
/* 44 */           array[2] = effect;
/*    */       } 
/*    */     
/*    */     } 
/* 48 */     return array;
/*    */   }
/*    */   
/*    */   public String getPotionString(PotionEffect effect) {
/* 52 */     Potion potion = effect.getPotion();
/* 53 */     return I18n.format(potion.getName(), new Object[0]) + " " + (effect.getAmplifier() + 1) + " " + ChatFormatting.WHITE + Potion.getPotionDurationString(effect, 1.0F);
/*    */   }
/*    */   
/*    */   public String getColoredPotionString(PotionEffect effect) {
/* 57 */     return getPotionString(effect);
/*    */   }
/*    */   
/*    */   public static class PotionList {
/* 61 */     private final List<PotionEffect> effects = new ArrayList<>();
/*    */     
/*    */     public void addEffect(PotionEffect effect) {
/* 64 */       if (effect != null) {
/* 65 */         this.effects.add(effect);
/*    */       }
/*    */     }
/*    */     
/*    */     public List<PotionEffect> getEffects() {
/* 70 */       return this.effects;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\PotionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
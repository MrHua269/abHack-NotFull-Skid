/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import java.text.DecimalFormat;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NameLabel
/*    */   extends Module
/*    */ {
/* 18 */   public Setting<Integer> range = register(new Setting("Range", Integer.valueOf(30), Integer.valueOf(1), Integer.valueOf(60)));
/* 19 */   public Setting<Boolean> mobs = register(new Setting("Mobs", Boolean.valueOf(true)));
/* 20 */   public Setting<Boolean> animals = register(new Setting("Animals", Boolean.valueOf(true)));
/*    */   
/*    */   public NameLabel() {
/* 23 */     super("NameLabel", "", Module.Category.RENDER, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/* 28 */     for (Entity entity : mc.world.loadedEntityList) {
/* 29 */       if (entity instanceof net.minecraft.entity.player.EntityPlayer || entity instanceof net.minecraft.entity.item.EntityArmorStand)
/*    */         continue; 
/* 31 */       if (!(entity instanceof EntityLivingBase) || (
/* 32 */         !((Boolean)this.mobs.getValue()).booleanValue() && EntityUtil.isMobAggressive(entity)))
/*    */         continue; 
/* 34 */       if (!((Boolean)this.animals.getValue()).booleanValue() && EntityUtil.isPassive(entity))
/*    */         continue; 
/* 36 */       DecimalFormat df = new DecimalFormat("#0.00");
/* 37 */       EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
/* 38 */       RenderUtil.renderLabel((Entity)entityLivingBase, getNameColor(entity) + entityLivingBase.getDisplayName().getFormattedText() + " " + getHealthColor(entity) + df.format(EntityUtil.getHealth((Entity)entityLivingBase)) + " ❤", ((Integer)this.range.getValue()).intValue(), event.getPartialTicks());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ChatFormatting getNameColor(Entity entity) {
/* 44 */     if (EntityUtil.isMobAggressive(entity))
/* 45 */       return ChatFormatting.RED; 
/* 46 */     if (EntityUtil.isPassive(entity))
/* 47 */       return ChatFormatting.GREEN; 
/* 48 */     return ChatFormatting.YELLOW;
/*    */   }
/*    */   
/*    */   private ChatFormatting getHealthColor(Entity entity) {
/*    */     ChatFormatting color;
/* 53 */     EntityLivingBase livingBase = (EntityLivingBase)entity;
/* 54 */     int percentage = (int)(EntityUtil.getHealth(entity) / livingBase.getMaxHealth() * 100.0F);
/*    */     
/* 56 */     if (percentage >= 80)
/* 57 */     { color = ChatFormatting.DARK_GREEN; }
/* 58 */     else if (percentage >= 70)
/* 59 */     { color = ChatFormatting.GREEN; }
/* 60 */     else if (percentage >= 60)
/* 61 */     { color = ChatFormatting.YELLOW; }
/* 62 */     else if (percentage >= 40)
/* 63 */     { color = ChatFormatting.RED; }
/* 64 */     else { color = ChatFormatting.DARK_RED; }
/*    */     
/* 66 */     return color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\NameLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
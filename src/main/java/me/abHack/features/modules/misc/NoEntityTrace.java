/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ 
/*    */ 
/*    */ public class NoEntityTrace
/*    */   extends Module
/*    */ {
/* 14 */   private static NoEntityTrace INSTANCE = new NoEntityTrace();
/* 15 */   public Setting<Boolean> pick = register(new Setting("Pick", Boolean.valueOf(true)));
/* 16 */   public Setting<Boolean> gap = register(new Setting("Gap", Boolean.valueOf(true)));
/* 17 */   public Setting<Boolean> totem = register(new Setting("Totem", Boolean.valueOf(false)));
/* 18 */   public Setting<Boolean> obby = register(new Setting("Obby", Boolean.valueOf(false)));
/*    */   public boolean noTrace;
/*    */   
/*    */   public NoEntityTrace() {
/* 22 */     super("NoEntityTrace", "Mine through entities", Module.Category.MISC, false, false, false);
/* 23 */     setInstance();
/*    */   }
/*    */   
/*    */   public static NoEntityTrace getINSTANCE() {
/* 27 */     if (INSTANCE == null) {
/* 28 */       INSTANCE = new NoEntityTrace();
/*    */     }
/* 30 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 34 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 39 */     Item item = mc.player.getHeldItemMainhand().getItem();
/* 40 */     if (item instanceof net.minecraft.item.ItemPickaxe && ((Boolean)this.pick.getValue()).booleanValue()) {
/* 41 */       this.noTrace = true;
/*    */       return;
/*    */     } 
/* 44 */     if (item == Items.GOLDEN_APPLE && ((Boolean)this.gap.getValue()).booleanValue()) {
/* 45 */       this.noTrace = true;
/*    */       return;
/*    */     } 
/* 48 */     if (item == Items.TOTEM_OF_UNDYING && ((Boolean)this.totem.getValue()).booleanValue()) {
/* 49 */       this.noTrace = true;
/*    */       return;
/*    */     } 
/* 52 */     if (item instanceof ItemBlock) {
/* 53 */       this.noTrace = (((ItemBlock)item).getBlock() == Blocks.OBSIDIAN && ((Boolean)this.obby.getValue()).booleanValue());
/*    */       return;
/*    */     } 
/* 56 */     this.noTrace = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\NoEntityTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
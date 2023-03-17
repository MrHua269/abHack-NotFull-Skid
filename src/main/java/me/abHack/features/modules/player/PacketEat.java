/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class PacketEat
/*    */   extends Module {
/*  8 */   private static PacketEat INSTANCE = new PacketEat();
/*  9 */   public Setting<Boolean> food = register(new Setting("Food", Boolean.valueOf(true)));
/* 10 */   public Setting<Boolean> potion = register(new Setting("Potion", Boolean.valueOf(false)));
/*    */   
/*    */   public PacketEat() {
/* 13 */     super("PacketEat", "PacketEat", Module.Category.PLAYER, true, false, false);
/* 14 */     setInstance();
/*    */   }
/*    */   
/*    */   public static PacketEat getInstance() {
/* 18 */     if (INSTANCE == null) {
/* 19 */       INSTANCE = new PacketEat();
/*    */     }
/* 21 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 25 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\PacketEat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
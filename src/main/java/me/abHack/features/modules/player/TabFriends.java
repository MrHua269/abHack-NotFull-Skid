/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class TabFriends
/*    */   extends Module {
/*  8 */   public static String color = "";
/*    */   public static TabFriends INSTANCE;
/* 10 */   public Setting<FriendColor> mode = register(new Setting("Color", FriendColor.Green));
/* 11 */   public Setting<Boolean> prefix = register(new Setting("prefix", Boolean.valueOf(true)));
/*    */   
/*    */   public TabFriends() {
/* 14 */     super("TabFriends", "Renders your friends differently in the tablist", Module.Category.PLAYER, true, false, false);
/* 15 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 20 */     switch ((FriendColor)this.mode.getValue()) {
/*    */       case DarkRed:
/* 22 */         color = "§4";
/*    */         break;
/*    */       case Red:
/* 25 */         color = "§c";
/*    */         break;
/*    */       case Gold:
/* 28 */         color = "§6";
/*    */         break;
/*    */       case Yellow:
/* 31 */         color = "§e";
/*    */         break;
/*    */       case DarkGreen:
/* 34 */         color = "§2";
/*    */         break;
/*    */       case Green:
/* 37 */         color = "§a";
/*    */         break;
/*    */       case Aqua:
/* 40 */         color = "§b";
/*    */         break;
/*    */       case DarkAqua:
/* 43 */         color = "§3";
/*    */         break;
/*    */       case DarkBlue:
/* 46 */         color = "§1";
/*    */         break;
/*    */       case Blue:
/* 49 */         color = "§9";
/*    */         break;
/*    */       case LightPurple:
/* 52 */         color = "§d";
/*    */         break;
/*    */       case DarkPurple:
/* 55 */         color = "§5";
/*    */         break;
/*    */       case Gray:
/* 58 */         color = "§7";
/*    */         break;
/*    */       case DarkGray:
/* 61 */         color = "§8";
/*    */         break;
/*    */       case Black:
/* 64 */         color = "§0";
/*    */         break;
/*    */       case None:
/* 67 */         color = "";
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private enum FriendColor
/*    */   {
/* 75 */     DarkRed, Red, Gold, Yellow, DarkGreen, Green, Aqua, DarkAqua, DarkBlue, Blue, LightPurple, DarkPurple, Gray, DarkGray, Black, None;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\TabFriends.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
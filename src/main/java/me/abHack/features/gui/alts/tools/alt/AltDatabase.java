/*    */ package me.abHack.features.gui.alts.tools.alt;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import me.abHack.features.gui.alts.tools.Config;
/*    */ 
/*    */ public class AltDatabase
/*    */   implements Serializable
/*    */ {
/*    */   public static final long serialVersionUID = -1585600597L;
/*    */   private static AltDatabase instance;
/* 12 */   private final ArrayList<AccountData> altList = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void loadFromConfig() {
/* 18 */     if (instance == null) {
/* 19 */       instance = (AltDatabase)Config.getInstance().getKey("altaccounts");
/*    */     }
/*    */   }
/*    */   
/*    */   private static void saveToConfig() {
/* 24 */     Config.getInstance().setKey("altaccounts", instance);
/*    */   }
/*    */   
/*    */   public static AltDatabase getInstance() {
/* 28 */     loadFromConfig();
/* 29 */     if (instance == null) {
/* 30 */       instance = new AltDatabase();
/* 31 */       saveToConfig();
/*    */     } 
/* 33 */     return instance;
/*    */   }
/*    */   
/*    */   public ArrayList<AccountData> getAlts() {
/* 37 */     return this.altList;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\tools\alt\AltDatabase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
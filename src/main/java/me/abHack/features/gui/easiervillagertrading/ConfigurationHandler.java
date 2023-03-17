/*    */ package me.abHack.features.gui.easiervillagertrading;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraftforge.common.config.Configuration;
/*    */ import net.minecraftforge.fml.client.event.ConfigChangedEvent;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigurationHandler
/*    */ {
/*    */   private static ConfigurationHandler instance;
/*    */   private Configuration config;
/*    */   private boolean showLeft;
/*    */   private int leftPixelOffset;
/*    */   
/*    */   public static ConfigurationHandler getInstance() {
/* 19 */     if (instance == null)
/* 20 */       instance = new ConfigurationHandler(); 
/* 21 */     return instance;
/*    */   }
/*    */   
/*    */   public static Configuration getConfig() {
/* 25 */     return (getInstance()).config;
/*    */   }
/*    */   
/*    */   public static boolean showLeft() {
/* 29 */     return (getInstance()).showLeft;
/*    */   }
/*    */   
/*    */   public static int leftPixelOffset() {
/* 33 */     return (getInstance()).leftPixelOffset;
/*    */   }
/*    */   
/*    */   public void load(File configFile) {
/* 37 */     if (this.config == null) {
/* 38 */       this.config = new Configuration(configFile);
/* 39 */       loadConfig();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
/* 46 */     if (event.getModID().equalsIgnoreCase("easiervillagertrading")) {
/* 47 */       loadConfig();
/*    */     }
/*    */   }
/*    */   
/*    */   private void loadConfig() {
/* 52 */     this.showLeft = this.config.getBoolean("Trades list left", "client", 
/* 53 */         Loader.isModLoaded("jei"), "Show trades list to the left, for Just Enough Items compatibility");
/*    */     
/* 55 */     this.leftPixelOffset = this.config.getInt("Trades left pixel offset", "client", 0, 0, 2147483647, "How many pixels left of the GUI the trades list will be shown. Use 0 for auto detect. Only used if Trades list left is true.");
/*    */ 
/*    */ 
/*    */     
/* 59 */     if (this.config.hasChanged())
/* 60 */       this.config.save(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\easiervillagertrading\ConfigurationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
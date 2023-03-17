/*    */ package me.abHack.features.gui.alts.ias;
/*    */ 
/*    */ import me.abHack.features.gui.alts.MR;
/*    */ import me.abHack.features.gui.alts.ias.config.ConfigValues;
/*    */ import me.abHack.features.gui.alts.ias.events.ClientEvents;
/*    */ import me.abHack.features.gui.alts.ias.tools.SkinTools;
/*    */ import me.abHack.features.gui.alts.iasencrypt.Standards;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.common.config.Configuration;
/*    */ import net.minecraftforge.common.config.Property;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*    */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*    */ import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
/*    */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*    */ 
/*    */ @Mod(modid = "ias", name = "In-Game Account Switcher", clientSideOnly = true, guiFactory = "me.abHack.features.gui.alts.ias.config.IASGuiFactory", updateJSON = "https://thefireplace.bitnamiapp.com/jsons/ias.json", acceptedMinecraftVersions = "[1.11,)")
/*    */ public class IAS
/*    */ {
/*    */   public static Configuration config;
/*    */   
/*    */   public static void syncConfig() {
/* 24 */     ConfigValues.CASESENSITIVE = CASESENSITIVE_PROPERTY.getBoolean();
/* 25 */     ConfigValues.ENABLERELOG = ENABLERELOG_PROPERTY.getBoolean();
/* 26 */     if (config.hasChanged())
/* 27 */       config.save(); 
/*    */   }
/*    */   private static Property CASESENSITIVE_PROPERTY; private static Property ENABLERELOG_PROPERTY;
/*    */   
/*    */   @EventHandler
/*    */   public void preInit(FMLPreInitializationEvent event) {
/* 33 */     config = new Configuration(event.getSuggestedConfigurationFile());
/* 34 */     config.load();
/* 35 */     CASESENSITIVE_PROPERTY = config.get("general", "ias.cfg.casesensitive", false, I18n.format("ias.cfg.casesensitive.tooltip", new Object[0]));
/* 36 */     ENABLERELOG_PROPERTY = config.get("general", "ias.cfg.enablerelog", false, I18n.format("ias.cfg.enablerelog.tooltip", new Object[0]));
/* 37 */     syncConfig();
/* 38 */     if (!(event.getModMetadata()).version.equals("${version}")) {
/* 39 */       Standards.updateFolder();
/*    */     } else {
/* 41 */       System.out.println("Dev environment detected!");
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void init(FMLInitializationEvent event) {
/* 47 */     MR.init();
/* 48 */     MinecraftForge.EVENT_BUS.register(new ClientEvents());
/* 49 */     Standards.importAccounts();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void postInit(FMLPostInitializationEvent event) {
/* 54 */     SkinTools.cacheSkins();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\IAS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
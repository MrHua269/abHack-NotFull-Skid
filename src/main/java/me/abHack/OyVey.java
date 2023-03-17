/*     */ package me.abHack;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import me.abHack.features.gui.easiervillagertrading.ConfigurationHandler;
/*     */ import me.abHack.features.gui.easiervillagertrading.OpenTradeEventHandler;
import me.abHack.manager.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import me.abHack.util.IconUtil;
/*     */ import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

/*     */
/*     */ @Mod(modid = "ab", name = "ab-Hack", version = "0.0.1")
/*     */ public class OyVey {
/*  24 */   public static final Logger LOGGER = LogManager.getLogger("ab-Hack");
/*     */   
/*     */   public static CommandManager commandManager;
/*     */   
/*     */   public static FriendManager friendManager;
/*     */   
/*     */   public static ModuleManager moduleManager;
/*     */   
/*     */   public static ColorManager colorManager;
/*     */   public static PotionManager potionManager;
/*     */   public static RotationManager rotationManager;
/*     */   public static PositionManager positionManager;
/*     */   public static SpeedManager speedManager;
/*     */   public static ReloadManager reloadManager;
/*     */   public static FileManager fileManager;
/*     */   public static ConfigManager configManager;
/*     */   public static ServerManager serverManager;
/*     */   public static EventManager eventManager;
/*     */   public static TextManager textManager;
/*     */   public static BreakManager breakManager;
/*     */   @Mod.Instance
/*     */   public static OyVey INSTANCE;
/*     */   private static boolean unloaded = false;
/*     */   
/*     */   public static void load() {
/*  49 */     unloaded = false;
/*  50 */     if (reloadManager != null) {
/*  51 */       reloadManager.unload();
/*  52 */       reloadManager = null;
/*     */     } 
/*  54 */     textManager = new TextManager();
/*  55 */     commandManager = new CommandManager();
/*  56 */     friendManager = new FriendManager();
/*  57 */     moduleManager = new ModuleManager();
/*  58 */     rotationManager = new RotationManager();
/*  59 */     eventManager = new EventManager();
/*  60 */     speedManager = new SpeedManager();
/*  61 */     potionManager = new PotionManager();
/*  62 */     serverManager = new ServerManager();
/*  63 */     fileManager = new FileManager();
/*  64 */     colorManager = new ColorManager();
/*  65 */     positionManager = new PositionManager();
/*  66 */     configManager = new ConfigManager();
/*  67 */     breakManager = new BreakManager();
/*  68 */     moduleManager.init();
/*  69 */     LOGGER.info("Modules loaded.");
/*  70 */     configManager.init();
/*  71 */     eventManager.init();
/*  72 */     LOGGER.info("EventManager loaded.");
/*  73 */     moduleManager.onLoad();
/*     */   }
/*     */   
/*     */   public static void unload(boolean unload) {
/*  77 */     if (unload) {
/*  78 */       reloadManager = new ReloadManager();
/*  79 */       reloadManager.init((commandManager != null) ? commandManager.getPrefix() : ".");
/*     */     } 
/*  81 */     onUnload();
/*  82 */     eventManager = null;
/*  83 */     friendManager = null;
/*  84 */     speedManager = null;
/*  85 */     positionManager = null;
/*  86 */     rotationManager = null;
/*  87 */     configManager = null;
/*  88 */     commandManager = null;
/*  89 */     colorManager = null;
/*  90 */     serverManager = null;
/*  91 */     fileManager = null;
/*  92 */     potionManager = null;
/*  93 */     moduleManager = null;
/*  94 */     textManager = null;
/*  95 */     breakManager = null;
/*     */   }
/*     */   
/*     */   public static void reload() {
/*  99 */     unload(false);
/* 100 */     load();
/*     */   }
/*     */   
/*     */   public static void onUnload() {
/* 104 */     if (!unloaded) {
/* 105 */       eventManager.onUnload();
/* 106 */       moduleManager.onUnload();
/* 107 */       configManager.saveConfig(configManager.config.replaceFirst("ab-Hack/", ""));
/* 108 */       moduleManager.onUnloadPost();
/* 109 */       unloaded = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setWindowIcon() {
/* 114 */     if (Util.getOSType() != Util.EnumOS.OSX) {
/* 115 */       try(InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/steve16x.png");
/* 116 */           InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/steve32x.png")) {
/* 117 */         ByteBuffer[] icons = { IconUtil.INSTANCE.readImageToBuffer(inputStream16x), IconUtil.INSTANCE.readImageToBuffer(inputStream32x) };
/* 118 */         Display.setIcon(icons);
/* 119 */       } catch (Exception e) {
/* 120 */         LOGGER.error("Couldn't set Windows Icon", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void setWindowsIcon() {
/* 126 */     setWindowIcon();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent event) {
/* 131 */     ConfigurationHandler confHandler = ConfigurationHandler.getInstance();
/* 132 */     confHandler.load(event.getSuggestedConfigurationFile());
/* 133 */     MinecraftForge.EVENT_BUS.register(confHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent event) {
/* 140 */     Display.setTitle("ab-Hack 0.0.1");
/* 141 */     load();
/* 142 */     setWindowsIcon();
/* 143 */     MinecraftForge.EVENT_BUS.register(OpenTradeEventHandler.getInstance());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\OyVey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.client;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.awt.Color;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.ClientEvent;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.gui.OyVeyGui;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class ClickGui
/*     */   extends Module
/*     */ {
/*     */   public static ClickGui INSTANCE;
/*  19 */   public Setting<String> prefix = register(new Setting("Prefix", "."));
/*     */   
/*  21 */   public Setting<Boolean> customFov = register(new Setting("CustomFov", Boolean.valueOf(false)));
/*     */   
/*  23 */   public Setting<Float> fov = register(new Setting("Fov", Float.valueOf(90.0F), Float.valueOf(-130.0F), Float.valueOf(130.0F), v -> ((Boolean)this.customFov.getValue()).booleanValue()));
/*     */   
/*  25 */   public Setting<Integer> red = register(new Setting("Red", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  27 */   public Setting<Integer> green = register(new Setting("Green", Integer.valueOf(24), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  29 */   public Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  31 */   public Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(200), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  33 */   public Setting<Boolean> rainbow = register(new Setting("Rainbow", Boolean.valueOf(false)));
/*     */   
/*  35 */   public Setting<Integer> rainbowHue = register(new Setting("Delay", Integer.valueOf(600), Integer.valueOf(0), Integer.valueOf(600), v -> ((Boolean)this.rainbow.getValue()).booleanValue()));
/*     */   
/*  37 */   public Setting<Float> rainbowBrightness = register(new Setting("Brightness ", Float.valueOf(200.0F), Float.valueOf(1.0F), Float.valueOf(255.0F), v -> ((Boolean)this.rainbow.getValue()).booleanValue()));
/*     */   
/*  39 */   public Setting<Float> rainbowSaturation = register(new Setting("Saturation", Float.valueOf(255.0F), Float.valueOf(1.0F), Float.valueOf(255.0F), v -> ((Boolean)this.rainbow.getValue()).booleanValue()));
/*     */   
/*  41 */   public Setting<RainbowArray> arraymode = register(new Setting("ArrayMode", RainbowArray.Unique, v -> ((Boolean)this.rainbow.getValue()).booleanValue()));
/*     */   
/*  43 */   public Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/*     */   
/*  45 */   public Setting<Boolean> moduleDescription = register(new Setting("Description", Boolean.valueOf(true)));
/*     */   
/*  47 */   public Setting<Boolean> snowing = register(new Setting("Snowing", Boolean.valueOf(true)));
/*     */   
/*  49 */   public Setting<Boolean> particles = register(new Setting("Particles", Boolean.valueOf(true)));
/*     */   
/*  51 */   public Setting<Integer> particleLength = register(new Setting("ParticleLength", Integer.valueOf(80), Integer.valueOf(0), Integer.valueOf(300), v -> ((Boolean)this.particles.getValue()).booleanValue()));
/*     */   
/*  53 */   public Setting<Integer> particlered = register(new Setting("ParticleRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.particles.getValue()).booleanValue()));
/*     */   
/*  55 */   public Setting<Integer> particlegreen = register(new Setting("ParticleGreen", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.particles.getValue()).booleanValue()));
/*     */   
/*  57 */   public Setting<Integer> particleblue = register(new Setting("ParticleBlue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.particles.getValue()).booleanValue()));
/*     */   
/*  59 */   public Setting<Boolean> rbg = register(new Setting("ParticleRainbow", Boolean.valueOf(true), v -> ((Boolean)this.particles.getValue()).booleanValue()));
/*     */   
/*  61 */   public Setting<Boolean> bypass163 = register(new Setting("Bypass163", Boolean.valueOf(true), v -> ((Boolean)this.particles.getValue()).booleanValue()));
/*     */ 
/*     */   
/*     */   public ClickGui() {
/*  65 */     super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
/*  66 */     INSTANCE = this;
/*  67 */     setBind(25);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  72 */     if (((Boolean)this.customFov.getValue()).booleanValue())
/*  73 */       mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, ((Float)this.fov.getValue()).floatValue()); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSettingChange(ClientEvent event) {
/*  78 */     if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
/*  79 */       if (event.getSetting().equals(this.prefix)) {
/*  80 */         OyVey.commandManager.setPrefix((String)this.prefix.getPlannedValue());
/*  81 */         Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + OyVey.commandManager.getPrefix());
/*     */       } 
/*  83 */       OyVey.colorManager.setColor(((Integer)this.red.getPlannedValue()).intValue(), ((Integer)this.green.getPlannedValue()).intValue(), ((Integer)this.blue.getPlannedValue()).intValue(), ((Integer)this.alpha.getPlannedValue()).intValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  88 */     mc.displayGuiScreen((GuiScreen)OyVeyGui.getClickGui());
/*     */   }
/*     */   
/*     */   public void onLoad() {
/*  92 */     OyVey.colorManager.setColor(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue());
/*  93 */     OyVey.commandManager.setPrefix((String)this.prefix.getValue());
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  97 */     if (!(mc.currentScreen instanceof OyVeyGui))
/*  98 */       disable(); 
/*     */   }
/*     */   
/*     */   public Color getGuiColor() {
/* 102 */     return new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue());
/*     */   }
/*     */   
/*     */   public enum RainbowArray {
/* 106 */     Up,
/* 107 */     Unique;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\client\ClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
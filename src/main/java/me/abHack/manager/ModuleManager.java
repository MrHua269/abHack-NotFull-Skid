/*     */ package me.abHack.manager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/*     */ import me.abHack.event.events.Render2DEvent;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.Feature;
/*     */ import me.abHack.features.modules.Module;

/*     */ import me.abHack.features.modules.client.*;
import me.abHack.features.modules.combat.*;
import me.abHack.features.modules.misc.*;
/*     */ import me.abHack.features.modules.movement.*;
/*     */ import me.abHack.features.modules.player.*;
/*     */ import me.abHack.features.modules.render.*;
/*     */ import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

/*     */
/*     */ public class ModuleManager extends Feature {
/*  26 */   public ArrayList<Module> modules = new ArrayList<>();
/*  27 */   public List<Module> sortedModules = new ArrayList<>();
/*  28 */   public List<String> sortedModulesABC = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  33 */     this.modules.add(new ClickGui());
/*  34 */     this.modules.add(new HUD());
/*  35 */     this.modules.add(new Capes());
/*  36 */     this.modules.add(new WaterMark());
/*  37 */     this.modules.add(new Colors());
/*     */ 
/*     */     
/*  40 */     this.modules.add(new Offhand());
/*  41 */     this.modules.add(new Surround());
/*  42 */     this.modules.add(new AutoTrap());
/*  43 */     this.modules.add(new AutoCity());
/*  44 */     this.modules.add(new KeyCity());
/*  45 */     this.modules.add(new AutoTotem());
/*  46 */     this.modules.add(new AutoWeb());
/*  47 */     this.modules.add(new AntiPiston());
/*  48 */     this.modules.add(new AntiCity());
/*  49 */     this.modules.add(new HFAura());
/*  50 */     this.modules.add(new Killaura());
/*  51 */     this.modules.add(new Criticals());
/*  52 */     this.modules.add(new AutoArmor());
/*  53 */     this.modules.add(new FastBow());
/*  54 */     this.modules.add(new Flatten());
/*  55 */     this.modules.add(new BowAim());
/*  56 */     this.modules.add(new AntiCev());
/*  57 */     this.modules.add(new TrapHead());
/*  58 */     this.modules.add(new TrapSelf());
/*  59 */     this.modules.add(new AutoCev());
/*  60 */     this.modules.add(new AutoPush());
/*  61 */     this.modules.add(new PistonCrystal());
/*     */     
/*  63 */     this.modules.add(new Timer());
/*  64 */     this.modules.add(new ShulkerViewer());
/*  65 */     this.modules.add(new Interact());
/*  66 */     this.modules.add(new MiddleFriend());
/*  67 */     this.modules.add(new PopCounter());
/*  68 */     this.modules.add(new Message());
/*  69 */     this.modules.add(new AutoGG());
/*  70 */     this.modules.add(new NoEntityTrace());
/*  71 */     this.modules.add(new ChatSuffix());
/*  72 */     this.modules.add(new AutoRespawn());
/*  73 */     this.modules.add(new BowGod());
/*  74 */     this.modules.add(new ColorSigns());
/*  75 */     this.modules.add(new ChestStealer());
/*  76 */     this.modules.add(new Crash());
/*  77 */     this.modules.add(new NoRotate());
/*  78 */     this.modules.add(new Gamemode());
/*  79 */     this.modules.add(new AntiCrash());
/*  80 */     this.modules.add(new Spammer());
/*  81 */     this.modules.add(new AutoBanxxs());
/*  82 */     this.modules.add(new SkinFlicker());
/*  83 */     this.modules.add(new AutoReconnect());
/*  84 */     this.modules.add(new VillagerNotifier());
/*  85 */     this.modules.add(new AutoLog());
/*     */ 
/*     */     
/*  88 */     this.modules.add(new BlockHighlight());
/*  89 */     this.modules.add(new HoleESP());
/*  90 */     this.modules.add(new Trajectories());
/*  91 */     this.modules.add(new NoRender());
/*  92 */     this.modules.add(new NameTags());
/*  93 */     this.modules.add(new Particles());
/*  94 */     this.modules.add(new ESP());
/*  95 */     this.modules.add(new ItemPhysics());
/*  96 */     this.modules.add(new Fullbright());
/*  97 */     this.modules.add(new CameraClip());
/*  98 */     this.modules.add(new PlayerLogEsp());
/*  99 */     this.modules.add(new PopChams());
/* 100 */     this.modules.add(new Breadcrumbs());
/* 101 */     this.modules.add(new SkyColor());
/* 102 */     this.modules.add(new ViewModel());
/* 103 */     this.modules.add(new ChestESP());
/* 104 */     this.modules.add(new Chams());
/* 105 */     this.modules.add(new PortalESP());
/* 106 */     this.modules.add(new BurrowESP());
/* 107 */     this.modules.add(new CityRender());
/* 108 */     this.modules.add(new Target());
/* 109 */     this.modules.add(new Ranges());
/* 110 */     this.modules.add(new Trails());
/* 111 */     this.modules.add(new VoidESP());
/* 112 */     this.modules.add(new ExplosionChams());
/* 113 */     this.modules.add(new BreakRender());
/* 114 */     this.modules.add(new Tracer());
/* 115 */     this.modules.add(new Xray());
/* 116 */     this.modules.add(new JumpCircles());
/* 117 */     this.modules.add(new FreeLook());
/* 118 */     this.modules.add(new NameLabel());
/* 119 */     this.modules.add(new ItemChams());
/*     */ 
/*     */     
/* 122 */     this.modules.add(new Replenish());
/* 123 */     this.modules.add(new FakePlayer());
/* 124 */     this.modules.add(new TpsSync());
/* 125 */     this.modules.add(new MultiTask());
/* 126 */     this.modules.add(new FastPlace());
/* 127 */     this.modules.add(new InstantMine());
/* 128 */     this.modules.add(new Phase());
/* 129 */     this.modules.add(new Reach());
/* 130 */     this.modules.add(new Portal());
/* 131 */     this.modules.add(new AntiHunger());
/* 132 */     this.modules.add(new XCarry());
/* 133 */     this.modules.add(new Anti32k());
/* 134 */     this.modules.add(new AutoXP());
/* 135 */     this.modules.add(new AutoEat());
/* 136 */     this.modules.add(new Blink());
/* 137 */     this.modules.add(new Burrow());
/* 138 */     this.modules.add(new BurrowX());
/* 139 */     this.modules.add(new PacketEat());
/* 140 */     this.modules.add(new tp());
/* 141 */     this.modules.add(new Freecam());
/* 142 */     this.modules.add(new AntiContainer());
/* 143 */     this.modules.add(new AntiShulkerBox());
/* 144 */     this.modules.add(new AutoDupe());
/* 145 */     //this.modules.add(new Nuker());
/* 146 */     this.modules.add(new FastBreak());
/* 147 */    // this.modules.add(new AutoBuilder());
/* 148 */     this.modules.add(new Lawnmower());
/* 149 */     this.modules.add(new AutoWither());
/* 150 */     this.modules.add(new TabFriends());
/*     */ 
/*     */     
/* 153 */     this.modules.add(new Step());
/* 154 */     this.modules.add(new Flight());
/* 155 */     this.modules.add(new Scaffold());
/* 156 */     this.modules.add(new ReverseStep());
/* 157 */     this.modules.add(new AntiEffects());
/* 158 */     this.modules.add(new AutoWalk());
/* 159 */     this.modules.add(new BoatFly());
/* 160 */     this.modules.add(new ElytraFlight());
/* 161 */     this.modules.add(new EntityControl());
/* 162 */     this.modules.add(new NoFall());
/* 163 */     this.modules.add(new PlayerTweaks());
/* 164 */     this.modules.add(new Sprint());
/* 165 */     this.modules.add(new AirJump());
/* 166 */     this.modules.add(new AntiVoid());
/* 167 */     this.modules.add(new MiddlePearl());
/* 168 */     this.modules.add(new NoWeb());
/* 169 */     this.modules.add(new Strafe());
/* 170 */     this.modules.add(new Speed());
/* 171 */     this.modules.add(new HoleSnap());
/*     */   }
/*     */   
/*     */   public Module getModuleByName(String name) {
/* 175 */     for (Module module : this.modules) {
/* 176 */       if (!module.getName().equalsIgnoreCase(name))
/* 177 */         continue;  return module;
/*     */     } 
/* 179 */     return null;
/*     */   }
/*     */   
/*     */   public <T extends Module> T getModuleByClass(Class<T> clazz) {
/* 183 */     for (Module module : this.modules) {
/* 184 */       if (!clazz.isInstance(module))
/* 185 */         continue;  return (T)module;
/*     */     } 
/* 187 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isModuleEnabled(String name) {
/* 191 */     Module module = getModuleByName(name);
/* 192 */     return (module != null && module.isOn());
/*     */   }
/*     */   
/*     */   public boolean isModuleEnabled(Class<Module> clazz) {
/* 196 */     Module module = getModuleByClass(clazz);
/* 197 */     return (module != null && module.isOn());
/*     */   }
/*     */   
/*     */   public Module getModuleByDisplayName(String displayName) {
/* 201 */     for (Module module : this.modules) {
/* 202 */       if (!module.getDisplayName().equalsIgnoreCase(displayName))
/* 203 */         continue;  return module;
/*     */     } 
/* 205 */     return null;
/*     */   }
/*     */   
/*     */   public ArrayList<Module> getEnabledModules() {
/* 209 */     ArrayList<Module> enabledModules = new ArrayList<>();
/* 210 */     for (Module module : this.modules) {
/* 211 */       if (!module.isEnabled())
/* 212 */         continue;  enabledModules.add(module);
/*     */     } 
/* 214 */     return enabledModules;
/*     */   }
/*     */   
/*     */   public ArrayList<String> getEnabledModulesName() {
/* 218 */     ArrayList<String> enabledModules = new ArrayList<>();
/* 219 */     for (Module module : this.modules) {
/* 220 */       if (!module.isEnabled() || !module.isDrawn())
/* 221 */         continue;  enabledModules.add(module.getFullArrayString());
/*     */     } 
/* 223 */     return enabledModules;
/*     */   }
/*     */   
/*     */   public ArrayList<Module> getModulesByCategory(Module.Category category) {
/* 227 */     ArrayList<Module> modulesCategory = new ArrayList<>();
/* 228 */     this.modules.forEach(module -> {
/*     */           if (module.getCategory() == category) {
/*     */             modulesCategory.add(module);
/*     */           }
/*     */         });
/* 233 */     return modulesCategory;
/*     */   }
/*     */   
/*     */   public List<Module.Category> getCategories() {
/* 237 */     return Arrays.asList(Module.Category.values());
/*     */   }
/*     */   
/*     */   public void onLoad() {
/* 241 */     this.modules.stream().filter(Module::listening).forEach(MinecraftForge.EVENT_BUS::register);
/* 242 */     this.modules.forEach(Module::onLoad);
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 246 */     this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
/*     */   }
/*     */   
/*     */   public void onTick() {
/* 250 */     this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
/*     */   }
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {
/* 254 */     this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 258 */     this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
/*     */   }
/*     */   
/*     */   public void sortModules(boolean reverse) {
/* 262 */     this.sortedModules = (List<Module>)getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> Integer.valueOf(this.renderer.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1)))).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public void sortModulesABC() {
/* 266 */     this.sortedModulesABC = new ArrayList<>(getEnabledModulesName());
/* 267 */     this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
/*     */   }
/*     */   
/*     */   public void onLogout() {
/* 271 */     this.modules.forEach(Module::onLogout);
/*     */   }
/*     */   
/*     */   public void onLogin() {
/* 275 */     this.modules.forEach(Module::onLogin);
/*     */   }
/*     */   
/*     */   public void onUnload() {
/* 279 */     this.modules.forEach(MinecraftForge.EVENT_BUS::unregister);
/* 280 */     this.modules.forEach(Module::onUnload);
/*     */   }
/*     */   
/*     */   public void onUnloadPost() {
/* 284 */     for (Module module : this.modules) {
/* 285 */       module.enabled.setValue(Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onKeyPressed(int eventKey) {
/* 290 */     if (eventKey == 0 || !Keyboard.getEventKeyState() || mc.currentScreen instanceof me.abHack.features.gui.OyVeyGui) {
/*     */       return;
/*     */     }
/* 293 */     this.modules.forEach(module -> {
/*     */           if (module.getBind().getKey() == eventKey)
/*     */             module.toggle(); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\ModuleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
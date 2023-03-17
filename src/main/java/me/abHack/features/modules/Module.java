/*     */ package me.abHack.features.modules;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.ClientEvent;
/*     */ import me.abHack.event.events.Render2DEvent;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.Feature;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.client.HUD;
/*     */ import me.abHack.features.setting.Bind;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ public class Module extends Feature {
/*     */   private final String description;
/*  20 */   public Setting<Boolean> enabled = register(new Setting("Enabled", Boolean.valueOf(false))); private final Category category;
/*  21 */   public Setting<Boolean> drawn = register(new Setting("Drawn", Boolean.valueOf(true)));
/*  22 */   public Setting<Bind> bind = register(new Setting("Keybind", new Bind(-1)));
/*     */   public Setting<String> displayName;
/*     */   public boolean hasListener;
/*     */   public boolean alwaysListening;
/*     */   public boolean hidden;
/*     */   public float offset;
/*     */   public boolean sliding;
/*     */   
/*     */   public Module(String name, String description, Category category, boolean hasListener, boolean hidden, boolean alwaysListening) {
/*  31 */     super(name);
/*  32 */     this.displayName = register(new Setting("DisplayName", name));
/*  33 */     this.description = description;
/*  34 */     this.category = category;
/*  35 */     this.hasListener = hasListener;
/*  36 */     this.hidden = hidden;
/*  37 */     this.alwaysListening = alwaysListening;
/*     */   }
/*     */   
/*     */   public boolean isSliding() {
/*  41 */     return this.sliding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {}
/*     */ 
/*     */   
/*     */   public void onDisable() {}
/*     */ 
/*     */   
/*     */   public void onToggle() {}
/*     */ 
/*     */   
/*     */   public void onLoad() {}
/*     */ 
/*     */   
/*     */   public void onTick() {}
/*     */ 
/*     */   
/*     */   public void onLogin() {}
/*     */ 
/*     */   
/*     */   public void onLogout() {}
/*     */ 
/*     */   
/*     */   public void onUpdate() {}
/*     */ 
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {}
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {}
/*     */ 
/*     */   
/*     */   public void onUnload() {}
/*     */   
/*     */   public String getDisplayInfo() {
/*  78 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isOn() {
/*  82 */     return ((Boolean)this.enabled.getValue()).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isOff() {
/*  86 */     return !((Boolean)this.enabled.getValue()).booleanValue();
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  90 */     if (enabled) {
/*  91 */       enable();
/*     */     } else {
/*  93 */       disable();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void enable() {
/*  98 */     this.enabled.setValue(Boolean.TRUE);
/*  99 */     onToggle();
/* 100 */     onEnable();
/* 101 */     if (((Boolean)HUD.INSTANCE.notifyToggles.getValue()).booleanValue()) {
/* 102 */       TextComponentString text = new TextComponentString(OyVey.commandManager.getClientMessage() + " " + ChatFormatting.GREEN + getDisplayName() + " toggled on.");
/* 103 */       mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)text, 1);
/*     */     } 
/* 105 */     if (isOn() && this.hasListener && !this.alwaysListening) {
/* 106 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void disable() {
/* 111 */     if (this.hasListener && !this.alwaysListening) {
/* 112 */       MinecraftForge.EVENT_BUS.unregister(this);
/*     */     }
/* 114 */     this.enabled.setValue(Boolean.valueOf(false));
/* 115 */     if (((Boolean)HUD.INSTANCE.notifyToggles.getValue()).booleanValue()) {
/* 116 */       TextComponentString text = new TextComponentString(OyVey.commandManager.getClientMessage() + " " + ChatFormatting.RED + getDisplayName() + " toggled off.");
/* 117 */       mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)text, 1);
/*     */     } 
/* 119 */     onToggle();
/* 120 */     onDisable();
/*     */   }
/*     */   
/*     */   public void toggle() {
/* 124 */     ClientEvent event = new ClientEvent(!isEnabled() ? 1 : 0, this);
/* 125 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 126 */     if (!event.isCanceled()) {
/* 127 */       setEnabled(!isEnabled());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 132 */     return (String)this.displayName.getValue();
/*     */   }
/*     */   
/*     */   public void setDisplayName(String name) {
/* 136 */     Module module = OyVey.moduleManager.getModuleByDisplayName(name);
/* 137 */     Module originalModule = OyVey.moduleManager.getModuleByName(name);
/* 138 */     if (module == null && originalModule == null) {
/* 139 */       Command.sendMessage(getDisplayName() + ", name: " + getName() + ", has been renamed to: " + name);
/* 140 */       this.displayName.setValue(name);
/*     */       return;
/*     */     } 
/* 143 */     Command.sendMessage(ChatFormatting.RED + "A module of this name already exists.");
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 147 */     return this.description;
/*     */   }
/*     */   
/*     */   public boolean isDrawn() {
/* 151 */     return ((Boolean)this.drawn.getValue()).booleanValue();
/*     */   }
/*     */   
/*     */   public void setDrawn(boolean drawn) {
/* 155 */     this.drawn.setValue(Boolean.valueOf(drawn));
/*     */   }
/*     */   
/*     */   public Category getCategory() {
/* 159 */     return this.category;
/*     */   }
/*     */   
/*     */   public String getInfo() {
/* 163 */     return null;
/*     */   }
/*     */   
/*     */   public Bind getBind() {
/* 167 */     return (Bind)this.bind.getValue();
/*     */   }
/*     */   
/*     */   public void setBind(int key) {
/* 171 */     this.bind.setValue(new Bind(key));
/*     */   }
/*     */   
/*     */   public boolean listening() {
/* 175 */     return ((this.hasListener && isOn()) || this.alwaysListening);
/*     */   }
/*     */   
/*     */   public String getFullArrayString() {
/* 179 */     return getDisplayName() + ChatFormatting.GRAY + ((getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
/*     */   }
/*     */   
/*     */   public enum Category {
/* 183 */     COMBAT("Combat"),
/* 184 */     MISC("Misc"),
/* 185 */     RENDER("Render"),
/* 186 */     MOVEMENT("Movement"),
/* 187 */     PLAYER("Player"),
/* 188 */     CLIENT("Client");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     Category(String name) {
/* 193 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 197 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\Module.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
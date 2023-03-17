/*    */ package me.abHack.features;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.gui.OyVeyGui;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.manager.TextManager;
/*    */ import me.abHack.util.Util;
/*    */ 
/*    */ public class Feature
/*    */   implements Util
/*    */ {
/* 15 */   public List<Setting> settings = new ArrayList<>();
/* 16 */   public TextManager renderer = OyVey.textManager;
/*    */   
/*    */   private String name;
/*    */   
/*    */   public Feature() {}
/*    */   
/*    */   public Feature(String name) {
/* 23 */     this.name = name;
/*    */   }
/*    */   
/*    */   public static boolean nullCheck() {
/* 27 */     return (mc.player == null);
/*    */   }
/*    */   
/*    */   public static boolean fullNullCheck() {
/* 31 */     return (mc.player == null || mc.world == null);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 35 */     return this.name;
/*    */   }
/*    */   
/*    */   public List<Setting> getSettings() {
/* 39 */     return this.settings;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 43 */     if (this instanceof Module) {
/* 44 */       return ((Module)this).isOn();
/*    */     }
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isDisabled() {
/* 50 */     return !isEnabled();
/*    */   }
/*    */   
/*    */   public Setting register(Setting setting) {
/* 54 */     setting.setFeature(this);
/* 55 */     this.settings.add(setting);
/* 56 */     if (this instanceof Module && mc.currentScreen instanceof OyVeyGui) {
/* 57 */       OyVeyGui.getInstance().updateModule((Module)this);
/*    */     }
/* 59 */     return setting;
/*    */   }
/*    */   
/*    */   public void unregister(Setting settingIn) {
/* 63 */     ArrayList<Setting> removeList = new ArrayList<>();
/* 64 */     for (Setting setting : this.settings) {
/* 65 */       if (!setting.equals(settingIn))
/* 66 */         continue;  removeList.add(setting);
/*    */     } 
/* 68 */     if (!removeList.isEmpty()) {
/* 69 */       this.settings.removeAll(removeList);
/*    */     }
/* 71 */     if (this instanceof Module && mc.currentScreen instanceof OyVeyGui) {
/* 72 */       OyVeyGui.getInstance().updateModule((Module)this);
/*    */     }
/*    */   }
/*    */   
/*    */   public Setting getSettingByName(String name) {
/* 77 */     for (Setting setting : this.settings) {
/* 78 */       if (!setting.getName().equalsIgnoreCase(name))
/* 79 */         continue;  return setting;
/*    */     } 
/* 81 */     return null;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 85 */     for (Setting setting : this.settings) {
/* 86 */       setting.setValue(setting.getDefaultValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public void clearSettings() {
/* 91 */     this.settings = new ArrayList<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\Feature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
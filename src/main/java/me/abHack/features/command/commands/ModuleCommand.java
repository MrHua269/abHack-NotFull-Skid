/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.Feature;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.manager.ConfigManager;
/*    */ 
/*    */ public class ModuleCommand extends Command {
/*    */   public ModuleCommand() {
/* 14 */     super("module", new String[] { "<module>", "<set/reset>", "<setting>", "<value>" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 20 */     if (commands.length == 1) {
/* 21 */       sendMessage("Modules: ");
/* 22 */       for (Module.Category category : OyVey.moduleManager.getCategories()) {
/* 23 */         StringBuilder modules = new StringBuilder(category.getName() + ": ");
/* 24 */         for (Module module1 : OyVey.moduleManager.getModulesByCategory(category)) {
/* 25 */           modules.append(module1.isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED).append(module1.getName()).append(ChatFormatting.WHITE).append(", ");
/*    */         }
/* 27 */         sendMessage(modules.toString());
/*    */       } 
/*    */       return;
/*    */     } 
/* 31 */     Module module = OyVey.moduleManager.getModuleByDisplayName(commands[0]);
/* 32 */     if (module == null) {
/* 33 */       module = OyVey.moduleManager.getModuleByName(commands[0]);
/* 34 */       if (module == null) {
/* 35 */         sendMessage("This module doesnt exist.");
/*    */         return;
/*    */       } 
/* 38 */       sendMessage(" This is the original name of the module. Its current name is: " + module.getDisplayName());
/*    */       return;
/*    */     } 
/* 41 */     if (commands.length == 2) {
/* 42 */       sendMessage(module.getDisplayName() + " : " + module.getDescription());
/* 43 */       for (Setting setting2 : module.getSettings()) {
/* 44 */         sendMessage(setting2.getName() + " : " + setting2.getValue() + ", " + setting2.getDescription());
/*    */       }
/*    */       return;
/*    */     } 
/* 48 */     if (commands.length == 3) {
/* 49 */       if (commands[1].equalsIgnoreCase("set")) {
/* 50 */         sendMessage("Please specify a setting.");
/* 51 */       } else if (commands[1].equalsIgnoreCase("reset")) {
/* 52 */         for (Setting setting3 : module.getSettings()) {
/* 53 */           setting3.setValue(setting3.getDefaultValue());
/*    */         }
/*    */       } else {
/* 56 */         sendMessage("This command doesnt exist.");
/*    */       } 
/*    */       return;
/*    */     } 
/* 60 */     if (commands.length == 4) {
/* 61 */       sendMessage("Please specify a value."); return;
/*    */     } 
/*    */     Setting setting;
/* 64 */     if (commands.length == 5 && (setting = module.getSettingByName(commands[2])) != null) {
/* 65 */       JsonParser jp = new JsonParser();
/* 66 */       if (setting.getType().equalsIgnoreCase("String")) {
/* 67 */         setting.setValue(commands[3]);
/* 68 */         sendMessage(ChatFormatting.DARK_GRAY + module.getName() + " " + setting.getName() + " has been set to " + commands[3] + ".");
/*    */         return;
/*    */       } 
/*    */       try {
/* 72 */         if (setting.getName().equalsIgnoreCase("Enabled")) {
/* 73 */           if (commands[3].equalsIgnoreCase("true")) {
/* 74 */             module.enable();
/*    */           }
/* 76 */           if (commands[3].equalsIgnoreCase("false")) {
/* 77 */             module.disable();
/*    */           }
/*    */         } 
/* 80 */         ConfigManager.setValueFromJson((Feature)module, setting, jp.parse(commands[3]));
/* 81 */       } catch (Exception e) {
/* 82 */         sendMessage("Bad Value! This setting requires a: " + setting.getType() + " value.");
/*    */         return;
/*    */       } 
/* 85 */       sendMessage(ChatFormatting.GRAY + module.getName() + " " + setting.getName() + " has been set to " + commands[3] + ".");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ModuleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
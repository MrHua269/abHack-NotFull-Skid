/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Bind;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class BindCommand
/*    */   extends Command {
/*    */   public BindCommand() {
/* 13 */     super("bind", new String[] { "<module>", "<bind>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 18 */     if (commands.length == 1) {
/* 19 */       sendMessage("Please specify a module.");
/*    */       return;
/*    */     } 
/* 22 */     String rkey = commands[1];
/* 23 */     String moduleName = commands[0];
/* 24 */     Module module = OyVey.moduleManager.getModuleByName(moduleName);
/* 25 */     if (module == null) {
/* 26 */       sendMessage("Unknown module '" + module + "'!");
/*    */       return;
/*    */     } 
/* 29 */     if (rkey == null) {
/* 30 */       sendMessage(module.getName() + " is bound to " + ChatFormatting.GRAY + module.getBind().toString());
/*    */       return;
/*    */     } 
/* 33 */     int key = Keyboard.getKeyIndex(rkey.toUpperCase());
/* 34 */     if (rkey.equalsIgnoreCase("none")) {
/* 35 */       key = -1;
/*    */     }
/* 37 */     if (key == 0) {
/* 38 */       sendMessage("Unknown key '" + rkey + "'!");
/*    */       return;
/*    */     } 
/* 41 */     module.bind.setValue(new Bind(key));
/* 42 */     sendMessage("Bind for " + ChatFormatting.GREEN + module.getName() + ChatFormatting.WHITE + " set to " + ChatFormatting.GRAY + rkey.toUpperCase());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\BindCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
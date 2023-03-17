/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ 
/*    */ public class ToggleCommand extends Command {
/*    */   public ToggleCommand() {
/*  9 */     super("toggle", new String[] { "<toggle>", "<module>" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 13 */     if (commands.length == 2) {
/* 14 */       String name = commands[0].replaceAll("_", " ");
/* 15 */       Module module = OyVey.moduleManager.getModuleByName(name);
/* 16 */       if (module != null) {
/* 17 */         module.toggle();
/*    */       } else {
/* 19 */         Command.sendMessage("Unable to find a module with that name!");
/*    */       } 
/*    */     } else {
/* 22 */       Command.sendMessage("Please provide a valid module name!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ToggleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class HelpCommand
/*    */   extends Command {
/*    */   public HelpCommand() {
/* 10 */     super("help");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     sendMessage("Commands: ");
/* 16 */     for (Command command : OyVey.commandManager.getCommands())
/* 17 */       sendMessage(ChatFormatting.GRAY + OyVey.commandManager.getPrefix() + command.getName()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\HelpCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
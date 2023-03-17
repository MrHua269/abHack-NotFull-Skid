/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class PrefixCommand
/*    */   extends Command {
/*    */   public PrefixCommand() {
/* 10 */     super("prefix", new String[] { "<char>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     if (commands.length == 1) {
/* 16 */       Command.sendMessage(ChatFormatting.GREEN + "Current prefix is " + OyVey.commandManager.getPrefix());
/*    */       return;
/*    */     } 
/* 19 */     OyVey.commandManager.setPrefix(commands[0]);
/* 20 */     Command.sendMessage("Prefix changed to " + ChatFormatting.GRAY + commands[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\PrefixCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
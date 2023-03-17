/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ public class ClientCommand extends Command {
/*    */   public ClientCommand() {
/*  9 */     super("client", new String[] { "<name>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     if (commands.length == 1 || commands[0] == null) {
/* 15 */       Display.setTitle("ab-Hack 0.0.1");
/* 16 */       Command.sendMessage(ChatFormatting.DARK_RED + ".Client <Client Name>");
/*    */       return;
/*    */     } 
/* 19 */     String name = "ab-Hack 0.0.1";
/* 20 */     if (commands[0] != null)
/* 21 */       name = commands[0]; 
/* 22 */     if (commands[0] != null && commands[1] != null)
/* 23 */       name = commands[0] + " " + commands[1]; 
/* 24 */     Display.setTitle(name);
/* 25 */     Command.sendMessage("new Client Name.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ClientCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.Clipboard;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class TokenCommand
/*    */   extends Command
/*    */ {
/*    */   public TokenCommand() {
/* 12 */     super("token", new String[] { "get <token>" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     StringSelection contents = new StringSelection("Name: " + mc.session.getUsername() + " UUID: " + mc.session.getPlayerID() + " Token: " + mc.session.getToken());
/* 17 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 18 */     clipboard.setContents(contents, null);
/* 19 */     Command.sendMessage("Saved Token to Clipboard, use CTRL + V to paste.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\TokenCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
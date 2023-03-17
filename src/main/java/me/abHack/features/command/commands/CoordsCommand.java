/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.Clipboard;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import java.text.DecimalFormat;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class CoordsCommand
/*    */   extends Command {
/*    */   public CoordsCommand() {
/* 12 */     super("coords", new String[0]);
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     DecimalFormat format = new DecimalFormat("#");
/* 17 */     StringSelection contents = new StringSelection(format.format(mc.player.posX) + ", " + format.format(mc.player.posY) + ", " + format.format(mc.player.posZ));
/* 18 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 19 */     clipboard.setContents(contents, null);
/* 20 */     Command.sendMessage("Saved Coordinates To Clipboard.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\CoordsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
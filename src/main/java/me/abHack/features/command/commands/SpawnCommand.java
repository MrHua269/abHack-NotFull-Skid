/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.Clipboard;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ public class SpawnCommand
/*    */   extends Command
/*    */ {
/*    */   public SpawnCommand() {
/* 14 */     super("spawn", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 19 */     StringSelection contents = new StringSelection((Minecraft.getMinecraft()).player.getBedLocation() + " !");
/* 20 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 21 */     clipboard.setContents(contents, null);
/* 22 */     Command.sendMessage("Saved Spawn Coords to Clipboard, use CTRL + V to paste.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\SpawnCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.Clipboard;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public final class IpCommand
/*    */   extends Command {
/*    */   public IpCommand() {
/* 12 */     super("ip", new String[0]);
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     Minecraft mc = Minecraft.getMinecraft();
/* 17 */     if (mc.getCurrentServerData() != null) {
/* 18 */       StringSelection contents = new StringSelection((mc.getCurrentServerData()).serverIP);
/* 19 */       Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 20 */       clipboard.setContents(contents, null);
/* 21 */       Command.sendMessage("Copied IP to clipboard");
/*    */     } else {
/* 23 */       Command.sendMessage("Error, Join a server");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\IpCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
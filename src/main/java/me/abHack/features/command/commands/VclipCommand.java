/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class VclipCommand extends Command {
/*    */   public VclipCommand() {
/*  8 */     super("vclip", new String[] { "<y>" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 12 */     if (commands.length == 1) {
/* 13 */       Command.sendMessage(ChatFormatting.DARK_RED + ".Vclip <Highly>");
/*    */       return;
/*    */     } 
/* 16 */     int y = Integer.parseInt(commands[0]);
/* 17 */     Command.sendMessage("Vclip to " + ChatFormatting.GRAY + y);
/* 18 */     mc.player.setPosition(mc.player.posX, mc.player.posY + y, mc.player.posZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\VclipCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
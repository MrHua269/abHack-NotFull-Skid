/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ 
/*    */ public class SayCommand extends Command {
/*    */   public SayCommand() {
/* 10 */     super("say", new String[] { "<message>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     if (commands.length == 1 || commands.length > 2) {
/* 16 */       Command.sendMessage(ChatFormatting.DARK_RED + ".say <message>");
/*    */       return;
/*    */     } 
/* 19 */     StringBuilder message = new StringBuilder();
/* 20 */     for (String arg : commands) {
/* 21 */       if (arg != null)
/* 22 */         message.append(" ").append(arg); 
/*    */     } 
/* 24 */     mc.player.connection.sendPacket((Packet)new CPacketChatMessage(message.toString()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\SayCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
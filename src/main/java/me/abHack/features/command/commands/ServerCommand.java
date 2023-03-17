/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ 
/*    */ public class ServerCommand
/*    */   extends Command {
/*    */   public ServerCommand() {
/* 12 */     super("server", new String[] { "<ip>" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     if (commands.length == 1) {
/* 17 */       Command.sendMessage(ChatFormatting.DARK_RED + ".server <ip>");
/*    */       return;
/*    */     } 
/* 20 */     mc.currentServerData = new ServerData("server", commands[0], false);
/* 21 */     mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(null));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ServerCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
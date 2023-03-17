/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ 
/*    */ public class HatCommand
/*    */   extends Command
/*    */ {
/*    */   public HatCommand() {
/* 12 */     super("Hat", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 17 */     if (commands.length == 1) {
/* 18 */       Command.sendMessage(ChatFormatting.DARK_RED + ".Hat <item> <count> <data> <nbt>");
/*    */       return;
/*    */     } 
/* 21 */     if (commands.length == 2) {
/* 22 */       mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/replaceitem entity @p slot.armor.head " + commands[0]));
/* 23 */       Command.sendMessage("New Hat.");
/*    */     } 
/* 25 */     if (commands.length == 3) {
/* 26 */       mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/replaceitem entity @p slot.armor.head " + commands[0] + " " + commands[1]));
/* 27 */       Command.sendMessage("New Hat.");
/*    */     } 
/*    */     
/* 30 */     if (commands.length == 4) {
/* 31 */       mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/replaceitem entity @p slot.armor.head " + commands[0] + " " + commands[1] + " " + commands[2]));
/* 32 */       Command.sendMessage("New Hat.");
/*    */     } 
/*    */     
/* 35 */     if (commands.length == 5) {
/* 36 */       mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/replaceitem entity @p slot.armor.head " + commands[0] + " " + commands[1] + " " + commands[2] + " " + commands[3]));
/* 37 */       Command.sendMessage("New Hat.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\HatCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
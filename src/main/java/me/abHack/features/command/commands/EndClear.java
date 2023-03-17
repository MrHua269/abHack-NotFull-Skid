/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ 
/*    */ public class EndClear extends Command {
/*    */   public EndClear() {
/* 10 */     super("endclear", new String[] { "<player>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     if (commands.length == 1) {
/* 16 */       Command.sendMessage(ChatFormatting.DARK_RED + ".EndClear <player>");
/*    */       return;
/*    */     } 
/* 19 */     for (int i = 0; i < 27; i++)
/* 20 */       mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/replaceitem entity " + commands[0] + " slot.enderchest." + i + " air")); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\EndClear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
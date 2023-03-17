/*    */ package me.abHack.features.command.commands;
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class TpCommand extends Command {
/*    */   public TpCommand() {
/* 10 */     super("tp", new String[] { "<x> <y> <z>" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     if (commands.length == 1 || commands.length > 4) {
/* 15 */       Command.sendMessage(ChatFormatting.DARK_RED + ".tp <x> <y> <z> / .tp <player>");
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 20 */     if (commands.length == 2) {
/* 21 */       for (EntityPlayer player : mc.world.playerEntities) {
/* 22 */         if (player.getName().equals(commands[0])) {
/* 23 */           int sum = (int)Math.max(Math.abs(player.posX - mc.player.posX), Math.abs(player.posY - mc.player.posY));
/* 24 */           sum = (int)Math.max(Math.abs(player.posZ - mc.player.posZ), sum); int i;
/* 25 */           for (i = 0; i < sum; i += 10)
/* 26 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.001D, mc.player.posZ, true)); 
/* 27 */           mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY, player.posZ, false));
/*    */ 
/*    */           
/* 30 */           Command.sendMessage("ok tp");
/*    */           return;
/*    */         } 
/* 33 */         Command.sendMessage("no targer");
/*    */       } 
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 39 */     if (commands.length == 4) {
/*    */       
/* 41 */       int x = Integer.parseInt(commands[0]);
/* 42 */       int y = Integer.parseInt(commands[1]);
/* 43 */       int z = Integer.parseInt(commands[2]);
/* 44 */       int sum = Math.max(Math.abs(x), Math.abs(y));
/* 45 */       sum = Math.max(Math.abs(z), sum); int i;
/* 46 */       for (i = 0; i < sum; i += 10)
/* 47 */         mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.001D, mc.player.posZ, true)); 
/* 48 */       mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z, false));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\TpCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
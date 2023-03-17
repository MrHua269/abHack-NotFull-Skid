/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.mixin.mixins.MixinIMinecraft;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ public class RenameCommand extends Command {
/*    */   public RenameCommand() {
/* 12 */     super("rename", new String[] { "name" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     if (commands.length == 1) {
/* 17 */       Command.sendMessage(ChatFormatting.DARK_RED + ".Rename <name>");
/*    */       return;
/*    */     } 
/* 20 */     String name = commands[0];
/* 21 */     if (name.contains(".") || name.contains("*") || name.contains("?") || name.contains("+") || name.contains("/")) {
/*    */       
/* 23 */       Command.sendMessage(ChatFormatting.RED + "error name !!!");
/*    */       return;
/*    */     } 
/* 26 */     Session user = new Session(name, "uuid", "token", "legacy");
/* 27 */     ((MixinIMinecraft)mc).setSession(user);
/* 28 */     Command.sendMessage(ChatFormatting.YELLOW + "New name: " + ChatFormatting.GREEN + name);
/* 29 */     mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(null));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\RenameCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
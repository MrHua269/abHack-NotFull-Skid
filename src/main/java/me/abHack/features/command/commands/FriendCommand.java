/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.manager.FriendManager;
/*    */ 
/*    */ public class FriendCommand
/*    */   extends Command {
/*    */   public FriendCommand() {
/* 11 */     super("friend", new String[] { "<add/del/reset/list>", "<name>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     if (commands.length == 1) {
/* 17 */       sendMessage(".friend <add/del/reset/list>");
/*    */       return;
/*    */     } 
/* 20 */     if (commands.length == 2) {
/* 21 */       StringBuilder f; switch (commands[0]) {
/*    */         case "reset":
/* 23 */           OyVey.friendManager.onLoad();
/* 24 */           sendMessage("Friends got reset.");
/*    */           return;
/*    */         
/*    */         case "add":
/* 28 */           sendMessage(".friend add <player>");
/*    */           return;
/*    */         
/*    */         case "del":
/* 32 */           sendMessage(".friend del <player>");
/*    */           return;
/*    */         
/*    */         case "list":
/* 36 */           if (OyVey.friendManager.getFriends().isEmpty()) {
/* 37 */             sendMessage("No Friends."); break;
/*    */           } 
/* 39 */           f = new StringBuilder("Friends: ");
/* 40 */           for (FriendManager.Friend friend : OyVey.friendManager.getFriends()) {
/*    */             try {
/* 42 */               f.append(friend.getUsername()).append(", ");
/* 43 */             } catch (Exception exception) {}
/*    */           } 
/*    */           
/* 46 */           sendMessage(f.toString());
/*    */           break;
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     if (commands.length >= 3)
/* 53 */       switch (commands[0]) {
/*    */         case "add":
/* 55 */           OyVey.friendManager.addFriend(commands[1]);
/* 56 */           sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
/*    */           return;
/*    */         
/*    */         case "del":
/* 60 */           OyVey.friendManager.removeFriend(commands[1]);
/* 61 */           sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
/*    */           break;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\FriendCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
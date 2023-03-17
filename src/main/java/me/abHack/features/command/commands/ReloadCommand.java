/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class ReloadCommand
/*    */   extends Command {
/*    */   public ReloadCommand() {
/*  9 */     super("reload", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     OyVey.reload();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ReloadCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
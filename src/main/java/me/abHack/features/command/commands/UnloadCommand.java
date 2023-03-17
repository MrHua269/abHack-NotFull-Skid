/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class UnloadCommand
/*    */   extends Command {
/*    */   public UnloadCommand() {
/*  9 */     super("unload", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     OyVey.unload(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\UnloadCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
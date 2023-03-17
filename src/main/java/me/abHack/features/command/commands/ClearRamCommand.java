/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class ClearRamCommand extends Command {
/*    */   public ClearRamCommand() {
/*  7 */     super("clearram");
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 11 */     System.gc();
/* 12 */     Command.sendMessage("Finished clearing the ram.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ClearRamCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
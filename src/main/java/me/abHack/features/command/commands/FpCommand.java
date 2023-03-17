/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.player.FakePlayer;
/*    */ 
/*    */ public class FpCommand
/*    */   extends Command
/*    */ {
/*    */   public FpCommand() {
/* 10 */     super("fp", new String[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     if (FakePlayer.getInstance().isOff()) {
/* 17 */       FakePlayer.getInstance().enable();
/*    */     } else {
/* 19 */       FakePlayer.getInstance().disable();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\FpCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
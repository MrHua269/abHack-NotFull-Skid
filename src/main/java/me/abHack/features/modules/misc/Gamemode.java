/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.world.GameType;
/*    */ 
/*    */ public class Gamemode
/*    */   extends Module {
/*    */   public Gamemode() {
/*  9 */     super("Gamemode", "fake gamemode", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 14 */     if (fullNullCheck())
/*    */       return; 
/* 16 */     mc.playerController.setGameType(GameType.CREATIVE);
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 20 */     if (fullNullCheck())
/*    */       return; 
/* 22 */     mc.playerController.setGameType(GameType.SURVIVAL);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\Gamemode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
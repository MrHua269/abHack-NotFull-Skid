/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.player.TabFriends;
/*    */ import net.minecraft.client.gui.GuiPlayerTabOverlay;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*    */ import net.minecraft.scoreboard.Team;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({GuiPlayerTabOverlay.class})
/*    */ public class MixinGuiPlayerTabOverlay {
/*    */   @Inject(method = {"getPlayerName"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getPlayerNameHead(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable<String> cir) {
/* 18 */     cir.setReturnValue(getPlayerNameGS(networkPlayerInfoIn));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private String getPlayerNameGS(NetworkPlayerInfo networkPlayerInfoIn) {
/* 24 */     String displayName = (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
/*    */ 
/*    */     
/* 27 */     if (OyVey.moduleManager.isModuleEnabled("TabFriends")) {
/* 28 */       String color = TabFriends.color;
/* 29 */       if (OyVey.friendManager.isFriend(displayName)) {
/* 30 */         return (((Boolean)TabFriends.INSTANCE.prefix.getValue()).booleanValue() ? ("§7[" + color + "F§7] ") : "") + color + displayName;
/*    */       }
/* 32 */       return displayName;
/*    */     } 
/*    */     
/* 35 */     return displayName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinGuiPlayerTabOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
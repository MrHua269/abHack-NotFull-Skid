/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraftforge.client.event.GuiOpenEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoRespawn
/*    */   extends Module
/*    */ {
/* 13 */   public Setting<Boolean> antiDeathScreen = register(new Setting("AntiDeathScreen", Boolean.valueOf(true)));
/* 14 */   public Setting<Boolean> deathCoords = register(new Setting("DeathCoords", Boolean.valueOf(false)));
/* 15 */   public Setting<Boolean> respawn = register(new Setting("Respawn", Boolean.valueOf(true)));
/*    */   
/*    */   public AutoRespawn() {
/* 18 */     super("AutoRespawn", "Respawns you when you die.", Module.Category.MISC, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onDisplayDeathScreen(GuiOpenEvent event) {
/* 23 */     if (OyVey.moduleManager.isModuleEnabled("Ghost"))
/*    */       return; 
/* 25 */     if (event.getGui() instanceof net.minecraft.client.gui.GuiGameOver) {
/* 26 */       if (((Boolean)this.deathCoords.getValue()).booleanValue() && event.getGui() instanceof net.minecraft.client.gui.GuiGameOver) {
/* 27 */         Command.sendMessage(String.format("You died at X: %d Y: %d Z: %d", new Object[] { Integer.valueOf((int)mc.player.posX), Integer.valueOf((int)mc.player.posY), Integer.valueOf((int)mc.player.posZ) }));
/*    */       }
/* 29 */       if ((((Boolean)this.respawn.getValue()).booleanValue() && mc.player.getHealth() <= 0.0F) || (((Boolean)this.antiDeathScreen.getValue()).booleanValue() && mc.player.getHealth() > 0.0F)) {
/* 30 */         event.setCanceled(true);
/* 31 */         mc.player.respawnPlayer();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\AutoRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
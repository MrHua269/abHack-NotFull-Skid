/*    */ package me.abHack.features.gui.alts.ias.events;
/*    */ 
/*    */ import me.abHack.features.gui.alts.ias.IAS;
/*    */ import me.abHack.features.gui.alts.ias.gui.GuiAccountSelector;
/*    */ import me.abHack.features.gui.alts.ias.gui.GuiButtonWithImage;
/*    */ import me.abHack.features.gui.alts.tools.Config;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraftforge.client.event.GuiScreenEvent;
/*    */ import net.minecraftforge.fml.client.event.ConfigChangedEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ 
/*    */ public class ClientEvents
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void guiEvent(GuiScreenEvent.InitGuiEvent.Post event) {
/* 20 */     GuiScreen gui = event.getGui();
/* 21 */     if (gui instanceof net.minecraft.client.gui.GuiMainMenu) {
/* 22 */       event.getButtonList().add(new GuiButtonWithImage(20, gui.width / 2 + 104, gui.height / 4 + 48 + 72 + 12, 20, 20, ""));
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onClick(GuiScreenEvent.ActionPerformedEvent event) {
/* 28 */     if (event.getGui() instanceof net.minecraft.client.gui.GuiMainMenu && (event.getButton()).id == 20) {
/* 29 */       if (Config.getInstance() == null) {
/* 30 */         Config.load();
/*    */       }
/* 32 */       Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiAccountSelector());
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTick(TickEvent.RenderTickEvent t) {
/* 38 */     GuiScreen screen = (Minecraft.getMinecraft()).currentScreen;
/* 39 */     if (screen instanceof net.minecraft.client.gui.GuiMainMenu) {
/* 40 */       screen.drawCenteredString((Minecraft.getMinecraft()).fontRenderer, I18n.format("ias.loggedinas", new Object[0]) + Minecraft.getMinecraft().getSession().getUsername() + ".", screen.width / 2, screen.height / 4 + 48 + 72 + 12 + 22, -3372920);
/* 41 */     } else if (screen instanceof net.minecraft.client.gui.GuiMultiplayer && Minecraft.getMinecraft().getSession().getToken().equals("0")) {
/* 42 */       screen.drawCenteredString((Minecraft.getMinecraft()).fontRenderer, I18n.format("ias.offlinemode", new Object[0]), screen.width / 2, 10, 16737380);
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void configChanged(ConfigChangedEvent event) {
/* 48 */     if (event.getModID().equals("ias"))
/* 49 */       IAS.syncConfig(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\events\ClientEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
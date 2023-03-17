/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.MathUtil;
/*    */ import me.abHack.util.Timer;
/*    */ import me.abHack.util.Util;
/*    */ import net.minecraft.client.gui.GuiDisconnected;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.multiplayer.GuiConnecting;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraftforge.client.event.GuiOpenEvent;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class AutoReconnect
/*    */   extends Module
/*    */ {
/*    */   private static ServerData serverData;
/* 21 */   private static AutoReconnect INSTANCE = new AutoReconnect();
/*    */ 
/*    */   
/* 24 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(5)));
/*    */   
/*    */   public AutoReconnect() {
/* 27 */     super("AutoReconnect", "Reconnects you if you disconnect.", Module.Category.MISC, true, false, false);
/* 28 */     setInstance();
/*    */   }
/*    */   
/*    */   public static AutoReconnect getInstance() {
/* 32 */     if (INSTANCE == null) {
/* 33 */       INSTANCE = new AutoReconnect();
/*    */     }
/* 35 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 39 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void sendPacket(GuiOpenEvent event) {
/* 44 */     if (event.getGui() instanceof GuiDisconnected) {
/* 45 */       updateLastConnectedServer();
/* 46 */       GuiDisconnected disconnected = (GuiDisconnected)event.getGui();
/* 47 */       event.setGui((GuiScreen)new GuiDisconnectedHook(disconnected));
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onWorldUnload(WorldEvent.Unload event) {
/* 53 */     updateLastConnectedServer();
/*    */   }
/*    */   
/*    */   public void updateLastConnectedServer() {
/* 57 */     ServerData data = Util.mc.getCurrentServerData();
/* 58 */     if (data != null)
/* 59 */       serverData = data; 
/*    */   }
/*    */   
/*    */   private class GuiDisconnectedHook
/*    */     extends GuiDisconnected
/*    */   {
/*    */     private final Timer timer;
/*    */     
/*    */     public GuiDisconnectedHook(GuiDisconnected disconnected) {
/* 68 */       super(disconnected.parentScreen, disconnected.reason, disconnected.message);
/* 69 */       this.timer = new Timer();
/* 70 */       this.timer.reset();
/*    */     }
/*    */     
/*    */     public void updateScreen() {
/* 74 */       if (this.timer.passedS(((Integer)AutoReconnect.this.delay.getValue()).intValue())) {
/* 75 */         this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this.parentScreen, this.mc, (AutoReconnect.serverData == null) ? this.mc.currentServerData : AutoReconnect.serverData));
/*    */       }
/*    */     }
/*    */     
/*    */     public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 80 */       super.drawScreen(mouseX, mouseY, partialTicks);
/* 81 */       String s = "Reconnecting in " + MathUtil.round(((((Integer)AutoReconnect.this.delay.getValue()).intValue() * 1000) - this.timer.getPassedTimeMs()) / 1000.0D, 1);
/* 82 */       AutoReconnect.this.renderer.drawString(s, (this.width / 2 - AutoReconnect.this.renderer.getStringWidth(s) / 2), (this.height - 16), 16777215, true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\AutoReconnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
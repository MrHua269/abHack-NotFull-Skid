/*    */ package me.abHack.features.gui.easiervillagertrading;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiMerchant;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.client.event.GuiOpenEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class OpenTradeEventHandler {
/*    */   private static OpenTradeEventHandler instance;
/*    */   
/*    */   public static OpenTradeEventHandler getInstance() {
/* 14 */     if (instance == null) {
/* 15 */       instance = new OpenTradeEventHandler();
/* 16 */       instance.mc = Minecraft.getMinecraft();
/*    */     } 
/* 18 */     return instance;
/*    */   }
/*    */   private Minecraft mc;
/*    */   @SubscribeEvent
/*    */   public void guiOpenEvent(GuiOpenEvent event) {
/* 23 */     if (event.getGui() instanceof GuiMerchant)
/* 24 */       event.setGui((GuiScreen)new BetterGuiMerchant(this.mc.player.inventory, (GuiMerchant)event.getGui(), (World)this.mc.world)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\easiervillagertrading\OpenTradeEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
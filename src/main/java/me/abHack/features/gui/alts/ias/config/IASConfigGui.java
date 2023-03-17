/*    */ package me.abHack.features.gui.alts.ias.config;
/*    */ 
/*    */ import me.abHack.features.gui.alts.ias.IAS;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraftforge.common.config.ConfigElement;
/*    */ import net.minecraftforge.fml.client.config.GuiConfig;
/*    */ 
/*    */ public class IASConfigGui
/*    */   extends GuiConfig {
/*    */   public IASConfigGui(GuiScreen parentScreen) {
/* 11 */     super(parentScreen, (new ConfigElement(IAS.config.getCategory("general"))).getChildElements(), "ias", false, false, GuiConfig.getAbridgedConfigPath(IAS.config.toString()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\config\IASConfigGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.gui.alts.ias.config;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraftforge.fml.client.IModGuiFactory;
/*    */ 
/*    */ 
/*    */ public class IASGuiFactory
/*    */   implements IModGuiFactory
/*    */ {
/*    */   public void initialize(Minecraft minecraftInstance) {}
/*    */   
/*    */   public boolean hasConfigGui() {
/* 15 */     return true;
/*    */   }
/*    */   
/*    */   public GuiScreen createConfigGui(GuiScreen parentScreen) {
/* 19 */     return (GuiScreen)new IASConfigGui(parentScreen);
/*    */   }
/*    */   
/*    */   public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
/* 23 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\config\IASGuiFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
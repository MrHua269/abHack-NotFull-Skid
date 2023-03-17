/*    */ package me.abHack.mixin;
/*    */ 
/*    */ import java.util.Map;
/*    */ import me.abHack.OyVey;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ 
/*    */ public class OyVeyLoader
/*    */   implements IFMLLoadingPlugin
/*    */ {
/*    */   public OyVeyLoader() {
/* 15 */     OyVey.LOGGER.info("\n\nLoading mixins by abHack");
/* 16 */     MixinBootstrap.init();
/* 17 */     Mixins.addConfiguration("mixins.abHack.json");
/* 18 */     MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
/* 19 */     OyVey.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
/*    */   }
/*    */   
/*    */   public String[] getASMTransformerClass() {
/* 23 */     return new String[0];
/*    */   }
/*    */   
/*    */   public String getModContainerClass() {
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public String getSetupClass() {
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\OyVeyLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
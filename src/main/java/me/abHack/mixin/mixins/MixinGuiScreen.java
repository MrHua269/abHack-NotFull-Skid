/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.features.modules.misc.ShulkerViewer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiScreen.class})
/*    */ public class MixinGuiScreen
/*    */   extends Gui
/*    */ {
/*    */   @Inject(method = {"renderToolTip"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderToolTipHook(ItemStack stack, int x, int y, CallbackInfo info) {
/* 18 */     if (ShulkerViewer.getInstance().isOn() && stack.getItem() instanceof net.minecraft.item.ItemShulkerBox) {
/* 19 */       ShulkerViewer.getInstance().renderShulkerToolTip(stack, x, y, null);
/* 20 */       info.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinGuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
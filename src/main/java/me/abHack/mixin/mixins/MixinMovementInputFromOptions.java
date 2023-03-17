/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.features.modules.movement.PlayerTweaks;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.util.MovementInput;
/*    */ import net.minecraft.util.MovementInputFromOptions;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ 
/*    */ @Mixin(value = {MovementInputFromOptions.class}, priority = 10001)
/*    */ public class MixinMovementInputFromOptions
/*    */   extends MovementInput {
/*    */   @Redirect(method = {"updatePlayerMoveState"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
/*    */   public boolean isKeyPressed(KeyBinding keyBinding) {
/* 18 */     int keyCode = keyBinding.getKeyCode();
/* 19 */     if (keyCode <= 0)
/* 20 */       return keyBinding.isKeyDown(); 
/* 21 */     if (keyCode >= 256)
/* 22 */       return keyBinding.isKeyDown(); 
/* 23 */     if (!PlayerTweaks.getInstance().isOn())
/* 24 */       return keyBinding.isKeyDown(); 
/* 25 */     if (!((Boolean)(PlayerTweaks.getInstance()).guiMove.getValue()).booleanValue())
/* 26 */       return keyBinding.isKeyDown(); 
/* 27 */     if ((Minecraft.getMinecraft()).currentScreen == null)
/* 28 */       return keyBinding.isKeyDown(); 
/* 29 */     if ((Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiChat)
/* 30 */       return keyBinding.isKeyDown(); 
/* 31 */     return Keyboard.isKeyDown(keyCode);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinMovementInputFromOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
package me.abHack.mixin.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({EntityRenderer.class})
public interface IEntityRenderer {
  @Invoker("setupCameraTransform")
  void setupCamera(float paramFloat, int paramInt);
  
  @Invoker("renderHand")
  void invokeRenderHand(float paramFloat, int paramInt);
}


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\IEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
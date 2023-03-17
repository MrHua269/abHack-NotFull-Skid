package me.abHack.mixin.mixins;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Render.class})
abstract class MixinRenderer<T extends Entity> {
  @Shadow
  protected boolean renderOutlines;
  
  @Shadow
  @Final
  protected RenderManager renderManager;
  
  @Shadow
  protected abstract boolean bindEntityTexture(T paramT);
  
  @Shadow
  protected abstract int getTeamColor(T paramT);
}


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
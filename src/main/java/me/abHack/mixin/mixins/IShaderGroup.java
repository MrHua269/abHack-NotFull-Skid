package me.abHack.mixin.mixins;

import java.util.List;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ShaderGroup.class})
public interface IShaderGroup {
  @Accessor("listShaders")
  List<Shader> getListShaders();
  
  @Accessor("mainFramebuffer")
  Framebuffer getMainFramebuffer();
}


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\IShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
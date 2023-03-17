package me.abHack.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Minecraft.class})
public interface MixinIMinecraft {
  @Accessor("session")
  void setSession(Session paramSession);
}


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinIMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
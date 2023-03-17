package me.abHack.mixin.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({EntityPlayerSP.class})
public interface AccessorEntityPlayerSP {
  @Accessor("handActive")
  void gsSetHandActive(boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\AccessorEntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
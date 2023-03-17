package me.abHack.mixin.mixins;

import net.minecraft.network.play.client.CPacketUpdateSign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketUpdateSign.class})
public interface ICPacketUpdateSign {
  @Accessor("lines")
  String[] getLines();
  
  @Accessor("lines")
  void setLines(String[] paramArrayOfString);
}


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\ICPacketUpdateSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
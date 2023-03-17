/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.event.events.DecodeEvent;
/*    */ import net.minecraft.network.NettyCompressionDecoder;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.Constant;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*    */ 
/*    */ @Mixin({NettyCompressionDecoder.class})
/*    */ public class MixinNettyCompressionDecoder {
/*    */   @ModifyConstant(method = {"decode"}, constant = {@Constant(intValue = 2097152)})
/*    */   private int onDecode(int n) {
/* 15 */     DecodeEvent decodeEvent = new DecodeEvent();
/* 16 */     MinecraftForge.EVENT_BUS.post((Event)decodeEvent);
/*    */ 
/*    */     
/* 19 */     if (decodeEvent.isCanceled()) {
/* 20 */       return Integer.MAX_VALUE;
/*    */     }
/*    */     
/* 23 */     return n;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinNettyCompressionDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
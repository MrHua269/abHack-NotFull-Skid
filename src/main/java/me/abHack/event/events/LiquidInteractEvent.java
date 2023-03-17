/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class LiquidInteractEvent
/*    */   extends Event {
/*    */   private final IBlockState blockState;
/*    */   private final boolean liquidLevel;
/*    */   
/*    */   public LiquidInteractEvent(IBlockState blockState, boolean liquidLevel) {
/* 14 */     this.blockState = blockState;
/* 15 */     this.liquidLevel = liquidLevel;
/*    */   }
/*    */   
/*    */   public IBlockState getBlockState() {
/* 19 */     return this.blockState;
/*    */   }
/*    */   
/*    */   public boolean getLiquidLevel() {
/* 23 */     return this.liquidLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\LiquidInteractEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
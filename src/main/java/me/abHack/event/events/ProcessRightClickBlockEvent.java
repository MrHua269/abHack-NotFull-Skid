/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import me.abHack.event.EventStage;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ @Cancelable
/*    */ public class ProcessRightClickBlockEvent
/*    */   extends EventStage {
/*    */   public BlockPos pos;
/*    */   public EnumHand hand;
/*    */   public ItemStack stack;
/*    */   
/*    */   public ProcessRightClickBlockEvent(BlockPos pos, EnumHand hand, ItemStack stack) {
/* 17 */     this.pos = pos;
/* 18 */     this.hand = hand;
/* 19 */     this.stack = stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\ProcessRightClickBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
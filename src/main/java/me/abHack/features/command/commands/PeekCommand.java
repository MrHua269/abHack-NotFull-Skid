/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import java.util.Map;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.misc.ShulkerViewer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class PeekCommand
/*    */   extends Command
/*    */ {
/*    */   public PeekCommand() {
/* 14 */     super("peek", new String[] { "<player>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 19 */     if (commands.length == 1) {
/* 20 */       ItemStack stack = mc.player.getHeldItemMainhand();
/* 21 */       if (stack != null && stack.getItem() instanceof net.minecraft.item.ItemShulkerBox) {
/* 22 */         ShulkerViewer.displayInv(stack, null);
/*    */       } else {
/* 24 */         Command.sendMessage("§cYou need to hold a Shulker in your mainhand.");
/*    */         return;
/*    */       } 
/*    */     } 
/* 28 */     if (commands.length > 1)
/* 29 */       if (ShulkerViewer.getInstance().isOn()) {
/* 30 */         for (Map.Entry<EntityPlayer, ItemStack> entry : (Iterable<Map.Entry<EntityPlayer, ItemStack>>)(ShulkerViewer.getInstance()).spiedPlayers.entrySet()) {
/* 31 */           if (!((EntityPlayer)entry.getKey()).getName().equalsIgnoreCase(commands[0]))
/* 32 */             continue;  ItemStack stack = entry.getValue();
/* 33 */           ShulkerViewer.displayInv(stack, ((EntityPlayer)entry.getKey()).getName());
/*    */         } 
/*    */       } else {
/*    */         
/* 37 */         Command.sendMessage("§cYou need to turn on ShulkerViewer Module");
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\PeekCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
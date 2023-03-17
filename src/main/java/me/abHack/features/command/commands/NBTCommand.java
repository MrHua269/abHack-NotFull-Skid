/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.Clipboard;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class NBTCommand
/*    */   extends Command
/*    */ {
/* 15 */   private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 16 */   Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   StringSelection nbt;
/*    */   
/*    */   public NBTCommand() {
/* 21 */     super("nbt", new String[] { "<get/wipe/copy>" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 26 */     if (commands.length == 1 || commands.length > 2) {
/* 27 */       Command.sendMessage(ChatFormatting.DARK_RED + ".nbt <get/copy/wipe>");
/*    */       return;
/*    */     } 
/* 30 */     ItemStack item = this.mc.player.inventory.getCurrentItem();
/* 31 */     if (commands[0].equalsIgnoreCase("get")) {
/* 32 */       if (item.getTagCompound() != null) {
/* 33 */         Command.sendMessage("§6§lNBT:\n" + item.getTagCompound() + "");
/*    */       } else {
/* 35 */         Command.sendMessage(ChatFormatting.RED + item.getDisplayName());
/*    */       } 
/* 37 */     } else if (commands[0].equalsIgnoreCase("copy")) {
/* 38 */       if (item.getTagCompound() != null) {
/* 39 */         this.nbt = new StringSelection(item.getTagCompound() + "");
/* 40 */         this.clipboard.setContents(this.nbt, this.nbt);
/* 41 */         Command.sendMessage("§6Copied\n§f" + item.getTagCompound() + "\n§6to clipboard.");
/*    */       } else {
/* 43 */         Command.sendMessage(ChatFormatting.YELLOW + "No NBT on " + item.getDisplayName());
/*    */       } 
/* 45 */     } else if (commands[0].equalsIgnoreCase("wipe")) {
/* 46 */       Command.sendMessage("§6Wiped\n§f" + item.getTagCompound() + "\n§6from " + item.getDisplayName() + ".");
/* 47 */       item.setTagCompound(new NBTTagCompound());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\NBTCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
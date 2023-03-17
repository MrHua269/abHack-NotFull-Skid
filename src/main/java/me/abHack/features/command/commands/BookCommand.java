/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import io.netty.buffer.Unpooled;
/*    */ import java.util.Random;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.IntStream;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.nbt.NBTTagString;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*    */ 
/*    */ public class BookCommand
/*    */   extends Command {
/*    */   public BookCommand() {
/* 21 */     super("book", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 26 */     ItemStack heldItem = mc.player.getHeldItemMainhand();
/* 27 */     if (heldItem.getItem() == Items.WRITABLE_BOOK) {
/* 28 */       Random rand = new Random();
/* 29 */       IntStream characterGenerator = rand.ints(128, 1112063).map(i -> (i < 55296) ? i : (i + 2048));
/* 30 */       String joinedPages = characterGenerator.limit(10500L).<CharSequence>mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
/* 31 */       NBTTagList pages = new NBTTagList();
/* 32 */       for (int page = 0; page < 50; page++) {
/* 33 */         pages.appendTag((NBTBase)new NBTTagString(joinedPages.substring(page * 210, (page + 1) * 210)));
/*    */       }
/* 35 */       if (heldItem.hasTagCompound()) {
/* 36 */         assert heldItem.getTagCompound() != null;
/* 37 */         heldItem.getTagCompound().setTag("pages", (NBTBase)pages);
/*    */       } else {
/* 39 */         heldItem.setTagInfo("pages", (NBTBase)pages);
/*    */       } 
/* 41 */       StringBuilder stackName = new StringBuilder();
/* 42 */       for (int i2 = 0; i2 < 16; i2++) {
/* 43 */         stackName.append("\024\f");
/*    */       }
/* 45 */       heldItem.setTagInfo("author", (NBTBase)new NBTTagString(mc.player.getName()));
/* 46 */       heldItem.setTagInfo("title", (NBTBase)new NBTTagString(stackName.toString()));
/* 47 */       PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
/* 48 */       buf.writeItemStack(heldItem);
/* 49 */       mc.player.connection.sendPacket((Packet)new CPacketCustomPayload("MC|BSign", buf));
/* 50 */       sendMessage(OyVey.commandManager.getPrefix() + "Book Hack Success!");
/*    */     } else {
/* 52 */       sendMessage(OyVey.commandManager.getPrefix() + "b1g 3rr0r!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\BookCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
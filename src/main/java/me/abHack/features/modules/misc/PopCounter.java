/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import java.util.HashMap;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class PopCounter
/*    */   extends Module
/*    */ {
/* 12 */   public static HashMap<String, Integer> TotemPopContainer = new HashMap<>();
/* 13 */   private static PopCounter INSTANCE = new PopCounter();
/*    */   
/*    */   public PopCounter() {
/* 16 */     super("PopCounter", "Counts other players totem pops.", Module.Category.MISC, true, false, false);
/* 17 */     setInstance();
/*    */   }
/*    */   
/*    */   public static PopCounter getInstance() {
/* 21 */     if (INSTANCE == null) {
/* 22 */       INSTANCE = new PopCounter();
/*    */     }
/* 24 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 28 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 33 */     TotemPopContainer.clear();
/*    */   }
/*    */   
/*    */   public void onDeath(EntityPlayer player) {
/* 37 */     if (TotemPopContainer.containsKey(player.getName())) {
/* 38 */       int l_Count = ((Integer)TotemPopContainer.get(player.getName())).intValue();
/* 39 */       TotemPopContainer.remove(player.getName());
/* 40 */       if (l_Count == 1) {
/* 41 */         if (mc.player.equals(player)) {
/* 42 */           Command.sendMessage(ChatFormatting.BLUE + "You died after popping " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totem!");
/*    */         } else {
/* 44 */           Command.sendMessage(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totem!");
/*    */         } 
/* 46 */       } else if (mc.player.equals(player)) {
/* 47 */         Command.sendMessage(ChatFormatting.BLUE + "You died after popping " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totems!");
/*    */       } else {
/* 49 */         Command.sendMessage(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totems!");
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onTotemPop(EntityPlayer player) {
/* 55 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/*    */     
/* 59 */     int l_Count = 1;
/* 60 */     if (TotemPopContainer.containsKey(player.getName())) {
/* 61 */       l_Count = ((Integer)TotemPopContainer.get(player.getName())).intValue();
/* 62 */       TotemPopContainer.put(player.getName(), Integer.valueOf(++l_Count));
/*    */     } else {
/* 64 */       TotemPopContainer.put(player.getName(), Integer.valueOf(l_Count));
/*    */     } 
/* 66 */     if (l_Count == 1) {
/* 67 */       if (mc.player.equals(player)) {
/* 68 */         Command.sendMessage(ChatFormatting.BLUE + "You popped " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totem.");
/*    */       } else {
/* 70 */         Command.sendMessage(ChatFormatting.RED + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totem.");
/*    */       } 
/* 72 */     } else if (mc.player.equals(player)) {
/* 73 */       Command.sendMessage(ChatFormatting.BLUE + "You popped " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totems.");
/*    */     } else {
/* 75 */       Command.sendMessage(ChatFormatting.RED + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totems.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\PopCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
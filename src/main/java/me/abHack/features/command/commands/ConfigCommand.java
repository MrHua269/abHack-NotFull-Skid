/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ 
/*    */ public class ConfigCommand
/*    */   extends Command {
/*    */   public ConfigCommand() {
/* 15 */     super("config", new String[] { "<save/load/list>" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 19 */     if (commands.length == 1) {
/* 20 */       sendMessage(".config <save/load/list>");
/*    */       return;
/*    */     } 
/* 23 */     if (commands.length == 2)
/* 24 */       if ("list".equals(commands[0])) {
/* 25 */         String configs = "Configs: ";
/* 26 */         File file = new File("ab-Hack/");
/* 27 */         List<File> directories = (List<File>)Arrays.stream(Objects.requireNonNull(file.listFiles())).filter(File::isDirectory).filter(f -> !f.getName().equals("util")).collect(Collectors.toList());
/* 28 */         StringBuilder builder = new StringBuilder(configs);
/* 29 */         for (File file1 : directories)
/* 30 */           builder.append(file1.getName()).append(", "); 
/* 31 */         configs = builder.toString();
/* 32 */         sendMessage(configs);
/*    */       } else {
/* 34 */         sendMessage("Not a valid command... Possible usage: <list>");
/*    */       }  
/* 36 */     if (commands.length >= 3)
/* 37 */       switch (commands[0]) {
/*    */         case "save":
/* 39 */           OyVey.configManager.saveConfig(commands[1]);
/* 40 */           sendMessage(ChatFormatting.GREEN + "Config '" + commands[1] + "' has been saved.");
/*    */           return;
/*    */         case "load":
/* 43 */           if (OyVey.configManager.configExists(commands[1])) {
/* 44 */             OyVey.configManager.loadConfig(commands[1]);
/* 45 */             sendMessage(ChatFormatting.GREEN + "Config '" + commands[1] + "' has been loaded."); break;
/*    */           } 
/* 47 */           sendMessage(ChatFormatting.RED + "Config '" + commands[1] + "' does not exist.");
/*    */           break;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ConfigCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.manager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
import me.abHack.features.Feature;
import me.abHack.features.command.Command;
/*     */ import me.abHack.features.command.commands.*;
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class CommandManager extends Feature {
/*  13 */   private final ArrayList<Command> commands = new ArrayList<>();
/*  14 */   private String clientMessage = "<ab-Hack>";
/*  15 */   private String prefix = ".";
/*     */   
/*     */   public CommandManager() {
/*  18 */     super("Command");
/*  19 */     this.commands.add(new BindCommand());
/*  20 */     this.commands.add(new ModuleCommand());
/*  21 */     this.commands.add(new PrefixCommand());
/*  22 */     this.commands.add(new ConfigCommand());
/*  23 */     this.commands.add(new FriendCommand());
/*  24 */     this.commands.add(new HelpCommand());
/*  25 */     this.commands.add(new ReloadCommand());
/*  26 */     this.commands.add(new VclipCommand());
/*  27 */     this.commands.add(new BookCommand());
/*  28 */     this.commands.add(new ClearRamCommand());
/*  29 */     this.commands.add(new IpCommand());
/*  30 */     this.commands.add(new CoordsCommand());
/*  31 */     this.commands.add(new SpawnCommand());
/*  32 */     this.commands.add(new PeekCommand());
/*  33 */     this.commands.add(new UnloadCommand());
/*  34 */     this.commands.add(new ToggleCommand());
/*  35 */     this.commands.add(new ReloadSoundCommand());
/*  36 */     this.commands.add(new ClientCommand());
/*  37 */     this.commands.add(new HatCommand());
/*  38 */     this.commands.add(new TpCommand());
/*  39 */     this.commands.add(new HClipCommand());
/*  40 */     this.commands.add(new FpCommand());
/*  41 */     this.commands.add(new NBTCommand());
/*  42 */     this.commands.add(new SayCommand());
/*  43 */     this.commands.add(new EndClear());
/*  44 */     this.commands.add(new RenameCommand());
/*  45 */     this.commands.add(new TokenCommand());
/*  46 */     this.commands.add(new ServerCommand());
/*     */   }
/*     */   
/*     */   public static String[] removeElement(String[] input, int indexToDelete) {
/*  50 */     LinkedList<String> result = new LinkedList<>();
/*  51 */     for (int i = 0; i < input.length; i++) {
/*  52 */       if (i != indexToDelete)
/*  53 */         result.add(input[i]); 
/*     */     } 
/*  55 */     return result.<String>toArray(input);
/*     */   }
/*     */   
/*     */   private static String strip(String str) {
/*  59 */     if (str.startsWith("\"") && str.endsWith("\"")) {
/*  60 */       return str.substring("\"".length(), str.length() - "\"".length());
/*     */     }
/*  62 */     return str;
/*     */   }
/*     */   
/*     */   public void executeCommand(String command) {
/*  66 */     String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
/*  67 */     String name = parts[0].substring(1);
/*  68 */     String[] args = removeElement(parts, 0);
/*  69 */     for (int i = 0; i < args.length; i++) {
/*  70 */       if (args[i] != null)
/*  71 */         args[i] = strip(args[i]); 
/*     */     } 
/*  73 */     for (Command c : this.commands) {
/*  74 */       if (!c.getName().equalsIgnoreCase(name))
/*  75 */         continue;  c.execute(parts);
/*     */       return;
/*     */     } 
/*  78 */     Command.sendMessage(ChatFormatting.GRAY + "Command not found, type 'help' for the commands list.");
/*     */   }
/*     */   
/*     */   public Command getCommandByName(String name) {
/*  82 */     for (Command command : this.commands) {
/*  83 */       if (!command.getName().equals(name))
/*  84 */         continue;  return command;
/*     */     } 
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public ArrayList<Command> getCommands() {
/*  90 */     return this.commands;
/*     */   }
/*     */   
/*     */   public String getClientMessage() {
/*  94 */     return this.clientMessage;
/*     */   }
/*     */   
/*     */   public void setClientMessage(String clientMessage) {
/*  98 */     this.clientMessage = clientMessage;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 102 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix) {
/* 106 */     this.prefix = prefix;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
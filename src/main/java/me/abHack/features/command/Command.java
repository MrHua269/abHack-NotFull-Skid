/*    */ package me.abHack.features.command;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.Feature;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentBase;
/*    */ 
/*    */ public abstract class Command
/*    */   extends Feature
/*    */ {
/*    */   protected String name;
/*    */   protected String[] commands;
/*    */   
/*    */   public Command(String name) {
/* 18 */     super(name);
/* 19 */     this.name = name;
/* 20 */     this.commands = new String[] { "" };
/*    */   }
/*    */   
/*    */   public Command(String name, String[] commands) {
/* 24 */     super(name);
/* 25 */     this.name = name;
/* 26 */     this.commands = commands;
/*    */   }
/*    */   
/*    */   public static void sendMessage(String message) {
/* 30 */     sendSilentMessage(OyVey.commandManager.getClientMessage() + " " + ChatFormatting.GRAY + message);
/*    */   }
/*    */   
/*    */   public static void sendSilentMessage(String message) {
/* 34 */     if (nullCheck()) {
/*    */       return;
/*    */     }
/* 37 */     mc.player.sendMessage((ITextComponent)new ChatMessage(message));
/*    */   }
/*    */   
/*    */   public static String getCommandPrefix() {
/* 41 */     return OyVey.commandManager.getPrefix();
/*    */   }
/*    */   
/*    */   public static void sendMessagePh(String message) {
/* 45 */     sendSilentMessage(OyVey.commandManager.getClientMessage() + " §r" + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void execute(String[] paramArrayOfString);
/*    */   
/*    */   public String getName() {
/* 52 */     return this.name;
/*    */   }
/*    */   
/*    */   public String[] getCommands() {
/* 56 */     return this.commands;
/*    */   }
/*    */   
/*    */   public static class ChatMessage
/*    */     extends TextComponentBase {
/*    */     private final String text;
/*    */     
/*    */     public ChatMessage(String text) {
/* 64 */       Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
/* 65 */       Matcher matcher = pattern.matcher(text);
/* 66 */       StringBuffer stringBuffer = new StringBuffer();
/* 67 */       while (matcher.find()) {
/* 68 */         String replacement = matcher.group().substring(1);
/* 69 */         matcher.appendReplacement(stringBuffer, replacement);
/*    */       } 
/* 71 */       matcher.appendTail(stringBuffer);
/* 72 */       this.text = stringBuffer.toString();
/*    */     }
/*    */     
/*    */     public String getUnformattedComponentText() {
/* 76 */       return this.text;
/*    */     }
/*    */     
/*    */     public ITextComponent createCopy() {
/* 80 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
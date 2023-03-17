/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.client.audio.SoundHandler;
/*    */ import net.minecraft.client.audio.SoundManager;
/*    */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*    */ 
/*    */ public class ReloadSoundCommand extends Command {
/*    */   public ReloadSoundCommand() {
/* 11 */     super("sound", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/*    */     try {
/* 17 */       SoundManager sndManager = (SoundManager)ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, mc.getSoundHandler(), new String[] { "sndManager", "sndManager" });
/* 18 */       sndManager.reloadSoundSystem();
/* 19 */       Command.sendMessage(ChatFormatting.GREEN + "Reloaded Sound System.");
/* 20 */     } catch (Exception e) {
/* 21 */       System.out.println(ChatFormatting.RED + "Could not restart sound manager: " + e);
/* 22 */       e.printStackTrace();
/* 23 */       Command.sendMessage(ChatFormatting.RED + "Couldnt Reload Sound System!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\ReloadSoundCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
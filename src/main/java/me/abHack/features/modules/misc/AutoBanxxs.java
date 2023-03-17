/*     */ package me.abHack.features.modules.misc;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.List;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketChatMessage;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoBanxxs extends Module {
/*     */   private static final String xxsfile = "ab-Hack/小学生.txt";
/*  25 */   private final Timer timer = new Timer(); private static final String kickfile = "ab-Hack/踢出敏感词.txt"; private static final String banfile = "ab-Hack/封禁敏感词.txt";
/*  26 */   private final Setting<Boolean> banip = register(new Setting("Banip", Boolean.valueOf(false)));
/*  27 */   private final Setting<String> reason = register(new Setting("Kick", "宣传,误封联系管理员!"));
/*  28 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(20)));
/*  29 */   private final Setting<Boolean> safe = register(new Setting("User Security", Boolean.valueOf(true)));
/*  30 */   private final Setting<Boolean> escoff = register(new Setting("EscOff", Boolean.valueOf(true)));
/*     */   
/*     */   private File xxsFiles;
/*     */   private File kickFiles;
/*     */   private File banFiles;
/*     */   
/*     */   public AutoBanxxs() {
/*  37 */     super("AutoBanxxs", "AutoBanxxs", Module.Category.MISC, true, false, false);
/*     */   }
/*     */   
/*     */   public static void writeFile(File file, String conent) {
/*  41 */     BufferedWriter out = null;
/*     */     
/*     */     try {
/*  44 */       out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
/*  45 */       out.write(conent + "\r\n");
/*  46 */     } catch (Exception e) {
/*  47 */       e.printStackTrace();
/*     */     } finally {
/*  49 */       if (out != null) {
/*     */         try {
/*  51 */           out.close();
/*  52 */         } catch (IOException e) {
/*  53 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean readTxtFile(String fileName, String message) {
/*  60 */     Path path = Paths.get(fileName, new String[0]);
/*     */     
/*     */     try {
/*  63 */       List<String> lines = Files.readAllLines(path);
/*  64 */       if (!lines.isEmpty())
/*  65 */         for (String line : lines) {
/*  66 */           if (message.contains(line.trim()) && !line.trim().isEmpty()) return true;
/*     */         
/*     */         }  
/*  69 */     } catch (IOException e) {
/*  70 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  78 */     this.xxsFiles = new File("ab-Hack/小学生.txt");
/*  79 */     this.kickFiles = new File("ab-Hack/踢出敏感词.txt");
/*  80 */     this.banFiles = new File("ab-Hack/封禁敏感词.txt");
/*     */     
/*  82 */     if (!this.xxsFiles.exists()) {
/*     */       try {
/*  84 */         this.xxsFiles.createNewFile();
/*  85 */       } catch (IOException e) {
/*  86 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  90 */     if (!this.kickFiles.exists()) {
/*     */       try {
/*  92 */         this.kickFiles.createNewFile();
/*  93 */       } catch (IOException e) {
/*  94 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  98 */     if (!this.banFiles.exists()) {
/*     */       try {
/* 100 */         this.banFiles.createNewFile();
/* 101 */       } catch (IOException e) {
/* 102 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLogout() {
/* 110 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("AutoBanxxs")) {
/* 111 */       disable();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onLogin() {
/* 116 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("AutoBanxxs")) {
/* 117 */       disable();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 123 */     if (event.getPacket() instanceof SPacketChat) {
/* 124 */       SPacketChat packet = (SPacketChat)event.getPacket();
/* 125 */       String message = packet.getChatComponent().getFormattedText();
/* 126 */       String name = "";
/* 127 */       boolean cnm = false;
/* 128 */       int removeNext = -69;
/*     */       
/* 130 */       for (int i = 0; i < message.length() - 1; i++) {
/* 131 */         if (i != removeNext) {
/* 132 */           char a = message.charAt(i);
/*     */           
/* 134 */           if (a == '§') {
/* 135 */             removeNext = i + 1;
/*     */           }
/*     */           else {
/*     */             
/* 139 */             removeNext = -69;
/*     */             
/* 141 */             if (cnm) {
/* 142 */               if (a == '>')
/* 143 */                 break;  name = name + a;
/*     */             } 
/*     */             
/* 146 */             if (!cnm && a == '<')
/* 147 */               cnm = true; 
/*     */           } 
/*     */         } 
/* 150 */       }  if (mc.player.getName().equals(name) || (message.replace(" ", "").contains("ᴀʙʜᴀᴄᴋ") && ((Boolean)this.safe.getValue()).booleanValue()) || name.trim().isEmpty()) {
/*     */         return;
/*     */       }
/* 153 */       if (readTxtFile("ab-Hack/踢出敏感词.txt", message) && this.timer.passedS(((Integer)this.delay.getValue()).intValue())) {
/* 154 */         mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/kick " + name + " " + (String)this.reason.getValue()));
/* 155 */         Command.sendMessage(ChatFormatting.GREEN + "违规玩家: " + ChatFormatting.RED + name);
/* 156 */         this.timer.reset();
/* 157 */         writeFile(this.xxsFiles, "名字: " + name + " 执行处罚 : Kick  内容: " + message);
/*     */       } 
/* 159 */       if (readTxtFile("ab-Hack/封禁敏感词.txt", message) && this.timer.passedS(((Integer)this.delay.getValue()).intValue())) {
/* 160 */         if (((Boolean)this.banip.getValue()).booleanValue())
/* 161 */           mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/ban-ip " + name + " " + (String)this.reason.getValue())); 
/* 162 */         mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/ban " + name + " " + (String)this.reason.getValue()));
/* 163 */         Command.sendMessage(ChatFormatting.GREEN + "违规玩家: " + ChatFormatting.RED + name);
/* 164 */         this.timer.reset();
/* 165 */         writeFile(this.xxsFiles, "名字: " + name + " 执行处罚 : Ban  内容: " + message);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\AutoBanxxs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
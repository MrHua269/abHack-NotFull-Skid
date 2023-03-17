/*     */ package me.abHack.features.modules.misc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.FileUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketChatMessage;
/*     */ 
/*     */ public class Spammer
/*     */   extends Module {
/*     */   private static final String fileName = "ab-Hack/Spammer.txt";
/*     */   private static final String defaultMessage = "Welcome to use abHack";
/*  19 */   private static final List<String> spamMessages = new ArrayList<>();
/*  20 */   private static final Random rnd = new Random();
/*  21 */   private final Setting<Boolean> escoff = register(new Setting("EscOff", Boolean.valueOf(true)));
/*  22 */   private final Timer timer = new Timer();
/*  23 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.FILE));
/*  24 */   public Setting<Double> delay = register(new Setting("Delay", Double.valueOf(5.0D), Double.valueOf(0.0D), Double.valueOf(20.0D)));
/*  25 */   public Setting<String> custom = register(new Setting("Custom", "String", v -> (this.mode.getValue() == Mode.MSG)));
/*  26 */   public Setting<String> msgTarget = register(new Setting("MsgTarget", "Target...", v -> (this.mode.getValue() == Mode.MSG)));
/*  27 */   public Setting<Boolean> greentext = register(new Setting("Greentext", Boolean.FALSE, v -> (this.mode.getValue() == Mode.FILE)));
/*  28 */   public Setting<Boolean> random = register(new Setting("Random", Boolean.FALSE, v -> (this.mode.getValue() == Mode.FILE)));
/*  29 */   public Setting<Boolean> loadFile = register(new Setting("LoadFile", Boolean.FALSE, v -> (this.mode.getValue() == Mode.FILE)));
/*     */   
/*     */   public Spammer() {
/*  32 */     super("Spammer", "Spams stuff.", Module.Category.MISC, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  37 */     if (fullNullCheck()) {
/*  38 */       disable();
/*     */       return;
/*     */     } 
/*  41 */     readSpamFile();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLogin() {
/*  46 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("Spammer")) {
/*  47 */       disable();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onLogout() {
/*  52 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("Spammer")) {
/*  53 */       disable();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  58 */     spamMessages.clear();
/*  59 */     this.timer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  64 */     if (fullNullCheck()) {
/*  65 */       disable();
/*     */       return;
/*     */     } 
/*  68 */     if (((Boolean)this.loadFile.getValue()).booleanValue()) {
/*  69 */       readSpamFile();
/*  70 */       this.loadFile.setValue(Boolean.valueOf(false));
/*     */     } 
/*  72 */     if (this.timer.passedS(((Double)this.delay.getValue()).doubleValue())) {
/*  73 */       if (this.mode.getValue() == Mode.MSG) {
/*  74 */         String msg = (String)this.custom.getValue();
/*  75 */         msg = "/msg " + (String)this.msgTarget.getValue() + " " + msg;
/*  76 */         mc.player.sendChatMessage(msg);
/*  77 */       } else if (spamMessages.size() > 0) {
/*     */         String messageOut;
/*  79 */         if (((Boolean)this.random.getValue()).booleanValue()) {
/*  80 */           int index = rnd.nextInt(spamMessages.size());
/*  81 */           messageOut = spamMessages.get(index);
/*  82 */           spamMessages.remove(index);
/*     */         } else {
/*  84 */           messageOut = spamMessages.get(0);
/*  85 */           spamMessages.remove(0);
/*     */         } 
/*  87 */         spamMessages.add(messageOut);
/*  88 */         if (((Boolean)this.greentext.getValue()).booleanValue()) {
/*  89 */           messageOut = "> " + messageOut;
/*     */         }
/*  91 */         mc.player.connection.sendPacket((Packet)new CPacketChatMessage(messageOut.replaceAll("§", "")));
/*     */       } 
/*  93 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readSpamFile() {
/*  98 */     List<String> fileInput = FileUtil.readTextFileAllLines("ab-Hack/Spammer.txt");
/*  99 */     Iterator<String> i = fileInput.iterator();
/* 100 */     spamMessages.clear();
/* 101 */     while (i.hasNext()) {
/* 102 */       String s = i.next();
/* 103 */       if (s.replaceAll("\\s", "").isEmpty())
/* 104 */         continue;  spamMessages.add(s);
/*     */     } 
/* 106 */     if (spamMessages.size() == 0)
/* 107 */       spamMessages.add("Welcome to use abHack"); 
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 112 */     FILE,
/* 113 */     MSG;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\Spammer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
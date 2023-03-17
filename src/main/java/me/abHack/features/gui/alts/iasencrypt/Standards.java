/*     */ package me.abHack.features.gui.alts.iasencrypt;
/*     */ import java.io.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import java.nio.file.Files;
import java.nio.file.Path;
/*     */ import java.nio.file.attribute.DosFileAttributeView;
/*     */ import java.nio.file.attribute.DosFileAttributes;
/*     */ import me.abHack.features.gui.alts.ias.account.ExtendedAccountData;
/*     */ import me.abHack.features.gui.alts.tools.Config;
/*     */ import me.abHack.features.gui.alts.tools.alt.AccountData;
/*     */ import me.abHack.features.gui.alts.tools.alt.AltDatabase;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public final class Standards {
/*     */   public static final String cfgn = ".iasx";
/*  18 */   public static File IASFOLDER = (Minecraft.getMinecraft()).gameDir; public static final String pwdn = ".iasp";
/*     */   
/*     */   public static String getPassword() {
/*  21 */     File passwordFile = new File(IASFOLDER, ".iasp");
/*  22 */     if (passwordFile.exists()) {
/*     */       String pass;
/*     */       try {
/*  25 */         ObjectInputStream stream = new ObjectInputStream(new FileInputStream(passwordFile));
/*  26 */         pass = (String)stream.readObject();
/*  27 */         stream.close();
/*  28 */       } catch (IOException|ClassNotFoundException e) {
/*  29 */         throw new RuntimeException(e);
/*     */       } 
/*  31 */       return pass;
/*     */     } 
/*  33 */     String newPass = EncryptionTools.generatePassword();
/*     */     try {
/*  35 */       ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(passwordFile));
/*  36 */       out.writeObject(newPass);
/*  37 */       out.close();
/*  38 */     } catch (IOException e) {
/*  39 */       throw new RuntimeException(e);
/*     */     } 
/*     */     try {
/*  42 */       Path file = passwordFile.toPath();
/*  43 */       DosFileAttributes attr = Files.<DosFileAttributes>readAttributes(file, DosFileAttributes.class, new java.nio.file.LinkOption[0]);
/*  44 */       DosFileAttributeView view = Files.<DosFileAttributeView>getFileAttributeView(file, DosFileAttributeView.class, new java.nio.file.LinkOption[0]);
/*  45 */       if (!attr.isHidden()) {
/*  46 */         view.setHidden(true);
/*     */       }
/*  48 */     } catch (Exception e) {
/*  49 */       e.printStackTrace();
/*     */     } 
/*  51 */     return newPass;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateFolder() {
/*  56 */     String dir, OS = System.getProperty("os.name").toUpperCase();
/*  57 */     if (OS.contains("WIN")) {
/*  58 */       dir = System.getenv("AppData");
/*     */     } else {
/*  60 */       dir = System.getProperty("user.home");
/*  61 */       if (OS.contains("MAC")) {
/*  62 */         dir = dir + "/Library/Application Support";
/*     */       }
/*     */     } 
/*  65 */     IASFOLDER = new File(dir);
/*     */   }
/*     */   
/*     */   public static void importAccounts() {
/*  69 */     processData(getConfigV3());
/*  70 */     processData(getConfigV2());
/*  71 */     processData(getConfigV1(), false);
/*     */   }
/*     */   
/*     */   private static boolean hasData(AccountData data) {
/*  75 */     for (AccountData edata : AltDatabase.getInstance().getAlts()) {
/*  76 */       if (!edata.equalsBasic(data))
/*  77 */         continue;  return true;
/*     */     } 
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   private static void processData(Config olddata) {
/*  83 */     processData(olddata, true);
/*     */   }
/*     */   
/*     */   private static void processData(Config olddata, boolean decrypt) {
/*  87 */     if (olddata != null) {
/*  88 */       for (AccountData data : ((AltDatabase)olddata.getKey("altaccounts")).getAlts()) {
/*  89 */         ExtendedAccountData data2 = convertData(data, decrypt);
/*  90 */         if (hasData((AccountData)data2))
/*  91 */           continue;  AltDatabase.getInstance().getAlts().add(data2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static ExtendedAccountData convertData(AccountData oldData, boolean decrypt) {
/*  97 */     if (decrypt) {
/*  98 */       if (oldData instanceof ExtendedAccountData) {
/*  99 */         return new ExtendedAccountData(EncryptionTools.decodeOld(oldData.user), EncryptionTools.decodeOld(oldData.pass), oldData.alias, ((ExtendedAccountData)oldData).useCount, ((ExtendedAccountData)oldData).lastused, ((ExtendedAccountData)oldData).premium);
/*     */       }
/* 101 */       return new ExtendedAccountData(EncryptionTools.decodeOld(oldData.user), EncryptionTools.decodeOld(oldData.pass), oldData.alias);
/*     */     } 
/* 103 */     if (oldData instanceof ExtendedAccountData) {
/* 104 */       return new ExtendedAccountData(oldData.user, oldData.pass, oldData.alias, ((ExtendedAccountData)oldData).useCount, ((ExtendedAccountData)oldData).lastused, ((ExtendedAccountData)oldData).premium);
/*     */     }
/* 106 */     return new ExtendedAccountData(oldData.user, oldData.pass, oldData.alias);
/*     */   }
/*     */   
/*     */   private static Config getConfigV3() {
/* 110 */     File f = new File(IASFOLDER, ".ias");
/* 111 */     Config cfg = null;
/* 112 */     if (f.exists()) {
/*     */       try {
/* 114 */         ObjectInputStream stream = new ObjectInputStream(new FileInputStream(f));
/* 115 */         cfg = (Config)stream.readObject();
/* 116 */         stream.close();
/* 117 */       } catch (IOException|ClassNotFoundException e) {
/* 118 */         e.printStackTrace();
/*     */       } 
/* 120 */       f.delete();
/*     */     } 
/* 122 */     return cfg;
/*     */   }
/*     */   
/*     */   private static Config getConfigV2() {
/* 126 */     File f = new File((Minecraft.getMinecraft()).gameDir, ".ias");
/* 127 */     Config cfg = null;
/* 128 */     if (f.exists()) {
/*     */       try {
/* 130 */         ObjectInputStream stream = new ObjectInputStream(new FileInputStream(f));
/* 131 */         cfg = (Config)stream.readObject();
/* 132 */         stream.close();
/* 133 */       } catch (IOException|ClassNotFoundException e) {
/* 134 */         e.printStackTrace();
/*     */       } 
/* 136 */       f.delete();
/*     */     } 
/* 138 */     return cfg;
/*     */   }
/*     */   
/*     */   private static Config getConfigV1() {
/* 142 */     File f = new File((Minecraft.getMinecraft()).gameDir, "user.cfg");
/* 143 */     Config cfg = null;
/* 144 */     if (f.exists()) {
/*     */       try {
/* 146 */         ObjectInputStream stream = new ObjectInputStream(new FileInputStream(f));
/* 147 */         cfg = (Config)stream.readObject();
/* 148 */         stream.close();
/* 149 */       } catch (IOException|ClassNotFoundException e) {
/* 150 */         e.printStackTrace();
/*     */       } 
/* 152 */       f.delete();
/*     */     } 
/* 154 */     return cfg;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\iasencrypt\Standards.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.gui.alts.tools;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
/*     */ import java.nio.file.attribute.DosFileAttributeView;
/*     */ import java.nio.file.attribute.DosFileAttributes;
/*     */ import java.util.ArrayList;
/*     */ import me.abHack.features.gui.alts.iasencrypt.Standards;
/*     */ import me.abHack.features.gui.alts.tools.alt.AltDatabase;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class Config implements Serializable {
/*     */   public static final long serialVersionUID = -559038737L;
/*  20 */   private final ArrayList<Pair<String, Object>> field_218893_c = new ArrayList<>(); private static final String configFileName = ".iasx"; private static Config instance;
/*     */   
/*     */   private Config() {
/*  23 */     instance = this;
/*     */   }
/*     */   
/*     */   public static Config getInstance() {
/*  27 */     return instance;
/*     */   }
/*     */   
/*     */   public static void save() {
/*  31 */     saveToFile();
/*     */   }
/*     */   
/*     */   public static void load() {
/*  35 */     loadFromOld();
/*  36 */     readFromFile();
/*     */   }
/*     */   
/*     */   private static void readFromFile() {
/*  40 */     File f = new File(Standards.IASFOLDER, ".iasx");
/*  41 */     if (f.exists()) {
/*     */       try {
/*  43 */         ObjectInputStream stream = new ObjectInputStream(new FileInputStream(f));
/*  44 */         instance = (Config)stream.readObject();
/*  45 */         stream.close();
/*  46 */       } catch (IOException|ClassNotFoundException e) {
/*  47 */         e.printStackTrace();
/*  48 */         instance = new Config();
/*  49 */         f.delete();
/*     */       } 
/*     */     }
/*  52 */     if (instance == null) {
/*  53 */       instance = new Config();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void saveToFile() {
/*     */     
/*  62 */     try { Path file2 = (new File(Standards.IASFOLDER, ".iasx")).toPath();
/*  63 */       DosFileAttributes attr = Files.<DosFileAttributes>readAttributes(file2, DosFileAttributes.class, new java.nio.file.LinkOption[0]);
/*  64 */       DosFileAttributeView view = Files.<DosFileAttributeView>getFileAttributeView(file2, DosFileAttributeView.class, new java.nio.file.LinkOption[0]);
/*  65 */       if (attr.isHidden()) {
/*  66 */         view.setHidden(false);
/*     */       } }
/*  68 */     catch (NoSuchFileException noSuchFileException) {  }
/*  69 */     catch (Exception e)
/*  70 */     { e.printStackTrace(); }
/*     */     
/*     */     try {
/*  73 */       ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(Standards.IASFOLDER, ".iasx")));
/*  74 */       out.writeObject(instance);
/*  75 */       out.close();
/*  76 */     } catch (IOException e) {
/*  77 */       e.printStackTrace();
/*     */     } 
/*     */     try {
/*  80 */       Path file2 = (new File(Standards.IASFOLDER, ".iasx")).toPath();
/*  81 */       DosFileAttributes attr = Files.<DosFileAttributes>readAttributes(file2, DosFileAttributes.class, new java.nio.file.LinkOption[0]);
/*  82 */       DosFileAttributeView view = Files.<DosFileAttributeView>getFileAttributeView(file2, DosFileAttributeView.class, new java.nio.file.LinkOption[0]);
/*  83 */       if (!attr.isHidden()) {
/*  84 */         view.setHidden(true);
/*     */       }
/*  86 */     } catch (Exception e) {
/*  87 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadFromOld() {
/*  92 */     File f = new File((Minecraft.getMinecraft()).gameDir, "user.cfg");
/*  93 */     if (f.exists()) {
/*     */       try {
/*  95 */         ObjectInputStream stream = new ObjectInputStream(new FileInputStream(f));
/*  96 */         instance = (Config)stream.readObject();
/*  97 */         stream.close();
/*  98 */         f.delete();
/*  99 */         System.out.println("Loaded data from old file");
/* 100 */       } catch (IOException|ClassNotFoundException e) {
/* 101 */         e.printStackTrace();
/* 102 */         f.delete();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setKey(Pair<String, Object> key) {
/* 108 */     if (getKey(key.getValue1()) != null) {
/* 109 */       removeKey(key.getValue1());
/*     */     }
/* 111 */     this.field_218893_c.add(key);
/* 112 */     save();
/*     */   }
/*     */   
/*     */   public void setKey(String key, AltDatabase value) {
/* 116 */     setKey(new Pair<>(key, value));
/*     */   }
/*     */   
/*     */   public Object getKey(String key) {
/* 120 */     for (Pair<String, Object> aField_218893_c : this.field_218893_c) {
/* 121 */       if (!((String)aField_218893_c.getValue1()).equals(key))
/* 122 */         continue;  return aField_218893_c.getValue2();
/*     */     } 
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   private void removeKey(String key) {
/* 128 */     for (int i = 0; i < this.field_218893_c.size(); i++) {
/* 129 */       if (((String)((Pair)this.field_218893_c.get(i)).getValue1()).equals(key))
/* 130 */         this.field_218893_c.remove(i); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\tools\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
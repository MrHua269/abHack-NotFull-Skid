/*     */ package me.abHack.features.gui.alts.ias.tools;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.imageio.ImageIO;
/*     */ import me.abHack.features.gui.alts.tools.alt.AccountData;
/*     */ import me.abHack.features.gui.alts.tools.alt.AltDatabase;
import net.minecraft.client.Minecraft;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class SkinTools {
/*  16 */   public static final File cachedir = new File((Minecraft.getMinecraft()).gameDir, "cachedImages/skins/");
/*  17 */   private static final File skinOut = new File(cachedir, "temp.png");
/*     */ 
/*     */   
/*     */   public static void buildSkin(String name) {
/*     */     BufferedImage skin;
/*     */     try {
/*  23 */       skin = ImageIO.read(new File(cachedir, name + ".png"));
/*  24 */     } catch (IOException e) {
/*  25 */       if (skinOut.exists()) {
/*  26 */         skinOut.delete();
/*     */       }
/*     */       return;
/*     */     } 
/*  30 */     BufferedImage drawing = new BufferedImage(16, 32, 2);
/*  31 */     if (skin.getHeight() == 64) {
/*     */       
/*  33 */       int[] head = skin.getRGB(8, 8, 8, 8, null, 0, 8);
/*  34 */       int[] torso = skin.getRGB(20, 20, 8, 12, null, 0, 8);
/*  35 */       int[] larm = skin.getRGB(44, 20, 4, 12, null, 0, 4);
/*  36 */       int[] rarm = skin.getRGB(36, 52, 4, 12, null, 0, 4);
/*  37 */       int[] lleg = skin.getRGB(4, 20, 4, 12, null, 0, 4);
/*  38 */       int[] rleg = skin.getRGB(20, 52, 4, 12, null, 0, 4);
/*  39 */       int[] hat = skin.getRGB(40, 8, 8, 8, null, 0, 8);
/*  40 */       int[] jacket = skin.getRGB(20, 36, 8, 12, null, 0, 8);
/*  41 */       int[] larm2 = skin.getRGB(44, 36, 4, 12, null, 0, 4);
/*  42 */       int[] rarm2 = skin.getRGB(52, 52, 4, 12, null, 0, 4);
/*  43 */       int[] lleg2 = skin.getRGB(4, 36, 4, 12, null, 0, 4);
/*  44 */       int[] rleg2 = skin.getRGB(4, 52, 4, 12, null, 0, 4); int i;
/*  45 */       for (i = 0; i < hat.length; i++) {
/*  46 */         if (hat[i] == 0)
/*  47 */           hat[i] = head[i]; 
/*     */       } 
/*  49 */       for (i = 0; i < jacket.length; i++) {
/*  50 */         if (jacket[i] == 0)
/*  51 */           jacket[i] = torso[i]; 
/*     */       } 
/*  53 */       for (i = 0; i < larm2.length; i++) {
/*  54 */         if (larm2[i] == 0)
/*  55 */           larm2[i] = larm[i]; 
/*     */       } 
/*  57 */       for (i = 0; i < rarm2.length; i++) {
/*  58 */         if (rarm2[i] == 0)
/*  59 */           rarm2[i] = rarm[i]; 
/*     */       } 
/*  61 */       for (i = 0; i < lleg2.length; i++) {
/*  62 */         if (lleg2[i] == 0)
/*  63 */           lleg2[i] = lleg[i]; 
/*     */       } 
/*  65 */       for (i = 0; i < rleg2.length; i++) {
/*  66 */         if (rleg2[i] == 0)
/*  67 */           rleg2[i] = rleg[i]; 
/*     */       } 
/*  69 */       drawing.setRGB(4, 0, 8, 8, hat, 0, 8);
/*  70 */       drawing.setRGB(4, 8, 8, 12, jacket, 0, 8);
/*  71 */       drawing.setRGB(0, 8, 4, 12, larm2, 0, 4);
/*  72 */       drawing.setRGB(12, 8, 4, 12, rarm2, 0, 4);
/*  73 */       drawing.setRGB(4, 20, 4, 12, lleg2, 0, 4);
/*  74 */       drawing.setRGB(8, 20, 4, 12, rleg2, 0, 4);
/*     */     } else {
/*  76 */       int[] head = skin.getRGB(8, 8, 8, 8, null, 0, 8);
/*  77 */       int[] torso = skin.getRGB(20, 20, 8, 12, null, 0, 8);
/*  78 */       int[] arm = skin.getRGB(44, 20, 4, 12, null, 0, 4);
/*  79 */       int[] leg = skin.getRGB(4, 20, 4, 12, null, 0, 4);
/*  80 */       int[] hat = skin.getRGB(40, 8, 8, 8, null, 0, 8);
/*  81 */       for (int i = 0; i < hat.length; i++) {
/*  82 */         if (hat[i] == 0)
/*  83 */           hat[i] = head[i]; 
/*     */       } 
/*  85 */       drawing.setRGB(4, 0, 8, 8, hat, 0, 8);
/*  86 */       drawing.setRGB(4, 8, 8, 12, torso, 0, 8);
/*  87 */       drawing.setRGB(0, 8, 4, 12, arm, 0, 4);
/*  88 */       drawing.setRGB(12, 8, 4, 12, arm, 0, 4);
/*  89 */       drawing.setRGB(4, 20, 4, 12, leg, 0, 4);
/*  90 */       drawing.setRGB(8, 20, 4, 12, leg, 0, 4);
/*     */     } 
/*     */     try {
/*  93 */       ImageIO.write(drawing, "png", skinOut);
/*  94 */     } catch (IOException e) {
/*  95 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void javDrawSkin(int x, int y, int width, int height) {
/* 100 */     if (!skinOut.exists()) {
/*     */       return;
/*     */     }
/* 103 */     SkinRender r = new SkinRender(Minecraft.getMinecraft().getTextureManager(), skinOut);
/* 104 */     r.drawImage(x, y, width, height);
/*     */   }
/*     */   
/*     */   public static void cacheSkins() {
/* 108 */     if (!cachedir.exists() && !cachedir.mkdirs()) {
/* 109 */       System.out.println("Skin cache directory creation failed.");
/*     */     }
/* 111 */     for (AccountData data : AltDatabase.getInstance().getAlts()) {
/* 112 */       File file = new File(cachedir, data.alias + ".png");
/*     */       
/*     */       try {
/* 115 */         URL url = new URL(String.format("https://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { data.alias }));
/* 116 */         InputStream is = url.openStream();
/* 117 */         if (file.exists()) {
/* 118 */           file.delete();
/*     */         }
/* 120 */         file.createNewFile();
/* 121 */         FileOutputStream os = new FileOutputStream(file);
/* 122 */         byte[] b = new byte[2048]; int length;
/* 123 */         while ((length = is.read(b)) != -1) {
/* 124 */           os.write(b, 0, length);
/*     */         }
/* 126 */         is.close();
/* 127 */         os.close();
/* 128 */       } catch (IOException e) {
/*     */         
/*     */         try {
/* 131 */           URL url = new URL("https://skins.minecraft.net/MinecraftSkins/direwolf20.png");
/* 132 */           InputStream is = url.openStream();
/* 133 */           if (file.exists()) {
/* 134 */             file.delete();
/*     */           }
/* 136 */           file.createNewFile();
/* 137 */           FileOutputStream os = new FileOutputStream(file);
/* 138 */           byte[] b = new byte[2048]; int length;
/* 139 */           while ((length = is.read(b)) != -1) {
/* 140 */             os.write(b, 0, length);
/*     */           }
/* 142 */           is.close();
/* 143 */           os.close();
/* 144 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\tools\SkinTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
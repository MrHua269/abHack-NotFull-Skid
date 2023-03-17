/*    */ package me.abHack.features.gui.alts.iasencrypt;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.Arrays;
/*    */ import java.util.Base64;
/*    */ import javax.crypto.BadPaddingException;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.KeyGenerator;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ public final class EncryptionTools {
/* 15 */   private static final Base64.Encoder encoder = Base64.getEncoder(); public static final String DEFAULT_ENCODING = "UTF-8";
/* 16 */   private static final Base64.Decoder decoder = Base64.getDecoder();
/* 17 */   private static final MessageDigest sha512 = getSha512Hasher();
/* 18 */   private static final KeyGenerator keyGen = getAESGenerator();
/*    */   private static final String secretSalt = "${secretSalt}";
/*    */   
/*    */   public static String decodeOld(String text) {
/*    */     try {
/* 23 */       return new String(decoder.decode(text), "UTF-8");
/* 24 */     } catch (IOException e) {
/* 25 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String encode(String text) {
/*    */     try {
/* 31 */       byte[] data = text.getBytes("UTF-8");
/* 32 */       Cipher cipher = Cipher.getInstance("AES");
/* 33 */       cipher.init(1, getSecretKey());
/* 34 */       return new String(encoder.encode(cipher.doFinal(data)));
/* 35 */     } catch (BadPaddingException e) {
/* 36 */       throw new RuntimeException("The password does not match", e);
/* 37 */     } catch (IOException|java.security.InvalidKeyException|NoSuchAlgorithmException|javax.crypto.IllegalBlockSizeException|javax.crypto.NoSuchPaddingException e) {
/* 38 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String decode(String text) {
/*    */     try {
/* 44 */       byte[] data = decoder.decode(text);
/* 45 */       Cipher cipher = Cipher.getInstance("AES");
/* 46 */       cipher.init(2, getSecretKey());
/* 47 */       return new String(cipher.doFinal(data), "UTF-8");
/* 48 */     } catch (BadPaddingException e) {
/* 49 */       throw new RuntimeException("The password does not match", e);
/* 50 */     } catch (IOException|java.security.InvalidKeyException|NoSuchAlgorithmException|javax.crypto.IllegalBlockSizeException|javax.crypto.NoSuchPaddingException e) {
/* 51 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String generatePassword() {
/* 56 */     keyGen.init(256);
/* 57 */     return new String(encoder.encode(keyGen.generateKey().getEncoded()));
/*    */   }
/*    */   
/*    */   private static MessageDigest getSha512Hasher() {
/*    */     try {
/* 62 */       return MessageDigest.getInstance("SHA-512");
/* 63 */     } catch (NoSuchAlgorithmException e) {
/* 64 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static KeyGenerator getAESGenerator() {
/*    */     try {
/* 70 */       return KeyGenerator.getInstance("AES");
/* 71 */     } catch (NoSuchAlgorithmException e) {
/* 72 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static SecretKeySpec getSecretKey() {
/*    */     try {
/* 78 */       String password = "${secretSalt}" + Standards.getPassword() + "${secretSalt}";
/* 79 */       byte[] key = Arrays.copyOf(sha512.digest(password.getBytes("UTF-8")), 16);
/* 80 */       return new SecretKeySpec(key, "AES");
/* 81 */     } catch (UnsupportedEncodingException e) {
/* 82 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\iasencrypt\EncryptionTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.util;
/*    */ import java.io.IOException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.OpenOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FileUtil {
/*    */   public static void appendTextFile(String data, String file) {
/*    */     try {
/* 15 */       Path path = Paths.get(file, new String[0]);
/* 16 */       Files.write(path, Collections.singletonList(data), StandardCharsets.UTF_8, new OpenOption[] { Files.exists(path, new java.nio.file.LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE });
/* 17 */     } catch (IOException e) {
/* 18 */       System.out.println("WARNING: Unable to write file: " + file);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static List<String> readTextFileAllLines(String file) {
/*    */     try {
/* 24 */       Path path = Paths.get(file, new String[0]);
/* 25 */       return Files.readAllLines(path, StandardCharsets.UTF_8);
/* 26 */     } catch (IOException e) {
/* 27 */       System.out.println("WARNING: Unable to read file, creating new file: " + file);
/* 28 */       appendTextFile("", file);
/* 29 */       return Collections.emptyList();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\FileUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
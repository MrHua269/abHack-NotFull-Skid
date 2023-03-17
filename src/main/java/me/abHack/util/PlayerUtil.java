/*     */ package me.abHack.util;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ import java.util.Scanner;
/*     */ import java.util.UUID;
/*     */ import me.abHack.features.command.Command;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ 
/*     */ 
/*     */ public class PlayerUtil
/*     */   implements Util
/*     */ {
/*     */   public static UUID getUUIDFromName(String name) {
/*     */     try {
/*  26 */       lookUpUUID process = new lookUpUUID(name);
/*  27 */       Thread thread = new Thread(process);
/*  28 */       thread.start();
/*  29 */       thread.join();
/*  30 */       return process.getUUID();
/*  31 */     } catch (Exception e) {
/*  32 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String requestIDs(String data) {
/*     */     try {
/*  39 */       String query = "https://api.mojang.com/profiles/minecraft";
/*  40 */       URL url = new URL(query);
/*  41 */       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*  42 */       conn.setConnectTimeout(5000);
/*  43 */       conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
/*  44 */       conn.setDoOutput(true);
/*  45 */       conn.setDoInput(true);
/*  46 */       conn.setRequestMethod("POST");
/*  47 */       OutputStream os = conn.getOutputStream();
/*  48 */       os.write(data.getBytes(StandardCharsets.UTF_8));
/*  49 */       os.close();
/*  50 */       InputStream in = new BufferedInputStream(conn.getInputStream());
/*  51 */       String res = convertStreamToString(in);
/*  52 */       in.close();
/*  53 */       conn.disconnect();
/*  54 */       return res;
/*  55 */     } catch (Exception e) {
/*  56 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String convertStreamToString(InputStream is) {
/*  61 */     Scanner s = (new Scanner(is)).useDelimiter("\\A");
/*  62 */     return s.hasNext() ? s.next() : "/";
/*     */   }
/*     */   
/*     */   public static class lookUpUUID
/*     */     implements Runnable {
/*     */     private final String name;
/*     */     private volatile UUID uuid;
/*     */     
/*     */     public lookUpUUID(String name) {
/*  71 */       this.name = name;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       NetworkPlayerInfo profile;
/*     */       try {
/*  77 */         ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<>(((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(Util.mc.getConnection())).getPlayerInfoMap());
/*  78 */         profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(this.name)).findFirst().orElse(null);
/*  79 */         assert profile != null;
/*  80 */         this.uuid = profile.getGameProfile().getId();
/*  81 */       } catch (Exception e) {
/*  82 */         profile = null;
/*     */       } 
/*  84 */       if (profile == null) {
/*  85 */         Command.sendMessage("Player isn't online. Looking up UUID..");
/*  86 */         String s = PlayerUtil.requestIDs("[\"" + this.name + "\"]");
/*  87 */         if (s == null || s.isEmpty()) {
/*  88 */           Command.sendMessage("Couldn't find player ID. Are you connected to the internet? (0)");
/*     */         } else {
/*  90 */           JsonElement element = (new JsonParser()).parse(s);
/*  91 */           if (element.getAsJsonArray().size() == 0) {
/*  92 */             Command.sendMessage("Couldn't find player ID. (1)");
/*     */           } else {
/*     */             try {
/*  95 */               String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
/*  96 */               this.uuid = UUIDTypeAdapter.fromString(id);
/*  97 */             } catch (Exception e) {
/*  98 */               e.printStackTrace();
/*  99 */               Command.sendMessage("Couldn't find player ID. (2)");
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public UUID getUUID() {
/* 107 */       return this.uuid;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 111 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\PlayerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
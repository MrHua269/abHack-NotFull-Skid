/*    */ package me.abHack.features.gui.alts.tools.alt;
/*    */ 
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.UserAuthentication;
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*    */ import com.mojang.util.UUIDTypeAdapter;
/*    */ import java.util.UUID;
/*    */ import me.abHack.features.gui.alts.MR;
/*    */ import me.abHack.features.gui.alts.ias.account.AlreadyLoggedInException;
/*    */ import me.abHack.features.gui.alts.ias.config.ConfigValues;
/*    */ import me.abHack.features.gui.alts.iasencrypt.EncryptionTools;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ public class AltManager
/*    */ {
/*    */   private static AltManager manager;
/*    */   private final UserAuthentication auth;
/*    */   
/*    */   private AltManager() {
/* 21 */     UUID uuid = UUID.randomUUID();
/* 22 */     YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), uuid.toString());
/* 23 */     this.auth = authService.createUserAuthentication(Agent.MINECRAFT);
/* 24 */     authService.createMinecraftSessionService();
/*    */   }
/*    */   
/*    */   public static AltManager getInstance() {
/* 28 */     if (manager == null) {
/* 29 */       manager = new AltManager();
/*    */     }
/* 31 */     return manager;
/*    */   }
/*    */   public Throwable setUser(String username, String password) {
/*    */     AlreadyLoggedInException alreadyLoggedInException = null;
/* 35 */     Exception throwable = null;
/* 36 */     if (!Minecraft.getMinecraft().getSession().getUsername().equals(EncryptionTools.decode(username)) || Minecraft.getMinecraft().getSession().getToken().equals("0")) {
/* 37 */       if (!Minecraft.getMinecraft().getSession().getToken().equals("0")) {
/* 38 */         for (AccountData data : AltDatabase.getInstance().getAlts()) {
/* 39 */           if (!data.alias.equals(Minecraft.getMinecraft().getSession().getUsername()) || !data.user.equals(username))
/*    */             continue; 
/* 41 */           alreadyLoggedInException = new AlreadyLoggedInException();
/* 42 */           return (Throwable)alreadyLoggedInException;
/*    */         } 
/*    */       }
/* 45 */       this.auth.logOut();
/* 46 */       this.auth.setUsername(EncryptionTools.decode(username));
/* 47 */       this.auth.setPassword(EncryptionTools.decode(password));
/*    */       try {
/* 49 */         this.auth.logIn();
/* 50 */         Session session = new Session(this.auth.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(this.auth.getSelectedProfile().getId()), this.auth.getAuthenticatedToken(), this.auth.getUserType().getName());
/* 51 */         MR.setSession(session);
/* 52 */         for (int i = 0; i < AltDatabase.getInstance().getAlts().size(); i++) {
/* 53 */           AccountData data = AltDatabase.getInstance().getAlts().get(i);
/* 54 */           if (data.user.equals(username) && data.pass.equals(password))
/* 55 */             data.alias = session.getUsername(); 
/*    */         } 
/* 57 */       } catch (Exception e) {
/* 58 */         throwable = e;
/*    */       } 
/* 60 */     } else if (!ConfigValues.ENABLERELOG) {
/* 61 */       alreadyLoggedInException = new AlreadyLoggedInException();
/*    */     } 
/* 63 */     return (Throwable)alreadyLoggedInException;
/*    */   }
/*    */   
/*    */   public void setUserOffline(String username) {
/* 67 */     this.auth.logOut();
/* 68 */     Session session = new Session(username, username, "0", "legacy");
/*    */     try {
/* 70 */       MR.setSession(session);
/* 71 */     } catch (Exception e) {
/* 72 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\tools\alt\AltManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
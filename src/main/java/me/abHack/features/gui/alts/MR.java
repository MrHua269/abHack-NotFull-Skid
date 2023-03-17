/*    */ package me.abHack.features.gui.alts;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import me.abHack.features.gui.alts.tools.Config;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ public class MR
/*    */ {
/*    */   public static void init() {
/* 11 */     Config.load();
/*    */   }
/*    */   
/*    */   public static void setSession(Session s) throws Exception {
/* 15 */     Class<?> mc = Minecraft.getMinecraft().getClass();
/*    */     try {
/* 17 */       Field session = null;
/* 18 */       for (Field f : mc.getDeclaredFields()) {
/* 19 */         if (f.getType().isInstance(s)) {
/* 20 */           session = f;
/* 21 */           System.out.println("Found field " + f + ", injecting...");
/*    */         } 
/* 23 */       }  if (session == null) {
/* 24 */         throw new IllegalStateException("No field of type " + Session.class.getCanonicalName() + " declared.");
/*    */       }
/* 26 */       session.setAccessible(true);
/* 27 */       session.set(Minecraft.getMinecraft(), s);
/* 28 */       session.setAccessible(false);
/* 29 */     } catch (Exception e) {
/* 30 */       e.printStackTrace();
/* 31 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\MR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
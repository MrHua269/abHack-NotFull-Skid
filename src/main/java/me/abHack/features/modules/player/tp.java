/*    */ package me.abHack.features.modules.player;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ 
/*    */ public class tp
/*    */   extends Module
/*    */ {
/* 10 */   private final Setting<String> xcoord = register(new Setting("X", "100"));
/* 11 */   private final Setting<String> zcoord = register(new Setting("Z", "100"));
/* 12 */   private final Setting<Integer> tickblock = register(new Setting("Tick", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(10)));
/*    */   
/*    */   public tp() {
/* 15 */     super("tp", "tp coord", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */   
/*    */   public static boolean isInteger(String str) {
/* 19 */     Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
/* 20 */     return pattern.matcher(str).matches();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 26 */     if (!isInteger((String)this.xcoord.getValue()) || !isInteger((String)this.zcoord.getValue()))
/*    */       return; 
/* 28 */     if (mc.player.posY < 180.0D) {
/* 29 */       mc.player.setPosition(mc.player.posX, mc.player.posY + 5.0D, mc.player.posZ);
/*    */     }
/* 31 */     if (mc.player.posX <= Integer.parseInt((String)this.xcoord.getValue()) && Integer.parseInt((String)this.xcoord.getValue()) >= 0) {
/* 32 */       mc.player.setPosition(mc.player.posX + ((Integer)this.tickblock.getValue()).intValue(), mc.player.posY, mc.player.posZ);
/* 33 */     } else if (mc.player.posX >= Integer.parseInt((String)this.xcoord.getValue()) && (Integer.parseInt((String)this.xcoord.getValue()) <= 0 || Integer.parseInt((String)this.xcoord.getValue()) >= 0)) {
/*    */       
/* 35 */       mc.player.setPosition(mc.player.posX - ((Integer)this.tickblock.getValue()).intValue(), mc.player.posY, mc.player.posZ);
/* 36 */     } else if (mc.player.posX <= Integer.parseInt((String)this.xcoord.getValue()) && Integer.parseInt((String)this.xcoord.getValue()) <= 0) {
/* 37 */       mc.player.setPosition(mc.player.posX + ((Integer)this.tickblock.getValue()).intValue(), mc.player.posY, mc.player.posZ);
/*    */     } 
/* 39 */     if (mc.player.posX + 10.0D >= Integer.parseInt((String)this.xcoord.getValue()) && mc.player.posX - 10.0D <= Integer.parseInt((String)this.xcoord.getValue()))
/* 40 */       if (mc.player.posZ <= Integer.parseInt((String)this.zcoord.getValue()) && Integer.parseInt((String)this.zcoord.getValue()) >= 0) {
/* 41 */         mc.player.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ + ((Integer)this.tickblock.getValue()).intValue());
/* 42 */       } else if (mc.player.posZ >= Integer.parseInt((String)this.zcoord.getValue()) && (Integer.parseInt((String)this.zcoord.getValue()) <= 0 || Integer.parseInt((String)this.zcoord.getValue()) >= 0)) {
/* 43 */         mc.player.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ - ((Integer)this.tickblock.getValue()).intValue());
/* 44 */       } else if (mc.player.posZ <= Integer.parseInt((String)this.zcoord.getValue()) && Integer.parseInt((String)this.zcoord.getValue()) <= 0) {
/* 45 */         mc.player.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ + ((Integer)this.tickblock.getValue()).intValue());
/*    */       }  
/* 47 */     mc.player.setPosition(mc.player.posX, mc.player.posY - 1.0D, mc.player.posZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\tp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
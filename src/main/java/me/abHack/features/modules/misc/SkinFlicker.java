/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import java.util.Random;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*    */ 
/*    */ 
/*    */ public class SkinFlicker
/*    */   extends Module
/*    */ {
/* 12 */   private static final EnumPlayerModelParts[] PARTS_HORIZONTAL = new EnumPlayerModelParts[] { EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.HORIZONTAL));
/* 21 */   private final Setting<Integer> slowness = register(new Setting("Slowness", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(10)));
/* 22 */   private final Random r = new Random();
/* 23 */   private final int len = (EnumPlayerModelParts.values()).length;
/*    */   
/*    */   public SkinFlicker() {
/* 26 */     super("SkinFlicker", "Dynamic skin.", Module.Category.MISC, false, false, false);
/*    */   }
/*    */   public void onTick() {
/*    */     int i;
/*    */     boolean on;
/* 31 */     if (mc.player == null)
/* 32 */       return;  switch ((Mode)this.mode.getValue()) {
/*    */       case RANDOM:
/* 34 */         if (mc.player.ticksExisted % ((Integer)this.slowness.getValue()).intValue() != 0)
/* 35 */           return;  mc.gameSettings.switchModelPartEnabled(EnumPlayerModelParts.values()[this.r.nextInt(this.len)]);
/*    */         break;
/*    */       case VERTICAL:
/*    */       case HORIZONTAL:
/* 39 */         i = mc.player.ticksExisted / ((Integer)this.slowness.getValue()).intValue() % PARTS_HORIZONTAL.length * 2;
/* 40 */         on = false;
/* 41 */         if (i >= PARTS_HORIZONTAL.length) {
/* 42 */           on = true;
/* 43 */           i -= PARTS_HORIZONTAL.length;
/*    */         } 
/* 45 */         mc.gameSettings.setModelPartEnabled(PARTS_HORIZONTAL[i], on);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum Mode {
/* 51 */     HORIZONTAL,
/* 52 */     VERTICAL,
/* 53 */     RANDOM;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\SkinFlicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.gui.alts.ias.legacysupport;
/*    */ 
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class OldJava
/*    */   implements ILegacyCompat
/*    */ {
/*    */   public int[] getDate() {
/*  9 */     return new int[3];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFormattedDate() {
/* 14 */     return I18n.format("ias.updatejava", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\legacysupport\OldJava.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
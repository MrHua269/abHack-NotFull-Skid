/*    */ package me.abHack.features.gui.alts.tools;
/*    */ 
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ public class Tools {
/*    */   public static void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderColor, int insideColor) {
/*  7 */     Gui.drawRect(x + size, y + size, x1 - size, y1 - size, insideColor);
/*  8 */     Gui.drawRect(x + size, y + size, x1, y, borderColor);
/*  9 */     Gui.drawRect(x, y, x + size, y1, borderColor);
/* 10 */     Gui.drawRect(x1, y1, x1 - size, y + size, borderColor);
/* 11 */     Gui.drawRect(x, y1 - size, x1, y1, borderColor);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\tools\Tools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
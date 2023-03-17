/*    */ package me.abHack.features.gui.alts.ias.legacysupport;
/*    */ 
/*    */ import java.time.LocalDate;
/*    */ import java.time.LocalDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.time.format.FormatStyle;
/*    */ 
/*    */ public class NewJava
/*    */   implements ILegacyCompat
/*    */ {
/*    */   public int[] getDate() {
/* 12 */     return new int[] { LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getYear() };
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFormattedDate() {
/* 17 */     DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
/* 18 */     LocalDate date = LocalDateTime.now().withDayOfMonth(getDate()[1]).withMonth(getDate()[0]).withYear(getDate()[2]).toLocalDate();
/* 19 */     return date.format(format);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\alts\ias\legacysupport\NewJava.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
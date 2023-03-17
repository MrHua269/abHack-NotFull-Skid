/*     */ package me.abHack.util.render;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class TextUtil
/*     */ {
/*   9 */   public static final String BLACK = String.valueOf(ChatFormatting.BLACK);
/*  10 */   public static final String DARK_BLUE = String.valueOf(ChatFormatting.DARK_BLUE);
/*  11 */   public static final String DARK_GREEN = String.valueOf(ChatFormatting.DARK_GREEN);
/*  12 */   public static final String DARK_AQUA = String.valueOf(ChatFormatting.DARK_AQUA);
/*  13 */   public static final String DARK_RED = String.valueOf(ChatFormatting.DARK_RED);
/*  14 */   public static final String DARK_PURPLE = String.valueOf(ChatFormatting.DARK_PURPLE);
/*  15 */   public static final String GOLD = String.valueOf(ChatFormatting.GOLD);
/*  16 */   public static final String GRAY = String.valueOf(ChatFormatting.GRAY);
/*  17 */   public static final String DARK_GRAY = String.valueOf(ChatFormatting.DARK_GRAY);
/*  18 */   public static final String BLUE = String.valueOf(ChatFormatting.BLUE);
/*  19 */   public static final String GREEN = String.valueOf(ChatFormatting.GREEN);
/*  20 */   public static final String AQUA = String.valueOf(ChatFormatting.AQUA);
/*  21 */   public static final String RED = String.valueOf(ChatFormatting.RED);
/*  22 */   public static final String LIGHT_PURPLE = String.valueOf(ChatFormatting.LIGHT_PURPLE);
/*  23 */   public static final String YELLOW = String.valueOf(ChatFormatting.YELLOW);
/*  24 */   public static final String WHITE = String.valueOf(ChatFormatting.WHITE);
/*  25 */   public static final String OBFUSCATED = String.valueOf(ChatFormatting.OBFUSCATED);
/*  26 */   public static final String BOLD = String.valueOf(ChatFormatting.BOLD);
/*  27 */   public static final String STRIKE = String.valueOf(ChatFormatting.STRIKETHROUGH);
/*  28 */   public static final String UNDERLINE = String.valueOf(ChatFormatting.UNDERLINE);
/*  29 */   public static final String ITALIC = String.valueOf(ChatFormatting.ITALIC);
/*  30 */   public static final String RESET = String.valueOf(ChatFormatting.RESET);
/*  31 */   private static final Random rand = new Random();
/*     */   
/*     */   public static String stripColor(String input) {
/*  34 */     if (input != null) {
/*  35 */       return Pattern.compile("(?i)§[0-9A-FK-OR]").matcher(input).replaceAll("");
/*     */     }
/*  37 */     return "";
/*     */   }
/*     */   
/*     */   public static String coloredString(String string, Color color) {
/*  41 */     String coloredString = string;
/*  42 */     switch (color) {
/*     */       case AQUA:
/*  44 */         coloredString = ChatFormatting.AQUA + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case WHITE:
/*  48 */         coloredString = ChatFormatting.WHITE + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case BLACK:
/*  52 */         coloredString = ChatFormatting.BLACK + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case DARK_BLUE:
/*  56 */         coloredString = ChatFormatting.DARK_BLUE + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case DARK_GREEN:
/*  60 */         coloredString = ChatFormatting.DARK_GREEN + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case DARK_AQUA:
/*  64 */         coloredString = ChatFormatting.DARK_AQUA + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case DARK_RED:
/*  68 */         coloredString = ChatFormatting.DARK_RED + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case DARK_PURPLE:
/*  72 */         coloredString = ChatFormatting.DARK_PURPLE + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case GOLD:
/*  76 */         coloredString = ChatFormatting.GOLD + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case DARK_GRAY:
/*  80 */         coloredString = ChatFormatting.DARK_GRAY + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case GRAY:
/*  84 */         coloredString = ChatFormatting.GRAY + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case BLUE:
/*  88 */         coloredString = ChatFormatting.BLUE + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case RED:
/*  92 */         coloredString = ChatFormatting.RED + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case GREEN:
/*  96 */         coloredString = ChatFormatting.GREEN + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case LIGHT_PURPLE:
/* 100 */         coloredString = ChatFormatting.LIGHT_PURPLE + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */       
/*     */       case YELLOW:
/* 104 */         coloredString = ChatFormatting.YELLOW + coloredString + ChatFormatting.RESET;
/*     */         break;
/*     */     } 
/* 107 */     return coloredString;
/*     */   }
/*     */   
/*     */   public static String cropMaxLengthMessage(String s, int i) {
/* 111 */     String output = "";
/* 112 */     if (s.length() >= 256 - i) {
/* 113 */       output = s.substring(0, 256 - i);
/*     */     }
/* 115 */     return output;
/*     */   }
/*     */   
/*     */   public enum Color {
/* 119 */     NONE,
/* 120 */     WHITE,
/* 121 */     BLACK,
/* 122 */     DARK_BLUE,
/* 123 */     DARK_GREEN,
/* 124 */     DARK_AQUA,
/* 125 */     DARK_RED,
/* 126 */     DARK_PURPLE,
/* 127 */     GOLD,
/* 128 */     GRAY,
/* 129 */     DARK_GRAY,
/* 130 */     BLUE,
/* 131 */     GREEN,
/* 132 */     AQUA,
/* 133 */     RED,
/* 134 */     LIGHT_PURPLE,
/* 135 */     YELLOW;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\TextUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
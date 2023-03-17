/*     */ package me.abHack.features.setting;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import me.abHack.event.events.ClientEvent;
/*     */ import me.abHack.features.Feature;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ public class Setting<T> {
/*     */   private final String name;
/*     */   private final T defaultValue;
/*     */   private T value;
/*     */   private T plannedValue;
/*     */   private T min;
/*     */   private T max;
/*     */   private boolean hasRestriction;
/*     */   private Predicate<T> visibility;
/*     */   private String description;
/*     */   private Feature feature;
/*     */   
/*     */   public Setting(String name, T defaultValue) {
/*  22 */     this.name = name;
/*  23 */     this.defaultValue = defaultValue;
/*  24 */     this.value = defaultValue;
/*  25 */     this.plannedValue = defaultValue;
/*  26 */     this.description = "";
/*     */   }
/*     */   
/*     */   public Setting(String name, T defaultValue, String description) {
/*  30 */     this.name = name;
/*  31 */     this.defaultValue = defaultValue;
/*  32 */     this.value = defaultValue;
/*  33 */     this.plannedValue = defaultValue;
/*  34 */     this.description = description;
/*     */   }
/*     */   
/*     */   public Setting(String name, T defaultValue, T min, T max, String description) {
/*  38 */     this.name = name;
/*  39 */     this.defaultValue = defaultValue;
/*  40 */     this.value = defaultValue;
/*  41 */     this.min = min;
/*  42 */     this.max = max;
/*  43 */     this.plannedValue = defaultValue;
/*  44 */     this.description = description;
/*  45 */     this.hasRestriction = true;
/*     */   }
/*     */   
/*     */   public Setting(String name, T defaultValue, T min, T max) {
/*  49 */     this.name = name;
/*  50 */     this.defaultValue = defaultValue;
/*  51 */     this.value = defaultValue;
/*  52 */     this.min = min;
/*  53 */     this.max = max;
/*  54 */     this.plannedValue = defaultValue;
/*  55 */     this.description = "";
/*  56 */     this.hasRestriction = true;
/*     */   }
/*     */   
/*     */   public Setting(String name, T defaultValue, T min, T max, Predicate<T> visibility, String description) {
/*  60 */     this.name = name;
/*  61 */     this.defaultValue = defaultValue;
/*  62 */     this.value = defaultValue;
/*  63 */     this.min = min;
/*  64 */     this.max = max;
/*  65 */     this.plannedValue = defaultValue;
/*  66 */     this.visibility = visibility;
/*  67 */     this.description = description;
/*  68 */     this.hasRestriction = true;
/*     */   }
/*     */   
/*     */   public Setting(String name, T defaultValue, T min, T max, Predicate<T> visibility) {
/*  72 */     this.name = name;
/*  73 */     this.defaultValue = defaultValue;
/*  74 */     this.value = defaultValue;
/*  75 */     this.min = min;
/*  76 */     this.max = max;
/*  77 */     this.plannedValue = defaultValue;
/*  78 */     this.visibility = visibility;
/*  79 */     this.description = "";
/*  80 */     this.hasRestriction = true;
/*     */   }
/*     */   
/*     */   public Setting(String name, T defaultValue, Predicate<T> visibility) {
/*  84 */     this.name = name;
/*  85 */     this.defaultValue = defaultValue;
/*  86 */     this.value = defaultValue;
/*  87 */     this.visibility = visibility;
/*  88 */     this.plannedValue = defaultValue;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  92 */     return this.name;
/*     */   }
/*     */   
/*     */   public T getValue() {
/*  96 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(T value) {
/* 100 */     setPlannedValue(value);
/* 101 */     if (this.hasRestriction) {
/* 102 */       if (((Number)this.min).floatValue() > ((Number)value).floatValue()) {
/* 103 */         setPlannedValue(this.min);
/*     */       }
/* 105 */       if (((Number)this.max).floatValue() < ((Number)value).floatValue()) {
/* 106 */         setPlannedValue(this.max);
/*     */       }
/*     */     } 
/* 109 */     ClientEvent event = new ClientEvent(this);
/* 110 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 111 */     if (!event.isCanceled()) {
/* 112 */       this.value = this.plannedValue;
/*     */     } else {
/* 114 */       this.plannedValue = this.value;
/*     */     } 
/*     */   }
/*     */   
/*     */   public T getPlannedValue() {
/* 119 */     return this.plannedValue;
/*     */   }
/*     */   
/*     */   public void setPlannedValue(T value) {
/* 123 */     this.plannedValue = value;
/*     */   }
/*     */   
/*     */   public T getMin() {
/* 127 */     return this.min;
/*     */   }
/*     */   
/*     */   public void setMin(T min) {
/* 131 */     this.min = min;
/*     */   }
/*     */   
/*     */   public T getMax() {
/* 135 */     return this.max;
/*     */   }
/*     */   
/*     */   public void setMax(T max) {
/* 139 */     this.max = max;
/*     */   }
/*     */   
/*     */   public Feature getFeature() {
/* 143 */     return this.feature;
/*     */   }
/*     */   
/*     */   public void setFeature(Feature feature) {
/* 147 */     this.feature = feature;
/*     */   }
/*     */   
/*     */   public int getEnum(String input) {
/* 151 */     for (int i = 0; i < (this.value.getClass().getEnumConstants()).length; ) {
/* 152 */       Enum e = (Enum)this.value.getClass().getEnumConstants()[i];
/* 153 */       if (!e.name().equalsIgnoreCase(input)) { i++; continue; }
/* 154 */        return i;
/*     */     } 
/* 156 */     return -1;
/*     */   }
/*     */   
/*     */   public String currentEnumName() {
/* 160 */     return EnumConverter.getProperName((Enum)this.value);
/*     */   }
/*     */   
/*     */   public int currentEnum() {
/* 164 */     return EnumConverter.currentEnum((Enum)this.value);
/*     */   }
/*     */   
/*     */   public void increaseEnum() {
/* 168 */     this.plannedValue = (T)EnumConverter.increaseEnum((Enum)this.value);
/* 169 */     ClientEvent event = new ClientEvent(this);
/* 170 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 171 */     if (!event.isCanceled()) {
/* 172 */       this.value = this.plannedValue;
/*     */     } else {
/* 174 */       this.plannedValue = this.value;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getType() {
/* 179 */     if (isEnumSetting()) {
/* 180 */       return "Enum";
/*     */     }
/* 182 */     return getClassName(this.defaultValue);
/*     */   }
/*     */   
/*     */   public <T> String getClassName(T value) {
/* 186 */     return value.getClass().getSimpleName();
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 190 */     if (this.description == null) {
/* 191 */       return "";
/*     */     }
/* 193 */     return this.description;
/*     */   }
/*     */   
/*     */   public boolean isNumberSetting() {
/* 197 */     return (this.value instanceof Double || this.value instanceof Integer || this.value instanceof Short || this.value instanceof Long || this.value instanceof Float);
/*     */   }
/*     */   
/*     */   public boolean isEnumSetting() {
/* 201 */     return (!isNumberSetting() && !(this.value instanceof String) && !(this.value instanceof Bind) && !(this.value instanceof Character) && !(this.value instanceof Boolean));
/*     */   }
/*     */   
/*     */   public boolean isStringSetting() {
/* 205 */     return this.value instanceof String;
/*     */   }
/*     */   
/*     */   public T getDefaultValue() {
/* 209 */     return this.defaultValue;
/*     */   }
/*     */   
/*     */   public String getValueAsString() {
/* 213 */     return this.value.toString();
/*     */   }
/*     */   
/*     */   public boolean hasRestriction() {
/* 217 */     return this.hasRestriction;
/*     */   }
/*     */   
/*     */   public void setVisibility(Predicate<T> visibility) {
/* 221 */     this.visibility = visibility;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 225 */     if (this.visibility == null) {
/* 226 */       return true;
/*     */     }
/* 228 */     return this.visibility.test(getValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\setting\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
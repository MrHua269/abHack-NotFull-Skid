/*    */ package me.abHack.features.modules.client;
/*    */ 
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class Capes
/*    */   extends Module
/*    */ {
/*    */   private static Capes instance;
/* 14 */   private final Map<Mode, ResourceLocation> capeCache = new EnumMap<>(Mode.class);
/* 15 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.Cool));
/*    */   
/*    */   public Capes() {
/* 18 */     super("Capes", "Renders the client's capes", Module.Category.CLIENT, false, false, false);
/* 19 */     instance = this;
/*    */   }
/*    */   
/*    */   public static Capes getInstance() {
/* 23 */     if (instance == null)
/* 24 */       instance = new Capes(); 
/* 25 */     return instance;
/*    */   }
/*    */   
/*    */   public ResourceLocation getCapeLocation() {
/* 29 */     return this.capeCache.putIfAbsent(this.mode.getValue(), ((Mode)this.mode.getValue()).getResourceLocation());
/*    */   }
/*    */   
/*    */   public enum Mode {
/* 33 */     Cool("cool"),
/* 34 */     Anime("anime"),
/* 35 */     Sky("sky"),
/* 36 */     Chicken("chicken"),
/* 37 */     Duck("duck"),
/* 38 */     Galaxy("galaxy"),
/* 39 */     Future("future"),
/* 40 */     Akatsuki("akatsuki"),
/* 41 */     Lunar("lunar"),
/* 42 */     OF("of"),
/* 43 */     Delta("delta"),
/* 44 */     Moon("moon"),
/* 45 */     Enderman("enderman"),
/* 46 */     Panda("panda"),
/* 47 */     Scenery("scenery"),
/* 48 */     Heart("heart"),
/* 49 */     Purple("purple"),
/* 50 */     Sad("sad"),
/* 51 */     Shawchi("shawchi"),
/* 52 */     Sunset("sunset"),
/* 53 */     Steam("steam"),
/* 54 */     Sun("sun"),
/* 55 */     Reimu("reimu"),
/* 56 */     Uwu("uwu"),
/* 57 */     Vape("vape"),
/* 58 */     Sad_two("sad_two"),
/* 59 */     Xulu("xuluplus"),
/* 60 */     Kiuo("kuro"),
/* 61 */     Gradient("gradient"),
/* 62 */     Gentleman("gentleman"),
/* 63 */     Black("black");
/*    */     
/*    */     public final String location;
/*    */     
/*    */     Mode(String location) {
/* 68 */       this.location = location;
/*    */     }
/*    */     
/*    */     public ResourceLocation getResourceLocation() {
/* 72 */       return new ResourceLocation("textures/capes/" + this.location + ".png");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\client\Capes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.util.render.ItemChams;
/*    */ import me.abHack.util.render.ItemChams.shaders.*;
/*    */
/*    */

/*    */
/*    */ public enum ShaderMode {
/*  7 */   AQUA("Aqua", AquaShader::getInstance),
/*  8 */   AQUAGLOW("AquaGlow", AquaGlShader::getInstance),
/*  9 */   FLOW("Flow", FlowShader::getInstance),
/* 10 */   FLOWBLUR("zocker", () -> BasicShader.getInstance("flowglow_z.frag", 5.0E-4F)),
/* 11 */   FLOWGLOW("FlowGLow", FlowGlShader::getInstance),
/* 12 */   GHOST("Ghost", GlowShader::getInstance),
/* 13 */   SMOKE("Smoke", SmokeShader::getInstance),
/* 14 */   RED("Red", RedShader::getInstance),
/* 15 */   HOLYFUCK("holyfuck", HolyFuckShader::getInstance),
/* 16 */   GANG("gang", GangGlShader::getInstance),
/* 17 */   BLUEFLAMES("BlueFlames", () -> BasicShader.getInstance("blueflames.frag", 0.01F)),
/* 18 */   GAMER("Gang", () -> BasicShader.getInstance("gamer.frag", 0.03F)),
/* 19 */   CODEX("Codex", () -> BasicShader.getInstance("codex.frag")),
/* 20 */   GALAXY("Galaxy", () -> BasicShader.getInstance("galaxy33.frag", 0.001F)),
/* 21 */   DDEV("Ddev", () -> BasicShader.getInstance("ddev.frag")),
/* 22 */   CRAZY("crazy", () -> BasicShader.getInstance("crazy.frag", 0.01F)),
/* 23 */   SNOW("snow", () -> BasicShader.getInstance("snow.frag", 0.01F)),
/* 24 */   TECHNO("techno", () -> BasicShader.getInstance("techno.frag", 0.01F)),
/* 25 */   GOLDEN("golden", () -> BasicShader.getInstance("golden.frag", 0.01F)),
/* 26 */   HOTSHIT("hotshit", () -> BasicShader.getInstance("hotshit.frag", 0.005F)),
/* 27 */   GUISHADER("guishader", () -> BasicShader.getInstance("clickguishader.frag", 0.02F)),
/* 28 */   HIDEF("hidef", () -> BasicShader.getInstance("hidef.frag", 0.05F)),
/* 29 */   HOMIE("homie", () -> BasicShader.getInstance("homie.frag", 0.001F)),
/* 30 */   KFC("kfc", () -> BasicShader.getInstance("kfc.frag", 0.01F)),
/* 31 */   OHMYLORD("ohmylord", () -> BasicShader.getInstance("ohmylord.frag", 0.01F)),
/* 32 */   SHELDON("sheldon", () -> BasicShader.getInstance("sheldon.frag", 0.001F)),
/* 33 */   SMOKY("smoky", () -> BasicShader.getInstance("smoky.frag", 0.001F)),
/* 34 */   STROXINJAT("stroxinjat", () -> BasicShader.getInstance("stroxinjat.frag")),
/* 35 */   WEIRD("weird", () -> BasicShader.getInstance("weird.frag", 0.01F)),
/* 36 */   YIPPIEOWNS("yippieOwns", () -> BasicShader.getInstance("yippieOwns.frag")),
/* 37 */   PURPLE("purple", PurpleShader::getInstance);
/*    */   private final String name;
/*    */   private final ShaderProducer shaderProducer;
/*    */   
/*    */   ShaderMode(String name, ShaderProducer shaderProducer) {
/* 42 */     this.name = name;
/* 43 */     this.shaderProducer = shaderProducer;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 47 */     return this.name;
/*    */   }
/*    */   
/*    */   public FramebufferShader getShader() {
/* 51 */     return this.shaderProducer.getInstance();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\render\ItemChams\ShaderMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
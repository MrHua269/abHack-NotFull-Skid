/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.render.ColorUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ 
/*    */ public class BlockHighlight
/*    */   extends Module
/*    */ {
/* 14 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*    */ 
/*    */   
/*    */   public BlockHighlight() {
/* 18 */     super("BlockHighlight", "Render the block you are looking at.", Module.Category.RENDER, false, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/* 23 */     RayTraceResult ray = mc.objectMouseOver;
/* 24 */     if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
/* 25 */       BlockPos blockpos = ray.getBlockPos();
/* 26 */       RenderUtil.drawBlockOutline(blockpos, ColorUtil.getPrimaryColor(), ((Float)this.lineWidth.getValue()).floatValue(), false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\BlockHighlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
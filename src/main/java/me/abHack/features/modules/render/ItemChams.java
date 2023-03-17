/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import me.abHack.event.events.Render2DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.mixin.mixins.IEntityRenderer;
/*    */ import me.abHack.util.BlockUtil;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.render.ColorUtil;
/*    */ import me.abHack.util.render.ItemChams.FramebufferShader;
/*    */ import me.abHack.util.render.ItemChams.ShaderMode;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraftforge.client.event.RenderHandEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ public class ItemChams extends Module {
/*    */   private static ItemChams INSTANCE;
/* 20 */   public Setting<ShaderMode> shader = register(new Setting("Shader Mode", ShaderMode.AQUA));
/* 21 */   public Setting<Boolean> animation = register(new Setting("Animation", Boolean.valueOf(true)));
/* 22 */   public Setting<Integer> animationSpeed = register(new Setting("Animation Speed", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10)));
/* 23 */   public Setting<Float> radius = register(new Setting("Glow Radius", Float.valueOf(3.3F), Float.valueOf(1.0F), Float.valueOf(10.0F)));
/* 24 */   public Setting<Float> divider = register(new Setting("Glow Divider", Float.valueOf(158.6F), Float.valueOf(1.0F), Float.valueOf(1000.0F)));
/* 25 */   public Setting<Float> maxSample = register(new Setting("Glow MaxSample", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(20.0F)));
/* 26 */   public Setting<Boolean> reset = register(new Setting("Reset", Boolean.valueOf(false)));
/* 27 */   private Boolean criticalSection = Boolean.valueOf(false);
/*    */   
/*    */   public ItemChams() {
/* 30 */     super("ItemChams", "item render", Module.Category.RENDER, true, false, false);
/*    */   }
/*    */   
/*    */   public static ItemChams getInstance() {
/* 34 */     if (INSTANCE == null) {
/* 35 */       INSTANCE = new ItemChams();
/*    */     }
/* 37 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 42 */     if (((Boolean)this.reset.getValue()).booleanValue()) {
/* 43 */       this.shader.setValue(ShaderMode.AQUA);
/* 44 */       this.animation.setValue(Boolean.valueOf(true));
/* 45 */       this.animationSpeed.setValue(Integer.valueOf(1));
/* 46 */       this.radius.setValue(Float.valueOf(3.3F));
/* 47 */       this.divider.setValue(Float.valueOf(158.6F));
/* 48 */       this.maxSample.setValue(Float.valueOf(10.0F));
/* 49 */       this.reset.setValue(Boolean.valueOf(false));
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderHand(RenderHandEvent event) {
/* 55 */     if (!this.criticalSection.booleanValue()) {
/* 56 */       event.setCanceled(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender2D(Render2DEvent event) {
/* 62 */     if (Display.isActive() || Display.isVisible()) {
/* 63 */       if (BlockUtil.getBlock(EntityUtil.getPlayerPos((EntityPlayer)mc.player).up()).equals(Blocks.WATER))
/*    */         return; 
/* 65 */       FramebufferShader shader = ((ShaderMode)this.shader.getValue()).getShader();
/* 66 */       if (shader == null) {
/*    */         return;
/*    */       }
/* 69 */       shader.setShaderParams((Boolean)this.animation.getValue(), ((Integer)this.animationSpeed.getValue()).intValue(), ColorUtil.getPrimaryColor(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.divider.getValue()).floatValue(), ((Float)this.maxSample.getValue()).floatValue());
/* 70 */       this.criticalSection = Boolean.valueOf(true);
/* 71 */       shader.startDraw(mc.getRenderPartialTicks());
/* 72 */       ((IEntityRenderer)mc.entityRenderer).invokeRenderHand(mc.getRenderPartialTicks(), 2);
/* 73 */       shader.stopDraw();
/* 74 */       this.criticalSection = Boolean.valueOf(false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\ItemChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
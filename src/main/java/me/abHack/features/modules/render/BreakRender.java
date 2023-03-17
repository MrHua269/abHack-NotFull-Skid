/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.manager.BreakManager;
/*    */ import me.abHack.util.MathUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.play.server.SPacketBlockBreakAnim;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class BreakRender
/*    */   extends Module
/*    */ {
/* 22 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(8.0F), Float.valueOf(1.0F), Float.valueOf(15.0F)));
/* 23 */   private final Setting<Boolean> name = register(new Setting("Name", Boolean.valueOf(false)));
/* 24 */   private final Setting<Integer> red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 25 */   private final Setting<Integer> green = register(new Setting("Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/* 26 */   private final Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/* 27 */   private final Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 28 */   private final Map<String, BlockPos> map = new HashMap<>();
/* 29 */   public Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(true)));
/* 30 */   private final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(85), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.box.getValue()).booleanValue()));
/* 31 */   public Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/* 32 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/* 33 */   public Setting<Double> height = register(new Setting("Height", Double.valueOf(-0.9D), Double.valueOf(-2.0D), Double.valueOf(2.0D)));
/*    */   
/*    */   public BreakRender() {
/* 36 */     super("BreakRender", "targer break esp .", Module.Category.RENDER, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 41 */     this.map.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 46 */     if (mc.player.isDead) {
/*    */       
/* 48 */       this.map.clear();
/* 49 */       BreakManager.map.clear();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent render3DEvent) {
/* 55 */     if (this.map.isEmpty())
/*    */       return; 
/* 57 */     RenderUtil.prepareGL3D();
/* 58 */     for (Map.Entry<String, BlockPos> a : this.map.entrySet()) {
/* 59 */       if (mc.player.getDistanceSq(a.getValue()) > MathUtil.square(((Float)this.range.getValue()).floatValue()))
/*    */         continue; 
/* 61 */       if (OyVey.friendManager.isFriend(a.getKey())) {
/* 62 */         RenderUtil.drawBoxESP(a.getValue(), new Color(100, 24, 250, ((Integer)this.alpha.getValue()).intValue()), false, null, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), true, ((Double)this.height.getValue()).doubleValue(), false, false, false, false, 0);
/*    */       } else {
/* 64 */         RenderUtil.drawBoxESP(a.getValue(), new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue()), false, null, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), true, ((Double)this.height.getValue()).doubleValue(), false, false, false, false, 0);
/* 65 */       }  if (((Boolean)this.name.getValue()).booleanValue()) {
/* 66 */         RenderUtil.drawText(a.getValue(), a.getKey());
/*    */       }
/*    */     } 
/* 69 */     RenderUtil.releaseGL3D();
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacket(PacketEvent.Receive event) {
/* 75 */     if (event.getPacket() instanceof SPacketBlockBreakAnim) {
/* 76 */       SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim)event.getPacket();
/* 77 */       BlockPos pos = packet.getPosition();
/* 78 */       Entity breaker = mc.world.getEntityByID(packet.getBreakerId());
/* 79 */       if (breaker == null)
/* 80 */         return;  this.map.put(breaker.getName(), pos);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\BreakRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
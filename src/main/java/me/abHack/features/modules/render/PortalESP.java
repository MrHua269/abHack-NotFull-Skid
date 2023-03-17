/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ 
/*    */ public class PortalESP
/*    */   extends Module
/*    */ {
/* 18 */   private final ArrayList<BlockPos> blockPosArrayList = new ArrayList<>();
/* 19 */   private final Setting<Integer> distance = register(new Setting("Distance", Integer.valueOf(60), Integer.valueOf(1), Integer.valueOf(256)));
/* 20 */   private final Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(false)));
/* 21 */   private final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.box.getValue()).booleanValue()));
/* 22 */   private final Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/* 23 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*    */   private int cooldownTicks;
/*    */   
/*    */   public PortalESP() {
/* 27 */     super("PortalESP", "Draws esp over portals.", Module.Category.RENDER, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTickEvent(TickEvent.ClientTickEvent event) {
/* 32 */     if (mc.world == null) {
/*    */       return;
/*    */     }
/* 35 */     if (this.cooldownTicks < 1) {
/* 36 */       this.blockPosArrayList.clear();
/* 37 */       compileDL();
/* 38 */       this.cooldownTicks = 80;
/*    */     } 
/* 40 */     this.cooldownTicks--;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/* 45 */     if (mc.world == null) {
/*    */       return;
/*    */     }
/* 48 */     for (BlockPos pos : this.blockPosArrayList) {
/* 49 */       RenderUtil.prepareGL3D();
/* 50 */       RenderUtil.drawBoxESP(pos, new Color(204, 0, 153, 255), false, new Color(204, 0, 153, 255), ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), false);
/* 51 */       RenderUtil.releaseGL3D();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void compileDL() {
/* 56 */     if (mc.world == null || mc.player == null) {
/*    */       return;
/*    */     }
/* 59 */     for (int x = (int)mc.player.posX - ((Integer)this.distance.getValue()).intValue(); x <= (int)mc.player.posX + ((Integer)this.distance.getValue()).intValue(); x++) {
/* 60 */       for (int y = (int)mc.player.posY - ((Integer)this.distance.getValue()).intValue(); y <= (int)mc.player.posY + ((Integer)this.distance.getValue()).intValue(); y++) {
/* 61 */         int z = (int)Math.max(mc.player.posZ - ((Integer)this.distance.getValue()).intValue(), 1.0D);
/* 62 */         while (z <= Math.min(mc.player.posZ + ((Integer)this.distance.getValue()).intValue(), 256.0D)) {
/* 63 */           BlockPos pos = new BlockPos(x, y, z);
/* 64 */           Block block = mc.world.getBlockState(pos).getBlock();
/* 65 */           if (block instanceof net.minecraft.block.BlockPortal) {
/* 66 */             this.blockPosArrayList.add(pos);
/*    */           }
/* 68 */           z++;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\PortalESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
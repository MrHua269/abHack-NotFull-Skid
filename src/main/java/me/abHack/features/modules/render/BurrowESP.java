/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class BurrowESP
/*    */   extends Module
/*    */ {
/*    */   private final Setting<Integer> boxRed;
/*    */   private final Setting<Integer> outlineGreen;
/*    */   private final Setting<Integer> boxGreen;
/*    */   private final Setting<Boolean> box;
/*    */   private final Setting<Boolean> cOutline;
/*    */   private final Setting<Integer> outlineBlue;
/* 26 */   private final Setting<Boolean> name = register(new Setting("Name", Boolean.valueOf(false)));
/*    */   private final Setting<Integer> boxAlpha;
/*    */   private final Setting<Float> outlineWidth;
/*    */   private final Setting<Integer> outlineRed;
/*    */   private final Setting<Boolean> outline;
/*    */   private final Setting<Integer> boxBlue;
/*    */   private final Map<EntityPlayer, BlockPos> burrowedPlayers;
/*    */   private final Setting<Integer> outlineAlpha;
/*    */   
/*    */   public BurrowESP() {
/* 36 */     super("BurrowESP", "Show burrow players .", Module.Category.RENDER, true, false, false);
/* 37 */     this.box = new Setting("Box", Boolean.valueOf(true));
/* 38 */     this.boxRed = register(new Setting("BoxRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 39 */     this.boxGreen = register(new Setting("BoxGreen", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 40 */     this.boxBlue = register(new Setting("BoxBlue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 41 */     this.boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255)));
/* 42 */     this.outline = register(new Setting("Outline", Boolean.valueOf(true)));
/* 43 */     this.outlineWidth = register(new Setting("OutlineWidth", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/* 44 */     this.cOutline = register(new Setting("CustomOutline", Boolean.valueOf(false), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/* 45 */     this.outlineRed = register(new Setting("OutlineRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.cOutline.getValue()).booleanValue()));
/* 46 */     this.outlineGreen = register(new Setting("OutlineGreen", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.cOutline.getValue()).booleanValue()));
/* 47 */     this.outlineBlue = register(new Setting("OutlineBlue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.cOutline.getValue()).booleanValue()));
/* 48 */     this.outlineAlpha = register(new Setting("OutlineAlpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.cOutline.getValue()).booleanValue()));
/* 49 */     this.burrowedPlayers = new HashMap<>();
/*    */   }
/*    */   
/*    */   private void getPlayers() {
/* 53 */     for (EntityPlayer entityPlayer : mc.world.playerEntities) {
/* 54 */       if (entityPlayer == mc.player || OyVey.friendManager.isFriend(entityPlayer.getName()) || !EntityUtil.isLiving((Entity)entityPlayer) || !isBurrowed(entityPlayer))
/*    */         continue; 
/* 56 */       this.burrowedPlayers.put(entityPlayer, new BlockPos(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 62 */     this.burrowedPlayers.clear();
/*    */   }
/*    */   
/*    */   private void lambda$onRender3D$8(Map.Entry entry) {
/* 66 */     renderBurrowedBlock((BlockPos)entry.getValue());
/* 67 */     if (((Boolean)this.name.getValue()).booleanValue()) {
/* 68 */       RenderUtil.drawText((BlockPos)entry.getValue(), ((EntityPlayer)entry.getKey()).getGameProfile().getName());
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean isBurrowed(EntityPlayer entityPlayer) {
/* 73 */     BlockPos blockPos = new BlockPos(Math.floor(entityPlayer.posX), Math.floor(entityPlayer.posY + 0.2D), Math.floor(entityPlayer.posZ));
/* 74 */     return (mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos).getBlock() == Blocks.CHEST);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 79 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 82 */     this.burrowedPlayers.clear();
/* 83 */     getPlayers();
/*    */   }
/*    */   
/*    */   private void renderBurrowedBlock(BlockPos blockPos) {
/* 87 */     RenderUtil.prepareGL3D();
/* 88 */     RenderUtil.drawBoxESP(blockPos, new Color(((Integer)this.boxRed.getValue()).intValue(), ((Integer)this.boxGreen.getValue()).intValue(), ((Integer)this.boxBlue.getValue()).intValue(), ((Integer)this.boxAlpha.getValue()).intValue()), true, new Color(((Integer)this.outlineRed.getValue()).intValue(), ((Integer)this.outlineGreen.getValue()).intValue(), ((Integer)this.outlineBlue.getValue()).intValue(), ((Integer)this.outlineAlpha.getValue()).intValue()), ((Float)this.outlineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), true);
/* 89 */     RenderUtil.releaseGL3D();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent render3DEvent) {
/* 94 */     if (!this.burrowedPlayers.isEmpty())
/* 95 */       this.burrowedPlayers.entrySet().forEach(this::lambda$onRender3D$8); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\BurrowESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
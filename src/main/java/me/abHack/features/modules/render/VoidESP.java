/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import java.util.stream.Collectors;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.BlockUtil;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.RotationUtil;
/*    */ import me.abHack.util.Timer;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class VoidESP
/*    */   extends Module {
/* 21 */   private final Setting<Float> radius = register(new Setting("Radius", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(50.0F)));
/* 22 */   private final Timer timer = new Timer();
/* 23 */   private final Setting<Integer> updates = register(new Setting("Updates", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(1000)));
/* 24 */   private final Setting<Integer> voidCap = register(new Setting("VoidCap", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(1000)));
/* 25 */   private final Setting<Integer> red = register(new Setting("Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/* 26 */   private final Setting<Integer> green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 27 */   private final Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/* 28 */   private final Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 29 */   public Setting<Boolean> air = register(new Setting("OnlyAir", Boolean.valueOf(false)));
/* 30 */   public Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(true)));
/* 31 */   private final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(85), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.box.getValue()).booleanValue()));
/* 32 */   public Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/* 33 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/* 34 */   public Setting<Double> height = register(new Setting("Height", Double.valueOf(-0.8D), Double.valueOf(-2.0D), Double.valueOf(2.0D)));
/* 35 */   private List<BlockPos> voidHoles = new CopyOnWriteArrayList<>();
/*    */   
/*    */   public VoidESP() {
/* 38 */     super("VoidEsp", "Esps the void", Module.Category.RENDER, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onToggle() {
/* 43 */     this.timer.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLogin() {
/* 48 */     this.timer.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 53 */     if ((!fullNullCheck() || mc.player.dimension != 1) && this.timer.passedMs(((Integer)this.updates.getValue()).intValue())) {
/* 54 */       this.voidHoles.clear();
/* 55 */       this.voidHoles = findVoidHoles();
/* 56 */       if (this.voidHoles.size() > ((Integer)this.voidCap.getValue()).intValue()) {
/* 57 */         this.voidHoles.clear();
/*    */       }
/* 59 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/* 65 */     if (fullNullCheck() && mc.player.dimension == 1) {
/*    */       return;
/*    */     }
/* 68 */     for (BlockPos pos : this.voidHoles) {
/* 69 */       if (!RotationUtil.isInFov(pos))
/* 70 */         continue;  RenderUtil.prepareGL3D();
/* 71 */       RenderUtil.drawBoxESP(pos, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue()), false, null, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), true, ((Double)this.height.getValue()).doubleValue(), false, false, false, false, 0);
/* 72 */       RenderUtil.releaseGL3D();
/*    */     } 
/*    */   }
/*    */   
/*    */   private List<BlockPos> findVoidHoles() {
/* 77 */     BlockPos playerPos = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/* 78 */     return (List<BlockPos>)BlockUtil.getDisc(playerPos.add(0, -playerPos.getY(), 0), ((Float)this.radius.getValue()).floatValue()).stream().filter(this::isVoid).collect(Collectors.toList());
/*    */   }
/*    */   
/*    */   private boolean isVoid(BlockPos pos) {
/* 82 */     return ((mc.world.getBlockState(pos).getBlock() == Blocks.AIR || (!((Boolean)this.air.getValue()).booleanValue() && mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK)) && pos.getY() < 1 && pos.getY() >= 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\VoidESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
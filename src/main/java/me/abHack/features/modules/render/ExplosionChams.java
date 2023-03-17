/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.event.events.Render3DEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.Timer;
/*    */ import me.abHack.util.render.ColorUtil;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExplosionChams
/*    */   extends Module
/*    */ {
/*    */   public final Setting<Float> increase;
/*    */   public final Setting<Integer> riseSpeed;
/*    */   public Map<EntityEnderCrystal, BlockPos> explodedCrystals;
/*    */   public BlockPos crystalPos;
/*    */   public int aliveTicks;
/*    */   public double speed;
/*    */   public Timer timer;
/*    */   
/*    */   public ExplosionChams() {
/* 37 */     super("ExplosionChams", "Draws a cham when a crystal explodes", Module.Category.RENDER, true, false, false);
/* 38 */     this.increase = register(new Setting("Increase Size", Float.valueOf(0.5F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/* 39 */     this.riseSpeed = register(new Setting("Rise Time", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(50)));
/* 40 */     this.explodedCrystals = new HashMap<>();
/* 41 */     this.crystalPos = null;
/* 42 */     this.aliveTicks = 0;
/* 43 */     this.speed = 0.0D;
/* 44 */     this.timer = new Timer();
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 48 */     this.explodedCrystals.clear();
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 52 */     if (fullNullCheck())
/*    */       return; 
/* 54 */     this.aliveTicks++;
/* 55 */     if (this.timer.passedMs(5L)) {
/* 56 */       this.speed += 0.5D;
/* 57 */       this.timer.reset();
/*    */     } 
/* 59 */     if (this.speed > ((Integer)this.riseSpeed.getValue()).intValue()) {
/* 60 */       this.speed = 0.0D;
/* 61 */       this.explodedCrystals.clear();
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/*    */     try {
/* 68 */       for (Entity crystal : mc.world.loadedEntityList) {
/* 69 */         if (crystal instanceof EntityEnderCrystal && event.getPacket() instanceof net.minecraft.network.play.server.SPacketExplosion) {
/* 70 */           this.crystalPos = new BlockPos(crystal.posX, crystal.posY, crystal.posZ);
/* 71 */           this.explodedCrystals.put((EntityEnderCrystal)crystal, this.crystalPos);
/*    */         } 
/*    */       } 
/* 74 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender3D(Render3DEvent event) {
/* 80 */     if (!this.explodedCrystals.isEmpty()) {
/* 81 */       Color color = ColorUtil.getPrimaryColor();
/* 82 */       RenderUtil.prepareGL3D();
/* 83 */       RenderUtil.drawCircle(this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0F + 0.7F, this.crystalPos.getZ(), 0.6F + ((Float)this.increase.getValue()).floatValue(), color);
/* 84 */       RenderUtil.drawCircle(this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0F + 0.6F, this.crystalPos.getZ(), 0.5F + ((Float)this.increase.getValue()).floatValue(), color);
/* 85 */       RenderUtil.drawCircle(this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0F + 0.5F, this.crystalPos.getZ(), 0.4F + ((Float)this.increase.getValue()).floatValue(), color);
/* 86 */       RenderUtil.drawCircle(this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0F + 0.4F, this.crystalPos.getZ(), 0.3F + ((Float)this.increase.getValue()).floatValue(), color);
/* 87 */       RenderUtil.drawCircle(this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0F + 0.3F, this.crystalPos.getZ(), 0.2F + ((Float)this.increase.getValue()).floatValue(), color);
/* 88 */       RenderUtil.drawCircle(this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0F + 0.2F, this.crystalPos.getZ(), 0.1F + ((Float)this.increase.getValue()).floatValue(), color);
/* 89 */       RenderUtil.drawCircle(this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0F + 0.1F, this.crystalPos.getZ(), 0.0F + ((Float)this.increase.getValue()).floatValue(), color);
/* 90 */       RenderUtil.releaseGL3D();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\ExplosionChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
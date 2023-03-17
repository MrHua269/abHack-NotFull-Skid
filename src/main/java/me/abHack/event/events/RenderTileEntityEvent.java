/*    */ package me.abHack.event.events;
/*    */ 
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class RenderTileEntityEvent
/*    */   extends Event
/*    */ {
/*    */   private final TileEntity tileEntity;
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   private final float partialTicks;
/*    */   private final int destroyStage;
/*    */   private final float partial;
/*    */   private final Tessellator buffer;
/*    */   
/*    */   public RenderTileEntityEvent(TileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float partial, Tessellator buffer) {
/* 29 */     this.tileEntity = tileEntity;
/* 30 */     this.x = x;
/* 31 */     this.y = y;
/* 32 */     this.z = z;
/* 33 */     this.partialTicks = partialTicks;
/* 34 */     this.destroyStage = destroyStage;
/* 35 */     this.partial = partial;
/* 36 */     this.buffer = buffer;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 40 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 44 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 48 */     return this.z;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 52 */     return this.partialTicks;
/*    */   }
/*    */   
/*    */   public int getDestroyStage() {
/* 56 */     return this.destroyStage;
/*    */   }
/*    */   
/*    */   public float getPartial() {
/* 60 */     return this.partial;
/*    */   }
/*    */   
/*    */   public Tessellator getBuffer() {
/* 64 */     return this.buffer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity getTileEntity() {
/* 73 */     return this.tileEntity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\event\events\RenderTileEntityEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
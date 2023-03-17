/*    */ package me.abHack.features.modules.combat;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.manager.BreakManager;
/*    */ import me.abHack.util.BlockUtil;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class TrapSelf extends Module {
/* 16 */   private final Setting<Boolean> smart = register(new Setting("Smart", Boolean.valueOf(true)));
/* 17 */   private final Setting<Boolean> breakcrystal = register(new Setting("Break Crystal", Boolean.valueOf(true)));
/* 18 */   private final Setting<Boolean> center = register(new Setting("TPCenter", Boolean.valueOf(true)));
/* 19 */   private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(false)));
/* 20 */   private final Setting<Boolean> toggle = register(new Setting("Toggle", Boolean.valueOf(false)));
/*    */   private int obsidian;
/*    */   
/*    */   public TrapSelf() {
/* 24 */     super("TrapSelf", "One Self Trap", Module.Category.COMBAT, true, false, false);
/* 25 */     this.obsidian = -1;
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 29 */     BlockPos startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
/* 30 */     if (((Boolean)this.center.getValue()).booleanValue()) {
/* 31 */       OyVey.positionManager.setPositionPacket(startPos.getX() + 0.5D, startPos.getY(), startPos.getZ() + 0.5D, true, true, true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 37 */     if (fullNullCheck())
/*    */       return; 
/* 39 */     this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
/* 40 */     if (this.obsidian == -1)
/*    */       return; 
/* 42 */     BlockPos pos = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/*    */ 
/*    */ 
/*    */     
/* 46 */     place(pos.add(1, 0, 0));
/*    */     
/* 48 */     place(pos.add(1, 1, 0));
/*    */     
/* 50 */     place(pos.add(1, 2, 0));
/*    */ 
/*    */     
/* 53 */     place(pos.add(-1, 0, 0));
/*    */     
/* 55 */     place(pos.add(-1, 1, 0));
/*    */     
/* 57 */     place(pos.add(-1, 2, 0));
/*    */ 
/*    */     
/* 60 */     place(pos.add(0, 0, 1));
/*    */     
/* 62 */     place(pos.add(0, 1, 1));
/*    */     
/* 64 */     place(pos.add(0, 2, 1));
/*    */ 
/*    */     
/* 67 */     place(pos.add(0, 0, -1));
/*    */     
/* 69 */     place(pos.add(0, 1, -1));
/*    */     
/* 71 */     place(pos.add(0, 2, -1));
/*    */ 
/*    */     
/* 74 */     place(pos.add(0, 2, 0));
/*    */     
/* 76 */     if (((Boolean)this.toggle.getValue()).booleanValue() || OyVey.speedManager.getPlayerSpeed((EntityPlayer)mc.player) > 10.0D) {
/* 77 */       toggle();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void place(BlockPos pos) {
/* 84 */     if ((((Boolean)this.smart.getValue()).booleanValue() && BreakManager.isMine(pos, true)) || BlockUtil.PlayerCheck(pos) || !BlockUtil.CanPlace(pos))
/*    */       return; 
/* 86 */     if (((Boolean)this.breakcrystal.getValue()).booleanValue() && BlockUtil.CrystalCheck(pos))
/* 87 */       Surround.breakcrystal(true); 
/* 88 */     int old = mc.player.inventory.currentItem;
/* 89 */     InventoryUtil.switchToSlot(this.obsidian);
/* 90 */     BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
/* 91 */     InventoryUtil.switchToSlot(old);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\TrapSelf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
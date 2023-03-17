/*     */ package me.abHack.features.modules.combat;
/*     */ 
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AntiPiston
/*     */   extends Module
/*     */ {
/*  23 */   public Setting<Boolean> crystal = register(new Setting("Crystal", Boolean.valueOf(false)));
/*  24 */   public Setting<Boolean> push = register(new Setting("AntiPush", Boolean.valueOf(true)));
/*  25 */   int obsidian = -1;
/*     */   
/*     */   public AntiPiston() {
/*  28 */     super("AntiPiston", "Anti push/pistonCrystal", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  33 */     if (((Boolean)this.crystal.getValue()).booleanValue())
/*  34 */       blockPiston(); 
/*  35 */     if (((Boolean)this.push.getValue()).booleanValue()) {
/*  36 */       AntiPush();
/*     */     }
/*     */   }
/*     */   
/*     */   private void blockPiston() {
/*  41 */     for (Entity t : mc.world.loadedEntityList) {
/*     */       
/*  43 */       if (t instanceof net.minecraft.entity.item.EntityEnderCrystal && t.posX >= mc.player.posX - 1.5D && t.posX <= mc.player.posX + 1.5D && t.posZ >= mc.player.posZ - 1.5D && t.posZ <= mc.player.posZ + 1.5D)
/*     */       {
/*     */ 
/*     */         
/*  47 */         for (int i = -2; i < 3; i++) {
/*  48 */           for (int j = -2; j < 3; j++) {
/*     */ 
/*     */             
/*  51 */             if (getblock(new BlockPos(t.posX + i, t.posY, t.posZ + j)) instanceof net.minecraft.block.BlockPistonBase)
/*     */             {
/*  53 */               EntityUtil.attackEntity(t, true);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void AntiPush() {
/*  63 */     this.obsidian = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/*  64 */     if (this.obsidian == -1)
/*     */       return; 
/*  66 */     if (OyVey.speedManager.getPlayerSpeed((EntityPlayer)mc.player) > 12.0D)
/*     */       return; 
/*  68 */     BlockPos player = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/*     */     
/*  70 */     if (getblock(player.add(1, 1, 0)) == Blocks.PISTON || RedStoneCheck(player.add(1, 1, 0))) {
/*  71 */       place(player.add(0, 2, 0));
/*  72 */       place(player.add(-1, 1, 0));
/*  73 */     } else if (getblock(player.add(-1, 1, 0)) == Blocks.PISTON || RedStoneCheck(player.add(-1, 1, 0))) {
/*  74 */       place(player.add(0, 2, 0));
/*  75 */       place(player.add(1, 1, 0));
/*  76 */     } else if (getblock(player.add(0, 1, 1)) == Blocks.PISTON || RedStoneCheck(player.add(0, 1, 1))) {
/*  77 */       place(player.add(0, 2, 0));
/*  78 */       place(player.add(0, 1, -1));
/*  79 */     } else if (getblock(player.add(0, 1, -1)) == Blocks.PISTON || RedStoneCheck(player.add(0, 1, -1))) {
/*  80 */       place(player.add(0, 2, 0));
/*  81 */       place(player.add(0, 1, 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean RedStoneCheck(BlockPos block) {
/*  88 */     return (RedStone(block.add(0, 1, 0)) || RedStone(block.add(0, -1, 0)) || RedStone(block.add(1, 0, 0)) || RedStone(block.add(-1, 0, 0)) || RedStone(block.add(0, 0, 1)) || RedStone(block.add(0, 0, -1)));
/*     */   }
/*     */   
/*     */   private boolean RedStone(BlockPos block) {
/*  92 */     return (getblock(block) == Blocks.REDSTONE_BLOCK || getblock(block) == Blocks.REDSTONE_TORCH);
/*     */   }
/*     */   
/*     */   private void place(BlockPos pos) {
/*  96 */     if (!BlockUtil.EntityCheck(pos) && !BreakManager.isMine(pos, true) && BlockUtil.CanPlace(pos)) {
/*  97 */       int old = mc.player.inventory.currentItem;
/*  98 */       InventoryUtil.switchToSlot(this.obsidian);
/*  99 */       BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, false, true, false);
/* 100 */       InventoryUtil.switchToSlot(old);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Block getblock(BlockPos pos) {
/* 107 */     return mc.world.getBlockState(pos).getBlock();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AntiPiston.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
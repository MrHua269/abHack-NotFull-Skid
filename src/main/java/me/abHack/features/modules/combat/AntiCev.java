/*    */ package me.abHack.features.modules.combat;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.BlockUtil;
/*    */ import me.abHack.util.EntityUtil;
/*    */ import me.abHack.util.InventoryUtil;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockObsidian;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class AntiCev
/*    */   extends Module {
/* 22 */   private final Setting<Boolean> face = register(new Setting("Face", Boolean.valueOf(true)));
/* 23 */   private final Setting<Boolean> feet = register(new Setting("Feet", Boolean.valueOf(true)));
/* 24 */   private final Setting<Boolean> security = register(new Setting("SecurityMode", Boolean.valueOf(true), v -> ((Boolean)this.feet.getValue()).booleanValue()));
/* 25 */   private final List<Block> cevBlocks = Arrays.asList(new Block[] { Blocks.AIR, Blocks.OBSIDIAN });
/*    */   
/*    */   public AntiCev() {
/* 28 */     super("AntiCev", "AntiCev", Module.Category.COMBAT, true, false, false);
/*    */   }
/*    */   
/*    */   public void onTick() {
/* 32 */     if (mc.player == null)
/*    */       return; 
/* 34 */     int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 35 */     int old = mc.player.inventory.currentItem;
/* 36 */     if (obbySlot == -1)
/*    */       return; 
/* 38 */     BlockPos player = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/* 39 */     for (Entity crystal : mc.world.loadedEntityList.stream().filter(e -> (e instanceof net.minecraft.entity.item.EntityEnderCrystal && !e.isDead)).sorted(Comparator.comparing(e -> Float.valueOf(mc.player.getDistance(e)))).collect(Collectors.toList())) {
/* 40 */       BlockPos crystalPos = new BlockPos(crystal.posX, crystal.posY, crystal.posZ);
/* 41 */       if (this.cevBlocks.contains(BlockUtil.getBlock(crystalPos.add(0, -1, 0))) && ((Boolean)this.face.getValue()).booleanValue() && (crystalPos.equals(player.add(1, 2, 0)) || crystalPos.equals(player.add(-1, 2, 0)) || crystalPos.equals(player.add(0, 2, 1)) || crystalPos.equals(player.add(0, 2, -1)))) {
/* 42 */         EntityUtil.attackEntity(crystal, true);
/* 43 */         InventoryUtil.switchToSlot(obbySlot);
/* 44 */         BlockUtil.placeBlock(crystalPos.add(0, -1, 0), EnumHand.MAIN_HAND, false, true, false);
/* 45 */         BlockUtil.placeBlock(crystalPos, EnumHand.MAIN_HAND, false, true, false);
/* 46 */         InventoryUtil.switchToSlot(old);
/*    */       } 
/*    */       
/* 49 */       if (BlockUtil.isAir(crystalPos.add(0, -1, 0)) && (crystalPos.equals(player.add(0, 3, 0)) || crystalPos.equals(player.add(0, 4, 0)) || crystalPos.equals(player.add(0, 5, 0)))) {
/* 50 */         EntityUtil.attackEntity(crystal, true);
/* 51 */         InventoryUtil.switchToSlot(obbySlot);
/* 52 */         BlockUtil.placeBlock(crystalPos, EnumHand.MAIN_HAND, false, true, false);
/* 53 */         BlockUtil.placeBlock(crystalPos.add(0, -1, 0), EnumHand.MAIN_HAND, false, true, false);
/* 54 */         InventoryUtil.switchToSlot(old);
/* 55 */         if (BlockUtil.isAir(crystalPos.add(1, 0, 0)) && BlockUtil.isAir(crystalPos.add(-1, 0, 0)) && BlockUtil.isAir(crystalPos.add(0, 0, 1)) && BlockUtil.isAir(crystalPos.add(0, 0, -1))) {
/* 56 */           if (BlockUtil.CanPlace(crystalPos.add(1, 0, 0))) {
/* 57 */             InventoryUtil.switchToSlot(obbySlot);
/* 58 */             BlockUtil.placeBlock(crystalPos.add(1, 0, 0), EnumHand.MAIN_HAND, false, true, false);
/* 59 */             InventoryUtil.switchToSlot(old);
/* 60 */           } else if (BlockUtil.CanPlace(crystalPos.add(-1, 0, 0))) {
/* 61 */             InventoryUtil.switchToSlot(obbySlot);
/* 62 */             BlockUtil.placeBlock(crystalPos.add(-1, 0, 0), EnumHand.MAIN_HAND, false, true, false);
/* 63 */             InventoryUtil.switchToSlot(old);
/* 64 */           } else if (BlockUtil.CanPlace(crystalPos.add(0, 0, 1))) {
/* 65 */             InventoryUtil.switchToSlot(obbySlot);
/* 66 */             BlockUtil.placeBlock(crystalPos.add(0, 0, 1), EnumHand.MAIN_HAND, false, true, false);
/* 67 */             InventoryUtil.switchToSlot(old);
/* 68 */           } else if (BlockUtil.CanPlace(crystalPos.add(0, 0, -1))) {
/* 69 */             InventoryUtil.switchToSlot(obbySlot);
/* 70 */             BlockUtil.placeBlock(crystalPos.add(0, 0, -1), EnumHand.MAIN_HAND, false, true, false);
/* 71 */             InventoryUtil.switchToSlot(old);
/*    */           } 
/*    */         }
/*    */       } 
/* 75 */       if (((this.cevBlocks.contains(BlockUtil.getBlock(crystalPos.add(0, -1, 0))) && !((Boolean)this.security.getValue()).booleanValue()) || safe(crystalPos.add(0, -1, 0))) && ((Boolean)this.feet.getValue()).booleanValue() && (crystalPos.equals(player.add(1, 1, 0)) || crystalPos.equals(player.add(-1, 1, 0)) || crystalPos.equals(player.add(0, 1, 1)) || crystalPos.equals(player.add(0, 1, -1)))) {
/* 76 */         EntityUtil.attackEntity(crystal, true);
/* 77 */         InventoryUtil.switchToSlot(obbySlot);
/* 78 */         BlockUtil.placeBlock(crystalPos, EnumHand.MAIN_HAND, false, true, false);
/* 79 */         InventoryUtil.switchToSlot(old);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean safe(BlockPos pos) {
/* 88 */     return ((BlockUtil.getBlock(pos) == Blocks.OBSIDIAN || BlockUtil.getBlock(pos) == Blocks.BEDROCK) && mc.player.getHealth() <= 8.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AntiCev.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.combat;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.server.SPacketBlockBreakAnim;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AntiCity extends Module {
/*  21 */   private final Setting<Double> range = register(new Setting("Range", Double.valueOf(5.0D), Double.valueOf(1.0D), Double.valueOf(10.0D)));
/*  22 */   private final Setting<Boolean> smart = register(new Setting("Smart", Boolean.valueOf(false)));
/*  23 */   private final Setting<Boolean> breakcrystal = register(new Setting("Break Crystal", Boolean.valueOf(true)));
/*     */   BlockPos wudi;
/*  25 */   private int obsidian = -1;
/*     */   private int placeab;
/*     */   
/*     */   public AntiCity() {
/*  29 */     super("AntiCity", "AntiCity", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */   
/*     */   public static boolean noHard(Block block) {
/*  33 */     return (block != Blocks.BEDROCK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  38 */     if (fullNullCheck())
/*     */       return; 
/*  40 */     if (OyVey.speedManager.getPlayerSpeed((EntityPlayer)mc.player) >= 15.0D)
/*     */       return; 
/*  42 */     this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
/*  43 */     if (this.obsidian == -1) {
/*     */       return;
/*     */     }
/*  46 */     BlockPos pos = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/*  47 */     if (getTarget(((Double)this.range.getValue()).doubleValue()) == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  52 */     if (this.wudi != null) {
/*  53 */       if ((this.wudi.equals(pos.add(1, 0, 0)) || this.wudi.equals(pos.add(1, 1, 0))) && 
/*  54 */         isAir(pos.add(1, 0, 0)) && isAir(pos.add(1, 1, 0))) {
/*  55 */         if (this.wudi.equals(pos.add(1, 0, 0))) {
/*  56 */           perform(pos.add(1, 1, 0));
/*     */         } else {
/*  58 */           perform(pos.add(1, 0, 0));
/*     */         } 
/*     */       }
/*     */       
/*  62 */       if ((this.wudi.equals(pos.add(-1, 0, 0)) || this.wudi.equals(pos.add(-1, 1, 0))) && 
/*  63 */         isAir(pos.add(-1, 0, 0)) && isAir(pos.add(-1, 1, 0))) {
/*  64 */         if (this.wudi.equals(pos.add(-1, 0, 0))) {
/*  65 */           perform(pos.add(-1, 1, 0));
/*     */         } else {
/*  67 */           perform(pos.add(-1, 0, 0));
/*     */         } 
/*     */       }
/*  70 */       if ((this.wudi.equals(pos.add(0, 0, 1)) || this.wudi.equals(pos.add(0, 1, 1))) && 
/*  71 */         isAir(pos.add(0, 0, 1)) && isAir(pos.add(0, 1, 1))) {
/*  72 */         if (this.wudi.equals(pos.add(0, 0, 1))) {
/*  73 */           perform(pos.add(0, 1, 1));
/*     */         } else {
/*  75 */           perform(pos.add(0, 0, 1));
/*     */         } 
/*     */       }
/*  78 */       if ((this.wudi.equals(pos.add(0, 0, -1)) || this.wudi.equals(pos.add(0, 1, -1))) && 
/*  79 */         isAir(pos.add(0, 0, -1)) && isAir(pos.add(0, 1, -1))) {
/*  80 */         if (this.wudi.equals(pos.add(0, 0, -1))) {
/*  81 */           perform(pos.add(0, 1, -1));
/*     */         } else {
/*  83 */           perform(pos.add(0, 0, -1));
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (noHard(getBlock(pos.add(1, 0, 0)).getBlock())) {
/*  91 */       if (this.placeab == 1) {
/*  92 */         perform(pos.add(2, 0, 0));
/*  93 */         perform(pos.add(1, 0, 1));
/*  94 */         perform(pos.add(1, 0, -1));
/*  95 */         perform(pos.add(1, 1, 0));
/*  96 */         if (BlockUtil.EntityCheck(pos.add(2, 0, 0))) {
/*  97 */           perform(pos.add(3, 0, 0));
/*  98 */           perform(pos.add(3, 1, 0));
/*     */         } 
/*     */       } 
/* 101 */       if (this.placeab == 5) {
/* 102 */         perform(pos.add(1, 0, 0));
/* 103 */         perform(pos.add(2, 1, 0));
/* 104 */         perform(pos.add(3, 0, 0));
/*     */       } 
/*     */       
/* 107 */       if (this.placeab == 9) {
/* 108 */         perform(pos.add(1, 0, 0));
/* 109 */         perform(pos.add(2, 1, 0));
/*     */       } 
/*     */       
/* 112 */       if (this.placeab == 13 || this.placeab == 14) {
/* 113 */         perform(pos.add(1, 0, 0));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (noHard(getBlock(pos.add(-1, 0, 0)).getBlock())) {
/*     */       
/* 122 */       if (this.placeab == 2) {
/* 123 */         perform(pos.add(-2, 0, 0));
/* 124 */         perform(pos.add(-1, 0, 1));
/* 125 */         perform(pos.add(-1, 0, -1));
/* 126 */         perform(pos.add(-1, 1, 0));
/* 127 */         if (BlockUtil.EntityCheck(pos.add(-2, 0, 0))) {
/* 128 */           perform(pos.add(-3, 0, 0));
/* 129 */           perform(pos.add(-3, 1, 0));
/*     */         } 
/*     */       } 
/*     */       
/* 133 */       if (this.placeab == 6) {
/* 134 */         perform(pos.add(-1, 0, 0));
/* 135 */         perform(pos.add(-2, 1, 0));
/* 136 */         perform(pos.add(-3, 0, 0));
/*     */       } 
/* 138 */       if (this.placeab == 10) {
/* 139 */         perform(pos.add(-1, 0, 0));
/* 140 */         perform(pos.add(-2, 1, 0));
/*     */       } 
/*     */       
/* 143 */       if (this.placeab == 15 || this.placeab == 16) {
/* 144 */         perform(pos.add(-1, 0, 0));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (noHard(getBlock(pos.add(0, 0, 1)).getBlock())) {
/* 151 */       if (this.placeab == 3) {
/* 152 */         perform(pos.add(0, 0, 2));
/* 153 */         perform(pos.add(1, 0, 1));
/* 154 */         perform(pos.add(-1, 0, 1));
/* 155 */         perform(pos.add(0, 1, 1));
/* 156 */         if (BlockUtil.EntityCheck(pos.add(0, 0, 2))) {
/* 157 */           perform(pos.add(0, 0, 3));
/* 158 */           perform(pos.add(0, 1, 3));
/*     */         } 
/*     */       } 
/*     */       
/* 162 */       if (this.placeab == 7) {
/* 163 */         perform(pos.add(0, 0, 1));
/* 164 */         perform(pos.add(0, 1, 2));
/* 165 */         perform(pos.add(0, 0, 3));
/*     */       } 
/* 167 */       if (this.placeab == 11) {
/* 168 */         perform(pos.add(0, 0, 1));
/* 169 */         perform(pos.add(0, 1, 2));
/*     */       } 
/*     */       
/* 172 */       if (this.placeab == 13 || this.placeab == 15) {
/* 173 */         perform(pos.add(0, 0, 1));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 179 */     if (noHard(getBlock(pos.add(0, 0, -1)).getBlock())) {
/* 180 */       if (this.placeab == 4) {
/* 181 */         perform(pos.add(0, 0, -2));
/* 182 */         perform(pos.add(1, 0, -1));
/* 183 */         perform(pos.add(-1, 0, -1));
/* 184 */         perform(pos.add(0, 1, -1));
/* 185 */         if (BlockUtil.EntityCheck(pos.add(0, 0, -2))) {
/* 186 */           perform(pos.add(0, 0, -3));
/* 187 */           perform(pos.add(0, 1, -3));
/*     */         } 
/*     */       } 
/*     */       
/* 191 */       if (this.placeab == 8) {
/* 192 */         perform(pos.add(0, 0, -1));
/* 193 */         perform(pos.add(0, 1, -2));
/* 194 */         perform(pos.add(0, 0, -3));
/*     */       } 
/*     */       
/* 197 */       if (this.placeab == 12) {
/* 198 */         perform(pos.add(0, 0, -1));
/* 199 */         perform(pos.add(0, 1, -2));
/*     */       } 
/*     */       
/* 202 */       if (this.placeab == 14 || this.placeab == 16) {
/* 203 */         perform(pos.add(0, 0, -1));
/*     */       }
/*     */     } 
/*     */     
/* 207 */     this.placeab = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacket(PacketEvent.Receive event) {
/* 213 */     if (event.getPacket() instanceof SPacketBlockBreakAnim) {
/*     */       
/* 215 */       SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim)event.getPacket();
/* 216 */       BlockPos ab = packet.getPosition();
/* 217 */       this.wudi = packet.getPosition();
/* 218 */       BlockPos player = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/* 219 */       if (ab.equals(player.add(1, 0, 0)))
/* 220 */         this.placeab = 1; 
/* 221 */       if (ab.equals(player.add(-1, 0, 0)))
/* 222 */         this.placeab = 2; 
/* 223 */       if (ab.equals(player.add(0, 0, 1)))
/* 224 */         this.placeab = 3; 
/* 225 */       if (ab.equals(player.add(0, 0, -1))) {
/* 226 */         this.placeab = 4;
/*     */       }
/* 228 */       if (ab.equals(player.add(2, 0, 0)))
/* 229 */         this.placeab = 5; 
/* 230 */       if (ab.equals(player.add(-2, 0, 0)))
/* 231 */         this.placeab = 6; 
/* 232 */       if (ab.equals(player.add(0, 0, 2)))
/* 233 */         this.placeab = 7; 
/* 234 */       if (ab.equals(player.add(0, 0, -2))) {
/* 235 */         this.placeab = 8;
/*     */       }
/* 237 */       if (ab.equals(player.add(1, 1, 0)))
/* 238 */         this.placeab = 9; 
/* 239 */       if (ab.equals(player.add(-1, 1, 0)))
/* 240 */         this.placeab = 10; 
/* 241 */       if (ab.equals(player.add(0, 1, 1)))
/* 242 */         this.placeab = 11; 
/* 243 */       if (ab.equals(player.add(0, 1, -1))) {
/* 244 */         this.placeab = 12;
/*     */       }
/* 246 */       if (ab.equals(player.add(1, 0, 1)))
/* 247 */         this.placeab = 13; 
/* 248 */       if (ab.equals(player.add(1, 0, -1)))
/* 249 */         this.placeab = 14; 
/* 250 */       if (ab.equals(player.add(-1, 0, 1)))
/* 251 */         this.placeab = 15; 
/* 252 */       if (ab.equals(player.add(-1, 0, -1))) {
/* 253 */         this.placeab = 16;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState getBlock(BlockPos block) {
/* 260 */     return mc.world.getBlockState(block);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void perform(BlockPos pos) {
/* 266 */     if (BlockUtil.PlayerCheck(pos) || !BlockUtil.CanPlace(pos) || (((Boolean)this.smart.getValue()).booleanValue() && BreakManager.isMine(pos, true)))
/*     */       return; 
/* 268 */     if (((Boolean)this.breakcrystal.getValue()).booleanValue() && BlockUtil.CrystalCheck(pos))
/* 269 */       Surround.breakcrystal(true); 
/* 270 */     int old = mc.player.inventory.currentItem;
/* 271 */     InventoryUtil.switchToSlot(this.obsidian);
/* 272 */     BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, false, true, false);
/* 273 */     InventoryUtil.switchToSlot(old);
/*     */   }
/*     */   
/*     */   private boolean isAir(BlockPos block) {
/* 277 */     return (mc.world.getBlockState(block).getBlock() == Blocks.AIR);
/*     */   }
/*     */   
/*     */   private EntityPlayer getTarget(double range) {
/* 281 */     EntityPlayer target = null;
/* 282 */     double distance = Math.pow(range, 2.0D) + 1.0D;
/* 283 */     for (EntityPlayer player : AutoTrap.mc.world.playerEntities) {
/* 284 */       if (EntityUtil.isntValid((Entity)player, range) || OyVey.speedManager.getPlayerSpeed(player) > 10.0D)
/*     */         continue; 
/* 286 */       if (target == null) {
/* 287 */         target = player;
/* 288 */         distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
/*     */         continue;
/*     */       } 
/* 291 */       if (AutoTrap.mc.player.getDistanceSq((Entity)player) >= distance)
/*     */         continue; 
/* 293 */       target = player;
/* 294 */       distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
/*     */     } 
/* 296 */     return target;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\AntiCity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
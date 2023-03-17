/*     */ package me.abHack.features.modules.player;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.Color;
/*     */ import java.util.Comparator;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.BlockEvent;
/*     */ import me.abHack.event.events.PlayerDamageBlockEvent;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.client.ClickGui;
/*     */ import me.abHack.features.modules.combat.AutoCev;
/*     */ import me.abHack.features.setting.Bind;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.manager.BreakManager;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import me.abHack.util.abUtil;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class InstantMine extends Module {
/*     */   public static BlockPos breakPos;
/*  48 */   public final Setting<Boolean> crystal = register(new Setting("Crystal", Boolean.valueOf(false))); public static InstantMine INSTANCE;
/*  49 */   public final Setting<Boolean> attackcrystal = register(new Setting("Attack Crystal", Boolean.valueOf(false), v -> ((Boolean)this.crystal.getValue()).booleanValue()));
/*  50 */   public final Setting<Boolean> only_autoCev = register(new Setting("Only AutoCev", Boolean.valueOf(false), v -> ((Boolean)this.crystal.getValue()).booleanValue()));
/*  51 */   public final Setting<Bind> bind = register(new Setting("ObsidianBind", new Bind(-1), v -> ((Boolean)this.crystal.getValue()).booleanValue()));
/*  52 */   public final Timer timer = new Timer();
/*  53 */   private final Setting<Boolean> ghostHand = register(new Setting("GhostHand", Boolean.valueOf(true)));
/*  54 */   private final Setting<Boolean> doulebreak = register(new Setting("DouleBreak", Boolean.valueOf(false)));
/*  55 */   private final Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true)));
/*  56 */   private final Setting<Boolean> rainbow = register(new Setting("Rainbow", Boolean.valueOf(true), v -> ((Boolean)this.render.getValue()).booleanValue()));
/*  57 */   private final Setting<Mode> rendermode = register(new Setting("Render Mode", Mode.Fill, v -> ((Boolean)this.render.getValue()).booleanValue()));
/*  58 */   private final Setting<Integer> fillAlpha = register(new Setting("Fill Alpha", Integer.valueOf(80), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.render.getValue()).booleanValue() && this.rendermode.getValue() == Mode.Fill)));
/*  59 */   private final Setting<Integer> boxAlpha = register(new Setting("Box Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.render.getValue()).booleanValue() && this.rendermode.getValue() == Mode.Box)));
/*  60 */   private final Set<Block> godBlocks = Sets.newHashSet(new Block[] { Blocks.AIR, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, Blocks.BEDROCK });
/*  61 */   private final Timer breakSuccess = new Timer();
/*  62 */   private final Timer Rendertimer = new Timer();
/*  63 */   public float breakTime = -1.0F;
/*     */   public IBlockState currentBlockState;
/*     */   double manxi;
/*     */   private BlockPos currentPos;
/*     */   private IBlockState breakState;
/*     */   private EnumFacing facing;
/*     */   private boolean cancelStart = false;
/*     */   private boolean empty = false;
/*     */   
/*     */   public InstantMine() {
/*  73 */     super("InstantMine", "InstantMine", Module.Category.PLAYER, true, false, false);
/*  74 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void attackcrystal() {
/*  79 */     for (Entity crystal : mc.world.loadedEntityList.stream().filter(e -> (e instanceof net.minecraft.entity.item.EntityEnderCrystal && !e.isDead)).sorted(Comparator.comparing(e -> Float.valueOf(mc.player.getDistance(e)))).collect(Collectors.toList())) {
/*  80 */       BlockPos crystalPos = new BlockPos(crystal.posX, crystal.posY, crystal.posZ);
/*  81 */       if (crystal instanceof net.minecraft.entity.item.EntityEnderCrystal && crystalPos.equals(breakPos.add(0, 1, 0))) {
/*  82 */         mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
/*  83 */         mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  90 */     if (fullNullCheck())
/*  91 */       return;  if (mc.player.capabilities.isCreativeMode)
/*  92 */       return;  if (this.cancelStart) {
/*  93 */       if (AutoCev.INSTANCE.isOn())
/*  94 */         this.crystal.setValue(Boolean.valueOf(true)); 
/*  95 */       if (((Boolean)this.only_autoCev.getValue()).booleanValue() && AutoCev.INSTANCE.isOff())
/*  96 */         this.crystal.setValue(Boolean.valueOf(false)); 
/*  97 */       if (((Boolean)this.crystal.getValue()).booleanValue()) {
/*  98 */         if (((Boolean)this.attackcrystal.getValue()).booleanValue() && abUtil.isAir(breakPos))
/*  99 */           attackcrystal(); 
/* 100 */         if (((Bind)this.bind.getValue()).isDown() && InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1 && abUtil.isAir(breakPos) && abUtil.CanPlaceCrystal(breakPos) && (
/* 101 */           mc.currentScreen == null || mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
/* 102 */           int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 103 */           int old = mc.player.inventory.currentItem;
/* 104 */           InventoryUtil.switchToSlot(obbySlot);
/* 105 */           BlockUtil.placeBlock(breakPos, EnumHand.MAIN_HAND, false, !BreakManager.isMine(breakPos, false), false);
/* 106 */           InventoryUtil.switchToSlot(old);
/*     */         } 
/*     */         
/* 109 */         if (InventoryUtil.getItemHotbar(Items.END_CRYSTAL) != -1 && mc.world.getBlockState(breakPos).getBlock() == Blocks.OBSIDIAN && !check() && abUtil.CanPlaceCrystal(breakPos)) {
/* 110 */           int old = mc.player.inventory.currentItem;
/* 111 */           int crystal = InventoryUtil.getItemHotbar(Items.END_CRYSTAL);
/* 112 */           InventoryUtil.switchToSlot(crystal);
/* 113 */           BlockUtil.placeCrystalOnBlock(breakPos, EnumHand.MAIN_HAND);
/* 114 */           InventoryUtil.switchToSlot(old);
/*     */         } 
/*     */       } 
/*     */       
/* 118 */       if (this.godBlocks.contains(mc.world.getBlockState(breakPos).getBlock()) && ((Boolean)this.crystal.getValue()).booleanValue() && check2()) {
/*     */         return;
/*     */       }
/* 121 */       if (((Boolean)this.ghostHand.getValue()).booleanValue()) {
/* 122 */         int slotMain = mc.player.inventory.currentItem;
/*     */ 
/*     */         
/* 125 */         IBlockState checkState = getGoodBlockState(breakPos);
/* 126 */         int toolSlot = getBestAvailableToolSlot(checkState);
/*     */         
/* 128 */         if (toolSlot != -1) {
/* 129 */           float breakTime = mc.world.getBlockState(breakPos).getBlockHardness((World)mc.world, breakPos);
/* 130 */           if (this.breakSuccess.passedMs((long)breakTime)) {
/* 131 */             InventoryUtil.switchToSlot(toolSlot);
/* 132 */             mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
/* 133 */             InventoryUtil.switchToSlot(slotMain);
/*     */           } 
/*     */         } else {
/* 136 */           mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
/*     */         } 
/*     */       } else {
/*     */         
/* 140 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 147 */     if (fullNullCheck())
/*     */       return; 
/* 149 */     if (this.currentPos != null && ((Boolean)this.doulebreak.getValue()).booleanValue()) {
/*     */       
/* 151 */       int slot = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
/* 152 */       if (((Boolean)this.doulebreak.getValue()).booleanValue() && this.timer.passedMs((int)(2000.0F * OyVey.serverManager.getTpsFactor())) && slot != -1)
/* 153 */         mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot)); 
/* 154 */       if (((Boolean)this.doulebreak.getValue()).booleanValue() && this.timer.passedMs((int)(2200.0F * OyVey.serverManager.getTpsFactor()))) {
/* 155 */         int oldSlot = mc.player.inventory.currentItem;
/* 156 */         mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldSlot));
/* 157 */         mc.playerController.updateController();
/*     */       } 
/* 159 */       if (!mc.world.getBlockState(this.currentPos).equals(this.currentBlockState) || mc.world.getBlockState(this.currentPos).getBlock() == Blocks.AIR) {
/* 160 */         this.currentPos = null;
/* 161 */         this.currentBlockState = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 167 */     if (fullNullCheck())
/*     */       return; 
/* 169 */     if (((Boolean)this.render.getValue()).booleanValue() && this.cancelStart) {
/* 170 */       if (this.godBlocks.contains(mc.world.getBlockState(breakPos).getBlock())) {
/* 171 */         this.empty = true;
/*     */       }
/*     */       
/* 174 */       float RenderTime = mc.world.getBlockState(breakPos).getBlockHardness((World)mc.world, breakPos) / 5.0F;
/*     */       
/* 176 */       if (mc.world.getBlockState(breakPos).getBlock() == Blocks.OBSIDIAN) {
/* 177 */         RenderTime = 11.0F;
/*     */       }
/*     */       
/* 180 */       if (this.Rendertimer.passedMs((int)RenderTime)) {
/* 181 */         if (this.manxi <= 10.0D) {
/* 182 */           this.manxi += 0.11D;
/*     */         }
/* 184 */         this.Rendertimer.reset();
/*     */       } 
/*     */       
/* 187 */       AxisAlignedBB axisAlignedBB = mc.world.getBlockState(breakPos).getSelectedBoundingBox((World)mc.world, breakPos);
/*     */       
/* 189 */       double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0D;
/* 190 */       double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0D;
/* 191 */       double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0D;
/*     */       
/* 193 */       double progressValX = this.manxi * (axisAlignedBB.maxX - centerX) / 10.0D;
/* 194 */       double progressValY = this.manxi * (axisAlignedBB.maxY - centerY) / 10.0D;
/* 195 */       double progressValZ = this.manxi * (axisAlignedBB.maxZ - centerZ) / 10.0D;
/*     */       
/* 197 */       AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
/* 198 */       Color color = ((Boolean)this.rainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()) : new Color(this.empty ? 0 : 255, this.empty ? 255 : 0, 0, 255);
/* 199 */       Color color2 = ((Boolean)this.rainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()) : new Color(this.empty ? 0 : 125, this.empty ? 255 : 0, this.empty ? 0 : 255, 255);
/* 200 */       if (((Boolean)this.doulebreak.getValue()).booleanValue()) {
/* 201 */         RenderUtil.boxESP(breakPos, color2, 1.0F, true, true, 85, true);
/* 202 */         if (this.currentPos != null)
/* 203 */           RenderUtil.boxESP(this.currentPos, color2, 1.0F, true, true, 85, true); 
/* 204 */       } else if (this.rendermode.getValue() == Mode.Fill) {
/* 205 */         RenderUtil.drawBBFill(axisAlignedBB1, color, ((Integer)this.fillAlpha.getValue()).intValue());
/* 206 */       } else if (this.rendermode.getValue() == Mode.Box) {
/* 207 */         RenderUtil.drawBBBox(axisAlignedBB1, color, ((Integer)this.boxAlpha.getValue()).intValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onBlockEvent(BlockEvent event) {
/* 216 */     if (fullNullCheck() || !((Boolean)this.doulebreak.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 219 */     if (event.getStage() == 3 && mc.world.getBlockState(event.pos).getBlock() instanceof net.minecraft.block.BlockEndPortalFrame) {
/* 220 */       mc.world.getBlockState(event.pos).getBlock().setHardness(50.0F);
/*     */     }
/*     */     
/* 223 */     if (event.getStage() == 4)
/*     */     {
/* 225 */       if (BlockUtil.canBreak(event.pos)) {
/* 226 */         if (this.currentPos == null || (breakPos != null && mc.world.getBlockState(breakPos).getBlock() == Blocks.AIR && mc.world.getBlockState(this.currentPos).getBlock() != Blocks.AIR)) {
/* 227 */           this.currentPos = event.pos;
/* 228 */           this.currentBlockState = mc.world.getBlockState(this.currentPos);
/* 229 */           ItemStack object = new ItemStack(Items.DIAMOND_PICKAXE);
/* 230 */           this.breakTime = object.getDestroySpeed(this.currentBlockState) / 3.71F;
/* 231 */           this.timer.reset();
/*     */         } 
/* 233 */         mc.player.swingArm(EnumHand.MAIN_HAND);
/* 234 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.pos, event.facing));
/* 235 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.pos, event.facing));
/*     */         
/* 237 */         event.setCanceled(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onBlockEvent(PlayerDamageBlockEvent event) {
/* 246 */     if (fullNullCheck())
/* 247 */       return;  if (breakPos != null && breakPos.getX() == event.getPos().getX() && breakPos.getY() == event.getPos().getY() && breakPos.getZ() == event.getPos().getZ())
/*     */       return; 
/* 249 */     if (mc.player.capabilities.isCreativeMode)
/* 250 */       return;  if (BlockUtil.canBreak(event.pos)) {
/* 251 */       breakPos = event.pos;
/* 252 */       this.breakState = mc.world.getBlockState(event.pos);
/* 253 */       this.facing = event.facing;
/* 254 */       this.manxi = 0.0D;
/* 255 */       this.empty = false;
/* 256 */       this.cancelStart = false;
/* 257 */       this.breakSuccess.reset();
/* 258 */       if (breakPos != null) {
/* 259 */         mc.player.swingArm(EnumHand.MAIN_HAND);
/* 260 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, breakPos, this.facing));
/* 261 */         this.cancelStart = true;
/* 262 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
/* 263 */         event.setCanceled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBestAvailableToolSlot(IBlockState blockState) {
/* 270 */     int toolSlot = -1;
/* 271 */     double max = 0.0D;
/*     */     
/* 273 */     for (int i = 0; i < 9; i++) {
/* 274 */       ItemStack stack = mc.player.inventory.getStackInSlot(i);
/*     */       float speed;
/* 276 */       if (!stack.isEmpty && (speed = stack.getDestroySpeed(blockState)) > 1.0F) {
/*     */         int eff;
/* 278 */         if ((speed = (float)(speed + (((eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack)) > 0) ? (Math.pow(eff, 2.0D) + 1.0D) : 0.0D))) > max) {
/* 279 */           max = speed;
/* 280 */           toolSlot = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     return toolSlot;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState getGoodBlockState(BlockPos breakPos) {
/* 290 */     IBlockState checkState = this.breakState;
/* 291 */     Block checkBlock = checkState.getBlock();
/*     */     
/* 293 */     IBlockState currentState = mc.world.getBlockState(breakPos);
/* 294 */     Block currentBlock = currentState.getBlock();
/*     */     
/* 296 */     if (currentBlock != Blocks.AIR && currentBlock != checkBlock) {
/* 297 */       checkState = currentState;
/*     */     }
/*     */     
/* 300 */     return checkState;
/*     */   }
/*     */   
/*     */   private boolean check() {
/* 304 */     BlockPos player = EntityUtil.getPlayerPos((EntityPlayer)mc.player);
/* 305 */     return (breakPos.equals(player.add(0, 2, 0)) || breakPos.equals(player.add(0, 3, 0)) || breakPos.equals(player.add(0, -1, 0)) || breakPos.equals(player.add(1, 0, 0)) || breakPos.equals(player.add(-1, 0, 0)) || breakPos
/* 306 */       .equals(player.add(0, 0, 1)) || breakPos.equals(player.add(0, 0, -1)) || breakPos.equals(player.add(1, 1, 0)) || breakPos.equals(player.add(-1, 1, 0)) || breakPos.equals(player.add(0, 1, 1)) || breakPos
/* 307 */       .equals(player.add(0, 1, -1)));
/*     */   }
/*     */   
/*     */   private boolean check2() {
/* 311 */     return (mc.world.getBlockState(breakPos.add(0, 1, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(breakPos.add(0, 2, 0)).getBlock() == Blocks.AIR && !breakPos.equals(new BlockPos(mc.player.posX, mc.player.posY + 2.0D, mc.player.posZ)) && 
/* 312 */       !BlockUtil.PlayerCheck(breakPos.add(0, 1, 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 317 */     return ((Boolean)this.ghostHand.getValue()).booleanValue() ? "Ghost" : "Normal";
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 321 */     Fill,
/* 322 */     Box;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\InstantMine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
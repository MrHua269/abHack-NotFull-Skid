/*     */ package me.abHack.features.modules.combat;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.event.events.ProcessRightClickBlockEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.block.BlockWeb;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class Offhand extends Module {
/*     */   private static Offhand instance;
/*  32 */   private final Queue<InventoryUtil.Task> taskList = new ConcurrentLinkedQueue<>();
/*  33 */   private final Timer timer = new Timer();
/*  34 */   private final Timer secondTimer = new Timer();
/*  35 */   public Setting<Boolean> crystal = register(new Setting("Crystal", Boolean.valueOf(true)));
/*  36 */   public Setting<Float> crystalHealth = register(new Setting("CrystalHP", Float.valueOf(13.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> ((Boolean)this.crystal.getValue()).booleanValue()));
/*  37 */   public Setting<Float> crystalHoleHealth = register(new Setting("CrystalHoleHP", Float.valueOf(3.5F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> ((Boolean)this.crystal.getValue()).booleanValue()));
/*  38 */   public Setting<Boolean> bed = register(new Setting("Bed", Boolean.valueOf(true), v -> !((Boolean)this.crystal.getValue()).booleanValue()));
/*  39 */   public Setting<Float> bedHealth = register(new Setting("BedHP", Float.valueOf(13.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> ((Boolean)this.bed.getValue()).booleanValue()));
/*  40 */   public Setting<Boolean> gapple = register(new Setting("Gapple", Boolean.valueOf(true)));
/*  41 */   public Setting<Float> gappleHealth = register(new Setting("GappleHP", Float.valueOf(8.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> ((Boolean)this.gapple.getValue()).booleanValue()));
/*  42 */   public Setting<Boolean> armorCheck = register(new Setting("ArmorCheck", Boolean.valueOf(true)));
/*  43 */   public Setting<Integer> actions = register(new Setting("Packets", Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(4)));
/*  44 */   public Mode2 currentMode = Mode2.TOTEMS;
/*  45 */   public int totems = 0;
/*  46 */   public int crystals = 0;
/*  47 */   public int gapples = 0;
/*  48 */   public int beds = 0;
/*  49 */   public int lastTotemSlot = -1;
/*  50 */   public int lastGappleSlot = -1;
/*  51 */   public int lastCrystalSlot = -1;
/*  52 */   public int lastBedSlot = -1;
/*  53 */   public int lastObbySlot = -1;
/*  54 */   public int lastWebSlot = -1;
/*     */   public boolean holdingCrystal = false;
/*     */   public boolean holdingBed = false;
/*     */   public boolean holdingTotem = false;
/*     */   public boolean holdingGapple = false;
/*     */   public boolean didSwitchThisTick = false;
/*     */   private boolean second = false;
/*     */   private boolean switchedForHealthReason = false;
/*     */   
/*     */   public Offhand() {
/*  64 */     super("Offhand", "Allows you to switch up your ", Module.Category.COMBAT, true, false, false);
/*  65 */     instance = this;
/*     */   }
/*     */   
/*     */   public static Offhand getInstance() {
/*  69 */     if (instance == null) {
/*  70 */       instance = new Offhand();
/*     */     }
/*  72 */     return instance;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayer(ProcessRightClickBlockEvent event) {
/*  77 */     if (event.hand == EnumHand.MAIN_HAND && event.stack.getItem() == Items.END_CRYSTAL && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.objectMouseOver != null && event.pos == mc.objectMouseOver.getBlockPos()) {
/*  78 */       event.setCanceled(true);
/*  79 */       mc.player.setActiveHand(EnumHand.OFF_HAND);
/*  80 */       mc.playerController.processRightClick((EntityPlayer)mc.player, (World)mc.world, EnumHand.OFF_HAND);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  86 */     if (this.timer.passedMs(50L)) {
/*  87 */       if (mc.player != null && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && Mouse.isButtonDown(1)) {
/*  88 */         mc.player.setActiveHand(EnumHand.OFF_HAND);
/*  89 */         mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
/*     */       } 
/*  91 */     } else if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
/*  92 */       mc.gameSettings.keyBindUseItem.pressed = false;
/*     */     } 
/*  94 */     if (nullCheck()) {
/*     */       return;
/*     */     }
/*  97 */     doOffhand();
/*  98 */     if (this.secondTimer.passedMs(50L) && this.second) {
/*  99 */       this.second = false;
/* 100 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 106 */     if (!fullNullCheck() && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && mc.gameSettings.keyBindUseItem.isKeyDown()) {
/* 107 */       if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
/* 108 */         CPacketPlayerTryUseItemOnBlock packet2 = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
/* 109 */         if (packet2.getHand() == EnumHand.MAIN_HAND) {
/* 110 */           if (this.timer.passedMs(50L)) {
/* 111 */             mc.player.setActiveHand(EnumHand.OFF_HAND);
/* 112 */             mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
/*     */           } 
/* 114 */           event.setCanceled(true);
/*     */         } 
/* 116 */       } else if (event.getPacket() instanceof CPacketPlayerTryUseItem && ((CPacketPlayerTryUseItem)event.getPacket()).getHand() == EnumHand.OFF_HAND && !this.timer.passedMs(50L)) {
/* 117 */         event.setCanceled(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 124 */     if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
/* 125 */       return "Crystals";
/*     */     }
/* 127 */     if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
/* 128 */       return "Totems";
/*     */     }
/* 130 */     if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
/* 131 */       return "Gapples";
/*     */     }
/* 133 */     if (mc.player.getHeldItemOffhand().getItem() == Items.BED) {
/* 134 */       return "Beds";
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */   
/*     */   public void doOffhand() {
/* 140 */     this.didSwitchThisTick = false;
/* 141 */     this.holdingCrystal = (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
/* 142 */     this.holdingTotem = (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING);
/* 143 */     this.holdingGapple = (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE);
/* 144 */     this.holdingBed = (mc.player.getHeldItemOffhand().getItem() == Items.BED);
/* 145 */     this.totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
/* 146 */     if (this.holdingTotem) {
/* 147 */       this.totems += mc.player.inventory.offHandInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
/*     */     }
/* 149 */     this.crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.END_CRYSTAL)).mapToInt(ItemStack::getCount).sum();
/* 150 */     if (this.holdingCrystal) {
/* 151 */       this.crystals += mc.player.inventory.offHandInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.END_CRYSTAL)).mapToInt(ItemStack::getCount).sum();
/*     */     }
/* 153 */     this.beds = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.BED)).mapToInt(ItemStack::getCount).sum();
/* 154 */     if (this.holdingBed) {
/* 155 */       this.beds += mc.player.inventory.offHandInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.BED)).mapToInt(ItemStack::getCount).sum();
/*     */     }
/* 157 */     this.gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.GOLDEN_APPLE)).mapToInt(ItemStack::getCount).sum();
/* 158 */     if (this.holdingGapple) {
/* 159 */       this.gapples += mc.player.inventory.offHandInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.GOLDEN_APPLE)).mapToInt(ItemStack::getCount).sum();
/*     */     }
/* 161 */     doSwitch();
/*     */   }
/*     */   public void doSwitch() {
/*     */     int lastSlot;
/* 165 */     this.currentMode = Mode2.TOTEMS;
/* 166 */     if (((Boolean)this.gapple.getValue()).booleanValue() && mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemSword && mc.gameSettings.keyBindUseItem.isKeyDown() && !(mc.world.getBlockState(((RayTraceResult)Objects.requireNonNull(mc.player.rayTrace(5.0D, mc.getRenderPartialTicks()))).getBlockPos()).getBlock() instanceof net.minecraft.block.BlockContainer) && EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.gappleHealth.getValue()).floatValue()) {
/* 167 */       this.currentMode = Mode2.GAPPLES;
/* 168 */     } else if (this.currentMode != Mode2.CRYSTALS && ((Boolean)this.crystal.getValue()).booleanValue() && ((EntityUtil.isSafe((Entity)mc.player) && EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHoleHealth.getValue()).floatValue()) || EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHealth.getValue()).floatValue())) {
/* 169 */       this.currentMode = Mode2.CRYSTALS;
/* 170 */     } else if (this.currentMode != Mode2.BEDS && ((Boolean)this.bed.getValue()).booleanValue() && EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.bedHealth.getValue()).floatValue()) {
/* 171 */       this.currentMode = Mode2.BEDS;
/*     */     } 
/* 173 */     if (this.currentMode == Mode2.CRYSTALS && this.crystals == 0) {
/* 174 */       setMode(Mode2.TOTEMS);
/*     */     }
/* 176 */     if (this.currentMode == Mode2.BEDS && this.beds == 0) {
/* 177 */       setMode(Mode2.TOTEMS);
/*     */     }
/* 179 */     if (this.currentMode == Mode2.CRYSTALS && ((!EntityUtil.isSafe((Entity)mc.player) && EntityUtil.getHealth((Entity)mc.player, true) <= ((Float)this.crystalHealth.getValue()).floatValue()) || EntityUtil.getHealth((Entity)mc.player, true) <= ((Float)this.crystalHoleHealth.getValue()).floatValue())) {
/* 180 */       if (this.currentMode == Mode2.CRYSTALS) {
/* 181 */         this.switchedForHealthReason = true;
/*     */       }
/* 183 */       setMode(Mode2.TOTEMS);
/*     */     } 
/* 185 */     if (this.currentMode == Mode2.BEDS && EntityUtil.getHealth((Entity)mc.player, true) <= ((Float)this.bedHealth.getValue()).floatValue()) {
/* 186 */       if (this.currentMode == Mode2.BEDS) {
/* 187 */         this.switchedForHealthReason = true;
/*     */       }
/* 189 */       setMode(Mode2.TOTEMS);
/*     */     } 
/* 191 */     if (this.switchedForHealthReason && ((EntityUtil.isSafe((Entity)mc.player) && EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHoleHealth.getValue()).floatValue()) || EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHealth.getValue()).floatValue())) {
/* 192 */       setMode(Mode2.CRYSTALS);
/* 193 */       this.switchedForHealthReason = false;
/*     */     } 
/* 195 */     if (this.switchedForHealthReason && EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.bedHealth.getValue()).floatValue()) {
/* 196 */       setMode(Mode2.BEDS);
/* 197 */       this.switchedForHealthReason = false;
/*     */     } 
/* 199 */     if (this.currentMode == Mode2.CRYSTALS && ((Boolean)this.armorCheck.getValue()).booleanValue() && (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == Items.AIR)) {
/* 200 */       setMode(Mode2.TOTEMS);
/*     */     }
/* 202 */     if (this.currentMode == Mode2.BEDS && ((Boolean)this.armorCheck.getValue()).booleanValue() && (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == Items.AIR)) {
/* 203 */       setMode(Mode2.TOTEMS);
/*     */     }
/* 205 */     if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer && !(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
/*     */       return;
/*     */     }
/* 208 */     Item currentOffhandItem = mc.player.getHeldItemOffhand().getItem();
/* 209 */     switch (this.currentMode) {
/*     */       case TOTEMS:
/* 211 */         if (this.totems <= 0 || this.holdingTotem)
/* 212 */           break;  this.lastTotemSlot = InventoryUtil.findItemInventorySlot(Items.TOTEM_OF_UNDYING, false);
/* 213 */         lastSlot = getLastSlot(currentOffhandItem, this.lastTotemSlot);
/* 214 */         putItemInOffhand(this.lastTotemSlot, lastSlot);
/*     */         break;
/*     */       
/*     */       case GAPPLES:
/* 218 */         if (this.gapples <= 0 || this.holdingGapple)
/* 219 */           break;  this.lastGappleSlot = InventoryUtil.findItemInventorySlot(Items.GOLDEN_APPLE, false);
/* 220 */         lastSlot = getLastSlot(currentOffhandItem, this.lastGappleSlot);
/* 221 */         putItemInOffhand(this.lastGappleSlot, lastSlot);
/*     */         break;
/*     */       
/*     */       case BEDS:
/* 225 */         if (this.beds <= 0 || this.holdingBed)
/* 226 */           break;  this.lastBedSlot = InventoryUtil.findItemInventorySlot(Items.BED, false);
/* 227 */         lastSlot = getLastSlot(currentOffhandItem, this.lastBedSlot);
/* 228 */         putItemInOffhand(this.lastBedSlot, lastSlot);
/*     */       
/*     */       default:
/* 231 */         if (this.crystals <= 0 || this.holdingCrystal)
/* 232 */           break;  this.lastCrystalSlot = InventoryUtil.findItemInventorySlot(Items.END_CRYSTAL, false);
/* 233 */         lastSlot = getLastSlot(currentOffhandItem, this.lastCrystalSlot);
/* 234 */         putItemInOffhand(this.lastCrystalSlot, lastSlot);
/*     */         break;
/*     */     } 
/* 237 */     for (int i = 0; i < ((Integer)this.actions.getValue()).intValue(); i++) {
/* 238 */       InventoryUtil.Task task = this.taskList.poll();
/* 239 */       if (task != null) {
/* 240 */         task.run();
/* 241 */         if (task.isSwitching())
/* 242 */           this.didSwitchThisTick = true; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private int getLastSlot(Item item, int slotIn) {
/* 247 */     if (item == Items.END_CRYSTAL) {
/* 248 */       return this.lastCrystalSlot;
/*     */     }
/* 250 */     if (item == Items.GOLDEN_APPLE) {
/* 251 */       return this.lastGappleSlot;
/*     */     }
/* 253 */     if (item == Items.TOTEM_OF_UNDYING) {
/* 254 */       return this.lastTotemSlot;
/*     */     }
/* 256 */     if (InventoryUtil.isBlock(item, BlockObsidian.class)) {
/* 257 */       return this.lastObbySlot;
/*     */     }
/* 259 */     if (InventoryUtil.isBlock(item, BlockWeb.class)) {
/* 260 */       return this.lastWebSlot;
/*     */     }
/* 262 */     if (item == Items.AIR) {
/* 263 */       return -1;
/*     */     }
/* 265 */     return slotIn;
/*     */   }
/*     */   
/*     */   private void putItemInOffhand(int slotIn, int slotOut) {
/* 269 */     if (slotIn != -1 && this.taskList.isEmpty()) {
/* 270 */       this.taskList.add(new InventoryUtil.Task(slotIn));
/* 271 */       this.taskList.add(new InventoryUtil.Task(45));
/* 272 */       this.taskList.add(new InventoryUtil.Task(slotOut));
/* 273 */       this.taskList.add(new InventoryUtil.Task());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMode(Mode2 mode) {
/* 278 */     this.currentMode = (this.currentMode == mode) ? Mode2.TOTEMS : mode;
/*     */   }
/*     */   
/*     */   public enum Mode2 {
/* 282 */     TOTEMS,
/* 283 */     GAPPLES,
/* 284 */     CRYSTALS,
/* 285 */     BEDS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\Offhand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
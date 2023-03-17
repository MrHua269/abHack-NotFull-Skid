/*     */ package me.abHack.features.modules.combat;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.awt.*;
import java.util.ArrayList;
/*     */ import java.util.Comparator;
import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.*;
/*     */
/*     */ import me.abHack.OyVey;
import me.abHack.event.events.Packet;
import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.Feature;
import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.BlockUtil;
/*     */ import me.abHack.util.DamageUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import me.abHack.util.Util;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.*;
/*     */
/*     */
/*     */
/*     */ import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class HFAura extends Module {
/*  41 */   private static final int THREADS = Runtime.getRuntime().availableProcessors();
/*  42 */   private static final ThreadFactory FACTORY = createDaemonThreadFactoryBuilder().build();
/*  43 */   private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
/*  44 */       Math.max(1, THREADS - 1), THREADS * 4, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), FACTORY);
/*     */ 
/*     */   
/*     */   private static HFAura INSTANCE;
/*     */   
/*  49 */   private final Timer placeTimer = new Timer();
/*  50 */   private final Timer breakTimer = new Timer();
/*  51 */   private final Setting<Settings> setting = register(new Setting("Settings", Settings.PLACE));
/*  52 */   private final Setting<Boolean> place = register(new Setting("Place", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.PLACE)));
/*  53 */   private final Setting<Double> placeDelay = register(new Setting("PlaceDelay", Double.valueOf(30.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), v -> (((Boolean)this.place.getValue()).booleanValue() && this.setting.getValue() == Settings.PLACE)));
/*  54 */   public final Setting<Double> placeRange = register(new Setting("PlaceRange", Double.valueOf(5.1D), Double.valueOf(0.0D), Double.valueOf(6.0D), v -> (((Boolean)this.place.getValue()).booleanValue() && this.setting.getValue() == Settings.PLACE)));
/*  55 */   private final Setting<Boolean> slowMode = register(new Setting("SlowMode", Boolean.valueOf(true), v -> (((Boolean)this.place.getValue()).booleanValue() && this.setting.getValue() == Settings.PLACE)));
/*  56 */   private final Setting<Double> slowBalance = register(new Setting("SlowBalance", Double.valueOf(1.5D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (((Boolean)this.place.getValue()).booleanValue() && this.setting.getValue() == Settings.PLACE && ((Boolean)this.slowMode.getValue()).booleanValue())));
/*  57 */   private final Setting<Double> slowDelay = register(new Setting("SlowDelay", Double.valueOf(500.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), v -> (((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.slowMode.getValue()).booleanValue() && this.setting.getValue() == Settings.PLACE)));
/*  58 */   private final Setting<Boolean> multiplecalculate = register(new Setting("MultipleCalculate", Boolean.valueOf(true), v -> (((Boolean)this.place.getValue()).booleanValue() && this.setting.getValue() == Settings.PLACE)));
/*  59 */   private final Setting<Integer> maxtarger = register(new Setting("MaxTarger", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(5), v -> (((Boolean)this.place.getValue()).booleanValue() && this.setting.getValue() == Settings.PLACE && ((Boolean)this.multiplecalculate.getValue()).booleanValue())));
/*  60 */   private final Setting<Boolean> explode = register(new Setting("Break", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.BREAK)));
/*  61 */   private final Setting<Boolean> packetBreak = register(new Setting("PacketBreak", Boolean.valueOf(true), v -> (((Boolean)this.explode.getValue()).booleanValue() && this.setting.getValue() == Settings.BREAK)));
/*  62 */   private final Setting<Double> breakDelay = register(new Setting("BreakDelay", Double.valueOf(60.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), v -> (((Boolean)this.explode.getValue()).booleanValue() && this.setting.getValue() == Settings.BREAK)));
/*  63 */   private final Setting<Double> breakRange = register(new Setting("BreakRange", Double.valueOf(6.0D), Double.valueOf(0.0D), Double.valueOf(6.0D), v -> (((Boolean)this.explode.getValue()).booleanValue() && this.setting.getValue() == Settings.BREAK)));
/*  64 */   private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(false), v -> (this.setting.getValue() == Settings.MISC)));
/*  65 */   private final Setting<Boolean> antiSuicide = register(new Setting("AntiSuicide", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.MISC)));
/*  66 */   private final Setting<SwitchMode> switchMode = register(new Setting("Switch", SwitchMode.Slient, v -> (this.setting.getValue() == Settings.MISC)));
/*  67 */   private final Setting<Double> raytrace = register(new Setting("Raytrace", Double.valueOf(3.0D), Double.valueOf(0.0D), Double.valueOf(3.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  68 */   private final Setting<Double> targetRange = register(new Setting("TargetRange", Double.valueOf(12.0D), Double.valueOf(0.0D), Double.valueOf(12.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  69 */   private final Setting<Integer> faceplace = register(new Setting("FacePlace", Integer.valueOf(8), Integer.valueOf(0), Integer.valueOf(15), v -> (this.setting.getValue() == Settings.MISC)));
/*  70 */   private final Setting<Double> forceSafe = register(new Setting("ForceSafe", Double.valueOf(14.0D), Double.valueOf(5.0D), Double.valueOf(36.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  71 */   private final Setting<Double> minPlaceDmg = register(new Setting("MinPlaceDmg", Double.valueOf(5.5D), Double.valueOf(0.0D), Double.valueOf(16.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  72 */   private final Setting<Double> placeBalance = register(new Setting("placeBalance", Double.valueOf(3.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  73 */   private final Setting<Double> minBreakDmg = register(new Setting("MinBreakDmg", Double.valueOf(4.5D), Double.valueOf(0.0D), Double.valueOf(16.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  74 */   private final Setting<Double> breakBalance = register(new Setting("BreakBalance", Double.valueOf(4.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  75 */   private final Setting<Double> maxSelfDmg = register(new Setting("MaxSelfDmg", Double.valueOf(20.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), v -> (this.setting.getValue() == Settings.MISC)));
/*  76 */   private final Setting<EnumHand> breakSwing = register(new Setting("BreakSwing", EnumHand.MAIN_HAND, v -> (this.setting.getValue() == Settings.MISC)));
/*  77 */   private final Setting<Integer> predictTick = register(new Setting("PredictTick", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(10), v -> (this.setting.getValue() == Settings.MISC)));
/*  78 */   private final Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.RENDER)));
/*  79 */   private final Setting<Boolean> renderDmg = register(new Setting("RenderDmg", Boolean.valueOf(true), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  80 */   private final Setting<Integer> red = register(new Setting("Red", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  81 */   private final Setting<Integer> green = register(new Setting("Green", Integer.valueOf(25), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  82 */   private final Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  83 */   private final Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  84 */   public Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(true), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  85 */   private final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(85), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.box.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  86 */   public Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  87 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.outline.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*  88 */   public Setting<Double> height = register(new Setting("Height", Double.valueOf(-0.9D), Double.valueOf(-2.0D), Double.valueOf(2.0D), v -> (((Boolean)this.render.getValue()).booleanValue() && this.setting.getValue() == Settings.RENDER)));
/*     */   private final UpdateThread thread;
/*     */   public EntityPlayer target;
/*     */   private EntityEnderCrystal crystal;
/*     */   private boolean rotating = false;
/*     */   private String displaytarget;
/*  94 */   private float pitch = 0.0F;
/*  95 */   private float yaw = 0.0F;
/*     */   private BlockPos pos;
/*     */   private float targetDamage;
/*     */   
/*     */   public HFAura() {
/* 100 */     super("HFAura", "HF Ez xxs", Module.Category.COMBAT, true, false, false);
/*     */     
/* 102 */     EXECUTOR.allowsCoreThreadTimeOut();
/* 103 */     this.thread = new UpdateThread(this);
/* 104 */     this.thread.setDaemon(true);
/* 105 */     this.thread.start();
/*     */   }
/*     */   
/*     */   public static ThreadFactoryBuilder createDaemonThreadFactoryBuilder() {
/* 109 */     ThreadFactoryBuilder factory = new ThreadFactoryBuilder();
/* 110 */     factory.setDaemon(true);
/* 111 */     return factory;
/*     */   }
/*     */   
/*     */   public static HFAura getInstance() {
/* 115 */     if (INSTANCE == null) INSTANCE = new HFAura();
/*     */     
/* 117 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public void onEnable() {
/* 121 */     this.thread.interrupt();
/*     */     
/* 123 */     this.placeTimer.reset();
/* 124 */     this.breakTimer.reset();
/* 125 */     this.target = null;
/* 126 */     this.displaytarget = null;
/* 127 */     this.crystal = null;
/* 128 */     this.pos = null;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 132 */     this.rotating = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 138 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiChest && mc.world.getBlockState(((RayTraceResult)Objects.<RayTraceResult>requireNonNull(mc.player.rayTrace(4.5D, mc.getRenderPartialTicks()))).getBlockPos()).getBlock() == Blocks.ENDER_CHEST && this.target != null) {
/* 143 */       mc.displayGuiScreen(null);
/*     */     }
/*     */     
/* 146 */     EntityPlayer target = this.target;
/*     */     
/* 148 */     if (target == null) {
/* 149 */       this.crystal = null;
/*     */       
/*     */       return;
/*     */     } 
/* 153 */     BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/*     */     
/* 155 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 160 */       .crystal = mc.world.loadedEntityList.stream().filter(crystal -> checkCanBreak(crystal, mutablePos)).map(crystal -> (EntityEnderCrystal)crystal).min(Comparator.comparing(target::getDistance)).orElse(null);
/*     */     
/* 162 */     if (this.crystal != null && ((Boolean)this.explode.getValue()).booleanValue() && this.breakTimer.passedMs(((Double)this.breakDelay.getValue()).longValue())) {
/* 163 */       rotateTo((Entity)this.crystal);
/* 164 */       EntityUtil.attackEntity((Entity)this.crystal, ((Boolean)this.packetBreak.getValue()).booleanValue(), (EnumHand)this.breakSwing.getValue());
/* 165 */       this.breakTimer.reset();
/*     */     } 
/*     */     
/* 168 */     BlockPos placePos = this.pos;
/* 169 */     if (placePos == null)
/*     */       return; 
/* 171 */     if (((Boolean)this.place.getValue()).booleanValue() && this.placeTimer.passedMs(((Double)this.placeDelay.getValue()).longValue())) {
/* 172 */       long newDelay = 0L;
/*     */       
/* 174 */       if (this.targetDamage < ((Double)this.minPlaceDmg.getValue()).doubleValue() && EntityUtil.getHealth((Entity)target) > ((Integer)this.faceplace.getValue()).intValue() && EntityUtil.getHealth((Entity)mc.player) > ((Double)this.forceSafe.getValue()).doubleValue() && ((Boolean)this.slowMode.getValue()).booleanValue()) {
/* 175 */         newDelay = ((Double)this.slowDelay.getValue()).longValue();
/*     */       }
/*     */       
/* 178 */       if (this.placeTimer.passedMs(newDelay)) {
/* 179 */         rotateToPos(placePos);
/* 180 */         int old = mc.player.inventory.currentItem;
/* 181 */         if (this.switchMode.getValue() == SwitchMode.Slient && InventoryUtil.getItemHotbar(Items.END_CRYSTAL) != -1) {
/* 182 */           InventoryUtil.switchToSlot(InventoryUtil.getItemHotbar(Items.END_CRYSTAL));
/* 183 */           BlockUtil.placeCrystalOnBlock(placePos, (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
/* 184 */           InventoryUtil.switchToSlot(old);
/* 185 */         } else if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
/* 186 */           BlockUtil.placeCrystalOnBlock(placePos, (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
/*     */         } 
/* 188 */         this.placeTimer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 195 */     if (((Boolean)this.rotate.getValue()).booleanValue() && this.rotating && event.getPacket() instanceof CPacketPlayer) {
/* 196 */       CPacketPlayer packet = (CPacketPlayer)event.getPacket();
/* 197 */       packet.yaw = this.yaw;
/* 198 */       packet.pitch = this.pitch;
/* 199 */       this.rotating = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 205 */     if (((Boolean)this.explode.getValue()).booleanValue() && event.getPacket() instanceof SPacketSpawnObject) {
/* 206 */       SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
/*     */       
/* 208 */       if (packet.getType() == 51 && checkCanBreak(packet.getX(), packet.getY(), packet.getZ(), new BlockPos.MutableBlockPos())) {
/* 209 */         CPacketUseEntity predict = new CPacketUseEntity();
/* 210 */         predict.entityId = packet.getEntityID();
/* 211 */         predict.action = CPacketUseEntity.Action.ATTACK;
/* 212 */         mc.player.connection.sendPacket(predict);
/*     */       }
/*     */     
/* 215 */     } else if (event.getPacket() instanceof SPacketSoundEffect) {
/* 216 */       SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
/* 217 */       if (packet
/* 218 */         .getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE &&
/* 219 */         checkCanBreak(packet.getX(), packet.getY(), packet.getZ(), new BlockPos.MutableBlockPos()))
/*     */       {
/* 221 */         mc.addScheduledTask(() -> {
/*     */               if (this.crystal != null)
/*     */                 this.crystal.setDead(); 
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 230 */     BlockPos placePos = this.pos;
/*     */     
/* 232 */     if (placePos != null && ((Boolean)this.render.getValue()).booleanValue()) {
/* 233 */       RenderUtil.drawBoxESP(placePos.add(0, 1, 0), new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue()), false, null, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), true, ((Double)this.height.getValue()).doubleValue(), false, false, false, false, 0);
/* 234 */       int color = (this.targetDamage > 18.0D) ? 16711680 : ((this.targetDamage > 16.0D) ? 16720896 : ((this.targetDamage > 12.0D) ? 16744192 : ((this.targetDamage > 8.0D) ? 16776960 : ((this.targetDamage > 5.0D) ? 65535 : 65280))));
/* 235 */       if (((Boolean)this.renderDmg.getValue()).booleanValue()) {
/* 236 */         RenderUtil.drawText(placePos.add(0, 1, 0), ((Math.floor(this.targetDamage) == this.targetDamage) ? Integer.valueOf((int)this.targetDamage) : String.format("%.1f", new Object[] { Float.valueOf(this.targetDamage) })) + "", color);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 243 */     EntityPlayer target = this.target;
/* 244 */     if (target != null) {
/* 245 */       this.displaytarget = target.getName();
/*     */     }
/*     */     
/* 248 */     if (this.displaytarget != null) {
/* 249 */       return this.displaytarget;
/*     */     }
/*     */     
/* 252 */     return null;
/*     */   }
/*     */   
/*     */   private boolean checkCanBreak(Entity entity, BlockPos.MutableBlockPos pos) {
/* 256 */     if (entity == null || !(entity instanceof EntityEnderCrystal) || entity.isDead) {
/* 257 */       return false;
/*     */     }
/* 259 */     return checkCanBreak(entity.posX, entity.posY, entity.posZ, pos);
/*     */   }
/*     */   
/*     */   private boolean checkCanBreak(double x, double y, double z, BlockPos.MutableBlockPos pos) {
/* 263 */     EntityPlayer target = this.target;
/* 264 */     if (target == null || target.isDead || target.getHealth() <= 0.0F) {
/* 265 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     if (!checkValidBreakRange(x, y, z)) {
/* 273 */       return false;
/*     */     }
/*     */     
/* 276 */     double selfDamage = DamageUtil.calculateDamage(x, y, z, (Entity)mc.player, pos.setPos(x, y, z));
/* 277 */     if (selfDamage + (((Boolean)this.antiSuicide.getValue()).booleanValue() ? 2.0D : 0.5D) >= EntityUtil.getHealth((Entity)mc.player) || selfDamage - 0.5D > ((Double)this.maxSelfDmg.getValue()).doubleValue()) {
/* 278 */       return false;
/*     */     }
/*     */     
/* 281 */     double targetDamage = DamageUtil.calculateDamage(x, y, z, (Entity)target, pos);
/* 282 */     if (targetDamage >= EntityUtil.getHealth((Entity)target)) {
/* 283 */       return true;
/*     */     }
/*     */     
/* 286 */     if (targetDamage - ((((Boolean)this.slowMode.getValue()).booleanValue() && targetDamage <= ((Double)this.minPlaceDmg.getValue()).doubleValue()) ? (Double)this.slowBalance.getValue() : (Double)this.breakBalance.getValue()).doubleValue() < selfDamage) {
/* 287 */       return false;
/*     */     }
/* 289 */     return (targetDamage > ((Double)this.minBreakDmg.getValue()).doubleValue() || (((Boolean)this.slowMode.getValue()).booleanValue() && targetDamage >= 1.5D));
/*     */   }
/*     */   
/*     */   private boolean checkValidBreakRange(double x, double y, double z) {
/* 293 */     double dist = mc.player.getDistance(x, y, z);
/* 294 */     return (dist <= ((Double)this.breakRange.getValue()).doubleValue() || dist <= ((Double)this.raytrace.getValue()).doubleValue() || BlockUtil.canBlockBeSeen(x, y, z));
/*     */   }
/*     */   
/*     */   private void rotateTo(Entity entity) {
/* 298 */     if (((Boolean)this.rotate.getValue()).booleanValue()) {
/* 299 */       float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionVector());
/* 300 */       this.yaw = angle[0];
/* 301 */       this.pitch = angle[1];
/* 302 */       this.rotating = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void rotateToPos(BlockPos pos) {
/* 307 */     if (((Boolean)this.rotate.getValue()).booleanValue()) {
/* 308 */       float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((pos.getX() + 0.5F), (pos.getY() - 0.5F), (pos.getZ() + 0.5F)));
/* 309 */       this.yaw = angle[0];
/* 310 */       this.pitch = angle[1];
/* 311 */       this.rotating = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<BlockPos> getBlockWithinRadius() {
/* 316 */     List<BlockPos> list = new ArrayList<>();
/*     */     
/* 318 */     int xFloor = MathHelper.floor(mc.player.posX);
/* 319 */     int yFloor = MathHelper.floor(mc.player.posY);
/* 320 */     int zFloor = MathHelper.floor(mc.player.posZ);
/*     */     
/* 322 */     double placeRange = ((Double)this.placeRange.getValue()).doubleValue();
/* 323 */     double placeRangeSq = placeRange * placeRange;
/* 324 */     int placeRangeFloor = MathHelper.floor(placeRange);
/* 325 */     int placeRangeCeil = MathHelper.ceil(placeRange);
/*     */     
/* 327 */     BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/* 328 */     BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();
/*     */     
/* 330 */     for (int x = xFloor - placeRangeFloor; x <= xFloor + placeRangeCeil; x++) {
/* 331 */       for (int z = zFloor - placeRangeFloor; z <= zFloor + placeRangeCeil; z++) {
/* 332 */         for (int y = yFloor - placeRangeFloor; y <= yFloor + placeRangeCeil; y++) {
/* 333 */           double dist = ((xFloor - x) * (xFloor - x) + (zFloor - z) * (zFloor - z) + (yFloor - y) * (yFloor - y));
/* 334 */           if (dist < placeRangeSq)
/*     */           {
/* 336 */             if (canPlaceCrystal(pos.setPos(x, y, z), mutablePos)) {
/*     */ 
/*     */ 
/*     */               
/* 340 */               pos.setPos(x, y, z);
/*     */               
/* 342 */               if (mc.player
/* 343 */                 .getDistance(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D) <= ((Double)this.raytrace.getValue()).doubleValue() || 
/* 344 */                 BlockUtil.canBlockBeSeen((World)mc.world, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, mutablePos))
/*     */               {
/*     */ 
/*     */ 
/*     */                 
/* 349 */                 list.add(pos.toImmutable()); } 
/*     */             }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 354 */     pos.release();
/* 355 */     return list;
/*     */   }
/*     */   
/*     */   private boolean canPlaceCrystal(BlockPos.PooledMutableBlockPos pos, BlockPos.MutableBlockPos mutablePos) {
/* 359 */     Block block = mc.world.getBlockState((BlockPos)pos).getBlock();
/* 360 */     if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN) {
/* 361 */       return false;
/*     */     }
/*     */     
/* 364 */     if (mc.world
/* 365 */       .getBlockState((BlockPos)pos.setPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() != Blocks.AIR || mc.world
/* 366 */       .getBlockState((BlockPos)pos.setPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() != Blocks.AIR)
/*     */     {
/* 368 */       return false;
/*     */     }
/*     */     
/* 371 */     double minX = pos.getX();
/* 372 */     double minY = pos.getY() - 1.0D;
/* 373 */     double minZ = pos.getZ();
/*     */     
/* 375 */     double maxX = pos.getX() + 1.0D;
/* 376 */     double maxY = pos.getY() + 1.0D;
/* 377 */     double maxZ = pos.getZ() + 1.0D;
/*     */     
/* 379 */     for (Entity entity : new ArrayList<>(mc.world.loadedEntityList)) {
/*     */       
/* 381 */       if (entity == null || entity.isDead) {
/*     */         continue;
/*     */       }
/*     */       
/* 385 */       double x = entity.posX, y = entity.posY, z = entity.posZ, width = entity.width / 2.0D;
/*     */       
/* 387 */       double motionX = x - entity.prevPosX, motionY = y - entity.prevPosY, motionZ = z - entity.prevPosZ;
/*     */       
/* 389 */       for (int i = 0; i < ((Integer)this.predictTick.getValue()).intValue(); i++) {
/*     */ 
/*     */         
/* 392 */         motionY = (motionY > 0.4D) ? 0.314D : ((motionY > 0.3D) ? 0.23D : ((motionY > 0.2D) ? 0.147D : ((motionY > 0.1D) ? 0.065D : motionY)));
/* 393 */         boolean jump = (motionY > 0.0D);
/* 394 */         if (jump && entity instanceof EntityPlayer)
/* 395 */           motionY += ((EntityPlayer)entity).isPotionActive(MobEffects.JUMP_BOOST) ? ((((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST))).getAmplifier() + 1) * 0.1F) : 0.0D; 
/* 396 */         x += motionX;
/* 397 */         y += jump ? motionY : 0.0D;
/* 398 */         z += motionZ;
/*     */ 
/*     */         
/* 401 */         if (!mc.world.getCollisionBoxes(entity, new AxisAlignedBB(x - width, y, z - width, x + width, y + entity.height, z + width)).isEmpty()) {
/* 402 */           x -= motionX;
/* 403 */           y -= jump ? motionY : 0.0D;
/* 404 */           z -= motionZ;
/*     */         } 
/*     */       } 
/*     */       
/* 408 */       if (!entity.getEntityBoundingBox().intersects(minX, minY, minZ, maxX, maxY, maxZ) && !(new AxisAlignedBB(x - width, y, z - width, x + width, y + entity.height, z + width)).intersects(minX, minY, minZ, maxX, maxY, maxZ)) {
/*     */         continue;
/*     */       }
/* 411 */       if (!(entity instanceof EntityEnderCrystal) || !checkValidBreakRange(entity.posX, entity.posY, entity.posZ)) {
/* 412 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 416 */     return true;
/*     */   }
/*     */   
/*     */   private enum SwitchMode {
/* 420 */     None, Slient;
/*     */   }
/*     */   
/*     */   public enum Settings {
/* 424 */     PLACE, BREAK, MISC, RENDER;
/*     */   }
/*     */   
/*     */   private static class UpdateThread extends Thread {
/*     */     private final HFAura parent;
/*     */     
/*     */     public UpdateThread(HFAura parent) {
/* 431 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 436 */       Future<?> updateFuture = null;
/*     */       
/*     */       while (true) {
/*     */         try {
/* 440 */           if (Feature.fullNullCheck() || this.parent.isDisabled()) {
/* 441 */             Thread.yield();
/*     */             
/*     */             continue;
/*     */           } 
/* 445 */           if (updateFuture == null || updateFuture.isDone()) {
/* 446 */             updateFuture = HFAura.EXECUTOR.submit(() -> {
/*     */                   try {
/*     */                     calcPlacePos();
/* 449 */                   } catch (Throwable t) {
/*     */                     t.printStackTrace();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */           
/*     */           try {
/* 456 */             Thread.sleep(1L);
/* 457 */           } catch (InterruptedException interruptedException) {}
/*     */         }
/* 459 */         catch (Throwable t) {
/* 460 */           t.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void calcPlacePos() {
/* 466 */       if (this.parent.isOff()) {
/* 467 */         this.parent.pos = null;
/* 468 */         this.parent.targetDamage = 0.0F;
/* 469 */         this.parent.target = null;
/*     */         
/*     */         return;
/*     */       } 
/* 473 */       List<BlockPos> sphereList = this.parent.getBlockWithinRadius();
/*     */       
/* 475 */       EntityPlayer target = null;
/* 476 */       float maxDamage = 0.0F;
/* 477 */       BlockPos bestPos = null;
/*     */       
/* 479 */       int max = 1;
/* 480 */       BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/*     */       
/* 482 */       if (!sphereList.isEmpty()) {
/* 483 */         for (EntityPlayer entity : new ArrayList<>(Util.mc.world.playerEntities)) {
/* 484 */           if (entity.isDead || 
/*     */             
/* 486 */             EntityUtil.getHealth((Entity)entity) <= 0.0F || entity
/* 487 */             .getName().equals(Util.mc.player.getName()) || OyVey.friendManager
/* 488 */             .isFriend(entity.getName()) || entity
/* 489 */             .isCreative() || entity
/* 490 */             .getDistance((Entity)Util.mc.player) > ((Double)this.parent.targetRange.getValue()).doubleValue()) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 495 */           for (BlockPos pos : sphereList) {
/* 496 */             if (pos == null)
/*     */               continue; 
/*     */             try {
/* 499 */               double selfDamage = DamageUtil.calculateDamage(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, (Entity)Util.mc.player, mutablePos);
/*     */               
/* 501 */               if (selfDamage + (
/* 502 */                 ((Boolean)this.parent.antiSuicide.getValue()).booleanValue() ? 2.0D : 0.5D) >= EntityUtil.getHealth((Entity)Util.mc.player) || selfDamage - 0.5D > (
/* 503 */                 (Double)this.parent.maxSelfDmg.getValue()).doubleValue()) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/* 508 */               float targetDamage = DamageUtil.calculateDamagePredict(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, (Entity)entity, mutablePos, ((Integer)this.parent.predictTick.getValue()).intValue());
/* 509 */               if (targetDamage < 1.5F || maxDamage >= targetDamage || (
/* 510 */                 targetDamage - ((((Boolean)this.parent.slowMode.getValue()).booleanValue() && targetDamage <= ((Double)this.parent.minPlaceDmg.getValue()).doubleValue()) ? (Double)this.parent.slowBalance.getValue() : (Double)this.parent.placeBalance.getValue()).doubleValue() < selfDamage && EntityUtil.getHealth((Entity)entity) - targetDamage > 0.0F)) {
/*     */                 continue;
/*     */               }
/* 513 */               bestPos = pos;
/* 514 */               maxDamage = targetDamage;
/* 515 */               target = entity;
/* 516 */             } catch (NullPointerException e) {
/* 517 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */           
/* 521 */           if (target != null) {
/* 522 */             if (!((Boolean)this.parent.multiplecalculate.getValue()).booleanValue() || ((Integer)this.parent.maxtarger.getValue()).intValue() == max) {
/*     */               break;
/*     */             }
/* 525 */             max++;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 530 */       this.parent.pos = bestPos;
/* 531 */       this.parent.targetDamage = maxDamage;
/* 532 */       this.parent.target = target;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\HFAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
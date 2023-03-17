/*     */ package me.abHack.features.modules.client;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.ClientEvent;
/*     */ import me.abHack.event.events.PotionRenderHUDEvent;
/*     */ import me.abHack.event.events.Render2DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.abUtil;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import me.abHack.util.render.TextUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraftforge.client.event.ClientChatReceivedEvent;
/*     */ import net.minecraftforge.event.entity.player.AttackEntityEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class HUD<T> extends Module {
/*  40 */   private static final ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);
/*     */   public static HUD INSTANCE;
/*  42 */   private final Setting<Boolean> grayNess = register(new Setting("Gray", Boolean.valueOf(true)));
/*  43 */   private final Setting<Boolean> renderingUp = register(new Setting("RenderingUp", Boolean.valueOf(true), "Orientation of the HUD-Elements."));
/*  44 */   private final Setting<Boolean> waterMark = register(new Setting("Watermark", Boolean.valueOf(false), "displays watermark"));
/*  45 */   private final Setting<Boolean> arrayList = register(new Setting("ActiveModules", Boolean.valueOf(true), "Lists the active modules."));
/*  46 */   private final Setting<Boolean> coords = register(new Setting("Coords", Boolean.valueOf(false), "Your current coordinates"));
/*  47 */   private final Setting<Boolean> direction = register(new Setting("Direction", Boolean.valueOf(false), "The Direction you are facing."));
/*  48 */   private final Setting<Boolean> armor = register(new Setting("Armor", Boolean.valueOf(true), "ArmorHUD"));
/*  49 */   private final Setting<Boolean> totems = register(new Setting("Totems", Boolean.valueOf(true), "TotemHUD"));
/*  50 */   private final Setting<Boolean> greeter = register(new Setting("Welcomer", Boolean.valueOf(false), "The time"));
/*  51 */   private final Setting<Boolean> speed = register(new Setting("Speed", Boolean.valueOf(true), "Your Speed"));
/*  52 */   private final Setting<Boolean> potions = register(new Setting("Potions", Boolean.valueOf(true), "Your Speed"));
/*  53 */   private final Setting<Boolean> potionIcons = register(new Setting("PotionIcons", Boolean.valueOf(false)));
/*  54 */   private final Setting<Boolean> server = register(new Setting("IP", Boolean.valueOf(true), "Shows the server"));
/*  55 */   private final Setting<Boolean> ping = register(new Setting("Ping", Boolean.valueOf(true), "Your response time to the server."));
/*  56 */   private final Setting<Boolean> tps = register(new Setting("TPS", Boolean.valueOf(true), "Ticks per second of the server."));
/*  57 */   private final Setting<Boolean> fps = register(new Setting("FPS", Boolean.valueOf(true), "Your frames per second."));
/*  58 */   private final Setting<Boolean> lag = register(new Setting("LagNotifier", Boolean.valueOf(true), "The time"));
/*  59 */   private final Setting<Boolean> hitMarkers = register(new Setting("HitMarkers", Boolean.valueOf(false)));
/*  60 */   public Setting<Boolean> time = register(new Setting("Time", Boolean.valueOf(true), "The time"));
/*  61 */   public Setting<Boolean> chattime = register(new Setting("ChatTime", Boolean.valueOf(true), "Prefixes chat messages with the time"));
/*  62 */   public Setting<Boolean> Info = register(new Setting("Info", Boolean.valueOf(false)));
/*  63 */   private final Setting<Boolean> crystals = register(new Setting("Crystals", Boolean.valueOf(true), v -> ((Boolean)this.Info.getValue()).booleanValue()));
/*  64 */   public Setting<Integer> crystalX = register(new Setting("CrystalX", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(900), v -> (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.crystals.getValue()).booleanValue())));
/*  65 */   public Setting<Integer> crystalY = register(new Setting("CrystalY", Integer.valueOf(122), Integer.valueOf(0), Integer.valueOf(530), v -> (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.crystals.getValue()).booleanValue())));
/*  66 */   public Setting<Integer> expX = register(new Setting("ExpX", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(900), v -> (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.crystals.getValue()).booleanValue())));
/*  67 */   public Setting<Integer> expY = register(new Setting("ExpY", Integer.valueOf(128), Integer.valueOf(0), Integer.valueOf(530), v -> (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.crystals.getValue()).booleanValue())));
/*  68 */   private final Setting<Boolean> exp = register(new Setting("Exp", Boolean.valueOf(true), v -> ((Boolean)this.Info.getValue()).booleanValue()));
/*  69 */   private final Setting<Boolean> gapples = register(new Setting("Gapples", Boolean.valueOf(true), v -> ((Boolean)this.Info.getValue()).booleanValue()));
/*  70 */   public Setting<Integer> GapplesX = register(new Setting("GapplesX", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(900), v -> (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.gapples.getValue()).booleanValue())));
/*  71 */   public Setting<Integer> GapplesY = register(new Setting("GapplesY", Integer.valueOf(135), Integer.valueOf(0), Integer.valueOf(530), v -> (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.gapples.getValue()).booleanValue())));
/*  72 */   public Setting<Boolean> textRadar = register(new Setting("TextRadar", Boolean.valueOf(true)));
/*  73 */   public Setting<Integer> ranger = register(new Setting("Ranger", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(150), v -> ((Boolean)this.textRadar.getValue()).booleanValue()));
/*  74 */   public Setting<Integer> textRadarX = register(new Setting("TextRadarX", Integer.valueOf(440), Integer.valueOf(0), Integer.valueOf(700), v -> ((Boolean)this.textRadar.getValue()).booleanValue()));
/*  75 */   public Setting<Integer> textRadarY = register(new Setting("TextRadarY", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500), v -> ((Boolean)this.textRadar.getValue()).booleanValue()));
/*  76 */   public Setting<Boolean> FriendList = register(new Setting("FriendList", Boolean.valueOf(false)));
/*  77 */   public Setting<Integer> FriendListY = register(new Setting("FriendListY", Integer.valueOf(173), Integer.valueOf(0), Integer.valueOf(530), v -> ((Boolean)this.FriendList.getValue()).booleanValue()));
/*  78 */   public Setting<Boolean> playerViewer = register(new Setting("PlayerViewer", Boolean.valueOf(false)));
/*  79 */   public Setting<Integer> playerViewerX = register(new Setting("PlayerX", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(700), v -> ((Boolean)this.playerViewer.getValue()).booleanValue()));
/*  80 */   public Setting<Integer> playerViewerY = register(new Setting("PlayerY", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(500), v -> ((Boolean)this.playerViewer.getValue()).booleanValue()));
/*  81 */   public Setting<Float> playerScale = register(new Setting("PlayerScale", Float.valueOf(0.7F), Float.valueOf(0.1F), Float.valueOf(2.0F), v -> ((Boolean)this.playerViewer.getValue()).booleanValue()));
/*  82 */   public Setting<TextUtil.Color> bracketColor = register(new Setting("BracketColor", TextUtil.Color.BLUE));
/*  83 */   public Setting<TextUtil.Color> commandColor = register(new Setting("NameColor", TextUtil.Color.BLUE));
/*  84 */   public Setting<Boolean> notifyToggles = register(new Setting("ChatNotify", Boolean.valueOf(true), "notifys in chat"));
/*  85 */   public Setting<Boolean> magenDavid = register(new Setting("FutureGui", Boolean.valueOf(true), "draws magen david"));
/*  86 */   public Setting<RenderingMode> renderingMode = register(new Setting("Ordering", RenderingMode.Length));
/*  87 */   public Setting<Integer> waterMarkY = register(new Setting("WatermarkPosY", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(20), v -> ((Boolean)this.waterMark.getValue()).booleanValue()));
/*  88 */   public Setting<Integer> lagTime = register(new Setting("LagTime", Integer.valueOf(1000), Integer.valueOf(0), Integer.valueOf(2000)));
/*     */   private int color;
/*     */   private boolean shouldIncrement;
/*     */   private int hitMarkerTimer;
/*     */   
/*     */   public HUD() {
/*  94 */     super("HUD", "HUD Elements rendered on your screen", Module.Category.CLIENT, true, false, false);
/*  95 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 100 */     if (this.shouldIncrement)
/* 101 */       this.hitMarkerTimer++; 
/* 102 */     if (this.hitMarkerTimer == 10) {
/* 103 */       this.hitMarkerTimer = 0;
/* 104 */       this.shouldIncrement = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPotionEffect(PotionRenderHUDEvent event) {
/* 110 */     if (((Boolean)this.potionIcons.getValue()).booleanValue()) {
/* 111 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientChatReceived(ClientChatReceivedEvent event) {
/* 117 */     if (((Boolean)this.chattime.getValue()).booleanValue()) {
/* 118 */       Date date = new Date();
/* 119 */       SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
/* 120 */       String strDate = dateFormatter.format(date);
/* 121 */       TextComponentString time = new TextComponentString(ChatFormatting.LIGHT_PURPLE + "[" + ChatFormatting.DARK_PURPLE + strDate + ChatFormatting.LIGHT_PURPLE + "]" + ChatFormatting.RESET + " ");
/* 122 */       event.setMessage(time.appendSibling(event.getMessage()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {
/* 127 */     if (fullNullCheck())
/*     */       return; 
/* 129 */     int width = this.renderer.scaledWidth;
/* 130 */     int height = this.renderer.scaledHeight;
/* 131 */     this.color = ColorUtil.toRGBA(((Integer)ClickGui.INSTANCE.red.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.green.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.blue.getValue()).intValue());
/* 132 */     if (((Boolean)this.waterMark.getValue()).booleanValue()) {
/* 133 */       String string = "ab-Hack v0.0.1";
/* 134 */       if (((Boolean)ClickGui.INSTANCE.rainbow.getValue()).booleanValue()) {
/*     */         
/* 136 */         int[] arrayOfInt = { 1 };
/* 137 */         char[] stringToCharArray = string.toCharArray();
/* 138 */         float f = 0.0F;
/* 139 */         for (char c : stringToCharArray) {
/* 140 */           this.renderer.drawString(String.valueOf(c), 2.0F + f, ((Integer)this.waterMarkY.getValue()).intValue(), ColorUtil.rainbow(arrayOfInt[0] * ((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRGB(), true);
/* 141 */           f += this.renderer.getStringWidth(String.valueOf(c));
/* 142 */           arrayOfInt[0] = arrayOfInt[0] + 1;
/*     */         } 
/*     */       } else {
/*     */         
/* 146 */         this.renderer.drawString(string, 2.0F, ((Integer)this.waterMarkY.getValue()).intValue(), this.color, true);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 151 */     String Crystals = "Crystals: " + InventoryUtil.getItemCount(Items.END_CRYSTAL);
/* 152 */     String Exp = "Exp: " + InventoryUtil.getItemCount(Items.EXPERIENCE_BOTTLE);
/* 153 */     String Gapples = "Gapples: " + InventoryUtil.getItemCount(Items.GOLDEN_APPLE);
/* 154 */     this.color = ColorUtil.toRGBA(((Integer)ClickGui.INSTANCE.red.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.green.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.blue.getValue()).intValue());
/*     */     
/* 156 */     if (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.crystals.getValue()).booleanValue()) {
/* 157 */       this.renderer.drawString(Crystals, ((Integer)this.crystalX.getValue()).intValue(), ((Integer)this.crystalY.getValue()).intValue(), this.color, true);
/*     */     }
/* 159 */     if (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.exp.getValue()).booleanValue()) {
/* 160 */       this.renderer.drawString(Exp, ((Integer)this.expX.getValue()).intValue(), ((Integer)this.expY.getValue()).intValue(), this.color, true);
/*     */     }
/* 162 */     if (((Boolean)this.Info.getValue()).booleanValue() && ((Boolean)this.gapples.getValue()).booleanValue()) {
/* 163 */       this.renderer.drawString(Gapples, ((Integer)this.GapplesX.getValue()).intValue(), ((Integer)this.GapplesY.getValue()).intValue(), this.color, true);
/*     */     }
/*     */ 
/*     */     
/* 167 */     if (((Boolean)this.FriendList.getValue()).booleanValue()) {
/* 168 */       renderFriends();
/*     */     }
/*     */     
/* 171 */     if (((Boolean)this.textRadar.getValue()).booleanValue()) {
/* 172 */       drawTextRadar(((Integer)this.textRadarX.getValue()).intValue(), ((Integer)this.textRadarY.getValue()).intValue());
/*     */     }
/*     */     
/* 175 */     int[] counter1 = { 1 };
/* 176 */     int j = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && !((Boolean)this.renderingUp.getValue()).booleanValue()) ? 14 : 0;
/* 177 */     if (((Boolean)this.arrayList.getValue()).booleanValue())
/* 178 */       if (((Boolean)this.renderingUp.getValue()).booleanValue()) {
/* 179 */         if (this.renderingMode.getValue() == RenderingMode.ABC) {
/* 180 */           for (int k = 0; k < OyVey.moduleManager.sortedModulesABC.size(); k++) {
/* 181 */             String str = OyVey.moduleManager.sortedModulesABC.get(k);
/* 182 */             this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str)), (2 + j * 10), getArrayColor(counter1[0]), true);
/* 183 */             j++;
/* 184 */             counter1[0] = counter1[0] + 1;
/*     */           } 
/*     */         } else {
/* 187 */           for (int k = 0; k < OyVey.moduleManager.sortedModules.size(); k++) {
/* 188 */             Module module = OyVey.moduleManager.sortedModules.get(k);
/* 189 */             String str = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
/* 190 */             this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str)), (2 + j * 10), getArrayColor(counter1[0]), true);
/* 191 */             j++;
/* 192 */             counter1[0] = counter1[0] + 1;
/*     */           } 
/*     */         } 
/* 195 */       } else if (this.renderingMode.getValue() == RenderingMode.ABC) {
/* 196 */         for (int k = 0; k < OyVey.moduleManager.sortedModulesABC.size(); k++) {
/* 197 */           String str = OyVey.moduleManager.sortedModulesABC.get(k);
/* 198 */           j += 10;
/* 199 */           this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str)), (height - j), getArrayColor(counter1[0]), true);
/* 200 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/*     */       } else {
/* 203 */         for (int k = 0; k < OyVey.moduleManager.sortedModules.size(); k++) {
/* 204 */           Module module = OyVey.moduleManager.sortedModules.get(k);
/* 205 */           String str = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
/* 206 */           j += 10;
/* 207 */           this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str)), (height - j), getArrayColor(counter1[0]), true);
/* 208 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/*     */       }  
/* 211 */     String grayString = ((Boolean)this.grayNess.getValue()).booleanValue() ? String.valueOf(ChatFormatting.GRAY) : "";
/* 212 */     int i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && ((Boolean)this.renderingUp.getValue()).booleanValue()) ? 13 : (((Boolean)this.renderingUp.getValue()).booleanValue() ? -2 : 0);
/* 213 */     if (((Boolean)this.renderingUp.getValue()).booleanValue()) {
/* 214 */       if (((Boolean)this.potions.getValue()).booleanValue()) {
/* 215 */         List<PotionEffect> effects = new ArrayList<>((Minecraft.getMinecraft()).player.getActivePotionEffects());
/* 216 */         for (PotionEffect potionEffect : effects) {
/* 217 */           String str = OyVey.potionManager.getColoredPotionString(potionEffect);
/* 218 */           i += 10;
/* 219 */           this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (height - 2 - i), potionEffect.getPotion().getLiquidColor(), true);
/*     */         } 
/*     */       } 
/* 222 */       if (((Boolean)this.server.getValue()).booleanValue()) {
/* 223 */         String sText = grayString + "IP " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : ((ServerData)Objects.requireNonNull((T)mc.getCurrentServerData())).serverIP);
/* 224 */         i += 10;
/* 225 */         this.renderer.drawString(sText, (width - this.renderer.getStringWidth(sText) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 226 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 228 */       if (((Boolean)this.speed.getValue()).booleanValue()) {
/* 229 */         String str = grayString + "Speed " + ChatFormatting.WHITE + OyVey.speedManager.getSpeedKpH() + " km/h";
/* 230 */         i += 10;
/* 231 */         this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 232 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 234 */       if (((Boolean)this.time.getValue()).booleanValue()) {
/* 235 */         String str = grayString + "Time " + ChatFormatting.WHITE + (new SimpleDateFormat("h:mm a")).format(new Date());
/* 236 */         i += 10;
/* 237 */         this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 238 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 240 */       if (((Boolean)this.tps.getValue()).booleanValue()) {
/* 241 */         String str = grayString + "TPS " + ChatFormatting.WHITE + OyVey.serverManager.getTPS();
/* 242 */         i += 10;
/* 243 */         this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 244 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 246 */       String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
/* 247 */       String str1 = grayString + "Ping " + ChatFormatting.WHITE + OyVey.serverManager.getPing();
/* 248 */       if (this.renderer.getStringWidth(str1) > this.renderer.getStringWidth(fpsText)) {
/* 249 */         if (((Boolean)this.ping.getValue()).booleanValue()) {
/* 250 */           i += 10;
/* 251 */           this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 252 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/* 254 */         if (((Boolean)this.fps.getValue()).booleanValue()) {
/* 255 */           i += 10;
/* 256 */           this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 257 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/*     */       } else {
/* 260 */         if (((Boolean)this.fps.getValue()).booleanValue()) {
/* 261 */           i += 10;
/* 262 */           this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 263 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/* 265 */         if (((Boolean)this.ping.getValue()).booleanValue()) {
/* 266 */           i += 10;
/* 267 */           this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (height - 2 - i), getArrayColor(counter1[0]), true);
/* 268 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 272 */       if (((Boolean)this.potions.getValue()).booleanValue()) {
/* 273 */         List<PotionEffect> effects = new ArrayList<>((Minecraft.getMinecraft()).player.getActivePotionEffects());
/* 274 */         for (PotionEffect potionEffect : effects) {
/* 275 */           String str = OyVey.potionManager.getColoredPotionString(potionEffect);
/* 276 */           this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (2 + i++ * 10), potionEffect.getPotion().getLiquidColor(), true);
/*     */         } 
/*     */       } 
/* 279 */       if (((Boolean)this.server.getValue()).booleanValue()) {
/* 280 */         String sText = grayString + "IP " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : ((ServerData)Objects.requireNonNull((T)mc.getCurrentServerData())).serverIP);
/* 281 */         this.renderer.drawString(sText, (width - this.renderer.getStringWidth(sText) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 282 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 284 */       if (((Boolean)this.speed.getValue()).booleanValue()) {
/* 285 */         String str = grayString + "Speed " + ChatFormatting.WHITE + OyVey.speedManager.getSpeedKpH() + " km/h";
/* 286 */         this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 287 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 289 */       if (((Boolean)this.time.getValue()).booleanValue()) {
/* 290 */         String str = grayString + "Time " + ChatFormatting.WHITE + (new SimpleDateFormat("h:mm a")).format(new Date());
/* 291 */         this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 292 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 294 */       if (((Boolean)this.tps.getValue()).booleanValue()) {
/* 295 */         String str = grayString + "TPS " + ChatFormatting.WHITE + OyVey.serverManager.getTPS();
/* 296 */         this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 297 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/* 299 */       String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
/* 300 */       String str1 = grayString + "Ping " + ChatFormatting.WHITE + OyVey.serverManager.getPing();
/* 301 */       if (this.renderer.getStringWidth(str1) > this.renderer.getStringWidth(fpsText)) {
/* 302 */         if (((Boolean)this.ping.getValue()).booleanValue()) {
/* 303 */           this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 304 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/* 306 */         if (((Boolean)this.fps.getValue()).booleanValue()) {
/* 307 */           this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 308 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/*     */       } else {
/* 311 */         if (((Boolean)this.fps.getValue()).booleanValue()) {
/* 312 */           this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 313 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/* 315 */         if (((Boolean)this.ping.getValue()).booleanValue()) {
/* 316 */           this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (2 + i++ * 10), getArrayColor(counter1[0]), true);
/* 317 */           counter1[0] = counter1[0] + 1;
/*     */         } 
/*     */       } 
/*     */     } 
/* 321 */     boolean inHell = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell");
/* 322 */     int posX = (int)mc.player.posX;
/* 323 */     int posY = (int)mc.player.posY;
/* 324 */     int posZ = (int)mc.player.posZ;
/* 325 */     float nether = !inHell ? 0.125F : 8.0F;
/* 326 */     int hposX = (int)(mc.player.posX * nether);
/* 327 */     int hposZ = (int)(mc.player.posZ * nether);
/* 328 */     i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat) ? 14 : 0;
/* 329 */     String coordinates = ChatFormatting.WHITE + "XYZ " + ChatFormatting.RESET + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]" + ChatFormatting.RESET) : (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]"));
/* 330 */     String direction = ((Boolean)this.direction.getValue()).booleanValue() ? OyVey.rotationManager.getDirection4D(false) : "";
/* 331 */     String coords = ((Boolean)this.coords.getValue()).booleanValue() ? coordinates : "";
/* 332 */     i += 10;
/* 333 */     if (((Boolean)ClickGui.INSTANCE.rainbow.getValue()).booleanValue()) {
/* 334 */       String rainbowCoords = ((Boolean)this.coords.getValue()).booleanValue() ? ("XYZ " + posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]") : "";
/*     */       
/* 336 */       int[] counter2 = { 1 };
/* 337 */       char[] stringToCharArray = direction.toCharArray();
/* 338 */       float s = 0.0F;
/* 339 */       for (char c : stringToCharArray) {
/* 340 */         this.renderer.drawString(String.valueOf(c), 2.0F + s, (height - i - 11), ColorUtil.rainbow(counter2[0] * ((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRGB(), true);
/* 341 */         s += this.renderer.getStringWidth(String.valueOf(c));
/* 342 */         counter2[0] = counter2[0] + 1;
/*     */       } 
/* 344 */       int[] counter3 = { 1 };
/* 345 */       char[] stringToCharArray2 = rainbowCoords.toCharArray();
/* 346 */       float u = 0.0F;
/* 347 */       for (char c : stringToCharArray2) {
/* 348 */         this.renderer.drawString(String.valueOf(c), 2.0F + u, (height - i), ColorUtil.rainbow(counter3[0] * ((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRGB(), true);
/* 349 */         u += this.renderer.getStringWidth(String.valueOf(c));
/* 350 */         counter3[0] = counter3[0] + 1;
/*     */       } 
/*     */     } else {
/*     */       
/* 354 */       this.renderer.drawString(direction, 2.0F, (height - i - 11), this.color, true);
/* 355 */       this.renderer.drawString(coords, 2.0F, (height - i), this.color, true);
/*     */     } 
/* 357 */     if (((Boolean)this.armor.getValue()).booleanValue())
/* 358 */       renderArmorHUD(true); 
/* 359 */     if (((Boolean)this.totems.getValue()).booleanValue())
/* 360 */       renderTotemHUD(); 
/* 361 */     if (((Boolean)this.greeter.getValue()).booleanValue())
/* 362 */       renderGreeter(); 
/* 363 */     if (((Boolean)this.lag.getValue()).booleanValue())
/* 364 */       renderLag(); 
/* 365 */     if (((Boolean)this.playerViewer.getValue()).booleanValue()) {
/* 366 */       drawPlayer();
/*     */     }
/* 368 */     if (!((Boolean)this.hitMarkers.getValue()).booleanValue())
/*     */       return; 
/* 370 */     if (this.hitMarkerTimer <= 0)
/*     */       return; 
/* 372 */     drawHitMarkers();
/*     */   }
/*     */   
/*     */   public void renderGreeter() {
/* 376 */     int width = this.renderer.scaledWidth;
/* 377 */     String text = "";
/* 378 */     if (((Boolean)this.greeter.getValue()).booleanValue())
/* 379 */       text = text + MathUtil.getTimeOfDay() + mc.player.getDisplayNameString(); 
/* 380 */     if (((Boolean)ClickGui.INSTANCE.rainbow.getValue()).booleanValue()) {
/*     */       
/* 382 */       int[] counter1 = { 1 };
/* 383 */       char[] stringToCharArray = text.toCharArray();
/* 384 */       float i = 0.0F;
/* 385 */       for (char c : stringToCharArray) {
/* 386 */         this.renderer.drawString(String.valueOf(c), width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F + i, 2.0F, ColorUtil.rainbow(counter1[0] * ((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRGB(), true);
/* 387 */         i += this.renderer.getStringWidth(String.valueOf(c));
/* 388 */         counter1[0] = counter1[0] + 1;
/*     */       } 
/*     */     } else {
/*     */       
/* 392 */       this.renderer.drawString(text, width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F, 2.0F, this.color, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderLag() {
/* 397 */     int width = this.renderer.scaledWidth;
/* 398 */     if (OyVey.serverManager.isServerNotResponding()) {
/* 399 */       String text = ChatFormatting.RED + "Server not responding " + MathUtil.round((float)OyVey.serverManager.serverRespondingTime() / 1000.0F, 1) + "s.";
/* 400 */       this.renderer.drawString(text, width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F, 20.0F, this.color, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawHitMarkers() {
/* 405 */     ScaledResolution resolution = new ScaledResolution(mc);
/* 406 */     RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F - 4.0F, resolution.getScaledHeight() / 2.0F - 4.0F, resolution.getScaledWidth() / 2.0F - 8.0F, resolution.getScaledHeight() / 2.0F - 8.0F, 1.0F, ColorUtil.toRGBA(255, 255, 255, 255));
/* 407 */     RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F + 4.0F, resolution.getScaledHeight() / 2.0F - 4.0F, resolution.getScaledWidth() / 2.0F + 8.0F, resolution.getScaledHeight() / 2.0F - 8.0F, 1.0F, ColorUtil.toRGBA(255, 255, 255, 255));
/* 408 */     RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F - 4.0F, resolution.getScaledHeight() / 2.0F + 4.0F, resolution.getScaledWidth() / 2.0F - 8.0F, resolution.getScaledHeight() / 2.0F + 8.0F, 1.0F, ColorUtil.toRGBA(255, 255, 255, 255));
/* 409 */     RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F + 4.0F, resolution.getScaledHeight() / 2.0F + 4.0F, resolution.getScaledWidth() / 2.0F + 8.0F, resolution.getScaledHeight() / 2.0F + 8.0F, 1.0F, ColorUtil.toRGBA(255, 255, 255, 255));
/*     */   }
/*     */   
/*     */   public void renderTotemHUD() {
/* 413 */     int width = this.renderer.scaledWidth;
/* 414 */     int height = this.renderer.scaledHeight;
/* 415 */     int totems = InventoryUtil.getItemCount(Items.TOTEM_OF_UNDYING);
/* 416 */     if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
/* 417 */       totems += mc.player.getHeldItemOffhand().getCount(); 
/* 418 */     if (totems > 0) {
/* 419 */       GlStateManager.enableTexture2D();
/* 420 */       int i = width / 2;
/* 421 */       int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
/* 422 */       int x = i - 189 + 180 + 2;
/* 423 */       GlStateManager.enableDepth();
/* 424 */       RenderUtil.itemRender.zLevel = 200.0F;
/* 425 */       RenderUtil.itemRender.renderItemAndEffectIntoGUI(totem, x, y);
/* 426 */       RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, totem, x, y, "");
/* 427 */       RenderUtil.itemRender.zLevel = 0.0F;
/* 428 */       GlStateManager.enableTexture2D();
/* 429 */       GlStateManager.disableLighting();
/* 430 */       GlStateManager.disableDepth();
/* 431 */       this.renderer.drawStringWithShadow(totems + "", (x + 19 - 2 - this.renderer.getStringWidth(totems + "")), (y + 9), 16777215);
/* 432 */       GlStateManager.enableDepth();
/* 433 */       GlStateManager.disableLighting();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderArmorHUD(boolean percent) {
/* 438 */     int width = this.renderer.scaledWidth;
/* 439 */     int height = this.renderer.scaledHeight;
/* 440 */     GlStateManager.enableTexture2D();
/* 441 */     int i = width / 2;
/* 442 */     int iteration = 0;
/* 443 */     int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
/* 444 */     for (ItemStack is : mc.player.inventory.armorInventory) {
/* 445 */       iteration++;
/* 446 */       if (is.isEmpty())
/* 447 */         continue;  int x = i - 90 + (9 - iteration) * 20 + 2;
/* 448 */       GlStateManager.enableDepth();
/* 449 */       RenderUtil.itemRender.zLevel = 200.0F;
/* 450 */       RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
/* 451 */       RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
/* 452 */       RenderUtil.itemRender.zLevel = 0.0F;
/* 453 */       GlStateManager.enableTexture2D();
/* 454 */       GlStateManager.disableLighting();
/* 455 */       GlStateManager.disableDepth();
/* 456 */       String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
/* 457 */       this.renderer.drawStringWithShadow(s, (x + 19 - 2 - this.renderer.getStringWidth(s)), (y + 9), 16777215);
/* 458 */       if (!percent)
/*     */         continue; 
/* 460 */       float green = (is.getMaxDamage() - is.getItemDamage()) / is.getMaxDamage();
/* 461 */       float red = 1.0F - green;
/* 462 */       int dmg = 100 - (int)(red * 100.0F);
/* 463 */       if (dmg == -2147483547)
/* 464 */         dmg = 100; 
/* 465 */       this.renderer.drawStringWithShadow(dmg + "", (x + 8 - this.renderer.getStringWidth(dmg + "") / 2), (y - 11), ColorUtil.toRGBA((int)(red * 255.0F), (int)(green * 255.0F), 0));
/*     */     } 
/* 467 */     GlStateManager.enableDepth();
/* 468 */     GlStateManager.disableLighting();
/*     */   }
/*     */   
/*     */   public void drawPlayer() {
/* 472 */     EntityPlayerSP ent = mc.player;
/* 473 */     GlStateManager.pushMatrix();
/* 474 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 475 */     RenderHelper.enableStandardItemLighting();
/* 476 */     GlStateManager.enableAlpha();
/* 477 */     GlStateManager.shadeModel(7424);
/* 478 */     GlStateManager.enableAlpha();
/* 479 */     GlStateManager.enableDepth();
/* 480 */     GlStateManager.rotate(0.0F, 0.0F, 5.0F, 0.0F);
/* 481 */     GlStateManager.enableColorMaterial();
/* 482 */     GlStateManager.pushMatrix();
/* 483 */     GlStateManager.translate((((Integer)this.playerViewerX.getValue()).intValue() + 25), (((Integer)this.playerViewerY.getValue()).intValue() + 25), 50.0F);
/* 484 */     GlStateManager.scale(-50.0F * ((Float)this.playerScale.getValue()).floatValue(), 50.0F * ((Float)this.playerScale.getValue()).floatValue(), 50.0F * ((Float)this.playerScale.getValue()).floatValue());
/* 485 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 486 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/* 487 */     RenderHelper.enableStandardItemLighting();
/* 488 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 489 */     GlStateManager.rotate(-((float)Math.atan((((Integer)this.playerViewerY.getValue()).intValue() / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
/* 490 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 491 */     RenderManager rendermanager = mc.getRenderManager();
/* 492 */     rendermanager.setPlayerViewY(180.0F);
/* 493 */     rendermanager.setRenderShadow(false);
/*     */     try {
/* 495 */       rendermanager.renderEntity((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
/* 496 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 499 */     rendermanager.setRenderShadow(true);
/* 500 */     GlStateManager.popMatrix();
/* 501 */     RenderHelper.disableStandardItemLighting();
/* 502 */     GlStateManager.disableRescaleNormal();
/* 503 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 504 */     GlStateManager.disableTexture2D();
/* 505 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 506 */     GlStateManager.depthFunc(515);
/* 507 */     GlStateManager.resetColor();
/* 508 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayer(AttackEntityEvent event) {
/* 513 */     this.shouldIncrement = true;
/*     */   }
/*     */   
/*     */   public void onLoad() {
/* 517 */     OyVey.commandManager.setClientMessage(getCommandMessage());
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSettingChange(ClientEvent event) {
/* 522 */     if (event.getStage() == 2 && 
/* 523 */       equals(event.getSetting().getFeature()))
/* 524 */       OyVey.commandManager.setClientMessage(getCommandMessage()); 
/*     */   }
/*     */   
/*     */   public int getArrayColor(int color) {
/* 528 */     if (((Boolean)ClickGui.INSTANCE.rainbow.getValue()).booleanValue()) {
/* 529 */       if (ClickGui.INSTANCE.arraymode.getValue() == ClickGui.RainbowArray.Up)
/* 530 */         return ColorUtil.rainbow(color * ((Integer)ClickGui.INSTANCE.rainbowHue.getValue()).intValue()).getRGB(); 
/* 531 */       if (ClickGui.INSTANCE.arraymode.getValue() == ClickGui.RainbowArray.Unique) {
/* 532 */         return ColorUtil.toRGBA(color * 1000 % 255, color * 3000 % 255, color * 7000 % 255);
/*     */       }
/*     */     } 
/* 535 */     return ColorUtil.toRGBA(((Integer)ClickGui.INSTANCE.red.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.green.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.blue.getValue()).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandMessage() {
/* 541 */     return TextUtil.coloredString("<", (TextUtil.Color)this.bracketColor.getPlannedValue()) + TextUtil.coloredString("ab-Hack", (TextUtil.Color)this.commandColor.getPlannedValue()) + TextUtil.coloredString(">", (TextUtil.Color)this.bracketColor.getPlannedValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderFriends() {
/* 548 */     List<String> friends = new ArrayList<>();
/* 549 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 550 */       if (OyVey.friendManager.isFriend(player.getName()) && !player.equals(mc.player) && abUtil.Freecamcheck((Entity)player))
/* 551 */         friends.add(player.getName()); 
/*     */     } 
/* 553 */     int color = ColorUtil.toRGBA(((Integer)ClickGui.INSTANCE.red.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.green.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.blue.getValue()).intValue());
/* 554 */     int y = ((Integer)this.FriendListY.getValue()).intValue();
/* 555 */     if (friends.isEmpty()) {
/* 556 */       this.renderer.drawString("No friends", 0.0F, y, color, true);
/*     */     } else {
/* 558 */       this.renderer.drawString("Friends:", 0.0F, y, color, true);
/* 559 */       y += 12;
/* 560 */       for (String friend : friends) {
/* 561 */         this.renderer.drawString(friend, 0.0F, y, color, true);
/* 562 */         y += 12;
/* 563 */         color++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTextRadar(int x, int offsety) {
/* 573 */     int y = offsety;
/* 574 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 575 */       if (!player.equals(mc.player) && abUtil.Freecamcheck((Entity)player) && !player.isDead && mc.player.getDistanceSq((Entity)player) <= MathUtil.square(((Integer)this.ranger.getValue()).intValue())) {
/*     */         ChatFormatting color;
/* 577 */         if (EntityUtil.getHealth((Entity)player) > 18.0F) {
/* 578 */           color = ChatFormatting.GREEN;
/* 579 */         } else if (EntityUtil.getHealth((Entity)player) > 16.0F) {
/* 580 */           color = ChatFormatting.DARK_GREEN;
/* 581 */         } else if (EntityUtil.getHealth((Entity)player) > 12.0F) {
/* 582 */           color = ChatFormatting.YELLOW;
/* 583 */         } else if (EntityUtil.getHealth((Entity)player) > 8.0F) {
/* 584 */           color = ChatFormatting.DARK_RED;
/* 585 */         } else if (EntityUtil.getHealth((Entity)player) > 5.0F) {
/* 586 */           color = ChatFormatting.DARK_RED;
/*     */         } else {
/* 588 */           color = ChatFormatting.DARK_RED;
/*     */         } 
/* 590 */         int Distance = (int)Math.sqrt(mc.player.getDistanceSq((Entity)player));
/* 591 */         if (OyVey.friendManager.isFriend(player)) {
/* 592 */           this.renderer.drawString(ChatFormatting.BLUE + player.getName() + ChatFormatting.YELLOW + " -> " + color + (int)EntityUtil.getHealth((Entity)player) + "♥ " + ChatFormatting.WHITE + "[" + Distance + "m]", x, y, 65280, true);
/*     */         } else {
/* 594 */           this.renderer.drawString(ChatFormatting.RED + player.getName() + ChatFormatting.YELLOW + " -> " + color + (int)EntityUtil.getHealth((Entity)player) + "♥ " + ChatFormatting.WHITE + "[" + Distance + "m]", x, y, 65280, true);
/* 595 */         }  int textheight = this.renderer.getFontHeight() + 1;
/*     */         
/* 597 */         y += textheight;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum RenderingMode {
/* 603 */     Length, ABC;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\client\HUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.Objects;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.DamageUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.render.ColorHolder;
/*     */ import me.abHack.util.render.TextUtil;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameTags
/*     */   extends Module
/*     */ {
/*  33 */   private static NameTags INSTANCE = new NameTags();
/*  34 */   private final Setting<Boolean> rect = register(new Setting("Rectangle", Boolean.valueOf(true)));
/*  35 */   private final Setting<Boolean> armor = register(new Setting("Armor", Boolean.valueOf(true)));
/*  36 */   private final Setting<Boolean> reversed = register(new Setting("ArmorReversed", Boolean.valueOf(false), v -> ((Boolean)this.armor.getValue()).booleanValue()));
/*  37 */   private final Setting<Boolean> health = register(new Setting("Health", Boolean.valueOf(true)));
/*  38 */   private final Setting<Boolean> ping = register(new Setting("Ping", Boolean.valueOf(true)));
/*  39 */   private final Setting<Boolean> gamemode = register(new Setting("Gamemode", Boolean.valueOf(true)));
/*  40 */   private final Setting<Boolean> entityID = register(new Setting("EntityID", Boolean.valueOf(false)));
/*  41 */   private final Setting<Boolean> heldStackName = register(new Setting("StackName", Boolean.valueOf(true)));
/*  42 */   private final Setting<Boolean> max = register(new Setting("Max", Boolean.valueOf(false)));
/*  43 */   private final Setting<Boolean> maxText = register(new Setting("NoMaxText", Boolean.valueOf(false), v -> ((Boolean)this.max.getValue()).booleanValue()));
/*  44 */   private final Setting<Integer> Mred = register(new Setting("Max-Red", Integer.valueOf(178), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.max.getValue()).booleanValue()));
/*  45 */   private final Setting<Integer> Mgreen = register(new Setting("Max-Green", Integer.valueOf(52), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.max.getValue()).booleanValue()));
/*  46 */   private final Setting<Integer> Mblue = register(new Setting("Max-Blue", Integer.valueOf(57), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.max.getValue()).booleanValue()));
/*  47 */   private final Setting<Float> size = register(new Setting("Size", Float.valueOf(2.5F), Float.valueOf(0.1F), Float.valueOf(15.0F)));
/*  48 */   private final Setting<Boolean> scaleing = register(new Setting("Scale", Boolean.valueOf(true)));
/*  49 */   private final Setting<Boolean> smartScale = register(new Setting("SmartScale", Boolean.valueOf(true), v -> ((Boolean)this.scaleing.getValue()).booleanValue()));
/*  50 */   private final Setting<Float> factor = register(new Setting("Factor", Float.valueOf(0.3F), Float.valueOf(0.1F), Float.valueOf(1.0F), v -> ((Boolean)this.scaleing.getValue()).booleanValue()));
/*  51 */   private final Setting<Boolean> textcolor = register(new Setting("TextColor", Boolean.valueOf(true)));
/*  52 */   private final Setting<Boolean> NCRainbow = register(new Setting("Text-Rainbow", Boolean.valueOf(true), v -> ((Boolean)this.textcolor.getValue()).booleanValue()));
/*  53 */   private final Setting<Integer> NCred = register(new Setting("Text-Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.textcolor.getValue()).booleanValue()));
/*  54 */   private final Setting<Integer> NCgreen = register(new Setting("Text-Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.textcolor.getValue()).booleanValue()));
/*  55 */   private final Setting<Integer> NCblue = register(new Setting("Text-Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.textcolor.getValue()).booleanValue()));
/*  56 */   private final Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/*  57 */   private final Setting<Boolean> ORainbow = register(new Setting("Outline-Rainbow", Boolean.valueOf(false), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*  58 */   private final Setting<Float> Owidth = register(new Setting("Outline-Width", Float.valueOf(1.3F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*  59 */   private final Setting<Integer> Ored = register(new Setting("Outline-Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*  60 */   private final Setting<Integer> Ogreen = register(new Setting("Outline-Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*  61 */   private final Setting<Integer> Oblue = register(new Setting("Outline-Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*  62 */   private final Setting<Boolean> friendcolor = register(new Setting("FriendColor", Boolean.valueOf(true)));
/*  63 */   private final Setting<Boolean> FCRainbow = register(new Setting("Friend-Rainbow", Boolean.valueOf(false), v -> ((Boolean)this.friendcolor.getValue()).booleanValue()));
/*  64 */   private final Setting<Integer> FCred = register(new Setting("Friend-Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.friendcolor.getValue()).booleanValue()));
/*  65 */   private final Setting<Integer> FCgreen = register(new Setting("Friend-Green", Integer.valueOf(213), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.friendcolor.getValue()).booleanValue()));
/*  66 */   private final Setting<Integer> FCblue = register(new Setting("Friend-Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.friendcolor.getValue()).booleanValue()));
/*  67 */   private final Setting<Boolean> FORainbow = register(new Setting("FriendOutline-Rainbow", Boolean.valueOf(false), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.friendcolor.getValue()).booleanValue())));
/*  68 */   private final Setting<Integer> FOred = register(new Setting("FriendOutline-Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.friendcolor.getValue()).booleanValue())));
/*  69 */   private final Setting<Integer> FOgreen = register(new Setting("FriendOutline-Green", Integer.valueOf(213), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.friendcolor.getValue()).booleanValue())));
/*  70 */   private final Setting<Integer> FOblue = register(new Setting("FriendOutline-Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.friendcolor.getValue()).booleanValue())));
/*  71 */   private final Setting<Boolean> sneakcolor = register(new Setting("Sneak", Boolean.valueOf(false)));
/*  72 */   private final Setting<Boolean> sneak = register(new Setting("EnableSneak", Boolean.valueOf(true), v -> ((Boolean)this.sneakcolor.getValue()).booleanValue()));
/*  73 */   private final Setting<Boolean> SCRainbow = register(new Setting("Sneak-Rainbow", Boolean.valueOf(false), v -> ((Boolean)this.sneakcolor.getValue()).booleanValue()));
/*  74 */   private final Setting<Integer> SCred = register(new Setting("Sneak-Red", Integer.valueOf(245), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.sneakcolor.getValue()).booleanValue()));
/*  75 */   private final Setting<Integer> SCgreen = register(new Setting("Sneak-Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.sneakcolor.getValue()).booleanValue()));
/*  76 */   private final Setting<Integer> SCblue = register(new Setting("Sneak-Blue", Integer.valueOf(122), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.sneakcolor.getValue()).booleanValue()));
/*  77 */   private final Setting<Boolean> SORainbow = register(new Setting("SneakOutline-Rainbow", Boolean.valueOf(false), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.sneakcolor.getValue()).booleanValue())));
/*  78 */   private final Setting<Integer> SOred = register(new Setting("SneakOutline-Red", Integer.valueOf(245), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.sneakcolor.getValue()).booleanValue())));
/*  79 */   private final Setting<Integer> SOgreen = register(new Setting("SneakOutline-Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.sneakcolor.getValue()).booleanValue())));
/*  80 */   private final Setting<Integer> SOblue = register(new Setting("SneakOutline-Blue", Integer.valueOf(122), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.sneakcolor.getValue()).booleanValue())));
/*  81 */   private final Setting<Boolean> invisiblescolor = register(new Setting("InvisiblesColor", Boolean.valueOf(false)));
/*  82 */   private final Setting<Boolean> invisibles = register(new Setting("EnableInvisibles", Boolean.valueOf(true), v -> ((Boolean)this.invisiblescolor.getValue()).booleanValue()));
/*  83 */   private final Setting<Boolean> ICRainbow = register(new Setting("Invisible-Rainbow", Boolean.valueOf(false), v -> ((Boolean)this.invisiblescolor.getValue()).booleanValue()));
/*  84 */   private final Setting<Integer> ICred = register(new Setting("Invisible-Red", Integer.valueOf(148), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.invisiblescolor.getValue()).booleanValue()));
/*  85 */   private final Setting<Integer> ICgreen = register(new Setting("Invisible-Green", Integer.valueOf(148), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.invisiblescolor.getValue()).booleanValue()));
/*  86 */   private final Setting<Integer> ICblue = register(new Setting("Invisible-Blue", Integer.valueOf(148), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.invisiblescolor.getValue()).booleanValue()));
/*  87 */   private final Setting<Boolean> IORainbow = register(new Setting("InvisibleOutline-Rainbow", Boolean.valueOf(false), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.invisiblescolor.getValue()).booleanValue())));
/*  88 */   private final Setting<Integer> IOred = register(new Setting("InvisibleOutline-Red", Integer.valueOf(148), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.invisiblescolor.getValue()).booleanValue())));
/*  89 */   private final Setting<Integer> IOgreen = register(new Setting("InvisibleOutline-Green", Integer.valueOf(148), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.invisiblescolor.getValue()).booleanValue())));
/*  90 */   private final Setting<Integer> IOblue = register(new Setting("InvisibleOutline-Blue", Integer.valueOf(148), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.outline.getValue()).booleanValue() && ((Boolean)this.invisiblescolor.getValue()).booleanValue())));
/*     */   
/*     */   public NameTags() {
/*  93 */     super("NameTags", "Renders info about the player on a NameTag", Module.Category.RENDER, false, false, false);
/*     */   }
/*     */   
/*     */   public static NameTags getInstance() {
/*  97 */     if (INSTANCE == null) {
/*  98 */       INSTANCE = new NameTags();
/*     */     }
/* 100 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 105 */     for (EntityPlayer player : mc.world.playerEntities) {
/* 106 */       if (player != null && !player.equals(mc.player) && player.isEntityAlive() && (!player.isInvisible() || ((Boolean)this.invisibles.getValue()).booleanValue())) {
/* 107 */         double x = interpolate(player.lastTickPosX, player.posX, event.getPartialTicks()) - (mc.getRenderManager()).renderPosX;
/* 108 */         double y = interpolate(player.lastTickPosY, player.posY, event.getPartialTicks()) - (mc.getRenderManager()).renderPosY;
/* 109 */         double z = interpolate(player.lastTickPosZ, player.posZ, event.getPartialTicks()) - (mc.getRenderManager()).renderPosZ;
/* 110 */         renderNameTag(player, x, y, z, event.getPartialTicks());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
/* 116 */     double tempY = y;
/* 117 */     tempY += player.isSneaking() ? 0.5D : 0.7D;
/* 118 */     Entity camera = mc.getRenderViewEntity();
/* 119 */     assert camera != null;
/* 120 */     double originalPositionX = camera.posX;
/* 121 */     double originalPositionY = camera.posY;
/* 122 */     double originalPositionZ = camera.posZ;
/* 123 */     camera.posX = interpolate(camera.prevPosX, camera.posX, delta);
/* 124 */     camera.posY = interpolate(camera.prevPosY, camera.posY, delta);
/* 125 */     camera.posZ = interpolate(camera.prevPosZ, camera.posZ, delta);
/*     */     
/* 127 */     String displayTag = getDisplayTag(player);
/* 128 */     double distance = camera.getDistance(x + (mc.getRenderManager()).viewerPosX, y + (mc.getRenderManager()).viewerPosY, z + (mc.getRenderManager()).viewerPosZ);
/* 129 */     int width = this.renderer.getStringWidth(displayTag) / 2;
/* 130 */     double scale = (0.0018D + ((Float)this.size.getValue()).floatValue() * distance * ((Float)this.factor.getValue()).floatValue()) / 1000.0D;
/*     */     
/* 132 */     if (distance <= 8.0D && ((Boolean)this.smartScale.getValue()).booleanValue()) {
/* 133 */       scale = 0.0245D;
/*     */     }
/*     */     
/* 136 */     if (!((Boolean)this.scaleing.getValue()).booleanValue()) {
/* 137 */       scale = ((Float)this.size.getValue()).floatValue() / 100.0D;
/*     */     }
/*     */     
/* 140 */     GlStateManager.pushMatrix();
/* 141 */     RenderHelper.enableStandardItemLighting();
/* 142 */     GlStateManager.enablePolygonOffset();
/* 143 */     GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
/* 144 */     GlStateManager.disableLighting();
/* 145 */     GlStateManager.translate((float)x, (float)tempY + 1.4F, (float)z);
/* 146 */     GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 147 */     GlStateManager.rotate((mc.getRenderManager()).playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/* 148 */     GlStateManager.scale(-scale, -scale, scale);
/* 149 */     GlStateManager.disableDepth();
/* 150 */     GlStateManager.enableBlend();
/* 151 */     GlStateManager.enableBlend();
/* 152 */     if (((Boolean)this.rect.getValue()).booleanValue()) {
/* 153 */       drawRect((-width - 2), -(mc.fontRenderer.FONT_HEIGHT + 1), width + 2.0F, 1.5F, 1426063360);
/*     */     }
/* 155 */     if (((Boolean)this.outline.getValue()).booleanValue()) {
/* 156 */       drawOutlineRect((-width - 2), -(mc.fontRenderer.FONT_HEIGHT + 1), width + 2.0F, 1.5F, getOutlineColor(player));
/*     */     }
/* 158 */     GlStateManager.disableBlend();
/* 159 */     ItemStack renderMainHand = player.getHeldItemMainhand().copy();
/* 160 */     if (renderMainHand.hasEffect() && (renderMainHand.getItem() instanceof net.minecraft.item.ItemTool || renderMainHand.getItem() instanceof net.minecraft.item.ItemArmor)) {
/* 161 */       renderMainHand.stackSize = 1;
/*     */     }
/*     */     
/* 164 */     if (((Boolean)this.heldStackName.getValue()).booleanValue() && !renderMainHand.isEmpty && renderMainHand.getItem() != Items.AIR) {
/* 165 */       String stackName = renderMainHand.getDisplayName();
/* 166 */       int stackNameWidth = this.renderer.getStringWidth(stackName) / 2;
/* 167 */       GL11.glPushMatrix();
/* 168 */       GL11.glScalef(0.75F, 0.75F, 0.0F);
/* 169 */       this.renderer.drawStringWithShadow(stackName, -stackNameWidth, -(getBiggestArmorTag(player) + 20.0F), -1);
/* 170 */       GL11.glScalef(1.5F, 1.5F, 1.0F);
/* 171 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 174 */     if (((Boolean)this.armor.getValue()).booleanValue()) {
/* 175 */       GlStateManager.pushMatrix();
/* 176 */       int xOffset = -6;
/* 177 */       for (ItemStack armourStack : player.inventory.armorInventory) {
/* 178 */         if (armourStack != null) {
/* 179 */           xOffset -= 8;
/*     */         }
/*     */       } 
/*     */       
/* 183 */       xOffset -= 8;
/* 184 */       ItemStack renderOffhand = player.getHeldItemOffhand().copy();
/* 185 */       if (renderOffhand.hasEffect() && (renderOffhand.getItem() instanceof net.minecraft.item.ItemTool || renderOffhand.getItem() instanceof net.minecraft.item.ItemArmor)) {
/* 186 */         renderOffhand.stackSize = 1;
/*     */       }
/*     */       
/* 189 */       renderItemStack(renderOffhand, xOffset);
/* 190 */       xOffset += 16;
/*     */       
/* 192 */       if (((Boolean)this.reversed.getValue()).booleanValue()) {
/* 193 */         for (int index = 0; index <= 3; index++) {
/* 194 */           ItemStack armourStack = (ItemStack)player.inventory.armorInventory.get(index);
/* 195 */           if (armourStack.getItem() != Items.AIR) {
/*     */             
/* 197 */             renderItemStack(armourStack, xOffset);
/* 198 */             xOffset += 16;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 202 */         for (int index = 3; index >= 0; index--) {
/* 203 */           ItemStack armourStack = (ItemStack)player.inventory.armorInventory.get(index);
/* 204 */           if (armourStack.getItem() != Items.AIR) {
/*     */             
/* 206 */             renderItemStack(armourStack, xOffset);
/* 207 */             xOffset += 16;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 212 */       renderItemStack(renderMainHand, xOffset);
/*     */       
/* 214 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 217 */     this.renderer.drawStringWithShadow(displayTag, -width, -(this.renderer.getFontHeight() - 1), getDisplayColor(player));
/*     */     
/* 219 */     camera.posX = originalPositionX;
/* 220 */     camera.posY = originalPositionY;
/* 221 */     camera.posZ = originalPositionZ;
/* 222 */     GlStateManager.enableDepth();
/* 223 */     GlStateManager.disableBlend();
/* 224 */     GlStateManager.disablePolygonOffset();
/* 225 */     GlStateManager.doPolygonOffset(1.0F, 1500000.0F);
/* 226 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private int getDisplayColor(EntityPlayer player) {
/* 230 */     int displaycolor = ColorHolder.toHex(((Integer)this.NCred.getValue()).intValue(), ((Integer)this.NCgreen.getValue()).intValue(), ((Integer)this.NCblue.getValue()).intValue());
/* 231 */     if (OyVey.friendManager.isFriend(player))
/* 232 */       return ColorHolder.toHex(((Integer)this.FCred.getValue()).intValue(), ((Integer)this.FCgreen.getValue()).intValue(), ((Integer)this.FCblue.getValue()).intValue()); 
/* 233 */     if (player.isInvisible() && ((Boolean)this.invisibles.getValue()).booleanValue()) {
/* 234 */       displaycolor = ColorHolder.toHex(((Integer)this.ICred.getValue()).intValue(), ((Integer)this.ICgreen.getValue()).intValue(), ((Integer)this.ICblue.getValue()).intValue());
/* 235 */     } else if (player.isSneaking() && ((Boolean)this.sneak.getValue()).booleanValue()) {
/* 236 */       displaycolor = ColorHolder.toHex(((Integer)this.SCred.getValue()).intValue(), ((Integer)this.SCgreen.getValue()).intValue(), ((Integer)this.SCblue.getValue()).intValue());
/*     */     } 
/* 238 */     return displaycolor;
/*     */   }
/*     */   
/*     */   private int getOutlineColor(EntityPlayer player) {
/* 242 */     int outlinecolor = ColorHolder.toHex(((Integer)this.Ored.getValue()).intValue(), ((Integer)this.Ogreen.getValue()).intValue(), ((Integer)this.Oblue.getValue()).intValue());
/* 243 */     if (OyVey.friendManager.isFriend(player)) {
/* 244 */       outlinecolor = ColorHolder.toHex(((Integer)this.FOred.getValue()).intValue(), ((Integer)this.FOgreen.getValue()).intValue(), ((Integer)this.FOblue.getValue()).intValue());
/* 245 */     } else if (player.isInvisible() && ((Boolean)this.invisibles.getValue()).booleanValue()) {
/* 246 */       outlinecolor = ColorHolder.toHex(((Integer)this.IOred.getValue()).intValue(), ((Integer)this.IOgreen.getValue()).intValue(), ((Integer)this.IOblue.getValue()).intValue());
/* 247 */     } else if (player.isSneaking() && ((Boolean)this.sneak.getValue()).booleanValue()) {
/* 248 */       outlinecolor = ColorHolder.toHex(((Integer)this.SOred.getValue()).intValue(), ((Integer)this.SOgreen.getValue()).intValue(), ((Integer)this.SOblue.getValue()).intValue());
/*     */     } 
/* 250 */     return outlinecolor;
/*     */   }
/*     */   
/*     */   private void renderItemStack(ItemStack stack, int x) {
/* 254 */     GlStateManager.pushMatrix();
/* 255 */     GlStateManager.depthMask(true);
/* 256 */     GlStateManager.clear(256);
/*     */     
/* 258 */     RenderHelper.enableStandardItemLighting();
/* 259 */     (mc.getRenderItem()).zLevel = -150.0F;
/* 260 */     GlStateManager.disableAlpha();
/* 261 */     GlStateManager.enableDepth();
/* 262 */     GlStateManager.disableCull();
/*     */     
/* 264 */     mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -26);
/* 265 */     mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, -26);
/*     */     
/* 267 */     (mc.getRenderItem()).zLevel = 0.0F;
/* 268 */     RenderHelper.disableStandardItemLighting();
/*     */     
/* 270 */     GlStateManager.enableCull();
/* 271 */     GlStateManager.enableAlpha();
/*     */     
/* 273 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 274 */     GlStateManager.disableDepth();
/* 275 */     renderEnchantmentText(stack, x);
/* 276 */     GlStateManager.enableDepth();
/* 277 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 278 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private void renderEnchantmentText(ItemStack stack, int x) {
/* 282 */     int enchantmentY = -34;
/*     */     
/* 284 */     if (stack.getItem() == Items.GOLDEN_APPLE && stack.hasEffect()) {
/* 285 */       this.renderer.drawStringWithShadow("god", (x * 2), enchantmentY, -3977919);
/* 286 */       enchantmentY -= 8;
/*     */     } 
/*     */     
/* 289 */     NBTTagList enchants = stack.getEnchantmentTagList();
/* 290 */     if (enchants.tagCount() > 2 && ((Boolean)this.max.getValue()).booleanValue()) {
/* 291 */       if (((Boolean)this.maxText.getValue()).booleanValue()) {
/* 292 */         this.renderer.drawStringWithShadow("", (x * 2), enchantmentY, ColorHolder.toHex(((Integer)this.Mred.getValue()).intValue(), ((Integer)this.Mgreen.getValue()).intValue(), ((Integer)this.Mblue.getValue()).intValue()));
/*     */       } else {
/* 294 */         this.renderer.drawStringWithShadow("max", (x * 2), enchantmentY, ColorHolder.toHex(((Integer)this.Mred.getValue()).intValue(), ((Integer)this.Mgreen.getValue()).intValue(), ((Integer)this.Mblue.getValue()).intValue()));
/*     */       } 
/* 296 */       enchantmentY -= 8;
/*     */     } else {
/* 298 */       for (int index = 0; index < enchants.tagCount(); index++) {
/* 299 */         short id = enchants.getCompoundTagAt(index).getShort("id");
/* 300 */         short level = enchants.getCompoundTagAt(index).getShort("lvl");
/* 301 */         Enchantment enc = Enchantment.getEnchantmentByID(id);
/* 302 */         if (enc != null) {
/*     */ 
/*     */ 
/*     */           
/* 306 */           String encName = enc.isCurse() ? (TextFormatting.RED + enc.getTranslatedName(level).substring(0, 4).toLowerCase()) : enc.getTranslatedName(level).substring(0, 2).toLowerCase();
/* 307 */           encName = encName + level;
/* 308 */           this.renderer.drawStringWithShadow(encName, (x * 2), enchantmentY, -1);
/* 309 */           enchantmentY -= 8;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     if (DamageUtil.hasDurability(stack)) {
/*     */       String color;
/* 316 */       float green = (stack.getMaxDamage() - stack.getItemDamage()) / stack.getMaxDamage();
/* 317 */       float red = 1.0F - green;
/* 318 */       int dmg = 100 - (int)(red * 100.0F);
/*     */       
/* 320 */       if (dmg >= 60) {
/* 321 */         color = TextUtil.GREEN;
/* 322 */       } else if (dmg >= 25) {
/* 323 */         color = TextUtil.YELLOW;
/*     */       } else {
/* 325 */         color = TextUtil.RED;
/*     */       } 
/* 327 */       this.renderer.drawStringWithShadow(color + dmg + "%", (x * 2), enchantmentY, -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getBiggestArmorTag(EntityPlayer player) {
/* 332 */     float enchantmentY = 0.0F;
/* 333 */     boolean arm = false;
/* 334 */     for (ItemStack stack : player.inventory.armorInventory) {
/* 335 */       float encY = 0.0F;
/* 336 */       if (stack != null) {
/* 337 */         NBTTagList enchants = stack.getEnchantmentTagList();
/* 338 */         for (int index = 0; index < enchants.tagCount(); index++) {
/* 339 */           short id = enchants.getCompoundTagAt(index).getShort("id");
/* 340 */           Enchantment enc = Enchantment.getEnchantmentByID(id);
/* 341 */           if (enc != null) {
/* 342 */             encY += 8.0F;
/* 343 */             arm = true;
/*     */           } 
/*     */         } 
/*     */       } 
/* 347 */       if (encY > enchantmentY) enchantmentY = encY; 
/*     */     } 
/* 349 */     ItemStack renderMainHand = player.getHeldItemMainhand().copy();
/* 350 */     if (renderMainHand.hasEffect()) {
/* 351 */       float encY = 0.0F;
/* 352 */       NBTTagList enchants = renderMainHand.getEnchantmentTagList();
/* 353 */       for (int index = 0; index < enchants.tagCount(); index++) {
/* 354 */         short id = enchants.getCompoundTagAt(index).getShort("id");
/* 355 */         Enchantment enc = Enchantment.getEnchantmentByID(id);
/* 356 */         if (enc != null) {
/* 357 */           encY += 8.0F;
/* 358 */           arm = true;
/*     */         } 
/*     */       } 
/* 361 */       if (encY > enchantmentY) enchantmentY = encY; 
/*     */     } 
/* 363 */     ItemStack renderOffHand = player.getHeldItemOffhand().copy();
/* 364 */     if (renderOffHand.hasEffect()) {
/* 365 */       float encY = 0.0F;
/* 366 */       NBTTagList enchants = renderOffHand.getEnchantmentTagList();
/* 367 */       for (int index = 0; index < enchants.tagCount(); index++) {
/* 368 */         short id = enchants.getCompoundTagAt(index).getShort("id");
/* 369 */         Enchantment enc = Enchantment.getEnchantmentByID(id);
/* 370 */         if (enc != null) {
/* 371 */           encY += 8.0F;
/* 372 */           arm = true;
/*     */         } 
/*     */       } 
/* 375 */       if (encY > enchantmentY) enchantmentY = encY; 
/*     */     } 
/* 377 */     return (arm ? 0 : 20) + enchantmentY;
/*     */   }
/*     */   
/*     */   private String getDisplayTag(EntityPlayer player) {
/* 381 */     String color, name = player.getDisplayName().getFormattedText();
/* 382 */     if (name.contains(mc.getSession().getUsername())) {
/* 383 */       name = "You";
/*     */     }
/*     */     
/* 386 */     if (!((Boolean)this.health.getValue()).booleanValue()) {
/* 387 */       return name;
/*     */     }
/*     */     
/* 390 */     float health = EntityUtil.getHealth((Entity)player);
/*     */ 
/*     */     
/* 393 */     if (health > 18.0F) {
/* 394 */       color = TextUtil.GREEN;
/* 395 */     } else if (health > 16.0F) {
/* 396 */       color = TextUtil.DARK_GREEN;
/* 397 */     } else if (health > 12.0F) {
/* 398 */       color = TextUtil.YELLOW;
/* 399 */     } else if (health > 8.0F) {
/* 400 */       color = TextUtil.RED;
/* 401 */     } else if (health > 5.0F) {
/* 402 */       color = TextUtil.DARK_RED;
/*     */     } else {
/* 404 */       color = TextUtil.DARK_RED;
/*     */     } 
/*     */     
/* 407 */     String pingStr = "";
/* 408 */     if (((Boolean)this.ping.getValue()).booleanValue()) {
/*     */       try {
/* 410 */         int responseTime = ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc.getConnection())).getPlayerInfo(player.getUniqueID()).getResponseTime();
/* 411 */         pingStr = pingStr + responseTime + "ms ";
/* 412 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 416 */     String idString = "";
/* 417 */     if (((Boolean)this.entityID.getValue()).booleanValue()) {
/* 418 */       idString = idString + "ID: " + player.getEntityId() + " ";
/*     */     }
/*     */     
/* 421 */     String gameModeStr = "";
/* 422 */     if (((Boolean)this.gamemode.getValue()).booleanValue()) {
/* 423 */       if (player.isCreative()) {
/* 424 */         gameModeStr = gameModeStr + "[C] ";
/* 425 */       } else if (player.isSpectator() || player.isInvisible()) {
/* 426 */         gameModeStr = gameModeStr + "[I] ";
/*     */       } else {
/* 428 */         gameModeStr = gameModeStr + "[S] ";
/*     */       } 
/*     */     }
/*     */     
/* 432 */     if (Math.floor(health) == health) {
/* 433 */       name = name + color + " " + ((health > 0.0F) ? Integer.valueOf((int)Math.floor(health)) : "dead");
/*     */     } else {
/* 435 */       name = name + color + " " + ((health > 0.0F) ? Integer.valueOf((int)health) : "dead");
/*     */     } 
/* 437 */     return " " + pingStr + idString + gameModeStr + name + " ";
/*     */   }
/*     */   
/*     */   private double interpolate(double previous, double current, float delta) {
/* 441 */     return previous + (current - previous) * delta;
/*     */   }
/*     */   
/*     */   public void drawOutlineRect(float x, float y, float w, float h, int color) {
/* 445 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 446 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 447 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 448 */     float blue = (color & 0xFF) / 255.0F;
/* 449 */     Tessellator tessellator = Tessellator.getInstance();
/* 450 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 451 */     GlStateManager.enableBlend();
/* 452 */     GlStateManager.disableTexture2D();
/* 453 */     GlStateManager.glLineWidth(((Float)this.Owidth.getValue()).floatValue());
/* 454 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 455 */     bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
/* 456 */     bufferbuilder.pos(x, h, 0.0D).color(red, green, blue, alpha).endVertex();
/* 457 */     bufferbuilder.pos(w, h, 0.0D).color(red, green, blue, alpha).endVertex();
/* 458 */     bufferbuilder.pos(w, y, 0.0D).color(red, green, blue, alpha).endVertex();
/* 459 */     bufferbuilder.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
/* 460 */     tessellator.draw();
/* 461 */     GlStateManager.enableTexture2D();
/* 462 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public void drawRect(float x, float y, float w, float h, int color) {
/* 466 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 467 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 468 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 469 */     float blue = (color & 0xFF) / 255.0F;
/* 470 */     Tessellator tessellator = Tessellator.getInstance();
/* 471 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 472 */     GlStateManager.enableBlend();
/* 473 */     GlStateManager.disableTexture2D();
/* 474 */     GlStateManager.glLineWidth(((Float)this.Owidth.getValue()).floatValue());
/* 475 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 476 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 477 */     bufferbuilder.pos(x, h, 0.0D).color(red, green, blue, alpha).endVertex();
/* 478 */     bufferbuilder.pos(w, h, 0.0D).color(red, green, blue, alpha).endVertex();
/* 479 */     bufferbuilder.pos(w, y, 0.0D).color(red, green, blue, alpha).endVertex();
/* 480 */     bufferbuilder.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
/* 481 */     tessellator.draw();
/* 482 */     GlStateManager.enableTexture2D();
/* 483 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 488 */     if (((Boolean)this.outline.getValue()).equals(Boolean.valueOf(false))) {
/* 489 */       this.rect.setValue(Boolean.valueOf(true));
/* 490 */     } else if (((Boolean)this.rect.getValue()).equals(Boolean.valueOf(false))) {
/* 491 */       this.outline.setValue(Boolean.valueOf(true));
/*     */     } 
/* 493 */     if (((Boolean)this.ORainbow.getValue()).booleanValue()) {
/* 494 */       OutlineRainbow();
/*     */     }
/* 496 */     if (((Boolean)this.NCRainbow.getValue()).booleanValue()) {
/* 497 */       TextRainbow();
/*     */     }
/* 499 */     if (((Boolean)this.FCRainbow.getValue()).booleanValue()) {
/* 500 */       FriendRainbow();
/*     */     }
/* 502 */     if (((Boolean)this.SCRainbow.getValue()).booleanValue()) {
/* 503 */       SneakColorRainbow();
/*     */     }
/* 505 */     if (((Boolean)this.ICRainbow.getValue()).booleanValue()) {
/* 506 */       InvisibleRainbow();
/*     */     }
/* 508 */     if (((Boolean)this.FORainbow.getValue()).booleanValue()) {
/* 509 */       FriendOutlineRainbow();
/*     */     }
/* 511 */     if (((Boolean)this.IORainbow.getValue()).booleanValue()) {
/* 512 */       InvisibleOutlineRainbow();
/*     */     }
/* 514 */     if (((Boolean)this.SORainbow.getValue()).booleanValue()) {
/* 515 */       SneakOutlineRainbow();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void OutlineRainbow() {
/* 523 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 526 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 528 */     this.Ored.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 529 */     this.Ogreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 530 */     this.Oblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public void TextRainbow() {
/* 535 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 538 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 540 */     this.NCred.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 541 */     this.NCgreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 542 */     this.NCblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public void FriendRainbow() {
/* 547 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 550 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 552 */     this.FCred.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 553 */     this.FCgreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 554 */     this.FCblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public void SneakColorRainbow() {
/* 559 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 562 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 564 */     this.SCred.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 565 */     this.SCgreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 566 */     this.SCblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public void InvisibleRainbow() {
/* 571 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 574 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 576 */     this.ICred.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 577 */     this.ICgreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 578 */     this.ICblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public void InvisibleOutlineRainbow() {
/* 583 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 586 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 588 */     this.IOred.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 589 */     this.IOgreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 590 */     this.IOblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public void FriendOutlineRainbow() {
/* 595 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 598 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 600 */     this.FOred.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 601 */     this.FOgreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 602 */     this.FOblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public void SneakOutlineRainbow() {
/* 607 */     float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
/*     */ 
/*     */     
/* 610 */     int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
/*     */     
/* 612 */     this.SOred.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
/* 613 */     this.SOgreen.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
/* 614 */     this.SOblue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\NameTags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
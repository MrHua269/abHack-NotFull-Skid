/*     */ package me.abHack.features.modules.render;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.Objects;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.Render2DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.gui.inventory.GuiInventory;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.renderer.DestroyBlockProgress;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class Target extends Module {
/*  29 */   private static Target INSTANCE = new Target();
/*  30 */   public Setting<Boolean> targetHudBackground = register(new Setting("TargetHudBackground", Boolean.valueOf(true)));
/*  31 */   public Setting<Integer> backgroundAlpha = register(new Setting("Background Alpha", Integer.valueOf(160), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.targetHudBackground.getValue()).booleanValue()));
/*  32 */   public Setting<Integer> targetHudX = register(new Setting("TargetHudX", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000)));
/*  33 */   public Setting<Integer> targetHudY = register(new Setting("TargetHudY", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000)));
/*  34 */   public Setting<Integer> red = register(new Setting("Background-Red", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(255)));
/*  35 */   public Setting<Integer> green = register(new Setting("Background-Green", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(255)));
/*  36 */   public Setting<Integer> blue = register(new Setting("Background-Blue", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*     */   public Target() {
/*  39 */     super("Target", "Displays Target", Module.Category.RENDER, false, false, true);
/*  40 */     setInstance();
/*     */   }
/*     */   
/*     */   public static Target getInstance() {
/*  44 */     if (INSTANCE == null)
/*  45 */       INSTANCE = new Target(); 
/*  46 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static EntityPlayer getClosestEnemy() {
/*  50 */     EntityPlayer closestPlayer = null;
/*  51 */     for (EntityPlayer player : mc.world.playerEntities) {
/*  52 */       if (player == mc.player || OyVey.friendManager.isFriend(player))
/*     */         continue; 
/*  54 */       if (closestPlayer == null) {
/*  55 */         closestPlayer = player;
/*     */         continue;
/*     */       } 
/*  58 */       if (mc.player.getDistanceSq((Entity)player) >= mc.player.getDistanceSq((Entity)closestPlayer))
/*     */         continue; 
/*  60 */       closestPlayer = player;
/*     */     } 
/*  62 */     return closestPlayer;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  66 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {
/*  70 */     if (fullNullCheck())
/*     */       return; 
/*  72 */     drawTargetHud(event.partialTicks);
/*     */   }
/*     */   
/*     */   public void drawTargetHud(float partialTicks) {
/*  76 */     EntityPlayer target = getClosestEnemy();
/*  77 */     if (target == null)
/*     */       return; 
/*  79 */     if (target.isDead)
/*  80 */       target = null; 
/*  81 */     String pingStr = "";
/*     */     try {
/*  83 */       int responseTime = ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc.getConnection())).getPlayerInfo(((EntityPlayer)Objects.<EntityPlayer>requireNonNull(target)).getUniqueID()).getResponseTime();
/*  84 */       pingStr = pingStr + responseTime + "";
/*  85 */     } catch (Exception exception) {}
/*     */     
/*  87 */     if (target != null) {
/*  88 */       int ping; if (((Boolean)this.targetHudBackground.getValue()).booleanValue()) {
/*  89 */         float f = target.getHealth() + target.getAbsorptionAmount();
/*  90 */         int j = (f >= 33.0F) ? ColorUtil.toRGBA(0, 255, 0, 255) : ((f >= 30.0F) ? ColorUtil.toRGBA(150, 255, 0, 255) : ((f > 25.0F) ? ColorUtil.toRGBA(75, 255, 0, 255) : ((f > 20.0F) ? ColorUtil.toRGBA(255, 255, 0, 255) : ((f > 15.0F) ? ColorUtil.toRGBA(255, 200, 0, 255) : ((f > 10.0F) ? ColorUtil.toRGBA(255, 150, 0, 255) : ((f > 5.0F) ? ColorUtil.toRGBA(255, 50, 0, 255) : ColorUtil.toRGBA(255, 0, 0, 255)))))));
/*  91 */         RenderUtil.drawRectangleCorrectly(((Integer)this.targetHudX.getValue()).intValue(), ((Integer)this.targetHudY.getValue()).intValue(), 170, 90, ColorUtil.toRGBA(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.backgroundAlpha.getValue()).intValue()));
/*  92 */         RenderUtil.drawGradientSideways(((Integer)this.targetHudX.getValue()).intValue(), ((Integer)this.targetHudY.getValue()).intValue() + 84.0D, ((Integer)this.targetHudX.getValue()).intValue() + f * 4.722222D, ((Integer)this.targetHudY.getValue()).intValue() + 86.8D, j, j);
/*     */       } 
/*  94 */       GlStateManager.disableRescaleNormal();
/*  95 */       GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  96 */       GlStateManager.disableTexture2D();
/*  97 */       GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  98 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       try {
/* 100 */         GuiInventory.drawEntityOnScreen(((Integer)this.targetHudX.getValue()).intValue() + 30, ((Integer)this.targetHudY.getValue()).intValue() + 60, 30, 0.0F, 0.0F, (EntityLivingBase)target);
/* 101 */       } catch (Exception e) {
/* 102 */         e.printStackTrace();
/*     */       } 
/* 104 */       int distance = (int)target.getDistance((Entity)mc.player);
/* 105 */       GlStateManager.enableRescaleNormal();
/* 106 */       GlStateManager.enableTexture2D();
/* 107 */       GlStateManager.enableBlend();
/* 108 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 109 */       this.renderer.drawStringWithShadow(target.getName(), (((Integer)this.targetHudX.getValue()).intValue() + 60), (((Integer)this.targetHudY.getValue()).intValue() + 10), ColorUtil.toRGBA(255, 255, 255, 255));
/* 110 */       if (!EntityUtil.isInHole((Entity)target)) {
/* 111 */         String hole = "Unsafe";
/* 112 */         int j = ColorUtil.toRGBA(255, 0, 0, 255);
/* 113 */         this.renderer.drawStringWithShadow("" + ChatFormatting.BOLD + hole, (((Integer)this.targetHudX.getValue()).intValue() + 60), (((Integer)this.targetHudY.getValue()).intValue() + this.renderer.getFontHeight() + 40), j);
/*     */       } else {
/* 115 */         String hole = "Safe";
/* 116 */         int j = ColorUtil.toRGBA(0, 255, 0, 255);
/* 117 */         this.renderer.drawStringWithShadow("" + ChatFormatting.BOLD + hole, (((Integer)this.targetHudX.getValue()).intValue() + 60), (((Integer)this.targetHudY.getValue()).intValue() + this.renderer.getFontHeight() + 40), j);
/*     */       } 
/* 119 */       float healthLine = target.getHealth() + target.getAbsorptionAmount();
/* 120 */       int lineColor = (healthLine >= 33.0F) ? ColorUtil.toRGBA(0, 255, 0, 255) : ((healthLine >= 30.0F) ? ColorUtil.toRGBA(150, 255, 0, 255) : ((healthLine > 25.0F) ? ColorUtil.toRGBA(75, 255, 0, 255) : ((healthLine > 20.0F) ? ColorUtil.toRGBA(255, 255, 0, 255) : ((healthLine > 15.0F) ? ColorUtil.toRGBA(255, 200, 0, 255) : ((healthLine > 10.0F) ? ColorUtil.toRGBA(255, 150, 0, 255) : ((healthLine > 5.0F) ? ColorUtil.toRGBA(255, 50, 0, 255) : ColorUtil.toRGBA(255, 0, 0, 255)))))));
/* 121 */       this.renderer.drawStringWithShadow("", (((Integer)this.targetHudX.getValue()).intValue() + 60 + this.renderer.getStringWidth("")), (((Integer)this.targetHudY.getValue()).intValue() + 10), lineColor);
/*     */       
/* 123 */       if (EntityUtil.isFakePlayer()) {
/* 124 */         ping = 0;
/*     */       } else {
/* 126 */         ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc.getConnection())).getPlayerInfo(target.getUniqueID());
/* 127 */         ping = mc.getConnection().getPlayerInfo(target.getUniqueID()).getResponseTime();
/*     */       } 
/* 129 */       int color = (ping >= 100) ? ColorUtil.toRGBA(0, 255, 0, 255) : ((ping > 50) ? ColorUtil.toRGBA(255, 255, 0, 255) : ColorUtil.toRGBA(255, 255, 255, 255));
/* 130 */       int distancecolor = (distance <= 5) ? ColorUtil.toRGBA(255, 0, 0, 255) : ((distance < 10) ? ColorUtil.toRGBA(255, 100, 0, 255) : ((distance < 20) ? ColorUtil.toRGBA(255, 150, 0, 255) : ((distance < 30) ? ColorUtil.toRGBA(255, 200, 0, 255) : ((distance < 50) ? ColorUtil.toRGBA(255, 255, 0, 255) : ((distance < 100) ? ColorUtil.toRGBA(150, 255, 0, 255) : ColorUtil.toRGBA(255, 255, 255, 255))))));
/* 131 */       this.renderer.drawStringWithShadow("Health: " + healthLine, (((Integer)this.targetHudX.getValue()).intValue() + 60), (((Integer)this.targetHudY.getValue()).intValue() + this.renderer.getFontHeight() + 10), lineColor);
/* 132 */       this.renderer.drawStringWithShadow("Ping: " + pingStr, (((Integer)this.targetHudX.getValue()).intValue() + 60), (((Integer)this.targetHudY.getValue()).intValue() + this.renderer.getFontHeight() + 20), color);
/* 133 */       this.renderer.drawStringWithShadow("Distance: " + distance, (((Integer)this.targetHudX.getValue()).intValue() + 60), (((Integer)this.targetHudY.getValue()).intValue() + this.renderer.getFontHeight() + 30), distancecolor);
/* 134 */       drawOverlay(partialTicks, (Entity)target, ((Integer)this.targetHudX.getValue()).intValue() + 120, ((Integer)this.targetHudY.getValue()).intValue() + 35);
/* 135 */       GlStateManager.enableTexture2D();
/* 136 */       int iteration = 0;
/* 137 */       int i = ((Integer)this.targetHudX.getValue()).intValue() + 50;
/* 138 */       int y = ((Integer)this.targetHudY.getValue()).intValue() + this.renderer.getFontHeight() * 3 + 44;
/* 139 */       for (ItemStack is : target.inventory.armorInventory) {
/* 140 */         iteration++;
/* 141 */         if (is.isEmpty())
/*     */           continue; 
/* 143 */         int x = i - 90 + (9 - iteration) * 20 + 2;
/* 144 */         GlStateManager.enableDepth();
/* 145 */         RenderUtil.itemRender.zLevel = 200.0F;
/* 146 */         RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, i - 150 + (9 - iteration) * 20 + 2, y - 2);
/* 147 */         RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, i - 150 + (9 - iteration) * 20 + 2, y - 2, "");
/*     */         
/* 149 */         RenderUtil.itemRender.zLevel = 0.0F;
/* 150 */         GlStateManager.enableTexture2D();
/* 151 */         GlStateManager.disableLighting();
/*     */         
/* 153 */         String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
/* 154 */         this.renderer.drawStringWithShadow(s, (x - 50 - this.renderer.getStringWidth(s)), (y + 9), 16777215);
/*     */         
/* 156 */         float green = (is.getMaxDamage() - is.getItemDamage()) / is.getMaxDamage();
/* 157 */         float red = 1.0F - green;
/* 158 */         int dmg = 100 - (int)(red * 100.0F);
/* 159 */         this.renderer.drawStringWithShadow(dmg + "", (x - 47 - this.renderer.getStringWidth(dmg + "")), (y - 8), ColorUtil.toRGBA((int)(red * 255.0F), (int)(green * 255.0F), 0));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawOverlay(float partialTicks, Entity player, int x, int y) {
/* 165 */     float yaw = 0.0F;
/* 166 */     int dir = MathHelper.floor((player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 167 */     switch (dir) {
/*     */       case 1:
/* 169 */         yaw = 90.0F;
/*     */         break;
/*     */       case 2:
/* 172 */         yaw = -180.0F;
/*     */         break;
/*     */       case 3:
/* 175 */         yaw = -90.0F;
/*     */         break;
/*     */     } 
/* 178 */     BlockPos northPos = traceToBlock(partialTicks, yaw, player);
/* 179 */     Block north = getBlock(northPos);
/* 180 */     if (north != null && north != Blocks.AIR) {
/* 181 */       int damage = getBlockDamage(northPos);
/* 182 */       if (damage != 0)
/* 183 */         RenderUtil.drawRect((x + 16), y, (x + 32), (y + 16), 1627324416); 
/* 184 */       drawBlock(north, (x + 16), y);
/*     */     } 
/*     */     BlockPos southPos;
/*     */     Block south;
/* 188 */     if ((south = getBlock(southPos = traceToBlock(partialTicks, yaw - 180.0F, player))) != null && south != Blocks.AIR) {
/* 189 */       int damage = getBlockDamage(southPos);
/* 190 */       if (damage != 0)
/* 191 */         RenderUtil.drawRect((x + 16), (y + 32), (x + 32), (y + 48), 1627324416); 
/* 192 */       drawBlock(south, (x + 16), (y + 32));
/*     */     } 
/*     */     BlockPos eastPos;
/*     */     Block east;
/* 196 */     if ((east = getBlock(eastPos = traceToBlock(partialTicks, yaw + 90.0F, player))) != null && east != Blocks.AIR) {
/* 197 */       int damage = getBlockDamage(eastPos);
/* 198 */       if (damage != 0)
/* 199 */         RenderUtil.drawRect((x + 32), (y + 16), (x + 48), (y + 32), 1627324416); 
/* 200 */       drawBlock(east, (x + 32), (y + 16));
/*     */     } 
/*     */     BlockPos westPos;
/*     */     Block west;
/* 204 */     if ((west = getBlock(westPos = traceToBlock(partialTicks, yaw - 90.0F, player))) != null && west != Blocks.AIR) {
/* 205 */       int damage = getBlockDamage(westPos);
/* 206 */       if (damage != 0)
/* 207 */         RenderUtil.drawRect(x, (y + 16), (x + 16), (y + 32), 1627324416); 
/* 208 */       drawBlock(west, x, (y + 16));
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getBlockDamage(BlockPos pos) {
/* 213 */     for (DestroyBlockProgress destBlockProgress : mc.renderGlobal.damagedBlocks.values()) {
/* 214 */       if (destBlockProgress.getPosition().getX() != pos.getX() || destBlockProgress.getPosition().getY() != pos.getY() || destBlockProgress.getPosition().getZ() != pos.getZ())
/*     */         continue; 
/* 216 */       return destBlockProgress.getPartialBlockDamage();
/*     */     } 
/* 218 */     return 0;
/*     */   }
/*     */   
/*     */   private BlockPos traceToBlock(float partialTicks, float yaw, Entity player) {
/* 222 */     Vec3d pos = EntityUtil.interpolateEntity(player, partialTicks);
/* 223 */     Vec3d dir = MathUtil.direction(yaw);
/* 224 */     return new BlockPos(pos.x + dir.x, pos.y, pos.z + dir.z);
/*     */   }
/*     */   
/*     */   private Block getBlock(BlockPos pos) {
/* 228 */     Block block = mc.world.getBlockState(pos).getBlock();
/* 229 */     if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN)
/* 230 */       return block; 
/* 231 */     return Blocks.AIR;
/*     */   }
/*     */   
/*     */   private void drawBlock(Block block, float x, float y) {
/* 235 */     ItemStack stack = new ItemStack(block);
/* 236 */     GlStateManager.pushMatrix();
/* 237 */     GlStateManager.enableBlend();
/* 238 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 239 */     RenderHelper.enableGUIStandardItemLighting();
/* 240 */     GlStateManager.translate(x, y, 0.0F);
/* 241 */     (mc.getRenderItem()).zLevel = 501.0F;
/* 242 */     mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
/* 243 */     (mc.getRenderItem()).zLevel = 0.0F;
/* 244 */     RenderHelper.disableStandardItemLighting();
/* 245 */     GlStateManager.disableBlend();
/* 246 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 247 */     GlStateManager.popMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Target.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
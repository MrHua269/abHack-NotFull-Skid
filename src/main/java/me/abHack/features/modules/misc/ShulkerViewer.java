/*     */ package me.abHack.features.modules.misc;
/*     */ import java.awt.Color;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import me.abHack.event.events.Render2DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.modules.client.ClickGui;
/*     */ import me.abHack.util.Timer;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemShulkerBox;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class ShulkerViewer extends Module {
/*  29 */   private static final ResourceLocation SHULKER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
/*  30 */   private static ShulkerViewer INSTANCE = new ShulkerViewer();
/*  31 */   public Map<EntityPlayer, ItemStack> spiedPlayers = new ConcurrentHashMap<>();
/*  32 */   public Map<EntityPlayer, Timer> playerTimers = new ConcurrentHashMap<>();
/*     */   
/*     */   public ShulkerViewer() {
/*  35 */     super("ShulkerViewer", "Several tweaks for ", Module.Category.MISC, true, false, false);
/*  36 */     setInstance();
/*     */   }
/*     */   
/*     */   public static ShulkerViewer getInstance() {
/*  40 */     if (INSTANCE == null) {
/*  41 */       INSTANCE = new ShulkerViewer();
/*     */     }
/*  43 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static void displayInv(ItemStack stack, String name) {
/*     */     try {
/*  48 */       Item item = stack.getItem();
/*  49 */       TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
/*  50 */       ItemShulkerBox shulker = (ItemShulkerBox)item;
/*  51 */       entityBox.blockType = shulker.getBlock();
/*  52 */       entityBox.setWorld((World)mc.world);
/*  53 */       assert stack.getTagCompound() != null;
/*  54 */       ItemStackHelper.loadAllItems(stack.getTagCompound().getCompoundTag("BlockEntityTag"), entityBox.items);
/*  55 */       entityBox.readFromNBT(stack.getTagCompound().getCompoundTag("BlockEntityTag"));
/*  56 */       entityBox.setCustomName((name == null) ? stack.getDisplayName() : name);
/*  57 */       (new Thread(() -> {
/*     */             try {
/*     */               Thread.sleep(200L);
/*  60 */             } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */             
/*     */             mc.player.displayGUIChest((IInventory)entityBox);
/*  64 */           })).start();
/*  65 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setInstance() {
/*  71 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  76 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  79 */     for (EntityPlayer player : mc.world.playerEntities) {
/*  80 */       if (player == null || !(player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox) || mc.player == player)
/*     */         continue; 
/*  82 */       ItemStack stack = player.getHeldItemMainhand();
/*  83 */       this.spiedPlayers.put(player, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {
/*  89 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  92 */     int x = -3;
/*  93 */     int y = 124;
/*  94 */     for (EntityPlayer player : mc.world.playerEntities) {
/*     */       
/*  96 */       if (this.spiedPlayers.get(player) == null)
/*  97 */         continue;  player.getHeldItemMainhand();
/*  98 */       if (!(player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox)) {
/*  99 */         Timer playerTimer = this.playerTimers.get(player);
/* 100 */         if (playerTimer == null)
/* 101 */         { Timer timer = new Timer();
/* 102 */           timer.reset();
/* 103 */           this.playerTimers.put(player, timer); }
/* 104 */         else if (playerTimer.passedS(3.0D)) { continue; }
/*     */       
/*     */       } else {
/* 107 */         Timer playerTimer; if (player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox && (playerTimer = this.playerTimers.get(player)) != null) {
/* 108 */           playerTimer.reset();
/* 109 */           this.playerTimers.put(player, playerTimer);
/*     */         } 
/* 111 */       }  ItemStack stack = this.spiedPlayers.get(player);
/* 112 */       renderShulkerToolTip(stack, x, y, player.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void makeTooltip(ItemTooltipEvent event) {}
/*     */ 
/*     */   
/*     */   public void renderShulkerToolTip(ItemStack stack, int x, int y, String name) {
/* 122 */     NBTTagCompound tagCompound = stack.getTagCompound(); NBTTagCompound blockEntityTag;
/* 123 */     if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10) && (blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
/* 124 */       GlStateManager.enableTexture2D();
/* 125 */       GlStateManager.disableLighting();
/* 126 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 127 */       GlStateManager.enableBlend();
/* 128 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 129 */       mc.getTextureManager().bindTexture(SHULKER_GUI_TEXTURE);
/* 130 */       RenderUtil.drawTexturedRect(x, y, 0, 0, 176, 16, 500);
/* 131 */       RenderUtil.drawTexturedRect(x, y + 16, 0, 16, 176, 57, 500);
/* 132 */       RenderUtil.drawTexturedRect(x, y + 16 + 54, 0, 160, 176, 8, 500);
/* 133 */       GlStateManager.disableDepth();
/* 134 */       Color color = new Color(((Integer)ClickGui.INSTANCE.red.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.green.getValue()).intValue(), ((Integer)ClickGui.INSTANCE.blue.getValue()).intValue(), 200);
/* 135 */       this.renderer.drawStringWithShadow((name == null) ? stack.getDisplayName() : name, (x + 8), (y + 6), ColorUtil.toRGBA(color));
/* 136 */       GlStateManager.enableDepth();
/* 137 */       RenderHelper.enableGUIStandardItemLighting();
/* 138 */       GlStateManager.enableRescaleNormal();
/* 139 */       GlStateManager.enableColorMaterial();
/* 140 */       GlStateManager.enableLighting();
/* 141 */       NonNullList nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
/* 142 */       ItemStackHelper.loadAllItems(blockEntityTag, nonnulllist);
/* 143 */       for (int i = 0; i < nonnulllist.size(); i++) {
/* 144 */         int iX = x + i % 9 * 18 + 8;
/* 145 */         int iY = y + i / 9 * 18 + 18;
/* 146 */         ItemStack itemStack = (ItemStack)nonnulllist.get(i);
/* 147 */         (mc.getItemRenderer()).itemRenderer.zLevel = 501.0F;
/* 148 */         RenderUtil.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
/* 149 */         RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, iX, iY, null);
/* 150 */         (mc.getItemRenderer()).itemRenderer.zLevel = 0.0F;
/*     */       } 
/* 152 */       GlStateManager.disableLighting();
/* 153 */       GlStateManager.disableBlend();
/* 154 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\ShulkerViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.abHack.features.modules.misc;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class BowGod
/*     */   extends Module {
/*  20 */   private final Setting<String> spoofs = register(new Setting("Spoofs", "10"));
/*  21 */   public Setting<Boolean> Bows = register(new Setting("Bows", Boolean.valueOf(true)));
/*  22 */   public Setting<Boolean> pearls = register(new Setting("Pearls", Boolean.valueOf(true)));
/*  23 */   public Setting<Boolean> eggs = register(new Setting("Eggs", Boolean.valueOf(true)));
/*  24 */   public Setting<Boolean> snowballs = register(new Setting("SnowBallz", Boolean.valueOf(true)));
/*  25 */   public Setting<Integer> Timeout = register(new Setting("Timeout", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(2000)));
/*  26 */   public Setting<Boolean> bypass = register(new Setting("Bypass", Boolean.valueOf(false)));
/*  27 */   public Setting<Boolean> debug = register(new Setting("Debug", Boolean.valueOf(false)));
/*     */   private long lastShootTime;
/*     */   
/*     */   public BowGod() {
/*  31 */     super("BowGod", "super bow", Module.Category.MISC, true, false, false);
/*     */   }
/*     */   
/*     */   public static boolean isInteger(String str) {
/*  35 */     Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
/*  36 */     return pattern.matcher(str).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  41 */     if (isEnabled()) {
/*  42 */       this.lastShootTime = System.currentTimeMillis();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send event) {
/*  48 */     if (event.getStage() != 0)
/*     */       return; 
/*  50 */     if (event.getPacket() instanceof CPacketPlayerDigging) {
/*  51 */       CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
/*     */       
/*  53 */       if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM) {
/*  54 */         ItemStack handStack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
/*     */         
/*  56 */         if (!handStack.isEmpty()) {
/*  57 */           handStack.getItem();
/*  58 */           if (handStack.getItem() instanceof net.minecraft.item.ItemBow && ((Boolean)this.Bows.getValue()).booleanValue()) {
/*  59 */             doSpoofs();
/*  60 */             if (((Boolean)this.debug.getValue()).booleanValue()) Command.sendMessage("trying to spoof");
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*  65 */     } else if (event.getPacket() instanceof CPacketPlayerTryUseItem) {
/*  66 */       CPacketPlayerTryUseItem packet2 = (CPacketPlayerTryUseItem)event.getPacket();
/*     */       
/*  68 */       if (packet2.getHand() == EnumHand.MAIN_HAND) {
/*  69 */         ItemStack handStack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
/*     */         
/*  71 */         if (!handStack.isEmpty()) {
/*  72 */           handStack.getItem();
/*  73 */           if (handStack.getItem() instanceof net.minecraft.item.ItemEgg && ((Boolean)this.eggs.getValue()).booleanValue()) {
/*  74 */             doSpoofs();
/*  75 */           } else if (handStack.getItem() instanceof net.minecraft.item.ItemEnderPearl && ((Boolean)this.pearls.getValue()).booleanValue()) {
/*  76 */             doSpoofs();
/*  77 */           } else if (handStack.getItem() instanceof net.minecraft.item.ItemSnowball && ((Boolean)this.snowballs.getValue()).booleanValue()) {
/*  78 */             doSpoofs();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doSpoofs() {
/*  86 */     if (System.currentTimeMillis() - this.lastShootTime >= ((Integer)this.Timeout.getValue()).intValue()) {
/*  87 */       this.lastShootTime = System.currentTimeMillis();
/*     */       
/*  89 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SPRINTING));
/*  90 */       if (isInteger((String)this.spoofs.getValue())) {
/*  91 */         for (int index = 0; index < Integer.parseInt((String)this.spoofs.getValue()); index++) {
/*  92 */           if (((Boolean)this.bypass.getValue()).booleanValue()) {
/*  93 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-10D, mc.player.posZ, false));
/*  94 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 1.0E-10D, mc.player.posZ, true));
/*     */           } else {
/*  96 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 1.0E-10D, mc.player.posZ, true));
/*  97 */             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-10D, mc.player.posZ, false));
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 102 */       if (((Boolean)this.debug.getValue()).booleanValue()) Command.sendMessage("Spoofed"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\BowGod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
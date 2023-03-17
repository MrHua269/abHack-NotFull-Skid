/*     */ package me.abHack.features.modules.misc;
/*     */ 
/*     */ import me.abHack.event.events.DecodeEvent;
/*     */ import me.abHack.event.events.EntityWorldEvent;
/*     */ import me.abHack.event.events.ExceptionThrownEvent;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraft.network.play.server.SPacketParticles;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AntiCrash
/*     */   extends Module
/*     */ {
/*  24 */   private static final String[] UNICODE = "ā ȁ ́ Ё ԁ ܁ ࠁ ँ ਁ ଁ ก ༁ ခ ᄁ ሁ ጁ ᐁ ᔁ ᘁ ᜁ ᠁ ᤁ ᨁ ᬁ ᰁ ᴁ ḁ ἁ ℁ ∁ ⌁ ␁ ━ ✁ ⠁ ⤁ ⨁ ⬁ Ⰱ ⴁ ⸁ ⼁ 、 ㄁ ㈁ ㌁ 㐁 㔁 㘁 㜁 㠁 㤁 㨁 㬁 㰁 㴁 㸁 㼁 䀁 䄁 䈁 䌁 䐁 䔁 䘁 䜁 䠁 䤁 䨁 䬁 䰁 䴁 丁 企 倁 儁 刁 匁 吁 唁 嘁 圁 堁 夁 威 嬁 封 崁 币 弁 态 愁 戁 持 搁 攁 昁 朁 栁 椁 樁 欁 氁 洁 渁 漁 瀁 焁 爁 猁 琁 甁 瘁 省 码 礁 稁 笁 簁 紁 縁 缁 老 脁 舁 茁 萁 蔁 蘁 蜁 蠁 褁 訁 謁 谁 贁 踁 輁 送 鄁 鈁 錁 鐁 锁 阁 霁 頁 餁 騁 鬁 鰁 鴁 鸁 鼁 ꀁ ꄁ ꈁ ꌁ ꐁ ꔁ ꘁ ꜁ ꠁ ꤁ ꨁ ꬁ 각 괁 긁 꼁 뀁 넁 눁 댁 됁 딁 똁 뜁 렁 뤁 먁 묁 밁 봁".split(" ");
/*  25 */   public Setting<Boolean> offhand = register(new Setting("OffHand", Boolean.valueOf(true), "Prevents you from getting crashed from item equip sounds"));
/*  26 */   public Setting<Boolean> particles = register(new Setting("Particles", Boolean.valueOf(true), "Prevents laggy particles from crashing you"));
/*  27 */   public Setting<Boolean> unicode = register(new Setting("UnicodeCharacters", Boolean.valueOf(false), "Prevents unicode characters in chat from lagging you"));
/*  28 */   public Setting<Boolean> packets = register(new Setting("Packet", Boolean.valueOf(false), "Prevents you from getting kicked for invalid packets"));
/*  29 */   public Setting<Boolean> bookBan = register(new Setting("BookBan", Boolean.valueOf(true), "Prevents you from getting kicked for packet size limit"));
/*  30 */   public Setting<Boolean> fireworks = register(new Setting("Fireworks", Boolean.valueOf(false), "Prevents firework spam from crashing you"));
/*  31 */   public Setting<Boolean> slime = register(new Setting("Slime", Boolean.valueOf(false), "Prevents large slime entities from crashing you"));
/*     */   
/*     */   public AntiCrash() {
/*  34 */     super("AntiCrash", "anti offhand crash", Module.Category.MISC, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Receive event) {
/*  40 */     if (event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).getSound().equals(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC))
/*     */     {
/*     */       
/*  43 */       if (((Boolean)this.offhand.getValue()).booleanValue()) {
/*  44 */         event.setCanceled(true);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*  49 */     if (event.getPacket() instanceof SPacketParticles && (
/*  50 */       (Boolean)this.particles.getValue()).booleanValue())
/*     */     {
/*     */       
/*  53 */       if (((SPacketParticles)event.getPacket()).getParticleCount() > 200) {
/*  54 */         event.setCanceled(true);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (event.getPacket() instanceof SPacketChat) {
/*     */ 
/*     */       
/*  63 */       String chatMessage = ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText();
/*     */ 
/*     */       
/*  66 */       if (((Boolean)this.unicode.getValue()).booleanValue())
/*     */       {
/*     */         
/*  69 */         for (int i = 0; i < chatMessage.length(); i++) {
/*     */ 
/*     */           
/*  72 */           char character = chatMessage.charAt(i);
/*     */ 
/*     */           
/*  75 */           for (String unicode : UNICODE) {
/*     */ 
/*     */             
/*  78 */             if (unicode.equalsIgnoreCase(String.valueOf(character))) {
/*  79 */               event.setCanceled(true);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onDecode(DecodeEvent event) {
/*  91 */     if (((Boolean)this.bookBan.getValue()).booleanValue()) {
/*  92 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onExceptionThrown(ExceptionThrownEvent event) {
/* 100 */     if ((event.getException() instanceof NullPointerException || event.getException() instanceof java.io.IOException || event.getException() instanceof java.util.ConcurrentModificationException) && (
/* 101 */       (Boolean)this.packets.getValue()).booleanValue()) {
/* 102 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntitySpawn(EntityWorldEvent.EntitySpawnEvent event) {
/* 109 */     if (event.getEntity() instanceof EntityFireworkRocket)
/*     */     {
/*     */       
/* 112 */       if (((Boolean)this.fireworks.getValue()).booleanValue() && !((EntityFireworkRocket)event.getEntity()).isAttachedToEntity()) {
/* 113 */         event.setCanceled(true);
/*     */       }
/*     */     }
/*     */     
/* 117 */     if (event.getEntity() instanceof EntitySlime && ((EntitySlime)event.getEntity()).getSlimeSize() > 4)
/*     */     {
/*     */       
/* 120 */       if (((Boolean)this.slime.getValue()).booleanValue()) {
/* 121 */         event.setCanceled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityUpdate(EntityWorldEvent.EntityUpdateEvent event) {
/* 128 */     if (event.getEntity() instanceof EntityFireworkRocket)
/*     */     {
/*     */       
/* 131 */       if (((Boolean)this.fireworks.getValue()).booleanValue() && !((EntityFireworkRocket)event.getEntity()).isAttachedToEntity())
/* 132 */         event.setCanceled(true); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\AntiCrash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
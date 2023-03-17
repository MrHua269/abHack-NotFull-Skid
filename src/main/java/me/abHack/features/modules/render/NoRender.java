/*    */ package me.abHack.features.modules.render;
/*    */ 
/*    */ import me.abHack.event.events.PacketEvent;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraftforge.client.event.RenderBlockOverlayEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoRender
/*    */   extends Module {
/* 14 */   private static NoRender INSTANCE = new NoRender();
/*    */   
/* 16 */   public Setting<Boolean> armor = register(new Setting("Armor", Boolean.valueOf(true)));
/*    */   
/* 18 */   public Setting<Boolean> fire = register(new Setting("Frie", Boolean.valueOf(true)));
/*    */   
/* 20 */   public Setting<Boolean> blind = register(new Setting("Blind", Boolean.valueOf(true)));
/*    */   
/* 22 */   public Setting<Boolean> nausea = register(new Setting("Nausea", Boolean.valueOf(true)));
/*    */   
/* 24 */   public Setting<Boolean> hurtCam = register(new Setting("HurtCam", Boolean.valueOf(true)));
/*    */   
/* 26 */   public Setting<Boolean> totem = register(new Setting("Totem", Boolean.valueOf(false)));
/*    */   
/* 28 */   public Setting<Boolean> block = register(new Setting("Block", Boolean.valueOf(true)));
/*    */   
/* 30 */   public Setting<Boolean> water = register(new Setting("Water", Boolean.valueOf(true)));
/*    */   
/* 32 */   public Setting<Boolean> explosions = register(new Setting("Explosions", Boolean.valueOf(false)));
/*    */   
/* 34 */   public Setting<Boolean> items = register(new Setting("Items", Boolean.valueOf(false)));
/*    */   
/* 36 */   public Setting<Boolean> noWeather = register(new Setting("Weather", Boolean.valueOf(false)));
/*    */   
/* 38 */   public Setting<Boolean> time = register(new Setting("Change Time", Boolean.valueOf(false)));
/*    */   
/* 40 */   public Setting<Integer> newTime = register(new Setting("Time", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(23000), v -> ((Boolean)this.time.getValue()).booleanValue()));
/*    */   
/* 42 */   public Setting<Boolean> skyLightUpdate = register(new Setting("SkyLightUpdate", Boolean.valueOf(true)));
/*    */   
/*    */   public NoRender() {
/* 45 */     super("NoRender", "Shield some particle effects", Module.Category.RENDER, true, false, false);
/* 46 */     setInstance();
/*    */   }
/*    */   
/*    */   public static NoRender getInstance() {
/* 50 */     if (INSTANCE == null)
/* 51 */       INSTANCE = new NoRender(); 
/* 52 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 56 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 62 */     if (((Boolean)this.explosions.getValue()).booleanValue() && event.getPacket() instanceof net.minecraft.network.play.server.SPacketExplosion)
/* 63 */       event.setCanceled(true); 
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 67 */     if (((Boolean)this.items.getValue()).booleanValue()) {
/* 68 */       mc.world.loadedEntityList.stream().filter(EntityItem.class::isInstance).map(EntityItem.class::cast).forEach(Entity::setDead);
/*    */     }
/* 70 */     if (((Boolean)this.blind.getValue()).booleanValue() && mc.player.isPotionActive(MobEffects.BLINDNESS))
/* 71 */       mc.player.removePotionEffect(MobEffects.BLINDNESS); 
/* 72 */     if (((Boolean)this.nausea.getValue()).booleanValue() && mc.player.isPotionActive(MobEffects.NAUSEA))
/* 73 */       mc.player.removePotionEffect(MobEffects.NAUSEA); 
/* 74 */     if (((Boolean)this.time.getValue()).booleanValue())
/* 75 */       mc.world.setWorldTime(((Integer)this.newTime.getValue()).intValue()); 
/* 76 */     if (((Boolean)this.noWeather.getValue()).booleanValue() && mc.world.isRaining()) {
/* 77 */       mc.world.setRainStrength(0.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void blockOverlayEventListener(RenderBlockOverlayEvent event) {
/* 84 */     if (((Boolean)this.fire.getValue()).booleanValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE)
/* 85 */       event.setCanceled(true); 
/* 86 */     if (((Boolean)this.block.getValue()).booleanValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK) {
/* 87 */       event.setCanceled(true);
/*    */     }
/* 89 */     if (((Boolean)this.water.getValue()).booleanValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER)
/* 90 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\NoRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
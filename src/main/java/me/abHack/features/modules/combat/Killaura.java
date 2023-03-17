/*     */ package me.abHack.features.modules.combat;
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.event.events.UpdateWalkingPlayerEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.DamageUtil;
/*     */ import me.abHack.util.EntityUtil;
/*     */ import me.abHack.util.InventoryUtil;
/*     */ import me.abHack.util.MathUtil;
/*     */ import me.abHack.util.Timer;
/*     */ import me.abHack.util.render.ColorUtil;
/*     */ import me.abHack.util.render.RenderBuilder;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Killaura extends Module {
/*  22 */   private final Timer timer = new Timer(); public static Entity target;
/*  23 */   public Setting<Float> range = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(0.1F), Float.valueOf(6.0F)));
/*  24 */   public Setting<Boolean> delay = register(new Setting("HitDelay", Boolean.valueOf(true)));
/*  25 */   public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
/*  26 */   public Setting<Targer> targetMode = register(new Setting("Target", Targer.Closest));
/*  27 */   public Setting<Switch> swithmode = register(new Setting("Switch", Switch.None));
/*  28 */   public Setting<Float> raytrace = register(new Setting("Walls", Float.valueOf(3.0F), Float.valueOf(0.1F), Float.valueOf(3.0F)));
/*  29 */   public Setting<Boolean> players = register(new Setting("Players", Boolean.valueOf(true)));
/*  30 */   public Setting<Boolean> mobs = register(new Setting("Mobs", Boolean.valueOf(true)));
/*  31 */   public Setting<Boolean> animals = register(new Setting("Animals", Boolean.valueOf(true)));
/*  32 */   public Setting<Boolean> vehicles = register(new Setting("Entities", Boolean.valueOf(false)));
/*  33 */   public Setting<Boolean> projectiles = register(new Setting("Projectiles", Boolean.valueOf(false)));
/*  34 */   public Setting<Boolean> packet = register(new Setting("Packet", Boolean.valueOf(false)));
/*  35 */   public Setting<Boolean> tps = register(new Setting("TpsSync", Boolean.valueOf(true)));
/*  36 */   public Setting<Boolean> silent = register(new Setting("Silent", Boolean.valueOf(false)));
/*  37 */   public Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true)));
/*     */ 
/*     */   
/*     */   public Killaura() {
/*  41 */     super("KillAura", "Attacks entities automatically.", Module.Category.COMBAT, true, false, false);
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedPosition(Entity entity, float ticks) {
/*  45 */     return (new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ))
/*  46 */       .add((new Vec3d(entity.posX - entity.lastTickPosX, entity.posY - entity.lastTickPosY, entity.posZ - entity.lastTickPosZ))
/*  47 */         .scale(ticks));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  52 */     if (!((Boolean)this.rotate.getValue()).booleanValue()) {
/*  53 */       doKillaura();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  58 */     target = null;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
/*  63 */     if (event.getStage() == 0 && ((Boolean)this.rotate.getValue()).booleanValue() && (
/*  64 */       target = getTarget()) != null) {
/*  65 */       doKillaura();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  74 */     if (target != null && ((Boolean)this.render.getValue()).booleanValue()) {
/*     */       
/*  76 */       if (this.swithmode.getValue() == Switch.SwordOnly && !EntityUtil.holdingWeapon((EntityPlayer)mc.player)) {
/*     */         return;
/*     */       }
/*  79 */       RenderUtil.drawCircle((new RenderBuilder())
/*  80 */           .setup()
/*  81 */           .line(1.5F)
/*  82 */           .depth(true)
/*  83 */           .blend()
/*  84 */           .texture(), getInterpolatedPosition(target, 1.0F), target.width, target.height * 0.5D * (Math.sin(mc.player.ticksExisted * 3.5D * 0.017453292519943295D) + 1.0D), ColorUtil.getPrimaryColor());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void doKillaura() {
/*  91 */     target = getTarget();
/*  92 */     if (target == null)
/*     */       return; 
/*  94 */     int weapons = InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD);
/*     */     
/*  96 */     switch ((Switch)this.swithmode.getValue()) {
/*     */       case Auto:
/*  98 */         if (weapons != -1)
/*  99 */           InventoryUtil.switchToSlot(weapons); 
/*     */         break;
/*     */       case SwordOnly:
/* 102 */         if (!EntityUtil.holdingWeapon((EntityPlayer)mc.player))
/*     */           return;  break;
/*     */     } 
/* 105 */     if (((Boolean)this.rotate.getValue()).booleanValue())
/* 106 */       OyVey.rotationManager.lookAtEntity(target); 
/* 107 */     int silentslot = InventoryUtil.getWeaponHotbar();
/*     */     
/* 109 */     int attackdelay = !((Boolean)this.delay.getValue()).booleanValue() ? 0 : (int)(DamageUtil.getCooldownByWeapon((EntityPlayer)mc.player) * (((Boolean)this.tps.getValue()).booleanValue() ? OyVey.serverManager.getTpsFactor() : 1.0F));
/* 110 */     if (((Boolean)this.silent.getValue()).booleanValue() && silentslot != -1 && !EntityUtil.holdingWeapon((EntityPlayer)mc.player))
/* 111 */       attackdelay = (mc.player.inventory.getStackInSlot(silentslot).getItem() instanceof net.minecraft.item.ItemSword) ? 600 : 1000; 
/* 112 */     if (!this.timer.passedMs(attackdelay))
/*     */       return; 
/* 114 */     if (((Boolean)this.silent.getValue()).booleanValue() && silentslot != -1 && !EntityUtil.holdingWeapon((EntityPlayer)mc.player)) {
/* 115 */       int old = mc.player.inventory.currentItem;
/* 116 */       InventoryUtil.switchToSlot(silentslot);
/* 117 */       EntityUtil.attackEntity(target, ((Boolean)this.packet.getValue()).booleanValue(), true);
/* 118 */       InventoryUtil.switchToSlot(old);
/*     */     } else {
/* 120 */       EntityUtil.attackEntity(target, ((Boolean)this.packet.getValue()).booleanValue(), true);
/* 121 */     }  this.timer.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Entity getTarget() {
/* 127 */     Entity target = null;
/* 128 */     double maxHealth = 0.0D;
/* 129 */     for (Entity entity : mc.world.loadedEntityList) {
/* 130 */       if (entity instanceof net.minecraft.entity.item.EntityArmorStand)
/*     */         continue; 
/* 132 */       if (!((Boolean)this.players.getValue()).booleanValue() && entity instanceof EntityPlayer)
/*     */         continue; 
/* 134 */       if (!((Boolean)this.mobs.getValue()).booleanValue() && EntityUtil.isMobAggressive(entity))
/*     */         continue; 
/* 136 */       if (!((Boolean)this.animals.getValue()).booleanValue() && EntityUtil.isPassive(entity))
/*     */         continue; 
/* 138 */       if (!((Boolean)this.vehicles.getValue()).booleanValue() && EntityUtil.isVehicle(entity))
/*     */         continue; 
/* 140 */       if (!((Boolean)this.projectiles.getValue()).booleanValue() && EntityUtil.isProjectile(entity))
/*     */         continue; 
/* 142 */       if (EntityUtil.isntValid(entity, ((Float)this.range.getValue()).floatValue()))
/*     */         continue; 
/* 144 */       if (entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative())
/*     */         continue; 
/* 146 */       if (!mc.player.canEntityBeSeen(entity) && mc.player.getDistanceSq(entity) > MathUtil.square(((Float)this.raytrace.getValue()).floatValue())) {
/*     */         continue;
/*     */       }
/* 149 */       if (target == null) {
/* 150 */         target = entity;
/* 151 */         maxHealth = EntityUtil.getHealth(entity);
/*     */         
/*     */         continue;
/*     */       } 
/* 155 */       if (this.targetMode.getValue() == Targer.Closest && target.getDistance((Entity)mc.player) <= entity.getDistance((Entity)mc.player)) {
/*     */         continue;
/*     */       }
/*     */       
/* 159 */       if (this.targetMode.getValue() == Targer.Health && EntityUtil.getHealth(entity) > maxHealth)
/*     */         continue; 
/* 161 */       maxHealth = EntityUtil.getHealth(entity);
/*     */ 
/*     */       
/* 164 */       target = entity;
/*     */     } 
/*     */     
/* 167 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 172 */     if (target instanceof EntityPlayer)
/* 173 */       return target.getName(); 
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   public enum Switch {
/* 178 */     None, Auto, SwordOnly;
/*     */   }
/*     */   
/*     */   public enum Targer {
/* 182 */     Closest, Health;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\combat\Killaura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
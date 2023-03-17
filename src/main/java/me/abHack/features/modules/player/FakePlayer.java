/*    */ package me.abHack.features.modules.player;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Random;
/*    */ import java.util.UUID;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.MoverType;
import net.minecraft.world.World;

/*    */
/*    */ public class FakePlayer extends Module {
/* 15 */   private static FakePlayer INSTANCE = new FakePlayer();
/* 16 */   public Setting<Boolean> hollow = register(new Setting("Move", Boolean.valueOf(false)));
/*    */   private EntityOtherPlayerMP otherPlayer;
/*    */   
/*    */   public FakePlayer() {
/* 20 */     super("FakePlayer", "fake player", Module.Category.PLAYER, false, false, false);
/*    */   }
/*    */   
/*    */   public static FakePlayer getInstance() {
/* 24 */     if (INSTANCE == null)
/* 25 */       INSTANCE = new FakePlayer(); 
/* 26 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void onLogout() {
/* 30 */     if (OyVey.moduleManager.isModuleEnabled("FakePlayer"))
/* 31 */       disable(); 
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 35 */     if (this.otherPlayer != null) {
/* 36 */       Random random = new Random();
/* 37 */       this.otherPlayer.moveForward = mc.player.moveForward + random.nextInt(5) / 10.0F;
/* 38 */       this.otherPlayer.moveStrafing = mc.player.moveStrafing + random.nextInt(5) / 10.0F;
/* 39 */       if (((Boolean)this.hollow.getValue()).booleanValue()) travel(this.otherPlayer.moveStrafing, this.otherPlayer.moveVertical, this.otherPlayer.moveForward); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void travel(float strafe, float vertical, float forward) {
/* 44 */     double d0 = this.otherPlayer.posY;
/* 45 */     float f1 = 0.8F;
/* 46 */     float f2 = 0.02F;
/* 47 */     float f3 = EnchantmentHelper.getDepthStriderModifier((EntityLivingBase)this.otherPlayer);
/*    */     
/* 49 */     if (f3 > 3.0F) {
/* 50 */       f3 = 3.0F;
/*    */     }
/*    */     
/* 53 */     if (!this.otherPlayer.onGround) {
/* 54 */       f3 *= 0.5F;
/*    */     }
/*    */     
/* 57 */     if (f3 > 0.0F) {
/* 58 */       f1 += (0.54600006F - f1) * f3 / 3.0F;
/* 59 */       f2 += (this.otherPlayer.getAIMoveSpeed() - f2) * f3 / 4.0F;
/*    */     } 
/*    */     
/* 62 */     this.otherPlayer.moveRelative(strafe, vertical, forward, f2);
/* 63 */     this.otherPlayer.move(MoverType.SELF, this.otherPlayer.motionX, this.otherPlayer.motionY, this.otherPlayer.motionZ);
/* 64 */     this.otherPlayer.motionX *= f1;
/* 65 */     this.otherPlayer.motionY *= 0.800000011920929D;
/* 66 */     this.otherPlayer.motionZ *= f1;
/*    */     
/* 68 */     if (!this.otherPlayer.hasNoGravity()) {
/* 69 */       this.otherPlayer.motionY -= 0.02D;
/*    */     }
/*    */     
/* 72 */     if (this.otherPlayer.collidedHorizontally && this.otherPlayer.isOffsetPositionInLiquid(this.otherPlayer.motionX, this.otherPlayer.motionY + 0.6000000238418579D - this.otherPlayer.posY + d0, this.otherPlayer.motionZ)) {
/* 73 */       this.otherPlayer.motionY = 0.30000001192092896D;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 79 */     if (mc.world == null || mc.player == null) {
/* 80 */       toggle();
/*    */       return;
/*    */     } 
/* 83 */     if (this.otherPlayer == null) {
/* 84 */       this.otherPlayer = new EntityOtherPlayerMP((World)mc.world, new GameProfile(UUID.randomUUID(), "Steve_hf"));
/* 85 */       this.otherPlayer.copyLocationAndAnglesFrom((Entity)mc.player);
/* 86 */       this.otherPlayer.inventory.copyInventory(mc.player.inventory);
/*    */     } 
/* 88 */     mc.world.spawnEntity((Entity)this.otherPlayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 94 */     if (this.otherPlayer != null) {
/* 95 */       mc.world.removeEntity((Entity)this.otherPlayer);
/* 96 */       this.otherPlayer = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\player\FakePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
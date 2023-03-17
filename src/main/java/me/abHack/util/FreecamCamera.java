/*     */ package me.abHack.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.FoodStats;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.MovementInputFromOptions;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FreecamCamera extends EntityPlayerSP {
/*  29 */   private final Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*     */   private boolean copyInventory;
/*     */   
/*     */   private boolean follow;
/*     */   private float hSpeed;
/*     */   private float vSpeed;
/*     */   
/*     */   public FreecamCamera(boolean copyInventory, boolean follow, float hSpeed, float vSpeed) {
/*  38 */     super(Minecraft.getMinecraft(), (World)(Minecraft.getMinecraft()).world, Objects.<NetHandlerPlayClient>requireNonNull(Minecraft.getMinecraft().getConnection()), (Minecraft.getMinecraft()).player.getStatFileWriter(), (Minecraft.getMinecraft()).player.getRecipeBook());
/*  39 */     this.copyInventory = copyInventory;
/*  40 */     this.follow = follow;
/*  41 */     this.hSpeed = hSpeed;
/*  42 */     this.vSpeed = vSpeed;
/*  43 */     this.noClip = true;
/*  44 */     setHealth(this.mc.player.getHealth());
/*  45 */     this.posX = this.mc.player.posX;
/*  46 */     this.posY = this.mc.player.posY;
/*  47 */     this.posZ = this.mc.player.posZ;
/*  48 */     this.prevPosX = this.mc.player.prevPosX;
/*  49 */     this.prevPosY = this.mc.player.prevPosY;
/*  50 */     this.prevPosZ = this.mc.player.prevPosZ;
/*  51 */     this.lastTickPosX = this.mc.player.lastTickPosX;
/*  52 */     this.lastTickPosY = this.mc.player.lastTickPosY;
/*  53 */     this.lastTickPosZ = this.mc.player.lastTickPosZ;
/*  54 */     this.rotationYaw = this.mc.player.rotationYaw;
/*  55 */     this.rotationPitch = this.mc.player.rotationPitch;
/*  56 */     this.rotationYawHead = this.mc.player.rotationYawHead;
/*  57 */     this.prevRotationYaw = this.mc.player.prevRotationYaw;
/*  58 */     this.prevRotationPitch = this.mc.player.prevRotationPitch;
/*  59 */     this.prevRotationYawHead = this.mc.player.prevRotationYawHead;
/*  60 */     if (this.copyInventory) {
/*  61 */       this.inventory = this.mc.player.inventory;
/*  62 */       this.inventoryContainer = this.mc.player.inventoryContainer;
/*  63 */       setHeldItem(EnumHand.MAIN_HAND, this.mc.player.getHeldItemMainhand());
/*  64 */       setHeldItem(EnumHand.OFF_HAND, this.mc.player.getHeldItemOffhand());
/*     */     } 
/*  66 */     NBTTagCompound compound = new NBTTagCompound();
/*  67 */     this.mc.player.capabilities.writeCapabilitiesToNBT(compound);
/*  68 */     this.capabilities.readCapabilitiesFromNBT(compound);
/*  69 */     this.capabilities.isFlying = true;
/*  70 */     this.attackedAtYaw = this.mc.player.attackedAtYaw;
/*  71 */     this.movementInput = (MovementInput)new MovementInputFromOptions(this.mc.gameSettings);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {}
/*     */ 
/*     */   
/*     */   public boolean isInsideOfMaterial(@Nonnull Material material) {
/*  84 */     return this.mc.player.isInsideOfMaterial(material);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<Potion, PotionEffect> getActivePotionMap() {
/*  90 */     return this.mc.player.getActivePotionMap();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Collection<PotionEffect> getActivePotionEffects() {
/*  96 */     return this.mc.player.getActivePotionEffects();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 101 */     return this.mc.player.getTotalArmorValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAbsorptionAmount() {
/* 106 */     return this.mc.player.getAbsorptionAmount();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPotionActive(@Nonnull Potion potion) {
/* 111 */     return this.mc.player.isPotionActive(potion);
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionEffect getActivePotionEffect(@Nonnull Potion potion) {
/* 116 */     return this.mc.player.getActivePotionEffect(potion);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public FoodStats getFoodStats() {
/* 122 */     return this.mc.player.getFoodStats();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTriggerWalking() {
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity entity) {
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AxisAlignedBB getEntityBoundingBox() {
/* 143 */     return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEntityCollision(@Nonnull Entity entity) {}
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeAttackedWithItem() {
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeRidden(@Nonnull Entity entity) {
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRenderOnFire() {
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTrample(@Nonnull World world, @Nonnull Block block, @Nonnull BlockPos pos, float fallDistance) {
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doBlockCollisions() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFallState(double y, boolean onGroundIn, @Nonnull IBlockState state, @Nonnull BlockPos pos) {}
/*     */ 
/*     */   
/*     */   public boolean getIsInvulnerable() {
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EnumPushReaction getPushReaction() {
/* 201 */     return EnumPushReaction.IGNORE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoGravity() {
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 211 */     this.motionX = 0.0D;
/* 212 */     this.motionY = 0.0D;
/* 213 */     this.motionZ = 0.0D;
/* 214 */     this.movementInput.updatePlayerMoveState();
/* 215 */     float up = this.movementInput.jump ? 1.0F : (this.movementInput.sneak ? -1.0F : 0.0F);
/* 216 */     setMotion(this.movementInput.moveStrafe, up, this.movementInput.moveForward);
/* 217 */     if (this.mc.gameSettings.keyBindSprint.isKeyDown()) {
/* 218 */       this.motionX *= 2.0D;
/* 219 */       this.motionY *= 2.0D;
/* 220 */       this.motionZ *= 2.0D;
/* 221 */       setSprinting(true);
/*     */     } else {
/* 223 */       setSprinting(false);
/*     */     } 
/* 225 */     if (this.follow) {
/* 226 */       if (Math.abs(this.motionX) <= 9.99999993922529E-9D) {
/* 227 */         this.posX += this.mc.player.posX - this.mc.player.prevPosX;
/*     */       }
/* 229 */       if (Math.abs(this.motionY) <= 9.99999993922529E-9D) {
/* 230 */         this.motionY += this.mc.player.posY - this.mc.player.prevPosY;
/*     */       }
/* 232 */       if (Math.abs(this.motionZ) <= 9.99999993922529E-9D) {
/* 233 */         this.motionZ += this.mc.player.posZ - this.mc.player.prevPosZ;
/*     */       }
/*     */     } 
/* 236 */     setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */   }
/*     */   
/*     */   public void setMotion(float strafe, float up, float forward) {
/* 240 */     float f = strafe * strafe + up * up + forward * forward;
/* 241 */     if (f >= 1.0E-4F) {
/* 242 */       f = MathHelper.sqrt(f);
/* 243 */       if (f < 1.0F) f = 1.0F; 
/* 244 */       f /= 2.0F;
/* 245 */       strafe *= f;
/* 246 */       up *= f;
/* 247 */       forward *= f;
/*     */       
/* 249 */       float f1 = MathHelper.sin(this.rotationYaw * 0.017453292F);
/* 250 */       float f2 = MathHelper.cos(this.rotationYaw * 0.017453292F);
/* 251 */       this.motionX = ((strafe * f2 - forward * f1) * this.hSpeed);
/* 252 */       this.motionY = up * this.vSpeed;
/* 253 */       this.motionZ = ((forward * f2 + strafe * f1) * this.hSpeed);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCopyInventory(boolean copyInventory) {
/* 259 */     this.copyInventory = copyInventory;
/*     */   }
/*     */   
/*     */   public void setFollow(boolean follow) {
/* 263 */     this.follow = follow;
/*     */   }
/*     */   
/*     */   public void sethSpeed(float hSpeed) {
/* 267 */     this.hSpeed = hSpeed;
/*     */   }
/*     */   
/*     */   public void setvSpeed(float vSpeed) {
/* 271 */     this.vSpeed = vSpeed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHac\\util\FreecamCamera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.mixin.mixins;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.event.events.BlockEvent;
/*    */ import me.abHack.event.events.PlayerDamageBlockEvent;
/*    */ import me.abHack.event.events.ProcessRightClickBlockEvent;
/*    */ import me.abHack.features.modules.player.PacketEat;
/*    */ import me.abHack.features.modules.player.TpsSync;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({PlayerControllerMP.class})
/*    */ public abstract class MixinPlayerControllerMP
/*    */ {
/*    */   @Shadow
/*    */   protected abstract void syncCurrentPlayItem();
/*    */   
/*    */   @Redirect(method = {"onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
/*    */   public float getPlayerRelativeBlockHardnessHook(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
/* 39 */     return state.getPlayerRelativeBlockHardness(player, worldIn, pos) * ((TpsSync.getInstance().isOn() && ((Boolean)(TpsSync.getInstance()).mining.getValue()).booleanValue()) ? (1.0F / OyVey.serverManager.getTpsFactor()) : 1.0F);
/*    */   }
/*    */   
/*    */   @Inject(method = {"clickBlock"}, at = {@At("HEAD")})
/*    */   private void clickBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
/* 44 */     BlockEvent event = new BlockEvent(3, pos, face);
/* 45 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*    */   }
/*    */   
/*    */   @Inject(method = {"onPlayerDamageBlock"}, at = {@At("HEAD")})
/*    */   private void onPlayerDamageBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
/* 50 */     BlockEvent event = new BlockEvent(4, pos, face);
/* 51 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"onPlayerDamageBlock"}, at = {@At("HEAD")})
/*    */   private void onPlayerDamageBlockHooktwo(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> ci) {
/* 57 */     PlayerDamageBlockEvent event = new PlayerDamageBlockEvent(0, pos, face);
/* 58 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*    */   }
/*    */   
/*    */   @Inject(method = {"processRightClickBlock"}, at = {@At("HEAD")})
/*    */   public void processRightClickBlock(EntityPlayerSP player, WorldClient worldIn, BlockPos pos, EnumFacing direction, Vec3d vec, EnumHand hand, CallbackInfoReturnable<EnumActionResult> cir) {
/* 63 */     ProcessRightClickBlockEvent event = new ProcessRightClickBlockEvent(pos, hand, Minecraft.instance.player.getHeldItem(hand));
/* 64 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 65 */     if (event.isCanceled()) {
/* 66 */       cir.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"onStoppedUsingItem"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onStoppedUsingItem(EntityPlayer playerIn, CallbackInfo ci) {
/* 73 */     if (PacketEat.getInstance().isOn() && (((
/* 74 */       (Boolean)(PacketEat.getInstance()).food.getValue()).booleanValue() && playerIn.getHeldItem(playerIn.getActiveHand()).getItem() instanceof net.minecraft.item.ItemFood) || ((
/* 75 */       (Boolean)(PacketEat.getInstance()).potion.getValue()).booleanValue() && playerIn.getHeldItem(playerIn.getActiveHand()).getItem() instanceof net.minecraft.item.ItemPotion))) {
/* 76 */       syncCurrentPlayItem();
/* 77 */       playerIn.stopActiveHand();
/* 78 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\mixin\mixins\MixinPlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
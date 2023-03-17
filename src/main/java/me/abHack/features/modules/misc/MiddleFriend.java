/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.command.Command;
/*    */ import me.abHack.features.modules.Module;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class MiddleFriend
/*    */   extends Module
/*    */ {
/*    */   private boolean clicked = false;
/*    */   
/*    */   public MiddleFriend() {
/* 17 */     super("MiddleFriend", "Middleclick Friends.", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 22 */     if (Mouse.isButtonDown(2)) {
/* 23 */       if (!this.clicked && mc.currentScreen == null) {
/* 24 */         onClick();
/*    */       }
/* 26 */       this.clicked = true;
/*    */     } else {
/* 28 */       this.clicked = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void onClick() {
/* 34 */     RayTraceResult result = mc.objectMouseOver;
/* 35 */     if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof net.minecraft.entity.player.EntityPlayer) {
    Entity entity = result.entityHit;
/* 36 */       if (OyVey.friendManager.isFriend(entity.getName())) {
/* 37 */         OyVey.friendManager.removeFriend(entity.getName());
/* 38 */         Command.sendMessage(ChatFormatting.RED + entity.getName() + ChatFormatting.RED + " has been unfriended.");
/*    */       } else {
/* 40 */         OyVey.friendManager.addFriend(entity.getName());
/* 41 */         Command.sendMessage(ChatFormatting.AQUA + entity.getName() + ChatFormatting.AQUA + " has been friended.");
/*    */       }
/*    */     }
/* 44 */     this.clicked = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\MiddleFriend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
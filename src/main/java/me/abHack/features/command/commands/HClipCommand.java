/*    */ package me.abHack.features.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.features.command.Command;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class HClipCommand
/*    */   extends Command
/*    */ {
/*    */   public HClipCommand() {
/* 12 */     super("hclip", new String[] { "<distance>" });
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     if (commands.length == 1) {
/* 17 */       Command.sendMessage(ChatFormatting.DARK_RED + ".Hclip <distance>");
/*    */       return;
/*    */     } 
/* 20 */     double weight = Double.parseDouble(commands[0]);
/* 21 */     Vec3d direction = new Vec3d(Math.cos(((mc.player.rotationYaw + 90.0F) * 0.017453292F)), 0.0D, Math.sin(((mc.player.rotationYaw + 90.0F) * 0.017453292F)));
/* 22 */     Entity target = mc.player.isRiding() ? mc.player.getRidingEntity() : (Entity)mc.player;
/* 23 */     target.setPosition(target.posX + direction.x * weight, target.posY, target.posZ + direction.z * weight);
/* 24 */     Command.sendMessage("Hclip to " + weight);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\command\commands\HClipCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
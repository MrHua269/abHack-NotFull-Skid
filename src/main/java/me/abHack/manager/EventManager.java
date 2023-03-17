/*     */ package me.abHack.manager;
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.Objects;
import java.util.UUID;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
import me.abHack.OyVey;
/*     */ import me.abHack.event.events.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import me.abHack.features.Feature;
/*     */ import me.abHack.features.command.Command;
/*     */ import me.abHack.features.modules.client.ClickGui;
import me.abHack.features.modules.client.HUD;
/*     */ import me.abHack.features.modules.misc.PopCounter;
/*     */ import me.abHack.util.Timer;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketBlockBreakAnim;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.ClientChatEvent;
/*     */ import net.minecraftforge.client.event.GuiOpenEvent;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.InputEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class EventManager extends Feature {
/*  40 */   private final Timer logoutTimer = new Timer();
/*  41 */   private final Timer packetFactorySettings = new Timer();
/*     */   private int packet;
/*     */   public boolean esc;
/*     */   
/*     */   public void init() {
/*  46 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void onUnload() {
/*  50 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdate(LivingEvent.LivingUpdateEvent event) {
/*  55 */     if (!fullNullCheck() && (event.getEntity().getEntityWorld()).isRemote && event.getEntityLiving().equals(mc.player)) {
/*  56 */       OyVey.moduleManager.onUpdate();
/*  57 */       if (HUD.INSTANCE.renderingMode.getValue() == HUD.RenderingMode.Length) {
/*  58 */         OyVey.moduleManager.sortModules(true);
/*     */       } else {
/*  60 */         OyVey.moduleManager.sortModulesABC();
/*     */       } 
/*     */     } 
/*  63 */     if (this.packetFactorySettings.passedMs(1000L)) {
/*  64 */       this.packet = 0;
/*  65 */       this.packetFactorySettings.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
/*  71 */     this.logoutTimer.reset();
/*  72 */     OyVey.moduleManager.onLogin();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void MainMenuOpen(GuiOpenEvent event) {
/*  78 */     GuiScreen gui = event.getGui();
/*  79 */     this.esc = (gui instanceof net.minecraft.client.gui.GuiMultiplayer && ((Boolean) ClickGui.INSTANCE.bypass163.getValue()).booleanValue());
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/*  84 */     OyVey.moduleManager.onLogout();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent event) {
/*  89 */     if (fullNullCheck())
/*     */       return; 
/*  91 */     OyVey.moduleManager.onTick();
/*  92 */     for (EntityPlayer player : mc.world.playerEntities) {
/*  93 */       if (!OyVey.moduleManager.isModuleEnabled("PopCounter") || 
/*  94 */         player == null || player.getHealth() > 0.0F)
/*     */         continue; 
/*  96 */       MinecraftForge.EVENT_BUS.post((Event)new DeathEvent(player));
/*  97 */       PopCounter.getInstance().onDeath(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
/* 104 */     if (fullNullCheck())
/*     */       return; 
/* 106 */     if (event.getStage() == 0) {
/* 107 */       OyVey.speedManager.updateValues();
/* 108 */       OyVey.rotationManager.updateRotations();
/* 109 */       OyVey.positionManager.updatePosition();
/*     */     } 
/* 111 */     if (event.getStage() == 1) {
/* 112 */       OyVey.rotationManager.restoreRotations();
/* 113 */       OyVey.positionManager.restorePosition();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public final void onPacketSend(PacketEvent.Send event) {
/* 121 */     if (event.getPacket() instanceof CPacketPlayer) {
/*     */       
/* 123 */       CPacketPlayer packet = (CPacketPlayer)event.getPacket();
/*     */       
/* 125 */       if (packet instanceof CPacketPlayer.PositionRotation && packet.moving && packet.rotating) {
/* 126 */         if (this.packet++ > 20) {
/* 127 */           CPacketPlayer.Position pos = new CPacketPlayer.Position(packet.x, packet.y, packet.z, packet.onGround);
/* 128 */           pos.moving = pos.rotating = false;
/* 129 */           mc.player.connection.sendPacket((Packet)pos);
/* 130 */           event.setCanceled(true);
/*     */         } 
/* 132 */       } else if (packet instanceof CPacketPlayer.Position && packet.moving && 
/* 133 */         this.packet <= 20) {
/* 134 */         this.packet++;
/* 135 */         CPacketPlayer.PositionRotation pos = new CPacketPlayer.PositionRotation(packet.x, packet.y, packet.z, mc.player.rotationYaw, mc.player.rotationPitch, packet.onGround);
/* 136 */         pos.moving = false;
/* 137 */         mc.player.connection.sendPacket((Packet)pos);
/* 138 */         event.setCanceled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
    /* 146 */
    if (event.getStage() != 0) {
        /*     */
        return;
        /*     */
    }
    /*     */
    /* 150 */
    OyVey.serverManager.onPacketReceived();
    /*     */
    /* 152 */
    if (event.getPacket() instanceof SPacketEntityStatus) {
        /* 153 */
        SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
        /* 154 */
        if (packet.getOpCode() == 35 && packet.getEntity((World) mc.world) instanceof EntityPlayer && OyVey.moduleManager.isModuleEnabled("PopCounter")) {
            /* 155 */
            EntityPlayer player = (EntityPlayer) packet.getEntity((World) mc.world);
            /* 156 */
            MinecraftForge.EVENT_BUS.post((Event) new TotemPopEvent(player));
            /* 157 */
            PopCounter.getInstance().onTotemPop(player);
            /*     */
        }
        /*     */
    }
    /* 160 */
    if (event.getPacket() instanceof SPacketPlayerListItem && !fullNullCheck() && this.logoutTimer.passedS(1.0D)) {
        SPacketPlayerListItem packet = event.getPacket();
        if (!SPacketPlayerListItem.Action.ADD_PLAYER.equals(packet.getAction()) && !SPacketPlayerListItem.Action.REMOVE_PLAYER.equals(packet.getAction())) return;
        packet.getEntries().stream().filter(Objects::nonNull).filter(data -> (!Strings.isNullOrEmpty(data.getProfile().getName()) || data.getProfile().getId() != null))
                .forEach(data -> {
                    String name;
                    EntityPlayer entity;
                    UUID id = data.getProfile().getId();
                    switch (packet.getAction()) {
                        case ADD_PLAYER:
                            name = data.getProfile().getName();
                            MinecraftForge.EVENT_BUS.post(new ConnectionEvent(0, id, name));
                            break;
                        case REMOVE_PLAYER:
                            entity = mc.world.getPlayerEntityByUUID(id);
                            if (entity != null) {
                                String logoutName = entity.getName();
                                MinecraftForge.EVENT_BUS.post(new ConnectionEvent(1, entity, id, logoutName));
                                break;
                            }
                            MinecraftForge.EVENT_BUS.post(new ConnectionEvent(2, id, null));
                            break;
                    }
                });
    }
    if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate) {
        OyVey.serverManager.update();
    }
    if (event.getPacket() instanceof SPacketBlockBreakAnim) {
        OyVey.breakManager.onPacketReceive(event.getPacket());
    }
}
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldRender(RenderWorldLastEvent event) {
/* 196 */     if (event.isCanceled()) {
/*     */       return;
/*     */     }
/* 199 */     mc.profiler.startSection("oyvey");
/* 200 */     RenderUtil.prepareGL3D();
/*     */     
/* 202 */     Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
/* 203 */     OyVey.moduleManager.onRender3D(render3dEvent);
/*     */     
/* 205 */     RenderUtil.releaseGL3D();
/* 206 */     mc.profiler.endSection();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderHUD(RenderGameOverlayEvent.Post event) {
/* 211 */     if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
/* 212 */       OyVey.textManager.updateResolution(); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
/* 217 */     if (event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
/* 218 */       ScaledResolution resolution = new ScaledResolution(mc);
/* 219 */       Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
/* 220 */       OyVey.moduleManager.onRender2D(render2DEvent);
/* 221 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
/*     */   public void onKeyInput(InputEvent.KeyInputEvent event) {
/* 227 */     if (Keyboard.getEventKeyState())
/* 228 */       OyVey.moduleManager.onKeyPressed(Keyboard.getEventKey()); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onChatSent(ClientChatEvent event) {
/* 233 */     if (event.getMessage().startsWith(Command.getCommandPrefix())) {
/* 234 */       event.setCanceled(true);
/*     */       try {
/* 236 */         mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
/* 237 */         if (event.getMessage().length() > 1) {
/* 238 */           OyVey.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
/*     */         } else {
/* 240 */           Command.sendMessage("Please enter a command.");
/*     */         } 
/* 242 */       } catch (Exception e) {
/* 243 */         e.printStackTrace();
/* 244 */         Command.sendMessage(ChatFormatting.RED + "An error occurred while running this command. Check the log!");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
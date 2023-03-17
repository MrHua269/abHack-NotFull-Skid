/*     */ package me.abHack.features.modules.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import me.abHack.event.events.PacketEvent;
/*     */ import me.abHack.event.events.Render3DEvent;
/*     */ import me.abHack.features.modules.Module;
/*     */ import me.abHack.features.setting.Setting;
/*     */ import me.abHack.util.render.RenderUtil;
/*     */ import me.abHack.util.render.Trails.AnimationMode;
/*     */ import me.abHack.util.render.Trails.TimeAnimation;
/*     */ import me.abHack.util.render.Trails.Trace;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.play.server.SPacketDestroyEntities;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Trails
/*     */   extends Module
/*     */ {
/*  28 */   public static final Vec3d ORIGIN = new Vec3d(8.0D, 64.0D, 8.0D);
/*     */   
/*  30 */   private final Setting<Boolean> arrows = register(new Setting("Arrows", Boolean.valueOf(true)));
/*     */   
/*  32 */   private final Setting<Boolean> pearls = register(new Setting("Pearls", Boolean.valueOf(true)));
/*     */   
/*  34 */   private final Setting<Boolean> snowballs = register(new Setting("Snowballs", Boolean.valueOf(true)));
/*     */   
/*  36 */   private final Setting<Integer> time = register(new Setting("Time", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10)));
/*     */   
/*  38 */   private final Setting<Integer> red = register(new Setting("Red", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  40 */   private final Setting<Integer> green = register(new Setting("Green", Integer.valueOf(24), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  42 */   private final Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  44 */   private final Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(230), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   
/*  46 */   private final Setting<Float> width = register(new Setting("Width", Float.valueOf(1.6F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*     */   
/*  48 */   protected Map<Integer, TimeAnimation> ids = new ConcurrentHashMap<>();
/*  49 */   protected Map<Integer, List<Trace>> traceLists = new ConcurrentHashMap<>();
/*  50 */   protected Map<Integer, Trace> traces = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   public Trails() {
/*  54 */     super("Trails", "trails", Module.Category.RENDER, true, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  60 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  63 */     if (this.ids.keySet().isEmpty())
/*     */       return; 
/*  65 */     for (Integer id : this.ids.keySet()) {
/*  66 */       if (id == null)
/*  67 */         continue;  if (mc.world.loadedEntityList == null)
/*  68 */         return;  if (mc.world.loadedEntityList.isEmpty())
/*  69 */         return;  Trace idTrace = this.traces.get(id);
/*  70 */       Entity entity = mc.world.getEntityByID(id.intValue());
/*  71 */       if (entity != null) {
/*  72 */         Vec3d vec = entity.getPositionVector();
/*  73 */         if (vec.equals(ORIGIN)) {
/*     */           continue;
/*     */         }
/*     */         
/*  77 */         if (!this.traces.containsKey(id) || idTrace == null) {
/*  78 */           this.traces.put(id, new Trace(0, null, mc.world.provider
/*     */                 
/*  80 */                 .getDimensionType(), vec, new ArrayList()));
/*     */ 
/*     */           
/*  83 */           idTrace = this.traces.get(id);
/*     */         } 
/*     */         
/*  86 */         List<Trace.TracePos> trace = idTrace.getTrace();
/*  87 */         Vec3d vec3d = trace.isEmpty() ? vec : ((Trace.TracePos)trace.get(trace.size() - 1)).getPos();
/*  88 */         if (!trace.isEmpty() && (vec
/*  89 */           .distanceTo(vec3d) > 100.0D || idTrace
/*  90 */           .getType() != mc.world.provider
/*  91 */           .getDimensionType())) {
/*  92 */           ((List<Trace>)this.traceLists.get(id)).add(idTrace);
/*  93 */           trace = new ArrayList<>();
/*  94 */           this.traces.put(id, new Trace(((List)this.traceLists.get(id)).size() + 1, null, mc.world.provider
/*     */                 
/*  96 */                 .getDimensionType(), vec, new ArrayList()));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 101 */         if (trace.isEmpty() || !vec.equals(vec3d)) {
/* 102 */           trace.add(new Trace.TracePos(vec));
/*     */         }
/*     */       } 
/*     */       
/* 106 */       TimeAnimation animation = this.ids.get(id);
/*     */       
/* 108 */       if (entity instanceof net.minecraft.entity.projectile.EntityArrow && (entity.onGround || entity.collided || !entity.isAirBorne)) {
/* 109 */         animation.play();
/*     */       }
/*     */       
/* 112 */       if (animation != null && ((Integer)this.alpha.getValue()).intValue() - animation.getCurrent() <= 0.0D) {
/* 113 */         animation.stop();
/* 114 */         this.ids.remove(id);
/* 115 */         this.traceLists.remove(id);
/* 116 */         this.traces.remove(id);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 125 */     if (event.getPacket() instanceof SPacketSpawnObject) {
/* 126 */       SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
/*     */       
/* 128 */       if ((((Boolean)this.pearls.getValue()).booleanValue() && packet.getType() == 65) || (((Boolean)this.arrows
/* 129 */         .getValue()).booleanValue() && packet.getType() == 60) || (((Boolean)this.snowballs
/* 130 */         .getValue()).booleanValue() && packet.getType() == 61)) {
/* 131 */         TimeAnimation animation = new TimeAnimation((((Integer)this.time.getValue()).intValue() * 1000), 0.0D, ((Integer)this.alpha.getValue()).intValue(), false, AnimationMode.LINEAR);
/* 132 */         animation.stop();
/* 133 */         this.ids.put(Integer.valueOf(packet.getEntityID()), animation);
/* 134 */         this.traceLists.put(Integer.valueOf(packet.getEntityID()), new ArrayList<>());
/* 135 */         this.traces.put(Integer.valueOf(packet.getEntityID()), new Trace(0, null, mc.world.provider
/*     */               
/* 137 */               .getDimensionType(), new Vec3d(packet
/* 138 */                 .getX(), packet.getY(), packet.getZ()), new ArrayList()));
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     if (event.getPacket() instanceof SPacketDestroyEntities) {
/* 143 */       for (int id : ((SPacketDestroyEntities)event.getPacket()).getEntityIDs()) {
/* 144 */         if (this.ids.containsKey(Integer.valueOf(id))) {
/* 145 */           ((TimeAnimation)this.ids.get(Integer.valueOf(id))).play();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 154 */     if (fullNullCheck())
/*     */       return; 
/* 156 */     RenderUtil.prepareGL3D();
/* 157 */     for (Map.Entry<Integer, List<Trace>> entry : this.traceLists.entrySet()) {
/* 158 */       GL11.glLineWidth(((Float)this.width.getValue()).floatValue());
/* 159 */       TimeAnimation animation = this.ids.get(entry.getKey());
/* 160 */       animation.add(event.getPartialTicks());
/* 161 */       GL11.glColor4f(((Integer)this.red.getValue()).intValue() / 255.0F, ((Integer)this.green
/* 162 */           .getValue()).intValue() / 255.0F, ((Integer)this.blue
/* 163 */           .getValue()).intValue() / 255.0F, 
/* 164 */           MathHelper.clamp((float)((((Integer)this.alpha.getValue()).intValue() / 255.0F) - animation.getCurrent() / 255.0D), 0.0F, 255.0F));
/*     */       
/* 166 */       ((List<Trace>)entry.getValue()).forEach(trace -> {
/*     */             GL11.glBegin(3);
/*     */             
/*     */             trace.getTrace().forEach(this::renderVec);
/*     */             
/*     */             GL11.glEnd();
/*     */           });
/* 173 */       GL11.glColor4f(((Integer)this.red.getValue()).intValue() / 255.0F, ((Integer)this.green
/* 174 */           .getValue()).intValue() / 255.0F, ((Integer)this.blue
/* 175 */           .getValue()).intValue() / 255.0F, 
/* 176 */           MathHelper.clamp((float)((((Integer)this.alpha.getValue()).intValue() / 255.0F) - animation.getCurrent() / 255.0D), 0.0F, 255.0F));
/*     */       
/* 178 */       GL11.glBegin(3);
/* 179 */       Trace trace = this.traces.get(entry.getKey());
/* 180 */       if (trace != null) {
/* 181 */         trace.getTrace().forEach(this::renderVec);
/*     */       }
/* 183 */       GL11.glEnd();
/*     */     } 
/*     */     
/* 186 */     RenderUtil.releaseGL3D();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderVec(Trace.TracePos tracePos) {
/* 192 */     double x = (tracePos.getPos()).x - (mc.getRenderManager()).renderPosX;
/* 193 */     double y = (tracePos.getPos()).y - (mc.getRenderManager()).renderPosY;
/* 194 */     double z = (tracePos.getPos()).z - (mc.getRenderManager()).renderPosZ;
/* 195 */     GL11.glVertex3d(x, y, z);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\modules\render\Trails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
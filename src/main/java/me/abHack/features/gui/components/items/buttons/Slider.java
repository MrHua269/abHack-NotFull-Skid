/*    */ package me.abHack.features.gui.components.items.buttons;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.abHack.OyVey;
/*    */ import me.abHack.features.gui.OyVeyGui;
/*    */ import me.abHack.features.gui.components.Component;
/*    */ import me.abHack.features.modules.client.ClickGui;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.render.RenderUtil;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class Slider
/*    */   extends Button {
/*    */   private final Number min;
/*    */   private final Number max;
/*    */   private final int difference;
/*    */   public Setting setting;
/*    */   
/*    */   public Slider(Setting setting) {
/* 20 */     super(setting.getName());
/* 21 */     this.setting = setting;
/* 22 */     this.min = (Number)setting.getMin();
/* 23 */     this.max = (Number)setting.getMax();
/* 24 */     this.difference = this.max.intValue() - this.min.intValue();
/* 25 */     this.width = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 30 */     dragSetting(mouseX, mouseY);
/* 31 */     RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4F, this.y + this.height - 0.5F, !isHovering(mouseX, mouseY) ? 290805077 : -2007673515);
/* 32 */     RenderUtil.drawRect(this.x, this.y, (((Number)this.setting.getValue()).floatValue() <= this.min.floatValue()) ? this.x : (this.x + (this.width + 7.4F) * partialMultiplier()), this.y + this.height - 0.5F, OyVey.colorManager.getColorWithAlpha(((Integer)((ClickGui)OyVey.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue()));
/* 33 */     OyVey.textManager.drawStringWithShadow(getName() + " " + ChatFormatting.GRAY + ((this.setting.getValue() instanceof Float) ? this.setting.getValue() : Double.valueOf(((Number)this.setting.getValue()).doubleValue())), this.x + 2.3F, this.y - 1.7F - OyVeyGui.getClickGui().getTextOffset(), -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 38 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 39 */     if (isHovering(mouseX, mouseY)) {
/* 40 */       setSettingFromX(mouseX);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHovering(int mouseX, int mouseY) {
/* 46 */     for (Component component : OyVeyGui.getClickGui().getComponents()) {
/* 47 */       if (!component.drag)
/* 48 */         continue;  return false;
/*    */     } 
/* 50 */     return (mouseX >= getX() && mouseX <= getX() + getWidth() + 8.0F && mouseY >= getY() && mouseY <= getY() + this.height);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 55 */     setHidden(!this.setting.isVisible());
/*    */   }
/*    */   
/*    */   private void dragSetting(int mouseX, int mouseY) {
/* 59 */     if (isHovering(mouseX, mouseY) && Mouse.isButtonDown(0)) {
/* 60 */       setSettingFromX(mouseX);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 66 */     return 14;
/*    */   }
/*    */   
/*    */   private void setSettingFromX(int mouseX) {
/* 70 */     float percent = (mouseX - this.x) / (this.width + 7.4F);
/* 71 */     if (this.setting.getValue() instanceof Double) {
/* 72 */       double result = ((Double)this.setting.getMin()).doubleValue() + (this.difference * percent);
/* 73 */       this.setting.setValue(Double.valueOf(Math.round(10.0D * result) / 10.0D));
/* 74 */     } else if (this.setting.getValue() instanceof Float) {
/* 75 */       float result = ((Float)this.setting.getMin()).floatValue() + this.difference * percent;
/* 76 */       this.setting.setValue(Float.valueOf(Math.round(10.0F * result) / 10.0F));
/* 77 */     } else if (this.setting.getValue() instanceof Integer) {
/* 78 */       this.setting.setValue(Integer.valueOf(((Integer)this.setting.getMin()).intValue() + (int)(this.difference * percent)));
/*    */     } 
/*    */   }
/*    */   
/*    */   private float middle() {
/* 83 */     return this.max.floatValue() - this.min.floatValue();
/*    */   }
/*    */   
/*    */   private float part() {
/* 87 */     return ((Number)this.setting.getValue()).floatValue() - this.min.floatValue();
/*    */   }
/*    */   
/*    */   private float partialMultiplier() {
/* 91 */     return part() / middle();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\features\gui\components\items\buttons\Slider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
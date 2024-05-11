// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.gui.clickgui.express;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;
import shop.xiaoda.Client;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.render.HUD;
import shop.xiaoda.module.values.BoolValue;
import shop.xiaoda.module.values.ColorValue;
import shop.xiaoda.module.values.ModeValue;
import shop.xiaoda.module.values.NumberValue;
import shop.xiaoda.module.values.Value;
import shop.xiaoda.utils.render.AnimationUtil;
import shop.xiaoda.utils.render.RenderUtil;
import shop.xiaoda.utils.render.fontRender.FontManager;

public class NormalClickGUI
        extends GuiScreen {
    public static Category currentModuleType;
    public static Module currentModule;
    public float startX = (float)RenderUtil.width() / 2.0f - 210.0f;
    public float startY = -320.0f;
    public double alphaAnimation = 255.0;
    public double scaleAnimation = 100.0;
    public static int moduleStart;
    public static int valueStart;
    boolean previousmouse = true;
    boolean Rpreviousmouse = true;
    boolean mouse;
    public float moveX = 0.0f;
    public float moveY = 0.0f;
    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public int mouseWheel;
    public int mouseX;
    public int mouseY;
    static double roller;
    double moduleAnimY = 29.5;
    double valueAnimY = 30.0;
    static double moduleAnim;
    static double categoryAnim;
    static double valueAnim;
    static double animationStartX;
    static double animationStartY;
    private final AnimationUtil animationUtil = new AnimationUtil();

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.animationUtil.resetTime();
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.lastPercent = this.percent;
        this.lastPercent2 = this.percent2;
        if ((double)this.percent > 0.98) {
            this.percent = (float)((double)this.percent + ((0.98 - (double)this.percent) / (double)1.45f - 0.001));
        }
        if ((double)this.percent <= 0.98 && this.percent2 < 1.0f) {
            this.percent2 = (float)((double)this.percent2 + ((double)((1.0f - this.percent2) / 2.8f) + 0.002));
        }
        if (this.scaleAnimation != 100.0) {
            this.startX = (float)animationStartX;
            this.startY = (float)this.animationUtil.animateNoFast(this.startY, animationStartY, 10.0);
        } else {
            this.startX = (float)animationStartX;
            this.startY = (float)animationStartY;
        }
        this.alphaAnimation = this.animationUtil.animateNoFast(this.alphaAnimation, 0.0, 8.0);
        this.scaleAnimation = this.animationUtil.animateNoFast(this.scaleAnimation, 100.0, 10.0);
        float endX = this.startX + 420.0f;
        float endY = this.startY + 320.0f;
        float xAmount = endX - 210.0f;
        float yAmount = endY - 160.0f;
        float finalXAnim = (float)((double)xAmount - this.scaleAnimation / 100.0 * (double)xAmount);
        float finalYAnim = (float)((double)yAmount - this.scaleAnimation / 100.0 * (double)yAmount);
        GL11.glTranslatef(finalXAnim, finalYAnim, 0.0f);
        GL11.glScalef((float)(this.scaleAnimation / 100.0), (float)(this.scaleAnimation / 100.0), 0.0f);
        if (this.isHovered(this.startX, this.startY - 25.0f, this.startX + 400.0f, this.startY + 25.0f, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (this.moveX == 0.0f && this.moveY == 0.0f) {
                this.moveX = (float)mouseX - this.startX;
                this.moveY = (float)mouseY - this.startY;
            } else {
                animationStartX = (float)mouseX - this.moveX;
                animationStartY = (float)mouseY - this.moveY;
            }
            this.previousmouse = true;
        } else if (this.moveX != 0.0f || this.moveY != 0.0f) {
            this.moveX = 0.0f;
            this.moveY = 0.0f;
        }
        RenderUtil.drawRect(this.startX - 40.0f, this.startY, this.startX + 420.0f, this.startY + 320.0f, this.getColor(250, 250, 250, 255));
        RenderUtil.drawRect(this.startX + 60.0f, this.startY, this.startX + 200.0f, this.startY + 320.0f, this.getColor(240, 240, 240, 255));
        RenderUtil.drawRect(this.startX + 200.0f, this.startY, this.startX + 420.0f, this.startY + 320.0f, this.getColor(230, 230, 230, 255));
        RenderUtil.drawRect(this.startX + 60.0f, this.startY, this.startX + 200.0f, this.startY + 25.0f, this.getColor(230, 230, 230, 255));
        RenderUtil.drawShadow(this.startX - 40.0f, this.startY, this.startX + 420.0f, this.startY + 320.0f);
        FontManager.Tahoma18.drawString("Xylitol Client", this.startX - 18.0f, (float)((double)(this.startY + 4.0f) + FontManager.Tahoma18.getStringHeight()), this.getColor(100, 100, 100, 255));
        Category[] mY = Category.values();
        for (int m = 0; m < Category.values().length; ++m) {
            if (mY[m] != currentModuleType) {
                FontManager.arial18.drawString(mY[m].toString(), (int)this.startX + 2, (int)this.startY + 37 + m * 42, this.getColor(80, 80, 80, 255));
            } else {
                categoryAnim = this.animationUtil.animateNoFast(categoryAnim, m * 42, 12.0);
                FontManager.arial22.drawString(mY[m].toString(), (int)this.startX + 2, (int)this.startY + 36 + m * 42, this.getColor(80, 80, 80, 255));
                RenderUtil.drawRect((int)this.startX - 28, (double)((int)this.startY + 32) + categoryAnim, (int)this.startX - 26, (double)((int)this.startY + 48) + categoryAnim, HUD.mainColor.getColor());
            }
            RenderUtil.drawImage(new ResourceLocation("express/icon/clickgui/" + mY[m].toString() + ".png"), (int)this.startX - 18, (int)this.startY + 32 + m * 42, 16, 16, this.getColor(80, 80, 80, 255));
            if (!this.isCategoryHovered(this.startX - 20.0f, this.startY + 32.0f + (float)(m * 42), this.startX + 56.0f, this.startY + 48.0f + (float)(m * 42), mouseX, mouseY) || !Mouse.isButtonDown(0)) continue;
            currentModuleType = mY[m];
            currentModule = Client.instance.moduleManager.getModsByCategory(currentModuleType).size() != 0 ? Client.instance.moduleManager.getModsByCategory(currentModuleType).get(0) : null;
            moduleStart = 0;
            moduleAnim = 0.0;
        }
        double moduleSize = Client.instance.moduleManager.getModsByCategory(currentModuleType).size() - 1;
        this.mouseWheel = Mouse.getDWheel();
        if (this.isCategoryHovered(this.startX + 60.0f, this.startY, this.startX + 200.0f, this.startY + 320.0f, mouseX, mouseY)) {
            if (this.mouseWheel < 0 && 235.0 / moduleSize * (double)moduleStart < 235.0) {
                ++moduleStart;
            }
            if (this.mouseWheel > 0 && moduleStart > 0) {
                --moduleStart;
            }
        }
        if (this.isCategoryHovered(this.startX + 200.0f, this.startY, this.startX + 420.0f, this.startY + 320.0f, mouseX, mouseY)) {
            if (this.mouseWheel < 0 && valueStart < currentModule.getValues().size()) {
                ++valueStart;
            }
            if (this.mouseWheel > 0 && valueStart > 0) {
                --valueStart;
            }
        }
        if (this.moduleAnimY != (moduleAnim = (double)(-moduleStart)) * -25.0) {
            this.moduleAnimY = this.animationUtil.animateNoFast(this.moduleAnimY, -moduleAnim * 25.0, 40.0);
        }
        float moreAddY = (float)(-this.moduleAnimY);
        valueAnim = -moduleStart;
        if (this.valueAnimY != valueAnim * -20.0) {
            this.valueAnimY = this.animationUtil.animateNoFast(this.moduleAnimY, -moduleAnim * 25.0, 40.0);
        }
        if (Client.instance.moduleManager.getModsByCategory(currentModuleType).size() > 1) {
            roller = Math.min(235.0, this.animationUtil.animateNoFast(roller, 235.0 / moduleSize * (double)moduleStart, 15.0));
            RenderUtil.drawRect(this.startX + 199.0f, (float)((double)this.startY + 25.0 + roller), this.startX + 201.0f, (float)((double)this.startY + 25.0 + 60.0 + roller), this.getColor(160, 160, 160, 255));
        } else {
            RenderUtil.drawRect(0.0, 0.0, 0.0, 0.0, Color.white.getRGB());
        }
        FontManager.Tahoma16.drawString(currentModuleType.toString(), this.startX + 70.0f, this.startY + 12.5f, this.getColor(80, 80, 80, 255));
        if (currentModule != null) {
            int i;
            float CStartY = 29.0f;
            FontManager.Tahoma20.drawString(NormalClickGUI.currentModule.name, this.startX + 210.0f, this.startY + 12.5f, this.getColor(60, 60, 60, 255));
            RenderUtil.startGlScissor((int)(this.startX + 60.0f), (int)(this.startY + 22.5f), (int)(this.startX + 200.0f), 290);
            for (i = 0; i < Client.instance.moduleManager.getModsByCategory(currentModuleType).size(); ++i) {
                Module value = Client.instance.moduleManager.getModsByCategory(currentModuleType).get(i);
                RenderUtil.drawRoundRect(this.startX + 195.0f, CStartY + this.startY + moreAddY, this.startX + 65.0f, CStartY + 20.0f + this.startY + moreAddY, this.getColor(60, 60, 60, 255));
                if (!value.getState()) {
                    RenderUtil.drawGoodCircle(this.startX + 74.0f, CStartY + 10.0f + this.startY + moreAddY, 2.0f, this.getColor(120, 120, 120, 255));
                } else {
                    RenderUtil.drawGoodCircle(this.startX + 74.0f, CStartY + 10.0f + this.startY + moreAddY, 2.0f, this.getColor(81, 161, 255, 255));
                }
                if (this.isSettingsButtonHovered(this.startX + 67.0f, CStartY + 2.0f + this.startY + moreAddY, this.startX + 183.0f, CStartY + 16.0f + (float)FontManager.Tahoma20.getHeight() + this.startY + moreAddY, mouseX, mouseY)) {
                    if (!this.previousmouse && Mouse.isButtonDown(0)) {
                        value.setState(!value.getState());
                        this.previousmouse = true;
                    }
                    if (!this.previousmouse && Mouse.isButtonDown(1)) {
                        this.previousmouse = true;
                    }
                    FontManager.Tahoma16.drawString(value.getName(), this.startX + 86.0f, CStartY + 8.0f + this.startY + moreAddY, this.getColor(120, 120, 120, 255));
                } else {
                    FontManager.Tahoma16.drawString(value.getName(), this.startX + 86.0f, CStartY + 8.0f + this.startY + moreAddY, this.getColor(248, 248, 248, 255));
                }
                if (!Mouse.isButtonDown(0)) {
                    this.previousmouse = false;
                }
                if (this.isSettingsButtonHovered(this.startX + 90.0f, CStartY + this.startY + moreAddY, this.startX + 100.0f + (float)FontManager.Tahoma20.getStringWidth(value.getName()), CStartY + 8.0f + (float)FontManager.Tahoma20.getHeight() + this.startY + moreAddY, mouseX, mouseY) && Mouse.isButtonDown(1)) {
                    currentModule = value;
                    valueStart = 0;
                }
                FontManager.Tahoma20.drawString(":", this.startX + 185.0f, CStartY + 10.0f - (float)FontManager.Tahoma20.getHeight() / 2.0f + this.startY + moreAddY, this.getColor(255, 255, 255, 255));
                if (!((double)i >= (double)moduleStart + moduleAnim)) continue;
                CStartY += 25.0f;
            }
            RenderUtil.stopGlScissor();
            CStartY = this.startY + 30.0f;
            if (currentModule.getValues().isEmpty()) {
                FontManager.Tahoma18.drawCenteredString("No settings", this.startX + 310.0f, this.startY + 150.0f, this.getColor(60, 60, 60, 255));
            }
            RenderUtil.startGlScissor((int)(this.startX + 200.0f), (int)(this.startY + 12.5f), (int)(this.startX + 420.0f), 290);
            for (i = 0; i < currentModule.getValues().size() && CStartY <= this.startY + 300.0f; ++i) {
                float x;
                Value<?> cValue;
                if (i < valueStart || !(cValue = currentModule.getValues().get(i)).isAvailable()) continue;
                if (cValue instanceof NumberValue) {
                    x = this.startX + 295.0f;
                    double current = 62.0f * (((Number)((NumberValue)cValue).getValue()).floatValue() - ((NumberValue)cValue).getMin().floatValue()) / (((NumberValue)cValue).getMax().floatValue() - ((NumberValue)cValue).getMin().floatValue());
                    RenderUtil.drawRect(x - 4.0f, CStartY + 1.0f, (float)((double)x + 69.0), CStartY + 4.0f, this.getColor(50, 50, 50, 255));
                    RenderUtil.drawBorderedRect(x + 75.0f, CStartY - 3.0f, (float)((double)x + 100.0), CStartY + 9.0f, 1.0f, this.getColor(85, 85, 85, 255), this.getColor(55, 55, 55, 255));
                    RenderUtil.drawRect(x - 4.0f, CStartY + 1.0f, (float)((double)x + current + 0.5), CStartY + 4.0f, this.getColor(61, 141, 255, 255));
                    RenderUtil.drawRect((float)((double)x + current + 2.0), CStartY, (float)((double)x + current + 7.0), CStartY + 5.0f, this.getColor(100, 100, 100, 255));
                    FontManager.Tahoma18.drawString(cValue.getName(), this.startX + 210.0f, CStartY, this.getColor(100, 100, 100, 255));
                    if (!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                    }
                    if (((Number)cValue.getValue()).doubleValue() - (double)((Number)cValue.getValue()).intValue() == 0.0) {
                        FontManager.Tahoma18.drawCenteredStringWithShadow(String.valueOf(((Number)cValue.getValue()).intValue()), x + 67.0f + 20.0f, CStartY, this.getColor(255, 255, 255, 255));
                    } else {
                        FontManager.Tahoma18.drawCenteredStringWithShadow(String.valueOf((Number)cValue.getValue()), x + 67.0f + 20.0f, CStartY, this.getColor(255, 255, 255, 255));
                    }
                    if (this.isButtonHovered(x, CStartY - 2.0f, x + 100.0f, CStartY + 7.0f, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                        if (!this.previousmouse && Mouse.isButtonDown(0)) {
                            current = ((NumberValue)cValue).getMin();
                            double max = ((NumberValue)cValue).getMax();
                            double inc = ((NumberValue)cValue).getInc();
                            double valAbs = (double)mouseX - ((double)x + 1.0);
                            double perc = valAbs / 64.0;
                            perc = Math.min(Math.max(0.0, perc), 1.0);
                            double valRel = (max - current) * perc;
                            double val = current + valRel;
                            val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
                            ((NumberValue)cValue).setValue(val);
                        }
                        if (!Mouse.isButtonDown(0)) {
                            this.previousmouse = false;
                        }
                    }
                    CStartY += 25.0f;
                }
                if (cValue instanceof BoolValue) {
                    x = this.startX + 317.0f;
                    FontManager.Tahoma18.drawString(cValue.getName(), this.startX + 210.0f, CStartY, this.getColor(100, 100, 100, 255));
                    RenderUtil.drawRoundedRect(x + 56.0f, CStartY, x + 76.0f, CStartY + 8.0f, 3, this.getColor(60, 60, 60, 255));
                    if (((Boolean)cValue.getValue()).booleanValue()) {
                        RenderUtil.drawCircle((double)x + 70.5, CStartY + 4.0f, 4.0, this.getColor(61, 141, 255, 255));
                    } else {
                        RenderUtil.drawCircle(x + 62.0f, CStartY + 4.0f, 4.0, this.getColor(120, 120, 120, 255));
                    }
                    if (this.isCheckBoxHovered(x + 56.0f, CStartY, x + 76.0f, CStartY + 9.0f, mouseX, mouseY)) {
                        if (!this.previousmouse && Mouse.isButtonDown(0)) {
                            this.previousmouse = true;
                            this.mouse = true;
                        }
                        if (this.mouse) {
                            ((BoolValue) cValue).setValue(!((BoolValue) cValue).getValue());
                            this.mouse = false;
                        }
                    }
                    if (!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                    }
                    CStartY += 25.0f;
                }
                if (cValue instanceof ModeValue) {
                    x = this.startX + 300.0f;
                    final ModeValue cast = (ModeValue) cValue;
                    Enum<?> mode = (Enum<?>) cast.getValue();
                    int next_str = mode.ordinal() - 1 < 0 ? cast.getModes().length - 1 : mode.ordinal() - 1;
                    int next_str_2 = mode.ordinal() + 1 >= cast.getModes().length ? 0 : mode.ordinal() + 1;
                    FontManager.Tahoma18.drawString(cValue.getName(), this.startX + 210.0f, CStartY + 2.0f, this.getColor(100, 100, 100, 255));
                    RenderUtil.drawRect(x - 10.0f, CStartY - 5.0f, x + 95.0f, CStartY + 15.0f, this.getColor(56, 56, 56, 255));
                    RenderUtil.drawBorderRect(x - 10.0f, CStartY - 5.0f, x + 95.0f, CStartY + 15.0f, this.getColor(60, 60, 60, 255), 2.0);
                    for (int i1 = 0; i1 < 2; ++i1) {
                        FontManager.Tahoma18.drawStringWithShadow(cast.getModes()[next_str].name().substring(cast.getModes()[next_str].name().length() - 2 + i1, cast.getModes()[next_str].name().length() + i1 - 1), x - 3.0f + (float)(i1 * FontManager.Tahoma18.getStringWidth(cast.getModes()[next_str].name().substring(cast.getModes()[next_str].name().length() - 3 + i1, cast.getModes()[next_str].name().length() + i1 - 2))), CStartY + 2.0f, this.getColor(255, 255, 255, 80 / (3 - (i1 + 1))));
                        FontManager.Tahoma18.drawStringWithShadow(cast.getModes()[next_str_2].name().substring(i1, i1 + 1), x + 79.0f + (float)(i1 > 0 ? i1 * FontManager.Tahoma18.getStringWidth(cast.getModes()[next_str_2].name().substring(0, i1)) : 0), CStartY + 2.0f, this.getColor(255, 255, 255, 80 / (i1 + 1)));
                    }
                    FontManager.Tahoma18.drawStringWithShadow(mode.name(), x + 42.5f - (float)(FontManager.Tahoma18.getStringWidth(mode.name()) / 2), CStartY + 2.0f, this.getColor(255, 255, 255, 255));
                    if (this.isStringHovered(x, CStartY - 5.0f, x + 100.0f, CStartY + 15.0f, mouseX, mouseY)) {
                        int next;
                        if (!this.previousmouse && Mouse.isButtonDown(0)) {
                            next = mode.ordinal() + 1 >= cast.getModes().length ? 0 : mode.ordinal() + 1;
                            cast.setValue(cast.getModes()[next]);
                            this.previousmouse = true;
                        }
                        if (Mouse.isButtonDown(1) && !this.Rpreviousmouse) {
                            next = mode.ordinal() - 1 < 0 ? cast.getModes().length - 1 : mode.ordinal() - 1;
                            cast.setValue(cast.getModes()[next]);
                            this.Rpreviousmouse = true;
                        }
                        if (!Mouse.isButtonDown(0)) {
                            this.previousmouse = false;
                        }
                        if (!Mouse.isButtonDown(1)) {
                            this.Rpreviousmouse = false;
                        }
                    }
                    CStartY += 25.0f;
                }
                if (!(cValue instanceof ColorValue)) continue;
                FontManager.Tahoma18.drawString(cValue.getName(), this.startX + 210.0f, CStartY + 2.0f, this.getColor(100, 100, 100, 255));
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 20.0f, 0.0f);
                x = this.startX + 210.0f;
                Color lastColor = RenderUtil.getColor(((ColorValue)cValue).getColor());
                float[] color = Color.RGBtoHSB(lastColor.getRed(), lastColor.getGreen(), lastColor.getBlue(), null);
                double[] selectXY = new double[]{144.0f - 144.0f * color[1], 70.0f - 70.0f * color[2]};
                double selectX = color[0] * 144.0f;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glDisable(2884);
                GL11.glShadeModel(7425);
                RenderUtil.glColor(this.getColor(60, 60, 60, 255));
                RenderUtil.quickDrawRect(x - 1.0f, CStartY + 79.0f, x + 145.8f, CStartY + 91.0f);
                for (int H = 0; H <= 360; H += 2) {
                    GL11.glBegin(9);
                    RenderUtil.glColor(Color.HSBtoRGB((float)H / 360.0f, 1.0f, 1.0f));
                    GL11.glVertex2d(x + (float)H / 2.5f, CStartY + 80.0f);
                    RenderUtil.glColor(Color.HSBtoRGB((float)H / 360.0f, 1.0f, 1.0f));
                    GL11.glVertex2d(x + (float)H / 2.5f, CStartY + 90.0f);
                    RenderUtil.glColor(Color.HSBtoRGB((float)(H + 1) / 360.0f, 1.0f, 1.0f));
                    GL11.glVertex2d(x + (float)H / 2.5f + 0.8f, CStartY + 90.0f);
                    RenderUtil.glColor(Color.HSBtoRGB((float)(H + 1) / 360.0f, 1.0f, 1.0f));
                    GL11.glVertex2d(x + (float)H / 2.5f + 0.8f, CStartY + 80.0f);
                    GL11.glEnd();
                }
                GL11.glShadeModel(7424);
                GL11.glEnable(3553);
                GL11.glEnable(2884);
                GL11.glDisable(3042);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
                if (this.isButtonHovered(x, CStartY + 80.0f, x + 144.0f, CStartY + 90.0f, mouseX, mouseY - 20) && Mouse.isButtonDown(0)) {
                    if (!this.previousmouse && Mouse.isButtonDown(0)) {
                        selectX = (float)mouseX - x;
                    }
                    if (!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                    }
                }
                color = new float[]{(float)(selectX / 144.0), 1.0f, 1.0f};
                NormalClickGUI.drawRect(selectX + (double)x - 0.25, CStartY + 79.5f, selectX + (double)x + 0.25, CStartY + 90.5f, this.getColor(60, 60, 60, 255));
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glDisable(2884);
                GL11.glShadeModel(7425);
                for (int s = 0; s <= 100; ++s) {
                    GL11.glBegin(9);
                    RenderUtil.glColor(Color.getHSBColor(color[0], (float)(100 - s) / 100.0f, 1.0f).getRGB());
                    GL11.glVertex2d(x + (float)s * 1.44f, CStartY);
                    RenderUtil.glColor(Color.getHSBColor(color[0], (float)(100 - s) / 100.0f, 0.0f).getRGB());
                    GL11.glVertex2d(x + (float)s * 1.44f, CStartY + 70.0f);
                    RenderUtil.glColor(Color.getHSBColor(color[0], (float)(100 - s) / 100.0f, 0.0f).getRGB());
                    GL11.glVertex2d(x + (float)s * 1.44f + 1.44f, CStartY + 70.0f);
                    RenderUtil.glColor(Color.getHSBColor(color[0], (float)(100 - s) / 100.0f, 1.0f).getRGB());
                    GL11.glVertex2d(x + (float)s * 1.44f + 1.44f, CStartY);
                    GL11.glEnd();
                }
                GL11.glShadeModel(7424);
                GL11.glEnable(3553);
                GL11.glEnable(2884);
                GL11.glDisable(3042);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
                if (this.isButtonHovered(x, CStartY, x + 144.0f, CStartY + 70.0f, mouseX, mouseY - 20) && Mouse.isButtonDown(0)) {
                    if (!this.previousmouse && Mouse.isButtonDown(0)) {
                        selectXY = new double[]{(float)mouseX - x, (float)(mouseY - 20) - CStartY};
                    }
                    if (!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                    }
                }
                color = new float[]{color[0], (float)((144.0 - selectXY[0]) / 144.0), (float)((70.0 - selectXY[1]) / 70.0)};
                NormalClickGUI.drawRect((double)x + selectXY[0] - 1.0, (double)CStartY + selectXY[1] - 1.0, (double)x + selectXY[0] + 1.0, (double)CStartY + selectXY[1] + 1.0, this.getColor(0, 0, 0, 255));
                NormalClickGUI.drawRect((double)x + selectXY[0] - 0.5, (double)CStartY + selectXY[1] - 0.5, (double)x + selectXY[0] + 0.5, (double)CStartY + selectXY[1] + 0.5, this.getColor(200, 200, 200, 255));
                ((ColorValue)cValue).setColor(Color.getHSBColor(color[0], color[1], color[2]).getRGB());
                GL11.glPopMatrix();
                CStartY += 120.0f;
            }
            RenderUtil.stopGlScissor();
        }
        GL11.glScalef(1.0f / (float)(this.scaleAnimation / 100.0), 1.0f / (float)(this.scaleAnimation / 100.0), 0.0f);
        GL11.glTranslatef(-finalXAnim, -finalYAnim, 0.0f);
    }

    public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public int getColor(int red, int green, int blue, int alpha) {
        return new Color(red, green, blue, (int)Math.max(0.0, Math.min((double)alpha - this.alphaAnimation * (double)((float)alpha / 255.0f), 255.0))).getRGB();
    }

    static {
        moduleAnim = 0.0;
        categoryAnim = 0.0;
        valueAnim = 0.0;
        currentModuleType = Category.Combat;
        currentModule = Client.instance.moduleManager.getModsByCategory(currentModuleType).size() != 0 ? Client.instance.moduleManager.getModsByCategory(currentModuleType).get(0) : null;
        animationStartX = 100.0;
        animationStartY = 100.0;
        roller = 25.0;
        moduleStart = 0;
        valueStart = 0;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.ui.modules;

import shop.xiaoda.gui.ui.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.render.*;
import shop.xiaoda.module.*;
import shop.xiaoda.utils.render.fontRender.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.render.animation.*;
import shop.xiaoda.event.*;
import net.minecraft.util.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.*;
import java.util.*;
import java.util.List;

import shop.xiaoda.utils.render.*;

public class ModuleList extends UiModule
{
    public List<Module> modules;
    
    public ModuleList() {
        super("ModuleList", RenderUtil.width(), 0.0, 100.0, 0.0);
    }
    
    @EventTarget
    public void blur(final EventShader event) {
        double yOffset = 0.0;
        final ScaledResolution sr = new ScaledResolution(ModuleList.mc);
        for (final Module module : this.modules) {
            if (HUD.importantModules.getValue() && module.getCategory() == Category.Render) {
                continue;
            }
            final Animation moduleAnimation = module.getAnimation();
            if (!module.getState() && moduleAnimation.finished(Direction.BACKWARDS)) {
                continue;
            }
            final String displayText = this.formatModule(module);
            final double textWidth = FontManager.arial16.getStringWidth(displayText);
            final double xValue = sr.getScaledWidth() - 10;
            final boolean flip = xValue <= sr.getScaledWidth() / 2.0f;
            double x = flip ? xValue : (sr.getScaledWidth() - (textWidth + 3.0));
            final double y = yOffset + 4.0;
            final double heightVal = HUD.height.getValue() + 1.0;
            final String name = HUD.animation.getValue().name();
            switch (name) {
                case "MoveIn": {
                    if (flip) {
                        x -= Math.abs((moduleAnimation.getOutput() - 1.0) * (sr.getScaledWidth() - (2.0 + textWidth)));
                        break;
                    }
                    x += Math.abs((moduleAnimation.getOutput() - 1.0) * (2.0 + textWidth));
                    break;
                }
                case "ScaleIn": {
                    RenderUtil.scaleStart((float)(x + FontManager.arial16.getStringWidth(displayText) / 2.0f), (float)(y + heightVal / 2.0 - FontManager.arial16.getHeight() / 2.0f), (float)moduleAnimation.getOutput());
                    break;
                }
            }
            if (HUD.background.getValue()) {
                Gui.drawRect3((double)(float)(x - 2.0), (double)(float)(y - 3.0), (double)(FontManager.arial16.getStringWidth(displayText) + 5), (double)(float)heightVal, new Color(0, 0, 0).getRGB());
            }
            if (HUD.animation.getValue().name() == "ScaleIn") {
                RenderUtil.scaleEnd();
            }
            yOffset += moduleAnimation.getOutput() * heightVal;
        }
    }
    
    private String formatModule(final Module module) {
        String name = module.getName();
        name = name.replaceAll(" ", "");
        final String formatText = "%s %s%s";
        final String suffix = module.getSuffix();
        if (suffix == null || suffix.isEmpty()) {
            return name;
        }
        return String.format(formatText, name, EnumChatFormatting.GRAY, suffix);
    }
    
    @EventTarget
    public void onRender2D(EventRender2D event) {
        ArrayList<Module> moduleList = new ArrayList<Module>();
        moduleList.addAll(Client.instance.moduleManager.getModules());
        if (this.modules == null) {
            this.modules = moduleList;
            this.modules.removeIf(module -> module.getCategory() == Category.Render && (Boolean)HUD.importantModules.getValue() != false);
        }
        this.modules.sort(Comparator.comparingDouble(m -> {
            for (Module mod : moduleList) {
                String name = mod.getName() + (mod.getSuffix() != "" ? " " + mod.getSuffix() : "");
                return FontManager.arial16.getStringWidth(name);
            }
            return 0;
        }).reversed());
        double yOffset = 0.0;
        ScaledResolution sr = new ScaledResolution(mc);
        int count = 0;
        for (Module module2 : this.modules) {
            if (((Boolean)HUD.importantModules.getValue()).booleanValue() && module2.getCategory() == Category.Render) continue;
            Animation moduleAnimation = module2.getAnimation();
            moduleAnimation.setDirection(module2.getState() ? Direction.FORWARDS : Direction.BACKWARDS);
            if (!module2.getState() && moduleAnimation.finished(Direction.BACKWARDS)) continue;
            String displayText = this.formatModule(module2);
            double textWidth = FontManager.arial16.getStringWidth(displayText);
            double xValue = sr.getScaledWidth() - 10;
            boolean flip = xValue <= (double)((float)sr.getScaledWidth() / 2.0f);
            double x = flip ? xValue : (double)sr.getScaledWidth() - (textWidth + 3.0);
            float alphaAnimation = 1.0f;
            double y = yOffset + 4.0;
            double heightVal = (Double)HUD.height.getValue() + 1.0;
            switch (((ANIM)(HUD.animation.getValue())).name()) {
                case "MoveIn": {
                    if (flip) {
                        x -= Math.abs((moduleAnimation.getOutput() - 1.0) * ((double)sr.getScaledWidth() - (2.0 - textWidth)));
                        break;
                    }
                    x += Math.abs((moduleAnimation.getOutput() - 1.0) * (2.0 + textWidth));
                    break;
                }
                case "ScaleIn": {
                    RenderUtil.scaleStart((float)(x + (double)((float)FontManager.arial16.getStringWidth(displayText) / 2.0f)), (float)(y + heightVal / 2.0 - (double)((float)FontManager.arial16.getHeight() / 2.0f)), (float)moduleAnimation.getOutput());
                    alphaAnimation = (float)moduleAnimation.getOutput();
                }
            }
            if (((Boolean)HUD.background.getValue()).booleanValue()) {
                Gui.drawRect3((float)(x - 2.0), (float)(y - 3.0), FontManager.arial16.getStringWidth(displayText) + 5, (float)heightVal, ColorUtil.applyOpacity(new Color(20, 20, 20), ((Double)HUD.backgroundAlpha.getValue()).floatValue() * alphaAnimation).getRGB());
            }
            if (((Boolean)HUD.hLine.getValue()).booleanValue()) {
                Gui.drawRect3((float)RenderUtil.width() - 1.0f, (float)(y - 3.0), 1.0, (float)heightVal, HUD.color(count).getRGB());
            }
            int textcolor = HUD.color(count).getRGB();
            FontManager.arial16.drawStringWithShadow(displayText, (float)x, (float)(y - 1.0 + (double)FontManager.arial16.getMiddleOfBox((float)heightVal)), ColorUtil.applyOpacity(textcolor, alphaAnimation));
            if (((ANIM)(HUD.animation.getValue())).name() == "ScaleIn") {
                RenderUtil.scaleEnd();
            }
            yOffset += moduleAnimation.getOutput() * heightVal;
            ++count;
        }
    }
    
    public enum ANIM
    {
        MoveIn, 
        ScaleIn;
    }
}

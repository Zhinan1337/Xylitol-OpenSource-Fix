//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.ui.modules;

import shop.xiaoda.gui.ui.*;
import shop.xiaoda.utils.render.animation.impl.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import java.awt.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.render.fontRender.*;

import java.util.List;
import java.util.stream.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.render.animation.*;
import shop.xiaoda.module.modules.render.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.util.*;

public class PotionsInfo extends UiModule
{
    private int maxString;
    private final Map<Integer, Integer> potionMaxDurations;
    private final ContinualAnimation widthanimation;
    private final ContinualAnimation heightanimation;
    private final EaseBackIn animation;
    List<PotionEffect> effects;
    
    public PotionsInfo() {
        super("PotionsInfo", 20.0, 40.0, 150.0, 60.0);
        this.maxString = 0;
        this.potionMaxDurations = new HashMap<Integer, Integer>();
        this.widthanimation = new ContinualAnimation();
        this.heightanimation = new ContinualAnimation();
        this.animation = new EaseBackIn(200, 1.0, 1.3f);
        this.effects = new ArrayList<PotionEffect>();
    }
    
    private String get(final PotionEffect potioneffect) {
        final Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
        String s1 = I18n.format(potion.getName(), new Object[0]);
        s1 = s1 + " " + this.intToRomanByGreedy(potioneffect.getAmplifier() + 1);
        return s1;
    }
    
    private String intToRomanByGreedy(int num) {
        final int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        final String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < values.length && num >= 0; ++i) {
            while (values[i] <= num) {
                num -= values[i];
                stringBuilder.append(symbols[i]);
            }
        }
        return stringBuilder.toString();
    }
    
    @EventTarget
    public void onShader(final EventShader event) {
        RenderUtil.scaleStart((float)(this.getPosX() + 50.0), (float)(this.getPosY() + 15.0), (float)this.animation.getOutput());
        RoundedUtils.drawRound((float)this.getPosX(), (float)this.getPosY(), 100.0f, 30.0f, 0.0f, new Color(0, 0, 0, 255));
        RenderUtil.scaleEnd();
        final int x = (int)this.getPosX();
        final int y = (int)this.getPosY();
        Gui.drawRect3((double)x, (double)y, (double)(int)this.widthanimation.getOutput(), (double)(int)this.heightanimation.getOutput(), new Color(0, 0, 0, 255).getRGB());
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D event) {
        this.effects = PotionsInfo.mc.thePlayer.getActivePotionEffects().stream().sorted(Comparator.comparingInt(it -> FontManager.arial18.getStringWidth(this.get(it)))).collect(Collectors.toList());
        final int x = (int)this.getPosX();
        final int y = (int)this.getPosY();
        final int offsetX = 21;
        final int offsetY = 14;
        int i2 = 16;
        final ArrayList<Integer> needRemove = new ArrayList<Integer>();
        for (final Map.Entry<Integer, Integer> entry : this.potionMaxDurations.entrySet()) {
            if (PotionsInfo.mc.thePlayer.getActivePotionEffect(Potion.potionTypes[entry.getKey()]) == null) {
                needRemove.add(entry.getKey());
            }
        }
        for (final int id : needRemove) {
            this.potionMaxDurations.remove(id);
        }
        for (final PotionEffect effect : this.effects) {
            if (!this.potionMaxDurations.containsKey(effect.getPotionID()) || this.potionMaxDurations.get(effect.getPotionID()) < effect.getDuration()) {
                this.potionMaxDurations.put(effect.getPotionID(), effect.getDuration());
            }
        }
        final float width = this.effects.isEmpty() ? 0.0f : ((float)Math.max(50 + FontManager.arial18.getStringWidth(this.get(this.effects.get(this.effects.size() - 1))), 60 + FontManager.arial18.getStringWidth(this.get(this.effects.get(this.effects.size() - 1)))));
        final float height = (float)(this.effects.size() * 25);
        this.widthanimation.animate(width, 20);
        this.heightanimation.animate(height, 20);
        if (PotionsInfo.mc.currentScreen instanceof GuiChat && this.effects.isEmpty()) {
            this.animation.setDirection(Direction.FORWARDS);
        }
        else if (!(PotionsInfo.mc.currentScreen instanceof GuiChat)) {
            this.animation.setDirection(Direction.BACKWARDS);
        }
        RenderUtil.scaleStart((float)(x + 50), (float)(y + 15), (float)this.animation.getOutput());
        final HUD hud = this.getModule(HUD.class);
        FontManager.arial18.drawStringWithShadow("Potion Example", x + 52.0f - FontManager.arial18.getStringWidth("Potion Example") / 2, (float)(y + 18 - FontManager.arial18.getHeight() / 2), new Color(255, 255, 255, 60).getRGB());
        RenderUtil.scaleEnd();
        if (this.effects.isEmpty()) {
            this.maxString = 0;
        }
        if (!this.effects.isEmpty()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableLighting();
            final int l = 24;
            switch (hud.hudModeValue.getValue()) {
                case Shit: {
                    RoundedUtils.drawRound((float)x, (float)(y - 1), this.widthanimation.getOutput(), 1.0f, 1.0f, HUD.color(8));
                    Gui.drawRect3((double)x, (double)y, (double)(int)this.widthanimation.getOutput(), (double)(int)this.heightanimation.getOutput(), new Color(19, 19, 19, 230).getRGB());
                    break;
                }
                case Neon: {
                    RoundedUtils.drawRound((float)x, (float)y, this.widthanimation.getOutput(), this.heightanimation.getOutput(), 0.0f, new Color(0, 0, 0, 100));
                    break;
                }
            }
            RenderUtil.startGlScissor(x, y, (int)this.widthanimation.getOutput(), (int)this.heightanimation.getOutput());
            for (final PotionEffect potioneffect : this.effects) {
                final Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                if (potion.hasStatusIcon()) {
                    PotionsInfo.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                    final int i3 = potion.getStatusIconIndex();
                    GlStateManager.enableBlend();
                    PotionsInfo.mc.ingameGUI.drawTexturedModalRect(x + offsetX - 17, y + i2 - offsetY + 2, 0 + i3 % 8 * 18, 198 + i3 / 8 * 18, 18, 18);
                }
                final String s = Potion.getDurationString(potioneffect);
                final String s2 = this.get(potioneffect);
                FontManager.arial18.drawStringWithShadow(s2, (float)(x + offsetX + 3), (float)(y + i2 - offsetY + 2), -1);
                FontManager.arial18.drawStringWithShadow(s, (float)(x + offsetX + 3), (float)(y + i2 + 11 - offsetY + 2), -1);
                i2 += l;
                if (this.maxString < PotionsInfo.mc.fontRendererObj.getStringWidth(s2)) {
                    this.maxString = PotionsInfo.mc.fontRendererObj.getStringWidth(s2);
                }
            }
            RenderUtil.stopGlScissor();
        }
    }
}

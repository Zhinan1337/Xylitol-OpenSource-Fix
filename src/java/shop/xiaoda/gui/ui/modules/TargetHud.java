//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.ui.modules;

import shop.xiaoda.gui.ui.*;
import java.text.*;
import shop.xiaoda.utils.render.animation.impl.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.module.modules.combat.*;
import shop.xiaoda.*;
import shop.xiaoda.utils.render.animation.*;
import shop.xiaoda.event.*;
import shop.xiaoda.module.modules.render.*;
import net.minecraft.entity.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.render.fontRender.*;
import java.awt.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.util.*;
import java.util.List;

import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import shop.xiaoda.utils.render.*;

public class TargetHud extends UiModule
{
    private boolean sentParticles;
    public final List<Particle> particles;
    private final TimeUtil timer;
    private final ContinualAnimation animation2;
    final DecimalFormat DF_1;
    private EntityLivingBase target;
    private final Animation animation;
    
    public TargetHud() {
        super("TargetHud", 50.0, 50.0, 200.0, 120.0);
        this.particles = new ArrayList<Particle>();
        this.timer = new TimeUtil();
        this.animation2 = new ContinualAnimation();
        this.DF_1 = new DecimalFormat("0.0");
        this.animation = new EaseBackIn(500, 1.0, 1.5f);
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        final KillAura aura = Client.instance.moduleManager.getModule(KillAura.class);
        if (!aura.getState()) {
            this.animation.setDirection(Direction.BACKWARDS);
        }
        if (KillAura.target != null) {
            this.target = KillAura.target;
            this.animation.setDirection(Direction.FORWARDS);
        }
        if (aura.getState() && (KillAura.target == null || this.target != KillAura.target)) {
            this.animation.setDirection(Direction.BACKWARDS);
        }
        if (TargetHud.mc.currentScreen instanceof GuiChat) {
            this.animation.setDirection(Direction.FORWARDS);
            this.target = (EntityLivingBase)TargetHud.mc.thePlayer;
        }
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D event) {
        final float x = (float)this.getPosX();
        float y = (float)this.getPosY();
        final KillAura ka = Client.instance.moduleManager.getModule(KillAura.class);
        if (HUD.multi_targetHUD.getValue()) {
            if (TargetHud.mc.currentScreen instanceof GuiChat) {
                this.render(x, y, 1.0f, (EntityLivingBase)TargetHud.mc.thePlayer, false);
            }
            else if (!KillAura.targets.isEmpty()) {
                int count = 0;
                for (int i = 0; i < KillAura.targets.size(); ++i) {
                    final Entity target = KillAura.targets.get(i);
                    if (count <= 4) {
                        this.render(x, y, 1.0f, (EntityLivingBase)target, false);
                        y += 60.0f;
                        ++count;
                    }
                }
            }
        }
        else {
            RenderUtil.scaleStart((float)(x + this.width / 2.0), (float)(y + this.height / 2.0), (float)this.animation.getOutput());
            this.render(x, y, 1.0f, this.target, false);
            RenderUtil.scaleEnd();
        }
    }
    
    @EventTarget
    public void shader(final EventShader event) {
        final float x = (float)this.getPosX();
        float y = (float)this.getPosY();
        final KillAura ka = Client.instance.moduleManager.getModule(KillAura.class);
        if (HUD.thudmodeValue.getValue().equals(HUD.THUDMode.WTFNovo) && event.isBloom()) {
            return;
        }
        if (HUD.multi_targetHUD.getValue()) {
            if (TargetHud.mc.currentScreen instanceof GuiChat) {
                this.render(x, y, 1.0f, (EntityLivingBase)TargetHud.mc.thePlayer, true);
            }
            else if (!KillAura.targets.isEmpty()) {
                int count = 0;
                for (int i = 0; i < KillAura.targets.size(); ++i) {
                    final Entity target = KillAura.targets.get(i);
                    if (count <= 4) {
                        this.render(x, y, 1.0f, (EntityLivingBase)target, true);
                        y += 60.0f;
                        ++count;
                    }
                }
            }
        }
        else {
            RenderUtil.scaleStart((float)(x + this.width / 2.0), (float)(y + this.height / 2.0), (float)this.animation.getOutput());
            this.render(x, y, 1.0f, this.target, true);
            RenderUtil.scaleEnd();
        }
    }
    
    public void render(final float x, final float y, final float alpha, final EntityLivingBase target, final boolean blur) {
        final Color firstColor = HUD.color(1);
        final Color secondColor = HUD.color(6);
        final String name = HUD.thudmodeValue.getValue().name();
        switch (name) {
            case "Neon": {
                final float width = (float)Math.max(128, FontManager.arial20.getStringWidth("Name: " + target.getName()) + 60);
                this.width = width;
                this.height = 50.0;
                if (blur) {
                    Gui.drawRect3((double)x, (double)y, (double)width, 50.0, new Color(0, 0, 0).getRGB());
                    Gui.drawRect3((double)x, (double)y, (double)width, 1.0, HUD.mainColor.getColor());
                    return;
                }
                Gui.drawRect3((double)x, (double)y, (double)width, 50.0, new Color(19, 19, 19, 180).getRGB());
                RenderUtil.drawHGradientRect(x, y, width, 1.0, firstColor.getRGB(), secondColor.getRGB());
                final int textColor = -1;
                Gui.drawRect3((double)x, (double)y, (double)width, 50.0, new Color(0, 0, 0, (int)(110.0f * alpha)).getRGB());
                final int scaleOffset = (int)(target.hurtTime * 0.35f);
                final float healthPercent = (target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount());
                final float var = (width - 28.0f) * healthPercent;
                target.animatedHealthBar = AnimationUtil.animate(target.animatedHealthBar, var, 0.1f);
                RenderUtil.drawHGradientRect(x + 5.0f, y + 40.0f, target.animatedHealthBar, 5.0, firstColor.getRGB(), secondColor.getRGB());
                for (final Particle p : this.particles) {
                    p.x = x + 20.0f;
                    p.y = y + 20.0f;
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    if (p.opacity > 4.0f) {
                        p.render2D();
                    }
                }
                if (target instanceof AbstractClientPlayer) {
                    GlStateManager.pushMatrix();
                    this.drawBigHead(x + 5.0f + scaleOffset / 2.0f, y + 5.0f + scaleOffset / 2.0f, (float)(30 - scaleOffset), (float)(30 - scaleOffset), (AbstractClientPlayer)target);
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                }
                else {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(x + 5.0f + scaleOffset / 2.0f + 15.0f, y + 5.0f + scaleOffset / 2.0f + 25.0f, 40.0f);
                    GlStateManager.scale(0.31, 0.31, 0.31);
                    this.drawModel(target.rotationYaw, target.rotationPitch, target);
                    GlStateManager.popMatrix();
                }
                if (this.timer.hasTimeElapsed(16L, true)) {
                    for (final Particle p : this.particles) {
                        p.updatePosition();
                        if (p.opacity < 1.0f) {
                            this.particles.remove(p);
                        }
                    }
                }
                final double healthNum = MathUtil.round(target.getHealth() + target.getAbsorptionAmount(), 1);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                FontManager.arial18.drawString(String.valueOf(healthNum), x + target.animatedHealthBar + 8.0f, y + 38.0f, textColor);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                FontManager.arial20.drawString("Name: " + target.getName(), x + 40.0f, y + 8.0f, textColor);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                FontManager.arial20.drawString("Distance: " + MathUtil.round(TargetHud.mc.thePlayer.getDistanceToEntity((Entity)target), 1), x + 40.0f, y + 20.0f, textColor);
                if (target.hurtTime == 9 && !this.sentParticles) {
                    for (int i = 0; i <= 15; ++i) {
                        final Particle particle = new Particle();
                        particle.init(x + 20.0f, y + 20.0f, (float)((Math.random() - 0.5) * 2.0 * 1.4), (float)((Math.random() - 0.5) * 2.0 * 1.4), (float)(Math.random() * 4.0), RenderUtil.reAlpha(Color.RED, target.hurtTime * 10));
                        this.particles.add(particle);
                    }
                    this.sentParticles = true;
                }
                if (target.hurtTime == 8) {
                    this.sentParticles = false;
                    break;
                }
                break;
            }
            case "Novoline": {
                final FontRenderer fr = TargetHud.mc.fontRendererObj;
                final double healthPercentage = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0.0f, 1.0f);
                this.width = Math.max(120, fr.getStringWidth(target.getName()) + 50);
                this.height = 36.0;
                final int alphaInt = (int)(alpha * 255.0f);
                Gui.drawRect3((double)x, (double)y, this.width, 36.0, new Color(29, 29, 29, alphaInt).getRGB());
                Gui.drawRect3((double)(x + 1.0f), (double)(y + 1.0f), this.width - 2.0, 34.0, new Color(40, 40, 40, alphaInt).getRGB());
                Gui.drawRect3((double)(x + 34.0f), (double)(y + 15.0f), 83.0, 10.0, -14213603);
                final float f = (float)(83.0 * healthPercentage);
                target.animatedHealthBar = AnimationUtil.animate(target.animatedHealthBar, f, 0.1f);
                RenderUtil.drawHGradientRect(x + 34.0f, y + 15.0f, target.animatedHealthBar, 10.0, firstColor.darker().darker().getRGB(), secondColor.darker().darker().getRGB());
                RenderUtil.drawHGradientRect(x + 34.0f, y + 15.0f, f, 10.0, firstColor.getRGB(), secondColor.getRGB());
                final int textColor = -1;
                final int mcTextColor = -1;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                if (target instanceof AbstractClientPlayer) {
                    RenderUtil.glColor(textColor);
                    this.drawBigHead(x + 3.5f, y + 3.0f, 28.0f, 28.0f, (AbstractClientPlayer)target);
                }
                else {
                    fr.drawStringWithShadow("?", x + 17.0f - fr.getStringWidth("?") / 2.0f, y + 17.0f - fr.FONT_HEIGHT / 2.0f, mcTextColor);
                }
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                fr.drawStringWithShadow(target.getName(), x + 34.0f, y + 5.0f, mcTextColor);
                final String healthText = MathUtil.round(healthPercentage * 100.0, 0) + "%";
                fr.drawStringWithShadow(healthText, (float)(x + 17.0f + this.width / 2.0 - fr.getStringWidth(healthText) / 2.0f), y + 16.0f, mcTextColor);
                break;
            }
            case "Exhibition": {
                GlStateManager.pushMatrix();
                this.width = (float)((FontManager.arial18.getStringWidth(target.getName()) > 70.0f) ? (125.0f + FontManager.arial18.getStringWidth(target.getName()) - 70.0f) : 125.0);
                this.height = 45.0;
                GlStateManager.translate(x, y + 6.0f, 0.0f);
                RenderUtil.skeetRect(0.0, -2.0, (FontManager.arial18.getStringWidth(target.getName()) > 70.0f) ? ((double)(124.0f + FontManager.arial18.getStringWidth(target.getName()) - 70.0f)) : 124.0, 38.0, 1.0);
                RenderUtil.skeetRectSmall(0.0, -2.0, 124.0, 38.0, 1.0);
                FontManager.arial18.drawStringWithShadow(target.getName(), 41.0f, 0.3f, -1);
                final float health = target.getHealth();
                final float healthWithAbsorption = target.getHealth() + target.getAbsorptionAmount();
                final float progress = health / target.getMaxHealth();
                final Color healthColor = (health >= 0.0f) ? ColorUtil.getBlendColor(target.getHealth(), target.getMaxHealth()).brighter() : Color.RED;
                double cockWidth = 0.0;
                cockWidth = MathUtil.round(cockWidth, 5);
                if (cockWidth < 50.0) {
                    cockWidth = 50.0;
                }
                final double healthBarPos = cockWidth * progress;
                Gui.drawRect(42.5, 10.3, 53.0 + healthBarPos + 0.5, 13.5, healthColor.getRGB());
                if (target.getAbsorptionAmount() > 0.0f) {
                    Gui.drawRect(97.5 - target.getAbsorptionAmount(), 10.3, 103.5, 13.5, new Color(137, 112, 9).getRGB());
                }
                RenderUtil.drawBorderedRect2(42.0, 9.800000190734863, 54.0 + cockWidth, 14.0, 0.5, 0, Color.BLACK.getRGB());
                for (int dist = 1; dist < 10; ++dist) {
                    final double cock = cockWidth / 8.5 * dist;
                    Gui.drawRect(43.5 + cock, 9.8, 43.5 + cock + 0.5, 14.0, Color.BLACK.getRGB());
                }
                GlStateManager.scale(0.5, 0.5, 0.5);
                final int distance = (int)TargetHud.mc.thePlayer.getDistanceToEntity((Entity)target);
                final String nice = "HP: " + (int)healthWithAbsorption + " | Dist: " + distance;
                TargetHud.mc.fontRendererObj.drawString(nice, 85.3f, 32.3f, -1, true);
                GlStateManager.scale(2.0, 2.0, 2.0);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                if (target != null) {
                    drawEquippedShit(28, 20, target);
                }
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.scale(0.31, 0.31, 0.31);
                GlStateManager.translate(73.0f, 102.0f, 40.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawModel(target.rotationYaw, target.rotationPitch, target);
                GlStateManager.popMatrix();
                break;
            }
            case "ThunderHack": {
                final double healthPercentage = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0.0f, 1.0f);
                if (blur) {
                    RoundedUtils.round(x, y, 70.0f, 50.0f, 6.0f, new Color(0, 0, 0, 255));
                    RoundedUtils.round(x + 50.0f, y, 10.0f, 50.0f, 6.0f, new Color(0, 0, 0, 255));
                    return;
                }
                this.setWidth(170.0);
                this.setHeight(50.0);
                RoundedUtils.round(x, y, 70.0f, 50.0f, 6.0f, new Color(0, 0, 0, 139));
                RoundedUtils.round(x + 50.0f, y, 100.0f, 50.0f, 6.0f, new Color(0, 0, 0, 255));
                RenderUtil.drawImageRound(new ResourceLocation("express/thud.png"), x + 30.0f, y - 1.0f, 150.0, 80.0, new Color(255, 255, 255, 150).getRGB(), () -> RoundedUtils.round(x + 50.0f, y, 100.0f, 50.0f, 6.0f, new Color(0, 0, 0, 255)));
                GlStateManager.resetColor();
                if (target instanceof AbstractClientPlayer) {
                    ParticleRender.render(x + 2.0f, y + 2.0f, target);
                    this.drawBigHeadRound(x + 2.0f, y + 3.0f, 44.0f, 44.0f, (AbstractClientPlayer)target);
                }
                FontManager.arial18.drawString(target.getName(), x + 54.0f, y + 6.0f, -1);
                final float f = (float)(92.0 * healthPercentage);
                target.animatedHealthBar = AnimationUtil.animate(target.animatedHealthBar, f, 0.1f);
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.8, 0.8, 0.8);
                if (target != null) {
                    drawEquippedShit2((int)(x + 42.0f) / 0.8, (int)(y + 18.0f) / 0.8, target);
                }
                GlStateManager.popMatrix();
                GlowUtils.drawGlow(x + 54.0f, y + 36.0f, target.animatedHealthBar, 8.0f, 16, HUD.mainColor.getColorC(), () -> RoundedUtils.drawRound(x + 54.0f, y + 36.0f, target.animatedHealthBar, 8.0f, 2.0f, HUD.mainColor.getColorC()));
                RoundedUtils.drawRound(x + 54.0f, y + 36.0f, target.animatedHealthBar, 8.0f, 2.0f, HUD.mainColor.getColorC());
                FontManager.arial14.drawCenteredString(String.valueOf(MathUtil.round(target.getHealth(), 1)), x + 100.0f, y + 38.0f, -1);
                break;
            }
            case "Raven": {
                final ScaledResolution sr = new ScaledResolution(TargetHud.mc);
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, 0.0f);
                RoundedUtils.round(0.0f, 0.0f, 70.0f + TargetHud.mc.fontRendererObj.getStringWidth(target.getName()), 40.0f, 12.0f, new Color(0, 0, 0, 92));
                RenderUtil.drawOutline(8.0f, 0.0f, 62.0f + TargetHud.mc.fontRendererObj.getStringWidth(target.getName()), 24.0f, 8.0f, 2.0f, 6.0f, firstColor.brighter(), secondColor.brighter());
                TargetHud.mc.fontRendererObj.drawStringWithShadow(target.getName(), 7.0f, 10.0f, new Color(244, 67, 54).getRGB());
                TargetHud.mc.fontRendererObj.drawStringWithShadow((target.getHealth() > TargetHud.mc.thePlayer.getHealth()) ? "L" : "W", TargetHud.mc.fontRendererObj.getStringWidth(target.getName()) + 55.0f, 10.0f, (target.getHealth() > TargetHud.mc.thePlayer.getHealth()) ? new Color(244, 67, 54).getRGB() : new Color(0, 255, 0).getRGB());
                TargetHud.mc.fontRendererObj.drawStringWithShadow(this.DF_1.format(target.getHealth()), 7.0f + TargetHud.mc.fontRendererObj.getStringWidth(target.getName()) + 4.0f, 10.0f, RenderUtil.getHealthColor(target.getHealth(), target.getMaxHealth()).getRGB());
                RoundedUtils.drawGradientRoundLR(6.0f, 25.0f, (float)((int)((70.0f + TargetHud.mc.fontRendererObj.getStringWidth(target.getName()) - 5.0f) * (target.getHealth() / target.getMaxHealth())) - 6), 5.0f, 2.0f, firstColor.brighter(), secondColor.brighter());
                GlStateManager.resetColor();
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                break;
            }
            case "Sils": {
                final DecimalFormat DF = new DecimalFormat("0.0");
                this.setWidth(Math.max(120.0f, FontManager.arial18.getStringWidth(target.getName() + "") + 100.0f));
                this.setHeight(45.0);
                RoundedUtils.drawRound(x, y, (float)this.getWidth(), (float)this.getHeight(), 5.0f, new Color(0, 0, 0, 120));
                RoundedUtils.drawRoundOutline(x, y, (float)this.getWidth(), (float)this.getHeight(), 5.0f, 0.5f, new Color(0, 0, 0, 0), Color.WHITE);
                float hurt_time = (float)Math.max(7, target.hurtTime);
                String str;
                if (TargetHud.mc.thePlayer.getHealth() >= target.animatedHealthBar) {
                    str = "Winning";
                }
                else {
                    str = "Losing";
                }
                target.animatedHealthBar = AnimationUtil.animate(target.animatedHealthBar, target.getHealth(), 0.2f);
                TargetHud.mc.fontRendererObj.drawStringWithShadow("Name: " + target.getName(), x + 43.0f, y + 7.0f, Color.WHITE.getRGB());
                TargetHud.mc.fontRendererObj.drawStringWithShadow("HP: " + DF.format(target.animatedHealthBar) + " | " + str, x + 43.0f, y + 19.0f, Color.WHITE.getRGB());
                RoundedUtils.drawRound(x + 43.0f, y + 32.0f, (float)(this.getWidth() - 53.0), 5.0f, 2.0f, new Color(255, 255, 255, 40));
                RoundedUtils.drawGradientRound(x + 43.0f, y + 33.0f, (float)(target.animatedHealthBar / target.getMaxHealth() * (this.getWidth() - 53.0)), 4.0f, 2.0f, firstColor, firstColor, secondColor, secondColor);
                NetworkPlayerInfo playerInfo = TargetHud.mc.getNetHandler().getPlayerInfo(TargetHud.mc.thePlayer.getUniqueID());
                if (target instanceof EntityPlayer) {
                    playerInfo = TargetHud.mc.getNetHandler().getPlayerInfo(target.getUniqueID());
                }
                if (playerInfo != null && target instanceof AbstractClientPlayer) {
                    final float renderHurtTime = target.hurtTime - ((target.hurtTime != 0) ? Minecraft.getMinecraft().timer.renderPartialTicks : 0.0f);
                    final float hurtPercent = renderHurtTime / 10.0f;
                    GL11.glColor4f(1.0f, 1.0f - hurtPercent, 1.0f - hurtPercent, 1.0f);
                    GL11.glPushMatrix();
                    this.drawBigHead(x + hurt_time, y + hurt_time, 44.0f - hurt_time * 2.0f, 44.0f - hurt_time * 2.0f, (AbstractClientPlayer)target);
                    GL11.glPopMatrix();
                }
                if (hurt_time == 7.0f) {
                    hurt_time = 0.0f;
                    break;
                }
                break;
            }
            case "WTFNovo": {
                this.setWidth(Math.max(120, FontManager.arial18.getStringWidth(target.getName()) + 15));
                this.setHeight(38.0);
                final double healthPercentage = MathHelper.clamp_float((target.animatedHealthBar + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0.0f, 1.0f);
                final int bg = new Color(0.0f, 0.0f, 0.0f, 0.4f * alpha).getRGB();
                final float hurtPercent = target.hurtTime / 10.0f;
                float scale;
                if (hurtPercent == 0.0f) {
                    scale = 1.0f;
                }
                else if (hurtPercent < 0.5f) {
                    scale = 1.0f - 0.1f * hurtPercent * 2.0f;
                }
                else {
                    scale = 0.9f + 0.1f * (hurtPercent - 0.5f) * 2.0f;
                }
                if (blur) {
                    Gui.drawRect3((double)x, (double)y, this.getWidth(), this.getHeight(), new Color(0, 0, 0).getRGB());
                    return;
                }
                Gui.drawRect3((double)x, (double)y, this.getWidth(), this.getHeight(), bg);
                Gui.drawRect3(x + 2.5, (double)(y + 31.0f), this.getWidth() - 6.0, 1.5, bg);
                Gui.drawRect3(x + 2.5, (double)(y + 34.0f), this.getWidth() - 6.0, 1.5, bg);
                final float endWidth = (float)Math.max(0.0, (this.getWidth() - 6.0) * healthPercentage);
                this.animation2.animate(endWidth, 18);
                if (this.animation2.getOutput() > 0.0f) {
                    RenderUtil.drawGradientRect(x + 2.5, y + 31.0f, x + 1.5 + this.animation2.getOutput(), y + 32.5, ColorUtil.applyOpacity(-16737215, alpha), ColorUtil.applyOpacity(-7405631, alpha));
                }
                final double armorValue = target.getTotalArmorValue() / 20.0;
                if (armorValue > 0.0) {
                    RenderUtil.drawGradientRect(x + 2.5, y + 34.0f, x + 1.5 + (this.getWidth() - 6.0) * armorValue, y + 35.5, ColorUtil.applyOpacity(-16750672, alpha), ColorUtil.applyOpacity(-12986881, alpha));
                }
                GlStateManager.pushMatrix();
                RenderUtil.setAlphaLimit(0.0f);
                final int textColor = ColorUtil.applyOpacity(-1, alpha);
                if (target instanceof AbstractClientPlayer) {
                    RenderUtil.color(textColor);
                    final float f = 0.8125f;
                    GlStateManager.scale(f, f, f);
                    RenderUtil.scaleStart(x / f + 3.0f + 16.0f, y / f + 3.0f + 16.0f, scale);
                    GL11.glColor4f(1.0f, 1.0f - hurtPercent, 1.0f - hurtPercent, 1.0f);
                    this.drawBigHead(x / f + 3.0f, y / f + 3.0f, 32.0f, 32.0f, (AbstractClientPlayer)target);
                    RenderUtil.scaleEnd();
                }
                else {
                    Gui.drawRect3((double)(x + 3.0f), (double)(y + 3.0f), 25.0, 25.0, bg);
                    GlStateManager.scale(2.0f, 2.0f, 2.0f);
                    FontManager.arial18.drawStringWithShadow("?", (x + 11.0f) / 2.0f, (y + 11.0f) / 2.0f, textColor);
                }
                GlStateManager.popMatrix();
                FontManager.arial18.drawStringWithShadow(target.getName(), x + 31.0f, y + 5.0f, textColor);
                FontManager.arial14.drawStringWithShadow("Health: " + this.DF_1.format(target.getHealth()), x + 31.0f, y + 15.0f, textColor);
                FontManager.arial14.drawStringWithShadow("Distance: " + this.DF_1.format(TargetHud.mc.thePlayer.getDistanceToEntity((Entity)target)) + "m", x + 31.0f, y + 22.0f, textColor);
                final float delta = (float)RenderUtil.deltaTime;
                target.animatedHealthBar += (float)((target.getHealth() - target.animatedHealthBar) / Math.pow(2.0, 6.0) * delta);
                break;
            }
            case "Exire": {
                final float hurtPercent = target.hurtTime / 10.0f;
                this.setWidth(Math.max(70.0f, FontManager.arial18.getStringWidth(target.getName() + "") + 64.0f));
                this.setHeight(32.0);
                if (blur) {
                    RenderUtil.drawRectWH(x, y, (float)this.getWidth(), (float)this.getHeight(), new Color(0, 0, 0, 255).getRGB());
                    return;
                }
                RenderUtil.drawRectWH(x, y, (float)this.getWidth(), (float)this.getHeight(), new Color(19, 19, 19, 220).getRGB());
                target.animatedHealthBar = AnimationUtil.animate(target.animatedHealthBar, target.getHealth(), 0.15f);
                FontManager.arial24.drawStringWithShadow(target.getName(), x + 32.0f, y + 6.0f, Color.WHITE.getRGB());
                RenderUtil.drawRectWH(x + 33.0f, y + 22.0f, this.getWidth() - 38.0, 6.0, new Color(0, 0, 0, 230).getRGB());
                RoundedUtils.drawGradientCornerLR(x + 33.0f, y + 23.0f, (float)(target.animatedHealthBar / target.getMaxHealth() * (this.getWidth() - 38.0)), 4.0f, 0.0f, firstColor, secondColor);
                final int textColor = ColorUtil.applyOpacity(-1, alpha);
                if (target instanceof AbstractClientPlayer) {
                    GlStateManager.pushMatrix();
                    RenderUtil.color(textColor);
                    final float f = 0.8125f;
                    GlStateManager.scale(f, f, f);
                    GL11.glColor4f(1.0f, 1.0f - hurtPercent, 1.0f - hurtPercent, 1.0f);
                    this.drawBigHead(x / f + 3.0f, y / f + 3.5f, 32.0f, 32.0f, (AbstractClientPlayer)target);
                    GlStateManager.popMatrix();
                    GlStateManager.resetColor();
                    break;
                }
                break;
            }
            case "Moon": {
                float getMaxHel;
                if (target.getMaxHealth() < 20.0f) {
                    getMaxHel = target.getMaxHealth();
                }
                else {
                    getMaxHel = 20.0f;
                }
                this.setWidth(Math.max(26.0f + getMaxHel * 3.0f + FontManager.arial18.getStringWidth(target.getName()) - 34.0f, 28.0f + getMaxHel * 3.0f));
                this.setHeight(36.0);
                if (blur) {
                    RoundedUtils.drawRound(x, y, Math.max(39.0f + getMaxHel * 3.0f, (float)(39 + FontManager.arial18.getStringWidth(target.getName()))), 36.0f, 5.0f, new Color(0, 0, 0));
                    return;
                }
                RoundedUtils.drawRound(x, y, Math.max(39.0f + getMaxHel * 3.0f, (float)(39 + FontManager.arial18.getStringWidth(target.getName()))), 36.0f, 5.0f, new Color(0, 0, 0, 100));
                if (target instanceof AbstractClientPlayer) {
                    this.drawBigHeadRound(x + 3.0f, y + 3.0f, 30.0f, 30.0f, (AbstractClientPlayer)target);
                }
                FontManager.arial18.drawString(target.getName(), x + 36.0f, y + 6.0f, new Color(255, 255, 255).getRGB());
                double nmsl;
                if (target.getHealth() - Math.floor(target.getHealth()) >= 0.5) {
                    nmsl = 0.5;
                }
                else {
                    nmsl = 0.0;
                }
                FontManager.arial14.drawString(Math.floor(target.getHealth()) + nmsl + " HP", x + 36.0f, y + 6.0f + FontManager.arial18.getHeight(), new Color(255, 255, 255).getRGB());
                target.animatedHealthBar = AnimationUtil.animate(target.animatedHealthBar, target.getHealth(), 0.15f);
                RoundedUtils.drawRound(x + 36.0f, y + 16.0f + FontManager.arial16.getHeight(), target.animatedHealthBar / target.getMaxHealth() * Math.max(getMaxHel * 3.0f, (float)FontManager.arial18.getStringWidth(target.getName())), 5.0f, 2.5f, HUD.color(8));
                break;
            }
        }
    }
    
    public static void drawEquippedShit(final int x, final int y, final EntityLivingBase target) {
        if (!(target instanceof EntityPlayer)) {
            return;
        }
        GL11.glPushMatrix();
        final ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
        int cock = -2;
        for (int geraltOfNigeria = 3; geraltOfNigeria >= 0; --geraltOfNigeria) {
            final ItemStack armor = target.getCurrentArmor(geraltOfNigeria);
            if (armor != null) {
                stuff.add(armor);
            }
        }
        if (target.getHeldItem() != null) {
            stuff.add(target.getHeldItem());
        }
        for (final ItemStack yes : stuff) {
            if (Minecraft.getMinecraft().theWorld != null) {
                RenderHelper.enableGUIStandardItemLighting();
                cock += 16;
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(yes, cock + x, y, 255.0f);
            Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, yes, cock + x, y);
            GlStateManager.disableBlend();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
            yes.getEnchantmentTagList();
        }
        GL11.glPopMatrix();
    }
    
    public static void drawEquippedShit2(final double x, final double y, final EntityLivingBase target) {
        if (!(target instanceof EntityPlayer)) {
            return;
        }
        GL11.glPushMatrix();
        final ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
        int cock = -2;
        if (target.getHeldItem() != null) {
            stuff.add(target.getHeldItem());
        }
        for (int geraltOfNigeria = 3; geraltOfNigeria >= 0; --geraltOfNigeria) {
            final ItemStack armor = target.getCurrentArmor(geraltOfNigeria);
            if (armor != null) {
                stuff.add(armor);
            }
        }
        for (final ItemStack yes : stuff) {
            if (Minecraft.getMinecraft().theWorld != null) {
                RenderHelper.enableGUIStandardItemLighting();
                cock += 16;
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            GL11.glHint(3155, 4352);
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(yes, cock + x, y, 255.0f);
            Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, yes, cock + x, y);
            GlStateManager.disableBlend();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
            yes.getEnchantmentTagList();
        }
        GL11.glPopMatrix();
    }
    
    public void drawModel(final float yaw, final float pitch, final EntityLivingBase entityLivingBase) {
        GlStateManager.resetColor();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 0.0f, 50.0f);
        GlStateManager.scale(-50.0f, 50.0f, 50.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        final float renderYawOffset = entityLivingBase.renderYawOffset;
        final float rotationYaw = entityLivingBase.rotationYaw;
        final float rotationPitch = entityLivingBase.rotationPitch;
        final float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
        final float rotationYawHead = entityLivingBase.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((float)(-Math.atan(pitch / 40.0f) * 20.0), 1.0f, 0.0f, 0.0f);
        entityLivingBase.renderYawOffset = yaw - 0.4f;
        entityLivingBase.rotationYaw = yaw - 0.2f;
        entityLivingBase.rotationPitch = pitch;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        final RenderManager renderManager = TargetHud.mc.getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw((Entity)entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        renderManager.setRenderShadow(true);
        entityLivingBase.renderYawOffset = renderYawOffset;
        entityLivingBase.rotationYaw = rotationYaw;
        entityLivingBase.rotationPitch = rotationPitch;
        entityLivingBase.prevRotationYawHead = prevRotationYawHead;
        entityLivingBase.rotationYawHead = rotationYawHead;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.resetColor();
    }
    
    protected void drawBigHead(final float x, final float y, final float width, final float height, final AbstractClientPlayer player) {
        final double offset = -(player.hurtTime * 23);
        RenderUtil.glColor(new Color(255, (int)(255.0 + offset), (int)(255.0 + offset)).getRGB());
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        TargetHud.mc.getTextureManager().bindTexture(player.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }
    
    protected void drawBigHeadRound(final float x, final float y, final float width, final float height, final AbstractClientPlayer player) {
        StencilUtil.initStencilToWrite();
        RenderUtil.renderRoundedRect(x, y, width, height, 4.0f, -1);
        StencilUtil.readStencilBuffer(1);
        RenderUtil.color(-1);
        this.drawBigHead(x, y, width, height, player);
        StencilUtil.uninitStencilBuffer();
        GlStateManager.disableBlend();
    }
    
    public static class Particle
    {
        public float x;
        public float y;
        public float adjustedX;
        public float adjustedY;
        public float deltaX;
        public float deltaY;
        public float size;
        public float opacity;
        public Color color;
        
        public void render2D() {
            RoundedUtils.round(this.x + this.adjustedX, this.y + this.adjustedY, this.size, this.size, 12.0f, this.color);
        }
        
        public void updatePosition() {
            for (int i = 1; i <= 2; ++i) {
                this.adjustedX += this.deltaX;
                this.adjustedY += this.deltaY;
                this.deltaY *= (float)0.97;
                this.deltaX *= (float)0.97;
                --this.opacity;
                if (this.opacity < 1.0f) {
                    this.opacity = 1.0f;
                }
            }
        }
        
        public void init(final float x, final float y, final float deltaX, final float deltaY, final float size, final Color color) {
            this.x = x;
            this.y = y;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.size = size;
            this.opacity = 254.0f;
            this.color = color;
        }
    }
}

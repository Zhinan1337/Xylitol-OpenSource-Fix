//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import java.awt.*;
import shop.xiaoda.module.*;
import org.apache.commons.lang3.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import net.minecraft.entity.player.*;
import shop.xiaoda.module.modules.combat.*;
import java.util.*;
import java.util.List;

import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import shop.xiaoda.utils.render.*;

public class ChinaHat extends Module
{
    private final ModeValue<ColorMode> colorModeValue;
    private final BoolValue renderInFirstPersonValue;
    private final BoolValue target;
    private final NumberValue sizeValue;
    private final NumberValue pointsValue;
    private final NumberValue offSetValue;
    public final ColorValue colorValue;
    public final ColorValue secondColorValue;
    public final ColorValue thirdColorValue;
    private final double[][] pointsCache;
    private int lastPoints;
    private double lastSize;
    private float yaw;
    private float prevYaw;
    private float pitch;
    private float prevPitch;
    private final Color[] gradient;
    private final Color[] cherry;
    private final Color[] rainbow;
    private final Color[] astolfo;
    private final Color[] metrix;
    
    public ChinaHat() {
        super("ChinaHat", Category.Render);
        this.colorModeValue = new ModeValue<ColorMode>("Color", ColorMode.values(), ColorMode.Astolfo);
        this.renderInFirstPersonValue = new BoolValue("First Person", false);
        this.target = new BoolValue("Target", false);
        this.sizeValue = new NumberValue("Size", 0.5, 0.0, 2.0, 0.1);
        this.pointsValue = new NumberValue("Points", 30.0, 3.0, 180.0, 1.0);
        this.offSetValue = new NumberValue("OffSet", 2000.0, 0.0, 5000.0, 100.0);
        this.colorValue = new ColorValue("Color", new Color(255, 255, 255).getRGB());
        this.secondColorValue = new ColorValue("SecondColor", new Color(0, 0, 0).getRGB());
        this.thirdColorValue = new ColorValue("ThirdColor", new Color(0, 0, 0).getRGB());
        this.pointsCache = new double[181][2];
        this.gradient = new Color[] { new Color(255, 150, 255), new Color(255, 132, 199), new Color(211, 101, 187), new Color(160, 80, 158), new Color(120, 63, 160), new Color(123, 65, 168), new Color(104, 52, 152), new Color(142, 74, 175), new Color(160, 83, 179), new Color(255, 110, 189), new Color(255, 150, 255) };
        this.cherry = new Color[] { new Color(35, 255, 145), new Color(35, 255, 145), new Color(35, 255, 145), new Color(35, 255, 145), new Color(35, 255, 145), new Color(155, 155, 155), new Color(255, 50, 130), new Color(255, 50, 130), new Color(255, 50, 130), new Color(255, 50, 130), new Color(255, 50, 130), new Color(200, 200, 200) };
        this.rainbow = new Color[] { new Color(30, 250, 215), new Color(0, 200, 255), new Color(50, 100, 255), new Color(100, 50, 255), new Color(255, 50, 240), new Color(255, 0, 0), new Color(255, 150, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(80, 240, 155) };
        this.astolfo = new Color[] { new Color(252, 106, 140), new Color(252, 106, 213), new Color(218, 106, 252), new Color(145, 106, 252), new Color(106, 140, 252), new Color(106, 213, 252), new Color(106, 213, 252), new Color(106, 140, 252), new Color(145, 106, 252), new Color(218, 106, 252), new Color(252, 106, 213), new Color(252, 106, 140) };
        this.metrix = new Color[] { new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)) };
    }
    
    @EventTarget
    public void onUpdatePosition(final EventMotion event) {
        if (event.isPre()) {
            this.yaw = event.getYaw();
            this.prevYaw = event.getPrevYaw();
            this.pitch = event.getPitch();
            this.prevPitch = event.getPrevPitch();
        }
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        final Iterator<EntityPlayer> iterator = getLoadedPlayers().iterator();
        if (this.lastSize != this.sizeValue.getValue() || this.lastPoints != this.pointsValue.getValue()) {
            this.lastSize = this.sizeValue.getValue();
            this.genPoints(this.lastPoints = this.pointsValue.getValue().intValue(), this.lastSize);
        }
        while (iterator.hasNext()) {
            final EntityPlayer entity = iterator.next();
            if (entity == ChinaHat.mc.thePlayer && ChinaHat.mc.gameSettings.thirdPersonView != 0) {
                this.drawHat(event, (EntityLivingBase)entity);
            }
            if (this.target.getValue() && entity == KillAura.target) {
                this.drawHat(event, (EntityLivingBase)entity);
            }
        }
    }
    
    public static List<EntityPlayer> getLoadedPlayers() {
        return (List<EntityPlayer>)ChinaHat.mc.theWorld.playerEntities;
    }
    
    private void drawHat(final EventRender3D event, final EntityLivingBase entity) {
        final boolean isPlayerSP = entity.isEntityEqual((Entity)ChinaHat.mc.thePlayer);
        if (ChinaHat.mc.gameSettings.thirdPersonView == 0 && isPlayerSP && !this.renderInFirstPersonValue.getValue()) {
            return;
        }
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        final Color[] colors = new Color[181];
        Color[] colorMode = null;
        switch (this.colorModeValue.getValue()) {
            case Purple: {
                colorMode = this.gradient;
                break;
            }
            case Astolfo: {
                colorMode = this.astolfo;
                break;
            }
            case Cherry: {
                colorMode = this.cherry;
                break;
            }
            case Matrix: {
                colorMode = this.metrix;
                break;
            }
            case Custom: {
                colorMode = new Color[] { new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()).darker(), new Color(this.colorValue.getValue()).darker().darker(), new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()).darker(), new Color(this.colorValue.getValue()).darker().darker(), new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()).darker(), new Color(this.colorValue.getValue()).darker().darker(), new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()) };
                break;
            }
            case Blend: {
                colorMode = new Color[] { new Color(this.colorValue.getValue()).darker().darker(), new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()), new Color(this.colorValue.getValue()).darker().darker(), new Color(this.secondColorValue.getValue()).darker().darker(), new Color(this.secondColorValue.getValue()), new Color(this.secondColorValue.getValue()), new Color(this.secondColorValue.getValue()), new Color(this.secondColorValue.getValue()).darker().darker(), new Color(this.thirdColorValue.getValue()).darker().darker(), new Color(this.thirdColorValue.getValue()), new Color(this.thirdColorValue.getValue()), new Color(this.thirdColorValue.getValue()), new Color(this.thirdColorValue.getValue()).darker().darker() };
                break;
            }
            default: {
                colorMode = this.rainbow;
                break;
            }
        }
        for (int i = 0; i < colors.length; ++i) {
            colors[i] = ((this.colorModeValue.getValue() == ColorMode.Rainbow) ? this.fadeBetween(colorMode, 6000.0, i * (6000.0 / this.pointsValue.getValue())) : this.fadeBetween(colorMode, (double)this.offSetValue.getValue().longValue(), i * (this.offSetValue.getValue().floatValue() / this.pointsValue.getValue())));
        }
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 1.9, z);
        if (entity.isSneaking()) {
            GL11.glTranslated(0.0, -0.2, 0.0);
        }
        GL11.glRotatef(RenderUtil.interpolate(this.prevYaw, this.yaw, event.getPartialTicks()), 0.0f, -1.0f, 0.0f);
        final float interpolate = RenderUtil.interpolate(this.prevPitch, this.pitch, event.getPartialTicks());
        GL11.glRotatef(interpolate / 3.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslated(0.0, 0.0, (double)(interpolate / 270.0f));
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(2);
        this.drawCircle(this.pointsValue.getValue().intValue() - 1, colors, 255);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glBegin(6);
        GL11.glVertex3d(0.0, this.sizeValue.getValue() / 2.0, 0.0);
        this.drawCircle(this.pointsValue.getValue().intValue(), colors, 85);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glShadeModel(7424);
        GL11.glEnable(2929);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
    }
    
    private void drawCircle(final int points, final Color[] colors, final int alpha) {
        for (int i = 0; i <= points; ++i) {
            final double[] point = this.pointsCache[i];
            final Color clr = colors[i];
            GL11.glColor4f(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, alpha / 255.0f);
            GL11.glVertex3d(point[0], 0.0, point[1]);
        }
    }
    
    private void genPoints(final int points, final double size) {
        for (int i = 0; i <= points; ++i) {
            final double cos = size * StrictMath.cos(i * 3.141592653589793 * 2.0 / points);
            final double sin = size * StrictMath.sin(i * 3.141592653589793 * 2.0 / points);
            this.pointsCache[i][0] = cos;
            this.pointsCache[i][1] = sin;
        }
    }
    
    public Color fadeBetween(final Color[] table, final double progress) {
        final int i = table.length;
        if (progress == 1.0) {
            return table[0];
        }
        if (progress == 0.0) {
            return table[i - 1];
        }
        final double max = Math.max(0.0, (1.0 - progress) * (i - 1));
        final int min = (int)max;
        return this.fadeBetween(table[min], table[min + 1], max - min);
    }
    
    public Color fadeBetween(final Color start, final Color end, double progress) {
        if (progress > 1.0) {
            progress = 1.0 - progress % 1.0;
        }
        return this.gradient(start, end, progress);
    }
    
    public Color gradient(final Color start, final Color end, final double progress) {
        final double invert = 1.0 - progress;
        return new Color((int)(start.getRed() * invert + end.getRed() * progress), (int)(start.getGreen() * invert + end.getGreen() * progress), (int)(start.getBlue() * invert + end.getBlue() * progress), (int)(start.getAlpha() * invert + end.getAlpha() * progress));
    }
    
    public Color fadeBetween(final Color[] table, final double speed, final double offset) {
        return this.fadeBetween(table, (System.currentTimeMillis() + offset) % speed / speed);
    }
    
    public enum ColorMode
    {
        Astolfo, 
        Rainbow, 
        Cherry, 
        Custom, 
        Purple, 
        Blend, 
        Matrix;
    }
}

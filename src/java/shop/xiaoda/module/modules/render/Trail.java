//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import net.minecraft.util.*;
import shop.xiaoda.module.*;
import java.awt.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.util.*;
import java.util.List;

import shop.xiaoda.utils.render.*;
import shop.xiaoda.utils.render.GLUtil;

public class Trail extends Module
{
    private final ModeValue<MODE> mode;
    private final NumberValue particleAmount;
    private final BoolValue seeThroughWalls;
    private final ModeValue<COLORMODE> colorMode;
    private final ColorValue color;
    private final List<Vec3> path;
    
    public Trail() {
        super("Trail", Category.Render);
        this.mode = new ModeValue<MODE>("Mode", MODE.values(), MODE.Line);
        this.particleAmount = new NumberValue("Particle Amount", 15.0, 1.0, 500.0, 1.0);
        this.seeThroughWalls = new BoolValue("Walls", true);
        this.colorMode = new ModeValue<COLORMODE>("Color Mode", COLORMODE.values(), COLORMODE.Sync);
        this.color = new ColorValue("Color", Color.WHITE.getRGB());
        this.path = new ArrayList<Vec3>();
    }
    
    @EventTarget
    public void onMotionEvent(final EventMotion e) {
        if (e.isPre()) {
            if (Trail.mc.thePlayer.lastTickPosX != Trail.mc.thePlayer.posX || Trail.mc.thePlayer.lastTickPosY != Trail.mc.thePlayer.posY || Trail.mc.thePlayer.lastTickPosZ != Trail.mc.thePlayer.posZ) {
                this.path.add(new Vec3(Trail.mc.thePlayer.posX, Trail.mc.thePlayer.posY, Trail.mc.thePlayer.posZ));
            }
            while (this.path.size() > this.particleAmount.getValue()) {
                this.path.remove(0);
            }
        }
    }
    
    @EventTarget
    public void onRender3DEvent(final EventRender3D event) {
        int i = 0;
        final Pair<Color, Color> colors = this.colorMode.getValue().name().equals("Custom") ? Pair.of(this.color.getColorC(), this.color.getColorC()) : Pair.of(HUD.mainColor.getColorC());
        final String name = this.mode.getValue().name();
        switch (name) {
            case "Rise": {
                if (this.seeThroughWalls.getValue()) {
                    GlStateManager.disableDepth();
                }
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glBlendFunc(770, 771);
                for (final Vec3 v : this.path) {
                    ++i;
                    boolean draw = true;
                    final double x = v.xCoord - Trail.mc.getRenderManager().renderPosX;
                    final double y = v.yCoord - Trail.mc.getRenderManager().renderPosY;
                    final double z = v.zCoord - Trail.mc.getRenderManager().renderPosZ;
                    final double distanceFromPlayer = Trail.mc.thePlayer.getDistance(v.xCoord, v.yCoord - 1.0, v.zCoord);
                    int quality = (int)(distanceFromPlayer * 4.0 + 10.0);
                    if (quality > 350) {
                        quality = 350;
                    }
                    if (i % 10 != 0 && distanceFromPlayer > 25.0) {
                        draw = false;
                    }
                    if (i % 3 == 0 && distanceFromPlayer > 15.0) {
                        draw = false;
                    }
                    if (draw) {
                        GL11.glPushMatrix();
                        GL11.glTranslated(x, y, z);
                        final float scale = 0.06f;
                        GL11.glScalef(-0.06f, -0.06f, -0.06f);
                        GL11.glRotated((double)(-Trail.mc.getRenderManager().playerViewY), 0.0, 1.0, 0.0);
                        GL11.glRotated((double)Trail.mc.getRenderManager().playerViewX, 1.0, 0.0, 0.0);
                        final Color c = ColorUtil.interpolateColorsBackAndForth(7, 3 + i * 20, colors.getFirst(), colors.getSecond(), false);
                        RenderUtil.drawFilledCircleNoGL(0, -2, 0.7, ColorUtil.applyOpacity(c.getRGB(), 0.6f), quality);
                        if (distanceFromPlayer < 4.0) {
                            RenderUtil.drawFilledCircleNoGL(0, -2, 1.4, ColorUtil.applyOpacity(c.getRGB(), 0.25f), quality);
                        }
                        if (distanceFromPlayer < 20.0) {
                            RenderUtil.drawFilledCircleNoGL(0, -2, 2.3, ColorUtil.applyOpacity(c.getRGB(), 0.15f), quality);
                        }
                        GL11.glScalef(0.8f, 0.8f, 0.8f);
                        GL11.glPopMatrix();
                    }
                }
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
                if (this.seeThroughWalls.getValue()) {
                    GlStateManager.enableDepth();
                }
                GL11.glColor3d(255.0, 255.0, 255.0);
                break;
            }
            case "Line": {
                this.renderLine(this.path, colors);
                break;
            }
        }
    }
    
    public void onEnable() {
        this.path.clear();
        super.onEnable();
    }
    
    public void onDisable() {
        this.path.clear();
        super.onDisable();
    }
    
    public void renderLine(final List<Vec3> path) {
        this.renderLine(path, Pair.of(Color.WHITE));
    }
    
    public void renderLine(final List<Vec3> path, final Pair<Color, Color> colors) {
        GlStateManager.disableDepth();
        RenderUtil.setAlphaLimit(0.0f);
        RenderUtil.resetColor();
        GLUtil.setup2DRendering();
        GLUtil.startBlend();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(3.0f);
        GL11.glBegin(3);
        int count = 0;
        int alpha = 200;
        final int fadeOffset = 15;
        for (final Vec3 v : path) {
            if (fadeOffset > count) {
                alpha = count * (200 / fadeOffset);
            }
            RenderUtil.resetColor();
            RenderUtil.color(RenderUtil.reAlpha(ColorUtil.interpolateColorsBackAndForth(15, count * 5, colors.getFirst(), colors.getSecond(), false), alpha).getRGB());
            final double x = v.xCoord - Trail.mc.getRenderManager().renderPosX;
            final double y = v.yCoord - Trail.mc.getRenderManager().renderPosY;
            final double z = v.zCoord - Trail.mc.getRenderManager().renderPosZ;
            GL11.glVertex3d(x, y, z);
            ++count;
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GLUtil.end2DRendering();
        GlStateManager.enableDepth();
    }
    
    private enum MODE
    {
        Line, 
        Rise;
    }
    
    private enum COLORMODE
    {
        Sync, 
        Custom;
    }
}

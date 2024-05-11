// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.utils.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import org.lwjgl.compatibility.display.Display;
import org.lwjgl.compatibility.util.glu.Cylinder;
import org.lwjgl.opengl.GL11;
import shop.xiaoda.utils.client.MathUtil;
import shop.xiaoda.utils.render.fontRender.RapeMasterFontManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RenderUtil {
    private static final Map<Integer, Boolean> glCapMap = new HashMap<Integer, Boolean>();
    public static int deltaTime;
    private static final Frustum FRUSTUM;
    private static final Tessellator tessellator;
    private static int lastScaledWidth;
    private static int lastScaledHeight;
    private static float lastGuiScale;
    private static ScaledResolution scaledResolution;
    private static final WorldRenderer worldrenderer;
    public static final Pattern COLOR_PATTERN;
    private static final Minecraft mc;
    private static final int[] DISPLAY_LISTS_2D;
    public static int[] enabledCaps;

    public static void drawGradientRectBordered(double left, double top, double right, double bottom, double width, int startColor, int endColor, int borderStartColor, int borderEndColor) {
        RenderUtil.drawGradientRect(left + width, top + width, right - width, bottom - width, startColor, endColor);
        RenderUtil.drawGradientRect(left + width, top, right - width, top + width, borderStartColor, borderEndColor);
        RenderUtil.drawGradientRect(left, top, left + width, bottom, borderStartColor, borderEndColor);
        RenderUtil.drawGradientRect(right - width, top, right, bottom, borderStartColor, borderEndColor);
        RenderUtil.drawGradientRect(left + width, bottom - width, right - width, bottom, borderStartColor, borderEndColor);
    }

    public static void drawLoadingCircle(float x, float y) {
        for (int i = 0; i < 2; ++i) {
            int rot = (int)(System.nanoTime() / 5000000L * (long)i % 360L);
            RenderUtil.drawCircle(x, y, i * 8, rot - 180, rot);
        }
    }

    public static void drawCircle(float x, float y, float radius, int start, int end) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtil.glColor(Color.WHITE.getRGB());
        GL11.glEnable(2848);
        GL11.glLineWidth(3.0f);
        GL11.glBegin(3);
        for (float i = (float)end; i >= (float)start; i -= 4.0f) {
            GL11.glVertex2f((float)((double)x + Math.cos((double)i * Math.PI / 180.0) * (double)(radius * 1.001f)), (float)((double)y + Math.sin((double)i * Math.PI / 180.0) * (double)(radius * 1.001f)));
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void renderOne() {
        StencilUtil.checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(4.0f);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void renderFour(int color) {
        RenderUtil.setColor(color);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }

    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }

    public static void setColor(int colorHex) {
        float alpha = (float)(colorHex >> 24 & 0xFF) / 255.0f;
        float red = (float)(colorHex >> 16 & 0xFF) / 255.0f;
        float green = (float)(colorHex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(colorHex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        GLUtil.setup2DRendering();
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        RenderUtil.color(startColor);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        RenderUtil.color(endColor);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(2848);
        GLUtil.end2DRendering();
        RenderUtil.resetColor();
    }

    public static double[] getInterpolatedPos(Entity entity) {
        float ticks = RenderUtil.mc.timer.renderPartialTicks;
        return new double[]{MathUtil.interpolate(entity.lastTickPosX, entity.posX, ticks) - RenderUtil.mc.getRenderManager().viewerPosX, MathUtil.interpolate(entity.lastTickPosY, entity.posY, ticks) - RenderUtil.mc.getRenderManager().viewerPosY, MathUtil.interpolate(entity.lastTickPosZ, entity.posZ, ticks) - RenderUtil.mc.getRenderManager().viewerPosZ};
    }

    public static void draw2D(BlockPos blockPos, int color, int backgroundColor) {
        RenderManager renderManager = mc.getRenderManager();
        double posX = (double)blockPos.getX() + 0.5 - renderManager.renderPosX;
        double posY = (double)blockPos.getY() - renderManager.renderPosY;
        double posZ = (double)blockPos.getZ() + 0.5 - renderManager.renderPosZ;
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GlStateManager.rotate(-RenderUtil.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GlStateManager.depthMask(true);
        RenderUtil.glColor(color);
        GL11.glCallList(DISPLAY_LISTS_2D[0]);
        RenderUtil.glColor(backgroundColor);
        GL11.glCallList(DISPLAY_LISTS_2D[1]);
        GlStateManager.translate(0.0f, 9.0f, 0.0f);
        RenderUtil.glColor(color);
        GL11.glCallList(DISPLAY_LISTS_2D[2]);
        RenderUtil.glColor(backgroundColor);
        GL11.glCallList(DISPLAY_LISTS_2D[3]);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GlStateManager.popMatrix();
    }

    public static AxisAlignedBB getInterpolatedBoundingBox(Entity entity) {
        double[] renderingEntityPos = RenderUtil.getInterpolatedPos(entity);
        double entityRenderWidth = (double)entity.width / 1.5;
        return new AxisAlignedBB(renderingEntityPos[0] - entityRenderWidth, renderingEntityPos[1], renderingEntityPos[2] - entityRenderWidth, renderingEntityPos[0] + entityRenderWidth, renderingEntityPos[1] + (double)entity.height + (entity.isSneaking() ? -0.3 : 0.18), renderingEntityPos[2] + entityRenderWidth).expand(0.15, 0.15, 0.15);
    }

    public static void renderBoundingBox(EntityLivingBase entityLivingBase, Color color, float alpha) {
        AxisAlignedBB bb = RenderUtil.getInterpolatedBoundingBox(entityLivingBase);
        GlStateManager.pushMatrix();
        GLUtil.setup2DRendering();
        GLUtil.enableCaps(3042, 2832, 2881, 2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(3.0f);
        GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        RenderUtil.color(RenderUtil.reAlpha(color, (int)alpha).getRGB());
        RenderGlobal.drawSelectionBoundingBox(bb, false, true);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GLUtil.disableCaps();
        GLUtil.end2DRendering();
        GlStateManager.popMatrix();
    }

    public static void drawEntityServerESP(Entity entity, float red, float green, float blue, float alpha, float lineAlpha, float lineWidth) {
        double d0 = (double)entity.serverPosX / 32.0;
        double d2 = (double)entity.serverPosY / 32.0;
        double d3 = (double)entity.serverPosZ / 32.0;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase livingBase = (EntityLivingBase)entity;
            d0 = (double)livingBase.realPosX / 32.0;
            d2 = (double)livingBase.realPosY / 32.0;
            d3 = (double)livingBase.realPosZ / 32.0;
        }
        float x = (float)(d0 - mc.getRenderManager().getRenderPosX());
        float y = (float)(d2 - mc.getRenderManager().getRenderPosY());
        float z = (float)(d3 - mc.getRenderManager().getRenderPosZ());
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtil.otherDrawBoundingBox(entity, x, y, z, entity.width - 0.2f, entity.height + 0.1f);
        if (lineWidth > 0.0f) {
            GL11.glLineWidth(lineWidth);
            GL11.glColor4f(red, green, blue, lineAlpha);
            RenderUtil.otherDrawOutlinedBoundingBox(entity, x, y, z, entity.width - 0.2f, entity.height + 0.1f);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void otherDrawOutlinedBoundingBox(Entity entity, float x, float y, float z, double width, double height) {
        float newYaw4;
        float newYaw3;
        float newYaw2;
        float newYaw1;
        width *= 1.5;
        float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0f;
        if (yaw1 < 0.0f) {
            newYaw1 = 0.0f;
            newYaw1 += 360.0f - Math.abs(yaw1);
        } else {
            newYaw1 = yaw1;
        }
        newYaw1 *= -1.0f;
        newYaw1 *= (float)Math.PI / 180;
        float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0f;
        if (yaw2 < 0.0f) {
            newYaw2 = 0.0f;
            newYaw2 += 360.0f - Math.abs(yaw2);
        } else {
            newYaw2 = yaw2;
        }
        newYaw2 *= -1.0f;
        newYaw2 *= (float)Math.PI / 180;
        float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0f;
        if (yaw3 < 0.0f) {
            newYaw3 = 0.0f;
            newYaw3 += 360.0f - Math.abs(yaw3);
        } else {
            newYaw3 = yaw3;
        }
        newYaw3 *= -1.0f;
        newYaw3 *= (float)Math.PI / 180;
        float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0f;
        if (yaw4 < 0.0f) {
            newYaw4 = 0.0f;
            newYaw4 += 360.0f - Math.abs(yaw4);
        } else {
            newYaw4 = yaw4;
        }
        newYaw4 *= -1.0f;
        newYaw4 *= (float)Math.PI / 180;
        float x2 = (float)(Math.sin(newYaw1) * width + (double)x);
        float z2 = (float)(Math.cos(newYaw1) * width + (double)z);
        float x3 = (float)(Math.sin(newYaw2) * width + (double)x);
        float z3 = (float)(Math.cos(newYaw2) * width + (double)z);
        float x4 = (float)(Math.sin(newYaw3) * width + (double)x);
        float z4 = (float)(Math.cos(newYaw3) * width + (double)z);
        float x5 = (float)(Math.sin(newYaw4) * width + (double)x);
        float z5 = (float)(Math.cos(newYaw4) * width + (double)z);
        float y2 = (float)((double)y + height);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }

    public static void otherDrawBoundingBox(Entity entity, float x, float y, float z, double width, double height) {
        float newYaw4;
        float newYaw3;
        float newYaw2;
        float newYaw1;
        width *= 1.5;
        float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0f;
        if (yaw1 < 0.0f) {
            newYaw1 = 0.0f;
            newYaw1 += 360.0f - Math.abs(yaw1);
        } else {
            newYaw1 = yaw1;
        }
        newYaw1 *= -1.0f;
        newYaw1 *= (float)Math.PI / 180;
        float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0f;
        if (yaw2 < 0.0f) {
            newYaw2 = 0.0f;
            newYaw2 += 360.0f - Math.abs(yaw2);
        } else {
            newYaw2 = yaw2;
        }
        newYaw2 *= -1.0f;
        newYaw2 *= (float)Math.PI / 180;
        float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0f;
        if (yaw3 < 0.0f) {
            newYaw3 = 0.0f;
            newYaw3 += 360.0f - Math.abs(yaw3);
        } else {
            newYaw3 = yaw3;
        }
        newYaw3 *= -1.0f;
        newYaw3 *= (float)Math.PI / 180;
        float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0f;
        if (yaw4 < 0.0f) {
            newYaw4 = 0.0f;
            newYaw4 += 360.0f - Math.abs(yaw4);
        } else {
            newYaw4 = yaw4;
        }
        newYaw4 *= -1.0f;
        newYaw4 *= (float)Math.PI / 180;
        float x2 = (float)(Math.sin(newYaw1) * width + (double)x);
        float z2 = (float)(Math.cos(newYaw1) * width + (double)z);
        float x3 = (float)(Math.sin(newYaw2) * width + (double)x);
        float z3 = (float)(Math.cos(newYaw2) * width + (double)z);
        float x4 = (float)(Math.sin(newYaw3) * width + (double)x);
        float z4 = (float)(Math.cos(newYaw3) * width + (double)z);
        float x5 = (float)(Math.sin(newYaw4) * width + (double)x);
        float z5 = (float)(Math.cos(newYaw4) * width + (double)z);
        float y2 = (float)((double)y + height);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }

    public static void drawOutline(float x, float y, float x2, float y2, float radius, float line, float offset, Color c1, Color c2) {
        double angleRadians;
        int i;
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        float edgeRadius = radius;
        float centerX = x + edgeRadius;
        float centerY = y + edgeRadius;
        int vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        int colorI = 0;
        centerX = x2;
        centerY = y2 + edgeRadius;
        vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i <= vertices; ++i) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            angleRadians = Math.PI * 2 * (double)i / (double)(vertices * 4);
            GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
            ++colorI;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x2 + edgeRadius;
        centerY = y2 + edgeRadius;
        i = 0;
        while ((float)i <= y2 - y) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            GL11.glVertex2d(centerX, centerY - (float)i);
            ++colorI;
            ++i;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x2;
        centerY = y + edgeRadius;
        for (i = 0; i <= vertices; ++i) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            angleRadians = Math.PI * 2 * (double)(i + 90) / (double)(vertices * 4);
            GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
            ++colorI;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x2;
        centerY = y;
        i = 0;
        while ((float)i <= x2 - x) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            GL11.glVertex2d(centerX - (float)i, centerY);
            ++colorI;
            ++i;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x;
        centerY = y + edgeRadius;
        for (i = 0; i <= vertices; ++i) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            angleRadians = Math.PI * 2 * (double)(i + 180) / (double)(vertices * 4);
            GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY + Math.cos(angleRadians) * (double)edgeRadius);
            ++colorI;
        }
        colorI = 0;
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x2;
        centerY = y2 + (float)vertices + offset;
        i = 0;
        while ((float)i <= x2 - x) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            GL11.glVertex2d(centerX - (float)i, centerY);
            ++colorI;
            ++i;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x;
        centerY = y2 + edgeRadius;
        for (i = 0; i <= vertices; ++i) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            angleRadians = Math.PI * 2 * (double)(i + 180) / (double)(vertices * 4);
            GL11.glVertex2d((double)centerX + Math.sin(angleRadians) * (double)edgeRadius, (double)centerY - Math.cos(angleRadians) * (double)edgeRadius);
            ++colorI;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x - edgeRadius;
        centerY = y2 + edgeRadius;
        i = 0;
        while ((float)i <= y2 - y) {
            RenderUtil.color(RenderUtil.fadeBetween(c1.getRGB(), c2.getRGB(), 20L * (long)colorI));
            GL11.glVertex2d(centerX, centerY - (float)i);
            ++colorI;
            ++i;
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static int fadeBetween(int startColour, int endColour, double progress) {
        if (progress > 1.0) {
            progress = 1.0 - progress % 1.0;
        }
        return RenderUtil.fadeTo(startColour, endColour, progress);
    }

    public static int fadeTo(int startColour, int endColour, double progress) {
        double invert = 1.0 - progress;
        int r = (int)((double)(startColour >> 16 & 0xFF) * invert + (double)(endColour >> 16 & 0xFF) * progress);
        int g = (int)((double)(startColour >> 8 & 0xFF) * invert + (double)(endColour >> 8 & 0xFF) * progress);
        int b = (int)((double)(startColour & 0xFF) * invert + (double)(endColour & 0xFF) * progress);
        int a = (int)((double)(startColour >> 24 & 0xFF) * invert + (double)(endColour >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int fadeBetween(int startColour, int endColour, long offset) {
        return RenderUtil.fadeBetween(startColour, endColour, (double)((System.currentTimeMillis() + offset) % 2000L) / 1000.0);
    }

    public static Color getHealthColor(float health, float maxHealth) {
        float[] fractions = new float[]{0.0f, 0.5f, 1.0f};
        Color[] colors = new Color[]{new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
        float progress = health / maxHealth;
        return Colors.blendColors(fractions, colors, progress).brighter();
    }

    public static void drawGradientRoundedRectH(int left, int top, int right, int bottom, int radius, int startColor, int endColor) {
        StencilUtil.write(false);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        RenderUtil.fastRoundedRect(left, top, right, bottom, radius);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        StencilUtil.erase(true);
        RenderUtil.drawGradientRectH(left, top, right, bottom, startColor, endColor);
        StencilUtil.dispose();
    }

    public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius) {
        double i;
        float z;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(9);
        double degree = Math.PI / 180;
        for (i = 0.0; i <= 90.0; i += 1.0) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
        }
        for (i = 90.0; i <= 180.0; i += 1.0) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
        }
        for (i = 180.0; i <= 270.0; i += 1.0) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
        }
        for (i = 270.0; i <= 360.0; i += 1.0) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
    }

    public static void drawGradientRectH(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(left, top, 0.0).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, 0.0).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(right, bottom, 0.0).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, top, 0.0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void enableRender3D(boolean disableDepth) {
        if (disableDepth) {
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
        }
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.0f);
    }

    public static void disableRender3D(boolean enableDepth) {
        if (enableDepth) {
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
        }
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawFilledCircleNoGL(int x, int y, double r, int c, int quality) {
        RenderUtil.resetColor();
        RenderUtil.setAlphaLimit(0.0f);
        GLUtil.setup2DRendering();
        RenderUtil.color(c);
        GL11.glBegin(6);
        for (int i = 0; i <= 360 / quality; ++i) {
            double x2 = Math.sin((double)(i * quality) * Math.PI / 180.0) * r;
            double y2 = Math.cos((double)(i * quality) * Math.PI / 180.0) * r;
            GL11.glVertex2d((double)x + x2, (double)y + y2);
        }
        GL11.glEnd();
        GLUtil.end2DRendering();
    }

    public static void drawRoundRect(double d, double e, double g, double h, int color) {
        RenderUtil.drawRect(d + 1.0, e, g - 1.0, h, color);
        RenderUtil.drawRect(d, e + 1.0, d + 1.0, h - 1.0, color);
        RenderUtil.drawRect(d + 1.0, e + 1.0, d + 0.5, e + 0.5, color);
        RenderUtil.drawRect(d + 1.0, e + 1.0, d + 0.5, e + 0.5, color);
        RenderUtil.drawRect(g - 1.0, e + 1.0, g - 0.5, e + 0.5, color);
        RenderUtil.drawRect(g - 1.0, e + 1.0, g, h - 1.0, color);
        RenderUtil.drawRect(d + 1.0, h - 1.0, d + 0.5, h - 0.5, color);
        RenderUtil.drawRect(g - 1.0, h - 1.0, g - 0.5, h - 0.5, color);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        RenderUtil.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderUtil.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        RenderUtil.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        RenderUtil.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
        RenderUtil.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        RenderUtil.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        RenderUtil.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        RenderUtil.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        RenderUtil.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        RenderUtil.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderUtil.disableGL2D();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
    }

    public static void drawBorderRect(double x, double y, double x1, double y1, int color, double lwidth) {
        RenderUtil.drawHLine(x, y, x1, y, (float)lwidth, color);
        RenderUtil.drawHLine(x1, y, x1, y1, (float)lwidth, color);
        RenderUtil.drawHLine(x, y1, x1, y1, (float)lwidth, color);
        RenderUtil.drawHLine(x, y1, x, y, (float)lwidth, color);
    }

    public static void drawHLine(double x, double y, double x1, double y1, float width, int color) {
        float var11 = (float)(color >> 24 & 0xFF) / 255.0f;
        float var12 = (float)(color >> 16 & 0xFF) / 255.0f;
        float var13 = (float)(color >> 8 & 0xFF) / 255.0f;
        float var14 = (float)(color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        GL11.glPushMatrix();
        GL11.glLineWidth(width);
        GL11.glBegin(3);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glLineWidth(1.0f);
        GL11.glPopMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawHLine(float x, float y, float x1, int y1) {
        if (y < x) {
            float var5 = x;
            x = y;
            y = var5;
        }
        RenderUtil.drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }

    public static void drawVLine(float x, float y, float x1, int y1) {
        if (x1 < y) {
            float var5 = y;
            y = x1;
            x1 = var5;
        }
        RenderUtil.drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
    }

    public static void disableSmoothLine() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(width);
    }

    public static void scissorStart(double x, double y, double width, double height) {
        GL11.glEnable(3089);
        ScaledResolution sr = new ScaledResolution(mc);
        double scale = sr.getScaleFactor();
        double finalHeight = height * scale;
        double finalY = (sr.getScaledHeight_double() - y) * scale;
        double finalX = x * scale;
        double finalWidth = width * scale;
        GL11.glScissor((int)finalX, (int)(finalY - finalHeight), (int)finalWidth, (int)finalHeight);
    }

    public static void scissorEnd() {
        GL11.glDisable(3089);
    }

    public static void TScylinder2(Entity player, double x, double y, double z, double range, float smoothLine, int s, int color) {
        Cylinder c = new Cylinder();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        GlStateManager.resetColor();
        RenderUtil.glColor(color);
        RenderUtil.enableSmoothLine(smoothLine);
        c.draw((float)(range + 0.25), (float)(range + 0.25), 0.0f, s, 0);
        c.draw((float)(range + 0.25), (float)(range + 0.25), 0.0f, s, 0);
        RenderUtil.disableSmoothLine();
        GL11.glPopMatrix();
    }

    public static void skeetRect(double x, double y, double x1, double y1, double size) {
        RenderUtil.rectangleBordered(x, y - 4.0, x1 + size, y1 + size, 0.5, new Color(60, 60, 60).getRGB(), new Color(10, 10, 10).getRGB());
        RenderUtil.rectangleBordered(x + 1.0, y - 3.0, x1 + size - 1.0, y1 + size - 1.0, 1.0, new Color(40, 40, 40).getRGB(), new Color(40, 40, 40).getRGB());
        RenderUtil.rectangleBordered(x + 2.5, y - 1.5, x1 + size - 2.5, y1 + size - 2.5, 0.5, new Color(40, 40, 40).getRGB(), new Color(60, 60, 60).getRGB());
        RenderUtil.rectangleBordered(x + 2.5, y - 1.5, x1 + size - 2.5, y1 + size - 2.5, 0.5, new Color(22, 22, 22).getRGB(), new Color(255, 255, 255, 0).getRGB());
    }

    public static void skeetRectSmall(double x, double y, double x1, double y1, double size) {
        RenderUtil.rectangleBordered(x + 4.35, y + 0.5, x1 + size - 84.5, y1 + size - 4.35, 0.5, new Color(48, 48, 48).getRGB(), new Color(10, 10, 10).getRGB());
        RenderUtil.rectangleBordered(x + 5.0, y + 1.0, x1 + size - 85.0, y1 + size - 5.0, 0.5, new Color(17, 17, 17).getRGB(), new Color(255, 255, 255, 0).getRGB());
    }

    public static double interpolate(double old, double now, double progress) {
        return old + (now - old) * progress;
    }

    public static AxisAlignedBB interpolate(Entity entity, AxisAlignedBB boundingBox, float partialTicks) {
        float invertedPT = 1.0f - partialTicks;
        return boundingBox.offset((entity.posX - entity.prevPosX) * (double)(-invertedPT), (entity.posY - entity.prevPosY) * (double)(-invertedPT), (entity.posZ - entity.prevPosZ) * (double)(-invertedPT));
    }

    public static Vec3 interpolate(Vec3 old, Vec3 now, double progress) {
        Vec3 difVec = now.subtract(old);
        return new Vec3(old.xCoord + difVec.xCoord * progress, old.yCoord + difVec.yCoord * progress, old.zCoord + difVec.zCoord * progress);
    }

    public static float interpolate(float old, float now, float partialTicks) {
        return old + (now - old) * partialTicks;
    }

    public static boolean isBBInFrustum(AxisAlignedBB aabb) {
        EntityPlayerSP player = RenderUtil.mc.thePlayer;
        FRUSTUM.setPosition(player.posX, player.posY, player.posZ);
        return FRUSTUM.isBoundingBoxInFrustum(aabb);
    }

    public static int getIntFromPercentage(float percentage) {
        return Color.HSBtoRGB(percentage / 3.0f, 1.0f, 1.0f);
    }

    public static void drawOutlinedString(RapeMasterFontManager fontRenderer, String string, float x, float y, float width, int color, int outlineColor) {
        GlStateManager.pushMatrix();
        fontRenderer.drawString(RenderUtil.stripColor(string), x - width, y, outlineColor);
        fontRenderer.drawString(RenderUtil.stripColor(string), x, y - width, outlineColor);
        fontRenderer.drawString(RenderUtil.stripColor(string), x + width, y, outlineColor);
        fontRenderer.drawString(RenderUtil.stripColor(string), x, y + width, outlineColor);
        fontRenderer.drawString(string, x, y, color);
        GlStateManager.popMatrix();
    }

    public static String stripColor(String input) {
        return COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return RenderUtil.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = mc.getRenderViewEntity();
        FRUSTUM.setPosition(current.posX, current.posY, current.posZ);
        return FRUSTUM.isBoundingBoxInFrustum(bb);
    }

    public static int getColorFromPercentage(float percentage) {
        return Color.HSBtoRGB(percentage / 3.0f, 1.0f, 1.0f);
    }

    public static ScaledResolution getScaledResolution() {
        int displayWidth = Display.getWidth();
        int displayHeight = Display.getHeight();
        float guiScale = RenderUtil.mc.gameSettings.guiScale;
        if (displayWidth != lastScaledWidth || displayHeight != lastScaledHeight || guiScale != lastGuiScale) {
            lastScaledWidth = displayWidth;
            lastScaledHeight = displayHeight;
            lastGuiScale = guiScale;
            scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            return scaledResolution;
        }
        return scaledResolution;
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        Gui.drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawRect(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawRect(x, y, x + width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawRect(x1 - width, y, x1, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawRectBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtil.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        RenderUtil.rectangle(x + width, y, x1 - width, y + width, borderColor);
        RenderUtil.rectangle(x, y, x + width, y1, borderColor);
        RenderUtil.rectangle(x1 - width, y, x1, y1, borderColor);
        RenderUtil.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        double var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }
        float var6 = (float)(color >> 24 & 0xFF) / 255.0f;
        float var7 = (float)(color >> 16 & 0xFF) / 255.0f;
        float var8 = (float)(color >> 8 & 0xFF) / 255.0f;
        float var9 = (float)(color & 0xFF) / 255.0f;
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var7, var8, var9, var6);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(left, bottom, 0.0).endVertex();
        worldRenderer.pos(right, bottom, 0.0).endVertex();
        worldRenderer.pos(right, top, 0.0).endVertex();
        worldRenderer.pos(left, top, 0.0).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawESPRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static float[] getNextWheelPosition(int wheel, float[] memorize, float topY, float bottomY, float objectY, float offset, boolean updateAction) {
        float reversingOffset;
        float target = memorize[0];
        float current = memorize[1];
        if (updateAction) {
            int i;
            if (wheel > 0) {
                for (i = 0; i < 15; ++i) {
                    float f = target;
                    target = f + 1.0f;
                    if (f > 0.0f) break;
                }
                if (target > 0.0f) {
                    target = 0.0f;
                }
            } else if (wheel < 0) {
                for (i = 0; i < 15 && !(topY + (objectY - current + (target - 1.0f)) + offset < bottomY); ++i) {
                    target -= 1.0f;
                }
            }
        }
        if (objectY - current + target < (reversingOffset = bottomY - topY - offset)) {
            float diff = reversingOffset - (objectY - current + target);
            target = target + diff <= 0.0f ? (target += diff) : (target += diff - (target + diff));
        }
        current = AnimationUtil.animateSmooth(current, target, 0.2f);
        return new float[]{target, current};
    }

    public static boolean isHovering(float x, float y, float width, float height, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseY >= y && (float)mouseX < x + width && (float)mouseY < y + height;
    }

    public static void drawAxisAlignedBB(AxisAlignedBB axisAlignedBB, boolean outline, int color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        RenderUtil.color(color);
        RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, outline, true);
        GlStateManager.resetColor();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawVGradientRect(double x, double y, double width, double height, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GLUtil.setup2DRendering(() -> {
            GL11.glShadeModel(7425);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(x + width, y, 0.0).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(x, y, 0.0).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(x, y + height, 0.0).color(f5, f6, f7, f4).endVertex();
            worldrenderer.pos(x + width, y + height, 0.0).color(f5, f6, f7, f4).endVertex();
            tessellator.draw();
            GlStateManager.resetColor();
            GL11.glShadeModel(7424);
        });
    }

    public static void drawHGradientRect(double x, double y, double width, double height, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GLUtil.setup2DRendering(() -> {
            GL11.glShadeModel(7425);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(x, y, 0.0).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(x, y + height, 0.0).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(x + width, y + height, 0.0).color(f5, f6, f7, f4).endVertex();
            worldrenderer.pos(x + width, y, 0.0).color(f5, f6, f7, f4).endVertex();
            tessellator.draw();
            GlStateManager.resetColor();
            GL11.glShadeModel(7424);
        });
    }

    public static void drawRoundedRect(float left, float top, float right, float bottom, int color) {
        RenderUtil.drawRect(left - 0.5f, top + 0.5f, left, bottom - 0.5f, color);
        RenderUtil.drawRect(right, top + 0.5f, right + 0.5f, bottom - 0.5f, color);
        RenderUtil.drawRect(left + 0.5f, top - 0.5f, right - 0.5f, top, color);
        RenderUtil.drawRect(left + 0.5f, bottom, right - 0.5f, bottom + 0.5f, color);
        RenderUtil.drawRect(left, top, right, bottom, color);
    }

    public static void renderRoundedRect(float x, float y, float width, float height, float radius, int color) {
        RenderUtil.drawGoodCircle(x + radius, y + radius, radius, color);
        RenderUtil.drawGoodCircle(x + width - radius, y + radius, radius, color);
        RenderUtil.drawGoodCircle(x + radius, y + height - radius, radius, color);
        RenderUtil.drawGoodCircle(x + width - radius, y + height - radius, radius, color);
        Gui.drawRect3(x + radius, y, width - radius * 2.0f, height, color);
        Gui.drawRect3(x, y + radius, width, height - radius * 2.0f, color);
    }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }

    public static int height() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
    }

    public static void glDrawGradientLine(double x, double y, double x1, double y1, float lineWidth, int colour) {
        boolean restore = GLRendering.glEnableBlend();
        GL11.glDisable(3553);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glShadeModel(7425);
        int noAlpha = ColorUtil.removeAlphaComponent(colour);
        GL11.glDisable(3008);
        GL11.glBegin(3);
        GLRendering.glColour(noAlpha);
        GL11.glVertex2d(x, y);
        double dif = x1 - x;
        GLRendering.glColour(colour);
        GL11.glVertex2d(x + dif * 0.4, y);
        GL11.glVertex2d(x + dif * 0.6, y);
        GLRendering.glColour(noAlpha);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glShadeModel(7424);
        GLRendering.glRestoreBlend(restore);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3553);
    }

    public static void drawGoodCircle(double x, double y, float radius, int color) {
        RenderUtil.color(color);
        GLUtil.setup2DRendering(() -> {
            GL11.glEnable(2832);
            GL11.glHint(3153, 4354);
            GL11.glPointSize(radius * (float)(2 * Minecraft.getMinecraft().gameSettings.guiScale));
            GLUtil.render(0, () -> GL11.glVertex2d(x, y));
        });
    }

    public static void scaleStart(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.scale(scale, scale, 1.0f);
        GlStateManager.translate(-x, -y, 0.0f);
    }

    public static void scaleEnd() {
        GlStateManager.popMatrix();
    }

    public static int darker(int color) {
        return RenderUtil.darker(color, 0.6f);
    }

    public static Color reAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static int darker(int color, float factor) {
        int r = (int)((float)(color >> 16 & 0xFF) * factor);
        int g = (int)((float)(color >> 8 & 0xFF) * factor);
        int b = (int)((float)(color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
    }

    public static void drawImage(ResourceLocation image, float x, float y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(image);
        float f = 1.0f / (float)width;
        float f2 = 1.0f / (float)height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + (float)height, 0.0).tex(0.0f * f, (float)height * f2).endVertex();
        worldrenderer.pos(x + (float)width, y + (float)height, 0.0).tex((float)width * f, (float)height * f2).endVertex();
        worldrenderer.pos(x + (float)width, y, 0.0).tex((float)width * f, 0.0f * f2).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(0.0f * f, 0.0f * f2).endVertex();
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(col1 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static double transition(double now, double desired, double speed) {
        double dif = Math.abs(now - desired);
        int fps = Minecraft.getDebugFPS();
        if (dif > 0.0) {
            double animationSpeed = MathUtil.roundToDecimalPlace(Math.min(10.0, Math.max(0.0625, 144.0 / (double)fps * (dif / 10.0) * speed)), 0.0625);
            if (dif < animationSpeed) {
                animationSpeed = dif;
            }
            if (now < desired) {
                return now + animationSpeed;
            }
            if (now > desired) {
                return now - animationSpeed;
            }
        }
        return now;
    }

    public static void color(int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float)((double)limit * 0.01));
    }

    public static void glColor(int color) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f(f1, f2, f3, f);
    }

    public static Color getColor(int color) {
        int f = color >> 24 & 0xFF;
        int f1 = color >> 16 & 0xFF;
        int f2 = color >> 8 & 0xFF;
        int f3 = color & 0xFF;
        return new Color(f1, f2, f3, f);
    }

    public static void resetColor() {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void quickDrawRect(float x, float y, float x2, float y2) {
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
    }

    public static void drawRectWH(double x, double y, double width, double height, int color) {
        RenderUtil.resetColor();
        RenderUtil.setAlphaLimit(0.0f);
        GLUtil.setup2DRendering(true);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(x, y, 0.0).color(color).endVertex();
        worldrenderer.pos(x, y + height, 0.0).color(color).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).color(color).endVertex();
        worldrenderer.pos(x + width, y, 0.0).color(color).endVertex();
        tessellator.draw();
        GLUtil.end2DRendering();
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        double minX = Math.min(left, right);
        double maxX = Math.max(left, right);
        double minY = Math.min(top, bottom);
        double maxY = Math.max(top, bottom);
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(minX, maxY, 0.0).endVertex();
        worldrenderer.pos(maxX, maxY, 0.0).endVertex();
        worldrenderer.pos(maxX, minY, 0.0).endVertex();
        worldrenderer.pos(minX, minY, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorderedRect2(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        Gui.drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        Gui.drawRect(x + width, y, x1 - width, y + width, borderColor);
        Gui.drawRect(x, y, x + width, y1, borderColor);
        Gui.drawRect(x1 - width, y, x1, y1, borderColor);
        Gui.drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
    }

    public static void drawBorderedRect(double x, double y, double x2, double d, float l1, int col1, int col2) {
        RenderUtil.drawRect(x, y, x2, d, col2);
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x2, d);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x2, d);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + width) * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawTexturedRect(float x, float y, float width, float height, ResourceLocation image, int color) {
        boolean disableAlpha;
        GL11.glPushMatrix();
        boolean enableBlend = GL11.glIsEnabled(3042);
        boolean bl = disableAlpha = !GL11.glIsEnabled(3008);
        if (!enableBlend) {
            GL11.glEnable(3042);
        }
        if (!disableAlpha) {
            GL11.glDisable(3008);
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        RenderUtil.glColor(color);
        RenderUtil.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        if (!enableBlend) {
            GL11.glDisable(3042);
        }
        if (!disableAlpha) {
            GL11.glEnable(3008);
        }
        GL11.glPopMatrix();
    }

    public static void drawArc(float n, float n2, double n3, int n4, int n5, double n6, int n7) {
        n3 *= 2.0;
        n *= 2.0f;
        n2 *= 2.0f;
        float n8 = (float)(n4 >> 24 & 0xFF) / 255.0f;
        float n9 = (float)(n4 >> 16 & 0xFF) / 255.0f;
        float n10 = (float)(n4 >> 8 & 0xFF) / 255.0f;
        float n11 = (float)(n4 & 0xFF) / 255.0f;
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth(n7);
        GL11.glEnable(2848);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glBegin(3);
        int n12 = n5;
        while ((double)n12 <= n6) {
            GL11.glVertex2d((double)n + Math.sin((double)n12 * Math.PI / 180.0) * n3, (double)n2 + Math.cos((double)n12 * Math.PI / 180.0) * n3);
            ++n12;
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawCircle2(double x, double y, float radius, int color) {
        if (radius == 0.0f) {
            return;
        }
        float correctRadius = radius * 2.0f;
        GLUtil.setup2DRendering(() -> {
            RenderUtil.glColor(color);
            GL11.glEnable(2832);
            GL11.glHint(3153, 4354);
            GL11.glPointSize(correctRadius);
            GLUtil.setupRendering(0, () -> GL11.glVertex2d(x, y));
            GL11.glDisable(2832);
            GlStateManager.resetColor();
        });
    }

    public static void drawCircle(Entity player, double x, double y, double z, double range, int s, float smoothLine, int color) {
        Cylinder c = new Cylinder();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        GlStateManager.resetColor();
        RenderUtil.glColor(color);
        RenderUtil.enableSmoothLine(smoothLine);
        c.draw((float)(range + 0.25), (float)(range + 0.25), 0.0f, s, 0);
        c.draw((float)(range + 0.25), (float)(range + 0.25), 0.0f, s, 0);
        RenderUtil.disableSmoothLine();
        GL11.glPopMatrix();
    }

    public static void drawCircle(double x, double y, double radius, int c) {
        float alpha = (float)(c >> 24 & 0xFF) / 255.0f;
        float red = (float)(c >> 16 & 0xFF) / 255.0f;
        float green = (float)(c >> 8 & 0xFF) / 255.0f;
        float blue = (float)(c & 0xFF) / 255.0f;
        boolean blend = GL11.glIsEnabled(3042);
        boolean line = GL11.glIsEnabled(2848);
        boolean texture = GL11.glIsEnabled(3553);
        if (!blend) {
            GL11.glEnable(3042);
        }
        if (!line) {
            GL11.glEnable(2848);
        }
        if (texture) {
            GL11.glDisable(3553);
        }
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(x + Math.sin((double)i * 3.141526 / 180.0) * radius, y + Math.cos((double)i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
        if (texture) {
            GL11.glEnable(3553);
        }
        if (!line) {
            GL11.glDisable(2848);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
    }

    public static void drawFullCircle(int x, int y, double r, int segments, float lineWidth, int part, int color) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        r *= 2.0;
        x *= 2;
        y *= 2;
        GL11.glEnable(3042);
        GL11.glLineWidth(lineWidth);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        RenderUtil.glColor(color);
        GL11.glBegin(3);
        for (int i = segments - part; i <= segments; ++i) {
            double cx = Math.sin((double)i * Math.PI / 180.0) * r;
            double cy = Math.cos((double)i * Math.PI / 180.0) * r;
            GL11.glVertex2d((double)x + cx, (double)y + cy);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public static void drawPlayerHead(ResourceLocation skin, int x, int y, int width, int height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
    }

    public static void drawTriangle(double x, double y, double x1, double y1, double x2, double y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        RenderUtil.glColor(color);
        GL11.glBegin(4);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
    }

    public static void drawGradientRect(float x, float y, float x2, float y2, int colorBegin, int colorEnd) {
        float x3 = x;
        float y3 = y;
        if (x > x2) {
            x = x2;
            x2 = x3;
        }
        if (y > y2) {
            y = y2;
            y2 = y3;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(2884);
        GL11.glShadeModel(7425);
        GL11.glBegin(9);
        RenderUtil.glColor(colorBegin);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        RenderUtil.glColor(colorEnd);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawGradientRect(float x, float y, float x2, float y2, int colorLeftUp, int colorLeftDown, int colorRightUp, int colorRightDown) {
        float x3 = x;
        float y3 = y;
        if (x > x2) {
            x = x2;
            x2 = x3;
        }
        if (y > y2) {
            y = y2;
            y2 = y3;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(2884);
        GL11.glShadeModel(7425);
        GL11.glBegin(9);
        RenderUtil.glColor(colorLeftUp);
        GL11.glVertex2d(x, y);
        RenderUtil.glColor(colorLeftDown);
        GL11.glVertex2d(x, y2);
        RenderUtil.glColor(colorRightDown);
        GL11.glVertex2d(x2, y2);
        RenderUtil.glColor(colorRightUp);
        GL11.glVertex2d(x2, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawShadow(float x, float y, float x2, float y2) {
        RenderUtil.drawTexturedRect(x - 9.0f, y - 9.0f, 9.0f, 9.0f, new ResourceLocation("shaders/paneltopleft.png"), Color.white.getRGB());
        RenderUtil.drawTexturedRect(x - 9.0f, y2, 9.0f, 9.0f, new ResourceLocation("shaders/panelbottomleft.png"), Color.white.getRGB());
        RenderUtil.drawTexturedRect(x2, y2, 9.0f, 9.0f, new ResourceLocation("shaders/panelbottomright.png"), Color.white.getRGB());
        RenderUtil.drawTexturedRect(x2, y - 9.0f, 9.0f, 9.0f, new ResourceLocation("shaders/paneltopright.png"), Color.white.getRGB());
        RenderUtil.drawTexturedRect(x - 9.0f, y, 9.0f, y2 - y, new ResourceLocation("shaders/panelleft.png"), Color.white.getRGB());
        RenderUtil.drawTexturedRect(x2, y, 9.0f, y2 - y, new ResourceLocation("shaders/panelright.png"), Color.white.getRGB());
        RenderUtil.drawTexturedRect(x, y - 9.0f, x2 - x, 9.0f, new ResourceLocation("shaders/paneltop.png"), Color.white.getRGB());
        RenderUtil.drawTexturedRect(x, y2, x2 - x, 9.0f, new ResourceLocation("shaders/panelbottom.png"), Color.white.getRGB());
    }

    public static void renderLine(double x1, double y1, double x2, double y2, float width, int color) {
        GLUtil.setup2DRendering(() -> {
            RenderUtil.glColor(color);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(width);
            GLUtil.setupRendering(1, () -> {
                for (int i = 0; i < 2; ++i) {
                    GL11.glVertex2d(x1, y1);
                    GL11.glVertex2d(x2, y2);
                }
            });
            GL11.glDisable(2848);
            GlStateManager.resetColor();
        });
    }

    public static void drawImage(ResourceLocation imageLocation, double x, double y, double width, double height, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableAlpha();
        mc.getTextureManager().bindTexture(imageLocation);
        RenderUtil.glColor(color);
        Gui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0f, 0.0f, (float)width, (float)height, (float)width, (float)height);
        GlStateManager.resetColor();
        GlStateManager.bindTexture(0);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawImageRound(ResourceLocation imageLocation, double x, double y, double width, double height, int color, Runnable cutMethod) {
        GlStateManager.pushMatrix();
        StencilUtil.initStencilToWrite();
        cutMethod.run();
        StencilUtil.readStencilBuffer(1);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableAlpha();
        mc.getTextureManager().bindTexture(imageLocation);
        RenderUtil.glColor(color);
        Gui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0f, 0.0f, (float)width, (float)height, (float)width, (float)height);
        GlStateManager.resetColor();
        GlStateManager.bindTexture(0);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        StencilUtil.uninitStencilBuffer();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        RenderUtil.drawImage(image, x, y, width, height, Color.white.getRGB());
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, int color) {
        RenderUtil.drawTexturedRect(x, y, width, height, image, color);
    }

    public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline) {
        RenderManager renderManager = mc.getRenderManager();
        Timer timer = RenderUtil.mc.timer;
        double x = (double)blockPos.getX() - renderManager.renderPosX;
        double y = (double)blockPos.getY() - renderManager.renderPosY;
        double z = (double)blockPos.getZ() - renderManager.renderPosZ;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Block block = RenderUtil.mc.theWorld.getBlockState(blockPos).getBlock();
        if (block != null) {
            EntityPlayerSP player = RenderUtil.mc.thePlayer;
            double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)timer.renderPartialTicks;
            double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)timer.renderPartialTicks;
            double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)timer.renderPartialTicks;
            axisAlignedBB = block.getSelectedBoundingBox(RenderUtil.mc.theWorld, blockPos).expand(0.002f, 0.002f, 0.002f).offset(-posX, -posY, -posZ);
        }
        GL11.glBlendFunc(770, 771);
        RenderUtil.enableGlCap(3042);
        RenderUtil.disableGlCap(3553, 2929);
        GL11.glDepthMask(false);
        RenderUtil.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
        RenderUtil.drawFilledBox(axisAlignedBB);
        if (outline) {
            GL11.glLineWidth(1.0f);
            RenderUtil.enableGlCap(2848);
            RenderUtil.glColor(color.getRGB());
            RenderGlobal.drawSelectionBoundingBox(axisAlignedBB);
        }
        GlStateManager.resetColor();
        GL11.glDepthMask(true);
        RenderUtil.resetCaps();
    }

    public static void glColor(int red, int green, int blue, int alpha) {
        GlStateManager.color((float)red / 255.0f, (float)green / 255.0f, (float)blue / 255.0f, (float)alpha / 255.0f);
    }

    public static void resetCaps() {
        glCapMap.forEach(RenderUtil::setGlState);
    }

    public static void enableGlCap(int cap) {
        RenderUtil.setGlCap(cap, true);
    }

    public static void enableGlCap(int ... caps) {
        for (int cap : caps) {
            RenderUtil.setGlCap(cap, true);
        }
    }

    public static void disableGlCap(int cap) {
        RenderUtil.setGlCap(cap, true);
    }

    public static void disableGlCap(int ... caps) {
        for (int cap : caps) {
            RenderUtil.setGlCap(cap, false);
        }
    }

    public static void setGlCap(int cap, boolean state) {
        glCapMap.put(cap, GL11.glGetBoolean(cap));
        RenderUtil.setGlState(cap, state);
    }

    public static void setGlState(int cap, boolean state) {
        if (state) {
            GL11.glEnable(cap);
        } else {
            GL11.glDisable(cap);
        }
    }

    public static void drawFilledBox(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawStack(ItemStack stack, float x, float y) {
        GL11.glPushMatrix();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null) {
            RenderHelper.enableGUIStandardItemLighting();
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.enableBlend();
        mc.getRenderItem().zLevel = -150.0f;
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, (int)x, (int)y);
        mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.enableBlend();
        float z = 0.5f;
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glPopMatrix();
    }

    public static void enableDepth() {
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
    }

    public static void disableDepth() {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
    }

    public static void enableCaps(int ... caps) {
        for (int cap : caps) {
            GL11.glEnable(cap);
        }
        enabledCaps = caps;
    }

    public static void disableCaps() {
        for (int cap : enabledCaps) {
            GL11.glDisable(cap);
        }
    }

    public static void startBlend() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
    }

    public static void endBlend() {
        GlStateManager.disableBlend();
    }

    public static void setUpRenderer2D() {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(2884);
        GL11.glShadeModel(7425);
    }

    public static void endUpRenderer2D() {
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void startRotate(float x, float y, float rotate) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.rotate(rotate, 0.0f, 0.0f, -1.0f);
        GlStateManager.translate(-x, -y, 0.0f);
    }

    public static void endRotate() {
        GlStateManager.popMatrix();
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture(3553, texture);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        return RenderUtil.createFrameBuffer(framebuffer, false);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean depth) {
        if (RenderUtil.needsNewFramebuffer(framebuffer)) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(RenderUtil.mc.displayWidth, RenderUtil.mc.displayHeight, depth);
        }
        return framebuffer;
    }

    public static boolean needsNewFramebuffer(Framebuffer framebuffer) {
        return framebuffer == null || framebuffer.framebufferWidth != RenderUtil.mc.displayWidth || framebuffer.framebufferHeight != RenderUtil.mc.displayHeight;
    }

    public static void startGlScissor(int x, int y, int width, int height) {
        int scaleFactor = new ScaledResolution(mc).getScaleFactor();
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        GL11.glScissor(x * scaleFactor, RenderUtil.mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, (height += 14) * scaleFactor);
    }

    public static void stopGlScissor() {
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawSolidBlockESP(double x, double y, double z, int color) {
        double xPos = x - RenderUtil.mc.getRenderManager().renderPosX;
        double yPos = y - RenderUtil.mc.getRenderManager().renderPosY;
        double zPos = z - RenderUtil.mc.getRenderManager().renderPosZ;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        float f4 = (float)(color >> 24 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(f, f2, f3, f4);
        RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(xPos, yPos, zPos, xPos + 1.0, yPos + 1.0, zPos + 1.0));
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawSolidBlockESP(BlockPos pos, int color) {
        double xPos = (double)pos.getX() - RenderUtil.mc.getRenderManager().renderPosX;
        double yPos = (double)pos.getY() - RenderUtil.mc.getRenderManager().renderPosY;
        double zPos = (double)pos.getZ() - RenderUtil.mc.getRenderManager().renderPosZ;
        double height = RenderUtil.mc.theWorld.getBlockState(pos).getBlock().getBlockBoundsMaxY() - RenderUtil.mc.theWorld.getBlockState(pos).getBlock().getBlockBoundsMinY();
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        float f4 = (float)(color >> 24 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(f, f2, f3, f4);
        RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(xPos, yPos, zPos, xPos + 1.0, yPos + height, zPos + 1.0));
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawLine(BlockPos blockPos, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        double renderPosXDelta = (double)blockPos.getX() - mc.getRenderManager().renderPosX + 0.5;
        double renderPosYDelta = (double)blockPos.getY() - mc.getRenderManager().renderPosY + 0.5;
        double renderPosZDelta = (double)blockPos.getZ() - mc.getRenderManager().renderPosZ + 0.5;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        float blockPos9 = (float)(mc.thePlayer.posX - (double)blockPos.getX());
        float blockPos7 = (float)(mc.thePlayer.posY - (double)blockPos.getY());
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        float f4 = (float)(color >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(f, f2, f3, f4);
        GL11.glLoadIdentity();
        boolean previousState = mc.gameSettings.viewBobbing;
        mc.gameSettings.viewBobbing = false;
        mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0, mc.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta);
        GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta);
        GL11.glEnd();
        mc.gameSettings.viewBobbing = previousState;
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    static {
        FRUSTUM = new Frustum();
        tessellator = Tessellator.getInstance();
        worldrenderer = tessellator.getWorldRenderer();
        COLOR_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
        mc = Minecraft.getMinecraft();
        DISPLAY_LISTS_2D = new int[4];
        for (int i = 0; i < DISPLAY_LISTS_2D.length; ++i) {
            RenderUtil.DISPLAY_LISTS_2D[i] = GL11.glGenLists(1);
        }
        GL11.glNewList(DISPLAY_LISTS_2D[0], 4864);
        RenderUtil.quickDrawRect(-7.0f, 2.0f, -4.0f, 3.0f);
        RenderUtil.quickDrawRect(4.0f, 2.0f, 7.0f, 3.0f);
        RenderUtil.quickDrawRect(-7.0f, 0.5f, -6.0f, 3.0f);
        RenderUtil.quickDrawRect(6.0f, 0.5f, 7.0f, 3.0f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[1], 4864);
        RenderUtil.quickDrawRect(-7.0f, 3.0f, -4.0f, 3.3f);
        RenderUtil.quickDrawRect(4.0f, 3.0f, 7.0f, 3.3f);
        RenderUtil.quickDrawRect(-7.3f, 0.5f, -7.0f, 3.3f);
        RenderUtil.quickDrawRect(7.0f, 0.5f, 7.3f, 3.3f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[2], 4864);
        RenderUtil.quickDrawRect(4.0f, -20.0f, 7.0f, -19.0f);
        RenderUtil.quickDrawRect(-7.0f, -20.0f, -4.0f, -19.0f);
        RenderUtil.quickDrawRect(6.0f, -20.0f, 7.0f, -17.5f);
        RenderUtil.quickDrawRect(-7.0f, -20.0f, -6.0f, -17.5f);
        GL11.glEndList();
        GL11.glNewList(DISPLAY_LISTS_2D[3], 4864);
        RenderUtil.quickDrawRect(7.0f, -20.0f, 7.3f, -17.5f);
        RenderUtil.quickDrawRect(-7.3f, -20.0f, -7.0f, -17.5f);
        RenderUtil.quickDrawRect(4.0f, -20.3f, 7.3f, -20.0f);
        RenderUtil.quickDrawRect(-7.3f, -20.3f, -4.0f, -20.0f);
        GL11.glEndList();
        enabledCaps = new int[32];
    }
}

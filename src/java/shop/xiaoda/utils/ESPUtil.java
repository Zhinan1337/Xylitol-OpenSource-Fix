//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import net.minecraft.client.renderer.culling.*;
import java.nio.*;
import net.minecraft.entity.*;
import shop.xiaoda.*;
import org.lwjgl.opengl.*;
import org.lwjgl.compatibility.util.glu.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.util.*;
import org.lwjgl.compatibility.util.vector.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;
import org.lwjgl.*;
import net.minecraft.client.renderer.*;

public class ESPUtil
{
    private static final Frustum frustum;
    private static final FloatBuffer windPos;
    private static final IntBuffer intBuffer;
    private static final FloatBuffer floatBuffer1;
    private static final FloatBuffer floatBuffer2;
    
    public static boolean isInView(final Entity ent) {
        ESPUtil.frustum.setPosition(Client.mc.getRenderViewEntity().posX, Client.mc.getRenderViewEntity().posY, Client.mc.getRenderViewEntity().posZ);
        return ESPUtil.frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
    }
    
    public static Vector3f projectOn2D(final float x, final float y, final float z, final int scaleFactor) {
        GL11.glGetFloatv(2982, ESPUtil.floatBuffer1);
        GL11.glGetFloatv(2983, ESPUtil.floatBuffer2);
        GL11.glGetIntegerv(2978, ESPUtil.intBuffer);
        if (GLU.gluProject(x, y, z, ESPUtil.floatBuffer1, ESPUtil.floatBuffer2, ESPUtil.intBuffer, ESPUtil.windPos)) {
            return new Vector3f(ESPUtil.windPos.get(0) / scaleFactor, (Client.mc.displayHeight - ESPUtil.windPos.get(1)) / scaleFactor, ESPUtil.windPos.get(2));
        }
        return null;
    }
    
    public static double[] getInterpolatedPos(final Entity entity) {
        final float ticks = Client.mc.timer.renderPartialTicks;
        return new double[] { MathUtil.interpolate(entity.lastTickPosX, entity.posX, (double)ticks) - Client.mc.getRenderManager().viewerPosX, MathUtil.interpolate(entity.lastTickPosY, entity.posY, (double)ticks) - Client.mc.getRenderManager().viewerPosY, MathUtil.interpolate(entity.lastTickPosZ, entity.posZ, (double)ticks) - Client.mc.getRenderManager().viewerPosZ };
    }
    
    public static AxisAlignedBB getInterpolatedBoundingBox(final Entity entity) {
        final double[] renderingEntityPos = getInterpolatedPos(entity);
        final double entityRenderWidth = entity.width / 1.5;
        return new AxisAlignedBB(renderingEntityPos[0] - entityRenderWidth, renderingEntityPos[1], renderingEntityPos[2] - entityRenderWidth, renderingEntityPos[0] + entityRenderWidth, renderingEntityPos[1] + entity.height + (entity.isSneaking() ? -0.3 : 0.18), renderingEntityPos[2] + entityRenderWidth).expand(0.15, 0.15, 0.15);
    }
    
    public static Vector4f getEntityPositionsOn2D(final Entity entity) {
        final AxisAlignedBB bb = getInterpolatedBoundingBox(entity);
        final float yOffset = 0.0f;
        final List<Vector3f> vectors = Arrays.asList(new Vector3f((float)bb.minX, (float)bb.minY, (float)bb.minZ), new Vector3f((float)bb.minX, (float)bb.maxY - yOffset, (float)bb.minZ), new Vector3f((float)bb.maxX, (float)bb.minY, (float)bb.minZ), new Vector3f((float)bb.maxX, (float)bb.maxY - yOffset, (float)bb.minZ), new Vector3f((float)bb.minX, (float)bb.minY, (float)bb.maxZ), new Vector3f((float)bb.minX, (float)bb.maxY - yOffset, (float)bb.maxZ), new Vector3f((float)bb.maxX, (float)bb.minY, (float)bb.maxZ), new Vector3f((float)bb.maxX, (float)bb.maxY - yOffset, (float)bb.maxZ));
        final Vector4f entityPos = new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, -1.0f, -1.0f);
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        for (Vector3f vector3f : vectors) {
            vector3f = projectOn2D(vector3f.x, vector3f.y, vector3f.z, sr.getScaleFactor());
            if (vector3f != null && vector3f.z >= 0.0 && vector3f.z < 1.0) {
                entityPos.x = Math.min(vector3f.x, entityPos.x);
                entityPos.y = Math.min(vector3f.y, entityPos.y);
                entityPos.z = Math.max(vector3f.x, entityPos.z);
                entityPos.w = Math.max(vector3f.y, entityPos.w);
            }
        }
        return entityPos;
    }
    
    static {
        frustum = new Frustum();
        windPos = BufferUtils.createFloatBuffer(4);
        intBuffer = GLAllocation.createDirectIntBuffer(16);
        floatBuffer1 = GLAllocation.createDirectFloatBuffer(16);
        floatBuffer2 = GLAllocation.createDirectFloatBuffer(16);
    }
}

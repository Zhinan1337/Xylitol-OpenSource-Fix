//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import org.lwjgl.compatibility.display.Display;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import java.nio.*;
import shop.xiaoda.module.*;
import java.text.*;
import net.minecraft.entity.player.*;
import java.util.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import net.minecraft.entity.*;
import shop.xiaoda.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.render.fontRender.*;
import shop.xiaoda.utils.render.*;
import java.awt.*;
import java.util.List;

import shop.xiaoda.module.modules.combat.*;
import shop.xiaoda.module.modules.misc.*;
import net.minecraft.client.entity.*;
import org.lwjgl.*;
import org.lwjgl.compatibility.util.glu.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import shop.xiaoda.module.modules.player.*;
import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class ESP extends Module
{
    private final BoolValue armorValue;
    private final BoolValue healthValue;
    private final BoolValue boxValue;
    private final BoolValue nameValue;
    private final NumberValue width2d;
    private final DecimalFormat decimalFormat;
    private static final FloatBuffer modelView;
    private static final FloatBuffer projection;
    private static final IntBuffer viewport;
    private final List<Vec3> positions;
    
    public ESP() {
        super("ESP", Category.Render);
        this.armorValue = new BoolValue("Armor", true);
        this.healthValue = new BoolValue("Health", true);
        this.boxValue = new BoolValue("Box", true);
        this.nameValue = new BoolValue("Name", true);
        this.width2d = new NumberValue("BoxWidth", 0.5, 0.1, 1.0, 0.1);
        this.decimalFormat = new DecimalFormat("0.0#", new DecimalFormatSymbols(Locale.ENGLISH));
        this.positions = new ArrayList<Vec3>();
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        for (final Entity entity : ESP.mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer || this.isValid(entity)) {
                updateView();
            }
        }
    }
    
    @EventTarget
    public void onRenderNameTag(final EventRenderNameTag e) {
        if (this.nameValue.getValue() && e.getTarget() instanceof EntityPlayer) {
            e.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onRender2DEvent(final EventRender2D e) {
        final ScaledResolution sr = RenderUtil.getScaledResolution();
        GlStateManager.pushMatrix();
        final double twoScale = sr.getScaleFactor() / Math.pow(sr.getScaleFactor(), 2.0);
        GlStateManager.scale(twoScale, twoScale, twoScale);
        for (final EntityPlayer entity : getLoadedPlayers()) {
            if (this.isValid((Entity)entity) && RenderUtil.isInViewFrustrum((Entity)entity)) {
                this.updatePositions((Entity)entity);
                int maxLeft = Integer.MAX_VALUE;
                int maxRight = Integer.MIN_VALUE;
                int maxBottom = Integer.MIN_VALUE;
                int maxTop = Integer.MAX_VALUE;
                final Iterator<Vec3> iterator2 = this.positions.iterator();
                boolean canEntityBeSeen = false;
                while (iterator2.hasNext()) {
                    final Vec3 screenPosition = WorldToScreen(iterator2.next());
                    if (screenPosition != null && screenPosition.zCoord >= 0.0 && screenPosition.zCoord < 1.0) {
                        maxLeft = (int)Math.min(screenPosition.xCoord, maxLeft);
                        maxRight = (int)Math.max(screenPosition.xCoord, maxRight);
                        maxBottom = (int)Math.max(screenPosition.yCoord, maxBottom);
                        maxTop = (int)Math.min(screenPosition.yCoord, maxTop);
                        canEntityBeSeen = true;
                    }
                }
                if (!canEntityBeSeen) {
                    continue;
                }
                if (this.healthValue.getValue()) {
                    this.drawHealth((EntityLivingBase)entity, (float)maxLeft, (float)maxTop, (float)maxBottom);
                }
                if (this.armorValue.getValue()) {
                    this.drawArmor((EntityLivingBase)entity, (float)maxTop, (float)maxRight, (float)maxBottom);
                }
                if (this.boxValue.getValue()) {
                    this.drawBox(maxLeft, maxTop, maxRight, maxBottom);
                }
                if ((Client.instance.moduleManager.getModule((Class)NameTags.class)).state || !this.nameValue.getValue()) {
                    continue;
                }
                this.drawName((Entity)entity, maxLeft, maxTop, maxRight);
            }
        }
        GlStateManager.popMatrix();
    }
    
    private void drawName(final Entity e, final int left, final int top, final int right) {
        final EntityPlayer ent = (EntityPlayer)e;
        final String renderName = ent.getName();
        final RapeMasterFontManager font = FontManager.arial18;
        final float meme2 = (float)((right - left) / 2.0 - font.getStringWidth(renderName));
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        FontManager.arial18.drawStringWithShadow(renderName, (left + meme2) / 2.0f, (top - font.getStringWidth(renderName) / 1.5f * 2.0f) / 2.0f - 4.0f, this.getColor((EntityLivingBase)ent));
        GlStateManager.popMatrix();
    }
    
    public static List<EntityPlayer> getLoadedPlayers() {
        return (List<EntityPlayer>)ESP.mc.theWorld.playerEntities;
    }
    
    private void drawBox(final int left, final int top, final int right, final int bottom) {
        RenderUtil.drawRectBordered(left + 0.5, top + 0.5, right - 0.5, bottom - 0.5, 1.0, Colors.getColor(0, 0, 0, 0), Colors.WHITE);
        RenderUtil.drawRectBordered(left - 0.5, top - 0.5, right + 0.5, bottom + 0.5, 1.0, Colors.getColor(0, 0), Colors.getColor(0));
        RenderUtil.drawRectBordered(left + 1.5, top + 1.5, right - 1.5, bottom - 1.5, 1.0, Colors.getColor(0, 0), Colors.getColor(0));
    }
    
    private void drawArmor(final EntityLivingBase entityLivingBase, final float top, final float right, final float bottom) {
        final float height = bottom + 1.0f - top;
        final float currentArmor = (float)entityLivingBase.getTotalArmorValue();
        final float armorPercent = currentArmor / 20.0f;
        final float MOVE = 2.0f;
        final int line = 1;
        RenderUtil.drawESPRect(right + 2.0f + 1.0f + 2.0f, top - 2.0f, right + 1.0f - 1.0f + 2.0f, bottom + 1.0f, new Color(25, 25, 25, 150).getRGB());
        RenderUtil.drawESPRect(right + 3.0f + 2.0f, top + height * (1.0f - armorPercent) - 1.0f, right + 1.0f + 2.0f, bottom, new Color(78, 206, 229).getRGB());
        RenderUtil.drawESPRect(right + 3.0f + 2.0f + 1.0f, bottom + 1.0f, right + 3.0f + 2.0f, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderUtil.drawESPRect(right + 1.0f + 2.0f, bottom + 1.0f, right + 1.0f + 2.0f - 1.0f, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderUtil.drawESPRect(right + 1.0f + 2.0f, top - 1.0f, right + 3.0f + 2.0f, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderUtil.drawESPRect(right + 1.0f + 2.0f, bottom + 1.0f, right + 3.0f + 2.0f, bottom, new Color(0, 0, 0, 255).getRGB());
    }
    
    private void drawHealth(final EntityLivingBase entityLivingBase, final float left, final float top, final float bottom) {
        final float height = bottom + 1.0f - top;
        final float currentHealth = entityLivingBase.getHealth();
        final float maxHealth = entityLivingBase.getMaxHealth();
        final float healthPercent = currentHealth / maxHealth;
        final float MOVE = 2.0f;
        final int line = 1;
        final String healthStr = "\u00a7f" + this.decimalFormat.format(currentHealth) + "\u00a7c\u2764";
        final float bottom2 = top + height * (1.0f - healthPercent) - 1.0f;
        final float health = entityLivingBase.getHealth();
        final float[] fractions = { 0.0f, 0.5f, 1.0f };
        final Color[] colors = { Color.RED, Color.YELLOW, Color.GREEN };
        final float progress = health / entityLivingBase.getMaxHealth();
        final Color customColor = (health >= 0.0f) ? Colors.blendColors(fractions, colors, progress).brighter() : Color.RED;
        ESP.mc.fontRendererObj.drawStringWithShadow(healthStr, left - 3.0f - 2.0f - ESP.mc.fontRendererObj.getStringWidth(healthStr), bottom2, -1);
        RenderUtil.drawESPRect(left - 3.0f - 2.0f, bottom, left - 1.0f - 2.0f, top - 1.0f, new Color(25, 25, 25, 150).getRGB());
        RenderUtil.drawESPRect(left - 3.0f - 2.0f, bottom, left - 1.0f - 2.0f, bottom2, customColor.getRGB());
        RenderUtil.drawESPRect(left - 3.0f - 2.0f, bottom + 1.0f, left - 3.0f - 2.0f - 1.0f, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderUtil.drawESPRect(left - 1.0f - 2.0f + 1.0f, bottom + 1.0f, left - 1.0f - 2.0f, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderUtil.drawESPRect(left - 3.0f - 2.0f, top - 1.0f, left - 1.0f - 2.0f, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderUtil.drawESPRect(left - 3.0f - 2.0f, bottom + 1.0f, left - 1.0f - 2.0f, bottom, new Color(0, 0, 0, 255).getRGB());
    }
    
    private int getColor(final EntityLivingBase ent) {
        if (AntiBot.isServerBot((Entity)ent)) {
            return new Color(255, 0, 0).getRGB();
        }
        if (Teams.isSameTeam((Entity)ent) || ent instanceof EntityPlayerSP) {
            return new Color(0, 255, 0).getRGB();
        }
        return new Color(255, 0, 0).getRGB();
    }
    
    private static Vec3 WorldToScreen(final Vec3 position) {
        final FloatBuffer screenPositions = BufferUtils.createFloatBuffer(3);
        final boolean result = GLU.gluProject((float)position.xCoord, (float)position.yCoord, (float)position.zCoord, ESP.modelView, ESP.projection, ESP.viewport, screenPositions);
        if (result) {
            return new Vec3((double)screenPositions.get(0), (double)(Display.getHeight() - screenPositions.get(1)), (double)screenPositions.get(2));
        }
        return null;
    }
    
    public void updatePositions(final Entity entity) {
        this.positions.clear();
        final Vec3 position = getEntityRenderPosition(entity);
        final double x = position.xCoord - entity.posX;
        final double y = position.yCoord - entity.posY;
        final double z = position.zCoord - entity.posZ;
        final double height = (entity instanceof EntityItem) ? 0.5 : (entity.height + 0.1);
        final double width = (entity instanceof EntityItem) ? 0.25 : this.width2d.getValue();
        final AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - width + x, entity.posY + y, entity.posZ - width + z, entity.posX + width + x, entity.posY + height + y, entity.posZ + width + z);
        this.positions.add(new Vec3(aabb.minX, aabb.minY, aabb.minZ));
        this.positions.add(new Vec3(aabb.minX, aabb.minY, aabb.maxZ));
        this.positions.add(new Vec3(aabb.minX, aabb.maxY, aabb.minZ));
        this.positions.add(new Vec3(aabb.minX, aabb.maxY, aabb.maxZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.minY, aabb.minZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.minY, aabb.maxZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.maxY, aabb.minZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ));
    }
    
    private static Vec3 getEntityRenderPosition(final Entity entity) {
        return new Vec3(getEntityRenderX(entity), getEntityRenderY(entity), getEntityRenderZ(entity));
    }
    
    private static double getEntityRenderX(final Entity entity) {
        return entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosX;
    }
    
    private static double getEntityRenderY(final Entity entity) {
        return entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosY;
    }
    
    private static double getEntityRenderZ(final Entity entity) {
        return entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosZ;
    }
    
    private boolean isValid(final Entity entity) {
        final Blink blink = (Blink)Client.instance.moduleManager.getModule((Class)Blink.class);
        return (entity != ESP.mc.thePlayer || ESP.mc.gameSettings.thirdPersonView != 0) && !entity.isInvisible() && !(entity instanceof EntityArmorStand) && (blink.getState() || entity instanceof EntityPlayer);
    }
    
    private static void updateView() {
        GL11.glGetFloatv(2982, ESP.modelView);
        GL11.glGetFloatv(2983, ESP.projection);
        GL11.glGetIntegerv(2978, ESP.viewport);
    }
    
    static {
        modelView = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
        viewport = GLAllocation.createDirectIntBuffer(16);
    }
}

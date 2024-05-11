//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import org.lwjgl.compatibility.util.vector.*;
import java.text.*;
import shop.xiaoda.module.*;
import java.util.*;
import shop.xiaoda.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.misc.*;
import shop.xiaoda.*;
import shop.xiaoda.utils.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import shop.xiaoda.utils.render.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.utils.render.fontRender.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.module.modules.combat.*;

public class NameTags extends Module
{
    private final Map<Entity, Vector4f> entityPosition;
    private final DecimalFormat DF_1;
    
    public NameTags() {
        super("NameTags", Category.Render);
        this.entityPosition = new HashMap<Entity, Vector4f>();
        this.DF_1 = new DecimalFormat("0.0");
    }
    
    @EventTarget
    public void onRender3DEvent(final EventRender3D event) {
        this.entityPosition.clear();
        for (final Entity entity : NameTags.mc.theWorld.loadedEntityList) {
            if (this.shouldRender(entity) && ESPUtil.isInView(entity)) {
                this.entityPosition.put(entity, ESPUtil.getEntityPositionsOn2D(entity));
            }
        }
    }
    
    @EventTarget
    public void onRenderNameTag(final EventRenderNameTag event) {
        if (event.getTarget() instanceof EntityPlayer) {
            event.setCancelled(true);
        }
    }
    
    private void drawShit(final EntityLivingBase entity, final float x, final float y, final float right, final boolean blur) {
        final RapeMasterFontManager font = FontManager.arial20;
        final EntityLivingBase renderingEntity = entity;
        String rank = "";
        if (renderingEntity == NameTags.mc.thePlayer) {
            rank = "\u00a7a[You] ";
        }
        if (renderingEntity == KillAura.target) {
            rank = "\u00a74[Target] ";
        }
        else if (((Teams)Client.instance.moduleManager.getModule((Class)Teams.class)).getState() && Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a7a[Team]";
        }
        else if (HYTUtils.isStrength((EntityPlayer)renderingEntity) > 0 && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[Strength] ";
        }
        else if (HYTUtils.isRegen((EntityPlayer)renderingEntity) > 0 && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[Regen]";
        }
        else if (HYTUtils.isHoldingGodAxe((EntityPlayer)renderingEntity) && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[GodAxe] ";
        }
        else if (HYTUtils.isKBBall(renderingEntity.getHeldItem()) && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[KBBall] ";
        }
        else if (HYTUtils.hasEatenGoldenApple((EntityPlayer)renderingEntity) > 0 && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[GApple] ";
        }
        final String name = renderingEntity.getDisplayName().getFormattedText();
        final StringBuilder text = new StringBuilder(rank + "\u00a7f" + name + " " + this.DF_1.format(renderingEntity.getHealth()));
        final double fontScale = 0.8;
        float middle = x + (right - x) / 2.0f;
        final double fontHeight = font.getHeight() * fontScale;
        final float textWidth = (float)font.getStringWidth(text.toString());
        middle -= (float)(textWidth * fontScale / 2.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)middle, y - (fontHeight + 2.0), 0.0);
        GlStateManager.scale(fontScale, fontScale, 1.0);
        GlStateManager.translate((double)(-middle), -(y - (fontHeight + 2.0)), 0.0);
        final Color backgroundTagColor = new Color(0, 0, 0, 100);
        RoundedUtils.drawRound(middle - 3.0f - 2.0f, (float)(y - (fontHeight + 7.0)) - 2.0f, textWidth + 6.0f + 6.0f, 1.0f, 1.0f, HUD.color(8));
        RoundedUtils.drawRound(middle - 3.0f - 2.0f, (float)(y - (fontHeight + 7.0)), textWidth + 6.0f + 6.0f, (float)(fontHeight / fontScale), 1.0f, new Color(19, 19, 19, 200));
        RenderUtil.resetColor();
        GL11.glPopMatrix();
        if (!blur) {
            FontManager.arial16.drawStringWithShadow(text.toString(), middle, (float)(y - (fontHeight + 4.5)) + 1.0f, -1);
        }
    }
    
    private void drawNeon(final EntityLivingBase entity, final float x, final float y, final float right, final boolean blur) {
        final RapeMasterFontManager font = FontManager.arial20;
        final EntityLivingBase renderingEntity = entity;
        String rank = "";
        if (renderingEntity == NameTags.mc.thePlayer) {
            rank = "\u00a7a[You] ";
        }
        if (renderingEntity == KillAura.target) {
            rank = "\u00a74[Target] ";
        }
        else if (((Teams)Client.instance.moduleManager.getModule((Class)Teams.class)).getState() && Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a7a[Team]";
        }
        else if (HYTUtils.isStrength((EntityPlayer)renderingEntity) > 0 && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[Strength] ";
        }
        else if (HYTUtils.isRegen((EntityPlayer)renderingEntity) > 0 && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[Regen]";
        }
        else if (HYTUtils.isHoldingGodAxe((EntityPlayer)renderingEntity) && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[GodAxe] ";
        }
        else if (HYTUtils.isKBBall(renderingEntity.getHeldItem()) && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[KBBall] ";
        }
        else if (HYTUtils.hasEatenGoldenApple((EntityPlayer)renderingEntity) > 0 && renderingEntity != NameTags.mc.thePlayer && !Teams.isSameTeam((Entity)renderingEntity)) {
            rank = "\u00a74[GApple] ";
        }
        final String name = renderingEntity.getDisplayName().getFormattedText();
        final StringBuilder text = new StringBuilder(rank + "\u00a7f" + name + " " + this.DF_1.format(renderingEntity.getHealth()));
        final double fontScale = 0.8;
        float middle = x + (right - x) / 2.0f;
        final double fontHeight = font.getHeight() * fontScale;
        final float textWidth = (float)font.getStringWidth(text.toString());
        middle -= (float)(textWidth * fontScale / 2.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)middle, y - (fontHeight + 2.0), 0.0);
        GlStateManager.scale(fontScale, fontScale, 1.0);
        GlStateManager.translate((double)(-middle), -(y - (fontHeight + 2.0)), 0.0);
        final Color backgroundTagColor = new Color(0, 0, 0, 100);
        RoundedUtils.drawRound(middle - 3.0f - 15.0f, (float)(y - (fontHeight + 7.0)), textWidth + 6.0f + 30.0f, (float)(fontHeight / fontScale), 4.0f, backgroundTagColor);
        RenderUtil.resetColor();
        GL11.glPopMatrix();
        if (!blur) {
            FontManager.arial16.drawStringWithShadow(text.toString(), middle, (float)(y - (fontHeight + 5.0)) + 1.0f, -1);
        }
    }
    
    @EventTarget
    public void onShaderEvent(final EventShader e) {
        for (final Entity entity : this.entityPosition.keySet()) {
            final Vector4f pos = this.entityPosition.get(entity);
            final float x = pos.getX();
            final float y = pos.getY();
            final float right = pos.getZ();
            if (entity instanceof EntityLivingBase) {
                final HUD hud = (HUD)this.getModule((Class)HUD.class);
                switch ((HUD.HUDmode)hud.hudModeValue.getValue()) {
                    case Shit: {
                        this.drawShit((EntityLivingBase)entity, x, y, right, true);
                        continue;
                    }
                    case Neon: {
                        this.drawNeon((EntityLivingBase)entity, x, y, right, true);
                        continue;
                    }
                }
            }
        }
    }
    
    @EventTarget
    public void onRender2DEvent(final EventRender2D e) {
        for (final Entity entity : this.entityPosition.keySet()) {
            final Vector4f pos = this.entityPosition.get(entity);
            final float x = pos.getX();
            final float y = pos.getY();
            final float right = pos.getZ();
            if (entity instanceof EntityLivingBase) {
                final HUD hud = (HUD)this.getModule((Class)HUD.class);
                switch ((HUD.HUDmode)hud.hudModeValue.getValue()) {
                    case Shit: {
                        this.drawShit((EntityLivingBase)entity, x, y, right, false);
                        continue;
                    }
                    case Neon: {
                        this.drawNeon((EntityLivingBase)entity, x, y, right, false);
                        continue;
                    }
                }
            }
        }
    }
    
    private boolean shouldRender(final Entity entity) {
        if (entity.isDead || entity.isInvisible()) {
            return false;
        }
        if (AntiBot.isServerBot(entity)) {
            return false;
        }
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        if (entity == NameTags.mc.thePlayer) {
            return NameTags.mc.gameSettings.thirdPersonView != 0;
        }
        return !entity.getDisplayName().getUnformattedText().contains("[NPC");
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.waveycapes.renderlayers;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.waveycapes.config.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import shop.xiaoda.*;
import shop.xiaoda.utils.novoshader.*;
import net.minecraft.client.renderer.*;
import shop.xiaoda.utils.waveycapes.sim.*;
import shop.xiaoda.utils.waveycapes.util.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.waveycapes.*;

public class CustomCapeRenderLayer implements LayerRenderer<AbstractClientPlayer>
{
    static final int partCount = 16;
    private ModelRenderer[] customCape;
    private final RenderPlayer playerRenderer;
    private SmoothCapeRenderer smoothCapeRenderer;
    
    public CustomCapeRenderLayer(final RenderPlayer playerRenderer, final ModelBase model) {
        this.customCape = new ModelRenderer[16];
        this.smoothCapeRenderer = new SmoothCapeRenderer();
        this.playerRenderer = playerRenderer;
        this.buildMesh(model);
    }
    
    private void buildMesh(final ModelBase model) {
        this.customCape = new ModelRenderer[16];
        for (int i = 0; i < 16; ++i) {
            final ModelRenderer base = new ModelRenderer(model, 0, i);
            base.setTextureSize(64, 32);
            this.customCape[i] = base.addBox(-5.0f, (float)i, -1.0f, 10, 1, 1);
        }
    }
    
    public void doRenderLayer(final AbstractClientPlayer abstractClientPlayer, final float paramFloat1, final float paramFloat2, final float deltaTick, final float animationTick, final float paramFloat5, final float paramFloat6, final float paramFloat7) {
        if (abstractClientPlayer.isInvisible()) {
            return;
        }
        if (!abstractClientPlayer.hasPlayerInfo() || abstractClientPlayer.isInvisible() || !abstractClientPlayer.isWearing(EnumPlayerModelParts.CAPE) || abstractClientPlayer.getLocationCape() == null) {
            return;
        }
        if (Config.capeMovement == CapeMovement.BASIC_SIMULATION) {
            abstractClientPlayer.updateSimulation(abstractClientPlayer, 16);
        }
        this.playerRenderer.bindTexture(abstractClientPlayer.getLocationCape());
        if (Config.capeStyle == CapeStyle.SMOOTH) {
            if (abstractClientPlayer == Minecraft.getMinecraft().thePlayer) {
                if (Client.instance.blobShader == null) {
                    Client.instance.blobShader = new BackgroundShader();
                }
                this.smoothCapeRenderer.renderSmoothCape(this, abstractClientPlayer, deltaTick);
            }
            else {
                this.smoothCapeRenderer.renderSmoothCape(this, abstractClientPlayer, deltaTick);
            }
        }
        else {
            final ModelRenderer[] parts = this.customCape;
            for (int part = 0; part < 16; ++part) {
                final ModelRenderer model = parts[part];
                GlStateManager.pushMatrix();
                this.modifyPoseStack(abstractClientPlayer, deltaTick, part);
                model.render(0.0625f);
                GlStateManager.popMatrix();
            }
        }
    }
    
    private void modifyPoseStack(final AbstractClientPlayer abstractClientPlayer, final float h, final int part) {
        if (Config.capeMovement == CapeMovement.BASIC_SIMULATION) {
            this.modifyPoseStackSimulation(abstractClientPlayer, h, part);
            return;
        }
        this.modifyPoseStackVanilla(abstractClientPlayer, h, part);
    }
    
    private void modifyPoseStackSimulation(final AbstractClientPlayer abstractClientPlayer, final float delta, final int part) {
        final StickSimulation simulation = abstractClientPlayer.stickSimulation;
        GlStateManager.translate(0.0, 0.0, 0.125);
        float z = simulation.points.get(part).getLerpX(delta) - simulation.points.get(0).getLerpX(delta);
        if (z > 0.0f) {
            z = 0.0f;
        }
        final float y = simulation.points.get(0).getLerpY(delta) - part - simulation.points.get(part).getLerpY(delta);
        final float sidewaysRotationOffset = 0.0f;
        float partRotation = (float)(-Math.atan2(y, z));
        partRotation = Math.max(partRotation, 0.0f);
        if (partRotation != 0.0f) {
            partRotation = (float)(3.141592653589793 - partRotation);
        }
        partRotation *= (float)57.2958;
        partRotation *= 2.0f;
        float height = 0.0f;
        if (abstractClientPlayer.isSneaking()) {
            height += 25.0f;
            GlStateManager.translate(0.0f, 0.15f, 0.0f);
        }
        final float naturalWindSwing = this.getNatrualWindSwing(part);
        GlStateManager.rotate(6.0f + height + naturalWindSwing, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(sidewaysRotationOffset / 2.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-sidewaysRotationOffset / 2.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, y / 16.0f, z / 16.0f);
        GlStateManager.translate(0.0, 0.03, -0.03);
        GlStateManager.translate(0.0f, part * 1.0f / 16.0f, (float)(part * 0 / 16));
        GlStateManager.translate(0.0f, -part * 1.0f / 16.0f, (float)(-part * 0 / 16));
        GlStateManager.translate(0.0, -0.03, 0.03);
    }
    
    void modifyPoseStackVanilla(final AbstractClientPlayer abstractClientPlayer, final float h, final int part) {
        GlStateManager.translate(0.0, 0.0, 0.125);
        final double d = Mth.lerp(h, abstractClientPlayer.prevChasingPosX, abstractClientPlayer.chasingPosX) - Mth.lerp(h, abstractClientPlayer.prevPosX, abstractClientPlayer.posX);
        final double e = Mth.lerp(h, abstractClientPlayer.prevChasingPosY, abstractClientPlayer.chasingPosY) - Mth.lerp(h, abstractClientPlayer.prevPosY, abstractClientPlayer.posY);
        final double m = Mth.lerp(h, abstractClientPlayer.prevChasingPosZ, abstractClientPlayer.chasingPosZ) - Mth.lerp(h, abstractClientPlayer.prevPosZ, abstractClientPlayer.posZ);
        final float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        final double o = Math.sin(n * 0.017453292f);
        final double p = -Math.cos(n * 0.017453292f);
        float height = (float)e * 10.0f;
        height = MathHelper.clamp_float(height, -6.0f, 32.0f);
        float swing = (float)(d * o + m * p) * easeOutSine(0.0625f * part) * 100.0f;
        swing = MathHelper.clamp_float(swing, 0.0f, 150.0f * easeOutSine(0.0625f * part));
        float sidewaysRotationOffset = (float)(d * p - m * o) * 100.0f;
        sidewaysRotationOffset = MathHelper.clamp_float(sidewaysRotationOffset, -20.0f, 20.0f);
        final float t = Mth.lerp(h, abstractClientPlayer.prevCameraYaw, abstractClientPlayer.cameraYaw);
        height += (float)(Math.sin(Mth.lerp(h, abstractClientPlayer.prevDistanceWalkedModified, abstractClientPlayer.distanceWalkedModified) * 6.0f) * 32.0 * t);
        if (abstractClientPlayer.isSneaking()) {
            height += 25.0f;
            GlStateManager.translate(0.0f, 0.15f, 0.0f);
        }
        final float naturalWindSwing = this.getNatrualWindSwing(part);
        GlStateManager.rotate(6.0f + swing / 2.0f + height + naturalWindSwing, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(sidewaysRotationOffset / 2.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-sidewaysRotationOffset / 2.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
    }
    
    float getNatrualWindSwing(final int part) {
        if (Config.windMode == WindMode.WAVES) {
            final long highlightedPart = System.currentTimeMillis() / 3L % 360L;
            final float relativePart = (part + 1) / 16.0f;
            return (float)(Math.sin(Math.toRadians(relativePart * 360.0f - highlightedPart)) * 3.0);
        }
        return 0.0f;
    }
    
    private static float easeOutSine(final float x) {
        return (float)Math.sin(x * 3.141592653589793 / 2.0);
    }
    
    public boolean shouldCombineTextures() {
        return false;
    }
}

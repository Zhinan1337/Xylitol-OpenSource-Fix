//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.world;

import net.minecraft.util.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.render.fontRender.*;
import net.minecraft.client.*;
import shop.xiaoda.module.modules.render.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.event.rendering.*;
import java.awt.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.client.*;

public class CivBreak extends Module
{
    private BlockPos blockPos;
    private EnumFacing enumFacing;
    private final NumberValue range;
    private boolean breaking;
    private float breakPercent;
    private float widthAnim;
    private float alphaAnim;
    private float moveinAnim;
    private boolean canBreak;
    
    public CivBreak() {
        super("CivBreak", Category.Player);
        this.blockPos = null;
        this.enumFacing = null;
        this.range = new NumberValue("Range", 5.0, 1.0, 6.0, 0.5);
        this.breaking = false;
        this.breakPercent = 0.0f;
        this.widthAnim = 0.0f;
        this.alphaAnim = 0.0f;
        this.moveinAnim = 0.0f;
        this.canBreak = false;
    }
    
    @EventTarget
    public void onBlockClick(final EventClickBlock event) {
        this.breaking = true;
        this.blockPos = event.getClickedBlock();
        this.enumFacing = event.getEnumFacing();
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (this.blockPos == null || this.enumFacing == null) {
            return;
        }
        if (this.breakPercent * 50.0f >= 100.0f) {
            this.canBreak = (BlockUtil.getCenterDistance(this.blockPos) < this.range.getValue());
        }
        else {
            this.canBreak = false;
        }
        if (this.canBreak) {
            PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, this.enumFacing));
            this.blockPos = null;
            this.enumFacing = null;
            this.breaking = false;
            this.breakPercent = 0.0f;
        }
        if (this.breaking) {
            this.breakPercent += CivBreak.mc.theWorld.getBlockState(this.blockPos).getBlock().getPlayerRelativeBlockHardness((EntityPlayer)CivBreak.mc.thePlayer, (World)CivBreak.mc.theWorld, this.blockPos);
        }
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D event) {
        final ScaledResolution sr = new ScaledResolution(CivBreak.mc);
        if (this.breaking) {
            final float progress = Math.min(this.breakPercent / CivBreak.mc.theWorld.getBlockState(this.blockPos).getBlock().getBlockHardness((World)CivBreak.mc.theWorld, this.blockPos), 1.0f);
            final String string = String.format("%.1f", progress * 100.0f) + "%";
            final float x = sr.getScaledWidth() / 2 - 72.0f - FontManager.arial16.getStringWidth("100.0%") + 140.0f - 36.0f;
            FontManager.arial16.drawCenteredStringWithShadow(string, x, sr.getScaledHeight() - 70.0f + 2.0f, -1);
            final float widthAnim = this.widthAnim;
            final float target = progress * 140.0f;
            final float n = 8.0f;
            final Minecraft mc = CivBreak.mc;
            this.widthAnim = AnimationUtil.animateSmooth(widthAnim, target, n / Minecraft.getDebugFPS());
            final float moveinAnim = this.moveinAnim;
            final float target2 = 18.0f;
            final float n2 = 4.0f;
            final Minecraft mc2 = CivBreak.mc;
            this.moveinAnim = AnimationUtil.animateSmooth(moveinAnim, target2, n2 / Minecraft.getDebugFPS());
            final float alphaAnim = this.alphaAnim;
            final float target3 = 255.0f;
            final float n3 = 2.0f;
            final Minecraft mc3 = CivBreak.mc;
            this.alphaAnim = AnimationUtil.animateSmooth(alphaAnim, target3, n3 / Minecraft.getDebugFPS());
        }
        else {
            final float widthAnim2 = this.widthAnim;
            final float target4 = 0.0f;
            final float n4 = 8.0f;
            final Minecraft mc4 = CivBreak.mc;
            this.widthAnim = AnimationUtil.animateSmooth(widthAnim2, target4, n4 / Minecraft.getDebugFPS());
            final float moveinAnim2 = this.moveinAnim;
            final float target5 = 0.0f;
            final float n5 = 4.0f;
            final Minecraft mc5 = CivBreak.mc;
            this.moveinAnim = AnimationUtil.animateSmooth(moveinAnim2, target5, n5 / Minecraft.getDebugFPS());
            final float alphaAnim2 = this.alphaAnim;
            final float target6 = 0.0f;
            final float n6 = 2.0f;
            final Minecraft mc6 = CivBreak.mc;
            this.alphaAnim = AnimationUtil.animateSmooth(alphaAnim2, target6, n6 / Minecraft.getDebugFPS());
        }
        RoundedUtils.drawRound(sr.getScaledWidth() / 2 - 72.0f, sr.getScaledHeight() - 60 - 10 - this.moveinAnim, this.widthAnim, 6.0f, 3.0f, RenderUtil.reAlpha(HUD.color(8), (int)this.alphaAnim));
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        RenderUtil.drawBlockBox(this.blockPos, Color.WHITE, true);
    }
    
    @EventTarget
    public void onMotion(final EventMotion event) {
        if (event.isPost() && this.breaking) {
            PacketUtil.sendPacketC0F();
            PacketUtil.sendPacketNoEvent((Packet<?>)new C0APacketAnimation());
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import net.minecraft.client.*;
import shop.xiaoda.module.modules.world.*;
import shop.xiaoda.*;
import shop.xiaoda.utils.render.animation.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.init.*;
import shop.xiaoda.utils.render.fontRender.*;
import net.minecraft.item.*;
import shop.xiaoda.module.modules.movement.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import shop.xiaoda.utils.render.animation.impl.*;

public class ScaffoldCounter
{
    private static final Animation introAnimation;
    private static final Minecraft mc;
    
    public static void drawDefault() {
        final Scaffold Scaffold = (Scaffold)Client.instance.moduleManager.getModule((Class)Scaffold.class);
        if (!Scaffold.getState()) {
            return;
        }
        ScaffoldCounter.introAnimation.setDirection(Scaffold.getState() ? Direction.FORWARDS : Direction.BACKWARDS);
        GL11.glPushMatrix();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        scale(ScaffoldCounter.mc);
        ItemStack stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(ScaffoldCounter.mc.thePlayer.inventory.currentItem);
        try {
            if (ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot()) != null) {
                stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot());
            }
        }
        catch (RuntimeException e) {
            stack = new ItemStack(Blocks.barrier);
        }
        final int height = sr.getScaledHeight() - 90;
        float x = sr.getScaledWidth() / 2.0f - 1.0f;
        final float y = (float)(height + 19);
        final float y2 = (float)height;
        if (Scaffold.getBlockCount() >= 10 && Scaffold.getBlockCount() <= 99) {
            x -= 3.5;
        }
        else if (Scaffold.getBlockCount() >= 100 && Scaffold.getBlockCount() <= 999) {
            x -= 4.0f;
        }
        else if (Scaffold.getBlockCount() >= 1 && Scaffold.getBlockCount() <= 9) {
            --x;
        }
        RenderUtil.scaleStart(sr.getScaledWidth() / 2.0f - 13.0f - FontManager.arial20.getStringWidth("Blocks: " + Integer.toString(Scaffold.getBlockCount())) / 2 + (FontManager.arial20.getStringWidth("Blocks: " + Integer.toString(Scaffold.getBlockCount())) / 2 + 35) / 2, y2 - 6.0f + 10.0f, (float)ScaffoldCounter.introAnimation.getOutput());
        if (stack != null && stack.getItem() instanceof ItemBlock && OldScaffold.isValid(stack.getItem()) && !ScaffoldCounter.introAnimation.finished(Direction.BACKWARDS)) {
            RoundedUtils.drawRound(sr.getScaledWidth() / 2.0f - 13.0f - FontManager.arial20.getStringWidth("Blocks: " + Integer.toString(Scaffold.getBlockCount())) / 2, y2 - 6.0f, (float)(35 + FontManager.arial20.getStringWidth("Blocks: " + Integer.toString(Scaffold.getBlockCount())) / 2), 20.0f, 2.0f, new Color(0, 0, 0, 80));
            FontManager.arial20.drawCenteredStringWithShadow("Blocks: " + Integer.toString(Scaffold.getBlockCount()), sr.getScaledWidth() / 2.0f - 8.0f, y - 19.0f, -1);
        }
        RenderUtil.resetColor();
        RenderUtil.scaleEnd();
        GL11.glPopMatrix();
    }
    
    public static void drawDefaultBloom() {
        final Scaffold Scaffold = (Scaffold)Client.instance.moduleManager.getModule((Class)Scaffold.class);
        ScaffoldCounter.introAnimation.setDirection(Scaffold.getState() ? Direction.FORWARDS : Direction.BACKWARDS);
        GL11.glPushMatrix();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        scale(ScaffoldCounter.mc);
        ItemStack stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(ScaffoldCounter.mc.thePlayer.inventory.currentItem);
        try {
            if (ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot()) != null) {
                stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot());
            }
        }
        catch (RuntimeException e) {
            stack = new ItemStack(Blocks.barrier);
        }
        final int height = sr.getScaledHeight() - 90;
        final float y1 = (float)height;
        RenderUtil.resetColor();
        RenderUtil.scaleStart(sr.getScaledWidth() / 2.0f - 13.0f - FontManager.arial20.getStringWidth("Block: " + Integer.toString(Scaffold.getBlockCount())) / 2 + (FontManager.arial20.getStringWidth("Block: " + Integer.toString(Scaffold.getBlockCount())) / 2 + 35) / 2, y1 - 6.0f + 10.0f, (float)ScaffoldCounter.introAnimation.getOutput());
        if (stack != null && stack.getItem() instanceof ItemBlock && OldScaffold.isValid(stack.getItem()) && !ScaffoldCounter.introAnimation.finished(Direction.BACKWARDS)) {
            RoundedUtils.drawRound(sr.getScaledWidth() / 2.0f - 13.0f - FontManager.arial20.getStringWidth("Blocks: " + Integer.toString(Scaffold.getBlockCount())) / 2, y1 - 6.0f, (float)(35 + FontManager.arial20.getStringWidth("Blocks: " + Integer.toString(Scaffold.getBlockCount())) / 2), 20.0f, 2.0f, new Color(0, 0, 0, 255));
        }
        RenderUtil.resetColor();
        RenderUtil.scaleEnd();
        GL11.glPopMatrix();
    }
    
    public static void drawClassic() {
        final Scaffold Scaffold = (Scaffold)Client.instance.moduleManager.getModule((Class)Scaffold.class);
        ScaffoldCounter.introAnimation.setDirection(Scaffold.getState() ? Direction.FORWARDS : Direction.BACKWARDS);
        GL11.glPushMatrix();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        scale(ScaffoldCounter.mc);
        ItemStack stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(ScaffoldCounter.mc.thePlayer.inventory.currentItem);
        try {
            if (ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot()) != null) {
                stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot());
            }
        }
        catch (RuntimeException e) {
            stack = new ItemStack(Blocks.barrier);
        }
        final int height = sr.getScaledHeight() - 90;
        final float x = sr.getScaledWidth() / 2.0f - 1.0f;
        final float y = (float)(height + 19);
        final float y2 = (float)height;
        RenderUtil.scaleStart(sr.getScaledWidth() / 2.0f - 13.0f + 20.0f, y2 - 40.0f + 25.0f, (float)ScaffoldCounter.introAnimation.getOutput());
        if (stack != null && stack.getItem() instanceof ItemBlock && OldScaffold.isValid(stack.getItem()) && !ScaffoldCounter.introAnimation.finished(Direction.BACKWARDS)) {
            RoundedUtils.drawRound(sr.getScaledWidth() / 2.0f - 13.0f, y2 - 40.0f, 40.0f, 50.0f, 4.0f, new Color(0, 0, 0, 80));
            FontManager.arial20.drawCenteredStringWithShadow(Integer.toString(Scaffold.getBlockCount()), x + 7.0f, y - 25.0f, -1);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.5f, 1.5f, 1.5f);
            RenderUtil.drawStack(stack, (sr.getScaledWidth() / 2.0f - 4.5f) / 1.5f, (y - 55.0f) / 1.5f);
            GlStateManager.popMatrix();
        }
        RenderUtil.resetColor();
        RenderUtil.scaleEnd();
        GL11.glPopMatrix();
    }
    
    public static void drawClassicBloom() {
        final Scaffold Scaffold = (Scaffold)Client.instance.moduleManager.getModule((Class)Scaffold.class);
        ScaffoldCounter.introAnimation.setDirection(Scaffold.getState() ? Direction.FORWARDS : Direction.BACKWARDS);
        GL11.glPushMatrix();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        scale(ScaffoldCounter.mc);
        ItemStack stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(ScaffoldCounter.mc.thePlayer.inventory.currentItem);
        try {
            if (ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot()) != null) {
                stack = ScaffoldCounter.mc.thePlayer.inventory.getStackInSlot(Scaffold.getBlockSlot());
            }
        }
        catch (RuntimeException e) {
            stack = new ItemStack(Blocks.barrier);
        }
        final int height = sr.getScaledHeight() - 90;
        final float y1 = (float)height;
        RenderUtil.resetColor();
        RenderUtil.scaleStart(sr.getScaledWidth() / 2.0f - 13.0f + 20.0f, y1 - 40.0f + 25.0f, (float)ScaffoldCounter.introAnimation.getOutput());
        if (stack != null && stack.getItem() instanceof ItemBlock && OldScaffold.isValid(stack.getItem()) && !ScaffoldCounter.introAnimation.finished(Direction.BACKWARDS)) {
            RoundedUtils.drawRound(sr.getScaledWidth() / 2.0f - 13.0f, y1 - 40.0f, 40.0f, 50.0f, 4.0f, new Color(0, 0, 0, 255));
        }
        RenderUtil.resetColor();
        RenderUtil.scaleEnd();
        GL11.glPopMatrix();
    }
    
    public static void scale(final Minecraft mc) {
        switch (mc.gameSettings.guiScale) {
            case 0: {
                GlStateManager.scale(0.5, 0.5, 0.5);
                break;
            }
            case 1: {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                break;
            }
            case 3: {
                GlStateManager.scale(0.6666666666666667, 0.6666666666666667, 0.6666666666666667);
                break;
            }
        }
    }
    
    static {
        introAnimation = (Animation)new EaseBackIn(450, 1.0, 1.5f);
        mc = Minecraft.getMinecraft();
    }
}

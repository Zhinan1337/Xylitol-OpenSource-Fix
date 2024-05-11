
package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.*;
import java.util.*;
import java.text.*;
import shop.xiaoda.event.world.*;
import net.minecraft.client.gui.inventory.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import java.awt.*;

import shop.xiaoda.module.Module;
import shop.xiaoda.utils.render.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;
import net.minecraft.client.gui.*;

public final class Health extends Module
{
    private final DecimalFormat decimalFormat;
    private final Random random;
    private int width;
    
    public Health() {
        super("Health", Category.Render);
        this.decimalFormat = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
        this.random = new Random();
    }
    
    @EventTarget
    public void onRenderGuiEvent(final EventUpdate event) {
        if (Health.mc.currentScreen instanceof GuiInventory || Health.mc.currentScreen instanceof GuiChest || Health.mc.currentScreen instanceof GuiContainerCreative) {
            this.renderHealth();
        }
    }
    
    @EventTarget
    public void onRender2DEvent(final EventRender2D event) {
        if (!(Health.mc.currentScreen instanceof GuiInventory) && !(Health.mc.currentScreen instanceof GuiChest)) {
            this.renderHealth();
        }
    }
    
    private void renderHealth() {
        final ScaledResolution scaledResolution = new ScaledResolution(Health.mc);
        final GuiScreen screen = Health.mc.currentScreen;
        final float absorptionHealth = Health.mc.thePlayer.getAbsorptionAmount();
        final String string = this.decimalFormat.format(Health.mc.thePlayer.getHealth() / 2.0f) + "\u00a7c\u2764 " + ((absorptionHealth <= 0.0f) ? "" : ("\u00a7e" + this.decimalFormat.format(absorptionHealth / 2.0f) + "\u00a76\u2764"));
        int offsetY = 0;
        if ((Health.mc.thePlayer.getHealth() >= 0.0f && Health.mc.thePlayer.getHealth() < 10.0f) || (Health.mc.thePlayer.getHealth() >= 10.0f && Health.mc.thePlayer.getHealth() < 100.0f)) {
            this.width = 3;
        }
        if (screen instanceof GuiInventory) {
            offsetY = 70;
        }
        else if (screen instanceof GuiContainerCreative) {
            offsetY = 80;
        }
        else if (screen instanceof GuiChest) {
            offsetY = ((GuiChest)screen).ySize / 2 - 15;
        }
        final int x = new ScaledResolution(Health.mc).getScaledWidth() / 2 - this.width;
        final int y = new ScaledResolution(Health.mc).getScaledHeight() / 2 + 25 + offsetY;
        final Color color = Colors.blendColors(new float[] { 0.0f, 0.5f, 1.0f }, new Color[] { new Color(255, 37, 0), Color.YELLOW, Color.GREEN }, Health.mc.thePlayer.getHealth() / Health.mc.thePlayer.getMaxHealth());
        Health.mc.fontRendererObj.drawString(string, (absorptionHealth > 0.0f) ? (x - 15.5f) : (x - 3.5f), (float)y, color.getRGB(), true);
        GL11.glPushMatrix();
        Health.mc.getTextureManager().bindTexture(Gui.icons);
        this.random.setSeed(Health.mc.ingameGUI.updateCounter * 312871L);
        final float width = scaledResolution.getScaledWidth() / 2.0f - Health.mc.thePlayer.getMaxHealth() / 2.5f * 10.0f / 2.0f;
        final float maxHealth = Health.mc.thePlayer.getMaxHealth();
        final int lastPlayerHealth = Health.mc.ingameGUI.lastPlayerHealth;
        final int healthInt = MathHelper.ceiling_float_int(Health.mc.thePlayer.getHealth());
        int l2 = -1;
        final boolean flag = Health.mc.ingameGUI.healthUpdateCounter > Health.mc.ingameGUI.updateCounter && (Health.mc.ingameGUI.healthUpdateCounter - Health.mc.ingameGUI.updateCounter) / 3L % 2L == 1L;
        if (Health.mc.thePlayer.isPotionActive(Potion.regeneration)) {
            l2 = Health.mc.ingameGUI.updateCounter % MathHelper.ceiling_float_int(maxHealth + 5.0f);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        for (int i6 = MathHelper.ceiling_float_int(maxHealth / 2.0f) - 1; i6 >= 0; --i6) {
            int xOffset = 16;
            if (Health.mc.thePlayer.isPotionActive(Potion.poison)) {
                xOffset += 36;
            }
            else if (Health.mc.thePlayer.isPotionActive(Potion.wither)) {
                xOffset += 72;
            }
            int k3 = 0;
            if (flag) {
                k3 = 1;
            }
            final float renX = width + i6 % 10 * 8;
            float renY = scaledResolution.getScaledHeight() / 2.0f + 15.0f + offsetY;
            if (healthInt <= 4) {
                renY += this.random.nextInt(2);
            }
            if (i6 == l2) {
                renY -= 2.0f;
            }
            int yOffset = 0;
            if (Health.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                yOffset = 5;
            }
            Gui.drawTexturedModalRect(renX, renY, 16 + k3 * 9, 9 * yOffset, 9, 9);
            if (flag) {
                if (i6 * 2 + 1 < lastPlayerHealth) {
                    Gui.drawTexturedModalRect(renX, renY, xOffset + 54, 9 * yOffset, 9, 9);
                }
                if (i6 * 2 + 1 == lastPlayerHealth) {
                    Gui.drawTexturedModalRect(renX, renY, xOffset + 63, 9 * yOffset, 9, 9);
                }
            }
            if (i6 * 2 + 1 < healthInt) {
                Gui.drawTexturedModalRect(renX, renY, xOffset + 36, 9 * yOffset, 9, 9);
            }
            if (i6 * 2 + 1 == healthInt) {
                Gui.drawTexturedModalRect(renX, renY, xOffset + 45, 9 * yOffset, 9, 9);
            }
        }
        GL11.glPopMatrix();
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import java.awt.image.*;
import java.awt.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import java.nio.*;

public class ColorPalette
{
    public static void draw(final int x, final int y, final int width, final int height) {
        final BufferedImage image = new BufferedImage(360, 100, 2);
        for (int H = 0; H <= 360; H += 10) {
            for (int B = 0; B <= 100; B += 10) {
                final int color = Color.getHSBColor(H / 360.0f, 1.0f, B / 100.0f).getRGB();
                final int x2 = H / 10;
                final int y2 = 10 - B / 10;
                image.setRGB(x2, y2, color);
            }
        }
        final int textureId = TextureUtil.glGenTextures();
        GL11.glBindTexture(3553, textureId);
        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y3 = 0; y3 < image.getHeight(); ++y3) {
            for (int x2 = 0; x2 < image.getWidth(); ++x2) {
                final int pixel = image.getRGB(x2, y3);
                buffer.put((byte)(pixel >> 16 & 0xFF));
                buffer.put((byte)(pixel >> 8 & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)(pixel >> 24 & 0xFF));
            }
        }
        buffer.flip();
        GL11.glTexImage2D(3553, 0, 6408, image.getWidth(), image.getHeight(), 0, 6408, 5121, buffer);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, 0.0f);
        GL11.glBindTexture(3553, textureId);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f((float)width, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f((float)width, (float)height, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, (float)height, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}

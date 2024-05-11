package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.rendering.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import shop.xiaoda.utils.render.*;
import net.minecraft.client.renderer.entity.*;
import shop.xiaoda.event.*;

public class Chams extends Module
{
    public static ColorValue chamsColours;
    public static ModeValue<chamsMode> mode;
    public static BoolValue flat;
    public static BoolValue teamCol;
    public static NumberValue alpha;
    
    public Chams() {
        super("Chams", Category.Render);
    }
    
    @EventTarget
    public void onRenderLivingEntity(final EventRLE evt) {
        if (evt.getEntity() != Chams.mc.thePlayer) {
            if (evt.isPre()) {
                if (Chams.mode.getValue() == chamsMode.Colored) {
                    evt.setCancelled(true);
                    try {
                        final Render<Entity> renderObject = (Render<Entity>)Chams.mc.getRenderManager().getEntityRenderObject((Entity)evt.getEntity());
                        if (renderObject != null && Chams.mc.getRenderManager().renderEngine != null && renderObject instanceof RendererLivingEntity) {
                            GL11.glPushMatrix();
                            GL11.glDisable(2929);
                            GL11.glBlendFunc(770, 771);
                            GL11.glDisable(3553);
                            GL11.glEnable(3042);
                            Color teamColor = null;
                            if (Chams.flat.getValue()) {
                                GlStateManager.disableLighting();
                            }
                            if (Chams.teamCol.getValue()) {
                                final String text = evt.getEntity().getDisplayName().getFormattedText();
                                for (int i = 0; i < text.length(); ++i) {
                                    if (text.charAt(i) == '\u00a7' && i + 1 < text.length()) {
                                        final char oneMore = Character.toLowerCase(text.charAt(i + 1));
                                        final int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                                        if (colorCode < 16) {
                                            try {
                                                final Color newCol;
                                                teamColor = (newCol = new Color(Chams.mc.fontRendererObj.colorCode[colorCode]));
                                                GL11.glColor4f(newCol.getRed() / 255.0f, newCol.getGreen() / 255.0f, newCol.getBlue() / 255.0f, Chams.alpha.getValue().floatValue() / 255.0f);
                                            }
                                            catch (ArrayIndexOutOfBoundsException exception) {
                                                GL11.glColor4f(1.0f, 0.0f, 0.0f, Chams.alpha.getValue().floatValue() / 255.0f);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                final int c = RenderUtil.reAlpha(new Color(Chams.chamsColours.getValue()), Chams.alpha.getValue().intValue()).getRGB();
                                RenderUtil.glColor(c);
                            }
                            ((RendererLivingEntity)renderObject).renderModel(evt.getEntity(), evt.getLimbSwing(), evt.getLimbSwingAmount(), evt.getAgeInTicks(), evt.getRotationYawHead(), evt.getRotationPitch(), evt.getOffset());
                            GL11.glEnable(2929);
                            if (Chams.teamCol.getValue() && teamColor != null) {
                                GL11.glColor4f(teamColor.getRed() / 255.0f, teamColor.getGreen() / 255.0f, teamColor.getBlue() / 255.0f, Chams.alpha.getValue().floatValue() / 255.0f);
                            }
                            else {
                                final int c = Chams.chamsColours.getValue();
                                GL11.glColor4f((float)new Color(c).getRGB(), (float)new Color(c).getRGB(), (float)new Color(c).getRGB(), Chams.alpha.getValue().floatValue() / 255.0f);
                            }
                            ((RendererLivingEntity)renderObject).renderModel(evt.getEntity(), evt.getLimbSwing(), evt.getLimbSwingAmount(), evt.getAgeInTicks(), evt.getRotationYawHead(), evt.getRotationPitch(), evt.getOffset());
                            GL11.glEnable(3553);
                            GL11.glDisable(3042);
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, Chams.alpha.getValue().floatValue() / 255.0f);
                            if (Chams.flat.getValue()) {
                                GlStateManager.enableLighting();
                            }
                            GL11.glPopMatrix();
                            ((RendererLivingEntity)renderObject).renderLayers(evt.getEntity(), evt.getLimbSwing(), evt.getLimbSwingAmount(), Chams.mc.timer.renderPartialTicks, evt.getAgeInTicks(), evt.getRotationYawHead(), evt.getRotationPitch(), evt.getOffset());
                            GL11.glPopMatrix();
                        }
                    }
                    catch (Exception ex) {}
                }
                else {
                    GL11.glEnable(32823);
                    GL11.glPolygonOffset(1.0f, -1100000.0f);
                }
            }
            else if (Chams.mode.getValue() != chamsMode.Colored && evt.isPost()) {
                GL11.glDisable(32823);
                GL11.glPolygonOffset(1.0f, 1100000.0f);
            }
        }
    }
    
    static {
        Chams.chamsColours = new ColorValue("ChamsColor", Color.RED.getRGB());
        Chams.mode = new ModeValue<chamsMode>("ChamsMode", chamsMode.values(), chamsMode.Normal);
        Chams.flat = new BoolValue("Flat", false);
        Chams.teamCol = new BoolValue("TeamColor", false);
        Chams.alpha = new NumberValue("Alpha", 170.0, 0.0, 255.0, 1.0);
    }
    
    public enum chamsMode
    {
        Normal, 
        Colored;
    }
}

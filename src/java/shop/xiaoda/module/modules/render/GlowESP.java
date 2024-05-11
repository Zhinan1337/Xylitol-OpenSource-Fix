// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.rendering.EventRender2D;
import shop.xiaoda.event.rendering.EventRenderModel;
import shop.xiaoda.event.world.EventWorldLoad;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.render.HUD;
import shop.xiaoda.module.values.BoolValue;
import shop.xiaoda.module.values.ColorValue;
import shop.xiaoda.module.values.ModeValue;
import shop.xiaoda.module.values.NumberValue;
import shop.xiaoda.utils.ESPUtil;
import shop.xiaoda.utils.client.MathUtil;
import shop.xiaoda.utils.render.ColorUtil;
import shop.xiaoda.utils.render.GLUtil;
import shop.xiaoda.utils.render.RenderUtil;
import shop.xiaoda.utils.render.ShaderUtil;
import shop.xiaoda.utils.render.animation.Animation;
import shop.xiaoda.utils.render.animation.impl.DecelerateAnimation;

public class GlowESP
        extends Module {
    public static GlowESP INSTANCE;
    private final BoolValue kawaseGlow = new BoolValue("Kawase Glow", false);
    private final BoolValue outlineValue = new BoolValue("Outline", false);
    private final ModeValue<cmode> colorMode = new ModeValue("Color Mode", (Enum[])cmode.values(), (Enum)cmode.Sync);
    private final ColorValue playerColor = new ColorValue("Player Color", HUD.mainColor.getColor());
    private final ColorValue hurtTimeColor = new ColorValue("Hurt Time Color", Color.RED.getRGB());
    private final NumberValue radius = new NumberValue("Radius", 4.0, 20.0, 2.0, 2.0);
    private final NumberValue iterationsSetting = new NumberValue("Iterations", 4.0, 10.0, 2.0, 1.0);
    private final NumberValue offsetSetting = new NumberValue("Offset", 4.0, 10.0, 2.0, 1.0);
    private final NumberValue exposure = new NumberValue("Exposure", 2.2, 3.5, 0.5, 0.1);
    private final BoolValue seperate = new BoolValue("Seperate Texture", false);
    public static boolean renderNameTags;
    private final ShaderUtil chamsShader = new ShaderUtil("chams");
    private final ShaderUtil outlineShader = new ShaderUtil("express/shader/outline.frag");
    private final ShaderUtil glowShader = new ShaderUtil("glow");
    private final ShaderUtil kawaseGlowShader = new ShaderUtil("kawaseDownBloom");
    private final ShaderUtil kawaseGlowShader2 = new ShaderUtil("kawaseUpGlow");
    public Framebuffer framebuffer;
    public Framebuffer outlineFrameBuffer;
    public Framebuffer glowFrameBuffer;
    public static boolean renderGlint;
    private final List<Entity> entities = new ArrayList<Entity>();
    private final Map<Object, Color> entityColorMap = new HashMap<Object, Color>();
    public static Animation fadeIn;
    private static int currentIterations;
    private static final List<Framebuffer> framebufferList;

    public GlowESP() {
        super("GlowESP", Category.Render);
        INSTANCE = this;
    }

    @EventTarget
    public void onWorldEvent(EventWorldLoad event) {
        this.entityColorMap.clear();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.entityColorMap.clear();
        fadeIn = new DecelerateAnimation(250, 1.0);
    }

    public void createFrameBuffers() {
        this.framebuffer = RenderUtil.createFrameBuffer(this.framebuffer);
        this.outlineFrameBuffer = RenderUtil.createFrameBuffer(this.outlineFrameBuffer);
    }

    @EventTarget
    public void onRenderModelEvent(EventRenderModel e) {
        if (!e.isPost() && ((Boolean)this.outlineValue.getValue()).booleanValue()) {
            GlStateManager.resetColor();
            int color = this.getColor(e.getEntity()).getRGB();
            RenderUtil.setColor(color);
            RenderUtil.renderOne();
            e.drawModel();
            RenderUtil.setColor(color);
            RenderUtil.renderTwo();
            e.drawModel();
            RenderUtil.setColor(color);
            RenderUtil.renderThree();
            e.drawModel();
            RenderUtil.setColor(color);
            RenderUtil.renderFour(color);
            e.drawModel();
            RenderUtil.setColor(color);
            RenderUtil.renderFive();
            RenderUtil.setColor(Color.WHITE.getRGB());
        }
        if (e.isPost() && this.framebuffer != null) {
            if (!this.entities.contains(e.getEntity())) {
                return;
            }
            this.framebuffer.bindFramebuffer(false);
            this.chamsShader.init();
            this.chamsShader.setUniformi("textureIn", 0);
            Color color = this.getColor(e.getEntity());
            this.chamsShader.setUniformf("color", (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, 1.0f);
            RenderUtil.resetColor();
            GlStateManager.enableCull();
            renderGlint = false;
            e.drawModel();
            e.drawLayers();
            renderGlint = true;
            GlStateManager.disableCull();
            this.chamsShader.unload();
            mc.getFramebuffer().bindFramebuffer(false);
        }
    }

    @EventTarget
    public void onRender2DEvent(EventRender2D e) {
        this.createFrameBuffers();
        this.collectEntities();
        ScaledResolution sr = new ScaledResolution(mc);
        if (this.framebuffer != null && this.outlineFrameBuffer != null && this.entities.size() > 0) {
            RenderUtil.setAlphaLimit(0.0f);
            GLUtil.startBlend();
            this.outlineFrameBuffer.framebufferClear();
            this.outlineFrameBuffer.bindFramebuffer(false);
            this.outlineShader.init();
            this.setupOutlineUniforms(0.0f, 1.0f);
            RenderUtil.bindTexture(this.framebuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            this.outlineShader.init();
            this.setupOutlineUniforms(1.0f, 0.0f);
            RenderUtil.bindTexture(this.framebuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            this.outlineShader.unload();
            this.outlineFrameBuffer.unbindFramebuffer();
            if (((Boolean)this.kawaseGlow.getValue()).booleanValue()) {
                int i;
                int offset = ((Double)this.offsetSetting.getValue()).intValue();
                int iterations = 3;
                if (framebufferList.isEmpty() || currentIterations != iterations || this.framebuffer.framebufferWidth != GlowESP.mc.displayWidth || this.framebuffer.framebufferHeight != GlowESP.mc.displayHeight) {
                    this.initFramebuffers(iterations);
                    currentIterations = iterations;
                }
                RenderUtil.setAlphaLimit(0.0f);
                GL11.glBlendFunc(1, 1);
                GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GlowESP.renderFBO(framebufferList.get(1), this.outlineFrameBuffer.framebufferTexture, this.kawaseGlowShader, offset);
                for (i = 1; i < iterations; ++i) {
                    GlowESP.renderFBO(framebufferList.get(i + 1), GlowESP.framebufferList.get((int)i).framebufferTexture, this.kawaseGlowShader, offset);
                }
                for (i = iterations; i > 1; --i) {
                    GlowESP.renderFBO(framebufferList.get(i - 1), GlowESP.framebufferList.get((int)i).framebufferTexture, this.kawaseGlowShader2, offset);
                }
                Framebuffer lastBuffer = framebufferList.get(0);
                lastBuffer.framebufferClear();
                lastBuffer.bindFramebuffer(false);
                this.kawaseGlowShader2.init();
                this.kawaseGlowShader2.setUniformf("offset", offset, offset);
                this.kawaseGlowShader2.setUniformi("inTexture", 0);
                this.kawaseGlowShader2.setUniformi("check", (Boolean)this.seperate.getValue() != false ? 1 : 0);
                this.kawaseGlowShader2.setUniformf("lastPass", 1.0f);
                this.kawaseGlowShader2.setUniformf("exposure", (float)((double)((Double)this.exposure.getValue()).floatValue() * fadeIn.getOutput()));
                this.kawaseGlowShader2.setUniformi("textureToCheck", 16);
                this.kawaseGlowShader2.setUniformf("halfpixel", 1.0f / (float)lastBuffer.framebufferWidth, 1.0f / (float)lastBuffer.framebufferHeight);
                this.kawaseGlowShader2.setUniformf("iResolution", lastBuffer.framebufferWidth, lastBuffer.framebufferHeight);
                GL13.glActiveTexture(34000);
                RenderUtil.bindTexture(this.framebuffer.framebufferTexture);
                GL13.glActiveTexture(33984);
                RenderUtil.bindTexture(GlowESP.framebufferList.get((int)1).framebufferTexture);
                ShaderUtil.drawQuads();
                this.kawaseGlowShader2.unload();
                GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GL11.glBlendFunc(770, 771);
                this.framebuffer.framebufferClear();
                RenderUtil.resetColor();
                mc.getFramebuffer().bindFramebuffer(true);
                RenderUtil.bindTexture(GlowESP.framebufferList.get((int)0).framebufferTexture);
                ShaderUtil.drawQuads();
                RenderUtil.setAlphaLimit(0.0f);
                GlStateManager.bindTexture(0);
            } else {
                if (!framebufferList.isEmpty()) {
                    for (Framebuffer framebuffer : framebufferList) {
                        framebuffer.deleteFramebuffer();
                    }
                    this.glowFrameBuffer = null;
                    framebufferList.clear();
                }
                this.glowFrameBuffer = RenderUtil.createFrameBuffer(this.glowFrameBuffer);
                GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                this.glowFrameBuffer.framebufferClear();
                this.glowFrameBuffer.bindFramebuffer(false);
                this.glowShader.init();
                this.setupGlowUniforms(1.0f, 0.0f);
                RenderUtil.bindTexture(this.outlineFrameBuffer.framebufferTexture);
                GL11.glBlendFunc(1, 1);
                ShaderUtil.drawQuads();
                this.glowShader.unload();
                mc.getFramebuffer().bindFramebuffer(false);
                GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                this.glowShader.init();
                this.setupGlowUniforms(0.0f, 1.0f);
                if (((Boolean)this.seperate.getValue()).booleanValue()) {
                    GL13.glActiveTexture(34000);
                    RenderUtil.bindTexture(this.framebuffer.framebufferTexture);
                }
                GL13.glActiveTexture(33984);
                RenderUtil.bindTexture(this.glowFrameBuffer.framebufferTexture);
                GL11.glBlendFunc(770, 771);
                ShaderUtil.drawQuads();
                this.glowShader.unload();
                this.framebuffer.framebufferClear();
                mc.getFramebuffer().bindFramebuffer(false);
            }
        }
    }

    private void initFramebuffers(float iterations) {
        for (Framebuffer framebuffer : framebufferList) {
            framebuffer.deleteFramebuffer();
        }
        framebufferList.clear();
        this.glowFrameBuffer = RenderUtil.createFrameBuffer(null);
        framebufferList.add(this.glowFrameBuffer);
        int i = 1;
        while ((float)i <= iterations) {
            Framebuffer currentBuffer = new Framebuffer((int)((double)GlowESP.mc.displayWidth / Math.pow(2.0, i)), (int)((double)GlowESP.mc.displayHeight / Math.pow(2.0, i)), true);
            currentBuffer.setFramebufferFilter(9729);
            GlStateManager.bindTexture(currentBuffer.framebufferTexture);
            GL11.glTexParameteri(3553, 10242, 33648);
            GL11.glTexParameteri(3553, 10243, 33648);
            GlStateManager.bindTexture(0);
            framebufferList.add(currentBuffer);
            ++i;
        }
    }

    private static void renderFBO(Framebuffer framebuffer, int framebufferTexture, ShaderUtil shader, float offset) {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(false);
        shader.init();
        RenderUtil.bindTexture(framebufferTexture);
        shader.setUniformf("offset", offset, offset);
        shader.setUniformi("inTexture", 0);
        shader.setUniformi("check", 0);
        shader.setUniformf("lastPass", 0.0f);
        shader.setUniformf("halfpixel", 1.0f / (float)framebuffer.framebufferWidth, 1.0f / (float)framebuffer.framebufferHeight);
        shader.setUniformf("iResolution", framebuffer.framebufferWidth, framebuffer.framebufferHeight);
        ShaderUtil.drawQuads();
        shader.unload();
    }

    public void setupGlowUniforms(float dir1, float dir2) {
        this.glowShader.setUniformi("texture", 0);
        if (((Boolean)this.seperate.getValue()).booleanValue()) {
            this.glowShader.setUniformi("textureToCheck", 16);
        }
        this.glowShader.setUniformf("radius", ((Double)this.radius.getValue()).floatValue());
        this.glowShader.setUniformf("texelSize", 1.0f / (float)GlowESP.mc.displayWidth, 1.0f / (float)GlowESP.mc.displayHeight);
        this.glowShader.setUniformf("direction", dir1, dir2);
        this.glowShader.setUniformf("exposure", (float)((double)((Double)this.exposure.getValue()).floatValue() * fadeIn.getOutput()));
        this.glowShader.setUniformi("avoidTexture", (Boolean)this.seperate.getValue() != false ? 1 : 0);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(256);
        int i = 1;
        while ((float)i <= ((Double)this.radius.getValue()).floatValue()) {
            buffer.put(MathUtil.calculateGaussianValue(i, ((Double)this.radius.getValue()).floatValue() / 2.0f));
            ++i;
        }
        buffer.rewind();
        OpenGlHelper.glUniform1(this.glowShader.getUniform("weights"), buffer);
    }

    public void setupOutlineUniforms(float dir1, float dir2) {
        this.outlineShader.setUniformi("textureIn", 0);
        float iterations = (Boolean)this.kawaseGlow.getValue() != false ? ((Double)this.iterationsSetting.getValue()).floatValue() * 2.0f : ((Double)this.radius.getValue()).floatValue() / 1.5f;
        this.outlineShader.setUniformf("radius", iterations);
        this.outlineShader.setUniformf("texelSize", 1.0f / (float)GlowESP.mc.displayWidth, 1.0f / (float)GlowESP.mc.displayHeight);
        this.outlineShader.setUniformf("direction", dir1, dir2);
    }

    private Color getColor(Object entity) {
        Color color = Color.WHITE;
        switch (this.colorMode.get().name()) {
            case "Custom": {
                if (!(entity instanceof EntityPlayer)) break;
                color = this.playerColor.getColorC();
                break;
            }
            case "Sync": {
                color = HUD.color(8);
                break;
            }
            case "Random": {
                if (this.entityColorMap.containsKey(entity)) {
                    color = this.entityColorMap.get(entity);
                    break;
                }
                color = ColorUtil.getRandomColor();
                this.entityColorMap.put(entity, color);
            }
        }
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            if (entityLivingBase.hurtTime > 0) {
                color = ColorUtil.interpolateColorC(color, this.hurtTimeColor.getColorC(), (float)Math.sin((double)entityLivingBase.hurtTime * 0.3141592653589793));
            }
        }
        return color;
    }

    public void collectEntities() {
        this.entities.clear();
        for (Entity entity : GlowESP.mc.theWorld.getLoadedEntityList()) {
            if (!ESPUtil.isInView(entity) || entity == GlowESP.mc.thePlayer && GlowESP.mc.gameSettings.thirdPersonView == 0 || !(entity instanceof EntityPlayer)) continue;
            this.entities.add(entity);
        }
    }

    static {
        renderNameTags = true;
        renderGlint = true;
        framebufferList = new ArrayList<Framebuffer>();
    }

    public static enum cmode {
        Sync,
        Random,
        Custom;

    }
}

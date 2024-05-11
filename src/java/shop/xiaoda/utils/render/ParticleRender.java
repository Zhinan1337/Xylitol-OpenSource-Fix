//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import shop.xiaoda.utils.render.animation.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import shop.xiaoda.module.modules.render.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ParticleRender
{
    public static final List<Particle> particles;
    public static int rendered;
    public static final AnimTimeUtil timer;
    private static boolean sentParticles;
    
    public static void render(final float x, final float y, final EntityLivingBase target) {
        for (final Particle p : ParticleRender.particles) {
            p.x = x + 20.0f;
            p.y = y + 20.0f;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (p.opacity > 4.0f) {
                p.render2D();
            }
        }
        if (ParticleRender.timer.hasTimeElapsed(16L, true)) {
            for (final Particle p : ParticleRender.particles) {
                p.updatePosition();
                if (p.opacity < 1.0f) {
                    ParticleRender.particles.remove(p);
                }
            }
        }
        if (target.hurtTime == 9 && !ParticleRender.sentParticles) {
            for (int i = 0; i <= 15; ++i) {
                final Particle particle = new Particle();
                particle.init(x + 20.0f, y + 20.0f, (float)((Math.random() - 0.5) * 2.0 * 1.4), (float)((Math.random() - 0.5) * 2.0 * 1.4), (float)(Math.random() * 4.0), new Color(HUD.mainColor.getColor()));
                ParticleRender.particles.add(particle);
            }
            ParticleRender.sentParticles = true;
        }
        if (target.hurtTime == 8) {
            ParticleRender.sentParticles = false;
        }
    }
    
    public static void add(final Particle particle) {
        ParticleRender.particles.add(particle);
    }
    
    static {
        particles = new ArrayList<Particle>();
        timer = new AnimTimeUtil();
    }
}

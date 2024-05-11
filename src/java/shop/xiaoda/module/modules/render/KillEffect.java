// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.render;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.attack.EventAttack;
import shop.xiaoda.event.world.EventUpdate;
import shop.xiaoda.event.world.EventWorldLoad;
import shop.xiaoda.gui.notification.NotificationManager;
import shop.xiaoda.gui.notification.NotificationType;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.BoolValue;
import shop.xiaoda.module.values.ModeValue;
import shop.xiaoda.utils.render.animation.impl.ContinualAnimation;

public final class KillEffect
        extends Module {
    private final ModeValue<killEffectRenderModes> killEffectValue = new ModeValue("KillEffect", (Enum[])killEffectRenderModes.values(), (Enum)killEffectRenderModes.Squid);
    private final ModeValue<killEffectSoundModes> killSoundValue = new ModeValue("KillSound", (Enum[])killEffectSoundModes.values(), (Enum)killEffectSoundModes.Squid);
    private final BoolValue tipsKillsValue = new BoolValue("TipsKills", false);
    private int kills = 0;
    private EntityLivingBase target;
    private EntitySquid squid;
    private double percent = 0.0;
    private final ContinualAnimation anim = new ContinualAnimation();

    public KillEffect() {
        super("KillEffect", Category.Render);
    }

    public double easeInOutCirc(double x) {
        return x < 0.5 ? (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * x, 2.0))) / 2.0 : (Math.sqrt(1.0 - Math.pow(-2.0 * x + 2.0, 2.0)) + 1.0) / 2.0;
    }

    @Override
    public void onDisable() {
        if (((Boolean)this.tipsKillsValue.getValue()).booleanValue()) {
            this.kills = 0;
        }
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.tipsKillsValue.getValue()).booleanValue()) {
            this.kills = 0;
        }
    }

    @EventTarget
    public void onWorld(EventWorldLoad event) {
        if (((Boolean)this.tipsKillsValue.getValue()).booleanValue()) {
            this.kills = 0;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.killEffectValue.getValue() == killEffectRenderModes.Squid && this.squid != null) {
            if (KillEffect.mc.theWorld.loadedEntityList.contains(this.squid)) {
                if (this.percent < 1.0) {
                    this.percent += Math.random() * 0.048;
                }
                if (this.percent >= 1.0) {
                    this.percent = 0.0;
                    for (int i = 0; i <= 8; ++i) {
                        KillEffect.mc.effectRenderer.emitParticleAtEntity(this.squid, EnumParticleTypes.FLAME);
                    }
                    KillEffect.mc.theWorld.removeEntity(this.squid);
                    this.squid = null;
                    return;
                }
            } else {
                this.percent = 0.0;
            }
            double easeInOutCirc = this.easeInOutCirc(1.0 - this.percent);
            this.anim.animate((float)easeInOutCirc, 450);
            this.squid.setPositionAndUpdate(this.squid.posX, this.squid.posY + (double)this.anim.getOutput() * 0.9, this.squid.posZ);
        }
        if (this.squid != null && this.killEffectValue.getValue() == killEffectRenderModes.Squid) {
            this.squid.squidPitch = 0.0f;
            this.squid.prevSquidPitch = 0.0f;
            this.squid.squidYaw = 0.0f;
            this.squid.squidRotation = 90.0f;
        }
        if (this.target != null && this.target.getHealth() <= 0.0f && !KillEffect.mc.theWorld.loadedEntityList.contains(this.target)) {
            if (((Boolean)this.tipsKillsValue.getValue()).booleanValue()) {
                ++this.kills;
                NotificationManager.post(NotificationType.SUCCESS, "Kills +1", "Killed " + this.kills + " Players.  ");
            }
            if (this.killSoundValue.getValue() == killEffectSoundModes.Squid) {
                this.playSound(SoundType.KILL, 0.6f);
            }
            if (this.killEffectValue.getValue() == killEffectRenderModes.LightningBolt) {
                EntityLightningBolt entityLightningBolt = new EntityLightningBolt(KillEffect.mc.theWorld, this.target.posX, this.target.posY, this.target.posZ);
                KillEffect.mc.theWorld.addEntityToWorld((int)(-Math.random() * 100000.0), entityLightningBolt);
                KillEffect.mc.theWorld.playSound(KillEffect.mc.thePlayer.posX, KillEffect.mc.thePlayer.posY, KillEffect.mc.thePlayer.posZ, "ambient.weather.thunder", 1.0f, 1.0f, false);
                KillEffect.mc.theWorld.playSound(KillEffect.mc.thePlayer.posX, KillEffect.mc.thePlayer.posY, KillEffect.mc.thePlayer.posZ, "random.explode", 1.0f, 1.0f, false);
                for (int i = 0; i <= 8; ++i) {
                    KillEffect.mc.effectRenderer.emitParticleAtEntity(this.target, EnumParticleTypes.FLAME);
                }
                KillEffect.mc.theWorld.playSound(KillEffect.mc.thePlayer.posX, KillEffect.mc.thePlayer.posY, KillEffect.mc.thePlayer.posZ, "item.fireCharge.use", 1.0f, 1.0f, false);
            }
            if (this.killEffectValue.getValue() == killEffectRenderModes.Squid) {
                this.squid = new EntitySquid(KillEffect.mc.theWorld);
                KillEffect.mc.theWorld.addEntityToWorld(-8, this.squid);
                this.squid.setPosition(this.target.posX, this.target.posY, this.target.posZ);
            }
            this.target = null;
        }
        if (this.target != null && !this.target.isDead) {
            switch ((killEffectRenderModes)((Object)this.killEffectValue.getValue())) {
                case Flame: {
                    KillEffect.mc.effectRenderer.emitParticleAtEntity(this.target, EnumParticleTypes.FLAME);
                    this.target = null;
                    break;
                }
                case Smoke: {
                    KillEffect.mc.effectRenderer.emitParticleAtEntity(this.target, EnumParticleTypes.SMOKE_LARGE);
                    this.target = null;
                    break;
                }
                case Water: {
                    KillEffect.mc.effectRenderer.emitParticleAtEntity(this.target, EnumParticleTypes.WATER_DROP);
                    this.target = null;
                    break;
                }
                case Love: {
                    KillEffect.mc.effectRenderer.emitParticleAtEntity(this.target, EnumParticleTypes.HEART);
                    KillEffect.mc.effectRenderer.emitParticleAtEntity(this.target, EnumParticleTypes.WATER_DROP);
                    this.target = null;
                    break;
                }
                case Blood: {
                    for (int i = 0; i < 10; ++i) {
                        KillEffect.mc.effectRenderer.spawnEffectParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), this.target.posX, this.target.posY + (double)(this.target.height / 2.0f), this.target.posZ, this.target.motionX + (double)KillEffect.nextFloat(-0.5f, 0.5f), this.target.motionY + (double)KillEffect.nextFloat(-0.5f, 0.5f), this.target.motionZ + (double)KillEffect.nextFloat(-0.5f, 0.5f), Block.getStateId(Blocks.redstone_block.getDefaultState()));
                    }
                    this.target = null;
                }
            }
        }
    }

    public void playSound(SoundType st, float volume) {
        new Thread(() -> {
            try {
                AudioInputStream as = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/minecraft/express/sound/" + st.getName()))));
                Clip clip = AudioSystem.getClip();
                clip.open(as);
                clip.start();
                FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.start();
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f) {
            return startInclusive;
        }
        return (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random());
    }

    @EventTarget
    public void onAttack(EventAttack event) {
        if (event.getTarget() != null) {
            this.target = (EntityLivingBase)event.getTarget();
        }
    }

    public static enum killEffectSoundModes {
        Squid,
        Off;

    }

    public static enum killEffectRenderModes {
        LightningBolt,
        Flame,
        Smoke,
        Water,
        Love,
        Blood,
        Squid,
        Off;

    }

    public static enum SoundType {
        KILL("kill.wav");

        final String music;

        private SoundType(String fileName) {
            this.music = fileName;
        }

        String getName() {
            return this.music;
        }
    }
}

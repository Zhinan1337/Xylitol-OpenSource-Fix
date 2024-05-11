//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import org.lwjgl.compatibility.util.vector.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.event.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.module.modules.misc.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import java.util.*;
import java.util.stream.*;

public class BowAimbot extends Module
{
    public static EntityLivingBase target;
    public static NumberValue fov;
    public static NumberValue range;
    public static ModeValue<PRIORITY> priority;
    
    public BowAimbot() {
        super("BowAimbot", Category.Combat);
    }
    
    @EventTarget
    public void onUpdatePre(final EventMotion event) {
        if (event.isPost()) {
            return;
        }
        if (BowAimbot.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.bow || !BowAimbot.mc.thePlayer.isUsingItem()) {
            BowAimbot.target = null;
            return;
        }
        BowAimbot.target = this.getTarget();
        if (BowAimbot.target == null) {
            return;
        }
        final float[] rotation = this.getPlayerRotations((Entity)BowAimbot.target);
        RotationComponent.setRotations(new Vector2f(rotation[0], rotation[1]), 10.0, MovementFix.NORMAL, true);
    }
    
    public void onDisable() {
        BowAimbot.target = null;
        super.onDisable();
    }
    
    private float[] getPlayerRotations(final Entity entity) {
        final double distanceToEnt = BowAimbot.mc.thePlayer.getDistanceToEntity(entity);
        final double predictX = entity.posX + (entity.posX - entity.lastTickPosX) * (distanceToEnt * 0.8);
        final double predictZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) * (distanceToEnt * 0.8);
        final double x = predictX - BowAimbot.mc.thePlayer.posX;
        final double z = predictZ - BowAimbot.mc.thePlayer.posZ;
        final double h = entity.posY + 1.0 - (BowAimbot.mc.thePlayer.posY + BowAimbot.mc.thePlayer.getEyeHeight());
        final double h2 = Math.sqrt(x * x + z * z);
        final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = -RotationUtil.getTrajAngleSolutionLow((float)h2, (float)h, 1.0f);
        return new float[] { yaw, pitch };
    }
    
    public static boolean isFovInRange(final Entity entity, float fov) {
        fov *= 0.5;
        final double v = ((BowAimbot.mc.thePlayer.rotationYaw - KillAura.getPlayerRotation(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (v > 0.0 && v < fov) || (-fov < v && v < 0.0);
    }
    
    private EntityLivingBase getTarget() {
        Stream<EntityPlayer> stream = (Stream<EntityPlayer>)BowAimbot.mc.theWorld.playerEntities.stream().filter(e -> !Teams.isSameTeam(e)).filter(e -> !AntiBot.isServerBot(e)).filter(BowAimbot.mc.thePlayer::canEntityBeSeen).filter(e -> isFovInRange(e, BowAimbot.fov.getValue().floatValue()));
        if (BowAimbot.priority.getValue().name().equals("Range")) {
            stream = stream.sorted(Comparator.comparingDouble(e -> e.getDistanceToEntity((Entity)BowAimbot.mc.thePlayer)));
        }
        else if (BowAimbot.priority.getValue().name().equals("Angle")) {
            stream = stream.sorted(Comparator.comparingDouble((ToDoubleFunction<? super EntityPlayer>)RotationUtil::getBowRot));
        }
        final List<EntityPlayer> list = stream.collect(Collectors.toList());
        if (list.size() <= 0) {
            return null;
        }
        return (EntityLivingBase)list.get(0);
    }
    
    static {
        BowAimbot.fov = new NumberValue("FoV", 180.0, 10.0, 360.0, 10.0);
        BowAimbot.range = new NumberValue("Range", 100.0, 1.0, 200.0, 10.0);
        BowAimbot.priority = new ModeValue<PRIORITY>("Priority", PRIORITY.values(), PRIORITY.Angle);
    }
    
    private enum PRIORITY
    {
        Angle, 
        Range;
    }
}

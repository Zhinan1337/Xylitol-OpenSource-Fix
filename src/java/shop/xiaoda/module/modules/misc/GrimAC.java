// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.misc;

import java.text.DecimalFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.EnumChatFormatting;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.world.EventPacketReceive;
import shop.xiaoda.event.world.EventWorldLoad;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.misc.Teams;
import shop.xiaoda.module.values.BoolValue;
import shop.xiaoda.utils.DebugUtil;

public class GrimAC
        extends Module {
    public BoolValue reachValue = new BoolValue("Reach", true);
    public BoolValue noslowAValue = new BoolValue("NoSlowA", true);
    public static final DecimalFormat DF_1 = new DecimalFormat("0.000000");
    int vl;

    public GrimAC() {
        super("GrimAC", Category.Misc);
    }

    @Override
    public void onEnable() {
        this.vl = 0;
    }

    @EventTarget
    public void onWorld(EventWorldLoad event) {
        this.vl = 0;
    }

    @EventTarget
    public void onPacket(EventPacketReceive event) {
        if (GrimAC.mc.thePlayer.ticksExisted % 6 == 0) {
            S19PacketEntityStatus s19;
            if (event.getPacket() instanceof S19PacketEntityStatus && ((Boolean)this.reachValue.getValue()).booleanValue() && (s19 = (S19PacketEntityStatus)event.getPacket()).getOpCode() == 2) {
                new Thread(() -> this.checkCombatHurt(s19.getEntity(GrimAC.mc.theWorld))).start();
            }
            if (event.getPacket() instanceof S14PacketEntity && ((Boolean)this.noslowAValue.getValue()).booleanValue()) {
                S14PacketEntity packet = (S14PacketEntity)event.getPacket();
                Entity entity = packet.getEntity(GrimAC.mc.theWorld);
                if (!(entity instanceof EntityPlayer)) {
                    return;
                }
                new Thread(() -> this.checkPlayer((EntityPlayer)entity)).start();
            }
        }
    }

    private void checkCombatHurt(Entity entity) {
        if (!(entity instanceof EntityLivingBase)) {
            return;
        }
        Entity attacker = null;
        int attackerCount = 0;
        for (Entity worldEntity : GrimAC.mc.theWorld.getLoadedEntityList()) {
            if (!(worldEntity instanceof EntityPlayer) || ((EntityPlayer)worldEntity).getDistanceToEntity(entity) > 7.0f || ((Object)worldEntity).equals(entity)) continue;
            ++attackerCount;
            attacker = (EntityPlayer)worldEntity;
        }
        if (attacker == null || attacker.equals(entity) || Teams.isSameTeam(attacker)) {
            return;
        }
        double reach = attacker.getDistanceToEntity(entity);
        String prefix = (Object)((Object)EnumChatFormatting.GRAY) + "[" + (Object)((Object)EnumChatFormatting.AQUA) + "GrimAC" + (Object)((Object)EnumChatFormatting.GRAY) + "] " + (Object)((Object)EnumChatFormatting.RESET) + (Object)((Object)EnumChatFormatting.GRAY) + ((EntityPlayer)attacker).getName() + (Object)((Object)EnumChatFormatting.WHITE) + " failed ";
        if (reach > 3.0) {
            DebugUtil.log(prefix + (Object)((Object)EnumChatFormatting.AQUA) + "Reach" + (Object)((Object)EnumChatFormatting.WHITE) + " (vl:" + attackerCount + ".0)" + (Object)((Object)EnumChatFormatting.GRAY) + ": " + DF_1.format(reach) + " blocks");
        }
    }

    private void checkPlayer(EntityPlayer player) {
        if (player.equals(GrimAC.mc.thePlayer) || Teams.isSameTeam(player)) {
            return;
        }
        String prefix = (Object)((Object)EnumChatFormatting.GRAY) + "[" + (Object)((Object)EnumChatFormatting.AQUA) + "GrimAC" + (Object)((Object)EnumChatFormatting.GRAY) + "] " + (Object)((Object)EnumChatFormatting.RESET) + (Object)((Object)EnumChatFormatting.GRAY) + player.getName() + (Object)((Object)EnumChatFormatting.WHITE) + " failed ";
        if (player.isUsingItem() && (player.posX - player.lastTickPosX > 0.2 || player.posZ - player.lastTickPosZ > 0.2)) {
            DebugUtil.log(prefix + (Object)((Object)EnumChatFormatting.AQUA) + "NoSlowA (Prediction)" + (Object)((Object)EnumChatFormatting.WHITE) + " (vl:" + this.vl + ".0)");
            ++this.vl;
        }
        if (!GrimAC.mc.theWorld.loadedEntityList.contains(player) || !player.isEntityAlive()) {
            this.vl = 0;
        }
    }
}

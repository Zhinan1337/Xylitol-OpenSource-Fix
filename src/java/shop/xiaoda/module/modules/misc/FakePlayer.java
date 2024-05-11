//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import net.minecraft.client.entity.*;
import java.util.*;
import shop.xiaoda.module.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import shop.xiaoda.module.Module;

public class FakePlayer extends Module
{
    private EntityOtherPlayerMP fakePlayer;
    private final LinkedList<double[]> positions;
    
    public FakePlayer() {
        super("FakePlayer", Category.Misc);
        this.fakePlayer = null;
        this.positions = new LinkedList<double[]>();
    }
    
    public void onEnable() {
        if (FakePlayer.mc.thePlayer == null) {
            return;
        }
        (this.fakePlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.theWorld, FakePlayer.mc.thePlayer.getGameProfile())).clonePlayer((EntityPlayer)FakePlayer.mc.thePlayer, true);
        this.fakePlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.thePlayer);
        this.fakePlayer.rotationYawHead = FakePlayer.mc.thePlayer.rotationYawHead;
        FakePlayer.mc.theWorld.addEntityToWorld(-1337, (Entity)this.fakePlayer);
        synchronized (this.positions) {
            this.positions.add(new double[] { FakePlayer.mc.thePlayer.posX, FakePlayer.mc.thePlayer.getEntityBoundingBox().minY + FakePlayer.mc.thePlayer.getEyeHeight() / 2.0f, FakePlayer.mc.thePlayer.posZ });
            this.positions.add(new double[] { FakePlayer.mc.thePlayer.posX, FakePlayer.mc.thePlayer.getEntityBoundingBox().minY, FakePlayer.mc.thePlayer.posZ });
        }
    }
    
    public void onDisable() {
        if (FakePlayer.mc.thePlayer == null) {
            return;
        }
        if (this.fakePlayer != null) {
            FakePlayer.mc.theWorld.removeEntityFromWorld(this.fakePlayer.getEntityId());
            this.fakePlayer = null;
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.component;

import shop.xiaoda.event.world.*;
import shop.xiaoda.*;
import shop.xiaoda.event.*;

public final class FallDistanceComponent
{
    public static float distance;
    private float lastDistance;
    
    @EventTarget
    private void onMotion(final EventMotion event) {
        if (event.isPre()) {
            final float fallDistance = Client.mc.thePlayer.fallDistance;
            if (fallDistance == 0.0f) {
                FallDistanceComponent.distance = 0.0f;
            }
            FallDistanceComponent.distance += fallDistance - this.lastDistance;
            this.lastDistance = fallDistance;
        }
    }
}

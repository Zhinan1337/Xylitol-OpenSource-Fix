//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.callables.*;
import net.minecraft.network.*;

public class EventPacketReceive extends EventCancellable
{
    private Packet<?> packet;
    private final EnumPacketDirection direction;
    private final INetHandler netHandler;
    
    public EventPacketReceive(final Packet<?> packet, final EnumPacketDirection direction, final INetHandler netHandler) {
        this.packet = packet;
        this.direction = direction;
        this.netHandler = netHandler;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
    
    public EnumPacketDirection getDirection() {
        return this.direction;
    }
    
    public INetHandler getNetHandler() {
        return this.netHandler;
    }
    
    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.misc;

import shop.xiaoda.event.api.events.callables.*;

public class EventChat extends EventCancellable
{
    private String message;
    
    public EventChat(final String message) {
        this.message = message;
        this.setType((byte)0);
    }
    
    private void setType(final byte b) {
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}

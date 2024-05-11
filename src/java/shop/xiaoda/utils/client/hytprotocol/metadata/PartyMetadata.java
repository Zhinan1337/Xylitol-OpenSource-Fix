//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.hytprotocol.metadata;

public class PartyMetadata
{
    private final String name;
    private final String acceptId;
    private final String denyId;
    
    public PartyMetadata(final String name, final String acceptId, final String denyId) {
        this.name = name;
        this.acceptId = acceptId;
        this.denyId = denyId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getAcceptId() {
        return this.acceptId;
    }
    
    public String getDenyId() {
        return this.denyId;
    }
}

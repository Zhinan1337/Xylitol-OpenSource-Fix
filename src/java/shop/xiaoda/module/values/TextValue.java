//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.values;

public class TextValue extends Value<String>
{
    protected static TextValue self;
    public String selectedString;
    public static String DEFAULT_STRING;
    
    public TextValue(final String name, final String description, final String value) {
        super(name);
        this.selectedString = "";
    }
    
    public TextValue(final String name, final String description, final String value, final Dependency dependenc) {
        super(name, dependenc);
        this.selectedString = "";
    }
    
    public static TextValue create(final String name) {
        return TextValue.self = new TextValue(name, TextValue.DEFAULT_STRING, TextValue.DEFAULT_STRING);
    }
    
    public TextValue withDescription(final String description) {
        return TextValue.self;
    }
    
    public TextValue defaultTo(final String value) {
        TextValue.DEFAULT_STRING = value;
        return TextValue.self;
    }
    
    @Override
    public String getConfigValue() {
        return this.selectedString;
    }
    
    public String getSelectedString() {
        return this.selectedString;
    }
    
    public void setSelectedString(final String selectedString) {
        this.selectedString = selectedString;
    }
}

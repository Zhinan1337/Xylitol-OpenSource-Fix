//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.values;

public class NumberValue extends Value<Double>
{
    public double animatedPercentage;
    public boolean sliding;
    double max;
    double min;
    double inc;
    
    public NumberValue(final String name, final double val, final double min, final double max, final double inc) {
        super(name);
        this.setValue(val);
        this.max = max;
        this.min = min;
        this.inc = inc;
    }
    
    public NumberValue(final String name, final double val, final double min, final double max, final double inc, final Dependency dependenc) {
        super(name, dependenc);
        this.setValue(val);
        this.max = max;
        this.min = min;
        this.inc = inc;
    }
    
    public Double getMax() {
        return this.max;
    }
    
    public Double getMin() {
        return this.min;
    }
    
    public Double getInc() {
        return this.inc;
    }
    
    @Override
    public Double getConfigValue() {
        return this.getValue();
    }
}

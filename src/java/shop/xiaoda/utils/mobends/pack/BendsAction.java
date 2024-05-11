//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.pack;

import shop.xiaoda.utils.mobends.util.*;
import java.util.*;
import net.minecraft.util.*;

public class BendsAction
{
    public String anim;
    public String model;
    public List<Calculation> calculations;
    public EnumBoxProperty prop;
    public EnumAxis axis;
    public float smooth;
    public EnumModifier mod;
    public float visual_DeletePopUp;
    
    public BendsAction(final String argAnim, final String argModel, final EnumBoxProperty argProp, final EnumAxis argAxis, final float argSmooth, final float argNumber) {
        this.calculations = new ArrayList<Calculation>();
        this.anim = argAnim;
        this.model = argModel;
        this.prop = argProp;
        this.axis = argAxis;
        this.smooth = argSmooth;
        this.visual_DeletePopUp = 0.0f;
    }
    
    public BendsAction() {
        this.calculations = new ArrayList<Calculation>();
    }
    
    public BendsAction setModifier(final EnumModifier argMod) {
        this.mod = argMod;
        return this;
    }
    
    public float getNumber(final float in) {
        return Calculation.calculateAll(this.mod, in, this.calculations);
    }
    
    public static EnumOperator getOperatorFromSymbol(final String symbol) {
        return symbol.equalsIgnoreCase("+=") ? EnumOperator.ADD : (symbol.equalsIgnoreCase("-=") ? EnumOperator.SUBSTRACT : (symbol.equalsIgnoreCase("==") ? EnumOperator.SET : (symbol.equalsIgnoreCase("*=") ? EnumOperator.MULTIPLY : EnumOperator.DIVIDE)));
    }
    
    public enum EnumOperator
    {
        SET, 
        ADD, 
        MULTIPLY, 
        DIVIDE, 
        SUBSTRACT;
    }
    
    public enum EnumBoxProperty
    {
        ROT, 
        SCALE, 
        PREROT;
    }
    
    public enum EnumModifier
    {
        COS, 
        SIN;
    }
    
    public static class Calculation
    {
        public EnumOperator operator;
        public float number;
        public String globalVar;
        
        public Calculation(final EnumOperator argOperator, final float argNumber) {
            this.globalVar = null;
            this.operator = argOperator;
            this.number = argNumber;
        }
        
        public Calculation setGlobalVar(final String argGlobalVar) {
            this.globalVar = argGlobalVar;
            return this;
        }
        
        public float calculate(final float in) {
            final float num = (this.globalVar != null) ? BendsVar.getGlobalVar(this.globalVar) : this.number;
            float out = 0.0f;
            if (this.operator == EnumOperator.ADD) {
                out = in + num;
            }
            if (this.operator == EnumOperator.SET) {
                out = num;
            }
            if (this.operator == EnumOperator.SUBSTRACT) {
                out = in - num;
            }
            if (this.operator == EnumOperator.MULTIPLY) {
                out = in * num;
            }
            if (this.operator == EnumOperator.DIVIDE) {
                out = in / num;
            }
            return out;
        }
        
        public static float calculateAll(final EnumModifier mod, final float in, final List<Calculation> argCalc) {
            float out = in;
            for (int i = 0; i < argCalc.size(); ++i) {
                out = argCalc.get(i).calculate(out);
            }
            if (mod == EnumModifier.COS) {
                out = MathHelper.cos(out);
            }
            if (mod == EnumModifier.SIN) {
                out = MathHelper.sin(out);
            }
            return out;
        }
    }
}

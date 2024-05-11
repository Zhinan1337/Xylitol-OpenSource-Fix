//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;

public class MotionBlur extends Module
{
    public final NumberValue blurAmount;
    
    public MotionBlur() {
        super("MotionBlur", Category.Render);
        this.blurAmount = new NumberValue("Amount", 7.0, 0.0, 10.0, 0.1);
    }
}

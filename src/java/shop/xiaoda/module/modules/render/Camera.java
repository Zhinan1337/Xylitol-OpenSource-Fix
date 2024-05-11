//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;

public final class Camera extends Module
{
    public final BoolValue cameraClipValue;
    public final BoolValue noHurtCameraValue;
    public final BoolValue betterBobbingValue;
    public final BoolValue noFovValue;
    public final NumberValue fovValue;
    
    public Camera() {
        super("Camera", Category.Render);
        this.cameraClipValue = new BoolValue("CameraClip", false);
        this.noHurtCameraValue = new BoolValue("NoHurtCamera", false);
        this.betterBobbingValue = new BoolValue("BetterBobbing", false);
        this.noFovValue = new BoolValue("NoFov", false);
        this.fovValue = new NumberValue("Fov", 1.0, 0.0, 4.0, 0.1);
    }
}

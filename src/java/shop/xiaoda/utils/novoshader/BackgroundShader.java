//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.novoshader;

import org.lwjgl.compatibility.display.Display;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.utils.render.*;

public final class BackgroundShader extends Shader
{
    public static final BackgroundShader BACKGROUND_SHADER;
    private float time;
    
    public BackgroundShader() {
        super("background.frag");
    }
    
    @Override
    public void setupUniforms() {
        this.setupUniform("iResolution");
        this.setupUniform("iTime");
    }
    
    @Override
    public void updateUniforms() {
        final int resolutionID = this.getUniform("iResolution");
        if (resolutionID > -1) {
            GL20.glUniform2f(resolutionID, (float) Display.getWidth(), (float)Display.getHeight());
        }
        final int timeID = this.getUniform("iTime");
        if (timeID > -1) {
            GL20.glUniform1f(timeID, this.time);
        }
        this.time += 0.003f * RenderUtil.deltaTime;
    }
    
    static {
        BACKGROUND_SHADER = new BackgroundShader();
    }
}

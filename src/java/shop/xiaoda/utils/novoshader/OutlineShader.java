//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.novoshader;

import org.lwjgl.opengl.*;
import shop.xiaoda.*;

public final class OutlineShader extends FramebufferShader
{
    public static final OutlineShader OUTLINE_SHADER;
    
    public OutlineShader() {
        super("outline.frag");
    }
    
    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("color");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
    }
    
    public void updateUniforms() {
        GL20.glUniform1i(this.getUniform("texture"), 0);
        GL20.glUniform2f(this.getUniform("texelSize"), 1.0f / Client.mc.displayWidth * (this.radius * this.quality), 1.0f / Client.mc.displayHeight * (this.radius * this.quality));
        GL20.glUniform4f(this.getUniform("color"), this.red, this.green, this.blue, this.alpha);
        GL20.glUniform1f(this.getUniform("radius"), this.radius);
    }
    
    static {
        OUTLINE_SHADER = new OutlineShader();
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.menu.utils;

import org.lwjgl.opengl.*;
import java.io.*;
import java.nio.charset.*;

public class MainMenuBackground
{
    private final int programId;
    private final int timeUniform;
    private final int mouseUniform;
    private final int resolutionUniform;
    
    public MainMenuBackground(final String fragmentShaderLocation) throws IOException {
        final int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, this.createShader(MainMenuBackground.class.getResourceAsStream("/assets/minecraft/express/shader/passthrough.vsh"), 35633));
        GL20.glAttachShader(program, this.createShader(MainMenuBackground.class.getResourceAsStream(fragmentShaderLocation), 35632));
        GL20.glLinkProgram(program);
        final int linked = GL20.glGetProgrami(program, 35714);
        if (linked == 0) {
            System.err.println(GL20.glGetProgramInfoLog(program, GL20.glGetProgrami(program, 35716)));
            throw new IllegalStateException("Shader failed to link");
        }
        GL20.glUseProgram(this.programId = program);
        this.timeUniform = GL20.glGetUniformLocation(program, (CharSequence)"time");
        this.mouseUniform = GL20.glGetUniformLocation(program, (CharSequence)"mouse");
        this.resolutionUniform = GL20.glGetUniformLocation(program, (CharSequence)"resolution");
        GL20.glUseProgram(0);
    }
    
    public void useShader(final int width, final int height, final float time) {
        GL20.glUseProgram(this.programId);
        GL20.glUniform2f(this.resolutionUniform, (float)width, (float)height);
        GL20.glUniform2f(this.mouseUniform, (float)width, 1.0f - height);
        GL20.glUniform1f(this.timeUniform, time);
    }
    
    private int createShader(final InputStream inputStream, final int shaderType) throws IOException {
        final int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, (CharSequence)this.readStreamToString(inputStream));
        GL20.glCompileShader(shader);
        final int compiled = GL20.glGetShaderi(shader, 35713);
        if (compiled == 0) {
            System.err.println(GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, 35716)));
            throw new IllegalStateException("Failed to compile shader");
        }
        return shader;
    }
    
    private String readStreamToString(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final byte[] buffer = new byte[512];
        int read;
        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, read);
        }
        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }
}

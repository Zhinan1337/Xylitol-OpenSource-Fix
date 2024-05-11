//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.fontRender;

import java.awt.*;
import shop.xiaoda.*;
import java.io.*;

public class FontManager
{
    private static final String locate = "express/font/";
    public static RapeMasterFontManager arial12;
    public static RapeMasterFontManager arial14;
    public static RapeMasterFontManager arial16;
    public static RapeMasterFontManager arial18;
    public static RapeMasterFontManager arial20;
    public static RapeMasterFontManager arial22;
    public static RapeMasterFontManager arial24;
    public static RapeMasterFontManager arial26;
    public static RapeMasterFontManager thin40;
    public static RapeMasterFontManager thin16;
    public static RapeMasterFontManager arial28;
    public static RapeMasterFontManager arial32;
    public static RapeMasterFontManager arial40;
    public static RapeMasterFontManager arial42;
    public static RapeMasterFontManager splash40;
    public static RapeMasterFontManager splash18;
    public static RapeMasterFontManager Tahoma12;
    public static RapeMasterFontManager Tahoma14;
    public static RapeMasterFontManager Tahoma16;
    public static RapeMasterFontManager Tahoma18;
    public static RapeMasterFontManager Tahoma20;
    public static RapeMasterFontManager Tahoma22;
    public static RapeMasterFontManager Tahoma24;
    public static RapeMasterFontManager Tahoma26;
    public static RapeMasterFontManager Tahoma28;
    public static RapeMasterFontManager Tahoma32;
    public static RapeMasterFontManager Tahoma40;
    public static RapeMasterFontManager Tahoma42;
    public static RapeMasterFontManager icontestFont35;
    public static RapeMasterFontManager icontestFont90;
    public static RapeMasterFontManager icontestFont80;
    public static RapeMasterFontManager icontestFont20;
    public static RapeMasterFontManager icontestFont40;
    public static RapeMasterFontManager lettuceFont20;
    public static RapeMasterFontManager lettuceFont24;
    public static RapeMasterFontManager lettuceBoldFont26;
    public static RapeMasterFontManager infoFontBold;
    public static RapeMasterFontManager titleFontBold;
    public static RapeMasterFontManager bold22;
    public static RapeMasterFontManager infoFont;
    public static RapeMasterFontManager titleFont;
    
    public static void init() {
        FontManager.thin40 = new RapeMasterFontManager(getFont("sfthin.ttf", 40.0f));
        FontManager.thin16 = new RapeMasterFontManager(getFont("sfthin.ttf", 16.0f));
        FontManager.arial12 = new RapeMasterFontManager(getFont("font.ttf", 12.0f));
        FontManager.arial14 = new RapeMasterFontManager(getFont("font.ttf", 14.0f));
        FontManager.arial16 = new RapeMasterFontManager(getFont("font.ttf", 16.0f));
        FontManager.arial18 = new RapeMasterFontManager(getFont("font.ttf", 18.0f));
        FontManager.arial20 = new RapeMasterFontManager(getFont("font.ttf", 20.0f));
        FontManager.arial22 = new RapeMasterFontManager(getFont("font.ttf", 22.0f));
        FontManager.arial24 = new RapeMasterFontManager(getFont("font.ttf", 24.0f));
        FontManager.arial26 = new RapeMasterFontManager(getFont("font.ttf", 26.0f));
        FontManager.arial28 = new RapeMasterFontManager(getFont("font.ttf", 28.0f));
        FontManager.arial32 = new RapeMasterFontManager(getFont("font.ttf", 32.0f));
        FontManager.arial40 = new RapeMasterFontManager(getFont("font.ttf", 40.0f));
        FontManager.arial42 = new RapeMasterFontManager(getFont("font.ttf", 42.0f));
        FontManager.splash40 = new RapeMasterFontManager(getFont("font.ttf", 40.0f));
        FontManager.splash18 = new RapeMasterFontManager(getFont("font.ttf", 18.0f));
        FontManager.Tahoma12 = new RapeMasterFontManager(getFont("font.ttf", 12.0f));
        FontManager.Tahoma14 = new RapeMasterFontManager(getFont("font.ttf", 14.0f));
        FontManager.Tahoma16 = new RapeMasterFontManager(getFont("font.ttf", 16.0f));
        FontManager.Tahoma18 = new RapeMasterFontManager(getFont("font.ttf", 18.0f));
        FontManager.Tahoma20 = new RapeMasterFontManager(getFont("font.ttf", 20.0f));
        FontManager.Tahoma22 = new RapeMasterFontManager(getFont("font.ttf", 22.0f));
        FontManager.Tahoma24 = new RapeMasterFontManager(getFont("font.ttf", 24.0f));
        FontManager.Tahoma26 = new RapeMasterFontManager(getFont("font.ttf", 26.0f));
        FontManager.Tahoma28 = new RapeMasterFontManager(getFont("font.ttf", 28.0f));
        FontManager.Tahoma32 = new RapeMasterFontManager(getFont("font.ttf", 32.0f));
        FontManager.Tahoma40 = new RapeMasterFontManager(getFont("font.ttf", 40.0f));
        FontManager.Tahoma42 = new RapeMasterFontManager(getFont("font.ttf", 42.0f));
        FontManager.bold22 = new RapeMasterFontManager(getFont("bold.ttf", 18.0f));
        FontManager.titleFontBold = new RapeMasterFontManager(getFont("tahoma.ttf", 18.0f));
        FontManager.infoFontBold = new RapeMasterFontManager(getFont("tahoma.ttf", 15.0f));
        FontManager.titleFont = new RapeMasterFontManager(getFont("tahoma.ttf", 19.0f));
        FontManager.infoFont = new RapeMasterFontManager(getFont("tahoma.ttf", 12.0f));
        FontManager.icontestFont90 = new RapeMasterFontManager(getFont("icont.ttf", 90.0f));
        FontManager.icontestFont80 = new RapeMasterFontManager(getFont("icont.ttf", 80.0f));
        FontManager.icontestFont35 = new RapeMasterFontManager(getFont("icont.ttf", 35.0f));
        FontManager.icontestFont20 = new RapeMasterFontManager(getFont("icont.ttf", 20.0f));
        FontManager.icontestFont40 = new RapeMasterFontManager(getFont("icont.ttf", 40.0f));
        FontManager.lettuceFont20 = new RapeMasterFontManager(getFont("geologica.ttf", 20.0f));
        FontManager.lettuceFont24 = new RapeMasterFontManager(getFont("geologica.ttf", 24.0f));
        FontManager.lettuceBoldFont26 = new RapeMasterFontManager(getFont("geologica-bold.ttf", 26.0f));
    }
    
    private static Font getFont(final String fontName, final float fontSize) {
        Font font = null;
        try {
            final InputStream inputStream = Client.class.getResourceAsStream("/assets/minecraft/express/font/" + fontName);
            assert inputStream != null;
            font = Font.createFont(0, inputStream);
            font = font.deriveFont(fontSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }
}

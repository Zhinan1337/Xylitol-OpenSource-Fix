//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;

public class BlockHit extends Module
{
    public static final ModeValue<swords> Sword;
    public static final NumberValue Scale;
    public static final NumberValue itemPosX;
    public static final NumberValue itemPosY;
    public static final NumberValue itemPosZ;
    public static final NumberValue itemDistance;
    public static final NumberValue blockPosX;
    public static final NumberValue blockPosY;
    public static final NumberValue blockPosZ;
    public static final NumberValue SpeedSwing;
    public static final NumberValue mcSwordPos;
    public static final BoolValue fakeBlock;
    public static final BoolValue blockEverything;
    public static final BoolValue swing;
    public static final BoolValue oldBow;
    public static final BoolValue oldRod;
    public static final BoolValue oldSwing;
    
    public BlockHit() {
        super("BlockAnimations", Category.Render);
    }
    
    static {
        Sword = new ModeValue<swords>("Style", swords.values(), swords.Normal);
        Scale = new NumberValue("Scale", 0.4, 0.0, 4.0, 0.1);
        itemPosX = new NumberValue("ItemX", 0.0, -1.0, 1.0, 0.01);
        itemPosY = new NumberValue("ItemY", 0.0, -1.0, 1.0, 0.01);
        itemPosZ = new NumberValue("ItemZ", 0.0, -1.0, 1.0, 0.01);
        itemDistance = new NumberValue("ItemDistance", 1.0, 1.0, 5.0, 0.01);
        blockPosX = new NumberValue("BlockingX", 0.0, -1.0, 1.0, 0.01);
        blockPosY = new NumberValue("BlockingY", 0.0, -1.0, 1.0, 0.01);
        blockPosZ = new NumberValue("BlockingZ", 0.0, -1.0, 1.0, 0.01);
        SpeedSwing = new NumberValue("Swing-Speed", 4.0, 0.0, 20.0, 1.0);
        mcSwordPos = new NumberValue("MCPosOffset", 0.45, 0.0, 0.5, 0.01);
        fakeBlock = new BoolValue("Fake-Block", false);
        blockEverything = new BoolValue("Block-Everything", false);
        swing = new BoolValue("FluxSwing", false);
        oldBow = new BoolValue("1.7Bow", false);
        oldRod = new BoolValue("1.7Rod", false);
        oldSwing = new BoolValue("1.7Swing", false);
    }
    
    public enum swords
    {
        Normal, 
        SlideDown1, 
        SlideDown2, 
        Slide, 
        Slide1, 
        Minecraft, 
        Remix, 
        Exhibition, 
        Avatar, 
        Swang, 
        Tap1, 
        Tap2, 
        Poke, 
        Push1, 
        Push2, 
        Up, 
        Akrien, 
        VisionFX, 
        Swong, 
        Swank, 
        SigmaOld, 
        ETB, 
        Rotate360, 
        SmoothFloat, 
        Strange, 
        Reverse, 
        Zoom, 
        Move, 
        Stab, 
        Jello, 
        Old, 
        Flux, 
        Stella, 
        Tifality, 
        OldExhibition, 
        Smooth;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import net.minecraft.util.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import shop.xiaoda.utils.render.*;
import com.google.common.collect.*;
import java.util.concurrent.*;

public class XRay extends Module
{
    public static int alpha;
    public static boolean isEnabled;
    public static List<Integer> blockIdList;
    public static List<BlockPos> blockPosList;
    private TimeUtil timer;
    private final NumberValue opacity;
    private static final BoolValue esp;
    private final BoolValue tracers;
    private final BoolValue dia;
    private final BoolValue rs;
    private final BoolValue emb;
    private final BoolValue lap;
    private final BoolValue iron;
    private final BoolValue coal;
    private final BoolValue gold;
    private static final NumberValue distance;
    private final BoolValue update;
    private final NumberValue delay;
    
    public XRay() {
        super("XRay", Category.Render);
        this.timer = new TimeUtil();
        this.opacity = new NumberValue("Opacity", 160.0, 0.0, 255.0, 5.0);
        this.tracers = new BoolValue("Tracers", true);
        this.dia = new BoolValue("Diamond", true);
        this.rs = new BoolValue("Redstone", true);
        this.emb = new BoolValue("Emerald", true);
        this.lap = new BoolValue("Lapis", true);
        this.iron = new BoolValue("Iron", true);
        this.coal = new BoolValue("Coal", true);
        this.gold = new BoolValue("Gold", true);
        this.update = new BoolValue("Chunk-Update", true);
        this.delay = new NumberValue("Delay", 10.0, 1.0, 30.0, 0.5);
    }
    
    public void onEnable() {
        this.onToggle(true);
    }
    
    public void onDisable() {
        this.onToggle(false);
        this.timer.reset();
    }
    
    private void onToggle(final boolean enabled) {
        XRay.blockPosList.clear();
        XRay.mc.renderGlobal.loadRenderers();
        XRay.isEnabled = enabled;
    }
    
    @EventTarget
    public void update(final EventTick event) {
        if (XRay.alpha != this.opacity.getValue()) {
            XRay.mc.renderGlobal.loadRenderers();
            XRay.alpha = this.opacity.getValue().intValue();
        }
        else if (this.update.getValue() && this.timer.delay((float)(1000L * this.delay.getValue().longValue()))) {
            XRay.mc.renderGlobal.loadRenderers();
            this.timer.reset();
        }
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D e) {
        if (XRay.esp.getValue()) {
            for (final BlockPos pos : XRay.blockPosList) {
                if (XRay.mc.thePlayer.getDistance((double)pos.getX(), (double)pos.getZ()) <= XRay.distance.getValue()) {
                    final Block block = XRay.mc.theWorld.getBlockState(pos).getBlock();
                    if (block == Blocks.diamond_ore && this.dia.getValue()) {
                        this.render3D(pos, 0, 255, 255);
                    }
                    else if (block == Blocks.iron_ore && this.iron.getValue()) {
                        this.render3D(pos, 225, 225, 225);
                    }
                    else if (block == Blocks.lapis_ore && this.lap.getValue()) {
                        this.render3D(pos, 0, 0, 255);
                    }
                    else if (block == Blocks.redstone_ore && this.rs.getValue()) {
                        this.render3D(pos, 255, 0, 0);
                    }
                    else if (block == Blocks.coal_ore && this.coal.getValue()) {
                        this.render3D(pos, 0, 30, 30);
                    }
                    else if (block == Blocks.emerald_ore && this.emb.getValue()) {
                        this.render3D(pos, 0, 255, 0);
                    }
                    else {
                        if (block != Blocks.gold_ore || !this.gold.getValue()) {
                            continue;
                        }
                        this.render3D(pos, 255, 255, 0);
                    }
                }
            }
        }
    }
    
    private void render3D(final BlockPos pos, final int red, final int green, final int blue) {
        if (XRay.esp.getValue()) {
            RenderUtil.drawSolidBlockESP(pos, ColorUtil.getColor(red, green, blue));
        }
        if (this.tracers.getValue()) {
            RenderUtil.drawLine(pos, ColorUtil.getColor(red, green, blue));
        }
    }
    
    public static boolean showESP() {
        return XRay.esp.getValue();
    }
    
    public static double getDistance() {
        return XRay.distance.getValue();
    }
    
    static {
        XRay.blockIdList = Lists.newArrayList(new Integer[] { 10, 11, 8, 9, 14, 15, 16, 21, 41, 42, 46, 48, 52, 56, 57, 61, 62, 73, 74, 84, 89, 103, 116, 117, 118, 120, 129, 133, 137, 145, 152, 153, 154 });
        XRay.blockPosList = new CopyOnWriteArrayList<BlockPos>();
        esp = new BoolValue("ESP", true);
        distance = new NumberValue("Distance", 42.0, 16.0, 256.0, 4.0);
    }
}

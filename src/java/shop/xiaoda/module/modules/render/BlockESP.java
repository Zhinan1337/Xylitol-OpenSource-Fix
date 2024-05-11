//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.util.*;
import shop.xiaoda.module.*;
import java.awt.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.render.*;
import net.minecraft.block.*;
import shop.xiaoda.utils.player.*;
import java.util.*;
import java.util.List;

public class BlockESP extends Module
{
    private final ModeValue<renderMode> modeValue;
    private final NumberValue blockValue;
    private final NumberValue radiusValue;
    public ColorValue renderColor;
    private final BoolValue colorRainbow;
    private final TimeUtil searchTimer;
    private final List<BlockPos> posList;
    private Thread thread;
    
    public BlockESP() {
        super("BlockESP", Category.Render);
        this.modeValue = new ModeValue<renderMode>("Mode", renderMode.values(), renderMode.Box);
        this.blockValue = new NumberValue("BlockID", 26.0, 1.0, 168.0, 1.0);
        this.radiusValue = new NumberValue("Radius", 40.0, 5.0, 120.0, 1.0);
        this.renderColor = new ColorValue("RenderColor", Color.WHITE.getRGB());
        this.colorRainbow = new BoolValue("Rainbow", false);
        this.searchTimer = new TimeUtil();
        this.posList = new ArrayList<BlockPos>();
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        shop/xiaoda/module/modules/render/BlockESP.searchTimer:Lshop/xiaoda/utils/client/TimeUtil;
        //     4: ldc             1000.0
        //     6: invokevirtual   shop/xiaoda/utils/client/TimeUtil.delay:(F)Z
        //     9: ifeq            96
        //    12: aload_0         /* this */
        //    13: getfield        shop/xiaoda/module/modules/render/BlockESP.thread:Ljava/lang/Thread;
        //    16: ifnull          29
        //    19: aload_0         /* this */
        //    20: getfield        shop/xiaoda/module/modules/render/BlockESP.thread:Ljava/lang/Thread;
        //    23: invokevirtual   java/lang/Thread.isAlive:()Z
        //    26: ifne            96
        //    29: aload_0         /* this */
        //    30: getfield        shop/xiaoda/module/modules/render/BlockESP.radiusValue:Lshop/xiaoda/module/values/NumberValue;
        //    33: invokevirtual   shop/xiaoda/module/values/NumberValue.getValue:()Ljava/lang/Object;
        //    36: checkcast       Ljava/lang/Double;
        //    39: invokevirtual   java/lang/Double.intValue:()I
        //    42: istore_2        /* radius */
        //    43: aload_0         /* this */
        //    44: getfield        shop/xiaoda/module/modules/render/BlockESP.blockValue:Lshop/xiaoda/module/values/NumberValue;
        //    47: invokevirtual   shop/xiaoda/module/values/NumberValue.getValue:()Ljava/lang/Object;
        //    50: checkcast       Ljava/lang/Double;
        //    53: invokevirtual   java/lang/Double.intValue:()I
        //    56: invokestatic    net/minecraft/block/Block.getBlockById:(I)Lnet/minecraft/block/Block;
        //    59: astore_3        /* selectedBlock */
        //    60: aload_3         /* selectedBlock */
        //    61: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
        //    64: if_acmpne       68
        //    67: return         
        //    68: aload_0         /* this */
        //    69: new             Ljava/lang/Thread;
        //    72: dup            
        //    73: aload_0         /* this */
        //    74: iload_2         /* radius */
        //    75: aload_3         /* selectedBlock */
        //    76: invokedynamic   BootstrapMethod #0, run:(Lshop/xiaoda/module/modules/render/BlockESP;ILnet/minecraft/block/Block;)Ljava/lang/Runnable;
        //    81: ldc             "BlockESP-BlockFinder"
        //    83: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;Ljava/lang/String;)V
        //    86: putfield        shop/xiaoda/module/modules/render/BlockESP.thread:Ljava/lang/Thread;
        //    89: aload_0         /* this */
        //    90: getfield        shop/xiaoda/module/modules/render/BlockESP.thread:Ljava/lang/Thread;
        //    93: invokevirtual   java/lang/Thread.start:()V
        //    96: return         
        //    StackMapTable: 00 03 1D FD 00 26 01 07 00 83 F9 00 1B
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.base/java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        synchronized (this.posList) {
            final Color color = this.colorRainbow.getValue() ? ColorUtil.rainbow() : RenderUtil.getColor(this.renderColor.getValue());
            for (final BlockPos blockPos : this.posList) {
                switch (this.modeValue.getValue()) {
                    case Box: {
                        RenderUtil.drawBlockBox(blockPos, color, false);
                        continue;
                    }
                    case TwoD: {
                        RenderUtil.draw2D(blockPos, color.getRGB(), Color.BLACK.getRGB());
                        continue;
                    }
                    case Outline: {
                        RenderUtil.drawBlockBox(blockPos, color, false);
                        RenderUtil.renderOne();
                        RenderUtil.drawBlockBox(blockPos, color, false);
                        RenderUtil.renderTwo();
                        RenderUtil.drawBlockBox(blockPos, color, false);
                        RenderUtil.renderThree();
                        RenderUtil.renderFour(color.getRGB());
                        RenderUtil.drawBlockBox(blockPos, color, true);
                        RenderUtil.renderFive();
                        continue;
                    }
                }
            }
        }
    }
    
    public enum renderMode
    {
        Box, 
        TwoD, 
        Outline;
    }
}

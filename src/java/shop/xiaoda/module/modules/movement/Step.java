//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import net.minecraft.potion.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.block.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;

public class Step extends Module
{
    private final ModeValue<stepMode> mode;
    private final NumberValue height;
    private final NumberValue timer;
    private final BoolValue reverse;
    private final BoolValue hypixelSmooth;
    private final BoolValue twoBlockValue;
    private final BoolValue instantValue;
    private int ticks;
    private boolean doJump;
    private boolean step;
    private int onGroundTicks;
    private int offGroundTicks;
    
    public Step() {
        super("Step", Category.Movement);
        this.mode = new ModeValue<stepMode>("Mode", stepMode.values(), stepMode.Vanilla);
        this.height = new NumberValue("Height", 1.0, 1.0, 2.5, 0.1);
        this.timer = new NumberValue("Timer", 0.5, 0.1, 1.0, 0.1);
        this.reverse = new BoolValue("Reverse", false);
        this.hypixelSmooth = new BoolValue("HypixelSmooth", false);
        this.twoBlockValue = new BoolValue("Matrix-2Block", true);
        this.instantValue = new BoolValue("Matrix-Instant", true);
    }
    
    public void onDisable() {
        if (this.mode.getValue() != stepMode.Jump) {
            Step.mc.thePlayer.stepHeight = 0.6f;
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion event) {
        if (event.isPre()) {
            switch (this.mode.getValue()) {
                case Jump: {
                    if (Step.mc.thePlayer.onGround && Step.mc.thePlayer.isCollidedHorizontally) {
                        Step.mc.thePlayer.jump();
                        break;
                    }
                    break;
                }
                case Matrix: {
                    Step.mc.thePlayer.stepHeight = (this.twoBlockValue.getValue() ? 2.0f : 1.0f);
                    if (!this.doJump) {
                        break;
                    }
                    if ((this.ticks > 0 && Step.mc.thePlayer.onGround) || this.ticks > 5) {
                        this.ticks = 0;
                        this.doJump = false;
                        return;
                    }
                    if (this.ticks % 3 == 0) {
                        event.setOnGround(true);
                        Step.mc.thePlayer.jump();
                    }
                    ++this.ticks;
                    break;
                }
                case NCP_PacketLess: {
                    if (Step.mc.thePlayer.onGround && Step.mc.thePlayer.isCollidedHorizontally && !Step.mc.thePlayer.isPotionActive(Potion.jump)) {
                        Step.mc.thePlayer.jump();
                        MoveUtil.stop();
                        this.step = true;
                    }
                    if (Step.mc.thePlayer.offGroundTicks == 3 && this.step) {
                        Step.mc.thePlayer.motionY = MoveUtil.predictedMotion(Step.mc.thePlayer.motionY, 2);
                        MoveUtil.strafe(0.221);
                        this.step = false;
                        break;
                    }
                    break;
                }
                case NCP: {
                    if (Step.mc.thePlayer.onGround && !PlayerUtil.isInLiquid()) {
                        Step.mc.thePlayer.stepHeight = this.height.getValue().floatValue();
                    }
                    else {
                        Step.mc.thePlayer.stepHeight = 0.6f;
                    }
                    if (!this.reverse.getValue() || PlayerUtil.blockRelativeToPlayer(0.0, -(this.height.getValue().intValue() + 1), 0.0) instanceof BlockAir || PlayerUtil.isInLiquid()) {
                        return;
                    }
                    for (int i = 1; i < this.height.getValue().intValue() + 1; ++i) {
                        final EntityPlayerSP thePlayer = Step.mc.thePlayer;
                        thePlayer.motionY -= i;
                    }
                    break;
                }
                case Hypixel: {
                    final double mx = Step.mc.thePlayer.motionX;
                    final double my = Step.mc.thePlayer.motionY;
                    final double mz = Step.mc.thePlayer.motionZ;
                    final double x = Step.mc.thePlayer.posX;
                    final double y = Step.mc.thePlayer.posY;
                    final double z = Step.mc.thePlayer.posZ;
                    if (Step.mc.thePlayer.onGround) {
                        ++this.onGroundTicks;
                        this.offGroundTicks = 0;
                    }
                    else {
                        ++this.offGroundTicks;
                        this.onGroundTicks = 0;
                    }
                    if (y % 1.0 < 0.01 && this.offGroundTicks == 3) {
                        this.setMotion(mx, -0.784, mz);
                        MoveUtil.setSpeed(((boolean)this.hypixelSmooth.getValue()) ? 0.36 : 0.38);
                        Step.mc.thePlayer.setPosition(x, Math.floor(y), z);
                    }
                    if (Step.mc.thePlayer.isCollidedHorizontally && Step.mc.thePlayer.onGround) {
                        Step.mc.thePlayer.setPosition(x, Math.floor(y), z);
                        this.setMotion(mx, my, mz);
                        Step.mc.thePlayer.jump();
                        MoveUtil.setSpeed(((boolean)this.hypixelSmooth.getValue()) ? 0.41 : 0.43);
                    }
                    break;
                }
                case Vanilla: {
                    Step.mc.thePlayer.stepHeight = this.height.getValue().floatValue();
                    if (!this.reverse.getValue() || !PlayerUtil.isBlockUnder(this.height.getValue().floatValue() + Step.mc.thePlayer.getEyeHeight()) || PlayerUtil.isInLiquid()) {
                        return;
                    }
                    if (Step.mc.thePlayer.posY < Step.mc.thePlayer.lastGroundY && !Step.mc.thePlayer.onGround && Step.mc.thePlayer.offGroundTicks <= 1) {
                        Step.mc.thePlayer.motionY = -this.height.getValue();
                    }
                    if (Step.mc.thePlayer.offGroundTicks == 1 && Step.mc.thePlayer.posY < Step.mc.thePlayer.lastLastGroundY) {
                        Step.mc.timer.timerSpeed = this.timer.getValue().floatValue();
                        break;
                    }
                    break;
                }
                case Vulcan: {
                    if (Step.mc.thePlayer.ticksSinceJump > 11) {
                        Step.mc.thePlayer.stepHeight = 1.0f;
                        break;
                    }
                    Step.mc.thePlayer.stepHeight = 0.6f;
                    break;
                }
            }
        }
    }
    
    public void setMotion(final double motionX, final double motionY, final double motionZ) {
        Step.mc.thePlayer.motionX = motionX;
        Step.mc.thePlayer.motionY = motionY;
        Step.mc.thePlayer.motionZ = motionZ;
    }
    
    @EventTarget
    public void onStep(final EventStep event) {
        if (!event.isPre()) {
            switch (this.mode.getValue()) {
                case Matrix: {
                    if (event.getStepHeight() > 1.0) {
                        if (this.instantValue.getValue()) {
                            PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 0.41999998688698, Step.mc.thePlayer.posZ, false));
                            PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 0.7531999805212, Step.mc.thePlayer.posZ, false));
                            PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 1.00133597911215, Step.mc.thePlayer.posZ, true));
                            PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 1.42133596599913, Step.mc.thePlayer.posZ, false));
                            PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 1.75453595963335, Step.mc.thePlayer.posZ, false));
                            PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 2.0026719582243, Step.mc.thePlayer.posZ, false));
                            Step.mc.timer.timerSpeed = 0.14285715f;
                        }
                        else {
                            this.doJump = true;
                            this.ticks = 0;
                            Step.mc.thePlayer.setPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY, Step.mc.thePlayer.posZ);
                        }
                        return;
                    }
                    if (event.getStepHeight() > 0.6000000238418579) {
                        Step.mc.timer.timerSpeed = 0.33333f;
                        PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 0.41999998688697815, Step.mc.thePlayer.posZ, false));
                        PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 0.41999998688697815, Step.mc.thePlayer.posZ, true));
                        break;
                    }
                    break;
                }
                case NCP: {
                    if (!Step.mc.thePlayer.onGround || PlayerUtil.isInLiquid()) {
                        return;
                    }
                    final double height = event.getStepHeight();
                    if (height <= 0.6) {
                        return;
                    }
                    double[] values;
                    if (height > 2.019) {
                        values = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.919 };
                    }
                    else if (height > 1.869) {
                        values = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869 };
                    }
                    else if (height > 1.5) {
                        values = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652 };
                    }
                    else if (height > 1.015) {
                        values = new double[] { 0.42, 0.7532, 1.01, 1.093, 1.015 };
                    }
                    else if (height > 0.875) {
                        values = new double[] { 0.42, 0.7532 };
                    }
                    else {
                        values = new double[] { 0.39, 0.6938 };
                    }
                    Step.mc.timer.timerSpeed = this.timer.getValue().floatValue();
                    for (final double d : values) {
                        PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + (d + Math.random() / 2000.0), Step.mc.thePlayer.posZ, false));
                    }
                    break;
                }
                case Vanilla: {
                    if (event.getStepHeight() > 0.6) {
                        Step.mc.timer.timerSpeed = this.timer.getValue().floatValue();
                        break;
                    }
                    break;
                }
                case Vulcan: {
                    if (event.getStepHeight() > 0.6) {
                        Step.mc.timer.timerSpeed = 0.5f;
                        PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + 0.5, Step.mc.thePlayer.posZ, true));
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public enum stepMode
    {
        Vanilla, 
        NCP, 
        Hypixel, 
        NCP_PacketLess, 
        Vulcan, 
        Matrix, 
        Jump;
    }
}

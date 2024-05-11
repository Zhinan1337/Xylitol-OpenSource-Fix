//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.*;
import shop.xiaoda.event.*;
import net.minecraft.client.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import net.minecraft.item.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import java.lang.reflect.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.module.values.*;

public class AutoClicker extends Module
{
    private final BoolValue left;
    private final BoolValue right;
    private final NumberValue maxCps;
    private final NumberValue minCps;
    private final NumberValue RmaxCps;
    private final NumberValue RminCps;
    public static final NumberValue jitter;
    private final BoolValue blockHit;
    private final BoolValue autoUnBlock;
    private final BoolValue weaponOnly;
    private final Random random;
    private final TimeUtil timeUtils;
    public static boolean b;
    public static double c;
    
    public AutoClicker() {
        super("AutoClicker", Category.Combat);
        this.left = new BoolValue("Left Clicker", true);
        this.right = new BoolValue("Right Clicker", false);
        this.maxCps = new NumberValue("Left MaxCPS", 10.0, 1.0, 20.0, 1.0);
        this.minCps = new NumberValue("Left MinCPS", 10.0, 1.0, 20.0, 1.0);
        this.RmaxCps = new NumberValue("Right MaxCPS", 14.0, 1.0, 20.0, 1.0);
        this.RminCps = new NumberValue("Right MinCPS", 10.0, 1.0, 20.0, 1.0);
        this.blockHit = new BoolValue("BlockHit", false);
        this.autoUnBlock = new BoolValue("AutoUnblock", false);
        this.weaponOnly = new BoolValue("Weapons Only", false);
        this.random = new Random();
        this.timeUtils = new TimeUtil();
    }
    
    @EventTarget
    public void Tick(final EventTick event) {
        if (this.minCps.getValue() >= this.maxCps.getValue()) {
            this.maxCps.setValue(this.minCps.getValue());
        }
        if (this.maxCps.getValue() <= this.minCps.getValue()) {
            this.minCps.setValue(this.maxCps.getValue());
        }
        if (this.RminCps.getValue() >= this.RmaxCps.getValue()) {
            this.RmaxCps.setValue(this.RminCps.getValue());
        }
        if (this.RmaxCps.getValue() <= this.RminCps.getValue()) {
            this.RminCps.setValue(this.RmaxCps.getValue());
        }
    }
    
    public static void clickMouse() {
        final int leftClickCounter = (int)ReflectionUtil.getFieldValue(Minecraft.getMinecraft(), "leftClickCounter", "leftClickCounter");
        if (leftClickCounter <= 0) {
            Minecraft.getMinecraft().thePlayer.swingItem();
            if (Minecraft.getMinecraft().objectMouseOver == null) {
                if (Minecraft.getMinecraft().playerController.isNotCreative()) {
                    ReflectionUtil.setFieldValue(Minecraft.getMinecraft(), 10, "leftClickCounter", "leftClickCounter");
                }
            }
            else {
                switch (Minecraft.getMinecraft().objectMouseOver.typeOfHit) {
                    case ENTITY: {
                        try {
                            final PlayerControllerMP playerController = Minecraft.getMinecraft().playerController;
                            final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
                            final MovingObjectPosition objectMouseOver = Minecraft.getMinecraft().objectMouseOver;
                            playerController.attackEntity((EntityPlayer)thePlayer, MovingObjectPosition.entityHit);
                        }
                        catch (NullPointerException exception) {
                            exception.printStackTrace();
                        }
                        return;
                    }
                    case BLOCK: {
                        final BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
                        try {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
                                Minecraft.getMinecraft().playerController.clickBlock(blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit);
                                return;
                            }
                        }
                        catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                }
                if (Minecraft.getMinecraft().playerController.isNotCreative()) {
                    ReflectionUtil.setFieldValue(Minecraft.getMinecraft(), 10, "leftClickCounter", "leftClickCounter");
                }
            }
        }
    }
    
    @EventTarget
    public void onTick(final EventTick e) {
        this.setSuffix(String.format("%s - %s", ((Value)this.minCps).getValue(), ((Value)this.maxCps).getValue()));
        if (!this.state) {
            return;
        }
        if (AutoClicker.mc.currentScreen == null && Mouse.isButtonDown(0)) {
            if (!this.left.getValue()) {
                return;
            }
            if (this.weaponOnly.getValue()) {
                if (AutoClicker.mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(AutoClicker.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(AutoClicker.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                    return;
                }
            }
            if (!this.blockHit.getValue() && AutoClicker.mc.thePlayer.isUsingItem()) {
                return;
            }
            if (this.shouldAttack(Objects.equals(this.minCps.getValue().intValue(), this.maxCps.getValue().intValue()) ? this.maxCps.getValue().intValue() : ThreadLocalRandom.current().nextInt(this.minCps.getValue().intValue(), this.maxCps.getValue().intValue()))) {
                this.timeUtils.reset();
                ReflectionUtil.setFieldValue(Minecraft.getMinecraft(), 0, "leftClickCounter", "leftClickCounter");
                clickMouse();
                if (this.autoUnBlock.getValue() && Mouse.isButtonDown(1) && AutoClicker.mc.thePlayer.getHeldItem() != null && AutoClicker.mc.thePlayer.getHeldItem().getItem() != null && AutoClicker.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                    if (AutoClicker.mc.thePlayer.isBlocking()) {
                        KeyBinding.setKeyBindState(AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                        AutoClicker.mc.playerController.onStoppedUsingItem((EntityPlayer)AutoClicker.mc.thePlayer);
                        AutoClicker.mc.thePlayer.setItemInUse(AutoClicker.mc.thePlayer.getItemInUse(), 0);
                    }
                    else {
                        KeyBinding.setKeyBindState(AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                        AutoClicker.mc.playerController.sendUseItem((EntityPlayer)AutoClicker.mc.thePlayer, (World)AutoClicker.mc.theWorld, AutoClicker.mc.thePlayer.inventory.getCurrentItem());
                    }
                }
            }
        }
        if (AutoClicker.mc.currentScreen == null && Mouse.isButtonDown(1)) {
            if (!this.right.getValue()) {
                return;
            }
            if (this.shouldAttack((this.RminCps.getValue().intValue() == this.RmaxCps.getValue().intValue()) ? this.RmaxCps.getValue().intValue() : ThreadLocalRandom.current().nextInt(this.RminCps.getValue().intValue(), this.RmaxCps.getValue().intValue() + 1))) {
                this.timeUtils.reset();
                try {
                    final Field rightClickDelay = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                    rightClickDelay.setAccessible(true);
                    rightClickDelay.set(AutoClicker.mc, 0);
                }
                catch (Exception d) {
                    try {
                        final Field ex = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                        ex.setAccessible(true);
                        ex.set(AutoClicker.mc, 0);
                    }
                    catch (Exception f) {
                        this.onDisable();
                    }
                }
            }
        }
    }
    
    @EventTarget
    public void onUpdate(final EventMotion e) {
        if (!this.state) {
            return;
        }
        if (AutoClicker.mc.currentScreen == null && Mouse.isButtonDown(0) && AutoClicker.jitter.getValue() > 0.0) {
            final double a = AutoClicker.jitter.getValue() * 0.45;
            boolean b = this.random.nextBoolean();
            final EntityPlayerSP thePlayer5 = Minecraft.getMinecraft().thePlayer;
            thePlayer5.rotationYawHead += AutoClicker.mc.thePlayer.rotationYaw;
            final EntityPlayerSP thePlayer6 = Minecraft.getMinecraft().thePlayer;
            thePlayer6.renderYawOffset += AutoClicker.mc.thePlayer.rotationYaw;
            e.setYaw(AutoClicker.mc.thePlayer.rotationYaw);
            Minecraft.getMinecraft().thePlayer.rotationPitchHead = AutoClicker.mc.thePlayer.rotationPitch;
            e.setPitch(AutoClicker.mc.thePlayer.rotationPitch);
            if (b) {
                final EntityPlayerSP thePlayer = AutoClicker.mc.thePlayer;
                AutoClicker.c = this.random.nextFloat() * a;
                final EntityPlayerSP thePlayer7 = Minecraft.getMinecraft().thePlayer;
                thePlayer7.rotationYawHead += (float)AutoClicker.c;
                final EntityPlayerSP thePlayer8 = Minecraft.getMinecraft().thePlayer;
                thePlayer8.renderYawOffset += (float)AutoClicker.c;
                e.setYaw(e.getYaw() + (float)AutoClicker.c);
            }
            else {
                final EntityPlayerSP thePlayer2 = AutoClicker.mc.thePlayer;
                AutoClicker.c = this.random.nextFloat() * a;
                final EntityPlayerSP thePlayer9 = Minecraft.getMinecraft().thePlayer;
                thePlayer9.rotationYawHead -= (float)AutoClicker.c;
                final EntityPlayerSP thePlayer10 = Minecraft.getMinecraft().thePlayer;
                thePlayer10.renderYawOffset -= (float)AutoClicker.c;
                e.setYaw(e.getYaw() - (float)AutoClicker.c);
            }
            b = this.random.nextBoolean();
            if (b) {
                final EntityPlayerSP thePlayer3 = AutoClicker.mc.thePlayer;
                final EntityPlayerSP thePlayer11 = Minecraft.getMinecraft().thePlayer;
                thePlayer11.rotationPitchHead += (float)(this.random.nextFloat() * (a * 0.45));
                e.setPitch(e.getPitch() + (float)(this.random.nextFloat() * (a * 0.45)));
            }
            else {
                final EntityPlayerSP thePlayer4 = AutoClicker.mc.thePlayer;
                final EntityPlayerSP thePlayer12 = Minecraft.getMinecraft().thePlayer;
                thePlayer12.rotationPitchHead -= (float)(this.random.nextFloat() * (a * 0.45));
                e.setPitch(e.getPitch() - (float)(this.random.nextFloat() * (a * 0.45)));
            }
        }
    }
    
    public boolean shouldAttack(final int cps) {
        return this.timeUtils.hasReached(1000.0 / cps);
    }
    
    public void reset() {
        this.timeUtils.reset();
    }
    
    static {
        jitter = new NumberValue("Jitter", 0.0, 0.0, 3.0, 0.1);
        AutoClicker.b = false;
        AutoClicker.c = 0.0;
    }
}

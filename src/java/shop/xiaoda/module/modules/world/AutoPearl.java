//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.world;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.render.fontRender.*;
import javax.vecmath.*;
import java.awt.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.utils.component.*;
import net.minecraft.item.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.utils.client.*;

public class AutoPearl extends Module
{
    private BoolValue debugValue;
    private static final double T = 10.0;
    private static final double T_MIN = 1.0E-4;
    private static final double ALPHA = 0.997;
    private CalculateThread calculateThread;
    private TimeUtil timer;
    private boolean attempted;
    private boolean calculating;
    private int bestPearlSlot;
    
    public AutoPearl() {
        super("AutoPearl", Category.World);
        this.debugValue = new BoolValue("Debug", false);
        this.timer = new TimeUtil();
    }
    
    @EventTarget
    public void onMoveInput(final EventMoveInput event) {
        if (this.calculating) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onRender(final EventRender2D event) {
        if (!this.debugValue.getValue()) {
            return;
        }
        FontManager.arial12.drawString("assessment: " + new ProjectileUtil.EnderPearlPredictor(AutoPearl.mc.thePlayer.posX, AutoPearl.mc.thePlayer.posY, AutoPearl.mc.thePlayer.posZ, AutoPearl.mc.thePlayer.motionY - 0.01, AutoPearl.mc.thePlayer.motionY + 0.02).assessRotation(new Vector2f(AutoPearl.mc.thePlayer.rotationYaw, AutoPearl.mc.thePlayer.rotationPitch)), 20.0f, 20.0f, Color.WHITE.getRGB());
        FontManager.arial12.drawString("(" + AutoPearl.mc.thePlayer.rotationYaw + ", " + AutoPearl.mc.thePlayer.rotationPitch + ")", 20.0f, 30.0f, Color.WHITE.getRGB());
    }
    
    @EventTarget
    public void onMotion(final EventMotion event) throws InterruptedException {
        if (AutoPearl.mc.thePlayer.onGround) {
            this.attempted = false;
            this.calculating = false;
        }
        if (event.isPost() && this.calculating && (this.calculateThread == null || this.calculateThread.completed)) {
            this.calculating = false;
            Stuck.throwPearl(this.calculateThread.solution);
        }
        final boolean overVoid = !AutoPearl.mc.thePlayer.onGround && !PlayerUtil.isBlockUnder(30.0, true);
        if (!this.attempted && !AutoPearl.mc.thePlayer.onGround && overVoid && FallDistanceComponent.distance > 2.0f) {
            FallDistanceComponent.distance = 0.0f;
            DebugUtil.log("1");
            this.attempted = true;
            for (int slot = 5; slot < 45; ++slot) {
                final ItemStack stack = AutoPearl.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
                if (stack != null && stack.getItem() instanceof ItemEnderPearl && slot >= 36) {
                    this.bestPearlSlot = slot;
                    if (this.debugValue.getValue()) {
                        DebugUtil.log("Found Pearl:" + (this.bestPearlSlot - 36));
                    }
                    if (this.bestPearlSlot - 36 != -37) {
                        AutoPearl.mc.thePlayer.inventory.currentItem = this.bestPearlSlot - 36;
                    }
                }
            }
            if (this.bestPearlSlot == 0) {
                return;
            }
            DebugUtil.log(this.bestPearlSlot);
            if (!(AutoPearl.mc.thePlayer.inventoryContainer.getSlot(this.bestPearlSlot).getStack().getItem() instanceof ItemEnderPearl)) {
                return;
            }
            this.calculating = true;
            (this.calculateThread = new CalculateThread(AutoPearl.mc.thePlayer.posX, AutoPearl.mc.thePlayer.posY, AutoPearl.mc.thePlayer.posZ, 0.0, 0.0)).start();
            ((Stuck)this.getModule((Class)Stuck.class)).setState(true);
        }
    }
    
    private void putItemInSlot(final int slot, final int slotIn) {
        InventoryUtil.windowClick(AutoPearl.mc, slotIn, slot - 36, InventoryUtil.ClickType.SWAP_WITH_HOT_BAR_SLOT);
    }
    
    private class CalculateThread extends Thread
    {
        private int iteration;
        private boolean completed;
        private double temperature;
        private double energy;
        private double solutionE;
        private Vector2f solution;
        public boolean stop;
        private final ProjectileUtil.EnderPearlPredictor predictor;
        
        private CalculateThread(final double predictX, final double predictY, final double predictZ, final double minMotionY, final double maxMotionY) {
            this.predictor = new ProjectileUtil.EnderPearlPredictor(predictX, predictY, predictZ, minMotionY, maxMotionY);
            this.iteration = 0;
            this.temperature = 10.0;
            this.energy = 0.0;
            this.stop = false;
            this.completed = false;
        }
        
        @Override
        public void run() {
            final TimeUtil timer = new TimeUtil();
            timer.reset();
            this.solution = new Vector2f((float)MathUtil.getRandomInRange(-180, 180), (float)MathUtil.getRandomInRange(-90, 90));
            Vector2f current = this.solution;
            this.energy = this.predictor.assessRotation(this.solution);
            this.solutionE = this.energy;
            while (this.temperature >= 1.0E-4 && !this.stop) {
                final Vector2f rotation = new Vector2f((float)(current.x + MathUtil.getRandomInRange(-this.temperature * 18.0, this.temperature * 18.0)), (float)(current.y + MathUtil.getRandomInRange(-this.temperature * 9.0, this.temperature * 9.0)));
                if (rotation.y > 90.0f) {
                    rotation.y = 90.0f;
                }
                if (rotation.y < -90.0f) {
                    rotation.y = -90.0f;
                }
                final double assessment = this.predictor.assessRotation(rotation);
                final double deltaE = assessment - this.energy;
                if (deltaE >= 0.0 || MathUtil.getRandomInRange(0, 1) < Math.exp(-deltaE / this.temperature * 100.0)) {
                    this.energy = assessment;
                    current = rotation;
                    if (assessment > this.solutionE) {
                        this.solutionE = assessment;
                        this.solution = new Vector2f(rotation.x, rotation.y);
                        DebugUtil.log("Find a better solution: (" + this.solution.x + ", " + this.solution.y + "), value: " + this.solutionE);
                    }
                }
                this.temperature *= 0.997;
                ++this.iteration;
            }
            DebugUtil.log("Simulated annealing completed within " + this.iteration + " iterations");
            DebugUtil.log("Time used: " + timer.getDifference() + " solution energy: " + this.solutionE);
            this.completed = true;
        }
    }
}

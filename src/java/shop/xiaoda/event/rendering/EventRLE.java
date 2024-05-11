//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.rendering;

import shop.xiaoda.event.api.events.callables.*;
import net.minecraft.entity.*;

public class EventRLE extends EventCancellable
{
    private final EntityLivingBase entity;
    private float limbSwing;
    private float limbSwingAmount;
    private float ageInTicks;
    private float rotationYawHead;
    private float rotationPitch;
    private float chestRot;
    private float offset;
    private float fuckingTick;
    private final boolean pre;
    
    public EventRLE(final EntityLivingBase entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float rotationYawHead, final float rotationPitch, final float chestRot, final float offset, final float fuckingTick) {
        this.entity = entity;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.rotationYawHead = rotationYawHead;
        this.rotationPitch = rotationPitch;
        this.chestRot = chestRot;
        this.offset = offset;
        this.fuckingTick = fuckingTick;
        this.pre = true;
    }
    
    public EventRLE(final EntityLivingBase entity) {
        this.entity = entity;
        this.pre = false;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public float getLimbSwing() {
        return this.limbSwing;
    }
    
    public void setLimbSwing(final float limbSwing) {
        this.limbSwing = limbSwing;
    }
    
    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }
    
    public void setLimbSwingAmount(final float limbSwingAmount) {
        this.limbSwingAmount = limbSwingAmount;
    }
    
    public float getAgeInTicks() {
        return this.ageInTicks;
    }
    
    public void setAgeInTicks(final float ageInTicks) {
        this.ageInTicks = ageInTicks;
    }
    
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    public void setRotationYawHead(final float rotationYawHead) {
        this.rotationYawHead = rotationYawHead;
    }
    
    public float getRotationPitch() {
        return this.rotationPitch;
    }
    
    public void setRotationPitch(final float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }
    
    public float getOffset() {
        return this.offset;
    }
    
    public void setOffset(final float offset) {
        this.offset = offset;
    }
    
    public float getRotationChest() {
        return this.chestRot;
    }
    
    public void setRotationChest(final float rotationChest) {
        this.chestRot = rotationChest;
    }
    
    public float getTick() {
        return this.fuckingTick;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public boolean isPost() {
        return !this.pre;
    }
}

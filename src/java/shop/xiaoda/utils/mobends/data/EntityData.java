//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.data;

import net.minecraft.client.model.*;
import org.lwjgl.compatibility.util.vector.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;

public class EntityData
{
    public int entityID;
    public String entityType;
    public ModelBase model;
    public Vector3f position;
    public Vector3f motion_prev;
    public Vector3f motion;
    public float ticks;
    public float ticksPerFrame;
    public float lastTicks;
    public float lastTicksPerFrame;
    public boolean updatedThisFrame;
    public float ticksAfterLiftoff;
    public float ticksAfterTouchdown;
    public float ticksAfterPunch;
    public float ticksAfterThrowup;
    public boolean alreadyPunched;
    public boolean onGround;
    
    public EntityData(final int argEntityID) {
        this.position = new Vector3f();
        this.motion_prev = new Vector3f();
        this.motion = new Vector3f();
        this.ticks = 0.0f;
        this.ticksPerFrame = 0.0f;
        this.lastTicks = 0.0f;
        this.lastTicksPerFrame = 0.0f;
        this.updatedThisFrame = false;
        this.ticksAfterLiftoff = 0.0f;
        this.ticksAfterTouchdown = 0.0f;
        this.ticksAfterPunch = 0.0f;
        this.ticksAfterThrowup = 0.0f;
        this.alreadyPunched = false;
        this.entityID = argEntityID;
        if (Minecraft.getMinecraft().theWorld.getEntityByID(argEntityID) != null) {
            this.entityType = Minecraft.getMinecraft().theWorld.getEntityByID(argEntityID).getName();
        }
        else {
            this.entityType = "NULL";
        }
        this.model = null;
    }
    
    public boolean canBeUpdated() {
        return !this.updatedThisFrame;
    }
    
    public boolean calcOnGround() {
        final Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(this.entityID);
        if (entity == null) {
            return false;
        }
        final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox();
        final double var1 = this.position.y + this.motion.y;
        final List list = entity.worldObj.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox().addCoord(0.0, -0.0010000000474974513, 0.0));
        final int i = 0;
        return i < list.size();
    }
    
    public boolean calcCollidedHorizontally() {
        final Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(this.entityID);
        if (entity == null) {
            return false;
        }
        final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox();
        final List list = entity.worldObj.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox().addCoord((double)this.motion.x, 0.0, (double)this.motion.z));
        final int i = 0;
        return i < list.size();
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void update(final float argPartialTicks) {
        if (this.getEntity() == null) {
            return;
        }
        this.ticksPerFrame = Minecraft.getMinecraft().thePlayer.ticksExisted + argPartialTicks - this.ticks;
        this.ticks = Minecraft.getMinecraft().thePlayer.ticksExisted + argPartialTicks;
        this.updatedThisFrame = false;
        if (this.calcOnGround() & !this.onGround) {
            this.onTouchdown();
            this.onGround = true;
        }
        if ((!this.calcOnGround() & this.onGround) | (this.motion_prev.y <= 0.0f && this.motion.y - this.motion_prev.y > 0.4f && this.ticksAfterLiftoff > 2.0f)) {
            this.onLiftoff();
            this.onGround = false;
        }
        if (this.getEntity().swingProgress > 0.0f) {
            if (!this.alreadyPunched) {
                this.onPunch();
                this.alreadyPunched = true;
            }
        }
        else {
            this.alreadyPunched = false;
        }
        if (this.motion_prev.y <= 0.0f && this.motion.y > 0.0f) {
            this.onThrowup();
        }
        if (!this.isOnGround()) {
            this.ticksAfterLiftoff += this.ticksPerFrame;
        }
        if (this.isOnGround()) {
            this.ticksAfterTouchdown += this.ticksPerFrame;
        }
        this.ticksAfterPunch += this.ticksPerFrame;
        this.ticksAfterThrowup += this.ticksPerFrame;
    }
    
    public EntityLivingBase getEntity() {
        if (Minecraft.getMinecraft().theWorld.getEntityByID(this.entityID) instanceof EntityLivingBase) {
            return (EntityLivingBase)Minecraft.getMinecraft().theWorld.getEntityByID(this.entityID);
        }
        return null;
    }
    
    public void onTouchdown() {
        this.ticksAfterTouchdown = 0.0f;
    }
    
    public void onLiftoff() {
        this.ticksAfterLiftoff = 0.0f;
    }
    
    public void onThrowup() {
        this.ticksAfterThrowup = 0.0f;
    }
    
    public void onPunch() {
        this.ticksAfterPunch = 0.0f;
    }
}

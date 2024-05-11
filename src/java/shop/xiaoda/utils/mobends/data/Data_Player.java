//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.data;

import shop.xiaoda.utils.mobends.client.model.*;
import shop.xiaoda.utils.mobends.util.*;
import shop.xiaoda.utils.mobends.client.renderer.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import java.util.*;

public class Data_Player extends EntityData
{
    public static List<Data_Player> dataList;
    public ModelRendererBends head;
    public ModelRendererBends headwear;
    public ModelRendererBends body;
    public ModelRendererBends rightArm;
    public ModelRendererBends leftArm;
    public ModelRendererBends rightLeg;
    public ModelRendererBends leftLeg;
    public ModelRendererBends ears;
    public ModelRendererBends cloak;
    public ModelRendererBends rightForeArm;
    public ModelRendererBends leftForeArm;
    public ModelRendererBends rightForeLeg;
    public ModelRendererBends leftForeLeg;
    public SmoothVector3f renderOffset;
    public SmoothVector3f renderRotation;
    public SmoothVector3f renderItemRotation;
    public SwordTrail swordTrail;
    public boolean sprintJumpLeg;
    public boolean fistPunchArm;
    public int currentAttack;
    
    public Data_Player(final int argEntityID) {
        super(argEntityID);
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
        this.renderItemRotation = new SmoothVector3f();
        this.swordTrail = new SwordTrail();
        this.sprintJumpLeg = false;
        this.fistPunchArm = false;
        this.currentAttack = 0;
    }
    
    public void syncModelInfo(final ModelBendsPlayer argModel) {
        if (this.head == null) {
            this.head = new ModelRendererBends((ModelBase)argModel);
        }
        this.head.sync((ModelRendererBends)argModel.bipedHead);
        if (this.headwear == null) {
            this.headwear = new ModelRendererBends((ModelBase)argModel);
        }
        this.headwear.sync((ModelRendererBends)argModel.bipedHeadwear);
        if (this.body == null) {
            this.body = new ModelRendererBends((ModelBase)argModel);
        }
        this.body.sync((ModelRendererBends)argModel.bipedBody);
        if (this.rightArm == null) {
            this.rightArm = new ModelRendererBends((ModelBase)argModel);
        }
        this.rightArm.sync((ModelRendererBends)argModel.bipedRightArm);
        if (this.leftArm == null) {
            this.leftArm = new ModelRendererBends((ModelBase)argModel);
        }
        this.leftArm.sync((ModelRendererBends)argModel.bipedLeftArm);
        if (this.rightLeg == null) {
            this.rightLeg = new ModelRendererBends((ModelBase)argModel);
        }
        this.rightLeg.sync((ModelRendererBends)argModel.bipedRightLeg);
        if (this.leftLeg == null) {
            this.leftLeg = new ModelRendererBends((ModelBase)argModel);
        }
        this.leftLeg.sync((ModelRendererBends)argModel.bipedLeftLeg);
        if (this.rightForeArm == null) {
            this.rightForeArm = new ModelRendererBends((ModelBase)argModel);
        }
        this.rightForeArm.sync(argModel.bipedRightForeArm);
        if (this.leftForeArm == null) {
            this.leftForeArm = new ModelRendererBends((ModelBase)argModel);
        }
        this.leftForeArm.sync(argModel.bipedLeftForeArm);
        if (this.rightForeLeg == null) {
            this.rightForeLeg = new ModelRendererBends((ModelBase)argModel);
        }
        this.rightForeLeg.sync(argModel.bipedRightForeLeg);
        if (this.leftForeLeg == null) {
            this.leftForeLeg = new ModelRendererBends((ModelBase)argModel);
        }
        this.leftForeLeg.sync(argModel.bipedLeftForeLeg);
        this.renderOffset.set(argModel.renderOffset);
        this.renderRotation.set(argModel.renderRotation);
        this.renderItemRotation.set(argModel.renderItemRotation);
        this.swordTrail = argModel.swordTrail;
    }
    
    public static void add(final Data_Player argData) {
        Data_Player.dataList.add(argData);
    }
    
    public static Data_Player get(final int argEntityID) {
        for (int i = 0; i < Data_Player.dataList.size(); ++i) {
            if (Data_Player.dataList.get(i).entityID == argEntityID) {
                return Data_Player.dataList.get(i);
            }
        }
        final Data_Player newData = new Data_Player(argEntityID);
        if (Minecraft.getMinecraft().theWorld.getEntityByID(argEntityID) != null) {
            Data_Player.dataList.add(newData);
        }
        return newData;
    }
    
    @Override
    public void update(final float argPartialTicks) {
        super.update(argPartialTicks);
        if (this.ticksAfterPunch > 20.0f) {
            this.currentAttack = 0;
        }
        if (this.ticksAfterThrowup - this.ticksPerFrame == 0.0f) {
            this.sprintJumpLeg = !this.sprintJumpLeg;
        }
    }
    
    @Override
    public void onLiftoff() {
        super.onLiftoff();
    }
    
    @Override
    public void onPunch() {
        if (this.getEntity().getHeldItem() != null) {
            if (this.ticksAfterPunch > 6.0f) {
                if (this.currentAttack == 0) {
                    this.currentAttack = 1;
                    this.ticksAfterPunch = 0.0f;
                }
                else if (this.ticksAfterPunch < 15.0f) {
                    if (this.currentAttack == 1) {
                        this.currentAttack = 2;
                    }
                    else if (this.currentAttack == 2) {
                        this.currentAttack = (this.getEntity().isRiding() ? 1 : 3);
                    }
                    else if (this.currentAttack == 3) {
                        this.currentAttack = 1;
                    }
                    this.ticksAfterPunch = 0.0f;
                }
            }
        }
        else {
            this.fistPunchArm = !this.fistPunchArm;
            this.ticksAfterPunch = 0.0f;
        }
    }
    
    static {
        Data_Player.dataList = new ArrayList<Data_Player>();
    }
}

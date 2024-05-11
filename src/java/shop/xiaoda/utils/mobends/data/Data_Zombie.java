//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.data;

import shop.xiaoda.utils.mobends.client.model.*;
import shop.xiaoda.utils.mobends.util.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import java.util.*;

public class Data_Zombie extends EntityData
{
    public static List<Data_Zombie> dataList;
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
    public int currentWalkingState;
    public float ticksBeforeStateChange;
    
    public Data_Zombie(final int argEntityID) {
        super(argEntityID);
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
        this.currentWalkingState = 0;
        this.ticksBeforeStateChange = 0.0f;
    }
    
    public void syncModelInfo(final ModelBendsZombie argModel) {
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
        this.rightForeArm.sync((ModelRendererBends)argModel.bipedRightForeArm);
        if (this.leftForeArm == null) {
            this.leftForeArm = new ModelRendererBends((ModelBase)argModel);
        }
        this.leftForeArm.sync((ModelRendererBends)argModel.bipedLeftForeArm);
        if (this.rightForeLeg == null) {
            this.rightForeLeg = new ModelRendererBends((ModelBase)argModel);
        }
        this.rightForeLeg.sync((ModelRendererBends)argModel.bipedRightForeLeg);
        if (this.leftForeLeg == null) {
            this.leftForeLeg = new ModelRendererBends((ModelBase)argModel);
        }
        this.leftForeLeg.sync((ModelRendererBends)argModel.bipedLeftForeLeg);
        this.renderOffset.set(argModel.renderOffset);
        this.renderRotation.set(argModel.renderRotation);
    }
    
    public static void add(final Data_Zombie argData) {
        Data_Zombie.dataList.add(argData);
    }
    
    public static Data_Zombie get(final int argEntityID) {
        for (int i = 0; i < Data_Zombie.dataList.size(); ++i) {
            if (Data_Zombie.dataList.get(i).entityID == argEntityID) {
                return Data_Zombie.dataList.get(i);
            }
        }
        final Data_Zombie newData = new Data_Zombie(argEntityID);
        if (Minecraft.getMinecraft().theWorld.getEntityByID(argEntityID) != null) {
            Data_Zombie.dataList.add(newData);
        }
        return newData;
    }
    
    @Override
    public void update(final float argPartialTicks) {
        super.update(argPartialTicks);
        this.ticksBeforeStateChange -= argPartialTicks;
        if (this.ticksBeforeStateChange <= 0.0f) {
            final Random random = new Random();
            this.currentWalkingState = random.nextInt(2);
            this.ticksBeforeStateChange = (float)(80 + random.nextInt(20));
        }
    }
    
    @Override
    public void onLiftoff() {
        super.onLiftoff();
    }
    
    static {
        Data_Zombie.dataList = new ArrayList<Data_Zombie>();
    }
}

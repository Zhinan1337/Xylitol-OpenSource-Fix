//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.data;

import shop.xiaoda.utils.mobends.client.model.*;
import shop.xiaoda.utils.mobends.util.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import java.util.*;

public class Data_Spider extends EntityData
{
    public static List<Data_Spider> dataList;
    public ModelRendererBends spiderHead;
    public ModelRendererBends spiderNeck;
    public ModelRendererBends spiderBody;
    public ModelRendererBends spiderLeg1;
    public ModelRendererBends spiderLeg2;
    public ModelRendererBends spiderLeg3;
    public ModelRendererBends spiderLeg4;
    public ModelRendererBends spiderLeg5;
    public ModelRendererBends spiderLeg6;
    public ModelRendererBends spiderLeg7;
    public ModelRendererBends spiderLeg8;
    public ModelRendererBends spiderForeLeg1;
    public ModelRendererBends spiderForeLeg2;
    public ModelRendererBends spiderForeLeg3;
    public ModelRendererBends spiderForeLeg4;
    public ModelRendererBends spiderForeLeg5;
    public ModelRendererBends spiderForeLeg6;
    public ModelRendererBends spiderForeLeg7;
    public ModelRendererBends spiderForeLeg8;
    public SmoothVector3f renderOffset;
    public SmoothVector3f renderRotation;
    
    public Data_Spider(final int argEntityID) {
        super(argEntityID);
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
    }
    
    public void syncModelInfo(final ModelBendsSpider argModel) {
        if (this.spiderHead == null) {
            this.spiderHead = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderHead.sync((ModelRendererBends)argModel.spiderHead);
        if (this.spiderNeck == null) {
            this.spiderNeck = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderNeck.sync((ModelRendererBends)argModel.spiderNeck);
        if (this.spiderBody == null) {
            this.spiderBody = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderBody.sync((ModelRendererBends)argModel.spiderBody);
        if (this.spiderLeg1 == null) {
            this.spiderLeg1 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg1.sync((ModelRendererBends)argModel.spiderLeg1);
        if (this.spiderLeg2 == null) {
            this.spiderLeg2 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg2.sync((ModelRendererBends)argModel.spiderLeg2);
        if (this.spiderLeg3 == null) {
            this.spiderLeg3 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg3.sync((ModelRendererBends)argModel.spiderLeg3);
        if (this.spiderLeg4 == null) {
            this.spiderLeg4 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg4.sync((ModelRendererBends)argModel.spiderLeg4);
        if (this.spiderLeg5 == null) {
            this.spiderLeg5 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg5.sync((ModelRendererBends)argModel.spiderLeg5);
        if (this.spiderLeg6 == null) {
            this.spiderLeg6 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg6.sync((ModelRendererBends)argModel.spiderLeg6);
        if (this.spiderLeg7 == null) {
            this.spiderLeg7 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg7.sync((ModelRendererBends)argModel.spiderLeg7);
        if (this.spiderLeg8 == null) {
            this.spiderLeg8 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderLeg8.sync((ModelRendererBends)argModel.spiderLeg8);
        if (this.spiderForeLeg1 == null) {
            this.spiderForeLeg1 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg1.sync(argModel.spiderForeLeg1);
        if (this.spiderForeLeg2 == null) {
            this.spiderForeLeg2 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg2.sync(argModel.spiderForeLeg2);
        if (this.spiderForeLeg3 == null) {
            this.spiderForeLeg3 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg3.sync(argModel.spiderForeLeg3);
        if (this.spiderForeLeg4 == null) {
            this.spiderForeLeg4 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg4.sync(argModel.spiderForeLeg4);
        if (this.spiderForeLeg5 == null) {
            this.spiderForeLeg5 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg5.sync(argModel.spiderForeLeg5);
        if (this.spiderForeLeg6 == null) {
            this.spiderForeLeg6 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg6.sync(argModel.spiderForeLeg6);
        if (this.spiderForeLeg7 == null) {
            this.spiderForeLeg7 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg7.sync(argModel.spiderForeLeg7);
        if (this.spiderForeLeg8 == null) {
            this.spiderForeLeg8 = new ModelRendererBends((ModelBase)argModel);
        }
        this.spiderForeLeg8.sync(argModel.spiderForeLeg8);
        this.renderOffset.set(argModel.renderOffset);
        this.renderRotation.set(argModel.renderRotation);
    }
    
    public static void add(final Data_Spider argData) {
        Data_Spider.dataList.add(argData);
    }
    
    public static Data_Spider get(final int argEntityID) {
        for (int i = 0; i < Data_Spider.dataList.size(); ++i) {
            if (Data_Spider.dataList.get(i).entityID == argEntityID) {
                return Data_Spider.dataList.get(i);
            }
        }
        final Data_Spider newData = new Data_Spider(argEntityID);
        if (Minecraft.getMinecraft().theWorld.getEntityByID(argEntityID) != null) {
            Data_Spider.dataList.add(newData);
        }
        return newData;
    }
    
    @Override
    public void update(final float argPartialTicks) {
        super.update(argPartialTicks);
    }
    
    @Override
    public void onLiftoff() {
        super.onLiftoff();
    }
    
    static {
        Data_Spider.dataList = new ArrayList<Data_Spider>();
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import net.minecraft.util.*;
import shop.xiaoda.module.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.mobends.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.mobends.data.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import org.lwjgl.compatibility.util.vector.*;
import net.minecraft.client.renderer.entity.*;
import shop.xiaoda.utils.mobends.client.renderer.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;

public class MoBendsMod extends Module
{
    public static float partialTicks;
    public static float ticks;
    public static float ticksPerFrame;
    public static final ResourceLocation texture_NULL;
    
    public MoBendsMod() {
        super("MoreBends", Category.Render);
    }
    
    public void onEnable() {
        AnimatedEntity.register();
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D e) {
        if (MoBendsMod.mc.theWorld == null) {
            return;
        }
        for (int i = 0; i < Data_Player.dataList.size(); ++i) {
            Data_Player.dataList.get(i).update(e.getPartialTicks());
        }
        for (int i = 0; i < Data_Zombie.dataList.size(); ++i) {
            Data_Zombie.dataList.get(i).update(e.getPartialTicks());
        }
        for (int i = 0; i < Data_Spider.dataList.size(); ++i) {
            Data_Spider.dataList.get(i).update(e.getPartialTicks());
        }
        if (MoBendsMod.mc.thePlayer != null) {
            final float newTicks = MoBendsMod.mc.thePlayer.ticksExisted + e.getPartialTicks();
            if (!MoBendsMod.mc.theWorld.isRemote || !MoBendsMod.mc.isGamePaused()) {
                MoBendsMod.ticksPerFrame = Math.min(Math.max(0.0f, newTicks - MoBendsMod.ticks), 1.0f);
                MoBendsMod.ticks = newTicks;
            }
            else {
                MoBendsMod.ticksPerFrame = 0.0f;
            }
        }
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        if (MoBendsMod.mc.theWorld == null) {
            return;
        }
        for (int i = 0; i < Data_Player.dataList.size(); ++i) {
            final Data_Player data = Data_Player.dataList.get(i);
            final Entity entity = MoBendsMod.mc.theWorld.getEntityByID(data.entityID);
            if (entity != null) {
                if (!data.entityType.equalsIgnoreCase(entity.getName())) {
                    Data_Player.dataList.remove(data);
                    Data_Player.add(new Data_Player(entity.getEntityId()));
                }
                else {
                    data.motion_prev.set((ReadableVector3f)data.motion);
                    data.motion.x = (float)entity.posX - data.position.x;
                    data.motion.y = (float)entity.posY - data.position.y;
                    data.motion.z = (float)entity.posZ - data.position.z;
                    data.position = new Vector3f((float)entity.posX, (float)entity.posY, (float)entity.posZ);
                }
            }
            else {
                Data_Player.dataList.remove(data);
            }
        }
        for (int i = 0; i < Data_Zombie.dataList.size(); ++i) {
            final Data_Zombie data2 = Data_Zombie.dataList.get(i);
            final Entity entity = MoBendsMod.mc.theWorld.getEntityByID(data2.entityID);
            if (entity != null) {
                if (!data2.entityType.equalsIgnoreCase(entity.getName())) {
                    Data_Zombie.dataList.remove(data2);
                    Data_Zombie.add(new Data_Zombie(entity.getEntityId()));
                }
                else {
                    data2.motion_prev.set((ReadableVector3f)data2.motion);
                    data2.motion.x = (float)entity.posX - data2.position.x;
                    data2.motion.y = (float)entity.posY - data2.position.y;
                    data2.motion.z = (float)entity.posZ - data2.position.z;
                    data2.position = new Vector3f((float)entity.posX, (float)entity.posY, (float)entity.posZ);
                }
            }
            else {
                Data_Zombie.dataList.remove(data2);
            }
        }
        for (int i = 0; i < Data_Spider.dataList.size(); ++i) {
            final Data_Spider data3 = Data_Spider.dataList.get(i);
            final Entity entity = MoBendsMod.mc.theWorld.getEntityByID(data3.entityID);
            if (entity != null) {
                if (!data3.entityType.equalsIgnoreCase(entity.getName())) {
                    Data_Spider.dataList.remove(data3);
                    Data_Spider.add(new Data_Spider(entity.getEntityId()));
                }
                else {
                    data3.motion_prev.set((ReadableVector3f)data3.motion);
                    data3.motion.x = (float)entity.posX - data3.position.x;
                    data3.motion.y = (float)entity.posY - data3.position.y;
                    data3.motion.z = (float)entity.posZ - data3.position.z;
                    data3.position = new Vector3f((float)entity.posX, (float)entity.posY, (float)entity.posZ);
                }
            }
            else {
                Data_Spider.dataList.remove(data3);
            }
        }
    }
    
    public boolean onRenderLivingEvent(final RendererLivingEntity renderer, final EntityLivingBase entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        if (!this.getState() || renderer instanceof RenderBendsPlayer || renderer instanceof RenderBendsZombie || renderer instanceof RenderBendsSpider) {
            return false;
        }
        final AnimatedEntity animatedEntity = AnimatedEntity.getByEntity((Entity)entity);
        if ((animatedEntity != null && (entity instanceof EntityPlayer || entity instanceof EntityZombie)) || entity instanceof EntitySpider) {
            if (entity instanceof EntityPlayer) {
                final AbstractClientPlayer player = (AbstractClientPlayer)entity;
                AnimatedEntity.getPlayerRenderer(player).doRender(player, x, y, z, entityYaw, partialTicks);
            }
            else if (entity instanceof EntityZombie) {
                final EntityZombie zombie = (EntityZombie)entity;
                AnimatedEntity.zombieRenderer.doRender((EntityLiving)zombie, x, y, z, entityYaw, partialTicks);
            }
            else {
                final EntitySpider spider = (EntitySpider)entity;
                AnimatedEntity.spiderRenderer.doRender((EntityLiving)spider, x, y, z, entityYaw, partialTicks);
            }
            return true;
        }
        return false;
    }
    
    static {
        MoBendsMod.partialTicks = 0.0f;
        MoBendsMod.ticks = 0.0f;
        MoBendsMod.ticksPerFrame = 0.0f;
        texture_NULL = new ResourceLocation("mobends/textures/white.png");
    }
}

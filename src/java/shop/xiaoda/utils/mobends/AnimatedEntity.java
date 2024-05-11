//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends;

import shop.xiaoda.utils.mobends.animation.player.Animation_Jump;
import shop.xiaoda.utils.mobends.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import shop.xiaoda.utils.mobends.animation.*;
import shop.xiaoda.utils.mobends.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import shop.xiaoda.utils.mobends.animation.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import shop.xiaoda.utils.mobends.animation.spider.*;
import java.util.*;
import net.minecraft.client.entity.*;
import com.google.common.collect.*;

public class AnimatedEntity
{
    public static List<AnimatedEntity> animatedEntities;
    public static Map<String, RenderBendsPlayer> skinMap;
    public static RenderBendsPlayer playerRenderer;
    public static RenderBendsZombie zombieRenderer;
    public static RenderBendsSpider spiderRenderer;
    public String id;
    public String displayName;
    public Entity entity;
    public Class<? extends Entity> entityClass;
    public Render renderer;
    public List<Animation> animations;
    
    public AnimatedEntity(final String argID, final String argDisplayName, final Entity argEntity, final Class<? extends Entity> argClass, final Render argRenderer) {
        this.animations = new ArrayList<Animation>();
        this.id = argID;
        this.displayName = argDisplayName;
        this.entityClass = argClass;
        this.renderer = argRenderer;
        this.entity = argEntity;
    }
    
    public AnimatedEntity add(final Animation argGroup) {
        this.animations.add(argGroup);
        return this;
    }
    
    public static void register() {
        BendsLogger.log("Registering Animated Entities...", BendsLogger.INFO);
        AnimatedEntity.animatedEntities.clear();
        registerEntity(new AnimatedEntity("player", "Player", (Entity)Minecraft.getMinecraft().thePlayer, (Class<? extends Entity>)EntityPlayer.class, (Render)new RenderBendsPlayer(Minecraft.getMinecraft().getRenderManager())).add(new Animation_Stand()).add(new Animation_Walk()).add(new Animation_Sneak()).add(new Animation_Sprint()).add(new Animation_Jump()).add(new Animation_Attack()).add(new Animation_Swimming()).add(new Animation_Bow()).add(new Animation_Riding()).add(new Animation_Mining()).add(new Animation_Axe()));
        registerEntity(new AnimatedEntity("zombie", "Zombie", (Entity)new EntityZombie((World)null), (Class<? extends Entity>)EntityZombie.class, (Render)new RenderBendsZombie(Minecraft.getMinecraft().getRenderManager())).add(new shop.xiaoda.utils.mobends.animation.zombie.Animation_Stand()).add(new shop.xiaoda.utils.mobends.animation.zombie.Animation_Walk()));
        registerEntity(new AnimatedEntity("spider", "Spider", (Entity)new EntitySpider((World)null), (Class<? extends Entity>)EntitySpider.class, (Render)new RenderBendsSpider(Minecraft.getMinecraft().getRenderManager())).add(new Animation_OnGround()).add(new shop.xiaoda.utils.mobends.animation.spider.Animation_Jump()).add(new Animation_WallClimb()));
        AnimatedEntity.playerRenderer = new RenderBendsPlayer(Minecraft.getMinecraft().getRenderManager());
        AnimatedEntity.zombieRenderer = new RenderBendsZombie(Minecraft.getMinecraft().getRenderManager());
        AnimatedEntity.spiderRenderer = new RenderBendsSpider(Minecraft.getMinecraft().getRenderManager());
        AnimatedEntity.skinMap.put("default", AnimatedEntity.playerRenderer);
        AnimatedEntity.skinMap.put("slim", new RenderBendsPlayer(Minecraft.getMinecraft().getRenderManager(), true));
    }
    
    public static void registerEntity(final AnimatedEntity argEntity) {
        AnimatedEntity.animatedEntities.add(argEntity);
    }
    
    public Animation get(final String argName) {
        for (final Animation animation : this.animations) {
            if (animation.getName().equalsIgnoreCase(argName)) {
                return animation;
            }
        }
        return null;
    }
    
    public static AnimatedEntity getByEntity(final Entity argEntity) {
        for (int i = 0; i < AnimatedEntity.animatedEntities.size(); ++i) {
            if (AnimatedEntity.animatedEntities.get(i).entityClass.isInstance(argEntity)) {
                return AnimatedEntity.animatedEntities.get(i);
            }
        }
        return null;
    }
    
    public static RenderBendsPlayer getPlayerRenderer(final AbstractClientPlayer player) {
        final String s = player.getSkinType();
        final RenderBendsPlayer renderPlayer = AnimatedEntity.skinMap.get(s);
        return (renderPlayer != null) ? renderPlayer : AnimatedEntity.playerRenderer;
    }
    
    static {
        AnimatedEntity.animatedEntities = new ArrayList<AnimatedEntity>();
        AnimatedEntity.skinMap = Maps.newHashMap();
    }
}

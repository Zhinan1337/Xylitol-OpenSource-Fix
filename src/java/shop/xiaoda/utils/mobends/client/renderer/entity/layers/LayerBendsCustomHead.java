//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class LayerBendsCustomHead implements LayerRenderer<EntityLivingBase>
{
    private final ModelBendsPlayer model;
    
    public LayerBendsCustomHead(final ModelBendsPlayer p_i46120_1_) {
        this.model = p_i46120_1_;
    }
    
    public void doRenderLayer(final EntityLivingBase livingBase, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        final ItemStack itemstack = livingBase.getCurrentArmor(3);
        if (itemstack != null && itemstack.getItem() != null) {
            final Item item = itemstack.getItem();
            final Minecraft minecraft = Minecraft.getMinecraft();
            GlStateManager.pushMatrix();
            if (livingBase.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            final boolean flag = livingBase instanceof EntityVillager || (livingBase instanceof EntityZombie && ((EntityZombie)livingBase).isVillager());
            if (!flag && livingBase.isChild()) {
                final float f7 = 2.0f;
                final float f8 = 1.4f;
                GlStateManager.scale(f8 / f7, f8 / f7, f8 / f7);
                GlStateManager.translate(0.0f, 16.0f * scale, 0.0f);
            }
            this.model.bipedBody.postRender(0.0625f);
            this.model.bipedHead.postRender(0.0625f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (item instanceof ItemBlock) {
                final float f7 = 0.625f;
                GlStateManager.translate(0.0f, -0.25f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.scale(f7, -f7, -f7);
                if (flag) {
                    GlStateManager.translate(0.0f, 0.1875f, 0.0f);
                }
                minecraft.getItemRenderer().renderItem(livingBase, itemstack, ItemCameraTransforms.TransformType.HEAD);
            }
            else if (item == Items.skull) {
                final float f7 = 1.1875f;
                GlStateManager.scale(f7, -f7, -f7);
                if (flag) {
                    GlStateManager.translate(0.0f, 0.0625f, 0.0f);
                }
                GameProfile gameprofile = null;
                if (itemstack.hasTagCompound()) {
                    final NBTTagCompound nbttagcompound = itemstack.getTagCompound();
                    if (nbttagcompound.hasKey("SkullOwner", 10)) {
                        gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
                    }
                    else if (nbttagcompound.hasKey("SkullOwner", 8)) {
                        gameprofile = TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner")));
                        nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
                    }
                }
                TileEntitySkullRenderer.instance.renderSkull(-0.5f, 0.0f, -0.5f, EnumFacing.UP, 180.0f, itemstack.getMetadata(), gameprofile, -1);
            }
            GlStateManager.popMatrix();
        }
    }
    
    public boolean shouldCombineTextures() {
        return true;
    }
}

// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.utils.player;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.compatibility.util.vector.Vector2f;
import shop.xiaoda.Client;
import shop.xiaoda.utils.player.EnumFacingOffset;

public final class PlayerUtil {
    public static int getSpeedPotion() {
        return Client.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? Client.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
    }

    public static boolean isVoid() {
        for (double posY = Client.mc.thePlayer.posY; posY > 0.0; posY -= 1.0) {
            if (Client.mc.theWorld.getBlockState(new BlockPos(Client.mc.thePlayer.posX, posY, Client.mc.thePlayer.posZ)).getBlock() instanceof BlockAir) continue;
            return false;
        }
        return true;
    }

    public static boolean isBlockBlacklisted(Item item) {
        return item instanceof ItemAnvilBlock || item.getUnlocalizedName().contains("sand") || item.getUnlocalizedName().contains("gravel") || item.getUnlocalizedName().contains("ladder") || item.getUnlocalizedName().contains("tnt") || item.getUnlocalizedName().contains("chest") || item.getUnlocalizedName().contains("web");
    }

    public static BlockPos getBlockCorner(BlockPos start, BlockPos end) {
        for (int x = 0; x <= 1; ++x) {
            for (int y = 0; y <= 1; ++y) {
                for (int z = 0; z <= 1; ++z) {
                    BlockPos pos = new BlockPos(end.getX() + x, end.getY() + y, end.getZ() + z);
                    if (PlayerUtil.isBlockBetween(start, pos)) continue;
                    return pos;
                }
            }
        }
        return null;
    }

    public static boolean isBlockBetween(BlockPos start, BlockPos end) {
        int startX = start.getX();
        int startY = start.getY();
        int startZ = start.getZ();
        int endX = end.getX();
        int endY = end.getY();
        int endZ = end.getZ();
        double diffX = endX - startX;
        double diffY = endY - startY;
        double diffZ = endZ - startZ;
        double x = startX;
        double y = startY;
        double z = startZ;
        double STEP = 0.1;
        int STEPS = (int)Math.max(Math.abs(diffX), Math.max(Math.abs(diffY), Math.abs(diffZ))) * 4;
        for (int i = 0; i < STEPS - 1; ++i) {
            BlockPos pos;
            Block block;
            if ((x += diffX / (double)STEPS) == (double)endX && (y += diffY / (double)STEPS) == (double)endY && (z += diffZ / (double)STEPS) == (double)endZ || (block = Client.mc.theWorld.getBlockState(pos = new BlockPos(x, y, z)).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.water || block instanceof BlockVine || block instanceof BlockLadder) continue;
            return true;
        }
        return false;
    }

    public static float getMoveYaw(float yaw) {
        Vector2f from = new Vector2f((float)Client.mc.thePlayer.lastTickPosX, (float)Client.mc.thePlayer.lastTickPosZ);
        Vector2f to = new Vector2f((float)Client.mc.thePlayer.posX, (float)Client.mc.thePlayer.posZ);
        Vector2f diff = new Vector2f(to.x - from.x, to.y - from.y);
        double x = diff.x;
        double z = diff.y;
        if (x != 0.0 && z != 0.0) {
            yaw = (float)Math.toDegrees((Math.atan2(-x, z) + (double)MathHelper.PI2) % (double)MathHelper.PI2);
        }
        return yaw;
    }

    public static boolean isBlockUnder(double height) {
        return PlayerUtil.isBlockUnder(height, true);
    }

    public static boolean isBlockUnder(double height, boolean boundingBox) {
        if (boundingBox) {
            int offset = 0;
            while ((double)offset < height) {
                AxisAlignedBB bb = Client.mc.thePlayer.getEntityBoundingBox().offset(0.0, -offset, 0.0);
                if (!Client.mc.theWorld.getCollidingBoundingBoxes(Client.mc.thePlayer, bb).isEmpty()) {
                    return true;
                }
                offset += 2;
            }
        } else {
            int offset = 0;
            while ((double)offset < height) {
                if (PlayerUtil.blockRelativeToPlayer(0.0, -offset, 0.0).isFullBlock()) {
                    return true;
                }
                ++offset;
            }
        }
        return false;
    }

    public static Block blockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
        return Client.mc.theWorld.getBlockState(new BlockPos(Client.mc.thePlayer).add(offsetX, offsetY, offsetZ)).getBlock();
    }

    public static Vec3 getPlacePossibility(double offsetX, double offsetY, double offsetZ) {
        ArrayList<Vec3> possibilities = new ArrayList<Vec3>();
        int range = (int)(5.0 + (Math.abs(offsetX) + Math.abs(offsetZ)));
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    Block block = PlayerUtil.blockRelativeToPlayer(x, y, z);
                    if (block instanceof BlockAir) continue;
                    for (int x2 = -1; x2 <= 1; x2 += 2) {
                        possibilities.add(new Vec3(Client.mc.thePlayer.posX + (double)x + (double)x2, Client.mc.thePlayer.posY + (double)y, Client.mc.thePlayer.posZ + (double)z));
                    }
                    for (int y2 = -1; y2 <= 1; y2 += 2) {
                        possibilities.add(new Vec3(Client.mc.thePlayer.posX + (double)x, Client.mc.thePlayer.posY + (double)y + (double)y2, Client.mc.thePlayer.posZ + (double)z));
                    }
                    for (int z2 = -1; z2 <= 1; z2 += 2) {
                        possibilities.add(new Vec3(Client.mc.thePlayer.posX + (double)x, Client.mc.thePlayer.posY + (double)y, Client.mc.thePlayer.posZ + (double)z + (double)z2));
                    }
                }
            }
        }
        possibilities.removeIf(vec3 -> Client.mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord) > 5.0 || !(PlayerUtil.block(vec3.xCoord, vec3.yCoord, vec3.zCoord) instanceof BlockAir));
        if (possibilities.isEmpty()) {
            return null;
        }
        possibilities.sort(Comparator.comparingDouble(vec3 -> {
            double d0 = Client.mc.thePlayer.posX + offsetX - vec3.xCoord;
            double d1 = Client.mc.thePlayer.posY - 1.0 + offsetY - vec3.yCoord;
            double d2 = Client.mc.thePlayer.posZ + offsetZ - vec3.zCoord;
            return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
        }));
        return (Vec3)possibilities.get(0);
    }

    public static Block block(double x, double y, double z) {
        return Client.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static EnumFacingOffset getEnumFacing(Vec3 position) {
        for (int x2 = -1; x2 <= 1; x2 += 2) {
            if (PlayerUtil.block(position.xCoord + (double)x2, position.yCoord, position.zCoord) instanceof BlockAir) continue;
            if (x2 > 0) {
                return new EnumFacingOffset(EnumFacing.WEST, new Vec3(x2, 0.0, 0.0));
            }
            return new EnumFacingOffset(EnumFacing.EAST, new Vec3(x2, 0.0, 0.0));
        }
        for (int y2 = -1; y2 <= 1; y2 += 2) {
            if (PlayerUtil.block(position.xCoord, position.yCoord + (double)y2, position.zCoord) instanceof BlockAir || y2 >= 0) continue;
            return new EnumFacingOffset(EnumFacing.UP, new Vec3(0.0, y2, 0.0));
        }
        for (int z2 = -1; z2 <= 1; z2 += 2) {
            if (PlayerUtil.block(position.xCoord, position.yCoord, position.zCoord + (double)z2) instanceof BlockAir) continue;
            if (z2 < 0) {
                return new EnumFacingOffset(EnumFacing.SOUTH, new Vec3(0.0, 0.0, z2));
            }
            return new EnumFacingOffset(EnumFacing.NORTH, new Vec3(0.0, 0.0, z2));
        }
        return null;
    }

    public static int findSoup() {
        for (int i = 36; i < 45; ++i) {
            ItemStack itemStack = Client.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack == null || !itemStack.getItem().equals(Items.mushroom_stew) || itemStack.stackSize <= 0 || !(itemStack.getItem() instanceof ItemFood)) continue;
            return i;
        }
        return -1;
    }

    public static int findItem(int startSlot, int endSlot, Item item) {
        for (int i = startSlot; i < endSlot; ++i) {
            ItemStack stack = Client.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack == null || stack.getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public static boolean hasSpaceHotbar() {
        for (int i = 36; i < 45; ++i) {
            ItemStack itemStack = Client.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null) continue;
            return true;
        }
        return false;
    }

    public static boolean isOnGround(double height) {
        return !Client.mc.theWorld.getCollidingBoundingBoxes(Client.mc.thePlayer, Client.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public static Block getBlock(BlockPos pos) {
        return Client.mc.theWorld.getBlockState(pos).getBlock();
    }

    public static Block getBlock(double d, double d2, double d3) {
        return Client.mc.theWorld.getBlockState(new BlockPos(d, d2, d3)).getBlock();
    }

    public static boolean isOnGround(Entity entity, double height) {
        return !Client.mc.theWorld.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public static boolean colorTeam(EntityPlayer sb) {
        String targetName = sb.getDisplayName().getFormattedText().replace("\u00a7r", "");
        String clientName = Client.mc.thePlayer.getDisplayName().getFormattedText().replace("\u00a7r", "");
        return targetName.startsWith("\u00a7" + clientName.charAt(1));
    }

    public static boolean armorTeam(EntityPlayer entityPlayer) {
        if (Client.mc.thePlayer.inventory.armorInventory[3] != null && entityPlayer.inventory.armorInventory[3] != null) {
            ItemStack myHead = Client.mc.thePlayer.inventory.armorInventory[3];
            ItemArmor myItemArmor = (ItemArmor)myHead.getItem();
            ItemStack entityHead = entityPlayer.inventory.armorInventory[3];
            ItemArmor entityItemArmor = (ItemArmor)entityHead.getItem();
            if (String.valueOf(entityItemArmor.getColor(entityHead)).equals("10511680")) {
                return true;
            }
            return myItemArmor.getColor(myHead) == entityItemArmor.getColor(entityHead);
        }
        return false;
    }

    public static boolean scoreTeam(EntityPlayer entityPlayer) {
        return Client.mc.thePlayer.isOnSameTeam(entityPlayer);
    }

    public static boolean MovementInput() {
        return Client.mc.gameSettings.keyBindForward.isKeyDown() || Client.mc.gameSettings.keyBindLeft.isKeyDown() || Client.mc.gameSettings.keyBindRight.isKeyDown() || Client.mc.gameSettings.keyBindBack.isKeyDown();
    }

    public static boolean isInLiquid() {
        if (Client.mc.thePlayer.isInWater()) {
            return true;
        }
        boolean inLiquid = false;
        int y = (int)Client.mc.thePlayer.getEntityBoundingBox().minY;
        for (int x = MathHelper.floor_double(Client.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Client.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(Client.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Client.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = Client.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block == null || block.getMaterial() == Material.air) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                inLiquid = true;
            }
        }
        return inLiquid;
    }

    public static boolean isOnLiquid() {
        AxisAlignedBB boundingBox = Client.mc.thePlayer.getEntityBoundingBox();
        if (boundingBox == null) {
            return false;
        }
        boundingBox = boundingBox.contract(0.01, 0.0, 0.01).offset(0.0, -0.01, 0.0);
        boolean onLiquid = false;
        int y = (int)boundingBox.minY;
        for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX + 1.0); ++x) {
            for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ + 1.0); ++z) {
                Block block = Client.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block == Blocks.air) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                onLiquid = true;
            }
        }
        return onLiquid;
    }

    private PlayerUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

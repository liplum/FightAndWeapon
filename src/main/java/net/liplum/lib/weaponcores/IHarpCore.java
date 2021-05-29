package net.liplum.lib.weaponcores;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IHarpCore extends IMagicToolCore {
    /**
     * Gets the radius of the range
     *
     * @return the radius of the skill.
     */
    double getRadius();

    /**
     * It's equal to 60 minutes
     *
     * @return
     */
    default int getMaxUseDuration() {
        return 72000;
    }

    int getFrequency();

    /**
     * @param world
     * @param player
     * @param itemStack
     * @param hand
     * @param radius
     * @param abilityPower
     * @param releasedCount base on 1 (such as 1,2,3,4,5,...)
     * @return
     */
    boolean continueSkill(World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, double radius, float abilityPower, int releasedCount);

    boolean releaseSkill(World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, double radius, float abilityPower);
}

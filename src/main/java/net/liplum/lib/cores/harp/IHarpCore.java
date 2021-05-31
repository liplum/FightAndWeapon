package net.liplum.lib.cores.harp;

import net.liplum.api.weapon.IMagicToolCore;
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

    boolean continueSkill(ContinuousHarpArgs args);

    boolean releaseSkill(SingleHarpArgs args);
}

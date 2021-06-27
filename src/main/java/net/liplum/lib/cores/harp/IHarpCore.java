package net.liplum.lib.cores.harp;

import net.liplum.api.weapon.IMagicToolCore;
import net.liplum.items.weapons.harp.HarpItem;

import javax.annotation.Nonnull;

public interface IHarpCore extends IMagicToolCore {
    /**
     * Gets the radius of the range
     *
     * @return the radius of the skill.
     */
    double getRadius();

    /**
     * The default one equals 60 minutes
     *
     * @return
     */
    default int getMaxUseDuration() {
        return 72000;
    }

    int getFrequency();

    boolean continueSkill(ContinuousHarpArgs args);

    boolean releaseSkill(SingleHarpArgs args);

    @Nonnull
    @Override
    default Class<HarpItem> getWeaponType() {
        return HarpItem.class;
    }
}

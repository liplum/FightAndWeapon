package net.liplum.items.weapons.harp;

import net.liplum.api.weapon.WeaponSkillArgs;

public class ContinuousHarpArgs extends WeaponSkillArgs{
    private int releasedCount = 0;

    public ContinuousHarpArgs() {
    }

    /**
     * @return base on 1 (such as 1,2,3,4,5,...)
     */
    public int getReleasedCount() {
        return releasedCount;
    }

    /**
     * @param releasedCount base on 1 (such as 1,2,3,4,5,...)
     * @return
     */
    public ContinuousHarpArgs setReleasedCount(int releasedCount) {
        this.releasedCount = releasedCount;
        return this;
    }
}

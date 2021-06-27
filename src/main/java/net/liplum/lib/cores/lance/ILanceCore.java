package net.liplum.lib.cores.lance;

import net.liplum.api.weapon.IWeaponCore;
import net.liplum.items.weapons.lance.LanceItem;

import javax.annotation.Nonnull;

public interface ILanceCore extends IWeaponCore {
    boolean releaseSkill(LanceArgs args);

    /**
     * The double value of this is the true length of a sprint.
     *
     * @return
     */
    float getSprintLength();

    @Nonnull
    @Override
    default Class<LanceItem> getWeaponType() {
        return LanceItem.class;
    }
}

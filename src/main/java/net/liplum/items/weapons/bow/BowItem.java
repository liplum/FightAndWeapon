package net.liplum.items.weapons.bow;

import net.liplum.api.weapon.WeaponBaseItem;

import javax.annotation.Nonnull;

public class BowItem extends WeaponBaseItem {
    private final BowCore core;

    public BowItem(@Nonnull BowCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
        this.addProperty();
    }

    protected void addProperty() {

    }
}

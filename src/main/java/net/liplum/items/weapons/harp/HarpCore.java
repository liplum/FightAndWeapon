package net.liplum.items.weapons.harp;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.MagicToolCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class HarpCore extends MagicToolCore {

    @Override
    protected void build(WeaponCoreBuilder builder) {

    }

    @Override
    protected void initAllAttributes(List<Attribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.Harp.Frequency);
        attributes.add(Attributes.Harp.MaxUseDuration);
        attributes.add(Attributes.Harp.Radius);
    }

    public abstract boolean continueSkill(ContinuousHarpArgs args);

    public abstract boolean releaseSkill(SingleHarpArgs args);

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Harp;
    }
}

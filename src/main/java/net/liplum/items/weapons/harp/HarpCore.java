package net.liplum.items.weapons.harp;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.MagicToolCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.List;

import static net.liplum.Attributes.Continuous.MaxUseDuration;
import static net.liplum.Attributes.Harp.Frequency;
import static net.liplum.Attributes.Harp.Radius;

public abstract class HarpCore extends MagicToolCore {

    @Override
    protected void build(WeaponCoreBuilder builder) {
        super.build(builder);
    }

    @Override
    protected void initAllAttributes(List<IAttribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Frequency);
        attributes.add(MaxUseDuration);
        attributes.add(Radius);
    }

    public abstract boolean continueSkill(ContinuousHarpArgs args);

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Harp;
    }
}

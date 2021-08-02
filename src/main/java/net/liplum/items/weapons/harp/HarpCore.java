package net.liplum.items.weapons.harp;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.MagickToolCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.IAttribute;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;
import java.util.List;

import static net.liplum.Attributes.Generic.MaxUseDuration;
import static net.liplum.Attributes.Harp.Frequency;
import static net.liplum.Attributes.Harp.Radius;

public abstract class HarpCore extends MagickToolCore {

    public HarpCore(@Nonnull String registerName) {
        super(registerName);
    }

    public HarpCore(@Nonnull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                EnumAction.BOW
        );
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

package net.liplum.items.weapons.harp;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.MagickToolCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.IAttribute;
import net.minecraft.item.EnumAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.liplum.Attributes.Generic.MaxUseDuration;
import static net.liplum.Attributes.Harp.Frequency;
import static net.liplum.Attributes.Harp.Radius;

public abstract class HarpCore extends MagickToolCore {

    public HarpCore(@NotNull String registerName) {
        super(registerName);
    }

    public HarpCore(@NotNull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }

    @Override
    protected void build(@NotNull WeaponCoreBuilder builder) {
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

    @NotNull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Harp;
    }
}

package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.IAttribute;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;
import java.util.List;

import static net.liplum.Attributes.Generic.AttackSpeed;

public abstract class LanceCore extends WeaponCore {

    public LanceCore(@Nonnull String registerName) {
        super(registerName);
    }

    @Override
    protected void initAllAttributes(List<IAttribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.Lance.SprintStrength);
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                AttackSpeed, 2F
        ).set(
                EnumAction.BLOCK
        );
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Lance;
    }
}

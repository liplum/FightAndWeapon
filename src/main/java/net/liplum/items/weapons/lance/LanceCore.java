package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.List;

import static net.liplum.Attributes.Generic.AttackSpeed;

public abstract class LanceCore extends WeaponCore {

    @Override
    protected void initAllAttributes(List<Attribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.Lance.SprintStrength);
    }

    @Override
    protected void build(WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                AttackSpeed, AttackSpeed.newBasicAttrValue(2F)
        );
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Lance;
    }
}

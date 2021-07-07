package net.liplum.items.weapons.battleaxe;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

import static net.liplum.Attributes.Generic.AttackSpeed;

public abstract class BattleAxeCore extends WeaponCore {
    public abstract boolean releaseSkill(BattleAxeArgs args);


    @Override
    protected void initAllAttributes(List<Attribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.BattleAxe.SweepRange);
    }

    @Override
    protected void buildAttributes(AttributeBuilder builder) {
        builder.set(
                AttackSpeed, AttackSpeed.newBasicAttrValue(1.2F)
        );
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.BattleAxe;
    }
}

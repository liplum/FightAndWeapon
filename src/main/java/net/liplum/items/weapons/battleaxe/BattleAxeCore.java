package net.liplum.items.weapons.battleaxe;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public abstract class BattleAxeCore extends WeaponCore {
    public abstract boolean releaseSkill(BattleAxeArgs args);


    @Override
    protected List<Attribute> initAllAttributes() {
        LinkedList<Attribute> list = new LinkedList<>();
        list.add(Attributes.BattleAxe.SweepRange);
        return list;
    }

    @Override
    protected void buildAttributes(AttributeBuilder builder) {

    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.BattleAxe;
    }
}

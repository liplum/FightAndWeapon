package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public abstract class LanceCore extends WeaponCore {
    public abstract boolean releaseSkill(LanceArgs args);

    @Override
    protected List<Attribute> initAllAttributes() {
        LinkedList<Attribute> list = new LinkedList<>();
        list.add(Attributes.Lance.SprintStrength);
        return list;
    }

    @Override
    protected void buildAttributes(AttributeBuilder builder) {

    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Lance;
    }
}

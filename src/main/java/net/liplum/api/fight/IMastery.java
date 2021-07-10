package net.liplum.api.fight;

import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IMastery {
    @Nonnull
    String getRegisterName();

    @Nonnull
    WeaponType getWeaponType();

    @Nonnull
    Map<Attribute, AttrDelta> getAttributeAmplifier(int level);

    @Nonnull
    List<IPassiveSkill<?>> getPassiveSkills(int level);

    @Nonnull
    List<IActiveSkill> getActiveSkills(int level);
}
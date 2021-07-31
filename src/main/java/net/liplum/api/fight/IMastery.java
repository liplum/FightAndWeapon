package net.liplum.api.fight;

import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

public interface IMastery {
    @Nonnull
    default String getRegisterName() {
        return getWeaponType().getRegisterName();
    }

    @Nonnull
    WeaponType getWeaponType();

    @Nonnull
    Map<IAttribute, AttrDelta> getAttributeAmplifiers(int level);

    @Nonnull
    Set<IPassiveSkill<?>> getPassiveSkills(int level);

    @Nonnull
    UnlockedPSkillList getUnlockedPassiveSkills(int level);
}

package net.liplum.api.fight;

import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IMastery {
    @Nonnull
    default String getRegisterName() {
        return getWeaponType().getRegisterName();
    }

    @Nonnull
    WeaponType getWeaponType();

    @Nonnull
    Map<IAttribute, AttrDelta> getAttributeAmplifier(int level);

    @Nonnull
    List<IPassiveSkill<?>> getPassiveSkills(int level);

    @Nonnull
    UnlockedPSkillList getUnlockedPassiveSkills(int level);
}

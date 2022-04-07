package net.liplum.api.fight;

import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public interface IMastery {
    @NotNull
    default String getRegisterName() {
        return getWeaponType().getRegisterName();
    }

    @NotNull
    WeaponType getWeaponType();

    @NotNull
    Map<IAttribute, AttrDelta> getAttributeAmplifiers(int level);

    @NotNull
    Set<IPassiveSkill<?>> getPassiveSkills(int level);

    @NotNull
    UnlockedPSkillList getUnlockedPassiveSkills(int level);
}

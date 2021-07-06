package net.liplum.api.fight;

import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IMaster {
    @Nonnull
    String getRegisterName();

    @Nonnull
    WeaponType getWeaponType();

    @Nonnull
    Map<String, Number> getAttributeAmplifier(int level);

    @Nonnull
    List<IPassiveSkill<?>> getPassiveSkills(int level);

    @Nonnull
    List<IActiveSkill> getActiveSkills(int level);
}

package net.liplum.api.fight;

import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IMaster {
    @Nonnull
    String getRegisterName();

    @Nonnull
    Class<? extends WeaponBaseItem<?>> getWeaponType();

    @Nonnull
    Map<String, Number> getAttributeAmplifier(int level);

    @Nonnull
    List<IPassiveSkill<?>> getPassiveSkills(int level);

    @Nonnull
    List<IActiveSkill> getActiveSkills(int level);
}

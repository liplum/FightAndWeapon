package net.liplum.api.fight;

import net.liplum.lib.items.WeaponBaseItem;

import java.util.List;
import java.util.Map;

public interface IMaster {
    String getRegisterName();

    Class<? extends WeaponBaseItem<?>> getWeaponType();

    Map<String, Number> getAttributeAmplifier(int level);

    List<IPassiveSkill<?>> getPassiveSkills(int level);

    List<IActiveSkill> getActiveSkills(int level);
}

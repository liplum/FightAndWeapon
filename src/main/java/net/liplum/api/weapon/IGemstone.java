package net.liplum.api.weapon;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IGemstone {

    String getRegisterName();

    boolean hasModifierOf(IWeaponCore core);

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    IModifier<?> getModifierOf(IWeaponCore core);

    @Nonnull
    IPassiveSkill<?>[] getPassiveSkillsOf(IWeaponCore core);

    IGemstone addModifier(IModifier<?> newModifier);

    IGemstone addPassiveSkillToCore(IWeaponCore core, IPassiveSkill<?> newPassiveSkill);

    IGemstone addPassiveSkillToWeaponType(Class<? extends WeaponBaseItem<?>> weaponType, IPassiveSkill<?> newPassiveSkill);

    IGemstone addPassiveSkillToAll(IPassiveSkill<?> newPassiveSkill);

    IGemstone removeModifier(IWeaponCore core);

    IGemstone removeModifier(IModifier<?> modifier);

    IGemstone removePassiveSkillFromCore(IWeaponCore core, IPassiveSkill<?> passiveSkill);

    IGemstone removePassiveSkillFromWeaponType(Class<? extends WeaponBaseItem<?>> weaponType, IPassiveSkill<?> passiveSkill);

    IGemstone removePassiveSkillFromAll(IPassiveSkill<?> passiveSkill);

    boolean hasAnyAmplifier(IWeaponCore core);
}

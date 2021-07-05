package net.liplum.api.weapon;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IGemstone {

    String getRegisterName();

    boolean hasModifierOf(WeaponCore core);

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    Modifier<?> getModifierOf(WeaponCore core);

    @Nonnull
    IPassiveSkill<?>[] getPassiveSkillsOf(WeaponCore core);

    IGemstone addModifier(Modifier<?> newModifier);

    IGemstone addPassiveSkillToCore(WeaponCore core, IPassiveSkill<?> newPassiveSkill);

    IGemstone addPassiveSkillToWeaponType(Class<? extends WeaponBaseItem<?>> weaponType, IPassiveSkill<?> newPassiveSkill);

    IGemstone addPassiveSkillToAll(IPassiveSkill<?> newPassiveSkill);

    IGemstone removeModifier(WeaponCore core);

    IGemstone removeModifier(Modifier<?> modifier);

    IGemstone removePassiveSkillFromCore(WeaponCore core, IPassiveSkill<?> passiveSkill);

    IGemstone removePassiveSkillFromWeaponType(Class<? extends WeaponBaseItem<?>> weaponType, IPassiveSkill<?> passiveSkill);

    IGemstone removePassiveSkillFromAll(IPassiveSkill<?> passiveSkill);

    boolean hasAnyAmplifier(WeaponCore core);
}

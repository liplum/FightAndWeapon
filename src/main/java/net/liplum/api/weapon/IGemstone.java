package net.liplum.api.weapon;

import net.liplum.api.fight.IPassiveSkill;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface IGemstone {

    String getRegisterName();

    boolean hasModifierOf(WeaponCore core);

    /**
     * Check whether this weapon core has this passive skill in the gemstone<br/>
     * NOTE:It will check the exclusive skills of weapon core + the exclusive skills of its weapon type + the skills of all
     *
     * @param core         the weapon core
     * @param passiveSkill the passive skill you want to check
     * @return whether this weapon core has this passive skill
     */
    boolean hasPassiveSkillOfCore(WeaponCore core, IPassiveSkill<?> passiveSkill);

    /**
     * Check whether this weapon core has any passive skill in the gemstone<br/>
     * NOTE:It will check the exclusive skills of weapon core + the exclusive skills of its weapon type + the skills of all
     *
     * @param core the weapon core
     * @return whether this weapon core has any passive skill
     */
    boolean hasAnyPassiveSkillOfCore(WeaponCore core);

    /**
     * Check whether this weapon type has this passive skill in the gemstone<br/>
     * NOTE:It will check the exclusive skills of its weapon type + the skills of all
     *
     * @param weaponType   the weapon type
     * @param passiveSkill the passive skill you want to check
     * @return whether this weapon type has this passive skill
     */
    boolean hasPassiveSkillOfWeaponType(WeaponType weaponType, IPassiveSkill<?> passiveSkill);

    /**
     * Check whether this weapon type has any passive skill in the gemstone<br/>
     * NOTE:It will check the exclusive skills of its weapon type + the skills of all
     *
     * @param weaponType the weapon type
     * @return whether this weapon type has any passive skill
     */
    boolean hasAnyPassiveSkillOfWeaponType(WeaponType weaponType);

    /**
     * Check whether the gemstone has this passive skill<br/>
     * NOTE:It will check the skills of all
     *
     * @param passiveSkill the passive skill you want to check
     * @return whether the gemstone has this passive skill
     */
    boolean hasPassiveSkillOfAll(IPassiveSkill<?> passiveSkill);

    /**
     * Check whether the gemstone has any passive skill<br/>
     * NOTE:It will check the skills of all
     *
     * @return whether the gemstone has any passive skill
     */
    boolean hasAnyPassiveSkillOfAll();

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    Modifier getModifierOf(WeaponCore core);

    @Nonnull
    Collection<IPassiveSkill<?>> getPassiveSkillsOf(WeaponCore core);

    IGemstone addModifier(Modifier newModifier);

    default IGemstone addModifiers(Collection<Modifier> newModifiers) {
        for (Modifier modifier : newModifiers) {
            this.addModifier(modifier);
        }
        return this;
    }

    default IGemstone addModifiers(Modifier... newModifiers) {
        for (Modifier modifier : newModifiers) {
            this.addModifier(modifier);
        }
        return this;
    }

    IGemstone addPassiveSkillToCore(WeaponCore core, IPassiveSkill<?> newPassiveSkill);

    IGemstone addPassiveSkillToWeaponType(WeaponType weaponType, IPassiveSkill<?> newPassiveSkill);

    IGemstone addPassiveSkillToAll(IPassiveSkill<?> newPassiveSkill);

    IGemstone removeModifier(WeaponCore core);

    IGemstone removeModifier(Modifier modifier);

    IGemstone removePassiveSkillFromCore(WeaponCore core, IPassiveSkill<?> passiveSkill);

    IGemstone removePassiveSkillFromWeaponType(WeaponType weaponType, IPassiveSkill<?> passiveSkill);

    IGemstone removePassiveSkillFromAll(IPassiveSkill<?> passiveSkill);

    boolean hasAnyAmplifier(WeaponCore core);
}

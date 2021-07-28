package net.liplum.masteries;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;
import net.liplum.lib.math.MathUtil;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface IMasteryDetail {
    @Nonnull
    LvExpPair getLvAndExp(@Nonnull IMastery mastery);

    default int getLevel(@Nonnull IMastery mastery) {
        return getLvAndExp(mastery).getLevel();
    }

    default int getLevel(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getLevel(mastery);
        }
        return LvExpPair.BaseLevel;
    }

    default int getExp(@Nonnull IMastery mastery) {
        return MathUtil.removeDigit(getLvAndExp(mastery).getExp(),1);
    }

    default int getExp(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getExp(mastery);
        }
        return LvExpPair.BaseExp;
    }

    /**
     * @return how many level it upgraded
     */
    int addExp(@Nonnull IMastery mastery, int amount);

    /**
     * @return how many level it upgraded
     */
    default int addExp(@Nonnull WeaponType weaponType, int amount) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return addExp(mastery, amount);
        }
        return 0;
    }

    @Nonnull
    Map<IAttribute, AttrDelta> getAttributeAmplifier(@Nonnull IMastery mastery);

    @Nonnull
    UnlockedPSkillList getUnlockedPassiveSkills(@Nonnull IMastery mastery);

    @Nonnull
    List<IPassiveSkill<?>> getPassiveSkills(@Nonnull IMastery mastery);

    @Nonnull
    default Map<IAttribute, AttrDelta> getAttributeAmplifier(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getAttributeAmplifier(mastery);
        }
        return Collections.emptyMap();
    }

    @Nonnull
    default UnlockedPSkillList getUnlockedPassiveSkills(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getUnlockedPassiveSkills(mastery);
        }
        return UnlockedPSkillList.Empty;
    }

    @Nonnull
    default List<IPassiveSkill<?>> getPassiveSkills(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getPassiveSkills(mastery);
        }
        return Collections.emptyList();
    }
}

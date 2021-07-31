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
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

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
        return MathUtil.removeDigit(getLvAndExp(mastery).getExp(), 1);
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
    Map<IAttribute, AttrDelta> getAttrAmp(@Nonnull IMastery mastery);

    @Nonnull
    UnlockedPSkillList getUnlockedPSkills(@Nonnull IMastery mastery);

    @Nonnull
    Set<IPassiveSkill<?>> getPassiveSkills(@Nonnull IMastery mastery);

    @Nonnull
    default Map<IAttribute, AttrDelta> getAttrAmp(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getAttrAmp(mastery);
        }
        return Collections.emptyMap();
    }

    @Nonnull
    default UnlockedPSkillList getUnlockedPSkills(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getUnlockedPSkills(mastery);
        }
        return UnlockedPSkillList.Empty;
    }

    @Nonnull
    default Set<IPassiveSkill<?>> getPassiveSkills(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getPassiveSkills(mastery);
        }
        return Collections.emptySet();
    }

    @Nonnull
    default AttrDelta getAttrAmpValue(@Nonnull IMastery mastery, @Nonnull IAttribute attribute) {
        Map<IAttribute, AttrDelta> map = this.getAttrAmp(mastery);
        AttrDelta res = map.get(attribute);
        return res == null ? attribute.emptyAttrDelta() : res;
    }

    @Nonnull
    default AttrDelta getAttrAmpValue(@Nonnull WeaponType weaponType, @Nonnull IAttribute attribute) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getAttrAmpValue(mastery, attribute);
        }
        return attribute.emptyAttrDelta();
    }

    @Nullable
    Map<String, LvExpPair> getAllMasteries();

    void resetMastery(@Nonnull IMastery mastery);

    default void resetMastery(@Nonnull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            resetMastery(mastery);
        }
    }

    void sync();
}

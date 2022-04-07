package net.liplum.masteries;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;
import net.liplum.lib.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public interface IMasteryDetail {
    @NotNull
    LvExpPair getLvAndExp(@NotNull IMastery mastery);

    default int getLevel(@NotNull IMastery mastery) {
        return getLvAndExp(mastery).getLevel();
    }

    default int getLevel(@NotNull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getLevel(mastery);
        }
        return LvExpPair.BaseLevel;
    }

    default int getExp(@NotNull IMastery mastery) {
        return MathUtil.removeDigit(getLvAndExp(mastery).getExp(), 1);
    }

    default int getExp(@NotNull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getExp(mastery);
        }
        return LvExpPair.BaseExp;
    }

    /**
     * @return how many level it upgraded
     */
    int addExp(@NotNull IMastery mastery, int amount);

    /**
     * @return how many level it upgraded
     */
    default int addExp(@NotNull WeaponType weaponType, int amount) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return addExp(mastery, amount);
        }
        return 0;
    }

    @NotNull
    Map<IAttribute, AttrDelta> getAttrAmp(@NotNull IMastery mastery);

    @NotNull
    UnlockedPSkillList getUnlockedPSkills(@NotNull IMastery mastery);

    @NotNull
    Set<IPassiveSkill<?>> getPassiveSkills(@NotNull IMastery mastery);

    @NotNull
    default Map<IAttribute, AttrDelta> getAttrAmp(@NotNull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getAttrAmp(mastery);
        }
        return Collections.emptyMap();
    }

    @NotNull
    default UnlockedPSkillList getUnlockedPSkills(@NotNull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getUnlockedPSkills(mastery);
        }
        return UnlockedPSkillList.Empty;
    }

    @NotNull
    default Set<IPassiveSkill<?>> getPassiveSkills(@NotNull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getPassiveSkills(mastery);
        }
        return Collections.emptySet();
    }

    @NotNull
    default AttrDelta getAttrAmpValue(@NotNull IMastery mastery, @NotNull IAttribute attribute) {
        Map<IAttribute, AttrDelta> map = this.getAttrAmp(mastery);
        AttrDelta res = map.get(attribute);
        return res == null ? attribute.emptyAttrDelta() : res;
    }

    @NotNull
    default AttrDelta getAttrAmpValue(@NotNull WeaponType weaponType, @NotNull IAttribute attribute) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getAttrAmpValue(mastery, attribute);
        }
        return attribute.emptyAttrDelta();
    }

    @Nullable
    Map<String, LvExpPair> getAllMasteries();

    void resetMastery(@NotNull IMastery mastery);

    default void resetMastery(@NotNull WeaponType weaponType) {
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            resetMastery(mastery);
        }
    }

    void sync();
}

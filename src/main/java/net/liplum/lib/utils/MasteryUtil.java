package net.liplum.lib.utils;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.registeies.WeaponTypeRegistry;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.masteries.LvExpPair;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public final class MasteryUtil {
    private static final int MaxLevel = 99;
    private static final long[] RequiredExpSheet = new long[MaxLevel];//default maximum is 100.
    private static final long BaseRequiredExp = 100;

    public static void init() {
        initRequiredExpSheet();
    }

    private static void initRequiredExpSheet() {
        int length = RequiredExpSheet.length;
        for (int i = 0; i < length; i++) {
            RequiredExpSheet[i] = curExp(i + 1);
        }
    }

    public static long getRequiredExpToUpgrade(int currentLevel) throws IllegalArgumentException {
        if (currentLevel < 1 || currentLevel > RequiredExpSheet.length) {
            throw new IllegalArgumentException();
        }
        return RequiredExpSheet[currentLevel - 1];
    }

    private static long curExp(int endLevel) {
        long total = BaseRequiredExp;
        for (int i = 1; i < endLevel; i++) {
            total += BaseRequiredExp + 0.1 * ((long) endLevel * endLevel);
        }
        return total;
    }

    public static boolean tryUpgrade(LvExpPair lvAndExp) throws IllegalArgumentException {
        int lv = lvAndExp.getLevel();
        long exp = lvAndExp.getExp();
        long required = getRequiredExpToUpgrade(lv);
        if (exp >= required) {
            exp -= required;
            lvAndExp.setLevel(lv + 1);
            lvAndExp.setExp(exp);
            return true;
        }
        return false;
    }

    public static boolean canUpgrade(LvExpPair lvAndExp) {
        int lv = lvAndExp.getLevel();
        long exp = lvAndExp.getExp();
        long required = getRequiredExpToUpgrade(lv);
        return exp >= required;
    }

    public static void addExp(@Nonnull EntityPlayer player, @Nonnull WeaponType weaponType, int amount) {
        //TODO:Add mastery exp
    }

    @Nonnull
    public static LvExpPair getMaster(@Nonnull MasteryCapability masteryCapability, @Nonnull String masterName) {
        return masteryCapability.getLevelAndExp(masterName);
    }

    @Nonnull
    private static Set<IPassiveSkill<?>> getPassiveSkills(@Nonnull EntityPlayer player, @Nonnull IMastery master, @Nonnull WeaponCore weaponCore) {
        MasteryCapability masteryCapability = player.getCapability(CapabilityRegistry.Mastery_Capability, null);
        HashSet<IPassiveSkill<?>> res = new HashSet<>();
        if (masteryCapability != null) {
            LvExpPair lvAndExp = getMaster(masteryCapability, master.getRegisterName());
            int lv = lvAndExp.getLevel();
            addPassiveSkillsFromMasteryGiven(res, lv, master);
            addPassiveSkillsFromUnlock(res, lv, master, weaponCore);
        }
        return res;
    }

    private static void addPassiveSkillsFromMasteryGiven(@Nonnull HashSet<IPassiveSkill<?>> result, int level, @Nonnull IMastery master) {
        List<IPassiveSkill<?>> fromGiven = master.getPassiveSkills(level);
        result.addAll(fromGiven);
        UnlockedPSkillList unlock = master.getUnlockedPassiveSkills(level);
    }

    private static void addPassiveSkillsFromUnlock(@Nonnull HashSet<IPassiveSkill<?>> result, int level, @Nonnull IMastery master, @Nonnull WeaponCore weaponCore) {
        UnlockedPSkillList unlock = master.getUnlockedPassiveSkills(level);
        List<IPassiveSkill<?>> fromUnlock = weaponCore.unlockPassiveSkills(unlock);
        result.addAll(fromUnlock);
    }

    @Nonnull
    public static Collection<IPassiveSkill<?>> getPassiveSkills(@Nonnull EntityPlayer player, @Nonnull WeaponCore weaponCore) {
        WeaponType weaponType = weaponCore.getWeaponType();
        IMastery mastery = MasteryRegistry.getMasterOf(weaponType);
        if (mastery != null) {
            return getPassiveSkills(player, mastery, weaponCore);
        }
        return new HashSet<>();
    }

    public static AttrDelta getAttributeValue(@Nonnull MasteryCapability masteryCapability, @Nonnull WeaponType weaponType, @Nonnull IAttribute attribute) {
        IMastery mastery = MasteryRegistry.getMasterOf(weaponType);
        if (mastery != null) {
            LvExpPair levelAndExp = masteryCapability.getLevelAndExp(mastery.getRegisterName());
            int lv = levelAndExp.getLevel();
            Map<IAttribute, AttrDelta> amplifiers = mastery.getAttributeAmplifier(lv);
            AttrDelta res = amplifiers.get(attribute);
            if (res != null) {
                return res;
            }
        }
        return attribute.emptyAttrDelta();
    }

    @Nullable
    public static IMastery getMasterOf(@Nonnull String registerName) {
        WeaponType weaponType = WeaponTypeRegistry.getWeaponTypeOf(registerName);
        if (weaponType != null) {
            return MasteryRegistry.getMasterOf(weaponType);
        }
        return null;
    }
}
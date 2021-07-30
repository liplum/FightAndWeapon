package net.liplum.lib.utils;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.masteries.IMasteryDetail;
import net.liplum.masteries.LvExpPair;
import net.liplum.masteries.Mastery;
import net.liplum.masteries.MasteryDetail;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockMelon;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.*;

public final class MasteryUtil {
    private static final int[] RequiredExpSheet = new int[Mastery.MaxLevel];//default maximum is 100.
    private static final int BaseRequiredExp = 100;

    public static void init() {
        initRequiredExpSheet();
    }

    private static void initRequiredExpSheet() {
        int length = RequiredExpSheet.length;
        for (int i = 0; i < length; i++) {
            RequiredExpSheet[i] = curExp(i + 1);
        }
    }

    public static int getRequiredExpToUpgrade(int currentLevel) throws IllegalArgumentException {
        if (currentLevel < 1 || currentLevel > RequiredExpSheet.length) {
            throw new IllegalArgumentException();
        }
        return RequiredExpSheet[currentLevel - 1];
    }

    private static int curExp(int endLevel) {
        int total = BaseRequiredExp;
        for (int i = 1; i < endLevel; i++) {
            total += BaseRequiredExp + 0.1 * (endLevel * endLevel);
        }
        //Expands 10 times to avoid the decimal
        return total * 10;
    }

    /**
     * @return how many level it upgraded
     */
    public static int tryUpgrade(LvExpPair lvAndExp) {
        int lv = lvAndExp.getLevel();
        int exp = lvAndExp.getExp();
        int required = getRequiredExpToUpgrade(lv);
        int upgraded = 0;

        while (exp >= required) {
            exp -= required;
            lvAndExp.setLevel(lv + 1);
            lvAndExp.setExp(exp);
            upgraded++;

            lv = lvAndExp.getLevel();
            exp = lvAndExp.getExp();
            required = getRequiredExpToUpgrade(lv);
        }

        return upgraded;
    }

    public static boolean canUpgrade(LvExpPair lvAndExp) {
        int lv = lvAndExp.getLevel();
        int exp = lvAndExp.getExp();
        int required = getRequiredExpToUpgrade(lv);
        return exp >= required;
    }

    /**
     * @return how many level it upgraded
     */
    public static int addExp(@Nonnull EntityPlayer player, @Nonnull WeaponType weaponType, int amount) {
        return MasteryDetail.create(player).addExp(weaponType, amount);
    }

    /**
     * @return how many level it upgraded
     */
    public static int addExp(@Nonnull EntityPlayer player, @Nonnull IMastery mastery, int amount) {
        return MasteryDetail.create(player).addExp(mastery, amount);
    }

    @Nonnull
    private static Set<IPassiveSkill<?>> getPassiveSkills(@Nonnull EntityPlayer player, @Nonnull IMastery mastery, @Nonnull WeaponCore weaponCore) {
        HashSet<IPassiveSkill<?>> res = new HashSet<>();
        IMasteryDetail detail = MasteryDetail.create(player);
        addPassiveSkillsFromMasteryGiven(res, detail, mastery);
        addPassiveSkillsFromUnlock(res, detail, mastery, weaponCore);
        return res;
    }

    private static void addPassiveSkillsFromMasteryGiven(@Nonnull HashSet<IPassiveSkill<?>> result, @Nonnull IMasteryDetail detail, @Nonnull IMastery mastery) {
        Set<IPassiveSkill<?>> fromGiven = detail.getPassiveSkills(mastery);
        result.addAll(fromGiven);
    }

    private static void addPassiveSkillsFromUnlock(@Nonnull HashSet<IPassiveSkill<?>> result, @Nonnull IMasteryDetail detail, @Nonnull IMastery mastery, @Nonnull WeaponCore weaponCore) {
        UnlockedPSkillList unlock = detail.getUnlockedPSkills(mastery);
        List<IPassiveSkill<?>> fromUnlock = weaponCore.unlockPassiveSkills(unlock);
        result.addAll(fromUnlock);
    }

    @Nonnull
    public static Collection<IPassiveSkill<?>> getPassiveSkills(@Nonnull EntityPlayer player, @Nonnull WeaponCore weaponCore) {
        WeaponType weaponType = weaponCore.getWeaponType();
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getPassiveSkills(player, mastery, weaponCore);
        }
        return Collections.emptyList();
    }
}
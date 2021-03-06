package net.liplum.lib.utils;

import net.liplum.I18ns;
import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.masteries.IMasteryDetail;
import net.liplum.masteries.LvExpPair;
import net.liplum.masteries.Mastery;
import net.liplum.masteries.MasteryDetail;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        try {
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
        } catch (IllegalArgumentException e) {
            return 0;
        }
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
    public static int addExp(@NotNull EntityPlayer player, @NotNull WeaponType weaponType, int amount) {
        return MasteryDetail.create(player).addExp(weaponType, amount);
    }

    /**
     * @return how many level it upgraded
     */
    public static int addExp(@NotNull EntityPlayer player, @NotNull IMastery mastery, int amount) {
        return MasteryDetail.create(player).addExp(mastery, amount);
    }

    @NotNull
    private static Set<IPassiveSkill<?>> getPassiveSkills(@NotNull EntityPlayer player, @NotNull IMastery mastery, @NotNull WeaponCore weaponCore) {
        HashSet<IPassiveSkill<?>> res = new HashSet<>();
        IMasteryDetail detail = MasteryDetail.create(player);
        addPassiveSkillsFromMasteryGiven(res, detail, mastery);
        addPassiveSkillsFromUnlock(res, detail, mastery, weaponCore);
        return res;
    }

    private static void addPassiveSkillsFromMasteryGiven(@NotNull Set<IPassiveSkill<?>> result, @NotNull IMasteryDetail detail, @NotNull IMastery mastery) {
        Set<IPassiveSkill<?>> fromGiven = detail.getPassiveSkills(mastery);
        result.addAll(fromGiven);
    }

    private static void addPassiveSkillsFromUnlock(@NotNull Set<IPassiveSkill<?>> result, @NotNull IMasteryDetail detail, @NotNull IMastery mastery, @NotNull WeaponCore weaponCore) {
        UnlockedPSkillList unlock = detail.getUnlockedPSkills(mastery);
        Set<IPassiveSkill<?>> fromUnlock = weaponCore.unlockPassiveSkills(unlock);
        result.addAll(fromUnlock);
    }

    @NotNull
    public static Collection<IPassiveSkill<?>> getPassiveSkills(@NotNull EntityPlayer player, @NotNull WeaponCore weaponCore) {
        WeaponType weaponType = weaponCore.getWeaponType();
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponType);
        if (mastery != null) {
            return getPassiveSkills(player, mastery, weaponCore);
        }
        return Collections.emptyList();
    }

    public static void showAllMasteries(@NotNull EntityPlayer player) {
        IMasteryDetail detail = MasteryDetail.create(player);
        String lvI18n = I18n.format(I18ns.Command.Mastery_Show_Level);
        String expI18n = I18n.format(I18ns.Command.Mastery_Show_Exp);
        for (IMastery mastery : MasteryRegistry.getAllMasteries()) {
            int lv = detail.getLevel(mastery);
            int exp = detail.getExp(mastery);
            TextComponentString text = new TextComponentString(
                    I18n.format(FawI18n.getNameI18nKey(mastery.getWeaponType())) + ": " +
                            lvI18n + " " + lv + "," +
                            expI18n + " " + exp
            );
            player.sendMessage(text);
        }
    }

    public static void showMastery(@NotNull EntityPlayer player, @NotNull IMastery mastery) {
        IMasteryDetail detail = MasteryDetail.create(player);
        int lv = detail.getLevel(mastery);
        int exp = detail.getExp(mastery);
        TextComponentString text = new TextComponentString(
                I18n.format(FawI18n.getNameI18nKey(mastery.getWeaponType())) + ": " +
                        I18n.format(I18ns.Command.Mastery_Show_Level) + " " + lv + "," +
                        I18n.format(I18ns.Command.Mastery_Show_Exp) + " " + exp
        );
        player.sendMessage(text);
    }
}
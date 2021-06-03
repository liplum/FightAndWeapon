package net.liplum.lib.utils;

import net.liplum.lib.masters.LvExpPair;

public final class MasterUtil {
    private static int MaxLevel = 100;
    private static long[] RequiredExpSheet = new long[100];//default maximum is 100.
    private static long BaseRequiredExp = 100;

    public static void init() {
        initRequiredExpSheet();
    }

    private static void initRequiredExpSheet() {
        int length = RequiredExpSheet.length;
        for (int i = 0; i < length; i++) {
            RequiredExpSheet[i] = curExp(BaseRequiredExp, i + 1);
        }
    }

    public static long getRequiredExpToUpgrade(int currentLevel) throws IllegalArgumentException {
        if (currentLevel < 1 || currentLevel > RequiredExpSheet.length) {
            throw new IllegalArgumentException();
        }
        return RequiredExpSheet[currentLevel - 1];
    }

    private static long curExp(long base, int endLevel) {
        long total = base;
        for (int i = 0; i < endLevel; i++) {
            total += base + (1 / 10) * (endLevel * endLevel);
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

}

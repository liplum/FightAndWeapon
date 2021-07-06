package net.liplum.lib.utils;

import net.liplum.api.fight.IMaster;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.MasterRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.Attribute;
import net.liplum.capabilities.MasterCapability;
import net.liplum.masters.LvExpPair;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MasterUtil {
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

    @Nonnull
    public static LvExpPair getMaster(@Nonnull MasterCapability masterCapability, @Nonnull String masterName) {
        return masterCapability.getLevelAndExp(masterName);
    }

    public static Set<IPassiveSkill<?>> getPassiveSkills(@Nonnull EntityPlayer player, @Nonnull IMaster master) {
        MasterCapability masterCapability = player.getCapability(CapabilityRegistry.Master_Capability, null);
        if (masterCapability != null) {
            LvExpPair lvAndExp = getMaster(masterCapability, master.getRegisterName());
            int lv = lvAndExp.getLevel();
            return new HashSet<>(master.getPassiveSkills(lv));
        }
        return new HashSet<>();
    }

    @Nonnull
    public static Set<IPassiveSkill<?>> getPassiveSkills(@Nonnull EntityPlayer player, @Nonnull WeaponType weaponType) {
        IMaster master = MasterRegistry.getMasterOf(weaponType);
        if (master != null) {
            return getPassiveSkills(player, master);
        }
        return new HashSet<>();
    }

    public static AttrDelta getAttributeValue(@Nonnull MasterCapability masterCapability, @Nonnull WeaponType weaponType, @Nonnull Attribute attribute) {
        IMaster master = MasterRegistry.getMasterOf(weaponType);
        if (master != null) {
            LvExpPair levelAndExp = masterCapability.getLevelAndExp(master.getRegisterName());
            int lv = levelAndExp.getLevel();
            Map<String, Number> attributeAmplifier = master.getAttributeAmplifier(lv);
            Number value = attributeAmplifier.get(attribute.getRegisterName());
            return attribute.newAttrDelta(value);
        }
        return attribute.genAttrDelta();
    }
}
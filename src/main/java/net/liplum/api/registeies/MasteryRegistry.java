package net.liplum.api.registeies;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.IMastery;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Developing
public final class MasteryRegistry {
    @Nonnull
    private static final Map<WeaponType, IMastery> WeaponTypeMasteryMap = new HashMap<>();
    @Nonnull
    private static final Map<String, IMastery> MasteryMap = new HashMap<>();

    @Nonnull
    public static IMastery register(@Nonnull IMastery master) {
        WeaponTypeMasteryMap.put(master.getWeaponType(), master);
        MasteryMap.put(master.getRegisterName(), master);
        return master;
    }

    @Nonnull
    public static List<String> getAllMasteryNames() {
        return WeaponTypeRegistry.getAllWeaponTypeNames();
    }


    @Nullable
    public static IMastery getMasteryOf(@Nonnull WeaponType weaponType) {
        return WeaponTypeMasteryMap.get(weaponType);
    }

    @Nullable
    public static IMastery getMasteryOf(@Nonnull String registerName) {
        return MasteryMap.get(registerName);
    }

    @Nonnull
    @LongSupport
    public static Collection<IMastery> getAllMasteries() {
        return MasteryMap.values();
    }
}

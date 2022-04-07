package net.liplum.api.registeies;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.IMastery;
import net.liplum.api.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Developing
public final class MasteryRegistry {
    @NotNull
    private static final Map<WeaponType, IMastery> WeaponTypeMasteryMap = new HashMap<>();
    @NotNull
    private static final Map<String, IMastery> MasteryMap = new HashMap<>();

    @NotNull
    public static IMastery register(@NotNull IMastery master) {
        WeaponTypeMasteryMap.put(master.getWeaponType(), master);
        MasteryMap.put(master.getRegisterName(), master);
        return master;
    }

    @NotNull
    public static List<String> getAllMasteryNames() {
        return WeaponTypeRegistry.getAllWeaponTypeNames();
    }


    @Nullable
    public static IMastery getMasteryOf(@NotNull WeaponType weaponType) {
        return WeaponTypeMasteryMap.get(weaponType);
    }

    @Nullable
    public static IMastery getMasteryOf(@NotNull String registerName) {
        return MasteryMap.get(registerName);
    }

    @NotNull
    @LongSupport
    public static Collection<IMastery> getAllMasteries() {
        return MasteryMap.values();
    }
}

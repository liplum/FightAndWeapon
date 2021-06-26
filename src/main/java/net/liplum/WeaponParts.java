package net.liplum;

import net.liplum.api.registeies.WeaponPartRegistry;
import net.liplum.api.weapon.WeaponPart;

public final class WeaponParts {
    private static int ID = 0;
    public static final WeaponPart Chinese_Lance_Head = withCast(new WeaponPart(ID++, "chinese_lance_head"));
    public static final WeaponPart Knight_Lance_Head = withCast(new WeaponPart(ID++, "knight_lance_head"));

    private static WeaponPart withCast(WeaponPart part) {
        return WeaponPartRegistry.register(part, WeaponPartRegistry.RegisterType.Has_Cast);
    }

    private static WeaponPart noCast(WeaponPart part) {
        return WeaponPartRegistry.register(part, WeaponPartRegistry.RegisterType.No_Cast);
    }

    //You must call it to load this class and all the static fields.
    public static void load() {
    }
}

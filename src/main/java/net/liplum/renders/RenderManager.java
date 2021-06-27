package net.liplum.renders;

import net.liplum.api.registeies.WeaponPartRegistry;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.registeies.ModelRegistry;

public final class RenderManager {

    public static void init() {
        for (WeaponPart weaponPart : WeaponPartRegistry.getAllWeaponParts()) {
            ModelRegistry.registerWeaponPart(weaponPart);
        }
    }
}

package net.liplum.registries;

import net.liplum.Names;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.lib.FawLocation;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ModelRegistry {
    public static void registerWeaponPart(@NotNull WeaponPart weaponPart) {
        String registerName = weaponPart.getRegisterName();
        ResourceLocation location = new FawLocation(
                Names.Special.Parts + '/' + registerName
        );

    }
}

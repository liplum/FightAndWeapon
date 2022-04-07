package net.liplum.api.weapon;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WeaponPart {
    private final String registerName;
    private final int ID;

    public WeaponPart(int id, String registerName) {
        ID = id;
        this.registerName = registerName;
    }

    @NotNull
    public String getRegisterName() {
        return registerName;
    }

    public int getID() {
        return ID;
    }

    public boolean isPart(@NotNull ItemStack itemStack) {
        return true;
    }
}

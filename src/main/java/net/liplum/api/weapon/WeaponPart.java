package net.liplum.api.weapon;

import net.minecraft.item.ItemStack;

public class WeaponPart {
    private final String registerName;

    public WeaponPart(String registerName) {
        this.registerName = registerName;
    }

    public String getRegisterName(){
        return registerName;
    }

    public boolean isPart(ItemStack itemStack){
        return true;
    }
}

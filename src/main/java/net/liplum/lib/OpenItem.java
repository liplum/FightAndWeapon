package net.liplum.lib;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.UUID;

public class OpenItem extends Item {
    @Nonnull
    public static UUID getAttackSpeedModifierUUID(){
        return Item.ATTACK_SPEED_MODIFIER;
    }
    @Nonnull
    public static UUID getAttackDamageModifierUUID(){
        return Item.ATTACK_SPEED_MODIFIER;
    }
}

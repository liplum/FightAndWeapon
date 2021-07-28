package net.liplum;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.masteries.Behavior;
import net.liplum.masteries.BehaviorHandler;
import net.liplum.masteries.IBehaviorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

import javax.annotation.Nonnull;

public final class BehaviorHandlers {
    public static final IBehaviorHandler OnWeaponUse = new BehaviorHandler(Behavior.UseWeapon) {
        @Override
        public void handle(@Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack, Object... args) {
            StatBase state = StatList.getObjectUseStats(weapon);
            if (state != null) {
                player.addStat(state);
            }
        }
    };

    //You must call it to load this class and all the static fields.
    public static void init(){

    }
}

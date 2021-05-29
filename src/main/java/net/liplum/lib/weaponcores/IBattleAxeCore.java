package net.liplum.lib.weaponcores;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IBattleAxeCore extends IWeaponCore {
    boolean releaseSkill(World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, float strength, float sweepRange);

    float getSweepRange();
}

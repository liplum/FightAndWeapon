package net.liplum.lib.modifiers;

import net.liplum.lib.weaponcores.IBattleAxeCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class BattleAxeModifier extends Modifier<IBattleAxeCore>{
    public boolean releaseSkill(IBattleAxeCore core, World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, float strength, float sweepRange) {
        return core.releaseSkill(world, player, itemStack, hand, strength, sweepRange);
    }

    public float getSweepRangeDelta() {
        return 0;
    }

    public float getSweepRate() {
        return 0;
    }
}

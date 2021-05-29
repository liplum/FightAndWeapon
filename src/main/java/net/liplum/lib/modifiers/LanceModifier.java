package net.liplum.lib.modifiers;

import net.liplum.lib.weaponcores.ILanceCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class LanceModifier extends Modifier<ILanceCore> {
    public float getSprintLengthDelta() {
        return 0;
    }

    public float getSprintLengthRate() {
        return 0;
    }

    public boolean releaseSkill(ILanceCore core, World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, float strength, float sprintLength){
        return core.releaseSkill(world,player,itemStack,hand,strength,sprintLength);
    }
}

package net.liplum.lib.modifiers;

import net.liplum.lib.weaponcores.ILanceCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class LanceModifier extends Modifier {
    public float getSprintLengthDelta() {
        return 0;
    }

    public float getSprintLengthRate() {
        return 0;
    }

    public boolean releaseSkill(ILanceCore weaponCore, World world, EntityPlayer player, ItemStack itemStack, EnumHand handIn, float strength, float sprintLength){
        return weaponCore.releaseSkill(world,player,itemStack,handIn,strength,sprintLength);
    }
}

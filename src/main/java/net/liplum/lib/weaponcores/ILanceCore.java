package net.liplum.lib.weaponcores;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface ILanceCore extends IWeaponCore{
    boolean releaseSkill(World world, EntityPlayer player, ItemStack itemStack, EnumHand handIn, float strength, float sprintLength);
    /**
     * The double value of this is the true length of a sprint.
     *
     * @return
     */
    float getSprintLength();
}

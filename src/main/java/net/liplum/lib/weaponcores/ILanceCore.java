package net.liplum.lib.weaponcores;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface ILanceCore extends IWeaponCore{
    boolean releaseSkill(World world, EntityPlayer player, EnumHand handIn, float sprintLength);
    /**
     * The double value of this is the true length of a sprint.
     *
     * @return
     */
    float getSprintLength();
}

package net.liplum.lib.weaponcores;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface ILanceCore{
    boolean releaseLanceSkill(World world, EntityPlayer player, EnumHand handIn, float sprintLength);
    /**
     * Gets the cool down time of weapon.
     *
     * @return The cool down time of weapon(by tick)
     */
    int getCoolDown();

    /**
     * The double value of this is the true length of a sprint.
     *
     * @return
     */
    float getSprintLength();
}

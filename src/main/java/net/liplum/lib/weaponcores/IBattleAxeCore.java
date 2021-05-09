package net.liplum.lib.weaponcores;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IBattleAxeCore {
    void releaseBattleAxeSkill(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);
}

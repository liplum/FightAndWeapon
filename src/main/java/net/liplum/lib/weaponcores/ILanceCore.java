package net.liplum.lib.weaponcores;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface ILanceCore{
    void releaseLanceSkill(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);

}

package net.liplum.lib.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IHarpModifier {
    double getUseRadiusModifier();

    double getSkillRadiusModifier();

    int getCoolDownModifier();

    void doExtraUseEffect();

    void doExtraSkillEffect(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);
}

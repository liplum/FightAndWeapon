package net.liplum.lib.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IHarpModifier {
    double getHarpUseRadiusModifier();

    double getHarpSkillRadiusModifier();

    int getHarpCoolDownModifier();

    void doHarpExtraUseEffect();

    void doHarpExtraSkillEffect(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);
}

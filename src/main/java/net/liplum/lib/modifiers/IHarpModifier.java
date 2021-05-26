package net.liplum.lib.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IHarpModifier extends IModifier {
    double getUseRadiusModifier();

    double getSkillRadiusModifier();

    int getCoolDownModifier();

    void doExtraUseEffect();

    void doExtraSkillEffect(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);
}

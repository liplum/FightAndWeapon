package net.liplum.lib.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class IHarpModifier extends Modifier {
    public abstract double getUseRadiusModifier();

    public abstract double getSkillRadiusModifier();

    public abstract int getCoolDownModifier();

    public abstract void doExtraUseEffect();

    public abstract void doExtraSkillEffect(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);
}

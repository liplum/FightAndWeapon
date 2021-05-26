package net.liplum.items.weapons;

import net.liplum.lib.modifiers.IHarpModifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public final class EmptyModifier {
    public static IHarpModifier EmptyHarpModifier = new IHarpModifier() {
        @Override
        public double getUseRadiusModifier() {
            return 0;
        }

        @Override
        public double getSkillRadiusModifier() {
            return 0;
        }

        @Override
        public int getCoolDownModifier() {
            return 0;
        }

        @Override
        public void doExtraUseEffect() {

        }

        @Override
        public void doExtraSkillEffect(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target) {

        }
    };
}

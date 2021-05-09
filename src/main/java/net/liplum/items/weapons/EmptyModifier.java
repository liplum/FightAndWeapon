package net.liplum.items.weapons;

import net.liplum.lib.modifiers.IHarpModifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public final class EmptyModifier {
    public static IHarpModifier EmptyHarpModifier = new IHarpModifier() {
        @Override
        public double getHarpUseRadiusModifier() {
            return 0;
        }

        @Override
        public double getHarpSkillRadiusModifier() {
            return 0;
        }

        @Override
        public int getHarpCoolDownModifier() {
            return 0;
        }

        @Override
        public void doHarpExtraUseEffect() {

        }

        @Override
        public void doHarpExtraSkillEffect(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target) {

        }
    };
}

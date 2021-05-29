package net.liplum.lib.modifiers;

import net.liplum.lib.weaponcores.IHarpCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class HarpModifier extends Modifier<IHarpCore> {
    public double getRadiusDelta() {
        return 0;
    }

    public double getRadiusRate() {
        return 0;
    }

    public int getMaxUseDelta(){
        return 0;
    }
    public float getMaxUseRate(){
        return 0;
    }

    public int getFrequencyDelta(){
        return 0;
    }

    public float getFrequencyRate(){
        return 0;
    }

    public boolean releaseSkill(IHarpCore core, World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, double radius, float abilityPower,int releasedCount) {
        return core.continueSkill(world, player, itemStack, hand, radius, abilityPower,releasedCount);
    }
}

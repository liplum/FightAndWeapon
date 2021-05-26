package net.liplum.lib.registeies.modifier;

import net.liplum.lib.modifiers.ILanceModifier;
import net.liplum.lib.weaponcores.ILanceCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public final class LanceRegistry extends ModifierRegistry<ILanceCore, ILanceModifier> {
    private static LanceRegistry instance;

    public static LanceRegistry Instance() {
        return instance;
    }

    public boolean releaseSkill(ILanceCore core, World world, EntityPlayer player, EnumHand handIn, float sprintLength){
        return false;
    }

}

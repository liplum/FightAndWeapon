package net.liplum.events.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FawWeaponLeftClickEvent extends Event implements FawArgsGetter {
    @Nonnull
    protected final EntityPlayer player;
    @Nonnull
    protected final WeaponBaseItem weapon;
    @Nullable
    protected final Modifier modifier;
    @Nonnull
    protected final ItemStack itemStack;

    public FawWeaponLeftClickEvent(@Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nonnull ItemStack itemStack) {
        this.player = player;
        this.weapon = weapon;
        this.modifier = modifier;
        this.itemStack = itemStack;
    }

    @Nonnull
    public World world() {
        return player.world;
    }

    @Override
    @Nonnull
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @Nullable
    @Override
    public Modifier modifier() {
        return modifier;
    }

    @Override
    @Nonnull
    public ItemStack itemStack() {
        return itemStack;
    }

    @Nonnull
    @Override
    public EntityLivingBase entity() {
        return player;
    }
}

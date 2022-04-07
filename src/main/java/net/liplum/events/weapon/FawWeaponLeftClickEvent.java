package net.liplum.events.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FawWeaponLeftClickEvent extends Event implements FawArgsGetter {
    @NotNull
    protected final EntityPlayer player;
    @NotNull
    protected final WeaponBaseItem weapon;
    @Nullable
    protected final Modifier modifier;
    @NotNull
    protected final ItemStack itemStack;

    public FawWeaponLeftClickEvent(@NotNull EntityPlayer player, @NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @NotNull ItemStack itemStack) {
        this.player = player;
        this.weapon = weapon;
        this.modifier = modifier;
        this.itemStack = itemStack;
    }

    @NotNull
    public World world() {
        return player.world;
    }

    @NotNull
    @Override
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @Nullable
    @Override
    public Modifier modifier() {
        return modifier;
    }

    @Override
    @NotNull
    public ItemStack itemStack() {
        return itemStack;
    }

    @NotNull
    @Override
    public EntityLivingBase entity() {
        return player;
    }
}

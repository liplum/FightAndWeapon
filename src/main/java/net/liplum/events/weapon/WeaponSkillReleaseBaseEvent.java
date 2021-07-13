package net.liplum.events.weapon;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class WeaponSkillReleaseBaseEvent extends Event {
    protected final World world;
    protected final EntityPlayer player;
    protected final WeaponBaseItem weapon;
    protected final Modifier modifier;
    protected final ItemStack itemStack;
    protected final EnumHand hand;

    public WeaponSkillReleaseBaseEvent(@Nonnull World world, @Nonnull EntityPlayer player,
                                       @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier,
                                       @Nonnull ItemStack itemStack, @Nonnull EnumHand hand) {
        this.world = world;
        this.player = player;
        this.weapon = weapon;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.hand = hand;
    }

    public World getWorld() {
        return world;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public WeaponBaseItem getWeapon() {
        return weapon;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public EnumHand getHand() {
        return hand;
    }
}

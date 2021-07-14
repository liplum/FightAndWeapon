package net.liplum.events.weapon;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class WeaponSkillReleaseEvent extends Event {
    protected final World world;
    protected final EntityPlayer player;
    protected final WeaponBaseItem weapon;
    protected final Modifier modifier;
    protected final ItemStack itemStack;
    protected final EnumHand hand;

    public WeaponSkillReleaseEvent(@Nonnull World world, @Nonnull EntityPlayer player,
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

    public static class Post extends WeaponSkillReleaseEvent {

        public Post(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nonnull ItemStack itemStack, @Nonnull EnumHand hand) {
            super(world, player, weapon, modifier, itemStack, hand);
        }
    }

    /**
     * If you cancel it then the player can't release the skill this time.
     */

    @Cancelable
    public static class Pre extends WeaponSkillReleaseEvent {

        public Pre(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nonnull ItemStack itemStack, @Nonnull EnumHand hand) {
            super(world, player, weapon, modifier, itemStack, hand);
        }
    }
}

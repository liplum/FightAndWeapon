package net.liplum.events.weapon;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@LongSupport
public abstract class WeaponSkillReleaseEvent extends Event implements FawArgsGetter {
    protected final World world;
    protected final EntityLivingBase entity;
    protected final WeaponBaseItem weapon;
    protected final Modifier modifier;
    protected final ItemStack itemStack;
    protected final EnumHand hand;

    public WeaponSkillReleaseEvent(@NotNull World world, @NotNull EntityLivingBase entity,
                                   @NotNull WeaponBaseItem weapon, @Nullable Modifier modifier,
                                   @NotNull ItemStack itemStack, @NotNull EnumHand hand) {
        this.world = world;
        this.entity = entity;
        this.weapon = weapon;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.hand = hand;
    }

    @NotNull
    public World world() {
        return world;
    }

    @Override
    @NotNull
    public EntityLivingBase entity() {
        return entity;
    }

    @NotNull
    @Override
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @Override
    @Nullable
    public Modifier modifier() {
        return modifier;
    }

    @Override
    @NotNull
    public ItemStack itemStack() {
        return itemStack;
    }

    @NotNull
    public EnumHand hand() {
        return hand;
    }

    @LongSupport
    public static class Post extends WeaponSkillReleaseEvent {

        public Post(@NotNull World world, @NotNull EntityLivingBase player, @NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @NotNull ItemStack itemStack, @NotNull EnumHand hand) {
            super(world, player, weapon, modifier, itemStack, hand);
        }
    }

    /**
     * If you cancel it then the player can't release the skill this time.
     */
    @LongSupport
    @Cancelable
    public static class Pre extends WeaponSkillReleaseEvent {

        public Pre(@NotNull World world, @NotNull EntityLivingBase player, @NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @NotNull ItemStack itemStack, @NotNull EnumHand hand) {
            super(world, player, weapon, modifier, itemStack, hand);
        }
    }
}

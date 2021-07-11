package net.liplum.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeaponSkillArgs {
    private World world = null;
    private EntityPlayer player = null;
    private WeaponBaseItem weapon = null;
    private ItemStack itemStack = null;
    private Modifier modifier = null;
    private EnumHand hand = null;

    /**
     * @return the modifier or null if this weapon didn't have a modifier.
     */
    @Nullable
    public Modifier getModifier() {
        return modifier;
    }

    @Nonnull
    public WeaponSkillArgs setModifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    @Nonnull
    public World getWorld() {
        return world;
    }

    @Nonnull
    public WeaponSkillArgs setWorld(@Nonnull World world) {
        this.world = world;
        return this;
    }

    @Nonnull
    public EntityPlayer getPlayer() {
        return player;
    }

    @Nonnull
    public WeaponSkillArgs setPlayer(@Nonnull EntityPlayer player) {
        this.player = player;
        return this;
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Nonnull
    public WeaponSkillArgs setItemStack(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    @Nonnull
    public EnumHand getHand() {
        return hand;
    }

    @Nonnull
    public WeaponSkillArgs setHand(@Nonnull EnumHand hand) {
        this.hand = hand;
        return this;
    }

    public WeaponBaseItem getWeapon() {
        return weapon;
    }

    public WeaponType getWeaponType() {
        return weapon.getWeaponType();
    }

    public WeaponCore getWeaponCore() {
        return weapon.getCore();
    }

    public WeaponSkillArgs setWeapon(WeaponBaseItem weapon) {
        this.weapon = weapon;
        return this;
    }
}

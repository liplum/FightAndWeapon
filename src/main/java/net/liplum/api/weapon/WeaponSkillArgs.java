package net.liplum.api.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.fight.FawArgsSetter;
import net.liplum.attributes.AttrCalculator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeaponSkillArgs implements FawArgsGetter, FawArgsSetter<WeaponSkillArgs> {
    private World world = null;
    private EntityPlayer player = null;
    private WeaponBaseItem weapon = null;
    private ItemStack itemStack = null;
    private Modifier modifier = null;
    private EnumHand hand = null;
    private AttrCalculator calculator = null;

    /**
     * @return the modifier or null if this weapon didn't have a modifier.
     */
    @Nullable
    @Override
    public Modifier modifier() {
        return modifier;
    }

    @Nonnull
    @Override
    public WeaponSkillArgs modifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    @Nonnull
    public World world() {
        return world;
    }

    @Nonnull
    public WeaponSkillArgs world(@Nonnull World world) {
        this.world = world;
        return this;
    }

    @Nonnull
    @Override
    public EntityPlayer entity() {
        return player;
    }

    @Nonnull
    public WeaponSkillArgs entity(@Nonnull EntityPlayer player) {
        this.player = player;
        return this;
    }

    @Nonnull
    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @Nonnull
    @Override
    public WeaponSkillArgs itemStack(@Nonnull ItemStack itemStack) {
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

    public WeaponSkillArgs setWeapon(WeaponBaseItem weapon) {
        this.weapon = weapon;
        return this;
    }

    public WeaponType getWeaponType() {
        return weapon.getWeaponType();
    }

    public WeaponCore getWeaponCore() {
        return weapon.getCore();
    }

    public AttrCalculator getCalculator() {
        return calculator;
    }

    public WeaponSkillArgs setCalculator(AttrCalculator calculator) {
        this.calculator = calculator;
        return this;
    }
}

package net.liplum.api.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.fight.FawArgsSetter;
import net.liplum.attributes.AttrCalculator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeaponSkillArgs implements FawArgsGetter, FawArgsSetter<WeaponSkillArgs> {
    private World world = null;
    private EntityLivingBase entity = null;
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
    public EntityLivingBase entity() {
        return entity;
    }

    @Nonnull
    public WeaponSkillArgs entity(@Nonnull EntityLivingBase entity) {
        this.entity = entity;
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
    public EnumHand hand() {
        return hand;
    }

    @Nonnull
    public WeaponSkillArgs hand(@Nonnull EnumHand hand) {
        this.hand = hand;
        return this;
    }

    @Nonnull
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @Override
    @Nonnull
    public WeaponSkillArgs weapon(@Nonnull WeaponBaseItem weapon) {
        this.weapon = weapon;
        return this;
    }

    @Nonnull
    public AttrCalculator calculator() {
        return calculator;
    }

    @Nonnull
    public WeaponSkillArgs calculator(AttrCalculator calculator) {
        this.calculator = calculator;
        return this;
    }
}

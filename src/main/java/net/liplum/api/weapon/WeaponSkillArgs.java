package net.liplum.api.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.fight.FawArgsSetter;
import net.liplum.attributes.AttrCalculator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public WeaponSkillArgs modifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    @NotNull
    public World world() {
        return world;
    }

    @NotNull
    public WeaponSkillArgs world(@NotNull World world) {
        this.world = world;
        return this;
    }

    @NotNull
    @Override
    public EntityLivingBase entity() {
        return entity;
    }

    @NotNull
    public WeaponSkillArgs entity(@NotNull EntityLivingBase entity) {
        this.entity = entity;
        return this;
    }

    @NotNull
    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @NotNull
    @Override
    public WeaponSkillArgs itemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    @NotNull
    public EnumHand hand() {
        return hand;
    }

    @NotNull
    public WeaponSkillArgs hand(@NotNull EnumHand hand) {
        this.hand = hand;
        return this;
    }

    @NotNull
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @NotNull
    @Override
    public WeaponSkillArgs weapon(@NotNull WeaponBaseItem weapon) {
        this.weapon = weapon;
        return this;
    }

    @NotNull
    public AttrCalculator calculator() {
        return calculator;
    }

    @NotNull
    public WeaponSkillArgs calculator(AttrCalculator calculator) {
        this.calculator = calculator;
        return this;
    }
}

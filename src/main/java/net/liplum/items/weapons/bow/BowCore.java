package net.liplum.items.weapons.bow;

import net.liplum.WeaponTypes;
import net.liplum.api.annotations.Developing;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.lib.utils.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static net.liplum.Attributes.Generic.MaxUseDuration;

@Developing
public abstract class BowCore extends WeaponCore {
    private final boolean checkPulling;

    public BowCore(@Nonnull String registerName, boolean checkPulling) {
        super(registerName);
        this.checkPulling = checkPulling;
    }

    public BowCore(@Nonnull String registerName) {
        this(registerName, false);
    }

    public BowCore(@Nonnull String registerName, boolean checkPulling, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
        this.checkPulling = checkPulling;
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Bow;
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                EnumAction.BOW
        ).set(
                MaxUseDuration, MaxUseDuration.newBasicAttrValue(72000)
        );
    }

    public boolean isAmmo(@Nonnull ItemStack itemStack) {
        return ItemUtil.isArrow(itemStack);
    }

    @Nonnull
    public ItemStack getDefaultAmmo(@Nonnull EntityLivingBase entity, @Nonnull ItemStack itemStack) {
        return new ItemStack(Items.ARROW);
    }

    public boolean isCheckPulling() {
        return checkPulling;
    }

    public void onPulling(@Nonnull PullingBowArgs args) {
    }

    public boolean canShoot(int ticks) {
        return ticks > 10;
    }

    public abstract float computeDamage(float originalDamage, int tick);

    public int fullPullingTime(){
        return 1;
    }

}

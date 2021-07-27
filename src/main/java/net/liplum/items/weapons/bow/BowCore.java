package net.liplum.items.weapons.bow;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;

import static net.liplum.Attributes.Generic.MaxUseDuration;

public abstract class BowCore extends WeaponCore {
    private final boolean checkPulling;

    public BowCore(boolean checkPulling) {
        this.checkPulling = checkPulling;
    }

    public BowCore() {
        this(false);
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

    public boolean isCheckPulling() {
        return checkPulling;
    }

    public void onPulling(PullingBowArgs args){}
}

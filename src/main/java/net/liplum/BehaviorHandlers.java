package net.liplum;

import net.liplum.api.annotations.OnlyWhenInitialization;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.masteries.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import org.jetbrains.annotations.NotNull;

public final class BehaviorHandlers {
    public static final IBehaviorHandler OnWeaponUse = new BehaviorHandler(Behavior.UseWeapon) {
        @Override
        public void handle(@NotNull EntityPlayer player, @NotNull WeaponBaseItem weapon, @NotNull ItemStack itemStack, Object... args) {
            StatBase state = StatList.getObjectUseStats(weapon);
            if (state != null) {
                player.addStat(state);
            }
        }
    };

    public static final IBehaviorHandler OnCauseDamage = new BehaviorHandler(Behavior.CauseDamage) {
        /**
         * The var-args are {@code Entity target, float damage}.
         */
        @Override
        public void handle(@NotNull EntityPlayer player, @NotNull WeaponBaseItem weapon, @NotNull ItemStack itemStack, Object... args) {
            Entity target = (Entity) args[0];
            float damage = (float) args[1];
            int exp = (int) damage * 5;
            IMasteryDetail detail = MasteryDetail.create(player);
            detail.addExp(weapon.getWeaponType(), exp);
        }
    };

    public static final IBehaviorHandler OnKillMob = new BehaviorHandler(Behavior.KillEntity) {
        /**
         * The var-args are {@code Entity target, float damage}.
         */
        @Override
        public void handle(@NotNull EntityPlayer player, @NotNull WeaponBaseItem weapon, @NotNull ItemStack itemStack, Object... args) {
            Entity target = (Entity) args[0];
            if (target instanceof EntityLivingBase && !(target instanceof EntityPlayer)) {
                EntityLivingBase entity = (EntityLivingBase) target;
                float maxHp = entity.getMaxHealth();
                int exp = (int) (5 + maxHp * 0.08) * 10;
                IMasteryDetail detail = MasteryDetail.create(player);
                detail.addExp(weapon.getWeaponType(), exp);
            }
        }
    };

    public static final IBehaviorHandler OnHarpHealing = new BehaviorHandler(Behaviors.HarpHealing) {
        /**
         * The var-args are {@code Float amount}
         */
        @Override
        public void handle(@NotNull EntityPlayer player, @NotNull WeaponBaseItem weapon, @NotNull ItemStack itemStack, Object... args) {
            if (weapon.getWeaponType() == WeaponTypes.Harp) {

            }
        }
    };

    public static final IBehaviorHandler OnHarpBuff = new BehaviorHandler(Behaviors.HarpBuff) {
        /**
         * The var-args are {@code ?}
         */
        @Override
        public void handle(@NotNull EntityPlayer player, @NotNull WeaponBaseItem weapon, @NotNull ItemStack itemStack, Object... args) {
            if (weapon.getWeaponType() == WeaponTypes.Harp) {

            }
        }
    };

    //You must call it to load this class and all the static fields.
    @OnlyWhenInitialization
    public static void init() {

    }
}

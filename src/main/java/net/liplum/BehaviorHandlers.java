package net.liplum;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.masteries.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

import javax.annotation.Nonnull;

public final class BehaviorHandlers {
    public static final IBehaviorHandler OnWeaponUse = new BehaviorHandler(Behavior.UseWeapon) {
        @Override
        public void handle(@Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack, Object... args) {
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
        public void handle(@Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack, Object... args) {
            Entity target = (Entity) args[0];
            float damage = (float) args[1];
            int exp = (int) damage;
            IMasteryDetail detail = MasteryDetail.create(player);
            detail.addExp(weapon.getWeaponType(), exp);
        }
    };
    public static final IBehaviorHandler OnKillMob = new BehaviorHandler(Behavior.KillEntity) {
        /**
         * The var-args are {@code Entity target, float damage}.
         */
        @Override
        public void handle(@Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack, Object... args) {
            Entity target = (Entity) args[0];
            if (target instanceof EntityLivingBase && !(target instanceof EntityPlayer)) {
                EntityLivingBase entity = (EntityLivingBase) target;
                float maxHp = entity.getMaxHealth();
                int exp = (int) (5 + maxHp * 0.05) * 10;
                IMasteryDetail detail = MasteryDetail.create(player);
                detail.addExp(weapon.getWeaponType(), exp);
            }
        }
    };

    //You must call it to load this class and all the static fields.
    public static void init() {

    }
}

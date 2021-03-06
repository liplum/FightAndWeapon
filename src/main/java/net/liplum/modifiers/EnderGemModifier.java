package net.liplum.modifiers;

import net.liplum.api.annotations.Developing;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.items.weapons.lance.LanceCore;
import net.liplum.items.weapons.lance.LanceCores;
import net.liplum.items.weapons.lance.LanceModifier;
import net.liplum.lib.math.P2D;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.PhysicsUtil;
import net.liplum.lib.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.liplum.Attributes.Generic.Strength;
import static net.liplum.Attributes.Lance.SprintStrength;

@Developing
public final class EnderGemModifier {
    public final static LanceModifier Light_Lance = new LanceModifier() {
        @Override
        public LanceCore getCore() {
            return LanceCores.LightLance;
        }

        @Override
        protected void build(@NotNull Modifier.ModifierBuilder builder) {
            super.build(builder);
            builder.set(
                    SprintStrength, 2F, 0
            );
        }

        @Override
        public boolean releaseSkill(@NotNull WeaponCore core, @NotNull WeaponSkillArgs args) {
            World world = args.world();
            EntityLivingBase player = args.entity();
            AttrCalculator calculator = args.calculator();
            WeaponBaseItem weapon = args.weapon();
            ItemStack itemStack = args.itemStack();

            float strength = calculator.calcu(Strength).getFloat();
            float sprintLength = calculator.calcu(SprintStrength).getFloat();

            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sprintLength, 0.25D, sprintLength));
            Vector2D look = P2D.toV2D(player.getLookVec());
            int damagedCount = 0;
            for (EntityLivingBase e : allInRange) {
                if (EntityUtil.canAttack(player, e) && P2D.isInside(look, P2D.toPosition(player), P2D.toPosition(e), 2, sprintLength)) {

                    weapon.dealDamage(EntityUtil.genFawDamage(player, itemStack), e, strength);
                    damagedCount++;
                }
            }
            FawItemUtil.damageWeapon(weapon, itemStack, damagedCount, player);
            Vec3d originPos = player.getPositionVector();
            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(sprintLength);
            Vec3d nowPos = new Vec3d(sprintForce.x + originPos.x, originPos.y, sprintForce.z + originPos.z);
            PhysicsUtil.setPosition(player, nowPos.x, nowPos.y + 0.5, nowPos.z);
            if (world.isRemote) {
                for (int i = 0; i < 32; ++i) {
                    world.spawnParticle(EnumParticleTypes.PORTAL,
                            nowPos.x,
                            nowPos.y + Utils.getRandom().nextDouble() * 2.0D,
                            nowPos.z,
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D,
                            -Utils.getRandom().nextDouble(),
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D);
                    /*world.spawnParticle(EnumParticleTypes.PORTAL,
                            nowPos.x + (Utils.getRandom().nextDouble() - 0.5D) * 2,
                            nowPos.y + Utils.getRandom().nextDouble() * 2 - 0.25D,
                            nowPos.z + (Utils.getRandom().nextDouble() - 0.5D) * 2,
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D,
                            -Utils.getRandom().nextDouble(),
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D);*/
                }
            }
            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1F, 1F);
            return true;
        }
    };
}

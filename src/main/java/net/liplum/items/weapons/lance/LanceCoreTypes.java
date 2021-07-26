package net.liplum.items.weapons.lance;

import net.liplum.Names;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.coroutine.Coroutine;
import net.liplum.coroutine.IWaitable;
import net.liplum.coroutine.WaitForNextTick;
import net.liplum.enumerator.Yield;
import net.liplum.events.skill.LanceSprintEvent;
import net.liplum.lib.coroutine.CoroutineSystem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.P2D;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.PhysicsTool;
import net.liplum.registeies.PotionRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.liplum.Attributes.Generic.*;
import static net.liplum.Attributes.Lance.SprintStrength;

public final class LanceCoreTypes {
    public static final LanceCore Empty = new LanceCore() {

        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.setHasWeaponSkill(false);
        }

    };

    public static final LanceCore TrainingLance = new LanceCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.setRegisterName(
                    Names.Item.Lance.TrainingLanceItem
            ).set(
                    Strength, Strength.newBasicAttrValue(5)
            ).setHasWeaponSkill(false);
        }
    };

    public static final LanceCore LightLance = new LanceCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            boolean canceled = MinecraftForge.EVENT_BUS.post(new LanceSprintEvent(args));
            if (canceled) {
                return false;
            }
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();
            AttrCalculator calculator = args.getCalculator();

            float strength = calculator.calcu(Strength).getFloat();
            float sprintLength = calculator.calcu(SprintStrength).getFloat();

            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(MathHelper.sqrt(sprintLength));
            PhysicsTool.setMotion(player, sprintForce.x, 0.32, sprintForce.z);
            if (!world.isRemote) {
                player.addPotionEffect(new PotionEffect(PotionRegistry.Unstoppable_Potion, 15, 0, false, false));
                CoroutineSystem.Instance().attachCoroutineToPlayer(player, new Yield<IWaitable>() {
                    final Set<EntityLivingBase> damaged = new HashSet<>();

                    @Override
                    protected void runTask() {
                        AxisAlignedBB playerBox = player.getEntityBoundingBox();
                        List<EntityLivingBase> allInRange = world
                                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(0.25D, 0.25D, 0.25D));
                        for (EntityLivingBase e : allInRange) {
                            if (!damaged.contains(e) && EntityUtil.canAttack(player, e)) {
                                e.attackEntityFrom(DamageSource.causePlayerDamage(player), strength);
                                damaged.add(e);
                                FawItemUtil.damageWeapon(args.getWeapon(), args.getItemStack(), 1, player);
                            }
                        }
                        yieldReturn(new WaitForNextTick());
                    }
                }, 25);
            }
            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.setRegisterName(
                    Names.Item.Lance.LightLanceItem
            ).set(
                    //  It means you can dash 4 units.
                    SprintStrength, SprintStrength.newBasicAttrValue(2F)
            ).set(
                    CoolDown, SprintStrength.newBasicAttrValue(6 * 20)
            ).set(
                    Strength, SprintStrength.newBasicAttrValue(5F)
            ).set(
                    AttackReach, AttackReach.newBasicAttrValue(8F)
            );
        }
    };

    public static final LanceCore KnightLance = new LanceCore() {

        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();
            AttrCalculator calculator = args.getCalculator();

            float strength = calculator.calcu(Strength).getFloat();
            float sprintLength = calculator.calcu(SprintStrength).getFloat();

            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sprintLength, 0.25D, sprintLength));
            Vector2D look = P2D.toV2D(player.getLookVec());
            int damagedEntityCount = 0;
            for (EntityLivingBase e : allInRange) {
                if (EntityUtil.canAttack(player, e) && P2D.isInside(look, PhysicsTool.get2DPosition(player), PhysicsTool.get2DPosition(e), 1.5, sprintLength)) {
                    e.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.5F * strength);
                    damagedEntityCount++;
                }
            }
            int weaponDamage = (int) MathUtil.castTo(1F, 5F, (float) damagedEntityCount / 3);
            FawItemUtil.damageWeapon(args.getWeapon(), args.getItemStack(), weaponDamage, player);

            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.setRegisterName(
                    Names.Item.Lance.KnightLanceItem
            ).set(
                    SprintStrength, SprintStrength.newBasicAttrValue(4F)
            ).set(
                    CoolDown, CoolDown.newBasicAttrValue(10 * 20)
            ).set(
                    Strength, Strength.newBasicAttrValue(6F)
            );
        }
    };

    public static final LanceCore ArenaLance = new LanceCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            EntityPlayer player = args.getPlayer();
            World world = args.getWorld();
            double x = player.posX,
                    y = player.posY,
                    z = player.posZ;
            AttrCalculator calculator = args.getCalculator();

            float strength = calculator.calcu(Strength).getFloat();


            if (!world.isRemote) {
                CoroutineSystem.Instance().attachCoroutineToPlayer(player, new Coroutine(new Yield<IWaitable>() {
                    private int duration = 20;

                    @Override
                    protected void runTask() {
                        if (duration > 0) {
                            //EntityUtil.setRooting(player, x, y, z);
                            EntityUtil.setRooting(player);
                            duration--;
                            yieldReturn(new WaitForNextTick());
                        } else {
                            AxisAlignedBB playerBox = player.getEntityBoundingBox();
                            List<EntityLivingBase> allInRange = world
                                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(3D, 0.25D, 3D));
                            allInRange.remove(player);
                            EntityLivingBase target;
                            if (allInRange.isEmpty()) {
                                return;
                            } else if (allInRange.size() == 1) {
                                target = allInRange.get(0);
                            } else {
                                target = (EntityLivingBase) allInRange.stream().sorted((e1, e2) -> {
                                    double d1 = player.getDistanceSq(e1);
                                    double d2 = player.getDistanceSq(e2);
                                    if (d1 == d2) {
                                        return 0;
                                    }
                                    return d1 > d2 ? 1 : -1;
                                }).toArray()[0];
                            }
                            target.attackEntityFrom(DamageSource.causePlayerDamage(player), 2.5F * strength);
                            FawItemUtil.damageWeapon(args.getWeapon(), args.getItemStack(), 2, player);
                        }
                    }
                }));
            }
            return true;
        }


        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.setRegisterName(
                    Names.Item.Lance.ArenaLanceItem
            ).set(
                    CoolDown, CoolDown.newBasicAttrValue(10 * 20)
            ).set(
                    Strength, Strength.newBasicAttrValue(6F)
            );
        }
    };

    public static final LanceCore TestLance = new LanceCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            EntityPlayer player = args.getPlayer();

            AttrCalculator calculator = args.getCalculator();

            float sprintLength = calculator.calcu(SprintStrength).getFloat();

            Vec3d originPos = player.getPositionVector();
            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(sprintLength);
            Vec3d nowPos = new Vec3d(sprintForce.x + originPos.x, originPos.y, sprintForce.z + originPos.z);
            PhysicsTool.setPosition(player, nowPos.x, nowPos.y + 0.5, nowPos.z);
            FawItemUtil.damageWeapon(args.getWeapon(), args.getItemStack(), 5, player);
            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    SprintStrength, SprintStrength.newBasicAttrValue(10F)
            ).set(
                    CoolDown, CoolDown.newBasicAttrValue(20)
            );
        }
    };
}

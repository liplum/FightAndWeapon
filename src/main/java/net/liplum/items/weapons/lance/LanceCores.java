package net.liplum.items.weapons.lance;

import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.api.weapon.WeaponSkillPredicatePrecast;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.BoolAttribute;
import net.liplum.coroutine.Coroutine;
import net.liplum.coroutine.IWaitable;
import net.liplum.coroutine.WaitForNextTick;
import net.liplum.enumerator.Yield;
import net.liplum.events.skill.LanceSprintEvent;
import net.liplum.lib.ItemProperty;
import net.liplum.lib.coroutine.CoroutineSystem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.P2D;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.nbt.NbtUtil;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.PhysicsUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.liplum.Attributes.Generic.*;
import static net.liplum.Attributes.Lance.SprintStrength;
import static net.liplum.items.weapons.lance.PSkills.Unstoppable;

@LongSupport
public final class LanceCores {
    public static final LanceCore Empty = new LanceCore(Names.Item.EmptyCore, false) {
        @Override
        public boolean releaseSkill(@Nonnull WeaponSkillArgs args) {
            return false;
        }
    };

    public static final LanceCore TrainingLance = new LanceCore(Names.Item.Lance.TrainingLanceItem, false) {
        @Override
        public boolean releaseSkill(@Nonnull WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    Strength, 5
            );
        }
    };

    public static final LanceCore LightLance = new LanceCore(Names.Item.Lance.LightLanceItem) {
        @Override
        public boolean releaseSkill(@Nonnull WeaponSkillArgs args) {
            boolean canceled = MinecraftForge.EVENT_BUS.post(new LanceSprintEvent(args));
            if (canceled) {
                return false;
            }
            World world = args.world();
            EntityLivingBase player = args.entity();
            final WeaponBaseItem weapon = args.weapon();
            final ItemStack itemStack = args.itemStack();
            AttrCalculator calculator = args.calculator();

            float strength = calculator.calcu(Strength).getFloat();
            float sprintStrength = calculator.calcu(SprintStrength).getFloat();

            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(MathHelper.sqrt(sprintStrength));
            PhysicsUtil.setMotion(player, sprintForce.x, 0.32, sprintForce.z);
            if (!world.isRemote) {
                //          player.addPotionEffect(new PotionEffect(PotionRegistry.Unstoppable_Potion, 15, 0, false, false));
                CoroutineSystem.Instance().attachCoroutine(player, new Yield<IWaitable>() {
                    final Set<EntityLivingBase> damaged = new HashSet<>();

                    @Override
                    protected void runTask() {
                        AxisAlignedBB playerBox = player.getEntityBoundingBox();
                        List<EntityLivingBase> allInRange = world
                                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(0.25D, 0.25D, 0.25D));
                        for (EntityLivingBase e : allInRange) {
                            if (!damaged.contains(e) && EntityUtil.canAttack(player, e)) {
                                weapon.dealDamage(EntityUtil.genFawDamage(player, itemStack), e, strength);
                                damaged.add(e);
                                FawItemUtil.damageWeapon(weapon, itemStack, 1, player);
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
            builder.set(
                    //  It means you can dash 4 units.
                    SprintStrength, 2F
            ).set(
                    CoolDown, 6 * 20
            ).set(
                    Strength, 5F
            ).set(
                    AttackReach, 8F
            ).add(
                    1, Unstoppable
            );
        }
    };

    public static final LanceCore KnightLance = new LanceCore(Names.Item.Lance.KnightLanceItem) {

        @Override
        public boolean releaseSkill(@Nonnull WeaponSkillArgs args) {
            World world = args.world();
            EntityLivingBase player = args.entity();
            WeaponBaseItem weapon = args.weapon();
            ItemStack itemStack = args.itemStack();
            AttrCalculator calculator = args.calculator();

            float strength = calculator.calcu(Strength).getFloat();
            float sprintLength = calculator.calcu(SprintStrength).getFloat();

            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sprintLength, 0.25D, sprintLength));
            Vector2D look = P2D.toV2D(player.getLookVec());
            int damagedEntityCount = 0;
            for (EntityLivingBase e : allInRange) {
                if (EntityUtil.canAttack(player, e) && P2D.isInside(look, P2D.toPosition(player), P2D.toPosition(e), 1.5, sprintLength)) {
                    weapon.dealDamage(EntityUtil.genFawDamage(player, itemStack), e, strength);
                    damagedEntityCount++;
                }
            }
            int weaponDamage = (int) MathUtil.castTo(1F, 5F, (float) damagedEntityCount / 3);
            FawItemUtil.damageWeapon(weapon, itemStack, weaponDamage, player);

            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    SprintStrength, 4F
            ).set(
                    CoolDown, 10 * 20
            ).set(
                    Strength, 6F
            );
        }
    };

    public static final LanceCore ArenaLance = new LanceCore(Names.Item.Lance.ArenaLanceItem) {
        @Override
        public boolean releaseSkill(@Nonnull WeaponSkillArgs args) {
            EntityLivingBase player = args.entity();
            World world = args.world();
            double x = player.posX,
                    y = player.posY,
                    z = player.posZ;
            final WeaponBaseItem weapon = args.weapon();
            final ItemStack itemStack = args.itemStack();
            AttrCalculator calculator = args.calculator();

            float strength = calculator.calcu(Strength).getFloat();


            if (!world.isRemote) {
                CoroutineSystem.Instance().attachCoroutine(player, new Coroutine(new Yield<IWaitable>() {
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
                            weapon.dealDamage(EntityUtil.genFawDamage(player, itemStack), target, strength);
                            FawItemUtil.damageWeapon(weapon, itemStack, 2, player);
                        }
                    }
                }));
            }
            return true;
        }


        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    CoolDown, 10 * 20
            ).set(
                    Strength, 6F
            );
        }
    };

    public static final LanceCore DrillLance = new LanceCore(Names.Item.Lance.DrillLanceItem) {
        @Override
        public boolean releaseSkill(@Nonnull WeaponSkillArgs args) {

            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.add(new ItemProperty(Names.Item.Lance.DrillLanceItem_Property_Drilling) {
                @Override
                public float apply(@Nonnull ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                    return BoolAttribute.toInt(NbtUtil.getOrCreateFrom(stack).getBoolean(Names.Item.Lance.DrillLanceItem_Property_Drilling));
                }
            }).set(WeaponSkillPredicatePrecast.AlwaysTrue);
        }

        @Override
        public boolean onStopUsing(@Nonnull WeaponSkillArgs args, int totalTickUsed, int tickLeft) {
            //TODO:To be continued
            AttrCalculator calculator = args.calculator();
            float strength = calculator.calcu(Strength).getFloat();
            float sprintStrength = calculator.calcu(SprintStrength).getFloat();
            World world = args.world();
            WeaponBaseItem weapon = args.weapon();
            ItemStack itemStack = args.itemStack();
            EntityLivingBase player = args.entity();
            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(MathHelper.sqrt(sprintStrength));
            PhysicsUtil.setMotion(player, sprintForce.x, 0.32, sprintForce.z);
            CoroutineSystem.Instance().attachCoroutine(player, new Yield<IWaitable>() {
                int tick = 0;
                final Set<EntityLivingBase> damaged = new HashSet<>();

                @Override
                protected void runTask() {
                    AxisAlignedBB playerBox = player.getEntityBoundingBox();
                    List<EntityLivingBase> allInRange = world
                            .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(0.25D, 0.25D, 0.25D));
                    for (EntityLivingBase e : allInRange) {
                        if (!damaged.contains(e) && EntityUtil.canAttack(player, e)) {
                            weapon.dealDamage(EntityUtil.genFawDamage(player, itemStack), e, strength);
                            damaged.add(e);
                            FawItemUtil.damageWeapon(weapon, itemStack, 1, player);
                        }
                    }
                    if (tick == 24) {
                        stopDrilling(itemStack);
                    }
                    tick++;
                }
            }, 25);
            return true;
        }

        private void startDrilling(@Nonnull ItemStack drillLance) {
            NBTTagCompound root = NbtUtil.getOrCreateFrom(drillLance);
            root.setBoolean(Names.Item.Lance.DrillLanceItem_Property_Drilling, true);
        }

        private void stopDrilling(@Nonnull ItemStack drillLance) {
            NBTTagCompound root = NbtUtil.getOrCreateFrom(drillLance);
            root.setBoolean(Names.Item.Lance.DrillLanceItem_Property_Drilling, false);
        }
    };

    public static final LanceCore TestLance = new LanceCore(Names.Item.EmptyCore) {
        @Override
        public boolean releaseSkill(@Nonnull WeaponSkillArgs args) {
            EntityLivingBase player = args.entity();

            AttrCalculator calculator = args.calculator();

            float sprintLength = calculator.calcu(SprintStrength).getFloat();

            Vec3d originPos = player.getPositionVector();
            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(sprintLength);
            Vec3d nowPos = new Vec3d(sprintForce.x + originPos.x, originPos.y, sprintForce.z + originPos.z);
            PhysicsUtil.setPosition(player, nowPos.x, nowPos.y + 0.5, nowPos.z);
            FawItemUtil.damageWeapon(args.weapon(), args.itemStack(), 5, player);
            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    SprintStrength, 10F
            ).set(
                    CoolDown, 20
            );
        }
    };
}

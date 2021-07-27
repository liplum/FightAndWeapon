package net.liplum.skills;

import net.liplum.Names;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemUtil;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import java.util.List;

@Developing
public class UndeterminedSkills {
    public final static IPassiveSkill<PlayerPickupXpEvent> XpMending =
            new PassiveSkill<PlayerPickupXpEvent>(Names.PassiveSkill.ExpMending, PlayerPickupXpEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull PlayerPickupXpEvent event) {
                    EntityXPOrb orb = event.getOrb();
                    EntityPlayer player = event.getEntityPlayer();
                    List<ItemStack> weapons = ItemUtil.getItemStacks(this::hasXpMending,
                            player.getHeldItemMainhand(), player.getHeldItemOffhand());
                    for (ItemStack weapon : weapons) {
                        if (weapon.isItemDamaged()) {
                            float ratio = weapon.getItem().getXpRepairRatio(weapon);
                            int repairAmount = MathUtil.fixMax(MathUtil.randomFloor(orb.xpValue * ratio), weapon.getItemDamage());
                            orb.xpValue -= (float) repairAmount / ratio;
                            WeaponBaseItem weaponItem = (WeaponBaseItem) weapon.getItem();
                            FawItemUtil.healWeapon(weaponItem, weapon, repairAmount, player);
                        }
                    }
                    return PSkillResult.Complete;
                }

                private boolean hasXpMending(ItemStack itemStack) {
                    Item item = itemStack.getItem();
                    if (item instanceof WeaponBaseItem) {
                        WeaponBaseItem weapon = (WeaponBaseItem) item;
                        IGemstone gemstone = GemUtil.getGemstoneFrom(itemStack);
                        if (gemstone != null) {
                            return gemstone.hasPassiveSkillOfCore(weapon.getCore(), this);
                        }
                    }
                    return false;
                }
            }.setBanedWhenBroken(false);

    public static final IPassiveSkill<TickEvent.PlayerTickEvent> FrostWalker =
            new PassiveSkill<TickEvent.PlayerTickEvent>(Names.PassiveSkill.FrostWalker, TickEvent.PlayerTickEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull TickEvent.PlayerTickEvent event) {
                    EntityPlayer player = event.player;
                    EnchantmentFrostWalker.freezeNearby(player, player.world, new BlockPos(player), 2);
                    return PSkillResult.Complete;
                }
            }.setBanedWhenBroken(false);
}

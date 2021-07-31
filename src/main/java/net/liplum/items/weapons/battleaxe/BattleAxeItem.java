package net.liplum.items.weapons.battleaxe;

import net.liplum.FawBehaviors;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.events.weapon.WeaponSkillReleaseEvent;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.FawUtil;
import net.liplum.lib.utils.GemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

import static net.liplum.Attributes.Generic.CoolDown;

public class BattleAxeItem extends WeaponBaseItem {
    private final BattleAxeCore core;

    public BattleAxeItem(@Nonnull BattleAxeCore core) {
        super(core);
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        //Default is PASS
        //If play were not sneaking, didn't detect.
        if (core.getWeaponSkillPredicate().canRelease(worldIn, playerIn, held)) {
            Modifier modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillReleaseEvent.Pre(worldIn, playerIn, this, modifier, held, handIn)
            );
            if (!cancelRelease) {

                WeaponSkillArgs args = new WeaponSkillArgs()
                        .world(worldIn)
                        .entity(playerIn)
                        .itemStack(held)
                        .hand(handIn)
                        .modifier(modifier)
                        .weapon(this);
                AttrCalculator calculator = FawUtil.toCalculator(args);
                args.calculator(calculator);
                int coolDown = calculator.calcu(CoolDown).getInt();

                boolean releasedSuccessfully = FawItemUtil.releaseWeaponSkill(core, modifier, args);

                if (releasedSuccessfully) {
                    if (FawItemUtil.heatWeaponType(playerIn, getWeaponType())) {
                        //When you release the skill, it will make your shield hot.
                        //Don't worry about the EMPTY, if that it'll return Items.AIR (no exception).
                        if (offHeld.getItem() == Items.SHIELD) {
                            playerIn.getCooldownTracker().setCooldown(offHeld.getItem(), coolDown / 5 * 2);
                        }
                    }
                    result = EnumActionResult.SUCCESS;
                    MinecraftForge.EVENT_BUS.post(
                            new WeaponSkillReleaseEvent.Post(worldIn, playerIn, this, modifier, held, handIn)
                    );
                    FawBehaviors.onWeaponUse(playerIn, this,held);
                }
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Nonnull
    @Override
    public BattleAxeCore getCore() {
        return core;
    }
}

package net.liplum.items.weapons.magickwand;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.events.weapon.WeaponSkillReleaseEvent;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.FawUtil;
import net.liplum.lib.utils.GemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class MagickWandItem extends WeaponBaseItem {
    @NotNull
    private final MagickWandCore core;

    public MagickWandItem(@NotNull MagickWandCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @NotNull
    @Override
    public MagickWandCore getConcreteCore() {
        return core;
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull EntityPlayer playerIn, @NotNull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
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
                        .weapon(this)
                        .modifier(modifier);
                args.calculator(FawUtil.toCalculator(args));
                boolean releasedSuccessfully = FawItemUtil.releaseWeaponSkill(core, modifier, args);
                result = EnumActionResult.SUCCESS;
            }
        }
        return new ActionResult<>(result, held);
    }
}

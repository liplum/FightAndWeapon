package net.liplum.items.weapons.lance;

import net.liplum.api.weapon.Modifier;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.events.skill.WeaponSkillPostReleasedEvent;
import net.liplum.events.skill.WeaponSkillPreReleaseEvent;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

import static net.liplum.Attributes.Generic.CoolDown;
import static net.liplum.Attributes.Generic.Strength;
import static net.liplum.Attributes.Lance.SprintStrength;

public class LanceItem extends WeaponBaseItem<LanceCore> {
    private final LanceCore core;

    public LanceItem(@Nonnull LanceCore core) {
        super();
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        //Player can't sprint in the sky.
        if (playerIn.onGround && playerIn.isSneaking()) {
            Modifier<?> modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillPreReleaseEvent(worldIn, playerIn, core, modifier, held, handIn)
            );
            if (!cancelRelease) {
                FinalAttrValue finalCoolDown = FawItemUtil.calcuAttribute(CoolDown, core, modifier);
                FinalAttrValue finalStrength = FawItemUtil.calcuAttribute(Strength, core, modifier, playerIn);
                FinalAttrValue finalSprintStrength = FawItemUtil.calcuAttribute(SprintStrength, core, modifier, playerIn);

                boolean releasedSuccessfully;
                LanceArgs args = new LanceArgs()
                        .setWorld(worldIn)
                        .setPlayer(playerIn)
                        .setItemStack(held)
                        .setHand(handIn)
                        .setSprintLength(finalSprintStrength.getFloat())
                        .setStrength(finalStrength.getFloat());

                if (modifier != null) {
                    LanceModifier mod = (LanceModifier) modifier;
                    args.setModifier(mod);
                    releasedSuccessfully = mod.releaseSkill(core, args);
                } else {
                    releasedSuccessfully = core.releaseSkill(args);
                }
                if (releasedSuccessfully) {
                    ItemTool.heatWeaponIfSurvival(playerIn, held.getItem(), finalCoolDown.getInt());
                    result = EnumActionResult.SUCCESS;
                    MinecraftForge.EVENT_BUS.post(
                            new WeaponSkillPostReleasedEvent(worldIn, playerIn, core, modifier, held, handIn)
                    );
                }
            }
        }
        return ActionResult.newResult(result, held);
    }


    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.BLOCK;
    }

    @Nonnull
    @Override
    public LanceCore getCore() {
        return core;
    }
}

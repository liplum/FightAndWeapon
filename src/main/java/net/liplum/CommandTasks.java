package net.liplum;

import com.google.common.collect.Lists;
import net.liplum.api.fight.IMastery;
import net.liplum.api.registeies.GemstoneRegistry;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.registeies.WeaponTypeRegistry;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponType;
import net.liplum.commands.ITask;
import net.liplum.commands.TabCompletionProvider;
import net.liplum.commands.Task;
import net.liplum.commands.ValidInfo;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.MasteryUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;

import static net.liplum.I18ns.Command.*;

public final class CommandTasks {
    public static final TabCompletionProvider AllWeaponTypeNamesTabCompletion = (server, sender, needBeCompetedIndex, targetPos) -> WeaponTypeRegistry.getAllWeaponTypeNames();
    public static final TabCompletionProvider AllMasteryNamesTabCompletion = (server, sender, needBeCompetedIndex, targetPos) -> MasteryRegistry.getAllMasteryNames();


    public static final ITask ClearOneWeaponTypeCoolDown = new Task() {
        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                WeaponType weaponType = (WeaponType) transformedArgs[0];
                FawItemUtil.clearWeaponCoolDown(player, weaponType);
            }
        }
    }.addValidRequirement(
            0, para -> ValidInfo.invalidWhenNull(WeaponTypeRegistry.getWeaponTypeOf(para))
    ).addTabCompletion(
            0, AllWeaponTypeNamesTabCompletion
    );

    public static final ITask ClearAllWeaponTypeCoolDown = new Task() {
        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                FawItemUtil.clearAllWeaponsCoolDown(player);
            }
        }
    }.addValidRequirement(
            0, para -> new ValidInfo(para.equals(Names.Command.ClearCDSub.All), null)
    ).addTabCompletion(
            0, (server, sender, needBeCompetedIndex, targetPos) -> Lists.newArrayList(Names.Command.ClearCDSub.All)
    );

    public static final ITask InlayGemstone = new Task() {
        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) sender;
                IGemstone gemstone = (IGemstone) transformedArgs[0];
                String gemstoneName = gemstone.getRegisterName();
                ItemStack held = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
                GemUtil.InlayResult inlayResult = GemUtil.inlayGemstone(held, gemstoneName);
                switch (inlayResult) {
                    case NotFawWeapon:
                        throw new CommandException(Inlay_Failure_NotFawWeapon, entity.getName());
                    case NoSuchGemstone:
                        throw new CommandException(Inlay_Failure_NoSuchGemstone, gemstoneName);
                }
            }
        }
    }.addValidRequirement(
            0, para -> ValidInfo.invalidWhenNull(GemstoneRegistry.getGemstone(para))
    ).addTabCompletion(
            0, (server, sender, needBeCompetedIndex, targetPos) -> GemstoneRegistry.getAllGemstoneNames()
    );

    public static final ITask RemoveGemstone = new Task() {
        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) sender;
                ItemStack held = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
                GemUtil.RemoveResult removeResult = GemUtil.removeGemstone(held);
                switch (removeResult) {
                    case NotFawWeapon:
                        throw new CommandException(Inlay_Failure_NotFawWeapon, entity.getName());
                    case NoGemstone:
                        throw new CommandException(Inlay_Failure_NoFawWeapon, entity.getName());
                }
            }
        }
    }.addValidRequirement(
            0, para -> ValidInfo.nullTransformedArg(para.equals(Names.Command.InlaySub.Remove))
    ).addTabCompletion(
            0, (server, sender, needBeCompetedIndex, targetPos) -> Lists.newArrayList(Names.Command.InlaySub.Remove)
    );

    public static final ITask ShowOneMasteryInfo = new Task() {
        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                IMastery mastery = (IMastery) transformedArgs[0];
                MasteryUtil.showMastery(player, mastery);
            }
        }
    }.addValidRequirement(
            0, para -> ValidInfo.invalidWhenNull(MasteryRegistry.getMasteryOf(para))
    ).addTabCompletion(
            0, AllMasteryNamesTabCompletion
    );

    public static final ITask ShowAllMasteryInfo = new Task() {
        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                MasteryUtil.showAllMasteries(player);
            }
        }
    }.addValidRequirement(
            0, para -> ValidInfo.nullTransformedArg(para.equals(Names.Command.MasterySub.All))
    ).addTabCompletion(
            0, (server, sender, needBeCompetedIndex, targetPos) -> Lists.newArrayList(Names.Command.MasterySub.All)
    );
}

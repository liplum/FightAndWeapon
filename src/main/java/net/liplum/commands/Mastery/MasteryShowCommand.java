package net.liplum.commands.Mastery;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.IMastery;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.lib.utils.MasteryUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static net.liplum.I18ns.Command.Mastery_Failure_NotSuchWeaponType;

@LongSupport
public class MasteryShowCommand extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return Names.Command.MasterySub.MasteryShow;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return I18ns.Command.MasteryShow;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new WrongUsageException(getUsage(sender));
        }
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            String masteryName = args[0];
            if (masteryName.equals(Names.Command.MasterySub.All)) {
                MasteryUtil.showAllMasteries(player);
            } else {
                IMastery mastery = MasteryRegistry.getMasteryOf(masteryName);
                if (mastery == null) {
                    throw new CommandException(Mastery_Failure_NotSuchWeaponType, masteryName);
                }
                MasteryUtil.showMastery(player, mastery);
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> allMasteriesNames = new LinkedList<>(MasteryRegistry.getAllMasteryNames());
            allMasteriesNames.add(Names.Command.MasterySub.All);
            return getListOfStringsMatchingLastWord(args, allMasteriesNames);
        }
        return Collections.emptyList();
    }
}

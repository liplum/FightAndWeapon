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

import static net.liplum.I18ns.Command.*;

/**
 * Usage:/mastery exp <Weapon Type> <Amount><br/>
 * /mastery exp <Weapon Type> <Amount> <Player>
 */
@LongSupport
public class MasteryExpCommand extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return Names.Command.MasterySub.MasteryExp;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return I18ns.Command.MasteryExp;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2 && args.length != 3) {
            throw new WrongUsageException(getUsage(sender));
        }
        //mastery exp <Weapon Type> <Amount> <Player>
        EntityPlayer player = args.length == 3 ? getPlayer(server, sender, args[2]) : getCommandSenderAsPlayer(sender);
        String weaponTypeName = args[0];
        IMastery mastery = MasteryRegistry.getMasteryOf(weaponTypeName);
        if (mastery == null) {
            throw new CommandException(Mastery_Failure_NotSuchWeaponType, weaponTypeName);
        }
        String expCountStr = args[1];
        int exp;
        try {
            exp = Integer.parseInt(expCountStr);
        } catch (Exception e) {
            throw new CommandException(Mastery_Failure_NaN, expCountStr);
        }
        if (exp <= 0) {
            throw new CommandException(Mastery_Failure_NegativeNumber, weaponTypeName);
        }
        MasteryUtil.addExp(player, mastery, exp * 10);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> allMasteriesNames = new LinkedList<>(MasteryRegistry.getAllMasteriesNames());
            allMasteriesNames.add(Names.Command.MasterySub.All);
            return getListOfStringsMatchingLastWord(args, allMasteriesNames);
        }
        if (args.length == 3) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }

        return Collections.emptyList();
    }
}
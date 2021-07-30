package net.liplum.commands.Mastery;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.api.fight.IMastery;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.masteries.IMasteryDetail;
import net.liplum.masteries.MasteryDetail;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static net.liplum.I18ns.Command.*;

/**
 * Usage:/mastery reset <Weapon Type><br/>
 * /mastery reset <Weapon Type> <Player>
 */
public class MasteryResetCommand extends CommandBase {
    @Nonnull
    @Override
    public String getName() {
        return Names.Command.MasterySub.MasteryReset;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return I18ns.Command.MasteryReset;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length != 1 && args.length != 2) {
            throw new WrongUsageException(getUsage(sender));
        }
        EntityPlayer player = args.length == 2 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
        IMasteryDetail detail = MasteryDetail.create(player);
        String weaponTypeName = args[0];
        if (weaponTypeName.equals(Names.Command.MasterySub.All)) {
            for (IMastery mastery : MasteryRegistry.getAllMasteries()) {
                detail.resetMastery(mastery);
            }
        } else {
            IMastery mastery = MasteryRegistry.getMasteryOf(weaponTypeName);
            if (mastery == null) {
                throw new CommandException(Mastery_Failure_NotSuchWeaponType, weaponTypeName);
            }
            detail.resetMastery(mastery);
        }
        detail.sync();
        if (player == sender) {
            player.sendMessage(new TextComponentTranslation(Mastery_Reset_Yours_Successfully));
        } else {
            player.sendMessage(new TextComponentTranslation(Mastery_Yours_Is_Reset));
            sender.sendMessage(new TextComponentTranslation(Mastery_Reset_Successfully, player));
        }
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> allMasteriesNames = new LinkedList<>(MasteryRegistry.getAllMasteriesNames());
            allMasteriesNames.add(Names.Command.MasterySub.All);
            return getListOfStringsMatchingLastWord(args, allMasteriesNames);
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return Collections.emptyList();
    }
}

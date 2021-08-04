package net.liplum.commands.weapon;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.api.registeies.WeaponTypeRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.lib.utils.FawItemUtil;
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

public class ClearCoolDownCommand extends CommandBase {
    @Nonnull
    @Override
    public String getName() {
        return Names.Command.ClearCD;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return I18ns.Command.ClearCD;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length > 1) {
            throw new WrongUsageException(getUsage(sender));
        }
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            if (args.length == 0) {
                FawItemUtil.clearAllWeaponsCoolDown(player);
            } else {
                String weaponTypeName = args[0];
                if (weaponTypeName.equals(Names.Command.ClearCDSub.All)) {
                    FawItemUtil.clearAllWeaponsCoolDown(player);
                } else {
                    WeaponType weaponType = WeaponTypeRegistry.getWeaponTypeOf(weaponTypeName);
                    if (weaponType != null) {
                        FawItemUtil.clearWeaponCoolDown(player, weaponType);
                    }
                }
            }
        }
    }


    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 0) {
            List<String> allWeaponTypeNames = new LinkedList<>(WeaponTypeRegistry.getAllWeaponTypeNames());
            allWeaponTypeNames.add(Names.Command.ClearCDSub.All);
            return getListOfStringsMatchingLastWord(args, allWeaponTypeNames);
        }
        return Collections.emptyList();
    }
}

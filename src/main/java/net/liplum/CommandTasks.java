package net.liplum;

import net.liplum.api.registeies.WeaponTypeRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.commands.ITask;
import net.liplum.commands.Task;
import net.liplum.commands.ValidInfo;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class CommandTasks {
    public static final ITask ClearOneWeaponTypeCoolDown = new Task(1, true, false) {
        @Nonnull
        @Override
        public ValidInfo isValid(@Nonnull String parameter, int index) {
            if (index == 0) {
                WeaponType weaponType = WeaponTypeRegistry.getWeaponTypeOf(parameter);
                if (weaponType == null) {
                    return ValidInfo.AlwaysFalseAndNull;
                } else {
                    return new ValidInfo(true, weaponType);
                }
            }
            return ValidInfo.AlwaysFalseAndNull;
        }

        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                WeaponType weaponType = (WeaponType) transformedArgs[0];
                FawItemUtil.clearWeaponCoolDown(player, weaponType);
            }
        }

        @Nonnull
        @Override
        public List<String> getCompletions(int needBeCompetedIndex) {
            if (needBeCompetedIndex == 0) {
                return WeaponTypeRegistry.getAllWeaponTypeNames();
            }
            return Collections.emptyList();
        }
    };

    public static final ITask ClearAllWeaponTypeCoolDown = new Task(1, true, false) {
        @Nonnull
        @Override
        public ValidInfo isValid(@Nonnull String parameter, int index) {
            if (index == 0) {
                return new ValidInfo(parameter.equals(Names.Command.ClearCDSub.All), null);
            }
            return ValidInfo.AlwaysFalseAndNull;
        }

        @Override
        public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                FawItemUtil.clearAllWeaponsCoolDown(player);
            }
        }

        @Nonnull
        @Override
        public List<String> getCompletions(int needBeCompetedIndex) {
            if (needBeCompetedIndex == 0) {
                LinkedList<String> list = new LinkedList<>();
                list.add(Names.Command.ClearCDSub.All);
                return list;
            }
            return Collections.emptyList();
        }
    };
}

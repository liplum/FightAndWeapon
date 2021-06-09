package net.liplum.commands;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.api.registeies.GemstoneRegistry;
import net.liplum.lib.utils.FawGemUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class InlayCommand extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return Names.Command.Inlay;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return I18ns.Command.Inlay;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new WrongUsageException(getUsage(sender));
        }
        if (sender instanceof EntityLivingBase) {
            String gemstoneName = args[0];
            if (!gemstoneName.isEmpty()) {
                EntityLivingBase entity = (EntityLivingBase) sender;
                ItemStack heldItemStack = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

                if (gemstoneName.equals(Names.Command.InlaySub.Remove)) {
                    //Remove current gemstone
                    FawGemUtil.RemoveResult removeResult = FawGemUtil.removeGemstone(heldItemStack);
                    switch (removeResult) {
                        case NotFawWeapon:
                            throw new CommandException("commands.inlay.failure.notFawWeapon", sender);
                        case NoGemstone:
                            throw new CommandException("commands.inlay.failure.noGemstone", sender);
                    }
                } else {
                    //Inlay new gemstone
                    FawGemUtil.InlayResult inlayResult = FawGemUtil.inlayGemstone(heldItemStack, gemstoneName);
                    switch (inlayResult) {
                        case NotFawWeapon:
                            throw new CommandException("commands.inlay.failure.notFawWeapon", sender);
                        case NoSuchGemstone:
                            throw new CommandException("commands.inlay.failure.noSuchGemstone", gemstoneName);
                    }
                }
            } else {
                throw new WrongUsageException(getUsage(sender));
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, GemstoneRegistry.getAllGemstoneNames());
        }
        return Collections.emptyList();
    }
}

package net.liplum.commands;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.registeies.GemstoneRegistry;
import net.liplum.lib.utils.GemUtil;
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
import java.util.LinkedList;
import java.util.List;

import static net.liplum.I18ns.Command.*;

@LongSupport
public class InlayCommand extends CommandBase {

    public static void removeCurrentGemstone(@Nonnull EntityLivingBase entity, @Nonnull ItemStack held) throws CommandException {
        GemUtil.RemoveResult removeResult = GemUtil.removeGemstone(held);
        switch (removeResult) {
            case NotFawWeapon:
                throw new CommandException(Inlay_Failure_NotFawWeapon, entity.getName());
            case NoGemstone:
                throw new CommandException(Inlay_Failure_NoFawWeapon, entity.getName());
        }
    }

    public static void inlayNewGemstone(@Nonnull EntityLivingBase entity, @Nonnull ItemStack held, @Nonnull String gemstoneName) throws CommandException {
        GemUtil.InlayResult inlayResult = GemUtil.inlayGemstone(held, gemstoneName);
        switch (inlayResult) {
            case NotFawWeapon:
                throw new CommandException(Inlay_Failure_NotFawWeapon, entity.getName());
            case NoSuchGemstone:
                throw new CommandException(Inlay_Failure_NoSuchGemstone, gemstoneName);
        }
    }

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
                    removeCurrentGemstone(entity, heldItemStack);
                } else {
                    inlayNewGemstone(entity, heldItemStack, gemstoneName);
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
            List<String> allGemstoneNames = new LinkedList<>(GemstoneRegistry.getAllGemstoneNames());
            allGemstoneNames.add(Names.Command.InlaySub.Remove);
            return getListOfStringsMatchingLastWord(args, allGemstoneNames);
        }
        return Collections.emptyList();
    }
}

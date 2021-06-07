package net.liplum.commands;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.lib.utils.FawGemUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;

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
        if (args.length == 1) {
            if (sender instanceof EntityLivingBase) {
                String gemstoneName = args[0];
                if (!gemstoneName.isEmpty()) {
                    EntityLivingBase entity = (EntityLivingBase) sender;
                    ItemStack heldItemStack = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
                    FawGemUtil.inlayGemstone(heldItemStack,gemstoneName);
                }
            }
        }
    }
}

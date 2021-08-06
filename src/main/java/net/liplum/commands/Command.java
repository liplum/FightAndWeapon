package net.liplum.commands;

import net.liplum.lib.math.MathUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Command extends CommandBase {
    @Nonnull
    private final String name;
    @Nonnull
    private final Function<ICommandSender, String> usageGetter;
    private final int requiredPermissionLevel;
    @Nonnull
    private final List<ITask> allTasks = new LinkedList<>();
    private int minArgsSize = 0;
    private int maxArgsSize = 0;

    public Command(@Nonnull String name, int requiredPermissionLevel, @Nonnull Function<ICommandSender, String> usageGetter) {
        this.name = name;
        this.usageGetter = usageGetter;
        this.requiredPermissionLevel = requiredPermissionLevel;
    }

    public Command(@Nonnull String name, int requiredPermissionLevel, @Nonnull String usage) {
        this(name, requiredPermissionLevel, (s) -> usage);
    }

    public Command addTask(@Nonnull ITask task) {
        allTasks.add(task);
        setMinMaxArgsSize(task.getArgsCount());
        return this;
    }

    private void setMinMaxArgsSize(int checkedOne) {
        minArgsSize = Math.min(minArgsSize, checkedOne);
        maxArgsSize = Math.min(maxArgsSize, checkedOne);
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return usageGetter.apply(sender);
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        int length = args.length;
        if (!isArgsInValidRange(length)) {
            throw new WrongUsageException(getUsage(sender));
        }
        List<ITask> possibleTasks = allTasks.stream().filter(task -> task.getArgsCount() == length).collect(Collectors.toList());
        if (possibleTasks.isEmpty()) {
            throw new WrongUsageException(getUsage(sender));
        }
        List<ITask> validTasks = possibleTasks;
        List<ITask> temporaryValidTasks = new LinkedList<>();
        Map<ITask, Object[]> temporaryTaskTransformedArgs = new HashMap<>();
        for (int i = 0; i < length; i++) {
            String parameter = args[i];
            for (ITask task : validTasks) {
                ValidInfo validInfo = task.isValid(parameter, i);
                if (validInfo.isValid()) {
                    temporaryValidTasks.add(task);
                    Object[] taskArgs = temporaryTaskTransformedArgs.get(task);
                    if (taskArgs == null) {
                        taskArgs = new Object[task.getArgsCount()];
                        temporaryTaskTransformedArgs.put(task, taskArgs);
                    }
                    taskArgs[i] = validInfo.transformedArg();
                }
            }
            validTasks = temporaryValidTasks;
        }

        if (validTasks.isEmpty()) {
            throw new WrongUsageException(getUsage(sender));
        }
        int validTaskCount = validTasks.size();
        if (validTaskCount > 1) {
            new AmbiguityCommandException(name, validTaskCount).printStackTrace();
        }
        ITask task = validTasks.get(0);
        Object[] itsArgs = temporaryTaskTransformedArgs.get(task);
        task.run(server, sender, itsArgs);
    }

    private boolean isArgsInValidRange(int length) {
        return MathUtil.belongToCC(minArgsSize, maxArgsSize, length);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return requiredPermissionLevel;
    }
}

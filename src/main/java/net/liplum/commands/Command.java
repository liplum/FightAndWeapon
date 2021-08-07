package net.liplum.commands;

import net.liplum.lib.math.MathUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    @Nonnull
    private final List<String> aliases = new LinkedList<>();
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

    @Nonnull
    public Command addTask(@Nonnull ITask task) {
        allTasks.add(task);
        setMinMaxArgsSize(task.getArgsCount());
        return this;
    }

    private void setMinMaxArgsSize(int checkedOne) {
        minArgsSize = Math.min(minArgsSize, checkedOne);
        maxArgsSize = Math.max(maxArgsSize, checkedOne);
    }

    @Nonnull
    public Command addAlias(@Nonnull String alias) {
        this.aliases.add(alias);
        return this;
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return this.aliases;
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

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        int length = args.length;
        int index = length - 1;
        List<String> result = new LinkedList<>();
        ITask mutex = hasMutexTabCompletion(index);
        if (mutex != null) {
            List<String> completions = mutex.getCompletions(server, sender, index, targetPos);
            result.addAll(completions);
        } else {
            for (ITask task : allTasks) {
                if (task.hasTabCompletion(index)) {
                    List<String> completions = task.getCompletions(server, sender, index, targetPos);
                    result.addAll(completions);
                }
            }
        }
        return getListOfStringsMatchingLastWord(args, result);
    }

    @Nullable
    private ITask hasMutexTabCompletion(int index) {
        for (ITask task : allTasks) {
            if (task.isTabCompletionMutex(index)) {
                return task;
            }
        }
        return null;
    }
}

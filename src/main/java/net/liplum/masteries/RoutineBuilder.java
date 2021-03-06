package net.liplum.masteries;

import net.liplum.api.annotations.Developing;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static net.liplum.masteries.Mastery.MaxLevel;

@Developing
public class RoutineBuilder implements IRoutineReader {
    @NotNull
    public final static Consumer<Node> Empty = (n) -> {
    };
    @NotNull
    private final LinkedList<Node> nodes = new LinkedList<>();
    @NotNull
    private final ListIterator<Node> iterator = nodes.listIterator();
    private int level = 1;

    @Override
    public Routine toRoutine() {
        truncate();
        List<Node> cleaned = nodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return new Routine(new ArrayList<>(cleaned));
    }

    private void truncate() {
        while (nodes.size() > MaxLevel) {
            nodes.removeLast();
        }
    }

    public RoutineBuilder node(@NotNull Consumer<Node> nodeBuilder) {
        Node node = new Node();
        nodeBuilder.accept(node);
        iterator.add(node);
        if (iterator.hasNext()) {
            iterator.next();
        }
        level++;
        return this;
    }
}

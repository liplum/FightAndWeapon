package net.liplum.masteries;

import net.liplum.api.annotations.Developing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

@Developing
public class RoutineBuilder implements IRoutineReader {
    @Nonnull
    private final LinkedList<Node> nodes = new LinkedList<>();
    @Nonnull
    private ListIterator<Node> iterator = nodes.listIterator();
    private int level = 1;
    @Nonnull
    private final NodeGetter currentGetter = new NodeGetter();

    @Override
    public Routine toRoutine() {
        toNextLv();
        return new Routine(new ArrayList<>(nodes));
    }

    public RoutineBuilder node(@Nonnull Node node) {
        this.currentGetter.setCurrent(node);
        return this;
    }

    public RoutineBuilder repeat(int count) {
        for (int i = 0; i < count; i++) {
            this.toNextLv();
        }
        return this;
    }

    @Developing
    public RoutineBuilder nextLv() {
        toNextLv();
        clearCurrent();
        return this;
    }

    public RoutineBuilder refresh() {
        refreshIterator();
        return this;
    }

    private void toNextLv() {
        iterator.add(this.currentGetter.get());
        iterator.next();
        level++;
    }

    private void clearCurrent() {
        currentGetter.clear();
    }

    public void refreshIterator() {
        this.iterator = nodes.listIterator();
    }

    private static class NodeGetter {
        @Nonnull
        private static final Node Default = new Node();
        @Nullable
        private Node current;

        public void setCurrent(@Nonnull Node getter) {
            this.current = getter;
        }

        public void clear() {
            this.current = null;
        }

        @Nonnull
        public Node get() {
            return current != null ? current : Default;
        }
    }

}

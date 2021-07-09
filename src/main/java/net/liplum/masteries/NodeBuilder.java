package net.liplum.masteries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.Supplier;

public class NodeBuilder implements INodeReader {
    @Nonnull
    private final LinkedList<Node> nodes = new LinkedList<>();
    @Nonnull
    private ListIterator<Node> iterator = nodes.listIterator();
    private int level = 1;
    @Nullable
    private Node current;

    @Override
    public Routine toRoutine() {
        if (current != null) {
            iterator.add(this.current);
            current = null;
        }
        return new Routine(new ArrayList<>(nodes));
    }

    public NodeBuilder genNode(@Nonnull Supplier<Node> nodeSupplier) {
        this.current = nodeSupplier.get();
        return this;
    }

    public NodeBuilder nextLv() {
        toNextLv();
        return this;
    }

    public NodeBuilder refresh() {
        refreshIterator();
        return this;
    }

    private void toNextLv() {
        if (current == null) {
            current = new Node();
        }
        iterator.add(this.current);
        current = null;
        iterator.next();
        level++;
    }

    public void refreshIterator() {
        this.iterator = nodes.listIterator();
    }

}

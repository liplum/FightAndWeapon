package net.liplum.masters;

import java.util.ArrayList;
import java.util.LinkedList;

public class NodeBuilder implements INodeReader {
    private final LinkedList<Node> nodes = new LinkedList<>();

    @Override
    public ArrayList<Node> generate() {
        return new ArrayList<>(nodes);
    }
}

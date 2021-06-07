package net.liplum.lib.masters;

import net.liplum.lib.utils.Utils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Routine {
    private LinkedList<Node> allNodes = new LinkedList<>();

    public Routine() {
    }

    @Nonnull
    public LinkedList<Node> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(@Nonnull LinkedList<Node> allNodes) {
        this.allNodes = allNodes;
    }

    @Nonnull
    public Map<String, Number> getAttributeAmplifiers(int count) {
        HashMap<String, Number> res = new HashMap<>();
        for (Node node : allNodes.subList(0, count)) {
            List<AttributeAmplifier> amplifiers = node.getAttributeAmplifiers();
            for (AttributeAmplifier amp : amplifiers) {
                String attrName = amp.getAttributeName();
                Number value = amp.getValue();
                switch (amp.getType()) {
                    case INT: {
                        if (res.containsKey(attrName)) {
                            Number sum = res.get(attrName);
                            res.put(attrName, Utils.intAdd(sum, value));
                        } else {
                            res.put(attrName, value);
                        }
                    }
                    break;
                    case FLOAT: {
                        if (res.containsKey(attrName)) {
                            Number sum = res.get(attrName);
                            res.put(attrName, Utils.floatAdd(sum, value));
                        } else {
                            res.put(attrName, value);
                        }
                    }
                    break;
                    case DOUBLE: {
                        if (res.containsKey(attrName)) {
                            Number sum = res.get(attrName);
                            res.put(attrName, Utils.doubleAdd(sum, value));
                        } else {
                            res.put(attrName, value);
                        }
                    }
                    break;
                }
            }
        }
        return res;
    }

    @Nonnull
    public List<String> getPassiveSkills(int count) {
        List<String> res = new LinkedList<>();
        for (Node node : allNodes.subList(0, count)) {
            res.addAll(node.getPassiveSkills());
        }
        return res;
    }

    @Nonnull
    public List<String> getActiveSkills(int count) {
        List<String> res = new LinkedList<>();
        for (Node node : allNodes.subList(0, count)) {
            res.addAll(node.getActiveSkills());
        }
        return res;
    }

}

package net.liplum.masters;

import net.liplum.attributes.DataType;
import net.liplum.lib.utils.Utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class Routine {
    private ArrayList<Node> allNodes = new ArrayList<>();

    public Routine() {
    }

    @Nonnull
    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(@Nonnull ArrayList<Node> allNodes) {
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
                if (res.containsKey(attrName)) {
                    Number sum = res.get(attrName);
                    res.put(attrName, add(amp.getType(), sum, value));
                } else {
                    res.put(attrName, value);
                }
            }
        }
        return res;
    }

    private static Number add(DataType dataType, Number summand, Number addend) {
        if (dataType == DataType.Int) {
            return Utils.intAdd(summand, addend);
        }
        return Utils.floatAdd(summand, addend);
    }

    @Nullable
    public Node getNodeAt(int level) {
        if (allNodes.size() < level) {
            return null;
        }
        int index = level - 1;
        return allNodes.get(index);
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

package net.liplum.masteries;

import net.liplum.lib.utils.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class Routine {
    @NotNull
    private ArrayList<Node> allNodes = new ArrayList<>();

    public Routine() {
    }

    public Routine(@NotNull ArrayList<Node> allNodes) {
        this.allNodes = allNodes;
    }

    @NotNull
    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public Routine setAllNodes(@NotNull ArrayList<Node> allNodes) {
        this.allNodes = allNodes;
        return this;
    }

    public int getNodesCount() {
        return allNodes.size();
    }

    @NotNull
    public Map<String, Number> getAttributeAmplifiers(int level) {
        HashMap<String, Number> res = new HashMap<>();
        int index = 0;
        for (Node node : allNodes) {
            if (index >= level) {
                break;
            }
            List<AttrAmp> amplifiers = node.getAttributeAmplifiers();
            for (AttrAmp amp : amplifiers) {
                String attrName = amp.getAttributeName();
                Number value = amp.getValue();
                if (res.containsKey(attrName)) {
                    Number sum = res.get(attrName);
                    res.put(attrName, Utils.add(amp.getType(), sum, value));
                } else {
                    res.put(attrName, value);
                }
            }
            index++;
        }
        return res;
    }

    @Nullable
    public Node getNodeAt(int level) {
        if (allNodes.size() < level) {
            return null;
        }
        int index = level - 1;
        return allNodes.get(index);
    }

    @NotNull
    public Set<String> getPassiveSkills(int level) {
        int index = 0;
        Set<String> res = new HashSet<>();
        for (Node node : allNodes) {
            if (index >= level) {
                break;
            }
            List<String> ps = node.getPassiveSkills();
            res.addAll(ps);
            index++;
        }
        return res;
    }

    @NotNull
    public List<Integer> getLockedPassiveSkills(int level) {
        int index = 0;
        List<Integer> res = new LinkedList<>();
        for (Node node : allNodes) {
            if (index >= level) {
                break;
            }
            Integer skill = node.getLockedPassiveSkill();
            if (skill != null) {
                res.add(skill);
            }
            index++;
        }
        return res;
    }
}

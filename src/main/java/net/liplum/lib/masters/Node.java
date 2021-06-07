package net.liplum.lib.masters;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class Node {
    private List<AttributeAmplifier> attributeAmplifiers = new LinkedList<>();
    private List<String> passiveSkills = new LinkedList<>();
    private List<String> activeSkills = new LinkedList<>();

    @Nonnull
    public List<AttributeAmplifier> getAttributeAmplifiers() {
        return attributeAmplifiers;
    }

    public void setAttributeAmplifiers(@Nonnull List<AttributeAmplifier> attributeAmplifiers) {
        this.attributeAmplifiers = attributeAmplifiers;
    }

    @Nonnull
    public List<String> getPassiveSkills() {
        return passiveSkills;
    }

    public void setPassiveSkills(@Nonnull List<String> passiveSkills) {
        this.passiveSkills = passiveSkills;
    }

    @Nonnull
    public List<String> getActiveSkills() {
        return activeSkills;
    }

    public void setActiveSkills(@Nonnull List<String> activeSkills) {
        this.activeSkills = activeSkills;
    }

}

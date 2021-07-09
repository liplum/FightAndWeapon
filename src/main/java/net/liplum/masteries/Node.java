package net.liplum.masteries;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Node {
    @Nonnull
    private List<AttributeAmplifier> attributeAmplifiers = new LinkedList<>();
    @Nonnull
    private List<String> passiveSkills = new LinkedList<>();
    @Nonnull
    private List<String> activeSkills = new LinkedList<>();

    @Nonnull
    public List<AttributeAmplifier> getAttributeAmplifiers() {
        return attributeAmplifiers;
    }

    public Node setAttributeAmplifiers(@Nonnull List<AttributeAmplifier> attributeAmplifiers) {
        this.attributeAmplifiers = attributeAmplifiers;
        return this;
    }

    public Node addAttributeAmplifiers(AttributeAmplifier... attributeAmplifier) {
        this.attributeAmplifiers.addAll(Arrays.asList(attributeAmplifier));
        return this;
    }

    public Node addPassiveSkills(String... passiveSkills) {
        this.passiveSkills.addAll(Arrays.asList(passiveSkills));
        return this;
    }

    public Node addActiveSkills(String... activeSkills) {
        this.activeSkills.addAll(Arrays.asList(activeSkills));
        return this;
    }

    @Nonnull
    public List<String> getPassiveSkills() {
        return passiveSkills;
    }

    public Node setPassiveSkills(@Nonnull List<String> passiveSkills) {
        this.passiveSkills = passiveSkills;
        return this;
    }

    @Nonnull
    public List<String> getActiveSkills() {
        return activeSkills;
    }

    public Node setActiveSkills(@Nonnull List<String> activeSkills) {
        this.activeSkills = activeSkills;
        return this;
    }

}

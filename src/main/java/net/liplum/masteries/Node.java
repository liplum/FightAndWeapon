package net.liplum.masteries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Node {
    @Nonnull
    public static final Node Empty = new Node(){
        @Nonnull
        @Override
        public Node addAttrAmps(AttributeAmplifier... attributeAmplifier) {
            return super.addAttrAmps(attributeAmplifier);
        }

        @Nonnull
        @Override
        public Node addPassiveSkills(String... passiveSkills) {
            return super.addPassiveSkills(passiveSkills);
        }

        @Nonnull
        @Override
        public Node setAttributeAmplifiers(@Nonnull List<AttributeAmplifier> attributeAmplifiers) {
            return this;
        }

        @Nonnull
        @Override
        public Node setPassiveSkills(@Nonnull List<String> passiveSkills) {
            return this;
        }

        @Nonnull
        @Override
        public Node lockedPSkill(int slot) {
            return this;
        }
    };
    @Nonnull
    private List<AttributeAmplifier> attributeAmplifiers = new LinkedList<>();
    @Nonnull
    private List<String> passiveSkills = new LinkedList<>();
    @Nullable
    private Integer lockedPassiveSkill = null;

    @Nonnull
    public List<AttributeAmplifier> getAttributeAmplifiers() {
        return attributeAmplifiers;
    }

    @Nonnull
    public Node setAttributeAmplifiers(@Nonnull List<AttributeAmplifier> attributeAmplifiers) {
        this.attributeAmplifiers = attributeAmplifiers;
        return this;
    }

    @Nonnull
    public Node addAttrAmps(AttributeAmplifier... attributeAmplifier) {
        this.attributeAmplifiers.addAll(Arrays.asList(attributeAmplifier));
        return this;
    }

    @Nonnull
    public Node addPassiveSkills(String... passiveSkills) {
        this.passiveSkills.addAll(Arrays.asList(passiveSkills));
        return this;
    }

    @Nonnull
    public List<String> getPassiveSkills() {
        return passiveSkills;
    }

    @Nonnull
    public Node setPassiveSkills(@Nonnull List<String> passiveSkills) {
        this.passiveSkills = passiveSkills;
        return this;
    }

    @Nullable
    public Integer getLockedPassiveSkill() {
        return lockedPassiveSkill;
    }

    @Nonnull
    public Node lockedPSkill(int slot) {
        this.lockedPassiveSkill = slot;
        return this;
    }


}

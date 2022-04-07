package net.liplum.masteries;

import net.liplum.api.fight.IPassiveSkill;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {
    @NotNull
    public static final Node Empty = new Node() {
        @NotNull
        @Override
        public Node addAttrAmps(AttrAmp... attrAmp) {
            return super.addAttrAmps(attrAmp);
        }

        @NotNull
        @Override
        public Node addPassiveSkills(String... passiveSkills) {
            return super.addPassiveSkills(passiveSkills);
        }

        @NotNull
        @Override
        public Node setAttributeAmplifiers(@NotNull List<AttrAmp> attrAmps) {
            return this;
        }

        @NotNull
        @Override
        public Node setPassiveSkills(@NotNull List<String> passiveSkills) {
            return this;
        }

        @NotNull
        @Override
        public Node lockedPSkill(int slot) {
            return this;
        }
    };
    @NotNull
    private List<AttrAmp> attrAmps = new LinkedList<>();
    @NotNull
    private List<String> passiveSkills = new LinkedList<>();
    @Nullable
    private Integer lockedPassiveSkill = null;

    @NotNull
    public List<AttrAmp> getAttributeAmplifiers() {
        return attrAmps;
    }

    @NotNull
    public Node setAttributeAmplifiers(@NotNull List<AttrAmp> attrAmps) {
        this.attrAmps = attrAmps;
        return this;
    }

    @NotNull
    public Node addAttrAmps(AttrAmp... attrAmp) {
        this.attrAmps.addAll(Arrays.asList(attrAmp));
        return this;
    }

    @NotNull
    public Node addPassiveSkills(String... passiveSkills) {
        this.passiveSkills.addAll(Arrays.asList(passiveSkills));
        return this;
    }

    @NotNull
    public Node addPassiveSkills(IPassiveSkill<?>... passiveSkills) {
        this.passiveSkills.addAll(Arrays.stream(passiveSkills).map(IPassiveSkill::getRegisterName).collect(Collectors.toList()));
        return this;
    }

    @NotNull
    public List<String> getPassiveSkills() {
        return passiveSkills;
    }

    @NotNull
    public Node setPassiveSkills(@NotNull List<String> passiveSkills) {
        this.passiveSkills = passiveSkills;
        return this;
    }

    @Nullable
    public Integer getLockedPassiveSkill() {
        return lockedPassiveSkill;
    }

    @NotNull
    public Node lockedPSkill(int slot) {
        this.lockedPassiveSkill = slot;
        return this;
    }


}

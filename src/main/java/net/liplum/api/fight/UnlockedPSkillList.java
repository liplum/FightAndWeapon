package net.liplum.api.fight;

import net.liplum.api.annotations.Developing;

import javax.annotation.Nonnull;
import java.util.List;

@Developing
public class UnlockedPSkillList {
    private final List<Integer> slots;

    @Developing
    public UnlockedPSkillList(List<Integer> slots) {
        this.slots = slots;
    }

    @Nonnull
    @Developing
    public List<Integer> getSlots() {
        return slots;
    }
}

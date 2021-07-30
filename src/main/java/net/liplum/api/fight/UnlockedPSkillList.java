package net.liplum.api.fight;

import net.liplum.api.annotations.Developing;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

@Developing
public class UnlockedPSkillList {
    public static final UnlockedPSkillList Empty = new UnlockedPSkillList(Collections.emptyList());
    @Nonnull
    private final List<Integer> slots;

    @Developing
    public UnlockedPSkillList(@Nonnull List<Integer> slots) {
        this.slots = slots;
    }

    @Nonnull
    @Developing
    public List<Integer> getSlots() {
        return slots;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnlockedPSkillList) {
            UnlockedPSkillList b = (UnlockedPSkillList) obj;
            if (b.slots.size() != this.slots.size()) {
                return false;
            }
            return b.slots.containsAll(this.slots);
        }
        return false;
    }
}

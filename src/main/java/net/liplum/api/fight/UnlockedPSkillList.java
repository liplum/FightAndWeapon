package net.liplum.api.fight;

import net.liplum.api.annotations.Developing;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@Developing
public class UnlockedPSkillList {
    public static final UnlockedPSkillList Empty = new UnlockedPSkillList(Collections.emptyList());
    @NotNull
    private final List<Integer> slots;

    @Developing
    public UnlockedPSkillList(@NotNull List<Integer> slots) {
        this.slots = slots;
    }

    @NotNull
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

package net.liplum.masteries;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.Attribute;
import net.liplum.attributes.IAttribute;
import net.liplum.lib.math.MathUtil;

import javax.annotation.Nonnull;
import java.util.*;

public class Mastery implements IMastery {
    public static final int MaxLevel = 99;
    @Nonnull
    private final WeaponType weaponType;
    @Nonnull
    private final HashSet<Map<IAttribute, AttrDelta>> attrAmpsPool = new HashSet<>();
    @Nonnull
    private final HashSet<Set<IPassiveSkill<?>>> pSkillsPool = new HashSet<>();
    @Nonnull
    private final HashSet<UnlockedPSkillList> unlockedPSkillsPool = new HashSet<>();
    @Nonnull
    private Routine routine;
    private ArrayList<Map<IAttribute, AttrDelta>> attrAmpsCache;
    private ArrayList<Set<IPassiveSkill<?>>> pSkillsCache;
    private ArrayList<UnlockedPSkillList> unlockedPSkillListCache;

    /**
     * Whenever you create the instance, it will register it self to {@link MasteryRegistry} automatically.
     *
     * @param weaponType the corresponding weapon type of this master
     */
    public Mastery(@Nonnull WeaponType weaponType) {
        this.weaponType = weaponType;
        this.routine = new Routine();
        genCache();
        MasteryRegistry.register(this);
    }

    @Nonnull
    public Routine getRoutine() {
        return routine;
    }

    @Nonnull
    public Mastery setRoutine(@Nonnull Routine routine) {
        this.routine = routine;
        genCache();
        return this;
    }

    private void genCache() {
        int nodesCount = routine.getNodesCount();
        attrAmpsPool.clear();
        pSkillsPool.clear();
        unlockedPSkillsPool.clear();
        attrAmpsCache = new ArrayList<>(nodesCount);
        pSkillsCache = new ArrayList<>(nodesCount);
        unlockedPSkillListCache = new ArrayList<>(nodesCount);
        int level = 1;
        for (int i = 0; i < nodesCount; i++, level++) {
            genAttrAmpCache(level);
            genPSkillsCache(level);
            genUnlockedPSkillsCache(level);
        }
    }

    private void genAttrAmpCache(int level) {
        Map<String, Number> source = routine.getAttributeAmplifiers(level);
        Map<IAttribute, AttrDelta> attrAmps = new HashMap<>();
        for (Map.Entry<String, Number> entry : source.entrySet()) {
            IAttribute attribute = Attribute.getAttribute(entry.getKey());
            if (attribute != null) {
                attrAmps.put(attribute, attribute.newAttrDelta(entry.getValue()));
            }
        }

        Optional<Map<IAttribute, AttrDelta>> maybeAttrAmps = attrAmpsPool.stream()
                .filter(e -> {
                    if (e.size() != attrAmps.size()) {
                        return false;
                    }
                    Set<Map.Entry<IAttribute, AttrDelta>> eEntries = e.entrySet();
                    Set<Map.Entry<IAttribute, AttrDelta>> attrAmpsEntries = attrAmps.entrySet();
                    for (Map.Entry<IAttribute, AttrDelta> eEntry : eEntries) {
                        for (Map.Entry<IAttribute, AttrDelta> attrAmpsEntry : attrAmpsEntries) {
                            if ((!eEntry.getKey().equals(attrAmpsEntry.getKey())) ||
                                    (!eEntry.getValue().equals(attrAmpsEntry.getValue()))
                            ) {
                                return false;
                            }
                        }

                    }
                    return true;
                })
                .findFirst();
        if (maybeAttrAmps.isPresent()) {
            attrAmpsCache.add(maybeAttrAmps.get());
        } else {
            attrAmpsPool.add(attrAmps);
            attrAmpsCache.add(attrAmps);
        }
    }

    private void genPSkillsCache(int level) {
        Set<IPassiveSkill<?>> pSkills = new HashSet<>();
        for (String name : routine.getPassiveSkills(level)) {
            IPassiveSkill<?> skill = SkillRegistry.getPassiveSkillsFromName(name);
            if (skill != null) {
                pSkills.add(skill);
            }
        }
        Optional<Set<IPassiveSkill<?>>> maybePSkills = pSkillsPool.stream()
                .filter(e -> e.size() == pSkills.size() && e.containsAll(pSkills))
                .findFirst();
        if (maybePSkills.isPresent()) {
            pSkillsCache.add(maybePSkills.get());
        } else {
            pSkillsPool.add(pSkills);
            pSkillsCache.add(pSkills);
        }
    }

    private void genUnlockedPSkillsCache(int level) {
        UnlockedPSkillList unlockedPSkillList = new UnlockedPSkillList(routine.getLockedPassiveSkills(level));
        Optional<UnlockedPSkillList> maybeUnlockedPSkillList = unlockedPSkillsPool.stream()
                .filter(unlockedPSkillList::equals)
                .findFirst();
        if (maybeUnlockedPSkillList.isPresent()) {
            unlockedPSkillListCache.add(maybeUnlockedPSkillList.get());
        } else {
            unlockedPSkillsPool.add(unlockedPSkillList);
            unlockedPSkillListCache.add(unlockedPSkillList);
        }
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Nonnull
    @Override
    public Map<IAttribute, AttrDelta> getAttributeAmplifiers(int level) {
        if (level < 1) {
            return Collections.emptyMap();
        }
        int index = MathUtil.fixMax(level, attrAmpsCache.size()) - 1;
        try {
            return attrAmpsCache.get(index);
        } catch (Exception e) {
            Map<String, Number> source = routine.getAttributeAmplifiers(level);
            Map<IAttribute, AttrDelta> res = new HashMap<>();
            for (Map.Entry<String, Number> entry : source.entrySet()) {
                IAttribute attribute = Attribute.getAttribute(entry.getKey());
                if (attribute != null) {
                    res.put(attribute, attribute.newAttrDelta(entry.getValue()));
                }
            }
            return res;
        }
    }

    @Nonnull
    @Override
    public Set<IPassiveSkill<?>> getPassiveSkills(int level) {
        if (level < 1) {
            return Collections.emptySet();
        }
        int index = MathUtil.fixMax(level, pSkillsCache.size()) - 1;
        try {
            return pSkillsCache.get(index);
        } catch (Exception e) {
            Set<IPassiveSkill<?>> res = new HashSet<>();
            for (String name : routine.getPassiveSkills(level)) {
                IPassiveSkill<?> skill = SkillRegistry.getPassiveSkillsFromName(name);
                if (skill != null) {
                    res.add(skill);
                }
            }
            return res;
        }
    }

    @Nonnull
    @Override
    public UnlockedPSkillList getUnlockedPassiveSkills(int level) {
        if (level < 1) {
            return UnlockedPSkillList.Empty;
        }
        int index = MathUtil.fixMax(level, unlockedPSkillListCache.size()) - 1;
        try {
            return unlockedPSkillListCache.get(index);
        } catch (Exception e) {
            return new UnlockedPSkillList(routine.getLockedPassiveSkills(level));
        }
    }
}

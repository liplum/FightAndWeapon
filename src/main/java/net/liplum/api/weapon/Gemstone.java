package net.liplum.api.weapon;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.GemstoneRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;


public class Gemstone implements IGemstone {
    private final String registerName;
    private final AmplifierOfCores amplifierOfCores = new AmplifierOfCores();
    private final AmplifierOfWeaponTypes amplifierOfWeaponTypes = new AmplifierOfWeaponTypes();
    private final AmplifierOfAllWeaponTypes amplifierOfAllWeaponTypes = new AmplifierOfAllWeaponTypes();

    private final GemQuality gemQuality;
    private final int displayedOrderID;

    /**
     * Whenever you create the instance, it will register itself to {@link GemstoneRegistry} automatically.
     *
     * @param registerName the name to register itself
     */
    public Gemstone(@Nonnull String registerName) {
        this(registerName, GemQuality.None, Integer.MAX_VALUE);
    }

    /**
     * Whenever you create the instance, it will register itself to {@link GemstoneRegistry} automatically.
     *
     * @param registerName the name to register itself
     */
    public Gemstone(@Nonnull String registerName, int displayedOrderID) {
        this(registerName, GemQuality.None, displayedOrderID);
    }

    /**
     * Whenever you create the instance, it will register itself to {@link GemstoneRegistry} automatically.
     *
     * @param registerName the name to register itself
     */
    public Gemstone(@Nonnull String registerName, @Nonnull GemQuality quality) {
        this(registerName, quality, Integer.MAX_VALUE);
    }

    /**
     * Whenever you create the instance, it will register itself to {@link GemstoneRegistry} automatically.
     *
     * @param registerName the name to register itself
     */
    public Gemstone(@Nonnull String registerName, @Nonnull GemQuality quality, int displayedOrderID) {
        this.registerName = registerName;
        this.gemQuality = quality;
        this.displayedOrderID = displayedOrderID;
        GemstoneRegistry.register(this);
    }

    @Override
    public String getRegisterName() {
        return registerName;
    }

    @Override
    public boolean hasModifierOf(WeaponCore core) {
        return amplifierOfCores.hasAnyAmplifier(core);
    }

    @Override
    public boolean hasPassiveSkillOfCore(WeaponCore core, IPassiveSkill<?> passiveSkill) {
        Stream<IPassiveSkill<?>> ofCore = amplifierOfCores.getPassiveSkillsOf(core).stream();
        Stream<IPassiveSkill<?>> ofWeaponType = amplifierOfWeaponTypes.getPassiveSkillsOf(core.getWeaponType()).stream();
        Stream<IPassiveSkill<?>> ofAllWeaponType = amplifierOfAllWeaponTypes.getPassiveSkills().stream();
        Stream<IPassiveSkill<?>> OfAll = Stream.concat(Stream.concat(ofCore, ofWeaponType), ofAllWeaponType);
        return OfAll.anyMatch(passiveSkill::equals);
    }

    @Override
    public boolean hasAnyPassiveSkillOfCore(WeaponCore core) {
        return amplifierOfAllWeaponTypes.getPassiveSkills().size() > 0 ||
                amplifierOfWeaponTypes.getPassiveSkillsOf(core.getWeaponType()).size() > 0 ||
                amplifierOfCores.getPassiveSkillsOf(core).size() > 0
                ;
    }

    @Override
    public boolean hasPassiveSkillOfWeaponType(WeaponType weaponType, IPassiveSkill<?> passiveSkill) {
        Stream<IPassiveSkill<?>> ofWeaponType = amplifierOfWeaponTypes.getPassiveSkillsOf(weaponType).stream();
        Stream<IPassiveSkill<?>> ofAllWeaponType = amplifierOfAllWeaponTypes.getPassiveSkills().stream();
        Stream<IPassiveSkill<?>> OfAll = Stream.concat(ofWeaponType, ofAllWeaponType);
        return OfAll.anyMatch(passiveSkill::equals);
    }

    @Override
    public boolean hasAnyPassiveSkillOfWeaponType(WeaponType weaponType) {
        return amplifierOfAllWeaponTypes.getPassiveSkills().size() > 0 ||
                amplifierOfWeaponTypes.getPassiveSkillsOf(weaponType).size() > 0
                ;
    }

    @Override
    public boolean hasPassiveSkillOfAll(IPassiveSkill<?> passiveSkill) {
        return amplifierOfAllWeaponTypes.getPassiveSkills().contains(passiveSkill);
    }

    @Override
    public boolean hasAnyPassiveSkillOfAll() {
        return amplifierOfAllWeaponTypes.getPassiveSkills().size() > 0;
    }

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Override
    @Nullable
    public Modifier getModifierOf(WeaponCore core) {
        return amplifierOfCores.getModifierOf(core);
    }

    @Nonnull
    @Override
    public Collection<IPassiveSkill<?>> getPassiveSkillsOf(WeaponCore core) {
        Collection<IPassiveSkill<?>> psFromAll = amplifierOfAllWeaponTypes.getPassiveSkills();
        Set<IPassiveSkill<?>> skills = new HashSet<>(psFromAll);

        skills.addAll(amplifierOfWeaponTypes.getPassiveSkillsOf(core.getWeaponType()));

        skills.addAll(amplifierOfCores.getPassiveSkillsOf(core));

        return skills;
    }

    @Override
    public IGemstone addModifier(Modifier newModifier) {
        amplifierOfCores.addModifier(newModifier);
        return this;
    }

    @Override
    public IGemstone addPassiveSkillsToCore(WeaponCore core, IPassiveSkill<?>... newPassiveSkills) {
        for (IPassiveSkill<?> passiveSkill : newPassiveSkills) {
            amplifierOfCores.addPassiveSkills(core, passiveSkill);
        }
        return this;
    }

    @Override
    public IGemstone addPassiveSkillsToWeaponType(WeaponType weaponType, IPassiveSkill<?>... newPassiveSkills) {
        for (IPassiveSkill<?> passiveSkill : newPassiveSkills) {
            amplifierOfWeaponTypes.addPassiveSkills(weaponType, passiveSkill);
        }
        return this;
    }

    @Override
    public IGemstone addPassiveSkillsToAll(IPassiveSkill<?>... newPassiveSkills) {
        for (IPassiveSkill<?> passiveSkill : newPassiveSkills) {
            amplifierOfAllWeaponTypes.addPassiveSkills(passiveSkill);
        }
        return this;
    }

    @Override
    public IGemstone addPassiveSkillToWeaponTypes(IPassiveSkill<?> newPassiveSkill, WeaponType... weaponTypes) {
        for (WeaponType weaponType : weaponTypes) {
            amplifierOfWeaponTypes.addPassiveSkills(weaponType, newPassiveSkill);
        }
        return this;
    }

    @Override
    public IGemstone addPassiveSkillsToCores(IPassiveSkill<?> newPassiveSkill, WeaponCore... weaponCores) {
        for (WeaponCore weaponCore : weaponCores) {
            amplifierOfCores.addPassiveSkills(weaponCore, newPassiveSkill);
        }
        return this;
    }

    @Override
    public IGemstone removeModifier(WeaponCore core) {
        amplifierOfCores.removeModifier(core);
        return this;
    }

    @Override
    public IGemstone removeModifier(Modifier modifier) {
        amplifierOfCores.removeModifier(modifier.getCore());
        return this;
    }

    @Override
    public IGemstone removePassiveSkillFromCore(WeaponCore core, IPassiveSkill<?> passiveSkill) {
        amplifierOfCores.removePassiveSkill(core, passiveSkill);
        return this;
    }

    @Override
    public IGemstone removePassiveSkillFromWeaponType(WeaponType weaponType, IPassiveSkill<?> passiveSkill) {
        amplifierOfWeaponTypes.removePassiveSkill(weaponType, passiveSkill);
        return this;
    }

    @Override
    public IGemstone removePassiveSkillFromAll(IPassiveSkill<?> passiveSkill) {
        amplifierOfAllWeaponTypes.removePassiveSkill(passiveSkill);
        return this;
    }

    @Override
    public boolean hasAnyAmplifier(WeaponCore core) {
        if (hasModifierOf(core)) {
            return true;
        }
        if (amplifierOfAllWeaponTypes.hasAnyAmplifier()) {
            return true;
        }
        return amplifierOfWeaponTypes.hasAnyAmplifier(core.getWeaponType());
    }

    @Override
    public int getDisplayedOrderID() {
        return displayedOrderID;
    }

    @Nonnull
    @Override
    public GemQuality getQuality() {
        return gemQuality;
    }


    private static class AmplifierOfCores {
        private final Map<WeaponCore, CoreAmplifier> amplifiers = new HashMap<>();

        @Nullable
        public Modifier getModifierOf(WeaponCore core) {
            if (amplifiers.containsKey(core)) {
                return amplifiers.get(core).getModifier();
            }
            return null;
        }

        public boolean hasAnyAmplifier(WeaponCore core) {
            if (amplifiers.containsKey(core)) {
                return amplifiers.get(core).hasAny();
            }
            return false;
        }

        @Nonnull
        public Collection<IPassiveSkill<?>> getPassiveSkillsOf(WeaponCore core) {
            if (this.amplifiers.containsKey(core)) {
                return this.amplifiers.get(core).getPassiveSkills();
            }
            return Collections.emptyList();
        }

        public void addModifier(Modifier modifier) {
            WeaponCore core = modifier.getCore();
            if (amplifiers.containsKey(core)) {
                amplifiers.get(core).setModifier(modifier);
            } else {
                CoreAmplifier amplifier = new CoreAmplifier();
                amplifier.setModifier(modifier);
                amplifiers.put(core, amplifier);
            }
        }

        public void addPassiveSkills(WeaponCore core, IPassiveSkill<?> passiveSkill) {
            if (amplifiers.containsKey(core)) {
                amplifiers.get(core).addPassiveSkills(passiveSkill);
            } else {
                CoreAmplifier amplifier = new CoreAmplifier();
                amplifier.addPassiveSkills(passiveSkill);
                amplifiers.put(core, amplifier);
            }
        }

        public void removeModifier(WeaponCore core) {
            if (amplifiers.containsKey(core)) {
                amplifiers.get(core).removeModifier();
            }
        }

        public void removePassiveSkill(WeaponCore core, IPassiveSkill<?> passiveSkill) {
            if (amplifiers.containsKey(core)) {
                amplifiers.get(core).removePassiveSkill(passiveSkill);
            }
        }

        private static class CoreAmplifier {
            private final Set<IPassiveSkill<?>> passiveSkills = new HashSet<>();
            private Modifier modifier = null;

            @Nullable
            public Modifier getModifier() {
                return modifier;
            }

            public void setModifier(Modifier modifier) {
                this.modifier = modifier;
            }

            @Nonnull
            public Collection<IPassiveSkill<?>> getPassiveSkills() {
                return passiveSkills;
            }

            public void addPassiveSkills(IPassiveSkill<?> passiveSkill) {
                passiveSkills.add(passiveSkill);
            }

            public void removeModifier() {
                modifier = null;
            }

            public void removePassiveSkill(IPassiveSkill<?> passiveSkill) {
                passiveSkills.remove(passiveSkill);
            }

            public boolean hasAny() {
                return modifier != null || passiveSkills.size() > 0;
            }
        }
    }

    private static class AmplifierOfWeaponTypes {
        private final Map<WeaponType, Set<IPassiveSkill<?>>> amplifiers = new HashMap<>();

        @Nonnull
        public Collection<IPassiveSkill<?>> getPassiveSkillsOf(WeaponType weaponType) {
            if (amplifiers.containsKey(weaponType)) {
                return amplifiers.get(weaponType);
            }
            return Collections.emptyList();
        }

        public void addPassiveSkills(WeaponType weaponType, IPassiveSkill<?> passiveSkill) {
            if (amplifiers.containsKey(weaponType)) {
                amplifiers.get(weaponType).add(passiveSkill);
            } else {
                HashSet<IPassiveSkill<?>> skills = new HashSet<>();
                skills.add(passiveSkill);
                amplifiers.put(weaponType, skills);
            }
        }

        public void removePassiveSkill(WeaponType weaponType, IPassiveSkill<?> passiveSkill) {
            if (amplifiers.containsKey(weaponType)) {
                amplifiers.get(weaponType).remove(passiveSkill);
            }
        }

        public boolean hasAnyAmplifier(WeaponType weaponType) {
            return amplifiers.containsKey(weaponType);
        }
    }

    private static class AmplifierOfAllWeaponTypes {
        private final Set<IPassiveSkill<?>> amplifiers = new HashSet<>();

        @Nonnull
        public Collection<IPassiveSkill<?>> getPassiveSkills() {
            return amplifiers;
        }

        public void addPassiveSkills(IPassiveSkill<?> passiveSkill) {
            amplifiers.add(passiveSkill);
        }

        public void removePassiveSkill(IPassiveSkill<?> passiveSkill) {
            amplifiers.remove(passiveSkill);
        }

        public boolean hasAnyAmplifier() {
            return amplifiers.size() > 0;
        }
    }

}

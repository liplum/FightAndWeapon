package net.liplum.api.weapon;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.GemstoneRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;


public class Gemstone implements IGemstone {
    private final String registerName;
    private final AmplifierOfCores amplifierOfCores = new AmplifierOfCores();
    private final AmplifierOfWeaponTypes amplifierOfWeaponTypes = new AmplifierOfWeaponTypes();
    private final AmplifierOfAllWeaponTypes amplifierOfAllWeaponTypes = new AmplifierOfAllWeaponTypes();

    /**
     * Whenever you create the instance, it will register itself to {@link GemstoneRegistry} automatically.
     * @param registerName the name to register itself
     */
    public Gemstone(String registerName) {
        this.registerName = registerName;
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
    public IPassiveSkill<?>[] getPassiveSkillsOf(WeaponCore core) {
        Collection<IPassiveSkill<?>> psFromAll = amplifierOfAllWeaponTypes.getPassiveSkills();
        Set<IPassiveSkill<?>> skills = new HashSet<>(psFromAll);


        Collection<IPassiveSkill<?>> psFromWeaponTypes = amplifierOfWeaponTypes.getPassiveSkills(core.getWeaponType());
        if (psFromWeaponTypes != null) {
            skills.addAll(psFromWeaponTypes);
        }

        Collection<IPassiveSkill<?>> psFromCores = amplifierOfCores.getPassiveSkillsOf(core);
        if (psFromCores != null) {
            skills.addAll(psFromCores);
        }

        return skills.toArray(new IPassiveSkill[0]);
    }

    @Override
    public IGemstone addModifier(Modifier newModifier) {
        amplifierOfCores.addModifier(newModifier);
        return this;
    }

    @Override
    public IGemstone addPassiveSkillToCore(WeaponCore core, IPassiveSkill<?> newPassiveSkill) {
        amplifierOfCores.addPassiveSkills(core, newPassiveSkill);
        return this;
    }

    @Override
    public IGemstone addPassiveSkillToWeaponType(WeaponType weaponType, IPassiveSkill<?> newPassiveSkill) {
        amplifierOfWeaponTypes.addPassiveSkills(weaponType, newPassiveSkill);
        return this;
    }

    @Override
    public IGemstone addPassiveSkillToAll(IPassiveSkill<?> newPassiveSkill) {
        amplifierOfAllWeaponTypes.addPassiveSkills(newPassiveSkill);
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


    private static class AmplifierOfCores {
        private final Map<WeaponCore, CoreAmplifier> amplifiers = new HashMap<>();

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

        @Nullable
        public Collection<IPassiveSkill<?>> getPassiveSkillsOf(WeaponCore core) {
            if (this.amplifiers.containsKey(core)) {
                return this.amplifiers.get(core).getPassiveSkills();
            }
            return null;
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

        @Nullable
        public Collection<IPassiveSkill<?>> getPassiveSkills(WeaponType weaponType) {
            if (amplifiers.containsKey(weaponType)) {
                return amplifiers.get(weaponType);
            }
            return null;
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

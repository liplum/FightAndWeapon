package net.liplum.items.weapons.lance;

import net.liplum.api.weapon.WeaponArgs;

public class LanceArgs extends WeaponArgs<LanceArgs> {
    private float sprintStrength = 0;

    public LanceArgs() {
    }

    public float getSprintStrength() {
        return sprintStrength;
    }

    public LanceArgs setSprintStrength(float sprintStrength) {
        this.sprintStrength = sprintStrength;
        return this;
    }
}

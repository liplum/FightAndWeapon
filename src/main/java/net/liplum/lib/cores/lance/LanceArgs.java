package net.liplum.lib.cores.lance;

import net.liplum.api.weapon.WeaponArgs;

public class LanceArgs extends WeaponArgs<LanceArgs> {
    private float sprintLength = 0;

    public LanceArgs() {
    }

    public float getSprintLength() {
        return sprintLength;
    }

    public LanceArgs setSprintLength(float sprintLength) {
        this.sprintLength = sprintLength;
        return this;
    }
}

package net.liplum.api.fight;

public class CoolDown {
    private int restTicks;

    public CoolDown(int restTicks) {
        this.restTicks = restTicks;
    }

    public void tick() {
        restTicks--;
    }

    public boolean isFinished() {
        return restTicks <= 0;
    }

    public boolean isInCoolingDown() {
        return !isFinished();
    }

    public int getRestTicks() {
        return restTicks;
    }
}

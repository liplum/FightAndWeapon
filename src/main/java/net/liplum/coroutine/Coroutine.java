package net.liplum.coroutine;

import net.liplum.enumerator.IEnumerable;

public class Coroutine {
    private int lifeSpan = -1;
    private int currentTime = 0;
    private final IEnumerable task;

    public Coroutine(IEnumerable task) {
        this.task = task;
    }

    public Coroutine(IEnumerable task, int lifeSpan) {
        this(task);
        int lf = lifeSpan;
        if (lf <= 0) {
            lf = -1;
        }
        this.lifeSpan = lf;
    }

    public void onTick() {
        currentTime++;
    }

    public boolean isDead() {
        if (lifeSpan == -1) {
            return false;
        }
        return currentTime >= lifeSpan;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public IEnumerable getTask() {
        return task;
    }
}

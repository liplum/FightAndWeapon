package net.liplum.coroutine;

public final class WaitForTicks implements IWaitable {
    private int tick = 0;

    public WaitForTicks(int tick) {
        this.tick = tick;
    }

    @Override
    public void onTick() {
        tick--;
    }

    @Override
    public boolean isFinished() {
        return tick <= 0;
    }
}

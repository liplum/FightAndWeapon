package net.liplum.coroutine;

public class NotWait implements IWaitable {
    @Override
    public void onTick() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

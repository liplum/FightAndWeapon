package net.liplum.coroutine;

public interface IWaitable {
    void onTick();
    boolean isFinished();
}

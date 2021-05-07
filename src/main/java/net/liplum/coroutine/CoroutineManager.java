package net.liplum.coroutine;

import net.liplum.enumerator.IEnumerable;
import net.liplum.enumerator.IEnumerator;

import java.util.LinkedList;

public class CoroutineManager {
    public CoroutineManager() {
    }

    private final LinkedList<Coroutine> coroutinesList = new LinkedList<>();

    public Coroutine startCoroutine(IEnumerable task) {
        Coroutine c = new Coroutine(task);
        startCoroutine(c);
        return c;
    }

    public Coroutine startCoroutine(IEnumerable task, int lifeSpan) {
        Coroutine c = new Coroutine(task, lifeSpan);
        startCoroutine(c);
        return c;
    }

    public Coroutine startCoroutine(Coroutine task) {
        coroutinesList.addLast(task);
        return task;
    }

    public void StopCoroutine(Coroutine coroutine) {
        coroutinesList.remove(coroutine);
    }

    public void OnTick() {
        LinkedList<Coroutine> needRemoves = new LinkedList<>();
        for (Coroutine c : coroutinesList) {
            c.onTick();
            if (c.isDead()) {
                needRemoves.addLast(c);
            }
            else {
                boolean hasRest = true;
                IEnumerable ie = c.getTask();
                IEnumerator ier = ie.getEnumerator();
                Object currentResult = ier.getCurrent();
                if (currentResult instanceof IWaitable) {
                    IWaitable wait = (IWaitable) currentResult;
                    wait.onTick();
                    if (wait.isFinished()) {
                        hasRest = ier.MoveNext();
                    }
                } else {
                    hasRest = ier.MoveNext();
                }

                if (!hasRest) {
                    needRemoves.addLast(c);
                }
            }
        }
        for (Coroutine needRemove : needRemoves) {
            coroutinesList.remove(needRemove);
        }
    }
}

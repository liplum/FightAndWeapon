package net.liplum.enumerator;


public abstract class Yield<T> implements IEnumerable<T> {

    protected abstract void task();

    private T current = null;
    private boolean hasNext = false;

    private void yieldNext() {
        hasNext = false;
        task();
    }

    protected final void yieldReturn(T item) {
        current = item;
        hasNext = true;
    }

    protected final void yieldBreak() {
        hasNext = false;
    }

    private IEnumerator<T> enumerator = null;

    @Override
    public IEnumerator<T> getEnumerator() {
        if (enumerator == null) {
            yieldNext();
            enumerator = new IEnumerator<T>() {
                @Override
                public T getCurrent() {
                    return current;
                }

                @Override
                public boolean MoveNext() {
                    yieldNext();
                    return hasNext;
                }
            };
        }
        return enumerator;
    }
}

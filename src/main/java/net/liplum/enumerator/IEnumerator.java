package net.liplum.enumerator;

public interface IEnumerator<T> {
    T getCurrent();
    boolean MoveNext();
}

package net.liplum.enumerator;

public interface IEnumerable<T> {
    IEnumerator<T> getEnumerator();
}

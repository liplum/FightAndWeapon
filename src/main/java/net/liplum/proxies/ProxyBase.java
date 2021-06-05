package net.liplum.proxies;

public abstract class ProxyBase {
    public abstract boolean isServer();

    public boolean isClient() {
        return !isServer();
    }

    public abstract void init();
}

package net.liplum.lib.cores.harp;

public class ContinuousHarpArgs extends AbstractHarpArgs<ContinuousHarpArgs> {
    private int releasedCount = 0;

    public ContinuousHarpArgs() {
    }

    /**
     * @return base on 1 (such as 1,2,3,4,5,...)
     */
    public int getReleasedCount() {
        return releasedCount;
    }

    /**
     * @param releasedCount base on 1 (such as 1,2,3,4,5,...)
     * @return
     */
    public ContinuousHarpArgs setReleasedCount(int releasedCount) {
        this.releasedCount = releasedCount;
        return this;
    }
}

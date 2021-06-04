package net.liplum.lib.masters;

public class LvExpPair implements Cloneable {
    private int level = 0;
    private long exp = 0;

    public LvExpPair() {
    }

    public LvExpPair(int level, long exp) {
        this.level = level;
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    @Override
    protected Object clone() {
        return new LvExpPair(level, exp);
    }
}

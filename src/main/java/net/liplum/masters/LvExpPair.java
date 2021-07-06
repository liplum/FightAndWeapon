package net.liplum.masters;

public class LvExpPair implements Cloneable {
    public static int BaseLevel = 1;
    public static long BaseExp = 0;

    private int level = BaseLevel;
    private long exp = BaseExp;

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
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

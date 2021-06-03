package net.liplum.lib.masters;

public class LvExpPair {
    private int level = 0;
    private long Exp = 0;

    public LvExpPair() {
    }

    public LvExpPair(int level, long exp) {
        this.level = level;
        Exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExp() {
        return Exp;
    }

    public void setExp(long exp) {
        Exp = exp;
    }
}

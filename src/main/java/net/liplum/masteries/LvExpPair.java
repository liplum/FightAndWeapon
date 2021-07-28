package net.liplum.masteries;

public class LvExpPair implements Cloneable {
    public static final LvExpPair Empty = new LvExpPair() {
        @Override
        public void setLevel(int level) {
        }

        @Override
        public void setExp(int exp) {
        }

        @Override
        public void addExp(int exp) {
        }
    };

    public static int BaseLevel = 1;
    public static int BaseExp = 0;

    private int level = BaseLevel;
    private int exp = BaseExp;

    public LvExpPair() {
    }

    public LvExpPair(int level, int exp) {
        this.level = level;
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int exp) {
        this.exp += exp;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

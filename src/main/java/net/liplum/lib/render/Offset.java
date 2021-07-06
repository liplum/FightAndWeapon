package net.liplum.lib.render;

public class Offset {
    private int x = 0;
    private int y = 0;

    public Offset(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Offset() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Offset add(int x, int y) {
        return new Offset(this.x + x, this.y + y);
    }

    public Offset add(Offset other) {
        return this.add(other.x, other.y);
    }

    public Offset minus(Offset other) {
        return this.minus(other.x, other.y);
    }

    public Offset minus(int x, int y) {
        return new Offset(this.x - x, this.y - y);
    }
}

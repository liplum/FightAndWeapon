package net.liplum.lib.gui;

public class View implements IView {
    public final int width;
    public final int height;

    public View(int width, int height) {
        if (width < 0 || height < 0) {
            throw new ViewSizeOutOfRangeException();
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLeft() {
        return 0;
    }

    @Override
    public int getTop() {
        return 0;
    }

    @Override
    public int getRight() {
        return width;
    }

    @Override
    public int getBottom() {
        return height;
    }
}

package net.liplum.lib.gui;

import net.liplum.lib.math.MathUtil;

import javax.annotation.Nonnull;

public interface IView {
    int getWidth();

    int getHeight();

    int getLeft();

    int getTop();

    int getRight();

    int getBottom();

    default IView slice(int xOffset, int yOffset, int width, int height) {
        return new SubView(this, xOffset, yOffset, width, height);
    }

    default IView slice(int width, int height) {
        return new SubView(this, 0, 0, width, height);
    }
}

class SubView implements IView {
    private final int width;
    private final int height;

    private final int top;
    private final int left;
    private final int right;
    private final int bottom;

    public SubView(@Nonnull IView parent, int xOffset, int yOffset, int width, int height) {
        if (xOffset < 0 || yOffset < 0 || width < 0 || height < 0) {
            throw new ViewSizeOutOfRangeException();
        }
        this.width = width;
        this.height = height;

        if (xOffset > parent.getWidth() || yOffset > parent.getHeight()) {
            throw new ViewSizeOutOfRangeException();
        }

        this.left = parent.getLeft() + xOffset;
        this.top = parent.getTop() + yOffset;

        this.right = MathUtil.fixMax(this.left + width, parent.getRight());
        this.bottom = MathUtil.fixMax(this.top + height, parent.getBottom());
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
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public int getRight() {
        return right;
    }

    @Override
    public int getBottom() {
        return bottom;
    }
}

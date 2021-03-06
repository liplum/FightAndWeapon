package net.liplum.lib.gui;

import net.liplum.lib.math.MathUtil;
import org.jetbrains.annotations.NotNull;

public class View {
    private final int width;
    private final int height;

    public View(int width, int height) {
        if (width < 0 || height < 0) {
            throw new ViewSizeOutOfRangeException();
        }
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLeft() {
        return 0;
    }

    public int getTop() {
        return 0;
    }

    public int getRight() {
        return width;
    }

    public int getBottom() {
        return height;
    }

    public View slice(int xOffset, int yOffset, int width, int height) {
        return new SubView(this, xOffset, yOffset, width, height);
    }

    public View slice(int width, int height) {
        return new SubView(this, width, height);
    }

/*    public View[] splitByCell(int cellWidth, int cellHeight, int row, int column) {
        if (cellWidth <= 0 || cellHeight <= 0 || row <= 0 || column <= 0) {
            throw new ArithmeticException();
        }
        if (cellWidth > getWidth() || cellHeight > getHeight()) {
            throw new ViewSizeOutOfRangeException();
        }
        //Now  cellWidth > width ,cellHeight > height ,row > 0 ,column > 0
        int finalWidth = getWidth() - (getWidth() % cellWidth);
        int finalHeight = getHeight() - (getHeight() % cellHeight);
        int finalRow = Math.min(row, finalHeight / cellHeight);
        int finalColumn = Math.min(column, finalWidth / cellWidth);

    }*/
}

class SubView extends View {
    private final int top;
    private final int left;
    private final int right;
    private final int bottom;

    public SubView(@NotNull View parent, int xOffset, int yOffset, int width, int height) {
        super(width, height);
        if (xOffset < 0 || yOffset < 0) {
            throw new ViewSizeOutOfRangeException();
        }

        if (xOffset > parent.getWidth() || yOffset > parent.getHeight()) {
            throw new ViewSizeOutOfRangeException();
        }

        this.left = parent.getLeft() + xOffset;
        this.top = parent.getTop() + yOffset;

        this.right = MathUtil.fixMax(this.left + width, parent.getRight());
        this.bottom = MathUtil.fixMax(this.top + height, parent.getBottom());
    }

    public SubView(@NotNull View parent, int width, int height) {
        super(width, height);

        this.left = parent.getLeft();
        this.top = parent.getTop();

        this.right = MathUtil.fixMax(this.left + width, parent.getRight());
        this.bottom = MathUtil.fixMax(this.top + height, parent.getBottom());
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

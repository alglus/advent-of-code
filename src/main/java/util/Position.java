package util;

public class Position {

    private int x;
    private int y;
    private Range<Integer> rangeX;
    private Range<Integer> rangeY;

    public Position(int x, int y) {
        var maxRange = new Range<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        new Position(x, y, maxRange, maxRange);
    }

    public Position(int x, int y, Range<Integer> rangeX, Range<Integer> rangeY) {
        this.x = x;
        this.y = y;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void move(Step step) {
        this.x += step.x();
        this.y += step.y();
    }

    public Position add(Step step) {
        return new Position(x + step.x(), y + step.y(), rangeX, rangeY);
    }

    public int xDistanceTo(Position other) {
        return this.x - other.x;
    }

    public int yDistanceTo(Position other) {
        return this.y - other.y;
    }

    public boolean isInRange() {
        return rangeX.contains(x) && rangeY.contains(y);
    }

    @Override
    public String toString() {
        return "(" + x + ',' + y + ')';
    }

}

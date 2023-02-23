package util;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(Step step) {
        this.x += step.x();
        this.y += step.y();
    }

    public int xDistanceTo(Position other) {
        return this.x - other.x;
    }

    public int yDistanceTo(Position other) {
        return this.y - other.y;
    }

    @Override
    public String toString() {
        return "(" + x + ',' + y + ')';
    }
}

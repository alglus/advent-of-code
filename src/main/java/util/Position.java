package util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Position {

    private int x;
    private int y;

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

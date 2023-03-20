package util;

public enum Directions {
    R(1, 0), L(-1, 0), U(0, -1), D(0, 1);

    private final Step step;

    Directions(int stepX, int stepY) {
        this.step = new Step(stepX, stepY);
    }

    public Step step() {
        return step;
    }
}


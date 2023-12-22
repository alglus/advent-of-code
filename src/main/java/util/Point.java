package util;

public record Point(int x, int y) {
    
    public static Point at(int x, int y) {
        return new Point(x, y);
    }

    public Point add(Step step) {
        return new Point(x + step.x(), y + step.y());
    }

}

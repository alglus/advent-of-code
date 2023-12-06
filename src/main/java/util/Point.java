package util;

public record Point(int x, int y) {
    public static Point at(int x, int y) {
        return new Point(x, y);
    }
}

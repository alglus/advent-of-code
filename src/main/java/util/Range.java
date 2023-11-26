package util;

public record Range(int fromInc, int toInc) {

    public boolean contains(int point) {
        return this.fromInc <= point && point <= toInc;
    }

    public boolean inside(Range other) {
        return this.fromInc >= other.fromInc && this.toInc <= other.toInc;
    }

    public boolean overlaps(Range other) {
        return this.contains(other.fromInc) || this.contains(other.toInc);
    }

}

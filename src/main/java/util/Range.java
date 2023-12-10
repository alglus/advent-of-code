package util;

public record Range<T extends Number & Comparable<T>>(T fromInc, T toInc) {

    public boolean contains(T value) {
        return fromInc.compareTo(value) <= 0 && value.compareTo(toInc) <= 0;
    }

    public boolean inside(Range<T> other) {
        return other.fromInc.compareTo(this.fromInc) <= 0
                && this.toInc.compareTo(other.toInc) <= 0;
    }

    public boolean overlaps(Range<T> other) {
        return this.fromInc.compareTo(other.toInc) <= 0
                && other.fromInc.compareTo(this.toInc) <= 0;
    }

}

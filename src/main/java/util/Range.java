package util;

public record Range(int fromInc, int toInc) {

    public boolean spans(int point) {
        return this.fromInc <= point && point <= toInc;
    }
}

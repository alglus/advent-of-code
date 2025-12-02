package util;

public class MathUtil {

    public static long gcd(long a, long b) {
        while (b != 0) {
            final var temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long lcm(final long a, final long b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    public static boolean isSignDifferent(final Integer a, final Integer b) {
        return a * b < 0;
    }

    public static boolean isDivisible(final long numerator, final long denominator) {
        return numerator % denominator == 0;
    }

    public static boolean isEven(final int n) {
        return (n & 1) == 0;
    }

    public static boolean isOdd(final int n) {
        return !isEven(n);
    }

    public static boolean isInteger(final double n) {
        return n % 1 == 0;
    }
}

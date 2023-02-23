package util;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuadFunction<S, T, U, V, R> {

    R apply(S var1, T var2, U var3, V var4);

    default <W> QuadFunction<S, T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (s, t, u, v) -> after.apply(this.apply(s, t, u, v));
    }
}

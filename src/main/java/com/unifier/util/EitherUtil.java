package com.unifier.util;

import io.vavr.*;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EitherUtil {

    public static <L, R> Either<L, R> EitherTry(CheckedFunction0<? extends R> checkedFunction, Function<? super Throwable, L> errorFn) {
        try {
            return Either.right(checkedFunction.apply());
        } catch (Throwable throwable) {
            return Either.left(errorFn.apply(throwable));
        }
    }

    public static <L extends Throwable, R> Function0<Either<L, R>> liftAndMapLeft(Function0<? extends R> partialFunction,
                                                                                         Function<Throwable, L> leftMapper) {
        return () -> Try.<R>of(() -> partialFunction.apply()).toEither().mapLeft(leftMapper);
    }

    public static <T1, L extends Throwable, R> Function<T1, Either<L, R>> liftAndMapLeft(Function<? super T1, ? extends R> partialFunction,
                                                                                          Function<Throwable, L> leftMapper) {
        return (t1) -> Try.<R>of(() -> partialFunction.apply(t1)).toEither().mapLeft(leftMapper);
    }

    public static <T1, T2, L extends Throwable, R> Function2<T1, T2, Either<L, R>> liftAndMapLeft(Function2<? super T1, ? super T2, ? extends R> partialFunction,
                                                                                                   Function<Throwable, L> leftMapper) {
        return (t1, t2) -> Try.<R>of(() -> partialFunction.apply(t1, t2)).toEither().mapLeft(leftMapper);
    }

    public static <T1, T2, T3, L extends Throwable, R> Function3<T1, T2, T3, Either<L, R>> liftAndMapLeft(Function3<? super T1, ? super T2,? super T3, ? extends R> partialFunction,
                                                                                                           Function<Throwable, L> leftMapper) {
        return (t1, t2, t3) -> Try.<R>of(() -> partialFunction.apply(t1, t2, t3)).toEither().mapLeft(leftMapper);
    }

    public static <T1, T2, T3, T4, L extends Throwable, R> Function4<T1, T2, T3, T4, Either<L, R>> liftAndMapLeft(Function4<? super T1, ? super T2, ? super T3,
            ? super T4, ? extends R> partialFunction, Function<Throwable, L> leftMapper) {
        return (t1, t2, t3, t4) -> Try.<R>of(() -> partialFunction.apply(t1, t2, t3, t4)).toEither().mapLeft(leftMapper);
    }


    //Methods below 'liftAndMapLeft2' almost equal as above methods, diff is leftMapper can also take 1st argument passed to partialFunction, <br>
    //this is done to support better error handling for caller
    public static <T1, L extends Throwable, R> Function<T1, Either<L, R>> liftAndMapLeft2(Function<? super T1, ? extends R> partialFunction,
                                                                                          Function<? super T1, Function<Throwable, L>> leftMapper) {
        return (t1) -> Try.<R>of(() -> partialFunction.apply(t1)).toEither().mapLeft(leftMapper.apply(t1));
    }

    public static <T1, T2, L extends Throwable, R> Function2<T1, T2, Either<L, R>> liftAndMapLeft2(Function2<? super T1, ? super T2, ? extends R> partialFunction,
                                                                                          Function2<? super T1, ? super T2, Function<Throwable, L>> leftMapper) {
        return (t1, t2) -> Try.<R>of(() -> partialFunction.apply(t1, t2)).toEither().mapLeft(leftMapper.apply(t1, t2));
    }

    public static <T1, T2, T3, L extends Throwable, R> Function3<T1, T2, T3, Either<L, R>> liftAndMapLeft2(Function3<? super T1, ? super T2, ? super T3, ? extends R> partialFunction,
                                                                                                   Function3<? super T1, ? super T2, ? super T3, Function<Throwable, L>> leftMapper) {
        return (t1, t2, t3) -> Try.<R>of(() -> partialFunction.apply(t1, t2, t3)).toEither().mapLeft(leftMapper.apply(t1, t2, t3));
    }
}

/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.collect;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.google.common.base.Throwables;
import com.opengamma.collect.function.CheckedBiConsumer;
import com.opengamma.collect.function.CheckedBiFunction;
import com.opengamma.collect.function.CheckedBiPredicate;
import com.opengamma.collect.function.CheckedBinaryOperator;
import com.opengamma.collect.function.CheckedConsumer;
import com.opengamma.collect.function.CheckedFunction;
import com.opengamma.collect.function.CheckedPredicate;
import com.opengamma.collect.function.CheckedRunnable;
import com.opengamma.collect.function.CheckedSupplier;
import com.opengamma.collect.function.CheckedUnaryOperator;

/**
 * Static utility methods that allow a lambda block to be decorated to avoid
 * handling checked exceptions.
 * <p>
 * For example, the method {@link File#getCanonicalFile()} throws an {@link IOException}
 * which can be handled as follows:
 * <pre>
 *  stream.map(Unchecked.function(file -> file.getCanonicalFile())
 * </pre>
 * <p>
 * Each method accepts a functional interface that is defined to throw {@link Throwable}.
 * Catching {@code Throwable} means that any method can be wrapped.
 * Any {@link IOException} is converted to an {@link UncheckedIOException}.
 * Any {@link Error} or {@link RuntimeException} is re-thrown without alteration.
 * Any other exception is wrapped in a {@link RuntimeException}.
 */
public final class Unchecked {

  /**
   * Restricted constructor.
   */
  private Unchecked() {
  }

  //-------------------------------------------------------------------------
  /**
   * Converts checked exceptions to unchecked based on the {@code Runnable} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param runnable  the runnable to be decorated
   * @return the runnable instance that handles checked exceptions
   */
  public static Runnable runnable(CheckedRunnable runnable) {
    return () -> {
      try {
        runnable.run();
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts checked exceptions to unchecked based on the {@code Function} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the input type of the function
   * @param <R>  the return type of the function
   * @param function  the function to be decorated
   * @return the function instance that handles checked exceptions
   */
  public static <T, R> Function<T, R> function(CheckedFunction<T, R> function) {
    return (t) -> {
      try {
        return function.apply(t);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  /**
   * Converts checked exceptions to unchecked based on the {@code BiFunction} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the first input type of the function
   * @param <U>  the second input type of the function
   * @param <R>  the return type of the function
   * @param function  the function to be decorated
   * @return the function instance that handles checked exceptions
   */
  public static <T, U, R> BiFunction<T, U, R> biFunction(CheckedBiFunction<T, U, R> function) {
    return (t, u) -> {
      try {
        return function.apply(t, u);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts checked exceptions to unchecked based on the {@code UnaryOperator} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the operator
   * @param function  the function to be decorated
   * @return the function instance that handles checked exceptions
   */
  public static <T> UnaryOperator<T> unaryOperator(CheckedUnaryOperator<T> function) {
    return (t) -> {
      try {
        return function.apply(t);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  /**
   * Converts checked exceptions to unchecked based on the {@code BinaryOperator} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the operator
   * @param function  the function to be decorated
   * @return the function instance that handles checked exceptions
   */
  public static <T> BinaryOperator<T> binaryOperator(CheckedBinaryOperator<T> function) {
    return (t, u) -> {
      try {
        return function.apply(t, u);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts checked exceptions to unchecked based on the {@code Predicate} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the predicate
   * @param predicate  the predicate to be decorated
   * @return the predicate instance that handles checked exceptions
   */
  public static <T> Predicate<T> predicate(CheckedPredicate<T> predicate) {
    return (t) -> {
      try {
        return predicate.test(t);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  /**
   * Converts checked exceptions to unchecked based on the {@code BiPredicate} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the first type of the predicate
   * @param <U>  the second type of the predicate
   * @param predicate  the predicate to be decorated
   * @return the predicate instance that handles checked exceptions
   */
  public static <T, U> BiPredicate<T, U> biPredicate(CheckedBiPredicate<T, U> predicate) {
    return (t, u) -> {
      try {
        return predicate.test(t, u);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts checked exceptions to unchecked based on the {@code Consumer} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the type of the consumer
   * @param consumer  the consumer to be decorated
   * @return the consumer instance that handles checked exceptions
   */
  public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer) {
    return (t) -> {
      try {
        consumer.accept(t);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  /**
   * Converts checked exceptions to unchecked based on the {@code BiConsumer} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <T>  the first type of the consumer
   * @param <U>  the second type of the consumer
   * @param consumer  the consumer to be decorated
   * @return the consumer instance that handles checked exceptions
   */
  public static <T, U> BiConsumer<T, U> biConsumer(CheckedBiConsumer<T, U> consumer) {
    return (t, u) -> {
      try {
        consumer.accept(t, u);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

  //-------------------------------------------------------------------------
  /**
   * Converts checked exceptions to unchecked based on the {@code Supplier} interface.
   * <p>
   * This executes the specified block of code.
   * If a checked exception is thrown it is converted to an {@link UncheckedIOException}
   * or {@link RuntimeException} as appropriate.
   * 
   * @param <R>  the result type of the supplier
   * @param supplier  the supplier to be decorated
   * @return the supplier instance that handles checked exceptions
   */
  public static <R> Supplier<R> supplier(CheckedSupplier<R> supplier) {
    return () -> {
      try {
        return supplier.get();
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Throwable ex) {
        throw Throwables.propagate(ex);
      }
    };
  }

}

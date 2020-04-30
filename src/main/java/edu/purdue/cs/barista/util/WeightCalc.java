package edu.purdue.cs.barista.util;

import edu.purdue.cs.barista.TestCase;
import edu.purdue.cs.barista.TestSuite;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.reflections.Reflections;

/**
 * The {@link WeightCalc} class checks that all test cases total
 * to {@code 100.0}. If the test cases do not total to {@code 100.0},
 * Gradescope could give students an incorrect grade.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.3.1, 03/26/2020
 * @since 1.1
 */
public final class WeightCalc {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String SUCCESS_MESSAGE = "ALL TESTS TOTAL TO 100.0!";
    private static final String ERROR_MESSAGE = "!! WARNING: TEST CASES DO NOT TOTAL TO 100.0 !!";

    /**
     * Private default constructor so no objects can be created of this type.
     */
    private WeightCalc() {
    }

    /**
     * Checks that all test cases in default package total to {@code 100.0}.
     *
     * @param args has no effect
     */
    public static void main(String[] args) {
        if (totalsCorrectly("")) {
            System.out.printf("%s%s%s\n", ANSI_GREEN, SUCCESS_MESSAGE, ANSI_RESET);
            System.exit(0);
        } else {
            System.out.printf("%s%s%s\n", ANSI_RED, ERROR_MESSAGE, ANSI_RESET);
            System.exit(1);
        }
    }

    /**
     * Checks that all test cases in the given package total to {@code 100.0}.
     *
     * @param packageName the package to search
     * @return {@code true} if the tests total to 100.0, {@code false} otherwise
     */
    static boolean totalsCorrectly(String packageName) {
        Reflections reflect = new Reflections(packageName);
        Set<Class<?>> testSuites = reflect.getTypesAnnotatedWith(TestSuite.class);
        return calculateTotal(testSuites) == 100.0;
    }

    /**
     * Calculates the total points from each {@link TestCase} in the
     * given {@link TestSuite}s.
     *
     * @param testSuites the test suites to scan
     * @return the total points for the {@link TestSuite}s
     */
    static double calculateTotal(Set<Class<?>> testSuites) {
        double grandTotal = 0;

        for (Class<?> testSuite : testSuites) {
            Set<Class<?>> innerClasses = new HashSet<>(Arrays.asList(testSuite.getDeclaredClasses()));
            double classTotal = calculateClassTotal(testSuite);

            if (!innerClasses.isEmpty()) {
                classTotal += calculateTotal(innerClasses);
            }

            grandTotal += classTotal;
        }

        return grandTotal;
    }

    /**
     * Calculates the total {@link TestCase} points in the given
     * {@link Class}.
     *
     * @param clazz the class to total {@link TestCase}s in
     * @return the total points for the {@link TestSuite}s
     */
    static double calculateClassTotal(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
            .map(m -> m.getAnnotation(TestCase.class))
            .filter(Objects::nonNull)
            .mapToDouble(TestCase::points)
            .sum();
    }

}

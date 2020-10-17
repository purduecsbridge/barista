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
 * to a given {@code maxScore}. If the test cases do not total to the max score set
 * in Gradescope, students could be given an incorrect grade.
 *
 * @author Andrew Davis, asd@alumni.purdue.edu
 * @version 3.1, 2020-05-10
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
     * Checks that all {@link TestCase}s in a given package total to a maximum score.
     * Totals test weights for {@link TestSuite} classes inside of the package given by the
     * {@code test.package.name} system property. If this property is not set,
     * the {@code default} package is used to calculate test weights. The maximum score is given by the
     * {@code test.maxScore} system property. The value of {@code test.maxScore} must be a non-negative integer.
     * If the maxScore property is not specified, 100 is used as the default.
     *
     * @param args has no effect
     */
    public static void main(String[] args) {
        String testPackage = System.getProperty("test.package.name", "");
        int maxScore;
        try {
            maxScore = Integer.parseInt(System.getProperty("test.maxScore", "100"));
            assert maxScore >= 0;
        } catch (NumberFormatException | AssertionError e) {
            throw new IllegalArgumentException("`test.maxScore` system property must be a non-negative integer");
        }

        if (totalsCorrectly(testPackage, maxScore)) {
            System.out.printf("%s%s%s\n", ANSI_GREEN, SUCCESS_MESSAGE, ANSI_RESET);
            System.exit(0);
        } else {
            System.out.printf("%s%s%s\n", ANSI_RED, ERROR_MESSAGE, ANSI_RESET);
            System.exit(1);
        }
    }

    /**
     * Checks that all test cases in the given package total to {@code maxScore}.
     *
     * @param packageName the package to search
     * @param maxScore    the maximum score to check the total against
     * @return {@code true} if the tests total to the maxScore, {@code false} otherwise
     */
    static boolean totalsCorrectly(String packageName, double maxScore) {
        Reflections reflect = new Reflections(packageName);
        Set<Class<?>> testSuites = reflect.getTypesAnnotatedWith(TestSuite.class);
        return calculateTotal(testSuites) == maxScore;
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

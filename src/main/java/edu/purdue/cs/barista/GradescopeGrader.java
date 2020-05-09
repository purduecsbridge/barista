package edu.purdue.cs.barista;

import com.github.tkutche1.jgrade.Grader;
import com.github.tkutche1.jgrade.gradescope.GradescopeJsonObserver;

import java.util.Arrays;

import org.junit.runner.JUnitCore;

/**
 * The {@link GradescopeGrader} class contains logic to run graded {@link TestCase} methods.
 * The {@link GradescopeGrader#run} method will run a set of {@link TestSuite} classes
 * and print out the results.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 2.0, 04/29/2020
 * @since 1.0
 */
public final class GradescopeGrader {

    /**
     * Private default constructor so no objects can be created of this type.
     */
    private GradescopeGrader() {
    }

    /**
     * Runs all of the {@link TestCase} methods in the given {@link TestSuite}
     * classes. Prints the test results to {@link System#out} in JSON that
     * Gradescope can parse. This output can be redirected to
     * {@code /autograder/results/results.json} inside of a Gradescope Docker
     * container.
     *
     * @param testSuites an array of {@link TestSuite} classes to run
     * @param maxScore   the maximum total score for the given {@link TestCase}s
     * @throws IllegalArgumentException if not all of the given classes have the {@link TestSuite} annotation
     */
    public static void run(Class<?>[] testSuites, double maxScore) {
        if (!Arrays.stream(testSuites).allMatch(s -> s.isAnnotationPresent(TestSuite.class))) {
            throw new IllegalArgumentException("All test suites must have the @TestSuite annotation.");
        }

        Grader grader = new Grader();

        GradescopeJsonObserver observer = new GradescopeJsonObserver();
        grader.attachOutputObserver(observer);

        GradescopeListener listener = new GradescopeListener();

        JUnitCore runner = new JUnitCore();
        runner.addListener(listener);

        grader.startTimer();
        runner.run(testSuites);
        grader.stopTimer();

        listener.getTestResults().forEach(grader::addGradedTestResult);

        grader.setMaxScore(maxScore);
        grader.notifyOutputObservers();

        observer.setPrettyPrint(2);
        System.out.println(observer.toString());
    }

}

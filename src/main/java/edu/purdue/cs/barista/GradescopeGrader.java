package edu.purdue.cs.barista;

import com.github.tkutche1.jgrade.Grader;
import com.github.tkutche1.jgrade.gradescope.GradescopeJsonObserver;

import java.util.Arrays;
import java.util.Objects;

import org.junit.runner.JUnitCore;

/**
 * The {@link GradescopeGrader} class contains logic to run graded {@link TestCase} methods.
 * The {@link GradescopeGrader#run} method will run a set of {@link TestSuite} classes
 * and print out the results.
 *
 * @author Andrew Davis, asd@alumni.purdue.edu
 * @version 3.1, 2020-08-17
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
     * @param testSuites an array of classes with {@link TestCase}s to run
     * @param maxScore   the maximum total score for the given {@link TestCase}s
     * @throws IllegalArgumentException if {@code maxScore} is less than 0
     *                                  or {@code testSuites} is null or contains null elements
     */
    public static void run(Class<?>[] testSuites, double maxScore) {
        if (testSuites == null || Arrays.stream(testSuites).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("testSuites parameter cannot be null or contain null elements.");
        }

        if (maxScore < 0) {
            throw new IllegalArgumentException("maxScore cannot be negative.");
        }

        Grader grader = new Grader();

        GradescopeJsonObserver observer = new GradescopeJsonObserver();
        grader.attachOutputObserver(observer);

        GradescopeListener listener = new GradescopeListener(maxScore);

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

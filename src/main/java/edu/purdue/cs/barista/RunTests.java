package edu.purdue.cs.barista;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

import org.junit.runner.JUnitCore;

import org.reflections.Reflections;

import com.github.tkutche1.jgrade.Grader;
import com.github.tkutche1.jgrade.gradedtest.GradedTestResult;
import com.github.tkutche1.jgrade.gradescope.GradescopeJsonObserver;

/**
 * The {@link RunTests} class contains the logic to run tests
 * for a given set of test suites on Gradescope.
 * {@link RunTests#main} will run all of the tests in
 * the default package that have the {@link TestSuite} annotation.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.0, 10/17/2019
 * @since 1.0
 */
public final class RunTests {

    /**
     * Private default constructor so no objects can be created of this type.
     */
    private RunTests() {
    }

    /**
     * Runs the {@link TestSuite}s in the {@code default} package.
     *
     * @param args has no effect
     */
    public static void main(String[] args) {
        Grader grader = new Grader();

        GradescopeJsonObserver observer = new GradescopeJsonObserver();
        grader.attachOutputObserver(observer);

        GradescopeListener listener = new GradescopeListener();

        JUnitCore runner = new JUnitCore();
        runner.addListener(listener);

        Class<?>[] testSuites = getTestSuites();

        grader.startTimer();
        runner.run(testSuites);
        grader.stopTimer();

        List<GradedTestResult> results = listener.getTestResults();
        for (GradedTestResult result : results) {
            grader.addGradedTestResult(result);
        }

        grader.setMaxScore(100.0);
        grader.notifyOutputObservers();

        observer.setPrettyPrint(2);
        System.out.println(observer.toString());
    }

    /**
     * Gets the {@link TestSuite} classes in the {@code default} package.
     *
     * @return all {@link TestSuite} classes in the {@code default} package
     */
    private static Class<?>[] getTestSuites() {
        // Change standard error to print to nowhere
        // Removes debug statements from org.reflections library
        final PrintStream ORIGINAL_ERR = System.err;
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // do nothing
            }
        }));

        Reflections reflect = new Reflections("");
        Set<Class<?>> testSet = reflect.getTypesAnnotatedWith(TestSuite.class);

        System.setErr(ORIGINAL_ERR);

        return testSet.toArray(new Class<?>[]{});
    }

}

package edu.purdue.cs.barista;

import com.github.tkutche1.jgrade.gradedtest.GradedTestResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * The {@link GradescopeListener} class is adapted from the
 * {@link com.github.tkutche1.jgrade.gradedtest.GradedTestListener} class from Tim Kutcher.
 * Modifications have been made such that the {@link TestCase} annotation can be used in
 * {@link TestSuite} classes.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 3.1, 2020-08-17
 * @since 1.0
 */
public class GradescopeListener extends RunListener {

    private final static PrintStream ORIGINAL_OUT = System.out;

    /**
     * List of test results to pass to Gradescope.
     */
    private final Map<String, GradedTestResult> testResults;

    /**
     * The output of the current test being ran.
     */
    private final Map<String, ByteArrayOutputStream> testOutputs;

    /**
     * The maximum score for the assignment.
     */
    private final double maxScore;

    /**
     * Creates a new {@link GradescopeListener} object.
     *
     * @param maxScore the maximum score to scale the test cases to
     */
    public GradescopeListener(double maxScore) {
        this.testResults = new HashMap<>();
        this.testOutputs = new HashMap<>();
        this.maxScore = maxScore;
    }

    /**
     * Returns the list of test results.
     *
     * @return the list of test results
     */
    public Collection<GradedTestResult> getTestResults() {
        scaleTestCases();
        return this.testResults.values();
    }

    /**
     * {@inheritDoc}
     *
     * Called when a JUnit test is about to start. Reads test case metadata and sets up the
     * testing environment.
     *
     * @param description the {@link Description} object given by JUnit
     */
    @Override
    public void testStarted(Description description) {
        final String testKey = description.getDisplayName();
        TestCase testCase = description.getAnnotation(TestCase.class);
        if (testCase != null) {
            this.testResults.put(testKey, new GradedTestResult(
                    testCase.name(),
                    testCase.number(),
                    testCase.points(),
                    testCase.visibility().toString()
            ));
            this.testResults.get(testKey).setScore(testCase.points());
        }

        this.testOutputs.put(testKey, new ByteArrayOutputStream());
        System.setOut(new PrintStream(this.testOutputs.get(testKey)));
    }

    /**
     * {@inheritDoc}
     *
     * Called when a JUnit test has finished. Adds the test result and cleans up the
     * testing environment.
     *
     * @param description the {@link Description} object given by JUnit
     */
    @Override
    public void testFinished(Description description) {
        final String testKey = description.getDisplayName();
        final String testOutput = this.testOutputs.get(testKey).toString();
        if (this.testResults.containsKey(testKey)) {
            this.testResults.get(testKey).addOutput(testOutput);
        }

        System.setOut(ORIGINAL_OUT);
    }

    /**
     * {@inheritDoc}
     *
     * Called when a JUnit test has failed. Sets the score to 0 and gives test output.
     *
     * @param failure the {@link Failure} object given by JUnit
     */
    @Override
    public void testFailure(Failure failure) {
        final String testKey = failure.getDescription().getDisplayName();

        // This is a setup/teardown method failure
        if (failure.getDescription().isSuite()) {
            String testSuiteName = failure.getDescription().getTestClass().getSimpleName();
            GradedTestResult result = new GradedTestResult(
                "INFO: " + testSuiteName,
                "",
                0.0,
                TestCase.Visibility.VISIBLE.toString()
            );
            result.addOutput(failure.getMessage() + "\n");
            this.testResults.put(testKey, result);
        } else {
            if (this.testResults.containsKey(testKey)) {
                GradedTestResult result = this.testResults.get(testKey);
                result.setScore(0);
                result.addOutput("TEST FAILED:\n");

                if (failure.getMessage() != null) {
                    result.addOutput(failure.getMessage());
                } else {
                    result.addOutput("No description provided.");
                }

                result.addOutput("\n");
                result.setPassed(false);
            }
        }
    }

    /**
     * Scales the test cases to the {@link GradescopeListener#maxScore}.
     * Called when the test results are requested.
     */
    void scaleTestCases() {
        final double total = this.testResults.values().stream().mapToDouble(GradedTestResult::getPoints).sum();
        final double ratio = maxScore / total;
        this.testResults.values().forEach(r -> {
            r.setPoints(r.getPoints() * ratio);
            r.setScore(r.getScore() * ratio);
        });
    }

}

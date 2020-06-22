package edu.purdue.cs.barista;

import com.github.tkutche1.jgrade.gradedtest.GradedTestResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
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
 * @version 2.1.1, 06/21/2020
 * @since 1.0
 */
public class GradescopeListener extends RunListener {

    private final static PrintStream ORIGINAL_OUT = System.out;

    /**
     * List of test results to pass to Gradescope.
     */
    private ArrayList<GradedTestResult> testResults;

    /**
     * The results of the current test being ran.
     */
    private GradedTestResult currentTestResult;

    /**
     * The output of the current test being ran.
     */
    private ByteArrayOutputStream currentTestOutput;

    /**
     * Creates a new {@link GradescopeListener} object.
     */
    public GradescopeListener() {
        this.testResults = new ArrayList<>();
        this.currentTestOutput = new ByteArrayOutputStream();
    }

    /**
     * Returns the list of test results.
     *
     * @return the list of test results
     */
    public List<GradedTestResult> getTestResults() {
        return this.testResults;
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
        this.currentTestResult = null;

        TestCase testCase = description.getAnnotation(TestCase.class);
        if (testCase != null) {
            this.currentTestResult = new GradedTestResult(
                    testCase.name(),
                    testCase.number(),
                    testCase.points(),
                    testCase.visibility().toString()
            );

            this.currentTestResult.setScore(testCase.points());
        }

        this.currentTestOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.currentTestOutput));
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
        if (this.currentTestResult != null) {
            this.currentTestResult.addOutput(currentTestOutput.toString());
            this.testResults.add(this.currentTestResult);
        }

        this.currentTestResult = null;
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
        TestSuite testSuite = failure.getDescription().getAnnotation(TestSuite.class);

        // This is a setup/teardown method failure
        if (testSuite != null) {
            String testSuiteName = failure.getDescription().getTestClass().getSimpleName();
            GradedTestResult result = new GradedTestResult(
                "INFO: " + testSuiteName,
                "",
                0.0,
                TestCase.Visibility.VISIBLE.toString()
            );
            result.addOutput(failure.getMessage() + "\n");
            this.testResults.add(result);
        } else {
            if (this.currentTestResult != null) {
                this.currentTestResult.setScore(0);
                this.currentTestResult.addOutput("TEST FAILED:\n");

                if (failure.getMessage() != null) {
                    this.currentTestResult.addOutput(failure.getMessage());
                } else {
                    this.currentTestResult.addOutput("No description provided.");
                }

                this.currentTestResult.addOutput("\n");
                this.currentTestResult.setPassed(false);
            }
        }
    }

}

package edu.purdue.cs.barista;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;

/**
 * The {@link TestError} class contains common methods to represent
 * common test failures.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.2.1, 2019-12-23
 * @since 1.2.1
 */
public final class TestError {

    /**
     * Private default constructor so no objects can be created of this type.
     */
    private TestError() {
    }

    /**
     * Alerts the grader/student when a test fails unexpectedly.
     *
     * @param testCase the name of the test case
     */
    public static void unexpectedError(String testCase) {
        Assert.fail(String.format("Test case %s failed unexpectedly.", testCase));
    }

    /**
     * Alerts the grader/student when a test fails due to student error.
     *
     * @param e the {@link Exception} thrown during testing
     */
    public static void studentError(Throwable e) {
        ByteArrayOutputStream error = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(error));
        Assert.fail(error.toString());
    }

}

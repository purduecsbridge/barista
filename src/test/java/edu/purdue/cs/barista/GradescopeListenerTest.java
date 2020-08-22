package edu.purdue.cs.barista;

import com.github.tkutche1.jgrade.gradedtest.GradedTestResult;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Tests the {@link GradescopeListener} class.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 3.1, 2020-08-21
 * @since 3.1
 */
public class GradescopeListenerTest {

    @Test(timeout = 1000)
    @SuppressWarnings("unchecked")
    public void testScaleTestCases() {
        final int maxScore = 100;
        GradescopeListener listener = new GradescopeListener(maxScore);

        Field testResultsField;
        try {
            testResultsField = listener.getClass().getDeclaredField("testResults");
            testResultsField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        Map<String, GradedTestResult> testResults;
        try {
            testResults = (Map<String, GradedTestResult>) testResultsField.get(listener);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 4; i++) {
            testResults.put(String.valueOf(i), new GradedTestResult("", "", 5, "visible"));
            testResults.get(String.valueOf(i)).setScore(5);
        }

        listener.scaleTestCases();

        boolean testsScaled = testResults.values().stream().allMatch(r -> r.getPoints() == 25 && r.getScore() == 25);
        double total = testResults.values().stream().mapToDouble(GradedTestResult::getPoints).sum();

        Assert.assertTrue(testsScaled);
        Assert.assertEquals(maxScore, total, 0.0);
    }

    @Test(timeout = 1000)
    @SuppressWarnings("unchecked")
    public void testScaleTestCasesRepeatingRemainder() {
        final int maxScore = 100;
        GradescopeListener listener = new GradescopeListener(maxScore);

        Field testResultsField;
        try {
            testResultsField = listener.getClass().getDeclaredField("testResults");
            testResultsField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        Map<String, GradedTestResult> testResults;
        try {
            testResults = (Map<String, GradedTestResult>) testResultsField.get(listener);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 3; i++) {
            testResults.put(String.valueOf(i), new GradedTestResult("", "", 1, "visible"));
            testResults.get(String.valueOf(i)).setScore(1);
        }

        listener.scaleTestCases();

        double total = testResults.values().stream().mapToDouble(GradedTestResult::getPoints).sum();

        Assert.assertEquals(maxScore, total, 0.0);
    }

}

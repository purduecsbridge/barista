package edu.purdue.cs.barista.util;

import edu.purdue.cs.barista.TestCase;
import edu.purdue.cs.barista.TestSuite;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests the {@link WeightCalc} class.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.1.4, 2019-12-04
 * @since 1.1.4
 */
public class WeightCalcTest {

    /**
     * Tests the {@link WeightCalc#totalsCorrectly} method.
     */
    @Test(timeout = 1000)
    public void totalsCorrectlyTest() {
        final String prefix = this.getClass().getPackage().getName();
        Assert.assertTrue(WeightCalc.totalsCorrectly(prefix));
    }

    /**
     * Tests the {@link WeightCalc#calculateTotal} method.
     */
    @Test(timeout = 1000)
    public void calculateTotalTest() {
        final Set<Class<?>> input = new HashSet<>(Collections.singletonList(TestSuiteOne.class));
        final double expected = 35.0;
        final double actual = WeightCalc.calculateTotal(input);

        Assert.assertEquals(expected, actual, 0);
    }

    /**
     * Input {@link TestSuite} 1 for testing.
     */
    @TestSuite
    static class TestSuiteOne {
        @TestCase(points = 15.0)
        public void test1(){}

        @TestCase(points = 20.0)
        public void test2(){}
    }

    /**
     * Input {@link TestSuite} 2 for testing.
     */
    @TestSuite
    static class TestSuiteTwo {
        @TestCase(points = 55.0)
        public void test1(){}

        @TestCase(points = 10.0)
        public void test2(){}
    }
}

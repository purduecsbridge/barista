package edu.purdue.cs.barista.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link StringUtilities} class.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.1.4, 2019-12-04
 * @since 1.1.4
 */
public class StringUtilitiesTest {

    /**
     * Tests the {@link StringUtilities#fuzzyEquals} method.
     */
    @Test(timeout = 1000)
    public void fuzzyEqualsTest() {
        final String s1 = "HeLlO\tWoRlD!!\n";
        final String s2 = "helloworld!!";
        final String s3 = "hello world";

        Assert.assertTrue(StringUtilities.fuzzyEquals(s1, s2));
        Assert.assertFalse(StringUtilities.fuzzyEquals(s2, s3));
    }

    /**
     * Tests the {@link StringUtilities#normalizeLineEndings} method.
     */
    @Test(timeout = 1000)
    public void normalizeLineEndingsTest() {
        final String input = "Hello\r\nWorld";
        final String expected = String.format("Hello%sWorld", System.lineSeparator());
        final String actual = StringUtilities.normalizeLineEndings(input);

        Assert.assertEquals(expected, actual);
    }

    /**
     * Tests the {@link StringUtilities#removeAllWhitespace} method.
     */
    @Test(timeout = 1000)
    public void removeAllWhitespaceTest() {
        final String input = "H\te\r\nl l\fo";
        final String expected = "Hello";
        final String actual = StringUtilities.removeAllWhitespace(input);

        Assert.assertEquals(expected, actual);
    }
}

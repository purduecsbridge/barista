package edu.purdue.cs.barista.util;

/**
 * The {@link SetupUtilities} class contains useful tools for setting up
 * test suites.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 2.0, 04/29/2020
 * @since 1.3
 */
public class SetupUtilities {

    /**
     * Returns the package to test. This is determined by
     * the {@code environment} system property. If the
     * {@code environment} property is set to {@code development},
     * the solution package, defined by the {@code solution.package.name}
     * system property, is returned, followed by a terminating period.
     * If the {@code environment} property is set to
     * {@code production} or anything but {@code development},
     * an empty {@link String} is returned, signifying the
     * {@code default} package.
     *
     * @return the name of the package to test
     */
    public static String getPackageToTest() {
        String environment = System.getProperty("environment", "production");

        if (environment.equals("development")) {
            String solutionPackage = System.getProperty("solution.package.name", "solution");
            return solutionPackage + ".";
        } else {
            return "";
        }
    }

}

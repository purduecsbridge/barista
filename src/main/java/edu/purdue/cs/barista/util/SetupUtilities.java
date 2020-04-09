package edu.purdue.cs.barista.util;

/**
 * The {@link SetupUtilities} class contains useful tools for setting up
 * test suites.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.3, 02/03/2020
 * @since 1.3
 */
public class SetupUtilities {

    /**
     * Returns the package to test, defined by the `BARISTA_TEST_PACKAGE`
     * environment variable.
     *
     * @return the name of the package to test
     */
    public static String getPackageToTest() {
        String packageName = System.getenv("BARISTA_TEST_PACKAGE");
        if (packageName == null) packageName = "";
        else packageName += ".";
        return packageName;
    }

}

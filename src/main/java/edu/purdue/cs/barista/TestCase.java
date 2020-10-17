package edu.purdue.cs.barista;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link TestCase} annotation allows test methods to be used
 * in Gradescope testing. It attaches to JUnit test methods and forwards
 * the results and the annotation parameters to Gradescope.
 *
 * @author Andrew Davis, asd@alumni.purdue.edu
 * @version 1.0, 2019-10-17
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestCase {

    /**
     * The name of the test case.
     *
     * @return the name of the test case
     */
    String name() default "Unnamed test";

    /**
     * The number of the test case. Can be used to group test cases.
     *
     * @return the number of the test case
     */
    String number() default "";

    /**
     * The amount of points the test case is worth.
     *
     * @return the weight of the test case
     */
    double points() default 1.0;

    /**
     * The visibility level of the test case.
     *
     * @return the visibility of the test case
     */
    Visibility visibility() default Visibility.VISIBLE;


    /**
     * The {@link Visibility} enum defines the test visibility
     * in Gradescope.
     */
    enum Visibility {

        /**
         * The test case results will always be shown to students.
         */
        VISIBLE("visible"),

        /**
         * The test case results will be shown after grades are published.
         */
        AFTER_PUBLISH("after_published"),

        /**
         * The test case results will be shown after the due date has passed.
         */
        AFTER_DUE("after_due_date"),

        /**
         * The test case results will never be shown to students.
         */
        HIDDEN("hidden");

        private final String visibility;

        Visibility(String visibility) {
            this.visibility = visibility;
        }

        @Override
        public String toString() {
            return this.visibility;
        }
    }

}

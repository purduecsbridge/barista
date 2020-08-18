package edu.purdue.cs.barista;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link TestCase} annotation allows test methods to be used
 * in Gradescope testing. It attaches to JUnit test methods and forwards
 * the results and the annotation parameters to Gradescope. {@link TestCase}
 * methods should be contained in a {@link TestSuite} class.
 * <p>
 * The following optional parameters may be used:
 * <ul>
 * <li>{@link String} name - the name of the test</li>
 * <li>{@link String} number - the number of the test</li>
 * <li>{@link double} maxScore - how many points the test is worth</li>
 * <li>
 *     {@link Visibility} visibility - the visibility level of the test, options for visibility are:
 *     <ul>
 *         <li>{@code VISIBLE} (default) - the test case results will always be shown to students</li>
 *         <li>{@code AFTER_PUBLISH} - the test case results will be shown after grades are published</li>
 *         <li>{@code AFTER_DUE} - the test case results will be shown after the due date has passed</li>
 *         <li>{@code HIDDEN} - the test case results will never be shown to students</li>
 *     </ul>
 * </li>
 * </ul>
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.0, 2019-10-17
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestCase {

    String name() default "Unnamed test";

    String number() default "";

    double points() default 1.0;

    Visibility visibility() default Visibility.VISIBLE;


    /**
     * The {@link Visibility} enum defines the test visibility
     * in Gradescope.
     */
    enum Visibility {
        VISIBLE("visible"),
        AFTER_PUBLISH("after_published"),
        AFTER_DUE("after_due_date"),
        HIDDEN("hidden");

        private String visibility;

        Visibility(String visibility) {
            this.visibility = visibility;
        }

        @Override
        public String toString() {
            return this.visibility;
        }
    }

}
